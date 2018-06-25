package com.google.android.gms.internal.measurement;

public abstract class zzacg {
    protected volatile int zzbxr = -1;

    public static final <T extends zzacg> T zza(T t, byte[] bArr) {
        return zzb(t, bArr, 0, bArr.length);
    }

    public static final void zza(zzacg zzacg, byte[] bArr, int i, int i2) {
        try {
            zzaby zzb = zzaby.zzb(bArr, 0, i2);
            zzacg.zza(zzb);
            zzb.zzvn();
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    private static final <T extends zzacg> T zzb(T t, byte[] bArr, int i, int i2) {
        try {
            zzabx zza = zzabx.zza(bArr, 0, i2);
            t.zzb(zza);
            zza.zzaj(0);
            return t;
        } catch (zzacf e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public /* synthetic */ Object clone() {
        return zzvo();
    }

    public String toString() {
        return zzach.zzc(this);
    }

    protected int zza() {
        return 0;
    }

    public void zza(zzaby zzaby) {
    }

    public abstract zzacg zzb(zzabx zzabx);

    public zzacg zzvo() {
        return (zzacg) super.clone();
    }

    public final int zzvu() {
        if (this.zzbxr < 0) {
            zzvv();
        }
        return this.zzbxr;
    }

    public final int zzvv() {
        int zza = zza();
        this.zzbxr = zza;
        return zza;
    }
}
