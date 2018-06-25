package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class TransactionInfo extends zzbgl {
    public static final Creator<TransactionInfo> CREATOR = new zzao();
    int zzlnw;
    String zzlnx;
    String zzlny;

    public final class Builder {
        private /* synthetic */ TransactionInfo zzlnz;

        private Builder(TransactionInfo transactionInfo) {
            this.zzlnz = transactionInfo;
        }

        public final TransactionInfo build() {
            Object obj = 1;
            zzbq.zzh(this.zzlnz.zzlny, "currencyCode must be set!");
            if (!(this.zzlnz.zzlnw == 1 || this.zzlnz.zzlnw == 2 || this.zzlnz.zzlnw == 3)) {
                obj = null;
            }
            if (obj == null) {
                throw new IllegalArgumentException("totalPriceStatus must be set to one of WalletConstants.TotalPriceStatus!");
            }
            if (this.zzlnz.zzlnw == 2) {
                zzbq.zzh(this.zzlnz.zzlnx, "An estimated total price must be set if totalPriceStatus is set to WalletConstants.TOTAL_PRICE_STATUS_ESTIMATED!");
            }
            if (this.zzlnz.zzlnw == 3) {
                zzbq.zzh(this.zzlnz.zzlnx, "An final total price must be set if totalPriceStatus is set to WalletConstants.TOTAL_PRICE_STATUS_FINAL!");
            }
            return this.zzlnz;
        }

        public final Builder setCurrencyCode(@NonNull String str) {
            this.zzlnz.zzlny = str;
            return this;
        }

        public final Builder setTotalPrice(@NonNull String str) {
            this.zzlnz.zzlnx = str;
            return this;
        }

        public final Builder setTotalPriceStatus(int i) {
            this.zzlnz.zzlnw = i;
            return this;
        }
    }

    private TransactionInfo() {
    }

    @Hide
    public TransactionInfo(int i, String str, String str2) {
        this.zzlnw = i;
        this.zzlnx = str;
        this.zzlny = str2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzlny;
    }

    @Nullable
    public final String getTotalPrice() {
        return this.zzlnx;
    }

    public final int getTotalPriceStatus() {
        return this.zzlnw;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzlnw);
        zzbgo.zza(parcel, 2, this.zzlnx, false);
        zzbgo.zza(parcel, 3, this.zzlny, false);
        zzbgo.zzai(parcel, zze);
    }
}
