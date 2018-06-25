package com.google.android.gms.wallet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.internal.zzdmv;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzai extends zzde<zzdmv, Boolean> {
    private /* synthetic */ IsReadyToPayRequest zzlno;

    zzai(PaymentsClient paymentsClient, IsReadyToPayRequest isReadyToPayRequest) {
        this.zzlno = isReadyToPayRequest;
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdmv) zzb).zza(this.zzlno, taskCompletionSource);
    }
}
