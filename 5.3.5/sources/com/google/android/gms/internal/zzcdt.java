package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.identity.intents.UserAddressRequest;

public final class zzcdt extends zzev implements zzcds {
    zzcdt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.identity.intents.internal.IAddressService");
    }

    public final void zza(zzcdq zzcdq, UserAddressRequest userAddressRequest, Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzcdq);
        zzex.zza(zzbc, (Parcelable) userAddressRequest);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(2, zzbc);
    }
}
