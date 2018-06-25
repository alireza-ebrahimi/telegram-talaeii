package com.crashlytics.android.p064a;

import com.crashlytics.android.p064a.ad.C1332b;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.crashlytics.android.a.y */
class C1362y implements C1354p {
    /* renamed from: b */
    static final Set<C1332b> f4124b = new C13611();
    /* renamed from: a */
    final int f4125a;

    /* renamed from: com.crashlytics.android.a.y$1 */
    static class C13611 extends HashSet<C1332b> {
        C13611() {
            add(C1332b.START);
            add(C1332b.RESUME);
            add(C1332b.PAUSE);
            add(C1332b.STOP);
        }
    }

    public C1362y(int i) {
        this.f4125a = i;
    }

    /* renamed from: a */
    public boolean mo1141a(ad adVar) {
        boolean z = f4124b.contains(adVar.f4032c) && adVar.f4030a.f4045g == null;
        return z && (Math.abs(adVar.f4030a.f4041c.hashCode() % this.f4125a) != 0);
    }
}
