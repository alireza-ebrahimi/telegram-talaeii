package com.google.firebase;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.util.Strings;

/* renamed from: com.google.firebase.f */
public final class C1924f {
    /* renamed from: a */
    private final String f5640a;
    /* renamed from: b */
    private final String f5641b;
    /* renamed from: c */
    private final String f5642c;
    /* renamed from: d */
    private final String f5643d;
    /* renamed from: e */
    private final String f5644e;
    /* renamed from: f */
    private final String f5645f;
    /* renamed from: g */
    private final String f5646g;

    private C1924f(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        Preconditions.checkState(!Strings.isEmptyOrWhitespace(str), "ApplicationId must be set.");
        this.f5641b = str;
        this.f5640a = str2;
        this.f5642c = str3;
        this.f5643d = str4;
        this.f5644e = str5;
        this.f5645f = str6;
        this.f5646g = str7;
    }

    /* renamed from: a */
    public static C1924f m8751a(Context context) {
        StringResourceValueReader stringResourceValueReader = new StringResourceValueReader(context);
        Object string = stringResourceValueReader.getString("google_app_id");
        return TextUtils.isEmpty(string) ? null : new C1924f(string, stringResourceValueReader.getString("google_api_key"), stringResourceValueReader.getString("firebase_database_url"), stringResourceValueReader.getString("ga_trackingId"), stringResourceValueReader.getString("gcm_defaultSenderId"), stringResourceValueReader.getString("google_storage_bucket"), stringResourceValueReader.getString("project_id"));
    }

    /* renamed from: a */
    public final String m8752a() {
        return this.f5640a;
    }

    /* renamed from: b */
    public final String m8753b() {
        return this.f5641b;
    }

    /* renamed from: c */
    public final String m8754c() {
        return this.f5644e;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C1924f)) {
            return false;
        }
        C1924f c1924f = (C1924f) obj;
        return Objects.equal(this.f5641b, c1924f.f5641b) && Objects.equal(this.f5640a, c1924f.f5640a) && Objects.equal(this.f5642c, c1924f.f5642c) && Objects.equal(this.f5643d, c1924f.f5643d) && Objects.equal(this.f5644e, c1924f.f5644e) && Objects.equal(this.f5645f, c1924f.f5645f) && Objects.equal(this.f5646g, c1924f.f5646g);
    }

    public final int hashCode() {
        return Objects.hashCode(this.f5641b, this.f5640a, this.f5642c, this.f5643d, this.f5644e, this.f5645f, this.f5646g);
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("applicationId", this.f5641b).add("apiKey", this.f5640a).add("databaseUrl", this.f5642c).add("gcmSenderId", this.f5644e).add("storageBucket", this.f5645f).add("projectId", this.f5646g).toString();
    }
}
