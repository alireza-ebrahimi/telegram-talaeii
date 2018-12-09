package org.telegram.messenger.time;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class FormatCache<F extends Format> {
    static final int NONE = -1;
    private static final ConcurrentMap<MultipartKey, String> cDateTimeInstanceCache = new ConcurrentHashMap(7);
    private final ConcurrentMap<MultipartKey, F> cInstanceCache = new ConcurrentHashMap(7);

    private static class MultipartKey {
        private int hashCode;
        private final Object[] keys;

        public MultipartKey(Object... objArr) {
            this.keys = objArr;
        }

        public boolean equals(Object obj) {
            return Arrays.equals(this.keys, ((MultipartKey) obj).keys);
        }

        public int hashCode() {
            int i = 0;
            if (this.hashCode == 0) {
                for (Object obj : this.keys) {
                    if (obj != null) {
                        i = (i * 7) + obj.hashCode();
                    }
                }
                this.hashCode = i;
            }
            return this.hashCode;
        }
    }

    FormatCache() {
    }

    private F getDateTimeInstance(Integer num, Integer num2, TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return getInstance(getPatternForStyle(num, num2, locale), timeZone, locale);
    }

    static String getPatternForStyle(Integer num, Integer num2, Locale locale) {
        MultipartKey multipartKey = new MultipartKey(num, num2, locale);
        String str = (String) cDateTimeInstanceCache.get(multipartKey);
        if (str != null) {
            return str;
        }
        DateFormat timeInstance;
        if (num == null) {
            try {
                timeInstance = DateFormat.getTimeInstance(num2.intValue(), locale);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("No date time pattern for locale: " + locale);
            }
        }
        timeInstance = num2 == null ? DateFormat.getDateInstance(num.intValue(), locale) : DateFormat.getDateTimeInstance(num.intValue(), num2.intValue(), locale);
        String toPattern = ((SimpleDateFormat) timeInstance).toPattern();
        str = (String) cDateTimeInstanceCache.putIfAbsent(multipartKey, toPattern);
        return str != null ? str : toPattern;
    }

    protected abstract F createInstance(String str, TimeZone timeZone, Locale locale);

    F getDateInstance(int i, TimeZone timeZone, Locale locale) {
        return getDateTimeInstance(Integer.valueOf(i), null, timeZone, locale);
    }

    F getDateTimeInstance(int i, int i2, TimeZone timeZone, Locale locale) {
        return getDateTimeInstance(Integer.valueOf(i), Integer.valueOf(i2), timeZone, locale);
    }

    public F getInstance() {
        return getDateTimeInstance(3, 3, TimeZone.getDefault(), Locale.getDefault());
    }

    public F getInstance(String str, TimeZone timeZone, Locale locale) {
        if (str == null) {
            throw new NullPointerException("pattern must not be null");
        }
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        MultipartKey multipartKey = new MultipartKey(str, timeZone, locale);
        Format format = (Format) this.cInstanceCache.get(multipartKey);
        if (format != null) {
            return format;
        }
        F createInstance = createInstance(str, timeZone, locale);
        format = (Format) this.cInstanceCache.putIfAbsent(multipartKey, createInstance);
        return format != null ? format : createInstance;
    }

    F getTimeInstance(int i, TimeZone timeZone, Locale locale) {
        return getDateTimeInstance(null, Integer.valueOf(i), timeZone, locale);
    }
}
