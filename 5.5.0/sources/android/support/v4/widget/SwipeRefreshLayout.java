package android.support.v4.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.content.C0235a;
import android.support.v4.view.C0078v;
import android.support.v4.view.C0107x;
import android.support.v4.view.C0659t;
import android.support.v4.view.C0661w;
import android.support.v4.view.C0662y;
import android.support.v4.view.ah;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SwipeRefreshLayout extends ViewGroup implements C0078v, C0107x {
    /* renamed from: D */
    private static final int[] f1442D = new int[]{16842766};
    /* renamed from: m */
    private static final String f1443m = SwipeRefreshLayout.class.getSimpleName();
    /* renamed from: A */
    private int f1444A;
    /* renamed from: B */
    private boolean f1445B;
    /* renamed from: C */
    private final DecelerateInterpolator f1446C;
    /* renamed from: E */
    private int f1447E;
    /* renamed from: F */
    private Animation f1448F;
    /* renamed from: G */
    private Animation f1449G;
    /* renamed from: H */
    private Animation f1450H;
    /* renamed from: I */
    private Animation f1451I;
    /* renamed from: J */
    private Animation f1452J;
    /* renamed from: K */
    private int f1453K;
    /* renamed from: L */
    private C0674a f1454L;
    /* renamed from: M */
    private AnimationListener f1455M;
    /* renamed from: N */
    private final Animation f1456N;
    /* renamed from: O */
    private final Animation f1457O;
    /* renamed from: a */
    C0675b f1458a;
    /* renamed from: b */
    boolean f1459b;
    /* renamed from: c */
    int f1460c;
    /* renamed from: d */
    boolean f1461d;
    /* renamed from: e */
    C0682b f1462e;
    /* renamed from: f */
    protected int f1463f;
    /* renamed from: g */
    float f1464g;
    /* renamed from: h */
    protected int f1465h;
    /* renamed from: i */
    int f1466i;
    /* renamed from: j */
    C0718r f1467j;
    /* renamed from: k */
    boolean f1468k;
    /* renamed from: l */
    boolean f1469l;
    /* renamed from: n */
    private View f1470n;
    /* renamed from: o */
    private int f1471o;
    /* renamed from: p */
    private float f1472p;
    /* renamed from: q */
    private float f1473q;
    /* renamed from: r */
    private final C0662y f1474r;
    /* renamed from: s */
    private final C0661w f1475s;
    /* renamed from: t */
    private final int[] f1476t;
    /* renamed from: u */
    private final int[] f1477u;
    /* renamed from: v */
    private boolean f1478v;
    /* renamed from: w */
    private int f1479w;
    /* renamed from: x */
    private float f1480x;
    /* renamed from: y */
    private float f1481y;
    /* renamed from: z */
    private boolean f1482z;

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$1 */
    class C06661 implements AnimationListener {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1432a;

        C06661(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1432a = swipeRefreshLayout;
        }

        @SuppressLint({"NewApi"})
        public void onAnimationEnd(Animation animation) {
            if (this.f1432a.f1459b) {
                this.f1432a.f1467j.setAlpha(255);
                this.f1432a.f1467j.start();
                if (this.f1432a.f1468k && this.f1432a.f1458a != null) {
                    this.f1432a.f1458a.j_();
                }
                this.f1432a.f1460c = this.f1432a.f1462e.getTop();
                return;
            }
            this.f1432a.m3269a();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$2 */
    class C06672 extends Animation {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1433a;

        C06672(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1433a = swipeRefreshLayout;
        }

        public void applyTransformation(float f, Transformation transformation) {
            this.f1433a.setAnimationProgress(f);
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$3 */
    class C06683 extends Animation {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1434a;

        C06683(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1434a = swipeRefreshLayout;
        }

        public void applyTransformation(float f, Transformation transformation) {
            this.f1434a.setAnimationProgress(1.0f - f);
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$5 */
    class C06705 implements AnimationListener {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1438a;

        C06705(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1438a = swipeRefreshLayout;
        }

        public void onAnimationEnd(Animation animation) {
            if (!this.f1438a.f1461d) {
                this.f1438a.m3272a(null);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$6 */
    class C06716 extends Animation {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1439a;

        C06716(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1439a = swipeRefreshLayout;
        }

        public void applyTransformation(float f, Transformation transformation) {
            this.f1439a.m3271a((((int) (((float) ((!this.f1439a.f1469l ? this.f1439a.f1466i - Math.abs(this.f1439a.f1465h) : this.f1439a.f1466i) - this.f1439a.f1463f)) * f)) + this.f1439a.f1463f) - this.f1439a.f1462e.getTop(), false);
            this.f1439a.f1467j.m3512a(1.0f - f);
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$7 */
    class C06727 extends Animation {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1440a;

        C06727(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1440a = swipeRefreshLayout;
        }

        public void applyTransformation(float f, Transformation transformation) {
            this.f1440a.m3270a(f);
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$8 */
    class C06738 extends Animation {
        /* renamed from: a */
        final /* synthetic */ SwipeRefreshLayout f1441a;

        C06738(SwipeRefreshLayout swipeRefreshLayout) {
            this.f1441a = swipeRefreshLayout;
        }

        public void applyTransformation(float f, Transformation transformation) {
            this.f1441a.setAnimationProgress(this.f1441a.f1464g + ((-this.f1441a.f1464g) * f));
            this.f1441a.m3270a(f);
        }
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$a */
    public interface C0674a {
        /* renamed from: a */
        boolean m3252a(SwipeRefreshLayout swipeRefreshLayout, View view);
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$b */
    public interface C0675b {
        void j_();
    }

    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f1459b = false;
        this.f1472p = -1.0f;
        this.f1476t = new int[2];
        this.f1477u = new int[2];
        this.f1444A = -1;
        this.f1447E = -1;
        this.f1455M = new C06661(this);
        this.f1456N = new C06716(this);
        this.f1457O = new C06727(this);
        this.f1471o = ViewConfiguration.get(context).getScaledTouchSlop();
        this.f1479w = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.f1446C = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.f1453K = (int) (40.0f * displayMetrics.density);
        m3261c();
        ah.m2790a((ViewGroup) this, true);
        this.f1466i = (int) (displayMetrics.density * 64.0f);
        this.f1472p = (float) this.f1466i;
        this.f1474r = new C0662y(this);
        this.f1475s = new C0661w(this);
        setNestedScrollingEnabled(true);
        int i = -this.f1453K;
        this.f1460c = i;
        this.f1465h = i;
        m3270a(1.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1442D);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a */
    private Animation m3253a(final int i, final int i2) {
        if (this.f1461d && m3265d()) {
            return null;
        }
        Animation c06694 = new Animation(this) {
            /* renamed from: c */
            final /* synthetic */ SwipeRefreshLayout f1437c;

            public void applyTransformation(float f, Transformation transformation) {
                this.f1437c.f1467j.setAlpha((int) (((float) i) + (((float) (i2 - i)) * f)));
            }
        };
        c06694.setDuration(300);
        this.f1462e.m3358a(null);
        this.f1462e.clearAnimation();
        this.f1462e.startAnimation(c06694);
        return c06694;
    }

    /* renamed from: a */
    private void m3254a(int i, AnimationListener animationListener) {
        this.f1463f = i;
        this.f1456N.reset();
        this.f1456N.setDuration(200);
        this.f1456N.setInterpolator(this.f1446C);
        if (animationListener != null) {
            this.f1462e.m3358a(animationListener);
        }
        this.f1462e.clearAnimation();
        this.f1462e.startAnimation(this.f1456N);
    }

    /* renamed from: a */
    private void m3255a(MotionEvent motionEvent) {
        int b = C0659t.m3206b(motionEvent);
        if (motionEvent.getPointerId(b) == this.f1444A) {
            this.f1444A = motionEvent.getPointerId(b == 0 ? 1 : 0);
        }
    }

    /* renamed from: a */
    private void m3256a(boolean z, boolean z2) {
        if (this.f1459b != z) {
            this.f1468k = z2;
            m3268g();
            this.f1459b = z;
            if (this.f1459b) {
                m3254a(this.f1460c, this.f1455M);
            } else {
                m3272a(this.f1455M);
            }
        }
    }

    /* renamed from: a */
    private boolean m3257a(Animation animation) {
        return (animation == null || !animation.hasStarted() || animation.hasEnded()) ? false : true;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: b */
    private void m3258b(float f) {
        this.f1467j.m3516a(true);
        float min = Math.min(1.0f, Math.abs(f / this.f1472p));
        float max = (((float) Math.max(((double) min) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float abs = Math.abs(f) - this.f1472p;
        float f2 = this.f1469l ? (float) (this.f1466i - this.f1465h) : (float) this.f1466i;
        abs = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(abs, f2 * 2.0f) / f2);
        abs = ((float) (((double) (abs / 4.0f)) - Math.pow((double) (abs / 4.0f), 2.0d))) * 2.0f;
        int i = ((int) ((f2 * min) + ((f2 * abs) * 2.0f))) + this.f1465h;
        if (this.f1462e.getVisibility() != 0) {
            this.f1462e.setVisibility(0);
        }
        if (!this.f1461d) {
            ah.m2813g(this.f1462e, 1.0f);
            ah.m2815h(this.f1462e, 1.0f);
        }
        if (this.f1461d) {
            setAnimationProgress(Math.min(1.0f, f / this.f1472p));
        }
        if (f < this.f1472p) {
            if (this.f1467j.getAlpha() > 76 && !m3257a(this.f1450H)) {
                m3266e();
            }
        } else if (this.f1467j.getAlpha() < 255 && !m3257a(this.f1451I)) {
            m3267f();
        }
        this.f1467j.m3513a((float) BitmapDescriptorFactory.HUE_RED, Math.min(0.8f, max * 0.8f));
        this.f1467j.m3512a(Math.min(1.0f, max));
        this.f1467j.m3518b(((-0.25f + (max * 0.4f)) + (abs * 2.0f)) * 0.5f);
        m3271a(i - this.f1460c, true);
    }

    /* renamed from: b */
    private void m3259b(int i, AnimationListener animationListener) {
        if (this.f1461d) {
            m3263c(i, animationListener);
            return;
        }
        this.f1463f = i;
        this.f1457O.reset();
        this.f1457O.setDuration(200);
        this.f1457O.setInterpolator(this.f1446C);
        if (animationListener != null) {
            this.f1462e.m3358a(animationListener);
        }
        this.f1462e.clearAnimation();
        this.f1462e.startAnimation(this.f1457O);
    }

    @SuppressLint({"NewApi"})
    /* renamed from: b */
    private void m3260b(AnimationListener animationListener) {
        this.f1462e.setVisibility(0);
        if (VERSION.SDK_INT >= 11) {
            this.f1467j.setAlpha(255);
        }
        this.f1448F = new C06672(this);
        this.f1448F.setDuration((long) this.f1479w);
        if (animationListener != null) {
            this.f1462e.m3358a(animationListener);
        }
        this.f1462e.clearAnimation();
        this.f1462e.startAnimation(this.f1448F);
    }

    /* renamed from: c */
    private void m3261c() {
        this.f1462e = new C0682b(getContext(), -328966);
        this.f1467j = new C0718r(getContext(), this);
        this.f1467j.m3520b(-328966);
        this.f1462e.setImageDrawable(this.f1467j);
        this.f1462e.setVisibility(8);
        addView(this.f1462e);
    }

    /* renamed from: c */
    private void m3262c(float f) {
        if (f > this.f1472p) {
            m3256a(true, true);
            return;
        }
        this.f1459b = false;
        this.f1467j.m3513a((float) BitmapDescriptorFactory.HUE_RED, (float) BitmapDescriptorFactory.HUE_RED);
        AnimationListener animationListener = null;
        if (!this.f1461d) {
            animationListener = new C06705(this);
        }
        m3259b(this.f1460c, animationListener);
        this.f1467j.m3516a(false);
    }

    @SuppressLint({"NewApi"})
    /* renamed from: c */
    private void m3263c(int i, AnimationListener animationListener) {
        this.f1463f = i;
        if (m3265d()) {
            this.f1464g = (float) this.f1467j.getAlpha();
        } else {
            this.f1464g = ah.m2828r(this.f1462e);
        }
        this.f1452J = new C06738(this);
        this.f1452J.setDuration(150);
        if (animationListener != null) {
            this.f1462e.m3358a(animationListener);
        }
        this.f1462e.clearAnimation();
        this.f1462e.startAnimation(this.f1452J);
    }

    @SuppressLint({"NewApi"})
    /* renamed from: d */
    private void m3264d(float f) {
        if (f - this.f1481y > ((float) this.f1471o) && !this.f1482z) {
            this.f1480x = this.f1481y + ((float) this.f1471o);
            this.f1482z = true;
            this.f1467j.setAlpha(76);
        }
    }

    /* renamed from: d */
    private boolean m3265d() {
        return VERSION.SDK_INT < 11;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: e */
    private void m3266e() {
        this.f1450H = m3253a(this.f1467j.getAlpha(), 76);
    }

    @SuppressLint({"NewApi"})
    /* renamed from: f */
    private void m3267f() {
        this.f1451I = m3253a(this.f1467j.getAlpha(), 255);
    }

    /* renamed from: g */
    private void m3268g() {
        if (this.f1470n == null) {
            int i = 0;
            while (i < getChildCount()) {
                View childAt = getChildAt(i);
                if (childAt.equals(this.f1462e)) {
                    i++;
                } else {
                    this.f1470n = childAt;
                    return;
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void setColorViewAlpha(int i) {
        this.f1462e.getBackground().setAlpha(i);
        this.f1467j.setAlpha(i);
    }

    /* renamed from: a */
    void m3269a() {
        this.f1462e.clearAnimation();
        this.f1467j.stop();
        this.f1462e.setVisibility(8);
        setColorViewAlpha(255);
        if (this.f1461d) {
            setAnimationProgress(BitmapDescriptorFactory.HUE_RED);
        } else {
            m3271a(this.f1465h - this.f1460c, true);
        }
        this.f1460c = this.f1462e.getTop();
    }

    /* renamed from: a */
    void m3270a(float f) {
        m3271a((this.f1463f + ((int) (((float) (this.f1465h - this.f1463f)) * f))) - this.f1462e.getTop(), false);
    }

    /* renamed from: a */
    void m3271a(int i, boolean z) {
        this.f1462e.bringToFront();
        ah.m2808e(this.f1462e, i);
        this.f1460c = this.f1462e.getTop();
        if (z && VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    /* renamed from: a */
    void m3272a(AnimationListener animationListener) {
        this.f1449G = new C06683(this);
        this.f1449G.setDuration(150);
        this.f1462e.m3358a(animationListener);
        this.f1462e.clearAnimation();
        this.f1462e.startAnimation(this.f1449G);
    }

    /* renamed from: a */
    public void m3273a(boolean z, int i, int i2) {
        this.f1461d = z;
        this.f1465h = i;
        this.f1466i = i2;
        this.f1469l = true;
        m3269a();
        this.f1459b = false;
    }

    /* renamed from: b */
    public boolean m3274b() {
        boolean z = false;
        if (this.f1454L != null) {
            return this.f1454L.m3252a(this, this.f1470n);
        }
        if (VERSION.SDK_INT >= 14) {
            return ah.m2798b(this.f1470n, -1);
        }
        if (this.f1470n instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.f1470n;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        } else {
            if (ah.m2798b(this.f1470n, -1) || this.f1470n.getScrollY() > 0) {
                z = true;
            }
            return z;
        }
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.f1475s.m3211a(f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.f1475s.m3210a(f, f2);
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.f1475s.m3214a(i, i2, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.f1475s.m3213a(i, i2, i3, i4, iArr);
    }

    protected int getChildDrawingOrder(int i, int i2) {
        return this.f1447E < 0 ? i2 : i2 == i + -1 ? this.f1447E : i2 >= this.f1447E ? i2 + 1 : i2;
    }

    public int getNestedScrollAxes() {
        return this.f1474r.m3217a();
    }

    public int getProgressCircleDiameter() {
        return this.f1453K;
    }

    public int getProgressViewEndOffset() {
        return this.f1466i;
    }

    public int getProgressViewStartOffset() {
        return this.f1465h;
    }

    public boolean hasNestedScrollingParent() {
        return this.f1475s.m3215b();
    }

    public boolean isNestedScrollingEnabled() {
        return this.f1475s.m3209a();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        m3269a();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        m3268g();
        int a = C0659t.m3205a(motionEvent);
        if (this.f1445B && a == 0) {
            this.f1445B = false;
        }
        if (!isEnabled() || this.f1445B || m3274b() || this.f1459b || this.f1478v) {
            return false;
        }
        switch (a) {
            case 0:
                m3271a(this.f1465h - this.f1462e.getTop(), true);
                this.f1444A = motionEvent.getPointerId(0);
                this.f1482z = false;
                a = motionEvent.findPointerIndex(this.f1444A);
                if (a >= 0) {
                    this.f1481y = motionEvent.getY(a);
                    break;
                }
                return false;
            case 1:
            case 3:
                this.f1482z = false;
                this.f1444A = -1;
                break;
            case 2:
                if (this.f1444A == -1) {
                    Log.e(f1443m, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                a = motionEvent.findPointerIndex(this.f1444A);
                if (a >= 0) {
                    m3264d(motionEvent.getY(a));
                    break;
                }
                return false;
            case 6:
                m3255a(motionEvent);
                break;
        }
        return this.f1482z;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.f1470n == null) {
                m3268g();
            }
            if (this.f1470n != null) {
                View view = this.f1470n;
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
                measuredHeight = this.f1462e.getMeasuredWidth();
                this.f1462e.layout((measuredWidth / 2) - (measuredHeight / 2), this.f1460c, (measuredWidth / 2) + (measuredHeight / 2), this.f1460c + this.f1462e.getMeasuredHeight());
            }
        }
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.f1470n == null) {
            m3268g();
        }
        if (this.f1470n != null) {
            this.f1470n.measure(MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.f1462e.measure(MeasureSpec.makeMeasureSpec(this.f1453K, 1073741824), MeasureSpec.makeMeasureSpec(this.f1453K, 1073741824));
            this.f1447E = -1;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                if (getChildAt(i3) == this.f1462e) {
                    this.f1447E = i3;
                    return;
                }
            }
        }
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (i2 > 0 && this.f1473q > BitmapDescriptorFactory.HUE_RED) {
            if (((float) i2) > this.f1473q) {
                iArr[1] = i2 - ((int) this.f1473q);
                this.f1473q = BitmapDescriptorFactory.HUE_RED;
            } else {
                this.f1473q -= (float) i2;
                iArr[1] = i2;
            }
            m3258b(this.f1473q);
        }
        if (this.f1469l && i2 > 0 && this.f1473q == BitmapDescriptorFactory.HUE_RED && Math.abs(i2 - iArr[1]) > 0) {
            this.f1462e.setVisibility(8);
        }
        int[] iArr2 = this.f1476t;
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr2[1] + iArr[1];
        }
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        dispatchNestedScroll(i, i2, i3, i4, this.f1477u);
        int i5 = this.f1477u[1] + i4;
        if (i5 < 0 && !m3274b()) {
            this.f1473q = ((float) Math.abs(i5)) + this.f1473q;
            m3258b(this.f1473q);
        }
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.f1474r.m3219a(view, view2, i);
        startNestedScroll(i & 2);
        this.f1473q = BitmapDescriptorFactory.HUE_RED;
        this.f1478v = true;
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (!isEnabled() || this.f1445B || this.f1459b || (i & 2) == 0) ? false : true;
    }

    public void onStopNestedScroll(View view) {
        this.f1474r.m3218a(view);
        this.f1478v = false;
        if (this.f1473q > BitmapDescriptorFactory.HUE_RED) {
            m3262c(this.f1473q);
            this.f1473q = BitmapDescriptorFactory.HUE_RED;
        }
        stopNestedScroll();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int a = C0659t.m3205a(motionEvent);
        if (this.f1445B && a == 0) {
            this.f1445B = false;
        }
        if (!isEnabled() || this.f1445B || m3274b() || this.f1459b || this.f1478v) {
            return false;
        }
        float y;
        switch (a) {
            case 0:
                this.f1444A = motionEvent.getPointerId(0);
                this.f1482z = false;
                break;
            case 1:
                a = motionEvent.findPointerIndex(this.f1444A);
                if (a < 0) {
                    Log.e(f1443m, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }
                if (this.f1482z) {
                    y = (motionEvent.getY(a) - this.f1480x) * 0.5f;
                    this.f1482z = false;
                    m3262c(y);
                }
                this.f1444A = -1;
                return false;
            case 2:
                a = motionEvent.findPointerIndex(this.f1444A);
                if (a < 0) {
                    Log.e(f1443m, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                y = motionEvent.getY(a);
                m3264d(y);
                if (this.f1482z) {
                    y = (y - this.f1480x) * 0.5f;
                    if (y > BitmapDescriptorFactory.HUE_RED) {
                        m3258b(y);
                        break;
                    }
                    return false;
                }
                break;
            case 3:
                return false;
            case 5:
                a = C0659t.m3206b(motionEvent);
                if (a >= 0) {
                    this.f1444A = motionEvent.getPointerId(a);
                    break;
                }
                Log.e(f1443m, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                return false;
            case 6:
                m3255a(motionEvent);
                break;
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (VERSION.SDK_INT < 21 && (this.f1470n instanceof AbsListView)) {
            return;
        }
        if (this.f1470n == null || ah.m2765E(this.f1470n)) {
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    void setAnimationProgress(float f) {
        if (m3265d()) {
            setColorViewAlpha((int) (255.0f * f));
            return;
        }
        ah.m2813g(this.f1462e, f);
        ah.m2815h(this.f1462e, f);
    }

    @Deprecated
    public void setColorScheme(int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(int... iArr) {
        m3268g();
        this.f1467j.m3517a(iArr);
    }

    public void setColorSchemeResources(int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = C0235a.m1075c(context, iArr[i]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i) {
        this.f1472p = (float) i;
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (!z) {
            m3269a();
        }
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.f1475s.m3208a(z);
    }

    public void setOnChildScrollUpCallback(C0674a c0674a) {
        this.f1454L = c0674a;
    }

    public void setOnRefreshListener(C0675b c0675b) {
        this.f1458a = c0675b;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i) {
        setProgressBackgroundColorSchemeResource(i);
    }

    public void setProgressBackgroundColorSchemeColor(int i) {
        this.f1462e.setBackgroundColor(i);
        this.f1467j.m3520b(i);
    }

    public void setProgressBackgroundColorSchemeResource(int i) {
        setProgressBackgroundColorSchemeColor(C0235a.m1075c(getContext(), i));
    }

    public void setRefreshing(boolean z) {
        if (!z || this.f1459b == z) {
            m3256a(z, false);
            return;
        }
        this.f1459b = z;
        m3271a((!this.f1469l ? this.f1466i + this.f1465h : this.f1466i) - this.f1460c, true);
        this.f1468k = false;
        m3260b(this.f1455M);
    }

    public void setSize(int i) {
        if (i == 0 || i == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i == 0) {
                this.f1453K = (int) (displayMetrics.density * 56.0f);
            } else {
                this.f1453K = (int) (displayMetrics.density * 40.0f);
            }
            this.f1462e.setImageDrawable(null);
            this.f1467j.m3515a(i);
            this.f1462e.setImageDrawable(this.f1467j);
        }
    }

    public boolean startNestedScroll(int i) {
        return this.f1475s.m3212a(i);
    }

    public void stopNestedScroll() {
        this.f1475s.m3216c();
    }
}
