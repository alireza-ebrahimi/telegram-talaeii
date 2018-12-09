package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import java.lang.ref.WeakReference;
import java.util.Map;

final class zzes<T> extends zzgm<Status> {
    private WeakReference<Map<T, zzhk<T>>> zzec;
    private WeakReference<T> zzed;

    zzes(Map<T, zzhk<T>> map, T t, ResultHolder<Status> resultHolder) {
        super(resultHolder);
        this.zzec = new WeakReference(map);
        this.zzed = new WeakReference(t);
    }

    public final void zza(Status status) {
        Map map = (Map) this.zzec.get();
        Object obj = this.zzed.get();
        if (!(status.getStatus().isSuccess() || map == null || obj == null)) {
            synchronized (map) {
                zzhk zzhk = (zzhk) map.remove(obj);
                if (zzhk != null) {
                    zzhk.clear();
                }
            }
        }
        zza(status);
    }
}
