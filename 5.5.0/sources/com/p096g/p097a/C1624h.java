package com.p096g.p097a;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.p096g.p097a.C1636m.C1632b;

/* renamed from: com.g.a.h */
class C1624h extends C1607a<ImageView> {
    /* renamed from: m */
    C1618d f4961m;

    C1624h(C1636m c1636m, ImageView imageView, C1640o c1640o, int i, int i2, int i3, Drawable drawable, String str, Object obj, C1618d c1618d, boolean z) {
        super(c1636m, imageView, c1640o, i, i2, i3, drawable, str, obj, z);
        this.f4961m = c1618d;
    }

    /* renamed from: a */
    public void mo1247a() {
        ImageView imageView = (ImageView) this.c.get();
        if (imageView != null) {
            if (this.g != 0) {
                imageView.setImageResource(this.g);
            } else if (this.h != null) {
                imageView.setImageDrawable(this.h);
            }
            if (this.f4961m != null) {
                this.f4961m.m7986b();
            }
        }
    }

    /* renamed from: a */
    public void mo1248a(Bitmap bitmap, C1632b c1632b) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
        }
        ImageView imageView = (ImageView) this.c.get();
        if (imageView != null) {
            Bitmap bitmap2 = bitmap;
            C1632b c1632b2 = c1632b;
            C1637n.m8022a(imageView, this.a.f4990c, bitmap2, c1632b2, this.d, this.a.f4997j);
            if (this.f4961m != null) {
                this.f4961m.m7985a();
            }
        }
    }

    /* renamed from: b */
    void mo1249b() {
        super.mo1249b();
        if (this.f4961m != null) {
            this.f4961m = null;
        }
    }
}
