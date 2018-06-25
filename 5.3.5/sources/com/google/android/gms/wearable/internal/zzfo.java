package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.Node;

public final class zzfo extends zzbgl implements Node {
    public static final Creator<zzfo> CREATOR = new zzfp();
    private final String zzbzd;
    private final String zzemi;
    private final int zzlvd;
    private final boolean zzlve;

    public zzfo(String str, String str2, int i, boolean z) {
        this.zzbzd = str;
        this.zzemi = str2;
        this.zzlvd = i;
        this.zzlve = z;
    }

    public final boolean equals(Object obj) {
        return !(obj instanceof zzfo) ? false : ((zzfo) obj).zzbzd.equals(this.zzbzd);
    }

    public final String getDisplayName() {
        return this.zzemi;
    }

    public final String getId() {
        return this.zzbzd;
    }

    public final int hashCode() {
        return this.zzbzd.hashCode();
    }

    public final boolean isNearby() {
        return this.zzlve;
    }

    public final String toString() {
        String str = this.zzemi;
        String str2 = this.zzbzd;
        int i = this.zzlvd;
        return new StringBuilder((String.valueOf(str).length() + 45) + String.valueOf(str2).length()).append("Node{").append(str).append(", id=").append(str2).append(", hops=").append(i).append(", isNearby=").append(this.zzlve).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getId(), false);
        zzbgo.zza(parcel, 3, getDisplayName(), false);
        zzbgo.zzc(parcel, 4, this.zzlvd);
        zzbgo.zza(parcel, 5, isNearby());
        zzbgo.zzai(parcel, zze);
    }
}
