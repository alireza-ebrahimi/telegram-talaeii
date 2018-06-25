package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.GoogleApiManager.zza;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInClient;
import com.google.android.gms.signin.SignInOptions;

public final class zzv<O extends ApiOptions> extends GoogleApi<O> {
    private final AbstractClientBuilder<? extends SignInClient, SignInOptions> zzdh;
    private final Client zzgd;
    private final zzp zzge;
    private final ClientSettings zzgf;

    public zzv(Context context, Api<O> api, Looper looper, Client client, zzp zzp, ClientSettings clientSettings, AbstractClientBuilder<? extends SignInClient, SignInOptions> abstractClientBuilder) {
        super(context, api, looper);
        this.zzgd = client;
        this.zzge = zzp;
        this.zzgf = clientSettings;
        this.zzdh = abstractClientBuilder;
        this.zzcq.zza((GoogleApi) this);
    }

    public final Client zza(Looper looper, zza<O> zza) {
        this.zzge.zza(zza);
        return this.zzgd;
    }

    public final zzby zza(Context context, Handler handler) {
        return new zzby(context, handler, this.zzgf, this.zzdh);
    }

    public final Client zzae() {
        return this.zzgd;
    }
}
