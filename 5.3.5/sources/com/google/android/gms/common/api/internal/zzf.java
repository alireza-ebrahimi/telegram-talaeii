package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzf extends zzb<Boolean> {
    private zzck<?> zzfuc;

    public zzf(zzck<?> zzck, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzfuc = zzck;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzae zzae, boolean z) {
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull RuntimeException runtimeException) {
        super.zza(runtimeException);
    }

    public final void zzb(zzbo<?> zzbo) throws RemoteException {
        zzcr zzcr = (zzcr) zzbo.zzakh().remove(this.zzfuc);
        if (zzcr != null) {
            zzcr.zzftz.zzc(zzbo.zzaix(), this.zzejm);
            zzcr.zzfty.zzaky();
            return;
        }
        this.zzejm.trySetResult(Boolean.valueOf(false));
    }

    public final /* bridge */ /* synthetic */ void zzs(@NonNull Status status) {
        super.zzs(status);
    }
}
