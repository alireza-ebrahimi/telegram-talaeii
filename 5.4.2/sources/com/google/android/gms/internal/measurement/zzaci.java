package com.google.android.gms.internal.measurement;

import java.util.Arrays;

final class zzaci {
    final int tag;
    final byte[] zzbrm;

    zzaci(int i, byte[] bArr) {
        this.tag = i;
        this.zzbrm = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzaci)) {
            return false;
        }
        zzaci zzaci = (zzaci) obj;
        return this.tag == zzaci.tag && Arrays.equals(this.zzbrm, zzaci.zzbrm);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbrm);
    }
}
