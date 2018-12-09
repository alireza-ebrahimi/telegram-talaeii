package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.LoyaltyPoints;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;
import java.util.Collection;

@Class(creator = "LoyaltyWalletObjectCreator")
@Reserved({1})
public final class LoyaltyWalletObject extends AbstractSafeParcelable {
    public static final Creator<LoyaltyWalletObject> CREATOR = new zzv();
    @Field(id = 12)
    int state;
    @Field(id = 2)
    String zzce;
    @Field(id = 3)
    String zzcf;
    @Field(id = 4)
    String zzcg;
    @Field(id = 5)
    String zzch;
    @Field(id = 6)
    String zzci;
    @Field(id = 7)
    String zzcj;
    @Field(id = 8)
    String zzck;
    @Field(id = 9)
    String zzcl;
    @Field(id = 10)
    String zzcm;
    @Field(id = 11)
    String zzcn;
    @Field(defaultValueUnchecked = "com.google.android.gms.common.util.ArrayUtils.newArrayList()", id = 13)
    ArrayList<WalletObjectMessage> zzco;
    @Field(id = 14)
    TimeInterval zzcp;
    @Field(defaultValueUnchecked = "com.google.android.gms.common.util.ArrayUtils.newArrayList()", id = 15)
    ArrayList<LatLng> zzcq;
    @Field(id = 16)
    String zzcr;
    @Field(id = 17)
    String zzcs;
    @Field(defaultValueUnchecked = "com.google.android.gms.common.util.ArrayUtils.newArrayList()", id = 18)
    ArrayList<LabelValueRow> zzct;
    @Field(id = 19)
    boolean zzcu;
    @Field(defaultValueUnchecked = "com.google.android.gms.common.util.ArrayUtils.newArrayList()", id = 20)
    ArrayList<UriData> zzcv;
    @Field(defaultValueUnchecked = "com.google.android.gms.common.util.ArrayUtils.newArrayList()", id = 21)
    ArrayList<TextModuleData> zzcw;
    @Field(defaultValueUnchecked = "com.google.android.gms.common.util.ArrayUtils.newArrayList()", id = 22)
    ArrayList<UriData> zzcx;
    @Field(id = 23)
    LoyaltyPoints zzcy;

    public final class Builder {
        private final /* synthetic */ LoyaltyWalletObject zzcz;

        private Builder(LoyaltyWalletObject loyaltyWalletObject) {
            this.zzcz = loyaltyWalletObject;
        }

        public final Builder addImageModuleDataMainImageUri(UriData uriData) {
            this.zzcz.zzcv.add(uriData);
            return this;
        }

        public final Builder addImageModuleDataMainImageUris(Collection<UriData> collection) {
            this.zzcz.zzcv.addAll(collection);
            return this;
        }

        public final Builder addInfoModuleDataLabeValueRow(LabelValueRow labelValueRow) {
            this.zzcz.zzct.add(labelValueRow);
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRows(Collection<LabelValueRow> collection) {
            this.zzcz.zzct.addAll(collection);
            return this;
        }

        public final Builder addLinksModuleDataUri(UriData uriData) {
            this.zzcz.zzcx.add(uriData);
            return this;
        }

        public final Builder addLinksModuleDataUris(Collection<UriData> collection) {
            this.zzcz.zzcx.addAll(collection);
            return this;
        }

        public final Builder addLocation(LatLng latLng) {
            this.zzcz.zzcq.add(latLng);
            return this;
        }

        public final Builder addLocations(Collection<LatLng> collection) {
            this.zzcz.zzcq.addAll(collection);
            return this;
        }

        public final Builder addMessage(WalletObjectMessage walletObjectMessage) {
            this.zzcz.zzco.add(walletObjectMessage);
            return this;
        }

        public final Builder addMessages(Collection<WalletObjectMessage> collection) {
            this.zzcz.zzco.addAll(collection);
            return this;
        }

        public final Builder addTextModuleData(TextModuleData textModuleData) {
            this.zzcz.zzcw.add(textModuleData);
            return this;
        }

        public final Builder addTextModulesData(Collection<TextModuleData> collection) {
            this.zzcz.zzcw.addAll(collection);
            return this;
        }

        public final LoyaltyWalletObject build() {
            return this.zzcz;
        }

        public final Builder setAccountId(String str) {
            this.zzcz.zzcf = str;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zzcz.zzci = str;
            return this;
        }

        public final Builder setBarcodeAlternateText(String str) {
            this.zzcz.zzcj = str;
            return this;
        }

        public final Builder setBarcodeLabel(String str) {
            this.zzcz.zzcm = str;
            return this;
        }

        public final Builder setBarcodeType(String str) {
            this.zzcz.zzck = str;
            return this;
        }

        public final Builder setBarcodeValue(String str) {
            this.zzcz.zzcl = str;
            return this;
        }

        public final Builder setClassId(String str) {
            this.zzcz.zzcn = str;
            return this;
        }

        public final Builder setId(String str) {
            this.zzcz.zzce = str;
            return this;
        }

        public final Builder setInfoModuleDataHexBackgroundColor(String str) {
            this.zzcz.zzcs = str;
            return this;
        }

        public final Builder setInfoModuleDataHexFontColor(String str) {
            this.zzcz.zzcr = str;
            return this;
        }

        public final Builder setInfoModuleDataShowLastUpdateTime(boolean z) {
            this.zzcz.zzcu = z;
            return this;
        }

        public final Builder setIssuerName(String str) {
            this.zzcz.zzcg = str;
            return this;
        }

        public final Builder setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
            this.zzcz.zzcy = loyaltyPoints;
            return this;
        }

