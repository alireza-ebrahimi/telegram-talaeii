package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;

@Class(creator = "MarkerOptionsCreator")
@Reserved({1})
public final class MarkerOptions extends AbstractSafeParcelable {
    public static final Creator<MarkerOptions> CREATOR = new zzh();
    @Field(defaultValue = "1.0f", getter = "getAlpha", id = 14)
    private float alpha = 1.0f;
    @Field(getter = "getPosition", id = 2)
    private LatLng position;
    @Field(getter = "getZIndex", id = 15)
    private float zzcr;
    @Field(getter = "isVisible", id = 9)
    private boolean zzcs = true;
    @Field(getter = "getAnchorU", id = 6)
    private float zzda = 0.5f;
    @Field(getter = "getAnchorV", id = 7)
    private float zzdb = 1.0f;
    @Field(getter = "getTitle", id = 3)
    private String zzdm;
    @Field(getter = "getSnippet", id = 4)
    private String zzdn;
    @Field(getter = "getWrappedIconDescriptorImplBinder", id = 5, type = "android.os.IBinder")
    private BitmapDescriptor zzdo;
    @Field(getter = "isDraggable", id = 8)
    private boolean zzdp;
    @Field(getter = "isFlat", id = 10)
    private boolean zzdq = false;
    @Field(getter = "getRotation", id = 11)
    private float zzdr = BitmapDescriptorFactory.HUE_RED;
    @Field(defaultValue = "0.5f", getter = "getInfoWindowAnchorU", id = 12)
    private float zzds = 0.5f;
    @Field(getter = "getInfoWindowAnchorV", id = 13)
    private float zzdt = BitmapDescriptorFactory.HUE_RED;

    @Constructor
    MarkerOptions(@Param(id = 2) LatLng latLng, @Param(id = 3) String str, @Param(id = 4) String str2, @Param(id = 5) IBinder iBinder, @Param(id = 6) float f, @Param(id = 7) float f2, @Param(id = 8) boolean z, @Param(id = 9) boolean z2, @Param(id = 10) boolean z3, @Param(id = 11) float f3, @Param(id = 12) float f4, @Param(id = 13) float f5, @Param(id = 14) float f6, @Param(id = 15) float f7) {
        this.position = latLng;
        this.zzdm = str;
        this.zzdn = str2;
        if (iBinder == null) {
            this.zzdo = null;
        } else {
            this.zzdo = new BitmapDescriptor(Stub.asInterface(iBinder));
        }
        this.zzda = f;
        this.zzdb = f2;
        this.zzdp = z;
        this.zzcs = z2;
        this.zzdq = z3;
        this.zzdr = f3;
        this.zzds = f4;
        this.zzdt = f5;
        this.alpha = f6;
        this.zzcr = f7;
    }

    public final MarkerOptions alpha(float f) {
        this.alpha = f;
        return this;
    }

    public final MarkerOptions anchor(float f, float f2) {
        this.zzda = f;
        this.zzdb = f2;
        return this;
    }

    public final MarkerOptions draggable(boolean z) {
        this.zzdp = z;
        return this;
    }

    public final MarkerOptions flat(boolean z) {
        this.zzdq = z;
        return this;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final float getAnchorU() {
        return this.zzda;
    }

    public final float getAnchorV() {
        return this.zzdb;
    }

    public final BitmapDescriptor getIcon() {
        return this.zzdo;
    }

    public final float getInfoWindowAnchorU() {
        return this.zzds;
    }

    public final float getInfoWindowAnchorV() {
        return this.zzdt;
    }

    public final LatLng getPosition() {
        return this.position;
    }

    public final float getRotation() {
        return this.zzdr;
    }

    public final String getSnippet() {
        return this.zzdn;
    }

    public final String getTitle() {
        return this.zzdm;
    }

    public final float getZIndex() {
        return this.zzcr;
    }

    public final MarkerOptions icon(BitmapDescriptor bitmapDescriptor) {
        this.zzdo = bitmapDescriptor;
        return this;
    }

    public final MarkerOptions infoWindowAnchor(float f, float f2) {
        this.zzds = f;
        this.zzdt = f2;
        return this;
    }

    public final boolean isDraggable() {
        return this.zzdp;
    }

    public final boolean isFlat() {
        return this.zzdq;
    }

    public final boolean isVisible() {
        return this.zzcs;
    }

    public final MarkerOptions position(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        }
        this.position = latLng;
        return this;
    }

    public final MarkerOptions rotation(float f) {
        this.zzdr = f;
        return this;
    }

    public final MarkerOptions snippet(String str) {
        this.zzdn = str;
        return this;
    }

    public final MarkerOptions title(String str) {
        this.zzdm = str;
        return this;
    }

    public final MarkerOptions visible(boolean z) {
        this.zzcs = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getPosition(), i, false);
        SafeParcelWriter.writeString(parcel, 3, getTitle(), false);
        SafeParcelWriter.writeString(parcel, 4, getSnippet(), false);
        SafeParcelWriter.writeIBinder(parcel, 5, this.zzdo == null ? null : this.zzdo.zza().asBinder(), false);
        SafeParcelWriter.writeFloat(parcel, 6, getAnchorU());
        SafeParcelWriter.writeFloat(parcel, 7, getAnchorV());
        SafeParcelWriter.writeBoolean(parcel, 8, isDraggable());
        SafeParcelWriter.writeBoolean(parcel, 9, isVisible());
        SafeParcelWriter.writeBoolean(parcel, 10, isFlat());
        SafeParcelWriter.writeFloat(parcel, 11, getRotation());
        SafeParcelWriter.writeFloat(parcel, 12, getInfoWindowAnchorU());
        SafeParcelWriter.writeFloat(parcel, 13, getInfoWindowAnchorV());
        SafeParcelWriter.writeFloat(parcel, 14, getAlpha());
        SafeParcelWriter.writeFloat(parcel, 15, getZIndex());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final MarkerOptions zIndex(float f) {
        this.zzcr = f;
        return this;
    }
}
