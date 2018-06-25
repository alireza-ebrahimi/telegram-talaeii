package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzx implements zzcd {
    private /* synthetic */ zzv zzfwc;

    private zzx(zzv zzv) {
        this.zzfwc = zzv;
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        this.zzfwc.zzfwa.lock();
        try {
            this.zzfwc.zzfvx = connectionResult;
            this.zzfwc.zzait();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }

    public final void zzf(int i, boolean z) {
        this.zzfwc.zzfwa.lock();
        try {
            if (this.zzfwc.zzfvz || this.zzfwc.zzfvy == null || !this.zzfwc.zzfvy.isSuccess()) {
                this.zzfwc.zzfvz = false;
                this.zzfwc.zze(i, z);
                return;
            }
            this.zzfwc.zzfvz = true;
            this.zzfwc.zzfvs.onConnectionSuspended(i);
            this.zzfwc.zzfwa.unlock();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }

    public final void zzk(@Nullable Bundle bundle) {
        this.zzfwc.zzfwa.lock();
        try {
            this.zzfwc.zzj(bundle);
            this.zzfwc.zzfvx = ConnectionResult.zzfqt;
            this.zzfwc.zzait();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }
}
