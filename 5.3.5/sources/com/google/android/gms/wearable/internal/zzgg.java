package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.WearableStatusCodes;

final class zzgg implements zzn<Status> {
    private TaskCompletionSource<Boolean> zzlvi;

    zzgg(TaskCompletionSource<Boolean> taskCompletionSource) {
        this.zzlvi = taskCompletionSource;
    }

    public final /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        int statusCode = status.getStatusCode();
        if (statusCode == 0) {
            this.zzlvi.setResult(Boolean.valueOf(true));
        } else if (statusCode == WearableStatusCodes.UNKNOWN_LISTENER) {
            this.zzlvi.setResult(Boolean.valueOf(false));
        } else {
            zzu(status);
        }
    }

    public final void zzu(Status status) {
        this.zzlvi.setException(new ApiException(status));
    }
}
