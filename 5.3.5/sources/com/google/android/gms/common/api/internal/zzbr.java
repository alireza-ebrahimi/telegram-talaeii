package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzbr implements Runnable {
    private /* synthetic */ zzbo zzgaa;
    private /* synthetic */ ConnectionResult zzgab;

    zzbr(zzbo zzbo, ConnectionResult connectionResult) {
        this.zzgaa = zzbo;
        this.zzgab = connectionResult;
    }

    public final void run() {
        this.zzgaa.onConnectionFailed(this.zzgab);
    }
}
