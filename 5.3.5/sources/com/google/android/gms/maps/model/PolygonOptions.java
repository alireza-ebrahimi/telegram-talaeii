package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions extends zzbgl {
    @Hide
    public static final Creator<PolygonOptions> CREATOR = new zzk();
    private int fillColor;
    private int strokeColor;
    private float zzjcz;
    private float zzjda;
    private boolean zzjdb;
    private boolean zzjdc;
    @Nullable
    private List<PatternItem> zzjdd;
    private final List<LatLng> zzjee;
    private final List<List<LatLng>> zzjef;
    private boolean zzjeg;
    private int zzjeh;

    public PolygonOptions() {
        this.zzjcz = 10.0f;
        this.strokeColor = -16777216;
        this.fillColor = 0;
        this.zzjda = 0.0f;
        this.zzjdb = true;
        this.zzjeg = false;
        this.zzjdc = false;
        this.zzjeh = 0;
        this.zzjdd = null;
        this.zzjee = new ArrayList();
        this.zzjef = new ArrayList();
    }

    @Hide
    PolygonOptions(List<LatLng> list, List list2, float f, int i, int i2, float f2, boolean z, boolean z2, boolean z3, int i3, @Nullable List<PatternItem> list3) {
        this.zzjcz = 10.0f;
        this.strokeColor = -16777216;
        this.fillColor = 0;
        this.zzjda = 0.0f;
        this.zzjdb = true;
        this.zzjeg = false;
        this.zzjdc = false;
        this.zzjeh = 0;
        this.zzjdd = null;
        this.zzjee = list;
        this.zzjef = list2;
        this.zzjcz = f;
        this.strokeColor = i;
        this.fillColor = i2;
        this.zzjda = f2;
        this.zzjdb = z;
        this.zzjeg = z2;
        this.zzjdc = z3;
        this.zzjeh = i3;
        this.zzjdd = list3;
    }

    public final PolygonOptions add(LatLng latLng) {
        this.zzjee.add(latLng);
        return this;
    }

    public final PolygonOptions add(LatLng... latLngArr) {
        this.zzjee.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public final PolygonOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.zzjee.add(add);
        }
        return this;
    }

    public final PolygonOptions addHole(Iterable<LatLng> iterable) {
        ArrayList arrayList = new ArrayList();
        for (LatLng add : iterable) {
            arrayList.add(add);
        }
        this.zzjef.add(arrayList);
        return this;
    }

    public final PolygonOptions clickable(boolean z) {
        this.zzjdc = z;
        return this;
    }

    public final PolygonOptions fillColor(int i) {
        this.fillColor = i;
        return this;
    }

    public final PolygonOptions geodesic(boolean z) {
        this.zzjeg = z;
        return this;
    }

    public final int getFillColor() {
        return this.fillColor;
    }

    public final List<List<LatLng>> getHoles() {
        return this.zzjef;
    }

    public final List<LatLng> getPoints() {
        return this.zzjee;
    }

    public final int getStrokeColor() {
        return this.strokeColor;
    }

    public final int getStrokeJointType() {
        return this.zzjeh;
    }

    @Nullable
    public final List<PatternItem> getStrokePattern() {
        return this.zzjdd;
    }

    public final float getStrokeWidth() {
        return this.zzjcz;
    }

    public final float getZIndex() {
        return this.zzjda;
    }

    public final boolean isClickable() {
        return this.zzjdc;
    }

    public final boolean isGeodesic() {
        return this.zzjeg;
    }

    public final boolean isVisible() {
        return this.zzjdb;
    }

    public final PolygonOptions strokeColor(int i) {
        this.strokeColor = i;
        return this;
    }

    public final PolygonOptions strokeJointType(int i) {
        this.zzjeh = i;
        return this;
    }

    public final PolygonOptions strokePattern(@Nullable List<PatternItem> list) {
        this.zzjdd = list;
        return this;
    }

    public final PolygonOptions strokeWidth(float f) {
        this.zzjcz = f;
        return this;
    }

    public final PolygonOptions visible(boolean z) {
        this.zzjdb = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, getPoints(), false);
        zzbgo.zzd(parcel, 3, this.zzjef, false);
        zzbgo.zza(parcel, 4, getStrokeWidth());
        zzbgo.zzc(parcel, 5, getStrokeColor());
        zzbgo.zzc(parcel, 6, getFillColor());
        zzbgo.zza(parcel, 7, getZIndex());
        zzbgo.zza(parcel, 8, isVisible());
        zzbgo.zza(parcel, 9, isGeodesic());
        zzbgo.zza(parcel, 10, isClickable());
        zzbgo.zzc(parcel, 11, getStrokeJointType());
        zzbgo.zzc(parcel, 12, getStrokePattern(), false);
        zzbgo.zzai(parcel, zze);
    }

    public final PolygonOptions zIndex(float f) {
        this.zzjda = f;
        return this;
    }
}
