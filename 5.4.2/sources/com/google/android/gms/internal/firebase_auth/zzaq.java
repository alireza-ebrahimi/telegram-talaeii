package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "ProviderUserInfoCreator")
@Reserved({1})
public final class zzaq extends AbstractSafeParcelable {
    public static final Creator<zzaq> CREATOR = new zzar();
    @Field(getter = "getEmail", id = 8)
    private String zzah;
    @Field(getter = "getPhoneNumber", id = 7)
    private String zzbd;
    @Field(getter = "getDisplayName", id = 3)
    private String zzbh;
    @Field(getter = "getPhotoUrl", id = 4)
    private String zzbr;
    @Field(getter = "getFederatedId", id = 2)
    private String zzcg;
    @Field(getter = "getRawUserInfo", id = 6)
    private String zzdf;
    @Field(getter = "getProviderId", id = 5)
    private String zzj;

    @Constructor
    zzaq(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7) {
        this.zzcg = str;
        this.zzbh = str2;
        this.zzbr = str3;
        this.zzj = str4;
        this.zzdf = str5;
        this.zzbd = str6;
        this.zzah = str7;
    }

    public final String getDisplayName() {
        return this.zzbh;
    }

    public final String getEmail() {
        return this.zzah;
    }

    public final String getPhoneNumber() {
        return this.zzbd;
    }

    public final Uri getPhotoUri() {
        return !TextUtils.isEmpty(this.zzbr) ? Uri.parse(this.zzbr) : null;
    }

    public final String getProviderId() {
        return this.zzj;
    }

    public final String getRawUserInfo() {
        return this.zzdf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzcg, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzbh, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzbr, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzj, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzdf, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzbd, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzah, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String zzaz() {
        return this.zzcg;
    }

    public final void zzt(String str) {
        this.zzdf = str;
    }
}
