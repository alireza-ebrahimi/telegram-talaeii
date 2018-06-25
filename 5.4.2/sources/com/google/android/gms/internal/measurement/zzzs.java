package com.google.android.gms.internal.measurement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzzs<MessageType extends zzzs<MessageType, BuilderType>, BuilderType> extends zzyw<MessageType, BuilderType> {
    private static Map<Object, zzzs<?, ?>> zzbsk = new ConcurrentHashMap();
    protected zzabm zzbsi = zzabm.zzuz();
    private int zzbsj = -1;

    public static abstract class zza<MessageType extends zza<MessageType, BuilderType>, BuilderType> extends zzzs<MessageType, BuilderType> implements zzaao {
        protected zzzo<Object> zzbsl = zzzo.zztr();
    }

    public enum zzb {
        private static final int zzbsm = 1;
        private static final int zzbsn = 2;
        public static final int zzbso = 3;
        private static final int zzbsp = 4;
        private static final int zzbsq = 5;
        public static final int zzbsr = 6;
        public static final int zzbss = 7;
        private static final /* synthetic */ int[] zzbst = new int[]{1, 2, 3, 4, 5, 6, 7};
        public static final int zzbsu = 1;
        private static final int zzbsv = 2;
        private static final /* synthetic */ int[] zzbsw = new int[]{1, 2};
        private static final int zzbsx = 1;
        private static final int zzbsy = 2;
        private static final /* synthetic */ int[] zzbsz = new int[]{1, 2};
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

    static <T extends zzzs<?, ?>> T zzf(Class<T> cls) {
        T t = (zzzs) zzbsk.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzzs) zzbsk.get(cls);
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
        return this == obj ? true : !((zzzs) zza(6, null, null)).getClass().isInstance(obj) ? false : zzaav.zzum().zzt(this).equals(this, (zzzs) obj);
    }

    public int hashCode() {
        if (this.zzbrd != 0) {
            return this.zzbrd;
        }
        this.zzbrd = zzaav.zzum().zzt(this).hashCode(this);
        return this.zzbrd;
    }

    public String toString() {
        return zzaap.zza(this, super.toString());
    }

    protected abstract Object zza(int i, Object obj, Object obj2);
}
