package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;

public final class zzcif extends zzbgl {
    public static final Creator<zzcif> CREATOR = new zzcig();
    public final String packageName;
    public final String zzina;
    public final String zzjfl;
    public final String zzjfn;
    public final long zzjfr;
    public final String zzjfs;
    public final long zzjft;
    public final long zzjfu;
    public final boolean zzjfv;
    public final long zzjfw;
    public final boolean zzjfx;
    public final String zzjgi;
    public final boolean zzjgj;
    public final long zzjgk;
    public final int zzjgl;

    zzcif(String str, String str2, String str3, long j, String str4, long j2, long j3, String str5, boolean z, boolean z2, String str6, long j4, long j5, int i, boolean z3) {
        zzbq.zzgv(str);
        this.packageName = str;
        if (TextUtils.isEmpty(str2)) {
            str2 = null;
        }
        this.zzjfl = str2;
        this.zzina = str3;
        this.zzjfr = j;
        this.zzjfs = str4;
        this.zzjft = j2;
        this.zzjfu = j3;
        this.zzjgi = str5;
        this.zzjfv = z;
        this.zzjgj = z2;
        this.zzjfn = str6;
        this.zzjfw = j4;
        this.zzjgk = j5;
        this.zzjgl = i;
        this.zzjfx = z3;
    }

    zzcif(String str, String str2, String str3, String str4, long j, long j2, String str5, boolean z, boolean z2, long j3, String str6, long j4, long j5, int i, boolean z3) {
        this.packageName = str;
        this.zzjfl = str2;
        this.zzina = str3;
        this.zzjfr = j3;
        this.zzjfs = str4;
        this.zzjft = j;
        this.zzjfu = j2;
        this.zzjgi = str5;
        this.zzjfv = z;
        this.zzjgj = z2;
        this.zzjfn = str6;
        this.zzjfw = j4;
        this.zzjgk = j5;
        this.zzjgl = i;
        this.zzjfx = z3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.packageName, false);
        zzbgo.zza(parcel, 3, this.zzjfl, false);
        zzbgo.zza(parcel, 4, this.zzina, false);
        zzbgo.zza(parcel, 5, this.zzjfs, false);
        zzbgo.zza(parcel, 6, this.zzjft);
        zzbgo.zza(parcel, 7, this.zzjfu);
        zzbgo.zza(parcel, 8, this.zzjgi, false);
        zzbgo.zza(parcel, 9, this.zzjfv);
        zzbgo.zza(parcel, 10, this.zzjgj);
        zzbgo.zza(parcel, 11, this.zzjfr);
        zzbgo.zza(parcel, 12, this.zzjfn, false);
        zzbgo.zza(parcel, 13, this.zzjfw);
        zzbgo.zza(parcel, 14, this.zzjgk);
        zzbgo.zzc(parcel, 15, this.zzjgl);
        zzbgo.zza(parcel, 16, this.zzjfx);
        zzbgo.zzai(parcel, zze);
    }
}
