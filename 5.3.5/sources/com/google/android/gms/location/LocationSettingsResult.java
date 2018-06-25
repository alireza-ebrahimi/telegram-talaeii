package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class LocationSettingsResult extends zzbgl implements Result {
    public static final Creator<LocationSettingsResult> CREATOR = new zzah();
    private final Status mStatus;
    private final LocationSettingsStates zzisu;

    @Hide
    public LocationSettingsResult(Status status) {
        this(status, null);
    }

    @Hide
    public LocationSettingsResult(Status status, LocationSettingsStates locationSettingsStates) {
        this.mStatus = status;
        this.zzisu = locationSettingsStates;
    }

    public final LocationSettingsStates getLocationSettingsStates() {
        return this.zzisu;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, getStatus(), i, false);
        zzbgo.zza(parcel, 2, getLocationSettingsStates(), i, false);
        zzbgo.zzai(parcel, zze);
    }
}
