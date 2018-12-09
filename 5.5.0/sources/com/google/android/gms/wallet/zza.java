package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "AddressCreator")
@Reserved({1})
@Deprecated
public final class zza extends AbstractSafeParcelable {
    public static final Creator<zza> CREATOR = new zzb();
    @Field(id = 2)
    private String name;
    @Field(id = 3)
    private String zze;
    @Field(id = 4)
    private String zzf;
    @Field(id = 5)
    private String zzg;
    @Field(id = 6)
    private String zzh;
    @Field(id = 7)
    private String zzi;
    @Field(id = 8)
    private String zzj;
    @Field(id = 9)
    private String zzk;
    @Field(id = 10)
    private String zzl;
    @Field(id = 11)
    private boolean zzm;
    @Field(id = 12)
    private String zzn;

    zza() {
    }

    @Constructor
    zza(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7, @Param(id = 9) String str8, @Param(id = 10) String str9, @Param(id = 11) boolean z, @Param(id = 12) String str10) {
        this.name = str;
        this.zze = str2;
        this.zzf = str3;
        this.zzg = str4;
        this.zzh = str5;
        this.zzi = str6;
        this.zzj = str7;
        this.zzk = str8;
        this.zzl = str9;
        this.zzm = z;
        this.zzn = str10;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeString(parcel, 3, this.zze, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzf, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzg, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzh, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzi, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzj, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzk, false);
        SafeParcelWriter.writeString(parcel, 10, this.zzl, false);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzm);
        SafeParcelWriter.writeString(parcel, 12, this.zzn, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
