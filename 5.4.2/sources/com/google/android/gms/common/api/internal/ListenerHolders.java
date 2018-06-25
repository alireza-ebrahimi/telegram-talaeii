package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

@KeepForSdk
public class ListenerHolders {
    private final Set<ListenerHolder<?>> zzlm = Collections.newSetFromMap(new WeakHashMap());

    @KeepForSdk
    public static <L> ListenerHolder<L> createListenerHolder(L l, Looper looper, String str) {
        Preconditions.checkNotNull(l, "Listener must not be null");
        Preconditions.checkNotNull(looper, "Looper must not be null");
        Preconditions.checkNotNull(str, "Listener type must not be null");
        return new ListenerHolder(looper, l, str);
    }

    @KeepForSdk
    public static <L> ListenerKey<L> createListenerKey(L l, String str) {
        Preconditions.checkNotNull(l, "Listener must not be null");
        Preconditions.checkNotNull(str, "Listener type must not be null");
        Preconditions.checkNotEmpty(str, "Listener type must not be empty");
        return new ListenerKey(l, str);
    }

    public final void release() {
        for (ListenerHolder clear : this.zzlm) {
            clear.clear();
        }
        this.zzlm.clear();
    }

    public final <L> ListenerHolder<L> zza(L l, Looper looper, String str) {
        ListenerHolder<L> createListenerHolder = createListenerHolder(l, looper, str);
        this.zzlm.add(createListenerHolder);
        return createListenerHolder;
    }
}
