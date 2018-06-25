package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

@Hide
public final class zzo extends zze {
    private /* synthetic */ zzd zzgfk;

    @BinderThread
    public zzo(zzd zzd, @Nullable int i, Bundle bundle) {
        this.zzgfk = zzd;
        super(zzd, i, null);
    }

    protected final boolean zzama() {
        this.zzgfk.zzgew.zzf(ConnectionResult.zzfqt);
        return true;
    }

    protected final void zzj(ConnectionResult connectionResult) {
        this.zzgfk.zzgew.zzf(connectionResult);
        this.zzgfk.onConnectionFailed(connectionResult);
    }
}
