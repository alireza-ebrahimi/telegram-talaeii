package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0741d;
import android.support.v7.view.menu.C0859o.C0794a;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow.OnDismissListener;

/* renamed from: android.support.v7.view.menu.n */
public class C0885n {
    /* renamed from: a */
    private final Context f2240a;
    /* renamed from: b */
    private final C0873h f2241b;
    /* renamed from: c */
    private final boolean f2242c;
    /* renamed from: d */
    private final int f2243d;
    /* renamed from: e */
    private final int f2244e;
    /* renamed from: f */
    private View f2245f;
    /* renamed from: g */
    private int f2246g;
    /* renamed from: h */
    private boolean f2247h;
    /* renamed from: i */
    private C0794a f2248i;
    /* renamed from: j */
    private C0868m f2249j;
    /* renamed from: k */
    private OnDismissListener f2250k;
    /* renamed from: l */
    private final OnDismissListener f2251l;

    /* renamed from: android.support.v7.view.menu.n$1 */
    class C08841 implements OnDismissListener {
        /* renamed from: a */
        final /* synthetic */ C0885n f2239a;

        C08841(C0885n c0885n) {
            this.f2239a = c0885n;
        }

        public void onDismiss() {
            this.f2239a.mo995e();
        }
    }

    public C0885n(Context context, C0873h c0873h, View view, boolean z, int i) {
        this(context, c0873h, view, z, i, 0);
    }

    public C0885n(Context context, C0873h c0873h, View view, boolean z, int i, int i2) {
        this.f2246g = 8388611;
        this.f2251l = new C08841(this);
        this.f2240a = context;
        this.f2241b = c0873h;
        this.f2245f = view;
        this.f2242c = z;
        this.f2243d = i;
        this.f2244e = i2;
    }

    /* renamed from: a */
    private void m4310a(int i, int i2, boolean z, boolean z2) {
        C0868m b = m4319b();
        b.mo738c(z2);
        if (z) {
            if ((C0625f.m3120a(this.f2246g, ah.m2812g(this.f2245f)) & 7) == 5) {
                i -= this.f2245f.getWidth();
            }
            b.mo735b(i);
            b.mo737c(i2);
            int i3 = (int) ((this.f2240a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            b.m4154a(new Rect(i - i3, i2 - i3, i + i3, i3 + i2));
        }
        b.mo729a();
    }

    /* renamed from: g */
    private C0868m m4311g() {
        Display defaultDisplay = ((WindowManager) this.f2240a.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else if (VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
        } else {
            point.set(defaultDisplay.getWidth(), defaultDisplay.getHeight());
        }
        C0868m c0869e = (Math.min(point.x, point.y) >= this.f2240a.getResources().getDimensionPixelSize(C0741d.abc_cascading_menus_min_smallest_width) ? 1 : null) != null ? new C0869e(this.f2240a, this.f2245f, this.f2243d, this.f2244e, this.f2242c) : new C0889t(this.f2240a, this.f2241b, this.f2245f, this.f2243d, this.f2244e, this.f2242c);
        c0869e.mo731a(this.f2241b);
        c0869e.mo733a(this.f2251l);
        c0869e.mo732a(this.f2245f);
        c0869e.mo721a(this.f2248i);
        c0869e.mo734a(this.f2247h);
        c0869e.mo730a(this.f2246g);
        return c0869e;
    }

    /* renamed from: a */
    public void m4312a() {
        if (!m4320c()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    /* renamed from: a */
    public void m4313a(int i) {
        this.f2246g = i;
    }

    /* renamed from: a */
    public void m4314a(C0794a c0794a) {
        this.f2248i = c0794a;
        if (this.f2249j != null) {
            this.f2249j.mo721a(c0794a);
        }
    }

    /* renamed from: a */
    public void m4315a(View view) {
        this.f2245f = view;
    }

    /* renamed from: a */
    public void m4316a(OnDismissListener onDismissListener) {
        this.f2250k = onDismissListener;
    }

    /* renamed from: a */
    public void m4317a(boolean z) {
        this.f2247h = z;
        if (this.f2249j != null) {
            this.f2249j.mo734a(z);
        }
    }

    /* renamed from: a */
    public boolean m4318a(int i, int i2) {
        if (m4323f()) {
            return true;
        }
        if (this.f2245f == null) {
            return false;
        }
        m4310a(i, i2, true, true);
        return true;
    }

    /* renamed from: b */
    public C0868m m4319b() {
        if (this.f2249j == null) {
            this.f2249j = m4311g();
        }
        return this.f2249j;
    }

    /* renamed from: c */
    public boolean m4320c() {
        if (m4323f()) {
            return true;
        }
        if (this.f2245f == null) {
            return false;
        }
        m4310a(0, 0, false, false);
        return true;
    }

    /* renamed from: d */
    public void m4321d() {
        if (m4323f()) {
            this.f2249j.mo736c();
        }
    }

    /* renamed from: e */
    protected void mo995e() {
        this.f2249j = null;
        if (this.f2250k != null) {
            this.f2250k.onDismiss();
        }
    }

    /* renamed from: f */
    public boolean m4323f() {
        return this.f2249j != null && this.f2249j.mo739d();
    }
}
