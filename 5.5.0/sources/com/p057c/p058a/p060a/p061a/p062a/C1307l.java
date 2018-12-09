package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1290e;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: com.c.a.a.a.a.l */
public class C1307l {
    /* renamed from: a */
    protected static Logger f3960a = Logger.getLogger(C1307l.class.getName());
    /* renamed from: b */
    protected static Map<Integer, Map<Integer, Class<? extends C1296b>>> f3961b = new HashMap();

    static {
        Set<Class> hashSet = new HashSet();
        hashSet.add(C1301f.class);
        hashSet.add(C1309n.class);
        hashSet.add(C1296b.class);
        hashSet.add(C1304i.class);
        hashSet.add(C1306k.class);
        hashSet.add(C1308m.class);
        hashSet.add(C1297a.class);
        hashSet.add(C1305j.class);
        hashSet.add(C1303h.class);
        hashSet.add(C1300e.class);
        for (Class cls : hashSet) {
            C1302g c1302g = (C1302g) cls.getAnnotation(C1302g.class);
            int[] a = c1302g.m6727a();
            int b = c1302g.m6728b();
            Map map = (Map) f3961b.get(Integer.valueOf(b));
            if (map == null) {
                map = new HashMap();
            }
            for (int valueOf : a) {
                map.put(Integer.valueOf(valueOf), cls);
            }
            f3961b.put(Integer.valueOf(b), map);
        }
    }

    /* renamed from: a */
    public static C1296b m6737a(int i, ByteBuffer byteBuffer) {
        C1296b c1310o;
        int d = C1290e.m6671d(byteBuffer);
        Map map = (Map) f3961b.get(Integer.valueOf(i));
        if (map == null) {
            map = (Map) f3961b.get(Integer.valueOf(-1));
        }
        Class cls = (Class) map.get(Integer.valueOf(d));
        if (cls == null || cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
            f3960a.warning("No ObjectDescriptor found for objectTypeIndication " + Integer.toHexString(i) + " and tag " + Integer.toHexString(d) + " found: " + cls);
            c1310o = new C1310o();
        } else {
            try {
                c1310o = (C1296b) cls.newInstance();
            } catch (Throwable e) {
                f3960a.log(Level.SEVERE, "Couldn't instantiate BaseDescriptor class " + cls + " for objectTypeIndication " + i + " and tag " + d, e);
                throw new RuntimeException(e);
            }
        }
        c1310o.m6694a(d, byteBuffer);
        return c1310o;
    }
}
