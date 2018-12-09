package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

/* renamed from: android.support.v7.app.n */
class C0804n {
    /* renamed from: a */
    private static Field f1859a;
    /* renamed from: b */
    private static boolean f1860b;
    /* renamed from: c */
    private static Class f1861c;
    /* renamed from: d */
    private static boolean f1862d;
    /* renamed from: e */
    private static Field f1863e;
    /* renamed from: f */
    private static boolean f1864f;
    /* renamed from: g */
    private static Field f1865g;
    /* renamed from: h */
    private static boolean f1866h;

    /* renamed from: a */
    static boolean m3820a(Resources resources) {
        int i = VERSION.SDK_INT;
        return i >= 24 ? C0804n.m3824d(resources) : i >= 23 ? C0804n.m3823c(resources) : i >= 21 ? C0804n.m3822b(resources) : false;
    }

    /* renamed from: a */
    private static boolean m3821a(Object obj) {
        if (!f1862d) {
            try {
                f1861c = Class.forName("android.content.res.ThemedResourceCache");
            } catch (Throwable e) {
                Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", e);
            }
            f1862d = true;
        }
        if (f1861c == null) {
            return false;
        }
        if (!f1864f) {
            try {
                f1863e = f1861c.getDeclaredField("mUnthemedEntries");
                f1863e.setAccessible(true);
            } catch (Throwable e2) {
                Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", e2);
            }
            f1864f = true;
        }
        if (f1863e == null) {
            return false;
        }
        LongSparseArray longSparseArray;
        try {
            longSparseArray = (LongSparseArray) f1863e.get(obj);
        } catch (Throwable e22) {
            Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e22);
            longSparseArray = null;
        }
        if (longSparseArray == null) {
            return false;
        }
        longSparseArray.clear();
        return true;
    }

    /* renamed from: b */
    private static boolean m3822b(Resources resources) {
        if (!f1860b) {
            try {
                f1859a = Resources.class.getDeclaredField("mDrawableCache");
                f1859a.setAccessible(true);
            } catch (Throwable e) {
                Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", e);
            }
            f1860b = true;
        }
        if (f1859a != null) {
            Map map;
            try {
                map = (Map) f1859a.get(resources);
            } catch (Throwable e2) {
                Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", e2);
                map = null;
            }
            if (map != null) {
                map.clear();
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    private static boolean m3823c(Resources resources) {
        Object obj;
        boolean z = true;
        if (!f1860b) {
            try {
                f1859a = Resources.class.getDeclaredField("mDrawableCache");
                f1859a.setAccessible(true);
            } catch (Throwable e) {
                Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", e);
            }
            f1860b = true;
        }
        if (f1859a != null) {
            try {
                obj = f1859a.get(resources);
            } catch (Throwable e2) {
                Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", e2);
            }
            if (obj == null) {
                return false;
            }
            if (obj == null || !C0804n.m3821a(obj)) {
                z = false;
            }
            return z;
        }
        obj = null;
        if (obj == null) {
            return false;
        }
        z = false;
        return z;
    }

    /* renamed from: d */
    private static boolean m3824d(Resources resources) {
        boolean z = true;
        if (!f1866h) {
            try {
                f1865g = Resources.class.getDeclaredField("mResourcesImpl");
                f1865g.setAccessible(true);
            } catch (Throwable e) {
                Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", e);
            }
            f1866h = true;
        }
        if (f1865g == null) {
            return false;
        }
        Object obj;
        try {
            obj = f1865g.get(resources);
        } catch (Throwable e2) {
            Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", e2);
            obj = null;
        }
        if (obj == null) {
            return false;
        }
        Object obj2;
        if (!f1860b) {
            try {
                f1859a = obj.getClass().getDeclaredField("mDrawableCache");
                f1859a.setAccessible(true);
            } catch (Throwable e22) {
                Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", e22);
            }
            f1860b = true;
        }
        if (f1859a != null) {
            try {
                obj2 = f1859a.get(obj);
            } catch (Throwable e222) {
                Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", e222);
            }
            if (obj2 == null || !C0804n.m3821a(obj2)) {
                z = false;
            }
            return z;
        }
        obj2 = null;
        z = false;
        return z;
    }
}
