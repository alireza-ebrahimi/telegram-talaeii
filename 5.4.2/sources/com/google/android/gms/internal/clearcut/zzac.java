package com.google.android.gms.internal.clearcut;

import android.database.ContentObserver;
import android.os.Handler;

final class zzac extends ContentObserver {
    private final /* synthetic */ zzab zzdm;

    zzac(zzab zzab, Handler handler) {
        this.zzdm = zzab;
        super(null);
    }

    public final void onChange(boolean z) {
        this.zzdm.zzh();
        this.zzdm.zzj();
    }
}
