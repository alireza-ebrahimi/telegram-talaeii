package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v4.view.bb;
import android.support.v4.view.bd;
import android.support.v7.app.C0765a.C0763b;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.view.C0842a;
import android.support.v7.view.C0850g;
import android.support.v7.view.C0852h;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.ActionBarOverlayLayout.C0816a;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ag;
import android.support.v7.widget.bc;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* renamed from: android.support.v7.app.r */
public class C0817r extends C0765a implements C0816a {
    /* renamed from: s */
    static final /* synthetic */ boolean f1900s = (!C0817r.class.desiredAssertionStatus());
    /* renamed from: t */
    private static final Interpolator f1901t = new AccelerateInterpolator();
    /* renamed from: u */
    private static final Interpolator f1902u = new DecelerateInterpolator();
    /* renamed from: v */
    private static final boolean f1903v;
    /* renamed from: A */
    private int f1904A = -1;
    /* renamed from: B */
    private boolean f1905B;
    /* renamed from: C */
    private boolean f1906C;
    /* renamed from: D */
    private ArrayList<C0763b> f1907D = new ArrayList();
    /* renamed from: E */
    private boolean f1908E;
    /* renamed from: F */
    private int f1909F = 0;
    /* renamed from: G */
    private boolean f1910G;
    /* renamed from: H */
    private boolean f1911H = true;
    /* renamed from: I */
    private boolean f1912I;
    /* renamed from: a */
    Context f1913a;
    /* renamed from: b */
    ActionBarOverlayLayout f1914b;
    /* renamed from: c */
    ActionBarContainer f1915c;
    /* renamed from: d */
    ag f1916d;
    /* renamed from: e */
    ActionBarContextView f1917e;
    /* renamed from: f */
    View f1918f;
    /* renamed from: g */
    bc f1919g;
    /* renamed from: h */
    C0815a f1920h;
    /* renamed from: i */
    C0814b f1921i;
    /* renamed from: j */
    C0797a f1922j;
    /* renamed from: k */
    boolean f1923k = true;
    /* renamed from: l */
    boolean f1924l;
    /* renamed from: m */
    boolean f1925m;
    /* renamed from: n */
    C0852h f1926n;
    /* renamed from: o */
    boolean f1927o;
    /* renamed from: p */
    final bb f1928p = new C08111(this);
    /* renamed from: q */
    final bb f1929q = new C08122(this);
    /* renamed from: r */
    final bd f1930r = new C08133(this);
    /* renamed from: w */
    private Context f1931w;
    /* renamed from: x */
    private Activity f1932x;
    /* renamed from: y */
    private Dialog f1933y;
    /* renamed from: z */
    private ArrayList<Object> f1934z = new ArrayList();

    /* renamed from: android.support.v7.app.r$1 */
    class C08111 extends android.support.v4.view.bc {
        /* renamed from: a */
        final /* synthetic */ C0817r f1890a;

        C08111(C0817r c0817r) {
            this.f1890a = c0817r;
        }

        public void onAnimationEnd(View view) {
            if (this.f1890a.f1923k && this.f1890a.f1918f != null) {
                ah.m2795b(this.f1890a.f1918f, (float) BitmapDescriptorFactory.HUE_RED);
                ah.m2795b(this.f1890a.f1915c, (float) BitmapDescriptorFactory.HUE_RED);
            }
            this.f1890a.f1915c.setVisibility(8);
            this.f1890a.f1915c.setTransitioning(false);
            this.f1890a.f1926n = null;
            this.f1890a.m3922i();
            if (this.f1890a.f1914b != null) {
                ah.m2834x(this.f1890a.f1914b);
            }
        }
    }

    /* renamed from: android.support.v7.app.r$2 */
    class C08122 extends android.support.v4.view.bc {
        /* renamed from: a */
        final /* synthetic */ C0817r f1891a;

        C08122(C0817r c0817r) {
            this.f1891a = c0817r;
        }

        public void onAnimationEnd(View view) {
            this.f1891a.f1926n = null;
            this.f1891a.f1915c.requestLayout();
        }
    }

    /* renamed from: android.support.v7.app.r$3 */
    class C08133 implements bd {
        /* renamed from: a */
        final /* synthetic */ C0817r f1892a;

