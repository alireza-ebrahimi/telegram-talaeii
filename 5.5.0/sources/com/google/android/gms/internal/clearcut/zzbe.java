package com.google.android.gms.internal.clearcut;

final class zzbe extends zzbi {
    private final int zzfm;
    private final int zzfn;

    zzbe(byte[] bArr, int i, int i2) {
        super(bArr);
        zzbb.zzb(i, i + i2, bArr.length);
        this.zzfm = i;
        this.zzfn = i2;
    }

    public final int size() {
        return this.zzfn;
    }

    protected final int zzac() {
        return this.zzfm;
    }

    public final byte zzj(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzfp[this.zzfm + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + size);
    }
}
