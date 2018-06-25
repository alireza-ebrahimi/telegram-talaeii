package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class PaymentMethodTokenizationParameters extends zzbgl {
    public static final Creator<PaymentMethodTokenizationParameters> CREATOR = new zzah();
    Bundle zzefr = new Bundle();
    int zzlnl;

    public final class Builder {
        private /* synthetic */ PaymentMethodTokenizationParameters zzlnn;

        private Builder(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzlnn = paymentMethodTokenizationParameters;
        }

        public final Builder addParameter(@NonNull String str, @NonNull String str2) {
            zzbq.zzh(str, "Tokenization parameter name must not be empty");
            zzbq.zzh(str2, "Tokenization parameter value must not be empty");
            this.zzlnn.zzefr.putString(str, str2);
            return this;
        }

        public final PaymentMethodTokenizationParameters build() {
            return this.zzlnn;
        }

        public final Builder setPaymentMethodTokenizationType(int i) {
            this.zzlnn.zzlnl = i;
            return this;
        }
    }

    private PaymentMethodTokenizationParameters() {
    }

    PaymentMethodTokenizationParameters(int i, Bundle bundle) {
        this.zzlnl = i;
        this.zzefr = bundle;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final Bundle getParameters() {
        return new Bundle(this.zzefr);
    }

    public final int getPaymentMethodTokenizationType() {
        return this.zzlnl;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.zzlnl);
        zzbgo.zza(parcel, 3, this.zzefr, false);
        zzbgo.zzai(parcel, zze);
    }
}
