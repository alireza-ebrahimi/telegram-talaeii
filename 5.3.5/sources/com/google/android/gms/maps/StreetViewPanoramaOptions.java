package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewSource;

public final class StreetViewPanoramaOptions extends zzbgl implements ReflectedParcelable {
    @Hide
    public static final Creator<StreetViewPanoramaOptions> CREATOR = new zzai();
    private String panoId;
    private LatLng position;
    private Boolean zzjav;
    private Boolean zzjba = Boolean.valueOf(true);
    private StreetViewPanoramaCamera zzjcf;
    private Integer zzjcg;
    private Boolean zzjch = Boolean.valueOf(true);
    private Boolean zzjci = Boolean.valueOf(true);
    private Boolean zzjcj = Boolean.valueOf(true);
    private StreetViewSource zzjck = StreetViewSource.DEFAULT;

    @Hide
    StreetViewPanoramaOptions(StreetViewPanoramaCamera streetViewPanoramaCamera, String str, LatLng latLng, Integer num, byte b, byte b2, byte b3, byte b4, byte b5, StreetViewSource streetViewSource) {
        this.zzjcf = streetViewPanoramaCamera;
        this.position = latLng;
        this.zzjcg = num;
        this.panoId = str;
        this.zzjch = zza.zza(b);
        this.zzjba = zza.zza(b2);
        this.zzjci = zza.zza(b3);
        this.zzjcj = zza.zza(b4);
        this.zzjav = zza.zza(b5);
        this.zzjck = streetViewSource;
    }

    public final Boolean getPanningGesturesEnabled() {
        return this.zzjci;
    }

    public final String getPanoramaId() {
        return this.panoId;
    }

    public final LatLng getPosition() {
        return this.position;
    }

    public final Integer getRadius() {
        return this.zzjcg;
    }

    public final StreetViewSource getSource() {
        return this.zzjck;
    }

    public final Boolean getStreetNamesEnabled() {
        return this.zzjcj;
    }

    public final StreetViewPanoramaCamera getStreetViewPanoramaCamera() {
        return this.zzjcf;
    }

    public final Boolean getUseViewLifecycleInFragment() {
        return this.zzjav;
    }

    public final Boolean getUserNavigationEnabled() {
        return this.zzjch;
    }

    public final Boolean getZoomGesturesEnabled() {
        return this.zzjba;
    }

    public final StreetViewPanoramaOptions panningGesturesEnabled(boolean z) {
        this.zzjci = Boolean.valueOf(z);
        return this;
    }

    public final StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.zzjcf = streetViewPanoramaCamera;
        return this;
    }

    public final StreetViewPanoramaOptions panoramaId(String str) {
        this.panoId = str;
        return this;
    }

    public final StreetViewPanoramaOptions position(LatLng latLng) {
        this.position = latLng;
        return this;
    }

    public final StreetViewPanoramaOptions position(LatLng latLng, StreetViewSource streetViewSource) {
        this.position = latLng;
        this.zzjck = streetViewSource;
        return this;
    }

    public final StreetViewPanoramaOptions position(LatLng latLng, Integer num) {
        this.position = latLng;
        this.zzjcg = num;
        return this;
    }

    public final StreetViewPanoramaOptions position(LatLng latLng, Integer num, StreetViewSource streetViewSource) {
        this.position = latLng;
        this.zzjcg = num;
        this.zzjck = streetViewSource;
        return this;
    }

    public final StreetViewPanoramaOptions streetNamesEnabled(boolean z) {
        this.zzjcj = Boolean.valueOf(z);
        return this;
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("PanoramaId", this.panoId).zzg("Position", this.position).zzg("Radius", this.zzjcg).zzg("Source", this.zzjck).zzg("StreetViewPanoramaCamera", this.zzjcf).zzg("UserNavigationEnabled", this.zzjch).zzg("ZoomGesturesEnabled", this.zzjba).zzg("PanningGesturesEnabled", this.zzjci).zzg("StreetNamesEnabled", this.zzjcj).zzg("UseViewLifecycleInFragment", this.zzjav).toString();
    }

    public final StreetViewPanoramaOptions useViewLifecycleInFragment(boolean z) {
        this.zzjav = Boolean.valueOf(z);
        return this;
    }

    public final StreetViewPanoramaOptions userNavigationEnabled(boolean z) {
        this.zzjch = Boolean.valueOf(z);
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getStreetViewPanoramaCamera(), i, false);
        zzbgo.zza(parcel, 3, getPanoramaId(), false);
        zzbgo.zza(parcel, 4, getPosition(), i, false);
        zzbgo.zza(parcel, 5, getRadius(), false);
        zzbgo.zza(parcel, 6, zza.zzc(this.zzjch));
        zzbgo.zza(parcel, 7, zza.zzc(this.zzjba));
        zzbgo.zza(parcel, 8, zza.zzc(this.zzjci));
        zzbgo.zza(parcel, 9, zza.zzc(this.zzjcj));
        zzbgo.zza(parcel, 10, zza.zzc(this.zzjav));
        zzbgo.zza(parcel, 11, getSource(), i, false);
        zzbgo.zzai(parcel, zze);
    }

    public final StreetViewPanoramaOptions zoomGesturesEnabled(boolean z) {
        this.zzjba = Boolean.valueOf(z);
        return this;
    }
}
