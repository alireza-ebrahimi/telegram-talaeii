package com.google.android.gms.internal.measurement;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class zzxh {
    private final ConcurrentHashMap<zzxi, List<Throwable>> zzbol = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzbom = new ReferenceQueue();

    zzxh() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Object poll = this.zzbom.poll();
        while (poll != null) {
            this.zzbol.remove(poll);
            poll = this.zzbom.poll();
        }
        return (List) this.zzbol.get(new zzxi(th, null));
    }
}
