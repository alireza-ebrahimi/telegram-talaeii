package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;

public abstract class zzen extends zzew implements zzem {
    public zzen() {
        attachInterface(this, "com.google.android.gms.wearable.internal.IWearableListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzbo((DataHolder) zzex.zza(parcel, DataHolder.CREATOR));
                break;
            case 2:
                zza((zzfe) zzex.zza(parcel, zzfe.CREATOR));
                break;
            case 3:
                zza((zzfo) zzex.zza(parcel, zzfo.CREATOR));
                break;
            case 4:
                zzb((zzfo) zzex.zza(parcel, zzfo.CREATOR));
                break;
            case 5:
                onConnectedNodes(parcel.createTypedArrayList(zzfo.CREATOR));
                break;
            case 6:
                zza((zzl) zzex.zza(parcel, zzl.CREATOR));
                break;
            case 7:
                zza((zzaw) zzex.zza(parcel, zzaw.CREATOR));
                break;
            case 8:
                zza((zzah) zzex.zza(parcel, zzah.CREATOR));
                break;
            case 9:
                zza((zzi) zzex.zza(parcel, zzi.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
