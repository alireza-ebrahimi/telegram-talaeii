package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

public final class zzbu extends zzev implements IStreetViewPanoramaDelegate {
    zzbu(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
    }

    public final void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long j) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) streetViewPanoramaCamera);
        zzbc.writeLong(j);
        zzb(9, zzbc);
    }

    public final void enablePanning(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(2, zzbc);
    }

    public final void enableStreetNames(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(4, zzbc);
    }

    public final void enableUserNavigation(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(3, zzbc);
    }

    public final void enableZoom(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(1, zzbc);
    }

    public final StreetViewPanoramaCamera getPanoramaCamera() throws RemoteException {
        Parcel zza = zza(10, zzbc());
        StreetViewPanoramaCamera streetViewPanoramaCamera = (StreetViewPanoramaCamera) zzex.zza(zza, StreetViewPanoramaCamera.CREATOR);
        zza.recycle();
        return streetViewPanoramaCamera;
    }

    public final StreetViewPanoramaLocation getStreetViewPanoramaLocation() throws RemoteException {
        Parcel zza = zza(14, zzbc());
        StreetViewPanoramaLocation streetViewPanoramaLocation = (StreetViewPanoramaLocation) zzex.zza(zza, StreetViewPanoramaLocation.CREATOR);
        zza.recycle();
        return streetViewPanoramaLocation;
    }

    public final boolean isPanningGesturesEnabled() throws RemoteException {
        Parcel zza = zza(6, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isStreetNamesEnabled() throws RemoteException {
        Parcel zza = zza(8, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isUserNavigationEnabled() throws RemoteException {
        Parcel zza = zza(7, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isZoomGesturesEnabled() throws RemoteException {
        Parcel zza = zza(5, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final IObjectWrapper orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) streetViewPanoramaOrientation);
        zzbc = zza(19, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final StreetViewPanoramaOrientation pointToOrientation(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        Parcel zza = zza(18, zzbc);
        StreetViewPanoramaOrientation streetViewPanoramaOrientation = (StreetViewPanoramaOrientation) zzex.zza(zza, StreetViewPanoramaOrientation.CREATOR);
        zza.recycle();
        return streetViewPanoramaOrientation;
    }

    public final void setOnStreetViewPanoramaCameraChangeListener(zzbh zzbh) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbh);
        zzb(16, zzbc);
    }

    public final void setOnStreetViewPanoramaChangeListener(zzbj zzbj) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbj);
        zzb(15, zzbc);
    }

    public final void setOnStreetViewPanoramaClickListener(zzbl zzbl) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbl);
        zzb(17, zzbc);
    }

    public final void setOnStreetViewPanoramaLongClickListener(zzbn zzbn) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbn);
        zzb(20, zzbc);
    }

    public final void setPosition(LatLng latLng) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzb(12, zzbc);
    }

    public final void setPositionWithID(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzb(11, zzbc);
    }

    public final void setPositionWithRadius(LatLng latLng, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzbc.writeInt(i);
        zzb(13, zzbc);
    }

    public final void setPositionWithRadiusAndSource(LatLng latLng, int i, StreetViewSource streetViewSource) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzbc.writeInt(i);
        zzex.zza(zzbc, (Parcelable) streetViewSource);
        zzb(22, zzbc);
    }

    public final void setPositionWithSource(LatLng latLng, StreetViewSource streetViewSource) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzex.zza(zzbc, (Parcelable) streetViewSource);
        zzb(21, zzbc);
    }
}
