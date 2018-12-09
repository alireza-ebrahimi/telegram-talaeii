package com.crashlytics.android.p066c;

import java.io.File;
import java.util.Map.Entry;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1096a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1130s;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1179c;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d;

/* renamed from: com.crashlytics.android.c.p */
class C1452p extends C1096a implements C1451o {
    public C1452p(C1237i c1237i, String str, String str2, C1177e c1177e) {
        super(c1237i, str, str2, c1177e, C1179c.POST);
    }

    /* renamed from: a */
    private C1187d m7243a(C1187d c1187d, ae aeVar) {
        int i = 0;
        c1187d.m6283e("report[identifier]", aeVar.mo1155b());
        if (aeVar.mo1157d().length == 1) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Adding single file " + aeVar.mo1154a() + " to report " + aeVar.mo1155b());
            return c1187d.m6266a("report[file]", aeVar.mo1154a(), "application/octet-stream", aeVar.mo1156c());
        }
        File[] d = aeVar.mo1157d();
        int length = d.length;
        int i2 = 0;
        while (i < length) {
            File file = d[i];
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Adding file " + file.getName() + " to report " + aeVar.mo1155b());
            c1187d.m6266a("report[file" + i2 + "]", file.getName(), "application/octet-stream", file);
            i2++;
            i++;
        }
        return c1187d;
    }

    /* renamed from: a */
    private C1187d m7244a(C1187d c1187d, C1450n c1450n) {
        C1187d a = c1187d.m6263a("X-CRASHLYTICS-API-KEY", c1450n.f4386a).m6263a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m6263a("X-CRASHLYTICS-API-CLIENT-VERSION", this.a.mo1080a());
        C1187d c1187d2 = a;
        for (Entry a2 : c1450n.f4387b.mo1158e().entrySet()) {
            c1187d2 = c1187d2.m6269a(a2);
        }
        return c1187d2;
    }

    /* renamed from: a */
    public boolean mo1174a(C1450n c1450n) {
        C1187d a = m7243a(m7244a(m5962b(), c1450n), c1450n.f4387b);
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Sending report to: " + m5961a());
        int b = a.m6273b();
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Create report request ID: " + a.m6275b("X-REQUEST-ID"));
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Result was: " + b);
        return C1130s.m6103a(b) == 0;
    }
}
