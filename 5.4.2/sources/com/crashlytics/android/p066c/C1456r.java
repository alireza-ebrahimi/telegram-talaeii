package com.crashlytics.android.p066c;

import android.content.Context;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p045g.C1213o;

/* renamed from: com.crashlytics.android.c.r */
class C1456r {
    /* renamed from: a */
    private final Context f4398a;
    /* renamed from: b */
    private final C1213o f4399b;

    public C1456r(Context context, C1213o c1213o) {
        this.f4398a = context;
        this.f4399b = c1213o;
    }

    /* renamed from: a */
    private String m7249a(String str, String str2) {
        return m7251b(C1110i.m6017b(this.f4398a, str), str2);
    }

    /* renamed from: a */
    private boolean m7250a(String str) {
        return str == null || str.length() == 0;
    }

    /* renamed from: b */
    private String m7251b(String str, String str2) {
        return m7250a(str) ? str2 : str;
    }

    /* renamed from: a */
    public String m7252a() {
        return m7249a("com.crashlytics.CrashSubmissionPromptTitle", this.f4399b.f3497a);
    }

    /* renamed from: b */
    public String m7253b() {
        return m7249a("com.crashlytics.CrashSubmissionPromptMessage", this.f4399b.f3498b);
    }

    /* renamed from: c */
    public String m7254c() {
        return m7249a("com.crashlytics.CrashSubmissionSendTitle", this.f4399b.f3499c);
    }

    /* renamed from: d */
    public String m7255d() {
        return m7249a("com.crashlytics.CrashSubmissionAlwaysSendTitle", this.f4399b.f3503g);
    }

    /* renamed from: e */
    public String m7256e() {
        return m7249a("com.crashlytics.CrashSubmissionCancelTitle", this.f4399b.f3501e);
    }
}
