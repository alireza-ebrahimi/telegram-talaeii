package com.google.p098a.p100b;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.a.b.i */
public final class C1737i {
    /* renamed from: a */
    private static final Map<Class<?>, Class<?>> f5327a;
    /* renamed from: b */
    private static final Map<Class<?>, Class<?>> f5328b;

    static {
        Map hashMap = new HashMap(16);
        Map hashMap2 = new HashMap(16);
        C1737i.m8343a(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        C1737i.m8343a(hashMap, hashMap2, Byte.TYPE, Byte.class);
        C1737i.m8343a(hashMap, hashMap2, Character.TYPE, Character.class);
        C1737i.m8343a(hashMap, hashMap2, Double.TYPE, Double.class);
        C1737i.m8343a(hashMap, hashMap2, Float.TYPE, Float.class);
        C1737i.m8343a(hashMap, hashMap2, Integer.TYPE, Integer.class);
        C1737i.m8343a(hashMap, hashMap2, Long.TYPE, Long.class);
        C1737i.m8343a(hashMap, hashMap2, Short.TYPE, Short.class);
        C1737i.m8343a(hashMap, hashMap2, Void.TYPE, Void.class);
        f5327a = Collections.unmodifiableMap(hashMap);
        f5328b = Collections.unmodifiableMap(hashMap2);
    }

    /* renamed from: a */
    public static <T> Class<T> m8342a(Class<T> cls) {
        Class<T> cls2 = (Class) f5327a.get(C1709a.m8275a((Object) cls));
        return cls2 == null ? cls : cls2;
    }

    /* renamed from: a */
    private static void m8343a(Map<Class<?>, Class<?>> map, Map<Class<?>, Class<?>> map2, Class<?> cls, Class<?> cls2) {
        map.put(cls, cls2);
        map2.put(cls2, cls);
    }

    /* renamed from: a */
    public static boolean m8344a(Type type) {
        return f5327a.containsKey(type);
    }
}
