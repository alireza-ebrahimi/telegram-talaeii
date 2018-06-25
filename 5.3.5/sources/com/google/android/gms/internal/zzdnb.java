package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;

final class zzdnb extends zzdmx {
    private final TaskCompletionSource<PaymentData> zzejm;

    public zzdnb(TaskCompletionSource<PaymentData> taskCompletionSource) {
        this.zzejm = taskCompletionSource;
    }

    public final void zza(Status status, PaymentData paymentData, Bundle bundle) {
        AutoResolveHelper.zza(status, (Object) paymentData, this.zzejm);
    }
}
