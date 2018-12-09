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

@Class(creator = "LoyaltyPointsCreator")
@Reserved({1, 4})
public final class LoyaltyPoints extends AbstractSafeParcelable {
    public static final Creator<LoyaltyPoints> CREATOR = new zzi();
    @Field(id = 2)
    String label;
    @Field(id = 5)
    TimeInterval zzcp;
    @Field(id = 3)
    LoyaltyPointsBalance zzgr;

    public final class Builder {
        private final /* synthetic */ LoyaltyPoints zzgs;

        private Builder(LoyaltyPoints loyaltyPoints) {
            this.zzgs = loyaltyPoints;
        }

        public final LoyaltyPoints build() {
            return this.zzgs;
        }

        public final Builder setBalance(LoyaltyPointsBalance loyaltyPointsBalance) {
            this.zzgs.zzgr = loyaltyPointsBalance;
            return this;
        }

        public final Builder setLabel(String str) {
            this.zzgs.label = str;
            return this;
        }

        @Deprecated
        public final Builder setType(String str) {
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            this.zzgs.zzcp = timeInterval;
            return this;
        }
    }

    LoyaltyPoints() {
    }

    @Constructor
    LoyaltyPoints(@Param(id = 2) String str, @Param(id = 3) LoyaltyPointsBalance loyaltyPointsBalance, @Param(id = 5) TimeInterval timeInterval) {
        this.label = str;
        this.zzgr = loyaltyPointsBalance;
        this.zzcp = timeInterval;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final LoyaltyPointsBalance getBalance() {
        return this.zzgr;
    }

    public final String getLabel() {
        return this.label;
    }

    @Deprecated
    public final String getType() {
        return null;
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzcp;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.label, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzgr, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzcp, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
