package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import java.util.Arrays;

@Hide
public final class zzbfv extends zzbgl {
    public static final Creator<zzbfv> CREATOR = new zzbfw();
    private String packageName;
    private int zzfpc;
    public final String zzfpd;
    public final int zzfpe;
    private String zzfpf;
    private String zzfpg;
    private boolean zzfph;
    private int zzfpi;
    private boolean zzfqq;

    public zzbfv(String str, int i, int i2, String str2, String str3, String str4, boolean z, int i3) {
        this.packageName = (String) zzbq.checkNotNull(str);
        this.zzfpc = i;
        this.zzfpe = i2;
        this.zzfpd = str2;
        this.zzfpf = str3;
        this.zzfpg = str4;
        this.zzfqq = !z;
        this.zzfph = z;
        this.zzfpi = i3;
    }

    public zzbfv(String str, int i, int i2, String str2, String str3, boolean z, String str4, boolean z2, int i3) {
        this.packageName = str;
        this.zzfpc = i;
        this.zzfpe = i2;
        this.zzfpf = str2;
        this.zzfpg = str3;
        this.zzfqq = z;
        this.zzfpd = str4;
        this.zzfph = z2;
        this.zzfpi = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbfv)) {
            return false;
        }
        zzbfv zzbfv = (zzbfv) obj;
        return zzbg.equal(this.packageName, zzbfv.packageName) && this.zzfpc == zzbfv.zzfpc && this.zzfpe == zzbfv.zzfpe && zzbg.equal(this.zzfpd, zzbfv.zzfpd) && zzbg.equal(this.zzfpf, zzbfv.zzfpf) && zzbg.equal(this.zzfpg, zzbfv.zzfpg) && this.zzfqq == zzbfv.zzfqq && this.zzfph == zzbfv.zzfph && this.zzfpi == zzbfv.zzfpi;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.packageName, Integer.valueOf(this.zzfpc), Integer.valueOf(this.zzfpe), this.zzfpd, this.zzfpf, this.zzfpg, Boolean.valueOf(this.zzfqq), Boolean.valueOf(this.zzfph), Integer.valueOf(this.zzfpi)});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PlayLoggerContext[");
        stringBuilder.append("package=").append(this.packageName).append(',');
        stringBuilder.append("packageVersionCode=").append(this.zzfpc).append(',');
        stringBuilder.append("logSource=").append(this.zzfpe).append(',');
        stringBuilder.append("logSourceName=").append(this.zzfpd).append(',');
        stringBuilder.append("uploadAccount=").append(this.zzfpf).append(',');
        stringBuilder.append("loggingId=").append(this.zzfpg).append(',');
        stringBuilder.append("logAndroidId=").append(this.zzfqq).append(',');
        stringBuilder.append("isAnonymous=").append(this.zzfph).append(',');
        stringBuilder.append("qosTier=").append(this.zzfpi);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.packageName, false);
        zzbgo.zzc(parcel, 3, this.zzfpc);
        zzbgo.zzc(parcel, 4, this.zzfpe);
        zzbgo.zza(parcel, 5, this.zzfpf, false);
        zzbgo.zza(parcel, 6, this.zzfpg, false);
        zzbgo.zza(parcel, 7, this.zzfqq);
        zzbgo.zza(parcel, 8, this.zzfpd, false);
        zzbgo.zza(parcel, 9, this.zzfph);
        zzbgo.zzc(parcel, 10, this.zzfpi);
        zzbgo.zzai(parcel, zze);
    }
}
