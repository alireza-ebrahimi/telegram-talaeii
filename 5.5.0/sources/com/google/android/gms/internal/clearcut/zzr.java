package com.google.android.gms.internal.clearcut;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.clearcut.zzge.zzv.zzb;

@Class(creator = "PlayLoggerContextCreator")
@Reserved({1})
public final class zzr extends AbstractSafeParcelable {
    public static final Creator<zzr> CREATOR = new zzs();
    @Field(id = 2)
    private final String packageName;
    @Field(defaultValue = "true", id = 7)
    private final boolean zzay;
    @Field(id = 10)
    private final int zzaz;
    @Field(id = 3)
    private final int zzi;
    @Field(id = 8)
    public final String zzj;
    @Field(id = 4)
    public final int zzk;
    @Field(id = 5)
    private final String zzl;
    @Field(id = 6)
    private final String zzm;
    @Field(id = 9)
    private final boolean zzn;

    public zzr(String str, int i, int i2, String str2, String str3, String str4, boolean z, zzb zzb) {
        this.packageName = (String) Preconditions.checkNotNull(str);
        this.zzi = i;
        this.zzk = i2;
        this.zzj = str2;
        this.zzl = str3;
        this.zzm = str4;
        this.zzay = !z;
        this.zzn = z;
        this.zzaz = zzb.zzc();
    }

    @Constructor
    public zzr(@Param(id = 2) String str, @Param(id = 3) int i, @Param(id = 4) int i2, @Param(id = 5) String str2, @Param(id = 6) String str3, @Param(id = 7) boolean z, @Param(id = 8) String str4, @Param(id = 9) boolean z2, @Param(id = 10) int i3) {
        this.packageName = str;
        this.zzi = i;
        this.zzk = i2;
        this.zzl = str2;
        this.zzm = str3;
        this.zzay = z;
        this.zzj = str4;
        this.zzn = z2;
        this.zzaz = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzr)) {
            return false;
        }
        zzr zzr = (zzr) obj;
        return Objects.equal(this.packageName, zzr.packageName) && this.zzi == zzr.zzi && this.zzk == zzr.zzk && Objects.equal(this.zzj, zzr.zzj) && Objects.equal(this.zzl, zzr.zzl) && Objects.equal(this.zzm, zzr.zzm) && this.zzay == zzr.zzay && this.zzn == zzr.zzn && this.zzaz == zzr.zzaz;
    }

    public final int hashCode() {
        return Objects.hashCode(this.packageName, Integer.valueOf(this.zzi), Integer.valueOf(this.zzk), this.zzj, this.zzl, this.zzm, Boolean.valueOf(this.zzay), Boolean.valueOf(this.zzn), Integer.valueOf(this.zzaz));
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PlayLoggerContext[");
        stringBuilder.append("package=").append(this.packageName).append(',');
        stringBuilder.append("packageVersionCode=").append(this.zzi).append(',');
        stringBuilder.append("logSource=").append(this.zzk).append(',');
        stringBuilder.append("logSourceName=").append(this.zzj).append(',');
        stringBuilder.append("uploadAccount=").append(this.zzl).append(',');
        stringBuilder.append("loggingId=").append(this.zzm).append(',');
        stringBuilder.append("logAndroidId=").append(this.zzay).append(',');
        stringBuilder.append("isAnonymous=").append(this.zzn).append(',');
        stringBuilder.append("qosTier=").append(this.zzaz);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzi);
        SafeParcelWriter.writeInt(parcel, 4, this.zzk);
        SafeParcelWriter.writeString(parcel, 5, this.zzl, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzm, false);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzay);
        SafeParcelWriter.writeString(parcel, 8, this.zzj, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzn);
        SafeParcelWriter.writeInt(parcel, 10, this.zzaz);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
