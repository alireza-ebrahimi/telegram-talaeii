package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;

public final class zzcii extends zzbgl {
    public static final Creator<zzcii> CREATOR = new zzcij();
    public String packageName;
    private int versionCode;
    public String zzjgm;
    public zzcnl zzjgn;
    public long zzjgo;
    public boolean zzjgp;
    public String zzjgq;
    public zzcix zzjgr;
    public long zzjgs;
    public zzcix zzjgt;
    public long zzjgu;
    public zzcix zzjgv;

    zzcii(int i, String str, String str2, zzcnl zzcnl, long j, boolean z, String str3, zzcix zzcix, long j2, zzcix zzcix2, long j3, zzcix zzcix3) {
        this.versionCode = i;
        this.packageName = str;
        this.zzjgm = str2;
        this.zzjgn = zzcnl;
        this.zzjgo = j;
        this.zzjgp = z;
        this.zzjgq = str3;
        this.zzjgr = zzcix;
        this.zzjgs = j2;
        this.zzjgt = zzcix2;
        this.zzjgu = j3;
        this.zzjgv = zzcix3;
    }

    zzcii(zzcii zzcii) {
        this.versionCode = 1;
        zzbq.checkNotNull(zzcii);
        this.packageName = zzcii.packageName;
        this.zzjgm = zzcii.zzjgm;
        this.zzjgn = zzcii.zzjgn;
        this.zzjgo = zzcii.zzjgo;
        this.zzjgp = zzcii.zzjgp;
        this.zzjgq = zzcii.zzjgq;
        this.zzjgr = zzcii.zzjgr;
        this.zzjgs = zzcii.zzjgs;
        this.zzjgt = zzcii.zzjgt;
        this.zzjgu = zzcii.zzjgu;
        this.zzjgv = zzcii.zzjgv;
    }

    zzcii(String str, String str2, zzcnl zzcnl, long j, boolean z, String str3, zzcix zzcix, long j2, zzcix zzcix2, long j3, zzcix zzcix3) {
        this.versionCode = 1;
        this.packageName = str;
        this.zzjgm = str2;
        this.zzjgn = zzcnl;
        this.zzjgo = j;
        this.zzjgp = z;
        this.zzjgq = str3;
        this.zzjgr = zzcix;
        this.zzjgs = j2;
        this.zzjgt = zzcix2;
        this.zzjgu = j3;
        this.zzjgv = zzcix3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zza(parcel, 2, this.packageName, false);
        zzbgo.zza(parcel, 3, this.zzjgm, false);
        zzbgo.zza(parcel, 4, this.zzjgn, i, false);
        zzbgo.zza(parcel, 5, this.zzjgo);
        zzbgo.zza(parcel, 6, this.zzjgp);
        zzbgo.zza(parcel, 7, this.zzjgq, false);
        zzbgo.zza(parcel, 8, this.zzjgr, i, false);
        zzbgo.zza(parcel, 9, this.zzjgs);
        zzbgo.zza(parcel, 10, this.zzjgt, i, false);
        zzbgo.zza(parcel, 11, this.zzjgu);
        zzbgo.zza(parcel, 12, this.zzjgv, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
