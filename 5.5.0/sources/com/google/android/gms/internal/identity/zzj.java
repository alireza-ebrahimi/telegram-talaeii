package com.google.android.gms.internal.identity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.identity.intents.UserAddressRequest;

public final class zzj extends zza implements zzi {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.identity.intents.internal.IAddressService");
    }

    public final void zza(zzg zzg, UserAddressRequest userAddressRequest, Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzg);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) userAddressRequest);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }
}
