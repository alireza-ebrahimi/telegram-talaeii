package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolvableVoidResult;
import com.google.android.gms.wallet.AutoResolveHelper;

final class zzdmz extends zzdmx {
    private final TaskCompletionSource<AutoResolvableVoidResult> zzjys;

    public zzdmz(TaskCompletionSource<AutoResolvableVoidResult> taskCompletionSource) {
        this.zzjys = taskCompletionSource;
    }

    public final void zzl(int i, Bundle bundle) {
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
        Status status = (pendingIntent == null || i != 6) ? new Status(i) : new Status(i, "Need to resolve PendingIntent", pendingIntent);
        AutoResolveHelper.zza(status, new AutoResolvableVoidResult(), this.zzjys);
    }
}
