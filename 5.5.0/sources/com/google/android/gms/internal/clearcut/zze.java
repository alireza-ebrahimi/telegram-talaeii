package com.google.android.gms.internal.clearcut;

import android.content.Context;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.zzb;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.util.VisibleForTesting;

public final class zze extends GoogleApi<NoOptions> implements zzb {
    @VisibleForTesting
    private zze(Context context) {
        super(context, ClearcutLogger.API, null, new ApiExceptionMapper());
    }

    public static zzb zzb(Context context) {
        return new zze(context);
    }

    public final PendingResult<Status> zzb(com.google.android.gms.clearcut.zze zze) {
        return doBestEffortWrite(new zzh(zze, asGoogleApiClient()));
    }
}
