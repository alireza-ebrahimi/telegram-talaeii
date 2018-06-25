package com.google.android.gms.internal.measurement;

public abstract class zzzg {
    private static volatile boolean zzbrq = false;
    int zzbrn;
    private int zzbro;
    private boolean zzbrp;

    private zzzg() {
        this.zzbrn = 100;
        this.zzbro = Integer.MAX_VALUE;
        this.zzbrp = false;
    }

    static zzzg zza(byte[] bArr, int i, int i2, boolean z) {
        zzzg zzzi = new zzzi(bArr, i, i2);
        try {
            zzzi.zzaf(i2);
            return zzzi;
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract int zzti();
}
