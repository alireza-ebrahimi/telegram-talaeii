package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import java.util.List;

public final class zzl extends zzev implements zzj {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
    }

    public final int getActiveLevelIndex() throws RemoteException {
        Parcel zza = zza(1, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final int getDefaultLevelIndex() throws RemoteException {
        Parcel zza = zza(2, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final List<IBinder> getLevels() throws RemoteException {
        Parcel zza = zza(3, zzbc());
        List createBinderArrayList = zza.createBinderArrayList();
        zza.recycle();
        return createBinderArrayList;
    }

    public final int hashCodeRemote() throws RemoteException {
        Parcel zza = zza(6, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final boolean isUnderground() throws RemoteException {
        Parcel zza = zza(4, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean zzb(zzj zzj) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzj);
        zzbc = zza(5, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }
}
