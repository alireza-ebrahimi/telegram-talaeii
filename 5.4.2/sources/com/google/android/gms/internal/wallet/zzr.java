package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentDataRequest;

public final class zzr extends zza implements zzq {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IOwService");
    }

    public final void zza(Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(5, obtainAndWriteInterfaceToken);
    }

    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) createWalletObjectsRequest);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(6, obtainAndWriteInterfaceToken);
    }

    public final void zza(FullWalletRequest fullWalletRequest, Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) fullWalletRequest);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(2, obtainAndWriteInterfaceToken);
    }

    public final void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) isReadyToPayRequest);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(14, obtainAndWriteInterfaceToken);
    }

    public final void zza(MaskedWalletRequest maskedWalletRequest, Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) maskedWalletRequest);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(1, obtainAndWriteInterfaceToken);
    }

    public final void zza(PaymentDataRequest paymentDataRequest, Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) paymentDataRequest);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(19, obtainAndWriteInterfaceToken);
    }

    public final void zza(String str, String str2, Bundle bundle, zzu zzu) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzu);
        transactOneway(3, obtainAndWriteInterfaceToken);
    }
}
