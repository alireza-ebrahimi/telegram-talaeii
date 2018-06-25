package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzd extends zzb<Void> {
    private zzcq<zzb, ?> zzfty;
    private zzdo<zzb, ?> zzftz;

    public zzd(zzcr zzcr, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zzfty = zzcr.zzfty;
        this.zzftz = zzcr.zzftz;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzae zzae, boolean z) {
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull RuntimeException runtimeException) {
        super.zza(runtimeException);
    }

    public final void zzb(zzbo<?> zzbo) throws RemoteException {
        this.zzfty.zzb(zzbo.zzaix(), this.zzejm);
        if (this.zzfty.zzakx() != null) {
            zzbo.zzakh().put(this.zzfty.zzakx(), new zzcr(this.zzfty, this.zzftz));
        }
    }

    public final /* bridge */ /* synthetic */ void zzs(@NonNull Status status) {
        super.zzs(status);
    }
}
