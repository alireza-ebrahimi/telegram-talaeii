package com.p111h.p112a.p117e;

import com.p111h.p112a.p116d.C1991a;
import java.util.Calendar;
import java.util.Locale;

/* renamed from: com.h.a.e.a */
public class C1992a {
    /* renamed from: a */
    public static boolean m9007a(int i) {
        return C1992a.m9009b(i) < C1991a.m9005b().get(1);
    }

    /* renamed from: a */
    public static boolean m9008a(int i, int i2) {
        if (C1992a.m9007a(i)) {
            return true;
        }
        Calendar b = C1991a.m9005b();
        return C1992a.m9009b(i) == b.get(1) && i2 < b.get(2) + 1;
    }

    /* renamed from: b */
    private static int m9009b(int i) {
        if (i >= 100 || i < 0) {
            return i;
        }
        String valueOf = String.valueOf(C1991a.m9005b().get(1));
        valueOf = valueOf.substring(0, valueOf.length() - 2);
        return Integer.parseInt(String.format(Locale.US, "%s%02d", new Object[]{valueOf, Integer.valueOf(i)}));
    }
}
