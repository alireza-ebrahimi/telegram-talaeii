package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchl;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzn extends zzcq<zzchh, LocationCallback> {
    private /* synthetic */ zzci zzhsp;
    private /* synthetic */ zzchl zzirm;

    zzn(FusedLocationProviderClient fusedLocationProviderClient, zzci zzci, zzchl zzchl, zzci zzci2) {
        this.zzirm = zzchl;
        this.zzhsp = zzci2;
        super(zzci);
    }

    protected final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzchh) zzb).zza(this.zzirm, this.zzhsp, new zza(taskCompletionSource));
    }
}
