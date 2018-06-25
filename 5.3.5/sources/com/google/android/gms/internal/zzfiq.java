package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzg;

final class zzfiq implements zzfjw {
    private static final zzfjb zzpqx = new zzfir();
    private final zzfjb zzpqw;

    public zzfiq() {
        this(new zzfis(zzfht.zzczp(), zzdas()));
    }

    private zzfiq(zzfjb zzfjb) {
        this.zzpqw = (zzfjb) zzfhz.zzc(zzfjb, "messageInfoFactory");
    }

    private static boolean zza(zzfja zzfja) {
        return zzfja.zzdaz() == zzg.zzpqc;
    }

    private static zzfjb zzdas() {
        try {
            return (zzfjb) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzpqx;
        }
    }

    public final <T> zzfjv<T> zzk(Class<T> cls) {
        zzfjx.zzm(cls);
        zzfja zzj = this.zzpqw.zzj(cls);
        if (zzj.zzdba()) {
            return zzfhu.class.isAssignableFrom(cls) ? zzfjh.zza(cls, zzfjx.zzdbm(), zzfhp.zzczh(), zzj.zzdbb()) : zzfjh.zza(cls, zzfjx.zzdbk(), zzfhp.zzczi(), zzj.zzdbb());
        } else {
            if (zzfhu.class.isAssignableFrom(cls)) {
                if (zza(zzj)) {
                    return zzfjg.zza(cls, zzj, zzfjk.zzdbd(), zzfim.zzdar(), zzfjx.zzdbm(), zzfhp.zzczh(), zzfiz.zzdax());
                }
                return zzfjg.zza(cls, zzj, zzfjk.zzdbd(), zzfim.zzdar(), zzfjx.zzdbm(), null, zzfiz.zzdax());
            } else if (zza(zzj)) {
                return zzfjg.zza(cls, zzj, zzfjk.zzdbc(), zzfim.zzdaq(), zzfjx.zzdbk(), zzfhp.zzczi(), zzfiz.zzdaw());
            } else {
                return zzfjg.zza(cls, zzj, zzfjk.zzdbc(), zzfim.zzdaq(), zzfjx.zzdbl(), null, zzfiz.zzdaw());
            }
        }
    }
}
