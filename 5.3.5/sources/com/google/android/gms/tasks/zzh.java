package com.google.android.gms.tasks;

final class zzh implements Runnable {
    private /* synthetic */ Task zzldy;
    private /* synthetic */ zzg zzlee;

    zzh(zzg zzg, Task task) {
        this.zzlee = zzg;
        this.zzldy = task;
    }

    public final void run() {
        synchronized (this.zzlee.mLock) {
            if (this.zzlee.zzled != null) {
                this.zzlee.zzled.onFailure(this.zzldy.getException());
            }
        }
    }
}
