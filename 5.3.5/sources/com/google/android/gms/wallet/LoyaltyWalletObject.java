package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.LoyaltyPoints;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;
import java.util.Collection;

public final class LoyaltyWalletObject extends zzbgl {
    public static final Creator<LoyaltyWalletObject> CREATOR = new zzv();
    int state;
    String zzgog;
    String zzllk;
    String zzlll;
    String zzllm;
    String zzlln;
    String zzllo;
    String zzllp;
    String zzllq;
    String zzllr;
    ArrayList<WalletObjectMessage> zzlls;
    TimeInterval zzllt;
    ArrayList<LatLng> zzllu;
    String zzllv;
    String zzllw;
    ArrayList<LabelValueRow> zzllx;
    boolean zzlly;
    ArrayList<UriData> zzllz;
    ArrayList<TextModuleData> zzlma;
    ArrayList<UriData> zzlmb;
    LoyaltyPoints zzlmc;
    String zzwc;

    public final class Builder {
        private /* synthetic */ LoyaltyWalletObject zzlmd;

        private Builder(LoyaltyWalletObject loyaltyWalletObject) {
            this.zzlmd = loyaltyWalletObject;
        }

        public final Builder addImageModuleDataMainImageUri(UriData uriData) {
            this.zzlmd.zzllz.add(uriData);
            return this;
        }

        public final Builder addImageModuleDataMainImageUris(Collection<UriData> collection) {
            this.zzlmd.zzllz.addAll(collection);
            return this;
        }

        public final Builder addInfoModuleDataLabeValueRow(LabelValueRow labelValueRow) {
            this.zzlmd.zzllx.add(labelValueRow);
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRows(Collection<LabelValueRow> collection) {
            this.zzlmd.zzllx.addAll(collection);
            return this;
        }

        public final Builder addLinksModuleDataUri(UriData uriData) {
            this.zzlmd.zzlmb.add(uriData);
            return this;
        }

        public final Builder addLinksModuleDataUris(Collection<UriData> collection) {
            this.zzlmd.zzlmb.addAll(collection);
            return this;
        }

        public final Builder addLocation(LatLng latLng) {
            this.zzlmd.zzllu.add(latLng);
            return this;
        }

        public final Builder addLocations(Collection<LatLng> collection) {
            this.zzlmd.zzllu.addAll(collection);
            return this;
        }

        public final Builder addMessage(WalletObjectMessage walletObjectMessage) {
            this.zzlmd.zzlls.add(walletObjectMessage);
            return this;
        }

        public final Builder addMessages(Collection<WalletObjectMessage> collection) {
            this.zzlmd.zzlls.addAll(collection);
            return this;
        }

        public final Builder addTextModuleData(TextModuleData textModuleData) {
            this.zzlmd.zzlma.add(textModuleData);
            return this;
        }

        public final Builder addTextModulesData(Collection<TextModuleData> collection) {
            this.zzlmd.zzlma.addAll(collection);
            return this;
        }

        public final LoyaltyWalletObject build() {
            return this.zzlmd;
        }

        public final Builder setAccountId(String str) {
            this.zzlmd.zzllk = str;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zzlmd.zzgog = str;
            return this;
        }

        public final Builder setBarcodeAlternateText(String str) {
            this.zzlmd.zzlln = str;
            return this;
        }

        public final Builder setBarcodeLabel(String str) {
            this.zzlmd.zzllq = str;
            return this;
        }

        public final Builder setBarcodeType(String str) {
            this.zzlmd.zzllo = str;
            return this;
        }

        public final Builder setBarcodeValue(String str) {
            this.zzlmd.zzllp = str;
            return this;
        }

        public final Builder setClassId(String str) {
            this.zzlmd.zzllr = str;
            return this;
        }

        public final Builder setId(String str) {
            this.zzlmd.zzwc = str;
            return this;
        }

        public final Builder setInfoModuleDataHexBackgroundColor(String str) {
            this.zzlmd.zzllw = str;
            return this;
        }

        public final Builder setInfoModuleDataHexFontColor(String str) {
            this.zzlmd.zzllv = str;
            return this;
        }

        public final Builder setInfoModuleDataShowLastUpdateTime(boolean z) {
            this.zzlmd.zzlly = z;
            return this;
        }

        public final Builder setIssuerName(String str) {
            this.zzlmd.zzlll = str;
            return this;
        }

        public final Builder setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
            this.zzlmd.zzlmc = loyaltyPoints;
            return this;
        }

        public final Builder setProgramName(String str) {
            this.zzlmd.zzllm = str;
            return this;
        }

        public final Builder setState(int i) {
            this.zzlmd.state = i;
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            this.zzlmd.zzllt = timeInterval;
            return this;
        }
    }

