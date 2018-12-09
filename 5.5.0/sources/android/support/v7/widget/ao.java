package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.ui.ActionBar.Theme;

public class ao extends ViewGroup {
    /* renamed from: a */
    private boolean f127a;
    /* renamed from: b */
    private int f128b;
    /* renamed from: c */
    private int f129c;
    /* renamed from: d */
    private int f130d;
    /* renamed from: e */
    private int f131e;
    /* renamed from: f */
    private int f132f;
    /* renamed from: g */
    private float f133g;
    /* renamed from: h */
    private boolean f134h;
    /* renamed from: i */
    private int[] f135i;
    /* renamed from: j */
    private int[] f136j;
    /* renamed from: k */
    private Drawable f137k;
    /* renamed from: l */
    private int f138l;
    /* renamed from: m */
    private int f139m;
    /* renamed from: n */
    private int f140n;
    /* renamed from: o */
    private int f141o;

    /* renamed from: android.support.v7.widget.ao$a */
    public static class C0899a extends MarginLayoutParams {
        /* renamed from: g */
        public float f2337g;
        /* renamed from: h */
        public int f2338h;

        public C0899a(int i, int i2) {
            super(i, i2);
            this.f2338h = -1;
            this.f2337g = BitmapDescriptorFactory.HUE_RED;
        }

        public C0899a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f2338h = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.LinearLayoutCompat_Layout);
            this.f2337g = obtainStyledAttributes.getFloat(C0747j.LinearLayoutCompat_Layout_android_layout_weight, BitmapDescriptorFactory.HUE_RED);
            this.f2338h = obtainStyledAttributes.getInt(C0747j.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            obtainStyledAttributes.recycle();
        }

