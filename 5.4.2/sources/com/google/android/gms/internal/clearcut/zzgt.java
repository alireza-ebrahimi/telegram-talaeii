package com.google.android.gms.internal.clearcut;

public final class zzgt {

    public static final class zza extends zzcg<zza, zza> implements zzdq {
        private static volatile zzdz<zza> zzbg;
        private static final zza zzbil = new zza();

        public static final class zza extends com.google.android.gms.internal.clearcut.zzcg.zza<zza, zza> implements zzdq {
            private zza() {
                super(zza.zzbil);
            }
        }

        public enum zzb implements zzcj {
            NO_RESTRICTION(0),
            SIDEWINDER_DEVICE(1),
            LATCHSKY_DEVICE(2);
            
            private static final zzck<zzb> zzbq = null;
            private final int value;

            static {
                zzbq = new zzgv();
            }

            private zzb(int i) {
                this.value = i;
            }

            public static zzb zzbe(int i) {
                switch (i) {
                    case 0:
                        return NO_RESTRICTION;
                    case 1:
                        return SIDEWINDER_DEVICE;
                    case 2:
                        return LATCHSKY_DEVICE;
                    default:
                        return null;
                }
            }

            public static zzck<zzb> zzd() {
                return zzbq;
            }

            public final int zzc() {
                return this.value;
            }
        }

        static {
            zzcg.zza(zza.class, zzbil);
        }

        private zza() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzgu.zzba[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    return zzcg.zza(zzbil, "\u0001\u0000", null);
                case 4:
                    return zzbil;
                case 5:
                    zzdz zzdz = zzbg;
                    if (zzdz != null) {
                        return zzdz;
                    }
                    Object obj3;
                    synchronized (zza.class) {
                        obj3 = zzbg;
                        if (obj3 == null) {
                            obj3 = new com.google.android.gms.internal.clearcut.zzcg.zzb(zzbil);
                            zzbg = obj3;
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf((byte) 1);
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
