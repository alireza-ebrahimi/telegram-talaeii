package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;

abstract class zzbgy<R extends Result> extends zzm<R, zzbha> {
    public zzbgy(GoogleApiClient googleApiClient) {
        super(zzbgs.API, googleApiClient);
    }
}
