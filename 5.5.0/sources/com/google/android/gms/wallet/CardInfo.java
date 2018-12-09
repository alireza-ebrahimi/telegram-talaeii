package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.identity.intents.model.UserAddress;

@Class(creator = "CardInfoCreator")
public final class CardInfo extends AbstractSafeParcelable {
    public static final Creator<CardInfo> CREATOR = new zzc();
    @Field(id = 1)
    private String zzad;
    @Field(id = 2)
    private String zzae;
    @Field(id = 3)
    private String zzaf;
    @Field(id = 4)
    private int zzag;
    @Field(id = 5)
    private UserAddress zzah;

    private CardInfo() {
    }

    @Constructor
    CardInfo(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3, @Param(id = 4) int i, @Param(id = 5) UserAddress userAddress) {
        this.zzad = str;
        this.zzae = str2;
        this.zzaf = str3;
        this.zzag = i;
        this.zzah = userAddress;
    }

    public final UserAddress getBillingAddress() {
        return this.zzah;
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

    public final String getCardDescription() {
        return this.zzad;
    }

    public final String getCardDetails() {
        return this.zzaf;
    }

    public final String getCardNetwork() {
        return this.zzae;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzad, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzae, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzaf, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzag);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzah, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
