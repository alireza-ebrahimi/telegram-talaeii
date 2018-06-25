package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;

public final class CommonApiImpl implements CommonApi {

    private static class zza extends BaseCommonServiceCallbacks {
        private final ResultHolder<Status> mResultHolder;

        public zza(ResultHolder<Status> resultHolder) {
            this.mResultHolder = resultHolder;
        }

        public final void onDefaultAccountCleared(int i) {
            this.mResultHolder.setResult(new Status(i));
        }
    }

    public final PendingResult<Status> clearDefaultAccount(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzb(this, googleApiClient));
    }
}
