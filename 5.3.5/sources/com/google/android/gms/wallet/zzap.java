package com.google.android.gms.wallet;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzdmv;
import com.google.android.gms.wallet.Wallet.WalletOptions;

final class zzap extends zza<zzdmv, WalletOptions> {
    zzap() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        WalletOptions walletOptions = (WalletOptions) obj;
        if (walletOptions == null) {
            walletOptions = new WalletOptions();
        }
        return new zzdmv(context, looper, zzr, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, walletOptions.theme, walletOptions.zzloc);
    }
}
