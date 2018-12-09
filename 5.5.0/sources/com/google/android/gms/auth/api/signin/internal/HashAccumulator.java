package com.google.android.gms.auth.api.signin.internal;

import com.google.android.gms.common.util.VisibleForTesting;

public class HashAccumulator {
    @VisibleForTesting
    private static int zzad = 31;
    private int zzae = 1;

    public HashAccumulator addBoolean(boolean z) {
        this.zzae = (z ? 1 : 0) + (this.zzae * zzad);
        return this;
    }

    public HashAccumulator addObject(Object obj) {
        this.zzae = (obj == null ? 0 : obj.hashCode()) + (this.zzae * zzad);
        return this;
    }

    public int hash() {
        return this.zzae;
    }
}
