package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzfb extends zzfh {
    private final /* synthetic */ zzey zzur;

    private zzfb(zzey zzey) {
        this.zzur = zzey;
        super(zzey);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzfa(this.zzur);
    }
}
