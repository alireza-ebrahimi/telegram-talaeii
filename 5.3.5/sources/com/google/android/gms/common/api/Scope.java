package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class Scope extends zzbgl implements ReflectedParcelable {
    public static final Creator<Scope> CREATOR = new zzf();
    private int zzehz;
    private final String zzftp;

    Scope(int i, String str) {
        zzbq.zzh(str, "scopeUri must not be null or empty");
        this.zzehz = i;
        this.zzftp = str;
    }

    public Scope(String str) {
        this(1, str);
    }

    public final boolean equals(Object obj) {
        return this == obj ? true : !(obj instanceof Scope) ? false : this.zzftp.equals(((Scope) obj).zzftp);
    }

    public final int hashCode() {
        return this.zzftp.hashCode();
    }

    public final String toString() {
        return this.zzftp;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, this.zzftp, false);
        zzbgo.zzai(parcel, zze);
    }

    @Hide
    public final String zzaie() {
        return this.zzftp;
    }
}
