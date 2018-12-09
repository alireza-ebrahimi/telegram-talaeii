package org.telegram.customization.util.view.slideshow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/* renamed from: org.telegram.customization.util.view.slideshow.c */
class C2957c extends LinearLayout {
    /* renamed from: a */
    private static final int[] f9805a = new int[]{16843049, 16843561, 16843562};
    /* renamed from: b */
    private Drawable f9806b;
    /* renamed from: c */
    private int f9807c;
    /* renamed from: d */
    private int f9808d;
    /* renamed from: e */
    private int f9809e;
    /* renamed from: f */
    private int f9810f;

    public C2957c(Context context, int i) {
        super(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, f9805a, i, 0);
        setDividerDrawable(obtainStyledAttributes.getDrawable(0));
        this.f9810f = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.f9809e = obtainStyledAttributes.getInteger(1, 0);
        obtainStyledAttributes.recycle();
    }

    /* renamed from: a */
    private void m13641a(Canvas canvas) {
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = getChildAt(i);
            if (!(childAt == null || childAt.getVisibility() == 8 || !m13643a(i))) {
                m13642a(canvas, childAt.getTop() - ((LayoutParams) childAt.getLayoutParams()).topMargin);
            }
            i++;
        }
        if (m13643a(childCount)) {
            View childAt2 = getChildAt(childCount - 1);
            m13642a(canvas, childAt2 == null ? (getHeight() - getPaddingBottom()) - this.f9808d : childAt2.getBottom());
        }
    }

    /* renamed from: a */
    private void m13642a(Canvas canvas, int i) {
        this.f9806b.setBounds(getPaddingLeft() + this.f9810f, i, (getWidth() - getPaddingRight()) - this.f9810f, this.f9808d + i);
        this.f9806b.draw(canvas);
    }

    /* renamed from: a */
    private boolean m13643a(int i) {
        if (i == 0 || i == getChildCount() || (this.f9809e & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private void m13644b(Canvas canvas) {
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = getChildAt(i);
            if (!(childAt == null || childAt.getVisibility() == 8 || !m13643a(i))) {
                m13645b(canvas, childAt.getLeft() - ((LayoutParams) childAt.getLayoutParams()).leftMargin);
            }
            i++;
        }
        if (m13643a(childCount)) {
            View childAt2 = getChildAt(childCount - 1);
            m13645b(canvas, childAt2 == null ? (getWidth() - getPaddingRight()) - this.f9807c : childAt2.getRight());
        }
    }

    /* renamed from: b */
    private void m13645b(Canvas canvas, int i) {
        this.f9806b.setBounds(i, getPaddingTop() + this.f9810f, this.f9807c + i, (getHeight() - getPaddingBottom()) - this.f9810f);
        this.f9806b.draw(canvas);
    }

    protected void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        int indexOfChild = indexOfChild(view);
        int orientation = getOrientation();
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (m13643a(indexOfChild)) {
            if (orientation == 1) {
                layoutParams.topMargin = this.f9808d;
            } else {
                layoutParams.leftMargin = this.f9807c;
            }
        }
        int childCount = getChildCount();
        if (indexOfChild == childCount - 1 && m13643a(childCount)) {
            if (orientation == 1) {
                layoutParams.bottomMargin = this.f9808d;
            } else {
                layoutParams.rightMargin = this.f9807c;
            }
        }
        super.measureChildWithMargins(view, i, i2, i3, i4);
    }

    protected void onDraw(Canvas canvas) {
        if (this.f9806b != null) {
            if (getOrientation() == 1) {
                m13641a(canvas);
            } else {
                m13644b(canvas);
            }
        }
        super.onDraw(canvas);
    }

    public void setDividerDrawable(Drawable drawable) {
        boolean z = false;
        if (drawable != this.f9806b) {
            this.f9806b = drawable;
            if (drawable != null) {
                this.f9807c = drawable.getIntrinsicWidth();
                this.f9808d = drawable.getIntrinsicHeight();
            } else {
                this.f9807c = 0;
                this.f9808d = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }
}
