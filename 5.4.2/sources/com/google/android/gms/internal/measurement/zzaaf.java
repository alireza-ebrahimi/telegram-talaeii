package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzzs.zzb;

final class zzaaf implements zzaay {
    private static final zzaam zzbto = new zzaag();
    private final zzaam zzbtn;

    public zzaaf() {
        this(new zzaah(zzzr.zztu(), zzub()));
    }

    private zzaaf(zzaam zzaam) {
        this.zzbtn = (zzaam) zzzt.zza(zzaam, "messageInfoFactory");
    }

    private static boolean zza(zzaal zzaal) {
        return zzaal.zzuf() == zzb.zzbsu;
    }

    private static zzaam zzub() {
        try {
            return (zzaam) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzbto;
        }
    }

    public final <T> zzaax<T> zzg(Class<T> cls) {
        zzaaz.zzh(cls);
        zzaal zze = this.zzbtn.zze(cls);
        if (zze.zzug()) {
            return zzzs.class.isAssignableFrom(cls) ? zzaar.zza(zzaaz.zzup(), zzzn.zztp(), zze.zzuh()) : zzaar.zza(zzaaz.zzun(), zzzn.zztq(), zze.zzuh());
        } else {
            if (zzzs.class.isAssignableFrom(cls)) {
                if (zza(zze)) {
                    return zzaaq.zza(cls, zze, zzaau.zzuk(), zzaab.zzua(), zzaaz.zzup(), zzzn.zztp(), zzaak.zzud());
                }
                return zzaaq.zza(cls, zze, zzaau.zzuk(), zzaab.zzua(), zzaaz.zzup(), null, zzaak.zzud());
            } else if (zza(zze)) {
                return zzaaq.zza(cls, zze, zzaau.zzuj(), zzaab.zztz(), zzaaz.zzun(), zzzn.zztq(), zzaak.zzuc());
            } else {
                return zzaaq.zza(cls, zze, zzaau.zzuj(), zzaab.zztz(), zzaaz.zzuo(), null, zzaak.zzuc());
            }
        }
    }
}
