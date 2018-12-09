package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.internal.maps.zzb;

public abstract class zzbg extends zzb implements zzbf {
    public zzbg() {
        super("com.google.android.gms.maps.internal.IOnPolylineClickListener");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        zza(zzaa.zzi(parcel.readStrongBinder()));
        parcel2.writeNoException();
        return true;
    }
}
