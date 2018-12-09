package utils.view.bottombar;

import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.C0088a;
import android.support.v4.view.be;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

abstract class VerticalScrollingBehavior<V extends View> extends C0088a<V> {
    /* renamed from: a */
    private int f10437a = 0;
    /* renamed from: b */
    private int f10438b = 0;
    /* renamed from: c */
    private int f10439c = 0;
    /* renamed from: d */
    private int f10440d = 0;

    VerticalScrollingBehavior() {
    }

    /* renamed from: a */
    public be m14318a(CoordinatorLayout coordinatorLayout, V v, be beVar) {
        return super.a(coordinatorLayout, v, beVar);
    }

    /* renamed from: a */
    abstract void mo4650a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3);

    /* renamed from: a */
    public void m14320a(CoordinatorLayout coordinatorLayout, V v, View view) {
        super.a(coordinatorLayout, v, view);
    }

    /* renamed from: a */
    public void m14321a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4) {
        super.a(coordinatorLayout, v, view, i, i2, i3, i4);
        if (i4 > 0 && this.f10437a < 0) {
            this.f10437a = 0;
            this.f10439c = 1;
        } else if (i4 < 0 && this.f10437a > 0) {
            this.f10437a = 0;
            this.f10439c = -1;
        }
        this.f10437a += i4;
        mo4650a(coordinatorLayout, (View) v, this.f10439c, i2, this.f10437a);
    }

    /* renamed from: a */
    public void m14322a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
        super.a(coordinatorLayout, v, view, i, i2, iArr);
        if (i2 > 0 && this.f10438b < 0) {
            this.f10438b = 0;
            this.f10440d = 1;
        } else if (i2 < 0 && this.f10438b > 0) {
            this.f10438b = 0;
            this.f10440d = -1;
        }
        this.f10438b += i2;
        mo4651a(coordinatorLayout, (View) v, view, i, i2, iArr, this.f10440d);
    }

    /* renamed from: a */
    abstract void mo4651a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3);

    /* renamed from: a */
    public boolean m14324a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        return super.a(coordinatorLayout, v, view, f, f2);
    }

    /* renamed from: a */
    abstract boolean mo4652a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, int i);

    /* renamed from: a */
    public boolean m14326a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, boolean z) {
        super.a(coordinatorLayout, v, view, f, f2, z);
        this.f10440d = f2 > BitmapDescriptorFactory.HUE_RED ? 1 : -1;
        return mo4652a(coordinatorLayout, (View) v, view, f, f2, this.f10440d);
    }

    /* renamed from: a */
    public boolean m14327a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        return (i & 2) != 0;
    }

    /* renamed from: b */
    public Parcelable m14328b(CoordinatorLayout coordinatorLayout, V v) {
        return super.b(coordinatorLayout, v);
    }

    /* renamed from: b */
    public void m14329b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        super.b(coordinatorLayout, v, view, view2, i);
    }
}
