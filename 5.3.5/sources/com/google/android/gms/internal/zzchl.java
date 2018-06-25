package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

@Hide
public final class zzchl extends zzbgl {
    public static final Creator<zzchl> CREATOR = new zzchm();
    static final List<zzcfs> zzitm = Collections.emptyList();
    @Nullable
    private String mTag;
    @Nullable
    private String zzeqs;
    private List<zzcfs> zzira;
    private LocationRequest zzium;
    private boolean zziun;
    private boolean zziuo;
    private boolean zziup;
    private boolean zziuq = true;

    zzchl(LocationRequest locationRequest, List<zzcfs> list, @Nullable String str, boolean z, boolean z2, boolean z3, String str2) {
        this.zzium = locationRequest;
        this.zzira = list;
        this.mTag = str;
        this.zziun = z;
        this.zziuo = z2;
        this.zziup = z3;
        this.zzeqs = str2;
    }

    @Deprecated
    public static zzchl zza(LocationRequest locationRequest) {
        return new zzchl(locationRequest, zzitm, null, false, false, false, null);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzchl)) {
            return false;
        }
        zzchl zzchl = (zzchl) obj;
        return zzbg.equal(this.zzium, zzchl.zzium) && zzbg.equal(this.zzira, zzchl.zzira) && zzbg.equal(this.mTag, zzchl.mTag) && this.zziun == zzchl.zziun && this.zziuo == zzchl.zziuo && this.zziup == zzchl.zziup && zzbg.equal(this.zzeqs, zzchl.zzeqs);
    }

    public final int hashCode() {
        return this.zzium.hashCode();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.zzium.toString());
        if (this.mTag != null) {
            stringBuilder.append(" tag=").append(this.mTag);
        }
        if (this.zzeqs != null) {
            stringBuilder.append(" moduleId=").append(this.zzeqs);
        }
        stringBuilder.append(" hideAppOps=").append(this.zziun);
        stringBuilder.append(" clients=").append(this.zzira);
        stringBuilder.append(" forceCoarseLocation=").append(this.zziuo);
        if (this.zziup) {
            stringBuilder.append(" exemptFromBackgroundThrottle");
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzium, i, false);
        zzbgo.zzc(parcel, 5, this.zzira, false);
        zzbgo.zza(parcel, 6, this.mTag, false);
        zzbgo.zza(parcel, 7, this.zziun);
        zzbgo.zza(parcel, 8, this.zziuo);
        zzbgo.zza(parcel, 9, this.zziup);
        zzbgo.zza(parcel, 10, this.zzeqs, false);
        zzbgo.zzai(parcel, zze);
    }
}
