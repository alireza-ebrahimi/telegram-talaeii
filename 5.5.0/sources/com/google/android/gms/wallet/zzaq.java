package com.google.android.gms.wallet;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.wallet.zzad;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzaq extends TaskApiCall<zzad, AutoResolvableVoidResult> {
    private final /* synthetic */ CreateWalletObjectsRequest zzet;

    zzaq(WalletObjectsClient walletObjectsClient, CreateWalletObjectsRequest createWalletObjectsRequest) {
        this.zzet = createWalletObjectsRequest;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzad) anyClient).zza(this.zzet, taskCompletionSource);
    }
}