        public final Builder setProgramName(String str) {
            this.zzcz.zzch = str;
            return this;
        }

        public final Builder setState(int i) {
            this.zzcz.state = i;
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            this.zzcz.zzcp = timeInterval;
            return this;
        }
    }

    LoyaltyWalletObject() {
        this.zzco = ArrayUtils.newArrayList();
        this.zzcq = ArrayUtils.newArrayList();
        this.zzct = ArrayUtils.newArrayList();
        this.zzcv = ArrayUtils.newArrayList();
        this.zzcw = ArrayUtils.newArrayList();
        this.zzcx = ArrayUtils.newArrayList();
    }

    @Constructor
    LoyaltyWalletObject(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7, @Param(id = 9) String str8, @Param(id = 10) String str9, @Param(id = 11) String str10, @Param(id = 12) int i, @Param(id = 13) ArrayList<WalletObjectMessage> arrayList, @Param(id = 14) TimeInterval timeInterval, @Param(id = 15) ArrayList<LatLng> arrayList2, @Param(id = 16) String str11, @Param(id = 17) String str12, @Param(id = 18) ArrayList<LabelValueRow> arrayList3, @Param(id = 19) boolean z, @Param(id = 20) ArrayList<UriData> arrayList4, @Param(id = 21) ArrayList<TextModuleData> arrayList5, @Param(id = 22) ArrayList<UriData> arrayList6, @Param(id = 23) LoyaltyPoints loyaltyPoints) {
        this.zzce = str;
        this.zzcf = str2;
        this.zzcg = str3;
        this.zzch = str4;
        this.zzci = str5;
        this.zzcj = str6;
        this.zzck = str7;
        this.zzcl = str8;
        this.zzcm = str9;
        this.zzcn = str10;
        this.state = i;
        this.zzco = arrayList;
        this.zzcp = timeInterval;
        this.zzcq = arrayList2;
        this.zzcr = str11;
        this.zzcs = str12;
        this.zzct = arrayList3;
        this.zzcu = z;
        this.zzcv = arrayList4;
        this.zzcw = arrayList5;
        this.zzcx = arrayList6;
        this.zzcy = loyaltyPoints;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getAccountId() {
        return this.zzcf;
    }

    public final String getAccountName() {
        return this.zzci;
    }

    public final String getBarcodeAlternateText() {
        return this.zzcj;
    }

    public final String getBarcodeLabel() {
        return this.zzcm;
    }

    public final String getBarcodeType() {
        return this.zzck;
    }

    public final String getBarcodeValue() {
        return this.zzcl;
    }

    public final String getClassId() {
        return this.zzcn;
    }

    public final String getId() {
        return this.zzce;
    }

    public final ArrayList<UriData> getImageModuleDataMainImageUris() {
        return this.zzcv;
    }

    public final String getInfoModuleDataHexBackgroundColor() {
        return this.zzcs;
    }

    public final String getInfoModuleDataHexFontColor() {
        return this.zzcr;
    }

    public final ArrayList<LabelValueRow> getInfoModuleDataLabelValueRows() {
        return this.zzct;
    }

    public final boolean getInfoModuleDataShowLastUpdateTime() {
        return this.zzcu;
    }

    public final String getIssuerName() {
        return this.zzcg;
    }

    public final ArrayList<UriData> getLinksModuleDataUris() {
        return this.zzcx;
    }

    public final ArrayList<LatLng> getLocations() {
        return this.zzcq;
    }

    public final LoyaltyPoints getLoyaltyPoints() {
        return this.zzcy;
    }

    public final ArrayList<WalletObjectMessage> getMessages() {
        return this.zzco;
    }

    public final String getProgramName() {
        return this.zzch;
    }

    public final int getState() {
        return this.state;
    }

    public final ArrayList<TextModuleData> getTextModulesData() {
        return this.zzcw;
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzcp;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzce, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzcf, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzcg, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzch, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzci, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzcj, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzck, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzcl, false);
        SafeParcelWriter.writeString(parcel, 10, this.zzcm, false);
        SafeParcelWriter.writeString(parcel, 11, this.zzcn, false);
        SafeParcelWriter.writeInt(parcel, 12, this.state);
        SafeParcelWriter.writeTypedList(parcel, 13, this.zzco, false);
        SafeParcelWriter.writeParcelable(parcel, 14, this.zzcp, i, false);
        SafeParcelWriter.writeTypedList(parcel, 15, this.zzcq, false);
        SafeParcelWriter.writeString(parcel, 16, this.zzcr, false);
        SafeParcelWriter.writeString(parcel, 17, this.zzcs, false);
        SafeParcelWriter.writeTypedList(parcel, 18, this.zzct, false);
        SafeParcelWriter.writeBoolean(parcel, 19, this.zzcu);
        SafeParcelWriter.writeTypedList(parcel, 20, this.zzcv, false);
        SafeParcelWriter.writeTypedList(parcel, 21, this.zzcw, false);
        SafeParcelWriter.writeTypedList(parcel, 22, this.zzcx, false);
        SafeParcelWriter.writeParcelable(parcel, 23, this.zzcy, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
