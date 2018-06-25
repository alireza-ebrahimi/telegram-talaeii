package com.p072e.p075c.p076a;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* renamed from: com.e.c.a.a */
public final class C1518a extends Animation {
    /* renamed from: a */
    public static final boolean f4590a = (Integer.valueOf(VERSION.SDK).intValue() < 11);
    /* renamed from: b */
    private static final WeakHashMap<View, C1518a> f4591b = new WeakHashMap();
    /* renamed from: c */
    private final WeakReference<View> f4592c;
    /* renamed from: d */
    private final Camera f4593d = new Camera();
    /* renamed from: e */
    private boolean f4594e;
    /* renamed from: f */
    private float f4595f = 1.0f;
    /* renamed from: g */
    private float f4596g;
    /* renamed from: h */
    private float f4597h;
    /* renamed from: i */
    private float f4598i;
    /* renamed from: j */
    private float f4599j;
    /* renamed from: k */
    private float f4600k;
    /* renamed from: l */
    private float f4601l = 1.0f;
    /* renamed from: m */
    private float f4602m = 1.0f;
    /* renamed from: n */
    private float f4603n;
    /* renamed from: o */
    private float f4604o;
    /* renamed from: p */
    private final RectF f4605p = new RectF();
    /* renamed from: q */
    private final RectF f4606q = new RectF();
    /* renamed from: r */
    private final Matrix f4607r = new Matrix();

    private C1518a(View view) {
        setDuration(0);
        setFillAfter(true);
        view.setAnimation(this);
        this.f4592c = new WeakReference(view);
    }

    /* renamed from: a */
    public static C1518a m7531a(View view) {
        Animation animation = (C1518a) f4591b.get(view);
        if (animation != null && animation == view.getAnimation()) {
            return animation;
        }
        C1518a c1518a = new C1518a(view);
        f4591b.put(view, c1518a);
        return c1518a;
    }

