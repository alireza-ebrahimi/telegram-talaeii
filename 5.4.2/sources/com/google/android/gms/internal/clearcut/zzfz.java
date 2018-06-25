package com.google.android.gms.internal.clearcut;

public class zzfz {
    protected volatile int zzrs = -1;

    public static final void zza(zzfz zzfz, byte[] bArr, int i, int i2) {
        try {
            zzfs zzh = zzfs.zzh(bArr, 0, i2);
            zzfz.zza(zzh);
            zzh.zzem();
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public /* synthetic */ Object clone() {
        return zzep();
    }

    public String toString() {
        return zzga.zza(this);
    }

    public void zza(zzfs zzfs) {
    }

    public final int zzas() {
        int zzen = zzen();
        this.zzrs = zzen;
        return zzen;
    }

    protected int zzen() {
        return 0;
    }

    public zzfz zzep() {
        return (zzfz) super.clone();
    }
}
