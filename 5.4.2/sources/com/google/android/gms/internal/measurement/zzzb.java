package com.google.android.gms.internal.measurement;

final class zzzb extends zzze {
    private final int zzbrk;
    private final int zzbrl;

    zzzb(byte[] bArr, int i, int i2) {
        super(bArr);
        zzyy.zzb(i, i + i2, bArr.length);
        this.zzbrk = i;
        this.zzbrl = i2;
    }

    public final int size() {
        return this.zzbrl;
    }

    public final byte zzae(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzbrm[this.zzbrk + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + size);
    }

    protected final int zzth() {
        return this.zzbrk;
    }
}
