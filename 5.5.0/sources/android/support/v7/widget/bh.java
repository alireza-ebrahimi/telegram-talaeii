package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class bh extends ContextWrapper {
    /* renamed from: a */
    private static final Object f3035a = new Object();
    /* renamed from: b */
    private static ArrayList<WeakReference<bh>> f3036b;
    /* renamed from: c */
    private final Resources f3037c;
    /* renamed from: d */
    private final Theme f3038d;

    private bh(Context context) {
        super(context);
        if (bm.m5716a()) {
            this.f3037c = new bm(this, context.getResources());
            this.f3038d = this.f3037c.newTheme();
            this.f3038d.setTo(context.getTheme());
            return;
        }
        this.f3037c = new bj(this, context.getResources());
        this.f3038d = null;
    }

    /* renamed from: a */
    public static Context m5649a(Context context) {
        if (!m5650b(context)) {
            return context;
        }
        synchronized (f3035a) {
            Context context2;
            if (f3036b == null) {
                f3036b = new ArrayList();
            } else {
                int size;
                WeakReference weakReference;
                for (size = f3036b.size() - 1; size >= 0; size--) {
                    weakReference = (WeakReference) f3036b.get(size);
                    if (weakReference == null || weakReference.get() == null) {
                        f3036b.remove(size);
                    }
                }
                size = f3036b.size() - 1;
                while (size >= 0) {
                    weakReference = (WeakReference) f3036b.get(size);
                    context2 = weakReference != null ? (bh) weakReference.get() : null;
                    if (context2 == null || context2.getBaseContext() != context) {
                        size--;
                    } else {
                        return context2;
                    }
                }
            }
            context2 = new bh(context);
            f3036b.add(new WeakReference(context2));
            return context2;
        }
    }

    /* renamed from: b */
    private static boolean m5650b(Context context) {
        return ((context instanceof bh) || (context.getResources() instanceof bj) || (context.getResources() instanceof bm)) ? false : VERSION.SDK_INT < 21 || bm.m5716a();
    }

    public AssetManager getAssets() {
        return this.f3037c.getAssets();
    }

    public Resources getResources() {
        return this.f3037c;
    }

    public Theme getTheme() {
        return this.f3038d == null ? super.getTheme() : this.f3038d;
    }

    public void setTheme(int i) {
        if (this.f3038d == null) {
            super.setTheme(i);
        } else {
            this.f3038d.applyStyle(i, true);
        }
    }
}
