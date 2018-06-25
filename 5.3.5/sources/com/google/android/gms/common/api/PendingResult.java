package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import java.util.concurrent.TimeUnit;

public abstract class PendingResult<R extends Result> {

    @Hide
    public interface zza {
        @Hide
        void zzr(Status status);
    }

    @NonNull
    public abstract R await();

    @NonNull
    public abstract R await(long j, @NonNull TimeUnit timeUnit);

    public abstract void cancel();

    public abstract boolean isCanceled();

    public abstract void setResultCallback(@NonNull ResultCallback<? super R> resultCallback);

    public abstract void setResultCallback(@NonNull ResultCallback<? super R> resultCallback, long j, @NonNull TimeUnit timeUnit);

    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zza(@NonNull zza zza) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Hide
    public Integer zzaid() {
        throw new UnsupportedOperationException();
    }
}
