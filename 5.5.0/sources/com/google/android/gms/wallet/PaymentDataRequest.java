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
import java.util.ArrayList;
import java.util.Collection;

@Class(creator = "PaymentDataRequestCreator")
public final class PaymentDataRequest extends AbstractSafeParcelable {
    public static final Creator<PaymentDataRequest> CREATOR = new zzae();
    @Field(id = 6)
    ArrayList<Integer> zzbw;
    @Field(id = 10)
    String zzby;
    @Field(id = 2)
    boolean zzdd;
    @Field(id = 4)
    boolean zzde;
    @Field(id = 7)
    PaymentMethodTokenizationParameters zzdo;
    @Field(id = 1)
    boolean zzdv;
    @Field(id = 3)
    CardRequirements zzdw;
    @Field(id = 5)
    ShippingAddressRequirements zzdx;
    @Field(id = 8)
    TransactionInfo zzdy;
    @Field(defaultValue = "true", id = 9)
    boolean zzdz;

    public final class Builder {
        private final /* synthetic */ PaymentDataRequest zzea;

        private Builder(PaymentDataRequest paymentDataRequest) {
            this.zzea = paymentDataRequest;
        }

        public final Builder addAllowedPaymentMethod(int i) {
            if (this.zzea.zzbw == null) {
                this.zzea.zzbw = new ArrayList();
            }
            this.zzea.zzbw.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedPaymentMethods(Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            Preconditions.checkArgument(z, "allowedPaymentMethods can't be null or empty!");
            if (this.zzea.zzbw == null) {
                this.zzea.zzbw = new ArrayList();
            }
            this.zzea.zzbw.addAll(collection);
            return this;
        }

        public final PaymentDataRequest build() {
            if (this.zzea.zzby == null) {
                Preconditions.checkNotNull(this.zzea.zzbw, "Allowed payment methods must be set! You can set it through addAllowedPaymentMethod() or addAllowedPaymentMethods() in the PaymentDataRequest Builder.");
                Preconditions.checkNotNull(this.zzea.zzdw, "Card requirements must be set!");
                if (this.zzea.zzdo != null) {
                    Preconditions.checkNotNull(this.zzea.zzdy, "Transaction info must be set if paymentMethodTokenizationParameters is set!");
                }
            }
            return this.zzea;
        }

        public final Builder setCardRequirements(CardRequirements cardRequirements) {
            this.zzea.zzdw = cardRequirements;
            return this;
        }

        public final Builder setEmailRequired(boolean z) {
            this.zzea.zzdv = z;
            return this;
        }

        public final Builder setPaymentMethodTokenizationParameters(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzea.zzdo = paymentMethodTokenizationParameters;
            return this;
        }

        public final Builder setPhoneNumberRequired(boolean z) {
            this.zzea.zzdd = z;
            return this;
        }

        public final Builder setShippingAddressRequired(boolean z) {
            this.zzea.zzde = z;
            return this;
        }

        public final Builder setShippingAddressRequirements(ShippingAddressRequirements shippingAddressRequirements) {
            this.zzea.zzdx = shippingAddressRequirements;
            return this;
        }

        public final Builder setTransactionInfo(TransactionInfo transactionInfo) {
            this.zzea.zzdy = transactionInfo;
            return this;
        }

        public final Builder setUiRequired(boolean z) {
            this.zzea.zzdz = z;
            return this;
        }
    }

    private PaymentDataRequest() {
        this.zzdz = true;
    }

    @Constructor
    PaymentDataRequest(@Param(id = 1) boolean z, @Param(id = 2) boolean z2, @Param(id = 3) CardRequirements cardRequirements, @Param(id = 4) boolean z3, @Param(id = 5) ShippingAddressRequirements shippingAddressRequirements, @Param(id = 6) ArrayList<Integer> arrayList, @Param(id = 7) PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, @Param(id = 8) TransactionInfo transactionInfo, @Param(id = 9) boolean z4, @Param(id = 10) String str) {
        this.zzdv = z;
        this.zzdd = z2;
        this.zzdw = cardRequirements;
        this.zzde = z3;
        this.zzdx = shippingAddressRequirements;
        this.zzbw = arrayList;
        this.zzdo = paymentMethodTokenizationParameters;
        this.zzdy = transactionInfo;
        this.zzdz = z4;
        this.zzby = str;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final ArrayList<Integer> getAllowedPaymentMethods() {
        return this.zzbw;
    }

    public final CardRequirements getCardRequirements() {
        return this.zzdw;
    }

    public final PaymentMethodTokenizationParameters getPaymentMethodTokenizationParameters() {
        return this.zzdo;
    }

    public final ShippingAddressRequirements getShippingAddressRequirements() {
        return this.zzdx;
    }

    public final TransactionInfo getTransactionInfo() {
        return this.zzdy;
    }

    public final boolean isEmailRequired() {
        return this.zzdv;
    }

    public final boolean isPhoneNumberRequired() {
        return this.zzdd;
    }

    public final boolean isShippingAddressRequired() {
        return this.zzde;
    }

    public final boolean isUiRequired() {
        return this.zzdz;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.zzdv);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzdd);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdw, i, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzde);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzdx, i, false);
        SafeParcelWriter.writeIntegerList(parcel, 6, this.zzbw, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzdo, i, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzdy, i, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzdz);
        SafeParcelWriter.writeString(parcel, 10, this.zzby, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
