package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import java.util.concurrent.atomic.AtomicReference;

final class zzbc implements ConnectionCallbacks {
    private /* synthetic */ zzba zzfyr;
    private /* synthetic */ AtomicReference zzfys;
    private /* synthetic */ zzdb zzfyt;

    zzbc(zzba zzba, AtomicReference atomicReference, zzdb zzdb) {
        this.zzfyr = zzba;
        this.zzfys = atomicReference;
        this.zzfyt = zzdb;
    }

    public final void onConnected(Bundle bundle) {
        this.zzfyr.zza((GoogleApiClient) this.zzfys.get(), this.zzfyt, true);
    }

    public final void onConnectionSuspended(int i) {
    }
}
