package com.google.android.gms.wallet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.internal.zzdmv;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzaq extends zzde<zzdmv, AutoResolvableVoidResult> {
    private /* synthetic */ CreateWalletObjectsRequest zzlog;

    zzaq(WalletObjectsClient walletObjectsClient, CreateWalletObjectsRequest createWalletObjectsRequest) {
        this.zzlog = createWalletObjectsRequest;
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdmv) zzb).zza(this.zzlog, taskCompletionSource);
    }
}
