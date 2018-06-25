package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.zzaa;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbe implements ResultCallback<Status> {
    private /* synthetic */ GoogleApiClient zzeya;
    private /* synthetic */ zzba zzfyr;
    private /* synthetic */ zzdb zzfyt;
    private /* synthetic */ boolean zzfyu;

    zzbe(zzba zzba, zzdb zzdb, boolean z, GoogleApiClient googleApiClient) {
        this.zzfyr = zzba;
        this.zzfyt = zzdb;
        this.zzfyu = z;
        this.zzeya = googleApiClient;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        zzaa.zzbs(this.zzfyr.mContext).zzacz();
        if (status.isSuccess() && this.zzfyr.isConnected()) {
            this.zzfyr.reconnect();
        }
        this.zzfyt.setResult(status);
        if (this.zzfyu) {
            this.zzeya.disconnect();
        }
    }
}
