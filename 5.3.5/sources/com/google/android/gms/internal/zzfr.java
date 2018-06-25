package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzfr extends zzev implements zzfp {
    zzfr(IBinder iBinder) {
        super(iBinder, AdvertisingInterface.ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
    }

    public final String getId() throws RemoteException {
        Parcel zza = zza(1, zzbc());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final boolean zzb(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, true);
        zzbc = zza(2, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }

    public final boolean zzbn() throws RemoteException {
        Parcel zza = zza(6, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }
}
