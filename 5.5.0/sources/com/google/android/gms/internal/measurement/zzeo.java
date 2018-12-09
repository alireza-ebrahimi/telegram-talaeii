package com.google.android.gms.internal.measurement;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;

abstract class zzeo {
    private static volatile Handler handler;
    private final zzhj zzafk;
    private final Runnable zzyd;
    private volatile long zzye;

    zzeo(zzhj zzhj) {
        Preconditions.checkNotNull(zzhj);
        this.zzafk = zzhj;
        this.zzyd = new zzep(this, zzhj);
    }

    private final Handler getHandler() {
        if (handler != null) {
            return handler;
        }
        Handler handler;
        synchronized (zzeo.class) {
            if (handler == null) {
                handler = new Handler(this.zzafk.getContext().getMainLooper());
            }
            handler = handler;
        }
        return handler;
    }

    final void cancel() {
        this.zzye = 0;
        getHandler().removeCallbacks(this.zzyd);
    }

    public abstract void run();

    public final boolean zzef() {
        return this.zzye != 0;
    }

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzye = this.zzafk.zzbt().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzyd, j)) {
                this.zzafk.zzgf().zzis().zzg("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }
}
