package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Collection;

public final class PaymentDataRequest extends zzbgl {
    public static final Creator<PaymentDataRequest> CREATOR = new zzae();
    ArrayList<Integer> zzlld;
    boolean zzlnc;
    boolean zzlnd;
    CardRequirements zzlne;
    boolean zzlnf;
    ShippingAddressRequirements zzlng;
    PaymentMethodTokenizationParameters zzlnh;
    TransactionInfo zzlni;
    boolean zzlnj;

    public final class Builder {
        private /* synthetic */ PaymentDataRequest zzlnk;

        private Builder(PaymentDataRequest paymentDataRequest) {
            this.zzlnk = paymentDataRequest;
        }

        public final Builder addAllowedPaymentMethod(int i) {
            if (this.zzlnk.zzlld == null) {
                this.zzlnk.zzlld = new ArrayList();
            }
            this.zzlnk.zzlld.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedPaymentMethods(@NonNull Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            zzbq.checkArgument(z, "allowedPaymentMethods can't be null or empty!");
            if (this.zzlnk.zzlld == null) {
                this.zzlnk.zzlld = new ArrayList();
            }
            this.zzlnk.zzlld.addAll(collection);
            return this;
        }

        public final PaymentDataRequest build() {
            zzbq.checkNotNull(this.zzlnk.zzlld, "Allowed payment methods must be set! You can set it through addAllowedPaymentMethod() or addAllowedPaymentMethods() in the PaymentDataRequest Builder.");
            zzbq.checkNotNull(this.zzlnk.zzlne, "Card requirements must be set!");
            if (this.zzlnk.zzlnh != null) {
                zzbq.checkNotNull(this.zzlnk.zzlni, "Transaction info must be set if paymentMethodTokenizationParameters is set!");
            }
            return this.zzlnk;
        }

        public final Builder setCardRequirements(@NonNull CardRequirements cardRequirements) {
            this.zzlnk.zzlne = cardRequirements;
            return this;
        }

        public final Builder setEmailRequired(boolean z) {
            this.zzlnk.zzlnc = z;
            return this;
        }

        public final Builder setPaymentMethodTokenizationParameters(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzlnk.zzlnh = paymentMethodTokenizationParameters;
            return this;
        }

        public final Builder setPhoneNumberRequired(boolean z) {
            this.zzlnk.zzlnd = z;
            return this;
        }

        public final Builder setShippingAddressRequired(boolean z) {
            this.zzlnk.zzlnf = z;
            return this;
        }

        public final Builder setShippingAddressRequirements(@NonNull ShippingAddressRequirements shippingAddressRequirements) {
            this.zzlnk.zzlng = shippingAddressRequirements;
            return this;
        }

        public final Builder setTransactionInfo(@NonNull TransactionInfo transactionInfo) {
            this.zzlnk.zzlni = transactionInfo;
            return this;
        }

        public final Builder setUiRequired(boolean z) {
            this.zzlnk.zzlnj = z;
            return this;
        }
    }

    private PaymentDataRequest() {
        this.zzlnj = true;
    }

    PaymentDataRequest(boolean z, boolean z2, CardRequirements cardRequirements, boolean z3, ShippingAddressRequirements shippingAddressRequirements, ArrayList<Integer> arrayList, PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, TransactionInfo transactionInfo, boolean z4) {
        this.zzlnc = z;
        this.zzlnd = z2;
        this.zzlne = cardRequirements;
        this.zzlnf = z3;
        this.zzlng = shippingAddressRequirements;
        this.zzlld = arrayList;
        this.zzlnh = paymentMethodTokenizationParameters;
        this.zzlni = transactionInfo;
        this.zzlnj = z4;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final ArrayList<Integer> getAllowedPaymentMethods() {
        return this.zzlld;
    }

    @Nullable
    public final CardRequirements getCardRequirements() {
        return this.zzlne;
    }

    public final PaymentMethodTokenizationParameters getPaymentMethodTokenizationParameters() {
        return this.zzlnh;
    }

    @Nullable
    public final ShippingAddressRequirements getShippingAddressRequirements() {
        return this.zzlng;
    }

    public final TransactionInfo getTransactionInfo() {
        return this.zzlni;
    }

    public final boolean isEmailRequired() {
        return this.zzlnc;
    }

    public final boolean isPhoneNumberRequired() {
        return this.zzlnd;
    }

    public final boolean isShippingAddressRequired() {
        return this.zzlnf;
    }

    public final boolean isUiRequired() {
        return this.zzlnj;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzlnc);
        zzbgo.zza(parcel, 2, this.zzlnd);
        zzbgo.zza(parcel, 3, this.zzlne, i, false);
        zzbgo.zza(parcel, 4, this.zzlnf);
        zzbgo.zza(parcel, 5, this.zzlng, i, false);
        zzbgo.zza(parcel, 6, this.zzlld, false);
        zzbgo.zza(parcel, 7, this.zzlnh, i, false);
        zzbgo.zza(parcel, 8, this.zzlni, i, false);
        zzbgo.zza(parcel, 9, this.zzlnj);
        zzbgo.zzai(parcel, zze);
    }
}
