package com.google.android.gms.internal.clearcut;

public abstract class zzbk {
    private static volatile boolean zzft;
    private int zzfq;
    private int zzfr;
    private boolean zzfs;

    static {
        zzft = false;
        zzft = true;
    }

    private zzbk() {
        this.zzfq = 100;
        this.zzfr = Integer.MAX_VALUE;
        this.zzfs = false;
    }

    public static long zza(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    static zzbk zza(byte[] bArr, int i, int i2, boolean z) {
        zzbk zzbm = new zzbm(bArr, 0, i2, false, null);
        try {
            zzbm.zzl(i2);
            return zzbm;
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzm(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public abstract int zzaf();

    public abstract int zzl(int i);
}
