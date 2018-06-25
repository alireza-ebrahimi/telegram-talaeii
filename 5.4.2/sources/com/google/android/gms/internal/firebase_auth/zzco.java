package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zzd;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzco {
    private static volatile boolean zznt = false;
    private static final Class<?> zznu = zzdj();
    private static volatile zzco zznv;
    static final zzco zznw = new zzco(true);
    private final Map<zza, zzd<?, ?>> zznx;

    static final class zza {
        private final int number;
        private final Object object;

        zza(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            return this.object == zza.object && this.number == zza.number;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * 65535) + this.number;
        }
    }

    zzco() {
        this.zznx = new HashMap();
    }

    private zzco(boolean z) {
        this.zznx = Collections.emptyMap();
    }

    static zzco zzdi() {
        return zzcz.zza(zzco.class);
    }

    private static Class<?> zzdj() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzco zzdk() {
        return zzcn.zzdh();
    }

    public static zzco zzdl() {
        zzco zzco = zznv;
        if (zzco == null) {
            synchronized (zzco.class) {
                zzco = zznv;
                if (zzco == null) {
                    zzco = zzcn.zzdi();
                    zznv = zzco;
                }
            }
        }
        return zzco;
    }

    public final <ContainingType extends zzeh> zzd<ContainingType, ?> zza(ContainingType containingType, int i) {
        return (zzd) this.zznx.get(new zza(containingType, i));
    }
}
