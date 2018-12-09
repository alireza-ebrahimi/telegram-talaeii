package com.google.android.gms.tasks;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzk<TResult> implements zzq<TResult> {
    private final Object mLock = new Object();
    private final Executor zzafk;
    @GuardedBy("mLock")
    private OnFailureListener zzafu;

    public zzk(Executor executor, OnFailureListener onFailureListener) {
        this.zzafk = executor;
        this.zzafu = onFailureListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzafu = null;
        }
    }

    public final void onComplete(Task<TResult> task) {
        if (!task.isSuccessful() && !task.isCanceled()) {
            synchronized (this.mLock) {
                if (this.zzafu == null) {
                    return;
                }
                this.zzafk.execute(new zzl(this, task));
            }
        }
    }
}
