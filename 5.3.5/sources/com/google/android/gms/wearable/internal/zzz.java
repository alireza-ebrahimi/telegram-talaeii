package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;

final class zzz extends zzn<Status> {
    private CapabilityListener zzlsp;

    private zzz(GoogleApiClient googleApiClient, CapabilityListener capabilityListener) {
        super(googleApiClient);
        this.zzlsp = capabilityListener;
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, this.zzlsp);
        this.zzlsp = null;
    }

    public final /* synthetic */ Result zzb(Status status) {
        this.zzlsp = null;
        return status;
    }
}
