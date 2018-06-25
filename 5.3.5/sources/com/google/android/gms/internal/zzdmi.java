package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentDataRequest;

@Hide
public interface zzdmi extends IInterface {
    void zza(Bundle bundle, zzdmm zzdmm) throws RemoteException;

    void zza(CreateWalletObjectsRequest createWalletObjectsRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException;

    void zza(FullWalletRequest fullWalletRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException;

    void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException;

    void zza(MaskedWalletRequest maskedWalletRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException;

    void zza(PaymentDataRequest paymentDataRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException;

    void zza(String str, String str2, Bundle bundle, zzdmm zzdmm) throws RemoteException;
}
