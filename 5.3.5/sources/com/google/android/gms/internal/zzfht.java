package com.google.android.gms.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

final class zzfht implements zzfjb {
    private static final zzfht zzppf = new zzfht();
    private final Map<Class<?>, Method> zzppg = new HashMap();

    private zzfht() {
    }

    public static zzfht zzczp() {
        return zzppf;
    }

    public final boolean zzi(Class<?> cls) {
        return zzfhu.class.isAssignableFrom(cls);
    }

    public final zzfja zzj(Class<?> cls) {
        if (zzfhu.class.isAssignableFrom(cls)) {
            try {
                Method method = (Method) this.zzppg.get(cls);
                if (method == null) {
                    method = cls.getDeclaredMethod("buildMessageInfo", new Class[0]);
                    method.setAccessible(true);
                    this.zzppg.put(cls, method);
                }
                return (zzfja) method.invoke(null, new Object[0]);
            } catch (Throwable e) {
                Throwable th = e;
                String str = "Unable to get message info for ";
                String valueOf = String.valueOf(cls.getName());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), th);
            }
        }
        String str2 = "Unsupported message type: ";
        valueOf = String.valueOf(cls.getName());
        throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }
}
