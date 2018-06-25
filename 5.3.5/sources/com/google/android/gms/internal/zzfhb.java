package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;

public abstract class zzfhb {
    private static volatile boolean zzpog;
    int zzpoc;
    int zzpod;
    int zzpoe;
    private boolean zzpof;

    static {
        zzpog = false;
        zzpog = true;
    }

    private zzfhb() {
        this.zzpod = 100;
        this.zzpoe = Integer.MAX_VALUE;
        this.zzpof = false;
    }

    static zzfhb zzb(byte[] bArr, int i, int i2, boolean z) {
        zzfhb zzfhd = new zzfhd(bArr, i, i2, z, null);
        try {
            zzfhd.zzli(i2);
            return zzfhd;
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static zzfhb zzbb(byte[] bArr) {
        return zzb(bArr, 0, bArr.length, false);
    }

    public static long zzct(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public static zzfhb zzh(InputStream inputStream) {
        if (inputStream != null) {
            return new zzfhe(inputStream, 4096, null);
        }
        byte[] bArr = zzfhz.EMPTY_BYTE_ARRAY;
        return zzb(bArr, 0, bArr.length, false);
    }

    public static zzfhb zzh(byte[] bArr, int i, int i2) {
        return zzb(bArr, i, i2, false);
    }

    public static int zzll(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public abstract double readDouble() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract String readString() throws IOException;

    public abstract <T extends zzfhu<T, ?>> T zza(T t, zzfhm zzfhm) throws IOException;

    public abstract void zza(zzfjd zzfjd, zzfhm zzfhm) throws IOException;

    public abstract int zzcxx() throws IOException;

    public abstract long zzcxy() throws IOException;

    public abstract long zzcxz() throws IOException;

    public abstract int zzcya() throws IOException;

    public abstract long zzcyb() throws IOException;

    public abstract int zzcyc() throws IOException;

    public abstract boolean zzcyd() throws IOException;

    public abstract String zzcye() throws IOException;

    public abstract zzfgs zzcyf() throws IOException;

    public abstract int zzcyg() throws IOException;

    public abstract int zzcyh() throws IOException;

    public abstract int zzcyi() throws IOException;

    public abstract long zzcyj() throws IOException;

    public abstract int zzcyk() throws IOException;

    public abstract long zzcyl() throws IOException;

    public abstract int zzcym() throws IOException;

    abstract long zzcyn() throws IOException;

    public abstract int zzcyo();

    public abstract boolean zzcyp() throws IOException;

    public abstract int zzcyq();

    public abstract void zzlf(int i) throws zzfie;

    public abstract boolean zzlg(int i) throws IOException;

    public final int zzlh(int i) {
        int i2 = this.zzpoe;
        this.zzpoe = Integer.MAX_VALUE;
        return i2;
    }

    public abstract int zzli(int i) throws zzfie;

    public abstract void zzlj(int i);

    public abstract void zzlk(int i) throws IOException;
}
