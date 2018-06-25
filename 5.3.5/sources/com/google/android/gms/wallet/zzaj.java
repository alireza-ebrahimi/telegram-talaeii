package com.google.android.gms.wallet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.internal.zzdmv;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzaj extends zzde<zzdmv, PaymentData> {
    private /* synthetic */ PaymentDataRequest zzlnp;

    zzaj(PaymentsClient paymentsClient, PaymentDataRequest paymentDataRequest) {
        this.zzlnp = paymentDataRequest;
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdmv) zzb).zza(this.zzlnp, taskCompletionSource);
    }
}
