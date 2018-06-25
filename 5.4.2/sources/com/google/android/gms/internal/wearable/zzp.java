package com.google.android.gms.internal.wearable;

public final class zzp implements Cloneable {
    private static final zzq zzhe = new zzq();
    private int mSize;
    private boolean zzhf;
    private int[] zzhg;
    private zzq[] zzhh;

    zzp() {
        this(10);
    }

    private zzp(int i) {
        this.zzhf = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzhg = new int[idealIntArraySize];
        this.zzhh = new zzq[idealIntArraySize];
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

    private final int zzq(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzhg[i4];
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
        zzp zzp = new zzp(i);
        System.arraycopy(this.zzhg, 0, zzp.zzhg, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzhh[i2] != null) {
                zzp.zzhh[i2] = (zzq) this.zzhh[i2].clone();
            }
        }
        zzp.mSize = i;
        return zzp;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzp)) {
            return false;
        }
        zzp zzp = (zzp) obj;
        if (this.mSize != zzp.mSize) {
            return false;
        }
        int i;
        boolean z;
        int[] iArr = this.zzhg;
        int[] iArr2 = zzp.zzhg;
        int i2 = this.mSize;
        for (i = 0; i < i2; i++) {
            if (iArr[i] != iArr2[i]) {
                z = false;
                break;
            }
        }
        z = true;
        if (z) {
            zzq[] zzqArr = this.zzhh;
            zzq[] zzqArr2 = zzp.zzhh;
            i2 = this.mSize;
            for (i = 0; i < i2; i++) {
                if (!zzqArr[i].equals(zzqArr2[i])) {
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
            i = (((i * 31) + this.zzhg[i2]) * 31) + this.zzhh[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int i, zzq zzq) {
        int zzq2 = zzq(i);
        if (zzq2 >= 0) {
            this.zzhh[zzq2] = zzq;
            return;
        }
        zzq2 ^= -1;
        if (zzq2 >= this.mSize || this.zzhh[zzq2] != zzhe) {
            if (this.mSize >= this.zzhg.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new zzq[idealIntArraySize];
                System.arraycopy(this.zzhg, 0, obj, 0, this.zzhg.length);
                System.arraycopy(this.zzhh, 0, obj2, 0, this.zzhh.length);
                this.zzhg = obj;
                this.zzhh = obj2;
            }
            if (this.mSize - zzq2 != 0) {
                System.arraycopy(this.zzhg, zzq2, this.zzhg, zzq2 + 1, this.mSize - zzq2);
                System.arraycopy(this.zzhh, zzq2, this.zzhh, zzq2 + 1, this.mSize - zzq2);
            }
            this.zzhg[zzq2] = i;
            this.zzhh[zzq2] = zzq;
            this.mSize++;
            return;
        }
        this.zzhg[zzq2] = i;
        this.zzhh[zzq2] = zzq;
    }

    final zzq zzo(int i) {
        int zzq = zzq(i);
        return (zzq < 0 || this.zzhh[zzq] == zzhe) ? null : this.zzhh[zzq];
    }

    final zzq zzp(int i) {
        return this.zzhh[i];
    }
}
