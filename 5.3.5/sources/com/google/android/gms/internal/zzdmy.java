package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdf;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdmy extends zzdmx {
    private final TaskCompletionSource<Boolean> zzejm;

    zzdmy(TaskCompletionSource<Boolean> taskCompletionSource) {
        this.zzejm = taskCompletionSource;
    }

    public final void zza(int i, boolean z, Bundle bundle) {
        zzdf.zza(new Status(i), Boolean.valueOf(z), this.zzejm);
    }

    public final void zza(Status status, boolean z, Bundle bundle) {
        zzdf.zza(status, Boolean.valueOf(z), this.zzejm);
    }
}
