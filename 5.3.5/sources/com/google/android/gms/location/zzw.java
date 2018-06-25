package com.google.android.gms.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzw extends zzev implements zzu {
    zzw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.ILocationCallback");
    }

    public final void onLocationAvailability(LocationAvailability locationAvailability) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) locationAvailability);
        zzc(2, zzbc);
    }

    public final void onLocationResult(LocationResult locationResult) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) locationResult);
        zzc(1, zzbc);
    }
}
