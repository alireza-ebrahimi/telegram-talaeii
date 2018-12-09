package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "VerifyAssertionRequestCreator")
@Reserved({1})
public final class zzbf extends AbstractSafeParcelable {
    public static final Creator<zzbf> CREATOR = new zzbg();
    @Field(getter = "getIdToken", id = 4)
    private String zzaf;
    @Field(getter = "getEmail", id = 7)
    private String zzah;
    @Field(getter = "getReturnSecureToken", id = 10)
    private boolean zzbt;
    @Field(getter = "getRequestUri", id = 2)
    private String zzca;
    @Field(getter = "getPostBody", id = 8)
    private String zzcb;
    @Field(getter = "getAutoCreate", id = 11)
    private boolean zzcf;
    @Field(getter = "getOauthTokenSecret", id = 9)
    private String zzdc;
    @Field(getter = "getAccessToken", id = 5)
    private String zzdv;
    @Field(getter = "getProviderId", id = 6)
    private String zzj;
    @Field(getter = "getCurrentIdToken", id = 3)
    private String zzki;
    @Field(getter = "getAuthCode", id = 12)
    private String zzkj;
    @Field(getter = "getIdpResponseUrl", id = 14)
    private String zzkk;
    @Field(getter = "getSessionId", id = 13)
    private String zzr;

    public zzbf() {
        this.zzbt = true;
        this.zzcf = true;
    }

    public zzbf(String str, String str2, String str3, String str4, String str5) {
        this(str, str2, str3, null, str5, null);
    }

    public zzbf(String str, String str2, String str3, String str4, String str5, String str6) {
        this.zzca = "http://localhost";
        this.zzaf = str;
        this.zzdv = str2;
        this.zzdc = str5;
        this.zzkj = str6;
        this.zzbt = true;
        if (TextUtils.isEmpty(this.zzaf) && TextUtils.isEmpty(this.zzdv) && TextUtils.isEmpty(this.zzkj)) {
            throw new IllegalArgumentException("idToken, accessToken and authCode cannot all be null");
        }
        this.zzj = Preconditions.checkNotEmpty(str3);
        this.zzah = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(this.zzaf)) {
            stringBuilder.append("id_token=").append(this.zzaf).append("&");
        }
        if (!TextUtils.isEmpty(this.zzdv)) {
            stringBuilder.append("access_token=").append(this.zzdv).append("&");
        }
        if (!TextUtils.isEmpty(this.zzah)) {
            stringBuilder.append("identifier=").append(this.zzah).append("&");
        }
        if (!TextUtils.isEmpty(this.zzdc)) {
            stringBuilder.append("oauth_token_secret=").append(this.zzdc).append("&");
        }
        if (!TextUtils.isEmpty(this.zzkj)) {
            stringBuilder.append("code=").append(this.zzkj).append("&");
        }
        stringBuilder.append("providerId=").append(this.zzj);
        this.zzcb = stringBuilder.toString();
        this.zzcf = true;
    }

    @Constructor
    zzbf(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7, @Param(id = 9) String str8, @Param(id = 10) boolean z, @Param(id = 11) boolean z2, @Param(id = 12) String str9, @Param(id = 13) String str10, @Param(id = 14) String str11) {
        this.zzca = str;
        this.zzki = str2;
        this.zzaf = str3;
        this.zzdv = str4;
        this.zzj = str5;
        this.zzah = str6;
        this.zzcb = str7;
        this.zzdc = str8;
        this.zzbt = z;
        this.zzcf = z2;
        this.zzkj = str9;
        this.zzr = str10;
        this.zzkk = str11;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzca, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzki, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzaf, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzdv, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzj, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzah, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzcb, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzdc, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzbt);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzcf);
        SafeParcelWriter.writeString(parcel, 12, this.zzkj, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzr, false);
        SafeParcelWriter.writeString(parcel, 14, this.zzkk, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzbf zzac(String str) {
        this.zzki = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzq = new zzq();
        zzq.zzaf = this.zzki;
        zzq.zzca = this.zzca;
        zzq.zzcb = this.zzcb;
        zzq.zzbt = this.zzbt;
        zzq.zzcf = this.zzcf;
        if (!TextUtils.isEmpty(this.zzr)) {
            zzq.zzr = this.zzr;
        }
        if (!TextUtils.isEmpty(this.zzkk)) {
            zzq.zzca = this.zzkk;
        }
        return zzq;
    }

    public final zzbf zzd(boolean z) {
        this.zzcf = false;
        return this;
    }
}
