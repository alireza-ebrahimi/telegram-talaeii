package com.google.android.gms.internal.firebase_messaging;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

final class zzj {
    private final ConcurrentHashMap<zzk, List<Throwable>> zzh = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzi = new ReferenceQueue();

    zzj() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Object poll = this.zzi.poll();
        while (poll != null) {
            this.zzh.remove(poll);
            poll = this.zzi.poll();
        }
        List<Throwable> list = (List) this.zzh.get(new zzk(th, null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        list = (List) this.zzh.putIfAbsent(new zzk(th, this.zzi), vector);
        return list == null ? vector : list;
    }
}
