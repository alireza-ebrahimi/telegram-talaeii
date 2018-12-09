package com.google.firebase.components;

import android.support.annotation.GuardedBy;
import com.google.firebase.p106a.C1808a;
import com.google.firebase.p106a.C1809b;
import com.google.firebase.p106a.C1810c;
import com.google.firebase.p106a.C1811d;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* renamed from: com.google.firebase.components.n */
class C1916n implements C1810c, C1811d {
    @GuardedBy("this")
    /* renamed from: a */
    private final Map<Class<?>, ConcurrentHashMap<C1809b<Object>, Executor>> f5624a = new HashMap();
    @GuardedBy("this")
    /* renamed from: b */
    private Queue<C1808a<?>> f5625b = new ArrayDeque();
    /* renamed from: c */
    private final Executor f5626c;

    C1916n(Executor executor) {
        this.f5626c = executor;
    }

    /* renamed from: b */
    private synchronized Set<Entry<C1809b<Object>, Executor>> m8743b(C1808a<?> c1808a) {
        Map map;
        map = (Map) this.f5624a.get(c1808a.m8467a());
        return map == null ? Collections.emptySet() : map.entrySet();
    }

    /* renamed from: a */
    final void m8744a() {
        Queue queue = null;
        synchronized (this) {
            if (this.f5625b != null) {
                queue = this.f5625b;
                this.f5625b = null;
            }
        }
        if (r0 != null) {
            for (C1808a a : r0) {
                m8745a(a);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public void m8745a(com.google.firebase.p106a.C1808a<?> r5) {
        /*
        r4 = this;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r5);
        monitor-enter(r4);
        r0 = r4.f5625b;	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x000f;
    L_0x0008:
        r0 = r4.f5625b;	 Catch:{ all -> 0x0033 }
        r0.add(r5);	 Catch:{ all -> 0x0033 }
        monitor-exit(r4);	 Catch:{ all -> 0x0033 }
    L_0x000e:
        return;
    L_0x000f:
        monitor-exit(r4);	 Catch:{ all -> 0x0033 }
        r0 = r4.m8743b(r5);
        r2 = r0.iterator();
    L_0x0018:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x000e;
    L_0x001e:
        r0 = r2.next();
        r0 = (java.util.Map.Entry) r0;
        r1 = r0.getValue();
        r1 = (java.util.concurrent.Executor) r1;
        r3 = new com.google.firebase.components.o;
        r3.<init>(r0, r5);
        r1.execute(r3);
        goto L_0x0018;
    L_0x0033:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0033 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.components.n.a(com.google.firebase.a.a):void");
    }
}
