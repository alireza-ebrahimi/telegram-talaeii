package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ActionCodeSettingsCreator")
public class ActionCodeSettings extends AbstractSafeParcelable {
    public static final Creator<ActionCodeSettings> CREATOR = new C1882l();
    @Field(getter = "getUrl", id = 1)
    /* renamed from: a */
    private final String f5423a;
    @Field(getter = "getIOSBundle", id = 2)
    /* renamed from: b */
    private final String f5424b;
    @Field(getter = "getIOSAppStoreId", id = 3)
    /* renamed from: c */
    private final String f5425c;
    @Field(getter = "getAndroidPackageName", id = 4)
    /* renamed from: d */
    private final String f5426d;
    @Field(getter = "getAndroidInstallApp", id = 5)
    /* renamed from: e */
    private final boolean f5427e;
    @Field(getter = "getAndroidMinimumVersion", id = 6)
    /* renamed from: f */
    private final String f5428f;
    @Field(getter = "canHandleCodeInApp", id = 7)
    /* renamed from: g */
    private final boolean f5429g;
    @Field(getter = "getLocaleHeader", id = 8)
    /* renamed from: h */
    private String f5430h;
    @Field(getter = "getRequestType", id = 9)
    /* renamed from: i */
    private int f5431i;

    @Constructor
    ActionCodeSettings(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3, @Param(id = 4) String str4, @Param(id = 5) boolean z, @Param(id = 6) String str5, @Param(id = 7) boolean z2, @Param(id = 8) String str6, @Param(id = 9) int i) {
        this.f5423a = str;
        this.f5424b = str2;
        this.f5425c = str3;
        this.f5426d = str4;
        this.f5427e = z;
        this.f5428f = str5;
        this.f5429g = z2;
        this.f5430h = str6;
        this.f5431i = i;
    }

    /* renamed from: a */
    public String m8480a() {
        return this.f5423a;
    }

    /* renamed from: b */
    public String m8481b() {
        return this.f5424b;
    }

    /* renamed from: c */
    public final String m8482c() {
        return this.f5425c;
    }

    /* renamed from: d */
    public String m8483d() {
        return this.f5426d;
    }

    /* renamed from: e */
    public boolean m8484e() {
        return this.f5427e;
    }

    /* renamed from: f */
    public String m8485f() {
        return this.f5428f;
    }

    /* renamed from: g */
    public boolean m8486g() {
        return this.f5429g;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, m8480a(), false);
        SafeParcelWriter.writeString(parcel, 2, m8481b(), false);
        SafeParcelWriter.writeString(parcel, 3, this.f5425c, false);
        SafeParcelWriter.writeString(parcel, 4, m8483d(), false);
        SafeParcelWriter.writeBoolean(parcel, 5, m8484e());
        SafeParcelWriter.writeString(parcel, 6, m8485f(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, m8486g());
        SafeParcelWriter.writeString(parcel, 8, this.f5430h, false);
        SafeParcelWriter.writeInt(parcel, 9, this.f5431i);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
