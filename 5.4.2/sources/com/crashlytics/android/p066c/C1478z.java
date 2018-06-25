package com.crashlytics.android.p066c;

import com.crashlytics.android.p066c.p067a.p068a.C1378a;
import com.crashlytics.android.p066c.p067a.p068a.C1379b;
import com.crashlytics.android.p066c.p067a.p068a.C1380c;
import com.crashlytics.android.p066c.p067a.p068a.C1381d;
import com.crashlytics.android.p066c.p067a.p068a.C1382e;
import com.crashlytics.android.p066c.p067a.p068a.C1384f;
import com.crashlytics.android.p066c.p067a.p068a.C1384f.C1383a;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.c.z */
class C1478z {
    /* renamed from: a */
    private static final C1382e f4443a = new C1382e(TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, 0);
    /* renamed from: b */
    private static final C1465j[] f4444b = new C1465j[0];
    /* renamed from: c */
    private static final C1477m[] f4445c = new C1477m[0];
    /* renamed from: d */
    private static final C1472g[] f4446d = new C1472g[0];
    /* renamed from: e */
    private static final C1467b[] f4447e = new C1467b[0];
    /* renamed from: f */
    private static final C1468c[] f4448f = new C1468c[0];

    /* renamed from: com.crashlytics.android.c.z$j */
    private static abstract class C1465j {
        /* renamed from: a */
        private final int f4415a;
        /* renamed from: b */
        private final C1465j[] f4416b;

        public C1465j(int i, C1465j... c1465jArr) {
            this.f4415a = i;
            if (c1465jArr == null) {
                c1465jArr = C1478z.f4444b;
            }
            this.f4416b = c1465jArr;
        }

        /* renamed from: a */
        public int mo1176a() {
            return 0;
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
        }

        /* renamed from: b */
        public int mo1178b() {
            int c = m7287c();
            return (c + C1400e.m7070l(c)) + C1400e.m7069j(this.f4415a);
        }

        /* renamed from: b */
        public void mo1179b(C1400e c1400e) {
            c1400e.m7093g(this.f4415a, 2);
            c1400e.m7095k(m7287c());
            mo1177a(c1400e);
            for (C1465j b : this.f4416b) {
                b.mo1179b(c1400e);
            }
        }

        /* renamed from: c */
        public int m7287c() {
            int a = mo1176a();
            for (C1465j b : this.f4416b) {
                a += b.mo1178b();
            }
            return a;
        }
    }

    /* renamed from: com.crashlytics.android.c.z$a */
    private static final class C1466a extends C1465j {
        public C1466a(C1471f c1471f, C1475k c1475k) {
            super(3, c1471f, c1475k);
        }
    }

    /* renamed from: com.crashlytics.android.c.z$b */
    private static final class C1467b extends C1465j {
        /* renamed from: a */
        private final long f4417a;
        /* renamed from: b */
        private final long f4418b;
        /* renamed from: c */
        private final String f4419c;
        /* renamed from: d */
        private final String f4420d;

        public C1467b(C1378a c1378a) {
            super(4, new C1465j[0]);
            this.f4417a = c1378a.f4160a;
            this.f4418b = c1378a.f4161b;
            this.f4419c = c1378a.f4162c;
            this.f4420d = c1378a.f4163d;
        }

        /* renamed from: a */
        public int mo1176a() {
            int b = C1400e.m7055b(1, this.f4417a);
            return ((b + C1400e.m7056b(3, C1395b.m7039a(this.f4419c))) + C1400e.m7055b(2, this.f4418b)) + C1400e.m7056b(4, C1395b.m7039a(this.f4420d));
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7077a(1, this.f4417a);
            c1400e.m7077a(2, this.f4418b);
            c1400e.m7078a(3, C1395b.m7039a(this.f4419c));
            c1400e.m7078a(4, C1395b.m7039a(this.f4420d));
        }
    }

    /* renamed from: com.crashlytics.android.c.z$c */
    private static final class C1468c extends C1465j {
        /* renamed from: a */
        private final String f4421a;
        /* renamed from: b */
        private final String f4422b;

