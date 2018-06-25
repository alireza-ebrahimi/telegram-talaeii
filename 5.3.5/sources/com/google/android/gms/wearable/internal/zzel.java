package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;

public abstract class zzel extends zzew implements zzek {
    public zzel() {
        attachInterface(this, "com.google.android.gms.wearable.internal.IWearableCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 2:
                zza((zzdw) zzex.zza(parcel, zzdw.CREATOR));
                break;
            case 3:
                zza((zzfu) zzex.zza(parcel, zzfu.CREATOR));
                break;
            case 4:
                zza((zzec) zzex.zza(parcel, zzec.CREATOR));
                break;
            case 5:
                zzbp((DataHolder) zzex.zza(parcel, DataHolder.CREATOR));
                break;
            case 6:
                zza((zzdg) zzex.zza(parcel, zzdg.CREATOR));
                break;
            case 7:
                zza((zzga) zzex.zza(parcel, zzga.CREATOR));
                break;
            case 8:
                zza((zzee) zzex.zza(parcel, zzee.CREATOR));
                break;
            case 9:
                zza((zzeg) zzex.zza(parcel, zzeg.CREATOR));
                break;
            case 10:
                zza((zzea) zzex.zza(parcel, zzea.CREATOR));
                break;
            case 11:
                zza((Status) zzex.zza(parcel, Status.CREATOR));
                break;
            case 12:
                zza((zzge) zzex.zza(parcel, zzge.CREATOR));
                break;
            case 13:
                zza((zzdy) zzex.zza(parcel, zzdy.CREATOR));
                break;
            case 14:
                zza((zzfq) zzex.zza(parcel, zzfq.CREATOR));
                break;
            case 15:
                zza((zzbt) zzex.zza(parcel, zzbt.CREATOR));
                break;
            case 16:
                zzb((zzbt) zzex.zza(parcel, zzbt.CREATOR));
                break;
            case 17:
                zza((zzdm) zzex.zza(parcel, zzdm.CREATOR));
                break;
            case 18:
                zza((zzdo) zzex.zza(parcel, zzdo.CREATOR));
                break;
            case 19:
                zza((zzbn) zzex.zza(parcel, zzbn.CREATOR));
                break;
            case 20:
                zza((zzbp) zzex.zza(parcel, zzbp.CREATOR));
                break;
            case 22:
                zza((zzdk) zzex.zza(parcel, zzdk.CREATOR));
                break;
            case 23:
                zza((zzdi) zzex.zza(parcel, zzdi.CREATOR));
                break;
            case 26:
                zza((zzf) zzex.zza(parcel, zzf.CREATOR));
                break;
            case 27:
                zza((zzfy) zzex.zza(parcel, zzfy.CREATOR));
                break;
            case 28:
                zza((zzdr) zzex.zza(parcel, zzdr.CREATOR));
                break;
            case 29:
                zza((zzdv) zzex.zza(parcel, zzdv.CREATOR));
                break;
            case 30:
                zza((zzdt) zzex.zza(parcel, zzdt.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
