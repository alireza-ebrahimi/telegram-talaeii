package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

abstract class zzay implements Runnable {
    private /* synthetic */ zzao zzfxt;

    private zzay(zzao zzao) {
        this.zzfxt = zzao;
    }

    @WorkerThread
    public void run() {
        this.zzfxt.zzfwa.lock();
        try {
            if (!Thread.interrupted()) {
                zzajj();
                this.zzfxt.zzfwa.unlock();
            }
        } catch (RuntimeException e) {
            this.zzfxt.zzfxd.zzb(e);
        } finally {
            this.zzfxt.zzfwa.unlock();
        }
    }

    @WorkerThread
    protected abstract void zzajj();
}
