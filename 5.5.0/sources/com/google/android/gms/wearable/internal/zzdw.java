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
import com.google.android.gms.wearable.ConnectionConfiguration;

@Class(creator = "GetConfigResponseCreator")
@Reserved({1})
@Deprecated
public final class zzdw extends AbstractSafeParcelable {
    public static final Creator<zzdw> CREATOR = new zzdx();
    @Field(id = 2)
    private final int statusCode;
    @Field(id = 3)
    private final ConnectionConfiguration zzdv;

    @Constructor
    public zzdw(@Param(id = 2) int i, @Param(id = 3) ConnectionConfiguration connectionConfiguration) {
        this.statusCode = i;
        this.zzdv = connectionConfiguration;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.statusCode);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdv, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
