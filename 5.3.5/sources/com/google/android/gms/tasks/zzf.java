package com.google.android.gms.tasks;

final class zzf implements Runnable {
    private /* synthetic */ Task zzldy;
    private /* synthetic */ zze zzlec;

    zzf(zze zze, Task task) {
        this.zzlec = zze;
        this.zzldy = task;
    }

    public final void run() {
        synchronized (this.zzlec.mLock) {
            if (this.zzlec.zzleb != null) {
                this.zzlec.zzleb.onComplete(this.zzldy);
            }
        }
    }
}
