package com.p077f.p078a.p086b.p092e;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.p077f.p078a.p086b.p087a.C1557h;
import com.p077f.p078a.p095c.C1602c;
import java.lang.reflect.Field;

/* renamed from: com.f.a.b.e.b */
public class C1582b extends C1581d {
    public C1582b(ImageView imageView) {
        super(imageView);
    }

    /* renamed from: a */
    private static int m7866a(Object obj, String str) {
        try {
            Field declaredField = ImageView.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            int intValue = ((Integer) declaredField.get(obj)).intValue();
            if (intValue > 0 && intValue < Integer.MAX_VALUE) {
                return intValue;
            }
        } catch (Throwable e) {
            C1602c.m7937a(e);
        }
        return 0;
    }

    /* renamed from: a */
    public int mo1228a() {
        int a = super.mo1228a();
        if (a <= 0) {
            Object obj = (ImageView) this.a.get();
            if (obj != null) {
                return C1582b.m7866a(obj, "mMaxWidth");
            }
        }
        return a;
    }

    /* renamed from: a */
    protected void mo1236a(Bitmap bitmap, View view) {
        ((ImageView) view).setImageBitmap(bitmap);
    }

    /* renamed from: a */
    protected void mo1237a(Drawable drawable, View view) {
        ((ImageView) view).setImageDrawable(drawable);
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }
    }

    /* renamed from: b */
    public int mo1231b() {
        int b = super.mo1231b();
        if (b <= 0) {
            Object obj = (ImageView) this.a.get();
            if (obj != null) {
                return C1582b.m7866a(obj, "mMaxHeight");
            }
        }
        return b;
    }

    /* renamed from: c */
    public C1557h mo1232c() {
        ImageView imageView = (ImageView) this.a.get();
        return imageView != null ? C1557h.m7677a(imageView) : super.mo1232c();
    }

    /* renamed from: d */
    public /* synthetic */ View mo1233d() {
        return m7873g();
    }

    /* renamed from: g */
    public ImageView m7873g() {
        return (ImageView) super.mo1233d();
    }
}
