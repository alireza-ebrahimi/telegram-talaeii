package com.google.android.gms.internal.firebase_auth;

public abstract class zzgt {
    protected volatile int zzya = -1;

    public static final void zza(zzgt zzgt, byte[] bArr, int i, int i2) {
        try {
            zzgl zzj = zzgl.zzj(bArr, 0, i2);
            zzgt.zza(zzj);
            zzj.zzgm();
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends zzgt> T zzb(T t, byte[] bArr, int i, int i2) {
        try {
            zzgk zzi = zzgk.zzi(bArr, 0, i2);
            t.zza(zzi);
            zzi.zzm(0);
            return t;
        } catch (zzgs e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public /* synthetic */ Object clone() {
        return zzgn();
    }

    public String toString() {
        return zzgu.zzc(this);
    }

    public abstract zzgt zza(zzgk zzgk);

    public void zza(zzgl zzgl) {
    }

    protected int zzb() {
        return 0;
    }

    public final int zzdq() {
        int zzb = zzb();
        this.zzya = zzb;
        return zzb;
    }

    public zzgt zzgn() {
        return (zzgt) super.clone();
    }
}