        C08133(C0817r c0817r) {
            this.f1892a = c0817r;
        }

        /* renamed from: a */
        public void mo678a(View view) {
            ((View) this.f1892a.f1915c.getParent()).invalidate();
        }
    }

    /* renamed from: android.support.v7.app.r$a */
    public class C0815a extends C0814b implements C0777a {
        /* renamed from: a */
        final /* synthetic */ C0817r f1895a;
        /* renamed from: b */
        private final Context f1896b;
        /* renamed from: c */
        private final C0873h f1897c;
        /* renamed from: d */
        private C0797a f1898d;
        /* renamed from: e */
        private WeakReference<View> f1899e;

        public C0815a(C0817r c0817r, Context context, C0797a c0797a) {
            this.f1895a = c0817r;
            this.f1896b = context;
            this.f1898d = c0797a;
            this.f1897c = new C0873h(context).m4216a(1);
            this.f1897c.mo757a((C0777a) this);
        }

        /* renamed from: a */
        public MenuInflater mo679a() {
            return new C0850g(this.f1896b);
        }

        /* renamed from: a */
        public void mo680a(int i) {
            mo686b(this.f1895a.f1913a.getResources().getString(i));
        }

        /* renamed from: a */
        public void mo634a(C0873h c0873h) {
            if (this.f1898d != null) {
                mo688d();
                this.f1895a.f1917e.mo766a();
            }
        }

        /* renamed from: a */
        public void mo681a(View view) {
            this.f1895a.f1917e.setCustomView(view);
            this.f1899e = new WeakReference(view);
        }

        /* renamed from: a */
        public void mo682a(CharSequence charSequence) {
            this.f1895a.f1917e.setSubtitle(charSequence);
        }

        /* renamed from: a */
        public void mo683a(boolean z) {
            super.mo683a(z);
            this.f1895a.f1917e.setTitleOptional(z);
        }

        /* renamed from: a */
        public boolean mo638a(C0873h c0873h, MenuItem menuItem) {
            return this.f1898d != null ? this.f1898d.mo661a((C0814b) this, menuItem) : false;
        }

        /* renamed from: b */
        public Menu mo684b() {
            return this.f1897c;
        }

        /* renamed from: b */
        public void mo685b(int i) {
            mo682a(this.f1895a.f1913a.getResources().getString(i));
        }

        /* renamed from: b */
        public void mo686b(CharSequence charSequence) {
            this.f1895a.f1917e.setTitle(charSequence);
        }

        /* renamed from: c */
        public void mo687c() {
            if (this.f1895a.f1920h == this) {
                if (C0817r.m3895a(this.f1895a.f1924l, this.f1895a.f1925m, false)) {
                    this.f1898d.mo659a(this);
                } else {
                    this.f1895a.f1921i = this;
                    this.f1895a.f1922j = this.f1898d;
                }
                this.f1898d = null;
                this.f1895a.m3925j(false);
                this.f1895a.f1917e.m4368b();
                this.f1895a.f1916d.mo965a().sendAccessibilityEvent(32);
                this.f1895a.f1914b.setHideOnContentScrollEnabled(this.f1895a.f1927o);
                this.f1895a.f1920h = null;
            }
        }

        /* renamed from: d */
        public void mo688d() {
            if (this.f1895a.f1920h == this) {
                this.f1897c.m4250g();
                try {
                    this.f1898d.mo662b(this, this.f1897c);
                } finally {
                    this.f1897c.m4251h();
                }
            }
        }

        /* renamed from: e */
        public boolean m3883e() {
            this.f1897c.m4250g();
            try {
                boolean a = this.f1898d.mo660a((C0814b) this, this.f1897c);
                return a;
            } finally {
                this.f1897c.m4251h();
            }
        }

        /* renamed from: f */
        public CharSequence mo689f() {
            return this.f1895a.f1917e.getTitle();
        }

        /* renamed from: g */
        public CharSequence mo690g() {
            return this.f1895a.f1917e.getSubtitle();
        }

        /* renamed from: h */
        public boolean mo691h() {
            return this.f1895a.f1917e.m4370d();
        }

        /* renamed from: i */
        public View mo692i() {
            return this.f1899e != null ? (View) this.f1899e.get() : null;
        }
    }

