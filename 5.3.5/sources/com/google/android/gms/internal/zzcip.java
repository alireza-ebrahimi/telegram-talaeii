package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
abstract class zzcip {
    private static volatile Handler handler;
    private boolean enabled = true;
    private volatile long zzhhl;
    private final zzckj zzjev;
    private final Runnable zzjhk;

    zzcip(zzckj zzckj) {
        zzbq.checkNotNull(zzckj);
        this.zzjev = zzckj;
        this.zzjhk = new zzciq(this, zzckj);
    }

    private final Handler getHandler() {
        if (handler != null) {
            return handler;
        }
        Handler handler;
        synchronized (zzcip.class) {
            if (handler == null) {
                handler = new Handler(this.zzjev.getContext().getMainLooper());
            }
            handler = handler;
        }
        return handler;
    }

    public final void cancel() {
        this.zzhhl = 0;
        getHandler().removeCallbacks(this.zzjhk);
    }

    public abstract void run();

    public final boolean zzea() {
        return this.zzhhl != 0;
    }

    public final void zzs(long j) {
        cancel();
        if (j >= 0) {
            this.zzhhl = this.zzjev.zzxx().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzjhk, j)) {
                this.zzjev.zzayp().zzbau().zzj("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }
}
