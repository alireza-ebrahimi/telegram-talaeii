package org.p138a.p141b.p143b;

import java.util.Hashtable;
import java.util.StringTokenizer;
import org.p138a.p139a.C2429d;
import org.p138a.p139a.C2434a;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p139a.p140a.C2432c;
import org.p138a.p139a.p140a.C2433d;
import org.p138a.p141b.p143b.C2443c.C2442a;

/* renamed from: org.a.b.b.b */
public final class C2441b {
    /* renamed from: e */
    static Hashtable f8158e = new Hashtable();
    /* renamed from: f */
    static Class f8159f;
    /* renamed from: g */
    private static Object[] f8160g = new Object[0];
    /* renamed from: a */
    Class f8161a;
    /* renamed from: b */
    ClassLoader f8162b;
    /* renamed from: c */
    String f8163c;
    /* renamed from: d */
    int f8164d = 0;

    static {
        f8158e.put("void", Void.TYPE);
        f8158e.put("boolean", Boolean.TYPE);
        f8158e.put("byte", Byte.TYPE);
        f8158e.put("char", Character.TYPE);
        f8158e.put("short", Short.TYPE);
        f8158e.put("int", Integer.TYPE);
        f8158e.put("long", Long.TYPE);
        f8158e.put("float", Float.TYPE);
        f8158e.put("double", Double.TYPE);
    }

    public C2441b(String str, Class cls) {
        this.f8163c = str;
        this.f8161a = cls;
        this.f8162b = cls.getClassLoader();
    }

    /* renamed from: a */
    static Class m11962a(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    /* renamed from: a */
    static Class m11963a(String str, ClassLoader classLoader) {
        if (str.equals("*")) {
            return null;
        }
        Class cls = (Class) f8158e.get(str);
        if (cls != null) {
            return cls;
        }
        if (classLoader != null) {
            return Class.forName(str, false, classLoader);
        }
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            if (f8159f != null) {
                return f8159f;
            }
            cls = C2441b.m11962a("java.lang.ClassNotFoundException");
            f8159f = cls;
            return cls;
        }
    }

    /* renamed from: a */
    public static C2434a m11964a(C2428a c2428a, Object obj, Object obj2) {
        return new C2443c(c2428a, obj, obj2, f8160g);
    }

    /* renamed from: a */
    public static C2434a m11965a(C2428a c2428a, Object obj, Object obj2, Object obj3) {
        return new C2443c(c2428a, obj, obj2, new Object[]{obj3});
    }

    /* renamed from: a */
    public C2428a m11966a(String str, C2429d c2429d, int i) {
        int i2 = this.f8164d;
        this.f8164d = i2 + 1;
        return new C2442a(i2, str, c2429d, m11968a(i, -1));
    }

    /* renamed from: a */
    public C2432c m11967a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        int i;
        int parseInt = Integer.parseInt(str, 16);
        Class a = C2441b.m11963a(str3, this.f8162b);
        StringTokenizer stringTokenizer = new StringTokenizer(str4, ":");
        int countTokens = stringTokenizer.countTokens();
        Class[] clsArr = new Class[countTokens];
        for (i = 0; i < countTokens; i++) {
            clsArr[i] = C2441b.m11963a(stringTokenizer.nextToken(), this.f8162b);
        }
        stringTokenizer = new StringTokenizer(str5, ":");
        int countTokens2 = stringTokenizer.countTokens();
        String[] strArr = new String[countTokens2];
        for (i = 0; i < countTokens2; i++) {
            strArr[i] = stringTokenizer.nextToken();
        }
        stringTokenizer = new StringTokenizer(str6, ":");
        int countTokens3 = stringTokenizer.countTokens();
        Class[] clsArr2 = new Class[countTokens3];
        for (i = 0; i < countTokens3; i++) {
            clsArr2[i] = C2441b.m11963a(stringTokenizer.nextToken(), this.f8162b);
        }
        return new C2444e(parseInt, str2, a, clsArr, strArr, clsArr2, C2441b.m11963a(str7, this.f8162b));
    }

    /* renamed from: a */
    public C2433d m11968a(int i, int i2) {
        return new C2447g(this.f8161a, this.f8163c, i);
    }
}
