package com.google.android.gms.internal.clearcut;

import android.os.Parcel;
import com.google.android.gms.clearcut.zzc;
import com.google.android.gms.clearcut.zze;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public abstract class zzm extends zzb implements zzl {
    public zzm() {
        super("com.google.android.gms.clearcut.internal.IClearcutLoggerCallbacks");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zza((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 2:
                zzb((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 3:
                zza((Status) zzc.zza(parcel, Status.CREATOR), parcel.readLong());
                break;
            case 4:
                zzc((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 5:
                zzb((Status) zzc.zza(parcel, Status.CREATOR), parcel.readLong());
                break;
            case 6:
                zza((Status) zzc.zza(parcel, Status.CREATOR), (zze[]) parcel.createTypedArray(zze.CREATOR));
                break;
            case 7:
                zza((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8:
                zza((Status) zzc.zza(parcel, Status.CREATOR), (zzc) zzc.zza(parcel, zzc.CREATOR));
                break;
            case 9:
                zzb((Status) zzc.zza(parcel, Status.CREATOR), (zzc) zzc.zza(parcel, zzc.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
