package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;

final class zzaj extends zzaf {
    private final TaskCompletionSource<PaymentData> zzgj;

    public zzaj(TaskCompletionSource<PaymentData> taskCompletionSource) {
        this.zzgj = taskCompletionSource;
    }

    public final void zza(Status status, PaymentData paymentData, Bundle bundle) {
        AutoResolveHelper.zza(status, (Object) paymentData, this.zzgj);
    }
}
