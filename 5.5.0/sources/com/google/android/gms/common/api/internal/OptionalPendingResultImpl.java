package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.StatusListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import java.util.concurrent.TimeUnit;

@KeepForSdk
public final class OptionalPendingResultImpl<R extends Result> extends OptionalPendingResult<R> {
    private final BasePendingResult<R> zzlo;

    public OptionalPendingResultImpl(PendingResult<R> pendingResult) {
        this.zzlo = (BasePendingResult) pendingResult;
    }

    public final void addStatusListener(StatusListener statusListener) {
        this.zzlo.addStatusListener(statusListener);
    }

    public final R await() {
        return this.zzlo.await();
    }

    public final R await(long j, TimeUnit timeUnit) {
        return this.zzlo.await(j, timeUnit);
    }

    public final void cancel() {
        this.zzlo.cancel();
    }

    public final R get() {
        if (isDone()) {
            return await(0, TimeUnit.MILLISECONDS);
        }
        throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
    }

    public final boolean isCanceled() {
        return this.zzlo.isCanceled();
    }

    public final boolean isDone() {
        return this.zzlo.isReady();
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        this.zzlo.setResultCallback(resultCallback);
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j, TimeUnit timeUnit) {
        this.zzlo.setResultCallback(resultCallback, j, timeUnit);
    }

    public final <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        return this.zzlo.then(resultTransform);
    }

    public final Integer zzo() {
        return this.zzlo.zzo();
    }
}
