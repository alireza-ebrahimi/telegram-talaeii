package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;

@KeepName
@Hide
public class CommonWalletObject extends zzbgl {
    public static final Creator<CommonWalletObject> CREATOR = new zzb();
    String name;
    int state;
    String zzlll;
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
    String zzwc;

    @Hide
    public final class zza {
        private /* synthetic */ CommonWalletObject zzlpt;

        private zza(CommonWalletObject commonWalletObject) {
            this.zzlpt = commonWalletObject;
        }

        public final zza zza(LabelValueRow labelValueRow) {
            this.zzlpt.zzllx.add(labelValueRow);
            return this;
        }

        public final zza zza(TextModuleData textModuleData) {
            this.zzlpt.zzlma.add(textModuleData);
            return this;
        }

        public final zza zza(TimeInterval timeInterval) {
            this.zzlpt.zzllt = timeInterval;
            return this;
        }

        public final zza zza(UriData uriData) {
            this.zzlpt.zzllz.add(uriData);
            return this;
        }

        public final zza zza(WalletObjectMessage walletObjectMessage) {
            this.zzlpt.zzlls.add(walletObjectMessage);
            return this;
        }

        public final zza zzb(LatLng latLng) {
            this.zzlpt.zzllu.add(latLng);
            return this;
        }

        public final zza zzb(UriData uriData) {
            this.zzlpt.zzlmb.add(uriData);
            return this;
        }

        public final CommonWalletObject zzblv() {
            return this.zzlpt;
        }

        public final zza zzcd(boolean z) {
            this.zzlpt.zzlly = z;
            return this;
        }

        public final zza zzfr(int i) {
            this.zzlpt.state = i;
            return this;
        }

        public final zza zzl(Collection<WalletObjectMessage> collection) {
            this.zzlpt.zzlls.addAll(collection);
            return this;
        }

        public final zza zzm(Collection<LatLng> collection) {
            this.zzlpt.zzllu.addAll(collection);
            return this;
        }

        public final zza zzn(Collection<LabelValueRow> collection) {
            this.zzlpt.zzllx.addAll(collection);
            return this;
        }

        public final zza zzns(String str) {
            this.zzlpt.zzwc = str;
            return this;
        }

        public final zza zznt(String str) {
            this.zzlpt.zzllr = str;
            return this;
        }

        public final zza zznu(String str) {
            this.zzlpt.name = str;
            return this;
        }

        public final zza zznv(String str) {
            this.zzlpt.zzlll = str;
            return this;
        }

        public final zza zznw(String str) {
            this.zzlpt.zzlln = str;
            return this;
        }

        public final zza zznx(String str) {
            this.zzlpt.zzllo = str;
            return this;
        }

        public final zza zzny(String str) {
            this.zzlpt.zzllp = str;
            return this;
        }

        public final zza zznz(String str) {
            this.zzlpt.zzllq = str;
            return this;
        }

        public final zza zzo(Collection<UriData> collection) {
            this.zzlpt.zzllz.addAll(collection);
            return this;
        }

        public final zza zzoa(String str) {
            this.zzlpt.zzllv = str;
            return this;
        }

        public final zza zzob(String str) {
            this.zzlpt.zzllw = str;
            return this;
        }

        public final zza zzp(Collection<TextModuleData> collection) {
            this.zzlpt.zzlma.addAll(collection);
            return this;
        }

        public final zza zzq(Collection<UriData> collection) {
            this.zzlpt.zzlmb.addAll(collection);
            return this;
        }
    }

    CommonWalletObject() {
        this.zzlls = new ArrayList();
        this.zzllu = new ArrayList();
        this.zzllx = new ArrayList();
        this.zzllz = new ArrayList();
        this.zzlma = new ArrayList();
        this.zzlmb = new ArrayList();
    }

    CommonWalletObject(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, ArrayList<WalletObjectMessage> arrayList, TimeInterval timeInterval, ArrayList<LatLng> arrayList2, String str9, String str10, ArrayList<LabelValueRow> arrayList3, boolean z, ArrayList<UriData> arrayList4, ArrayList<TextModuleData> arrayList5, ArrayList<UriData> arrayList6) {
        this.zzwc = str;
        this.zzllr = str2;
        this.name = str3;
        this.zzlll = str4;
        this.zzlln = str5;
        this.zzllo = str6;
        this.zzllp = str7;
        this.zzllq = str8;
        this.state = i;
        this.zzlls = arrayList;
        this.zzllt = timeInterval;
        this.zzllu = arrayList2;
        this.zzllv = str9;
        this.zzllw = str10;
        this.zzllx = arrayList3;
        this.zzlly = z;
        this.zzllz = arrayList4;
        this.zzlma = arrayList5;
        this.zzlmb = arrayList6;
    }

    @Hide
    public static zza zzblu() {
        return new zza();
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

    public final ArrayList<WalletObjectMessage> getMessages() {
        return this.zzlls;
    }

    public final String getName() {
        return this.name;
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

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzwc, false);
        zzbgo.zza(parcel, 3, this.zzllr, false);
        zzbgo.zza(parcel, 4, this.name, false);
        zzbgo.zza(parcel, 5, this.zzlll, false);
        zzbgo.zza(parcel, 6, this.zzlln, false);
        zzbgo.zza(parcel, 7, this.zzllo, false);
        zzbgo.zza(parcel, 8, this.zzllp, false);
        zzbgo.zza(parcel, 9, this.zzllq, false);
        zzbgo.zzc(parcel, 10, this.state);
        zzbgo.zzc(parcel, 11, this.zzlls, false);
        zzbgo.zza(parcel, 12, this.zzllt, i, false);
        zzbgo.zzc(parcel, 13, this.zzllu, false);
        zzbgo.zza(parcel, 14, this.zzllv, false);
        zzbgo.zza(parcel, 15, this.zzllw, false);
        zzbgo.zzc(parcel, 16, this.zzllx, false);
        zzbgo.zza(parcel, 17, this.zzlly);
        zzbgo.zzc(parcel, 18, this.zzllz, false);
        zzbgo.zzc(parcel, 19, this.zzlma, false);
        zzbgo.zzc(parcel, 20, this.zzlmb, false);
        zzbgo.zzai(parcel, zze);
    }
}
