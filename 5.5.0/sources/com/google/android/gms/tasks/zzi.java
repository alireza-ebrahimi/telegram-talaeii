package com.google.android.gms.tasks;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzi<TResult> implements zzq<TResult> {
    private final Object mLock = new Object();
    private final Executor zzafk;
    @GuardedBy("mLock")
    private OnCompleteListener<TResult> zzafs;

    public zzi(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.zzafk = executor;
        this.zzafs = onCompleteListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzafs = null;
        }
    }

    public final void onComplete(Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzafs == null) {
                return;
            }
            this.zzafk.execute(new zzj(this, task));
        }
    }
}
