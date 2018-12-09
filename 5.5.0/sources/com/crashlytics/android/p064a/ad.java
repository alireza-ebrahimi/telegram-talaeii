package com.crashlytics.android.p064a;

import android.app.Activity;
import java.util.Collections;
import java.util.Map;

/* renamed from: com.crashlytics.android.a.ad */
final class ad {
    /* renamed from: a */
    public final ae f4030a;
    /* renamed from: b */
    public final long f4031b;
    /* renamed from: c */
    public final C1332b f4032c;
    /* renamed from: d */
    public final Map<String, String> f4033d;
    /* renamed from: e */
    public final String f4034e;
    /* renamed from: f */
    public final Map<String, Object> f4035f;
    /* renamed from: g */
    public final String f4036g;
    /* renamed from: h */
    public final Map<String, Object> f4037h;
    /* renamed from: i */
    private String f4038i;

    /* renamed from: com.crashlytics.android.a.ad$a */
    static class C1331a {
        /* renamed from: a */
        final C1332b f4014a;
        /* renamed from: b */
        final long f4015b = System.currentTimeMillis();
        /* renamed from: c */
        Map<String, String> f4016c = null;
        /* renamed from: d */
        String f4017d = null;
        /* renamed from: e */
        Map<String, Object> f4018e = null;
        /* renamed from: f */
        String f4019f = null;
        /* renamed from: g */
        Map<String, Object> f4020g = null;

        public C1331a(C1332b c1332b) {
            this.f4014a = c1332b;
        }

        /* renamed from: a */
        public C1331a m6804a(String str) {
            this.f4017d = str;
            return this;
        }

        /* renamed from: a */
        public C1331a m6805a(Map<String, String> map) {
            this.f4016c = map;
            return this;
        }

        /* renamed from: a */
        public ad m6806a(ae aeVar) {
            return new ad(aeVar, this.f4015b, this.f4014a, this.f4016c, this.f4017d, this.f4018e, this.f4019f, this.f4020g);
        }

        /* renamed from: b */
        public C1331a m6807b(Map<String, Object> map) {
            this.f4018e = map;
            return this;
        }
    }

    /* renamed from: com.crashlytics.android.a.ad$b */
    enum C1332b {
        START,
        RESUME,
        PAUSE,
        STOP,
        CRASH,
        INSTALL,
        CUSTOM,
        PREDEFINED
    }

    private ad(ae aeVar, long j, C1332b c1332b, Map<String, String> map, String str, Map<String, Object> map2, String str2, Map<String, Object> map3) {
        this.f4030a = aeVar;
        this.f4031b = j;
        this.f4032c = c1332b;
        this.f4033d = map;
        this.f4034e = str;
        this.f4035f = map2;
        this.f4036g = str2;
        this.f4037h = map3;
    }

    /* renamed from: a */
    public static C1331a m6808a(long j) {
        return new C1331a(C1332b.INSTALL).m6805a(Collections.singletonMap("installedAt", String.valueOf(j)));
    }

    /* renamed from: a */
    public static C1331a m6809a(C1332b c1332b, Activity activity) {
        return new C1331a(c1332b).m6805a(Collections.singletonMap("activity", activity.getClass().getName()));
    }

    /* renamed from: a */
    public static C1331a m6810a(C1351m c1351m) {
        return new C1331a(C1332b.CUSTOM).m6804a(c1351m.m6877a()).m6807b(c1351m.m6785b());
    }

    /* renamed from: a */
    public static C1331a m6811a(String str) {
        return new C1331a(C1332b.CRASH).m6805a(Collections.singletonMap("sessionId", str));
    }

    /* renamed from: a */
    public static C1331a m6812a(String str, String str2) {
        return ad.m6811a(str).m6807b(Collections.singletonMap("exceptionName", str2));
    }

    public String toString() {
        if (this.f4038i == null) {
            this.f4038i = "[" + getClass().getSimpleName() + ": " + "timestamp=" + this.f4031b + ", type=" + this.f4032c + ", details=" + this.f4033d + ", customType=" + this.f4034e + ", customAttributes=" + this.f4035f + ", predefinedType=" + this.f4036g + ", predefinedAttributes=" + this.f4037h + ", metadata=[" + this.f4030a + "]]";
        }
        return this.f4038i;
    }
}
