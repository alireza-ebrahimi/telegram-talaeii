package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentDataRequest;

public interface zzq extends IInterface {
    void zza(Bundle bundle, zzu zzu);

    void zza(CreateWalletObjectsRequest createWalletObjectsRequest, Bundle bundle, zzu zzu);

    void zza(FullWalletRequest fullWalletRequest, Bundle bundle, zzu zzu);

    void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzu zzu);

    void zza(MaskedWalletRequest maskedWalletRequest, Bundle bundle, zzu zzu);

    void zza(PaymentDataRequest paymentDataRequest, Bundle bundle, zzu zzu);

    void zza(String str, String str2, Bundle bundle, zzu zzu);
}
