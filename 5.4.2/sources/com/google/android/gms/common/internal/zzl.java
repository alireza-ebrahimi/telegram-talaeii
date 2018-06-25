package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.StatusListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.common.internal.PendingResultUtil.StatusConverter;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zzl implements StatusListener {
    private final /* synthetic */ PendingResult zzuo;
    private final /* synthetic */ TaskCompletionSource zzup;
    private final /* synthetic */ ResultConverter zzuq;
    private final /* synthetic */ StatusConverter zzur;

    zzl(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, ResultConverter resultConverter, StatusConverter statusConverter) {
        this.zzuo = pendingResult;
        this.zzup = taskCompletionSource;
        this.zzuq = resultConverter;
        this.zzur = statusConverter;
    }

    public final void onComplete(Status status) {
        if (status.isSuccess()) {
            this.zzup.setResult(this.zzuq.convert(this.zzuo.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zzup.setException(this.zzur.convert(status));
    }
}
