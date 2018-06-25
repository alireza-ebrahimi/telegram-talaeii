package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.CommonWalletObject;
import com.google.android.gms.wallet.wobs.CommonWalletObject.zza;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;
import java.util.Collection;

public final class GiftCardWalletObject extends zzbgl {
    public static final Creator<GiftCardWalletObject> CREATOR = new zzo();
    String pin;
    CommonWalletObject zzlkp = CommonWalletObject.zzblu().zzblv();
    String zzlkq;
    String zzlkr;
    long zzlks;
    String zzlkt;
    long zzlku;
    String zzlkv;

    public final class Builder {
        private zza zzlkw;
        private /* synthetic */ GiftCardWalletObject zzlkx;

        private Builder(GiftCardWalletObject giftCardWalletObject) {
            this.zzlkx = giftCardWalletObject;
            this.zzlkw = CommonWalletObject.zzblu();
        }

        public final Builder addImageModuleDataMainImageUri(UriData uriData) {
            this.zzlkw.zza(uriData);
            return this;
        }

        public final Builder addImageModuleDataMainImageUris(Collection<UriData> collection) {
            this.zzlkw.zzo(collection);
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRow(LabelValueRow labelValueRow) {
            this.zzlkw.zza(labelValueRow);
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRows(Collection<LabelValueRow> collection) {
            this.zzlkw.zzn(collection);
            return this;
        }

        public final Builder addLinksModuleDataUri(UriData uriData) {
            this.zzlkw.zzb(uriData);
            return this;
        }

        public final Builder addLinksModuleDataUris(Collection<UriData> collection) {
            this.zzlkw.zzq(collection);
            return this;
        }

        public final Builder addLocation(LatLng latLng) {
            this.zzlkw.zzb(latLng);
            return this;
        }

        public final Builder addLocations(Collection<LatLng> collection) {
            this.zzlkw.zzm(collection);
            return this;
        }

        public final Builder addMessage(WalletObjectMessage walletObjectMessage) {
            this.zzlkw.zza(walletObjectMessage);
            return this;
        }

        public final Builder addMessages(Collection<WalletObjectMessage> collection) {
            this.zzlkw.zzl(collection);
            return this;
        }

        public final Builder addTextModuleData(TextModuleData textModuleData) {
            this.zzlkw.zza(textModuleData);
            return this;
        }

        public final Builder addTextModulesData(Collection<TextModuleData> collection) {
            this.zzlkw.zzp(collection);
            return this;
        }

        public final GiftCardWalletObject build() {
            boolean z = true;
            zzbq.checkArgument(!TextUtils.isEmpty(this.zzlkx.zzlkq), "Card number is required.");
            this.zzlkx.zzlkp = this.zzlkw.zzblv();
            zzbq.checkArgument(!TextUtils.isEmpty(this.zzlkx.zzlkp.getName()), "Card name is required.");
            if (TextUtils.isEmpty(this.zzlkx.zzlkp.getIssuerName())) {
                z = false;
            }
            zzbq.checkArgument(z, "Card issuer name is required.");
            return this.zzlkx;
        }

        public final Builder setBalanceCurrencyCode(String str) {
            this.zzlkx.zzlkt = str;
            return this;
        }

        public final Builder setBalanceMicros(long j) {
            this.zzlkx.zzlks = j;
            return this;
        }

        public final Builder setBalanceUpdateTime(long j) {
            this.zzlkx.zzlku = j;
            return this;
        }

        public final Builder setBarcodeAlternateText(String str) {
            this.zzlkw.zznw(str);
            return this;
        }

        public final Builder setBarcodeLabel(String str) {
            this.zzlkw.zznz(str);
            return this;
        }

        public final Builder setBarcodeType(String str) {
            this.zzlkw.zznx(str);
            return this;
        }

        public final Builder setBarcodeValue(String str) {
            this.zzlkw.zzny(str);
            return this;
        }

        public final Builder setCardIdentifier(String str) {
            this.zzlkx.zzlkr = str;
            return this;
        }

        public final Builder setCardNumber(String str) {
            this.zzlkx.zzlkq = str;
            return this;
        }

        public final Builder setClassId(String str) {
            this.zzlkw.zznt(str);
            return this;
        }

        public final Builder setEventNumber(String str) {
            this.zzlkx.zzlkv = str;
            return this;
        }

        public final Builder setId(String str) {
            this.zzlkw.zzns(str);
            return this;
        }

        public final Builder setInfoModuleDataHexBackgroundColor(String str) {
            this.zzlkw.zzob(str);
            return this;
        }

        public final Builder setInfoModuleDataHexFontColor(String str) {
            this.zzlkw.zzoa(str);
            return this;
        }

        public final Builder setInfoModuleDataShowLastUpdateTime(boolean z) {
            this.zzlkw.zzcd(z);
            return this;
        }

        public final Builder setIssuerName(String str) {
            this.zzlkw.zznv(str);
            return this;
        }

        public final Builder setPin(String str) {
            this.zzlkx.pin = str;
            return this;
        }

        public final Builder setState(int i) {
            this.zzlkw.zzfr(i);
            return this;
        }

        public final Builder setTitle(String str) {
            this.zzlkw.zznu(str);
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            this.zzlkw.zza(timeInterval);
            return this;
        }
    }

    GiftCardWalletObject() {
    }

    GiftCardWalletObject(CommonWalletObject commonWalletObject, String str, String str2, String str3, long j, String str4, long j2, String str5) {
        this.zzlkp = commonWalletObject;
        this.zzlkq = str;
        this.pin = str2;
        this.zzlks = j;
        this.zzlkt = str4;
        this.zzlku = j2;
        this.zzlkv = str5;
        this.zzlkr = str3;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getBalanceCurrencyCode() {
        return this.zzlkt;
    }

    public final long getBalanceMicros() {
        return this.zzlks;
    }

    public final long getBalanceUpdateTime() {
        return this.zzlku;
    }

    public final String getBarcodeAlternateText() {
        return this.zzlkp.getBarcodeAlternateText();
    }

    public final String getBarcodeLabel() {
        return this.zzlkp.getBarcodeLabel();
    }

    public final String getBarcodeType() {
        return this.zzlkp.getBarcodeType();
    }

    public final String getBarcodeValue() {
        return this.zzlkp.getBarcodeValue();
    }

    public final String getCardIdentifier() {
        return this.zzlkr;
    }

    public final String getCardNumber() {
        return this.zzlkq;
    }

    public final String getClassId() {
        return this.zzlkp.getClassId();
    }

    public final String getEventNumber() {
        return this.zzlkv;
    }

    public final String getId() {
        return this.zzlkp.getId();
    }

    public final ArrayList<UriData> getImageModuleDataMainImageUris() {
        return this.zzlkp.getImageModuleDataMainImageUris();
    }

    public final String getInfoModuleDataHexBackgroundColor() {
        return this.zzlkp.getInfoModuleDataHexBackgroundColor();
    }

    public final String getInfoModuleDataHexFontColor() {
        return this.zzlkp.getInfoModuleDataHexFontColor();
    }

    public final ArrayList<LabelValueRow> getInfoModuleDataLabelValueRows() {
        return this.zzlkp.getInfoModuleDataLabelValueRows();
    }

    public final boolean getInfoModuleDataShowLastUpdateTime() {
        return this.zzlkp.getInfoModuleDataShowLastUpdateTime();
    }

    public final String getIssuerName() {
        return this.zzlkp.getIssuerName();
    }

    public final ArrayList<UriData> getLinksModuleDataUris() {
        return this.zzlkp.getLinksModuleDataUris();
    }

    public final ArrayList<LatLng> getLocations() {
        return this.zzlkp.getLocations();
    }

    public final ArrayList<WalletObjectMessage> getMessages() {
        return this.zzlkp.getMessages();
    }

    public final String getPin() {
        return this.pin;
    }

    public final int getState() {
        return this.zzlkp.getState();
    }

    public final ArrayList<TextModuleData> getTextModulesData() {
        return this.zzlkp.getTextModulesData();
    }

    public final String getTitle() {
        return this.zzlkp.getName();
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzlkp.getValidTimeInterval();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlkp, i, false);
        zzbgo.zza(parcel, 3, this.zzlkq, false);
        zzbgo.zza(parcel, 4, this.pin, false);
        zzbgo.zza(parcel, 5, this.zzlkr, false);
        zzbgo.zza(parcel, 6, this.zzlks);
        zzbgo.zza(parcel, 7, this.zzlkt, false);
        zzbgo.zza(parcel, 8, this.zzlku);
        zzbgo.zza(parcel, 9, this.zzlkv, false);
        zzbgo.zzai(parcel, zze);
    }
}
