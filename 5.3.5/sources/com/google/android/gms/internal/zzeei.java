package com.google.android.gms.internal;

import java.util.Iterator;

final class zzeei implements Iterator<zzeej> {
    private int zzmzc = (this.zzmzd.length - 1);
    private /* synthetic */ zzeeh zzmzd;

    zzeei(zzeeh zzeeh) {
        this.zzmzd = zzeeh;
    }

    public final boolean hasNext() {
        return this.zzmzc >= 0;
    }

    public final /* synthetic */ Object next() {
        boolean z = true;
        long zzb = this.zzmzd.value & ((long) (1 << this.zzmzc));
        zzeej zzeej = new zzeej();
        if (zzb != 0) {
            z = false;
        }
        zzeej.zzmze = z;
        zzeej.zzmzf = (int) Math.pow(2.0d, (double) this.zzmzc);
        this.zzmzc--;
        return zzeej;
    }

    public final void remove() {
    }
}
