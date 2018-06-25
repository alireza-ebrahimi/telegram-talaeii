package com.crashlytics.android.p064a;

/* renamed from: com.crashlytics.android.a.ae */
final class ae {
    /* renamed from: a */
    public final String f4039a;
    /* renamed from: b */
    public final String f4040b;
    /* renamed from: c */
    public final String f4041c;
    /* renamed from: d */
    public final String f4042d;
    /* renamed from: e */
    public final String f4043e;
    /* renamed from: f */
    public final Boolean f4044f;
    /* renamed from: g */
    public final String f4045g;
    /* renamed from: h */
    public final String f4046h;
    /* renamed from: i */
    public final String f4047i;
    /* renamed from: j */
    public final String f4048j;
    /* renamed from: k */
    public final String f4049k;
    /* renamed from: l */
    public final String f4050l;
    /* renamed from: m */
    private String f4051m;

    public ae(String str, String str2, String str3, String str4, String str5, Boolean bool, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.f4039a = str;
        this.f4040b = str2;
        this.f4041c = str3;
        this.f4042d = str4;
        this.f4043e = str5;
        this.f4044f = bool;
        this.f4045g = str6;
        this.f4046h = str7;
        this.f4047i = str8;
        this.f4048j = str9;
        this.f4049k = str10;
        this.f4050l = str11;
    }

    public String toString() {
        if (this.f4051m == null) {
            this.f4051m = "appBundleId=" + this.f4039a + ", executionId=" + this.f4040b + ", installationId=" + this.f4041c + ", androidId=" + this.f4042d + ", advertisingId=" + this.f4043e + ", limitAdTrackingEnabled=" + this.f4044f + ", betaDeviceToken=" + this.f4045g + ", buildId=" + this.f4046h + ", osVersion=" + this.f4047i + ", deviceModel=" + this.f4048j + ", appVersionCode=" + this.f4049k + ", appVersionName=" + this.f4050l;
        }
        return this.f4051m;
    }
}
