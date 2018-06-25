package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzy implements zzcd {
    private /* synthetic */ zzv zzfwc;

    private zzy(zzv zzv) {
        this.zzfwc = zzv;
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        this.zzfwc.zzfwa.lock();
        try {
            this.zzfwc.zzfvy = connectionResult;
            this.zzfwc.zzait();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }

    public final void zzf(int i, boolean z) {
        this.zzfwc.zzfwa.lock();
        try {
            if (this.zzfwc.zzfvz) {
                this.zzfwc.zzfvz = false;
                this.zzfwc.zze(i, z);
                return;
            }
            this.zzfwc.zzfvz = true;
            this.zzfwc.zzfvr.onConnectionSuspended(i);
            this.zzfwc.zzfwa.unlock();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }

    public final void zzk(@Nullable Bundle bundle) {
        this.zzfwc.zzfwa.lock();
        try {
            this.zzfwc.zzfvy = ConnectionResult.zzfqt;
            this.zzfwc.zzait();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }
}
