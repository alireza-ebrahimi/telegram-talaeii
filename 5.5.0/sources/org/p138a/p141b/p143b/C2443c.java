package org.p138a.p141b.p143b;

import org.p138a.p139a.C2429d;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p139a.C2436c;
import org.p138a.p139a.p140a.C2433d;

/* renamed from: org.a.b.b.c */
class C2443c implements C2436c {
    /* renamed from: a */
    Object f8169a;
    /* renamed from: b */
    Object f8170b;
    /* renamed from: c */
    Object[] f8171c;
    /* renamed from: d */
    C2428a f8172d;

    /* renamed from: org.a.b.b.c$a */
    static class C2442a implements C2428a {
        /* renamed from: a */
        String f8165a;
        /* renamed from: b */
        C2429d f8166b;
        /* renamed from: c */
        C2433d f8167c;
        /* renamed from: d */
        private int f8168d;

        public C2442a(int i, String str, C2429d c2429d, C2433d c2433d) {
            this.f8165a = str;
            this.f8166b = c2429d;
            this.f8167c = c2433d;
            this.f8168d = i;
        }

        /* renamed from: a */
        public String m11969a() {
            return this.f8165a;
        }

        /* renamed from: a */
        String m11970a(C2448h c2448h) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(c2448h.m11987a(m11969a()));
            stringBuffer.append("(");
            stringBuffer.append(((C2438f) m11971b()).m11953b(c2448h));
            stringBuffer.append(")");
            return stringBuffer.toString();
        }

        /* renamed from: b */
        public C2429d m11971b() {
            return this.f8166b;
        }

        public final String toString() {
            return m11970a(C2448h.f8179k);
        }
    }

    public C2443c(C2428a c2428a, Object obj, Object obj2, Object[] objArr) {
        this.f8172d = c2428a;
        this.f8169a = obj;
        this.f8170b = obj2;
        this.f8171c = objArr;
    }

    /* renamed from: a */
    public Object mo3392a() {
        return this.f8170b;
    }

    public final String toString() {
        return this.f8172d.toString();
    }
}
