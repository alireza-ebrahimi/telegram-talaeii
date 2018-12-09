package com.google.p098a.p100b;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* renamed from: com.google.a.b.k */
public abstract class C1742k {

    /* renamed from: com.google.a.b.k$4 */
    static class C17464 extends C1742k {
        C17464() {
        }

        /* renamed from: a */
        public <T> T mo1289a(Class<T> cls) {
            throw new UnsupportedOperationException("Cannot allocate " + cls);
        }
    }

    /* renamed from: a */
    public static C1742k m8348a() {
        final Method method;
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            final Object obj = declaredField.get(null);
            method = cls.getMethod("allocateInstance", new Class[]{Class.class});
            return new C1742k() {
                /* renamed from: a */
                public <T> T mo1289a(Class<T> cls) {
                    return method.invoke(obj, new Object[]{cls});
                }
            };
        } catch (Exception e) {
            try {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                final int intValue = ((Integer) declaredMethod.invoke(null, new Object[]{Object.class})).intValue();
                method = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                method.setAccessible(true);
                return new C1742k() {
                    /* renamed from: a */
                    public <T> T mo1289a(Class<T> cls) {
                        return method.invoke(null, new Object[]{cls, Integer.valueOf(intValue)});
                    }
                };
            } catch (Exception e2) {
                try {
                    final Method declaredMethod2 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    declaredMethod2.setAccessible(true);
                    return new C1742k() {
                        /* renamed from: a */
                        public <T> T mo1289a(Class<T> cls) {
                            return declaredMethod2.invoke(null, new Object[]{cls, Object.class});
                        }
                    };
                } catch (Exception e3) {
                    return new C17464();
                }
            }
        }
    }

    /* renamed from: a */
    public abstract <T> T mo1289a(Class<T> cls);
}
