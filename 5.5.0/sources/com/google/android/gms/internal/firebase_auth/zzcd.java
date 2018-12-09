package com.google.android.gms.internal.firebase_auth;

public abstract class zzcd {
    private static volatile boolean zzmv = false;
    int zzmq;
    int zzmr;
    private int zzms;
    zzcg zzmt;
    private boolean zzmu;

    private zzcd() {
        this.zzmr = 100;
        this.zzms = Integer.MAX_VALUE;
        this.zzmu = false;
    }

    static zzcd zza(byte[] bArr, int i, int i2, boolean z) {
        zzcd zzcf = new zzcf(bArr, i, i2, false);
        try {
            zzcf.zzp(i2);
            return zzcf;
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static zzcd zzd(byte[] bArr, int i, int i2) {
        return zza(bArr, i, i2, false);
    }

    public abstract double readDouble();

    public abstract float readFloat();

    public abstract String readString();

    public abstract <T extends zzeh> T zza(zzer<T> zzer, zzco zzco);

    public abstract int zzcc();

    public abstract long zzcd();

    public abstract long zzce();

    public abstract int zzcf();

    public abstract long zzcg();

    public abstract int zzch();

    public abstract boolean zzci();

    public abstract String zzcj();

    public abstract zzbu zzck();

    public abstract int zzcl();

    public abstract int zzcm();

    public abstract int zzcn();

    public abstract long zzco();

    public abstract int zzcp();

    public abstract long zzcq();

    abstract long zzcr();

    public abstract boolean zzcs();

    public abstract int zzct();

    public abstract void zzm(int i);

    public abstract boolean zzn(int i);

    public final int zzo(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Recursion limit cannot be negative: " + i);
        }
        int i2 = this.zzmr;
        this.zzmr = i;
        return i2;
    }

    public abstract int zzp(int i);

    public abstract void zzq(int i);

    public abstract void zzr(int i);
}
