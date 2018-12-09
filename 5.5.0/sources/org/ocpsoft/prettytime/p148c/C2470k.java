package org.ocpsoft.prettytime.p148c;

import java.io.Serializable;
import java.util.Comparator;
import org.ocpsoft.prettytime.C2457e;

/* renamed from: org.ocpsoft.prettytime.c.k */
public class C2470k implements Serializable, Comparator<C2457e> {
    /* renamed from: a */
    public int m12061a(C2457e c2457e, C2457e c2457e2) {
        return c2457e.mo3404a() < c2457e2.mo3404a() ? -1 : c2457e.mo3404a() > c2457e2.mo3404a() ? 1 : 0;
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return m12061a((C2457e) obj, (C2457e) obj2);
    }
}
