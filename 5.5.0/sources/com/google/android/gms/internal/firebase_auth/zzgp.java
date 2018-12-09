package com.google.android.gms.internal.firebase_auth;

public final class zzgp implements Cloneable {
    private static final zzgq zzxt = new zzgq();
    private int mSize;
    private boolean zzxu;
    private int[] zzxv;
    private zzgq[] zzxw;

    zzgp() {
        this(10);
    }

    private zzgp(int i) {
        this.zzxu = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzxv = new int[idealIntArraySize];
        this.zzxw = new zzgq[idealIntArraySize];
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

    private final int zzbd(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzxv[i4];
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
        zzgp zzgp = new zzgp(i);
        System.arraycopy(this.zzxv, 0, zzgp.zzxv, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzxw[i2] != null) {
                zzgp.zzxw[i2] = (zzgq) this.zzxw[i2].clone();
            }
        }
        zzgp.mSize = i;
        return zzgp;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgp)) {
            return false;
        }
        zzgp zzgp = (zzgp) obj;
        if (this.mSize != zzgp.mSize) {
            return false;
        }
        int i;
        boolean z;
        int[] iArr = this.zzxv;
        int[] iArr2 = zzgp.zzxv;
        int i2 = this.mSize;
        for (i = 0; i < i2; i++) {
            if (iArr[i] != iArr2[i]) {
                z = false;
                break;
            }
        }
        z = true;
        if (z) {
            zzgq[] zzgqArr = this.zzxw;
            zzgq[] zzgqArr2 = zzgp.zzxw;
            i2 = this.mSize;
            for (i = 0; i < i2; i++) {
                if (!zzgqArr[i].equals(zzgqArr2[i])) {
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
            i = (((i * 31) + this.zzxv[i2]) * 31) + this.zzxw[i2].hashCode();
        }
        return i;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int i, zzgq zzgq) {
        int zzbd = zzbd(i);
        if (zzbd >= 0) {
            this.zzxw[zzbd] = zzgq;
            return;
        }
        zzbd ^= -1;
        if (zzbd >= this.mSize || this.zzxw[zzbd] != zzxt) {
            if (this.mSize >= this.zzxv.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new zzgq[idealIntArraySize];
                System.arraycopy(this.zzxv, 0, obj, 0, this.zzxv.length);
                System.arraycopy(this.zzxw, 0, obj2, 0, this.zzxw.length);
                this.zzxv = obj;
                this.zzxw = obj2;
            }
            if (this.mSize - zzbd != 0) {
                System.arraycopy(this.zzxv, zzbd, this.zzxv, zzbd + 1, this.mSize - zzbd);
                System.arraycopy(this.zzxw, zzbd, this.zzxw, zzbd + 1, this.mSize - zzbd);
            }
            this.zzxv[zzbd] = i;
            this.zzxw[zzbd] = zzgq;
            this.mSize++;
            return;
        }
        this.zzxv[zzbd] = i;
        this.zzxw[zzbd] = zzgq;
    }

    final zzgq zzbb(int i) {
        int zzbd = zzbd(i);
        return (zzbd < 0 || this.zzxw[zzbd] == zzxt) ? null : this.zzxw[zzbd];
    }

    final zzgq zzbc(int i) {
        return this.zzxw[i];
    }
}
