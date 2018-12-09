package com.google.android.gms.internal.stable;

import android.database.ContentObserver;
import android.os.Handler;

final class zzf extends ContentObserver {
    private final /* synthetic */ zzh zzagr;

    zzf(Handler handler, zzh zzh) {
        this.zzagr = zzh;
        super(null);
    }

    public final void onChange(boolean z) {
        this.zzagr.zzagu.set(true);
    }
}
