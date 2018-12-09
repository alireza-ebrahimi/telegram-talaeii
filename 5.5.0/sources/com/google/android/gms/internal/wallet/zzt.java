package com.google.android.gms.internal.wallet;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzt extends zza implements zzs {
    zzt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
    }

    public final zzl zza(IObjectWrapper iObjectWrapper, IFragmentWrapper iFragmentWrapper, WalletFragmentOptions walletFragmentOptions, zzo zzo) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iFragmentWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) walletFragmentOptions);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzo);
        obtainAndWriteInterfaceToken = transactAndReadException(1, obtainAndWriteInterfaceToken);
        zzl zza = zzm.zza(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return zza;
    }
}
