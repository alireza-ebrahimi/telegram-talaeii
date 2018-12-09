package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

final class zzas implements ConnectionCallbacks, OnConnectionFailedListener {
    private final /* synthetic */ zzaj zzhv;

    private zzas(zzaj zzaj) {
        this.zzhv = zzaj;
    }

    public final void onConnected(Bundle bundle) {
        this.zzhv.zzhn.signIn(new zzaq(this.zzhv));
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzhv.zzga.lock();
        try {
            if (this.zzhv.zzd(connectionResult)) {
                this.zzhv.zzau();
                this.zzhv.zzas();
            } else {
                this.zzhv.zze(connectionResult);
            }
            this.zzhv.zzga.unlock();
        } catch (Throwable th) {
            this.zzhv.zzga.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
