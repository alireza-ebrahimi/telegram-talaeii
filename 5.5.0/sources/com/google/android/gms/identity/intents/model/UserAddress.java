package com.google.android.gms.identity.intents.model;

import android.content.Intent;
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
import com.google.android.gms.identity.intents.AddressConstants.Extras;

@Class(creator = "UserAddressCreator")
@Reserved({1})
public final class UserAddress extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<UserAddress> CREATOR = new zzb();
    @Field(id = 2)
    private String name;
    @Field(id = 10)
    private String zzk;
    @Field(id = 3)
    private String zzl;
    @Field(id = 4)
    private String zzm;
    @Field(id = 5)
    private String zzn;
    @Field(id = 6)
    private String zzo;
    @Field(id = 7)
    private String zzp;
    @Field(id = 8)
    private String zzq;
    @Field(id = 9)
    private String zzr;
    @Field(id = 11)
    private String zzs;
    @Field(id = 12)
    private String zzt;
    @Field(id = 13)
    private String zzu;
    @Field(id = 14)
    private boolean zzv;
    @Field(id = 15)
    private String zzw;
    @Field(id = 16)
    private String zzx;

    UserAddress() {
    }

    @Constructor
    UserAddress(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7, @Param(id = 9) String str8, @Param(id = 10) String str9, @Param(id = 11) String str10, @Param(id = 12) String str11, @Param(id = 13) String str12, @Param(id = 14) boolean z, @Param(id = 15) String str13, @Param(id = 16) String str14) {
        this.name = str;
        this.zzl = str2;
        this.zzm = str3;
        this.zzn = str4;
        this.zzo = str5;
        this.zzp = str6;
        this.zzq = str7;
        this.zzr = str8;
        this.zzk = str9;
        this.zzs = str10;
        this.zzt = str11;
        this.zzu = str12;
        this.zzv = z;
        this.zzw = str13;
        this.zzx = str14;
    }

    public static UserAddress fromIntent(Intent intent) {
        return (intent == null || !intent.hasExtra(Extras.EXTRA_ADDRESS)) ? null : (UserAddress) intent.getParcelableExtra(Extras.EXTRA_ADDRESS);
    }

    public final String getAddress1() {
        return this.zzl;
    }

    public final String getAddress2() {
        return this.zzm;
    }

    public final String getAddress3() {
        return this.zzn;
    }

    public final String getAddress4() {
        return this.zzo;
    }

    public final String getAddress5() {
        return this.zzp;
    }

    public final String getAdministrativeArea() {
        return this.zzq;
    }

    public final String getCompanyName() {
        return this.zzw;
    }

    public final String getCountryCode() {
        return this.zzk;
    }

    public final String getEmailAddress() {
        return this.zzx;
    }

    public final String getLocality() {
        return this.zzr;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPhoneNumber() {
        return this.zzu;
    }

    public final String getPostalCode() {
        return this.zzs;
    }

    public final String getSortingCode() {
        return this.zzt;
    }

    public final boolean isPostBox() {
        return this.zzv;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzl, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzm, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzn, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzo, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzp, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzq, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzr, false);
        SafeParcelWriter.writeString(parcel, 10, this.zzk, false);
        SafeParcelWriter.writeString(parcel, 11, this.zzs, false);
        SafeParcelWriter.writeString(parcel, 12, this.zzt, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzu, false);
        SafeParcelWriter.writeBoolean(parcel, 14, this.zzv);
        SafeParcelWriter.writeString(parcel, 15, this.zzw, false);
        SafeParcelWriter.writeString(parcel, 16, this.zzx, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
