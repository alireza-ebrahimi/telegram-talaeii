package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.GoogleApiManager.zza;
import java.util.Collections;

final class zzbn implements Runnable {
    private final /* synthetic */ ConnectionResult zzkl;
    private final /* synthetic */ zzc zzkr;

    zzbn(zzc zzc, ConnectionResult connectionResult) {
        this.zzkr = zzc;
        this.zzkl = connectionResult;
    }

    public final void run() {
        if (this.zzkl.isSuccess()) {
            this.zzkr.zzkq = true;
            if (this.zzkr.zzka.requiresSignIn()) {
                this.zzkr.zzbu();
                return;
            } else {
                this.zzkr.zzka.getRemoteService(null, Collections.emptySet());
                return;
            }
        }
        ((zza) this.zzkr.zzjy.zzju.get(this.zzkr.zzhc)).onConnectionFailed(this.zzkl);
    }
}
