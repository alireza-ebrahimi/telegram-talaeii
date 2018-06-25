package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;

@Class(creator = "GroundOverlayOptionsCreator")
@Reserved({1})
public final class GroundOverlayOptions extends AbstractSafeParcelable {
    public static final Creator<GroundOverlayOptions> CREATOR = new zzd();
    public static final float NO_DIMENSION = -1.0f;
    @Field(getter = "getBearing", id = 7)
    private float bearing;
    @Field(getter = "getHeight", id = 5)
    private float height;
    @Field(getter = "getWidth", id = 4)
    private float width;
    @Field(getter = "getZIndex", id = 8)
    private float zzcr;
    @Field(getter = "isVisible", id = 9)
    private boolean zzcs = true;
    @Field(getter = "isClickable", id = 13)
    private boolean zzct = false;
    @Field(getter = "getWrappedImageDescriptorImplBinder", id = 2, type = "android.os.IBinder")
    private BitmapDescriptor zzcw;
    @Field(getter = "getLocation", id = 3)
    private LatLng zzcx;
    @Field(getter = "getBounds", id = 6)
    private LatLngBounds zzcy;
    @Field(getter = "getTransparency", id = 10)
    private float zzcz = BitmapDescriptorFactory.HUE_RED;
    @Field(getter = "getAnchorU", id = 11)
    private float zzda = 0.5f;
    @Field(getter = "getAnchorV", id = 12)
    private float zzdb = 0.5f;

    @Constructor
    GroundOverlayOptions(@Param(id = 2) IBinder iBinder, @Param(id = 3) LatLng latLng, @Param(id = 4) float f, @Param(id = 5) float f2, @Param(id = 6) LatLngBounds latLngBounds, @Param(id = 7) float f3, @Param(id = 8) float f4, @Param(id = 9) boolean z, @Param(id = 10) float f5, @Param(id = 11) float f6, @Param(id = 12) float f7, @Param(id = 13) boolean z2) {
        this.zzcw = new BitmapDescriptor(Stub.asInterface(iBinder));
        this.zzcx = latLng;
        this.width = f;
        this.height = f2;
        this.zzcy = latLngBounds;
        this.bearing = f3;
        this.zzcr = f4;
        this.zzcs = z;
        this.zzcz = f5;
        this.zzda = f6;
        this.zzdb = f7;
        this.zzct = z2;
    }

    private final GroundOverlayOptions zza(LatLng latLng, float f, float f2) {
        this.zzcx = latLng;
        this.width = f;
        this.height = f2;
        return this;
    }

    public final GroundOverlayOptions anchor(float f, float f2) {
        this.zzda = f;
        this.zzdb = f2;
        return this;
    }

    public final GroundOverlayOptions bearing(float f) {
        this.bearing = ((f % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    public final GroundOverlayOptions clickable(boolean z) {
        this.zzct = z;
        return this;
    }

    public final float getAnchorU() {
        return this.zzda;
    }

    public final float getAnchorV() {
        return this.zzdb;
    }

    public final float getBearing() {
        return this.bearing;
    }

    public final LatLngBounds getBounds() {
        return this.zzcy;
    }

    public final float getHeight() {
        return this.height;
    }

    public final BitmapDescriptor getImage() {
        return this.zzcw;
    }

    public final LatLng getLocation() {
        return this.zzcx;
    }

    public final float getTransparency() {
        return this.zzcz;
    }

    public final float getWidth() {
        return this.width;
    }

    public final float getZIndex() {
        return this.zzcr;
    }

    public final GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        Preconditions.checkNotNull(bitmapDescriptor, "imageDescriptor must not be null");
        this.zzcw = bitmapDescriptor;
        return this;
    }

    public final boolean isClickable() {
        return this.zzct;
    }

    public final boolean isVisible() {
        return this.zzcs;
    }

    public final GroundOverlayOptions position(LatLng latLng, float f) {
        boolean z = true;
        Preconditions.checkState(this.zzcy == null, "Position has already been set using positionFromBounds");
        Preconditions.checkArgument(latLng != null, "Location must be specified");
        if (f < BitmapDescriptorFactory.HUE_RED) {
            z = false;
        }
        Preconditions.checkArgument(z, "Width must be non-negative");
        return zza(latLng, f, -1.0f);
    }

    public final GroundOverlayOptions position(LatLng latLng, float f, float f2) {
        boolean z = true;
        Preconditions.checkState(this.zzcy == null, "Position has already been set using positionFromBounds");
        Preconditions.checkArgument(latLng != null, "Location must be specified");
        Preconditions.checkArgument(f >= BitmapDescriptorFactory.HUE_RED, "Width must be non-negative");
        if (f2 < BitmapDescriptorFactory.HUE_RED) {
            z = false;
        }
        Preconditions.checkArgument(z, "Height must be non-negative");
        return zza(latLng, f, f2);
    }

    public final GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        boolean z = this.zzcx == null;
        String valueOf = String.valueOf(this.zzcx);
        Preconditions.checkState(z, new StringBuilder(String.valueOf(valueOf).length() + 46).append("Position has already been set using position: ").append(valueOf).toString());
        this.zzcy = latLngBounds;
        return this;
    }

    public final GroundOverlayOptions transparency(float f) {
        boolean z = f >= BitmapDescriptorFactory.HUE_RED && f <= 1.0f;
        Preconditions.checkArgument(z, "Transparency must be in the range [0..1]");
        this.zzcz = f;
        return this;
    }

    public final GroundOverlayOptions visible(boolean z) {
        this.zzcs = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zzcw.zza().asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getLocation(), i, false);
        SafeParcelWriter.writeFloat(parcel, 4, getWidth());
        SafeParcelWriter.writeFloat(parcel, 5, getHeight());
        SafeParcelWriter.writeParcelable(parcel, 6, getBounds(), i, false);
        SafeParcelWriter.writeFloat(parcel, 7, getBearing());
        SafeParcelWriter.writeFloat(parcel, 8, getZIndex());
        SafeParcelWriter.writeBoolean(parcel, 9, isVisible());
        SafeParcelWriter.writeFloat(parcel, 10, getTransparency());
        SafeParcelWriter.writeFloat(parcel, 11, getAnchorU());
        SafeParcelWriter.writeFloat(parcel, 12, getAnchorV());
        SafeParcelWriter.writeBoolean(parcel, 13, isClickable());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final GroundOverlayOptions zIndex(float f) {
        this.zzcr = f;
        return this;
    }
}
