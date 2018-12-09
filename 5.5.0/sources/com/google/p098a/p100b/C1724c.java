package com.google.p098a.p100b;

import com.google.p098a.C1770h;
import com.google.p098a.C1774m;
import com.google.p098a.p100b.C1724c;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/* renamed from: com.google.a.b.c */
public final class C1724c {
    /* renamed from: a */
    private final Map<Type, C1770h<?>> f5286a;

    /* renamed from: com.google.a.b.c$2 */
    class C17162 implements C1714h<T> {
        /* renamed from: a */
        final /* synthetic */ C1724c f5271a;

        C17162(C1724c c1724c) {
            this.f5271a = c1724c;
        }

        /* renamed from: a */
        public T mo1288a() {
            return new LinkedHashMap();
        }
    }

    /* renamed from: com.google.a.b.c$3 */
    class C17173 implements C1714h<T> {
        /* renamed from: a */
        final /* synthetic */ C1724c f5272a;

        C17173(C1724c c1724c) {
            this.f5272a = c1724c;
        }

        /* renamed from: a */
        public T mo1288a() {
            return new C1736g();
        }
    }

    /* renamed from: com.google.a.b.c$7 */
    class C17217 implements C1714h<T> {
        /* renamed from: a */
        final /* synthetic */ C1724c f5282a;

        C17217(C1724c c1724c) {
            this.f5282a = c1724c;
        }

        /* renamed from: a */
        public T mo1288a() {
            return new TreeSet();
        }
    }

    /* renamed from: com.google.a.b.c$9 */
    class C17239 implements C1714h<T> {
        /* renamed from: a */
        final /* synthetic */ C1724c f5285a;

        C17239(C1724c c1724c) {
            this.f5285a = c1724c;
        }

        /* renamed from: a */
        public T mo1288a() {
            return new LinkedHashSet();
        }
    }

    public C1724c(Map<Type, C1770h<?>> map) {
        this.f5286a = map;
    }

    /* renamed from: a */
    private <T> C1714h<T> m8312a(Class<? super T> cls) {
        try {
            final Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new C1714h<T>(this) {
                /* renamed from: b */
                final /* synthetic */ C1724c f5281b;

                /* renamed from: a */
                public T mo1288a() {
                    try {
                        return declaredConstructor.newInstance(null);
                    } catch (Throwable e) {
                        throw new RuntimeException("Failed to invoke " + declaredConstructor + " with no args", e);
                    } catch (InvocationTargetException e2) {
                        throw new RuntimeException("Failed to invoke " + declaredConstructor + " with no args", e2.getTargetException());
                    } catch (IllegalAccessException e3) {
                        throw new AssertionError(e3);
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /* renamed from: a */
    private <T> C1714h<T> m8313a(final Type type, Class<? super T> cls) {
        return Collection.class.isAssignableFrom(cls) ? SortedSet.class.isAssignableFrom(cls) ? new C17217(this) : EnumSet.class.isAssignableFrom(cls) ? new C1714h<T>(this) {
            /* renamed from: b */
            final /* synthetic */ C1724c f5284b;

            /* renamed from: a */
            public T mo1288a() {
                if (type instanceof ParameterizedType) {
                    Type type = ((ParameterizedType) type).getActualTypeArguments()[0];
                    if (type instanceof Class) {
                        return EnumSet.noneOf((Class) type);
                    }
                    throw new C1774m("Invalid EnumSet type: " + type.toString());
                }
                throw new C1774m("Invalid EnumSet type: " + type.toString());
            }
        } : Set.class.isAssignableFrom(cls) ? new C17239(this) : Queue.class.isAssignableFrom(cls) ? new C1714h<T>(this) {
            /* renamed from: a */
            final /* synthetic */ C1724c f5265a;

            {
                this.f5265a = r1;
            }

            /* renamed from: a */
            public T mo1288a() {
                return new LinkedList();
            }
        } : new C1714h<T>(this) {
            /* renamed from: a */
            final /* synthetic */ C1724c f5266a;

            {
                this.f5266a = r1;
            }

            /* renamed from: a */
            public T mo1288a() {
                return new ArrayList();
            }
        } : Map.class.isAssignableFrom(cls) ? SortedMap.class.isAssignableFrom(cls) ? new C1714h<T>(this) {
            /* renamed from: a */
            final /* synthetic */ C1724c f5267a;

            {
                this.f5267a = r1;
            }

            /* renamed from: a */
            public T mo1288a() {
                return new TreeMap();
            }
        } : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(C1748a.m8356a(((ParameterizedType) type).getActualTypeArguments()[0]).m8359a())) ? new C17173(this) : new C17162(this) : null;
    }

    /* renamed from: b */
    private <T> C1714h<T> m8314b(final Type type, final Class<? super T> cls) {
        return new C1714h<T>(this) {
            /* renamed from: c */
            final /* synthetic */ C1724c f5275c;
            /* renamed from: d */
            private final C1742k f5276d = C1742k.m8348a();

            /* renamed from: a */
            public T mo1288a() {
                try {
                    return this.f5276d.mo1289a(cls);
                } catch (Throwable e) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e);
                }
            }
        };
    }

    /* renamed from: a */
    public <T> C1714h<T> m8315a(C1748a<T> c1748a) {
        final Type b = c1748a.m8360b();
        Class a = c1748a.m8359a();
        final C1770h c1770h = (C1770h) this.f5286a.get(b);
        if (c1770h != null) {
            return new C1714h<T>(this) {
                /* renamed from: c */
                final /* synthetic */ C1724c f5270c;

                /* renamed from: a */
                public T mo1288a() {
                    return c1770h.m8404a(b);
                }
            };
        }
        c1770h = (C1770h) this.f5286a.get(a);
        if (c1770h != null) {
            return new C1714h<T>(this) {
                /* renamed from: c */
                final /* synthetic */ C1724c f5279c;

                /* renamed from: a */
                public T mo1288a() {
                    return c1770h.m8404a(b);
                }
            };
        }
        C1714h<T> a2 = m8312a(a);
        if (a2 != null) {
            return a2;
        }
        a2 = m8313a(b, a);
        return a2 == null ? m8314b(b, a) : a2;
    }

    public String toString() {
        return this.f5286a.toString();
    }
}
