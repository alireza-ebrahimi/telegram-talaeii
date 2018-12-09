package com.persianswitch.p122a.p123a.p127b;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: com.persianswitch.a.a.b.p */
public final class C2159p extends RuntimeException {
    /* renamed from: a */
    private static final Method f6537a;
    /* renamed from: b */
    private IOException f6538b;

    static {
        Method declaredMethod;
        try {
            declaredMethod = Throwable.class.getDeclaredMethod("addSuppressed", new Class[]{Throwable.class});
        } catch (Exception e) {
            declaredMethod = null;
        }
        f6537a = declaredMethod;
    }

    public C2159p(IOException iOException) {
        super(iOException);
        this.f6538b = iOException;
    }

    /* renamed from: a */
    private void m9751a(IOException iOException, IOException iOException2) {
        if (f6537a != null) {
            try {
                f6537a.invoke(iOException, new Object[]{iOException2});
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e2) {
            }
        }
    }

    /* renamed from: a */
    public IOException m9752a() {
        return this.f6538b;
    }

    /* renamed from: a */
    public void m9753a(IOException iOException) {
        m9751a(iOException, this.f6538b);
        this.f6538b = iOException;
    }
}
