package com.google.android.gms.wallet;

import android.app.Activity;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.Wallet.WalletOptions;

public class WalletObjectsClient extends GoogleApi<WalletOptions> {
    WalletObjectsClient(Activity activity, WalletOptions walletOptions) {
        super(activity, Wallet.API, (ApiOptions) walletOptions, Settings.DEFAULT_SETTINGS);
    }

    public Task<AutoResolvableVoidResult> createWalletObjects(CreateWalletObjectsRequest createWalletObjectsRequest) {
        return doWrite(new zzaq(this, createWalletObjectsRequest));
    }
}
