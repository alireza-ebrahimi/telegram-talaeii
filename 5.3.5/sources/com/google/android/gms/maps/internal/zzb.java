package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public final class zzb extends zzev implements ICameraUpdateFactoryDelegate {
    zzb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
    }

    public final IObjectWrapper newCameraPosition(CameraPosition cameraPosition) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) cameraPosition);
        zzbc = zza(7, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper newLatLng(LatLng latLng) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzbc = zza(8, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper newLatLngBounds(LatLngBounds latLngBounds, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLngBounds);
        zzbc.writeInt(i);
        zzbc = zza(10, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper newLatLngBoundsWithSize(LatLngBounds latLngBounds, int i, int i2, int i3) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLngBounds);
        zzbc.writeInt(i);
        zzbc.writeInt(i2);
        zzbc.writeInt(i3);
        zzbc = zza(11, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper newLatLngZoom(LatLng latLng, float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzbc.writeFloat(f);
        zzbc = zza(9, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper scrollBy(float f, float f2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc.writeFloat(f2);
        zzbc = zza(3, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zoomBy(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc = zza(5, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zoomByWithFocus(float f, int i, int i2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc.writeInt(i);
        zzbc.writeInt(i2);
        zzbc = zza(6, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zoomIn() throws RemoteException {
        Parcel zza = zza(1, zzbc());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zoomOut() throws RemoteException {
        Parcel zza = zza(2, zzbc());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zoomTo(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc = zza(4, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }
}
