package com.p072e.p073a;

import android.util.Log;
import com.p072e.p074b.C1494c;
import com.p072e.p074b.C1495a;
import com.p072e.p074b.C1498b;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: com.e.a.j */
public class C1508j implements Cloneable {
    /* renamed from: i */
    private static final C1483k f4567i = new C1487d();
    /* renamed from: j */
    private static final C1483k f4568j = new C1484b();
    /* renamed from: k */
    private static Class[] f4569k = new Class[]{Float.TYPE, Float.class, Double.TYPE, Integer.TYPE, Double.class, Integer.class};
    /* renamed from: l */
    private static Class[] f4570l = new Class[]{Integer.TYPE, Integer.class, Float.TYPE, Double.TYPE, Float.class, Double.class};
    /* renamed from: m */
    private static Class[] f4571m = new Class[]{Double.TYPE, Double.class, Float.TYPE, Integer.TYPE, Float.class, Integer.class};
    /* renamed from: n */
    private static final HashMap<Class, HashMap<String, Method>> f4572n = new HashMap();
    /* renamed from: o */
    private static final HashMap<Class, HashMap<String, Method>> f4573o = new HashMap();
    /* renamed from: a */
    String f4574a;
    /* renamed from: b */
    protected C1494c f4575b;
    /* renamed from: c */
    Method f4576c;
    /* renamed from: d */
    Class f4577d;
    /* renamed from: e */
    C1485g f4578e;
    /* renamed from: f */
    final ReentrantReadWriteLock f4579f;
    /* renamed from: g */
    final Object[] f4580g;
    /* renamed from: h */
    private Method f4581h;
    /* renamed from: p */
    private C1483k f4582p;
    /* renamed from: q */
    private Object f4583q;

    /* renamed from: com.e.a.j$a */
    static class C1509a extends C1508j {
        /* renamed from: h */
        C1486c f4584h;
        /* renamed from: i */
        float f4585i;
        /* renamed from: j */
        private C1495a f4586j;

        public C1509a(C1494c c1494c, float... fArr) {
            super(c1494c);
            mo1208a(fArr);
            if (c1494c instanceof C1495a) {
                this.f4586j = (C1495a) this.b;
            }
        }

        public C1509a(String str, float... fArr) {
            super(str);
            mo1208a(fArr);
        }

        /* renamed from: a */
        public /* synthetic */ C1508j mo1205a() {
            return m7517e();
        }

        /* renamed from: a */
        void mo1206a(float f) {
            this.f4585i = this.f4584h.m7349b(f);
        }

        /* renamed from: a */
        void mo1207a(Class cls) {
            if (this.b == null) {
                super.mo1207a(cls);
            }
        }

        /* renamed from: a */
        public void mo1208a(float... fArr) {
            super.mo1208a(fArr);
            this.f4584h = (C1486c) this.e;
        }

