package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.Payments;

@Hide
@SuppressLint({"MissingRemoteException"})
public final class zzdmo implements Payments {
    public final void changeMaskedWallet(GoogleApiClient googleApiClient, String str, String str2, int i) {
        googleApiClient.zzd(new zzdms(this, googleApiClient, str, str2, i));
    }

    public final void checkForPreAuthorization(GoogleApiClient googleApiClient, int i) {
        googleApiClient.zzd(new zzdmp(this, googleApiClient, i));
    }

    public final PendingResult<BooleanResult> isReadyToPay(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzdmt(this, googleApiClient));
    }

    public final PendingResult<BooleanResult> isReadyToPay(GoogleApiClient googleApiClient, IsReadyToPayRequest isReadyToPayRequest) {
        return googleApiClient.zzd(new zzdmu(this, googleApiClient, isReadyToPayRequest));
    }

    public final void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int i) {
        googleApiClient.zzd(new zzdmr(this, googleApiClient, fullWalletRequest, i));
    }

    public final void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int i) {
        googleApiClient.zzd(new zzdmq(this, googleApiClient, maskedWalletRequest, i));
    }
}
