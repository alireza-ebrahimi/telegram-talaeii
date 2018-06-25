package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.model.internal.zzq;

public abstract class zzag extends zzew implements zzaf {
    public zzag() {
        attachInterface(this, "com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zzf(zzq.zzbm(parcel.readStrongBinder()));
        parcel2.writeNoException();
        return true;
    }
}
