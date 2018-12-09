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

@Class(creator = "WebPaymentDataCreator")
@Reserved({1})
public final class zzar extends AbstractSafeParcelable {
    public static final Creator<zzar> CREATOR = new zzas();
    @Field(id = 2)
    private String zzeu;

    private zzar() {
    }

    @Constructor
    zzar(@Param(id = 2) String str) {
        this.zzeu = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzeu, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
