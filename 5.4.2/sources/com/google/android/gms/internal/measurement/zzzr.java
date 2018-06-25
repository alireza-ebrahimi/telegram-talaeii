package com.google.android.gms.internal.measurement;

final class zzzr implements zzaam {
    private static final zzzr zzbsh = new zzzr();

    private zzzr() {
    }

    public static zzzr zztu() {
        return zzbsh;
    }

    public final boolean zzd(Class<?> cls) {
        return zzzs.class.isAssignableFrom(cls);
    }

    public final zzaal zze(Class<?> cls) {
        if (zzzs.class.isAssignableFrom(cls)) {
            try {
                return (zzaal) zzzs.zzf(cls.asSubclass(zzzs.class)).zza(3, null, null);
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
