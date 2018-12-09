package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.design.C0073a.C0065d;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.C0160j.C0108a;
import android.support.design.widget.CoordinatorLayout.C0088a;
import android.support.design.widget.CoordinatorLayout.C0102b;
import android.support.design.widget.CoordinatorLayout.C0104d;
import android.support.v4.content.p020a.C0402a;
import android.support.v4.view.ah;
import android.support.v7.widget.C1070n;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import java.util.List;

@C0102b(a = Behavior.class)
public class FloatingActionButton extends af {
    /* renamed from: a */
    int f342a;
    /* renamed from: b */
    boolean f343b;
    /* renamed from: c */
    final Rect f344c;
    /* renamed from: d */
    private ColorStateList f345d;
    /* renamed from: e */
    private Mode f346e;
    /* renamed from: f */
    private int f347f;
    /* renamed from: g */
    private int f348g;
    /* renamed from: h */
    private int f349h;
    /* renamed from: i */
    private final Rect f350i;
    /* renamed from: j */
    private C1070n f351j;
    /* renamed from: k */
    private C0160j f352k;

    public static class Behavior extends C0088a<FloatingActionButton> {
        /* renamed from: a */
        private Rect f337a;
        /* renamed from: b */
        private C0110a f338b;
        /* renamed from: c */
        private boolean f339c;

        public Behavior() {
            this.f339c = true;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.FloatingActionButton_Behavior_Layout);
            this.f339c = obtainStyledAttributes.getBoolean(C0072k.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            obtainStyledAttributes.recycle();
        }

