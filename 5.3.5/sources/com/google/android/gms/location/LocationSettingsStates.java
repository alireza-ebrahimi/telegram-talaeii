package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzbgq;

public final class LocationSettingsStates extends zzbgl {
    public static final Creator<LocationSettingsStates> CREATOR = new zzai();
    private final boolean zzisv;
    private final boolean zzisw;
    private final boolean zzisx;
    private final boolean zzisy;
    private final boolean zzisz;
    private final boolean zzita;

    @Hide
    public LocationSettingsStates(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.zzisv = z;
        this.zzisw = z2;
        this.zzisx = z3;
        this.zzisy = z4;
        this.zzisz = z5;
        this.zzita = z6;
    }

    public static LocationSettingsStates fromIntent(Intent intent) {
        return (LocationSettingsStates) zzbgq.zza(intent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
    }

    public final boolean isBlePresent() {
        return this.zzita;
    }

    public final boolean isBleUsable() {
        return this.zzisx;
    }

    public final boolean isGpsPresent() {
        return this.zzisy;
    }

    public final boolean isGpsUsable() {
        return this.zzisv;
    }

    public final boolean isLocationPresent() {
        return this.zzisy || this.zzisz;
    }

    public final boolean isLocationUsable() {
        return this.zzisv || this.zzisw;
    }

    public final boolean isNetworkLocationPresent() {
        return this.zzisz;
    }

    public final boolean isNetworkLocationUsable() {
        return this.zzisw;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, isGpsUsable());
        zzbgo.zza(parcel, 2, isNetworkLocationUsable());
        zzbgo.zza(parcel, 3, isBleUsable());
        zzbgo.zza(parcel, 4, isGpsPresent());
        zzbgo.zza(parcel, 5, isNetworkLocationPresent());
        zzbgo.zza(parcel, 6, isBlePresent());
        zzbgo.zzai(parcel, zze);
    }
}
