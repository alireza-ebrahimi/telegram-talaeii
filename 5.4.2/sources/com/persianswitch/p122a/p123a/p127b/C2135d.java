package com.persianswitch.p122a.p123a.p127b;

/* renamed from: com.persianswitch.a.a.b.d */
public final class C2135d {
    /* renamed from: a */
    public static int m9641a(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt != ' ' && charAt != '\t') {
                break;
            }
            i++;
        }
        return i;
    }

    /* renamed from: a */
    public static int m9642a(String str, int i, String str2) {
        while (i < str.length() && str2.indexOf(str.charAt(i)) == -1) {
            i++;
        }
        return i;
    }

    /* renamed from: b */
    public static int m9643b(String str, int i) {
        try {
            long parseLong = Long.parseLong(str);
            return parseLong > 2147483647L ? Integer.MAX_VALUE : parseLong < 0 ? 0 : (int) parseLong;
        } catch (NumberFormatException e) {
            return i;
        }
    }
}
