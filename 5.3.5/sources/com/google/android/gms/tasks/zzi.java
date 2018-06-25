package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzi<TResult> implements zzm<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkou;
    private OnSuccessListener<? super TResult> zzlef;

    public zzi(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzkou = executor;
        this.zzlef = onSuccessListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzlef = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzlef == null) {
                    return;
                }
                this.zzkou.execute(new zzj(this, task));
            }
        }
    }
}
