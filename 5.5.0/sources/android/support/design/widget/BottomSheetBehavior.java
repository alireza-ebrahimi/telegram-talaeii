package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.design.C0073a.C0065d;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.CoordinatorLayout.C0088a;
import android.support.design.widget.CoordinatorLayout.C0104d;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.C0078v;
import android.support.v4.view.C0659t;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.support.v4.widget.ag;
import android.support.v4.widget.ag.C0094a;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View> extends C0088a<V> {
    /* renamed from: a */
    int f262a;
    /* renamed from: b */
    int f263b;
    /* renamed from: c */
    boolean f264c;
    /* renamed from: d */
    int f265d = 4;
    /* renamed from: e */
    ag f266e;
    /* renamed from: f */
    int f267f;
    /* renamed from: g */
    WeakReference<V> f268g;
    /* renamed from: h */
    WeakReference<View> f269h;
    /* renamed from: i */
    int f270i;
    /* renamed from: j */
    boolean f271j;
    /* renamed from: k */
    private float f272k;
    /* renamed from: l */
    private int f273l;
    /* renamed from: m */
    private boolean f274m;
    /* renamed from: n */
    private int f275n;
    /* renamed from: o */
    private boolean f276o;
    /* renamed from: p */
    private boolean f277p;
    /* renamed from: q */
    private int f278q;
    /* renamed from: r */
    private boolean f279r;
    /* renamed from: s */
    private C0097a f280s;
    /* renamed from: t */
    private VelocityTracker f281t;
    /* renamed from: u */
    private int f282u;
    /* renamed from: v */
    private final C0094a f283v = new C00952(this);

    /* renamed from: android.support.design.widget.BottomSheetBehavior$2 */
    class C00952 extends C0094a {
        /* renamed from: a */
        final /* synthetic */ BottomSheetBehavior f257a;

        C00952(BottomSheetBehavior bottomSheetBehavior) {
            this.f257a = bottomSheetBehavior;
        }

        /* renamed from: a */
        public int mo88a(View view) {
            return this.f257a.f264c ? this.f257a.f267f - this.f257a.f262a : this.f257a.f263b - this.f257a.f262a;
        }

        /* renamed from: a */
        public int mo89a(View view, int i, int i2) {
            return C0168n.m809a(i, this.f257a.f262a, this.f257a.f264c ? this.f257a.f267f : this.f257a.f263b);
        }

        /* renamed from: a */
        public void mo90a(int i) {
            if (i == 1) {
                this.f257a.m486c(1);
            }
        }

        /* renamed from: a */
        public void mo91a(View view, float f, float f2) {
            int i;
            int i2 = 3;
            if (f2 < BitmapDescriptorFactory.HUE_RED) {
                i = this.f257a.f262a;
            } else if (this.f257a.f264c && this.f257a.m481a(view, f2)) {
                i = this.f257a.f267f;
                i2 = 5;
            } else if (f2 == BitmapDescriptorFactory.HUE_RED) {
                int top = view.getTop();
                if (Math.abs(top - this.f257a.f262a) < Math.abs(top - this.f257a.f263b)) {
                    i = this.f257a.f262a;
                } else {
                    i = this.f257a.f263b;
                    i2 = 4;
                }
            } else {
                i = this.f257a.f263b;
                i2 = 4;
            }
            if (this.f257a.f266e.m3345a(view.getLeft(), i)) {
                this.f257a.m486c(2);
                ah.m2787a(view, new C0098b(this.f257a, view, i2));
                return;
            }
            this.f257a.m486c(i2);
        }

        /* renamed from: a */
        public void mo92a(View view, int i, int i2, int i3, int i4) {
            this.f257a.m487d(i2);
        }

        /* renamed from: a */
        public boolean mo93a(View view, int i) {
            if (this.f257a.f265d == 1 || this.f257a.f271j) {
                return false;
            }
            if (this.f257a.f265d == 3 && this.f257a.f270i == i) {
                View view2 = (View) this.f257a.f269h.get();
                if (view2 != null && ah.m2798b(view2, -1)) {
                    return false;
                }
            }
            boolean z = this.f257a.f268g != null && this.f257a.f268g.get() == view;
            return z;
        }

        /* renamed from: b */
        public int mo94b(View view, int i, int i2) {
            return view.getLeft();
        }
    }

    protected static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C00961());
        /* renamed from: a */
        final int f258a;

        /* renamed from: android.support.design.widget.BottomSheetBehavior$SavedState$1 */
        static class C00961 implements C0085g<SavedState> {
            C00961() {
            }

            /* renamed from: a */
            public SavedState m462a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m463a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m462a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m463a(i);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f258a = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.f258a = i;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f258a);
        }
    }

    /* renamed from: android.support.design.widget.BottomSheetBehavior$a */
    public static abstract class C0097a {
        /* renamed from: a */
        public abstract void mo131a(View view, float f);

        /* renamed from: a */
        public abstract void mo132a(View view, int i);
    }

    /* renamed from: android.support.design.widget.BottomSheetBehavior$b */
    private class C0098b implements Runnable {
        /* renamed from: a */
        final /* synthetic */ BottomSheetBehavior f259a;
        /* renamed from: b */
        private final View f260b;
        /* renamed from: c */
        private final int f261c;

        C0098b(BottomSheetBehavior bottomSheetBehavior, View view, int i) {
            this.f259a = bottomSheetBehavior;
            this.f260b = view;
            this.f261c = i;
        }

        public void run() {
            if (this.f259a.f266e == null || !this.f259a.f266e.m3348a(true)) {
                this.f259a.m486c(this.f261c);
            } else {
                ah.m2787a(this.f260b, (Runnable) this);
            }
        }
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.BottomSheetBehavior_Layout);
        TypedValue peekValue = obtainStyledAttributes.peekValue(C0072k.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (peekValue == null || peekValue.data != -1) {
            m470a(obtainStyledAttributes.getDimensionPixelSize(C0072k.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            m470a(peekValue.data);
        }
        m476a(obtainStyledAttributes.getBoolean(C0072k.BottomSheetBehavior_Layout_behavior_hideable, false));
        m484b(obtainStyledAttributes.getBoolean(C0072k.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        obtainStyledAttributes.recycle();
        this.f272k = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    /* renamed from: a */
    public static <V extends View> BottomSheetBehavior<V> m466a(V v) {
        LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof C0104d) {
            C0088a b = ((C0104d) layoutParams).m501b();
            if (b instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) b;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }

    /* renamed from: a */
    private void m467a() {
        this.f270i = -1;
        if (this.f281t != null) {
            this.f281t.recycle();
            this.f281t = null;
        }
    }

    /* renamed from: b */
    private float m468b() {
        this.f281t.computeCurrentVelocity(1000, this.f272k);
        return af.m2517b(this.f281t, this.f270i);
    }

    /* renamed from: b */
    private View m469b(View view) {
        if (view instanceof C0078v) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View b = m469b(viewGroup.getChildAt(i));
                if (b != null) {
                    return b;
                }
            }
        }
        return null;
    }

    /* renamed from: a */
    public final void m470a(int i) {
        boolean z = true;
        if (i == -1) {
            if (!this.f274m) {
                this.f274m = true;
            }
            z = false;
        } else {
            if (this.f274m || this.f273l != i) {
                this.f274m = false;
                this.f273l = Math.max(0, i);
                this.f263b = this.f267f - i;
            }
            z = false;
        }
        if (z && this.f265d == 4 && this.f268g != null) {
            View view = (View) this.f268g.get();
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    /* renamed from: a */
    public void m471a(C0097a c0097a) {
        this.f280s = c0097a;
    }

    /* renamed from: a */
    public void mo69a(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.mo69a(coordinatorLayout, (View) v, savedState.getSuperState());
        if (savedState.f258a == 1 || savedState.f258a == 2) {
            this.f265d = 4;
        } else {
            this.f265d = savedState.f258a;
        }
    }

    /* renamed from: a */
    public void mo70a(CoordinatorLayout coordinatorLayout, V v, View view) {
        int i = 3;
        if (v.getTop() == this.f262a) {
            m486c(3);
        } else if (view == this.f269h.get() && this.f279r) {
            int i2;
            if (this.f278q > 0) {
                i2 = this.f262a;
            } else if (this.f264c && m481a((View) v, m468b())) {
                i2 = this.f267f;
                i = 5;
            } else if (this.f278q == 0) {
                int top = v.getTop();
                if (Math.abs(top - this.f262a) < Math.abs(top - this.f263b)) {
                    i2 = this.f262a;
                } else {
                    i2 = this.f263b;
                    i = 4;
                }
            } else {
                i2 = this.f263b;
                i = 4;
            }
            if (this.f266e.m3347a((View) v, v.getLeft(), i2)) {
                m486c(2);
                ah.m2787a((View) v, new C0098b(this, v, i));
            } else {
                m486c(i);
            }
            this.f279r = false;
        }
    }

    /* renamed from: a */
    public void mo72a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
        if (view == ((View) this.f269h.get())) {
            int top = v.getTop();
            int i3 = top - i2;
            if (i2 > 0) {
                if (i3 < this.f262a) {
                    iArr[1] = top - this.f262a;
                    ah.m2808e((View) v, -iArr[1]);
                    m486c(3);
                } else {
                    iArr[1] = i2;
                    ah.m2808e((View) v, -i2);
                    m486c(1);
                }
            } else if (i2 < 0 && !ah.m2798b(view, -1)) {
                if (i3 <= this.f263b || this.f264c) {
                    iArr[1] = i2;
                    ah.m2808e((View) v, -i2);
                    m486c(1);
                } else {
                    iArr[1] = top - this.f263b;
                    ah.m2808e((View) v, -iArr[1]);
                    m486c(4);
                }
            }
            m487d(v.getTop());
            this.f278q = i2;
            this.f279r = true;
        }
    }

    /* renamed from: a */
    void m475a(View view, int i) {
        int i2;
        if (i == 4) {
            i2 = this.f263b;
        } else if (i == 3) {
            i2 = this.f262a;
        } else if (this.f264c && i == 5) {
            i2 = this.f267f;
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + i);
        }
        m486c(2);
        if (this.f266e.m3347a(view, view.getLeft(), i2)) {
            ah.m2787a(view, new C0098b(this, view, i));
        }
    }

    /* renamed from: a */
    public void m476a(boolean z) {
        this.f264c = z;
    }

    /* renamed from: a */
    public boolean mo62a(CoordinatorLayout coordinatorLayout, V v, int i) {
        int max;
        if (ah.m2835y(coordinatorLayout) && !ah.m2835y(v)) {
            ah.m2789a((View) v, true);
        }
        int top = v.getTop();
        coordinatorLayout.m541a((View) v, i);
        this.f267f = coordinatorLayout.getHeight();
        if (this.f274m) {
            if (this.f275n == 0) {
                this.f275n = coordinatorLayout.getResources().getDimensionPixelSize(C0065d.design_bottom_sheet_peek_height_min);
            }
            max = Math.max(this.f275n, this.f267f - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            max = this.f273l;
        }
        this.f262a = Math.max(0, this.f267f - v.getHeight());
        this.f263b = Math.max(this.f267f - max, this.f262a);
        if (this.f265d == 3) {
            ah.m2808e((View) v, this.f262a);
        } else if (this.f264c && this.f265d == 5) {
            ah.m2808e((View) v, this.f267f);
        } else if (this.f265d == 4) {
            ah.m2808e((View) v, this.f263b);
        } else if (this.f265d == 1 || this.f265d == 2) {
            ah.m2808e((View) v, top - v.getTop());
        }
        if (this.f266e == null) {
            this.f266e = ag.m3326a((ViewGroup) coordinatorLayout, this.f283v);
        }
        this.f268g = new WeakReference(v);
        this.f269h = new WeakReference(m469b((View) v));
        return true;
    }

    /* renamed from: a */
    public boolean mo63a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z = true;
        if (v.isShown()) {
            View view;
            int a = C0659t.m3205a(motionEvent);
            if (a == 0) {
                m467a();
            }
            if (this.f281t == null) {
                this.f281t = VelocityTracker.obtain();
            }
            this.f281t.addMovement(motionEvent);
            switch (a) {
                case 0:
                    int x = (int) motionEvent.getX();
                    this.f282u = (int) motionEvent.getY();
                    view = (View) this.f269h.get();
                    if (view != null && coordinatorLayout.m546a(view, x, this.f282u)) {
                        this.f270i = motionEvent.getPointerId(motionEvent.getActionIndex());
                        this.f271j = true;
                    }
                    boolean z2 = this.f270i == -1 && !coordinatorLayout.m546a((View) v, x, this.f282u);
                    this.f277p = z2;
                    break;
                case 1:
                case 3:
                    this.f271j = false;
                    this.f270i = -1;
                    if (this.f277p) {
                        this.f277p = false;
                        return false;
                    }
                    break;
            }
            if (!this.f277p && this.f266e.m3346a(motionEvent)) {
                return true;
            }
            view = (View) this.f269h.get();
            if (a != 2 || view == null || this.f277p || this.f265d == 1 || coordinatorLayout.m546a(view, (int) motionEvent.getX(), (int) motionEvent.getY()) || Math.abs(((float) this.f282u) - motionEvent.getY()) <= ((float) this.f266e.m3342a())) {
                z = false;
            }
            return z;
        }
        this.f277p = true;
        return false;
    }

    /* renamed from: a */
    public boolean mo95a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        return view == this.f269h.get() && (this.f265d != 3 || super.mo95a(coordinatorLayout, (View) v, view, f, f2));
    }

    /* renamed from: a */
    public boolean mo76a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        this.f278q = 0;
        this.f279r = false;
        return (i & 2) != 0;
    }

    /* renamed from: a */
    boolean m481a(View view, float f) {
        return this.f276o ? true : view.getTop() < this.f263b ? false : Math.abs((((float) view.getTop()) + (0.1f * f)) - ((float) this.f263b)) / ((float) this.f273l) > 0.5f;
    }

    /* renamed from: b */
    public Parcelable mo79b(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.mo79b(coordinatorLayout, v), this.f265d);
    }

    /* renamed from: b */
    public final void m483b(final int i) {
        if (i != this.f265d) {
            if (this.f268g != null) {
                final View view = (View) this.f268g.get();
                if (view != null) {
                    ViewParent parent = view.getParent();
                    if (parent != null && parent.isLayoutRequested() && ah.m2769I(view)) {
                        view.post(new Runnable(this) {
                            /* renamed from: c */
                            final /* synthetic */ BottomSheetBehavior f256c;

                            public void run() {
                                this.f256c.m475a(view, i);
                            }
                        });
                    } else {
                        m475a(view, i);
                    }
                }
            } else if (i == 4 || i == 3 || (this.f264c && i == 5)) {
                this.f265d = i;
            }
        }
    }

    /* renamed from: b */
    public void m484b(boolean z) {
        this.f276o = z;
    }

    /* renamed from: b */
    public boolean mo64b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int a = C0659t.m3205a(motionEvent);
        if (this.f265d == 1 && a == 0) {
            return true;
        }
        this.f266e.m3351b(motionEvent);
        if (a == 0) {
            m467a();
        }
        if (this.f281t == null) {
            this.f281t = VelocityTracker.obtain();
        }
        this.f281t.addMovement(motionEvent);
        if (a == 2 && !this.f277p && Math.abs(((float) this.f282u) - motionEvent.getY()) > ((float) this.f266e.m3342a())) {
            this.f266e.m3343a((View) v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.f277p;
    }

    /* renamed from: c */
    void m486c(int i) {
        if (this.f265d != i) {
            this.f265d = i;
            View view = (View) this.f268g.get();
            if (view != null && this.f280s != null) {
                this.f280s.mo132a(view, i);
            }
        }
    }

    /* renamed from: d */
    void m487d(int i) {
        View view = (View) this.f268g.get();
        if (view != null && this.f280s != null) {
            if (i > this.f263b) {
                this.f280s.mo131a(view, ((float) (this.f263b - i)) / ((float) (this.f267f - this.f263b)));
            } else {
                this.f280s.mo131a(view, ((float) (this.f263b - i)) / ((float) (this.f263b - this.f262a)));
            }
        }
    }
}
