package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.List;

public final class zzu extends zzev implements zzs {
    zzu(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IPolygonDelegate");
    }

    public final int getFillColor() throws RemoteException {
        Parcel zza = zza(12, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final List getHoles() throws RemoteException {
        Parcel zza = zza(6, zzbc());
        List zzb = zzex.zzb(zza);
        zza.recycle();
        return zzb;
    }

    public final String getId() throws RemoteException {
        Parcel zza = zza(2, zzbc());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final List<LatLng> getPoints() throws RemoteException {
        Parcel zza = zza(4, zzbc());
        List createTypedArrayList = zza.createTypedArrayList(LatLng.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final int getStrokeColor() throws RemoteException {
        Parcel zza = zza(10, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final int getStrokeJointType() throws RemoteException {
        Parcel zza = zza(24, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final List<PatternItem> getStrokePattern() throws RemoteException {
        Parcel zza = zza(26, zzbc());
        List createTypedArrayList = zza.createTypedArrayList(PatternItem.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final float getStrokeWidth() throws RemoteException {
        Parcel zza = zza(8, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final IObjectWrapper getTag() throws RemoteException {
        Parcel zza = zza(28, zzbc());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final float getZIndex() throws RemoteException {
        Parcel zza = zza(14, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final int hashCodeRemote() throws RemoteException {
        Parcel zza = zza(20, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final boolean isClickable() throws RemoteException {
        Parcel zza = zza(22, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isGeodesic() throws RemoteException {
        Parcel zza = zza(18, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isVisible() throws RemoteException {
        Parcel zza = zza(16, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void remove() throws RemoteException {
        zzb(1, zzbc());
    }

    public final void setClickable(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(21, zzbc);
    }

    public final void setFillColor(int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzb(11, zzbc);
    }

    public final void setGeodesic(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(17, zzbc);
    }

    public final void setHoles(List list) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeList(list);
        zzb(5, zzbc);
    }

    public final void setPoints(List<LatLng> list) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeTypedList(list);
        zzb(3, zzbc);
    }

    public final void setStrokeColor(int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzb(9, zzbc);
    }

    public final void setStrokeJointType(int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzb(23, zzbc);
    }

    public final void setStrokePattern(List<PatternItem> list) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeTypedList(list);
        zzb(25, zzbc);
    }

    public final void setStrokeWidth(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(7, zzbc);
    }

    public final void setTag(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzb(27, zzbc);
    }

    public final void setVisible(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(15, zzbc);
    }

    public final void setZIndex(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(13, zzbc);
    }

    public final boolean zzb(zzs zzs) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzs);
        zzbc = zza(19, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }
}
