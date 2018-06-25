package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzl extends zzbgl {
    public static final Creator<zzl> CREATOR = new zzm();
    private final String mAppId;
    private int mId;
    private final String mPackageName;
    private final String zzemi;
    private final String zzesj;
    private final String zzevt;
    private final String zzlsg;
    private final String zzlsh;
    private final byte zzlsi;
    private final byte zzlsj;
    private final byte zzlsk;
    private final byte zzlsl;

    public zzl(int i, String str, String str2, String str3, String str4, String str5, String str6, byte b, byte b2, byte b3, byte b4, String str7) {
        this.mId = i;
        this.mAppId = str;
        this.zzlsg = str2;
        this.zzevt = str3;
        this.zzesj = str4;
        this.zzlsh = str5;
        this.zzemi = str6;
        this.zzlsi = b;
        this.zzlsj = b2;
        this.zzlsk = b3;
        this.zzlsl = b4;
        this.mPackageName = str7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzl zzl = (zzl) obj;
        return this.mId != zzl.mId ? false : this.zzlsi != zzl.zzlsi ? false : this.zzlsj != zzl.zzlsj ? false : this.zzlsk != zzl.zzlsk ? false : this.zzlsl != zzl.zzlsl ? false : !this.mAppId.equals(zzl.mAppId) ? false : (this.zzlsg == null ? zzl.zzlsg != null : !this.zzlsg.equals(zzl.zzlsg)) ? false : !this.zzevt.equals(zzl.zzevt) ? false : !this.zzesj.equals(zzl.zzesj) ? false : !this.zzlsh.equals(zzl.zzlsh) ? false : (this.zzemi == null ? zzl.zzemi != null : !this.zzemi.equals(zzl.zzemi)) ? false : this.mPackageName != null ? this.mPackageName.equals(zzl.mPackageName) : zzl.mPackageName == null;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((((this.zzemi != null ? this.zzemi.hashCode() : 0) + (((((((((this.zzlsg != null ? this.zzlsg.hashCode() : 0) + ((((this.mId + 31) * 31) + this.mAppId.hashCode()) * 31)) * 31) + this.zzevt.hashCode()) * 31) + this.zzesj.hashCode()) * 31) + this.zzlsh.hashCode()) * 31)) * 31) + this.zzlsi) * 31) + this.zzlsj) * 31) + this.zzlsk) * 31) + this.zzlsl) * 31;
        if (this.mPackageName != null) {
            i = this.mPackageName.hashCode();
        }
        return hashCode + i;
    }

    public final String toString() {
        int i = this.mId;
        String str = this.mAppId;
        String str2 = this.zzlsg;
        String str3 = this.zzevt;
        String str4 = this.zzesj;
        String str5 = this.zzlsh;
        String str6 = this.zzemi;
        byte b = this.zzlsi;
        byte b2 = this.zzlsj;
        byte b3 = this.zzlsk;
        byte b4 = this.zzlsl;
        String str7 = this.mPackageName;
        return new StringBuilder(((((((String.valueOf(str).length() + 211) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length()) + String.valueOf(str5).length()) + String.valueOf(str6).length()) + String.valueOf(str7).length()).append("AncsNotificationParcelable{, id=").append(i).append(", appId='").append(str).append('\'').append(", dateTime='").append(str2).append('\'').append(", notificationText='").append(str3).append('\'').append(", title='").append(str4).append('\'').append(", subtitle='").append(str5).append('\'').append(", displayName='").append(str6).append('\'').append(", eventId=").append(b).append(", eventFlags=").append(b2).append(", categoryId=").append(b3).append(", categoryCount=").append(b4).append(", packageName='").append(str7).append('\'').append('}').toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.mId);
        zzbgo.zza(parcel, 3, this.mAppId, false);
        zzbgo.zza(parcel, 4, this.zzlsg, false);
        zzbgo.zza(parcel, 5, this.zzevt, false);
        zzbgo.zza(parcel, 6, this.zzesj, false);
        zzbgo.zza(parcel, 7, this.zzlsh, false);
        zzbgo.zza(parcel, 8, this.zzemi == null ? this.mAppId : this.zzemi, false);
        zzbgo.zza(parcel, 9, this.zzlsi);
        zzbgo.zza(parcel, 10, this.zzlsj);
        zzbgo.zza(parcel, 11, this.zzlsk);
        zzbgo.zza(parcel, 12, this.zzlsl);
        zzbgo.zza(parcel, 13, this.mPackageName, false);
        zzbgo.zzai(parcel, zze);
    }
}
