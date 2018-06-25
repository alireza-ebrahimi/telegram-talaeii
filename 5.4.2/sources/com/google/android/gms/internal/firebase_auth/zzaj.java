package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
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
import com.google.firebase.auth.zzd;
import java.util.List;

@Class(creator = "GetAccountInfoUserCreator")
@Reserved({1})
public final class zzaj extends AbstractSafeParcelable {
    public static final Creator<zzaj> CREATOR = new zzak();
    @Field(getter = "getLocalId", id = 2)
    private String zzad;
    @Field(getter = "getEmail", id = 3)
    private String zzah;
    @Field(getter = "isNewUser", id = 12)
    private boolean zzak;
    @Field(getter = "getPhoneNumber", id = 9)
    private String zzbd;
    @Field(getter = "getDisplayName", id = 5)
    private String zzbh;
    @Field(getter = "getPassword", id = 8)
    private String zzbi;
    @Field(getter = "getPhotoUrl", id = 6)
    private String zzbr;
    @Field(getter = "isEmailVerified", id = 4)
    private boolean zzjq;
    @Field(getter = "getProviderInfoList", id = 7)
    private zzas zzjr;
    @Field(getter = "getCreationTimestamp", id = 10)
    private long zzjs;
    @Field(getter = "getLastSignInTimestamp", id = 11)
    private long zzjt;
    @Field(getter = "getDefaultOAuthCredential", id = 13)
    private zzd zzju;

    public zzaj() {
        this.zzjr = new zzas();
    }

    @Constructor
    public zzaj(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) boolean z, @Param(id = 5) String str3, @Param(id = 6) String str4, @Param(id = 7) zzas zzas, @Param(id = 8) String str5, @Param(id = 9) String str6, @Param(id = 10) long j, @Param(id = 11) long j2, @Param(id = 12) boolean z2, @Param(id = 13) zzd zzd) {
        this.zzad = str;
        this.zzah = str2;
        this.zzjq = z;
        this.zzbh = str3;
        this.zzbr = str4;
        this.zzjr = zzas == null ? new zzas() : zzas.zza(zzas);
        this.zzbi = str5;
        this.zzbd = str6;
        this.zzjs = j;
        this.zzjt = j2;
        this.zzak = z2;
        this.zzju = zzd;
    }

    public final long getCreationTimestamp() {
        return this.zzjs;
    }

    public final String getDisplayName() {
        return this.zzbh;
    }

    public final String getEmail() {
        return this.zzah;
    }

    public final long getLastSignInTimestamp() {
        return this.zzjt;
    }

    public final String getLocalId() {
        return this.zzad;
    }

    public final String getPhoneNumber() {
        return this.zzbd;
    }

    public final Uri getPhotoUri() {
        return !TextUtils.isEmpty(this.zzbr) ? Uri.parse(this.zzbr) : null;
    }

    public final boolean isEmailVerified() {
        return this.zzjq;
    }

    public final boolean isNewUser() {
        return this.zzak;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzad, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzah, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzjq);
        SafeParcelWriter.writeString(parcel, 5, this.zzbh, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzbr, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzjr, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzbi, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzbd, false);
        SafeParcelWriter.writeLong(parcel, 10, this.zzjs);
        SafeParcelWriter.writeLong(parcel, 11, this.zzjt);
        SafeParcelWriter.writeBoolean(parcel, 12, this.zzak);
        SafeParcelWriter.writeParcelable(parcel, 13, this.zzju, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final List<zzaq> zzat() {
        return this.zzjr.zzat();
    }

    public final zzas zzau() {
        return this.zzjr;
    }

    public final zzd zzav() {
        return this.zzju;
    }

    public final zzaj zzb(zzd zzd) {
        this.zzju = zzd;
        return this;
    }

    public final zzaj zzb(List<zzaq> list) {
        Preconditions.checkNotNull(list);
        this.zzjr = new zzas();
        this.zzjr.zzat().addAll(list);
        return this;
    }

    public final zzaj zzc(boolean z) {
        this.zzak = z;
        return this;
    }

    public final zzaj zzl(String str) {
        this.zzah = str;
        return this;
    }

    public final zzaj zzm(String str) {
        this.zzbh = str;
        return this;
    }

    public final zzaj zzn(String str) {
        this.zzbr = str;
        return this;
    }

    public final zzaj zzo(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzbi = str;
        return this;
    }
}
