package com.google.p098a;

import com.google.p098a.p100b.C1726d;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.a.g */
public final class C1769g {
    /* renamed from: a */
    private C1726d f5374a = C1726d.f5293a;
    /* renamed from: b */
    private C1779u f5375b = C1779u.DEFAULT;
    /* renamed from: c */
    private C1750e f5376c = C1751d.IDENTITY;
    /* renamed from: d */
    private final Map<Type, C1770h<?>> f5377d = new HashMap();
    /* renamed from: e */
    private final List<C1668x> f5378e = new ArrayList();
    /* renamed from: f */
    private final List<C1668x> f5379f = new ArrayList();
    /* renamed from: g */
    private boolean f5380g;
    /* renamed from: h */
    private String f5381h;
    /* renamed from: i */
    private int f5382i = 2;
    /* renamed from: j */
    private int f5383j = 2;
    /* renamed from: k */
    private boolean f5384k;
    /* renamed from: l */
    private boolean f5385l;
    /* renamed from: m */
    private boolean f5386m = true;
    /* renamed from: n */
    private boolean f5387n;
    /* renamed from: o */
    private boolean f5388o;

    /* renamed from: a */
    private void m8401a(String str, int i, int i2, List<C1668x> list) {
        Object c1667a;
        if (str != null && !TtmlNode.ANONYMOUS_REGION_ID.equals(str.trim())) {
            c1667a = new C1667a(str);
        } else if (i != 2 && i2 != 2) {
            c1667a = new C1667a(i, i2);
        } else {
            return;
        }
        list.add(C1784v.m8443a(C1748a.m8358b(Date.class), c1667a));
        list.add(C1784v.m8443a(C1748a.m8358b(Timestamp.class), c1667a));
        list.add(C1784v.m8443a(C1748a.m8358b(java.sql.Date.class), c1667a));
    }

    /* renamed from: a */
    public C1768f m8402a() {
        List arrayList = new ArrayList();
        arrayList.addAll(this.f5378e);
        Collections.reverse(arrayList);
        arrayList.addAll(this.f5379f);
        m8401a(this.f5381h, this.f5382i, this.f5383j, arrayList);
        return new C1768f(this.f5374a, this.f5376c, this.f5377d, this.f5380g, this.f5384k, this.f5388o, this.f5386m, this.f5387n, this.f5385l, this.f5375b, arrayList);
    }

    /* renamed from: a */
    public C1769g m8403a(C1668x c1668x) {
        this.f5378e.add(c1668x);
        return this;
    }
}
