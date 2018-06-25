package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzk;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzdml extends zzev implements zzdmk {
    zzdml(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
    }

    public final zzdmd zza(IObjectWrapper iObjectWrapper, zzk zzk, WalletFragmentOptions walletFragmentOptions, zzdmg zzdmg) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (IInterface) zzk);
        zzex.zza(zzbc, (Parcelable) walletFragmentOptions);
        zzex.zza(zzbc, (IInterface) zzdmg);
        zzbc = zza(1, zzbc);
        zzdmd zzbs = zzdme.zzbs(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbs;
    }
}
