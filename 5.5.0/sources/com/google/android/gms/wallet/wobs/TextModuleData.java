package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "TextModuleDataCreator")
@Reserved({1})
public final class TextModuleData extends AbstractSafeParcelable {
    public static final Creator<TextModuleData> CREATOR = new zzj();
    @Field(id = 2)
    private String zzgz;
    @Field(id = 3)
    private String zzha;

    TextModuleData() {
    }

    @Constructor
    public TextModuleData(@Param(id = 2) String str, @Param(id = 3) String str2) {
        this.zzgz = str;
        this.zzha = str2;
    }

    public final String getBody() {
        return this.zzha;
    }

    public final String getHeader() {
        return this.zzgz;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzgz, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzha, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
