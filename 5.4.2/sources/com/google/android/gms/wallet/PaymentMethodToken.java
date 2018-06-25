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

@Class(creator = "PaymentMethodTokenCreator")
@Reserved({1})
public final class PaymentMethodToken extends AbstractSafeParcelable {
    public static final Creator<PaymentMethodToken> CREATOR = new zzaf();
    @Field(id = 2)
    private int zzeb;
    @Field(id = 3)
    private String zzec;

    private PaymentMethodToken() {
    }

    @Constructor
    PaymentMethodToken(@Param(id = 2) int i, @Param(id = 3) String str) {
        this.zzeb = i;
        this.zzec = str;
    }

    public final int getPaymentMethodTokenizationType() {
        return this.zzeb;
    }

    public final String getToken() {
        return this.zzec;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzeb);
        SafeParcelWriter.writeString(parcel, 3, this.zzec, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
