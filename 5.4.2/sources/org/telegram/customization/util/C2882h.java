package org.telegram.customization.util;

/* renamed from: org.telegram.customization.util.h */
public class C2882h {
    /* renamed from: a */
    public static String m13368a(String str) {
        if (str == null || str.length() < 7) {
            return str;
        }
        if (str.startsWith("00") && str.length() > 2) {
            str = str.substring(2);
        }
        if (str.startsWith("+") && str.length() > 1) {
            str = str.substring(1);
        }
        if (str.startsWith("98") && str.length() > 2) {
            str = str.substring(2);
        }
        return !str.startsWith("0") ? "0" + str : str;
    }
}
