package com.google.android.gms.internal.phenotype;

import android.database.ContentObserver;
import android.os.Handler;

final class zzg extends ContentObserver {
    zzg(Handler handler) {
        super(null);
    }

    public final void onChange(boolean z) {
        zzf.zzbh.set(true);
    }
}