        public C1468c(C1379b c1379b) {
            super(2, new C1465j[0]);
            this.f4421a = c1379b.f4164a;
            this.f4422b = c1379b.f4165b;
        }

        /* renamed from: a */
        public int mo1176a() {
            return C1400e.m7056b(2, C1395b.m7039a(this.f4422b == null ? TtmlNode.ANONYMOUS_REGION_ID : this.f4422b)) + C1400e.m7056b(1, C1395b.m7039a(this.f4421a));
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7078a(1, C1395b.m7039a(this.f4421a));
            c1400e.m7078a(2, C1395b.m7039a(this.f4422b == null ? TtmlNode.ANONYMOUS_REGION_ID : this.f4422b));
        }
    }

    /* renamed from: com.crashlytics.android.c.z$d */
    private static final class C1469d extends C1465j {
        /* renamed from: a */
        private final float f4423a;
        /* renamed from: b */
        private final int f4424b;
        /* renamed from: c */
        private final boolean f4425c;
        /* renamed from: d */
        private final int f4426d;
        /* renamed from: e */
        private final long f4427e;
        /* renamed from: f */
        private final long f4428f;

        public C1469d(float f, int i, boolean z, int i2, long j, long j2) {
            super(5, new C1465j[0]);
            this.f4423a = f;
            this.f4424b = i;
            this.f4425c = z;
            this.f4426d = i2;
            this.f4427e = j;
            this.f4428f = j2;
        }

        /* renamed from: a */
        public int mo1176a() {
            return (((((0 + C1400e.m7054b(1, this.f4423a)) + C1400e.m7066f(2, this.f4424b)) + C1400e.m7057b(3, this.f4425c)) + C1400e.m7061d(4, this.f4426d)) + C1400e.m7055b(5, this.f4427e)) + C1400e.m7055b(6, this.f4428f);
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7075a(1, this.f4423a);
            c1400e.m7089c(2, this.f4424b);
            c1400e.m7079a(3, this.f4425c);
            c1400e.m7076a(4, this.f4426d);
            c1400e.m7077a(5, this.f4427e);
            c1400e.m7077a(6, this.f4428f);
        }
    }

    /* renamed from: com.crashlytics.android.c.z$e */
    private static final class C1470e extends C1465j {
        /* renamed from: a */
        private final long f4429a;
        /* renamed from: b */
        private final String f4430b;

        public C1470e(long j, String str, C1465j... c1465jArr) {
            super(10, c1465jArr);
            this.f4429a = j;
            this.f4430b = str;
        }

        /* renamed from: a */
        public int mo1176a() {
            return C1400e.m7055b(1, this.f4429a) + C1400e.m7056b(2, C1395b.m7039a(this.f4430b));
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7077a(1, this.f4429a);
            c1400e.m7078a(2, C1395b.m7039a(this.f4430b));
        }
    }

    /* renamed from: com.crashlytics.android.c.z$f */
    private static final class C1471f extends C1465j {
        public C1471f(C1476l c1476l, C1475k c1475k, C1475k c1475k2) {
            super(1, c1475k, c1476l, c1475k2);
        }
    }

    /* renamed from: com.crashlytics.android.c.z$g */
    private static final class C1472g extends C1465j {
        /* renamed from: a */
        private final long f4431a;
        /* renamed from: b */
        private final String f4432b;
        /* renamed from: c */
        private final String f4433c;
        /* renamed from: d */
        private final long f4434d;
        /* renamed from: e */
        private final int f4435e;

        public C1472g(C1383a c1383a) {
            super(3, new C1465j[0]);
            this.f4431a = c1383a.f4183a;
            this.f4432b = c1383a.f4184b;
            this.f4433c = c1383a.f4185c;
            this.f4434d = c1383a.f4186d;
            this.f4435e = c1383a.f4187e;
        }

