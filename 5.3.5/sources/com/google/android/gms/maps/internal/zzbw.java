package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzbw extends zzev implements IStreetViewPanoramaViewDelegate {
    zzbw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
    }

    public final IStreetViewPanoramaDelegate getStreetViewPanorama() throws RemoteException {
        IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate;
        Parcel zza = zza(1, zzbc());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            iStreetViewPanoramaDelegate = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            iStreetViewPanoramaDelegate = queryLocalInterface instanceof IStreetViewPanoramaDelegate ? (IStreetViewPanoramaDelegate) queryLocalInterface : new zzbu(readStrongBinder);
        }
        zza.recycle();
        return iStreetViewPanoramaDelegate;
    }

    public final void getStreetViewPanoramaAsync(zzbp zzbp) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbp);
        zzb(9, zzbc);
    }

    public final IObjectWrapper getView() throws RemoteException {
        Parcel zza = zza(8, zzbc());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(2, zzbc);
    }

    public final void onDestroy() throws RemoteException {
        zzb(5, zzbc());
    }

    public final void onLowMemory() throws RemoteException {
        zzb(6, zzbc());
    }

    public final void onPause() throws RemoteException {
        zzb(4, zzbc());
    }

    public final void onResume() throws RemoteException {
        zzb(3, zzbc());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzbc = zza(7, zzbc);
        if (zzbc.readInt() != 0) {
            bundle.readFromParcel(zzbc);
        }
        zzbc.recycle();
    }
}
