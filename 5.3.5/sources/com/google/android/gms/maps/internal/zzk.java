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

public final class zzk extends zzev implements IMapViewDelegate {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    public final IGoogleMapDelegate getMap() throws RemoteException {
        IGoogleMapDelegate iGoogleMapDelegate;
        Parcel zza = zza(1, zzbc());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            iGoogleMapDelegate = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            iGoogleMapDelegate = queryLocalInterface instanceof IGoogleMapDelegate ? (IGoogleMapDelegate) queryLocalInterface : new zzg(readStrongBinder);
        }
        zza.recycle();
        return iGoogleMapDelegate;
    }

    public final void getMapAsync(zzap zzap) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzap);
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

    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(10, zzbc);
    }

    public final void onExitAmbient() throws RemoteException {
        zzb(11, zzbc());
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

    public final void onStart() throws RemoteException {
        zzb(12, zzbc());
    }

    public final void onStop() throws RemoteException {
        zzb(13, zzbc());
    }
}
