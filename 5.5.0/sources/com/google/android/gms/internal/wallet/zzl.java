package com.google.android.gms.internal.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public interface zzl extends IInterface {
    int getState();

    void initialize(WalletFragmentInitParams walletFragmentInitParams);

    void onActivityResult(int i, int i2, Intent intent);

    void onCreate(Bundle bundle);

    IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle);

    void onPause();

    void onResume();

    void onSaveInstanceState(Bundle bundle);

    void onStart();

    void onStop();

    void setEnabled(boolean z);

    void updateMaskedWallet(MaskedWallet maskedWallet);

    void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest);

    void zza(IObjectWrapper iObjectWrapper, WalletFragmentOptions walletFragmentOptions, Bundle bundle);
}
