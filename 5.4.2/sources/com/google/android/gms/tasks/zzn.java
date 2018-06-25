package com.google.android.gms.tasks;

final class zzn implements Runnable {
    private final /* synthetic */ Task zzafn;
    private final /* synthetic */ zzm zzafx;

    zzn(zzm zzm, Task task) {
        this.zzafx = zzm;
        this.zzafn = task;
    }

    public final void run() {
        synchronized (this.zzafx.mLock) {
            if (this.zzafx.zzafw != null) {
                this.zzafx.zzafw.onSuccess(this.zzafn.getResult());
            }
        }
    }
}
