package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.IPolylineDelegate.zza;
import com.google.android.gms.maps.model.internal.zzd;
import com.google.android.gms.maps.model.internal.zze;
import com.google.android.gms.maps.model.internal.zzh;
import com.google.android.gms.maps.model.internal.zzj;
import com.google.android.gms.maps.model.internal.zzk;
import com.google.android.gms.maps.model.internal.zzp;
import com.google.android.gms.maps.model.internal.zzq;
import com.google.android.gms.maps.model.internal.zzs;
import com.google.android.gms.maps.model.internal.zzt;
import com.google.android.gms.maps.model.internal.zzw;
import com.google.android.gms.maps.model.internal.zzx;

public final class zzg extends zzev implements IGoogleMapDelegate {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IGoogleMapDelegate");
    }

    public final zzd addCircle(CircleOptions circleOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) circleOptions);
        zzbc = zza(35, zzbc);
        zzd zzbi = zze.zzbi(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbi;
    }

    public final com.google.android.gms.maps.model.internal.zzg addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) groundOverlayOptions);
        zzbc = zza(12, zzbc);
        com.google.android.gms.maps.model.internal.zzg zzbj = zzh.zzbj(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbj;
    }

    public final zzp addMarker(MarkerOptions markerOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) markerOptions);
        zzbc = zza(11, zzbc);
        zzp zzbm = zzq.zzbm(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbm;
    }

    public final zzs addPolygon(PolygonOptions polygonOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) polygonOptions);
        zzbc = zza(10, zzbc);
        zzs zzbn = zzt.zzbn(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbn;
    }

    public final IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) polylineOptions);
        zzbc = zza(9, zzbc);
        IPolylineDelegate zzbo = zza.zzbo(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbo;
    }

    public final zzw addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) tileOverlayOptions);
        zzbc = zza(13, zzbc);
        zzw zzbp = zzx.zzbp(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzbp;
    }

    public final void animateCamera(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzb(5, zzbc);
    }

    public final void animateCameraWithCallback(IObjectWrapper iObjectWrapper, zzc zzc) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (IInterface) zzc);
        zzb(6, zzbc);
    }

    public final void animateCameraWithDurationAndCallback(IObjectWrapper iObjectWrapper, int i, zzc zzc) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzbc.writeInt(i);
        zzex.zza(zzbc, (IInterface) zzc);
        zzb(7, zzbc);
    }

    public final void clear() throws RemoteException {
        zzb(14, zzbc());
    }

    public final CameraPosition getCameraPosition() throws RemoteException {
        Parcel zza = zza(1, zzbc());
        CameraPosition cameraPosition = (CameraPosition) zzex.zza(zza, CameraPosition.CREATOR);
        zza.recycle();
        return cameraPosition;
    }

    public final zzj getFocusedBuilding() throws RemoteException {
        Parcel zza = zza(44, zzbc());
        zzj zzbk = zzk.zzbk(zza.readStrongBinder());
        zza.recycle();
        return zzbk;
    }

    public final void getMapAsync(zzap zzap) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzap);
        zzb(53, zzbc);
    }

    public final int getMapType() throws RemoteException {
        Parcel zza = zza(15, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final float getMaxZoomLevel() throws RemoteException {
        Parcel zza = zza(2, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final float getMinZoomLevel() throws RemoteException {
        Parcel zza = zza(3, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final Location getMyLocation() throws RemoteException {
        Parcel zza = zza(23, zzbc());
        Location location = (Location) zzex.zza(zza, Location.CREATOR);
        zza.recycle();
        return location;
    }

    public final IProjectionDelegate getProjection() throws RemoteException {
        IProjectionDelegate iProjectionDelegate;
        Parcel zza = zza(26, zzbc());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            iProjectionDelegate = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
            iProjectionDelegate = queryLocalInterface instanceof IProjectionDelegate ? (IProjectionDelegate) queryLocalInterface : new zzbr(readStrongBinder);
        }
        zza.recycle();
        return iProjectionDelegate;
    }

    public final IUiSettingsDelegate getUiSettings() throws RemoteException {
        IUiSettingsDelegate iUiSettingsDelegate;
        Parcel zza = zza(25, zzbc());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            iUiSettingsDelegate = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
            iUiSettingsDelegate = queryLocalInterface instanceof IUiSettingsDelegate ? (IUiSettingsDelegate) queryLocalInterface : new zzbx(readStrongBinder);
        }
        zza.recycle();
        return iUiSettingsDelegate;
    }

    public final boolean isBuildingsEnabled() throws RemoteException {
        Parcel zza = zza(40, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isIndoorEnabled() throws RemoteException {
        Parcel zza = zza(19, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isMyLocationEnabled() throws RemoteException {
        Parcel zza = zza(21, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean isTrafficEnabled() throws RemoteException {
        Parcel zza = zza(17, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void moveCamera(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzb(4, zzbc);
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(54, zzbc);
    }

    public final void onDestroy() throws RemoteException {
        zzb(57, zzbc());
    }

    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzb(81, zzbc);
    }

    public final void onExitAmbient() throws RemoteException {
        zzb(82, zzbc());
    }

    public final void onLowMemory() throws RemoteException {
        zzb(58, zzbc());
    }

    public final void onPause() throws RemoteException {
        zzb(56, zzbc());
    }

    public final void onResume() throws RemoteException {
        zzb(55, zzbc());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bundle);
        zzbc = zza(60, zzbc);
        if (zzbc.readInt() != 0) {
            bundle.readFromParcel(zzbc);
        }
        zzbc.recycle();
    }

    public final void onStart() throws RemoteException {
        zzb(101, zzbc());
    }

    public final void onStop() throws RemoteException {
        zzb(102, zzbc());
    }

    public final void resetMinMaxZoomPreference() throws RemoteException {
        zzb(94, zzbc());
    }

    public final void setBuildingsEnabled(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(41, zzbc);
    }

    public final void setContentDescription(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzb(61, zzbc);
    }

    public final boolean setIndoorEnabled(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzbc = zza(20, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }

    public final void setInfoWindowAdapter(zzh zzh) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzh);
        zzb(33, zzbc);
    }

    public final void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) latLngBounds);
        zzb(95, zzbc);
    }

    public final void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iLocationSourceDelegate);
        zzb(24, zzbc);
    }

    public final boolean setMapStyle(MapStyleOptions mapStyleOptions) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) mapStyleOptions);
        zzbc = zza(91, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }

    public final void setMapType(int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzb(16, zzbc);
    }

    public final void setMaxZoomPreference(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(93, zzbc);
    }

    public final void setMinZoomPreference(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(92, zzbc);
    }

    public final void setMyLocationEnabled(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(22, zzbc);
    }

    public final void setOnCameraChangeListener(zzl zzl) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzl);
        zzb(27, zzbc);
    }

    public final void setOnCameraIdleListener(zzn zzn) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzn);
        zzb(99, zzbc);
    }

    public final void setOnCameraMoveCanceledListener(zzp zzp) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzp);
        zzb(98, zzbc);
    }

    public final void setOnCameraMoveListener(zzr zzr) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzr);
        zzb(97, zzbc);
    }

    public final void setOnCameraMoveStartedListener(zzt zzt) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzt);
        zzb(96, zzbc);
    }

    public final void setOnCircleClickListener(zzv zzv) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzv);
        zzb(89, zzbc);
    }

    public final void setOnGroundOverlayClickListener(zzx zzx) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzx);
        zzb(83, zzbc);
    }

    public final void setOnIndoorStateChangeListener(zzz zzz) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzz);
        zzb(45, zzbc);
    }

    public final void setOnInfoWindowClickListener(zzab zzab) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzab);
        zzb(32, zzbc);
    }

    public final void setOnInfoWindowCloseListener(zzad zzad) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzad);
        zzb(86, zzbc);
    }

    public final void setOnInfoWindowLongClickListener(zzaf zzaf) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzaf);
        zzb(84, zzbc);
    }

    public final void setOnMapClickListener(zzaj zzaj) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzaj);
        zzb(28, zzbc);
    }

    public final void setOnMapLoadedCallback(zzal zzal) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzal);
        zzb(42, zzbc);
    }

    public final void setOnMapLongClickListener(zzan zzan) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzan);
        zzb(29, zzbc);
    }

    public final void setOnMarkerClickListener(zzar zzar) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzar);
        zzb(30, zzbc);
    }

    public final void setOnMarkerDragListener(zzat zzat) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzat);
        zzb(31, zzbc);
    }

    public final void setOnMyLocationButtonClickListener(zzav zzav) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzav);
        zzb(37, zzbc);
    }

    public final void setOnMyLocationChangeListener(zzax zzax) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzax);
        zzb(36, zzbc);
    }

    public final void setOnMyLocationClickListener(zzaz zzaz) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzaz);
        zzb(107, zzbc);
    }

    public final void setOnPoiClickListener(zzbb zzbb) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbb);
        zzb(80, zzbc);
    }

    public final void setOnPolygonClickListener(zzbd zzbd) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbd);
        zzb(85, zzbc);
    }

    public final void setOnPolylineClickListener(zzbf zzbf) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbf);
        zzb(87, zzbc);
    }

    public final void setPadding(int i, int i2, int i3, int i4) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzbc.writeInt(i2);
        zzbc.writeInt(i3);
        zzbc.writeInt(i4);
        zzb(39, zzbc);
    }

    public final void setTrafficEnabled(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(18, zzbc);
    }

    public final void setWatermarkEnabled(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(51, zzbc);
    }

    public final void snapshot(zzbs zzbs, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbs);
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzb(38, zzbc);
    }

    public final void snapshotForTest(zzbs zzbs) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzbs);
        zzb(71, zzbc);
    }

    public final void stopAnimation() throws RemoteException {
        zzb(8, zzbc());
    }

    public final boolean useViewLifecycleWhenInFragment() throws RemoteException {
        Parcel zza = zza(59, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }
}
