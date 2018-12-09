package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener;

final class zzg implements BaseOnConnectionFailedListener {
    private final /* synthetic */ OnConnectionFailedListener zzte;

    zzg(OnConnectionFailedListener onConnectionFailedListener) {
        this.zzte = onConnectionFailedListener;
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzte.onConnectionFailed(connectionResult);
    }
}
