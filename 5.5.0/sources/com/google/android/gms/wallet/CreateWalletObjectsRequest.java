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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Class(creator = "CreateWalletObjectsRequestCreator")
@Reserved({1})
public final class CreateWalletObjectsRequest extends AbstractSafeParcelable {
    public static final Creator<CreateWalletObjectsRequest> CREATOR = new zzj();
    public static final int REQUEST_IMMEDIATE_SAVE = 1;
    public static final int SHOW_SAVE_PROMPT = 0;
    @Field(id = 2)
    LoyaltyWalletObject zzar;
    @Field(id = 3)
    OfferWalletObject zzas;
    @Field(id = 4)
    GiftCardWalletObject zzat;
    @Field(id = 5)
    int zzau;

    public final class Builder {
        private final /* synthetic */ CreateWalletObjectsRequest zzav;

        private Builder(CreateWalletObjectsRequest createWalletObjectsRequest) {
            this.zzav = createWalletObjectsRequest;
        }

        public final CreateWalletObjectsRequest build() {
            boolean z = true;
            if ((this.zzav.zzas == null ? 0 : 1) + ((this.zzav.zzar == null ? 0 : 1) + (this.zzav.zzat == null ? 0 : 1)) != 1) {
                z = false;
            }
            Preconditions.checkState(z, "CreateWalletObjectsRequest must have exactly one Wallet Object");
            return this.zzav;
        }

        public final Builder setCreateMode(int i) {
            this.zzav.zzau = i;
            return this;
        }

        public final Builder setGiftCardWalletObject(GiftCardWalletObject giftCardWalletObject) {
            this.zzav.zzat = giftCardWalletObject;
            return this;
        }

        public final Builder setLoyaltyWalletObject(LoyaltyWalletObject loyaltyWalletObject) {
            this.zzav.zzar = loyaltyWalletObject;
            return this;
        }

        public final Builder setOfferWalletObject(OfferWalletObject offerWalletObject) {
            this.zzav.zzas = offerWalletObject;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CreateMode {
    }

    CreateWalletObjectsRequest() {
    }

    @Deprecated
    public CreateWalletObjectsRequest(GiftCardWalletObject giftCardWalletObject) {
        this.zzat = giftCardWalletObject;
    }

    @Deprecated
    public CreateWalletObjectsRequest(LoyaltyWalletObject loyaltyWalletObject) {
        this.zzar = loyaltyWalletObject;
    }

    @Constructor
    CreateWalletObjectsRequest(@Param(id = 2) LoyaltyWalletObject loyaltyWalletObject, @Param(id = 3) OfferWalletObject offerWalletObject, @Param(id = 4) GiftCardWalletObject giftCardWalletObject, @Param(id = 5) int i) {
        this.zzar = loyaltyWalletObject;
        this.zzas = offerWalletObject;
        this.zzat = giftCardWalletObject;
        this.zzau = i;
    }

    @Deprecated
    public CreateWalletObjectsRequest(OfferWalletObject offerWalletObject) {
        this.zzas = offerWalletObject;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final int getCreateMode() {
        return this.zzau;
    }

    public final GiftCardWalletObject getGiftCardWalletObject() {
        return this.zzat;
    }

    public final LoyaltyWalletObject getLoyaltyWalletObject() {
        return this.zzar;
    }

    public final OfferWalletObject getOfferWalletObject() {
        return this.zzas;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzar, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzas, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzat, i, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzau);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
