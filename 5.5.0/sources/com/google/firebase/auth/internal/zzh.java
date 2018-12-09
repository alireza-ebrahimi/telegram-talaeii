package com.google.firebase.auth.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzv;
import com.google.firebase.auth.C1824k;
import org.json.JSONObject;

@Class(creator = "DefaultAuthUserInfoCreator")
public final class zzh extends AbstractSafeParcelable implements C1824k {
    public static final Creator<zzh> CREATOR = new C1870j();
    @Field(getter = "getUid", id = 1)
    /* renamed from: a */
    private String f5542a;
    @Field(getter = "getProviderId", id = 2)
    /* renamed from: b */
    private String f5543b;
    @Field(getter = "getDisplayName", id = 3)
    /* renamed from: c */
    private String f5544c;
    @Field(getter = "getPhotoUrlString", id = 4)
    /* renamed from: d */
    private String f5545d;
    /* renamed from: e */
    private Uri f5546e;
    @Field(getter = "getEmail", id = 5)
    /* renamed from: f */
    private String f5547f;
    @Field(getter = "getPhoneNumber", id = 6)
    /* renamed from: g */
    private String f5548g;
    @Field(getter = "isEmailVerified", id = 7)
    /* renamed from: h */
    private boolean f5549h;
    @Field(getter = "getRawUserInfo", id = 8)
    /* renamed from: i */
    private String f5550i;

    @Constructor
    @VisibleForTesting
    public zzh(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 5) String str3, @Param(id = 4) String str4, @Param(id = 3) String str5, @Param(id = 6) String str6, @Param(id = 7) boolean z, @Param(id = 8) String str7) {
        this.f5542a = str;
        this.f5543b = str2;
        this.f5547f = str3;
        this.f5548g = str4;
        this.f5544c = str5;
        this.f5545d = str6;
        if (!TextUtils.isEmpty(this.f5545d)) {
            this.f5546e = Uri.parse(this.f5545d);
        }
        this.f5549h = z;
        this.f5550i = str7;
    }

    /* renamed from: a */
    public static zzh m8638a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzh(jSONObject.optString("userId"), jSONObject.optString("providerId"), jSONObject.optString(Scopes.EMAIL), jSONObject.optString("phoneNumber"), jSONObject.optString("displayName"), jSONObject.optString("photoUrl"), jSONObject.optBoolean("isEmailVerified"), jSONObject.optString("rawUserInfo"));
        } catch (Throwable e) {
            Log.d("DefaultAuthUserInfo", "Failed to unpack UserInfo from JSON");
            throw new zzv(e);
        }
    }

    /* renamed from: a */
    public final String m8639a() {
        return this.f5542a;
    }

    /* renamed from: b */
    public final String m8640b() {
        return this.f5544c;
    }

    /* renamed from: c */
    public final String m8641c() {
        return this.f5547f;
    }

    /* renamed from: d */
    public final String m8642d() {
        return this.f5548g;
    }

    /* renamed from: e */
    public final boolean m8643e() {
        return this.f5549h;
    }

    /* renamed from: f */
    public final String m8644f() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("userId", this.f5542a);
            jSONObject.putOpt("providerId", this.f5543b);
            jSONObject.putOpt("displayName", this.f5544c);
            jSONObject.putOpt("photoUrl", this.f5545d);
            jSONObject.putOpt(Scopes.EMAIL, this.f5547f);
            jSONObject.putOpt("phoneNumber", this.f5548g);
            jSONObject.putOpt("isEmailVerified", Boolean.valueOf(this.f5549h));
            jSONObject.putOpt("rawUserInfo", this.f5550i);
            return jSONObject.toString();
        } catch (Throwable e) {
            Log.d("DefaultAuthUserInfo", "Failed to jsonify this object");
            throw new zzv(e);
        }
    }

    /* renamed from: k */
    public final String mo3026k() {
        return this.f5543b;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, m8639a(), false);
        SafeParcelWriter.writeString(parcel, 2, mo3026k(), false);
        SafeParcelWriter.writeString(parcel, 3, m8640b(), false);
        SafeParcelWriter.writeString(parcel, 4, this.f5545d, false);
        SafeParcelWriter.writeString(parcel, 5, m8641c(), false);
        SafeParcelWriter.writeString(parcel, 6, m8642d(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, m8643e());
        SafeParcelWriter.writeString(parcel, 8, this.f5550i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
