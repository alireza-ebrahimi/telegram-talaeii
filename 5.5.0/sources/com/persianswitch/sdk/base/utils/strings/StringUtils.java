package com.persianswitch.sdk.base.utils.strings;

public class StringUtils {
    /* renamed from: a */
    public static String m10799a(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        for (int i = 0; i < charSequence.length(); i++) {
            if (Character.isDigit(charSequence.charAt(i))) {
                stringBuilder.append(charSequence.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public static String m10800a(Object obj) {
        return obj == null ? TtmlNode.ANONYMOUS_REGION_ID : obj.toString();
    }

    /* renamed from: a */
    public static String m10801a(String str, boolean z, Object... objArr) {
        if (objArr == null) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        if (str == null) {
            str = TtmlNode.ANONYMOUS_REGION_ID;
        }
        StringBuilder stringBuilder = new StringBuilder(objArr.length * (str.length() + 5));
        int i = 0;
        while (i < objArr.length) {
            if (!((z && (objArr[i] == null || m10803a(objArr[i].toString()))) || objArr[i] == null)) {
                stringBuilder.append(m10806b(objArr[i].toString()));
                if (i != objArr.length - 1) {
                    stringBuilder.append(str);
                }
            }
            i++;
        }
        return stringBuilder.toString().trim();
    }

    /* renamed from: a */
    public static String m10802a(String str, Object... objArr) {
        return m10801a(str, true, objArr);
    }

    /* renamed from: a */
    public static boolean m10803a(String str) {
        return str == null || str.length() == 0;
    }

    /* renamed from: a */
    public static boolean m10804a(String str, int i, int i2) {
        return !m10803a(str) && str.length() >= i && str.length() <= i2;
    }

    /* renamed from: a */
    public static boolean m10805a(String str, String str2) {
        return (str == null && str2 == null) ? true : str != null ? str2 != null && str.equals(str2) : false;
    }

    /* renamed from: b */
    public static String m10806b(String str) {
        return m10800a((Object) str).trim();
    }

    /* renamed from: c */
    public static Boolean m10807c(String str) {
        try {
            return Boolean.valueOf(Boolean.parseBoolean(str));
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: d */
    public static Long m10808d(String str) {
        try {
            return Long.valueOf(Long.parseLong(str));
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: e */
    public static Integer m10809e(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (Exception e) {
            return null;
        }
    }
}
