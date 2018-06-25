package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzg<TResult> implements zzm<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkou;
    private OnFailureListener zzled;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzkou = executor;
        this.zzled = onFailureListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzled = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzled == null) {
                    return;
                }
                this.zzkou.execute(new zzh(this, task));
            }
        }
    }
}
