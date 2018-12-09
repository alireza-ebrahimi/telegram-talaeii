package com.google.android.gms.internal.gcm;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

final class zzh {
    private final ConcurrentHashMap<zzi, List<Throwable>> zzdf = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzdg = new ReferenceQueue();

    zzh() {
    }

    public final List<Throwable> zzd(Throwable th, boolean z) {
        Object poll = this.zzdg.poll();
        while (poll != null) {
            this.zzdf.remove(poll);
            poll = this.zzdg.poll();
        }
        List<Throwable> list = (List) this.zzdf.get(new zzi(th, null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        list = (List) this.zzdf.putIfAbsent(new zzi(th, this.zzdg), vector);
        return list == null ? vector : list;
    }
}