        /* renamed from: a */
        public int mo1176a() {
            return (((C1400e.m7055b(1, this.f4431a) + C1400e.m7056b(2, C1395b.m7039a(this.f4432b))) + C1400e.m7056b(3, C1395b.m7039a(this.f4433c))) + C1400e.m7055b(4, this.f4434d)) + C1400e.m7061d(5, this.f4435e);
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7077a(1, this.f4431a);
            c1400e.m7078a(2, C1395b.m7039a(this.f4432b));
            c1400e.m7078a(3, C1395b.m7039a(this.f4433c));
            c1400e.m7077a(4, this.f4434d);
            c1400e.m7076a(5, this.f4435e);
        }
    }

    /* renamed from: com.crashlytics.android.c.z$h */
    private static final class C1473h extends C1465j {
        /* renamed from: a */
        C1395b f4436a;

        public C1473h(C1395b c1395b) {
            super(6, new C1465j[0]);
            this.f4436a = c1395b;
        }

        /* renamed from: a */
        public int mo1176a() {
            return C1400e.m7056b(1, this.f4436a);
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7078a(1, this.f4436a);
        }
    }

    /* renamed from: com.crashlytics.android.c.z$i */
    private static final class C1474i extends C1465j {
        public C1474i() {
            super(0, new C1465j[0]);
        }

        /* renamed from: b */
        public int mo1178b() {
            return 0;
        }

        /* renamed from: b */
        public void mo1179b(C1400e c1400e) {
        }
    }

    /* renamed from: com.crashlytics.android.c.z$k */
    private static final class C1475k extends C1465j {
        /* renamed from: a */
        private final C1465j[] f4437a;

        public C1475k(C1465j... c1465jArr) {
            super(0, new C1465j[0]);
            this.f4437a = c1465jArr;
        }

        /* renamed from: b */
        public int mo1178b() {
            int i = 0;
            C1465j[] c1465jArr = this.f4437a;
            int i2 = 0;
            while (i < c1465jArr.length) {
                i2 += c1465jArr[i].mo1178b();
                i++;
            }
            return i2;
        }

