package com.google.android.gms.internal.clearcut;

public final class zzt {

    public static final class zza extends zzcg<zza, zza> implements zzdq {
        private static final zza zzbf = new zza();
        private static volatile zzdz<zza> zzbg;
        private int zzbb;
        private int zzbc;
        private int zzbd;
        private int zzbe;

        public static final class zza extends com.google.android.gms.internal.clearcut.zzcg.zza<zza, zza> implements zzdq {
            private zza() {
                super(zza.zzbf);
            }
        }

        public enum zzb implements zzcj {
            ARCH_UNKNOWN(0),
            ARCH_NON_NATIVE(1),
            ARCH_ARMV5(2),
            ARCH_ARMV7(4),
            ARCH_ARM64(5),
            ARCH_MIPS(6),
            ARCH_MIPS_64(7),
            ARCH_X86(8),
            ARCH_X86_64(9);
            
            private static final zzck<zzb> zzbq = null;
            private final int value;

            static {
                zzbq = new zzv();
            }

            private zzb(int i) {
                this.value = i;
            }

            public static zzb zza(int i) {
                switch (i) {
                    case 0:
                        return ARCH_UNKNOWN;
                    case 1:
                        return ARCH_NON_NATIVE;
                    case 2:
                        return ARCH_ARMV5;
                    case 4:
                        return ARCH_ARMV7;
                    case 5:
                        return ARCH_ARM64;
                    case 6:
                        return ARCH_MIPS;
                    case 7:
                        return ARCH_MIPS_64;
                    case 8:
                        return ARCH_X86;
                    case 9:
                        return ARCH_X86_64;
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

        public enum zzc implements zzcj {
            BUILD_TYPE_UNKNOWN(0),
            BUILD_TYPE_PROD(1),
            BUILD_TYPE_INTERNAL(2),
            BUILD_TYPE_PRODLMP(3),
            BUILD_TYPE_THINGS(4),
            BUILD_TYPE_PRODMNC(5),
            BUILD_TYPE_WEARABLE(6),
            BUILD_TYPE_AUTO(7),
            BUILD_TYPE_SIDEWINDERMNC(8),
            BUILD_TYPE_ATV(9),
            BUILD_TYPE_PRODPIX(10),
            BUILD_TYPE_PRODPI(11);
            
            private static final zzck<zzc> zzbq = null;
            private final int value;

            static {
                zzbq = new zzw();
            }

            private zzc(int i) {
                this.value = i;
            }

            public static zzc zzc(int i) {
                switch (i) {
                    case 0:
                        return BUILD_TYPE_UNKNOWN;
                    case 1:
                        return BUILD_TYPE_PROD;
                    case 2:
                        return BUILD_TYPE_INTERNAL;
                    case 3:
                        return BUILD_TYPE_PRODLMP;
                    case 4:
                        return BUILD_TYPE_THINGS;
                    case 5:
                        return BUILD_TYPE_PRODMNC;
                    case 6:
                        return BUILD_TYPE_WEARABLE;
                    case 7:
                        return BUILD_TYPE_AUTO;
                    case 8:
                        return BUILD_TYPE_SIDEWINDERMNC;
                    case 9:
                        return BUILD_TYPE_ATV;
                    case 10:
                        return BUILD_TYPE_PRODPIX;
                    case 11:
                        return BUILD_TYPE_PRODPI;
                    default:
                        return null;
                }
            }

            public static zzck<zzc> zzd() {
                return zzbq;
            }

            public final int zzc() {
                return this.value;
            }
        }

        public enum zzd implements zzcj {
            DENSITY_UNKNOWN(0),
            DENSITY_ALLDPI(1),
            DENSITY_LDPI(2),
            DENSITY_MDPI(3),
            DENSITY_TVDPI(4),
            DENSITY_HDPI(5),
            DENSITY_XHDPI(7),
            DENSITY_DPI400(8),
            DENSITY_XXHDPI(9),
            DENSITY_XXXHDPI(10);
            
            private static final zzck<zzd> zzbq = null;
            private final int value;

            static {
                zzbq = new zzx();
            }

            private zzd(int i) {
                this.value = i;
            }

            public static zzck<zzd> zzd() {
                return zzbq;
            }

            public static zzd zzd(int i) {
                switch (i) {
                    case 0:
                        return DENSITY_UNKNOWN;
                    case 1:
                        return DENSITY_ALLDPI;
                    case 2:
                        return DENSITY_LDPI;
                    case 3:
                        return DENSITY_MDPI;
                    case 4:
                        return DENSITY_TVDPI;
                    case 5:
                        return DENSITY_HDPI;
                    case 7:
                        return DENSITY_XHDPI;
                    case 8:
                        return DENSITY_DPI400;
                    case 9:
                        return DENSITY_XXHDPI;
                    case 10:
                        return DENSITY_XXXHDPI;
                    default:
                        return null;
                }
            }

            public final int zzc() {
                return this.value;
            }
        }

        static {
            zzcg.zza(zza.class, zzbf);
        }

        private zza() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzu.zzba[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    Object[] objArr = new Object[]{"zzbb", "zzbc", zzc.zzd(), "zzbd", zzb.zzd(), "zzbe", zzd.zzd()};
                    return zzcg.zza(zzbf, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001\f\u0000\u0002\f\u0001\u0003\f\u0002", objArr);
                case 4:
                    return zzbf;
                case 5:
                    zzdz zzdz = zzbg;
                    if (zzdz != null) {
                        return zzdz;
                    }
                    Object obj3;
                    synchronized (zza.class) {
                        obj3 = zzbg;
                        if (obj3 == null) {
                            obj3 = new com.google.android.gms.internal.clearcut.zzcg.zzb(zzbf);
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
