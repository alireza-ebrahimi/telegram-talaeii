package com.google.android.gms.internal;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class zzdys {
    private final ConcurrentHashMap<zzdyt, List<Throwable>> zzmmi = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzmmj = new ReferenceQueue();

    zzdys() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Object poll = this.zzmmj.poll();
        while (poll != null) {
            this.zzmmi.remove(poll);
            poll = this.zzmmj.poll();
        }
        return (List) this.zzmmi.get(new zzdyt(th, null));
    }
}
