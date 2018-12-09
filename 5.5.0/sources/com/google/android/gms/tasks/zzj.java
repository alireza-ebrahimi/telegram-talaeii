package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private final /* synthetic */ Task zzafn;
    private final /* synthetic */ zzi zzaft;

    zzj(zzi zzi, Task task) {
        this.zzaft = zzi;
        this.zzafn = task;
    }

    public final void run() {
        synchronized (this.zzaft.mLock) {
            if (this.zzaft.zzafs != null) {
                this.zzaft.zzafs.onComplete(this.zzafn);
            }
        }
    }
}
