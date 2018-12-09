package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;

public final class zzk extends zza implements IMapViewDelegate {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    public final IGoogleMapDelegate getMap() {
        IGoogleMapDelegate iGoogleMapDelegate;
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken());
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            iGoogleMapDelegate = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            iGoogleMapDelegate = queryLocalInterface instanceof IGoogleMapDelegate ? (IGoogleMapDelegate) queryLocalInterface : new zzg(readStrongBinder);
        }
        transactAndReadException.recycle();
        return iGoogleMapDelegate;
    }

    public final void getMapAsync(zzap zzap) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzap);
        transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken);
    }

    public final IObjectWrapper getView() {
        Parcel transactAndReadException = transactAndReadException(8, obtainAndWriteInterfaceToken());
        IObjectWrapper asInterface = Stub.asInterface(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return asInterface;
    }

    public final void onCreate(Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final void onDestroy() {
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken());
    }

    public final void onEnterAmbient(Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken);
    }

    public final void onExitAmbient() {
        transactAndReadExceptionReturnVoid(11, obtainAndWriteInterfaceToken());
    }

    public final void onLowMemory() {
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken());
    }

    public final void onPause() {
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken());
    }

    public final void onResume() {
        transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken());
    }

    public final void onSaveInstanceState(Bundle bundle) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        obtainAndWriteInterfaceToken = transactAndReadException(7, obtainAndWriteInterfaceToken);
        if (obtainAndWriteInterfaceToken.readInt() != 0) {
            bundle.readFromParcel(obtainAndWriteInterfaceToken);
        }
        obtainAndWriteInterfaceToken.recycle();
    }

    public final void onStart() {
        transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken());
    }

    public final void onStop() {
        transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken());
    }
}
