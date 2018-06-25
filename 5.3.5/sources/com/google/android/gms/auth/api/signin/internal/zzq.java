package com.google.android.gms.auth.api.signin.internal;

public final class zzq {
    private static int zzenz = 31;
    private int zzeoa = 1;

    public final int zzacr() {
        return this.zzeoa;
    }

    public final zzq zzav(boolean z) {
        this.zzeoa = (z ? 1 : 0) + (this.zzeoa * zzenz);
        return this;
    }

    public final zzq zzs(Object obj) {
        this.zzeoa = (obj == null ? 0 : obj.hashCode()) + (this.zzeoa * zzenz);
        return this;
    }
}
