package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout.C0104d;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.support.v4.view.be;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.List;

/* renamed from: android.support.design.widget.m */
abstract class C0090m extends ab<View> {
    /* renamed from: a */
    final Rect f238a = new Rect();
    /* renamed from: b */
    final Rect f239b = new Rect();
    /* renamed from: c */
    private int f240c = 0;
    /* renamed from: d */
    private int f241d;

    public C0090m(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* renamed from: c */
    private static int m401c(int i) {
        return i == 0 ? 8388659 : i;
    }

    /* renamed from: a */
    float mo82a(View view) {
        return 1.0f;
    }

    /* renamed from: a */
    final int m403a() {
        return this.f240c;
    }

    /* renamed from: a */
    public boolean mo74a(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
        int i5 = view.getLayoutParams().height;
        if (i5 == -1 || i5 == -2) {
            View b = mo85b(coordinatorLayout.m551c(view));
            if (b != null) {
                if (ah.m2835y(b) && !ah.m2835y(view)) {
                    ah.m2789a(view, true);
                    if (ah.m2835y(view)) {
                        view.requestLayout();
                        return true;
                    }
                }
                int size = MeasureSpec.getSize(i3);
                if (size == 0) {
                    size = coordinatorLayout.getHeight();
                }
                coordinatorLayout.m542a(view, i, i2, MeasureSpec.makeMeasureSpec(mo84b(b) + (size - b.getMeasuredHeight()), i5 == -1 ? 1073741824 : Integer.MIN_VALUE), i4);
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    int mo84b(View view) {
        return view.getMeasuredHeight();
    }

    /* renamed from: b */
    abstract View mo85b(List<View> list);

    /* renamed from: b */
    public final void m407b(int i) {
        this.f241d = i;
    }

    /* renamed from: b */
    protected void mo81b(CoordinatorLayout coordinatorLayout, View view, int i) {
        View b = mo85b(coordinatorLayout.m551c(view));
        if (b != null) {
            C0104d c0104d = (C0104d) view.getLayoutParams();
            Rect rect = this.f238a;
            rect.set(coordinatorLayout.getPaddingLeft() + c0104d.leftMargin, b.getBottom() + c0104d.topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - c0104d.rightMargin, ((coordinatorLayout.getHeight() + b.getBottom()) - coordinatorLayout.getPaddingBottom()) - c0104d.bottomMargin);
            be lastWindowInsets = coordinatorLayout.getLastWindowInsets();
            if (!(lastWindowInsets == null || !ah.m2835y(coordinatorLayout) || ah.m2835y(view))) {
                rect.left += lastWindowInsets.m3076a();
                rect.right -= lastWindowInsets.m3079c();
            }
            Rect rect2 = this.f239b;
            C0625f.m3121a(C0090m.m401c(c0104d.f294c), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i);
            int c = m409c(b);
            view.layout(rect2.left, rect2.top - c, rect2.right, rect2.bottom - c);
            this.f240c = rect2.top - b.getBottom();
            return;
        }
        super.mo81b(coordinatorLayout, view, i);
        this.f240c = 0;
    }

    /* renamed from: c */
    final int m409c(View view) {
        return this.f241d == 0 ? 0 : C0168n.m809a((int) (mo82a(view) * ((float) this.f241d)), 0, this.f241d);
    }

    /* renamed from: d */
    public final int m410d() {
        return this.f241d;
    }
}
