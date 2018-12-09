package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager.zza;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzf<ResultT> extends zzb {
    private final TaskCompletionSource<ResultT> zzdu;
    private final TaskApiCall<AnyClient, ResultT> zzdy;
    private final StatusExceptionMapper zzdz;

    public zzf(int i, TaskApiCall<AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        super(i);
        this.zzdu = taskCompletionSource;
        this.zzdy = taskApiCall;
        this.zzdz = statusExceptionMapper;
    }

    public final Feature[] getRequiredFeatures() {
        return this.zzdy.zzca();
    }

    public final boolean shouldAutoResolveMissingFeatures() {
        return this.zzdy.shouldAutoResolveMissingFeatures();
    }

    public final void zza(Status status) {
        this.zzdu.trySetException(this.zzdz.getException(status));
    }

    public final void zza(zza<?> zza) {
        try {
            this.zzdy.doExecute(zza.zzae(), this.zzdu);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zza(zzb.zza(e2));
        } catch (RuntimeException e3) {
            zza(e3);
        }
    }

    public final void zza(zzaa zzaa, boolean z) {
        zzaa.zza(this.zzdu, z);
    }

    public final void zza(RuntimeException runtimeException) {
        this.zzdu.trySetException(runtimeException);
    }
}
