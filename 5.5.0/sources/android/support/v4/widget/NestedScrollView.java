package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.C0074a;
import android.support.v4.view.C0078v;
import android.support.v4.view.C0107x;
import android.support.v4.view.C0659t;
import android.support.v4.view.C0661w;
import android.support.v4.view.C0662y;
import android.support.v4.view.ad;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.support.v4.view.p023a.C0510a;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0556o;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public class NestedScrollView extends FrameLayout implements ad, C0078v, C0107x {
    /* renamed from: v */
    private static final C0664a f1405v = new C0664a();
    /* renamed from: w */
    private static final int[] f1406w = new int[]{16843130};
    /* renamed from: A */
    private C0665b f1407A;
    /* renamed from: a */
    private long f1408a;
    /* renamed from: b */
    private final Rect f1409b;
    /* renamed from: c */
    private C0729x f1410c;
    /* renamed from: d */
    private C0700i f1411d;
    /* renamed from: e */
    private C0700i f1412e;
    /* renamed from: f */
    private int f1413f;
    /* renamed from: g */
    private boolean f1414g;
    /* renamed from: h */
    private boolean f1415h;
    /* renamed from: i */
    private View f1416i;
    /* renamed from: j */
    private boolean f1417j;
    /* renamed from: k */
    private VelocityTracker f1418k;
    /* renamed from: l */
    private boolean f1419l;
    /* renamed from: m */
    private boolean f1420m;
    /* renamed from: n */
    private int f1421n;
    /* renamed from: o */
    private int f1422o;
    /* renamed from: p */
    private int f1423p;
    /* renamed from: q */
    private int f1424q;
    /* renamed from: r */
    private final int[] f1425r;
    /* renamed from: s */
    private final int[] f1426s;
    /* renamed from: t */
    private int f1427t;
    /* renamed from: u */
    private SavedState f1428u;
    /* renamed from: x */
    private final C0662y f1429x;
    /* renamed from: y */
    private final C0661w f1430y;
    /* renamed from: z */
    private float f1431z;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C06631();
        /* renamed from: a */
        public int f1404a;

        /* renamed from: android.support.v4.widget.NestedScrollView$SavedState$1 */
        static class C06631 implements Creator<SavedState> {
            C06631() {
            }

            /* renamed from: a */
            public SavedState m3220a(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] m3221a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m3220a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m3221a(i);
            }
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.f1404a = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.f1404a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f1404a);
        }
    }

    /* renamed from: android.support.v4.widget.NestedScrollView$a */
    static class C0664a extends C0074a {
        C0664a() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            C0556o a = C0510a.m2132a(accessibilityEvent);
            a.m2485d(nestedScrollView.getScrollRange() > 0);
            a.m2484d(nestedScrollView.getScrollX());
            a.m2486e(nestedScrollView.getScrollY());
            a.m2487f(nestedScrollView.getScrollX());
            a.m2488g(nestedScrollView.getScrollRange());
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            c0531e.m2313b(ScrollView.class.getName());
            if (nestedScrollView.isEnabled()) {
                int scrollRange = nestedScrollView.getScrollRange();
                if (scrollRange > 0) {
                    c0531e.m2336i(true);
                    if (nestedScrollView.getScrollY() > 0) {
                        c0531e.m2305a((int) MessagesController.UPDATE_MASK_CHANNEL);
                    }
                    if (nestedScrollView.getScrollY() < scrollRange) {
                        c0531e.m2305a(4096);
                    }
                }
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            int min;
            switch (i) {
                case 4096:
                    min = Math.min(((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()) + nestedScrollView.getScrollY(), nestedScrollView.getScrollRange());
                    if (min == nestedScrollView.getScrollY()) {
                        return false;
                    }
                    nestedScrollView.m3247b(0, min);
                    return true;
                case MessagesController.UPDATE_MASK_CHANNEL /*8192*/:
                    min = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                    if (min == nestedScrollView.getScrollY()) {
                        return false;
                    }
                    nestedScrollView.m3247b(0, min);
                    return true;
                default:
                    return false;
            }
        }
    }

    /* renamed from: android.support.v4.widget.NestedScrollView$b */
    public interface C0665b {
        /* renamed from: a */
        void mo599a(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4);
    }

    public NestedScrollView(Context context) {
        this(context, null);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f1409b = new Rect();
        this.f1414g = true;
        this.f1415h = false;
        this.f1416i = null;
        this.f1417j = false;
        this.f1420m = true;
        this.f1424q = -1;
        this.f1425r = new int[2];
        this.f1426s = new int[2];
        m3224a();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1406w, i, 0);
        setFillViewport(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        this.f1429x = new C0662y(this);
        this.f1430y = new C0661w(this);
        setNestedScrollingEnabled(true);
        ah.m2783a((View) this, f1405v);
    }

    /* renamed from: a */
    private View m3223a(boolean z, int i, int i2) {
        List focusables = getFocusables(2);
        View view = null;
        Object obj = null;
        int size = focusables.size();
        int i3 = 0;
        while (i3 < size) {
            View view2;
            Object obj2;
            View view3 = (View) focusables.get(i3);
            int top = view3.getTop();
            int bottom = view3.getBottom();
            if (i < bottom && top < i2) {
                Object obj3 = (i >= top || bottom >= i2) ? null : 1;
                if (view == null) {
                    Object obj4 = obj3;
                    view2 = view3;
                    obj2 = obj4;
                } else {
                    Object obj5 = ((!z || top >= view.getTop()) && (z || bottom <= view.getBottom())) ? null : 1;
                    if (obj != null) {
                        if (!(obj3 == null || obj5 == null)) {
                            view2 = view3;
                            obj2 = obj;
                        }
                    } else if (obj3 != null) {
                        view2 = view3;
                        int i4 = 1;
                    } else if (obj5 != null) {
                        view2 = view3;
                        obj2 = obj;
                    }
                }
                i3++;
                view = view2;
                obj = obj2;
            }
            obj2 = obj;
            view2 = view;
            i3++;
            view = view2;
            obj = obj2;
        }
        return view;
    }

    /* renamed from: a */
    private void m3224a() {
        this.f1410c = C0729x.m3542a(getContext(), null);
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.f1421n = viewConfiguration.getScaledTouchSlop();
        this.f1422o = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f1423p = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    /* renamed from: a */
    private void m3225a(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.f1424q) {
            action = action == 0 ? 1 : 0;
            this.f1413f = (int) motionEvent.getY(action);
            this.f1424q = motionEvent.getPointerId(action);
            if (this.f1418k != null) {
                this.f1418k.clear();
            }
        }
    }

    /* renamed from: a */
    private boolean m3226a(int i, int i2, int i3) {
        boolean z = false;
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = scrollY + height;
        boolean z2 = i == 33;
        View a = m3223a(z2, i2, i3);
        if (a == null) {
            a = this;
        }
        if (i2 < scrollY || i3 > i4) {
            m3238e(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        }
        if (a != findFocus()) {
            a.requestFocus(i);
        }
        return z;
    }

    /* renamed from: a */
    private boolean m3227a(Rect rect, boolean z) {
        int a = m3242a(rect);
        boolean z2 = a != 0;
        if (z2) {
            if (z) {
                scrollBy(0, a);
            } else {
                m3243a(0, a);
            }
        }
        return z2;
    }

    /* renamed from: a */
    private boolean m3228a(View view) {
        return !m3229a(view, 0, getHeight());
    }

    /* renamed from: a */
    private boolean m3229a(View view, int i, int i2) {
        view.getDrawingRect(this.f1409b);
        offsetDescendantRectToMyCoords(view, this.f1409b);
        return this.f1409b.bottom + i >= getScrollY() && this.f1409b.top - i <= getScrollY() + i2;
    }

    /* renamed from: a */
    private static boolean m3230a(View view, View view2) {
        if (view == view2) {
            return true;
        }
        ViewParent parent = view.getParent();
        boolean z = (parent instanceof ViewGroup) && m3230a((View) parent, view2);
        return z;
    }

    /* renamed from: b */
    private static int m3231b(int i, int i2, int i3) {
        return (i2 >= i3 || i < 0) ? 0 : i2 + i > i3 ? i3 - i2 : i;
    }

    /* renamed from: b */
    private void m3232b(View view) {
        view.getDrawingRect(this.f1409b);
        offsetDescendantRectToMyCoords(view, this.f1409b);
        int a = m3242a(this.f1409b);
        if (a != 0) {
            scrollBy(0, a);
        }
    }

    /* renamed from: b */
    private boolean m3233b() {
        View childAt = getChildAt(0);
        if (childAt == null) {
            return false;
        }
        return getHeight() < (childAt.getHeight() + getPaddingTop()) + getPaddingBottom();
    }

    /* renamed from: c */
    private void m3234c() {
        if (this.f1418k == null) {
            this.f1418k = VelocityTracker.obtain();
        } else {
            this.f1418k.clear();
        }
    }

    /* renamed from: c */
    private boolean m3235c(int i, int i2) {
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollY = getScrollY();
        View childAt = getChildAt(0);
        return i2 >= childAt.getTop() - scrollY && i2 < childAt.getBottom() - scrollY && i >= childAt.getLeft() && i < childAt.getRight();
    }

    /* renamed from: d */
    private void m3236d() {
        if (this.f1418k == null) {
            this.f1418k = VelocityTracker.obtain();
        }
    }

    /* renamed from: e */
    private void m3237e() {
        if (this.f1418k != null) {
            this.f1418k.recycle();
            this.f1418k = null;
        }
    }

    /* renamed from: e */
    private void m3238e(int i) {
        if (i == 0) {
            return;
        }
        if (this.f1420m) {
            m3243a(0, i);
        } else {
            scrollBy(0, i);
        }
    }

    /* renamed from: f */
    private void m3239f() {
        this.f1417j = false;
        m3237e();
        stopNestedScroll();
        if (this.f1411d != null) {
            this.f1411d.m3426c();
            this.f1412e.m3426c();
        }
    }

    /* renamed from: f */
    private void m3240f(int i) {
        int scrollY = getScrollY();
        boolean z = (scrollY > 0 || i > 0) && (scrollY < getScrollRange() || i < 0);
        if (!dispatchNestedPreFling(BitmapDescriptorFactory.HUE_RED, (float) i)) {
            dispatchNestedFling(BitmapDescriptorFactory.HUE_RED, (float) i, z);
            if (z) {
                m3250d(i);
            }
        }
    }

    /* renamed from: g */
    private void m3241g() {
        if (getOverScrollMode() == 2) {
            this.f1411d = null;
            this.f1412e = null;
        } else if (this.f1411d == null) {
            Context context = getContext();
            this.f1411d = new C0700i(context);
            this.f1412e = new C0700i(context);
        }
    }

    private float getVerticalScrollFactorCompat() {
        if (this.f1431z == BitmapDescriptorFactory.HUE_RED) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                this.f1431z = typedValue.getDimension(context.getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
        }
        return this.f1431z;
    }

    /* renamed from: a */
    protected int m3242a(Rect rect) {
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i && rect.top > scrollY) {
            scrollY = Math.min(rect.height() > height ? (rect.top - scrollY) + 0 : (rect.bottom - i) + 0, getChildAt(0).getBottom() - i);
        } else if (rect.top >= scrollY || rect.bottom >= i) {
            scrollY = 0;
        } else {
            scrollY = Math.max(rect.height() > height ? 0 - (i - rect.bottom) : 0 - (scrollY - rect.top), -getScrollY());
        }
        return scrollY;
    }

    /* renamed from: a */
    public final void m3243a(int i, int i2) {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.f1408a > 250) {
                int max = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
                int scrollY = getScrollY();
                this.f1410c.m3543a(getScrollX(), scrollY, 0, Math.max(0, Math.min(scrollY + i2, max)) - scrollY);
                ah.m2799c(this);
            } else {
                if (!this.f1410c.m3547a()) {
                    this.f1410c.m3555h();
                }
                scrollBy(i, i2);
            }
            this.f1408a = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    /* renamed from: a */
    public boolean m3244a(int i) {
        int i2 = i == TsExtractor.TS_STREAM_TYPE_HDMV_DTS ? 1 : 0;
        int height = getHeight();
        if (i2 != 0) {
            this.f1409b.top = getScrollY() + height;
            i2 = getChildCount();
            if (i2 > 0) {
                View childAt = getChildAt(i2 - 1);
                if (this.f1409b.top + height > childAt.getBottom()) {
                    this.f1409b.top = childAt.getBottom() - height;
                }
            }
        } else {
            this.f1409b.top = getScrollY() - height;
            if (this.f1409b.top < 0) {
                this.f1409b.top = 0;
            }
        }
        this.f1409b.bottom = this.f1409b.top + height;
        return m3226a(i, this.f1409b.top, this.f1409b.bottom);
    }

    /* renamed from: a */
    boolean m3245a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        boolean z2;
        boolean z3;
        int overScrollMode = getOverScrollMode();
        Object obj = computeHorizontalScrollRange() > computeHorizontalScrollExtent() ? 1 : null;
        Object obj2 = computeVerticalScrollRange() > computeVerticalScrollExtent() ? 1 : null;
        Object obj3 = (overScrollMode == 0 || (overScrollMode == 1 && obj != null)) ? 1 : null;
        obj = (overScrollMode == 0 || (overScrollMode == 1 && obj2 != null)) ? 1 : null;
        int i9 = i3 + i;
        if (obj3 == null) {
            i7 = 0;
        }
        int i10 = i4 + i2;
        if (obj == null) {
            i8 = 0;
        }
        int i11 = -i7;
        int i12 = i7 + i5;
        overScrollMode = -i8;
        int i13 = i8 + i6;
        if (i9 > i12) {
            z2 = true;
        } else if (i9 < i11) {
            z2 = true;
            i12 = i11;
        } else {
            z2 = false;
            i12 = i9;
        }
        if (i10 > i13) {
            z3 = true;
        } else if (i10 < overScrollMode) {
            z3 = true;
            i13 = overScrollMode;
        } else {
            z3 = false;
            i13 = i10;
        }
        if (z3) {
            this.f1410c.m3548a(i12, i13, 0, 0, 0, getScrollRange());
        }
        onOverScrolled(i12, i13, z2, z3);
        return z2 || z3;
    }

    /* renamed from: a */
    public boolean m3246a(KeyEvent keyEvent) {
        int i = 33;
        this.f1409b.setEmpty();
        if (m3233b()) {
            if (keyEvent.getAction() != 0) {
                return false;
            }
            switch (keyEvent.getKeyCode()) {
                case 19:
                    return !keyEvent.isAltPressed() ? m3249c(33) : m3248b(33);
                case 20:
                    return !keyEvent.isAltPressed() ? m3249c(TsExtractor.TS_STREAM_TYPE_HDMV_DTS) : m3248b((int) TsExtractor.TS_STREAM_TYPE_HDMV_DTS);
                case 62:
                    if (!keyEvent.isShiftPressed()) {
                        i = TsExtractor.TS_STREAM_TYPE_HDMV_DTS;
                    }
                    m3244a(i);
                    return false;
                default:
                    return false;
            }
        } else if (!isFocused() || keyEvent.getKeyCode() == 4) {
            return false;
        } else {
            View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            findFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, TsExtractor.TS_STREAM_TYPE_HDMV_DTS);
            boolean z = (findFocus == null || findFocus == this || !findFocus.requestFocus(TsExtractor.TS_STREAM_TYPE_HDMV_DTS)) ? false : true;
            return z;
        }
    }

    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int i) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    /* renamed from: b */
    public final void m3247b(int i, int i2) {
        m3243a(i - getScrollX(), i2 - getScrollY());
    }

    /* renamed from: b */
    public boolean m3248b(int i) {
        int i2 = i == TsExtractor.TS_STREAM_TYPE_HDMV_DTS ? 1 : 0;
        int height = getHeight();
        this.f1409b.top = 0;
        this.f1409b.bottom = height;
        if (i2 != 0) {
            i2 = getChildCount();
            if (i2 > 0) {
                this.f1409b.bottom = getChildAt(i2 - 1).getBottom() + getPaddingBottom();
                this.f1409b.top = this.f1409b.bottom - height;
            }
        }
        return m3226a(i, this.f1409b.top, this.f1409b.bottom);
    }

    /* renamed from: c */
    public boolean m3249c(int i) {
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus == null || !m3229a(findNextFocus, maxScrollAmount, getHeight())) {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i == TsExtractor.TS_STREAM_TYPE_HDMV_DTS && getChildCount() > 0) {
                int bottom = getChildAt(0).getBottom();
                int scrollY = (getScrollY() + getHeight()) - getPaddingBottom();
                if (bottom - scrollY < maxScrollAmount) {
                    maxScrollAmount = bottom - scrollY;
                }
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != TsExtractor.TS_STREAM_TYPE_HDMV_DTS) {
                maxScrollAmount = -maxScrollAmount;
            }
            m3238e(maxScrollAmount);
        } else {
            findNextFocus.getDrawingRect(this.f1409b);
            offsetDescendantRectToMyCoords(findNextFocus, this.f1409b);
            m3238e(m3242a(this.f1409b));
            findNextFocus.requestFocus(i);
        }
        if (findFocus != null && findFocus.isFocused() && m3228a(findFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    public void computeScroll() {
        if (this.f1410c.m3554g()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int b = this.f1410c.m3549b();
            int c = this.f1410c.m3550c();
            if (scrollX != b || scrollY != c) {
                int scrollRange = getScrollRange();
                int overScrollMode = getOverScrollMode();
                int i = (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) ? 1 : 0;
                m3245a(b - scrollX, c - scrollY, scrollX, scrollY, 0, scrollRange, 0, 0, false);
                if (i != 0) {
                    m3241g();
                    if (c <= 0 && scrollY > 0) {
                        this.f1411d.m3423a((int) this.f1410c.m3553f());
                    } else if (c >= scrollRange && scrollY < scrollRange) {
                        this.f1412e.m3423a((int) this.f1410c.m3553f());
                    }
                }
            }
        }
    }

    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    public int computeVerticalScrollRange() {
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (getChildCount() == 0) {
            return height;
        }
        int bottom = getChildAt(0).getBottom();
        int scrollY = getScrollY();
        height = Math.max(0, bottom - height);
        return scrollY < 0 ? bottom - scrollY : scrollY > height ? bottom + (scrollY - height) : bottom;
    }

    /* renamed from: d */
    public void m3250d(int i) {
        if (getChildCount() > 0) {
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            int height2 = getChildAt(0).getHeight();
            this.f1410c.m3546a(getScrollX(), getScrollY(), 0, i, 0, 0, 0, Math.max(0, height2 - height), 0, height / 2);
            ah.m2799c(this);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || m3246a(keyEvent);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.f1430y.m3211a(f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.f1430y.m3210a(f, f2);
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.f1430y.m3214a(i, i2, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.f1430y.m3213a(i, i2, i3, i4, iArr);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.f1411d != null) {
            int save;
            int width;
            int scrollY = getScrollY();
            if (!this.f1411d.m3420a()) {
                save = canvas.save();
                width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                canvas.translate((float) getPaddingLeft(), (float) Math.min(0, scrollY));
                this.f1411d.m3419a(width, getHeight());
                if (this.f1411d.m3424a(canvas)) {
                    ah.m2799c(this);
                }
                canvas.restoreToCount(save);
            }
            if (!this.f1412e.m3420a()) {
                save = canvas.save();
                width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                int height = getHeight();
                canvas.translate((float) ((-width) + getPaddingLeft()), (float) (Math.max(getScrollRange(), scrollY) + height));
                canvas.rotate(180.0f, (float) width, BitmapDescriptorFactory.HUE_RED);
                this.f1412e.m3419a(width, height);
                if (this.f1412e.m3424a(canvas)) {
                    ah.m2799c(this);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    protected float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom());
        return bottom < verticalFadingEdgeLength ? ((float) bottom) / ((float) verticalFadingEdgeLength) : 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (0.5f * ((float) getHeight()));
    }

    public int getNestedScrollAxes() {
        return this.f1429x.m3217a();
    }

    int getScrollRange() {
        return getChildCount() > 0 ? Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop())) : 0;
    }

    protected float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        return scrollY < verticalFadingEdgeLength ? ((float) scrollY) / ((float) verticalFadingEdgeLength) : 1.0f;
    }

    public boolean hasNestedScrollingParent() {
        return this.f1430y.m3215b();
    }

    public boolean isNestedScrollingEnabled() {
        return this.f1430y.m3209a();
    }

    protected void measureChild(View view, int i, int i2) {
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, 0));
    }

    protected void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, (((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2, marginLayoutParams.width), MeasureSpec.makeMeasureSpec(marginLayoutParams.bottomMargin + marginLayoutParams.topMargin, 0));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f1415h = false;
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 8:
                if (this.f1417j) {
                    return false;
                }
                float a = C0659t.m3204a(motionEvent, 9);
                if (a == BitmapDescriptorFactory.HUE_RED) {
                    return false;
                }
                int verticalScrollFactorCompat = (int) (a * getVerticalScrollFactorCompat());
                int scrollRange = getScrollRange();
                int scrollY = getScrollY();
                verticalScrollFactorCompat = scrollY - verticalScrollFactorCompat;
                if (verticalScrollFactorCompat < 0) {
                    scrollRange = 0;
                } else if (verticalScrollFactorCompat <= scrollRange) {
                    scrollRange = verticalScrollFactorCompat;
                }
                if (scrollRange == scrollY) {
                    return false;
                }
                super.scrollTo(getScrollX(), scrollRange);
                return true;
            default:
                return false;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        int action = motionEvent.getAction();
        if (action == 2 && this.f1417j) {
            return true;
        }
        switch (action & 255) {
            case 0:
                action = (int) motionEvent.getY();
                if (!m3235c((int) motionEvent.getX(), action)) {
                    this.f1417j = false;
                    m3237e();
                    break;
                }
                this.f1413f = action;
                this.f1424q = motionEvent.getPointerId(0);
                m3234c();
                this.f1418k.addMovement(motionEvent);
                this.f1410c.m3554g();
                if (!this.f1410c.m3547a()) {
                    z = true;
                }
                this.f1417j = z;
                startNestedScroll(2);
                break;
            case 1:
            case 3:
                this.f1417j = false;
                this.f1424q = -1;
                m3237e();
                if (this.f1410c.m3548a(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ah.m2799c(this);
                }
                stopNestedScroll();
                break;
            case 2:
                action = this.f1424q;
                if (action != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(action);
                    if (findPointerIndex != -1) {
                        action = (int) motionEvent.getY(findPointerIndex);
                        if (Math.abs(action - this.f1413f) > this.f1421n && (getNestedScrollAxes() & 2) == 0) {
                            this.f1417j = true;
                            this.f1413f = action;
                            m3236d();
                            this.f1418k.addMovement(motionEvent);
                            this.f1427t = 0;
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    }
                    Log.e("NestedScrollView", "Invalid pointerId=" + action + " in onInterceptTouchEvent");
                    break;
                }
                break;
            case 6:
                m3225a(motionEvent);
                break;
        }
        return this.f1417j;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.f1414g = false;
        if (this.f1416i != null && m3230a(this.f1416i, (View) this)) {
            m3232b(this.f1416i);
        }
        this.f1416i = null;
        if (!this.f1415h) {
            if (this.f1428u != null) {
                scrollTo(getScrollX(), this.f1428u.f1404a);
                this.f1428u = null;
            }
            int max = Math.max(0, (getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0) - (((i4 - i2) - getPaddingBottom()) - getPaddingTop()));
            if (getScrollY() > max) {
                scrollTo(getScrollX(), max);
            } else if (getScrollY() < 0) {
                scrollTo(getScrollX(), 0);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.f1415h = true;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.f1419l && MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int measuredHeight = getMeasuredHeight();
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), ((FrameLayout.LayoutParams) childAt.getLayoutParams()).width), MeasureSpec.makeMeasureSpec((measuredHeight - getPaddingTop()) - getPaddingBottom(), 1073741824));
            }
        }
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z) {
            return false;
        }
        m3240f((int) f2);
        return true;
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        dispatchNestedPreScroll(i, i2, iArr, null);
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int scrollY = getScrollY();
        scrollBy(0, i4);
        int scrollY2 = getScrollY() - scrollY;
        dispatchNestedScroll(0, scrollY2, 0, i4 - scrollY2, null);
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.f1429x.m3219a(view, view2, i);
        startNestedScroll(2);
    }

    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        super.scrollTo(i, i2);
    }

    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (i == 2) {
            i = TsExtractor.TS_STREAM_TYPE_HDMV_DTS;
        } else if (i == 1) {
            i = 33;
        }
        View findNextFocus = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, i) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        return (findNextFocus == null || m3228a(findNextFocus)) ? false : findNextFocus.requestFocus(i, rect);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            this.f1428u = savedState;
            requestLayout();
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1404a = getScrollY();
        return savedState;
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.f1407A != null) {
            this.f1407A.mo599a(this, i, i2, i3, i4);
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && m3229a(findFocus, 0, i4)) {
            findFocus.getDrawingRect(this.f1409b);
            offsetDescendantRectToMyCoords(findFocus, this.f1409b);
            m3238e(m3242a(this.f1409b));
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (i & 2) != 0;
    }

    public void onStopNestedScroll(View view) {
        this.f1429x.m3218a(view);
        stopNestedScroll();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        m3236d();
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int a = C0659t.m3205a(motionEvent);
        if (a == 0) {
            this.f1427t = 0;
        }
        obtain.offsetLocation(BitmapDescriptorFactory.HUE_RED, (float) this.f1427t);
        switch (a) {
            case 0:
                if (getChildCount() != 0) {
                    boolean z = !this.f1410c.m3547a();
                    this.f1417j = z;
                    if (z) {
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (!this.f1410c.m3547a()) {
                        this.f1410c.m3555h();
                    }
                    this.f1413f = (int) motionEvent.getY();
                    this.f1424q = motionEvent.getPointerId(0);
                    startNestedScroll(2);
                    break;
                }
                return false;
            case 1:
                if (this.f1417j) {
                    VelocityTracker velocityTracker = this.f1418k;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.f1423p);
                    a = (int) af.m2517b(velocityTracker, this.f1424q);
                    if (Math.abs(a) > this.f1422o) {
                        m3240f(-a);
                    } else if (this.f1410c.m3548a(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                        ah.m2799c(this);
                    }
                }
                this.f1424q = -1;
                m3239f();
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.f1424q);
                if (findPointerIndex != -1) {
                    int i;
                    int y = (int) motionEvent.getY(findPointerIndex);
                    a = this.f1413f - y;
                    if (dispatchNestedPreScroll(0, a, this.f1426s, this.f1425r)) {
                        a -= this.f1426s[1];
                        obtain.offsetLocation(BitmapDescriptorFactory.HUE_RED, (float) this.f1425r[1]);
                        this.f1427t += this.f1425r[1];
                    }
                    if (this.f1417j || Math.abs(a) <= this.f1421n) {
                        i = a;
                    } else {
                        ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.f1417j = true;
                        i = a > 0 ? a - this.f1421n : a + this.f1421n;
                    }
                    if (this.f1417j) {
                        this.f1413f = y - this.f1425r[1];
                        int scrollY = getScrollY();
                        int scrollRange = getScrollRange();
                        a = getOverScrollMode();
                        Object obj = (a == 0 || (a == 1 && scrollRange > 0)) ? 1 : null;
                        if (m3245a(0, i, 0, getScrollY(), 0, scrollRange, 0, 0, true) && !hasNestedScrollingParent()) {
                            this.f1418k.clear();
                        }
                        int scrollY2 = getScrollY() - scrollY;
                        if (!dispatchNestedScroll(0, scrollY2, 0, i - scrollY2, this.f1425r)) {
                            if (obj != null) {
                                m3241g();
                                a = scrollY + i;
                                if (a < 0) {
                                    this.f1411d.m3422a(((float) i) / ((float) getHeight()), motionEvent.getX(findPointerIndex) / ((float) getWidth()));
                                    if (!this.f1412e.m3420a()) {
                                        this.f1412e.m3426c();
                                    }
                                } else if (a > scrollRange) {
                                    this.f1412e.m3422a(((float) i) / ((float) getHeight()), 1.0f - (motionEvent.getX(findPointerIndex) / ((float) getWidth())));
                                    if (!this.f1411d.m3420a()) {
                                        this.f1411d.m3426c();
                                    }
                                }
                                if (!(this.f1411d == null || (this.f1411d.m3420a() && this.f1412e.m3420a()))) {
                                    ah.m2799c(this);
                                    break;
                                }
                            }
                        }
                        this.f1413f -= this.f1425r[1];
                        obtain.offsetLocation(BitmapDescriptorFactory.HUE_RED, (float) this.f1425r[1]);
                        this.f1427t += this.f1425r[1];
                        break;
                    }
                }
                Log.e("NestedScrollView", "Invalid pointerId=" + this.f1424q + " in onTouchEvent");
                break;
                break;
            case 3:
                if (this.f1417j && getChildCount() > 0 && this.f1410c.m3548a(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ah.m2799c(this);
                }
                this.f1424q = -1;
                m3239f();
                break;
            case 5:
                a = C0659t.m3206b(motionEvent);
                this.f1413f = (int) motionEvent.getY(a);
                this.f1424q = motionEvent.getPointerId(a);
                break;
            case 6:
                m3225a(motionEvent);
                this.f1413f = (int) motionEvent.getY(motionEvent.findPointerIndex(this.f1424q));
                break;
        }
        if (this.f1418k != null) {
            this.f1418k.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    public void requestChildFocus(View view, View view2) {
        if (this.f1414g) {
            this.f1416i = view2;
        } else {
            m3232b(view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return m3227a(rect, z);
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            m3237e();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public void requestLayout() {
        this.f1414g = true;
        super.requestLayout();
    }

    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int b = m3231b(i, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            int b2 = m3231b(i2, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            if (b != getScrollX() || b2 != getScrollY()) {
                super.scrollTo(b, b2);
            }
        }
    }

    public void setFillViewport(boolean z) {
        if (z != this.f1419l) {
            this.f1419l = z;
            requestLayout();
        }
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.f1430y.m3208a(z);
    }

    public void setOnScrollChangeListener(C0665b c0665b) {
        this.f1407A = c0665b;
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.f1420m = z;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public boolean startNestedScroll(int i) {
        return this.f1430y.m3212a(i);
    }

    public void stopNestedScroll() {
        this.f1430y.m3216c();
    }
}
