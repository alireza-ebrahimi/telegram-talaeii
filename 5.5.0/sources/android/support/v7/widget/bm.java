package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.app.C0769e;
import java.lang.ref.WeakReference;

public class bm extends Resources {
    /* renamed from: a */
    private final WeakReference<Context> f3068a;

    public bm(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.f3068a = new WeakReference(context);
    }

    /* renamed from: a */
    public static boolean m5716a() {
        return C0769e.m3660k() && VERSION.SDK_INT <= 20;
    }

    /* renamed from: a */
    final Drawable m5717a(int i) {
        return super.getDrawable(i);
    }

    public Drawable getDrawable(int i) {
        Context context = (Context) this.f3068a.get();
        return context != null ? C1069l.m5865a().m5885a(context, this, i) : super.getDrawable(i);
    }
}
