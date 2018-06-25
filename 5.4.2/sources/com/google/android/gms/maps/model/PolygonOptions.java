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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.ui.ActionBar.Theme;

@Class(creator = "PolygonOptionsCreator")
@Reserved({1})
public final class PolygonOptions extends AbstractSafeParcelable {
    public static final Creator<PolygonOptions> CREATOR = new zzk();
    @Field(getter = "getFillColor", id = 6)
    private int fillColor;
    @Field(getter = "getStrokeColor", id = 5)
    private int strokeColor;
    @Field(getter = "getStrokeWidth", id = 4)
    private float zzcq;
    @Field(getter = "getZIndex", id = 7)
    private float zzcr;
    @Field(getter = "isVisible", id = 8)
    private boolean zzcs;
    @Field(getter = "isClickable", id = 10)
    private boolean zzct;
    @Field(getter = "getStrokePattern", id = 12)
    private List<PatternItem> zzcu;
    @Field(getter = "getPoints", id = 2)
    private final List<LatLng> zzdw;
    @Field(getter = "getHolesForParcel", id = 3, type = "java.util.List")
    private final List<List<LatLng>> zzdx;
    @Field(getter = "isGeodesic", id = 9)
    private boolean zzdy;
    @Field(getter = "getStrokeJointType", id = 11)
    private int zzdz;

    public PolygonOptions() {
        this.zzcq = 10.0f;
        this.strokeColor = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.fillColor = 0;
        this.zzcr = BitmapDescriptorFactory.HUE_RED;
        this.zzcs = true;
        this.zzdy = false;
        this.zzct = false;
        this.zzdz = 0;
        this.zzcu = null;
        this.zzdw = new ArrayList();
        this.zzdx = new ArrayList();
    }

    @Constructor
    PolygonOptions(@Param(id = 2) List<LatLng> list, @Param(id = 3) List list2, @Param(id = 4) float f, @Param(id = 5) int i, @Param(id = 6) int i2, @Param(id = 7) float f2, @Param(id = 8) boolean z, @Param(id = 9) boolean z2, @Param(id = 10) boolean z3, @Param(id = 11) int i3, @Param(id = 12) List<PatternItem> list3) {
        this.zzcq = 10.0f;
        this.strokeColor = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.fillColor = 0;
        this.zzcr = BitmapDescriptorFactory.HUE_RED;
        this.zzcs = true;
        this.zzdy = false;
        this.zzct = false;
        this.zzdz = 0;
        this.zzcu = null;
        this.zzdw = list;
        this.zzdx = list2;
        this.zzcq = f;
        this.strokeColor = i;
        this.fillColor = i2;
        this.zzcr = f2;
        this.zzcs = z;
        this.zzdy = z2;
        this.zzct = z3;
        this.zzdz = i3;
        this.zzcu = list3;
    }

    public final PolygonOptions add(LatLng latLng) {
        this.zzdw.add(latLng);
        return this;
    }

    public final PolygonOptions add(LatLng... latLngArr) {
        this.zzdw.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public final PolygonOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.zzdw.add(add);
        }
        return this;
    }

    public final PolygonOptions addHole(Iterable<LatLng> iterable) {
        ArrayList arrayList = new ArrayList();
        for (LatLng add : iterable) {
            arrayList.add(add);
        }
        this.zzdx.add(arrayList);
        return this;
    }

    public final PolygonOptions clickable(boolean z) {
        this.zzct = z;
        return this;
    }

    public final PolygonOptions fillColor(int i) {
        this.fillColor = i;
        return this;
    }

    public final PolygonOptions geodesic(boolean z) {
        this.zzdy = z;
        return this;
    }

    public final int getFillColor() {
        return this.fillColor;
    }

    public final List<List<LatLng>> getHoles() {
        return this.zzdx;
    }

    public final List<LatLng> getPoints() {
        return this.zzdw;
    }

    public final int getStrokeColor() {
        return this.strokeColor;
    }

    public final int getStrokeJointType() {
        return this.zzdz;
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

    public final boolean isGeodesic() {
        return this.zzdy;
    }

    public final boolean isVisible() {
        return this.zzcs;
    }

    public final PolygonOptions strokeColor(int i) {
        this.strokeColor = i;
        return this;
    }

    public final PolygonOptions strokeJointType(int i) {
        this.zzdz = i;
        return this;
    }

    public final PolygonOptions strokePattern(List<PatternItem> list) {
        this.zzcu = list;
        return this;
    }

    public final PolygonOptions strokeWidth(float f) {
        this.zzcq = f;
        return this;
    }

    public final PolygonOptions visible(boolean z) {
        this.zzcs = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, getPoints(), false);
        SafeParcelWriter.writeList(parcel, 3, this.zzdx, false);
        SafeParcelWriter.writeFloat(parcel, 4, getStrokeWidth());
        SafeParcelWriter.writeInt(parcel, 5, getStrokeColor());
        SafeParcelWriter.writeInt(parcel, 6, getFillColor());
        SafeParcelWriter.writeFloat(parcel, 7, getZIndex());
        SafeParcelWriter.writeBoolean(parcel, 8, isVisible());
        SafeParcelWriter.writeBoolean(parcel, 9, isGeodesic());
        SafeParcelWriter.writeBoolean(parcel, 10, isClickable());
        SafeParcelWriter.writeInt(parcel, 11, getStrokeJointType());
        SafeParcelWriter.writeTypedList(parcel, 12, getStrokePattern(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final PolygonOptions zIndex(float f) {
        this.zzcr = f;
        return this;
    }
}
