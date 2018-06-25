package p033b.p034a.p035a.p036a.p037a.p045g;

import java.io.Closeable;
import java.io.InputStream;
import java.util.Locale;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.C1239k;
import p033b.p034a.p035a.p036a.p037a.p039b.C1096a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1130s;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1179c;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d;

/* renamed from: b.a.a.a.a.g.a */
abstract class C1196a extends C1096a {
    public C1196a(C1237i c1237i, String str, String str2, C1177e c1177e, C1179c c1179c) {
        super(c1237i, str, str2, c1177e, c1179c);
    }

    /* renamed from: a */
    private C1187d m6321a(C1187d c1187d, C1199d c1199d) {
        return c1187d.m6263a("X-CRASHLYTICS-API-KEY", c1199d.f3461a).m6263a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m6263a("X-CRASHLYTICS-API-CLIENT-VERSION", this.a.mo1080a());
    }

    /* renamed from: b */
    private C1187d m6322b(C1187d c1187d, C1199d c1199d) {
        C1187d e = c1187d.m6283e("app[identifier]", c1199d.f3462b).m6283e("app[name]", c1199d.f3466f).m6283e("app[display_version]", c1199d.f3463c).m6283e("app[build_version]", c1199d.f3464d).m6262a("app[source]", Integer.valueOf(c1199d.f3467g)).m6283e("app[minimum_sdk_version]", c1199d.f3468h).m6283e("app[built_sdk_version]", c1199d.f3469i);
        if (!C1110i.m6025d(c1199d.f3465e)) {
            e.m6283e("app[instance_identifier]", c1199d.f3465e);
        }
        if (c1199d.f3470j != null) {
            Closeable closeable = null;
            try {
                closeable = this.a.m6452q().getResources().openRawResource(c1199d.f3470j.f3494b);
                e.m6283e("app[icon][hash]", c1199d.f3470j.f3493a).m6267a("app[icon][data]", "icon.png", "application/octet-stream", (InputStream) closeable).m6262a("app[icon][width]", Integer.valueOf(c1199d.f3470j.f3495c)).m6262a("app[icon][height]", Integer.valueOf(c1199d.f3470j.f3496d));
            } catch (Throwable e2) {
                C1230c.m6414h().mo1070e("Fabric", "Failed to find app icon with resource ID: " + c1199d.f3470j.f3494b, e2);
            } finally {
                String str = "Failed to close app icon InputStream.";
                C1110i.m6011a(closeable, str);
            }
        }
        if (c1199d.f3471k != null) {
            for (C1239k c1239k : c1199d.f3471k) {
                e.m6283e(m6323a(c1239k), c1239k.m6459b());
                e.m6283e(m6325b(c1239k), c1239k.m6460c());
            }
        }
        return e;
    }

    /* renamed from: a */
    String m6323a(C1239k c1239k) {
        return String.format(Locale.US, "app[build][libraries][%s][version]", new Object[]{c1239k.m6458a()});
    }

    /* renamed from: a */
    public boolean mo1054a(C1199d c1199d) {
        C1187d b = m6322b(m6321a(m5962b(), c1199d), c1199d);
        C1230c.m6414h().mo1062a("Fabric", "Sending app info to " + m5961a());
        if (c1199d.f3470j != null) {
            C1230c.m6414h().mo1062a("Fabric", "App icon hash is " + c1199d.f3470j.f3493a);
            C1230c.m6414h().mo1062a("Fabric", "App icon size is " + c1199d.f3470j.f3495c + "x" + c1199d.f3470j.f3496d);
        }
        int b2 = b.m6273b();
        C1230c.m6414h().mo1062a("Fabric", ("POST".equals(b.m6297p()) ? "Create" : "Update") + " app request ID: " + b.m6275b("X-REQUEST-ID"));
        C1230c.m6414h().mo1062a("Fabric", "Result was " + b2);
        return C1130s.m6103a(b2) == 0;
    }

    /* renamed from: b */
    String m6325b(C1239k c1239k) {
        return String.format(Locale.US, "app[build][libraries][%s][type]", new Object[]{c1239k.m6458a()});
    }
}
