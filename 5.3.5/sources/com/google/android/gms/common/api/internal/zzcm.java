package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzcm {
    private final Set<zzci<?>> zzfgd = Collections.newSetFromMap(new WeakHashMap());

    public static <L> zzci<L> zzb(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzbq.checkNotNull(l, "Listener must not be null");
        zzbq.checkNotNull(looper, "Looper must not be null");
        zzbq.checkNotNull(str, "Listener type must not be null");
        return new zzci(looper, l, str);
    }

    public static <L> zzck<L> zzb(@NonNull L l, @NonNull String str) {
        zzbq.checkNotNull(l, "Listener must not be null");
        zzbq.checkNotNull(str, "Listener type must not be null");
        zzbq.zzh(str, "Listener type must not be empty");
        return new zzck(l, str);
    }

    public final void release() {
        for (zzci clear : this.zzfgd) {
            clear.clear();
        }
        this.zzfgd.clear();
    }

    public final <L> zzci<L> zza(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzci<L> zzb = zzb(l, looper, str);
        this.zzfgd.add(zzb);
        return zzb;
    }
}
