package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

final class zzt implements zzbq {
    private final /* synthetic */ zzr zzgc;

    private zzt(zzr zzr) {
        this.zzgc = zzr;
    }

    public final void zzb(int i, boolean z) {
        this.zzgc.zzga.lock();
        try {
            if (this.zzgc.zzfz || this.zzgc.zzfy == null || !this.zzgc.zzfy.isSuccess()) {
                this.zzgc.zzfz = false;
                this.zzgc.zza(i, z);
                return;
            }
            this.zzgc.zzfz = true;
            this.zzgc.zzfs.onConnectionSuspended(i);
            this.zzgc.zzga.unlock();
        } finally {
            this.zzgc.zzga.unlock();
        }
    }

    public final void zzb(Bundle bundle) {
        this.zzgc.zzga.lock();
        try {
            this.zzgc.zza(bundle);
            this.zzgc.zzfx = ConnectionResult.RESULT_SUCCESS;
            this.zzgc.zzaa();
        } finally {
            this.zzgc.zzga.unlock();
        }
    }

    public final void zzc(ConnectionResult connectionResult) {
        this.zzgc.zzga.lock();
        try {
            this.zzgc.zzfx = connectionResult;
            this.zzgc.zzaa();
        } finally {
            this.zzgc.zzga.unlock();
        }
    }
}
