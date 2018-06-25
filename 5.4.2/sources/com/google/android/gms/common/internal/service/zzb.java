package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzb extends zzd {
    zzb(CommonApiImpl commonApiImpl, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((ICommonService) ((CommonClient) anyClient).getService()).clearDefaultAccount(new zza(this));
    }
}
