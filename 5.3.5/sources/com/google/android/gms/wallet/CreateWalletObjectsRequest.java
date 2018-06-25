package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class CreateWalletObjectsRequest extends zzbgl {
    public static final Creator<CreateWalletObjectsRequest> CREATOR = new zzj();
    public static final int REQUEST_IMMEDIATE_SAVE = 1;
    public static final int SHOW_SAVE_PROMPT = 0;
    LoyaltyWalletObject zzljx;
    OfferWalletObject zzljy;
    GiftCardWalletObject zzljz;
    int zzlka;

    public final class Builder {
        private /* synthetic */ CreateWalletObjectsRequest zzlkb;

        private Builder(CreateWalletObjectsRequest createWalletObjectsRequest) {
            this.zzlkb = createWalletObjectsRequest;
        }

        public final CreateWalletObjectsRequest build() {
            boolean z = true;
            if ((this.zzlkb.zzljy == null ? 0 : 1) + ((this.zzlkb.zzljx == null ? 0 : 1) + (this.zzlkb.zzljz == null ? 0 : 1)) != 1) {
                z = false;
            }
            zzbq.zza(z, (Object) "CreateWalletObjectsRequest must have exactly one Wallet Object");
            return this.zzlkb;
        }

        public final Builder setCreateMode(int i) {
            this.zzlkb.zzlka = i;
            return this;
        }

        public final Builder setGiftCardWalletObject(GiftCardWalletObject giftCardWalletObject) {
            this.zzlkb.zzljz = giftCardWalletObject;
            return this;
        }

        public final Builder setLoyaltyWalletObject(LoyaltyWalletObject loyaltyWalletObject) {
            this.zzlkb.zzljx = loyaltyWalletObject;
            return this;
        }

        public final Builder setOfferWalletObject(OfferWalletObject offerWalletObject) {
            this.zzlkb.zzljy = offerWalletObject;
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
        this.zzljz = giftCardWalletObject;
    }

    @Deprecated
    public CreateWalletObjectsRequest(LoyaltyWalletObject loyaltyWalletObject) {
        this.zzljx = loyaltyWalletObject;
    }

    CreateWalletObjectsRequest(LoyaltyWalletObject loyaltyWalletObject, OfferWalletObject offerWalletObject, GiftCardWalletObject giftCardWalletObject, int i) {
        this.zzljx = loyaltyWalletObject;
        this.zzljy = offerWalletObject;
        this.zzljz = giftCardWalletObject;
        this.zzlka = i;
    }

    @Deprecated
    public CreateWalletObjectsRequest(OfferWalletObject offerWalletObject) {
        this.zzljy = offerWalletObject;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final int getCreateMode() {
        return this.zzlka;
    }

    public final GiftCardWalletObject getGiftCardWalletObject() {
        return this.zzljz;
    }

    public final LoyaltyWalletObject getLoyaltyWalletObject() {
        return this.zzljx;
    }

    public final OfferWalletObject getOfferWalletObject() {
        return this.zzljy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzljx, i, false);
        zzbgo.zza(parcel, 3, this.zzljy, i, false);
        zzbgo.zza(parcel, 4, this.zzljz, i, false);
        zzbgo.zzc(parcel, 5, this.zzlka);
        zzbgo.zzai(parcel, zze);
    }
}
