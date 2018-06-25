package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcf extends TaskApiCall<A, ResultT> {
    private final /* synthetic */ Builder zzmc;

    zzcf(Builder builder, Feature[] featureArr, boolean z) {
        this.zzmc = builder;
        super(featureArr, z);
    }

    protected final void doExecute(A a, TaskCompletionSource<ResultT> taskCompletionSource) {
        this.zzmc.zzmb.accept(a, taskCompletionSource);
    }
}
