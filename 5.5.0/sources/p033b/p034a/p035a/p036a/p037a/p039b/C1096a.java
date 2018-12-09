package p033b.p034a.p035a.p036a.p037a.p039b;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1179c;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d;

/* renamed from: b.a.a.a.a.b.a */
public abstract class C1096a {
    /* renamed from: b */
    private static final Pattern f3236b = Pattern.compile("http(s?)://[^\\/]+", 2);
    /* renamed from: a */
    protected final C1237i f3237a;
    /* renamed from: c */
    private final String f3238c;
    /* renamed from: d */
    private final C1177e f3239d;
    /* renamed from: e */
    private final C1179c f3240e;
    /* renamed from: f */
    private final String f3241f;

    public C1096a(C1237i c1237i, String str, String str2, C1177e c1177e, C1179c c1179c) {
        if (str2 == null) {
            throw new IllegalArgumentException("url must not be null.");
        } else if (c1177e == null) {
            throw new IllegalArgumentException("requestFactory must not be null.");
        } else {
            this.f3237a = c1237i;
            this.f3241f = str;
            this.f3238c = m5959a(str2);
            this.f3239d = c1177e;
            this.f3240e = c1179c;
        }
    }

    /* renamed from: a */
    private String m5959a(String str) {
        return !C1110i.m6025d(this.f3241f) ? f3236b.matcher(str).replaceFirst(this.f3241f) : str;
    }

    /* renamed from: a */
    protected C1187d m5960a(Map<String, String> map) {
        return this.f3239d.mo1044a(this.f3240e, m5961a(), map).m6270a(false).m6260a(10000).m6263a("User-Agent", "Crashlytics Android SDK/" + this.f3237a.mo1080a()).m6263a("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa");
    }

    /* renamed from: a */
    protected String m5961a() {
        return this.f3238c;
    }

    /* renamed from: b */
    protected C1187d m5962b() {
        return m5960a(Collections.emptyMap());
    }
}
