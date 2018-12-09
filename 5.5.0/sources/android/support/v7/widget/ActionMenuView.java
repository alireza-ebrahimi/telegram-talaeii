package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.C0079p;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.view.menu.C0873h.C0857b;
import android.support.v7.view.menu.C0876j;
import android.support.v7.widget.ao.C0899a;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ActionMenuView extends ao implements C0857b, C0079p {
    /* renamed from: a */
    C0777a f2346a;
    /* renamed from: b */
    C0902e f2347b;
    /* renamed from: c */
    private C0873h f2348c;
    /* renamed from: d */
    private Context f2349d;
    /* renamed from: e */
    private int f2350e;
    /* renamed from: f */
    private boolean f2351f;
    /* renamed from: g */
    private C1052d f2352g;
    /* renamed from: h */
    private C0794a f2353h;
    /* renamed from: i */
    private boolean f2354i;
    /* renamed from: j */
    private int f2355j;
    /* renamed from: k */
    private int f2356k;
    /* renamed from: l */
    private int f2357l;

    /* renamed from: android.support.v7.widget.ActionMenuView$a */
    public interface C0856a {
        /* renamed from: c */
        boolean mo705c();

        /* renamed from: d */
        boolean mo706d();
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$b */
    private class C0898b implements C0794a {
        /* renamed from: a */
        final /* synthetic */ ActionMenuView f2336a;

        C0898b(ActionMenuView actionMenuView) {
            this.f2336a = actionMenuView;
        }

        /* renamed from: a */
        public void mo657a(C0873h c0873h, boolean z) {
        }

        /* renamed from: a */
        public boolean mo658a(C0873h c0873h) {
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$c */
    public static class C0900c extends C0899a {
        @ExportedProperty
        /* renamed from: a */
        public boolean f2339a;
        @ExportedProperty
        /* renamed from: b */
        public int f2340b;
        @ExportedProperty
        /* renamed from: c */
        public int f2341c;
        @ExportedProperty
        /* renamed from: d */
        public boolean f2342d;
        @ExportedProperty
        /* renamed from: e */
        public boolean f2343e;
        /* renamed from: f */
        boolean f2344f;

        public C0900c(int i, int i2) {
            super(i, i2);
            this.f2339a = false;
        }

        public C0900c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public C0900c(C0900c c0900c) {
            super(c0900c);
            this.f2339a = c0900c.f2339a;
        }

        public C0900c(LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$d */
    private class C0901d implements C0777a {
        /* renamed from: a */
        final /* synthetic */ ActionMenuView f2345a;

        C0901d(ActionMenuView actionMenuView) {
            this.f2345a = actionMenuView;
        }

        /* renamed from: a */
        public void mo634a(C0873h c0873h) {
            if (this.f2345a.f2346a != null) {
                this.f2345a.f2346a.mo634a(c0873h);
            }
        }

        /* renamed from: a */
        public boolean mo638a(C0873h c0873h, MenuItem menuItem) {
            return this.f2345a.f2347b != null && this.f2345a.f2347b.mo883a(menuItem);
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$e */
    public interface C0902e {
        /* renamed from: a */
        boolean mo883a(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.f2356k = (int) (56.0f * f);
        this.f2357l = (int) (f * 4.0f);
        this.f2349d = context;
        this.f2350e = 0;
    }

    /* renamed from: a */
    static int m4407a(View view, int i, int i2, int i3, int i4) {
        int i5;
        boolean z = false;
        C0900c c0900c = (C0900c) view.getLayoutParams();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i3) - i4, MeasureSpec.getMode(i3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean z2 = actionMenuItemView != null && actionMenuItemView.m4092b();
        if (i2 <= 0 || (z2 && i2 < 2)) {
            i5 = 0;
        } else {
            view.measure(MeasureSpec.makeMeasureSpec(i * i2, Integer.MIN_VALUE), makeMeasureSpec);
            int measuredWidth = view.getMeasuredWidth();
            i5 = measuredWidth / i;
            if (measuredWidth % i != 0) {
                i5++;
            }
            if (z2 && r1 < 2) {
                i5 = 2;
            }
        }
        if (!c0900c.f2339a && z2) {
            z = true;
        }
        c0900c.f2342d = z;
        c0900c.f2340b = i5;
        view.measure(MeasureSpec.makeMeasureSpec(i5 * i, 1073741824), makeMeasureSpec);
        return i5;
    }

    /* renamed from: c */
    private void m4408c(int i, int i2) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
        int i3 = size - paddingLeft;
        int i4 = i3 / this.f2356k;
        size = i3 % this.f2356k;
        if (i4 == 0) {
            setMeasuredDimension(i3, 0);
            return;
        }
        Object obj;
        int i5 = this.f2356k + (size / i4);
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        paddingLeft = 0;
        Object obj2 = null;
        long j = 0;
        int childCount = getChildCount();
        int i9 = 0;
        while (i9 < childCount) {
            int i10;
            long j2;
            int i11;
            int i12;
            int i13;
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() == 8) {
                i10 = paddingLeft;
                j2 = j;
                i11 = i6;
                i12 = i4;
                i4 = i7;
            } else {
                boolean z = childAt instanceof ActionMenuItemView;
                i13 = paddingLeft + 1;
                if (z) {
                    childAt.setPadding(this.f2357l, 0, this.f2357l, 0);
                }
                C0900c c0900c = (C0900c) childAt.getLayoutParams();
                c0900c.f2344f = false;
                c0900c.f2341c = 0;
                c0900c.f2340b = 0;
                c0900c.f2342d = false;
                c0900c.leftMargin = 0;
                c0900c.rightMargin = 0;
                boolean z2 = z && ((ActionMenuItemView) childAt).m4092b();
                c0900c.f2343e = z2;
                int a = m4407a(childAt, i5, c0900c.f2339a ? 1 : i4, childMeasureSpec, paddingTop);
                i7 = Math.max(i7, a);
                paddingLeft = c0900c.f2342d ? i8 + 1 : i8;
                obj = c0900c.f2339a ? 1 : obj2;
                int i14 = i4 - a;
                i8 = Math.max(i6, childAt.getMeasuredHeight());
                if (a == 1) {
                    long j3 = ((long) (1 << i9)) | j;
                    i11 = i8;
                    i12 = i14;
                    i8 = paddingLeft;
                    obj2 = obj;
                    j2 = j3;
                    i4 = i7;
                    i10 = i13;
                } else {
                    i10 = i13;
                    i4 = i7;
                    long j4 = j;
                    i11 = i8;
                    i12 = i14;
                    obj2 = obj;
                    i8 = paddingLeft;
                    j2 = j4;
                }
            }
            i9++;
            i7 = i4;
            i6 = i11;
            i4 = i12;
            j = j2;
            paddingLeft = i10;
        }
        Object obj3 = (obj2 == null || paddingLeft != 2) ? null : 1;
        Object obj4 = null;
        long j5 = j;
        paddingTop = i4;
        while (i8 > 0 && paddingTop > 0) {
            i13 = Integer.MAX_VALUE;
            j = 0;
            i4 = 0;
            int i15 = 0;
            while (i15 < childCount) {
                c0900c = (C0900c) getChildAt(i15).getLayoutParams();
                if (!c0900c.f2342d) {
                    size = i4;
                    i4 = i13;
                } else if (c0900c.f2340b < i13) {
                    i4 = c0900c.f2340b;
                    j = (long) (1 << i15);
                    size = 1;
                } else if (c0900c.f2340b == i13) {
                    j |= (long) (1 << i15);
                    size = i4 + 1;
                    i4 = i13;
                } else {
                    size = i4;
                    i4 = i13;
                }
                i15++;
                i13 = i4;
                i4 = size;
            }
            j5 |= j;
            if (i4 > paddingTop) {
                j = j5;
                break;
            }
            i15 = i13 + 1;
            i13 = 0;
            i4 = paddingTop;
            long j6 = j5;
            while (i13 < childCount) {
                View childAt2 = getChildAt(i13);
                c0900c = (C0900c) childAt2.getLayoutParams();
                if ((((long) (1 << i13)) & j) != 0) {
                    if (obj3 != null && c0900c.f2343e && i4 == 1) {
                        childAt2.setPadding(this.f2357l + i5, 0, this.f2357l, 0);
                    }
                    c0900c.f2340b++;
                    c0900c.f2344f = true;
                    size = i4 - 1;
                } else if (c0900c.f2340b == i15) {
                    j6 |= (long) (1 << i13);
                    size = i4;
                } else {
                    size = i4;
                }
                i13++;
                i4 = size;
            }
            j5 = j6;
            i9 = 1;
            paddingTop = i4;
        }
        j = j5;
        obj = (obj2 == null && paddingLeft == 1) ? 1 : null;
        if (paddingTop <= 0 || j == 0 || (paddingTop >= paddingLeft - 1 && obj == null && i7 <= 1)) {
            obj3 = obj4;
        } else {
            float f;
            View childAt3;
            float bitCount = (float) Long.bitCount(j);
            if (obj == null) {
                if (!((1 & j) == 0 || ((C0900c) getChildAt(0).getLayoutParams()).f2343e)) {
                    bitCount -= 0.5f;
                }
                if (!((((long) (1 << (childCount - 1))) & j) == 0 || ((C0900c) getChildAt(childCount - 1).getLayoutParams()).f2343e)) {
                    f = bitCount - 0.5f;
                    paddingLeft = f <= BitmapDescriptorFactory.HUE_RED ? (int) (((float) (paddingTop * i5)) / f) : 0;
                    i4 = 0;
                    obj3 = obj4;
                    while (i4 < childCount) {
                        if ((((long) (1 << i4)) & j) != 0) {
                            obj = obj3;
                        } else {
                            childAt3 = getChildAt(i4);
                            c0900c = (C0900c) childAt3.getLayoutParams();
                            if (childAt3 instanceof ActionMenuItemView) {
                                c0900c.f2341c = paddingLeft;
                                c0900c.f2344f = true;
                                if (i4 == 0 && !c0900c.f2343e) {
                                    c0900c.leftMargin = (-paddingLeft) / 2;
                                }
                                obj = 1;
                            } else if (c0900c.f2339a) {
                                if (i4 != 0) {
                                    c0900c.leftMargin = paddingLeft / 2;
                                }
                                if (i4 != childCount - 1) {
                                    c0900c.rightMargin = paddingLeft / 2;
                                }
                                obj = obj3;
                            } else {
                                c0900c.f2341c = paddingLeft;
                                c0900c.f2344f = true;
                                c0900c.rightMargin = (-paddingLeft) / 2;
                                obj = 1;
                            }
                        }
                        i4++;
                        obj3 = obj;
                    }
                }
            }
            f = bitCount;
            if (f <= BitmapDescriptorFactory.HUE_RED) {
            }
            i4 = 0;
            obj3 = obj4;
            while (i4 < childCount) {
                if ((((long) (1 << i4)) & j) != 0) {
                    childAt3 = getChildAt(i4);
                    c0900c = (C0900c) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        c0900c.f2341c = paddingLeft;
                        c0900c.f2344f = true;
                        c0900c.leftMargin = (-paddingLeft) / 2;
                        obj = 1;
                    } else if (c0900c.f2339a) {
                        if (i4 != 0) {
                            c0900c.leftMargin = paddingLeft / 2;
                        }
                        if (i4 != childCount - 1) {
                            c0900c.rightMargin = paddingLeft / 2;
                        }
                        obj = obj3;
                    } else {
                        c0900c.f2341c = paddingLeft;
                        c0900c.f2344f = true;
                        c0900c.rightMargin = (-paddingLeft) / 2;
                        obj = 1;
                    }
                } else {
                    obj = obj3;
                }
                i4++;
                obj3 = obj;
            }
        }
        if (obj3 != null) {
            for (paddingLeft = 0; paddingLeft < childCount; paddingLeft++) {
                childAt = getChildAt(paddingLeft);
                c0900c = (C0900c) childAt.getLayoutParams();
                if (c0900c.f2344f) {
                    childAt.measure(MeasureSpec.makeMeasureSpec(c0900c.f2341c + (c0900c.f2340b * i5), 1073741824), childMeasureSpec);
                }
            }
        }
        if (mode == 1073741824) {
            i6 = size2;
        }
        setMeasuredDimension(i3, i6);
    }

    /* renamed from: a */
    public C0900c m4409a(AttributeSet attributeSet) {
        return new C0900c(getContext(), attributeSet);
    }

    /* renamed from: a */
    protected C0900c m4410a(LayoutParams layoutParams) {
        if (layoutParams == null) {
            return m4416b();
        }
        C0900c c0900c = layoutParams instanceof C0900c ? new C0900c((C0900c) layoutParams) : new C0900c(layoutParams);
        if (c0900c.h > 0) {
            return c0900c;
        }
        c0900c.h = 16;
        return c0900c;
    }

    /* renamed from: a */
    public void mo54a(C0873h c0873h) {
        this.f2348c = c0873h;
    }

    /* renamed from: a */
    public void m4412a(C0794a c0794a, C0777a c0777a) {
        this.f2353h = c0794a;
        this.f2346a = c0777a;
    }

    /* renamed from: a */
    public boolean m4413a() {
        return this.f2351f;
    }

    /* renamed from: a */
    protected boolean m4414a(int i) {
        boolean z = false;
        if (i == 0) {
            return false;
        }
        View childAt = getChildAt(i - 1);
        View childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof C0856a)) {
            z = 0 | ((C0856a) childAt).mo706d();
        }
        return (i <= 0 || !(childAt2 instanceof C0856a)) ? z : ((C0856a) childAt2).mo705c() | z;
    }

    /* renamed from: a */
    public boolean mo707a(C0876j c0876j) {
        return this.f2348c.m4232a((MenuItem) c0876j, 0);
    }

    /* renamed from: b */
    protected C0900c m4416b() {
        C0900c c0900c = new C0900c(-2, -2);
        c0900c.h = 16;
        return c0900c;
    }

    /* renamed from: b */
    public /* synthetic */ C0899a mo784b(AttributeSet attributeSet) {
        return m4409a(attributeSet);
    }

    /* renamed from: b */
    protected /* synthetic */ C0899a mo785b(LayoutParams layoutParams) {
        return m4410a(layoutParams);
    }

    /* renamed from: c */
    public C0900c m4419c() {
        C0900c b = m4416b();
        b.f2339a = true;
        return b;
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof C0900c);
    }

    /* renamed from: d */
    public C0873h m4420d() {
        return this.f2348c;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    /* renamed from: e */
    public boolean m4421e() {
        return this.f2352g != null && this.f2352g.m5783d();
    }

    /* renamed from: f */
    public boolean m4422f() {
        return this.f2352g != null && this.f2352g.m5784e();
    }

    /* renamed from: g */
    public boolean m4423g() {
        return this.f2352g != null && this.f2352g.m5787h();
    }

    protected /* synthetic */ LayoutParams generateDefaultLayoutParams() {
        return m4416b();
    }

    public /* synthetic */ LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return m4409a(attributeSet);
    }

    protected /* synthetic */ LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return m4410a(layoutParams);
    }

    public Menu getMenu() {
        if (this.f2348c == null) {
            Context context = getContext();
            this.f2348c = new C0873h(context);
            this.f2348c.mo757a(new C0901d(this));
            this.f2352g = new C1052d(context);
            this.f2352g.m5781c(true);
            this.f2352g.mo721a(this.f2353h != null ? this.f2353h : new C0898b(this));
            this.f2348c.m4227a(this.f2352g, this.f2349d);
            this.f2352g.m5773a(this);
        }
        return this.f2348c;
    }

    public Drawable getOverflowIcon() {
        getMenu();
        return this.f2352g.m5780c();
    }

    public int getPopupTheme() {
        return this.f2350e;
    }

    public int getWindowAnimations() {
        return 0;
    }

    /* renamed from: h */
    public boolean m4424h() {
        return this.f2352g != null && this.f2352g.m5788i();
    }

    /* renamed from: i */
    public void m4425i() {
        if (this.f2352g != null) {
            this.f2352g.m5785f();
        }
    }

    /* renamed from: j */
    protected /* synthetic */ C0899a mo790j() {
        return m4416b();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.f2352g != null) {
            this.f2352g.mo724b(false);
            if (this.f2352g.m5787h()) {
                this.f2352g.m5784e();
                this.f2352g.m5783d();
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        m4425i();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.f2354i) {
            int i5;
            int i6;
            C0900c c0900c;
            int paddingLeft;
            int childCount = getChildCount();
            int i7 = (i4 - i2) / 2;
            int dividerWidth = getDividerWidth();
            int i8 = 0;
            int i9 = 0;
            int paddingRight = ((i3 - i) - getPaddingRight()) - getPaddingLeft();
            Object obj = null;
            boolean a = bp.m5747a(this);
            int i10 = 0;
            while (i10 < childCount) {
                Object obj2;
                View childAt = getChildAt(i10);
                if (childAt.getVisibility() == 8) {
                    obj2 = obj;
                    i5 = i9;
                    i6 = paddingRight;
                    paddingRight = i8;
                } else {
                    c0900c = (C0900c) childAt.getLayoutParams();
                    if (c0900c.f2339a) {
                        i6 = childAt.getMeasuredWidth();
                        if (m4414a(i10)) {
                            i6 += dividerWidth;
                        }
                        int measuredHeight = childAt.getMeasuredHeight();
                        if (a) {
                            paddingLeft = c0900c.leftMargin + getPaddingLeft();
                            i5 = paddingLeft + i6;
                        } else {
                            i5 = (getWidth() - getPaddingRight()) - c0900c.rightMargin;
                            paddingLeft = i5 - i6;
                        }
                        int i11 = i7 - (measuredHeight / 2);
                        childAt.layout(paddingLeft, i11, i5, measuredHeight + i11);
                        i6 = paddingRight - i6;
                        obj2 = 1;
                        i5 = i9;
                        paddingRight = i8;
                    } else {
                        i5 = (childAt.getMeasuredWidth() + c0900c.leftMargin) + c0900c.rightMargin;
                        paddingLeft = i8 + i5;
                        i5 = paddingRight - i5;
                        if (m4414a(i10)) {
                            paddingLeft += dividerWidth;
                        }
                        Object obj3 = obj;
                        i6 = i5;
                        i5 = i9 + 1;
                        paddingRight = paddingLeft;
                        obj2 = obj3;
                    }
                }
                i10++;
                i8 = paddingRight;
                paddingRight = i6;
                i9 = i5;
                obj = obj2;
            }
            if (childCount == 1 && obj == null) {
                View childAt2 = getChildAt(0);
                i6 = childAt2.getMeasuredWidth();
                i5 = childAt2.getMeasuredHeight();
                paddingRight = ((i3 - i) / 2) - (i6 / 2);
                i9 = i7 - (i5 / 2);
                childAt2.layout(paddingRight, i9, i6 + paddingRight, i5 + i9);
                return;
            }
            paddingLeft = i9 - (obj != null ? 0 : 1);
            paddingRight = Math.max(0, paddingLeft > 0 ? paddingRight / paddingLeft : 0);
            View childAt3;
            if (a) {
                i6 = getWidth() - getPaddingRight();
                i5 = 0;
                while (i5 < childCount) {
                    childAt3 = getChildAt(i5);
                    c0900c = (C0900c) childAt3.getLayoutParams();
                    if (childAt3.getVisibility() == 8) {
                        paddingLeft = i6;
                    } else if (c0900c.f2339a) {
                        paddingLeft = i6;
                    } else {
                        i6 -= c0900c.rightMargin;
                        i8 = childAt3.getMeasuredWidth();
                        i10 = childAt3.getMeasuredHeight();
                        dividerWidth = i7 - (i10 / 2);
                        childAt3.layout(i6 - i8, dividerWidth, i6, i10 + dividerWidth);
                        paddingLeft = i6 - ((c0900c.leftMargin + i8) + paddingRight);
                    }
                    i5++;
                    i6 = paddingLeft;
                }
                return;
            }
            i6 = getPaddingLeft();
            i5 = 0;
            while (i5 < childCount) {
                childAt3 = getChildAt(i5);
                c0900c = (C0900c) childAt3.getLayoutParams();
                if (childAt3.getVisibility() == 8) {
                    paddingLeft = i6;
                } else if (c0900c.f2339a) {
                    paddingLeft = i6;
                } else {
                    i6 += c0900c.leftMargin;
                    i8 = childAt3.getMeasuredWidth();
                    i10 = childAt3.getMeasuredHeight();
                    dividerWidth = i7 - (i10 / 2);
                    childAt3.layout(i6, dividerWidth, i6 + i8, i10 + dividerWidth);
                    paddingLeft = ((c0900c.rightMargin + i8) + paddingRight) + i6;
                }
                i5++;
                i6 = paddingLeft;
            }
            return;
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    protected void onMeasure(int i, int i2) {
        boolean z = this.f2354i;
        this.f2354i = MeasureSpec.getMode(i) == 1073741824;
        if (z != this.f2354i) {
            this.f2355j = 0;
        }
        int size = MeasureSpec.getSize(i);
        if (!(!this.f2354i || this.f2348c == null || size == this.f2355j)) {
            this.f2355j = size;
            this.f2348c.m4238b(true);
        }
        int childCount = getChildCount();
        if (!this.f2354i || childCount <= 0) {
            for (int i3 = 0; i3 < childCount; i3++) {
                C0900c c0900c = (C0900c) getChildAt(i3).getLayoutParams();
                c0900c.rightMargin = 0;
                c0900c.leftMargin = 0;
            }
            super.onMeasure(i, i2);
            return;
        }
        m4408c(i, i2);
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.f2352g.m5782d(z);
    }

    public void setOnMenuItemClickListener(C0902e c0902e) {
        this.f2347b = c0902e;
    }

    public void setOverflowIcon(Drawable drawable) {
        getMenu();
        this.f2352g.m5770a(drawable);
    }

    public void setOverflowReserved(boolean z) {
        this.f2351f = z;
    }

    public void setPopupTheme(int i) {
        if (this.f2350e != i) {
            this.f2350e = i;
            if (i == 0) {
                this.f2349d = getContext();
            } else {
                this.f2349d = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void setPresenter(C1052d c1052d) {
        this.f2352g = c1052d;
        this.f2352g.m5773a(this);
    }
}
