package utils.p180b;

/* renamed from: utils.b.c */
public class C5318c {
    /* renamed from: a */
    public static long m14131a(double d, double d2) {
        return (long) (d - (Math.floor(d / d2) * d2));
    }

    /* renamed from: a */
    public static long m14132a(long j) {
        long a = j - C5318c.m14133a(475, 0, 1);
        long a2 = C5318c.m14131a((double) a, 1029983.0d);
        a = ((((long) Math.floor(((double) a) / 1029983.0d)) * 2820) + 474) + (a2 != 1029982 ? (long) Math.floor(((((double) a2) * 2816.0d) + 1031337.0d) / 1028522.0d) : 2820);
        a2 = (1 + j) - C5318c.m14133a(a, 0, 1);
        int ceil = (int) (a2 > 186 ? Math.ceil(((double) (a2 - 6)) / 30.0d) - 1.0d : Math.ceil(((double) a2) / 31.0d) - 1.0d);
        return ((long) ((int) (j - (C5318c.m14133a(a, ceil, 1) - 1)))) | ((a << 16) | ((long) (ceil << 8)));
    }

    /* renamed from: a */
    public static long m14133a(long j, int i, int i2) {
        return (((long) (i < 7 ? i * 31 : (i * 30) + 6)) + ((1029983 * ((long) Math.floor(((double) (j - 474)) / 2820.0d))) + (((365 * ((C5318c.m14131a((double) (j - 474), 2820.0d) + 474) - 1)) + ((long) Math.floor(((double) ((682 * (C5318c.m14131a((double) (j - 474), 2820.0d) + 474)) - 110)) / 2816.0d))) + 1948320))) + ((long) i2);
    }
}
