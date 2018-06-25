package org.p138a.p141b.p143b;

import org.p138a.p139a.p140a.C2432c;

/* renamed from: org.a.b.b.e */
class C2444e extends C2440a implements C2432c {
    /* renamed from: d */
    Class f8173d;

    C2444e(int i, String str, Class cls, Class[] clsArr, String[] strArr, Class[] clsArr2, Class cls2) {
        super(i, str, cls, clsArr, strArr, clsArr2);
        this.f8173d = cls2;
    }

    /* renamed from: a */
    protected String mo3393a(C2448h c2448h) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(c2448h.m11983a(m11955d()));
        if (c2448h.f8182b) {
            stringBuffer.append(c2448h.m11984a(m11974c()));
        }
        if (c2448h.f8182b) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(c2448h.m11985a(m11958f(), m11959g()));
        stringBuffer.append(".");
        stringBuffer.append(m11957e());
        c2448h.m11990b(stringBuffer, mo3390a());
        c2448h.m11991c(stringBuffer, m11961b());
        return stringBuffer.toString();
    }

    /* renamed from: c */
    public Class m11974c() {
        if (this.f8173d == null) {
            this.f8173d = m11954c(6);
        }
        return this.f8173d;
    }
}
