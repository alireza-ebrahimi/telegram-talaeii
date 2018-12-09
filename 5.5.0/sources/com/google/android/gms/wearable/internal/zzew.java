package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.MessageApi.MessageListener;

final class zzew extends zzn<Status> {
    private final /* synthetic */ MessageListener zzef;

    zzew(zzeu zzeu, GoogleApiClient googleApiClient, MessageListener messageListener) {
        this.zzef = messageListener;
        super(googleApiClient);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzhg) anyClient).zza((ResultHolder) this, this.zzef);
    }
}
