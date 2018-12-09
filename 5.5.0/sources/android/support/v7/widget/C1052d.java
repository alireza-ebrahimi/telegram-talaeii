package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.view.C0616d;
import android.support.v4.view.C0616d.C0614a;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.p031g.C0841a;
import android.support.v7.view.C0842a;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.ActionMenuItemView.C0854b;
import android.support.v7.view.menu.C0079p;
import android.support.v7.view.menu.C0079p.C0077a;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.view.menu.C0860b;
import android.support.v7.view.menu.C0867s;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0876j;
import android.support.v7.view.menu.C0885n;
import android.support.v7.view.menu.C0890u;
import android.support.v7.widget.ActionMenuView.C0856a;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

/* renamed from: android.support.v7.widget.d */
class C1052d extends C0860b implements C0614a {
    /* renamed from: A */
    private C1046b f3093A;
    /* renamed from: g */
    C1049d f3094g;
    /* renamed from: h */
    C1050e f3095h;
    /* renamed from: i */
    C1045a f3096i;
    /* renamed from: j */
    C1047c f3097j;
    /* renamed from: k */
    final C1051f f3098k = new C1051f(this);
    /* renamed from: l */
    int f3099l;
    /* renamed from: m */
    private Drawable f3100m;
    /* renamed from: n */
    private boolean f3101n;
    /* renamed from: o */
    private boolean f3102o;
    /* renamed from: p */
    private boolean f3103p;
    /* renamed from: q */
    private int f3104q;
    /* renamed from: r */
    private int f3105r;
    /* renamed from: s */
    private int f3106s;
    /* renamed from: t */
    private boolean f3107t;
    /* renamed from: u */
    private boolean f3108u;
    /* renamed from: v */
    private boolean f3109v;
    /* renamed from: w */
    private boolean f3110w;
    /* renamed from: x */
    private int f3111x;
    /* renamed from: y */
    private final SparseBooleanArray f3112y = new SparseBooleanArray();
    /* renamed from: z */
    private View f3113z;

    /* renamed from: android.support.v7.widget.d$a */
    private class C1045a extends C0885n {
        /* renamed from: a */
        final /* synthetic */ C1052d f3083a;

        public C1045a(C1052d c1052d, Context context, C0890u c0890u, View view) {
            this.f3083a = c1052d;
            super(context, c0890u, view, false, C0738a.actionOverflowMenuStyle);
            if (!((C0876j) c0890u.getItem()).m4289j()) {
                m4315a(c1052d.f3094g == null ? (View) c1052d.f : c1052d.f3094g);
            }
            m4314a(c1052d.f3098k);
        }

        /* renamed from: e */
        protected void mo995e() {
            this.f3083a.f3096i = null;
            this.f3083a.f3099l = 0;
            super.mo995e();
        }
    }

    /* renamed from: android.support.v7.widget.d$b */
    private class C1046b extends C0854b {
        /* renamed from: a */
        final /* synthetic */ C1052d f3084a;

        C1046b(C1052d c1052d) {
            this.f3084a = c1052d;
        }

        /* renamed from: a */
        public C0867s mo996a() {
            return this.f3084a.f3096i != null ? this.f3084a.f3096i.m4319b() : null;
        }
    }

    /* renamed from: android.support.v7.widget.d$c */
    private class C1047c implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1052d f3085a;
        /* renamed from: b */
        private C1050e f3086b;

        public C1047c(C1052d c1052d, C1050e c1050e) {
            this.f3085a = c1052d;
            this.f3086b = c1050e;
        }

