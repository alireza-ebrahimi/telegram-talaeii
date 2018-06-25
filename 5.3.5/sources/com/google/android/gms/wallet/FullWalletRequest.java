package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class FullWalletRequest extends zzbgl implements ReflectedParcelable {
    public static final Creator<FullWalletRequest> CREATOR = new zzm();
    String zzlkc;
    String zzlkd;
    Cart zzlkn;

    public final class Builder {
        private /* synthetic */ FullWalletRequest zzlko;

        private Builder(FullWalletRequest fullWalletRequest) {
            this.zzlko = fullWalletRequest;
        }

        public final FullWalletRequest build() {
            return this.zzlko;
        }

        public final Builder setCart(Cart cart) {
            this.zzlko.zzlkn = cart;
            return this;
        }

        public final Builder setGoogleTransactionId(String str) {
            this.zzlko.zzlkc = str;
            return this;
        }

        public final Builder setMerchantTransactionId(String str) {
            this.zzlko.zzlkd = str;
            return this;
        }
    }

    FullWalletRequest() {
    }

    FullWalletRequest(String str, String str2, Cart cart) {
        this.zzlkc = str;
        this.zzlkd = str2;
        this.zzlkn = cart;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final Cart getCart() {
        return this.zzlkn;
    }

    public final String getGoogleTransactionId() {
        return this.zzlkc;
    }

    public final String getMerchantTransactionId() {
        return this.zzlkd;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlkc, false);
        zzbgo.zza(parcel, 3, this.zzlkd, false);
        zzbgo.zza(parcel, 4, this.zzlkn, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
