package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.WearableStatusCodes;

final class zzgg implements ResultHolder<Status> {
    private final TaskCompletionSource<Boolean> zzes;

    zzgg(TaskCompletionSource<Boolean> taskCompletionSource) {
        this.zzes = taskCompletionSource;
    }

    public final void setFailedResult(Status status) {
        this.zzes.setException(new ApiException(status));
    }

    public final /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        int statusCode = status.getStatusCode();
        if (statusCode == 0) {
            this.zzes.setResult(Boolean.valueOf(true));
        } else if (statusCode == WearableStatusCodes.UNKNOWN_LISTENER) {
            this.zzes.setResult(Boolean.valueOf(false));
        } else {
            setFailedResult(status);
        }
    }
}
