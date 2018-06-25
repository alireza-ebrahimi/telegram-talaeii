package com.google.android.gms.common.api.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;

public abstract class zzcb extends zzew implements zzca {
    public zzcb() {
        attachInterface(this, "com.google.android.gms.common.api.internal.IStatusCallback");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zzn((Status) zzex.zza(parcel, Status.CREATOR));
        return true;
    }
}
