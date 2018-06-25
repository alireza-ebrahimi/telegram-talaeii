package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout.C0088a;
import android.support.v4.view.C0659t;
import android.support.v4.view.ah;
import android.support.v4.widget.ag;
import android.support.v4.widget.ag.C0094a;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SwipeDismissBehavior<V extends View> extends C0088a<V> {
    /* renamed from: a */
    private boolean f368a;
    /* renamed from: b */
    ag f369b;
    /* renamed from: c */
    C0116a f370c;
    /* renamed from: d */
    int f371d = 2;
    /* renamed from: e */
    float f372e = 0.5f;
    /* renamed from: f */
    float f373f = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: g */
    float f374g = 0.5f;
    /* renamed from: h */
    private float f375h = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: i */
    private boolean f376i;
    /* renamed from: j */
    private final C0094a f377j = new C01151(this);

    /* renamed from: android.support.design.widget.SwipeDismissBehavior$1 */
    class C01151 extends C0094a {
        /* renamed from: a */
        final /* synthetic */ SwipeDismissBehavior f362a;
        /* renamed from: b */
        private int f363b;
        /* renamed from: c */
        private int f364c = -1;

        C01151(SwipeDismissBehavior swipeDismissBehavior) {
            this.f362a = swipeDismissBehavior;
        }

        /* renamed from: a */
        private boolean m601a(View view, float f) {
            if (f != BitmapDescriptorFactory.HUE_RED) {
                boolean z = ah.m2812g(view) == 1;
                return this.f362a.f371d == 2 ? true : this.f362a.f371d == 0 ? z ? f < BitmapDescriptorFactory.HUE_RED : f > BitmapDescriptorFactory.HUE_RED : this.f362a.f371d == 1 ? z ? f > BitmapDescriptorFactory.HUE_RED : f < BitmapDescriptorFactory.HUE_RED : false;
            } else {
                return Math.abs(view.getLeft() - this.f363b) >= Math.round(((float) view.getWidth()) * this.f362a.f372e);
            }
        }

        /* renamed from: a */
        public int mo89a(View view, int i, int i2) {
            return view.getTop();
        }

        /* renamed from: a */
        public void mo90a(int i) {
            if (this.f362a.f370c != null) {
                this.f362a.f370c.mo124a(i);
            }
        }

        /* renamed from: a */
        public void mo91a(View view, float f, float f2) {
            this.f364c = -1;
            int width = view.getWidth();
            boolean z = false;
            if (m601a(view, f)) {
                width = view.getLeft() < this.f363b ? this.f363b - width : this.f363b + width;
                z = true;
            } else {
                width = this.f363b;
            }
            if (this.f362a.f369b.m3345a(width, view.getTop())) {
                ah.m2787a(view, new C0117b(this.f362a, view, z));
            } else if (z && this.f362a.f370c != null) {
                this.f362a.f370c.mo125a(view);
            }
        }

        /* renamed from: a */
        public void mo92a(View view, int i, int i2, int i3, int i4) {
            float width = ((float) this.f363b) + (((float) view.getWidth()) * this.f362a.f373f);
            float width2 = ((float) this.f363b) + (((float) view.getWidth()) * this.f362a.f374g);
            if (((float) i) <= width) {
                ah.m2800c(view, 1.0f);
            } else if (((float) i) >= width2) {
                ah.m2800c(view, (float) BitmapDescriptorFactory.HUE_RED);
            } else {
                ah.m2800c(view, SwipeDismissBehavior.m612a((float) BitmapDescriptorFactory.HUE_RED, 1.0f - SwipeDismissBehavior.m615b(width, width2, (float) i), 1.0f));
            }
        }

        /* renamed from: a */
        public boolean mo93a(View view, int i) {
            return this.f364c == -1 && this.f362a.mo129a(view);
        }

        /* renamed from: b */
        public int mo117b(View view) {
            return view.getWidth();
        }

        /* renamed from: b */
        public int mo94b(View view, int i, int i2) {
            int width;
            int i3;
            Object obj = ah.m2812g(view) == 1 ? 1 : null;
            if (this.f362a.f371d == 0) {
                if (obj != null) {
                    width = this.f363b - view.getWidth();
                    i3 = this.f363b;
                } else {
                    width = this.f363b;
                    i3 = this.f363b + view.getWidth();
                }
            } else if (this.f362a.f371d != 1) {
                width = this.f363b - view.getWidth();
                i3 = this.f363b + view.getWidth();
            } else if (obj != null) {
                width = this.f363b;
                i3 = this.f363b + view.getWidth();
            } else {
                width = this.f363b - view.getWidth();
                i3 = this.f363b;
            }
            return SwipeDismissBehavior.m613a(width, i, i3);
        }

        /* renamed from: b */
        public void mo118b(View view, int i) {
            this.f364c = i;
            this.f363b = view.getLeft();
            ViewParent parent = view.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    /* renamed from: android.support.design.widget.SwipeDismissBehavior$a */
    public interface C0116a {
        /* renamed from: a */
        void mo124a(int i);

        /* renamed from: a */
        void mo125a(View view);
    }

    /* renamed from: android.support.design.widget.SwipeDismissBehavior$b */
    private class C0117b implements Runnable {
        /* renamed from: a */
        final /* synthetic */ SwipeDismissBehavior f365a;
        /* renamed from: b */
        private final View f366b;
        /* renamed from: c */
        private final boolean f367c;

        C0117b(SwipeDismissBehavior swipeDismissBehavior, View view, boolean z) {
            this.f365a = swipeDismissBehavior;
            this.f366b = view;
            this.f367c = z;
        }

        public void run() {
            if (this.f365a.f369b != null && this.f365a.f369b.m3348a(true)) {
                ah.m2787a(this.f366b, (Runnable) this);
            } else if (this.f367c && this.f365a.f370c != null) {
                this.f365a.f370c.mo125a(this.f366b);
            }
        }
    }

    /* renamed from: a */
    static float m612a(float f, float f2, float f3) {
        return Math.min(Math.max(f, f2), f3);
    }

    /* renamed from: a */
    static int m613a(int i, int i2, int i3) {
        return Math.min(Math.max(i, i2), i3);
    }

    /* renamed from: a */
    private void m614a(ViewGroup viewGroup) {
        if (this.f369b == null) {
            this.f369b = this.f376i ? ag.m3325a(viewGroup, this.f375h, this.f377j) : ag.m3326a(viewGroup, this.f377j);
        }
    }

    /* renamed from: b */
    static float m615b(float f, float f2, float f3) {
        return (f3 - f) / (f2 - f);
    }

    /* renamed from: a */
    public void m616a(float f) {
        this.f373f = m612a((float) BitmapDescriptorFactory.HUE_RED, f, 1.0f);
    }

    /* renamed from: a */
    public void m617a(int i) {
        this.f371d = i;
    }

    /* renamed from: a */
    public void m618a(C0116a c0116a) {
        this.f370c = c0116a;
    }

    /* renamed from: a */
    public boolean mo63a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z = this.f368a;
        switch (C0659t.m3205a(motionEvent)) {
            case 0:
                this.f368a = coordinatorLayout.m546a((View) v, (int) motionEvent.getX(), (int) motionEvent.getY());
                z = this.f368a;
                break;
            case 1:
            case 3:
                this.f368a = false;
                break;
        }
        if (!z) {
            return false;
        }
        m614a((ViewGroup) coordinatorLayout);
        return this.f369b.m3346a(motionEvent);
    }

    /* renamed from: a */
    public boolean mo129a(View view) {
        return true;
    }

    /* renamed from: b */
    public void m621b(float f) {
        this.f374g = m612a((float) BitmapDescriptorFactory.HUE_RED, f, 1.0f);
    }

    /* renamed from: b */
    public boolean mo64b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.f369b == null) {
            return false;
        }
        this.f369b.m3351b(motionEvent);
        return true;
    }
}
