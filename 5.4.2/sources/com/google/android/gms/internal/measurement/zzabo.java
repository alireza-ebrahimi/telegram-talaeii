package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zzabo {
    private static final Logger logger = Logger.getLogger(zzabo.class.getName());
    private static final Class<?> zzbrf = zzyx.zztf();
    private static final Unsafe zzbtt = zzva();
    private static final boolean zzbux = zzk(Long.TYPE);
    private static final boolean zzbuy = zzk(Integer.TYPE);
    private static final zzd zzbuz;
    private static final boolean zzbva = zzvc();
    private static final boolean zzbvb = zzvb();
    private static final long zzbvc = ((long) zzi(byte[].class));
    private static final long zzbvd = ((long) zzi(boolean[].class));
    private static final long zzbve = ((long) zzj(boolean[].class));
    private static final long zzbvf = ((long) zzi(int[].class));
    private static final long zzbvg = ((long) zzj(int[].class));
    private static final long zzbvh = ((long) zzi(long[].class));
    private static final long zzbvi = ((long) zzj(long[].class));
    private static final long zzbvj = ((long) zzi(float[].class));
    private static final long zzbvk = ((long) zzj(float[].class));
    private static final long zzbvl = ((long) zzi(double[].class));
    private static final long zzbvm = ((long) zzj(double[].class));
    private static final long zzbvn = ((long) zzi(Object[].class));
    private static final long zzbvo = ((long) zzj(Object[].class));
    private static final long zzbvp = zza(zzvd());
    private static final long zzbvq;
    private static final boolean zzbvr = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);

    static abstract class zzd {
        Unsafe zzbvs;

        zzd(Unsafe unsafe) {
            this.zzbvs = unsafe;
        }
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }
    }

    static {
        Field field = null;
        zzd zzb = zzbtt == null ? null : zzyx.zzte() ? zzbux ? new zzb(zzbtt) : zzbuy ? new zza(zzbtt) : null : new zzc(zzbtt);
        zzbuz = zzb;
        Field zza = zza(String.class, C1797b.VALUE);
        if (zza != null && zza.getType() == char[].class) {
            field = zza;
        }
        zzbvq = zza(field);
    }

    private zzabo() {
    }

    private static long zza(Field field) {
        return (field == null || zzbuz == null) ? -1 : zzbuz.zzbvs.objectFieldOffset(field);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable th) {
            return null;
        }
    }

    private static int zzi(Class<?> cls) {
        return zzbvb ? zzbuz.zzbvs.arrayBaseOffset(cls) : -1;
    }

    private static int zzj(Class<?> cls) {
        return zzbvb ? zzbuz.zzbvs.arrayIndexScale(cls) : -1;
    }

    private static boolean zzk(Class<?> cls) {
        if (!zzyx.zzte()) {
            return false;
        }
        try {
            Class cls2 = zzbrf;
            cls2.getMethod("peekLong", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeLong", new Class[]{cls, Long.TYPE, Boolean.TYPE});
            cls2.getMethod("pokeInt", new Class[]{cls, Integer.TYPE, Boolean.TYPE});
            cls2.getMethod("peekInt", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls2.getMethod("peekByte", new Class[]{cls});
            cls2.getMethod("pokeByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            cls2.getMethod("peekByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    static Unsafe zzva() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzabp());
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean zzvb() {
        if (zzbtt == null) {
            return false;
        }
        try {
            Class cls = zzbtt.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzyx.zzte()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", new StringBuilder(String.valueOf(valueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(valueOf).toString());
            return false;
        }
    }

    private static boolean zzvc() {
        if (zzbtt == null) {
            return false;
        }
        try {
            Class cls = zzbtt.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (zzvd() == null) {
                return false;
            }
            if (zzyx.zzte()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", new StringBuilder(String.valueOf(valueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(valueOf).toString());
            return false;
        }
    }

    private static Field zzvd() {
        Field zza;
        if (zzyx.zzte()) {
            zza = zza(Buffer.class, "effectiveDirectAddress");
            if (zza != null) {
                return zza;
            }
        }
        zza = zza(Buffer.class, "address");
        return (zza == null || zza.getType() != Long.TYPE) ? null : zza;
    }
}
