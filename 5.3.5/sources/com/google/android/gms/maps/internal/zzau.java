package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.model.internal.zzq;

public abstract class zzau extends zzew implements zzat {
    public zzau() {
        attachInterface(this, "com.google.android.gms.maps.internal.IOnMarkerDragListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzb(zzq.zzbm(parcel.readStrongBinder()));
                break;
            case 2:
                zzd(zzq.zzbm(parcel.readStrongBinder()));
                break;
            case 3:
                zzc(zzq.zzbm(parcel.readStrongBinder()));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
