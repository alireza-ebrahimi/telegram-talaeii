package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.p025a.C0748a.C0741d;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.widget.as;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/* renamed from: android.support.v7.view.menu.t */
final class C0889t extends C0868m implements C0859o, OnKeyListener, OnItemClickListener, OnDismissListener {
    /* renamed from: a */
    final as f2253a;
    /* renamed from: b */
    View f2254b;
    /* renamed from: c */
    private final Context f2255c;
    /* renamed from: d */
    private final C0873h f2256d;
    /* renamed from: e */
    private final C0872g f2257e;
    /* renamed from: f */
    private final boolean f2258f;
    /* renamed from: g */
    private final int f2259g;
    /* renamed from: h */
    private final int f2260h;
    /* renamed from: i */
    private final int f2261i;
    /* renamed from: j */
    private final OnGlobalLayoutListener f2262j = new C08881(this);
    /* renamed from: k */
    private OnDismissListener f2263k;
    /* renamed from: l */
    private View f2264l;
    /* renamed from: m */
    private C0794a f2265m;
    /* renamed from: n */
    private ViewTreeObserver f2266n;
    /* renamed from: o */
    private boolean f2267o;
    /* renamed from: p */
    private boolean f2268p;
    /* renamed from: q */
    private int f2269q;
    /* renamed from: r */
    private int f2270r = 0;
    /* renamed from: s */
    private boolean f2271s;

    /* renamed from: android.support.v7.view.menu.t$1 */
    class C08881 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ C0889t f2252a;

        C08881(C0889t c0889t) {
            this.f2252a = c0889t;
        }

        public void onGlobalLayout() {
            if (this.f2252a.mo739d() && !this.f2252a.f2253a.m5489g()) {
                View view = this.f2252a.f2254b;
                if (view == null || !view.isShown()) {
                    this.f2252a.mo736c();
                } else {
                    this.f2252a.f2253a.mo729a();
                }
            }
        }
    }

    public C0889t(Context context, C0873h c0873h, View view, int i, int i2, boolean z) {
        this.f2255c = context;
        this.f2256d = c0873h;
        this.f2258f = z;
        this.f2257e = new C0872g(c0873h, LayoutInflater.from(context), this.f2258f);
        this.f2260h = i;
        this.f2261i = i2;
        Resources resources = context.getResources();
        this.f2259g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C0741d.abc_config_prefDialogWidth));
        this.f2264l = view;
        this.f2253a = new as(this.f2255c, null, this.f2260h, this.f2261i);
        c0873h.m4227a((C0859o) this, context);
    }

    /* renamed from: h */
    private boolean m4327h() {
        if (mo739d()) {
            return true;
        }
        if (this.f2267o || this.f2264l == null) {
            return false;
        }
        this.f2254b = this.f2264l;
        this.f2253a.m5477a((OnDismissListener) this);
        this.f2253a.m5475a((OnItemClickListener) this);
        this.f2253a.m5478a(true);
        View view = this.f2254b;
        boolean z = this.f2266n == null;
        this.f2266n = view.getViewTreeObserver();
        if (z) {
            this.f2266n.addOnGlobalLayoutListener(this.f2262j);
        }
        this.f2253a.m5480b(view);
        this.f2253a.m5486e(this.f2270r);
        if (!this.f2268p) {
            this.f2269q = C0868m.m4149a(this.f2257e, null, this.f2255c, this.f2259g);
            this.f2268p = true;
        }
        this.f2253a.m5488g(this.f2269q);
        this.f2253a.m5491h(2);
        this.f2253a.m5473a(m4165g());
        this.f2253a.mo729a();
        ViewGroup e = this.f2253a.mo740e();
        e.setOnKeyListener(this);
        if (this.f2271s && this.f2256d.m4256m() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.f2255c).inflate(C0744g.abc_popup_menu_header_item_layout, e, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            if (textView != null) {
                textView.setText(this.f2256d.m4256m());
            }
            frameLayout.setEnabled(false);
            e.addHeaderView(frameLayout, null, false);
        }
        this.f2253a.mo1012a(this.f2257e);
        this.f2253a.mo729a();
        return true;
    }

    /* renamed from: a */
    public void mo729a() {
        if (!m4327h()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    /* renamed from: a */
    public void mo730a(int i) {
        this.f2270r = i;
    }

    /* renamed from: a */
    public void mo731a(C0873h c0873h) {
    }

    /* renamed from: a */
    public void mo720a(C0873h c0873h, boolean z) {
        if (c0873h == this.f2256d) {
            mo736c();
            if (this.f2265m != null) {
                this.f2265m.mo657a(c0873h, z);
            }
        }
    }

    /* renamed from: a */
    public void mo721a(C0794a c0794a) {
        this.f2265m = c0794a;
    }

    /* renamed from: a */
    public void mo732a(View view) {
        this.f2264l = view;
    }

    /* renamed from: a */
    public void mo733a(OnDismissListener onDismissListener) {
        this.f2263k = onDismissListener;
    }

    /* renamed from: a */
    public void mo734a(boolean z) {
        this.f2257e.m4205a(z);
    }

    /* renamed from: a */
    public boolean mo723a(C0890u c0890u) {
        if (c0890u.hasVisibleItems()) {
            C0885n c0885n = new C0885n(this.f2255c, c0890u, this.f2254b, this.f2258f, this.f2260h, this.f2261i);
            c0885n.m4314a(this.f2265m);
            c0885n.m4317a(C0868m.m4151b((C0873h) c0890u));
            c0885n.m4316a(this.f2263k);
            this.f2263k = null;
            this.f2256d.m4230a(false);
            if (c0885n.m4318a(this.f2253a.m5494j(), this.f2253a.m5495k())) {
                if (this.f2265m != null) {
                    this.f2265m.mo658a(c0890u);
                }
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public void mo735b(int i) {
        this.f2253a.m5482c(i);
    }

    /* renamed from: b */
    public void mo724b(boolean z) {
        this.f2268p = false;
        if (this.f2257e != null) {
            this.f2257e.notifyDataSetChanged();
        }
    }

    /* renamed from: b */
    public boolean mo725b() {
        return false;
    }

    /* renamed from: c */
    public void mo736c() {
        if (mo739d()) {
            this.f2253a.mo736c();
        }
    }

    /* renamed from: c */
    public void mo737c(int i) {
        this.f2253a.m5483d(i);
    }

    /* renamed from: c */
    public void mo738c(boolean z) {
        this.f2271s = z;
    }

    /* renamed from: d */
    public boolean mo739d() {
        return !this.f2267o && this.f2253a.mo739d();
    }

    /* renamed from: e */
    public ListView mo740e() {
        return this.f2253a.mo740e();
    }

    public void onDismiss() {
        this.f2267o = true;
        this.f2256d.close();
        if (this.f2266n != null) {
            if (!this.f2266n.isAlive()) {
                this.f2266n = this.f2254b.getViewTreeObserver();
            }
            this.f2266n.removeGlobalOnLayoutListener(this.f2262j);
            this.f2266n = null;
        }
        if (this.f2263k != null) {
            this.f2263k.onDismiss();
        }
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        mo736c();
        return true;
    }
}
