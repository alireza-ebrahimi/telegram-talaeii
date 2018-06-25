package com.google.android.gms.dynamic;

import java.util.Iterator;

final class zzb implements zzo<T> {
    private /* synthetic */ zza zzhct;

    zzb(zza zza) {
        this.zzhct = zza;
    }

    public final void zza(T t) {
        this.zzhct.zzhcp = t;
        Iterator it = this.zzhct.zzhcr.iterator();
        while (it.hasNext()) {
            ((zzi) it.next()).zzb(this.zzhct.zzhcp);
        }
        this.zzhct.zzhcr.clear();
        this.zzhct.zzhcq = null;
    }
}
