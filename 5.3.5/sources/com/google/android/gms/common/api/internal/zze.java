package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zze<TResult> extends zza {
    private final TaskCompletionSource<TResult> zzejm;
    private final zzde<zzb, TResult> zzfua;
    private final zzda zzfub;

    public zze(int i, zzde<zzb, TResult> zzde, TaskCompletionSource<TResult> taskCompletionSource, zzda zzda) {
        super(i);
        this.zzejm = taskCompletionSource;
        this.zzfua = zzde;
        this.zzfub = zzda;
    }

    public final void zza(@NonNull zzae zzae, boolean z) {
        zzae.zza(this.zzejm, z);
    }

    public final void zza(zzbo<?> zzbo) throws DeadObjectException {
        try {
            this.zzfua.zza(zzbo.zzaix(), this.zzejm);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zzs(zza.zza(e2));
        } catch (Exception e3) {
            this.zzejm.trySetException(e3);
        }
    }

    public final void zzs(@NonNull Status status) {
        this.zzejm.trySetException(this.zzfub.zzt(status));
    }
}
