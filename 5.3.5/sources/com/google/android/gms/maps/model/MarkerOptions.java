package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class MarkerOptions extends zzbgl {
    @Hide
    public static final Creator<MarkerOptions> CREATOR = new zzh();
    private float alpha = 1.0f;
    private LatLng position;
    private String title;
    private float zzjda;
    private boolean zzjdb = true;
    private float zzjdj = 0.5f;
    private float zzjdk = 1.0f;
    private String zzjdv;
    private BitmapDescriptor zzjdw;
    private boolean zzjdx;
    private boolean zzjdy = false;
    private float zzjdz = 0.0f;
    private float zzjea = 0.5f;
    private float zzjeb = 0.0f;

    @Hide
    MarkerOptions(LatLng latLng, String str, String str2, IBinder iBinder, float f, float f2, boolean z, boolean z2, boolean z3, float f3, float f4, float f5, float f6, float f7) {
        this.position = latLng;
        this.title = str;
        this.zzjdv = str2;
        if (iBinder == null) {
            this.zzjdw = null;
        } else {
            this.zzjdw = new BitmapDescriptor(zza.zzaq(iBinder));
        }
        this.zzjdj = f;
        this.zzjdk = f2;
        this.zzjdx = z;
        this.zzjdb = z2;
        this.zzjdy = z3;
        this.zzjdz = f3;
        this.zzjea = f4;
        this.zzjeb = f5;
        this.alpha = f6;
        this.zzjda = f7;
    }

    public final MarkerOptions alpha(float f) {
        this.alpha = f;
        return this;
    }

    public final MarkerOptions anchor(float f, float f2) {
        this.zzjdj = f;
        this.zzjdk = f2;
        return this;
    }

    public final MarkerOptions draggable(boolean z) {
        this.zzjdx = z;
        return this;
    }

    public final MarkerOptions flat(boolean z) {
        this.zzjdy = z;
        return this;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final float getAnchorU() {
        return this.zzjdj;
    }

    public final float getAnchorV() {
        return this.zzjdk;
    }

    public final BitmapDescriptor getIcon() {
        return this.zzjdw;
    }

    public final float getInfoWindowAnchorU() {
        return this.zzjea;
    }

    public final float getInfoWindowAnchorV() {
        return this.zzjeb;
    }

    public final LatLng getPosition() {
        return this.position;
    }

    public final float getRotation() {
        return this.zzjdz;
    }

    public final String getSnippet() {
        return this.zzjdv;
    }

    public final String getTitle() {
        return this.title;
    }

    public final float getZIndex() {
        return this.zzjda;
    }

    public final MarkerOptions icon(@Nullable BitmapDescriptor bitmapDescriptor) {
        this.zzjdw = bitmapDescriptor;
        return this;
    }

    public final MarkerOptions infoWindowAnchor(float f, float f2) {
        this.zzjea = f;
        this.zzjeb = f2;
        return this;
    }

    public final boolean isDraggable() {
        return this.zzjdx;
    }

    public final boolean isFlat() {
        return this.zzjdy;
    }

    public final boolean isVisible() {
        return this.zzjdb;
    }

    public final MarkerOptions position(@NonNull LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        }
        this.position = latLng;
        return this;
    }

    public final MarkerOptions rotation(float f) {
        this.zzjdz = f;
        return this;
    }

    public final MarkerOptions snippet(@Nullable String str) {
        this.zzjdv = str;
        return this;
    }

    public final MarkerOptions title(@Nullable String str) {
        this.title = str;
        return this;
    }

    public final MarkerOptions visible(boolean z) {
        this.zzjdb = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getPosition(), i, false);
        zzbgo.zza(parcel, 3, getTitle(), false);
        zzbgo.zza(parcel, 4, getSnippet(), false);
        zzbgo.zza(parcel, 5, this.zzjdw == null ? null : this.zzjdw.zzaxq().asBinder(), false);
        zzbgo.zza(parcel, 6, getAnchorU());
        zzbgo.zza(parcel, 7, getAnchorV());
        zzbgo.zza(parcel, 8, isDraggable());
        zzbgo.zza(parcel, 9, isVisible());
        zzbgo.zza(parcel, 10, isFlat());
        zzbgo.zza(parcel, 11, getRotation());
        zzbgo.zza(parcel, 12, getInfoWindowAnchorU());
        zzbgo.zza(parcel, 13, getInfoWindowAnchorV());
        zzbgo.zza(parcel, 14, getAlpha());
        zzbgo.zza(parcel, 15, getZIndex());
        zzbgo.zzai(parcel, zze);
    }

    public final MarkerOptions zIndex(float f) {
        this.zzjda = f;
        return this;
    }
}
