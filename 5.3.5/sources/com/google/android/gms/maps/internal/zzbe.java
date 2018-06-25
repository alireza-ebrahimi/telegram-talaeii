package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.model.internal.zzt;

public abstract class zzbe extends zzew implements zzbd {
    public zzbe() {
        attachInterface(this, "com.google.android.gms.maps.internal.IOnPolygonClickListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza(zzt.zzbn(parcel.readStrongBinder()));
        parcel2.writeNoException();
        return true;
    }
}
