package com.google.android.gms.internal;

import java.util.Arrays;

final class zzflu {
    final int tag;
    final byte[] zzjwl;

    zzflu(int i, byte[] bArr) {
        this.tag = i;
        this.zzjwl = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzflu)) {
            return false;
        }
        zzflu zzflu = (zzflu) obj;
        return this.tag == zzflu.tag && Arrays.equals(this.zzjwl, zzflu.zzjwl);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzjwl);
    }
}
