package com.p077f.p078a.p086b.p092e;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p087a.C1557h;

/* renamed from: com.f.a.b.e.c */
public class C1583c implements C1580a {
    /* renamed from: a */
    protected final String f4836a;
    /* renamed from: b */
    protected final C1553e f4837b;
    /* renamed from: c */
    protected final C1557h f4838c;

    public C1583c(String str, C1553e c1553e, C1557h c1557h) {
        if (c1553e == null) {
            throw new IllegalArgumentException("imageSize must not be null");
        } else if (c1557h == null) {
            throw new IllegalArgumentException("scaleType must not be null");
        } else {
            this.f4836a = str;
            this.f4837b = c1553e;
            this.f4838c = c1557h;
        }
    }

    /* renamed from: a */
    public int mo1228a() {
        return this.f4837b.m7673a();
    }

    /* renamed from: a */
    public boolean mo1229a(Bitmap bitmap) {
        return true;
    }

    /* renamed from: a */
    public boolean mo1230a(Drawable drawable) {
        return true;
    }

    /* renamed from: b */
    public int mo1231b() {
        return this.f4837b.m7676b();
    }

    /* renamed from: c */
    public C1557h mo1232c() {
        return this.f4838c;
    }

    /* renamed from: d */
    public View mo1233d() {
        return null;
    }

    /* renamed from: e */
    public boolean mo1234e() {
        return false;
    }

    /* renamed from: f */
    public int mo1235f() {
        return TextUtils.isEmpty(this.f4836a) ? super.hashCode() : this.f4836a.hashCode();
    }
}
