package com.google.android.gms.internal.clearcut;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzel extends zzer {
    private final /* synthetic */ zzei zzos;

    private zzel(zzei zzei) {
        this.zzos = zzei;
        super(zzei);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzek(this.zzos);
    }
}
