package android.support.v7.p027c.p028a;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.C0235a;
import android.support.v7.widget.C1069l;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;

/* renamed from: android.support.v7.c.a.b */
public final class C0825b {
    /* renamed from: a */
    private static final ThreadLocal<TypedValue> f1937a = new ThreadLocal();
    /* renamed from: b */
    private static final WeakHashMap<Context, SparseArray<C0824a>> f1938b = new WeakHashMap(0);
    /* renamed from: c */
    private static final Object f1939c = new Object();

    /* renamed from: android.support.v7.c.a.b$a */
    private static class C0824a {
        /* renamed from: a */
        final ColorStateList f1935a;
        /* renamed from: b */
        final Configuration f1936b;

        C0824a(ColorStateList colorStateList, Configuration configuration) {
            this.f1935a = colorStateList;
            this.f1936b = configuration;
        }
    }

    /* renamed from: a */
    public static ColorStateList m3936a(Context context, int i) {
        if (VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i);
        }
        ColorStateList d = C0825b.m3941d(context, i);
        if (d != null) {
            return d;
        }
        d = C0825b.m3940c(context, i);
        if (d == null) {
            return C0235a.m1073b(context, i);
        }
        C0825b.m3938a(context, i, d);
        return d;
    }

    /* renamed from: a */
    private static TypedValue m3937a() {
        TypedValue typedValue = (TypedValue) f1937a.get();
        if (typedValue != null) {
            return typedValue;
        }
        typedValue = new TypedValue();
        f1937a.set(typedValue);
        return typedValue;
    }

    /* renamed from: a */
    private static void m3938a(Context context, int i, ColorStateList colorStateList) {
        synchronized (f1939c) {
            SparseArray sparseArray = (SparseArray) f1938b.get(context);
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                f1938b.put(context, sparseArray);
            }
            sparseArray.append(i, new C0824a(colorStateList, context.getResources().getConfiguration()));
        }
    }

    /* renamed from: b */
    public static Drawable m3939b(Context context, int i) {
        return C1069l.m5865a().m5883a(context, i);
    }

    /* renamed from: c */
    private static ColorStateList m3940c(Context context, int i) {
        ColorStateList colorStateList = null;
        if (!C0825b.m3942e(context, i)) {
            Resources resources = context.getResources();
            try {
                colorStateList = C0823a.m3932a(resources, resources.getXml(i), context.getTheme());
            } catch (Throwable e) {
                Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", e);
            }
        }
        return colorStateList;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: d */
    private static android.content.res.ColorStateList m3941d(android.content.Context r5, int r6) {
        /*
        r2 = f1939c;
        monitor-enter(r2);
        r0 = f1938b;	 Catch:{ all -> 0x0035 }
        r0 = r0.get(r5);	 Catch:{ all -> 0x0035 }
        r0 = (android.util.SparseArray) r0;	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x0032;
    L_0x000d:
        r1 = r0.size();	 Catch:{ all -> 0x0035 }
        if (r1 <= 0) goto L_0x0032;
    L_0x0013:
        r1 = r0.get(r6);	 Catch:{ all -> 0x0035 }
        r1 = (android.support.v7.p027c.p028a.C0825b.C0824a) r1;	 Catch:{ all -> 0x0035 }
        if (r1 == 0) goto L_0x0032;
    L_0x001b:
        r3 = r1.f1936b;	 Catch:{ all -> 0x0035 }
        r4 = r5.getResources();	 Catch:{ all -> 0x0035 }
        r4 = r4.getConfiguration();	 Catch:{ all -> 0x0035 }
        r3 = r3.equals(r4);	 Catch:{ all -> 0x0035 }
        if (r3 == 0) goto L_0x002f;
    L_0x002b:
        r0 = r1.f1935a;	 Catch:{ all -> 0x0035 }
        monitor-exit(r2);	 Catch:{ all -> 0x0035 }
    L_0x002e:
        return r0;
    L_0x002f:
        r0.remove(r6);	 Catch:{ all -> 0x0035 }
    L_0x0032:
        monitor-exit(r2);	 Catch:{ all -> 0x0035 }
        r0 = 0;
        goto L_0x002e;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0035 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.c.a.b.d(android.content.Context, int):android.content.res.ColorStateList");
    }

    /* renamed from: e */
    private static boolean m3942e(Context context, int i) {
        Resources resources = context.getResources();
        TypedValue a = C0825b.m3937a();
        resources.getValue(i, a, true);
        return a.type >= 28 && a.type <= 31;
    }
}
