package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "TransactionInfoCreator")
public final class TransactionInfo extends AbstractSafeParcelable {
    public static final Creator<TransactionInfo> CREATOR = new zzao();
    @Field(id = 2)
    String zzan;
    @Field(id = 3)
    String zzao;
    @Field(id = 1)
    int zzen;

    public final class Builder {
        private final /* synthetic */ TransactionInfo zzeo;

        private Builder(TransactionInfo transactionInfo) {
            this.zzeo = transactionInfo;
        }

        public final TransactionInfo build() {
            Object obj = 1;
            Preconditions.checkNotEmpty(this.zzeo.zzao, "currencyCode must be set!");
            if (!(this.zzeo.zzen == 1 || this.zzeo.zzen == 2 || this.zzeo.zzen == 3)) {
                obj = null;
            }
            if (obj == null) {
                throw new IllegalArgumentException("totalPriceStatus must be set to one of WalletConstants.TotalPriceStatus!");
            }
            if (this.zzeo.zzen == 2) {
                Preconditions.checkNotEmpty(this.zzeo.zzan, "An estimated total price must be set if totalPriceStatus is set to WalletConstants.TOTAL_PRICE_STATUS_ESTIMATED!");
            }
            if (this.zzeo.zzen == 3) {
                Preconditions.checkNotEmpty(this.zzeo.zzan, "An final total price must be set if totalPriceStatus is set to WalletConstants.TOTAL_PRICE_STATUS_FINAL!");
            }
            return this.zzeo;
        }

        public final Builder setCurrencyCode(String str) {
            this.zzeo.zzao = str;
            return this;
        }

        public final Builder setTotalPrice(String str) {
            this.zzeo.zzan = str;
            return this;
        }

        public final Builder setTotalPriceStatus(int i) {
            this.zzeo.zzen = i;
            return this;
        }
    }

    private TransactionInfo() {
    }

    @Constructor
    public TransactionInfo(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) String str2) {
        this.zzen = i;
        this.zzan = str;
        this.zzao = str2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzao;
    }

    public final String getTotalPrice() {
        return this.zzan;
    }

    public final int getTotalPriceStatus() {
        return this.zzen;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzen);
        SafeParcelWriter.writeString(parcel, 2, this.zzan, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzao, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
