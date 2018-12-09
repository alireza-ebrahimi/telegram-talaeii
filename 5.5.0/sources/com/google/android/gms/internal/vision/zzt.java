package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "LineBoxParcelCreator")
@Reserved({1})
public final class zzt extends AbstractSafeParcelable {
    public static final Creator<zzt> CREATOR = new zzu();
    @Field(id = 7)
    private final float zzcm;
    @Field(id = 8)
    public final String zzcy;
    @Field(id = 2)
    public final zzac[] zzdd;
    @Field(id = 3)
    public final zzn zzde;
    @Field(id = 4)
    private final zzn zzdf;
    @Field(id = 5)
    private final zzn zzdg;
    @Field(id = 6)
    public final String zzdh;
    @Field(id = 9)
    private final int zzdi;
    @Field(id = 10)
    public final boolean zzdj;
    @Field(id = 11)
    public final int zzdk;
    @Field(id = 12)
    public final int zzdl;

    @Constructor
    public zzt(@Param(id = 2) zzac[] zzacArr, @Param(id = 3) zzn zzn, @Param(id = 4) zzn zzn2, @Param(id = 5) zzn zzn3, @Param(id = 6) String str, @Param(id = 7) float f, @Param(id = 8) String str2, @Param(id = 9) int i, @Param(id = 10) boolean z, @Param(id = 11) int i2, @Param(id = 12) int i3) {
        this.zzdd = zzacArr;
        this.zzde = zzn;
        this.zzdf = zzn2;
        this.zzdg = zzn3;
        this.zzdh = str;
        this.zzcm = f;
        this.zzcy = str2;
        this.zzdi = i;
        this.zzdj = z;
        this.zzdk = i2;
        this.zzdl = i3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzdd, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzde, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdf, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzdg, i, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzdh, false);
        SafeParcelWriter.writeFloat(parcel, 7, this.zzcm);
        SafeParcelWriter.writeString(parcel, 8, this.zzcy, false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzdi);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzdj);
        SafeParcelWriter.writeInt(parcel, 11, this.zzdk);
        SafeParcelWriter.writeInt(parcel, 12, this.zzdl);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
