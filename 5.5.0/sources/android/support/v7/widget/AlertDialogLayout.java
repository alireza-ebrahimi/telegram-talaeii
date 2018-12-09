package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.widget.ao.C0899a;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class AlertDialogLayout extends ao {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* renamed from: a */
    private void m4441a(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i + i3, i2 + i4);
    }

    /* renamed from: c */
    private static int m4442c(View view) {
        int p = ah.m2826p(view);
        if (p > 0) {
            return p;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return m4442c(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    /* renamed from: c */
    private boolean m4443c(int i, int i2) {
        int id;
        int measuredHeight;
        int i3;
        int i4;
        int a;
        int i5;
        View view = null;
        View view2 = null;
        int childCount = getChildCount();
        int i6 = 0;
        View view3 = null;
        while (i6 < childCount) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() == 8) {
                childAt = view2;
                view2 = view;
            } else {
                id = childAt.getId();
                if (id == C0743f.topPanel) {
                    View view4 = view2;
                    view2 = childAt;
                    childAt = view4;
                } else if (id == C0743f.buttonPanel) {
                    view2 = view;
                } else if (id != C0743f.contentPanel && id != C0743f.customPanel) {
                    return false;
                } else {
                    if (view3 != null) {
                        return false;
                    }
                    view3 = childAt;
                    childAt = view2;
                    view2 = view;
                }
            }
            i6++;
            view = view2;
            view2 = childAt;
        }
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i);
        id = 0;
        i6 = getPaddingBottom() + getPaddingTop();
        if (view != null) {
            view.measure(i, 0);
            i6 += view.getMeasuredHeight();
            id = ah.m2772a(0, ah.m2816i(view));
        }
        int i7 = 0;
        if (view2 != null) {
            view2.measure(i, 0);
            i7 = m4442c(view2);
            measuredHeight = view2.getMeasuredHeight() - i7;
            i6 += i7;
            id = ah.m2772a(id, ah.m2816i(view2));
            i3 = measuredHeight;
        } else {
            i3 = 0;
        }
        if (view3 != null) {
            view3.measure(i, mode == 0 ? 0 : MeasureSpec.makeMeasureSpec(Math.max(0, size - i6), mode));
            measuredHeight = view3.getMeasuredHeight();
            i6 += measuredHeight;
            id = ah.m2772a(id, ah.m2816i(view3));
            i4 = measuredHeight;
        } else {
            i4 = 0;
        }
        measuredHeight = size - i6;
        if (view2 != null) {
            i6 -= i7;
            i3 = Math.min(measuredHeight, i3);
            if (i3 > 0) {
                measuredHeight -= i3;
                i7 += i3;
            }
            view2.measure(i, MeasureSpec.makeMeasureSpec(i7, 1073741824));
            i7 = view2.getMeasuredHeight() + i6;
            a = ah.m2772a(id, ah.m2816i(view2));
            int i8 = measuredHeight;
            measuredHeight = i7;
            i7 = i8;
        } else {
            i7 = measuredHeight;
            a = id;
            measuredHeight = i6;
        }
        if (view3 == null || i7 <= 0) {
            i5 = measuredHeight;
            measuredHeight = a;
        } else {
            measuredHeight -= i4;
            i6 = i7 - i7;
            view3.measure(i, MeasureSpec.makeMeasureSpec(i7 + i4, mode));
            i8 = measuredHeight + view3.getMeasuredHeight();
            measuredHeight = ah.m2772a(a, ah.m2816i(view3));
            i5 = i8;
        }
        a = 0;
        for (i7 = 0; i7 < childCount; i7++) {
            View childAt2 = getChildAt(i7);
            if (childAt2.getVisibility() != 8) {
                a = Math.max(a, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(ah.m2773a(a + (getPaddingLeft() + getPaddingRight()), i, measuredHeight), ah.m2773a(i5, i2, 0));
        if (mode2 != 1073741824) {
            m4444d(childCount, i2);
        }
        return true;
    }

    /* renamed from: d */
    private void m4444d(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                C0899a c0899a = (C0899a) childAt.getLayoutParams();
                if (c0899a.width == -1) {
                    int i4 = c0899a.height;
                    c0899a.height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, i2, 0);
                    c0899a.height = i4;
                }
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int i5 = i3 - i;
        int paddingRight = i5 - getPaddingRight();
        int paddingRight2 = (i5 - paddingLeft) - getPaddingRight();
        i5 = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i6 = gravity & 8388615;
        switch (gravity & 112) {
            case 16:
                i5 = (((i4 - i2) - i5) / 2) + getPaddingTop();
                break;
            case 80:
                i5 = ((getPaddingTop() + i4) - i2) - i5;
                break;
            default:
                i5 = getPaddingTop();
                break;
        }
        Drawable dividerDrawable = getDividerDrawable();
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        int i7 = i5;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                int i9;
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                C0899a c0899a = (C0899a) childAt.getLayoutParams();
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
                i7 = c0899a.topMargin + (m178c(i8) ? i7 + intrinsicHeight : i7);
                m4441a(childAt, i9, i7, measuredWidth, measuredHeight);
                i7 += c0899a.bottomMargin + measuredHeight;
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        if (!m4443c(i, i2)) {
            super.onMeasure(i, i2);
        }
    }
}
