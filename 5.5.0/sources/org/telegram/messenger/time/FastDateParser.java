package org.telegram.messenger.time;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FastDateParser implements Serializable, DateParser {
    private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1) {
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            int parseInt = Integer.parseInt(str);
            if (parseInt < 100) {
                parseInt = fastDateParser.adjustYear(parseInt);
            }
            calendar.set(1, parseInt);
        }
    };
    private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
    private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
    private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
    private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
    private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
    static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
    private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
    private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);
    private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
    private static final Strategy MODULO_HOUR_OF_DAY_STRATEGY = new NumberStrategy(11) {
        int modify(int i) {
            return i % 24;
        }
    };
    private static final Strategy MODULO_HOUR_STRATEGY = new NumberStrategy(10) {
        int modify(int i) {
            return i % 12;
        }
    };
    private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2) {
        int modify(int i) {
            return i - 1;
        }
    };
    private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
    private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
    private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
    private static final ConcurrentMap<Locale, Strategy>[] caches = new ConcurrentMap[17];
    private static final Pattern formatPattern = Pattern.compile("D+|E+|F+|G+|H+|K+|M+|S+|W+|Z+|a+|d+|h+|k+|m+|s+|w+|y+|z+|''|'[^']++(''[^']*+)*+'|[^'A-Za-z]++");
    private static final long serialVersionUID = 2;
    private final int century;
    private transient String currentFormatField;
    private final Locale locale;
    private transient Strategy nextStrategy;
    private transient Pattern parsePattern;
    private final String pattern;
    private final int startYear;
    private transient Strategy[] strategies;
    private final TimeZone timeZone;

    private static abstract class Strategy {
        private Strategy() {
        }

        abstract boolean addRegex(FastDateParser fastDateParser, StringBuilder stringBuilder);

        boolean isNumber() {
            return false;
        }

        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
        }
    }

    private static class NumberStrategy extends Strategy {
        private final int field;

        NumberStrategy(int i) {
            super();
            this.field = i;
        }

        boolean addRegex(FastDateParser fastDateParser, StringBuilder stringBuilder) {
            if (fastDateParser.isNextNumber()) {
                stringBuilder.append("(\\p{Nd}{").append(fastDateParser.getFieldWidth()).append("}+)");
            } else {
                stringBuilder.append("(\\p{Nd}++)");
            }
            return true;
        }

        boolean isNumber() {
            return true;
        }

        int modify(int i) {
            return i;
        }

        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            calendar.set(this.field, modify(Integer.parseInt(str)));
        }
    }

    private static class CopyQuotedStrategy extends Strategy {
        private final String formatField;

        CopyQuotedStrategy(String str) {
            super();
            this.formatField = str;
        }

        boolean addRegex(FastDateParser fastDateParser, StringBuilder stringBuilder) {
            FastDateParser.escapeRegex(stringBuilder, this.formatField, true);
            return false;
        }

        boolean isNumber() {
            char charAt = this.formatField.charAt(0);
            if (charAt == '\'') {
                charAt = this.formatField.charAt(1);
            }
            return Character.isDigit(charAt);
        }
    }

    private static class TextStrategy extends Strategy {
        private final int field;
        private final Map<String, Integer> keyValues;

        TextStrategy(int i, Calendar calendar, Locale locale) {
            super();
            this.field = i;
            this.keyValues = FastDateParser.getDisplayNames(i, calendar, locale);
        }

        boolean addRegex(FastDateParser fastDateParser, StringBuilder stringBuilder) {
            stringBuilder.append('(');
            for (String access$100 : this.keyValues.keySet()) {
                FastDateParser.escapeRegex(stringBuilder, access$100, false).append('|');
            }
            stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
            return true;
        }

        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            Integer num = (Integer) this.keyValues.get(str);
            if (num == null) {
                StringBuilder stringBuilder = new StringBuilder(str);
                stringBuilder.append(" not in (");
                for (String append : this.keyValues.keySet()) {
                    stringBuilder.append(append).append(' ');
                }
                stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            calendar.set(this.field, num.intValue());
        }
    }

    private static class TimeZoneStrategy extends Strategy {
        private static final int ID = 0;
        private static final int LONG_DST = 3;
        private static final int LONG_STD = 1;
        private static final int SHORT_DST = 4;
        private static final int SHORT_STD = 2;
        private final SortedMap<String, TimeZone> tzNames = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        private final String validTimeZoneChars;

        TimeZoneStrategy(Locale locale) {
            super();
            for (Object[] objArr : DateFormatSymbols.getInstance(locale).getZoneStrings()) {
                if (!objArr[0].startsWith("GMT")) {
                    TimeZone timeZone = TimeZone.getTimeZone(objArr[0]);
                    if (!this.tzNames.containsKey(objArr[1])) {
                        this.tzNames.put(objArr[1], timeZone);
                    }
                    if (!this.tzNames.containsKey(objArr[2])) {
                        this.tzNames.put(objArr[2], timeZone);
                    }
                    if (timeZone.useDaylightTime()) {
                        if (!this.tzNames.containsKey(objArr[3])) {
                            this.tzNames.put(objArr[3], timeZone);
                        }
                        if (!this.tzNames.containsKey(objArr[4])) {
                            this.tzNames.put(objArr[4], timeZone);
                        }
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(GMT[+\\-]\\d{0,1}\\d{2}|[+\\-]\\d{2}:?\\d{2}|");
            for (String access$100 : this.tzNames.keySet()) {
                FastDateParser.escapeRegex(stringBuilder, access$100, false).append('|');
            }
            stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
            this.validTimeZoneChars = stringBuilder.toString();
        }

        boolean addRegex(FastDateParser fastDateParser, StringBuilder stringBuilder) {
            stringBuilder.append(this.validTimeZoneChars);
            return true;
        }

        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            TimeZone timeZone;
            if (str.charAt(0) == '+' || str.charAt(0) == '-') {
                timeZone = TimeZone.getTimeZone("GMT" + str);
            } else if (str.startsWith("GMT")) {
                timeZone = TimeZone.getTimeZone(str);
            } else {
                timeZone = (TimeZone) this.tzNames.get(str);
                if (timeZone == null) {
                    throw new IllegalArgumentException(str + " is not a supported timezone name");
                }
            }
            calendar.setTimeZone(timeZone);
        }
    }

    protected FastDateParser(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    protected FastDateParser(String str, TimeZone timeZone, Locale locale, Date date) {
        int i;
        this.pattern = str;
        this.timeZone = timeZone;
        this.locale = locale;
        Calendar instance = Calendar.getInstance(timeZone, locale);
        if (date != null) {
            instance.setTime(date);
            i = instance.get(1);
        } else if (locale.equals(JAPANESE_IMPERIAL)) {
            i = 0;
        } else {
            instance.setTime(new Date());
            i = instance.get(1) - 80;
        }
        this.century = (i / 100) * 100;
        this.startYear = i - this.century;
        init(instance);
    }

    private int adjustYear(int i) {
        int i2 = this.century + i;
        return i >= this.startYear ? i2 : i2 + 100;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.StringBuilder escapeRegex(java.lang.StringBuilder r4, java.lang.String r5, boolean r6) {
        /*
        r0 = "\\Q";
        r4.append(r0);
        r0 = 0;
    L_0x0007:
        r1 = r5.length();
        if (r0 >= r1) goto L_0x004d;
    L_0x000d:
        r1 = r5.charAt(r0);
        switch(r1) {
            case 39: goto L_0x001d;
            case 92: goto L_0x002d;
            default: goto L_0x0014;
        };
    L_0x0014:
        r3 = r1;
        r1 = r0;
        r0 = r3;
    L_0x0017:
        r4.append(r0);
        r0 = r1 + 1;
        goto L_0x0007;
    L_0x001d:
        if (r6 == 0) goto L_0x0014;
    L_0x001f:
        r1 = r0 + 1;
        r0 = r5.length();
        if (r1 != r0) goto L_0x0028;
    L_0x0027:
        return r4;
    L_0x0028:
        r0 = r5.charAt(r1);
        goto L_0x0017;
    L_0x002d:
        r2 = r0 + 1;
        r0 = r5.length();
        if (r2 != r0) goto L_0x0038;
    L_0x0035:
        r0 = r1;
        r1 = r2;
        goto L_0x0017;
    L_0x0038:
        r4.append(r1);
        r0 = r5.charAt(r2);
        r1 = 69;
        if (r0 != r1) goto L_0x0054;
    L_0x0043:
        r0 = "E\\\\E\\";
        r4.append(r0);
        r0 = 81;
        r1 = r2;
        goto L_0x0017;
    L_0x004d:
        r0 = "\\E";
        r4.append(r0);
        goto L_0x0027;
    L_0x0054:
        r1 = r2;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.time.FastDateParser.escapeRegex(java.lang.StringBuilder, java.lang.String, boolean):java.lang.StringBuilder");
    }

    private static ConcurrentMap<Locale, Strategy> getCache(int i) {
        ConcurrentMap<Locale, Strategy> concurrentMap;
        synchronized (caches) {
            if (caches[i] == null) {
                caches[i] = new ConcurrentHashMap(3);
            }
            concurrentMap = caches[i];
        }
        return concurrentMap;
    }

    private static String[] getDisplayNameArray(int i, boolean z, Locale locale) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        switch (i) {
            case 0:
                return dateFormatSymbols.getEras();
            case 2:
                return z ? dateFormatSymbols.getMonths() : dateFormatSymbols.getShortMonths();
            case 7:
                return z ? dateFormatSymbols.getWeekdays() : dateFormatSymbols.getShortWeekdays();
            case 9:
                return dateFormatSymbols.getAmPmStrings();
            default:
                return null;
        }
    }

    private static Map<String, Integer> getDisplayNames(int i, Calendar calendar, Locale locale) {
        return getDisplayNames(i, locale);
    }

    private static Map<String, Integer> getDisplayNames(int i, Locale locale) {
        Map<String, Integer> hashMap = new HashMap();
        insertValuesInMap(hashMap, getDisplayNameArray(i, false, locale));
        insertValuesInMap(hashMap, getDisplayNameArray(i, true, locale));
        return hashMap.isEmpty() ? null : hashMap;
    }

    private Strategy getLocaleSpecificStrategy(int i, Calendar calendar) {
        Strategy timeZoneStrategy;
        ConcurrentMap cache = getCache(i);
        Strategy strategy = (Strategy) cache.get(this.locale);
        if (strategy == null) {
            timeZoneStrategy = i == 15 ? new TimeZoneStrategy(this.locale) : new TextStrategy(i, calendar, this.locale);
            strategy = (Strategy) cache.putIfAbsent(this.locale, timeZoneStrategy);
            if (strategy != null) {
                return strategy;
            }
        }
        timeZoneStrategy = strategy;
        return timeZoneStrategy;
    }

    private Strategy getStrategy(String str, Calendar calendar) {
        switch (str.charAt(0)) {
            case '\'':
                if (str.length() > 2) {
                    return new CopyQuotedStrategy(str.substring(1, str.length() - 1));
                }
                break;
            case 'D':
                return DAY_OF_YEAR_STRATEGY;
            case 'E':
                return getLocaleSpecificStrategy(7, calendar);
            case 'F':
                return DAY_OF_WEEK_IN_MONTH_STRATEGY;
            case 'G':
                return getLocaleSpecificStrategy(0, calendar);
            case 'H':
                return MODULO_HOUR_OF_DAY_STRATEGY;
            case 'K':
                return HOUR_STRATEGY;
            case 'M':
                return str.length() >= 3 ? getLocaleSpecificStrategy(2, calendar) : NUMBER_MONTH_STRATEGY;
            case 'S':
                return MILLISECOND_STRATEGY;
            case 'W':
                return WEEK_OF_MONTH_STRATEGY;
            case 'Z':
            case 'z':
                return getLocaleSpecificStrategy(15, calendar);
            case 'a':
                return getLocaleSpecificStrategy(9, calendar);
            case 'd':
                return DAY_OF_MONTH_STRATEGY;
            case 'h':
                return MODULO_HOUR_STRATEGY;
            case 'k':
                return HOUR_OF_DAY_STRATEGY;
            case 'm':
                return MINUTE_STRATEGY;
            case 's':
                return SECOND_STRATEGY;
            case 'w':
                return WEEK_OF_YEAR_STRATEGY;
            case 'y':
                return str.length() > 2 ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
        }
        return new CopyQuotedStrategy(str);
    }

    private void init(Calendar calendar) {
        StringBuilder stringBuilder = new StringBuilder();
        List arrayList = new ArrayList();
        Matcher matcher = formatPattern.matcher(this.pattern);
        if (matcher.lookingAt()) {
            this.currentFormatField = matcher.group();
            Strategy strategy = getStrategy(this.currentFormatField, calendar);
            while (true) {
                matcher.region(matcher.end(), matcher.regionEnd());
                if (!matcher.lookingAt()) {
                    break;
                }
                String group = matcher.group();
                this.nextStrategy = getStrategy(group, calendar);
                if (strategy.addRegex(this, stringBuilder)) {
                    arrayList.add(strategy);
                }
                this.currentFormatField = group;
                strategy = this.nextStrategy;
            }
            this.nextStrategy = null;
            if (matcher.regionStart() != matcher.regionEnd()) {
                throw new IllegalArgumentException("Failed to parse \"" + this.pattern + "\" ; gave up at index " + matcher.regionStart());
            }
            if (strategy.addRegex(this, stringBuilder)) {
                arrayList.add(strategy);
            }
            this.currentFormatField = null;
            this.strategies = (Strategy[]) arrayList.toArray(new Strategy[arrayList.size()]);
            this.parsePattern = Pattern.compile(stringBuilder.toString());
            return;
        }
        throw new IllegalArgumentException("Illegal pattern character '" + this.pattern.charAt(matcher.regionStart()) + "'");
    }

    private static void insertValuesInMap(Map<String, Integer> map, String[] strArr) {
        if (strArr != null) {
            int i = 0;
            while (i < strArr.length) {
                if (strArr[i] != null && strArr[i].length() > 0) {
                    map.put(strArr[i], Integer.valueOf(i));
                }
                i++;
            }
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        init(Calendar.getInstance(this.timeZone, this.locale));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateParser)) {
            return false;
        }
        FastDateParser fastDateParser = (FastDateParser) obj;
        return this.pattern.equals(fastDateParser.pattern) && this.timeZone.equals(fastDateParser.timeZone) && this.locale.equals(fastDateParser.locale);
    }

    int getFieldWidth() {
        return this.currentFormatField.length();
    }

    public Locale getLocale() {
        return this.locale;
    }

    Pattern getParsePattern() {
        return this.parsePattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public int hashCode() {
        return this.pattern.hashCode() + ((this.timeZone.hashCode() + (this.locale.hashCode() * 13)) * 13);
    }

    boolean isNextNumber() {
        return this.nextStrategy != null && this.nextStrategy.isNumber();
    }

    public Date parse(String str) {
        Date parse = parse(str, new ParsePosition(0));
        if (parse != null) {
            return parse;
        }
        if (this.locale.equals(JAPANESE_IMPERIAL)) {
            throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\nUnparseable date: \"" + str + "\" does not match " + this.parsePattern.pattern(), 0);
        }
        throw new ParseException("Unparseable date: \"" + str + "\" does not match " + this.parsePattern.pattern(), 0);
    }

    public Date parse(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        Matcher matcher = this.parsePattern.matcher(str.substring(index));
        if (!matcher.lookingAt()) {
            return null;
        }
        Calendar instance = Calendar.getInstance(this.timeZone, this.locale);
        instance.clear();
        int i = 0;
        while (i < this.strategies.length) {
            int i2 = i + 1;
            this.strategies[i].setCalendar(this, instance, matcher.group(i2));
            i = i2;
        }
        parsePosition.setIndex(matcher.end() + index);
        return instance.getTime();
    }

    public Object parseObject(String str) {
        return parse(str);
    }

    public Object parseObject(String str, ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }

    public String toString() {
        return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
    }
}
