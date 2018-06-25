package com.google.android.gms.phenotype;

import android.database.ContentObserver;
import android.os.Handler;

final class zzb extends ContentObserver {
    private /* synthetic */ zza zzkfy;

    zzb(zza zza, Handler handler) {
        this.zzkfy = zza;
        super(null);
    }

    public final void onChange(boolean z) {
        this.zzkfy.zzbef();
    }
}
