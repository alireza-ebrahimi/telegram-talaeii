package com.google.android.gms.internal.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzn extends zza implements zzl {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
    }

    public final int getState() {
        Parcel transactAndReadException = transactAndReadException(13, obtainAndWriteInterfaceToken());
        int readInt = transactAndReadException.readInt();
        transactAndReadException.recycle();
        return readInt;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) walletFragmentInitParams);
        transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken);
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeInt(i);
        obtainAndWriteInterfaceToken.writeInt(i2);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) intent);
        transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken);
    }

    public final void onCreate(Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper2);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        obtainAndWriteInterfaceToken = transactAndReadException(3, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final void onPause() {
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken());
    }

    public final void onResume() {
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken());
    }

    public final void onSaveInstanceState(Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        obtainAndWriteInterfaceToken = transactAndReadException(8, obtainAndWriteInterfaceToken);
        if (obtainAndWriteInterfaceToken.readInt() != 0) {
            bundle.readFromParcel(obtainAndWriteInterfaceToken);
        }
        obtainAndWriteInterfaceToken.recycle();
    }

    public final void onStart() {
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken());
    }

    public final void onStop() {
        transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken());
    }

    public final void setEnabled(boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) maskedWallet);
        transactAndReadExceptionReturnVoid(14, obtainAndWriteInterfaceToken);
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) maskedWalletRequest);
        transactAndReadExceptionReturnVoid(11, obtainAndWriteInterfaceToken);
    }

    public final void zza(IObjectWrapper iObjectWrapper, WalletFragmentOptions walletFragmentOptions, Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) walletFragmentOptions);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }
}
