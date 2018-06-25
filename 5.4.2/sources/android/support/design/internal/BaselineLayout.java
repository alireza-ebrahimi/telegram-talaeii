package android.support.design.internal;

import android.content.Context;
import android.support.v4.view.ah;
import android.support.v7.widget.bp;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout extends ViewGroup {
    /* renamed from: a */
    private int f125a = -1;

    public BaselineLayout(Context context) {
        super(context, null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public int getBaseline() {
        return this.f125a;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = ((i3 - i) - getPaddingRight()) - paddingLeft;
        int paddingTop = getPaddingTop();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = paddingLeft + ((paddingRight - measuredWidth) / 2);
                int baseline = (this.f125a == -1 || childAt.getBaseline() == -1) ? paddingTop : (this.f125a + paddingTop) - childAt.getBaseline();
                childAt.layout(i6, baseline, measuredWidth + i6, measuredHeight + baseline);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = -1;
        int i6 = 0;
        int i7 = 0;
        int i8 = -1;
        while (i3 < childCount) {
            int i9;
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 8) {
                i9 = i4;
                i4 = i5;
                i5 = i8;
                i8 = i9;
            } else {
                measureChild(childAt, i, i2);
                int baseline = childAt.getBaseline();
                if (baseline != -1) {
                    i5 = Math.max(i5, baseline);
                    i8 = Math.max(i8, childAt.getMeasuredHeight() - baseline);
                }
                i7 = Math.max(i7, childAt.getMeasuredWidth());
                i6 = Math.max(i6, childAt.getMeasuredHeight());
                i9 = bp.m5745a(i4, ah.m2816i(childAt));
                i4 = i5;
                i5 = i8;
                i8 = i9;
            }
            i3++;
            i9 = i8;
            i8 = i5;
            i5 = i4;
            i4 = i9;
        }
        if (i5 != -1) {
            i6 = Math.max(i6, Math.max(i8, getPaddingBottom()) + i5);
            this.f125a = i5;
        }
        setMeasuredDimension(ah.m2773a(Math.max(i7, getSuggestedMinimumWidth()), i, i4), ah.m2773a(Math.max(i6, getSuggestedMinimumHeight()), i2, i4 << 16));
    }
}
