package com.google.android.gms.internal.firebase_auth;

final class zzbx extends zzcb {
    private final int zzmm;
    private final int zzmn;

    zzbx(byte[] bArr, int i, int i2) {
        super(bArr);
        zzbu.zzb(i, i + i2, bArr.length);
        this.zzmm = i;
        this.zzmn = i2;
    }

    public final int size() {
        return this.zzmn;
    }

    protected final int zzbz() {
        return this.zzmm;
    }

    public final byte zzk(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzmp[this.zzmm + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + size);
    }
}
