package com.google.android.gms.maps.model.internal;

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

public final class zzr extends zzev implements zzp {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IMarkerDelegate");
    }

    public final float getAlpha() throws RemoteException {
        Parcel zza = zza(26, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final String getId() throws RemoteException {
        Parcel zza = zza(2, zzbc());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final LatLng getPosition() throws RemoteException {
        Parcel zza = zza(4, zzbc());
        LatLng latLng = (LatLng) zzex.zza(zza, LatLng.CREATOR);
        zza.recycle();
        return latLng;
    }

    public final float getRotation() throws RemoteException {
        Parcel zza = zza(23, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final String getSnippet() throws RemoteException {
        Parcel zza = zza(8, zzbc());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final IObjectWrapper getTag() throws RemoteException {
        Parcel zza = zza(30, zzbc());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final String getTitle() throws RemoteException {
        Parcel zza = zza(6, zzbc());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final float getZIndex() throws RemoteException {
        Parcel zza = zza(28, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final int hashCodeRemote() throws RemoteException {
        Parcel zza = zza(17, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final void hideInfoWindow() throws RemoteException {
        zzb(12, zzbc());
    }

    public final boolean isDraggable() throws RemoteException {
        Parcel zza = zza(10, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isFlat() throws RemoteException {
        Parcel zza = zza(21, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isInfoWindowShown() throws RemoteException {
        Parcel zza = zza(13, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isVisible() throws RemoteException {
        Parcel zza = zza(15, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void remove() throws RemoteException {
        zzb(1, zzbc());
    }

    public final void setAlpha(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(25, zzbc);
    }

    public final void setAnchor(float f, float f2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc.writeFloat(f2);
        zzb(19, zzbc);
    }

    public final void setDraggable(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(9, zzbc);
    }

    public final void setFlat(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(20, zzbc);
    }

    public final void setInfoWindowAnchor(float f, float f2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc.writeFloat(f2);
        zzb(24, zzbc);
    }

    public final void setPosition(LatLng latLng) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLng);
        zzb(3, zzbc);
    }

    public final void setRotation(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(22, zzbc);
    }

    public final void setSnippet(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzb(7, zzbc);
    }

    public final void setTag(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzb(29, zzbc);
    }

    public final void setTitle(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzb(5, zzbc);
    }

    public final void setVisible(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(14, zzbc);
    }

    public final void setZIndex(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(27, zzbc);
    }

    public final void showInfoWindow() throws RemoteException {
        zzb(11, zzbc());
    }

    public final void zzae(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzb(18, zzbc);
    }

    public final boolean zzj(zzp zzp) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzp);
        zzbc = zza(16, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }
}
