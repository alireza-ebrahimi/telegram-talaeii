package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.model.internal.zze;

public abstract class zzw extends zzew implements zzv {
    public zzw() {
        attachInterface(this, "com.google.android.gms.maps.internal.IOnCircleClickListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza(zze.zzbi(parcel.readStrongBinder()));
        parcel2.writeNoException();
        return true;
    }
}
