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

@Class(creator = "PhoneAuthCredentialCreator")
public class PhoneAuthCredential extends AuthCredential implements Cloneable {
    public static final Creator<PhoneAuthCredential> CREATOR = new C1890t();
    @Field(getter = "getSessionInfo", id = 1)
    /* renamed from: a */
    private String f5449a;
    @Field(getter = "getSmsCode", id = 2)
    /* renamed from: b */
    private String f5450b;
    @Field(getter = "getHasVerificationProof", id = 3)
    /* renamed from: c */
    private boolean f5451c;
    @Field(getter = "getPhoneNumber", id = 4)
    /* renamed from: d */
    private String f5452d;
    @Field(getter = "getAutoCreate", id = 5)
    /* renamed from: e */
    private boolean f5453e;
    @Field(getter = "getTemporaryProof", id = 6)
    /* renamed from: f */
    private String f5454f;

    @Constructor
    PhoneAuthCredential(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) boolean z, @Param(id = 4) String str3, @Param(id = 5) boolean z2, @Param(id = 6) String str4) {
        boolean z3 = (z && !TextUtils.isEmpty(str3)) || !((TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) && (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)));
        Preconditions.checkArgument(z3, "Cannot create PhoneAuthCredential without either verificationProof, sessionInfo, ortemprary proof.");
        this.f5449a = str;
        this.f5450b = str2;
        this.f5451c = z;
        this.f5452d = str3;
        this.f5453e = z2;
        this.f5454f = str4;
    }

    /* renamed from: a */
    public String mo2997a() {
        return "phone";
    }

    /* renamed from: b */
    public String m8521b() {
        return this.f5450b;
    }

    public /* synthetic */ Object clone() {
        return new PhoneAuthCredential(this.f5449a, m8521b(), this.f5451c, this.f5452d, this.f5453e, this.f5454f);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.f5449a, false);
        SafeParcelWriter.writeString(parcel, 2, m8521b(), false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.f5451c);
        SafeParcelWriter.writeString(parcel, 4, this.f5452d, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.f5453e);
        SafeParcelWriter.writeString(parcel, 6, this.f5454f, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
