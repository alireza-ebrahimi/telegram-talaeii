package com.google.android.gms.wallet;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.Wallet.WalletOptions;

public class PaymentsClient extends GoogleApi<WalletOptions> {
    PaymentsClient(Activity activity, WalletOptions walletOptions) {
        super(activity, Wallet.API, (ApiOptions) walletOptions, Settings.DEFAULT_SETTINGS);
    }

    PaymentsClient(Context context, WalletOptions walletOptions) {
        super(context, Wallet.API, (ApiOptions) walletOptions, Settings.DEFAULT_SETTINGS);
    }

    public Task<Boolean> isReadyToPay(IsReadyToPayRequest isReadyToPayRequest) {
        return doRead(new zzai(this, isReadyToPayRequest));
    }

    public Task<PaymentData> loadPaymentData(PaymentDataRequest paymentDataRequest) {
        return doWrite(new zzaj(this, paymentDataRequest));
    }
}
