package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "PointOfInterestCreator")
@Reserved({1})
public final class PointOfInterest extends AbstractSafeParcelable {
    public static final Creator<PointOfInterest> CREATOR = new zzj();
    @Field(id = 2)
    public final LatLng latLng;
    @Field(id = 4)
    public final String name;
    @Field(id = 3)
    public final String placeId;

    @Constructor
    public PointOfInterest(@Param(id = 2) LatLng latLng, @Param(id = 3) String str, @Param(id = 4) String str2) {
        this.latLng = latLng;
        this.placeId = str;
        this.name = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.latLng, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.placeId, false);
        SafeParcelWriter.writeString(parcel, 4, this.name, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
