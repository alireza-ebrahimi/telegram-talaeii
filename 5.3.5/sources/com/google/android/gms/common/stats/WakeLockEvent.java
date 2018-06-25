package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.internal.zzbgo;
import java.util.List;

public final class WakeLockEvent extends StatsEvent {
    public static final Creator<WakeLockEvent> CREATOR = new zzd();
    private final long mTimeout;
    private int zzehz;
    private final long zzgjv;
    private int zzgjw;
    private final String zzgjx;
    private final String zzgjy;
    private final String zzgjz;
    private final int zzgka;
    private final List<String> zzgkb;
    private final String zzgkc;
    private final long zzgkd;
    private int zzgke;
    private final String zzgkf;
    private final float zzgkg;
    private long zzgkh;

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zzehz = i;
        this.zzgjv = j;
        this.zzgjw = i2;
        this.zzgjx = str;
        this.zzgjy = str3;
        this.zzgjz = str5;
        this.zzgka = i3;
        this.zzgkh = -1;
        this.zzgkb = list;
        this.zzgkc = str2;
        this.zzgkd = j2;
        this.zzgke = i4;
        this.zzgkf = str4;
        this.zzgkg = f;
        this.mTimeout = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public final int getEventType() {
        return this.zzgjw;
    }

    public final long getTimeMillis() {
        return this.zzgjv;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, getTimeMillis());
        zzbgo.zza(parcel, 4, this.zzgjx, false);
        zzbgo.zzc(parcel, 5, this.zzgka);
        zzbgo.zzb(parcel, 6, this.zzgkb, false);
        zzbgo.zza(parcel, 8, this.zzgkd);
        zzbgo.zza(parcel, 10, this.zzgjy, false);
        zzbgo.zzc(parcel, 11, getEventType());
        zzbgo.zza(parcel, 12, this.zzgkc, false);
        zzbgo.zza(parcel, 13, this.zzgkf, false);
        zzbgo.zzc(parcel, 14, this.zzgke);
        zzbgo.zza(parcel, 15, this.zzgkg);
        zzbgo.zza(parcel, 16, this.mTimeout);
        zzbgo.zza(parcel, 17, this.zzgjz, false);
        zzbgo.zzai(parcel, zze);
    }

    public final long zzann() {
        return this.zzgkh;
    }

    public final String zzano() {
        String str = this.zzgjx;
        int i = this.zzgka;
        String join = this.zzgkb == null ? "" : TextUtils.join(",", this.zzgkb);
        int i2 = this.zzgke;
        String str2 = this.zzgjy == null ? "" : this.zzgjy;
        String str3 = this.zzgkf == null ? "" : this.zzgkf;
        float f = this.zzgkg;
        String str4 = this.zzgjz == null ? "" : this.zzgjz;
        return new StringBuilder(((((String.valueOf(str).length() + 45) + String.valueOf(join).length()) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length()).append("\t").append(str).append("\t").append(i).append("\t").append(join).append("\t").append(i2).append("\t").append(str2).append("\t").append(str3).append("\t").append(f).append("\t").append(str4).toString();
    }
}
