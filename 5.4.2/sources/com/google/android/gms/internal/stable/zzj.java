package com.google.android.gms.internal.stable;

import android.database.ContentObserver;
import android.os.Handler;

final class zzj extends ContentObserver {
    zzj(Handler handler) {
        super(null);
    }

    public final void onChange(boolean z) {
        zzi.zzagy.set(true);
    }
}
