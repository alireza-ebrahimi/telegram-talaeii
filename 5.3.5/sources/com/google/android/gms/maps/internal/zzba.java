package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;

public abstract class zzba extends zzew implements zzaz {
    public zzba() {
        attachInterface(this, "com.google.android.gms.maps.internal.IOnMyLocationClickListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        onMyLocationClick((Location) zzex.zza(parcel, Location.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