    static {
        boolean z = true;
        if (VERSION.SDK_INT < 14) {
            z = false;
        }
        f1903v = z;
    }

    public C0817r(Activity activity, boolean z) {
        this.f1932x = activity;
        View decorView = activity.getWindow().getDecorView();
        m3894a(decorView);
        if (!z) {
            this.f1918f = decorView.findViewById(16908290);
        }
    }

    public C0817r(Dialog dialog) {
        this.f1933y = dialog;
        m3894a(dialog.getWindow().getDecorView());
    }

    /* renamed from: a */
    private void m3894a(View view) {
        this.f1914b = (ActionBarOverlayLayout) view.findViewById(C0743f.decor_content_parent);
        if (this.f1914b != null) {
            this.f1914b.setActionBarVisibilityCallback(this);
        }
        this.f1916d = m3896b(view.findViewById(C0743f.action_bar));
        this.f1917e = (ActionBarContextView) view.findViewById(C0743f.action_context_bar);
        this.f1915c = (ActionBarContainer) view.findViewById(C0743f.action_bar_container);
        if (this.f1916d == null || this.f1917e == null || this.f1915c == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with a compatible window decor layout");
        }
        this.f1913a = this.f1916d.mo974b();
        boolean z = (this.f1916d.mo991o() & 4) != 0;
        if (z) {
            this.f1905B = true;
        }
        C0842a a = C0842a.m4013a(this.f1913a);
        z = a.m4019f() || z;
        mo667a(z);
        m3897k(a.m4017d());
        TypedArray obtainStyledAttributes = this.f1913a.obtainStyledAttributes(null, C0747j.ActionBar, C0738a.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(C0747j.ActionBar_hideOnContentScroll, false)) {
            mo695b(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C0747j.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            mo664a((float) dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    /* renamed from: a */
    static boolean m3895a(boolean z, boolean z2, boolean z3) {
        return z3 ? true : (z || z2) ? false : true;
    }

    /* renamed from: b */
    private ag m3896b(View view) {
        if (view instanceof ag) {
            return (ag) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException(new StringBuilder().append("Can't make a decor toolbar out of ").append(view).toString() != null ? view.getClass().getSimpleName() : "null");
    }

    /* renamed from: k */
    private void m3897k(boolean z) {
        boolean z2 = true;
        this.f1908E = z;
        if (this.f1908E) {
            this.f1915c.setTabContainer(null);
            this.f1916d.mo969a(this.f1919g);
        } else {
            this.f1916d.mo969a(null);
            this.f1915c.setTabContainer(this.f1919g);
        }
        boolean z3 = m3924j() == 2;
        if (this.f1919g != null) {
            if (z3) {
                this.f1919g.setVisibility(0);
                if (this.f1914b != null) {
                    ah.m2834x(this.f1914b);
                }
            } else {
                this.f1919g.setVisibility(8);
            }
        }
        ag agVar = this.f1916d;
        boolean z4 = !this.f1908E && z3;
        agVar.mo973a(z4);
        ActionBarOverlayLayout actionBarOverlayLayout = this.f1914b;
        if (this.f1908E || !z3) {
            z2 = false;
        }
        actionBarOverlayLayout.setHasNonEmbeddedTabs(z2);
    }

    /* renamed from: l */
    private void m3898l(boolean z) {
        if (C0817r.m3895a(this.f1924l, this.f1925m, this.f1910G)) {
            if (!this.f1911H) {
                this.f1911H = true;
                m3921h(z);
            }
        } else if (this.f1911H) {
            this.f1911H = false;
            m3923i(z);
        }
    }

    /* renamed from: p */
    private void m3899p() {
        if (!this.f1910G) {
            this.f1910G = true;
            if (this.f1914b != null) {
                this.f1914b.setShowingForActionMode(true);
            }
            m3898l(false);
        }
    }

    /* renamed from: q */
    private void m3900q() {
        if (this.f1910G) {
            this.f1910G = false;
            if (this.f1914b != null) {
                this.f1914b.setShowingForActionMode(false);
            }
            m3898l(false);
        }
    }

    /* renamed from: r */
    private boolean m3901r() {
        return ah.m2767G(this.f1915c);
    }

    /* renamed from: a */
    public int mo663a() {
        return this.f1916d.mo991o();
    }

    /* renamed from: a */
    public C0814b mo693a(C0797a c0797a) {
        if (this.f1920h != null) {
            this.f1920h.mo687c();
        }
        this.f1914b.setHideOnContentScrollEnabled(false);
        this.f1917e.m4369c();
        C0814b c0815a = new C0815a(this, this.f1917e.getContext(), c0797a);
        if (!c0815a.m3883e()) {
            return null;
        }
        this.f1920h = c0815a;
        c0815a.mo688d();
        this.f1917e.m4366a(c0815a);
        m3925j(true);
        this.f1917e.sendAccessibilityEvent(32);
        return c0815a;
    }

    /* renamed from: a */
    public void mo664a(float f) {
        ah.m2821k(this.f1915c, f);
    }

    /* renamed from: a */
    public void mo694a(int i) {
        this.f1909F = i;
    }

    /* renamed from: a */
    public void m3906a(int i, int i2) {
        int o = this.f1916d.mo991o();
        if ((i2 & 4) != 0) {
            this.f1905B = true;
        }
        this.f1916d.mo977c((o & (i2 ^ -1)) | (i & i2));
    }

    /* renamed from: a */
    public void mo665a(Configuration configuration) {
        m3897k(C0842a.m4013a(this.f1913a).m4017d());
    }

    /* renamed from: a */
    public void mo666a(CharSequence charSequence) {
        this.f1916d.mo972a(charSequence);
    }

    /* renamed from: a */
    public void mo667a(boolean z) {
        this.f1916d.mo976b(z);
    }

    /* renamed from: b */
    public void mo695b(boolean z) {
        if (!z || this.f1914b.m4391a()) {
            this.f1927o = z;
            this.f1914b.setHideOnContentScrollEnabled(z);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    /* renamed from: b */
    public boolean mo669b() {
        int k = m3926k();
        return this.f1911H && (k == 0 || mo696d() < k);
    }

    /* renamed from: c */
    public Context mo670c() {
        if (this.f1931w == null) {
            TypedValue typedValue = new TypedValue();
            this.f1913a.getTheme().resolveAttribute(C0738a.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.f1931w = new ContextThemeWrapper(this.f1913a, i);
            } else {
                this.f1931w = this.f1913a;
            }
        }
        return this.f1931w;
    }

    /* renamed from: c */
    public void mo671c(boolean z) {
        if (!this.f1905B) {
            m3917f(z);
        }
    }

    /* renamed from: d */
    public int mo696d() {
        return this.f1914b.getActionBarHideOffset();
    }

    /* renamed from: d */
    public void mo672d(boolean z) {
        this.f1912I = z;
        if (!z && this.f1926n != null) {
            this.f1926n.m4068c();
        }
    }

    /* renamed from: e */
    public void mo673e(boolean z) {
        if (z != this.f1906C) {
            this.f1906C = z;
            int size = this.f1907D.size();
            for (int i = 0; i < size; i++) {
                ((C0763b) this.f1907D.get(i)).m3605a(z);
            }
        }
    }

    /* renamed from: f */
    public void m3917f(boolean z) {
        m3906a(z ? 4 : 0, 4);
    }

    /* renamed from: f */
    public boolean mo675f() {
        if (this.f1916d == null || !this.f1916d.mo978c()) {
            return false;
        }
        this.f1916d.mo979d();
        return true;
    }

    /* renamed from: g */
    public void mo697g(boolean z) {
        this.f1923k = z;
    }

    /* renamed from: g */
    public boolean mo676g() {
        ViewGroup a = this.f1916d.mo965a();
        if (a == null || a.hasFocus()) {
            return false;
        }
        a.requestFocus();
        return true;
    }

    /* renamed from: h */
    public void m3921h(boolean z) {
        if (this.f1926n != null) {
            this.f1926n.m4068c();
        }
        this.f1915c.setVisibility(0);
        if (this.f1909F == 0 && f1903v && (this.f1912I || z)) {
            ah.m2795b(this.f1915c, (float) BitmapDescriptorFactory.HUE_RED);
            float f = (float) (-this.f1915c.getHeight());
            if (z) {
                int[] iArr = new int[]{0, 0};
                this.f1915c.getLocationInWindow(iArr);
                f -= (float) iArr[1];
            }
            ah.m2795b(this.f1915c, f);
            C0852h c0852h = new C0852h();
            ax c = ah.m2827q(this.f1915c).m3028c(BitmapDescriptorFactory.HUE_RED);
            c.m3023a(this.f1930r);
            c0852h.m4062a(c);
            if (this.f1923k && this.f1918f != null) {
                ah.m2795b(this.f1918f, f);
                c0852h.m4062a(ah.m2827q(this.f1918f).m3028c(BitmapDescriptorFactory.HUE_RED));
            }
            c0852h.m4065a(f1902u);
            c0852h.m4061a(250);
            c0852h.m4064a(this.f1929q);
            this.f1926n = c0852h;
            c0852h.m4066a();
        } else {
            ah.m2800c(this.f1915c, 1.0f);
            ah.m2795b(this.f1915c, (float) BitmapDescriptorFactory.HUE_RED);
            if (this.f1923k && this.f1918f != null) {
                ah.m2795b(this.f1918f, (float) BitmapDescriptorFactory.HUE_RED);
            }
            this.f1929q.onAnimationEnd(null);
        }
        if (this.f1914b != null) {
            ah.m2834x(this.f1914b);
        }
    }

    /* renamed from: i */
    void m3922i() {
        if (this.f1922j != null) {
            this.f1922j.mo659a(this.f1921i);
            this.f1921i = null;
            this.f1922j = null;
        }
    }

    /* renamed from: i */
    public void m3923i(boolean z) {
        if (this.f1926n != null) {
            this.f1926n.m4068c();
        }
        if (this.f1909F == 0 && f1903v && (this.f1912I || z)) {
            ah.m2800c(this.f1915c, 1.0f);
            this.f1915c.setTransitioning(true);
            C0852h c0852h = new C0852h();
            float f = (float) (-this.f1915c.getHeight());
            if (z) {
                int[] iArr = new int[]{0, 0};
                this.f1915c.getLocationInWindow(iArr);
                f -= (float) iArr[1];
            }
            ax c = ah.m2827q(this.f1915c).m3028c(f);
            c.m3023a(this.f1930r);
            c0852h.m4062a(c);
            if (this.f1923k && this.f1918f != null) {
                c0852h.m4062a(ah.m2827q(this.f1918f).m3028c(f));
            }
            c0852h.m4065a(f1901t);
            c0852h.m4061a(250);
            c0852h.m4064a(this.f1928p);
            this.f1926n = c0852h;
            c0852h.m4066a();
            return;
        }
        this.f1928p.onAnimationEnd(null);
    }

    /* renamed from: j */
    public int m3924j() {
        return this.f1916d.mo992p();
    }

    /* renamed from: j */
    public void m3925j(boolean z) {
        if (z) {
            m3899p();
        } else {
            m3900q();
        }
        if (m3901r()) {
            ax a;
            ax a2;
            if (z) {
                a = this.f1916d.mo964a(4, 100);
                a2 = this.f1917e.mo765a(0, 200);
            } else {
                a2 = this.f1916d.mo964a(0, 200);
                a = this.f1917e.mo765a(8, 100);
            }
            C0852h c0852h = new C0852h();
            c0852h.m4063a(a, a2);
            c0852h.m4066a();
        } else if (z) {
            this.f1916d.mo980d(4);
            this.f1917e.setVisibility(0);
        } else {
            this.f1916d.mo980d(0);
            this.f1917e.setVisibility(8);
        }
    }

    /* renamed from: k */
    public int m3926k() {
        return this.f1915c.getHeight();
    }

    /* renamed from: l */
    public void mo698l() {
        if (this.f1925m) {
            this.f1925m = false;
            m3898l(true);
        }
    }

    /* renamed from: m */
    public void mo699m() {
        if (!this.f1925m) {
            this.f1925m = true;
            m3898l(true);
        }
    }

    /* renamed from: n */
    public void mo700n() {
        if (this.f1926n != null) {
            this.f1926n.m4068c();
            this.f1926n = null;
        }
    }

    /* renamed from: o */
    public void mo701o() {
    }
}
