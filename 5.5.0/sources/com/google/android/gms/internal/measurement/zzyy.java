package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Iterator;

public abstract class zzyy implements Serializable, Iterable<Byte> {
    public static final zzyy zzbrh = new zzze(zzzt.zzbta);
    private static final zzzc zzbri = (zzyx.zzte() ? new zzzf() : new zzza());
    private int zzbon = 0;

    zzyy() {
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

    public static zzyy zzfg(String str) {
        return new zzze(str.getBytes(zzzt.UTF_8));
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzbon;
        if (i == 0) {
            i = size();
            i = zza(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzbon = i;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return new zzyz(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    protected abstract int zza(int i, int i2, int i3);

    public abstract byte zzae(int i);

    public abstract zzyy zzb(int i, int i2);

    protected final int zztg() {
        return this.zzbon;
    }
}
