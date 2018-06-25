package com.google.firebase.auth.p104a.p105a;

/* renamed from: com.google.firebase.auth.a.a.ah */
public final class ah {
    /* renamed from: a */
    public static String m8563a(String str) {
        try {
            Object invoke = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class}).invoke(null, new Object[]{str});
            if (invoke != null && String.class.isAssignableFrom(invoke.getClass())) {
                return (String) invoke;
            }
        } catch (Exception e) {
        }
        return null;
    }
}