    /* renamed from: a */
    private void m7532a(Matrix matrix, View view) {
        float width = (float) view.getWidth();
        float height = (float) view.getHeight();
        boolean z = this.f4594e;
        float f = z ? this.f4596g : width / 2.0f;
        float f2 = z ? this.f4597h : height / 2.0f;
        float f3 = this.f4598i;
        float f4 = this.f4599j;
        float f5 = this.f4600k;
        if (!(f3 == BitmapDescriptorFactory.HUE_RED && f4 == BitmapDescriptorFactory.HUE_RED && f5 == BitmapDescriptorFactory.HUE_RED)) {
            Camera camera = this.f4593d;
            camera.save();
            camera.rotateX(f3);
            camera.rotateY(f4);
            camera.rotateZ(-f5);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-f, -f2);
            matrix.postTranslate(f, f2);
        }
        f3 = this.f4601l;
        f4 = this.f4602m;
        if (!(f3 == 1.0f && f4 == 1.0f)) {
            matrix.postScale(f3, f4);
            matrix.postTranslate((-(f / width)) * ((f3 * width) - width), (-(f2 / height)) * ((f4 * height) - height));
        }
        matrix.postTranslate(this.f4603n, this.f4604o);
    }

    /* renamed from: a */
    private void m7533a(RectF rectF, View view) {
        rectF.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) view.getWidth(), (float) view.getHeight());
        Matrix matrix = this.f4607r;
        matrix.reset();
        m7532a(matrix, view);
        this.f4607r.mapRect(rectF);
        rectF.offset((float) view.getLeft(), (float) view.getTop());
        if (rectF.right < rectF.left) {
            float f = rectF.right;
            rectF.right = rectF.left;
            rectF.left = f;
        }
        if (rectF.bottom < rectF.top) {
            f = rectF.top;
            rectF.top = rectF.bottom;
            rectF.bottom = f;
        }
    }

    /* renamed from: o */
    private void m7534o() {
        View view = (View) this.f4592c.get();
        if (view != null) {
            m7533a(this.f4605p, view);
        }
    }

    /* renamed from: p */
    private void m7535p() {
        View view = (View) this.f4592c.get();
        if (view != null && view.getParent() != null) {
            RectF rectF = this.f4606q;
            m7533a(rectF, view);
            rectF.union(this.f4605p);
            ((View) view.getParent()).invalidate((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
        }
    }

    /* renamed from: a */
    public float m7536a() {
        return this.f4595f;
    }

    /* renamed from: a */
    public void m7537a(float f) {
        if (this.f4595f != f) {
            this.f4595f = f;
            View view = (View) this.f4592c.get();
            if (view != null) {
                view.invalidate();
            }
        }
    }

    /* renamed from: a */
    public void m7538a(int i) {
        View view = (View) this.f4592c.get();
        if (view != null) {
            view.scrollTo(i, view.getScrollY());
        }
    }

    protected void applyTransformation(float f, Transformation transformation) {
        View view = (View) this.f4592c.get();
        if (view != null) {
            transformation.setAlpha(this.f4595f);
            m7532a(transformation.getMatrix(), view);
        }
    }

    /* renamed from: b */
    public float m7539b() {
        return this.f4596g;
    }

    /* renamed from: b */
    public void m7540b(float f) {
        if (!this.f4594e || this.f4596g != f) {
            m7534o();
            this.f4594e = true;
            this.f4596g = f;
            m7535p();
        }
    }

    /* renamed from: b */
    public void m7541b(int i) {
        View view = (View) this.f4592c.get();
        if (view != null) {
            view.scrollTo(view.getScrollX(), i);
        }
    }

    /* renamed from: c */
    public float m7542c() {
        return this.f4597h;
    }

    /* renamed from: c */
    public void m7543c(float f) {
        if (!this.f4594e || this.f4597h != f) {
            m7534o();
            this.f4594e = true;
            this.f4597h = f;
            m7535p();
        }
    }

    /* renamed from: d */
    public float m7544d() {
        return this.f4600k;
    }

    /* renamed from: d */
    public void m7545d(float f) {
        if (this.f4600k != f) {
            m7534o();
            this.f4600k = f;
            m7535p();
        }
    }

    /* renamed from: e */
    public float m7546e() {
        return this.f4598i;
    }

    /* renamed from: e */
    public void m7547e(float f) {
        if (this.f4598i != f) {
            m7534o();
            this.f4598i = f;
            m7535p();
        }
    }

    /* renamed from: f */
    public float m7548f() {
        return this.f4599j;
    }

    /* renamed from: f */
    public void m7549f(float f) {
        if (this.f4599j != f) {
            m7534o();
            this.f4599j = f;
            m7535p();
        }
    }

    /* renamed from: g */
    public float m7550g() {
        return this.f4601l;
    }

    /* renamed from: g */
    public void m7551g(float f) {
        if (this.f4601l != f) {
            m7534o();
            this.f4601l = f;
            m7535p();
        }
    }

    /* renamed from: h */
    public float m7552h() {
        return this.f4602m;
    }

    /* renamed from: h */
    public void m7553h(float f) {
        if (this.f4602m != f) {
            m7534o();
            this.f4602m = f;
            m7535p();
        }
    }

    /* renamed from: i */
    public int m7554i() {
        View view = (View) this.f4592c.get();
        return view == null ? 0 : view.getScrollX();
    }

    /* renamed from: i */
    public void m7555i(float f) {
        if (this.f4603n != f) {
            m7534o();
            this.f4603n = f;
            m7535p();
        }
    }

    /* renamed from: j */
    public int m7556j() {
        View view = (View) this.f4592c.get();
        return view == null ? 0 : view.getScrollY();
    }

    /* renamed from: j */
    public void m7557j(float f) {
        if (this.f4604o != f) {
            m7534o();
            this.f4604o = f;
            m7535p();
        }
    }

    /* renamed from: k */
    public float m7558k() {
        return this.f4603n;
    }

    /* renamed from: k */
    public void m7559k(float f) {
        View view = (View) this.f4592c.get();
        if (view != null) {
            m7555i(f - ((float) view.getLeft()));
        }
    }

    /* renamed from: l */
    public float m7560l() {
        return this.f4604o;
    }

    /* renamed from: l */
    public void m7561l(float f) {
        View view = (View) this.f4592c.get();
        if (view != null) {
            m7557j(f - ((float) view.getTop()));
        }
    }

    /* renamed from: m */
    public float m7562m() {
        View view = (View) this.f4592c.get();
        return view == null ? BitmapDescriptorFactory.HUE_RED : ((float) view.getLeft()) + this.f4603n;
    }

    /* renamed from: n */
    public float m7563n() {
        View view = (View) this.f4592c.get();
        return view == null ? BitmapDescriptorFactory.HUE_RED : ((float) view.getTop()) + this.f4604o;
    }
}
