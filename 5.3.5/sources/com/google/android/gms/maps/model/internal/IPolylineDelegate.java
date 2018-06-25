package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.List;

@Hide
public interface IPolylineDelegate extends IInterface {

    public static abstract class zza extends zzew implements IPolylineDelegate {
        public static IPolylineDelegate zzbo(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
            return queryLocalInterface instanceof IPolylineDelegate ? (IPolylineDelegate) queryLocalInterface : new zzv(iBinder);
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            throw new NoSuchMethodError();
        }
    }

    boolean equalsRemote(IPolylineDelegate iPolylineDelegate) throws RemoteException;

    int getColor() throws RemoteException;

    Cap getEndCap() throws RemoteException;

    String getId() throws RemoteException;

    int getJointType() throws RemoteException;

    List<PatternItem> getPattern() throws RemoteException;

    List<LatLng> getPoints() throws RemoteException;

    Cap getStartCap() throws RemoteException;

    IObjectWrapper getTag() throws RemoteException;

    float getWidth() throws RemoteException;

    float getZIndex() throws RemoteException;

    int hashCodeRemote() throws RemoteException;

    boolean isClickable() throws RemoteException;

    boolean isGeodesic() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void remove() throws RemoteException;

    void setClickable(boolean z) throws RemoteException;

    void setColor(int i) throws RemoteException;

    void setEndCap(Cap cap) throws RemoteException;

    void setGeodesic(boolean z) throws RemoteException;

    void setJointType(int i) throws RemoteException;

    void setPattern(List<PatternItem> list) throws RemoteException;

    void setPoints(List<LatLng> list) throws RemoteException;

    void setStartCap(Cap cap) throws RemoteException;

    void setTag(IObjectWrapper iObjectWrapper) throws RemoteException;

    void setVisible(boolean z) throws RemoteException;

    void setWidth(float f) throws RemoteException;

    void setZIndex(float f) throws RemoteException;
}
