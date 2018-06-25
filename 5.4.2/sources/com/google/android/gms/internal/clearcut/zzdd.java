package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg.zzg;

final class zzdd implements zzeg {
    private static final zzdn zzlz = new zzde();
    private final zzdn zzly;

    public zzdd() {
        this(new zzdf(zzcf.zzay(), zzby()));
    }

    private zzdd(zzdn zzdn) {
        this.zzly = (zzdn) zzci.zza((Object) zzdn, "messageInfoFactory");
    }

    private static boolean zza(zzdm zzdm) {
        return zzdm.zzcf() == zzg.zzkl;
    }

    private static zzdn zzby() {
        try {
            return (zzdn) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzlz;
        }
    }

    public final <T> zzef<T> zzd(Class<T> cls) {
        zzeh.zzf((Class) cls);
        zzdm zzb = this.zzly.zzb(cls);
        if (zzb.zzcg()) {
            return zzcg.class.isAssignableFrom(cls) ? zzdu.zza(zzeh.zzdo(), zzbx.zzap(), zzb.zzch()) : zzdu.zza(zzeh.zzdm(), zzbx.zzaq(), zzb.zzch());
        } else {
            if (zzcg.class.isAssignableFrom(cls)) {
                if (zza(zzb)) {
                    return zzds.zza((Class) cls, zzb, zzdy.zzck(), zzcy.zzbw(), zzeh.zzdo(), zzbx.zzap(), zzdl.zzcd());
                }
                return zzds.zza((Class) cls, zzb, zzdy.zzck(), zzcy.zzbw(), zzeh.zzdo(), null, zzdl.zzcd());
            } else if (zza(zzb)) {
                return zzds.zza((Class) cls, zzb, zzdy.zzcj(), zzcy.zzbv(), zzeh.zzdm(), zzbx.zzaq(), zzdl.zzcc());
            } else {
                return zzds.zza((Class) cls, zzb, zzdy.zzcj(), zzcy.zzbv(), zzeh.zzdn(), null, zzdl.zzcc());
            }
        }
    }
}
