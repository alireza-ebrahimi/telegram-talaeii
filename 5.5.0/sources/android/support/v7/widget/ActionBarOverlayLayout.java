package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.C0107x;
import android.support.v4.view.C0662y;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v4.view.bb;
import android.support.v4.view.bc;
import android.support.v4.widget.C0729x;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.view.menu.C0859o.C0794a;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window.Callback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ActionBarOverlayLayout extends ViewGroup implements C0107x, af {
    /* renamed from: e */
    static final int[] f2308e = new int[]{C0738a.actionBarSize, 16842841};
    /* renamed from: A */
    private final Runnable f2309A;
    /* renamed from: B */
    private final C0662y f2310B;
    /* renamed from: a */
    ActionBarContainer f2311a;
    /* renamed from: b */
    boolean f2312b;
    /* renamed from: c */
    ax f2313c;
    /* renamed from: d */
    final bb f2314d;
    /* renamed from: f */
    private int f2315f;
    /* renamed from: g */
    private int f2316g;
    /* renamed from: h */
    private ContentFrameLayout f2317h;
    /* renamed from: i */
    private ag f2318i;
    /* renamed from: j */
    private Drawable f2319j;
    /* renamed from: k */
    private boolean f2320k;
    /* renamed from: l */
    private boolean f2321l;
    /* renamed from: m */
    private boolean f2322m;
    /* renamed from: n */
    private boolean f2323n;
    /* renamed from: o */
    private int f2324o;
    /* renamed from: p */
    private int f2325p;
    /* renamed from: q */
    private final Rect f2326q;
    /* renamed from: r */
    private final Rect f2327r;
    /* renamed from: s */
    private final Rect f2328s;
    /* renamed from: t */
    private final Rect f2329t;
    /* renamed from: u */
    private final Rect f2330u;
    /* renamed from: v */
    private final Rect f2331v;
    /* renamed from: w */
    private C0816a f2332w;
    /* renamed from: x */
    private final int f2333x;
    /* renamed from: y */
    private C0729x f2334y;
    /* renamed from: z */
    private final Runnable f2335z;

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$a */
    public interface C0816a {
        /* renamed from: a */
        void mo694a(int i);

        /* renamed from: g */
        void mo697g(boolean z);

        /* renamed from: l */
        void mo698l();

        /* renamed from: m */
        void mo699m();

        /* renamed from: n */
        void mo700n();

        /* renamed from: o */
        void mo701o();
    }

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$1 */
    class C08941 extends bc {
        /* renamed from: a */
        final /* synthetic */ ActionBarOverlayLayout f2305a;

        C08941(ActionBarOverlayLayout actionBarOverlayLayout) {
            this.f2305a = actionBarOverlayLayout;
        }

        public void onAnimationCancel(View view) {
            this.f2305a.f2313c = null;
            this.f2305a.f2312b = false;
        }

        public void onAnimationEnd(View view) {
            this.f2305a.f2313c = null;
            this.f2305a.f2312b = false;
        }
    }

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$2 */
    class C08952 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ActionBarOverlayLayout f2306a;

        C08952(ActionBarOverlayLayout actionBarOverlayLayout) {
            this.f2306a = actionBarOverlayLayout;
        }

        public void run() {
            this.f2306a.m4394d();
            this.f2306a.f2313c = ah.m2827q(this.f2306a.f2311a).m3028c(BitmapDescriptorFactory.HUE_RED).m3022a(this.f2306a.f2314d);
        }
    }

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$3 */
    class C08963 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ActionBarOverlayLayout f2307a;

        C08963(ActionBarOverlayLayout actionBarOverlayLayout) {
            this.f2307a = actionBarOverlayLayout;
        }

        public void run() {
            this.f2307a.m4394d();
            this.f2307a.f2313c = ah.m2827q(this.f2307a.f2311a).m3028c((float) (-this.f2307a.f2311a.getHeight())).m3022a(this.f2307a.f2314d);
        }
    }

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$b */
    public static class C0897b extends MarginLayoutParams {
        public C0897b(int i, int i2) {
            super(i, i2);
        }

        public C0897b(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public C0897b(LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f2316g = 0;
        this.f2326q = new Rect();
        this.f2327r = new Rect();
        this.f2328s = new Rect();
        this.f2329t = new Rect();
        this.f2330u = new Rect();
        this.f2331v = new Rect();
        this.f2333x = 600;
        this.f2314d = new C08941(this);
        this.f2335z = new C08952(this);
        this.f2309A = new C08963(this);
        m4381a(context);
        this.f2310B = new C0662y(this);
    }

    /* renamed from: a */
    private ag m4380a(View view) {
        if (view instanceof ag) {
            return (ag) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    /* renamed from: a */
    private void m4381a(Context context) {
        boolean z = true;
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(f2308e);
        this.f2315f = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.f2319j = obtainStyledAttributes.getDrawable(1);
        setWillNotDraw(this.f2319j == null);
        obtainStyledAttributes.recycle();
        if (context.getApplicationInfo().targetSdkVersion >= 19) {
            z = false;
        }
        this.f2320k = z;
        this.f2334y = C0729x.m3541a(context);
    }

    /* renamed from: a */
    private boolean m4382a(float f, float f2) {
        this.f2334y.m3545a(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.f2334y.m3552e() > this.f2311a.getHeight();
    }

    /* renamed from: a */
    private boolean m4383a(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5 = false;
        C0897b c0897b = (C0897b) view.getLayoutParams();
        if (z && c0897b.leftMargin != rect.left) {
            c0897b.leftMargin = rect.left;
            z5 = true;
        }
        if (z2 && c0897b.topMargin != rect.top) {
            c0897b.topMargin = rect.top;
            z5 = true;
        }
        if (z4 && c0897b.rightMargin != rect.right) {
            c0897b.rightMargin = rect.right;
            z5 = true;
        }
        if (!z3 || c0897b.bottomMargin == rect.bottom) {
            return z5;
        }
        c0897b.bottomMargin = rect.bottom;
        return true;
    }

    /* renamed from: l */
    private void m4384l() {
        m4394d();
        postDelayed(this.f2335z, 600);
    }

    /* renamed from: m */
    private void m4385m() {
        m4394d();
        postDelayed(this.f2309A, 600);
    }

    /* renamed from: n */
    private void m4386n() {
        m4394d();
        this.f2335z.run();
    }

    /* renamed from: o */
    private void m4387o() {
        m4394d();
        this.f2309A.run();
    }

    /* renamed from: a */
    public C0897b m4388a(AttributeSet attributeSet) {
        return new C0897b(getContext(), attributeSet);
    }

    /* renamed from: a */
    public void mo773a(int i) {
        m4393c();
        switch (i) {
            case 2:
                this.f2318i.mo982f();
                return;
            case 5:
                this.f2318i.mo983g();
                return;
            case 109:
                setOverlayMode(true);
                return;
            default:
                return;
        }
    }

    /* renamed from: a */
    public void mo774a(Menu menu, C0794a c0794a) {
        m4393c();
        this.f2318i.mo970a(menu, c0794a);
    }

    /* renamed from: a */
    public boolean m4391a() {
        return this.f2321l;
    }

    /* renamed from: b */
    protected C0897b m4392b() {
        return new C0897b(-1, -1);
    }

    /* renamed from: c */
    void m4393c() {
        if (this.f2317h == null) {
            this.f2317h = (ContentFrameLayout) findViewById(C0743f.action_bar_activity_content);
            this.f2311a = (ActionBarContainer) findViewById(C0743f.action_bar_container);
            this.f2318i = m4380a(findViewById(C0743f.action_bar));
        }
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return layoutParams instanceof C0897b;
    }

    /* renamed from: d */
    void m4394d() {
        removeCallbacks(this.f2335z);
        removeCallbacks(this.f2309A);
        if (this.f2313c != null) {
            this.f2313c.m3027b();
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.f2319j != null && !this.f2320k) {
            int bottom = this.f2311a.getVisibility() == 0 ? (int) ((((float) this.f2311a.getBottom()) + ah.m2823m(this.f2311a)) + 0.5f) : 0;
            this.f2319j.setBounds(0, bottom, getWidth(), this.f2319j.getIntrinsicHeight() + bottom);
            this.f2319j.draw(canvas);
        }
    }

    /* renamed from: e */
    public boolean mo775e() {
        m4393c();
        return this.f2318i.mo984h();
    }

    /* renamed from: f */
    public boolean mo776f() {
        m4393c();
        return this.f2318i.mo985i();
    }

    protected boolean fitSystemWindows(Rect rect) {
        boolean a;
        m4393c();
        if ((ah.m2833w(this) & 256) != 0) {
            a = m4383a(this.f2311a, rect, true, true, false, true);
            this.f2329t.set(rect);
            bp.m5746a(this, this.f2329t, this.f2326q);
        } else {
            a = m4383a(this.f2311a, rect, true, true, false, true);
            this.f2329t.set(rect);
            bp.m5746a(this, this.f2329t, this.f2326q);
        }
        if (!this.f2327r.equals(this.f2326q)) {
            this.f2327r.set(this.f2326q);
            a = true;
        }
        if (a) {
            requestLayout();
        }
        return true;
    }

    /* renamed from: g */
    public boolean mo777g() {
        m4393c();
        return this.f2318i.mo986j();
    }

    protected /* synthetic */ LayoutParams generateDefaultLayoutParams() {
        return m4392b();
    }

    public /* synthetic */ LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return m4388a(attributeSet);
    }

    protected LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return new C0897b(layoutParams);
    }

    public int getActionBarHideOffset() {
        return this.f2311a != null ? -((int) ah.m2823m(this.f2311a)) : 0;
    }

    public int getNestedScrollAxes() {
        return this.f2310B.m3217a();
    }

    public CharSequence getTitle() {
        m4393c();
        return this.f2318i.mo981e();
    }

    /* renamed from: h */
    public boolean mo778h() {
        m4393c();
        return this.f2318i.mo987k();
    }

    /* renamed from: i */
    public boolean mo779i() {
        m4393c();
        return this.f2318i.mo988l();
    }

    /* renamed from: j */
    public void mo780j() {
        m4393c();
        this.f2318i.mo989m();
    }

    /* renamed from: k */
    public void mo781k() {
        m4393c();
        this.f2318i.mo990n();
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m4381a(getContext());
        ah.m2834x(this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        m4394d();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = (i3 - i) - getPaddingRight();
        int paddingTop = getPaddingTop();
        paddingRight = (i4 - i2) - getPaddingBottom();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                C0897b c0897b = (C0897b) childAt.getLayoutParams();
                int i6 = c0897b.leftMargin + paddingLeft;
                paddingRight = c0897b.topMargin + paddingTop;
                childAt.layout(i6, paddingRight, childAt.getMeasuredWidth() + i6, childAt.getMeasuredHeight() + paddingRight);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        int i3;
        m4393c();
        measureChildWithMargins(this.f2311a, i, 0, i2, 0);
        C0897b c0897b = (C0897b) this.f2311a.getLayoutParams();
        int max = Math.max(0, (this.f2311a.getMeasuredWidth() + c0897b.leftMargin) + c0897b.rightMargin);
        int max2 = Math.max(0, c0897b.bottomMargin + (this.f2311a.getMeasuredHeight() + c0897b.topMargin));
        int a = bp.m5745a(0, ah.m2816i(this.f2311a));
        Object obj = (ah.m2833w(this) & 256) != 0 ? 1 : null;
        if (obj != null) {
            i3 = this.f2315f;
            if (this.f2322m && this.f2311a.getTabContainer() != null) {
                i3 += this.f2315f;
            }
        } else {
            i3 = this.f2311a.getVisibility() != 8 ? this.f2311a.getMeasuredHeight() : 0;
        }
        this.f2328s.set(this.f2326q);
        this.f2330u.set(this.f2329t);
        Rect rect;
        Rect rect2;
        if (this.f2321l || obj != null) {
            rect = this.f2330u;
            rect.top = i3 + rect.top;
            rect2 = this.f2330u;
            rect2.bottom += 0;
        } else {
            rect = this.f2328s;
            rect.top = i3 + rect.top;
            rect2 = this.f2328s;
            rect2.bottom += 0;
        }
        m4383a(this.f2317h, this.f2328s, true, true, true, true);
        if (!this.f2331v.equals(this.f2330u)) {
            this.f2331v.set(this.f2330u);
            this.f2317h.m3806a(this.f2330u);
        }
        measureChildWithMargins(this.f2317h, i, 0, i2, 0);
        c0897b = (C0897b) this.f2317h.getLayoutParams();
        int max3 = Math.max(max, (this.f2317h.getMeasuredWidth() + c0897b.leftMargin) + c0897b.rightMargin);
        i3 = Math.max(max2, c0897b.bottomMargin + (this.f2317h.getMeasuredHeight() + c0897b.topMargin));
        int a2 = bp.m5745a(a, ah.m2816i(this.f2317h));
        setMeasuredDimension(ah.m2773a(Math.max(max3 + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), i, a2), ah.m2773a(Math.max(i3 + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), i2, a2 << 16));
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.f2323n || !z) {
            return false;
        }
        if (m4382a(f, f2)) {
            m4387o();
        } else {
            m4386n();
        }
        this.f2312b = true;
        return true;
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        this.f2324o += i2;
        setActionBarHideOffset(this.f2324o);
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.f2310B.m3219a(view, view2, i);
        this.f2324o = getActionBarHideOffset();
        m4394d();
        if (this.f2332w != null) {
            this.f2332w.mo700n();
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        return ((i & 2) == 0 || this.f2311a.getVisibility() != 0) ? false : this.f2323n;
    }

    public void onStopNestedScroll(View view) {
        if (this.f2323n && !this.f2312b) {
            if (this.f2324o <= this.f2311a.getHeight()) {
                m4384l();
            } else {
                m4385m();
            }
        }
        if (this.f2332w != null) {
            this.f2332w.mo701o();
        }
    }

    public void onWindowSystemUiVisibilityChanged(int i) {
        boolean z = true;
        if (VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i);
        }
        m4393c();
        int i2 = this.f2325p ^ i;
        this.f2325p = i;
        boolean z2 = (i & 4) == 0;
        boolean z3 = (i & 256) != 0;
        if (this.f2332w != null) {
            C0816a c0816a = this.f2332w;
            if (z3) {
                z = false;
            }
            c0816a.mo697g(z);
            if (z2 || !z3) {
                this.f2332w.mo698l();
            } else {
                this.f2332w.mo699m();
            }
        }
        if ((i2 & 256) != 0 && this.f2332w != null) {
            ah.m2834x(this);
        }
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.f2316g = i;
        if (this.f2332w != null) {
            this.f2332w.mo694a(i);
        }
    }

    public void setActionBarHideOffset(int i) {
        m4394d();
        ah.m2795b(this.f2311a, (float) (-Math.max(0, Math.min(i, this.f2311a.getHeight()))));
    }

    public void setActionBarVisibilityCallback(C0816a c0816a) {
        this.f2332w = c0816a;
        if (getWindowToken() != null) {
            this.f2332w.mo694a(this.f2316g);
            if (this.f2325p != 0) {
                onWindowSystemUiVisibilityChanged(this.f2325p);
                ah.m2834x(this);
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean z) {
        this.f2322m = z;
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.f2323n) {
            this.f2323n = z;
            if (!z) {
                m4394d();
                setActionBarHideOffset(0);
            }
        }
    }

    public void setIcon(int i) {
        m4393c();
        this.f2318i.mo966a(i);
    }

    public void setIcon(Drawable drawable) {
        m4393c();
        this.f2318i.mo967a(drawable);
    }

    public void setLogo(int i) {
        m4393c();
        this.f2318i.mo975b(i);
    }

    public void setOverlayMode(boolean z) {
        this.f2321l = z;
        boolean z2 = z && getContext().getApplicationInfo().targetSdkVersion < 19;
        this.f2320k = z2;
    }

    public void setShowingForActionMode(boolean z) {
    }

    public void setUiOptions(int i) {
    }

    public void setWindowCallback(Callback callback) {
        m4393c();
        this.f2318i.mo971a(callback);
    }

    public void setWindowTitle(CharSequence charSequence) {
        m4393c();
        this.f2318i.mo972a(charSequence);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
