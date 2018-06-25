package com.google.android.gms.internal.firebase_auth;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import libcore.io.Memory;
import sun.misc.Unsafe;

final class zzfv {
    private static final Logger logger = Logger.getLogger(zzfv.class.getName());
    private static final Class<?> zzme = zzbr.zzbv();
    private static final boolean zznf = zzgf();
    private static final Unsafe zztj = zzge();
    private static final boolean zzvj = zzj(Long.TYPE);
    private static final boolean zzvk = zzj(Integer.TYPE);
    private static final zzd zzvl;
    private static final boolean zzvm = zzgg();
    private static final long zzvn = ((long) zzh(byte[].class));
    private static final long zzvo = ((long) zzh(boolean[].class));
    private static final long zzvp = ((long) zzi(boolean[].class));
    private static final long zzvq = ((long) zzh(int[].class));
    private static final long zzvr = ((long) zzi(int[].class));
    private static final long zzvs = ((long) zzh(long[].class));
    private static final long zzvt = ((long) zzi(long[].class));
    private static final long zzvu = ((long) zzh(float[].class));
    private static final long zzvv = ((long) zzi(float[].class));
    private static final long zzvw = ((long) zzh(double[].class));
    private static final long zzvx = ((long) zzi(double[].class));
    private static final long zzvy = ((long) zzh(Object[].class));
    private static final long zzvz = ((long) zzi(Object[].class));
    private static final long zzwa = zza(zzgh());
    private static final long zzwb;
    private static final boolean zzwc = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);

    static abstract class zzd {
        Unsafe zzwd;

        zzd(Unsafe unsafe) {
            this.zzwd = unsafe;
        }

        public abstract void zza(long j, byte b);

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public final void zza(Object obj, long j, long j2) {
            this.zzwd.putLong(obj, j, j2);
        }

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zza(byte[] bArr, long j, long j2, long j3);

        public final void zzb(Object obj, long j, int i) {
            this.zzwd.putInt(obj, j, i);
        }

        public abstract void zze(Object obj, long j, byte b);

        public final int zzk(Object obj, long j) {
            return this.zzwd.getInt(obj, j);
        }

        public final long zzl(Object obj, long j) {
            return this.zzwd.getLong(obj, j);
        }

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte((int) (-1 & j), b);
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzfv.zzwc) {
                zzfv.zzb(obj, j, z);
            } else {
                zzfv.zzc(obj, j, z);
            }
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray((int) (-1 & j2), bArr, (int) j, (int) j3);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzfv.zzwc) {
                zzfv.zza(obj, j, b);
            } else {
                zzfv.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            return zzfv.zzwc ? zzfv.zzs(obj, j) : zzfv.zzt(obj, j);
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final byte zzy(Object obj, long j) {
            return zzfv.zzwc ? zzfv.zzq(obj, j) : zzfv.zzr(obj, j);
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte(j, b);
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzfv.zzwc) {
                zzfv.zzb(obj, j, z);
            } else {
                zzfv.zzc(obj, j, z);
            }
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray(j2, bArr, (int) j, (int) j3);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzfv.zzwc) {
                zzfv.zza(obj, j, b);
            } else {
                zzfv.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            return zzfv.zzwc ? zzfv.zzs(obj, j) : zzfv.zzt(obj, j);
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final byte zzy(Object obj, long j) {
            return zzfv.zzwc ? zzfv.zzq(obj, j) : zzfv.zzr(obj, j);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            this.zzwd.putByte(j, b);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzwd.putDouble(obj, j, d);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzwd.putFloat(obj, j, f);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzwd.putBoolean(obj, j, z);
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            this.zzwd.copyMemory(bArr, zzfv.zzvn + j, null, j2, j3);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzwd.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzwd.getBoolean(obj, j);
        }

        public final float zzn(Object obj, long j) {
            return this.zzwd.getFloat(obj, j);
        }

        public final double zzo(Object obj, long j) {
            return this.zzwd.getDouble(obj, j);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzwd.getByte(obj, j);
        }
    }

    static {
        Field field = null;
        zzd zzb = zztj == null ? null : zzbr.zzbu() ? zzvj ? new zzb(zztj) : zzvk ? new zza(zztj) : null : new zzc(zztj);
        zzvl = zzb;
        Field zzb2 = zzb(String.class, C1797b.VALUE);
        if (zzb2 != null && zzb2.getType() == char[].class) {
            field = zzb2;
        }
        zzwb = zza(field);
    }

    private zzfv() {
    }

    static byte zza(byte[] bArr, long j) {
        return zzvl.zzy(bArr, zzvn + j);
    }

    private static long zza(Field field) {
        return (field == null || zzvl == null) ? -1 : zzvl.zzwd.objectFieldOffset(field);
    }

    static void zza(long j, byte b) {
        zzvl.zza(j, b);
    }

    private static void zza(Object obj, long j, byte b) {
        int i = ((((int) j) ^ -1) & 3) << 3;
        zzb(obj, j & -4, (zzk(obj, j & -4) & ((255 << i) ^ -1)) | ((b & 255) << i));
    }

    static void zza(Object obj, long j, double d) {
        zzvl.zza(obj, j, d);
    }

    static void zza(Object obj, long j, float f) {
        zzvl.zza(obj, j, f);
    }

    static void zza(Object obj, long j, long j2) {
        zzvl.zza(obj, j, j2);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzvl.zzwd.putObject(obj, j, obj2);
    }

    static void zza(Object obj, long j, boolean z) {
        zzvl.zza(obj, j, z);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzvl.zze(bArr, zzvn + j, b);
    }

    static void zza(byte[] bArr, long j, long j2, long j3) {
        zzvl.zza(bArr, j, j2, j3);
    }

    static long zzb(ByteBuffer byteBuffer) {
        return zzvl.zzl(byteBuffer, zzwa);
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable th) {
            return null;
        }
    }

    private static void zzb(Object obj, long j, byte b) {
        int i = (((int) j) & 3) << 3;
        zzb(obj, j & -4, (zzk(obj, j & -4) & ((255 << i) ^ -1)) | ((b & 255) << i));
    }

    static void zzb(Object obj, long j, int i) {
        zzvl.zzb(obj, j, i);
    }

    private static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, (byte) (z ? 1 : 0));
    }

    private static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, (byte) (z ? 1 : 0));
    }

    static boolean zzgc() {
        return zznf;
    }

    static boolean zzgd() {
        return zzvm;
    }

    static Unsafe zzge() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzfw());
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean zzgf() {
        if (zztj == null) {
            return false;
        }
        try {
            Class cls = zztj.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzbr.zzbu()) {
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

    private static boolean zzgg() {
        if (zztj == null) {
            return false;
        }
        try {
            Class cls = zztj.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (zzgh() == null) {
                return false;
            }
            if (zzbr.zzbu()) {
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

    private static Field zzgh() {
        Field zzb;
        if (zzbr.zzbu()) {
            zzb = zzb(Buffer.class, "effectiveDirectAddress");
            if (zzb != null) {
                return zzb;
            }
        }
        zzb = zzb(Buffer.class, "address");
        return (zzb == null || zzb.getType() != Long.TYPE) ? null : zzb;
    }

    private static int zzh(Class<?> cls) {
        return zznf ? zzvl.zzwd.arrayBaseOffset(cls) : -1;
    }

    private static int zzi(Class<?> cls) {
        return zznf ? zzvl.zzwd.arrayIndexScale(cls) : -1;
    }

    private static boolean zzj(Class<?> cls) {
        if (!zzbr.zzbu()) {
            return false;
        }
        try {
            Class cls2 = zzme;
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

    static int zzk(Object obj, long j) {
        return zzvl.zzk(obj, j);
    }

    static long zzl(Object obj, long j) {
        return zzvl.zzl(obj, j);
    }

    static boolean zzm(Object obj, long j) {
        return zzvl.zzm(obj, j);
    }

    static float zzn(Object obj, long j) {
        return zzvl.zzn(obj, j);
    }

    static double zzo(Object obj, long j) {
        return zzvl.zzo(obj, j);
    }

    static Object zzp(Object obj, long j) {
        return zzvl.zzwd.getObject(obj, j);
    }

    private static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) (((-1 ^ j) & 3) << 3)));
    }

    private static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) ((3 & j) << 3)));
    }

    private static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != (byte) 0;
    }

    private static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != (byte) 0;
    }
}
