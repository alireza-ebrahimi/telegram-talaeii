package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

public final class zzbu extends zza implements IStreetViewPanoramaDelegate {
    zzbu(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
    }

    public final void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long j) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) streetViewPanoramaCamera);
        obtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken);
    }

    public final void enablePanning(boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final void enableStreetNames(boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken);
    }

    public final void enableUserNavigation(boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken);
    }

    public final void enableZoom(boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }

    public final StreetViewPanoramaCamera getPanoramaCamera() {
        Parcel transactAndReadException = transactAndReadException(10, obtainAndWriteInterfaceToken());
        StreetViewPanoramaCamera streetViewPanoramaCamera = (StreetViewPanoramaCamera) zzc.zza(transactAndReadException, StreetViewPanoramaCamera.CREATOR);
        transactAndReadException.recycle();
        return streetViewPanoramaCamera;
    }

    public final StreetViewPanoramaLocation getStreetViewPanoramaLocation() {
        Parcel transactAndReadException = transactAndReadException(14, obtainAndWriteInterfaceToken());
        StreetViewPanoramaLocation streetViewPanoramaLocation = (StreetViewPanoramaLocation) zzc.zza(transactAndReadException, StreetViewPanoramaLocation.CREATOR);
        transactAndReadException.recycle();
        return streetViewPanoramaLocation;
    }

    public final boolean isPanningGesturesEnabled() {
        Parcel transactAndReadException = transactAndReadException(6, obtainAndWriteInterfaceToken());
        boolean zza = zzc.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final boolean isStreetNamesEnabled() {
        Parcel transactAndReadException = transactAndReadException(8, obtainAndWriteInterfaceToken());
        boolean zza = zzc.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final boolean isUserNavigationEnabled() {
        Parcel transactAndReadException = transactAndReadException(7, obtainAndWriteInterfaceToken());
        boolean zza = zzc.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final boolean isZoomGesturesEnabled() {
        Parcel transactAndReadException = transactAndReadException(5, obtainAndWriteInterfaceToken());
        boolean zza = zzc.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final IObjectWrapper orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) streetViewPanoramaOrientation);
        obtainAndWriteInterfaceToken = transactAndReadException(19, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final StreetViewPanoramaOrientation pointToOrientation(IObjectWrapper iObjectWrapper) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        Parcel transactAndReadException = transactAndReadException(18, obtainAndWriteInterfaceToken);
        StreetViewPanoramaOrientation streetViewPanoramaOrientation = (StreetViewPanoramaOrientation) zzc.zza(transactAndReadException, StreetViewPanoramaOrientation.CREATOR);
        transactAndReadException.recycle();
        return streetViewPanoramaOrientation;
    }

    public final void setOnStreetViewPanoramaCameraChangeListener(zzbh zzbh) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzbh);
        transactAndReadExceptionReturnVoid(16, obtainAndWriteInterfaceToken);
    }

    public final void setOnStreetViewPanoramaChangeListener(zzbj zzbj) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzbj);
        transactAndReadExceptionReturnVoid(15, obtainAndWriteInterfaceToken);
    }

    public final void setOnStreetViewPanoramaClickListener(zzbl zzbl) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzbl);
        transactAndReadExceptionReturnVoid(17, obtainAndWriteInterfaceToken);
    }

    public final void setOnStreetViewPanoramaLongClickListener(zzbn zzbn) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzbn);
        transactAndReadExceptionReturnVoid(20, obtainAndWriteInterfaceToken);
    }

    public final void setPosition(LatLng latLng) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) latLng);
        transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken);
    }

    public final void setPositionWithID(String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(11, obtainAndWriteInterfaceToken);
    }

    public final void setPositionWithRadius(LatLng latLng, int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) latLng);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken);
    }

    public final void setPositionWithRadiusAndSource(LatLng latLng, int i, StreetViewSource streetViewSource) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) latLng);
        obtainAndWriteInterfaceToken.writeInt(i);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) streetViewSource);
        transactAndReadExceptionReturnVoid(22, obtainAndWriteInterfaceToken);
    }

    public final void setPositionWithSource(LatLng latLng, StreetViewSource streetViewSource) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) latLng);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) streetViewSource);
        transactAndReadExceptionReturnVoid(21, obtainAndWriteInterfaceToken);
    }
}
