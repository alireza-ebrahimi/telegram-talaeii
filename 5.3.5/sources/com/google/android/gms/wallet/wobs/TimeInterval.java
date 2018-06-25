package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class TimeInterval extends zzbgl {
    public static final Creator<TimeInterval> CREATOR = new zzk();
    private long zzlqh;
    private long zzlqi;

    TimeInterval() {
    }

    public TimeInterval(long j, long j2) {
        this.zzlqh = j;
        this.zzlqi = j2;
    }

    public final long getEndTimestamp() {
        return this.zzlqi;
    }

    public final long getStartTimestamp() {
        return this.zzlqh;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlqh);
        zzbgo.zza(parcel, 3, this.zzlqi);
        zzbgo.zzai(parcel, zze);
    }
}
