package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.content.p020a.C0402a;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.telegram.ui.ActionBar.Theme;

public class ButtonBarLayout extends LinearLayout {
    /* renamed from: a */
    private boolean f2386a;
    /* renamed from: b */
    private int f2387b = -1;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        boolean z = C0402a.m1860a(getResources()) >= 320;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.ButtonBarLayout);
        this.f2386a = obtainStyledAttributes.getBoolean(C0747j.ButtonBarLayout_allowStacking, z);
        obtainStyledAttributes.recycle();
    }

    /* renamed from: a */
    private int m4445a(int i) {
        int childCount = getChildCount();
        for (int i2 = i; i2 < childCount; i2++) {
            if (getChildAt(i2).getVisibility() == 0) {
                return i2;
            }
        }
        return -1;
    }

    /* renamed from: a */
    private boolean m4446a() {
        return getOrientation() == 1;
    }

    private void setStacked(boolean z) {
        setOrientation(z ? 1 : 0);
        setGravity(z ? 5 : 80);
        View findViewById = findViewById(C0743f.spacer);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 8 : 4);
        }
        for (int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
            bringChildToFront(getChildAt(childCount));
        }
    }

    protected void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int size = MeasureSpec.getSize(i);
        if (this.f2386a) {
            if (size > this.f2387b && m4446a()) {
                setStacked(false);
            }
            this.f2387b = size;
        }
        if (m4446a() || MeasureSpec.getMode(i) != 1073741824) {
            i3 = i;
            z = false;
        } else {
            i3 = MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            z = true;
        }
        super.onMeasure(i3, i2);
        if (this.f2386a && !m4446a()) {
            boolean z2;
            if (VERSION.SDK_INT >= 11) {
                z2 = (ah.m2814h(this) & Theme.ACTION_BAR_VIDEO_EDIT_COLOR) == 16777216;
            } else {
                int i4 = 0;
                for (i3 = 0; i3 < getChildCount(); i3++) {
                    i4 += getChildAt(i3).getMeasuredWidth();
                }
                z2 = (getPaddingLeft() + i4) + getPaddingRight() > size;
            }
            if (z2) {
                setStacked(true);
                z = true;
            }
        }
        if (z) {
            super.onMeasure(i, i2);
        }
        int a = m4445a(0);
        if (a >= 0) {
            View childAt = getChildAt(a);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            i3 = (layoutParams.bottomMargin + ((childAt.getMeasuredHeight() + getPaddingTop()) + layoutParams.topMargin)) + 0;
            if (m4446a()) {
                a = m4445a(a + 1);
                if (a >= 0) {
                    i3 = (int) (((float) i3) + (((float) getChildAt(a).getPaddingTop()) + (16.0f * getResources().getDisplayMetrics().density)));
                }
            } else {
                i3 += getPaddingBottom();
            }
        } else {
            i3 = 0;
        }
        if (ah.m2826p(this) != i3) {
            setMinimumHeight(i3);
        }
    }

    public void setAllowStacking(boolean z) {
        if (this.f2386a != z) {
            this.f2386a = z;
            if (!this.f2386a && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }
}
