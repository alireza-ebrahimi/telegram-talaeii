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

@Class(creator = "WordBoxParcelCreator")
@Reserved({1})
public final class zzac extends AbstractSafeParcelable {
    public static final Creator<zzac> CREATOR = new zzad();
    @Field(id = 6)
    private final float zzcm;
    @Field(id = 7)
    public final String zzcy;
    @Field(id = 3)
    public final zzn zzde;
    @Field(id = 4)
    private final zzn zzdf;
    @Field(id = 5)
    public final String zzdh;
    @Field(id = 2)
    private final zzx[] zzdn;
    @Field(id = 8)
    private final boolean zzdo;

    @Constructor
    public zzac(@Param(id = 2) zzx[] zzxArr, @Param(id = 3) zzn zzn, @Param(id = 4) zzn zzn2, @Param(id = 5) String str, @Param(id = 6) float f, @Param(id = 7) String str2, @Param(id = 8) boolean z) {
        this.zzdn = zzxArr;
        this.zzde = zzn;
        this.zzdf = zzn2;
        this.zzdh = str;
        this.zzcm = f;
        this.zzcy = str2;
        this.zzdo = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzdn, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzde, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdf, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzdh, false);
        SafeParcelWriter.writeFloat(parcel, 6, this.zzcm);
        SafeParcelWriter.writeString(parcel, 7, this.zzcy, false);
        SafeParcelWriter.writeBoolean(parcel, 8, this.zzdo);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
