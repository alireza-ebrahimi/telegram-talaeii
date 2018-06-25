package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zze<TResult> implements zzm<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkou;
    private OnCompleteListener<TResult> zzleb;

    public zze(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzkou = executor;
        this.zzleb = onCompleteListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzleb = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzleb == null) {
                return;
            }
            this.zzkou.execute(new zzf(this, task));
        }
    }
}
