package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzk<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzm<TResult> {
    private final Executor zzkou;
    private final zzp<TContinuationResult> zzldx;
    private final SuccessContinuation<TResult, TContinuationResult> zzleh;

    public zzk(@NonNull Executor executor, @NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation, @NonNull zzp<TContinuationResult> zzp) {
        this.zzkou = executor;
        this.zzleh = successContinuation;
        this.zzldx = zzp;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzkou.execute(new zzl(this, task));
    }

    public final void onFailure(@NonNull Exception exception) {
        this.zzldx.setException(exception);
    }

    public final void onSuccess(TContinuationResult tContinuationResult) {
        this.zzldx.setResult(tContinuationResult);
    }
}
