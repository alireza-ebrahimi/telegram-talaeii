package org.p138a.p141b.p143b;

import org.p138a.p139a.p140a.C2431a;

/* renamed from: org.a.b.b.a */
abstract class C2440a extends C2439d implements C2431a {
    /* renamed from: a */
    Class[] f8155a;
    /* renamed from: b */
    String[] f8156b;
    /* renamed from: c */
    Class[] f8157c;

    C2440a(int i, String str, Class cls, Class[] clsArr, String[] strArr, Class[] clsArr2) {
        super(i, str, cls);
        this.f8155a = clsArr;
        this.f8156b = strArr;
        this.f8157c = clsArr2;
    }

    /* renamed from: a */
    public Class[] mo3390a() {
        if (this.f8155a == null) {
            this.f8155a = m11956d(3);
        }
        return this.f8155a;
    }

    /* renamed from: b */
    public Class[] m11961b() {
        if (this.f8157c == null) {
            this.f8157c = m11956d(5);
        }
        return this.f8157c;
    }
}
