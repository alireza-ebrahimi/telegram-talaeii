package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.IObjectWrapper;
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
import com.google.android.gms.maps.model.internal.zzd;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzj;
import com.google.android.gms.maps.model.internal.zzp;
import com.google.android.gms.maps.model.internal.zzs;
import com.google.android.gms.maps.model.internal.zzw;

@Hide
public interface IGoogleMapDelegate extends IInterface {
    zzd addCircle(CircleOptions circleOptions) throws RemoteException;

    zzg addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException;

    zzp addMarker(MarkerOptions markerOptions) throws RemoteException;

    zzs addPolygon(PolygonOptions polygonOptions) throws RemoteException;

    IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException;

    zzw addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException;

    void animateCamera(IObjectWrapper iObjectWrapper) throws RemoteException;

    void animateCameraWithCallback(IObjectWrapper iObjectWrapper, zzc zzc) throws RemoteException;

    void animateCameraWithDurationAndCallback(IObjectWrapper iObjectWrapper, int i, zzc zzc) throws RemoteException;

    void clear() throws RemoteException;

    CameraPosition getCameraPosition() throws RemoteException;

    zzj getFocusedBuilding() throws RemoteException;

    void getMapAsync(zzap zzap) throws RemoteException;

    int getMapType() throws RemoteException;

    float getMaxZoomLevel() throws RemoteException;

    float getMinZoomLevel() throws RemoteException;

    Location getMyLocation() throws RemoteException;

    IProjectionDelegate getProjection() throws RemoteException;

    IUiSettingsDelegate getUiSettings() throws RemoteException;

    boolean isBuildingsEnabled() throws RemoteException;

    boolean isIndoorEnabled() throws RemoteException;

    boolean isMyLocationEnabled() throws RemoteException;

    boolean isTrafficEnabled() throws RemoteException;

    void moveCamera(IObjectWrapper iObjectWrapper) throws RemoteException;

    void onCreate(Bundle bundle) throws RemoteException;

    void onDestroy() throws RemoteException;

    void onEnterAmbient(Bundle bundle) throws RemoteException;

    void onExitAmbient() throws RemoteException;

    void onLowMemory() throws RemoteException;

    void onPause() throws RemoteException;

    void onResume() throws RemoteException;

    void onSaveInstanceState(Bundle bundle) throws RemoteException;

    void onStart() throws RemoteException;

    void onStop() throws RemoteException;

    void resetMinMaxZoomPreference() throws RemoteException;

    void setBuildingsEnabled(boolean z) throws RemoteException;

    void setContentDescription(String str) throws RemoteException;

    boolean setIndoorEnabled(boolean z) throws RemoteException;

    void setInfoWindowAdapter(zzh zzh) throws RemoteException;

    void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds) throws RemoteException;

    void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException;

    boolean setMapStyle(MapStyleOptions mapStyleOptions) throws RemoteException;

    void setMapType(int i) throws RemoteException;

    void setMaxZoomPreference(float f) throws RemoteException;

    void setMinZoomPreference(float f) throws RemoteException;

    void setMyLocationEnabled(boolean z) throws RemoteException;

    void setOnCameraChangeListener(zzl zzl) throws RemoteException;

    void setOnCameraIdleListener(zzn zzn) throws RemoteException;

    void setOnCameraMoveCanceledListener(zzp zzp) throws RemoteException;

    void setOnCameraMoveListener(zzr zzr) throws RemoteException;

    void setOnCameraMoveStartedListener(zzt zzt) throws RemoteException;

    void setOnCircleClickListener(zzv zzv) throws RemoteException;

    void setOnGroundOverlayClickListener(zzx zzx) throws RemoteException;

    void setOnIndoorStateChangeListener(zzz zzz) throws RemoteException;

    void setOnInfoWindowClickListener(zzab zzab) throws RemoteException;

    void setOnInfoWindowCloseListener(zzad zzad) throws RemoteException;

    void setOnInfoWindowLongClickListener(zzaf zzaf) throws RemoteException;

    void setOnMapClickListener(zzaj zzaj) throws RemoteException;

    void setOnMapLoadedCallback(zzal zzal) throws RemoteException;

    void setOnMapLongClickListener(zzan zzan) throws RemoteException;

    void setOnMarkerClickListener(zzar zzar) throws RemoteException;

    void setOnMarkerDragListener(zzat zzat) throws RemoteException;

    void setOnMyLocationButtonClickListener(zzav zzav) throws RemoteException;

    void setOnMyLocationChangeListener(zzax zzax) throws RemoteException;

    void setOnMyLocationClickListener(zzaz zzaz) throws RemoteException;

    void setOnPoiClickListener(zzbb zzbb) throws RemoteException;

    void setOnPolygonClickListener(zzbd zzbd) throws RemoteException;

    void setOnPolylineClickListener(zzbf zzbf) throws RemoteException;

    void setPadding(int i, int i2, int i3, int i4) throws RemoteException;

    void setTrafficEnabled(boolean z) throws RemoteException;

    void setWatermarkEnabled(boolean z) throws RemoteException;

    void snapshot(zzbs zzbs, IObjectWrapper iObjectWrapper) throws RemoteException;

    void snapshotForTest(zzbs zzbs) throws RemoteException;

    void stopAnimation() throws RemoteException;

    boolean useViewLifecycleWhenInFragment() throws RemoteException;
}