        public C0899a(LayoutParams layoutParams) {
            super(layoutParams);
            this.f2338h = -1;
        }
    }

    public ao(Context context) {
        this(context, null);
    }

    public ao(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ao(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f127a = true;
        this.f128b = -1;
        this.f129c = 0;
        this.f131e = 8388659;
        bk a = bk.m5654a(context, attributeSet, C0747j.LinearLayoutCompat, i, 0);
        int a2 = a.m5656a(C0747j.LinearLayoutCompat_android_orientation, -1);
        if (a2 >= 0) {
            setOrientation(a2);
        }
        a2 = a.m5656a(C0747j.LinearLayoutCompat_android_gravity, -1);
        if (a2 >= 0) {
            setGravity(a2);
        }
        boolean a3 = a.m5659a(C0747j.LinearLayoutCompat_android_baselineAligned, true);
        if (!a3) {
            setBaselineAligned(a3);
        }
        this.f133g = a.m5655a(C0747j.LinearLayoutCompat_android_weightSum, -1.0f);
        this.f128b = a.m5656a(C0747j.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.f134h = a.m5659a(C0747j.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.m5657a(C0747j.LinearLayoutCompat_divider));
        this.f140n = a.m5656a(C0747j.LinearLayoutCompat_showDividers, 0);
        this.f141o = a.m5666e(C0747j.LinearLayoutCompat_dividerPadding, 0);
        a.m5658a();
    }

    /* renamed from: a */
    private void m160a(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i + i3, i2 + i4);
    }

    /* renamed from: c */
    private void m161c(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View b = m173b(i3);
            if (b.getVisibility() != 8) {
                C0899a c0899a = (C0899a) b.getLayoutParams();
                if (c0899a.width == -1) {
                    int i4 = c0899a.height;
                    c0899a.height = b.getMeasuredHeight();
                    measureChildWithMargins(b, makeMeasureSpec, 0, i2, 0);
                    c0899a.height = i4;
                }
            }
        }
    }

    /* renamed from: d */
    private void m162d(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View b = m173b(i3);
            if (b.getVisibility() != 8) {
                C0899a c0899a = (C0899a) b.getLayoutParams();
                if (c0899a.height == -1) {
                    int i4 = c0899a.width;
                    c0899a.width = b.getMeasuredWidth();
                    measureChildWithMargins(b, i2, 0, makeMeasureSpec, 0);
                    c0899a.width = i4;
                }
            }
        }
    }

    /* renamed from: a */
    int m163a(View view) {
        return 0;
    }

    /* renamed from: a */
    int m164a(View view, int i) {
        return 0;
    }

    /* renamed from: a */
    void m165a(int i, int i2) {
        int i3;
        int i4;
        int i5;
        View b;
        this.f132f = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        Object obj = 1;
        float f = BitmapDescriptorFactory.HUE_RED;
        int virtualChildCount = getVirtualChildCount();
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        Object obj2 = null;
        Object obj3 = null;
        int i10 = this.f128b;
        boolean z = this.f134h;
        int i11 = Integer.MIN_VALUE;
        int i12 = 0;
        while (i12 < virtualChildCount) {
            Object obj4;
            Object obj5;
            int i13;
            View b2 = m173b(i12);
            if (b2 == null) {
                this.f132f += m179d(i12);
                i3 = i11;
                obj4 = obj3;
                obj5 = obj;
                i4 = i7;
                i13 = i6;
            } else if (b2.getVisibility() == 8) {
                i12 += m164a(b2, i12);
                i3 = i11;
                obj4 = obj3;
                obj5 = obj;
                i4 = i7;
                i13 = i6;
            } else {
                if (m178c(i12)) {
                    this.f132f += this.f139m;
                }
                C0899a c0899a = (C0899a) b2.getLayoutParams();
                float f2 = f + c0899a.f2337g;
                if (mode2 == 1073741824 && c0899a.height == 0 && c0899a.f2337g > BitmapDescriptorFactory.HUE_RED) {
                    i3 = this.f132f;
                    this.f132f = Math.max(i3, (c0899a.topMargin + i3) + c0899a.bottomMargin);
                    obj3 = 1;
                } else {
                    i3 = Integer.MIN_VALUE;
                    if (c0899a.height == 0 && c0899a.f2337g > BitmapDescriptorFactory.HUE_RED) {
                        i3 = 0;
                        c0899a.height = -2;
                    }
                    int i14 = i3;
                    m169a(b2, i12, i, 0, i2, f2 == BitmapDescriptorFactory.HUE_RED ? this.f132f : 0);
                    if (i14 != Integer.MIN_VALUE) {
                        c0899a.height = i14;
                    }
                    i3 = b2.getMeasuredHeight();
                    int i15 = this.f132f;
                    this.f132f = Math.max(i15, (((i15 + i3) + c0899a.topMargin) + c0899a.bottomMargin) + m170b(b2));
                    if (z) {
                        i11 = Math.max(i3, i11);
                    }
                }
                if (i10 >= 0 && i10 == i12 + 1) {
                    this.f129c = this.f132f;
                }
                if (i12 >= i10 || c0899a.f2337g <= BitmapDescriptorFactory.HUE_RED) {
                    Object obj6;
                    Object obj7 = null;
                    if (mode == 1073741824 || c0899a.width != -1) {
                        obj6 = obj2;
                    } else {
                        obj6 = 1;
                        obj7 = 1;
                    }
                    i4 = c0899a.rightMargin + c0899a.leftMargin;
                    i13 = b2.getMeasuredWidth() + i4;
                    i6 = Math.max(i6, i13);
                    int a = bp.m5745a(i7, ah.m2816i(b2));
                    obj5 = (obj == null || c0899a.width != -1) ? null : 1;
                    if (c0899a.f2337g > BitmapDescriptorFactory.HUE_RED) {
                        i3 = Math.max(i9, obj7 != null ? i4 : i13);
                        i4 = i8;
                    } else {
                        if (obj7 == null) {
                            i4 = i13;
                        }
                        i4 = Math.max(i8, i4);
                        i3 = i9;
                    }
                    i12 += m164a(b2, i12);
                    obj4 = obj3;
                    i9 = i3;
                    i8 = i4;
                    i13 = i6;
                    i3 = i11;
                    i4 = a;
                    obj2 = obj6;
                    f = f2;
                } else {
                    throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                }
            }
            i12++;
            i11 = i3;
            obj3 = obj4;
            obj = obj5;
            i7 = i4;
            i6 = i13;
        }
        if (this.f132f > 0 && m178c(virtualChildCount)) {
            this.f132f += this.f139m;
        }
        if (z && (mode2 == Integer.MIN_VALUE || mode2 == 0)) {
            this.f132f = 0;
            i5 = 0;
            while (i5 < virtualChildCount) {
                b = m173b(i5);
                if (b == null) {
                    this.f132f += m179d(i5);
                    i3 = i5;
                } else if (b.getVisibility() == 8) {
                    i3 = m164a(b, i5) + i5;
                } else {
                    C0899a c0899a2 = (C0899a) b.getLayoutParams();
                    int i16 = this.f132f;
                    this.f132f = Math.max(i16, (c0899a2.bottomMargin + ((i16 + i11) + c0899a2.topMargin)) + m170b(b));
                    i3 = i5;
                }
                i5 = i3 + 1;
            }
        }
        this.f132f += getPaddingTop() + getPaddingBottom();
        int a2 = ah.m2773a(Math.max(this.f132f, getSuggestedMinimumHeight()), i2, 0);
        i5 = (16777215 & a2) - this.f132f;
        int i17;
        if (obj3 != null || (i5 != 0 && f > BitmapDescriptorFactory.HUE_RED)) {
            if (this.f133g > BitmapDescriptorFactory.HUE_RED) {
                f = this.f133g;
            }
            this.f132f = 0;
            i11 = 0;
            float f3 = f;
            Object obj8 = obj;
            i17 = i8;
            i16 = i7;
            i9 = i6;
            i15 = i5;
            while (i11 < virtualChildCount) {
                View b3 = m173b(i11);
                if (b3.getVisibility() == 8) {
                    i3 = i17;
                    i5 = i16;
                    i4 = i9;
                    obj5 = obj8;
                } else {
                    float f4;
                    float f5;
                    c0899a2 = (C0899a) b3.getLayoutParams();
                    float f6 = c0899a2.f2337g;
                    if (f6 > BitmapDescriptorFactory.HUE_RED) {
                        i5 = (int) ((((float) i15) * f6) / f3);
                        f3 -= f6;
                        i15 -= i5;
                        i4 = getChildMeasureSpec(i, ((getPaddingLeft() + getPaddingRight()) + c0899a2.leftMargin) + c0899a2.rightMargin, c0899a2.width);
                        if (c0899a2.height == 0 && mode2 == 1073741824) {
                            if (i5 <= 0) {
                                i5 = 0;
                            }
                            b3.measure(i4, MeasureSpec.makeMeasureSpec(i5, 1073741824));
                        } else {
                            i5 += b3.getMeasuredHeight();
                            if (i5 < 0) {
                                i5 = 0;
                            }
                            b3.measure(i4, MeasureSpec.makeMeasureSpec(i5, 1073741824));
                        }
                        f4 = f3;
                        i12 = i15;
                        i15 = bp.m5745a(i16, ah.m2816i(b3) & -256);
                        f5 = f4;
                    } else {
                        f5 = f3;
                        i12 = i15;
                        i15 = i16;
                    }
                    i16 = c0899a2.leftMargin + c0899a2.rightMargin;
                    i4 = b3.getMeasuredWidth() + i16;
                    i9 = Math.max(i9, i4);
                    Object obj9 = (mode == 1073741824 || c0899a2.width != -1) ? null : 1;
                    if (obj9 == null) {
                        i16 = i4;
                    }
                    i4 = Math.max(i17, i16);
                    obj5 = (obj8 == null || c0899a2.width != -1) ? null : 1;
                    i13 = this.f132f;
                    this.f132f = Math.max(i13, (c0899a2.bottomMargin + ((b3.getMeasuredHeight() + i13) + c0899a2.topMargin)) + m170b(b3));
                    i3 = i4;
                    i4 = i9;
                    f4 = f5;
                    i5 = i15;
                    i15 = i12;
                    f3 = f4;
                }
                i11++;
                i17 = i3;
                i9 = i4;
                obj8 = obj5;
                i16 = i5;
            }
            this.f132f += getPaddingTop() + getPaddingBottom();
            obj = obj8;
            i3 = i17;
            i7 = i16;
            i5 = i9;
        } else {
            i17 = Math.max(i8, i9);
            if (z && mode2 != 1073741824) {
                for (i5 = 0; i5 < virtualChildCount; i5++) {
                    b = m173b(i5);
                    if (!(b == null || b.getVisibility() == 8 || ((C0899a) b.getLayoutParams()).f2337g <= BitmapDescriptorFactory.HUE_RED)) {
                        b.measure(MeasureSpec.makeMeasureSpec(b.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(i11, 1073741824));
                    }
                }
            }
            i3 = i17;
            i5 = i6;
        }
        if (obj != null || mode == 1073741824) {
            i3 = i5;
        }
        setMeasuredDimension(ah.m2773a(Math.max(i3 + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), i, i7), a2);
        if (obj2 != null) {
            m161c(virtualChildCount, i2);
        }
    }

    /* renamed from: a */
    void m166a(int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int i5 = i3 - i;
        int paddingRight = i5 - getPaddingRight();
        int paddingRight2 = (i5 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i6 = this.f131e & 8388615;
        switch (this.f131e & 112) {
            case 16:
                i5 = getPaddingTop() + (((i4 - i2) - this.f132f) / 2);
                break;
            case 80:
                i5 = ((getPaddingTop() + i4) - i2) - this.f132f;
                break;
            default:
                i5 = getPaddingTop();
                break;
        }
        int i7 = 0;
        int i8 = i5;
        while (i7 < virtualChildCount) {
            View b = m173b(i7);
            if (b == null) {
                i8 += m179d(i7);
                i5 = i7;
            } else if (b.getVisibility() != 8) {
                int i9;
                int measuredWidth = b.getMeasuredWidth();
                int measuredHeight = b.getMeasuredHeight();
                C0899a c0899a = (C0899a) b.getLayoutParams();
                i5 = c0899a.f2338h;
                if (i5 < 0) {
                    i5 = i6;
                }
                switch (C0625f.m3120a(i5, ah.m2812g(this)) & 7) {
                    case 1:
                        i9 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + c0899a.leftMargin) - c0899a.rightMargin;
                        break;
                    case 5:
                        i9 = (paddingRight - measuredWidth) - c0899a.rightMargin;
                        break;
                    default:
                        i9 = paddingLeft + c0899a.leftMargin;
                        break;
                }
                int i10 = (m178c(i7) ? this.f139m + i8 : i8) + c0899a.topMargin;
                m160a(b, i9, i10 + m163a(b), measuredWidth, measuredHeight);
                i8 = i10 + ((c0899a.bottomMargin + measuredHeight) + m170b(b));
                i5 = m164a(b, i7) + i7;
            } else {
                i5 = i7;
            }
            i7 = i5 + 1;
        }
    }

    /* renamed from: a */
    void m167a(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        int i = 0;
        while (i < virtualChildCount) {
            View b = m173b(i);
            if (!(b == null || b.getVisibility() == 8 || !m178c(i))) {
                m168a(canvas, (b.getTop() - ((C0899a) b.getLayoutParams()).topMargin) - this.f139m);
            }
            i++;
        }
        if (m178c(virtualChildCount)) {
            int height;
            View b2 = m173b(virtualChildCount - 1);
            if (b2 == null) {
                height = (getHeight() - getPaddingBottom()) - this.f139m;
            } else {
                C0899a c0899a = (C0899a) b2.getLayoutParams();
                height = c0899a.bottomMargin + b2.getBottom();
            }
            m168a(canvas, height);
        }
    }

    /* renamed from: a */
    void m168a(Canvas canvas, int i) {
        this.f137k.setBounds(getPaddingLeft() + this.f141o, i, (getWidth() - getPaddingRight()) - this.f141o, this.f139m + i);
        this.f137k.draw(canvas);
    }

    /* renamed from: a */
    void m169a(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    /* renamed from: b */
    int m170b(View view) {
        return 0;
    }

    /* renamed from: b */
    public C0899a mo784b(AttributeSet attributeSet) {
        return new C0899a(getContext(), attributeSet);
    }

    /* renamed from: b */
    protected C0899a mo785b(LayoutParams layoutParams) {
        return new C0899a(layoutParams);
    }

    /* renamed from: b */
    View m173b(int i) {
        return getChildAt(i);
    }

    /* renamed from: b */
    void m174b(int i, int i2) {
        int i3;
        int i4;
        int i5;
        C0899a c0899a;
        this.f132f = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        Object obj = 1;
        float f = BitmapDescriptorFactory.HUE_RED;
        int virtualChildCount = getVirtualChildCount();
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        Object obj2 = null;
        Object obj3 = null;
        if (this.f135i == null || this.f136j == null) {
            this.f135i = new int[4];
            this.f136j = new int[4];
        }
        int[] iArr = this.f135i;
        int[] iArr2 = this.f136j;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z = this.f127a;
        boolean z2 = this.f134h;
        Object obj4 = mode == 1073741824 ? 1 : null;
        int i10 = Integer.MIN_VALUE;
        int i11 = 0;
        while (i11 < virtualChildCount) {
            Object obj5;
            Object obj6;
            int i12;
            View b = m173b(i11);
            if (b == null) {
                this.f132f += m179d(i11);
                i3 = i10;
                obj5 = obj3;
                obj6 = obj;
                i4 = i7;
                i12 = i6;
            } else if (b.getVisibility() == 8) {
                i11 += m164a(b, i11);
                i3 = i10;
                obj5 = obj3;
                obj6 = obj;
                i4 = i7;
                i12 = i6;
            } else {
                Object obj7;
                if (m178c(i11)) {
                    this.f132f += this.f138l;
                }
                C0899a c0899a2 = (C0899a) b.getLayoutParams();
                float f2 = f + c0899a2.f2337g;
                if (mode == 1073741824 && c0899a2.width == 0 && c0899a2.f2337g > BitmapDescriptorFactory.HUE_RED) {
                    if (obj4 != null) {
                        this.f132f += c0899a2.leftMargin + c0899a2.rightMargin;
                    } else {
                        i3 = this.f132f;
                        this.f132f = Math.max(i3, (c0899a2.leftMargin + i3) + c0899a2.rightMargin);
                    }
                    if (z) {
                        i3 = MeasureSpec.makeMeasureSpec(0, 0);
                        b.measure(i3, i3);
                    } else {
                        obj3 = 1;
                    }
                } else {
                    i3 = Integer.MIN_VALUE;
                    if (c0899a2.width == 0 && c0899a2.f2337g > BitmapDescriptorFactory.HUE_RED) {
                        i3 = 0;
                        c0899a2.width = -2;
                    }
                    int i13 = i3;
                    m169a(b, i11, i, f2 == BitmapDescriptorFactory.HUE_RED ? this.f132f : 0, i2, 0);
                    if (i13 != Integer.MIN_VALUE) {
                        c0899a2.width = i13;
                    }
                    i3 = b.getMeasuredWidth();
                    if (obj4 != null) {
                        this.f132f += ((c0899a2.leftMargin + i3) + c0899a2.rightMargin) + m170b(b);
                    } else {
                        int i14 = this.f132f;
                        this.f132f = Math.max(i14, (((i14 + i3) + c0899a2.leftMargin) + c0899a2.rightMargin) + m170b(b));
                    }
                    if (z2) {
                        i10 = Math.max(i3, i10);
                    }
                }
                Object obj8 = null;
                if (mode2 == 1073741824 || c0899a2.height != -1) {
                    obj7 = obj2;
                } else {
                    obj7 = 1;
                    obj8 = 1;
                }
                i4 = c0899a2.bottomMargin + c0899a2.topMargin;
                i12 = b.getMeasuredHeight() + i4;
                int a = bp.m5745a(i7, ah.m2816i(b));
                if (z) {
                    i7 = b.getBaseline();
                    if (i7 != -1) {
                        int i15 = ((((c0899a2.f2338h < 0 ? this.f131e : c0899a2.f2338h) & 112) >> 4) & -2) >> 1;
                        iArr[i15] = Math.max(iArr[i15], i7);
                        iArr2[i15] = Math.max(iArr2[i15], i12 - i7);
                    }
                }
                i7 = Math.max(i6, i12);
                obj6 = (obj == null || c0899a2.height != -1) ? null : 1;
                if (c0899a2.f2337g > BitmapDescriptorFactory.HUE_RED) {
                    i3 = Math.max(i9, obj8 != null ? i4 : i12);
                    i4 = i8;
                } else {
                    if (obj8 == null) {
                        i4 = i12;
                    }
                    i4 = Math.max(i8, i4);
                    i3 = i9;
                }
                i11 += m164a(b, i11);
                obj5 = obj3;
                i9 = i3;
                i8 = i4;
                i12 = i7;
                i3 = i10;
                i4 = a;
                obj2 = obj7;
                f = f2;
            }
            i11++;
            i10 = i3;
            obj3 = obj5;
            obj = obj6;
            i7 = i4;
            i6 = i12;
        }
        if (this.f132f > 0 && m178c(virtualChildCount)) {
            this.f132f += this.f138l;
        }
        i11 = (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1 && iArr[3] == -1) ? i6 : Math.max(i6, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
        if (z2 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.f132f = 0;
            i5 = 0;
            while (i5 < virtualChildCount) {
                View b2 = m173b(i5);
                if (b2 == null) {
                    this.f132f += m179d(i5);
                    i3 = i5;
                } else if (b2.getVisibility() == 8) {
                    i3 = m164a(b2, i5) + i5;
                } else {
                    c0899a = (C0899a) b2.getLayoutParams();
                    if (obj4 != null) {
                        this.f132f = ((c0899a.rightMargin + (c0899a.leftMargin + i10)) + m170b(b2)) + this.f132f;
                        i3 = i5;
                    } else {
                        i4 = this.f132f;
                        this.f132f = Math.max(i4, (c0899a.rightMargin + ((i4 + i10) + c0899a.leftMargin)) + m170b(b2));
                        i3 = i5;
                    }
                }
                i5 = i3 + 1;
            }
        }
        this.f132f += getPaddingLeft() + getPaddingRight();
        int a2 = ah.m2773a(Math.max(this.f132f, getSuggestedMinimumWidth()), i, 0);
        i5 = (16777215 & a2) - this.f132f;
        int i16;
        if (obj3 != null || (i5 != 0 && f > BitmapDescriptorFactory.HUE_RED)) {
            if (this.f133g > BitmapDescriptorFactory.HUE_RED) {
                f = this.f133g;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            this.f132f = 0;
            i10 = 0;
            float f3 = f;
            Object obj9 = obj;
            i16 = i8;
            i15 = i7;
            i14 = i5;
            i8 = -1;
            while (i10 < virtualChildCount) {
                float f4;
                Object obj10;
                View b3 = m173b(i10);
                if (b3 == null) {
                    f4 = f3;
                    i5 = i14;
                    i4 = i8;
                    i14 = i16;
                    obj10 = obj9;
                } else if (b3.getVisibility() == 8) {
                    f4 = f3;
                    i5 = i14;
                    i4 = i8;
                    i14 = i16;
                    obj10 = obj9;
                } else {
                    float f5;
                    c0899a = (C0899a) b3.getLayoutParams();
                    float f6 = c0899a.f2337g;
                    if (f6 > BitmapDescriptorFactory.HUE_RED) {
                        i5 = (int) ((((float) i14) * f6) / f3);
                        f3 -= f6;
                        i4 = i14 - i5;
                        i14 = getChildMeasureSpec(i2, ((getPaddingTop() + getPaddingBottom()) + c0899a.topMargin) + c0899a.bottomMargin, c0899a.height);
                        if (c0899a.width == 0 && mode == 1073741824) {
                            if (i5 <= 0) {
                                i5 = 0;
                            }
                            b3.measure(MeasureSpec.makeMeasureSpec(i5, 1073741824), i14);
                        } else {
                            i5 += b3.getMeasuredWidth();
                            if (i5 < 0) {
                                i5 = 0;
                            }
                            b3.measure(MeasureSpec.makeMeasureSpec(i5, 1073741824), i14);
                        }
                        i9 = bp.m5745a(i15, ah.m2816i(b3) & Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
                        f5 = f3;
                    } else {
                        i4 = i14;
                        i9 = i15;
                        f5 = f3;
                    }
                    if (obj4 != null) {
                        this.f132f += ((b3.getMeasuredWidth() + c0899a.leftMargin) + c0899a.rightMargin) + m170b(b3);
                    } else {
                        i5 = this.f132f;
                        this.f132f = Math.max(i5, (((b3.getMeasuredWidth() + i5) + c0899a.leftMargin) + c0899a.rightMargin) + m170b(b3));
                    }
                    obj5 = (mode2 == 1073741824 || c0899a.height != -1) ? null : 1;
                    i11 = c0899a.topMargin + c0899a.bottomMargin;
                    i14 = b3.getMeasuredHeight() + i11;
                    i8 = Math.max(i8, i14);
                    i11 = Math.max(i16, obj5 != null ? i11 : i14);
                    obj5 = (obj9 == null || c0899a.height != -1) ? null : 1;
                    if (z) {
                        i12 = b3.getBaseline();
                        if (i12 != -1) {
                            i3 = ((((c0899a.f2338h < 0 ? this.f131e : c0899a.f2338h) & 112) >> 4) & -2) >> 1;
                            iArr[i3] = Math.max(iArr[i3], i12);
                            iArr2[i3] = Math.max(iArr2[i3], i14 - i12);
                        }
                    }
                    f4 = f5;
                    i14 = i11;
                    obj10 = obj5;
                    i15 = i9;
                    i5 = i4;
                    i4 = i8;
                }
                i10++;
                i16 = i14;
                i8 = i4;
                obj9 = obj10;
                i14 = i5;
                f3 = f4;
            }
            this.f132f += getPaddingLeft() + getPaddingRight();
            if (!(iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1 && iArr[3] == -1)) {
                i8 = Math.max(i8, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
            }
            obj = obj9;
            i3 = i16;
            i7 = i15;
            i5 = i8;
        } else {
            i16 = Math.max(i8, i9);
            if (z2 && mode != 1073741824) {
                for (i5 = 0; i5 < virtualChildCount; i5++) {
                    View b4 = m173b(i5);
                    if (!(b4 == null || b4.getVisibility() == 8 || ((C0899a) b4.getLayoutParams()).f2337g <= BitmapDescriptorFactory.HUE_RED)) {
                        b4.measure(MeasureSpec.makeMeasureSpec(i10, 1073741824), MeasureSpec.makeMeasureSpec(b4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i3 = i16;
            i5 = i11;
        }
        if (obj != null || mode2 == 1073741824) {
            i3 = i5;
        }
        setMeasuredDimension((Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i7) | a2, ah.m2773a(Math.max(i3 + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), i2, i7 << 16));
        if (obj2 != null) {
            m162d(virtualChildCount, i);
        }
    }

    /* renamed from: b */
    void m175b(int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        boolean a = bp.m5747a(this);
        int paddingTop = getPaddingTop();
        int i7 = i4 - i2;
        int paddingBottom = i7 - getPaddingBottom();
        int paddingBottom2 = (i7 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        i7 = this.f131e & 8388615;
        int i8 = this.f131e & 112;
        boolean z = this.f127a;
        int[] iArr = this.f135i;
        int[] iArr2 = this.f136j;
        switch (C0625f.m3120a(i7, ah.m2812g(this))) {
            case 1:
                paddingLeft = getPaddingLeft() + (((i3 - i) - this.f132f) / 2);
                break;
            case 5:
                paddingLeft = ((getPaddingLeft() + i3) - i) - this.f132f;
                break;
            default:
                paddingLeft = getPaddingLeft();
                break;
        }
        if (a) {
            i5 = -1;
            i6 = virtualChildCount - 1;
        } else {
            i5 = 1;
            i6 = 0;
        }
        int i9 = 0;
        while (i9 < virtualChildCount) {
            int i10 = i6 + (i5 * i9);
            View b = m173b(i10);
            if (b == null) {
                paddingLeft += m179d(i10);
                i7 = i9;
            } else if (b.getVisibility() != 8) {
                int i11;
                int measuredWidth = b.getMeasuredWidth();
                int measuredHeight = b.getMeasuredHeight();
                C0899a c0899a = (C0899a) b.getLayoutParams();
                i7 = (!z || c0899a.height == -1) ? -1 : b.getBaseline();
                int i12 = c0899a.f2338h;
                if (i12 < 0) {
                    i12 = i8;
                }
                switch (i12 & 112) {
                    case 16:
                        i11 = ((((paddingBottom2 - measuredHeight) / 2) + paddingTop) + c0899a.topMargin) - c0899a.bottomMargin;
                        break;
                    case 48:
                        i11 = paddingTop + c0899a.topMargin;
                        if (i7 != -1) {
                            i11 += iArr[1] - i7;
                            break;
                        }
                        break;
                    case 80:
                        i11 = (paddingBottom - measuredHeight) - c0899a.bottomMargin;
                        if (i7 != -1) {
                            i11 -= iArr2[2] - (b.getMeasuredHeight() - i7);
                            break;
                        }
                        break;
                    default:
                        i11 = paddingTop;
                        break;
                }
                paddingLeft = (m178c(i10) ? this.f138l + paddingLeft : paddingLeft) + c0899a.leftMargin;
                m160a(b, paddingLeft + m163a(b), i11, measuredWidth, measuredHeight);
                paddingLeft += (c0899a.rightMargin + measuredWidth) + m170b(b);
                i7 = m164a(b, i10) + i9;
            } else {
                i7 = i9;
            }
            i9 = i7 + 1;
        }
    }

    /* renamed from: b */
    void m176b(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        boolean a = bp.m5747a(this);
        int i = 0;
        while (i < virtualChildCount) {
            View b = m173b(i);
            if (!(b == null || b.getVisibility() == 8 || !m178c(i))) {
                C0899a c0899a = (C0899a) b.getLayoutParams();
                m177b(canvas, a ? c0899a.rightMargin + b.getRight() : (b.getLeft() - c0899a.leftMargin) - this.f138l);
            }
            i++;
        }
        if (m178c(virtualChildCount)) {
            int paddingLeft;
            View b2 = m173b(virtualChildCount - 1);
            if (b2 == null) {
                paddingLeft = a ? getPaddingLeft() : (getWidth() - getPaddingRight()) - this.f138l;
            } else {
                c0899a = (C0899a) b2.getLayoutParams();
                paddingLeft = a ? (b2.getLeft() - c0899a.leftMargin) - this.f138l : c0899a.rightMargin + b2.getRight();
            }
            m177b(canvas, paddingLeft);
        }
    }

    /* renamed from: b */
    void m177b(Canvas canvas, int i) {
        this.f137k.setBounds(i, getPaddingTop() + this.f141o, this.f138l + i, (getHeight() - getPaddingBottom()) - this.f141o);
        this.f137k.draw(canvas);
    }

    /* renamed from: c */
    protected boolean m178c(int i) {
        if (i == 0) {
            return (this.f140n & 1) != 0;
        } else {
            if (i == getChildCount()) {
                return (this.f140n & 4) != 0;
            } else {
                if ((this.f140n & 2) == 0) {
                    return false;
                }
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    if (getChildAt(i2).getVisibility() != 8) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return layoutParams instanceof C0899a;
    }

    /* renamed from: d */
    int m179d(int i) {
        return 0;
    }

    protected /* synthetic */ LayoutParams generateDefaultLayoutParams() {
        return mo790j();
    }

    public /* synthetic */ LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return mo784b(attributeSet);
    }

    protected /* synthetic */ LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return mo785b(layoutParams);
    }

    public int getBaseline() {
        if (this.f128b < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.f128b) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(this.f128b);
        int baseline = childAt.getBaseline();
        if (baseline != -1) {
            int i;
            int i2 = this.f129c;
            if (this.f130d == 1) {
                i = this.f131e & 112;
                if (i != 48) {
                    switch (i) {
                        case 16:
                            i = i2 + (((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f132f) / 2);
                            break;
                        case 80:
                            i = ((getBottom() - getTop()) - getPaddingBottom()) - this.f132f;
                            break;
                    }
                }
            }
            i = i2;
            return (((C0899a) childAt.getLayoutParams()).topMargin + i) + baseline;
        } else if (this.f128b == 0) {
            return -1;
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.f128b;
    }

    public Drawable getDividerDrawable() {
        return this.f137k;
    }

    public int getDividerPadding() {
        return this.f141o;
    }

    public int getDividerWidth() {
        return this.f138l;
    }

    public int getGravity() {
        return this.f131e;
    }

    public int getOrientation() {
        return this.f130d;
    }

    public int getShowDividers() {
        return this.f140n;
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.f133g;
    }

    /* renamed from: j */
    protected C0899a mo790j() {
        return this.f130d == 0 ? new C0899a(-2, -2) : this.f130d == 1 ? new C0899a(-1, -2) : null;
    }

    protected void onDraw(Canvas canvas) {
        if (this.f137k != null) {
            if (this.f130d == 1) {
                m167a(canvas);
            } else {
                m176b(canvas);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(ao.class.getName());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(ao.class.getName());
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.f130d == 1) {
            m166a(i, i2, i3, i4);
        } else {
            m175b(i, i2, i3, i4);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.f130d == 1) {
            m165a(i, i2);
        } else {
            m174b(i, i2);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.f127a = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.f128b = i;
    }

    public void setDividerDrawable(Drawable drawable) {
        boolean z = false;
        if (drawable != this.f137k) {
            this.f137k = drawable;
            if (drawable != null) {
                this.f138l = drawable.getIntrinsicWidth();
                this.f139m = drawable.getIntrinsicHeight();
            } else {
                this.f138l = 0;
                this.f139m = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int i) {
        this.f141o = i;
    }

    public void setGravity(int i) {
        if (this.f131e != i) {
            int i2 = (8388615 & i) == 0 ? 8388611 | i : i;
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.f131e = i2;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & 8388615;
        if ((this.f131e & 8388615) != i2) {
            this.f131e = i2 | (this.f131e & -8388616);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.f134h = z;
    }

    public void setOrientation(int i) {
        if (this.f130d != i) {
            this.f130d = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.f140n) {
            requestLayout();
        }
        this.f140n = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        if ((this.f131e & 112) != i2) {
            this.f131e = i2 | (this.f131e & -113);
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.f133g = Math.max(BitmapDescriptorFactory.HUE_RED, f);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
