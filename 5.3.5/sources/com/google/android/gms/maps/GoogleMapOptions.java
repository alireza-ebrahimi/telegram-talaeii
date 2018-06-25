package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.C0489R;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

public final class GoogleMapOptions extends zzbgl implements ReflectedParcelable {
    @Hide
    public static final Creator<GoogleMapOptions> CREATOR = new zzaa();
    private int mapType = -1;
    private Boolean zzjau;
    private Boolean zzjav;
    private CameraPosition zzjaw;
    private Boolean zzjax;
    private Boolean zzjay;
    private Boolean zzjaz;
    private Boolean zzjba;
    private Boolean zzjbb;
    private Boolean zzjbc;
    private Boolean zzjbd;
    private Boolean zzjbe;
    private Boolean zzjbf;
    private Float zzjbg = null;
    private Float zzjbh = null;
    private LatLngBounds zzjbi = null;

    @Hide
    GoogleMapOptions(byte b, byte b2, int i, CameraPosition cameraPosition, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9, byte b10, byte b11, Float f, Float f2, LatLngBounds latLngBounds) {
        this.zzjau = zza.zza(b);
        this.zzjav = zza.zza(b2);
        this.mapType = i;
        this.zzjaw = cameraPosition;
        this.zzjax = zza.zza(b3);
        this.zzjay = zza.zza(b4);
        this.zzjaz = zza.zza(b5);
        this.zzjba = zza.zza(b6);
        this.zzjbb = zza.zza(b7);
        this.zzjbc = zza.zza(b8);
        this.zzjbd = zza.zza(b9);
        this.zzjbe = zza.zza(b10);
        this.zzjbf = zza.zza(b11);
        this.zzjbg = f;
        this.zzjbh = f2;
        this.zzjbi = latLngBounds;
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, C0489R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(obtainAttributes.getInt(C0489R.styleable.MapAttrs_mapType, -1));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiCompass, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_liteMode, false));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_uiMapToolbar, true));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_ambientEnabled)) {
            googleMapOptions.ambientEnabled(obtainAttributes.getBoolean(C0489R.styleable.MapAttrs_ambientEnabled, false));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.minZoomPreference(obtainAttributes.getFloat(C0489R.styleable.MapAttrs_cameraMinZoomPreference, Float.NEGATIVE_INFINITY));
        }
        if (obtainAttributes.hasValue(C0489R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.maxZoomPreference(obtainAttributes.getFloat(C0489R.styleable.MapAttrs_cameraMaxZoomPreference, Float.POSITIVE_INFINITY));
        }
        googleMapOptions.latLngBoundsForCameraTarget(LatLngBounds.createFromAttributes(context, attributeSet));
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attributeSet));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    public final GoogleMapOptions ambientEnabled(boolean z) {
        this.zzjbf = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.zzjaw = cameraPosition;
        return this;
    }

    public final GoogleMapOptions compassEnabled(boolean z) {
        this.zzjay = Boolean.valueOf(z);
        return this;
    }

    public final Boolean getAmbientEnabled() {
        return this.zzjbf;
    }

    public final CameraPosition getCamera() {
        return this.zzjaw;
    }

    public final Boolean getCompassEnabled() {
        return this.zzjay;
    }

    public final LatLngBounds getLatLngBoundsForCameraTarget() {
        return this.zzjbi;
    }

    public final Boolean getLiteMode() {
        return this.zzjbd;
    }

    public final Boolean getMapToolbarEnabled() {
        return this.zzjbe;
    }

    public final int getMapType() {
        return this.mapType;
    }

    public final Float getMaxZoomPreference() {
        return this.zzjbh;
    }

    public final Float getMinZoomPreference() {
        return this.zzjbg;
    }

    public final Boolean getRotateGesturesEnabled() {
        return this.zzjbc;
    }

    public final Boolean getScrollGesturesEnabled() {
        return this.zzjaz;
    }

    public final Boolean getTiltGesturesEnabled() {
        return this.zzjbb;
    }

    public final Boolean getUseViewLifecycleInFragment() {
        return this.zzjav;
    }

    public final Boolean getZOrderOnTop() {
        return this.zzjau;
    }

    public final Boolean getZoomControlsEnabled() {
        return this.zzjax;
    }

    public final Boolean getZoomGesturesEnabled() {
        return this.zzjba;
    }

    public final GoogleMapOptions latLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
        this.zzjbi = latLngBounds;
        return this;
    }

    public final GoogleMapOptions liteMode(boolean z) {
        this.zzjbd = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions mapToolbarEnabled(boolean z) {
        this.zzjbe = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions mapType(int i) {
        this.mapType = i;
        return this;
    }

    public final GoogleMapOptions maxZoomPreference(float f) {
        this.zzjbh = Float.valueOf(f);
        return this;
    }

    public final GoogleMapOptions minZoomPreference(float f) {
        this.zzjbg = Float.valueOf(f);
        return this;
    }

    public final GoogleMapOptions rotateGesturesEnabled(boolean z) {
        this.zzjbc = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions scrollGesturesEnabled(boolean z) {
        this.zzjaz = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions tiltGesturesEnabled(boolean z) {
        this.zzjbb = Boolean.valueOf(z);
        return this;
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("MapType", Integer.valueOf(this.mapType)).zzg("LiteMode", this.zzjbd).zzg("Camera", this.zzjaw).zzg("CompassEnabled", this.zzjay).zzg("ZoomControlsEnabled", this.zzjax).zzg("ScrollGesturesEnabled", this.zzjaz).zzg("ZoomGesturesEnabled", this.zzjba).zzg("TiltGesturesEnabled", this.zzjbb).zzg("RotateGesturesEnabled", this.zzjbc).zzg("MapToolbarEnabled", this.zzjbe).zzg("AmbientEnabled", this.zzjbf).zzg("MinZoomPreference", this.zzjbg).zzg("MaxZoomPreference", this.zzjbh).zzg("LatLngBoundsForCameraTarget", this.zzjbi).zzg("ZOrderOnTop", this.zzjau).zzg("UseViewLifecycleInFragment", this.zzjav).toString();
    }

    public final GoogleMapOptions useViewLifecycleInFragment(boolean z) {
        this.zzjav = Boolean.valueOf(z);
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, zza.zzc(this.zzjau));
        zzbgo.zza(parcel, 3, zza.zzc(this.zzjav));
        zzbgo.zzc(parcel, 4, getMapType());
        zzbgo.zza(parcel, 5, getCamera(), i, false);
        zzbgo.zza(parcel, 6, zza.zzc(this.zzjax));
        zzbgo.zza(parcel, 7, zza.zzc(this.zzjay));
        zzbgo.zza(parcel, 8, zza.zzc(this.zzjaz));
        zzbgo.zza(parcel, 9, zza.zzc(this.zzjba));
        zzbgo.zza(parcel, 10, zza.zzc(this.zzjbb));
        zzbgo.zza(parcel, 11, zza.zzc(this.zzjbc));
        zzbgo.zza(parcel, 12, zza.zzc(this.zzjbd));
        zzbgo.zza(parcel, 14, zza.zzc(this.zzjbe));
        zzbgo.zza(parcel, 15, zza.zzc(this.zzjbf));
        zzbgo.zza(parcel, 16, getMinZoomPreference(), false);
        zzbgo.zza(parcel, 17, getMaxZoomPreference(), false);
        zzbgo.zza(parcel, 18, getLatLngBoundsForCameraTarget(), i, false);
        zzbgo.zzai(parcel, zze);
    }

    public final GoogleMapOptions zOrderOnTop(boolean z) {
        this.zzjau = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions zoomControlsEnabled(boolean z) {
        this.zzjax = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions zoomGesturesEnabled(boolean z) {
        this.zzjba = Boolean.valueOf(z);
        return this;
    }
}
