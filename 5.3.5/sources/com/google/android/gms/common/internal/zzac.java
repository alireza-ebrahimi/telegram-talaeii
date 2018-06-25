package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

final class zzac implements zzf {
    private /* synthetic */ ConnectionCallbacks zzggj;

    zzac(ConnectionCallbacks connectionCallbacks) {
        this.zzggj = connectionCallbacks;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzggj.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        this.zzggj.onConnectionSuspended(i);
    }
}