        /* renamed from: a */
        private void m560a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            int i = 0;
            Rect rect = floatingActionButton.f344c;
            if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
                C0104d c0104d = (C0104d) floatingActionButton.getLayoutParams();
                int i2 = floatingActionButton.getRight() >= coordinatorLayout.getWidth() - c0104d.rightMargin ? rect.right : floatingActionButton.getLeft() <= c0104d.leftMargin ? -rect.left : 0;
                if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - c0104d.bottomMargin) {
                    i = rect.bottom;
                } else if (floatingActionButton.getTop() <= c0104d.topMargin) {
                    i = -rect.top;
                }
                if (i != 0) {
                    ah.m2808e((View) floatingActionButton, i);
                }
                if (i2 != 0) {
                    ah.m2811f((View) floatingActionButton, i2);
                }
            }
        }

        /* renamed from: a */
        private boolean m561a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!m563a((View) appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.f337a == null) {
                this.f337a = new Rect();
            }
            Rect rect = this.f337a;
            C0210z.m991b(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.m590b(this.f338b, false);
            } else {
                floatingActionButton.m588a(this.f338b, false);
            }
            return true;
        }

        /* renamed from: a */
        private static boolean m562a(View view) {
            LayoutParams layoutParams = view.getLayoutParams();
            return layoutParams instanceof C0104d ? ((C0104d) layoutParams).m501b() instanceof BottomSheetBehavior : false;
        }

        /* renamed from: a */
        private boolean m563a(View view, FloatingActionButton floatingActionButton) {
            return !this.f339c ? false : ((C0104d) floatingActionButton.getLayoutParams()).m495a() != view.getId() ? false : floatingActionButton.getUserSetVisibility() == 0;
        }

        /* renamed from: b */
        private boolean m564b(View view, FloatingActionButton floatingActionButton) {
            if (!m563a(view, floatingActionButton)) {
                return false;
            }
            if (view.getTop() < ((C0104d) floatingActionButton.getLayoutParams()).topMargin + (floatingActionButton.getHeight() / 2)) {
                floatingActionButton.m590b(this.f338b, false);
            } else {
                floatingActionButton.m588a(this.f338b, false);
            }
            return true;
        }

        /* renamed from: a */
        public void mo110a(C0104d c0104d) {
            if (c0104d.f299h == 0) {
                c0104d.f299h = 80;
            }
        }

        /* renamed from: a */
        public boolean m566a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i) {
            List c = coordinatorLayout.m551c((View) floatingActionButton);
            int size = c.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = (View) c.get(i2);
                if (!(view instanceof AppBarLayout)) {
                    if (m562a(view) && m564b(view, floatingActionButton)) {
                        break;
                    }
                } else if (m561a(coordinatorLayout, (AppBarLayout) view, floatingActionButton)) {
                    break;
                }
            }
            coordinatorLayout.m541a((View) floatingActionButton, i);
            m560a(coordinatorLayout, floatingActionButton);
            return true;
        }

        /* renamed from: a */
        public boolean m567a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, Rect rect) {
            Rect rect2 = floatingActionButton.f344c;
            rect.set(floatingActionButton.getLeft() + rect2.left, floatingActionButton.getTop() + rect2.top, floatingActionButton.getRight() - rect2.right, floatingActionButton.getBottom() - rect2.bottom);
            return true;
        }

        /* renamed from: a */
        public boolean m568a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                m561a(coordinatorLayout, (AppBarLayout) view, floatingActionButton);
            } else if (m562a(view)) {
                m564b(view, floatingActionButton);
            }
            return false;
        }

        /* renamed from: c */
        public /* synthetic */ boolean mo87c(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return m568a(coordinatorLayout, (FloatingActionButton) view, view2);
        }
    }

    /* renamed from: android.support.design.widget.FloatingActionButton$a */
    public static abstract class C0110a {
        /* renamed from: a */
        public void m572a(FloatingActionButton floatingActionButton) {
        }

        /* renamed from: b */
        public void m573b(FloatingActionButton floatingActionButton) {
        }
    }

    /* renamed from: android.support.design.widget.FloatingActionButton$b */
    private class C0112b implements C0111p {
        /* renamed from: a */
        final /* synthetic */ FloatingActionButton f340a;

        C0112b(FloatingActionButton floatingActionButton) {
            this.f340a = floatingActionButton;
        }

        /* renamed from: a */
        public float mo112a() {
            return ((float) this.f340a.getSizeDimension()) / 2.0f;
        }

        /* renamed from: a */
        public void mo113a(int i, int i2, int i3, int i4) {
            this.f340a.f344c.set(i, i2, i3, i4);
            this.f340a.setPadding(this.f340a.f342a + i, this.f340a.f342a + i2, this.f340a.f342a + i3, this.f340a.f342a + i4);
        }

        /* renamed from: a */
        public void mo114a(Drawable drawable) {
            super.setBackgroundDrawable(drawable);
        }

        /* renamed from: b */
        public boolean mo115b() {
            return this.f340a.f343b;
        }
    }

    /* renamed from: a */
    private int m583a(int i) {
        Resources resources = getResources();
        switch (i) {
            case -1:
                return Math.max(C0402a.m1861b(resources), C0402a.m1860a(resources)) < 470 ? m583a(1) : m583a(0);
            case 1:
                return resources.getDimensionPixelSize(C0065d.design_fab_size_mini);
            default:
                return resources.getDimensionPixelSize(C0065d.design_fab_size_normal);
        }
    }

    /* renamed from: a */
    private static int m584a(int i, int i2) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        switch (mode) {
            case Integer.MIN_VALUE:
                return Math.min(i, size);
            case 1073741824:
                return size;
            default:
                return i;
        }
    }

    /* renamed from: a */
    private C0108a m585a(final C0110a c0110a) {
        return c0110a == null ? null : new C0108a(this) {
            /* renamed from: b */
            final /* synthetic */ FloatingActionButton f336b;

            /* renamed from: a */
            public void mo108a() {
                c0110a.m572a(this.f336b);
            }

            /* renamed from: b */
            public void mo109b() {
                c0110a.m573b(this.f336b);
            }
        };
    }

    /* renamed from: a */
    private C0160j m586a() {
        int i = VERSION.SDK_INT;
        return i >= 21 ? new C0166k(this, new C0112b(this), ad.f438a) : i >= 14 ? new C0164i(this, new C0112b(this), ad.f438a) : new C0161h(this, new C0112b(this), ad.f438a);
    }

    private C0160j getImpl() {
        if (this.f352k == null) {
            this.f352k = m586a();
        }
        return this.f352k;
    }

    /* renamed from: a */
    void m588a(C0110a c0110a, boolean z) {
        getImpl().mo154b(m585a(c0110a), z);
    }

    /* renamed from: a */
    public boolean m589a(Rect rect) {
        if (!ah.m2767G(this)) {
            return false;
        }
        rect.set(0, 0, getWidth(), getHeight());
        rect.left += this.f344c.left;
        rect.top += this.f344c.top;
        rect.right -= this.f344c.right;
        rect.bottom -= this.f344c.bottom;
        return true;
    }

    /* renamed from: b */
    void m590b(C0110a c0110a, boolean z) {
        getImpl().mo151a(m585a(c0110a), z);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().mo152a(getDrawableState());
    }

    public ColorStateList getBackgroundTintList() {
        return this.f345d;
    }

    public Mode getBackgroundTintMode() {
        return this.f346e;
    }

    public float getCompatElevation() {
        return getImpl().mo145a();
    }

    public Drawable getContentBackground() {
        return getImpl().m774f();
    }

    public int getRippleColor() {
        return this.f347f;
    }

    public int getSize() {
        return this.f348g;
    }

    int getSizeDimension() {
        return m583a(this.f348g);
    }

    public boolean getUseCompatPadding() {
        return this.f343b;
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().mo153b();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().m776h();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().m777i();
    }

    protected void onMeasure(int i, int i2) {
        int sizeDimension = getSizeDimension();
        this.f342a = (sizeDimension - this.f349h) / 2;
        getImpl().m775g();
        sizeDimension = Math.min(m584a(sizeDimension, i), m584a(sizeDimension, i2));
        setMeasuredDimension((this.f344c.left + sizeDimension) + this.f344c.right, (sizeDimension + this.f344c.top) + this.f344c.bottom);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                if (m589a(this.f350i) && !this.f350i.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    return false;
                }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setBackgroundColor(int i) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int i) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.f345d != colorStateList) {
            this.f345d = colorStateList;
            getImpl().mo148a(colorStateList);
        }
    }

    public void setBackgroundTintMode(Mode mode) {
        if (this.f346e != mode) {
            this.f346e = mode;
            getImpl().mo149a(mode);
        }
    }

    public void setCompatElevation(float f) {
        getImpl().m760a(f);
    }

    public void setImageResource(int i) {
        this.f351j.m5888a(i);
    }

    public void setRippleColor(int i) {
        if (this.f347f != i) {
            this.f347f = i;
            getImpl().mo147a(i);
        }
    }

    public void setSize(int i) {
        if (i != this.f348g) {
            this.f348g = i;
            requestLayout();
        }
    }

    public void setUseCompatPadding(boolean z) {
        if (this.f343b != z) {
            this.f343b = z;
            getImpl().mo155c();
        }
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }
}
