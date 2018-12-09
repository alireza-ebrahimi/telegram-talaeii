package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

final class zzo<TResult, TContinuationResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TContinuationResult>, zzq<TResult> {
    private final Executor zzafk;
    private final zzu<TContinuationResult> zzafm;
    private final SuccessContinuation<TResult, TContinuationResult> zzafy;

    public zzo(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation, zzu<TContinuationResult> zzu) {
        this.zzafk = executor;
        this.zzafy = successContinuation;
        this.zzafm = zzu;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onCanceled() {
        this.zzafm.zzdp();
    }

    public final void onComplete(Task<TResult> task) {
        this.zzafk.execute(new zzp(this, task));
    }

    public final void onFailure(Exception exception) {
        this.zzafm.setException(exception);
    }

    public final void onSuccess(TContinuationResult tContinuationResult) {
        this.zzafm.setResult(tContinuationResult);
    }
}
