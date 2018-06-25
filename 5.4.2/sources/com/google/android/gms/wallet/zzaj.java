package com.google.android.gms.wallet;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.wallet.zzad;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzaj extends TaskApiCall<zzad, PaymentData> {
    private final /* synthetic */ PaymentDataRequest zzeg;

    zzaj(PaymentsClient paymentsClient, PaymentDataRequest paymentDataRequest) {
        this.zzeg = paymentDataRequest;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzad) anyClient).zza(this.zzeg, taskCompletionSource);
    }
}
