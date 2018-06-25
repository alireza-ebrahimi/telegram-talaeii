package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public final class zzc extends zzbgl {
    public static final Creator<zzc> CREATOR = new zzd();
    private boolean zzfpw;
    private long zzfpx;
    private long zzfpy;

    public zzc(boolean z, long j, long j2) {
        this.zzfpw = z;
        this.zzfpx = j;
        this.zzfpy = j2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc zzc = (zzc) obj;
        return this.zzfpw == zzc.zzfpw && this.zzfpx == zzc.zzfpx && this.zzfpy == zzc.zzfpy;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.zzfpw), Long.valueOf(this.zzfpx), Long.valueOf(this.zzfpy)});
    }

    public final String toString() {
        return "CollectForDebugParcelable[skipPersistentStorage: " + this.zzfpw + ",collectForDebugStartTimeMillis: " + this.zzfpx + ",collectForDebugExpiryTimeMillis: " + this.zzfpy + "]";
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzfpw);
        zzbgo.zza(parcel, 2, this.zzfpy);
        zzbgo.zza(parcel, 3, this.zzfpx);
        zzbgo.zzai(parcel, zze);
    }
}
