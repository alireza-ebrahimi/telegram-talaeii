package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zze;

final class zzda implements zzeg {
    private static final zzda zzqw = new zzda();

    private zzda() {
    }

    public static zzda zzdy() {
        return zzqw;
    }

    public final boolean zzb(Class<?> cls) {
        return zzdb.class.isAssignableFrom(cls);
    }

    public final zzef zzc(Class<?> cls) {
        if (zzdb.class.isAssignableFrom(cls)) {
            try {
                return (zzef) zzdb.zzd(cls.asSubclass(zzdb.class)).zza(zze.zzrg, null, null);
            } catch (Throwable e) {
                Throwable th = e;
                String str = "Unable to get message info for ";
                String valueOf = String.valueOf(cls.getName());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), th);
            }
        }
        String str2 = "Unsupported message type: ";
        valueOf = String.valueOf(cls.getName());
        throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }
}
