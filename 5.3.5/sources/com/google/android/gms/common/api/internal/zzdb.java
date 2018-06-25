package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public final class zzdb extends BasePendingResult<Status> {
    @Deprecated
    public zzdb(Looper looper) {
        super(looper);
    }

    public zzdb(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
