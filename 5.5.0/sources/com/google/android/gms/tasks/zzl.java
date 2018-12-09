package com.google.android.gms.tasks;

final class zzl implements Runnable {
    private final /* synthetic */ Task zzafn;
    private final /* synthetic */ zzk zzafv;

    zzl(zzk zzk, Task task) {
        this.zzafv = zzk;
        this.zzafn = task;
    }

    public final void run() {
        synchronized (this.zzafv.mLock) {
            if (this.zzafv.zzafu != null) {
                this.zzafv.zzafu.onFailure(this.zzafn.getException());
            }
        }
    }
}
