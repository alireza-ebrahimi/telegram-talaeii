package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.zzbg;
import java.util.Arrays;

public final class zzh<O extends ApiOptions> {
    private final Api<O> zzfop;
    private final O zzfsm;
    private final boolean zzfud = true;
    private final int zzfue;

    private zzh(Api<O> api) {
        this.zzfop = api;
        this.zzfsm = null;
        this.zzfue = System.identityHashCode(this);
    }

    private zzh(Api<O> api, O o) {
        this.zzfop = api;
        this.zzfsm = o;
        this.zzfue = Arrays.hashCode(new Object[]{this.zzfop, this.zzfsm});
    }

    public static <O extends ApiOptions> zzh<O> zza(Api<O> api, O o) {
        return new zzh(api, o);
    }

    public static <O extends ApiOptions> zzh<O> zzb(Api<O> api) {
        return new zzh(api);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzh = (zzh) obj;
        return !this.zzfud && !zzh.zzfud && zzbg.equal(this.zzfop, zzh.zzfop) && zzbg.equal(this.zzfsm, zzh.zzfsm);
    }

    public final int hashCode() {
        return this.zzfue;
    }

    public final String zzaig() {
        return this.zzfop.getName();
    }
}
