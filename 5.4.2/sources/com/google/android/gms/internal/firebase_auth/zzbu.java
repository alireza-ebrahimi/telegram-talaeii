package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;

public abstract class zzbu implements Serializable, Iterable<Byte> {
    public static final zzbu zzmi = new zzcb(zzdd.EMPTY_BYTE_ARRAY);
    private static final zzby zzmj = (zzbr.zzbu() ? new zzcc() : new zzbw());
    private int zzmk = 0;

    zzbu() {
    }

    static zzbu zza(byte[] bArr) {
        return new zzcb(bArr);
    }

    public static zzbu zzak(String str) {
        return new zzcb(str.getBytes(zzdd.UTF_8));
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((((i | i2) | i4) | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("Beginning index: " + i + " < 0");
        } else if (i2 < i) {
            throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + i + ", " + i2);
        } else {
            throw new IndexOutOfBoundsException("End index: " + i2 + " >= " + i3);
        }
    }

    public static zzbu zzb(byte[] bArr, int i, int i2) {
        return new zzcb(zzmj.zzc(bArr, i, i2));
    }

    static zzbz zzl(int i) {
        return new zzbz(i);
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzmk;
        if (i == 0) {
            i = size();
            i = zza(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzmk = i;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return new zzbv(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzbu zza(int i, int i2);

    protected abstract String zza(Charset charset);

    abstract void zza(zzbt zzbt);

    public final String zzbw() {
        return size() == 0 ? TtmlNode.ANONYMOUS_REGION_ID : zza(zzdd.UTF_8);
    }

    public abstract boolean zzbx();

    protected final int zzby() {
        return this.zzmk;
    }

    public abstract byte zzk(int i);
}
