package com.google.android.gms.common.api.internal;

final class zzw implements Runnable {
    private /* synthetic */ zzv zzfwc;

    zzw(zzv zzv) {
        this.zzfwc = zzv;
    }

    public final void run() {
        this.zzfwc.zzfwa.lock();
        try {
            this.zzfwc.zzait();
        } finally {
            this.zzfwc.zzfwa.unlock();
        }
    }
}
