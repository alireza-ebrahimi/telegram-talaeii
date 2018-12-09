package com.crashlytics.android.p064a;

import android.content.Context;
import android.os.Bundle;
import java.lang.reflect.Method;

/* renamed from: com.crashlytics.android.a.k */
public class C1348k implements C1347q {
    /* renamed from: a */
    private final Method f4088a;
    /* renamed from: b */
    private final Object f4089b;

    public C1348k(Object obj, Method method) {
        this.f4089b = obj;
        this.f4088a = method;
    }

    /* renamed from: a */
    public static C1347q m6865a(Context context) {
        Class b = C1348k.m6867b(context);
        if (b == null) {
            return null;
        }
        Object a = C1348k.m6866a(context, b);
        if (a == null) {
            return null;
        }
        Method b2 = C1348k.m6868b(context, b);
        return b2 != null ? new C1348k(a, b2) : null;
    }

    /* renamed from: a */
    private static Object m6866a(Context context, Class cls) {
        try {
            return cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(cls, new Object[]{context});
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: b */
    private static Class m6867b(Context context) {
        try {
            return context.getClassLoader().loadClass("com.google.android.gms.measurement.AppMeasurement");
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: b */
    private static Method m6868b(Context context, Class cls) {
        try {
            return cls.getDeclaredMethod("logEventInternal", new Class[]{String.class, String.class, Bundle.class});
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: a */
    public void mo1133a(String str, Bundle bundle) {
        mo1134a("fab", str, bundle);
    }

    /* renamed from: a */
    public void mo1134a(String str, String str2, Bundle bundle) {
        try {
            this.f4088a.invoke(this.f4089b, new Object[]{str, str2, bundle});
        } catch (Exception e) {
        }
    }
}
