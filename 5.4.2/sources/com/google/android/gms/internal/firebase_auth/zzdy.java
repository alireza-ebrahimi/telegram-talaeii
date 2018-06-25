package com.google.android.gms.internal.firebase_auth;

final class zzdy implements zzeg {
    private zzeg[] zzta;

    zzdy(zzeg... zzegArr) {
        this.zzta = zzegArr;
    }

    public final boolean zzb(Class<?> cls) {
        for (zzeg zzb : this.zzta) {
            if (zzb.zzb(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzef zzc(Class<?> cls) {
        for (zzeg zzeg : this.zzta) {
            if (zzeg.zzb(cls)) {
                return zzeg.zzc(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
