package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions extends zzbgl {
    @Hide
    public static final Creator<PolylineOptions> CREATOR = new zzl();
    private int color;
    private float width;
    private float zzjda;
    private boolean zzjdb;
    private boolean zzjdc;
    private final List<LatLng> zzjee;
    private boolean zzjeg;
    @NonNull
    private Cap zzjej;
    @NonNull
    private Cap zzjek;
    private int zzjel;
    @Nullable
    private List<PatternItem> zzjem;

    public PolylineOptions() {
        this.width = 10.0f;
        this.color = -16777216;
        this.zzjda = 0.0f;
        this.zzjdb = true;
        this.zzjeg = false;
        this.zzjdc = false;
        this.zzjej = new ButtCap();
        this.zzjek = new ButtCap();
        this.zzjel = 0;
        this.zzjem = null;
        this.zzjee = new ArrayList();
    }

    @Hide
    PolylineOptions(List list, float f, int i, float f2, boolean z, boolean z2, boolean z3, @Nullable Cap cap, @Nullable Cap cap2, int i2, @Nullable List<PatternItem> list2) {
        this.width = 10.0f;
        this.color = -16777216;
        this.zzjda = 0.0f;
        this.zzjdb = true;
        this.zzjeg = false;
        this.zzjdc = false;
        this.zzjej = new ButtCap();
        this.zzjek = new ButtCap();
        this.zzjel = 0;
        this.zzjem = null;
        this.zzjee = list;
        this.width = f;
        this.color = i;
        this.zzjda = f2;
        this.zzjdb = z;
        this.zzjeg = z2;
        this.zzjdc = z3;
        if (cap != null) {
            this.zzjej = cap;
        }
        if (cap2 != null) {
            this.zzjek = cap2;
        }
        this.zzjel = i2;
        this.zzjem = list2;
    }

    public final PolylineOptions add(LatLng latLng) {
        this.zzjee.add(latLng);
        return this;
    }

    public final PolylineOptions add(LatLng... latLngArr) {
        this.zzjee.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public final PolylineOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.zzjee.add(add);
        }
        return this;
    }

    public final PolylineOptions clickable(boolean z) {
        this.zzjdc = z;
        return this;
    }

    public final PolylineOptions color(int i) {
        this.color = i;
        return this;
    }

    public final PolylineOptions endCap(@NonNull Cap cap) {
        this.zzjek = (Cap) zzbq.checkNotNull(cap, "endCap must not be null");
        return this;
    }

    public final PolylineOptions geodesic(boolean z) {
        this.zzjeg = z;
        return this;
    }

    public final int getColor() {
        return this.color;
    }

    @NonNull
    public final Cap getEndCap() {
        return this.zzjek;
    }

    public final int getJointType() {
        return this.zzjel;
    }

    @Nullable
    public final List<PatternItem> getPattern() {
        return this.zzjem;
    }

    public final List<LatLng> getPoints() {
        return this.zzjee;
    }

    @NonNull
    public final Cap getStartCap() {
        return this.zzjej;
    }

    public final float getWidth() {
        return this.width;
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

    public final PolylineOptions jointType(int i) {
        this.zzjel = i;
        return this;
    }

    public final PolylineOptions pattern(@Nullable List<PatternItem> list) {
        this.zzjem = list;
        return this;
    }

    public final PolylineOptions startCap(@NonNull Cap cap) {
        this.zzjej = (Cap) zzbq.checkNotNull(cap, "startCap must not be null");
        return this;
    }

    public final PolylineOptions visible(boolean z) {
        this.zzjdb = z;
        return this;
    }

    public final PolylineOptions width(float f) {
        this.width = f;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, getPoints(), false);
        zzbgo.zza(parcel, 3, getWidth());
        zzbgo.zzc(parcel, 4, getColor());
        zzbgo.zza(parcel, 5, getZIndex());
        zzbgo.zza(parcel, 6, isVisible());
        zzbgo.zza(parcel, 7, isGeodesic());
        zzbgo.zza(parcel, 8, isClickable());
        zzbgo.zza(parcel, 9, getStartCap(), i, false);
        zzbgo.zza(parcel, 10, getEndCap(), i, false);
        zzbgo.zzc(parcel, 11, getJointType());
        zzbgo.zzc(parcel, 12, getPattern(), false);
        zzbgo.zzai(parcel, zze);
    }

    public final PolylineOptions zIndex(float f) {
        this.zzjda = f;
        return this;
    }
}
