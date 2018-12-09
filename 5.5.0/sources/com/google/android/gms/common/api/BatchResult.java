package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {
    private final Status mStatus;
    private final PendingResult<?>[] zzcg;

    BatchResult(Status status, PendingResult<?>[] pendingResultArr) {
        this.mStatus = status;
        this.zzcg = pendingResultArr;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        Preconditions.checkArgument(batchResultToken.mId < this.zzcg.length, "The result token does not belong to this batch");
        return this.zzcg[batchResultToken.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
