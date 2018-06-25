package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;

public final class zzcnl extends zzbgl {
    public static final Creator<zzcnl> CREATOR = new zzcnm();
    public final String name;
    private int versionCode;
    private String zzgim;
    public final String zzjgm;
    public final long zzjsi;
    private Long zzjsj;
    private Float zzjsk;
    private Double zzjsl;

    zzcnl(int i, String str, long j, Long l, Float f, String str2, String str3, Double d) {
        Double d2 = null;
        this.versionCode = i;
        this.name = str;
        this.zzjsi = j;
        this.zzjsj = l;
        this.zzjsk = null;
        if (i == 1) {
            if (f != null) {
                d2 = Double.valueOf(f.doubleValue());
            }
            this.zzjsl = d2;
        } else {
            this.zzjsl = d;
        }
        this.zzgim = str2;
        this.zzjgm = str3;
    }

    zzcnl(zzcnn zzcnn) {
        this(zzcnn.name, zzcnn.zzjsi, zzcnn.value, zzcnn.zzjgm);
    }

    zzcnl(String str, long j, Object obj, String str2) {
        zzbq.zzgv(str);
        this.versionCode = 2;
        this.name = str;
        this.zzjsi = j;
        this.zzjgm = str2;
        if (obj == null) {
            this.zzjsj = null;
            this.zzjsk = null;
            this.zzjsl = null;
            this.zzgim = null;
        } else if (obj instanceof Long) {
            this.zzjsj = (Long) obj;
            this.zzjsk = null;
            this.zzjsl = null;
            this.zzgim = null;
        } else if (obj instanceof String) {
            this.zzjsj = null;
            this.zzjsk = null;
            this.zzjsl = null;
            this.zzgim = (String) obj;
        } else if (obj instanceof Double) {
            this.zzjsj = null;
            this.zzjsk = null;
            this.zzjsl = (Double) obj;
            this.zzgim = null;
        } else {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
    }

    public final Object getValue() {
        return this.zzjsj != null ? this.zzjsj : this.zzjsl != null ? this.zzjsl : this.zzgim != null ? this.zzgim : null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zza(parcel, 2, this.name, false);
        zzbgo.zza(parcel, 3, this.zzjsi);
        zzbgo.zza(parcel, 4, this.zzjsj, false);
        zzbgo.zza(parcel, 5, null, false);
        zzbgo.zza(parcel, 6, this.zzgim, false);
        zzbgo.zza(parcel, 7, this.zzjgm, false);
        zzbgo.zza(parcel, 8, this.zzjsl, false);
        zzbgo.zzai(parcel, zze);
    }
}
