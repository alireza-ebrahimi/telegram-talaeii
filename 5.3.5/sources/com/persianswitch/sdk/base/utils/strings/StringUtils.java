package com.persianswitch.sdk.base.utils.strings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class StringUtils {
    public static boolean isEmpty(@Nullable String text) {
        return text == null || text.length() == 0;
    }

    @NonNull
    public static String toNonNullString(@Nullable Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    @NonNull
    public static String trim(@Nullable String text) {
        return toNonNullString(text).trim();
    }

    public static <T> T split(String separator, String flat, StringParser<T> parser) {
        return parser.parseString(flat, separator);
    }

    @NonNull
    public static String join(String separator, Object... objects) {
        if (objects == null) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder sb = new StringBuilder(objects.length * (separator.length() + 5));
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                sb.append(objects[i].toString());
            }
            if (i != objects.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String trimJoin(@Nullable String separator, @Nullable Object... objects) {
        return trimJoin(separator, true, objects);
    }

    @NonNull
    public static String trimJoin(@Nullable String separator, boolean ignoreEmptyItems, @Nullable Object... objects) {
        if (objects == null) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder sb = new StringBuilder(objects.length * (separator.length() + 5));
        int i = 0;
        while (i < objects.length) {
            if (!((ignoreEmptyItems && (objects[i] == null || isEmpty(objects[i].toString()))) || objects[i] == null)) {
                sb.append(trim(objects[i].toString()));
                if (i != objects.length - 1) {
                    sb.append(separator);
                }
            }
            i++;
        }
        return sb.toString().trim();
    }

    @Nullable
    public static Boolean toBoolean(@Nullable String optBoolean) {
        try {
            return Boolean.valueOf(Boolean.parseBoolean(optBoolean));
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static Float toFloat(@Nullable String optFloat) {
        try {
            return Float.valueOf(Float.parseFloat(optFloat));
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static Long toLong(@Nullable String optNumber) {
        try {
            return Long.valueOf(Long.parseLong(optNumber));
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static Integer toInteger(@Nullable String optNumber) {
        try {
            return Integer.valueOf(Integer.parseInt(optNumber));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean between(String hostCardNo, int min, int max) {
        return !isEmpty(hostCardNo) && hostCardNo.length() >= min && hostCardNo.length() <= max;
    }

    public static boolean isEquals(@Nullable String object, @Nullable String compareTo) {
        if (object == null && compareTo == null) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (compareTo == null || !object.equals(compareTo)) {
            return false;
        }
        return true;
    }

    public static boolean isEqualsAny(@Nullable String object, @Nullable String... compareList) {
        if (object == null && compareList == null) {
            return true;
        }
        if (object == null || compareList == null) {
            return false;
        }
        for (String s : compareList) {
            if (isEquals(object, s)) {
                return true;
            }
        }
        return false;
    }

    public static String keepNumbersOnly(CharSequence numberString) {
        if (numberString == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(numberString.length());
        for (int i = 0; i < numberString.length(); i++) {
            if (Character.isDigit(numberString.charAt(i))) {
                sb.append(numberString.charAt(i));
            }
        }
        return sb.toString();
    }
}
