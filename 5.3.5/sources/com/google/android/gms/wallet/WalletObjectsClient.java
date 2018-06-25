package com.google.android.gms.wallet;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.Wallet.WalletOptions;

public class WalletObjectsClient extends GoogleApi<WalletOptions> {
    @Hide
    WalletObjectsClient(@NonNull Activity activity, @Nullable WalletOptions walletOptions) {
        super(activity, Wallet.API, (ApiOptions) walletOptions, zza.zzfsr);
    }

    public Task<AutoResolvableVoidResult> createWalletObjects(@NonNull CreateWalletObjectsRequest createWalletObjectsRequest) {
        return zzb(new zzaq(this, createWalletObjectsRequest));
    }
}
