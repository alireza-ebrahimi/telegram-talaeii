package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zza<TResult, TContinuationResult> implements zzm<TResult> {
    private final Executor zzkou;
    private final Continuation<TResult, TContinuationResult> zzldw;
    private final zzp<TContinuationResult> zzldx;

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzp<TContinuationResult> zzp) {
        this.zzkou = executor;
        this.zzldw = continuation;
        this.zzldx = zzp;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzkou.execute(new zzb(this, task));
    }
}
