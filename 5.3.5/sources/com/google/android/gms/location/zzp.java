package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzp extends zzcgs {
    private /* synthetic */ TaskCompletionSource zzeuo;

    zzp(FusedLocationProviderClient fusedLocationProviderClient, TaskCompletionSource taskCompletionSource) {
        this.zzeuo = taskCompletionSource;
    }

    public final void zza(zzcgl zzcgl) throws RemoteException {
        Status status = zzcgl.getStatus();
        if (status == null) {
            this.zzeuo.trySetException(new ApiException(new Status(8, "Got null status from location service")));
        } else if (status.getStatusCode() == 0) {
            this.zzeuo.setResult(Boolean.valueOf(true));
        } else {
            this.zzeuo.trySetException(zzb.zzy(status));
        }
    }
}
