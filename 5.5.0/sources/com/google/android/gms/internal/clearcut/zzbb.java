package com.google.android.gms.internal.clearcut;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;

public abstract class zzbb implements Serializable, Iterable<Byte> {
    public static final zzbb zzfi = new zzbi(zzci.zzkt);
    private static final zzbf zzfj = (zzaw.zzx() ? new zzbj() : new zzbd());
    private int zzfk = 0;

    zzbb() {
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

    public static zzbb zzb(byte[] bArr, int i, int i2) {
        return new zzbi(zzfj.zzc(bArr, i, i2));
    }

    public static zzbb zzf(String str) {
        return new zzbi(str.getBytes(zzci.UTF_8));
    }

    static zzbg zzk(int i) {
        return new zzbg(i);
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzfk;
        if (i == 0) {
            i = size();
            i = zza(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzfk = i;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return new zzbc(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzbb zza(int i, int i2);

    protected abstract String zza(Charset charset);

    abstract void zza(zzba zzba);

    public abstract boolean zzaa();

    protected final int zzab() {
        return this.zzfk;
    }

    public abstract byte zzj(int i);

    public final String zzz() {
        return size() == 0 ? TtmlNode.ANONYMOUS_REGION_ID : zza(zzci.UTF_8);
    }
}
