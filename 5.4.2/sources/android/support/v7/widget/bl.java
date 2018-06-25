package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v4.view.bc;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0742e;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0745h;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.view.menu.C0858a;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.widget.Toolbar.C0974b;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window.Callback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class bl implements ag {
    /* renamed from: a */
    Toolbar f3051a;
    /* renamed from: b */
    CharSequence f3052b;
    /* renamed from: c */
    Callback f3053c;
    /* renamed from: d */
    boolean f3054d;
    /* renamed from: e */
    private int f3055e;
    /* renamed from: f */
    private View f3056f;
    /* renamed from: g */
    private View f3057g;
    /* renamed from: h */
    private Drawable f3058h;
    /* renamed from: i */
    private Drawable f3059i;
    /* renamed from: j */
    private Drawable f3060j;
    /* renamed from: k */
    private boolean f3061k;
    /* renamed from: l */
    private CharSequence f3062l;
    /* renamed from: m */
    private CharSequence f3063m;
    /* renamed from: n */
    private C1052d f3064n;
    /* renamed from: o */
    private int f3065o;
    /* renamed from: p */
    private int f3066p;
    /* renamed from: q */
    private Drawable f3067q;

    /* renamed from: android.support.v7.widget.bl$1 */
    class C10401 implements OnClickListener {
        /* renamed from: a */
        final C0858a f3046a = new C0858a(this.f3047b.f3051a.getContext(), 0, 16908332, 0, 0, this.f3047b.f3052b);
        /* renamed from: b */
        final /* synthetic */ bl f3047b;

        C10401(bl blVar) {
            this.f3047b = blVar;
        }

        public void onClick(View view) {
            if (this.f3047b.f3053c != null && this.f3047b.f3054d) {
                this.f3047b.f3053c.onMenuItemSelected(0, this.f3046a);
            }
        }
    }

    public bl(Toolbar toolbar, boolean z) {
        this(toolbar, z, C0745h.abc_action_bar_up_description, C0742e.abc_ic_ab_back_material);
    }

    public bl(Toolbar toolbar, boolean z, int i, int i2) {
        this.f3065o = 0;
        this.f3066p = 0;
        this.f3051a = toolbar;
        this.f3052b = toolbar.getTitle();
        this.f3062l = toolbar.getSubtitle();
        this.f3061k = this.f3052b != null;
        this.f3060j = toolbar.getNavigationIcon();
        bk a = bk.m5654a(toolbar.getContext(), null, C0747j.ActionBar, C0738a.actionBarStyle, 0);
        this.f3067q = a.m5657a(C0747j.ActionBar_homeAsUpIndicator);
        if (z) {
            CharSequence c = a.m5663c(C0747j.ActionBar_title);
            if (!TextUtils.isEmpty(c)) {
                m5691b(c);
            }
            c = a.m5663c(C0747j.ActionBar_subtitle);
            if (!TextUtils.isEmpty(c)) {
                m5695c(c);
            }
            Drawable a2 = a.m5657a(C0747j.ActionBar_logo);
            if (a2 != null) {
                m5690b(a2);
            }
            a2 = a.m5657a(C0747j.ActionBar_icon);
            if (a2 != null) {
                mo967a(a2);
            }
            if (this.f3060j == null && this.f3067q != null) {
                m5694c(this.f3067q);
            }
            mo977c(a.m5656a(C0747j.ActionBar_displayOptions, 0));
            int g = a.m5670g(C0747j.ActionBar_customNavigationLayout, 0);
            if (g != 0) {
                m5684a(LayoutInflater.from(this.f3051a.getContext()).inflate(g, this.f3051a, false));
                mo977c(this.f3055e | 16);
            }
            g = a.m5668f(C0747j.ActionBar_height, 0);
            if (g > 0) {
                LayoutParams layoutParams = this.f3051a.getLayoutParams();
                layoutParams.height = g;
                this.f3051a.setLayoutParams(layoutParams);
            }
            g = a.m5664d(C0747j.ActionBar_contentInsetStart, -1);
            int d = a.m5664d(C0747j.ActionBar_contentInsetEnd, -1);
            if (g >= 0 || d >= 0) {
                this.f3051a.m5188a(Math.max(g, 0), Math.max(d, 0));
            }
            g = a.m5670g(C0747j.ActionBar_titleTextStyle, 0);
            if (g != 0) {
                this.f3051a.m5189a(this.f3051a.getContext(), g);
            }
            g = a.m5670g(C0747j.ActionBar_subtitleTextStyle, 0);
            if (g != 0) {
                this.f3051a.m5193b(this.f3051a.getContext(), g);
            }
            int g2 = a.m5670g(C0747j.ActionBar_popupTheme, 0);
            if (g2 != 0) {
                this.f3051a.setPopupTheme(g2);
            }
        } else {
            this.f3055e = m5673s();
        }
        a.m5658a();
        m5701e(i);
        this.f3063m = this.f3051a.getNavigationContentDescription();
        this.f3051a.setNavigationOnClickListener(new C10401(this));
    }

    /* renamed from: e */
    private void m5672e(CharSequence charSequence) {
        this.f3052b = charSequence;
        if ((this.f3055e & 8) != 0) {
            this.f3051a.setTitle(charSequence);
        }
    }

    /* renamed from: s */
    private int m5673s() {
        if (this.f3051a.getNavigationIcon() == null) {
            return 11;
        }
        this.f3067q = this.f3051a.getNavigationIcon();
        return 15;
    }

    /* renamed from: t */
    private void m5674t() {
        Drawable drawable = null;
        if ((this.f3055e & 2) != 0) {
            drawable = (this.f3055e & 1) != 0 ? this.f3059i != null ? this.f3059i : this.f3058h : this.f3058h;
        }
        this.f3051a.setLogo(drawable);
    }

    /* renamed from: u */
    private void m5675u() {
        if ((this.f3055e & 4) != 0) {
            this.f3051a.setNavigationIcon(this.f3060j != null ? this.f3060j : this.f3067q);
        } else {
            this.f3051a.setNavigationIcon(null);
        }
    }

    /* renamed from: v */
    private void m5676v() {
        if ((this.f3055e & 4) == 0) {
            return;
        }
        if (TextUtils.isEmpty(this.f3063m)) {
            this.f3051a.setNavigationContentDescription(this.f3066p);
        } else {
            this.f3051a.setNavigationContentDescription(this.f3063m);
        }
    }

    /* renamed from: a */
    public ax mo964a(final int i, long j) {
        return ah.m2827q(this.f3051a).m3020a(i == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED).m3021a(j).m3022a(new bc(this) {
            /* renamed from: b */
            final /* synthetic */ bl f3049b;
            /* renamed from: c */
            private boolean f3050c = false;

            public void onAnimationCancel(View view) {
                this.f3050c = true;
            }

            public void onAnimationEnd(View view) {
                if (!this.f3050c) {
                    this.f3049b.f3051a.setVisibility(i);
                }
            }

            public void onAnimationStart(View view) {
                this.f3049b.f3051a.setVisibility(0);
            }
        });
    }

    /* renamed from: a */
    public ViewGroup mo965a() {
        return this.f3051a;
    }

    /* renamed from: a */
    public void mo966a(int i) {
        mo967a(i != 0 ? C0825b.m3939b(mo974b(), i) : null);
    }

    /* renamed from: a */
    public void mo967a(Drawable drawable) {
        this.f3058h = drawable;
        m5674t();
    }

    /* renamed from: a */
    public void mo968a(C0794a c0794a, C0777a c0777a) {
        this.f3051a.m5191a(c0794a, c0777a);
    }

    /* renamed from: a */
    public void mo969a(bc bcVar) {
        if (this.f3056f != null && this.f3056f.getParent() == this.f3051a) {
            this.f3051a.removeView(this.f3056f);
        }
        this.f3056f = bcVar;
        if (bcVar != null && this.f3065o == 2) {
            this.f3051a.addView(this.f3056f, 0);
            C0974b c0974b = (C0974b) this.f3056f.getLayoutParams();
            c0974b.width = -2;
            c0974b.height = -2;
            c0974b.a = 8388691;
            bcVar.setAllowCollapse(true);
        }
    }

    /* renamed from: a */
    public void mo970a(Menu menu, C0794a c0794a) {
        if (this.f3064n == null) {
            this.f3064n = new C1052d(this.f3051a.getContext());
            this.f3064n.m4121a(C0743f.action_menu_presenter);
        }
        this.f3064n.mo721a(c0794a);
        this.f3051a.m5190a((C0873h) menu, this.f3064n);
    }

    /* renamed from: a */
    public void m5684a(View view) {
        if (!(this.f3057g == null || (this.f3055e & 16) == 0)) {
            this.f3051a.removeView(this.f3057g);
        }
        this.f3057g = view;
        if (view != null && (this.f3055e & 16) != 0) {
            this.f3051a.addView(this.f3057g);
        }
    }

    /* renamed from: a */
    public void mo971a(Callback callback) {
        this.f3053c = callback;
    }

    /* renamed from: a */
    public void mo972a(CharSequence charSequence) {
        if (!this.f3061k) {
            m5672e(charSequence);
        }
    }

    /* renamed from: a */
    public void mo973a(boolean z) {
        this.f3051a.setCollapsible(z);
    }

    /* renamed from: b */
    public Context mo974b() {
        return this.f3051a.getContext();
    }

    /* renamed from: b */
    public void mo975b(int i) {
        m5690b(i != 0 ? C0825b.m3939b(mo974b(), i) : null);
    }

    /* renamed from: b */
    public void m5690b(Drawable drawable) {
        this.f3059i = drawable;
        m5674t();
    }

    /* renamed from: b */
    public void m5691b(CharSequence charSequence) {
        this.f3061k = true;
        m5672e(charSequence);
    }

    /* renamed from: b */
    public void mo976b(boolean z) {
    }

    /* renamed from: c */
    public void mo977c(int i) {
        int i2 = this.f3055e ^ i;
        this.f3055e = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    m5676v();
                }
                m5675u();
            }
            if ((i2 & 3) != 0) {
                m5674t();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.f3051a.setTitle(this.f3052b);
                    this.f3051a.setSubtitle(this.f3062l);
                } else {
                    this.f3051a.setTitle(null);
                    this.f3051a.setSubtitle(null);
                }
            }
            if ((i2 & 16) != 0 && this.f3057g != null) {
                if ((i & 16) != 0) {
                    this.f3051a.addView(this.f3057g);
                } else {
                    this.f3051a.removeView(this.f3057g);
                }
            }
        }
    }

    /* renamed from: c */
    public void m5694c(Drawable drawable) {
        this.f3060j = drawable;
        m5675u();
    }

    /* renamed from: c */
    public void m5695c(CharSequence charSequence) {
        this.f3062l = charSequence;
        if ((this.f3055e & 8) != 0) {
            this.f3051a.setSubtitle(charSequence);
        }
    }

    /* renamed from: c */
    public boolean mo978c() {
        return this.f3051a.m5199g();
    }

    /* renamed from: d */
    public void mo979d() {
        this.f3051a.m5200h();
    }

    /* renamed from: d */
    public void mo980d(int i) {
        this.f3051a.setVisibility(i);
    }

    /* renamed from: d */
    public void m5699d(CharSequence charSequence) {
        this.f3063m = charSequence;
        m5676v();
    }

    /* renamed from: e */
    public CharSequence mo981e() {
        return this.f3051a.getTitle();
    }

    /* renamed from: e */
    public void m5701e(int i) {
        if (i != this.f3066p) {
            this.f3066p = i;
            if (TextUtils.isEmpty(this.f3051a.getNavigationContentDescription())) {
                m5703f(this.f3066p);
            }
        }
    }

    /* renamed from: f */
    public void mo982f() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    /* renamed from: f */
    public void m5703f(int i) {
        m5699d(i == 0 ? null : mo974b().getString(i));
    }

    /* renamed from: g */
    public void mo983g() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    /* renamed from: h */
    public boolean mo984h() {
        return this.f3051a.m5192a();
    }

    /* renamed from: i */
    public boolean mo985i() {
        return this.f3051a.m5194b();
    }

    /* renamed from: j */
    public boolean mo986j() {
        return this.f3051a.m5195c();
    }

    /* renamed from: k */
    public boolean mo987k() {
        return this.f3051a.m5196d();
    }

    /* renamed from: l */
    public boolean mo988l() {
        return this.f3051a.m5197e();
    }

    /* renamed from: m */
    public void mo989m() {
        this.f3054d = true;
    }

    /* renamed from: n */
    public void mo990n() {
        this.f3051a.m5198f();
    }

    /* renamed from: o */
    public int mo991o() {
        return this.f3055e;
    }

    /* renamed from: p */
    public int mo992p() {
        return this.f3065o;
    }

    /* renamed from: q */
    public int mo993q() {
        return this.f3051a.getVisibility();
    }

    /* renamed from: r */
    public Menu mo994r() {
        return this.f3051a.getMenu();
    }
}
