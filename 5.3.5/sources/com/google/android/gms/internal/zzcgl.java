package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzcgl extends zzbgl implements Result {
    public static final Creator<zzcgl> CREATOR = new zzcgm();
    @Hide
    private static zzcgl zzitz = new zzcgl(Status.zzftq);
    private final Status mStatus;

    @Hide
    public zzcgl(Status status) {
        this.mStatus = status;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, getStatus(), i, false);
        zzbgo.zzai(parcel, zze);
    }
}
