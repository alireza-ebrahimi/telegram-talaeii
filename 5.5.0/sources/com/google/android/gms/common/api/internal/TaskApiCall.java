package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
public abstract class TaskApiCall<A extends AnyClient, ResultT> {
    private final Feature[] zzlz;
    private final boolean zzma;

    @KeepForSdk
    public static class Builder<A extends AnyClient, ResultT> {
        private Feature[] zzlz;
        private boolean zzma;
        private BiConsumer<A, TaskCompletionSource<ResultT>> zzmb;

        private Builder() {
            this.zzma = true;
        }

        @KeepForSdk
        public TaskApiCall<A, ResultT> build() {
            if (this.zzmb != null) {
                return new zzcf(this, this.zzlz, this.zzma);
            }
            throw new IllegalArgumentException("execute parameter required");
        }

        @KeepForSdk
        public Builder<A, ResultT> execute(BiConsumer<A, TaskCompletionSource<ResultT>> biConsumer) {
            this.zzmb = biConsumer;
            return this;
        }

        @KeepForSdk
        public Builder<A, ResultT> setAutoResolveMissingFeatures(boolean z) {
            this.zzma = z;
            return this;
        }

        @KeepForSdk
        public Builder<A, ResultT> setFeatures(Feature[] featureArr) {
            this.zzlz = featureArr;
            return this;
        }
    }

    @KeepForSdk
    @Deprecated
    public TaskApiCall() {
        this.zzlz = null;
        this.zzma = false;
    }

    @KeepForSdk
    private TaskApiCall(Feature[] featureArr, boolean z) {
        this.zzlz = featureArr;
        this.zzma = z;
    }

    @KeepForSdk
    public static <A extends AnyClient, ResultT> Builder<A, ResultT> builder() {
        return new Builder();
    }

    @KeepForSdk
    protected abstract void doExecute(A a, TaskCompletionSource<ResultT> taskCompletionSource);

    @KeepForSdk
    public boolean shouldAutoResolveMissingFeatures() {
        return this.zzma;
    }

    public final Feature[] zzca() {
        return this.zzlz;
    }
}
