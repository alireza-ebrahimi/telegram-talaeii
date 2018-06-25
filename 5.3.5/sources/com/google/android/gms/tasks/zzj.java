package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private /* synthetic */ Task zzldy;
    private /* synthetic */ zzi zzleg;

    zzj(zzi zzi, Task task) {
        this.zzleg = zzi;
        this.zzldy = task;
    }

    public final void run() {
        synchronized (this.zzleg.mLock) {
            if (this.zzleg.zzlef != null) {
                this.zzleg.zzlef.onSuccess(this.zzldy.getResult());
            }
        }
    }
}
