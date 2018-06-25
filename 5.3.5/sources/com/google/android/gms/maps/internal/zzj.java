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
import com.google.android.gms.maps.GoogleMapOptions;

public final class zzj extends zzev implements IMapFragmentDelegate {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapFragmentDelegate");
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
        zzb(12, zzbc);
    }

    public final boolean isReady() throws RemoteException {
        Parcel zza = zza(11, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(3, zzbc);
    }

    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (IInterface) iObjectWrapper2);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzbc = zza(4, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final void onDestroy() throws RemoteException {
        zzb(8, zzbc());
    }

    public final void onDestroyView() throws RemoteException {
        zzb(7, zzbc());
    }

    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(13, zzbc);
    }

    public final void onExitAmbient() throws RemoteException {
        zzb(14, zzbc());
    }

    public final void onInflate(IObjectWrapper iObjectWrapper, GoogleMapOptions googleMapOptions, Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (Parcelable) googleMapOptions);
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(2, zzbc);
    }

    public final void onLowMemory() throws RemoteException {
        zzb(9, zzbc());
    }

    public final void onPause() throws RemoteException {
        zzb(6, zzbc());
    }

    public final void onResume() throws RemoteException {
        zzb(5, zzbc());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzbc = zza(10, zzbc);
        if (zzbc.readInt() != 0) {
            bundle.readFromParcel(zzbc);
        }
        zzbc.recycle();
    }

    public final void onStart() throws RemoteException {
        zzb(15, zzbc());
    }

    public final void onStop() throws RemoteException {
        zzb(16, zzbc());
    }
}
