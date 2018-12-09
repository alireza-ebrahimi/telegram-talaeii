package com.google.android.gms.wallet;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.wallet.zzad;
import com.google.android.gms.wallet.Wallet.WalletOptions;

final class zzap extends AbstractClientBuilder<zzad, WalletOptions> {
    zzap() {
    }

    public final /* synthetic */ Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        WalletOptions walletOptions = (WalletOptions) obj;
        if (walletOptions == null) {
            walletOptions = new WalletOptions();
        }
        return new zzad(context, looper, clientSettings, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, walletOptions.theme, walletOptions.zzer);
    }
}
