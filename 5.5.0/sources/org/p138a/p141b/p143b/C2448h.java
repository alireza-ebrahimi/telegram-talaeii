package org.p138a.p141b.p143b;

import java.lang.reflect.Modifier;

/* renamed from: org.a.b.b.h */
class C2448h {
    /* renamed from: j */
    static C2448h f8178j = new C2448h();
    /* renamed from: k */
    static C2448h f8179k = new C2448h();
    /* renamed from: l */
    static C2448h f8180l = new C2448h();
    /* renamed from: a */
    boolean f8181a = true;
    /* renamed from: b */
    boolean f8182b = true;
    /* renamed from: c */
    boolean f8183c = false;
    /* renamed from: d */
    boolean f8184d = false;
    /* renamed from: e */
    boolean f8185e = false;
    /* renamed from: f */
    boolean f8186f = true;
    /* renamed from: g */
    boolean f8187g = true;
    /* renamed from: h */
    boolean f8188h = true;
    /* renamed from: i */
    int f8189i;

    static {
        f8178j.f8181a = true;
        f8178j.f8182b = false;
        f8178j.f8183c = false;
        f8178j.f8184d = false;
        f8178j.f8185e = true;
        f8178j.f8186f = false;
        f8178j.f8187g = false;
        f8178j.f8189i = 0;
        f8179k.f8181a = true;
        f8179k.f8182b = true;
        f8179k.f8183c = false;
        f8179k.f8184d = false;
        f8179k.f8185e = false;
        f8178j.f8189i = 1;
        f8180l.f8181a = false;
        f8180l.f8182b = true;
        f8180l.f8183c = false;
        f8180l.f8184d = true;
        f8180l.f8185e = false;
        f8180l.f8188h = false;
        f8180l.f8189i = 2;
    }

    C2448h() {
    }

    /* renamed from: a */
    String m11983a(int i) {
        if (!this.f8184d) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        String modifier = Modifier.toString(i);
        return modifier.length() == 0 ? TtmlNode.ANONYMOUS_REGION_ID : new StringBuffer().append(modifier).append(" ").toString();
    }

    /* renamed from: a */
    public String m11984a(Class cls) {
        return m11986a(cls, cls.getName(), this.f8181a);
    }

    /* renamed from: a */
    public String m11985a(Class cls, String str) {
        return m11986a(cls, str, this.f8185e);
    }

    /* renamed from: a */
    String m11986a(Class cls, String str, boolean z) {
        if (cls == null) {
            return "ANONYMOUS";
        }
        if (!cls.isArray()) {
            return z ? m11989b(str).replace('$', '.') : str.replace('$', '.');
        } else {
            Class componentType = cls.getComponentType();
            return new StringBuffer().append(m11986a(componentType, componentType.getName(), z)).append("[]").toString();
        }
    }

    /* renamed from: a */
    String m11987a(String str) {
        int lastIndexOf = str.lastIndexOf(45);
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }

    /* renamed from: a */
    public void m11988a(StringBuffer stringBuffer, Class[] clsArr) {
        for (int i = 0; i < clsArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(m11984a(clsArr[i]));
        }
    }

    /* renamed from: b */
    String m11989b(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }

    /* renamed from: b */
    public void m11990b(StringBuffer stringBuffer, Class[] clsArr) {
        if (clsArr != null) {
            if (this.f8182b) {
                stringBuffer.append("(");
                m11988a(stringBuffer, clsArr);
                stringBuffer.append(")");
            } else if (clsArr.length == 0) {
                stringBuffer.append("()");
            } else {
                stringBuffer.append("(..)");
            }
        }
    }

    /* renamed from: c */
    public void m11991c(StringBuffer stringBuffer, Class[] clsArr) {
        if (this.f8183c && clsArr != null && clsArr.length != 0) {
            stringBuffer.append(" throws ");
            m11988a(stringBuffer, clsArr);
        }
    }
}
