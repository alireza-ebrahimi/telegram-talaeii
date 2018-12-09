package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;

final class zzgv {
    final int tag;
    final byte[] zzmp;

    zzgv(int i, byte[] bArr) {
        this.tag = i;
        this.zzmp = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgv)) {
            return false;
        }
        zzgv zzgv = (zzgv) obj;
        return this.tag == zzgv.tag && Arrays.equals(this.zzmp, zzgv.zzmp);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzmp);
    }
}
