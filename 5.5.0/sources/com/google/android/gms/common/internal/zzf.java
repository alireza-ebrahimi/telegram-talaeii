package com.google.android.gms.common.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks;

final class zzf implements BaseConnectionCallbacks {
    private final /* synthetic */ ConnectionCallbacks zztd;

    zzf(ConnectionCallbacks connectionCallbacks) {
        this.zztd = connectionCallbacks;
    }

    public final void onConnected(Bundle bundle) {
        this.zztd.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        this.zztd.onConnectionSuspended(i);
    }
}
