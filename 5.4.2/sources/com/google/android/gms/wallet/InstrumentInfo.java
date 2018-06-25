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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Class(creator = "InstrumentInfoCreator")
@Reserved({1})
public final class InstrumentInfo extends AbstractSafeParcelable {
    public static final int CARD_CLASS_CREDIT = 1;
    public static final int CARD_CLASS_DEBIT = 2;
    public static final int CARD_CLASS_PREPAID = 3;
    public static final int CARD_CLASS_UNKNOWN = 0;
    public static final Creator<InstrumentInfo> CREATOR = new zzp();
    @Field(getter = "getCardClass", id = 4)
    private int zzag;
    @Field(getter = "getInstrumentType", id = 2)
    private String zzbs;
    @Field(getter = "getInstrumentDetails", id = 3)
    private String zzbt;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CardClass {
    }

    private InstrumentInfo() {
    }

    @Constructor
    public InstrumentInfo(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) int i) {
        this.zzbs = str;
        this.zzbt = str2;
        this.zzag = i;
    }

    public final int getCardClass() {
        switch (this.zzag) {
            case 1:
            case 2:
            case 3:
                return this.zzag;
            default:
                return 0;
        }
    }

    public final String getInstrumentDetails() {
        return this.zzbt;
    }

    public final String getInstrumentType() {
        return this.zzbs;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getInstrumentType(), false);
        SafeParcelWriter.writeString(parcel, 3, getInstrumentDetails(), false);
        SafeParcelWriter.writeInt(parcel, 4, getCardClass());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
