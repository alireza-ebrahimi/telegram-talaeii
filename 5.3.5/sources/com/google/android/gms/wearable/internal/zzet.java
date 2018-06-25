package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.lang.ref.WeakReference;
import java.util.Map;

final class zzet<T> extends zzgm<Status> {
    private WeakReference<Map<T, zzhk<T>>> zzlut;
    private WeakReference<T> zzluu;

    zzet(Map<T, zzhk<T>> map, T t, zzn<Status> zzn) {
        super(zzn);
        this.zzlut = new WeakReference(map);
        this.zzluu = new WeakReference(t);
    }

    public final void zza(Status status) {
        Map map = (Map) this.zzlut.get();
        Object obj = this.zzluu.get();
        if (!(status.getStatus().getStatusCode() != WearableStatusCodes.UNKNOWN_LISTENER || map == null || obj == null)) {
            synchronized (map) {
                zzhk zzhk = (zzhk) map.remove(obj);
                if (zzhk != null) {
                    zzhk.clear();
                }
            }
        }
        zzav(status);
    }
}
