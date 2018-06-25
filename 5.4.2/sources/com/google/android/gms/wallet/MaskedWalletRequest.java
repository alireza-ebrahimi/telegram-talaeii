package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;

@Class(creator = "MaskedWalletRequestCreator")
@Reserved({1})
public final class MaskedWalletRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<MaskedWalletRequest> CREATOR = new zzz();
    @Field(id = 17)
    ArrayList<Integer> zzai;
    @Field(id = 7)
    String zzao;
    @Field(id = 2)
    String zzax;
    @Field(id = 9)
    Cart zzbh;
    @Field(id = 3)
    boolean zzdd;
    @Field(id = 4)
    boolean zzde;
    @Field(id = 5)
    boolean zzdf;
    @Field(id = 6)
    String zzdg;
    @Field(id = 8)
    String zzdh;
    @Field(id = 10)
    private boolean zzdi;
    @Field(id = 11)
    boolean zzdj;
    @Field(id = 12)
    private CountrySpecification[] zzdk;
    @Field(defaultValue = "true", id = 13)
    boolean zzdl;
    @Field(defaultValue = "true", id = 14)
    boolean zzdm;
    @Field(id = 15)
    ArrayList<CountrySpecification> zzdn;
    @Field(id = 16)
    PaymentMethodTokenizationParameters zzdo;
    @Field(id = 18)
    String zzh;

    public final class Builder {
        private final /* synthetic */ MaskedWalletRequest zzdp;

        private Builder(MaskedWalletRequest maskedWalletRequest) {
            this.zzdp = maskedWalletRequest;
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (this.zzdp.zzai == null) {
                this.zzdp.zzai = new ArrayList();
            }
            this.zzdp.zzai.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(Collection<Integer> collection) {
            if (collection != null) {
                if (this.zzdp.zzai == null) {
                    this.zzdp.zzai = new ArrayList();
                }
                this.zzdp.zzai.addAll(collection);
            }
            return this;
        }

        public final Builder addAllowedCountrySpecificationForShipping(CountrySpecification countrySpecification) {
            if (this.zzdp.zzdn == null) {
                this.zzdp.zzdn = new ArrayList();
            }
            this.zzdp.zzdn.add(countrySpecification);
            return this;
        }

        public final Builder addAllowedCountrySpecificationsForShipping(Collection<CountrySpecification> collection) {
            if (collection != null) {
                if (this.zzdp.zzdn == null) {
                    this.zzdp.zzdn = new ArrayList();
                }
                this.zzdp.zzdn.addAll(collection);
            }
            return this;
        }

        public final MaskedWalletRequest build() {
            return this.zzdp;
        }

        public final Builder setAllowDebitCard(boolean z) {
            this.zzdp.zzdm = z;
            return this;
        }

        public final Builder setAllowPrepaidCard(boolean z) {
            this.zzdp.zzdl = z;
            return this;
        }

        public final Builder setCart(Cart cart) {
            this.zzdp.zzbh = cart;
            return this;
        }

        public final Builder setCountryCode(String str) {
            this.zzdp.zzh = str;
            return this;
        }

        public final Builder setCurrencyCode(String str) {
            this.zzdp.zzao = str;
            return this;
        }

        public final Builder setEstimatedTotalPrice(String str) {
            this.zzdp.zzdg = str;
            return this;
        }

        @Deprecated
        public final Builder setIsBillingAgreement(boolean z) {
            this.zzdp.zzdj = z;
            return this;
        }

        public final Builder setMerchantName(String str) {
            this.zzdp.zzdh = str;
            return this;
        }

        public final Builder setMerchantTransactionId(String str) {
            this.zzdp.zzax = str;
            return this;
        }

        public final Builder setPaymentMethodTokenizationParameters(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzdp.zzdo = paymentMethodTokenizationParameters;
            return this;
        }

        public final Builder setPhoneNumberRequired(boolean z) {
            this.zzdp.zzdd = z;
            return this;
        }

        public final Builder setShippingAddressRequired(boolean z) {
            this.zzdp.zzde = z;
            return this;
        }

        @Deprecated
        public final Builder setUseMinimalBillingAddress(boolean z) {
            this.zzdp.zzdf = z;
            return this;
        }
    }

    MaskedWalletRequest() {
        this.zzdl = true;
        this.zzdm = true;
    }

    @Constructor
    MaskedWalletRequest(@Param(id = 2) String str, @Param(id = 3) boolean z, @Param(id = 4) boolean z2, @Param(id = 5) boolean z3, @Param(id = 6) String str2, @Param(id = 7) String str3, @Param(id = 8) String str4, @Param(id = 9) Cart cart, @Param(id = 10) boolean z4, @Param(id = 11) boolean z5, @Param(id = 12) CountrySpecification[] countrySpecificationArr, @Param(id = 13) boolean z6, @Param(id = 14) boolean z7, @Param(id = 15) ArrayList<CountrySpecification> arrayList, @Param(id = 16) PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, @Param(id = 17) ArrayList<Integer> arrayList2, @Param(id = 18) String str5) {
        this.zzax = str;
        this.zzdd = z;
        this.zzde = z2;
        this.zzdf = z3;
        this.zzdg = str2;
        this.zzao = str3;
        this.zzdh = str4;
        this.zzbh = cart;
        this.zzdi = z4;
        this.zzdj = z5;
        this.zzdk = countrySpecificationArr;
        this.zzdl = z6;
        this.zzdm = z7;
        this.zzdn = arrayList;
        this.zzdo = paymentMethodTokenizationParameters;
        this.zzai = arrayList2;
        this.zzh = str5;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final boolean allowDebitCard() {
        return this.zzdm;
    }

    public final boolean allowPrepaidCard() {
        return this.zzdl;
    }

    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzai;
    }

    public final ArrayList<CountrySpecification> getAllowedCountrySpecificationsForShipping() {
        return this.zzdn;
    }

    public final CountrySpecification[] getAllowedShippingCountrySpecifications() {
        return this.zzdk;
    }

    public final Cart getCart() {
        return this.zzbh;
    }

    public final String getCountryCode() {
        return this.zzh;
    }

    public final String getCurrencyCode() {
        return this.zzao;
    }

    public final String getEstimatedTotalPrice() {
        return this.zzdg;
    }

    public final String getMerchantName() {
        return this.zzdh;
    }

    public final String getMerchantTransactionId() {
        return this.zzax;
    }

    public final PaymentMethodTokenizationParameters getPaymentMethodTokenizationParameters() {
        return this.zzdo;
    }

    @Deprecated
    public final boolean isBillingAgreement() {
        return this.zzdj;
    }

    public final boolean isPhoneNumberRequired() {
        return this.zzdd;
    }

    public final boolean isShippingAddressRequired() {
        return this.zzde;
    }

    @Deprecated
    public final boolean useMinimalBillingAddress() {
        return this.zzdf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzax, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzdd);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzde);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzdf);
        SafeParcelWriter.writeString(parcel, 6, this.zzdg, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzao, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzdh, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzbh, i, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzdi);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzdj);
        SafeParcelWriter.writeTypedArray(parcel, 12, this.zzdk, i, false);
        SafeParcelWriter.writeBoolean(parcel, 13, this.zzdl);
        SafeParcelWriter.writeBoolean(parcel, 14, this.zzdm);
        SafeParcelWriter.writeTypedList(parcel, 15, this.zzdn, false);
        SafeParcelWriter.writeParcelable(parcel, 16, this.zzdo, i, false);
        SafeParcelWriter.writeIntegerList(parcel, 17, this.zzai, false);
        SafeParcelWriter.writeString(parcel, 18, this.zzh, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
