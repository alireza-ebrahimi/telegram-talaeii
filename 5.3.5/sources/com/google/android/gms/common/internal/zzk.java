package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

@Hide
public final class zzk extends zzax {
    private zzd zzgfm;
    private final int zzgfn;

    public zzk(@NonNull zzd zzd, int i) {
        this.zzgfm = zzd;
        this.zzgfn = i;
    }

    @BinderThread
    public final void zza(int i, @Nullable Bundle bundle) {
        Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
    }

    @BinderThread
    public final void zza(int i, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
        zzbq.checkNotNull(this.zzgfm, "onPostInitComplete can be called only once per call to getRemoteService");
        this.zzgfm.zza(i, iBinder, bundle, this.zzgfn);
        this.zzgfm = null;
    }
}
