package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

final class zzax implements ConnectionCallbacks, OnConnectionFailedListener {
    private /* synthetic */ zzao zzfxt;

    private zzax(zzao zzao) {
        this.zzfxt = zzao;
    }

    public final void onConnected(Bundle bundle) {
        this.zzfxt.zzfxl.zza(new zzav(this.zzfxt));
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzfxt.zzfwa.lock();
        try {
            if (this.zzfxt.zzd(connectionResult)) {
                this.zzfxt.zzajn();
                this.zzfxt.zzajl();
            } else {
                this.zzfxt.zze(connectionResult);
            }
            this.zzfxt.zzfwa.unlock();
        } catch (Throwable th) {
            this.zzfxt.zzfwa.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
