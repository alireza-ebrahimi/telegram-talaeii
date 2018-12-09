package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.List;
import org.telegram.ui.ActionBar.Theme;

@Class(creator = "CircleOptionsCreator")
@Reserved({1})
public final class CircleOptions extends AbstractSafeParcelable {
    public static final Creator<CircleOptions> CREATOR = new zzc();
    @Field(getter = "getFillColor", id = 6)
    private int fillColor = 0;
    @Field(getter = "getStrokeColor", id = 5)
    private int strokeColor = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
    @Field(getter = "getCenter", id = 2)
    private LatLng zzco = null;
    @Field(getter = "getRadius", id = 3)
    private double zzcp = 0.0d;
    @Field(getter = "getStrokeWidth", id = 4)
    private float zzcq = 10.0f;
    @Field(getter = "getZIndex", id = 7)
    private float zzcr = BitmapDescriptorFactory.HUE_RED;
    @Field(getter = "isVisible", id = 8)
    private boolean zzcs = true;
    @Field(getter = "isClickable", id = 9)
    private boolean zzct = false;
    @Field(getter = "getStrokePattern", id = 10)
    private List<PatternItem> zzcu = null;

    @Constructor
    CircleOptions(@Param(id = 2) LatLng latLng, @Param(id = 3) double d, @Param(id = 4) float f, @Param(id = 5) int i, @Param(id = 6) int i2, @Param(id = 7) float f2, @Param(id = 8) boolean z, @Param(id = 9) boolean z2, @Param(id = 10) List<PatternItem> list) {
        this.zzco = latLng;
        this.zzcp = d;
        this.zzcq = f;
        this.strokeColor = i;
        this.fillColor = i2;
        this.zzcr = f2;
        this.zzcs = z;
        this.zzct = z2;
        this.zzcu = list;
    }

    public final CircleOptions center(LatLng latLng) {
        this.zzco = latLng;
        return this;
    }

    public final CircleOptions clickable(boolean z) {
        this.zzct = z;
        return this;
    }

    public final CircleOptions fillColor(int i) {
        this.fillColor = i;
        return this;
    }

    public final LatLng getCenter() {
        return this.zzco;
    }

    public final int getFillColor() {
        return this.fillColor;
    }

    public final double getRadius() {
        return this.zzcp;
    }

    public final int getStrokeColor() {
        return this.strokeColor;
    }

    public final List<PatternItem> getStrokePattern() {
        return this.zzcu;
    }

    public final float getStrokeWidth() {
        return this.zzcq;
    }

    public final float getZIndex() {
        return this.zzcr;
    }

    public final boolean isClickable() {
        return this.zzct;
    }

    public final boolean isVisible() {
        return this.zzcs;
    }

    public final CircleOptions radius(double d) {
        this.zzcp = d;
        return this;
    }

    public final CircleOptions strokeColor(int i) {
        this.strokeColor = i;
        return this;
    }

    public final CircleOptions strokePattern(List<PatternItem> list) {
        this.zzcu = list;
        return this;
    }

    public final CircleOptions strokeWidth(float f) {
        this.zzcq = f;
        return this;
    }

    public final CircleOptions visible(boolean z) {
        this.zzcs = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getCenter(), i, false);
        SafeParcelWriter.writeDouble(parcel, 3, getRadius());
        SafeParcelWriter.writeFloat(parcel, 4, getStrokeWidth());
        SafeParcelWriter.writeInt(parcel, 5, getStrokeColor());
        SafeParcelWriter.writeInt(parcel, 6, getFillColor());
        SafeParcelWriter.writeFloat(parcel, 7, getZIndex());
        SafeParcelWriter.writeBoolean(parcel, 8, isVisible());
        SafeParcelWriter.writeBoolean(parcel, 9, isClickable());
        SafeParcelWriter.writeTypedList(parcel, 10, getStrokePattern(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final CircleOptions zIndex(float f) {
        this.zzcr = f;
        return this;
    }
}
