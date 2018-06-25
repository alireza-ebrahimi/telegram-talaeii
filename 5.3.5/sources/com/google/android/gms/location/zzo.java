package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdo;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzo extends zzdo<zzchh, LocationCallback> {
    private /* synthetic */ FusedLocationProviderClient zzirn;

    zzo(FusedLocationProviderClient fusedLocationProviderClient, zzck zzck) {
        this.zzirn = fusedLocationProviderClient;
        super(zzck);
    }

    protected final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        try {
            ((zzchh) zzb).zzb(zzakx(), this.zzirn.zzc(taskCompletionSource));
        } catch (Exception e) {
            taskCompletionSource.trySetException(e);
        }
    }
}
