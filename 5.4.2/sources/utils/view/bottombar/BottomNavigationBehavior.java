package utils.view.bottombar;

import android.os.Build.VERSION;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v4.view.p024b.C0605c;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class BottomNavigationBehavior<V extends View> extends VerticalScrollingBehavior<V> {
    /* renamed from: a */
    private static final Interpolator f10441a = new C0605c();
    /* renamed from: b */
    private final int f10442b;
    /* renamed from: c */
    private final int f10443c;
    /* renamed from: d */
    private boolean f10444d = false;
    /* renamed from: e */
    private ax f10445e;
    /* renamed from: f */
    private boolean f10446f = false;
    /* renamed from: g */
    private int f10447g = -1;
    /* renamed from: h */
    private final BottomNavigationWithSnackbar f10448h;
    /* renamed from: i */
    private boolean f10449i;

    private interface BottomNavigationWithSnackbar {
        /* renamed from: a */
        void mo4649a(CoordinatorLayout coordinatorLayout, View view, View view2);
    }

    private class LollipopBottomNavWithSnackBarImpl implements BottomNavigationWithSnackbar {
        /* renamed from: a */
        final /* synthetic */ BottomNavigationBehavior f10435a;

        private LollipopBottomNavWithSnackBarImpl(BottomNavigationBehavior bottomNavigationBehavior) {
            this.f10435a = bottomNavigationBehavior;
        }

        /* renamed from: a */
        public void mo4649a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            if (!this.f10435a.f10444d && (view instanceof SnackbarLayout)) {
                if (this.f10435a.f10447g == -1) {
                    this.f10435a.f10447g = view.getHeight();
                }
                if (ah.m(view2) == BitmapDescriptorFactory.HUE_RED) {
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), (this.f10435a.f10447g + this.f10435a.f10442b) - this.f10435a.f10443c);
                }
            }
        }
    }

    private class PreLollipopBottomNavWithSnackBarImpl implements BottomNavigationWithSnackbar {
        /* renamed from: a */
        final /* synthetic */ BottomNavigationBehavior f10436a;

        private PreLollipopBottomNavWithSnackBarImpl(BottomNavigationBehavior bottomNavigationBehavior) {
            this.f10436a = bottomNavigationBehavior;
        }

        /* renamed from: a */
        public void mo4649a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            if (!this.f10436a.f10444d && (view instanceof SnackbarLayout)) {
                if (this.f10436a.f10447g == -1) {
                    this.f10436a.f10447g = view.getHeight();
                }
                if (ah.m(view2) == BitmapDescriptorFactory.HUE_RED) {
                    ((MarginLayoutParams) view.getLayoutParams()).bottomMargin = (this.f10436a.f10442b + this.f10436a.f10447g) - this.f10436a.f10443c;
                    view2.bringToFront();
                    view2.getParent().requestLayout();
                    if (VERSION.SDK_INT < 19) {
                        ((View) view2.getParent()).invalidate();
                    }
                }
            }
        }
    }

    BottomNavigationBehavior(int i, int i2, boolean z) {
        this.f10448h = VERSION.SDK_INT >= 21 ? new LollipopBottomNavWithSnackBarImpl() : new PreLollipopBottomNavWithSnackBarImpl();
        this.f10449i = true;
        this.f10442b = i;
        this.f10443c = i2;
        this.f10444d = z;
    }

    /* renamed from: a */
    private void m14329a(V v) {
        if (this.f10445e == null) {
            this.f10445e = ah.q(v);
            this.f10445e.a(300);
            this.f10445e.a(f10441a);
            return;
        }
        this.f10445e.b();
    }

    /* renamed from: a */
    private void m14330a(V v, int i) {
        if (!this.f10449i) {
            return;
        }
        if (i == -1 && this.f10446f) {
            this.f10446f = false;
            m14334b(v, this.f10443c);
        } else if (i == 1 && !this.f10446f) {
            this.f10446f = true;
            m14334b(v, this.f10442b + this.f10443c);
        }
    }

    /* renamed from: a */
    private void m14331a(View view, boolean z) {
        if (!this.f10444d && (view instanceof SnackbarLayout)) {
            this.f10449i = z;
        }
    }

    /* renamed from: b */
    private void m14334b(V v, int i) {
        m14329a((View) v);
        this.f10445e.c((float) i).c();
    }

    /* renamed from: a */
    public void mo4650a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
    }

    /* renamed from: a */
    public void mo4651a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3) {
        m14330a((View) v, i3);
    }

    /* renamed from: a */
    protected boolean mo4652a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, int i) {
        m14330a((View) v, i);
        return true;
    }

    /* renamed from: b */
    public boolean m14340b(CoordinatorLayout coordinatorLayout, V v, View view) {
        this.f10448h.mo4649a(coordinatorLayout, view, v);
        return view instanceof SnackbarLayout;
    }

    /* renamed from: c */
    public boolean m14341c(CoordinatorLayout coordinatorLayout, V v, View view) {
        m14331a(view, false);
        return super.c(coordinatorLayout, v, view);
    }

    /* renamed from: d */
    public void m14342d(CoordinatorLayout coordinatorLayout, V v, View view) {
        m14331a(view, true);
        super.d(coordinatorLayout, v, view);
    }
}
