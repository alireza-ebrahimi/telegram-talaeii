package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzdmf extends zzev implements zzdmd {
    zzdmf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
    }

    public final int getState() throws RemoteException {
        Parcel zza = zza(13, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) walletFragmentInitParams);
        zzb(10, zzbc);
    }

    public final void onActivityResult(int i, int i2, Intent intent) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzbc.writeInt(i2);
        zzex.zza(zzbc, (Parcelable) intent);
        zzb(9, zzbc);
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(2, zzbc);
    }

    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (IInterface) iObjectWrapper2);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzbc = zza(3, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final void onPause() throws RemoteException {
        zzb(6, zzbc());
    }

    public final void onResume() throws RemoteException {
        zzb(5, zzbc());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzbc = zza(8, zzbc);
        if (zzbc.readInt() != 0) {
            bundle.readFromParcel(zzbc);
        }
        zzbc.recycle();
    }

    public final void onStart() throws RemoteException {
        zzb(4, zzbc());
    }

    public final void onStop() throws RemoteException {
        zzb(7, zzbc());
    }

    public final void setEnabled(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(12, zzbc);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) maskedWallet);
        zzb(14, zzbc);
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) maskedWalletRequest);
        zzb(11, zzbc);
    }

    public final void zza(IObjectWrapper iObjectWrapper, WalletFragmentOptions walletFragmentOptions, Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (Parcelable) walletFragmentOptions);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(1, zzbc);
    }
}
