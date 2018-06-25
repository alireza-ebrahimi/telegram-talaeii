package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Decade;
import org.ocpsoft.prettytime.units.Millennium;

public class Resources_ja extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS;
    private volatile ConcurrentMap<TimeUnit, TimeFormat> formatMap = new ConcurrentHashMap();

    private static class JaTimeFormat implements TimeFormat {
        private static final String NEGATIVE = "-";
        public static final String QUANTITY = "%n";
        public static final String SIGN = "%s";
        public static final String UNIT = "%u";
        private final ResourceBundle bundle;
        private String futurePluralName = "";
        private String futurePrefix = "";
        private String futureSingularName = "";
        private String futureSuffix = "";
        private String pastPluralName = "";
        private String pastPrefix = "";
        private String pastSingularName = "";
        private String pastSuffix = "";
        private String pattern = "";
        private String pluralName = "";
        private int roundingTolerance = 50;
        private String singularName = "";

        public JaTimeFormat(ResourceBundle bundle, TimeUnit unit) {
            this.bundle = bundle;
            setPattern(bundle.getString(getUnitName(unit) + "Pattern"));
            setFuturePrefix(bundle.getString(getUnitName(unit) + "FuturePrefix"));
            setFutureSuffix(bundle.getString(getUnitName(unit) + "FutureSuffix"));
            setPastPrefix(bundle.getString(getUnitName(unit) + "PastPrefix"));
            setPastSuffix(bundle.getString(getUnitName(unit) + "PastSuffix"));
            setSingularName(bundle.getString(getUnitName(unit) + "SingularName"));
            setPluralName(bundle.getString(getUnitName(unit) + "PluralName"));
            try {
                setFuturePluralName(bundle.getString(getUnitName(unit) + "FuturePluralName"));
            } catch (Exception e) {
            }
            try {
                setFutureSingularName(bundle.getString(getUnitName(unit) + "FutureSingularName"));
            } catch (Exception e2) {
            }
            try {
                setPastPluralName(bundle.getString(getUnitName(unit) + "PastPluralName"));
            } catch (Exception e3) {
            }
            try {
                setPastSingularName(bundle.getString(getUnitName(unit) + "PastSingularName"));
            } catch (Exception e4) {
            }
        }

        private String getUnitName(TimeUnit unit) {
            return unit.getClass().getSimpleName();
        }

        public String format(Duration duration) {
            return format(duration, true);
        }

        public String formatUnrounded(Duration duration) {
            return format(duration, false);
        }

        private String format(Duration duration, boolean round) {
            String sign = getSign(duration);
            String unit = getGramaticallyCorrectName(duration, round);
            long quantity = getQuantity(duration, round);
            if (duration.getUnit() instanceof Decade) {
                quantity *= 10;
            }
            if (duration.getUnit() instanceof Millennium) {
                quantity *= 1000;
            }
            return applyPattern(sign, unit, quantity);
        }

        private String applyPattern(String sign, String unit, long quantity) {
            return getPattern(quantity).replaceAll("%s", sign).replaceAll("%n", String.valueOf(quantity)).replaceAll("%u", unit);
        }

        protected String getPattern(long quantity) {
            return this.pattern;
        }

        public String getPattern() {
            return this.pattern;
        }

        protected long getQuantity(Duration duration, boolean round) {
            return Math.abs(round ? duration.getQuantityRounded(this.roundingTolerance) : duration.getQuantity());
        }

        protected String getGramaticallyCorrectName(Duration d, boolean round) {
            String result = getSingularName(d);
            if (Math.abs(getQuantity(d, round)) == 0 || Math.abs(getQuantity(d, round)) > 1) {
                return getPluralName(d);
            }
            return result;
        }

        private String getSign(Duration d) {
            if (d.getQuantity() < 0) {
                return NEGATIVE;
            }
            return "";
        }

        private String getSingularName(Duration duration) {
            if (duration.isInFuture() && this.futureSingularName != null && this.futureSingularName.length() > 0) {
                return this.futureSingularName;
            }
            if (!duration.isInPast() || this.pastSingularName == null || this.pastSingularName.length() <= 0) {
                return this.singularName;
            }
            return this.pastSingularName;
        }

        private String getPluralName(Duration duration) {
            if (duration.isInFuture() && this.futurePluralName != null && this.futureSingularName.length() > 0) {
                return this.futurePluralName;
            }
            if (!duration.isInPast() || this.pastPluralName == null || this.pastSingularName.length() <= 0) {
                return this.pluralName;
            }
            return this.pastPluralName;
        }

        public String decorate(Duration duration, String time) {
            StringBuilder result = new StringBuilder();
            if (duration.isInPast()) {
                result.append(this.pastPrefix).append(time).append(this.pastSuffix);
            } else {
                result.append(this.futurePrefix).append(time).append(this.futureSuffix);
            }
            return result.toString().replaceAll("\\s+", " ").trim();
        }

        public String decorateUnrounded(Duration duration, String time) {
            return decorate(duration, time);
        }

        public JaTimeFormat setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public JaTimeFormat setFuturePrefix(String futurePrefix) {
            this.futurePrefix = futurePrefix.trim();
            return this;
        }

        public JaTimeFormat setFutureSuffix(String futureSuffix) {
            this.futureSuffix = futureSuffix.trim();
            return this;
        }

        public JaTimeFormat setPastPrefix(String pastPrefix) {
            this.pastPrefix = pastPrefix.trim();
            return this;
        }

        public JaTimeFormat setPastSuffix(String pastSuffix) {
            this.pastSuffix = pastSuffix.trim();
            return this;
        }

        public JaTimeFormat setRoundingTolerance(int roundingTolerance) {
            this.roundingTolerance = roundingTolerance;
            return this;
        }

        public JaTimeFormat setSingularName(String name) {
            this.singularName = name;
            return this;
        }

        public JaTimeFormat setPluralName(String pluralName) {
            this.pluralName = pluralName;
            return this;
        }

        public JaTimeFormat setFutureSingularName(String futureSingularName) {
            this.futureSingularName = futureSingularName;
            return this;
        }

        public JaTimeFormat setFuturePluralName(String futurePluralName) {
            this.futurePluralName = futurePluralName;
            return this;
        }

        public JaTimeFormat setPastSingularName(String pastSingularName) {
            this.pastSingularName = pastSingularName;
            return this;
        }

        public JaTimeFormat setPastPluralName(String pastPluralName) {
            this.pastPluralName = pastPluralName;
            return this;
        }

        public String toString() {
            return "JaTimeFormat [pattern=" + this.pattern + ", futurePrefix=" + this.futurePrefix + ", futureSuffix=" + this.futureSuffix + ", pastPrefix=" + this.pastPrefix + ", pastSuffix=" + this.pastSuffix + ", roundingTolerance=" + this.roundingTolerance + "]";
        }
    }

    static {
        r0 = new Object[91][];
        r0[0] = new Object[]{"CenturyPattern", "%n%u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "今から"};
        r0[2] = new Object[]{"CenturyFutureSuffix", "後"};
        r0[3] = new Object[]{"CenturyPastPrefix", ""};
        r0[4] = new Object[]{"CenturyPastSuffix", "前"};
        r0[5] = new Object[]{"CenturySingularName", "世紀"};
        r0[6] = new Object[]{"CenturyPluralName", "世紀"};
        r0[7] = new Object[]{"DayPattern", "%n%u"};
        r0[8] = new Object[]{"DayFuturePrefix", "今から"};
        r0[9] = new Object[]{"DayFutureSuffix", "後"};
        r0[10] = new Object[]{"DayPastPrefix", ""};
        r0[11] = new Object[]{"DayPastSuffix", "前"};
        r0[12] = new Object[]{"DaySingularName", "日"};
        r0[13] = new Object[]{"DayPluralName", "日"};
        r0[14] = new Object[]{"DecadePattern", "%n%u"};
        r0[15] = new Object[]{"DecadeFuturePrefix", "今から"};
        r0[16] = new Object[]{"DecadeFutureSuffix", "後"};
        r0[17] = new Object[]{"DecadePastPrefix", ""};
        r0[18] = new Object[]{"DecadePastSuffix", "前"};
        r0[19] = new Object[]{"DecadeSingularName", "年"};
        r0[20] = new Object[]{"DecadePluralName", "年"};
        r0[21] = new Object[]{"HourPattern", "%n%u"};
        r0[22] = new Object[]{"HourFuturePrefix", "今から"};
        r0[23] = new Object[]{"HourFutureSuffix", "後"};
        r0[24] = new Object[]{"HourPastPrefix", ""};
        r0[25] = new Object[]{"HourPastSuffix", "前"};
        r0[26] = new Object[]{"HourSingularName", "時間"};
        r0[27] = new Object[]{"HourPluralName", "時間"};
        r0[28] = new Object[]{"JustNowPattern", "%u"};
        r0[29] = new Object[]{"JustNowFuturePrefix", "今から"};
        r0[30] = new Object[]{"JustNowFutureSuffix", "すぐ"};
        r0[31] = new Object[]{"JustNowPastPrefix", ""};
        r0[32] = new Object[]{"JustNowPastSuffix", "たった今"};
        r0[33] = new Object[]{"JustNowSingularName", ""};
        r0[34] = new Object[]{"JustNowPluralName", ""};
        r0[35] = new Object[]{"MillenniumPattern", "%n%u"};
        r0[36] = new Object[]{"MillenniumFuturePrefix", "今から"};
        r0[37] = new Object[]{"MillenniumFutureSuffix", "後"};
        r0[38] = new Object[]{"MillenniumPastPrefix", ""};
        r0[39] = new Object[]{"MillenniumPastSuffix", "前"};
        r0[40] = new Object[]{"MillenniumSingularName", "年"};
        r0[41] = new Object[]{"MillenniumPluralName", "年"};
        r0[42] = new Object[]{"MillisecondPattern", "%n%u"};
        r0[43] = new Object[]{"MillisecondFuturePrefix", "今から"};
        r0[44] = new Object[]{"MillisecondFutureSuffix", "後"};
        r0[45] = new Object[]{"MillisecondPastPrefix", ""};
        r0[46] = new Object[]{"MillisecondPastSuffix", "前"};
        r0[47] = new Object[]{"MillisecondSingularName", "ミリ秒"};
        r0[48] = new Object[]{"MillisecondPluralName", "ミリ秒"};
        r0[49] = new Object[]{"MinutePattern", "%n%u"};
        r0[50] = new Object[]{"MinuteFuturePrefix", "今から"};
        r0[51] = new Object[]{"MinuteFutureSuffix", "後"};
        r0[52] = new Object[]{"MinutePastPrefix", ""};
        r0[53] = new Object[]{"MinutePastSuffix", "前"};
        r0[54] = new Object[]{"MinuteSingularName", "分"};
        r0[55] = new Object[]{"MinutePluralName", "分"};
        r0[56] = new Object[]{"MonthPattern", "%n%u"};
        r0[57] = new Object[]{"MonthFuturePrefix", "今から"};
        r0[58] = new Object[]{"MonthFutureSuffix", "後"};
        r0[59] = new Object[]{"MonthPastPrefix", ""};
        r0[60] = new Object[]{"MonthPastSuffix", "前"};
        r0[61] = new Object[]{"MonthSingularName", "ヶ月"};
        r0[62] = new Object[]{"MonthPluralName", "ヶ月"};
        r0[63] = new Object[]{"SecondPattern", "%n%u"};
        r0[64] = new Object[]{"SecondFuturePrefix", "今から"};
        r0[65] = new Object[]{"SecondFutureSuffix", "後"};
        r0[66] = new Object[]{"SecondPastPrefix", ""};
        r0[67] = new Object[]{"SecondPastSuffix", "前"};
        r0[68] = new Object[]{"SecondSingularName", "秒"};
        r0[69] = new Object[]{"SecondPluralName", "秒"};
        r0[70] = new Object[]{"WeekPattern", "%n%u"};
        r0[71] = new Object[]{"WeekFuturePrefix", "今から"};
        r0[72] = new Object[]{"WeekFutureSuffix", "後"};
        r0[73] = new Object[]{"WeekPastPrefix", ""};
        r0[74] = new Object[]{"WeekPastSuffix", "前"};
        r0[75] = new Object[]{"WeekSingularName", "週間"};
        r0[76] = new Object[]{"WeekPluralName", "週間"};
        r0[77] = new Object[]{"YearPattern", "%n%u"};
        r0[78] = new Object[]{"YearFuturePrefix", "今から"};
        r0[79] = new Object[]{"YearFutureSuffix", "後"};
        r0[80] = new Object[]{"YearPastPrefix", ""};
        r0[81] = new Object[]{"YearPastSuffix", "前"};
        r0[82] = new Object[]{"YearSingularName", "年"};
        r0[83] = new Object[]{"YearPluralName", "年"};
        r0[84] = new Object[]{"AbstractTimeUnitPattern", ""};
        r0[85] = new Object[]{"AbstractTimeUnitFuturePrefix", ""};
        r0[86] = new Object[]{"AbstractTimeUnitFutureSuffix", ""};
        r0[87] = new Object[]{"AbstractTimeUnitPastPrefix", ""};
        r0[88] = new Object[]{"AbstractTimeUnitPastSuffix", ""};
        r0[89] = new Object[]{"AbstractTimeUnitSingularName", ""};
        r0[90] = new Object[]{"AbstractTimeUnitPluralName", ""};
        OBJECTS = r0;
    }

    public Object[][] getContents() {
        return OBJECTS;
    }

    public TimeFormat getFormatFor(TimeUnit t) {
        if (!this.formatMap.containsKey(t)) {
            this.formatMap.putIfAbsent(t, new JaTimeFormat(this, t));
        }
        return (TimeFormat) this.formatMap.get(t);
    }
}
