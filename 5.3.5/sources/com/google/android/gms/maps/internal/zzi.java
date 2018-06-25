package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.maps.model.internal.zzq;

public abstract class zzi extends zzew implements zzh {
    public zzi() {
        attachInterface(this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        IInterface zzh;
        switch (i) {
            case 1:
                zzh = zzh(zzq.zzbm(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzex.zza(parcel2, zzh);
                return true;
            case 2:
                zzh = zzi(zzq.zzbm(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzex.zza(parcel2, zzh);
                return true;
            default:
                return false;
        }
    }
}