    LoyaltyWalletObject() {
        this.zzlls = new ArrayList();
        this.zzllu = new ArrayList();
        this.zzllx = new ArrayList();
        this.zzllz = new ArrayList();
        this.zzlma = new ArrayList();
        this.zzlmb = new ArrayList();
    }

    LoyaltyWalletObject(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, ArrayList<WalletObjectMessage> arrayList, TimeInterval timeInterval, ArrayList<LatLng> arrayList2, String str11, String str12, ArrayList<LabelValueRow> arrayList3, boolean z, ArrayList<UriData> arrayList4, ArrayList<TextModuleData> arrayList5, ArrayList<UriData> arrayList6, LoyaltyPoints loyaltyPoints) {
        this.zzwc = str;
        this.zzllk = str2;
        this.zzlll = str3;
        this.zzllm = str4;
        this.zzgog = str5;
        this.zzlln = str6;
        this.zzllo = str7;
        this.zzllp = str8;
        this.zzllq = str9;
        this.zzllr = str10;
        this.state = i;
        this.zzlls = arrayList;
        this.zzllt = timeInterval;
        this.zzllu = arrayList2;
        this.zzllv = str11;
        this.zzllw = str12;
        this.zzllx = arrayList3;
        this.zzlly = z;
        this.zzllz = arrayList4;
        this.zzlma = arrayList5;
        this.zzlmb = arrayList6;
        this.zzlmc = loyaltyPoints;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getAccountId() {
        return this.zzllk;
    }

    public final String getAccountName() {
        return this.zzgog;
    }

    public final String getBarcodeAlternateText() {
        return this.zzlln;
    }

    public final String getBarcodeLabel() {
        return this.zzllq;
    }

    public final String getBarcodeType() {
        return this.zzllo;
    }

    public final String getBarcodeValue() {
        return this.zzllp;
    }

    public final String getClassId() {
        return this.zzllr;
    }

    public final String getId() {
        return this.zzwc;
    }

    public final ArrayList<UriData> getImageModuleDataMainImageUris() {
        return this.zzllz;
    }

    public final String getInfoModuleDataHexBackgroundColor() {
        return this.zzllw;
    }

    public final String getInfoModuleDataHexFontColor() {
        return this.zzllv;
    }

    public final ArrayList<LabelValueRow> getInfoModuleDataLabelValueRows() {
        return this.zzllx;
    }

    public final boolean getInfoModuleDataShowLastUpdateTime() {
        return this.zzlly;
    }

    public final String getIssuerName() {
        return this.zzlll;
    }

    public final ArrayList<UriData> getLinksModuleDataUris() {
        return this.zzlmb;
    }

    public final ArrayList<LatLng> getLocations() {
        return this.zzllu;
    }

    public final LoyaltyPoints getLoyaltyPoints() {
        return this.zzlmc;
    }

    public final ArrayList<WalletObjectMessage> getMessages() {
        return this.zzlls;
    }

    public final String getProgramName() {
        return this.zzllm;
    }

    public final int getState() {
        return this.state;
    }

    public final ArrayList<TextModuleData> getTextModulesData() {
        return this.zzlma;
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzllt;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzwc, false);
        zzbgo.zza(parcel, 3, this.zzllk, false);
        zzbgo.zza(parcel, 4, this.zzlll, false);
        zzbgo.zza(parcel, 5, this.zzllm, false);
        zzbgo.zza(parcel, 6, this.zzgog, false);
        zzbgo.zza(parcel, 7, this.zzlln, false);
        zzbgo.zza(parcel, 8, this.zzllo, false);
        zzbgo.zza(parcel, 9, this.zzllp, false);
        zzbgo.zza(parcel, 10, this.zzllq, false);
        zzbgo.zza(parcel, 11, this.zzllr, false);
        zzbgo.zzc(parcel, 12, this.state);
        zzbgo.zzc(parcel, 13, this.zzlls, false);
        zzbgo.zza(parcel, 14, this.zzllt, i, false);
        zzbgo.zzc(parcel, 15, this.zzllu, false);
        zzbgo.zza(parcel, 16, this.zzllv, false);
        zzbgo.zza(parcel, 17, this.zzllw, false);
        zzbgo.zzc(parcel, 18, this.zzllx, false);
        zzbgo.zza(parcel, 19, this.zzlly);
        zzbgo.zzc(parcel, 20, this.zzllz, false);
        zzbgo.zzc(parcel, 21, this.zzlma, false);
        zzbgo.zzc(parcel, 22, this.zzlmb, false);
        zzbgo.zza(parcel, 23, this.zzlmc, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
