package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentDataRequest;

public final class zzdmj extends zzev implements zzdmi {
    zzdmj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IOwService");
    }

    public final void zza(Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(5, zzbc);
    }

    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) createWalletObjectsRequest);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(6, zzbc);
    }

    public final void zza(FullWalletRequest fullWalletRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) fullWalletRequest);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(2, zzbc);
    }

    public final void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) isReadyToPayRequest);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(14, zzbc);
    }

    public final void zza(MaskedWalletRequest maskedWalletRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) maskedWalletRequest);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(1, zzbc);
    }

    public final void zza(PaymentDataRequest paymentDataRequest, Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) paymentDataRequest);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(19, zzbc);
    }

    public final void zza(String str, String str2, Bundle bundle, zzdmm zzdmm) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzex.zza(zzbc, (IInterface) zzdmm);
        zzc(3, zzbc);
    }
}
