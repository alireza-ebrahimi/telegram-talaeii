package com.mohamadamin.persianmaterialdatetimepicker.p121a;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.a.d */
public class C2020d {
    /* renamed from: a */
    public static long m9102a(double d, double d2) {
        return (long) (d - (Math.floor(d / d2) * d2));
    }

    /* renamed from: a */
    public static long m9103a(long j) {
        long a = j - C2020d.m9104a(475, 0, 1);
        long a2 = C2020d.m9102a((double) a, 1029983.0d);
        a = ((((long) Math.floor(((double) a) / 1029983.0d)) * 2820) + 474) + (a2 != 1029982 ? (long) Math.floor(((((double) a2) * 2816.0d) + 1031337.0d) / 1028522.0d) : 2820);
        a2 = (1 + j) - C2020d.m9104a(a, 0, 1);
        int ceil = (int) (a2 > 186 ? Math.ceil(((double) (a2 - 6)) / 30.0d) - 1.0d : Math.ceil(((double) a2) / 31.0d) - 1.0d);
        return ((long) ((int) (j - (C2020d.m9104a(a, ceil, 1) - 1)))) | ((a << 16) | ((long) (ceil << 8)));
    }

    /* renamed from: a */
    public static long m9104a(long j, int i, int i2) {
        return (((long) (i < 7 ? i * 31 : (i * 30) + 6)) + ((1029983 * ((long) Math.floor(((double) (j - 474)) / 2820.0d))) + (((365 * ((C2020d.m9102a((double) (j - 474), 2820.0d) + 474) - 1)) + ((long) Math.floor(((double) ((682 * (C2020d.m9102a((double) (j - 474), 2820.0d) + 474)) - 110)) / 2816.0d))) + 1948320))) + ((long) i2);
    }

    /* renamed from: a */
    public static boolean m9105a(int i) {
        return C2020d.m9102a((38.0d + ((double) (C2020d.m9102a((double) (((long) i) - 474), 2820.0d) + 474))) * 682.0d, 2816.0d) < 682;
    }
}
