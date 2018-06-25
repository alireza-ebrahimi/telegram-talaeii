package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.clearcut.zzc;
import com.google.android.gms.clearcut.zze;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public abstract class zzbfq extends zzew implements zzbfp {
    public zzbfq() {
        attachInterface(this, "com.google.android.gms.clearcut.internal.IClearcutLoggerCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzo((Status) zzex.zza(parcel, Status.CREATOR));
                break;
            case 2:
                zzp((Status) zzex.zza(parcel, Status.CREATOR));
                break;
            case 3:
                zza((Status) zzex.zza(parcel, Status.CREATOR), parcel.readLong());
                break;
            case 4:
                zzq((Status) zzex.zza(parcel, Status.CREATOR));
                break;
            case 5:
                zzb((Status) zzex.zza(parcel, Status.CREATOR), parcel.readLong());
                break;
            case 6:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zze[]) parcel.createTypedArray(zze.CREATOR));
                break;
            case 7:
                zza((DataHolder) zzex.zza(parcel, DataHolder.CREATOR));
                break;
            case 8:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzc) zzex.zza(parcel, zzc.CREATOR));
                break;
            case 9:
                zzb((Status) zzex.zza(parcel, Status.CREATOR), (zzc) zzex.zza(parcel, zzc.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
