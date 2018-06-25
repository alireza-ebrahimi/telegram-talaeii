package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbt;

public final class zzcyw extends zzbgl {
    public static final Creator<zzcyw> CREATOR = new zzcyx();
    private int zzehz;
    private final ConnectionResult zzfuw;
    private final zzbt zzklz;

    public zzcyw(int i) {
        this(new ConnectionResult(8, null), null);
    }

    zzcyw(int i, ConnectionResult connectionResult, zzbt zzbt) {
        this.zzehz = i;
        this.zzfuw = connectionResult;
        this.zzklz = zzbt;
    }

    private zzcyw(ConnectionResult connectionResult, zzbt zzbt) {
        this(1, connectionResult, null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, this.zzfuw, i, false);
        zzbgo.zza(parcel, 3, this.zzklz, i, false);
        zzbgo.zzai(parcel, zze);
    }

    public final ConnectionResult zzain() {
        return this.zzfuw;
    }

    public final zzbt zzbfa() {
        return this.zzklz;
    }
}
