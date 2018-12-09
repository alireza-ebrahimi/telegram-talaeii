package com.persianswitch.p122a.p123a;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: com.persianswitch.a.a.i */
class C2184i<T> {
    /* renamed from: a */
    private final Class<?> f6628a;
    /* renamed from: b */
    private final String f6629b;
    /* renamed from: c */
    private final Class[] f6630c;

    public C2184i(Class<?> cls, String str, Class... clsArr) {
        this.f6628a = cls;
        this.f6629b = str;
        this.f6630c = clsArr;
    }

    /* renamed from: a */
    private Method m9876a(Class<?> cls) {
        if (this.f6629b == null) {
            return null;
        }
        Method a = C2184i.m9877a(cls, this.f6629b, this.f6630c);
        return (a == null || this.f6628a == null || this.f6628a.isAssignableFrom(a.getReturnType())) ? a : null;
    }

    /* renamed from: a */
    private static Method m9877a(Class<?> cls, String str, Class[] clsArr) {
        try {
            Method method = cls.getMethod(str, clsArr);
            try {
                return (method.getModifiers() & 1) == 0 ? null : method;
            } catch (NoSuchMethodException e) {
                return method;
            }
        } catch (NoSuchMethodException e2) {
            return null;
        }
    }

    /* renamed from: a */
    public Object m9878a(T t, Object... objArr) {
        Object obj = null;
        Method a = m9876a(t.getClass());
        if (a != null) {
            try {
                obj = a.invoke(t, objArr);
            } catch (IllegalAccessException e) {
            }
        }
        return obj;
    }

    /* renamed from: a */
    public boolean m9879a(T t) {
        return m9876a(t.getClass()) != null;
    }

    /* renamed from: b */
    public Object m9880b(T t, Object... objArr) {
        try {
            return m9878a(t, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError assertionError = new AssertionError("Unexpected exception");
            assertionError.initCause(targetException);
            throw assertionError;
        }
    }

    /* renamed from: c */
    public Object m9881c(T t, Object... objArr) {
        Object a = m9876a(t.getClass());
        if (a == null) {
            throw new AssertionError("Method " + this.f6629b + " not supported for object " + t);
        }
        try {
            return a.invoke(t, objArr);
        } catch (Throwable e) {
            AssertionError assertionError = new AssertionError("Unexpectedly could not call: " + a);
            assertionError.initCause(e);
            throw assertionError;
        }
    }

    /* renamed from: d */
    public Object m9882d(T t, Object... objArr) {
        try {
            return m9881c(t, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError assertionError = new AssertionError("Unexpected exception");
            assertionError.initCause(targetException);
            throw assertionError;
        }
    }
}
