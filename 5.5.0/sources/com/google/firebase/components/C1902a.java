package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@KeepForSdk
/* renamed from: com.google.firebase.components.a */
public final class C1902a<T> {
    /* renamed from: a */
    private final Set<Class<? super T>> f5606a;
    /* renamed from: b */
    private final Set<C1905f> f5607b;
    /* renamed from: c */
    private final int f5608c;
    /* renamed from: d */
    private final C1817d<T> f5609d;
    /* renamed from: e */
    private final Set<Class<?>> f5610e;

    @KeepForSdk
    /* renamed from: com.google.firebase.components.a$a */
    public static class C1901a<T> {
        /* renamed from: a */
        private final Set<Class<? super T>> f5601a;
        /* renamed from: b */
        private final Set<C1905f> f5602b;
        /* renamed from: c */
        private int f5603c;
        /* renamed from: d */
        private C1817d<T> f5604d;
        /* renamed from: e */
        private Set<Class<?>> f5605e;

        private C1901a(Class<T> cls, Class<? super T>... clsArr) {
            int i = 0;
            this.f5601a = new HashSet();
            this.f5602b = new HashSet();
            this.f5603c = 0;
            this.f5605e = new HashSet();
            Preconditions.checkNotNull(cls, "Null interface");
            this.f5601a.add(cls);
            int length = clsArr.length;
            while (i < length) {
                Preconditions.checkNotNull(clsArr[i], "Null interface");
                i++;
            }
            Collections.addAll(this.f5601a, clsArr);
        }

        /* renamed from: a */
        private C1901a<T> m8700a(int i) {
            Preconditions.checkState(this.f5603c == 0, "Instantiation type has already been set.");
            this.f5603c = i;
            return this;
        }

        @KeepForSdk
        /* renamed from: a */
        public C1901a<T> m8701a() {
            return m8700a(1);
        }

        @KeepForSdk
        /* renamed from: a */
        public C1901a<T> m8702a(C1817d<T> c1817d) {
            this.f5604d = (C1817d) Preconditions.checkNotNull(c1817d, "Null factory");
            return this;
        }

        @KeepForSdk
        /* renamed from: a */
        public C1901a<T> m8703a(C1905f c1905f) {
            Preconditions.checkNotNull(c1905f, "Null dependency");
            Preconditions.checkArgument(!this.f5601a.contains(c1905f.m8719a()), "Components are not allowed to depend on interfaces they themselves provide.");
            this.f5602b.add(c1905f);
            return this;
        }

        @KeepForSdk
        /* renamed from: b */
        public C1902a<T> m8704b() {
            Preconditions.checkState(this.f5604d != null, "Missing required property: factory.");
            return new C1902a(new HashSet(this.f5601a), new HashSet(this.f5602b), this.f5603c, this.f5604d, this.f5605e);
        }
    }

    private C1902a(Set<Class<? super T>> set, Set<C1905f> set2, int i, C1817d<T> c1817d, Set<Class<?>> set3) {
        this.f5606a = Collections.unmodifiableSet(set);
        this.f5607b = Collections.unmodifiableSet(set2);
        this.f5608c = i;
        this.f5609d = c1817d;
        this.f5610e = Collections.unmodifiableSet(set3);
    }

    @KeepForSdk
    /* renamed from: a */
    public static <T> C1901a<T> m8705a(Class<T> cls) {
        return new C1901a(cls, new Class[0]);
    }

    @KeepForSdk
    /* renamed from: a */
    public static <T> C1901a<T> m8706a(Class<T> cls, Class<? super T>... clsArr) {
        return new C1901a(cls, clsArr);
    }

    @KeepForSdk
    @SafeVarargs
    /* renamed from: a */
    public static <T> C1902a<T> m8707a(T t, Class<T> cls, Class<? super T>... clsArr) {
        return C1902a.m8706a(cls, clsArr).m8702a(new C1909j(t)).m8704b();
    }

    /* renamed from: a */
    static final /* synthetic */ Object m8708a(Object obj) {
        return obj;
    }

    /* renamed from: a */
    public final Set<Class<? super T>> m8709a() {
        return this.f5606a;
    }

    /* renamed from: b */
    public final Set<C1905f> m8710b() {
        return this.f5607b;
    }

    /* renamed from: c */
    public final C1817d<T> m8711c() {
        return this.f5609d;
    }

    /* renamed from: d */
    public final Set<Class<?>> m8712d() {
        return this.f5610e;
    }

    /* renamed from: e */
    public final boolean m8713e() {
        return this.f5608c == 1;
    }

    /* renamed from: f */
    public final boolean m8714f() {
        return this.f5608c == 2;
    }

    public final String toString() {
        return "Component<" + Arrays.toString(this.f5606a.toArray()) + ">{" + this.f5608c + ", deps=" + Arrays.toString(this.f5607b.toArray()) + "}";
    }
}
