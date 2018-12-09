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

@Class(creator = "ProxyCardCreator")
@Reserved({1})
@Deprecated
public final class ProxyCard extends AbstractSafeParcelable {
    public static final Creator<ProxyCard> CREATOR = new zzak();
    @Field(id = 2)
    private String zzeh;
    @Field(id = 3)
    private String zzei;
    @Field(id = 4)
    private int zzej;
    @Field(id = 5)
    private int zzek;

    @Constructor
    public ProxyCard(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) int i, @Param(id = 5) int i2) {
        this.zzeh = str;
        this.zzei = str2;
        this.zzej = i;
        this.zzek = i2;
    }

    public final String getCvn() {
        return this.zzei;
    }

    public final int getExpirationMonth() {
        return this.zzej;
    }

    public final int getExpirationYear() {
        return this.zzek;
    }

    public final String getPan() {
        return this.zzeh;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzeh, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzei, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzej);
        SafeParcelWriter.writeInt(parcel, 5, this.zzek);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
