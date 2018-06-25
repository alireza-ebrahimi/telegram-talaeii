package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg.zzg;
import java.util.List;

public final class zzgw {

    public static final class zza extends zzcg<zza, zza> implements zzdq {
        private static volatile zzdz<zza> zzbg;
        private static final zza zzbir = new zza();
        private zzcn<zzb> zzbiq = zzcg.zzbb();

        public static final class zza extends com.google.android.gms.internal.clearcut.zzcg.zza<zza, zza> implements zzdq {
            private zza() {
                super(zza.zzbir);
            }
        }

        public static final class zzb extends zzcg<zzb, zza> implements zzdq {
            private static volatile zzdz<zzb> zzbg;
            private static final zzb zzbiv = new zzb();
            private int zzbb;
            private String zzbis = TtmlNode.ANONYMOUS_REGION_ID;
            private long zzbit;
            private long zzbiu;
            private int zzya;

            public static final class zza extends com.google.android.gms.internal.clearcut.zzcg.zza<zzb, zza> implements zzdq {
                private zza() {
                    super(zzb.zzbiv);
                }

                public final zza zzn(String str) {
                    zzbf();
                    ((zzb) this.zzjt).zzm(str);
                    return this;
                }

                public final zza zzr(long j) {
                    zzbf();
                    ((zzb) this.zzjt).zzp(j);
                    return this;
                }

                public final zza zzs(long j) {
                    zzbf();
                    ((zzb) this.zzjt).zzq(j);
                    return this;
                }
            }

            static {
                zzcg.zza(zzb.class, zzbiv);
            }

            private zzb() {
            }

            public static zza zzfz() {
                return (zza) ((com.google.android.gms.internal.clearcut.zzcg.zza) zzbiv.zza(zzg.zzkh, null, null));
            }

            private final void zzm(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.zzbb |= 2;
                this.zzbis = str;
            }

            private final void zzp(long j) {
                this.zzbb |= 4;
                this.zzbit = j;
            }

            private final void zzq(long j) {
                this.zzbb |= 8;
                this.zzbiu = j;
            }

            public final int getEventCode() {
                return this.zzya;
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzgx.zzba[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza();
                    case 3:
                        Object[] objArr = new Object[]{"zzbb", "zzya", "zzbis", "zzbit", "zzbiu"};
                        return zzcg.zza(zzbiv, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\u0004\u0000\u0002\b\u0001\u0003\u0002\u0002\u0004\u0002\u0003", objArr);
                    case 4:
                        return zzbiv;
                    case 5:
                        zzdz zzdz = zzbg;
                        if (zzdz != null) {
                            return zzdz;
                        }
                        Object obj3;
                        synchronized (zzb.class) {
                            obj3 = zzbg;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.clearcut.zzcg.zzb(zzbiv);
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

            public final boolean zzfv() {
                return (this.zzbb & 1) == 1;
            }

            public final String zzfw() {
                return this.zzbis;
            }

            public final long zzfx() {
                return this.zzbit;
            }

            public final long zzfy() {
                return this.zzbiu;
            }
        }

        static {
            zzcg.zza(zza.class, zzbir);
        }

        private zza() {
        }

        public static zza zzft() {
            return zzbir;
        }

        public static zza zzi(byte[] bArr) {
            return (zza) zzcg.zzb(zzbir, bArr);
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzgx.zzba[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    Object[] objArr = new Object[]{"zzbiq", zzb.class};
                    return zzcg.zza(zzbir, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0002\u0000\u0001\u0000\u0001\u001b", objArr);
                case 4:
                    return zzbir;
                case 5:
                    zzdz zzdz = zzbg;
                    if (zzdz != null) {
                        return zzdz;
                    }
                    Object obj3;
                    synchronized (zza.class) {
                        obj3 = zzbg;
                        if (obj3 == null) {
                            obj3 = new com.google.android.gms.internal.clearcut.zzcg.zzb(zzbir);
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

        public final List<zzb> zzfs() {
            return this.zzbiq;
        }
    }
}
