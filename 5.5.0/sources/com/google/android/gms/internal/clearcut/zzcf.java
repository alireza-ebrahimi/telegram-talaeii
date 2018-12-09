package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg.zzg;

final class zzcf implements zzdn {
    private static final zzcf zzjo = new zzcf();

    private zzcf() {
    }

    public static zzcf zzay() {
        return zzjo;
    }

    public final boolean zza(Class<?> cls) {
        return zzcg.class.isAssignableFrom(cls);
    }

    public final zzdm zzb(Class<?> cls) {
        if (zzcg.class.isAssignableFrom(cls)) {
            try {
                return (zzdm) zzcg.zzc(cls.asSubclass(zzcg.class)).zza(zzg.zzkf, null, null);
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
