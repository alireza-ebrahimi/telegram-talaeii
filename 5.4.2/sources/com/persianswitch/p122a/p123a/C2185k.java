package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.ab;
import java.util.LinkedHashSet;
import java.util.Set;

/* renamed from: com.persianswitch.a.a.k */
public final class C2185k {
    /* renamed from: a */
    private final Set<ab> f6631a = new LinkedHashSet();

    /* renamed from: a */
    public synchronized void m9883a(ab abVar) {
        this.f6631a.add(abVar);
    }

    /* renamed from: b */
    public synchronized void m9884b(ab abVar) {
        this.f6631a.remove(abVar);
    }

    /* renamed from: c */
    public synchronized boolean m9885c(ab abVar) {
        return this.f6631a.contains(abVar);
    }
}
