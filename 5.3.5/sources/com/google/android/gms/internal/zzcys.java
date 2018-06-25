package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzan;

public final class zzcys extends zzev implements zzcyr {
    zzcys(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zza(zzan zzan, int i, boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzan);
        zzbc.writeInt(i);
        zzex.zza(zzbc, z);
        zzb(9, zzbc);
    }

    public final void zza(zzcyu zzcyu, zzcyp zzcyp) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcyu);
        zzex.zza(zzbc, (IInterface) zzcyp);
        zzb(12, zzbc);
    }

    public final void zzev(int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzb(7, zzbc);
    }
}
