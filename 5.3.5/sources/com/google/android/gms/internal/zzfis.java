package com.google.android.gms.internal;

final class zzfis implements zzfjb {
    private zzfjb[] zzpqy;

    zzfis(zzfjb... zzfjbArr) {
        this.zzpqy = zzfjbArr;
    }

    public final boolean zzi(Class<?> cls) {
        for (zzfjb zzi : this.zzpqy) {
            if (zzi.zzi(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzfja zzj(Class<?> cls) {
        for (zzfjb zzfjb : this.zzpqy) {
            if (zzfjb.zzi(cls)) {
                return zzfjb.zzj(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
