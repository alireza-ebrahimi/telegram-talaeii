package org.p138a.p141b.p143b;

import java.lang.ref.SoftReference;
import java.util.StringTokenizer;
import org.p138a.p139a.C2429d;

/* renamed from: org.a.b.b.f */
abstract class C2438f implements C2429d {
    /* renamed from: a */
    private static boolean f8145a = true;
    /* renamed from: k */
    static String[] f8146k = new String[0];
    /* renamed from: l */
    static Class[] f8147l = new Class[0];
    /* renamed from: b */
    private String f8148b;
    /* renamed from: e */
    int f8149e = -1;
    /* renamed from: f */
    String f8150f;
    /* renamed from: g */
    String f8151g;
    /* renamed from: h */
    Class f8152h;
    /* renamed from: i */
    C2445a f8153i;
    /* renamed from: j */
    ClassLoader f8154j = null;

    /* renamed from: org.a.b.b.f$a */
    private interface C2445a {
        /* renamed from: a */
        String mo3394a(int i);

        /* renamed from: a */
        void mo3395a(int i, String str);
    }

    /* renamed from: org.a.b.b.f$b */
    private static final class C2446b implements C2445a {
        /* renamed from: a */
        private SoftReference f8174a;

        public C2446b() {
            m11978b();
        }

        /* renamed from: a */
        private String[] m11977a() {
            return (String[]) this.f8174a.get();
        }

        /* renamed from: b */
        private String[] m11978b() {
            Object obj = new String[3];
            this.f8174a = new SoftReference(obj);
            return obj;
        }

        /* renamed from: a */
        public String mo3394a(int i) {
            String[] a = m11977a();
            return a == null ? null : a[i];
        }

        /* renamed from: a */
        public void mo3395a(int i, String str) {
            String[] a = m11977a();
            if (a == null) {
                a = m11978b();
            }
            a[i] = str;
        }
    }

    C2438f(int i, String str, Class cls) {
        this.f8149e = i;
        this.f8150f = str;
        this.f8152h = cls;
    }

    /* renamed from: a */
    private ClassLoader mo3390a() {
        if (this.f8154j == null) {
            this.f8154j = getClass().getClassLoader();
        }
        return this.f8154j;
    }

    /* renamed from: a */
    String m11950a(int i) {
        int i2 = 0;
        int indexOf = this.f8148b.indexOf(45);
        while (true) {
            int i3 = i - 1;
            if (i <= 0) {
                break;
            }
            i2 = indexOf + 1;
            indexOf = this.f8148b.indexOf(45, i2);
            i = i3;
        }
        if (indexOf == -1) {
            indexOf = this.f8148b.length();
        }
        return this.f8148b.substring(i2, indexOf);
    }

    /* renamed from: a */
    protected abstract String mo3393a(C2448h c2448h);

    /* renamed from: b */
    int m11952b(int i) {
        return Integer.parseInt(m11950a(i), 16);
    }

    /* renamed from: b */
    String m11953b(C2448h c2448h) {
        String str = null;
        if (f8145a) {
            if (this.f8153i == null) {
                try {
                    this.f8153i = new C2446b();
                } catch (Throwable th) {
                    f8145a = false;
                }
            } else {
                str = this.f8153i.mo3394a(c2448h.f8189i);
            }
        }
        if (str == null) {
            str = mo3393a(c2448h);
        }
        if (f8145a) {
            this.f8153i.mo3395a(c2448h.f8189i, str);
        }
        return str;
    }

    /* renamed from: c */
    Class m11954c(int i) {
        return C2441b.m11963a(m11950a(i), mo3390a());
    }

    /* renamed from: d */
    public int m11955d() {
        if (this.f8149e == -1) {
            this.f8149e = m11952b(0);
        }
        return this.f8149e;
    }

    /* renamed from: d */
    Class[] m11956d(int i) {
        StringTokenizer stringTokenizer = new StringTokenizer(m11950a(i), ":");
        int countTokens = stringTokenizer.countTokens();
        Class[] clsArr = new Class[countTokens];
        for (int i2 = 0; i2 < countTokens; i2++) {
            clsArr[i2] = C2441b.m11963a(stringTokenizer.nextToken(), mo3390a());
        }
        return clsArr;
    }

    /* renamed from: e */
    public String m11957e() {
        if (this.f8150f == null) {
            this.f8150f = m11950a(1);
        }
        return this.f8150f;
    }

    /* renamed from: f */
    public Class m11958f() {
        if (this.f8152h == null) {
            this.f8152h = m11954c(2);
        }
        return this.f8152h;
    }

    /* renamed from: g */
    public String m11959g() {
        if (this.f8151g == null) {
            this.f8151g = m11958f().getName();
        }
        return this.f8151g;
    }

    public final String toString() {
        return m11953b(C2448h.f8179k);
    }
}
