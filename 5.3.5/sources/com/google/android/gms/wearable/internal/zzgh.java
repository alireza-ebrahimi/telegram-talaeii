package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.WearableStatusCodes;

final class zzgh implements zzn<Status> {
    private TaskCompletionSource<Void> zzlvi;

    zzgh(TaskCompletionSource<Void> taskCompletionSource) {
        this.zzlvi = taskCompletionSource;
    }

    public final /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        int statusCode = status.getStatusCode();
        if (statusCode == 0 || statusCode == WearableStatusCodes.DUPLICATE_LISTENER) {
            this.zzlvi.setResult(null);
        } else {
            zzu(status);
        }
    }

    public final void zzu(Status status) {
        this.zzlvi.setException(new ApiException(status));
    }
}
