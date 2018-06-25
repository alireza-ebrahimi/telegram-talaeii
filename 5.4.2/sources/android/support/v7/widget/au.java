package android.support.v7.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView.C0908i;
import android.support.v7.widget.RecyclerView.C0910h;
import android.view.View;

public abstract class au {
    /* renamed from: a */
    protected final C0910h f2931a;
    /* renamed from: b */
    final Rect f2932b;
    /* renamed from: c */
    private int f2933c;

    private au(C0910h c0910h) {
        this.f2933c = Integer.MIN_VALUE;
        this.f2932b = new Rect();
        this.f2931a = c0910h;
    }

    /* renamed from: a */
    public static au m5516a(C0910h c0910h) {
        return new au(c0910h) {
            /* renamed from: a */
            public int mo944a(View view) {
                return this.a.m4595h(view) - ((C0908i) view.getLayoutParams()).leftMargin;
            }

            /* renamed from: a */
            public void mo945a(int i) {
                this.a.mo880i(i);
            }

            /* renamed from: b */
            public int mo946b(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.rightMargin + this.a.m4599j(view);
            }

            /* renamed from: c */
            public int mo947c() {
                return this.a.m4491B();
            }

            /* renamed from: c */
            public int mo948c(View view) {
                this.a.m4534a(view, true, this.b);
                return this.b.right;
            }

            /* renamed from: d */
            public int mo949d() {
                return this.a.m4618z() - this.a.m4493D();
            }

            /* renamed from: d */
            public int mo950d(View view) {
                this.a.m4534a(view, true, this.b);
                return this.b.left;
            }

            /* renamed from: e */
            public int mo951e() {
                return this.a.m4618z();
            }

            /* renamed from: e */
            public int mo952e(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.rightMargin + (this.a.m4586f(view) + c0908i.leftMargin);
            }

            /* renamed from: f */
            public int mo953f() {
                return (this.a.m4618z() - this.a.m4491B()) - this.a.m4493D();
            }

            /* renamed from: f */
            public int mo954f(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.bottomMargin + (this.a.m4591g(view) + c0908i.topMargin);
            }

            /* renamed from: g */
            public int mo955g() {
                return this.a.m4493D();
            }

            /* renamed from: h */
            public int mo956h() {
                return this.a.m4616x();
            }

            /* renamed from: i */
            public int mo957i() {
                return this.a.m4617y();
            }
        };
    }

    /* renamed from: a */
    public static au m5517a(C0910h c0910h, int i) {
        switch (i) {
            case 0:
                return m5516a(c0910h);
            case 1:
                return m5518b(c0910h);
            default:
                throw new IllegalArgumentException("invalid orientation");
        }
    }

    /* renamed from: b */
    public static au m5518b(C0910h c0910h) {
        return new au(c0910h) {
            /* renamed from: a */
            public int mo944a(View view) {
                return this.a.m4597i(view) - ((C0908i) view.getLayoutParams()).topMargin;
            }

            /* renamed from: a */
            public void mo945a(int i) {
                this.a.mo881j(i);
            }

            /* renamed from: b */
            public int mo946b(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.bottomMargin + this.a.m4601k(view);
            }

            /* renamed from: c */
            public int mo947c() {
                return this.a.m4492C();
            }

            /* renamed from: c */
            public int mo948c(View view) {
                this.a.m4534a(view, true, this.b);
                return this.b.bottom;
            }

            /* renamed from: d */
            public int mo949d() {
                return this.a.m4490A() - this.a.m4494E();
            }

            /* renamed from: d */
            public int mo950d(View view) {
                this.a.m4534a(view, true, this.b);
                return this.b.top;
            }

            /* renamed from: e */
            public int mo951e() {
                return this.a.m4490A();
            }

            /* renamed from: e */
            public int mo952e(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.bottomMargin + (this.a.m4591g(view) + c0908i.topMargin);
            }

            /* renamed from: f */
            public int mo953f() {
                return (this.a.m4490A() - this.a.m4492C()) - this.a.m4494E();
            }

            /* renamed from: f */
            public int mo954f(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.rightMargin + (this.a.m4586f(view) + c0908i.leftMargin);
            }

            /* renamed from: g */
            public int mo955g() {
                return this.a.m4494E();
            }

            /* renamed from: h */
            public int mo956h() {
                return this.a.m4617y();
            }

            /* renamed from: i */
            public int mo957i() {
                return this.a.m4616x();
            }
        };
    }

    /* renamed from: a */
    public abstract int mo944a(View view);

    /* renamed from: a */
    public void m5520a() {
        this.f2933c = mo953f();
    }

    /* renamed from: a */
    public abstract void mo945a(int i);

    /* renamed from: b */
    public int m5522b() {
        return Integer.MIN_VALUE == this.f2933c ? 0 : mo953f() - this.f2933c;
    }

    /* renamed from: b */
    public abstract int mo946b(View view);

    /* renamed from: c */
    public abstract int mo947c();

    /* renamed from: c */
    public abstract int mo948c(View view);

    /* renamed from: d */
    public abstract int mo949d();

    /* renamed from: d */
    public abstract int mo950d(View view);

    /* renamed from: e */
    public abstract int mo951e();

    /* renamed from: e */
    public abstract int mo952e(View view);

    /* renamed from: f */
    public abstract int mo953f();

    /* renamed from: f */
    public abstract int mo954f(View view);

    /* renamed from: g */
    public abstract int mo955g();

    /* renamed from: h */
    public abstract int mo956h();

    /* renamed from: i */
    public abstract int mo957i();
}
