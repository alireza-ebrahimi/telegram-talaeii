package com.google.android.gms.maps.model;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.ui.ActionBar.Theme;

@Class(creator = "PolylineOptionsCreator")
@Reserved({1})
public final class PolylineOptions extends AbstractSafeParcelable {
    public static final Creator<PolylineOptions> CREATOR = new zzl();
    @Field(getter = "getColor", id = 4)
    private int color;
    @Field(getter = "getWidth", id = 3)
    private float width;
    @Field(getter = "getZIndex", id = 5)
    private float zzcr;
    @Field(getter = "isVisible", id = 6)
    private boolean zzcs;
    @Field(getter = "isClickable", id = 8)
    private boolean zzct;
    @Field(getter = "getPoints", id = 2)
    private final List<LatLng> zzdw;
    @Field(getter = "isGeodesic", id = 7)
    private boolean zzdy;
    @Field(getter = "getStartCap", id = 9)
    private Cap zzeb;
    @Field(getter = "getEndCap", id = 10)
    private Cap zzec;
    @Field(getter = "getJointType", id = 11)
    private int zzed;
    @Field(getter = "getPattern", id = 12)
    private List<PatternItem> zzee;

    public PolylineOptions() {
        this.width = 10.0f;
        this.color = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.zzcr = BitmapDescriptorFactory.HUE_RED;
        this.zzcs = true;
        this.zzdy = false;
        this.zzct = false;
        this.zzeb = new ButtCap();
        this.zzec = new ButtCap();
        this.zzed = 0;
        this.zzee = null;
        this.zzdw = new ArrayList();
    }

    @Constructor
    PolylineOptions(@Param(id = 2) List list, @Param(id = 3) float f, @Param(id = 4) int i, @Param(id = 5) float f2, @Param(id = 6) boolean z, @Param(id = 7) boolean z2, @Param(id = 8) boolean z3, @Param(id = 9) Cap cap, @Param(id = 10) Cap cap2, @Param(id = 11) int i2, @Param(id = 12) List<PatternItem> list2) {
        this.width = 10.0f;
        this.color = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.zzcr = BitmapDescriptorFactory.HUE_RED;
        this.zzcs = true;
        this.zzdy = false;
        this.zzct = false;
        this.zzeb = new ButtCap();
        this.zzec = new ButtCap();
        this.zzed = 0;
        this.zzee = null;
        this.zzdw = list;
        this.width = f;
        this.color = i;
        this.zzcr = f2;
        this.zzcs = z;
        this.zzdy = z2;
        this.zzct = z3;
        if (cap != null) {
            this.zzeb = cap;
        }
        if (cap2 != null) {
            this.zzec = cap2;
        }
        this.zzed = i2;
        this.zzee = list2;
    }

    public final PolylineOptions add(LatLng latLng) {
        this.zzdw.add(latLng);
        return this;
    }

    public final PolylineOptions add(LatLng... latLngArr) {
        this.zzdw.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public final PolylineOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.zzdw.add(add);
        }
        return this;
    }

    public final PolylineOptions clickable(boolean z) {
        this.zzct = z;
        return this;
    }

    public final PolylineOptions color(int i) {
        this.color = i;
        return this;
    }

    public final PolylineOptions endCap(Cap cap) {
        this.zzec = (Cap) Preconditions.checkNotNull(cap, "endCap must not be null");
        return this;
    }

    public final PolylineOptions geodesic(boolean z) {
        this.zzdy = z;
        return this;
    }

    public final int getColor() {
        return this.color;
    }

    public final Cap getEndCap() {
        return this.zzec;
    }

    public final int getJointType() {
        return this.zzed;
    }

    public final List<PatternItem> getPattern() {
        return this.zzee;
    }

    public final List<LatLng> getPoints() {
        return this.zzdw;
    }

    public final Cap getStartCap() {
        return this.zzeb;
    }

    public final float getWidth() {
        return this.width;
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

    public final PolylineOptions jointType(int i) {
        this.zzed = i;
        return this;
    }

    public final PolylineOptions pattern(List<PatternItem> list) {
        this.zzee = list;
        return this;
    }

    public final PolylineOptions startCap(Cap cap) {
        this.zzeb = (Cap) Preconditions.checkNotNull(cap, "startCap must not be null");
        return this;
    }

    public final PolylineOptions visible(boolean z) {
        this.zzcs = z;
        return this;
    }

    public final PolylineOptions width(float f) {
        this.width = f;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, getPoints(), false);
        SafeParcelWriter.writeFloat(parcel, 3, getWidth());
        SafeParcelWriter.writeInt(parcel, 4, getColor());
        SafeParcelWriter.writeFloat(parcel, 5, getZIndex());
        SafeParcelWriter.writeBoolean(parcel, 6, isVisible());
        SafeParcelWriter.writeBoolean(parcel, 7, isGeodesic());
        SafeParcelWriter.writeBoolean(parcel, 8, isClickable());
        SafeParcelWriter.writeParcelable(parcel, 9, getStartCap(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 10, getEndCap(), i, false);
        SafeParcelWriter.writeInt(parcel, 11, getJointType());
        SafeParcelWriter.writeTypedList(parcel, 12, getPattern(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final PolylineOptions zIndex(float f) {
        this.zzcr = f;
        return this;
    }
}