        /* renamed from: b */
        public void mo1179b(C1400e c1400e) {
            for (C1465j b : this.f4437a) {
                b.mo1179b(c1400e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.c.z$l */
    private static final class C1476l extends C1465j {
        /* renamed from: a */
        private final String f4438a;
        /* renamed from: b */
        private final String f4439b;
        /* renamed from: c */
        private final long f4440c;

        public C1476l(C1382e c1382e) {
            super(3, new C1465j[0]);
            this.f4438a = c1382e.f4180a;
            this.f4439b = c1382e.f4181b;
            this.f4440c = c1382e.f4182c;
        }

        /* renamed from: a */
        public int mo1176a() {
            return (C1400e.m7056b(1, C1395b.m7039a(this.f4438a)) + C1400e.m7056b(2, C1395b.m7039a(this.f4439b))) + C1400e.m7055b(3, this.f4440c);
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            c1400e.m7078a(1, C1395b.m7039a(this.f4438a));
            c1400e.m7078a(2, C1395b.m7039a(this.f4439b));
            c1400e.m7077a(3, this.f4440c);
        }
    }

    /* renamed from: com.crashlytics.android.c.z$m */
    private static final class C1477m extends C1465j {
        /* renamed from: a */
        private final String f4441a;
        /* renamed from: b */
        private final int f4442b;

        public C1477m(C1384f c1384f, C1475k c1475k) {
            super(1, c1475k);
            this.f4441a = c1384f.f4188a;
            this.f4442b = c1384f.f4189b;
        }

        /* renamed from: d */
        private boolean m7306d() {
            return this.f4441a != null && this.f4441a.length() > 0;
        }

        /* renamed from: a */
        public int mo1176a() {
            return (m7306d() ? C1400e.m7056b(1, C1395b.m7039a(this.f4441a)) : 0) + C1400e.m7061d(2, this.f4442b);
        }

        /* renamed from: a */
        public void mo1177a(C1400e c1400e) {
            if (m7306d()) {
                c1400e.m7078a(1, C1395b.m7039a(this.f4441a));
            }
            c1400e.m7076a(2, this.f4442b);
        }
    }

    /* renamed from: a */
    private static C1470e m7309a(C1381d c1381d, C1460u c1460u, Map<String, String> map) {
        C1466a c1466a = new C1466a(new C1471f(new C1476l(c1381d.f4175b != null ? c1381d.f4175b : f4443a), C1478z.m7314a(c1381d.f4176c), C1478z.m7311a(c1381d.f4177d)), C1478z.m7312a(C1478z.m7316a(c1381d.f4178e, map)));
        C1465j a = C1478z.m7310a(c1381d.f4179f);
        C1395b a2 = c1460u.m7268a();
        if (a2 == null) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "No log data to include with this event.");
        }
        c1460u.m7272b();
        C1473h c1473h = a2 != null ? new C1473h(a2) : new C1474i();
        return new C1470e(c1381d.f4174a, "ndk-crash", c1466a, a, c1473h);
    }

    /* renamed from: a */
    private static C1465j m7310a(C1380c c1380c) {
        return c1380c == null ? new C1474i() : new C1469d(((float) c1380c.f4171f) / 100.0f, c1380c.f4172g, c1380c.f4173h, c1380c.f4166a, c1380c.f4167b - c1380c.f4169d, c1380c.f4168c - c1380c.f4170e);
    }

    /* renamed from: a */
    private static C1475k m7311a(C1378a[] c1378aArr) {
        C1465j[] c1465jArr = c1378aArr != null ? new C1467b[c1378aArr.length] : f4447e;
        for (int i = 0; i < c1465jArr.length; i++) {
            c1465jArr[i] = new C1467b(c1378aArr[i]);
        }
        return new C1475k(c1465jArr);
    }

    /* renamed from: a */
    private static C1475k m7312a(C1379b[] c1379bArr) {
        C1465j[] c1465jArr = c1379bArr != null ? new C1468c[c1379bArr.length] : f4448f;
        for (int i = 0; i < c1465jArr.length; i++) {
            c1465jArr[i] = new C1468c(c1379bArr[i]);
        }
        return new C1475k(c1465jArr);
    }

    /* renamed from: a */
    private static C1475k m7313a(C1383a[] c1383aArr) {
        C1465j[] c1465jArr = c1383aArr != null ? new C1472g[c1383aArr.length] : f4446d;
        for (int i = 0; i < c1465jArr.length; i++) {
            c1465jArr[i] = new C1472g(c1383aArr[i]);
        }
        return new C1475k(c1465jArr);
    }

    /* renamed from: a */
    private static C1475k m7314a(C1384f[] c1384fArr) {
        C1465j[] c1465jArr = c1384fArr != null ? new C1477m[c1384fArr.length] : f4445c;
        for (int i = 0; i < c1465jArr.length; i++) {
            C1384f c1384f = c1384fArr[i];
            c1465jArr[i] = new C1477m(c1384f, C1478z.m7313a(c1384f.f4190c));
        }
        return new C1475k(c1465jArr);
    }

    /* renamed from: a */
    public static void m7315a(C1381d c1381d, C1460u c1460u, Map<String, String> map, C1400e c1400e) {
        C1478z.m7309a(c1381d, c1460u, map).mo1179b(c1400e);
    }

    /* renamed from: a */
    private static C1379b[] m7316a(C1379b[] c1379bArr, Map<String, String> map) {
        int i;
        Map treeMap = new TreeMap(map);
        if (c1379bArr != null) {
            for (C1379b c1379b : c1379bArr) {
                treeMap.put(c1379b.f4164a, c1379b.f4165b);
            }
        }
        Entry[] entryArr = (Entry[]) treeMap.entrySet().toArray(new Entry[treeMap.size()]);
        C1379b[] c1379bArr2 = new C1379b[entryArr.length];
        for (i = 0; i < c1379bArr2.length; i++) {
            c1379bArr2[i] = new C1379b((String) entryArr[i].getKey(), (String) entryArr[i].getValue());
        }
        return c1379bArr2;
    }
}
