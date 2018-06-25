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
import com.google.android.gms.common.logging.Logger;

@Class(creator = "EmailAuthCredentialCreator")
public class EmailAuthCredential extends AuthCredential {
    public static final Creator<EmailAuthCredential> CREATOR = new C1885o();
    /* renamed from: a */
    private static final Logger f5432a = new Logger("EmailAuthCredential", new String[0]);
    @Field(getter = "getEmail", id = 1)
    /* renamed from: b */
    private String f5433b;
    @Field(getter = "getPassword", id = 2)
    /* renamed from: c */
    private String f5434c;
    @Field(getter = "getSignInLink", id = 3)
    /* renamed from: d */
    private final String f5435d;
    @Field(getter = "getCachedState", id = 4)
    /* renamed from: e */
    private String f5436e;
    @Field(getter = "isForLinking", id = 5)
    /* renamed from: f */
    private boolean f5437f;

    @Constructor
    EmailAuthCredential(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3, @Param(id = 4) String str4, @Param(id = 5) boolean z) {
        this.f5433b = Preconditions.checkNotEmpty(str);
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Cannot create an EmailAuthCredential without a password or emailLink.");
        }
        this.f5434c = str2;
        this.f5435d = str3;
        this.f5436e = str4;
        this.f5437f = z;
    }

    /* renamed from: a */
    public String mo2997a() {
        return "password";
    }

    /* renamed from: b */
    public final String m8489b() {
        return this.f5433b;
    }

    /* renamed from: c */
    public final String m8490c() {
        return this.f5435d;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.f5433b, false);
        SafeParcelWriter.writeString(parcel, 2, this.f5434c, false);
        SafeParcelWriter.writeString(parcel, 3, this.f5435d, false);
        SafeParcelWriter.writeString(parcel, 4, this.f5436e, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.f5437f);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
