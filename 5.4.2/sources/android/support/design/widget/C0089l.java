package android.support.design.widget;

import android.content.Context;
import android.support.v4.view.C0659t;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.support.v4.widget.C0729x;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/* renamed from: android.support.design.widget.l */
abstract class C0089l<V extends View> extends ab<V> {
    /* renamed from: a */
    C0729x f222a;
    /* renamed from: b */
    private Runnable f223b;
    /* renamed from: c */
    private boolean f224c;
    /* renamed from: d */
    private int f225d = -1;
    /* renamed from: e */
    private int f226e;
    /* renamed from: f */
    private int f227f = -1;
    /* renamed from: g */
    private VelocityTracker f228g;

    /* renamed from: android.support.design.widget.l$a */
    private class C0167a implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0089l f565a;
        /* renamed from: b */
        private final CoordinatorLayout f566b;
        /* renamed from: c */
        private final V f567c;

        C0167a(C0089l c0089l, CoordinatorLayout coordinatorLayout, V v) {
            this.f565a = c0089l;
            this.f566b = coordinatorLayout;
            this.f567c = v;
        }

        public void run() {
            if (this.f567c != null && this.f565a.f222a != null) {
                if (this.f565a.f222a.m3554g()) {
                    this.f565a.a_(this.f566b, this.f567c, this.f565a.f222a.m3550c());
                    ah.m2787a(this.f567c, (Runnable) this);
                    return;
                }
                this.f565a.mo68a(this.f566b, this.f567c);
            }
        }
    }

    public C0089l(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* renamed from: d */
    private void m349d() {
        if (this.f228g == null) {
            this.f228g = VelocityTracker.obtain();
        }
    }

    /* renamed from: a */
    int mo65a() {
        return mo77b();
    }

    /* renamed from: a */
    int mo66a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        int b = mo77b();
        if (i2 == 0 || b < i2 || b > i3) {
            return 0;
        }
        int a = C0168n.m809a(i, i2, i3);
        if (b == a) {
            return 0;
        }
        mo73a(a);
        return b - a;
    }

    /* renamed from: a */
    int mo67a(V v) {
        return v.getHeight();
    }

    /* renamed from: a */
    void mo68a(CoordinatorLayout coordinatorLayout, V v) {
    }

    /* renamed from: a */
    final boolean m354a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, float f) {
        if (this.f223b != null) {
            v.removeCallbacks(this.f223b);
            this.f223b = null;
        }
        if (this.f222a == null) {
            this.f222a = C0729x.m3541a(v.getContext());
        }
        this.f222a.m3545a(0, mo77b(), 0, Math.round(f), 0, 0, i, i2);
        if (this.f222a.m3554g()) {
            this.f223b = new C0167a(this, coordinatorLayout, v);
            ah.m2787a((View) v, this.f223b);
            return true;
        }
        mo68a(coordinatorLayout, v);
        return false;
    }

    /* renamed from: a */
    public boolean mo63a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.f227f < 0) {
            this.f227f = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getAction() == 2 && this.f224c) {
            return true;
        }
        int y;
        switch (C0659t.m3205a(motionEvent)) {
            case 0:
                this.f224c = false;
                int x = (int) motionEvent.getX();
                y = (int) motionEvent.getY();
                if (mo80c(v) && coordinatorLayout.m546a((View) v, x, y)) {
                    this.f226e = y;
                    this.f225d = motionEvent.getPointerId(0);
                    m349d();
                    break;
                }
            case 1:
            case 3:
                this.f224c = false;
                this.f225d = -1;
                if (this.f228g != null) {
                    this.f228g.recycle();
                    this.f228g = null;
                    break;
                }
                break;
            case 2:
                y = this.f225d;
                if (y != -1) {
                    y = motionEvent.findPointerIndex(y);
                    if (y != -1) {
                        y = (int) motionEvent.getY(y);
                        if (Math.abs(y - this.f226e) > this.f227f) {
                            this.f224c = true;
                            this.f226e = y;
                            break;
                        }
                    }
                }
                break;
        }
        if (this.f228g != null) {
            this.f228g.addMovement(motionEvent);
        }
        return this.f224c;
    }

    int a_(CoordinatorLayout coordinatorLayout, V v, int i) {
        return mo66a(coordinatorLayout, (View) v, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /* renamed from: b */
    final int m356b(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        return mo66a(coordinatorLayout, (View) v, mo65a() - i, i2, i3);
    }

    /* renamed from: b */
    int mo78b(V v) {
        return -v.getHeight();
    }

    /* renamed from: b */
    public boolean mo64b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.f227f < 0) {
            this.f227f = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        switch (C0659t.m3205a(motionEvent)) {
            case 0:
                int y = (int) motionEvent.getY();
                if (coordinatorLayout.m546a((View) v, (int) motionEvent.getX(), y) && mo80c(v)) {
                    this.f226e = y;
                    this.f225d = motionEvent.getPointerId(0);
                    m349d();
                    break;
                }
                return false;
                break;
            case 1:
                if (this.f228g != null) {
                    this.f228g.addMovement(motionEvent);
                    this.f228g.computeCurrentVelocity(1000);
                    m354a(coordinatorLayout, (View) v, -mo67a(v), 0, af.m2517b(this.f228g, this.f225d));
                    break;
                }
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.f225d);
                if (findPointerIndex != -1) {
                    findPointerIndex = (int) motionEvent.getY(findPointerIndex);
                    int i = this.f226e - findPointerIndex;
                    if (!this.f224c && Math.abs(i) > this.f227f) {
                        this.f224c = true;
                        i = i > 0 ? i - this.f227f : i + this.f227f;
                    }
                    if (this.f224c) {
                        this.f226e = findPointerIndex;
                        m356b(coordinatorLayout, v, i, mo78b(v), 0);
                        break;
                    }
                }
                return false;
                break;
            case 3:
                break;
        }
        this.f224c = false;
        this.f225d = -1;
        if (this.f228g != null) {
            this.f228g.recycle();
            this.f228g = null;
        }
        if (this.f228g != null) {
            this.f228g.addMovement(motionEvent);
        }
        return true;
    }

    /* renamed from: c */
    boolean mo80c(V v) {
        return false;
    }
}
