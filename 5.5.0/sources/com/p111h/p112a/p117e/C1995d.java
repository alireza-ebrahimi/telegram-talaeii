package com.p111h.p112a.p117e;

/* renamed from: com.h.a.e.d */
public class C1995d {
    /* renamed from: a */
    public static boolean m9014a(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    public static boolean m9015a(String str, String... strArr) {
        if (str == null) {
            return false;
        }
        for (String startsWith : strArr) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public static String m9016b(String str) {
        return C1995d.m9017c(str) ? null : str;
    }

    /* renamed from: c */
    public static boolean m9017c(String str) {
        return str == null || str.trim().length() == 0;
    }

    /* renamed from: d */
    public static String m9018d(String str) {
        return C1995d.m9017c(str) ? null : "American Express".equalsIgnoreCase(str) ? "American Express" : "MasterCard".equalsIgnoreCase(str) ? "MasterCard" : "Diners Club".equalsIgnoreCase(str) ? "Diners Club" : "Discover".equalsIgnoreCase(str) ? "Discover" : "JCB".equalsIgnoreCase(str) ? "JCB" : "Visa".equalsIgnoreCase(str) ? "Visa" : "Unknown";
    }

    /* renamed from: e */
    public static String m9019e(String str) {
        return C1995d.m9017c(str) ? null : "credit".equalsIgnoreCase(str) ? "credit" : "debit".equalsIgnoreCase(str) ? "debit" : "prepaid".equalsIgnoreCase(str) ? "prepaid" : "unknown";
    }

    /* renamed from: f */
    public static String m9020f(String str) {
        return "card".equals(str) ? "card" : null;
    }
}
