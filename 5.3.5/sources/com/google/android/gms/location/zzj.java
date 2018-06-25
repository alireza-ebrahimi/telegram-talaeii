package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

@Hide
public final class zzj extends zzbgl {
    public static final Creator<zzj> CREATOR = new zzk();
    @Hide
    private boolean zzirh;
    @Hide
    private long zziri;
    @Hide
    private float zzirj;
    @Hide
    private long zzirk;
    @Hide
    private int zzirl;

    public zzj() {
        this(true, 50, 0.0f, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Hide
    zzj(boolean z, long j, float f, long j2, int i) {
        this.zzirh = z;
        this.zziri = j;
        this.zzirj = f;
        this.zzirk = j2;
        this.zzirl = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzj)) {
            return false;
        }
        zzj zzj = (zzj) obj;
        return this.zzirh == zzj.zzirh && this.zziri == zzj.zziri && Float.compare(this.zzirj, zzj.zzirj) == 0 && this.zzirk == zzj.zzirk && this.zzirl == zzj.zzirl;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.zzirh), Long.valueOf(this.zziri), Float.valueOf(this.zzirj), Long.valueOf(this.zzirk), Integer.valueOf(this.zzirl)});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeviceOrientationRequest[mShouldUseMag=").append(this.zzirh);
        stringBuilder.append(" mMinimumSamplingPeriodMs=").append(this.zziri);
        stringBuilder.append(" mSmallestAngleChangeRadians=").append(this.zzirj);
        if (this.zzirk != Long.MAX_VALUE) {
            long elapsedRealtime = this.zzirk - SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append(elapsedRealtime).append("ms");
        }
        if (this.zzirl != Integer.MAX_VALUE) {
            stringBuilder.append(" num=").append(this.zzirl);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzirh);
        zzbgo.zza(parcel, 2, this.zziri);
        zzbgo.zza(parcel, 3, this.zzirj);
        zzbgo.zza(parcel, 4, this.zzirk);
        zzbgo.zzc(parcel, 5, this.zzirl);
        zzbgo.zzai(parcel, zze);
    }
}
