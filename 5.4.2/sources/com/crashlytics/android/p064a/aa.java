package com.crashlytics.android.p064a;

import java.io.File;
import java.util.List;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1096a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1130s;
import p033b.p034a.p035a.p036a.p037a.p042d.C1171f;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1179c;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d;

/* renamed from: com.crashlytics.android.a.aa */
class aa extends C1096a implements C1171f {
    /* renamed from: b */
    private final String f4008b;

    public aa(C1237i c1237i, String str, String str2, C1177e c1177e, String str3) {
        super(c1237i, str, str2, c1177e, C1179c.POST);
        this.f4008b = str3;
    }

    /* renamed from: a */
    public boolean mo1125a(List<File> list) {
        C1187d a = m5962b().m6263a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m6263a("X-CRASHLYTICS-API-CLIENT-VERSION", this.a.mo1080a()).m6263a("X-CRASHLYTICS-API-KEY", this.f4008b);
        int i = 0;
        for (File file : list) {
            a.m6266a("session_analytics_file_" + i, file.getName(), "application/vnd.crashlytics.android.events", file);
            i++;
        }
        C1230c.m6414h().mo1062a("Answers", "Sending " + list.size() + " analytics files to " + m5961a());
        int b = a.m6273b();
        C1230c.m6414h().mo1062a("Answers", "Response code for analytics file send is " + b);
        return C1130s.m6103a(b) == 0;
    }
}
