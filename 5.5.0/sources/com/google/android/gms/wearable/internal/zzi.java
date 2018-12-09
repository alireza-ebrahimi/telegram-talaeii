package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "AmsEntityUpdateParcelableCreator")
@Reserved({1})
public final class zzi extends AbstractSafeParcelable {
    public static final Creator<zzi> CREATOR = new zzj();
    @Field(getter = "getValue", id = 4)
    private final String value;
    @Field(getter = "getEntityId", id = 2)
    private byte zzbd;
    @Field(getter = "getAttributeId", id = 3)
    private final byte zzbe;

    @Constructor
    public zzi(@Param(id = 2) byte b, @Param(id = 3) byte b2, @Param(id = 4) String str) {
        this.zzbd = b;
        this.zzbe = b2;
        this.value = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzi zzi = (zzi) obj;
        return this.zzbd != zzi.zzbd ? false : this.zzbe != zzi.zzbe ? false : this.value.equals(zzi.value);
    }

    public final int hashCode() {
        return ((((this.zzbd + 31) * 31) + this.zzbe) * 31) + this.value.hashCode();
    }

    public final String toString() {
        byte b = this.zzbd;
        byte b2 = this.zzbe;
        String str = this.value;
        return new StringBuilder(String.valueOf(str).length() + 73).append("AmsEntityUpdateParcelable{, mEntityId=").append(b).append(", mAttributeId=").append(b2).append(", mValue='").append(str).append('\'').append('}').toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByte(parcel, 2, this.zzbd);
        SafeParcelWriter.writeByte(parcel, 3, this.zzbe);
        SafeParcelWriter.writeString(parcel, 4, this.value, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
