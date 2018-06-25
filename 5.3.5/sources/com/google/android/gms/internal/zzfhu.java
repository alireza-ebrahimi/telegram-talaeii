package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzfhu<MessageType extends zzfhu<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfgj<MessageType, BuilderType> {
    private static Map<Object, zzfhu<?, ?>> zzppj = new ConcurrentHashMap();
    protected zzfko zzpph = zzfko.zzdca();
    protected int zzppi = -1;

    public static abstract class zza<MessageType extends zzfhu<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfgk<MessageType, BuilderType> {
        private final MessageType zzppk;
        protected MessageType zzppl;
        private boolean zzppm = false;

        protected zza(MessageType messageType) {
            this.zzppk = messageType;
            this.zzppl = (zzfhu) messageType.zza(zzg.zzppx, null, null);
        }

        private static void zza(MessageType messageType, MessageType messageType2) {
            Object obj = zzf.zzppq;
            messageType.zza(zzg.zzpps, obj, (Object) messageType2);
            messageType.zzpph = obj.zza(messageType.zzpph, messageType2.zzpph);
        }

        private final BuilderType zzd(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
            zzczv();
            try {
                this.zzppl.zza(zzg.zzppv, (Object) zzfhb, (Object) zzfhm);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zzfhu zzfhu;
            zza zza = (zza) this.zzppk.zza(zzg.zzppy, null, null);
            if (this.zzppm) {
                zzfhu = this.zzppl;
            } else {
                zzfhu = this.zzppl;
                zzfhu.zza(zzg.zzppw, null, null);
                zzfhu.zzpph.zzbkr();
                this.zzppm = true;
                zzfhu = this.zzppl;
            }
            zza.zza(zzfhu);
            return zza;
        }

        public final boolean isInitialized() {
            return zzfhu.zza(this.zzppl, false);
        }

        protected final /* synthetic */ zzfgk zza(zzfgj zzfgj) {
            return zza((zzfhu) zzfgj);
        }

        public final /* synthetic */ zzfgk zza(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
            return (zza) zzb(zzfhb, zzfhm);
        }

        public final BuilderType zza(MessageType messageType) {
            zzczv();
            zza(this.zzppl, (zzfhu) messageType);
            return this;
        }

        public final /* synthetic */ zzfjd zzb(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
            return zzd(zzfhb, zzfhm);
        }

        public final /* synthetic */ zzfgk zzcxj() {
            return (zza) clone();
        }

        public final /* synthetic */ zzfjc zzczu() {
            return this.zzppk;
        }

        protected final void zzczv() {
            if (this.zzppm) {
                zzfhu zzfhu = (zzfhu) this.zzppl.zza(zzg.zzppx, null, null);
                zza(zzfhu, this.zzppl);
                this.zzppl = zzfhu;
                this.zzppm = false;
            }
        }

        public final MessageType zzczw() {
            if (this.zzppm) {
                return this.zzppl;
            }
            zzfhu zzfhu = this.zzppl;
            zzfhu.zza(zzg.zzppw, null, null);
            zzfhu.zzpph.zzbkr();
            this.zzppm = true;
            return this.zzppl;
        }

        public final MessageType zzczx() {
            zzfhu zzfhu;
            boolean z;
            boolean z2 = true;
            if (this.zzppm) {
                zzfhu = this.zzppl;
            } else {
                zzfhu = this.zzppl;
                zzfhu.zza(zzg.zzppw, null, null);
                zzfhu.zzpph.zzbkr();
                this.zzppm = true;
                zzfhu = this.zzppl;
            }
            zzfhu = zzfhu;
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zzfhu.zza(zzg.zzppt, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                z = true;
            } else if (byteValue == (byte) 0) {
                z = false;
            } else {
                if (zzfhu.zza(zzg.zzppr, Boolean.FALSE, null) == null) {
                    z2 = false;
                }
                if (booleanValue) {
                    zzfhu.zza(zzg.zzppu, z2 ? zzfhu : null, null);
                }
                z = z2;
            }
            if (z) {
                return zzfhu;
            }
            throw new zzfkm(zzfhu);
        }

        public final /* synthetic */ zzfjc zzczy() {
            if (this.zzppm) {
                return this.zzppl;
            }
            zzfhu zzfhu = this.zzppl;
            zzfhu.zza(zzg.zzppw, null, null);
            zzfhu.zzpph.zzbkr();
            this.zzppm = true;
            return this.zzppl;
        }

        public final /* synthetic */ zzfjc zzczz() {
            zzfhu zzfhu;
            boolean z;
            boolean z2 = true;
            if (this.zzppm) {
                zzfhu = this.zzppl;
            } else {
                zzfhu = this.zzppl;
                zzfhu.zza(zzg.zzppw, null, null);
                zzfhu.zzpph.zzbkr();
                this.zzppm = true;
                zzfhu = this.zzppl;
            }
            zzfhu = zzfhu;
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zzfhu.zza(zzg.zzppt, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                z = true;
            } else if (byteValue == (byte) 0) {
                z = false;
            } else {
                if (zzfhu.zza(zzg.zzppr, Boolean.FALSE, null) == null) {
                    z2 = false;
                }
                if (booleanValue) {
                    zzfhu.zza(zzg.zzppu, z2 ? zzfhu : null, null);
                }
                z = z2;
            }
            if (z) {
                return zzfhu;
            }
            throw new zzfkm(zzfhu);
        }
    }

    public static class zzb<T extends zzfhu<T, ?>> extends zzfgm<T> {
        private T zzppk;

        public zzb(T t) {
            this.zzppk = t;
        }

        public final /* synthetic */ Object zze(zzfhb zzfhb, zzfhm zzfhm) throws zzfie {
            return zzfhu.zza(this.zzppk, zzfhb, zzfhm);
        }
    }

    public interface zzh {
        double zza(boolean z, double d, boolean z2, double d2);

        int zza(boolean z, int i, boolean z2, int i2);

        long zza(boolean z, long j, boolean z2, long j2);

        zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2);

        zzfic zza(zzfic zzfic, zzfic zzfic2);

        <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2);

        <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2);

        <T extends zzfjc> T zza(T t, T t2);

        zzfko zza(zzfko zzfko, zzfko zzfko2);

        Object zza(boolean z, Object obj, Object obj2);

        String zza(boolean z, String str, boolean z2, String str2);

        boolean zza(boolean z, boolean z2, boolean z3, boolean z4);

        Object zzb(boolean z, Object obj, Object obj2);

        Object zzc(boolean z, Object obj, Object obj2);

        Object zzd(boolean z, Object obj, Object obj2);

        void zzdn(boolean z);

        Object zze(boolean z, Object obj, Object obj2);

        Object zzf(boolean z, Object obj, Object obj2);

        Object zzg(boolean z, Object obj, Object obj2);
    }

    static class zzc implements zzh {
        static final zzc zzppn = new zzc();
        private static zzfhv zzppo = new zzfhv();

        private zzc() {
        }

        public final double zza(boolean z, double d, boolean z2, double d2) {
            if (z == z2 && d == d2) {
                return d;
            }
            throw zzppo;
        }

        public final int zza(boolean z, int i, boolean z2, int i2) {
            if (z == z2 && i == i2) {
                return i;
            }
            throw zzppo;
        }

        public final long zza(boolean z, long j, boolean z2, long j2) {
            if (z == z2 && j == j2) {
                return j;
            }
            throw zzppo;
        }

        public final zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2) {
            if (z == z2 && zzfgs.equals(zzfgs2)) {
                return zzfgs;
            }
            throw zzppo;
        }

        public final zzfic zza(zzfic zzfic, zzfic zzfic2) {
            if (zzfic.equals(zzfic2)) {
                return zzfic;
            }
            throw zzppo;
        }

        public final <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2) {
            if (zzfid.equals(zzfid2)) {
                return zzfid;
            }
            throw zzppo;
        }

        public final <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2) {
            if (zzfiw.equals(zzfiw2)) {
                return zzfiw;
            }
            throw zzppo;
        }

        public final <T extends zzfjc> T zza(T t, T t2) {
            if (t == null && t2 == null) {
                return null;
            }
            if (t == null || t2 == null) {
                throw zzppo;
            }
            T t3 = (zzfhu) t;
            if (t3 == t2 || !((zzfhu) t3.zza(zzg.zzppz, null, null)).getClass().isInstance(t2)) {
                return t;
            }
            Object obj = (zzfhu) t2;
            t3.zza(zzg.zzpps, (Object) this, obj);
            t3.zzpph = zza(t3.zzpph, obj.zzpph);
            return t;
        }

        public final zzfko zza(zzfko zzfko, zzfko zzfko2) {
            if (zzfko.equals(zzfko2)) {
                return zzfko;
            }
            throw zzppo;
        }

        public final Object zza(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzppo;
        }

        public final String zza(boolean z, String str, boolean z2, String str2) {
            if (z == z2 && str.equals(str2)) {
                return str;
            }
            throw zzppo;
        }

        public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
            if (z == z3 && z2 == z4) {
                return z2;
            }
            throw zzppo;
        }

        public final Object zzb(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzppo;
        }

        public final Object zzc(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzppo;
        }

        public final Object zzd(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzppo;
        }

        public final void zzdn(boolean z) {
            if (z) {
                throw zzppo;
            }
        }

        public final Object zze(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzppo;
        }

        public final Object zzf(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzppo;
        }

        public final Object zzg(boolean z, Object obj, Object obj2) {
            if (z) {
                Object obj3;
                zzfhu zzfhu = (zzfhu) obj;
                obj2 = (zzfjc) obj2;
                if (zzfhu == obj2) {
                    obj3 = 1;
                } else if (((zzfhu) zzfhu.zza(zzg.zzppz, null, null)).getClass().isInstance(obj2)) {
                    obj2 = (zzfhu) obj2;
                    zzfhu.zza(zzg.zzpps, (Object) this, obj2);
                    zzfhu.zzpph = zza(zzfhu.zzpph, obj2.zzpph);
                    int i = 1;
                } else {
                    obj3 = null;
                }
                if (obj3 != null) {
                    return obj;
                }
            }
            throw zzppo;
        }
    }

    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType> extends zzfhu<MessageType, BuilderType> implements zzfje {
        protected zzfhq<Object> zzppp = zzfhq.zzczj();
    }

    static class zze implements zzh {
        int zzmci = 0;

        zze() {
        }

        public final double zza(boolean z, double d, boolean z2, double d2) {
            this.zzmci = (this.zzmci * 53) + zzfhz.zzdf(Double.doubleToLongBits(d));
            return d;
        }

        public final int zza(boolean z, int i, boolean z2, int i2) {
            this.zzmci = (this.zzmci * 53) + i;
            return i;
        }

        public final long zza(boolean z, long j, boolean z2, long j2) {
            this.zzmci = (this.zzmci * 53) + zzfhz.zzdf(j);
            return j;
        }

        public final zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2) {
            this.zzmci = (this.zzmci * 53) + zzfgs.hashCode();
            return zzfgs;
        }

        public final zzfic zza(zzfic zzfic, zzfic zzfic2) {
            this.zzmci = (this.zzmci * 53) + zzfic.hashCode();
            return zzfic;
        }

        public final <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2) {
            this.zzmci = (this.zzmci * 53) + zzfid.hashCode();
            return zzfid;
        }

        public final <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2) {
            this.zzmci = (this.zzmci * 53) + zzfiw.hashCode();
            return zzfiw;
        }

        public final <T extends zzfjc> T zza(T t, T t2) {
            int i;
            if (t == null) {
                i = 37;
            } else if (t instanceof zzfhu) {
                Object obj = (zzfhu) t;
                if (obj.zzpno == 0) {
                    int i2 = this.zzmci;
                    this.zzmci = 0;
                    obj.zza(zzg.zzpps, (Object) this, obj);
                    obj.zzpph = zza(obj.zzpph, obj.zzpph);
                    obj.zzpno = this.zzmci;
                    this.zzmci = i2;
                }
                i = obj.zzpno;
            } else {
                i = t.hashCode();
            }
            this.zzmci = i + (this.zzmci * 53);
            return t;
        }

        public final zzfko zza(zzfko zzfko, zzfko zzfko2) {
            this.zzmci = (this.zzmci * 53) + zzfko.hashCode();
            return zzfko;
        }

        public final Object zza(boolean z, Object obj, Object obj2) {
            this.zzmci = zzfhz.zzdo(((Boolean) obj).booleanValue()) + (this.zzmci * 53);
            return obj;
        }

        public final String zza(boolean z, String str, boolean z2, String str2) {
            this.zzmci = (this.zzmci * 53) + str.hashCode();
            return str;
        }

        public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
            this.zzmci = (this.zzmci * 53) + zzfhz.zzdo(z2);
            return z2;
        }

        public final Object zzb(boolean z, Object obj, Object obj2) {
            this.zzmci = ((Integer) obj).intValue() + (this.zzmci * 53);
            return obj;
        }

        public final Object zzc(boolean z, Object obj, Object obj2) {
            this.zzmci = zzfhz.zzdf(Double.doubleToLongBits(((Double) obj).doubleValue())) + (this.zzmci * 53);
            return obj;
        }

        public final Object zzd(boolean z, Object obj, Object obj2) {
            this.zzmci = zzfhz.zzdf(((Long) obj).longValue()) + (this.zzmci * 53);
            return obj;
        }

        public final void zzdn(boolean z) {
            if (z) {
                throw new IllegalStateException();
            }
        }

        public final Object zze(boolean z, Object obj, Object obj2) {
            this.zzmci = (this.zzmci * 53) + obj.hashCode();
            return obj;
        }

        public final Object zzf(boolean z, Object obj, Object obj2) {
            this.zzmci = (this.zzmci * 53) + obj.hashCode();
            return obj;
        }

        public final Object zzg(boolean z, Object obj, Object obj2) {
            return zza((zzfjc) obj, (zzfjc) obj2);
        }
    }

    public static class zzf implements zzh {
        public static final zzf zzppq = new zzf();

        private zzf() {
        }

        public final double zza(boolean z, double d, boolean z2, double d2) {
            return z2 ? d2 : d;
        }

        public final int zza(boolean z, int i, boolean z2, int i2) {
            return z2 ? i2 : i;
        }

        public final long zza(boolean z, long j, boolean z2, long j2) {
            return z2 ? j2 : j;
        }

        public final zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2) {
            return z2 ? zzfgs2 : zzfgs;
        }

        public final zzfic zza(zzfic zzfic, zzfic zzfic2) {
            int size = zzfic.size();
            int size2 = zzfic2.size();
            if (size > 0 && size2 > 0) {
                if (!zzfic.zzcxk()) {
                    zzfic = zzfic.zzmk(size2 + size);
                }
                zzfic.addAll(zzfic2);
            }
            return size > 0 ? zzfic : zzfic2;
        }

        public final <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2) {
            int size = zzfid.size();
            int size2 = zzfid2.size();
            if (size > 0 && size2 > 0) {
                if (!zzfid.zzcxk()) {
                    zzfid = zzfid.zzmo(size2 + size);
                }
                zzfid.addAll(zzfid2);
            }
            return size > 0 ? zzfid : zzfid2;
        }

        public final <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2) {
            if (!zzfiw2.isEmpty()) {
                if (!zzfiw.isMutable()) {
                    zzfiw = zzfiw.zzdau();
                }
                zzfiw.zza(zzfiw2);
            }
            return zzfiw;
        }

        public final <T extends zzfjc> T zza(T t, T t2) {
            return (t == null || t2 == null) ? t == null ? t2 : t : t.zzczt().zzd(t2).zzczz();
        }

        public final zzfko zza(zzfko zzfko, zzfko zzfko2) {
            return zzfko2 == zzfko.zzdca() ? zzfko : zzfko.zzb(zzfko, zzfko2);
        }

        public final Object zza(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final String zza(boolean z, String str, boolean z2, String str2) {
            return z2 ? str2 : str;
        }

        public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
            return z3 ? z4 : z2;
        }

        public final Object zzb(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzc(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzd(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final void zzdn(boolean z) {
        }

        public final Object zze(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzf(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzg(boolean z, Object obj, Object obj2) {
            return z ? zza((zzfjc) obj, (zzfjc) obj2) : obj2;
        }
    }

    public enum zzg {
        public static final int zzppr = 1;
        public static final int zzpps = 2;
        public static final int zzppt = 3;
        public static final int zzppu = 4;
        public static final int zzppv = 5;
        public static final int zzppw = 6;
        public static final int zzppx = 7;
        public static final int zzppy = 8;
        public static final int zzppz = 9;
        public static final int zzpqa = 10;
        private static final /* synthetic */ int[] zzpqb = new int[]{zzppr, zzpps, zzppt, zzppu, zzppv, zzppw, zzppx, zzppy, zzppz, zzpqa};
        public static final int zzpqc = 1;
        private static int zzpqd = 2;
        private static final /* synthetic */ int[] zzpqe = new int[]{zzpqc, zzpqd};
        public static final int zzpqf = 1;
        public static final int zzpqg = 2;
        private static final /* synthetic */ int[] zzpqh = new int[]{zzpqf, zzpqg};

        /* renamed from: values$50KLMJ33DTMIUPRFDTJMOP9FE1P6UT3FC9QMCBQ7CLN6ASJ1EHIM8JB5EDPM2PR59HKN8P949LIN8Q3FCHA6UIBEEPNMMP9R0 */
        public static int[] m19x126d66cb() {
            return (int[]) zzpqb.clone();
        }
    }

    protected static <T extends zzfhu<T, ?>> T zza(T t, zzfgs zzfgs) throws zzfie {
        byte byteValue;
        Object obj;
        Object obj2 = 1;
        T zza = zza((zzfhu) t, zzfgs, zzfhm.zzczf());
        if (zza != null) {
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byteValue = ((Byte) zza.zza(zzg.zzppt, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                obj = 1;
            } else if (byteValue == (byte) 0) {
                obj = null;
            } else {
                Object obj3 = zza.zza(zzg.zzppr, Boolean.FALSE, null) != null ? 1 : null;
                if (booleanValue) {
                    zza.zza(zzg.zzppu, obj3 != null ? zza : null, null);
                }
                obj = obj3;
            }
            if (obj == null) {
                throw new zzfkm(zza).zzdbz().zzi(zza);
            }
        }
        if (zza != null) {
            boolean booleanValue2 = Boolean.TRUE.booleanValue();
            byteValue = ((Byte) zza.zza(zzg.zzppt, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                obj = 1;
            } else if (byteValue == (byte) 0) {
                obj = null;
            } else {
                if (zza.zza(zzg.zzppr, Boolean.FALSE, null) == null) {
                    obj2 = null;
                }
                if (booleanValue2) {
                    zza.zza(zzg.zzppu, obj2 != null ? zza : null, null);
                }
                obj = obj2;
            }
            if (obj == null) {
                throw new zzfkm(zza).zzdbz().zzi(zza);
            }
        }
        return zza;
    }

    private static <T extends zzfhu<T, ?>> T zza(T t, zzfgs zzfgs, zzfhm zzfhm) throws zzfie {
        T zza;
        try {
            zzfhb zzcxq = zzfgs.zzcxq();
            zza = zza((zzfhu) t, zzcxq, zzfhm);
            zzcxq.zzlf(0);
            return zza;
        } catch (zzfie e) {
            throw e.zzi(zza);
        } catch (zzfie e2) {
            throw e2;
        }
    }

    static <T extends zzfhu<T, ?>> T zza(T t, zzfhb zzfhb, zzfhm zzfhm) throws zzfie {
        zzfhu zzfhu = (zzfhu) t.zza(zzg.zzppx, null, null);
        try {
            zzfhu.zza(zzg.zzppv, (Object) zzfhb, (Object) zzfhm);
            zzfhu.zza(zzg.zzppw, null, null);
            zzfhu.zzpph.zzbkr();
            return zzfhu;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof zzfie) {
                throw ((zzfie) e.getCause());
            }
            throw e;
        }
    }

    protected static <T extends zzfhu<T, ?>> T zza(T t, byte[] bArr) throws zzfie {
        Object obj = 1;
        T zza = zza((zzfhu) t, bArr, zzfhm.zzczf());
        if (zza != null) {
            Object obj2;
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zza.zza(zzg.zzppt, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                obj2 = 1;
            } else if (byteValue == (byte) 0) {
                obj2 = null;
            } else {
                if (zza.zza(zzg.zzppr, Boolean.FALSE, null) == null) {
                    obj = null;
                }
                if (booleanValue) {
                    zza.zza(zzg.zzppu, obj != null ? zza : null, null);
                }
                obj2 = obj;
            }
            if (obj2 == null) {
                throw new zzfkm(zza).zzdbz().zzi(zza);
            }
        }
        return zza;
    }

    private static <T extends zzfhu<T, ?>> T zza(T t, byte[] bArr, zzfhm zzfhm) throws zzfie {
        T zza;
        try {
            zzfhb zzbb = zzfhb.zzbb(bArr);
            zza = zza((zzfhu) t, zzbb, zzfhm);
            zzbb.zzlf(0);
            return zza;
        } catch (zzfie e) {
            throw e.zzi(zza);
        } catch (zzfie e2) {
            throw e2;
        }
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        Throwable e;
        try {
            return method.invoke(obj, objArr);
        } catch (Throwable e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            e2 = e3.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            } else if (e2 instanceof Error) {
                throw ((Error) e2);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", e2);
            }
        }
    }

    protected static final <T extends zzfhu<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzg.zzppt, null, null)).byteValue();
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        return t.zza(zzg.zzppr, Boolean.FALSE, null) != null;
    }

    protected static zzfic zzczr() {
        return zzfhy.zzdad();
    }

    protected static <E> zzfid<E> zzczs() {
        return zzfjo.zzdbg();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzfhu) zza(zzg.zzppz, null, null)).getClass().isInstance(obj)) {
            return false;
        }
        try {
            Object obj2 = zzc.zzppn;
            obj = (zzfhu) obj;
            zza(zzg.zzpps, obj2, obj);
            this.zzpph = obj2.zza(this.zzpph, obj.zzpph);
            return true;
        } catch (zzfhv e) {
            return false;
        }
    }

    public int hashCode() {
        if (this.zzpno != 0) {
            return this.zzpno;
        }
        Object zze = new zze();
        zza(zzg.zzpps, zze, (Object) this);
        this.zzpph = zze.zza(this.zzpph, this.zzpph);
        this.zzpno = zze.zzmci;
        return this.zzpno;
    }

    public final boolean isInitialized() {
        boolean z = true;
        boolean booleanValue = Boolean.TRUE.booleanValue();
        byte byteValue = ((Byte) zza(zzg.zzppt, null, null)).byteValue();
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        if (zza(zzg.zzppr, Boolean.FALSE, null) == null) {
            z = false;
        }
        if (booleanValue) {
            zza(zzg.zzppu, z ? this : null, null);
        }
        return z;
    }

    public String toString() {
        return zzfjf.zza(this, super.toString());
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public void zza(zzfhg zzfhg) throws IOException {
        zzfjn.zzdbf().zzl(getClass()).zza(this, zzfhi.zzb(zzfhg));
    }

    protected final boolean zza(int i, zzfhb zzfhb) throws IOException {
        if ((i & 7) == 4) {
            return false;
        }
        if (this.zzpph == zzfko.zzdca()) {
            this.zzpph = zzfko.zzdcb();
        }
        return this.zzpph.zzb(i, zzfhb);
    }

    public final zzfjl<MessageType> zzczq() {
        return (zzfjl) zza(zzg.zzpqa, null, null);
    }

    public final /* synthetic */ zzfjd zzczt() {
        zza zza = (zza) zza(zzg.zzppy, null, null);
        zza.zza(this);
        return zza;
    }

    public final /* synthetic */ zzfjc zzczu() {
        return (zzfhu) zza(zzg.zzppz, null, null);
    }

    public int zzhs() {
        if (this.zzppi == -1) {
            this.zzppi = zzfjn.zzdbf().zzl(getClass()).zzct(this);
        }
        return this.zzppi;
    }
}
