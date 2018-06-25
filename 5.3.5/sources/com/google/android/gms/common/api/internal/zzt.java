package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;

public final class zzt implements ConnectionCallbacks, OnConnectionFailedListener {
    public final Api<?> zzfop;
    private final boolean zzfvo;
    private zzu zzfvp;

    public zzt(Api<?> api, boolean z) {
        this.zzfop = api;
        this.zzfvo = z;
    }

    private final void zzair() {
        zzbq.checkNotNull(this.zzfvp, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    public final void onConnected(@Nullable Bundle bundle) {
        zzair();
        this.zzfvp.onConnected(bundle);
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzair();
        this.zzfvp.zza(connectionResult, this.zzfop, this.zzfvo);
    }

    public final void onConnectionSuspended(int i) {
        zzair();
        this.zzfvp.onConnectionSuspended(i);
    }

    public final void zza(zzu zzu) {
        this.zzfvp = zzu;
    }
}
