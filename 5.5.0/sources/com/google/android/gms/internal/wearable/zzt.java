package com.google.android.gms.internal.wearable;

public abstract class zzt {
    protected volatile int zzhl = -1;

    public static final <T extends zzt> T zza(T t, byte[] bArr, int i, int i2) {
        try {
            zzk zza = zzk.zza(bArr, 0, i2);
            t.zza(zza);
            zza.zzc(0);
            return t;
        } catch (zzs e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public static final byte[] zzb(zzt zzt) {
        byte[] bArr = new byte[zzt.zzx()];
        try {
            zzl zzb = zzl.zzb(bArr, 0, bArr.length);
            zzt.zza(zzb);
            zzb.zzr();
            return bArr;
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public /* synthetic */ Object clone() {
        return zzs();
    }

    public String toString() {
        return zzu.zzc(this);
    }

    public abstract zzt zza(zzk zzk);

    public void zza(zzl zzl) {
    }

    protected int zzg() {
        return 0;
    }

    public zzt zzs() {
        return (zzt) super.clone();
    }

    public final int zzx() {
        int zzg = zzg();
        this.zzhl = zzg;
        return zzg;
    }
}
