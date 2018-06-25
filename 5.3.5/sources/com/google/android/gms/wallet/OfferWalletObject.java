package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
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

public final class OfferWalletObject extends zzbgl {
    public static final Creator<OfferWalletObject> CREATOR = new zzab();
    private final int zzehz;
    CommonWalletObject zzlkp;
    String zzlmv;
    String zzwc;

    public final class Builder {
        private zza zzlkw;
        private /* synthetic */ OfferWalletObject zzlmw;

        private Builder(OfferWalletObject offerWalletObject) {
            this.zzlmw = offerWalletObject;
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

        public final OfferWalletObject build() {
            this.zzlmw.zzlkp = this.zzlkw.zzblv();
            return this.zzlmw;
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

        public final Builder setClassId(String str) {
            this.zzlkw.zznt(str);
            return this;
        }

        public final Builder setId(String str) {
            this.zzlkw.zzns(str);
            this.zzlmw.zzwc = str;
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

        public final Builder setRedemptionCode(String str) {
            this.zzlmw.zzlmv = str;
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

    OfferWalletObject() {
        this.zzehz = 3;
    }

    OfferWalletObject(int i, String str, String str2, CommonWalletObject commonWalletObject) {
        this.zzehz = i;
        this.zzlmv = str2;
        if (i < 3) {
            this.zzlkp = CommonWalletObject.zzblu().zzns(str).zzblv();
        } else {
            this.zzlkp = commonWalletObject;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
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

    public final String getClassId() {
        return this.zzlkp.getClassId();
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

    public final String getRedemptionCode() {
        return this.zzlmv;
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

    public final int getVersionCode() {
        return this.zzehz;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, getVersionCode());
        zzbgo.zza(parcel, 2, this.zzwc, false);
        zzbgo.zza(parcel, 3, this.zzlmv, false);
        zzbgo.zza(parcel, 4, this.zzlkp, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
