package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.internal.firebase_auth.zzbf;

@Class(creator = "DefaultOAuthCredentialCreator")
public class zzd extends zzu {
    public static final Creator<zzd> CREATOR = new C1884n();
    @Field(getter = "getProvider", id = 1)
    /* renamed from: a */
    private final String f5571a;
    @Field(getter = "getIdToken", id = 2)
    /* renamed from: b */
    private final String f5572b;
    @Field(getter = "getAccessToken", id = 3)
    /* renamed from: c */
    private final String f5573c;
    @Field(getter = "getWebSignInCredential", id = 4)
    /* renamed from: d */
    private final zzbf f5574d;

    @Constructor
    zzd(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3, @Param(id = 4) zzbf zzbf) {
        this.f5571a = str;
        this.f5572b = str2;
        this.f5573c = str3;
        this.f5574d = zzbf;
    }

    /* renamed from: a */
    public static zzd m8670a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            return new zzd(Preconditions.checkNotEmpty(str, "Must specify a non-empty providerId"), str2, str3, null);
        }
        throw new IllegalArgumentException("Must specify an idToken or an accessToken.");
    }

    /* renamed from: a */
    public String mo2997a() {
        return this.f5571a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, mo2997a(), false);
        SafeParcelWriter.writeString(parcel, 2, this.f5572b, false);
        SafeParcelWriter.writeString(parcel, 3, this.f5573c, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.f5574d, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
