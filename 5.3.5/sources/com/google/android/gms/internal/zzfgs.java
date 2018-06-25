package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class zzfgs implements Serializable, Iterable<Byte> {
    public static final zzfgs zzpnw = new zzfgz(zzfhz.EMPTY_BYTE_ARRAY);
    private static final zzfgw zzpnx = (zzfgo.zzcxm() ? new zzfha() : new zzfgu());
    private int zzmmk = 0;

    zzfgs() {
    }

    private static zzfgs zza(Iterator<zzfgs> it, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", new Object[]{Integer.valueOf(i)}));
        } else if (i == 1) {
            return (zzfgs) it.next();
        } else {
            int i2 = i >>> 1;
            zzfgs zza = zza(it, i2);
            zzfgs zza2 = zza(it, i - i2);
            if (Integer.MAX_VALUE - zza.size() >= zza2.size()) {
                return zzfjq.zza(zza, zza2);
            }
            int size = zza.size();
            throw new IllegalArgumentException("ByteString would be too long: " + size + "+" + zza2.size());
        }
    }

    static void zzab(int i, int i2) {
        if (((i2 - (i + 1)) | i) >= 0) {
            return;
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + i2);
    }

    public static zzfgs zzaz(byte[] bArr) {
        return zzf(bArr, 0, bArr.length);
    }

    static zzfgs zzba(byte[] bArr) {
        return new zzfgz(bArr);
    }

    public static zzfgs zzf(byte[] bArr, int i, int i2) {
        return new zzfgz(zzpnx.zzg(bArr, i, i2));
    }

    public static zzfgs zzg(Iterable<zzfgs> iterable) {
        int size = ((Collection) iterable).size();
        return size == 0 ? zzpnw : zza(iterable.iterator(), size);
    }

    static int zzh(int i, int i2, int i3) {
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

    static zzfgx zzle(int i) {
        return new zzfgx(i);
    }

    public static zzfgs zztv(String str) {
        return new zzfgz(str.getBytes(zzfhz.UTF_8));
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzmmk;
        if (i == 0) {
            i = size();
            i = zzg(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzmmk = i;
        }
        return i;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public /* synthetic */ Iterator iterator() {
        return new zzfgt(this);
    }

    public abstract int size();

    public final byte[] toByteArray() {
        int size = size();
        if (size == 0) {
            return zzfhz.EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[size];
        zzb(bArr, 0, 0, size);
        return bArr;
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    abstract void zza(zzfgr zzfgr) throws IOException;

    public final void zza(byte[] bArr, int i, int i2, int i3) {
        zzh(i, i + i3, size());
        zzh(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            zzb(bArr, i, i2, i3);
        }
    }

    public abstract zzfgs zzaa(int i, int i2);

    protected abstract void zzb(byte[] bArr, int i, int i2, int i3);

    public abstract zzfhb zzcxq();

    protected abstract int zzcxr();

    protected abstract boolean zzcxs();

    protected final int zzcxt() {
        return this.zzmmk;
    }

    protected abstract int zzg(int i, int i2, int i3);

    public abstract byte zzld(int i);
}
