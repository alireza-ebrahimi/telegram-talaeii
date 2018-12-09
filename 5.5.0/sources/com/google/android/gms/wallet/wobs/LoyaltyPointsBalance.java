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

@Class(creator = "LoyaltyPointsBalanceCreator")
@Reserved({1})
public final class LoyaltyPointsBalance extends AbstractSafeParcelable {
    public static final Creator<LoyaltyPointsBalance> CREATOR = new zzh();
    @Field(id = 5)
    String zzbn;
    @Field(id = 2)
    int zzgt;
    @Field(id = 3)
    String zzgu;
    @Field(id = 4)
    double zzgv;
    @Field(id = 6)
    long zzgw;
    @Field(defaultValueUnchecked = "com.google.android.gms.wallet.wobs.LoyaltyPointsBalance.Type.UNDEFINED", id = 7)
    int zzgx;

    public final class Builder {
        private final /* synthetic */ LoyaltyPointsBalance zzgy;

        private Builder(LoyaltyPointsBalance loyaltyPointsBalance) {
            this.zzgy = loyaltyPointsBalance;
        }

        public final LoyaltyPointsBalance build() {
            return this.zzgy;
        }

        public final Builder setDouble(double d) {
            this.zzgy.zzgv = d;
            this.zzgy.zzgx = 2;
            return this;
        }

        public final Builder setInt(int i) {
            this.zzgy.zzgt = i;
            this.zzgy.zzgx = 0;
            return this;
        }

        public final Builder setMoney(String str, long j) {
            this.zzgy.zzbn = str;
            this.zzgy.zzgw = j;
            this.zzgy.zzgx = 3;
            return this;
        }

        public final Builder setString(String str) {
            this.zzgy.zzgu = str;
            this.zzgy.zzgx = 1;
            return this;
        }
    }

    public interface Type {
        public static final int DOUBLE = 2;
        public static final int INT = 0;
        public static final int MONEY = 3;
        public static final int STRING = 1;
        public static final int UNDEFINED = -1;
    }

    LoyaltyPointsBalance() {
        this.zzgx = -1;
        this.zzgt = -1;
        this.zzgv = -1.0d;
    }

    @Constructor
    LoyaltyPointsBalance(@Param(id = 2) int i, @Param(id = 3) String str, @Param(id = 4) double d, @Param(id = 5) String str2, @Param(id = 6) long j, @Param(id = 7) int i2) {
        this.zzgt = i;
        this.zzgu = str;
        this.zzgv = d;
        this.zzbn = str2;
        this.zzgw = j;
        this.zzgx = i2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzbn;
    }

    public final long getCurrencyMicros() {
        return this.zzgw;
    }

    public final double getDouble() {
        return this.zzgv;
    }

    public final int getInt() {
        return this.zzgt;
    }

    public final String getString() {
        return this.zzgu;
    }

    public final int getType() {
        return this.zzgx;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzgt);
        SafeParcelWriter.writeString(parcel, 3, this.zzgu, false);
        SafeParcelWriter.writeDouble(parcel, 4, this.zzgv);
        SafeParcelWriter.writeString(parcel, 5, this.zzbn, false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzgw);
        SafeParcelWriter.writeInt(parcel, 7, this.zzgx);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