        /* renamed from: b */
        void mo1209b(Object obj) {
            if (this.f4586j != null) {
                this.f4586j.mo1203a(obj, this.f4585i);
            } else if (this.b != null) {
                this.b.mo1201a(obj, Float.valueOf(this.f4585i));
            } else if (this.c != null) {
                try {
                    this.g[0] = Float.valueOf(this.f4585i);
                    this.c.invoke(obj, this.g);
                } catch (InvocationTargetException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (IllegalAccessException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        public /* synthetic */ Object clone() {
            return m7517e();
        }

        /* renamed from: d */
        Object mo1211d() {
            return Float.valueOf(this.f4585i);
        }

        /* renamed from: e */
        public C1509a m7517e() {
            C1509a c1509a = (C1509a) super.mo1205a();
            c1509a.f4584h = (C1486c) c1509a.e;
            return c1509a;
        }
    }

    /* renamed from: com.e.a.j$b */
    static class C1510b extends C1508j {
        /* renamed from: h */
        C1488e f4587h;
        /* renamed from: i */
        int f4588i;
        /* renamed from: j */
        private C1498b f4589j;

        public C1510b(C1494c c1494c, int... iArr) {
            super(c1494c);
            mo1212a(iArr);
            if (c1494c instanceof C1498b) {
                this.f4589j = (C1498b) this.b;
            }
        }

        public C1510b(String str, int... iArr) {
            super(str);
            mo1212a(iArr);
        }

        /* renamed from: a */
        public /* synthetic */ C1508j mo1205a() {
            return m7524e();
        }

        /* renamed from: a */
        void mo1206a(float f) {
            this.f4588i = this.f4587h.m7355b(f);
        }

        /* renamed from: a */
        void mo1207a(Class cls) {
            if (this.b == null) {
                super.mo1207a(cls);
            }
        }

        /* renamed from: a */
        public void mo1212a(int... iArr) {
            super.mo1212a(iArr);
            this.f4587h = (C1488e) this.e;
        }

        /* renamed from: b */
        void mo1209b(Object obj) {
            if (this.f4589j != null) {
                this.f4589j.mo1204a(obj, this.f4588i);
            } else if (this.b != null) {
                this.b.mo1201a(obj, Integer.valueOf(this.f4588i));
            } else if (this.c != null) {
                try {
                    this.g[0] = Integer.valueOf(this.f4588i);
                    this.c.invoke(obj, this.g);
                } catch (InvocationTargetException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (IllegalAccessException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        public /* synthetic */ Object clone() {
            return m7524e();
        }

        /* renamed from: d */
        Object mo1211d() {
            return Integer.valueOf(this.f4588i);
        }

        /* renamed from: e */
        public C1510b m7524e() {
            C1510b c1510b = (C1510b) super.mo1205a();
            c1510b.f4587h = (C1488e) c1510b.e;
            return c1510b;
        }
    }

    private C1508j(C1494c c1494c) {
        this.f4576c = null;
        this.f4581h = null;
        this.f4578e = null;
        this.f4579f = new ReentrantReadWriteLock();
        this.f4580g = new Object[1];
        this.f4575b = c1494c;
        if (c1494c != null) {
            this.f4574a = c1494c.m7427a();
        }
    }

    private C1508j(String str) {
        this.f4576c = null;
        this.f4581h = null;
        this.f4578e = null;
        this.f4579f = new ReentrantReadWriteLock();
        this.f4580g = new Object[1];
        this.f4574a = str;
    }

    /* renamed from: a */
    public static C1508j m7491a(C1494c<?, Float> c1494c, float... fArr) {
        return new C1509a((C1494c) c1494c, fArr);
    }

    /* renamed from: a */
    public static C1508j m7492a(C1494c<?, Integer> c1494c, int... iArr) {
        return new C1510b((C1494c) c1494c, iArr);
    }

    /* renamed from: a */
    public static C1508j m7493a(String str, float... fArr) {
        return new C1509a(str, fArr);
    }

    /* renamed from: a */
    public static C1508j m7494a(String str, int... iArr) {
        return new C1510b(str, iArr);
    }

    /* renamed from: a */
    static String m7495a(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        char toUpperCase = Character.toUpperCase(str2.charAt(0));
        return str + toUpperCase + str2.substring(1);
    }

    /* renamed from: a */
    private Method m7496a(Class cls, String str, Class cls2) {
        Method declaredMethod;
        Method method = null;
        String a = C1508j.m7495a(str, this.f4574a);
        Class[] clsArr = null;
        if (cls2 == null) {
            try {
                return cls.getMethod(a, clsArr);
            } catch (NoSuchMethodException e) {
                try {
                    declaredMethod = cls.getDeclaredMethod(a, clsArr);
                    try {
                        declaredMethod.setAccessible(true);
                        return declaredMethod;
                    } catch (NoSuchMethodException e2) {
                        Log.e("PropertyValuesHolder", "Couldn't find no-arg method for property " + this.f4574a + ": " + e);
                        return declaredMethod;
                    }
                } catch (NoSuchMethodException e3) {
                    declaredMethod = null;
                    Log.e("PropertyValuesHolder", "Couldn't find no-arg method for property " + this.f4574a + ": " + e);
                    return declaredMethod;
                }
            }
        }
        Class[] clsArr2 = new Class[1];
        clsArr = this.f4577d.equals(Float.class) ? f4569k : this.f4577d.equals(Integer.class) ? f4570l : this.f4577d.equals(Double.class) ? f4571m : new Class[]{this.f4577d};
        int length = clsArr.length;
        int i = 0;
        while (i < length) {
            Class cls3 = clsArr[i];
            clsArr2[0] = cls3;
            try {
                method = cls.getMethod(a, clsArr2);
                this.f4577d = cls3;
                return method;
            } catch (NoSuchMethodException e4) {
                try {
                    method = cls.getDeclaredMethod(a, clsArr2);
                    method.setAccessible(true);
                    this.f4577d = cls3;
                    return method;
                } catch (NoSuchMethodException e5) {
                    i++;
                }
            }
        }
        Log.e("PropertyValuesHolder", "Couldn't find setter/getter for property " + this.f4574a + " with value type " + this.f4577d);
        return method;
    }

    /* renamed from: a */
    private Method m7497a(Class cls, HashMap<Class, HashMap<String, Method>> hashMap, String str, Class cls2) {
        Method method = null;
        try {
            this.f4579f.writeLock().lock();
            HashMap hashMap2 = (HashMap) hashMap.get(cls);
            if (hashMap2 != null) {
                method = (Method) hashMap2.get(this.f4574a);
            }
            if (method == null) {
                method = m7496a(cls, str, cls2);
                if (hashMap2 == null) {
                    hashMap2 = new HashMap();
                    hashMap.put(cls, hashMap2);
                }
                hashMap2.put(this.f4574a, method);
            }
            Method method2 = method;
            this.f4579f.writeLock().unlock();
            return method2;
        } catch (Throwable th) {
            this.f4579f.writeLock().unlock();
        }
    }

    /* renamed from: b */
    private void m7498b(Class cls) {
        this.f4581h = m7497a(cls, f4573o, "get", null);
    }

    /* renamed from: a */
    public C1508j mo1205a() {
        try {
            C1508j c1508j = (C1508j) super.clone();
            c1508j.f4574a = this.f4574a;
            c1508j.f4575b = this.f4575b;
            c1508j.f4578e = this.f4578e.mo1182b();
            c1508j.f4582p = this.f4582p;
            return c1508j;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /* renamed from: a */
    void mo1206a(float f) {
        this.f4583q = this.f4578e.mo1181a(f);
    }

    /* renamed from: a */
    public void m7501a(C1494c c1494c) {
        this.f4575b = c1494c;
    }

    /* renamed from: a */
    void mo1207a(Class cls) {
        this.f4576c = m7497a(cls, f4572n, "set", this.f4577d);
    }

    /* renamed from: a */
    void m7503a(Object obj) {
        C1489f c1489f;
        if (this.f4575b != null) {
            try {
                this.f4575b.mo1202a(obj);
                Iterator it = this.f4578e.f4502e.iterator();
                while (it.hasNext()) {
                    c1489f = (C1489f) it.next();
                    if (!c1489f.m7363a()) {
                        c1489f.mo1184a(this.f4575b.mo1202a(obj));
                    }
                }
                return;
            } catch (ClassCastException e) {
                Log.e("PropertyValuesHolder", "No such property (" + this.f4575b.m7427a() + ") on target object " + obj + ". Trying reflection instead");
                this.f4575b = null;
            }
        }
        Class cls = obj.getClass();
        if (this.f4576c == null) {
            mo1207a(cls);
        }
        Iterator it2 = this.f4578e.f4502e.iterator();
        while (it2.hasNext()) {
            c1489f = (C1489f) it2.next();
            if (!c1489f.m7363a()) {
                if (this.f4581h == null) {
                    m7498b(cls);
                }
                try {
                    c1489f.mo1184a(this.f4581h.invoke(obj, new Object[0]));
                } catch (InvocationTargetException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                } catch (IllegalAccessException e3) {
                    Log.e("PropertyValuesHolder", e3.toString());
                }
            }
        }
    }

    /* renamed from: a */
    public void m7504a(String str) {
        this.f4574a = str;
    }

    /* renamed from: a */
    public void mo1208a(float... fArr) {
        this.f4577d = Float.TYPE;
        this.f4578e = C1485g.m7342a(fArr);
    }

    /* renamed from: a */
    public void mo1212a(int... iArr) {
        this.f4577d = Integer.TYPE;
        this.f4578e = C1485g.m7343a(iArr);
    }

    /* renamed from: b */
    void m7507b() {
        if (this.f4582p == null) {
            C1483k c1483k = this.f4577d == Integer.class ? f4567i : this.f4577d == Float.class ? f4568j : null;
            this.f4582p = c1483k;
        }
        if (this.f4582p != null) {
            this.f4578e.m7345a(this.f4582p);
        }
    }

    /* renamed from: b */
    void mo1209b(Object obj) {
        if (this.f4575b != null) {
            this.f4575b.mo1201a(obj, mo1211d());
        }
        if (this.f4576c != null) {
            try {
                this.f4580g[0] = mo1211d();
                this.f4576c.invoke(obj, this.f4580g);
            } catch (InvocationTargetException e) {
                Log.e("PropertyValuesHolder", e.toString());
            } catch (IllegalAccessException e2) {
                Log.e("PropertyValuesHolder", e2.toString());
            }
        }
    }

    /* renamed from: c */
    public String m7509c() {
        return this.f4574a;
    }

    public /* synthetic */ Object clone() {
        return mo1205a();
    }

    /* renamed from: d */
    Object mo1211d() {
        return this.f4583q;
    }

    public String toString() {
        return this.f4574a + ": " + this.f4578e.toString();
    }
}
