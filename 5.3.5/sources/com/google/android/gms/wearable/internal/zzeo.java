package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import java.util.List;

public final class zzeo extends zzev implements zzem {
    zzeo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableListener");
    }

    public final void onConnectedNodes(List<zzfo> list) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeTypedList(list);
        zzc(5, zzbc);
    }

    public final void zza(zzah zzah) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzah);
        zzc(8, zzbc);
    }

    public final void zza(zzaw zzaw) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzaw);
        zzc(7, zzbc);
    }

    public final void zza(zzfe zzfe) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzfe);
        zzc(2, zzbc);
    }

    public final void zza(zzfo zzfo) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzfo);
        zzc(3, zzbc);
    }

    public final void zza(zzi zzi) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzi);
        zzc(9, zzbc);
    }

    public final void zza(zzl zzl) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzl);
        zzc(6, zzbc);
    }

    public final void zzb(zzfo zzfo) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzfo);
        zzc(4, zzbc);
    }

    public final void zzbo(DataHolder dataHolder) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) dataHolder);
        zzc(1, zzbc);
    }
}
