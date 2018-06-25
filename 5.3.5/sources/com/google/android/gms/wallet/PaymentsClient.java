package com.google.android.gms.wallet;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.Wallet.WalletOptions;

public class PaymentsClient extends GoogleApi<WalletOptions> {
    @Hide
    PaymentsClient(@NonNull Activity activity, @NonNull WalletOptions walletOptions) {
        super(activity, Wallet.API, (ApiOptions) walletOptions, zza.zzfsr);
    }

    @Hide
    PaymentsClient(@NonNull Context context, @NonNull WalletOptions walletOptions) {
        super(context, Wallet.API, (ApiOptions) walletOptions, zza.zzfsr);
    }

    public Task<Boolean> isReadyToPay(@NonNull IsReadyToPayRequest isReadyToPayRequest) {
        return zza(new zzai(this, isReadyToPayRequest));
    }

    public Task<PaymentData> loadPaymentData(@NonNull PaymentDataRequest paymentDataRequest) {
        return zzb(new zzaj(this, paymentDataRequest));
    }
}
