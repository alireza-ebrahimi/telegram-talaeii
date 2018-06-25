package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zze;

final class zzdw implements zzew {
    private static final zzeg zzsz = new zzdx();
    private final zzeg zzsy;

    public zzdw() {
        this(new zzdy(zzda.zzdy(), zzes()));
    }

    private zzdw(zzeg zzeg) {
        this.zzsy = (zzeg) zzdd.zza((Object) zzeg, "messageInfoFactory");
    }

    private static boolean zza(zzef zzef) {
        return zzef.zzez() == zze.zzrm;
    }

    private static zzeg zzes() {
        try {
            return (zzeg) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzsz;
        }
    }

    public final <T> zzev<T> zze(Class<T> cls) {
        zzex.zzg(cls);
        zzef zzc = this.zzsy.zzc(cls);
        if (zzc.zzfa()) {
            return zzdb.class.isAssignableFrom(cls) ? zzem.zza(zzex.zzfl(), zzcr.zzdn(), zzc.zzfb()) : zzem.zza(zzex.zzfj(), zzcr.zzdo(), zzc.zzfb());
        } else {
            if (zzdb.class.isAssignableFrom(cls)) {
                if (zza(zzc)) {
                    return zzel.zza(cls, zzc, zzeq.zzfe(), zzdr.zzer(), zzex.zzfl(), zzcr.zzdn(), zzee.zzex());
                }
                return zzel.zza(cls, zzc, zzeq.zzfe(), zzdr.zzer(), zzex.zzfl(), null, zzee.zzex());
            } else if (zza(zzc)) {
                return zzel.zza(cls, zzc, zzeq.zzfd(), zzdr.zzeq(), zzex.zzfj(), zzcr.zzdo(), zzee.zzew());
            } else {
                return zzel.zza(cls, zzc, zzeq.zzfd(), zzdr.zzeq(), zzex.zzfk(), null, zzee.zzew());
            }
        }
    }
}
