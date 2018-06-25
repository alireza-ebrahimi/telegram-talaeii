package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

public final class zzi implements zzaa {
    private final Executor zzv;

    public zzi(Handler handler) {
        this.zzv = new zzj(this, handler);
    }

    public final void zza(zzr<?> zzr, zzae zzae) {
        zzr.zzb("post-error");
        this.zzv.execute(new zzk(this, zzr, zzx.zzc(zzae), null));
    }

    public final void zza(zzr<?> zzr, zzx<?> zzx, Runnable runnable) {
        zzr.zzj();
        zzr.zzb("post-response");
        this.zzv.execute(new zzk(this, zzr, zzx, runnable));
    }

    public final void zzb(zzr<?> zzr, zzx<?> zzx) {
        zza(zzr, zzx, null);
    }
}
