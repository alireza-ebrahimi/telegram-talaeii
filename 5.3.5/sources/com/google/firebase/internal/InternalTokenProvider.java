package com.google.firebase.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.GetTokenResult;

@KeepForSdk
public interface InternalTokenProvider {
    @Nullable
    String getUid();

    Task<GetTokenResult> zzcj(boolean z);
}
