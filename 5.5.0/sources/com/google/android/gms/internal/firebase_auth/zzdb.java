package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzdb<MessageType extends zzdb<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzbn<MessageType, BuilderType> {
    private static Map<Object, zzdb<?, ?>> zzqz = new ConcurrentHashMap();
    protected zzfq zzqx = zzfq.zzfz();
    private int zzqy = -1;

    public static abstract class zza<MessageType extends zzdb<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzbo<MessageType, BuilderType> {
        private final MessageType zzra;
        private MessageType zzrb;
        private boolean zzrc = false;

        protected zza(MessageType messageType) {
            this.zzra = messageType;
            this.zzrb = (zzdb) messageType.zza(zze.zzrh, null, null);
        }

        private static void zza(MessageType messageType, MessageType messageType2) {
            zzes.zzfg().zzq(messageType).zzc(messageType, messageType2);
        }

        public /* synthetic */ Object clone() {
            zzdb zzdb;
            zza zza = (zza) this.zzra.zza(zze.zzri, null, null);
            if (this.zzrc) {
                zzdb = this.zzrb;
            } else {
                zzdb = this.zzrb;
                zzes.zzfg().zzq(zzdb).zze(zzdb);
                this.zzrc = true;
                zzdb = this.zzrb;
            }
            zza.zza(zzdb);
            return zza;
        }

        public final boolean isInitialized() {
            return zzdb.zza(this.zzrb, false);
        }

        protected final /* synthetic */ zzbo zza(zzbn zzbn) {
            return zza((zzdb) zzbn);
        }

        public final BuilderType zza(MessageType messageType) {
            if (this.zzrc) {
                zzdb zzdb = (zzdb) this.zzrb.zza(zze.zzrh, null, null);
                zza(zzdb, this.zzrb);
                this.zzrb = zzdb;
                this.zzrc = false;
            }
            zza(this.zzrb, messageType);
            return this;
        }

        public final /* synthetic */ zzbo zzbq() {
            return (zza) clone();
        }

        public final /* synthetic */ zzeh zzeb() {
            return this.zzra;
        }

        public final /* synthetic */ zzeh zzec() {
            if (this.zzrc) {
                return this.zzrb;
            }
            zzdb zzdb = this.zzrb;
            zzes.zzfg().zzq(zzdb).zze(zzdb);
            this.zzrc = true;
            return this.zzrb;
        }

        public final /* synthetic */ zzeh zzed() {
            zzdb zzdb;
            boolean z;
            if (this.zzrc) {
                zzdb = this.zzrb;
            } else {
                zzdb = this.zzrb;
                zzes.zzfg().zzq(zzdb).zze(zzdb);
                this.zzrc = true;
                zzdb = this.zzrb;
            }
            zzdb = zzdb;
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zzdb.zza(zze.zzre, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                z = true;
            } else if (byteValue == (byte) 0) {
                z = false;
            } else {
                boolean zzp = zzes.zzfg().zzq(zzdb).zzp(zzdb);
                if (booleanValue) {
                    zzdb.zza(zze.zzrf, zzp ? zzdb : null, null);
                }
                z = zzp;
            }
            if (z) {
                return zzdb;
            }
            throw new zzfo(zzdb);
        }
    }

    public static class zzb<T extends zzdb<T, ?>> extends zzbp<T> {
        private T zzra;

        public zzb(T t) {
            this.zzra = t;
        }

        public final /* synthetic */ Object zza(zzcd zzcd, zzco zzco) {
            return zzdb.zza(this.zzra, zzcd, zzco);
        }
    }

    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzdb<MessageType, BuilderType> implements zzej {
        protected zzcs<Object> zzrd = zzcs.zzdp();
    }

    public static class zzd<ContainingType extends zzeh, Type> extends zzcm<ContainingType, Type> {
    }

    public enum zze {
        public static final int zzre = 1;
        public static final int zzrf = 2;
        public static final int zzrg = 3;
        public static final int zzrh = 4;
        public static final int zzri = 5;
        public static final int zzrj = 6;
        public static final int zzrk = 7;
        private static final /* synthetic */ int[] zzrl = new int[]{zzre, zzrf, zzrg, zzrh, zzri, zzrj, zzrk};
        public static final int zzrm = 1;
        public static final int zzrn = 2;
        private static final /* synthetic */ int[] zzro = new int[]{zzrm, zzrn};
        public static final int zzrp = 1;
        public static final int zzrq = 2;
        private static final /* synthetic */ int[] zzrr = new int[]{zzrp, zzrq};

        /* renamed from: values$50KLMJ33DTMIUPRFDTJMOP9FE1P6UT3FC9QMCBQ7CLN6ASJ1EHIM8JB5EDPM2PR59HKN8P949LIN8Q3FCHA6UIBEEPNMMP9R0 */
        public static int[] m8466x126d66cb() {
            return (int[]) zzrl.clone();
        }
    }

    static <T extends zzdb<T, ?>> T zza(T t, zzcd zzcd, zzco zzco) {
        zzdb zzdb = (zzdb) t.zza(zze.zzrh, null, null);
        try {
            zzes.zzfg().zzq(zzdb).zza(zzdb, zzcg.zza(zzcd), zzco);
            zzes.zzfg().zzq(zzdb).zze(zzdb);
            return zzdb;
        } catch (IOException e) {
            if (e.getCause() instanceof zzdh) {
                throw ((zzdh) e.getCause());
            }
            throw new zzdh(e.getMessage()).zzg(zzdb);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzdh) {
                throw ((zzdh) e2.getCause());
            }
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

    protected static <T extends zzdb<?, ?>> void zza(Class<T> cls, T t) {
        zzqz.put(cls, t);
    }

    protected static final <T extends zzdb<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zze.zzre, null, null)).byteValue();
        return byteValue == (byte) 1 ? true : byteValue == (byte) 0 ? false : zzes.zzfg().zzq(t).zzp(t);
    }

    static <T extends zzdb<?, ?>> T zzd(Class<T> cls) {
        T t = (zzdb) zzqz.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzdb) zzqz.get(cls);
            } catch (Throwable e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t != null) {
            return t;
        }
        String str = "Unable to get default instance for: ";
        String valueOf = String.valueOf(cls.getName());
        throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    public boolean equals(Object obj) {
        return this == obj ? true : !((zzdb) zza(zze.zzrj, null, null)).getClass().isInstance(obj) ? false : zzes.zzfg().zzq(this).equals(this, (zzdb) obj);
    }

    public int hashCode() {
        if (this.zzma != 0) {
            return this.zzma;
        }
        this.zzma = zzes.zzfg().zzq(this).hashCode(this);
        return this.zzma;
    }

    public final boolean isInitialized() {
        boolean booleanValue = Boolean.TRUE.booleanValue();
        byte byteValue = ((Byte) zza(zze.zzre, null, null)).byteValue();
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        boolean zzp = zzes.zzfg().zzq(this).zzp(this);
        if (booleanValue) {
            zza(zze.zzrf, zzp ? this : null, null);
        }
        return zzp;
    }

    public String toString() {
        return zzek.zza(this, super.toString());
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public final void zzb(zzci zzci) {
        zzes.zzfg().zzf(getClass()).zza(this, zzck.zza(zzci));
    }

    final int zzbp() {
        return this.zzqy;
    }

    public final int zzdq() {
        if (this.zzqy == -1) {
            this.zzqy = zzes.zzfg().zzq(this).zzo(this);
        }
        return this.zzqy;
    }

    public final /* synthetic */ zzei zzdz() {
        zza zza = (zza) zza(zze.zzri, null, null);
        zza.zza(this);
        return zza;
    }

    public final /* synthetic */ zzei zzea() {
        return (zza) zza(zze.zzri, null, null);
    }

    public final /* synthetic */ zzeh zzeb() {
        return (zzdb) zza(zze.zzrj, null, null);
    }

    final void zzg(int i) {
        this.zzqy = i;
    }
}
