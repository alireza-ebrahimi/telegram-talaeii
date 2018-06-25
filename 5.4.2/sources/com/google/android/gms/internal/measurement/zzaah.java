package com.google.android.gms.internal.measurement;

final class zzaah implements zzaam {
    private zzaam[] zzbtp;

    zzaah(zzaam... zzaamArr) {
        this.zzbtp = zzaamArr;
    }

    public final boolean zzd(Class<?> cls) {
        for (zzaam zzd : this.zzbtp) {
            if (zzd.zzd(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzaal zze(Class<?> cls) {
        for (zzaam zzaam : this.zzbtp) {
            if (zzaam.zzd(cls)) {
                return zzaam.zze(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
