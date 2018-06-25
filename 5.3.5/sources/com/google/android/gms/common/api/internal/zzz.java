package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;

@Hide
public final class zzz<O extends ApiOptions> extends GoogleApi<O> {
    private final zza<? extends zzcyj, zzcyk> zzfth;
    private final zze zzfwd;
    private final zzt zzfwe;
    private final zzr zzfwf;

    public zzz(@NonNull Context context, Api<O> api, Looper looper, @NonNull zze zze, @NonNull zzt zzt, zzr zzr, zza<? extends zzcyj, zzcyk> zza) {
        super(context, api, looper);
        this.zzfwd = zze;
        this.zzfwe = zzt;
        this.zzfwf = zzr;
        this.zzfth = zza;
        this.zzfsq.zza((GoogleApi) this);
    }

    public final zze zza(Looper looper, zzbo<O> zzbo) {
        this.zzfwe.zza(zzbo);
        return this.zzfwd;
    }

    public final zzcv zza(Context context, Handler handler) {
        return new zzcv(context, handler, this.zzfwf, this.zzfth);
    }

    public final zze zzaix() {
        return this.zzfwd;
    }
}
