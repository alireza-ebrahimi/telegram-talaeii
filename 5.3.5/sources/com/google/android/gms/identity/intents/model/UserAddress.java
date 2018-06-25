package com.google.android.gms.identity.intents.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.identity.intents.AddressConstants.Extras;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class UserAddress extends zzbgl implements ReflectedParcelable {
    public static final Creator<UserAddress> CREATOR = new zzb();
    private String name;
    private String phoneNumber;
    private String zzcyf;
    private String zzilq;
    private String zzilr;
    private String zzils;
    private String zzilt;
    private String zzilu;
    private String zzilv;
    private String zzilw;
    private String zzilx;
    private String zzily;
    private boolean zzilz;
    private String zzima;
    private String zzimb;

    UserAddress() {
    }

    UserAddress(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, String str13, String str14) {
        this.name = str;
        this.zzilq = str2;
        this.zzilr = str3;
        this.zzils = str4;
        this.zzilt = str5;
        this.zzilu = str6;
        this.zzilv = str7;
        this.zzilw = str8;
        this.zzcyf = str9;
        this.zzilx = str10;
        this.zzily = str11;
        this.phoneNumber = str12;
        this.zzilz = z;
        this.zzima = str13;
        this.zzimb = str14;
    }

    public static UserAddress fromIntent(Intent intent) {
        return (intent == null || !intent.hasExtra(Extras.EXTRA_ADDRESS)) ? null : (UserAddress) intent.getParcelableExtra(Extras.EXTRA_ADDRESS);
    }

    public final String getAddress1() {
        return this.zzilq;
    }

    public final String getAddress2() {
        return this.zzilr;
    }

    public final String getAddress3() {
        return this.zzils;
    }

    public final String getAddress4() {
        return this.zzilt;
    }

    public final String getAddress5() {
        return this.zzilu;
    }

    public final String getAdministrativeArea() {
        return this.zzilv;
    }

    public final String getCompanyName() {
        return this.zzima;
    }

    public final String getCountryCode() {
        return this.zzcyf;
    }

    public final String getEmailAddress() {
        return this.zzimb;
    }

    public final String getLocality() {
        return this.zzilw;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getPostalCode() {
        return this.zzilx;
    }

    public final String getSortingCode() {
        return this.zzily;
    }

    public final boolean isPostBox() {
        return this.zzilz;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.name, false);
        zzbgo.zza(parcel, 3, this.zzilq, false);
        zzbgo.zza(parcel, 4, this.zzilr, false);
        zzbgo.zza(parcel, 5, this.zzils, false);
        zzbgo.zza(parcel, 6, this.zzilt, false);
        zzbgo.zza(parcel, 7, this.zzilu, false);
        zzbgo.zza(parcel, 8, this.zzilv, false);
        zzbgo.zza(parcel, 9, this.zzilw, false);
        zzbgo.zza(parcel, 10, this.zzcyf, false);
        zzbgo.zza(parcel, 11, this.zzilx, false);
        zzbgo.zza(parcel, 12, this.zzily, false);
        zzbgo.zza(parcel, 13, this.phoneNumber, false);
        zzbgo.zza(parcel, 14, this.zzilz);
        zzbgo.zza(parcel, 15, this.zzima, false);
        zzbgo.zza(parcel, 16, this.zzimb, false);
        zzbgo.zzai(parcel, zze);
    }
}
