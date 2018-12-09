package com.google.android.gms.tasks;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzm<TResult> implements zzq<TResult> {
    private final Object mLock = new Object();
    private final Executor zzafk;
    @GuardedBy("mLock")
    private OnSuccessListener<? super TResult> zzafw;

    public zzm(Executor executor, OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzafk = executor;
        this.zzafw = onSuccessListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzafw = null;
        }
    }

    public final void onComplete(Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzafw == null) {
                    return;
                }
                this.zzafk.execute(new zzn(this, task));
            }
        }
    }
}
