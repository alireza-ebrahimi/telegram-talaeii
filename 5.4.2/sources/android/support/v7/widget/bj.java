package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

class bj extends ax {
    /* renamed from: a */
    private final WeakReference<Context> f3043a;

    public bj(Context context, Resources resources) {
        super(resources);
        this.f3043a = new WeakReference(context);
    }

    public Drawable getDrawable(int i) {
        Drawable drawable = super.getDrawable(i);
        Context context = (Context) this.f3043a.get();
        if (!(drawable == null || context == null)) {
            C1069l.m5865a();
            C1069l.m5871a(context, i, drawable);
        }
        return drawable;
    }
}
