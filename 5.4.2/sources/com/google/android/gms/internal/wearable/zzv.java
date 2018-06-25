package com.google.android.gms.internal.wearable;

import java.util.Arrays;

final class zzv {
    final int tag;
    final byte[] zzhm;

    zzv(int i, byte[] bArr) {
        this.tag = i;
        this.zzhm = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzv)) {
            return false;
        }
        zzv zzv = (zzv) obj;
        return this.tag == zzv.tag && Arrays.equals(this.zzhm, zzv.zzhm);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzhm);
    }
}
