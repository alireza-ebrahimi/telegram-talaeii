package org.apache.p144a.p145a;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/* renamed from: org.apache.a.a.b */
public class C2450b {
    /* renamed from: a */
    private static final Pattern f8190a = Pattern.compile("\\s+");
    /* renamed from: b */
    private static boolean f8191b;
    /* renamed from: c */
    private static Method f8192c;
    /* renamed from: d */
    private static final Pattern f8193d = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    /* renamed from: e */
    private static boolean f8194e;
    /* renamed from: f */
    private static Method f8195f;
    /* renamed from: g */
    private static Object f8196g;
    /* renamed from: h */
    private static final Pattern f8197h = f8193d;

    static {
        f8191b = false;
        f8192c = null;
        f8194e = false;
        f8195f = null;
        f8196g = null;
        try {
            f8196g = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer$Form").getField("NFD").get(null);
            f8195f = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer").getMethod("normalize", new Class[]{CharSequence.class, r0});
            f8194e = true;
        } catch (ClassNotFoundException e) {
            f8194e = false;
        } catch (NoSuchFieldException e2) {
            f8194e = false;
        } catch (IllegalAccessException e3) {
            f8194e = false;
        } catch (NoSuchMethodException e4) {
            f8194e = false;
        }
        try {
            f8192c = Thread.currentThread().getContextClassLoader().loadClass("sun.text.Normalizer").getMethod("decompose", new Class[]{String.class, Boolean.TYPE, Integer.TYPE});
            f8191b = true;
        } catch (ClassNotFoundException e5) {
            f8191b = false;
        } catch (NoSuchMethodException e6) {
            f8191b = false;
        }
    }

    /* renamed from: a */
    public static int m11993a(CharSequence charSequence, CharSequence charSequence2) {
        int i = 0;
        if (C2450b.m11998a(charSequence) || C2450b.m11998a(charSequence2)) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            i = C2449a.m11992a(charSequence, charSequence2, i);
            if (i == -1) {
                return i2;
            }
            i2++;
            i += charSequence2.length();
        }
    }

    /* renamed from: a */
    public static String m11994a(String str) {
        return str == null ? null : new StringBuilder(str).reverse().toString();
    }

    /* renamed from: a */
    public static String m11995a(String str, String str2) {
        return (C2450b.m11998a((CharSequence) str) || C2450b.m11998a((CharSequence) str2) || !str.startsWith(str2)) ? str : str.substring(str2.length());
    }

    /* renamed from: a */
    public static String m11996a(String str, String str2, String str3) {
        return C2450b.m11997a(str, str2, str3, -1);
    }

    /* renamed from: a */
    public static String m11997a(String str, String str2, String str3, int i) {
        int i2 = 64;
        if (C2450b.m11998a((CharSequence) str) || C2450b.m11998a((CharSequence) str2) || str3 == null || i == 0) {
            return str;
        }
        int indexOf = str.indexOf(str2, 0);
        if (indexOf == -1) {
            return str;
        }
        int length = str2.length();
        int length2 = str3.length() - length;
        if (length2 < 0) {
            length2 = 0;
        }
        if (i < 0) {
            i2 = 16;
        } else if (i <= 64) {
            i2 = i;
        }
        StringBuilder stringBuilder = new StringBuilder((i2 * length2) + str.length());
        i2 = 0;
        while (indexOf != -1) {
            stringBuilder.append(str.substring(i2, indexOf)).append(str3);
            i2 = indexOf + length;
            i--;
            if (i == 0) {
                break;
            }
            indexOf = str.indexOf(str2, i2);
        }
        stringBuilder.append(str.substring(i2));
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public static boolean m11998a(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }
}
