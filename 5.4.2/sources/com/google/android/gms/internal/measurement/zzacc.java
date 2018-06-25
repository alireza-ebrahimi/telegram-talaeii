package com.google.android.gms.internal.measurement;

public final class zzacc implements Cloneable {
    private static final zzacd zzbxk = new zzacd();
    private int mSize;
    private boolean zzbxl;
    private int[] zzbxm;
    private zzacd[] zzbxn;

    zzacc() {
        this(10);
    }

    private zzacc(int i) {
        this.zzbxl = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzbxm = new int[idealIntArraySize];
        this.zzbxn = new zzacd[idealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        for (int i3 = 4; i3 < 32; i3++) {
            if (i2 <= (1 << i3) - 12) {
                i2 = (1 << i3) - 12;
                break;
            }
        }
        return i2 / 4;
    }

    private final int zzav(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzbxm[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return i2 ^ -1;
    }

    public final /* synthetic */ Object clone() {
        int i = this.mSize;
        zzacc zzacc = new zzacc(i);
        System.arraycopy(this.zzbxm, 0, zzacc.zzbxm, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzbxn[i2] != null) {
                zzacc.zzbxn[i2] = (zzacd) this.zzbxn[i2].clone();
            }
        }
        zzacc.mSize = i;
        return zzacc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzacc)) {
            return false;
        }
        zzacc zzacc = (zzacc) obj;
        if (this.mSize != zzacc.mSize) {
            return false;
        }
        int i;
        boolean z;
        int[] iArr = this.zzbxm;
        int[] iArr2 = zzacc.zzbxm;
        int i2 = this.mSize;
        for (i = 0; i < i2; i++) {
            if (iArr[i] != iArr2[i]) {
                z = false;
                break;
            }
        }
        z = true;
        if (z) {
            zzacd[] zzacdArr = this.zzbxn;
            zzacd[] zzacdArr2 = zzacc.zzbxn;
            i2 = this.mSize;
            for (i = 0; i < i2; i++) {
                if (!zzacdArr[i].equals(zzacdArr2[i])) {
                    z = false;
                    break;
                }
            }
            z = true;
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzbxm[i2]) * 31) + this.zzbxn[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int i, zzacd zzacd) {
        int zzav = zzav(i);
        if (zzav >= 0) {
            this.zzbxn[zzav] = zzacd;
            return;
        }
        zzav ^= -1;
        if (zzav >= this.mSize || this.zzbxn[zzav] != zzbxk) {
            if (this.mSize >= this.zzbxm.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new zzacd[idealIntArraySize];
                System.arraycopy(this.zzbxm, 0, obj, 0, this.zzbxm.length);
                System.arraycopy(this.zzbxn, 0, obj2, 0, this.zzbxn.length);
                this.zzbxm = obj;
                this.zzbxn = obj2;
            }
            if (this.mSize - zzav != 0) {
                System.arraycopy(this.zzbxm, zzav, this.zzbxm, zzav + 1, this.mSize - zzav);
                System.arraycopy(this.zzbxn, zzav, this.zzbxn, zzav + 1, this.mSize - zzav);
            }
            this.zzbxm[zzav] = i;
            this.zzbxn[zzav] = zzacd;
            this.mSize++;
            return;
        }
        this.zzbxm[zzav] = i;
        this.zzbxn[zzav] = zzacd;
    }

    final zzacd zzat(int i) {
        int zzav = zzav(i);
        return (zzav < 0 || this.zzbxn[zzav] == zzbxk) ? null : this.zzbxn[zzav];
    }

    final zzacd zzau(int i) {
        return this.zzbxn[i];
    }
}
