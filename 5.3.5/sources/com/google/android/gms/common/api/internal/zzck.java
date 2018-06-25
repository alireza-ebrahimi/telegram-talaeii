package com.google.android.gms.common.api.internal;

public final class zzck<L> {
    private final L zzgat;
    private final String zzgaw;

    zzck(L l, String str) {
        this.zzgat = l;
        this.zzgaw = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzck)) {
            return false;
        }
        zzck zzck = (zzck) obj;
        return this.zzgat == zzck.zzgat && this.zzgaw.equals(zzck.zzgaw);
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zzgat) * 31) + this.zzgaw.hashCode();
    }
}
