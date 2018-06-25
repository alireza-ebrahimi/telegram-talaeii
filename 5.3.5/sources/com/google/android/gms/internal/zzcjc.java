package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public abstract class zzcjc extends zzew implements zzcjb {
    public zzcjc() {
        attachInterface(this, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        List zza;
        switch (i) {
            case 1:
                zza((zzcix) zzex.zza(parcel, zzcix.CREATOR), (zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                break;
            case 2:
                zza((zzcnl) zzex.zza(parcel, zzcnl.CREATOR), (zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                zza((zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                break;
            case 5:
                zza((zzcix) zzex.zza(parcel, zzcix.CREATOR), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                break;
            case 6:
                zzb((zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                break;
            case 7:
                zza = zza((zzcif) zzex.zza(parcel, zzcif.CREATOR), zzex.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                break;
            case 9:
                byte[] zza2 = zza((zzcix) zzex.zza(parcel, zzcix.CREATOR), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeByteArray(zza2);
                break;
            case 10:
                zza(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                break;
            case 11:
                String zzc = zzc((zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(zzc);
                break;
            case 12:
                zza((zzcii) zzex.zza(parcel, zzcii.CREATOR), (zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                break;
            case 13:
                zzb((zzcii) zzex.zza(parcel, zzcii.CREATOR));
                parcel2.writeNoException();
                break;
            case 14:
                zza = zza(parcel.readString(), parcel.readString(), zzex.zza(parcel), (zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                break;
            case 15:
                zza = zza(parcel.readString(), parcel.readString(), parcel.readString(), zzex.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                break;
            case 16:
                zza = zza(parcel.readString(), parcel.readString(), (zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                break;
            case 17:
                zza = zzk(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                break;
            case 18:
                zzd((zzcif) zzex.zza(parcel, zzcif.CREATOR));
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        return true;
    }
}
