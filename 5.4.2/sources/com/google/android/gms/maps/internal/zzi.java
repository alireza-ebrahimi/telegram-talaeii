package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.internal.maps.zzu;

public abstract class zzi extends zzb implements zzh {
    public zzi() {
        super("com.google.android.gms.maps.internal.IInfoWindowAdapter");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        IInterface zzh;
        switch (i) {
            case 1:
                zzh = zzh(zzu.zzg(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzc.zza(parcel2, zzh);
                break;
            case 2:
                zzh = zzi(zzu.zzg(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzc.zza(parcel2, zzh);
                break;
            default:
                return false;
        }
        return true;
    }
}