        public void run() {
            if (this.f3085a.c != null) {
                this.f3085a.c.m4249f();
            }
            View view = (View) this.f3085a.f;
            if (!(view == null || view.getWindowToken() == null || !this.f3086b.m4320c())) {
                this.f3085a.f3095h = this.f3086b;
            }
            this.f3085a.f3097j = null;
        }
    }

    /* renamed from: android.support.v7.widget.d$d */
    private class C1049d extends AppCompatImageView implements C0856a {
        /* renamed from: a */
        final /* synthetic */ C1052d f3089a;
        /* renamed from: b */
        private final float[] f3090b = new float[2];

        public C1049d(final C1052d c1052d, Context context) {
            this.f3089a = c1052d;
            super(context, null, C0738a.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            setOnTouchListener(new al(this, this) {
                /* renamed from: b */
                final /* synthetic */ C1049d f3088b;

                /* renamed from: a */
                public C0867s mo703a() {
                    return this.f3088b.f3089a.f3095h == null ? null : this.f3088b.f3089a.f3095h.m4319b();
                }

                /* renamed from: b */
                public boolean mo704b() {
                    this.f3088b.f3089a.m5783d();
                    return true;
                }

                /* renamed from: c */
                public boolean mo997c() {
                    if (this.f3088b.f3089a.f3097j != null) {
                        return false;
                    }
                    this.f3088b.f3089a.m5784e();
                    return true;
                }
            });
        }

        /* renamed from: c */
        public boolean mo705c() {
            return false;
        }

        /* renamed from: d */
        public boolean mo706d() {
            return false;
        }

        public boolean performClick() {
            if (!super.performClick()) {
                playSoundEffect(0);
                this.f3089a.m5783d();
            }
            return true;
        }

        protected boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int max = Math.max(width, height) / 2;
                width = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                height = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                C0375a.m1772a(background, width - max, height - max, width + max, height + max);
            }
            return frame;
        }
    }

    /* renamed from: android.support.v7.widget.d$e */
    private class C1050e extends C0885n {
        /* renamed from: a */
        final /* synthetic */ C1052d f3091a;

        public C1050e(C1052d c1052d, Context context, C0873h c0873h, View view, boolean z) {
            this.f3091a = c1052d;
            super(context, c0873h, view, z, C0738a.actionOverflowMenuStyle);
            m4313a(8388613);
            m4314a(c1052d.f3098k);
        }

        /* renamed from: e */
        protected void mo995e() {
            if (this.f3091a.c != null) {
                this.f3091a.c.close();
            }
            this.f3091a.f3095h = null;
            super.mo995e();
        }
    }

    /* renamed from: android.support.v7.widget.d$f */
    private class C1051f implements C0794a {
        /* renamed from: a */
        final /* synthetic */ C1052d f3092a;

        C1051f(C1052d c1052d) {
            this.f3092a = c1052d;
        }

        /* renamed from: a */
        public void mo657a(C0873h c0873h, boolean z) {
            if (c0873h instanceof C0890u) {
                c0873h.mo763p().m4230a(false);
            }
            C0794a a = this.f3092a.m4118a();
            if (a != null) {
                a.mo657a(c0873h, z);
            }
        }

        /* renamed from: a */
        public boolean mo658a(C0873h c0873h) {
            if (c0873h == null) {
                return false;
            }
            this.f3092a.f3099l = ((C0890u) c0873h).getItem().getItemId();
            C0794a a = this.f3092a.m4118a();
            return a != null ? a.mo658a(c0873h) : false;
        }
    }

    public C1052d(Context context) {
        super(context, C0744g.abc_action_menu_layout, C0744g.abc_action_menu_item_layout);
    }

    /* renamed from: a */
    private View m5760a(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.f;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof C0077a) && ((C0077a) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    /* renamed from: a */
    public C0079p mo998a(ViewGroup viewGroup) {
        C0079p c0079p = this.f;
        C0079p a = super.mo998a(viewGroup);
        if (c0079p != a) {
            ((ActionMenuView) a).setPresenter(this);
        }
        return a;
    }

    /* renamed from: a */
    public View mo999a(C0876j c0876j, View view, ViewGroup viewGroup) {
        View actionView = c0876j.getActionView();
        if (actionView == null || c0876j.m4293n()) {
            actionView = super.mo999a(c0876j, view, viewGroup);
        }
        actionView.setVisibility(c0876j.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.m4410a(layoutParams));
        }
        return actionView;
    }

    /* renamed from: a */
    public void mo719a(Context context, C0873h c0873h) {
        super.mo719a(context, c0873h);
        Resources resources = context.getResources();
        C0842a a = C0842a.m4013a(context);
        if (!this.f3103p) {
            this.f3102o = a.m4015b();
        }
        if (!this.f3109v) {
            this.f3104q = a.m4016c();
        }
        if (!this.f3107t) {
            this.f3106s = a.m4014a();
        }
        int i = this.f3104q;
        if (this.f3102o) {
            if (this.f3094g == null) {
                this.f3094g = new C1049d(this, this.a);
                if (this.f3101n) {
                    this.f3094g.setImageDrawable(this.f3100m);
                    this.f3100m = null;
                    this.f3101n = false;
                }
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
                this.f3094g.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.f3094g.getMeasuredWidth();
        } else {
            this.f3094g = null;
        }
        this.f3105r = i;
        this.f3111x = (int) (56.0f * resources.getDisplayMetrics().density);
        this.f3113z = null;
    }

    /* renamed from: a */
    public void m5769a(Configuration configuration) {
        if (!this.f3107t) {
            this.f3106s = C0842a.m4013a(this.b).m4014a();
        }
        if (this.c != null) {
            this.c.m4238b(true);
        }
    }

    /* renamed from: a */
    public void m5770a(Drawable drawable) {
        if (this.f3094g != null) {
            this.f3094g.setImageDrawable(drawable);
            return;
        }
        this.f3101n = true;
        this.f3100m = drawable;
    }

    /* renamed from: a */
    public void mo720a(C0873h c0873h, boolean z) {
        m5785f();
        super.mo720a(c0873h, z);
    }

    /* renamed from: a */
    public void mo1000a(C0876j c0876j, C0077a c0077a) {
        c0077a.mo43a(c0876j, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) c0077a;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.f);
        if (this.f3093A == null) {
            this.f3093A = new C1046b(this);
        }
        actionMenuItemView.setPopupCallback(this.f3093A);
    }

    /* renamed from: a */
    public void m5773a(ActionMenuView actionMenuView) {
        this.f = actionMenuView;
        actionMenuView.mo54a(this.c);
    }

    /* renamed from: a */
    public void mo1001a(boolean z) {
        if (z) {
            super.mo723a(null);
        } else if (this.c != null) {
            this.c.m4230a(false);
        }
    }

    /* renamed from: a */
    public boolean mo1002a(int i, C0876j c0876j) {
        return c0876j.m4289j();
    }

    /* renamed from: a */
    public boolean mo723a(C0890u c0890u) {
        if (!c0890u.hasVisibleItems()) {
            return false;
        }
        C0890u c0890u2 = c0890u;
        while (c0890u2.m4353s() != this.c) {
            c0890u2 = (C0890u) c0890u2.m4353s();
        }
        View a = m5760a(c0890u2.getItem());
        if (a == null) {
            return false;
        }
        boolean z;
        this.f3099l = c0890u.getItem().getItemId();
        int size = c0890u.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = c0890u.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
        }
        z = false;
        this.f3096i = new C1045a(this, this.b, c0890u, a);
        this.f3096i.m4317a(z);
        this.f3096i.m4312a();
        super.mo723a(c0890u);
        return true;
    }

    /* renamed from: a */
    public boolean mo1003a(ViewGroup viewGroup, int i) {
        return viewGroup.getChildAt(i) == this.f3094g ? false : super.mo1003a(viewGroup, i);
    }

    /* renamed from: b */
    public void mo724b(boolean z) {
        int i;
        int i2 = 1;
        int i3 = 0;
        ViewGroup viewGroup = (ViewGroup) ((View) this.f).getParent();
        if (viewGroup != null) {
            C0841a.m4012a(viewGroup);
        }
        super.mo724b(z);
        ((View) this.f).requestLayout();
        if (this.c != null) {
            ArrayList k = this.c.m4254k();
            int size = k.size();
            for (i = 0; i < size; i++) {
                C0616d a = ((C0876j) k.get(i)).mo710a();
                if (a != null) {
                    a.m3095a((C0614a) this);
                }
            }
        }
        ArrayList l = this.c != null ? this.c.m4255l() : null;
        if (this.f3102o && l != null) {
            i = l.size();
            if (i == 1) {
                i3 = !((C0876j) l.get(0)).isActionViewExpanded() ? 1 : 0;
            } else {
                if (i <= 0) {
                    i2 = 0;
                }
                i3 = i2;
            }
        }
        if (i3 != 0) {
            if (this.f3094g == null) {
                this.f3094g = new C1049d(this, this.a);
            }
            viewGroup = (ViewGroup) this.f3094g.getParent();
            if (viewGroup != this.f) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.f3094g);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.f;
                actionMenuView.addView(this.f3094g, actionMenuView.m4419c());
            }
        } else if (this.f3094g != null && this.f3094g.getParent() == this.f) {
            ((ViewGroup) this.f).removeView(this.f3094g);
        }
        ((ActionMenuView) this.f).setOverflowReserved(this.f3102o);
    }

    /* renamed from: b */
    public boolean mo725b() {
        int size;
        ArrayList arrayList;
        int i;
        if (this.c != null) {
            ArrayList i2 = this.c.m4252i();
            size = i2.size();
            arrayList = i2;
        } else {
            size = 0;
            arrayList = null;
        }
        int i3 = this.f3106s;
        int i4 = this.f3105r;
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) this.f;
        int i5 = 0;
        int i6 = 0;
        Object obj = null;
        int i7 = 0;
        while (i7 < size) {
            C0876j c0876j = (C0876j) arrayList.get(i7);
            if (c0876j.m4291l()) {
                i5++;
            } else if (c0876j.m4290k()) {
                i6++;
            } else {
                obj = 1;
            }
            i = (this.f3110w && c0876j.isActionViewExpanded()) ? 0 : i3;
            i7++;
            i3 = i;
        }
        if (this.f3102o && (r4 != null || i5 + i6 > i3)) {
            i3--;
        }
        i7 = i3 - i5;
        SparseBooleanArray sparseBooleanArray = this.f3112y;
        sparseBooleanArray.clear();
        i = 0;
        if (this.f3108u) {
            i = i4 / this.f3111x;
            i6 = ((i4 % this.f3111x) / i) + this.f3111x;
        } else {
            i6 = 0;
        }
        int i8 = 0;
        i3 = 0;
        int i9 = i;
        while (i8 < size) {
            c0876j = (C0876j) arrayList.get(i8);
            int i10;
            if (c0876j.m4291l()) {
                View a = mo999a(c0876j, this.f3113z, viewGroup);
                if (this.f3113z == null) {
                    this.f3113z = a;
                }
                if (this.f3108u) {
                    i9 -= ActionMenuView.m4407a(a, i6, i9, makeMeasureSpec, 0);
                } else {
                    a.measure(makeMeasureSpec, makeMeasureSpec);
                }
                i5 = a.getMeasuredWidth();
                i10 = i4 - i5;
                if (i3 != 0) {
                    i5 = i3;
                }
                i3 = c0876j.getGroupId();
                if (i3 != 0) {
                    sparseBooleanArray.put(i3, true);
                }
                c0876j.m4282d(true);
                i = i10;
                i3 = i7;
            } else if (c0876j.m4290k()) {
                boolean z;
                int groupId = c0876j.getGroupId();
                boolean z2 = sparseBooleanArray.get(groupId);
                boolean z3 = (i7 > 0 || z2) && i4 > 0 && (!this.f3108u || i9 > 0);
                if (z3) {
                    View a2 = mo999a(c0876j, this.f3113z, viewGroup);
                    if (this.f3113z == null) {
                        this.f3113z = a2;
                    }
                    boolean z4;
                    if (this.f3108u) {
                        int a3 = ActionMenuView.m4407a(a2, i6, i9, makeMeasureSpec, 0);
                        i10 = i9 - a3;
                        if (a3 == 0) {
                            i9 = 0;
                        } else {
                            z4 = z3;
                        }
                        i5 = i10;
                    } else {
                        a2.measure(makeMeasureSpec, makeMeasureSpec);
                        boolean z5 = z3;
                        i5 = i9;
                        z4 = z5;
                    }
                    i10 = a2.getMeasuredWidth();
                    i4 -= i10;
                    if (i3 == 0) {
                        i3 = i10;
                    }
                    if (this.f3108u) {
                        z = i9 & (i4 >= 0 ? 1 : 0);
                        i10 = i3;
                        i3 = i5;
                    } else {
                        z = i9 & (i4 + i3 > 0 ? 1 : 0);
                        i10 = i3;
                        i3 = i5;
                    }
                } else {
                    z = z3;
                    i10 = i3;
                    i3 = i9;
                }
                if (z && groupId != 0) {
                    sparseBooleanArray.put(groupId, true);
                    i9 = i7;
                } else if (z2) {
                    sparseBooleanArray.put(groupId, false);
                    i5 = i7;
                    for (i7 = 0; i7 < i8; i7++) {
                        C0876j c0876j2 = (C0876j) arrayList.get(i7);
                        if (c0876j2.getGroupId() == groupId) {
                            if (c0876j2.m4289j()) {
                                i5++;
                            }
                            c0876j2.m4282d(false);
                        }
                    }
                    i9 = i5;
                } else {
                    i9 = i7;
                }
                if (z) {
                    i9--;
                }
                c0876j.m4282d(z);
                i5 = i10;
                i = i4;
                int i11 = i3;
                i3 = i9;
                i9 = i11;
            } else {
                c0876j.m4282d(false);
                i5 = i3;
                i = i4;
                i3 = i7;
            }
            i8++;
            i4 = i;
            i7 = i3;
            i3 = i5;
        }
        return true;
    }

    /* renamed from: c */
    public Drawable m5780c() {
        return this.f3094g != null ? this.f3094g.getDrawable() : this.f3101n ? this.f3100m : null;
    }

    /* renamed from: c */
    public void m5781c(boolean z) {
        this.f3102o = z;
        this.f3103p = true;
    }

    /* renamed from: d */
    public void m5782d(boolean z) {
        this.f3110w = z;
    }

    /* renamed from: d */
    public boolean m5783d() {
        if (!this.f3102o || m5787h() || this.c == null || this.f == null || this.f3097j != null || this.c.m4255l().isEmpty()) {
            return false;
        }
        this.f3097j = new C1047c(this, new C1050e(this, this.b, this.c, this.f3094g, true));
        ((View) this.f).post(this.f3097j);
        super.mo723a(null);
        return true;
    }

    /* renamed from: e */
    public boolean m5784e() {
        if (this.f3097j == null || this.f == null) {
            C0885n c0885n = this.f3095h;
            if (c0885n == null) {
                return false;
            }
            c0885n.m4321d();
            return true;
        }
        ((View) this.f).removeCallbacks(this.f3097j);
        this.f3097j = null;
        return true;
    }

    /* renamed from: f */
    public boolean m5785f() {
        return m5784e() | m5786g();
    }

    /* renamed from: g */
    public boolean m5786g() {
        if (this.f3096i == null) {
            return false;
        }
        this.f3096i.m4321d();
        return true;
    }

    /* renamed from: h */
    public boolean m5787h() {
        return this.f3095h != null && this.f3095h.m4323f();
    }

    /* renamed from: i */
    public boolean m5788i() {
        return this.f3097j != null || m5787h();
    }
}
