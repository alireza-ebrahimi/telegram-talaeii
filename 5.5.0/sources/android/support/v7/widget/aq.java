package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v7.p015d.p016a.C0169a;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

public class aq extends ListView {
    /* renamed from: g */
    private static final int[] f2839g = new int[]{0};
    /* renamed from: a */
    final Rect f2840a = new Rect();
    /* renamed from: b */
    int f2841b = 0;
    /* renamed from: c */
    int f2842c = 0;
    /* renamed from: d */
    int f2843d = 0;
    /* renamed from: e */
    int f2844e = 0;
    /* renamed from: f */
    protected int f2845f;
    /* renamed from: h */
    private Field f2846h;
    /* renamed from: i */
    private C1024a f2847i;

    /* renamed from: android.support.v7.widget.aq$a */
    private static class C1024a extends C0169a {
        /* renamed from: a */
        private boolean f2923a = true;

        public C1024a(Drawable drawable) {
            super(drawable);
        }

        /* renamed from: a */
        void m5499a(boolean z) {
            this.f2923a = z;
        }

        public void draw(Canvas canvas) {
            if (this.f2923a) {
                super.draw(canvas);
            }
        }

        public void setHotspot(float f, float f2) {
            if (this.f2923a) {
                super.setHotspot(f, f2);
            }
        }

        public void setHotspotBounds(int i, int i2, int i3, int i4) {
            if (this.f2923a) {
                super.setHotspotBounds(i, i2, i3, i4);
            }
        }

        public boolean setState(int[] iArr) {
            return this.f2923a ? super.setState(iArr) : false;
        }

        public boolean setVisible(boolean z, boolean z2) {
            return this.f2923a ? super.setVisible(z, z2) : false;
        }
    }

    public aq(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        try {
            this.f2846h = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.f2846h.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public int m5434a(int i, int i2, int i3, int i4, int i5) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        getListPaddingLeft();
        getListPaddingRight();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        listPaddingBottom += listPaddingTop;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int i6 = 0;
        View view = null;
        int i7 = 0;
        int count = adapter.getCount();
        int i8 = 0;
        while (i8 < count) {
            View view2;
            listPaddingTop = adapter.getItemViewType(i8);
            if (listPaddingTop != i7) {
                int i9 = listPaddingTop;
                view2 = null;
                i7 = i9;
            } else {
                view2 = view;
            }
            view = adapter.getView(i8, view2, this);
            LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            view.measure(i, layoutParams.height > 0 ? MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824) : MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            listPaddingTop = (i8 > 0 ? listPaddingBottom + dividerHeight : listPaddingBottom) + view.getMeasuredHeight();
            if (listPaddingTop >= i4) {
                return (i5 < 0 || i8 <= i5 || i6 <= 0 || listPaddingTop == i4) ? i4 : i6;
            } else {
                if (i5 >= 0 && i8 >= i5) {
                    i6 = listPaddingTop;
                }
                i8++;
                listPaddingBottom = listPaddingTop;
            }
        }
        return listPaddingBottom;
    }

    /* renamed from: a */
    protected void m5435a(int i, View view) {
        boolean z = true;
        Drawable selector = getSelector();
        boolean z2 = (selector == null || i == -1) ? false : true;
        if (z2) {
            selector.setVisible(false, false);
        }
        m5440b(i, view);
        if (z2) {
            Rect rect = this.f2840a;
            float exactCenterX = rect.exactCenterX();
            float exactCenterY = rect.exactCenterY();
            if (getVisibility() != 0) {
                z = false;
            }
            selector.setVisible(z, false);
            C0375a.m1770a(selector, exactCenterX, exactCenterY);
        }
    }

    /* renamed from: a */
    protected void m5436a(int i, View view, float f, float f2) {
        m5435a(i, view);
        Drawable selector = getSelector();
        if (selector != null && i != -1) {
            C0375a.m1770a(selector, f, f2);
        }
    }

    /* renamed from: a */
    protected void m5437a(Canvas canvas) {
        if (!this.f2840a.isEmpty()) {
            Drawable selector = getSelector();
            if (selector != null) {
                selector.setBounds(this.f2840a);
                selector.draw(canvas);
            }
        }
    }

    /* renamed from: a */
    protected boolean mo931a() {
        return false;
    }

    /* renamed from: b */
    protected void m5439b() {
        Drawable selector = getSelector();
        if (selector != null && m5441c()) {
            selector.setState(getDrawableState());
        }
    }

    /* renamed from: b */
    protected void m5440b(int i, View view) {
        Rect rect = this.f2840a;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        rect.left -= this.f2841b;
        rect.top -= this.f2842c;
        rect.right += this.f2843d;
        rect.bottom += this.f2844e;
        try {
            boolean z = this.f2846h.getBoolean(this);
            if (view.isEnabled() != z) {
                this.f2846h.set(this, Boolean.valueOf(!z));
                if (i != -1) {
                    refreshDrawableState();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: c */
    protected boolean m5441c() {
        return mo931a() && isPressed();
    }

    protected void dispatchDraw(Canvas canvas) {
        m5437a(canvas);
        super.dispatchDraw(canvas);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        setSelectorEnabled(true);
        m5439b();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.f2845f = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setSelector(Drawable drawable) {
        this.f2847i = drawable != null ? new C1024a(drawable) : null;
        super.setSelector(this.f2847i);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.f2841b = rect.left;
        this.f2842c = rect.top;
        this.f2843d = rect.right;
        this.f2844e = rect.bottom;
    }

    protected void setSelectorEnabled(boolean z) {
        if (this.f2847i != null) {
            this.f2847i.m5499a(z);
        }
    }
}
