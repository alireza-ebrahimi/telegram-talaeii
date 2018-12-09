package com.google.android.gms.internal.clearcut;

public final class zzfw implements Cloneable {
    private static final zzfx zzrl = new zzfx();
    private int mSize;
    private boolean zzrm;
    private int[] zzrn;
    private zzfx[] zzro;

    zzfw() {
        this(10);
    }

    private zzfw(int i) {
        this.zzrm = false;
        int i2 = i << 2;
        for (int i3 = 4; i3 < 32; i3++) {
            if (i2 <= (1 << i3) - 12) {
                i2 = (1 << i3) - 12;
                break;
            }
        }
        i2 /= 4;
        this.zzrn = new int[i2];
        this.zzro = new zzfx[i2];
        this.mSize = 0;
    }

    public final /* synthetic */ Object clone() {
        int i = this.mSize;
        zzfw zzfw = new zzfw(i);
        System.arraycopy(this.zzrn, 0, zzfw.zzrn, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzro[i2] != null) {
                zzfw.zzro[i2] = (zzfx) this.zzro[i2].clone();
            }
        }
        zzfw.mSize = i;
        return zzfw;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfw)) {
            return false;
        }
        zzfw zzfw = (zzfw) obj;
        if (this.mSize != zzfw.mSize) {
            return false;
        }
        int i;
        boolean z;
        int[] iArr = this.zzrn;
        int[] iArr2 = zzfw.zzrn;
        int i2 = this.mSize;
        for (i = 0; i < i2; i++) {
            if (iArr[i] != iArr2[i]) {
                z = false;
                break;
            }
        }
        z = true;
        if (z) {
            zzfx[] zzfxArr = this.zzro;
            zzfx[] zzfxArr2 = zzfw.zzro;
            i2 = this.mSize;
            for (i = 0; i < i2; i++) {
                if (!zzfxArr[i].equals(zzfxArr2[i])) {
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
            i = (((i * 31) + this.zzrn[i2]) * 31) + this.zzro[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final zzfx zzaq(int i) {
        return this.zzro[i];
    }
}
