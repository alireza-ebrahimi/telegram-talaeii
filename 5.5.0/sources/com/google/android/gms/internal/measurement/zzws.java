package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

final class zzws extends ContentObserver {
    private final /* synthetic */ zzwr zzbnn;

    zzws(zzwr zzwr, Handler handler) {
        this.zzbnn = zzwr;
        super(null);
    }

    public final void onChange(boolean z) {
        this.zzbnn.zzsd();
        this.zzbnn.zzsf();
    }
}
