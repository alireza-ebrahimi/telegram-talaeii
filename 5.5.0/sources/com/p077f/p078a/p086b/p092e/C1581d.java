package com.p077f.p078a.p086b.p092e;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.p077f.p078a.p086b.p087a.C1557h;
import com.p077f.p078a.p095c.C1602c;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* renamed from: com.f.a.b.e.d */
public abstract class C1581d implements C1580a {
    /* renamed from: a */
    protected Reference<View> f4834a;
    /* renamed from: b */
    protected boolean f4835b;

    public C1581d(View view) {
        this(view, true);
    }

    public C1581d(View view, boolean z) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        this.f4834a = new WeakReference(view);
        this.f4835b = z;
    }

    /* renamed from: a */
    public int mo1228a() {
        View view = (View) this.f4834a.get();
        if (view == null) {
            return 0;
        }
        LayoutParams layoutParams = view.getLayoutParams();
        int width = (!this.f4835b || layoutParams == null || layoutParams.width == -2) ? 0 : view.getWidth();
        return (width > 0 || layoutParams == null) ? width : layoutParams.width;
    }

    /* renamed from: a */
    protected abstract void mo1236a(Bitmap bitmap, View view);

    /* renamed from: a */
    protected abstract void mo1237a(Drawable drawable, View view);

    /* renamed from: a */
    public boolean mo1229a(Bitmap bitmap) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            View view = (View) this.f4834a.get();
            if (view != null) {
                mo1236a(bitmap, view);
                return true;
            }
        }
        C1602c.m7941c("Can't set a bitmap into view. You should call ImageLoader on UI thread for it.", new Object[0]);
        return false;
    }

    /* renamed from: a */
    public boolean mo1230a(Drawable drawable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            View view = (View) this.f4834a.get();
            if (view != null) {
                mo1237a(drawable, view);
                return true;
            }
        }
        C1602c.m7941c("Can't set a drawable into view. You should call ImageLoader on UI thread for it.", new Object[0]);
        return false;
    }

    /* renamed from: b */
    public int mo1231b() {
        View view = (View) this.f4834a.get();
        if (view == null) {
            return 0;
        }
        LayoutParams layoutParams = view.getLayoutParams();
        int height = (!this.f4835b || layoutParams == null || layoutParams.height == -2) ? 0 : view.getHeight();
        return (height > 0 || layoutParams == null) ? height : layoutParams.height;
    }

    /* renamed from: c */
    public C1557h mo1232c() {
        return C1557h.CROP;
    }

    /* renamed from: d */
    public View mo1233d() {
        return (View) this.f4834a.get();
    }

    /* renamed from: e */
    public boolean mo1234e() {
        return this.f4834a.get() == null;
    }

    /* renamed from: f */
    public int mo1235f() {
        View view = (View) this.f4834a.get();
        return view == null ? super.hashCode() : view.hashCode();
    }
}
