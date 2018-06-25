package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.design.C0073a.C0063b;
import android.support.design.C0073a.C0071j;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.C0201w.C0083c;
import android.support.design.widget.CoordinatorLayout.C0088a;
import android.support.design.widget.CoordinatorLayout.C0102b;
import android.support.design.widget.CoordinatorLayout.C0104d;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.C0081z;
import android.support.v4.view.ah;
import android.support.v4.view.be;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.List;

@C0102b(a = Behavior.class)
public class AppBarLayout extends LinearLayout {
    /* renamed from: a */
    private int f244a;
    /* renamed from: b */
    private int f245b;
    /* renamed from: c */
    private int f246c;
    /* renamed from: d */
    private boolean f247d;
    /* renamed from: e */
    private int f248e;
    /* renamed from: f */
    private be f249f;
    /* renamed from: g */
    private List<C0092b> f250g;
    /* renamed from: h */
    private boolean f251h;
    /* renamed from: i */
    private boolean f252i;
    /* renamed from: j */
    private final int[] f253j;

    /* renamed from: android.support.design.widget.AppBarLayout$1 */
    class C00821 implements C0081z {
        /* renamed from: a */
        final /* synthetic */ AppBarLayout f212a;

        C00821(AppBarLayout appBarLayout) {
            this.f212a = appBarLayout;
        }

        /* renamed from: a */
        public be mo57a(View view, be beVar) {
            return this.f212a.m435a(beVar);
        }
    }

    public static class Behavior extends C0089l<AppBarLayout> {
        /* renamed from: b */
        private int f229b;
        /* renamed from: c */
        private boolean f230c;
        /* renamed from: d */
        private boolean f231d;
        /* renamed from: e */
        private C0201w f232e;
        /* renamed from: f */
        private int f233f = -1;
        /* renamed from: g */
        private boolean f234g;
        /* renamed from: h */
        private float f235h;
        /* renamed from: i */
        private WeakReference<View> f236i;
        /* renamed from: j */
        private C0087a f237j;

        protected static class SavedState extends AbsSavedState {
            public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C00861());
            /* renamed from: a */
            int f216a;
            /* renamed from: b */
            float f217b;
            /* renamed from: c */
            boolean f218c;

            /* renamed from: android.support.design.widget.AppBarLayout$Behavior$SavedState$1 */
            static class C00861 implements C0085g<SavedState> {
                C00861() {
                }

                /* renamed from: a */
                public SavedState m318a(Parcel parcel, ClassLoader classLoader) {
                    return new SavedState(parcel, classLoader);
                }

                /* renamed from: a */
                public SavedState[] m319a(int i) {
                    return new SavedState[i];
                }

                public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return m318a(parcel, classLoader);
                }

                public /* synthetic */ Object[] newArray(int i) {
                    return m319a(i);
                }
            }

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.f216a = parcel.readInt();
                this.f217b = parcel.readFloat();
                this.f218c = parcel.readByte() != (byte) 0;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.f216a);
                parcel.writeFloat(this.f217b);
                parcel.writeByte((byte) (this.f218c ? 1 : 0));
            }
        }

        /* renamed from: android.support.design.widget.AppBarLayout$Behavior$a */
        public static abstract class C0087a {
            /* renamed from: a */
            public abstract boolean m320a(AppBarLayout appBarLayout);
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* renamed from: a */
        private int m361a(AppBarLayout appBarLayout, int i) {
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                if (childAt.getTop() <= (-i) && childAt.getBottom() >= (-i)) {
                    return i2;
                }
            }
            return -1;
        }

        /* renamed from: a */
        private void m362a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, float f) {
            int abs = Math.abs(mo65a() - i);
            float abs2 = Math.abs(f);
            m363a(coordinatorLayout, appBarLayout, i, abs2 > BitmapDescriptorFactory.HUE_RED ? Math.round((((float) abs) / abs2) * 1000.0f) * 3 : (int) (((((float) abs) / ((float) appBarLayout.getHeight())) + 1.0f) * 150.0f));
        }

        /* renamed from: a */
        private void m363a(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i, int i2) {
            int a = mo65a();
            if (a != i) {
                if (this.f232e == null) {
                    this.f232e = ad.m659a();
                    this.f232e.m945a(C0126a.f430e);
                    this.f232e.m944a(new C0083c(this) {
                        /* renamed from: c */
                        final /* synthetic */ Behavior f215c;

                        /* renamed from: a */
                        public void mo58a(C0201w c0201w) {
                            this.f215c.a_(coordinatorLayout, appBarLayout, c0201w.m947c());
                        }
                    });
                } else {
                    this.f232e.m949e();
                }
                this.f232e.m942a((long) Math.min(i2, 600));
                this.f232e.m941a(a, i);
                this.f232e.m939a();
            } else if (this.f232e != null && this.f232e.m946b()) {
                this.f232e.m949e();
            }
        }

        /* renamed from: a */
        private void m364a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, boolean z) {
            boolean z2 = true;
            boolean z3 = false;
            View c = m367c(appBarLayout, i);
            if (c != null) {
                int a = ((C0091a) c.getLayoutParams()).m424a();
                if ((a & 1) != 0) {
                    int p = ah.m2826p(c);
                    if (i2 > 0 && (a & 12) != 0) {
                        z3 = (-i) >= (c.getBottom() - p) - appBarLayout.getTopInset();
                    } else if ((a & 2) != 0) {
                        if ((-i) < (c.getBottom() - p) - appBarLayout.getTopInset()) {
                            z2 = false;
                        }
                        z3 = z2;
                    }
                }
                boolean a2 = appBarLayout.m438a(z3);
                if (VERSION.SDK_INT < 11) {
                    return;
                }
                if (z || (a2 && m369d(coordinatorLayout, appBarLayout))) {
                    appBarLayout.jumpDrawablesToCurrentState();
                }
            }
        }

        /* renamed from: a */
        private static boolean m365a(int i, int i2) {
            return (i & i2) == i2;
        }

        /* renamed from: b */
        private int m366b(AppBarLayout appBarLayout, int i) {
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            int i2 = 0;
            while (i2 < childCount) {
                View childAt = appBarLayout.getChildAt(i2);
                C0091a c0091a = (C0091a) childAt.getLayoutParams();
                Interpolator b = c0091a.m425b();
                if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                    i2++;
                } else if (b == null) {
                    return i;
                } else {
                    int height;
                    i2 = c0091a.m424a();
                    if ((i2 & 1) != 0) {
                        height = (c0091a.bottomMargin + (childAt.getHeight() + c0091a.topMargin)) + 0;
                        if ((i2 & 2) != 0) {
                            height -= ah.m2826p(childAt);
                        }
                    } else {
                        height = 0;
                    }
                    if (ah.m2835y(childAt)) {
                        height -= appBarLayout.getTopInset();
                    }
                    if (height <= 0) {
                        return i;
                    }
                    return Integer.signum(i) * (Math.round(b.getInterpolation(((float) (abs - childAt.getTop())) / ((float) height)) * ((float) height)) + childAt.getTop());
                }
            }
            return i;
        }

        /* renamed from: c */
        private static View m367c(AppBarLayout appBarLayout, int i) {
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                if (abs >= childAt.getTop() && abs <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        /* renamed from: c */
        private void m368c(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int a = mo65a();
            int a2 = m361a(appBarLayout, a);
            if (a2 >= 0) {
                View childAt = appBarLayout.getChildAt(a2);
                int a3 = ((C0091a) childAt.getLayoutParams()).m424a();
                if ((a3 & 17) == 17) {
                    int i = -childAt.getTop();
                    int i2 = -childAt.getBottom();
                    if (a2 == appBarLayout.getChildCount() - 1) {
                        i2 += appBarLayout.getTopInset();
                    }
                    if (m365a(a3, 2)) {
                        i2 += ah.m2826p(childAt);
                        a2 = i;
                    } else if (m365a(a3, 5)) {
                        a2 = ah.m2826p(childAt) + i2;
                        if (a >= a2) {
                            i2 = a2;
                            a2 = i;
                        }
                    } else {
                        a2 = i;
                    }
                    if (a >= (i2 + a2) / 2) {
                        i2 = a2;
                    }
                    m362a(coordinatorLayout, appBarLayout, C0168n.m809a(i2, -appBarLayout.getTotalScrollRange(), 0), (float) BitmapDescriptorFactory.HUE_RED);
                }
            }
        }

        /* renamed from: d */
        private boolean m369d(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            List d = coordinatorLayout.m555d((View) appBarLayout);
            int size = d.size();
            int i = 0;
            while (i < size) {
                C0088a b = ((C0104d) ((View) d.get(i)).getLayoutParams()).m501b();
                if (b instanceof ScrollingViewBehavior) {
                    return ((ScrollingViewBehavior) b).m410d() != 0;
                } else {
                    i++;
                }
            }
            return false;
        }

        /* renamed from: a */
        int mo65a() {
            return mo77b() + this.f229b;
        }

        /* renamed from: a */
        int m371a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
            int a = mo65a();
            if (i2 == 0 || a < i2 || a > i3) {
                this.f229b = 0;
                return 0;
            }
            int a2 = C0168n.m809a(i, i2, i3);
            if (a == a2) {
                return 0;
            }
            int b = appBarLayout.m439b() ? m366b(appBarLayout, a2) : a2;
            boolean a3 = mo73a(b);
            int i4 = a - a2;
            this.f229b = a2 - b;
            if (!a3 && appBarLayout.m439b()) {
                coordinatorLayout.m548b((View) appBarLayout);
            }
            appBarLayout.m436a(mo77b());
            m364a(coordinatorLayout, appBarLayout, a2, a2 < a ? -1 : 1, false);
            return i4;
        }

        /* renamed from: a */
        /* synthetic */ int mo67a(View view) {
            return m399c((AppBarLayout) view);
        }

        /* renamed from: a */
        void m374a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            m368c(coordinatorLayout, appBarLayout);
        }

        /* renamed from: a */
        public void m375a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                SavedState savedState = (SavedState) parcelable;
                super.mo69a(coordinatorLayout, (View) appBarLayout, savedState.getSuperState());
                this.f233f = savedState.f216a;
                this.f235h = savedState.f217b;
                this.f234g = savedState.f218c;
                return;
            }
            super.mo69a(coordinatorLayout, (View) appBarLayout, parcelable);
            this.f233f = -1;
        }

        /* renamed from: a */
        public void m376a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view) {
            if (!this.f231d) {
                m368c(coordinatorLayout, appBarLayout);
            }
            this.f230c = false;
            this.f231d = false;
            this.f236i = new WeakReference(view);
        }

        /* renamed from: a */
        public void m377a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4) {
            if (i4 < 0) {
                m356b(coordinatorLayout, appBarLayout, i4, -appBarLayout.getDownNestedScrollRange(), 0);
                this.f230c = true;
                return;
            }
            this.f230c = false;
        }

        /* renamed from: a */
        public void m378a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr) {
            if (i2 != 0 && !this.f230c) {
                int i3;
                int downNestedPreScrollRange;
                if (i2 < 0) {
                    i3 = -appBarLayout.getTotalScrollRange();
                    downNestedPreScrollRange = i3 + appBarLayout.getDownNestedPreScrollRange();
                } else {
                    i3 = -appBarLayout.getUpNestedPreScrollRange();
                    downNestedPreScrollRange = 0;
                }
                iArr[1] = m356b(coordinatorLayout, appBarLayout, i2, i3, downNestedPreScrollRange);
            }
        }

        /* renamed from: a */
        boolean m385a(AppBarLayout appBarLayout) {
            if (this.f237j != null) {
                return this.f237j.m320a(appBarLayout);
            }
            if (this.f236i == null) {
                return true;
            }
            View view = (View) this.f236i.get();
            return (view == null || !view.isShown() || ah.m2798b(view, -1)) ? false : true;
        }

        /* renamed from: a */
        public boolean m386a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            boolean a = super.mo62a(coordinatorLayout, appBarLayout, i);
            int pendingAction = appBarLayout.getPendingAction();
            if (this.f233f >= 0 && (pendingAction & 8) == 0) {
                View childAt = appBarLayout.getChildAt(this.f233f);
                pendingAction = -childAt.getBottom();
                a_(coordinatorLayout, appBarLayout, this.f234g ? (ah.m2826p(childAt) + appBarLayout.getTopInset()) + pendingAction : Math.round(((float) childAt.getHeight()) * this.f235h) + pendingAction);
            } else if (pendingAction != 0) {
                boolean z = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    pendingAction = -appBarLayout.getUpNestedPreScrollRange();
                    if (z) {
                        m362a(coordinatorLayout, appBarLayout, pendingAction, (float) BitmapDescriptorFactory.HUE_RED);
                    } else {
                        a_(coordinatorLayout, appBarLayout, pendingAction);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z) {
                        m362a(coordinatorLayout, appBarLayout, 0, (float) BitmapDescriptorFactory.HUE_RED);
                    } else {
                        a_(coordinatorLayout, appBarLayout, 0);
                    }
                }
            }
            appBarLayout.m441d();
            this.f233f = -1;
            mo73a(C0168n.m809a(mo77b(), -appBarLayout.getTotalScrollRange(), 0));
            m364a(coordinatorLayout, appBarLayout, mo77b(), 0, true);
            appBarLayout.m436a(mo77b());
            return a;
        }

        /* renamed from: a */
        public boolean m387a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            if (((C0104d) appBarLayout.getLayoutParams()).height != -2) {
                return super.mo74a(coordinatorLayout, (View) appBarLayout, i, i2, i3, i4);
            }
            coordinatorLayout.m542a(appBarLayout, i, i2, MeasureSpec.makeMeasureSpec(0, 0), i4);
            return true;
        }

        /* renamed from: a */
        public boolean m388a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f, float f2, boolean z) {
            boolean z2 = false;
            if (!z) {
                z2 = m354a(coordinatorLayout, (View) appBarLayout, -appBarLayout.getTotalScrollRange(), 0, -f2);
            } else if (f2 < BitmapDescriptorFactory.HUE_RED) {
                r1 = (-appBarLayout.getTotalScrollRange()) + appBarLayout.getDownNestedPreScrollRange();
                if (mo65a() < r1) {
                    m362a(coordinatorLayout, appBarLayout, r1, f2);
                    z2 = true;
                }
            } else {
                r1 = -appBarLayout.getUpNestedPreScrollRange();
                if (mo65a() > r1) {
                    m362a(coordinatorLayout, appBarLayout, r1, f2);
                    z2 = true;
                }
            }
            this.f231d = z2;
            return z2;
        }

        /* renamed from: a */
        public boolean m389a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i) {
            boolean z = (i & 2) != 0 && appBarLayout.m440c() && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight();
            if (z && this.f232e != null) {
                this.f232e.m949e();
            }
            this.f236i = null;
            return z;
        }

        /* renamed from: b */
        public /* bridge */ /* synthetic */ int mo77b() {
            return super.mo77b();
        }

        /* renamed from: b */
        int m395b(AppBarLayout appBarLayout) {
            return -appBarLayout.getDownNestedScrollRange();
        }

        /* renamed from: b */
        public Parcelable m397b(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            boolean z = false;
            Parcelable b = super.mo79b(coordinatorLayout, appBarLayout);
            int b2 = mo77b();
            int childCount = appBarLayout.getChildCount();
            int i = 0;
            while (i < childCount) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + b2;
                if (childAt.getTop() + b2 > 0 || bottom < 0) {
                    i++;
                } else {
                    SavedState savedState = new SavedState(b);
                    savedState.f216a = i;
                    if (bottom == ah.m2826p(childAt) + appBarLayout.getTopInset()) {
                        z = true;
                    }
                    savedState.f218c = z;
                    savedState.f217b = ((float) bottom) / ((float) childAt.getHeight());
                    return savedState;
                }
            }
            return b;
        }

        /* renamed from: c */
        int m399c(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        /* renamed from: c */
        /* synthetic */ boolean mo80c(View view) {
            return m385a((AppBarLayout) view);
        }
    }

    public static class ScrollingViewBehavior extends C0090m {
        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.ScrollingViewBehavior_Layout);
            m407b(obtainStyledAttributes.getDimensionPixelSize(C0072k.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            obtainStyledAttributes.recycle();
        }

        /* renamed from: a */
        private static int m411a(AppBarLayout appBarLayout) {
            C0088a b = ((C0104d) appBarLayout.getLayoutParams()).m501b();
            return b instanceof Behavior ? ((Behavior) b).mo65a() : 0;
        }

        /* renamed from: e */
        private void m412e(CoordinatorLayout coordinatorLayout, View view, View view2) {
            C0088a b = ((C0104d) view2.getLayoutParams()).m501b();
            if (b instanceof Behavior) {
                int bottom = view2.getBottom() - view.getTop();
                ah.m2808e(view, ((((Behavior) b).f229b + bottom) + m403a()) - m409c(view2));
            }
        }

        /* renamed from: a */
        float mo82a(View view) {
            if (!(view instanceof AppBarLayout)) {
                return BitmapDescriptorFactory.HUE_RED;
            }
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
            int a = m411a(appBarLayout);
            if (downNestedPreScrollRange != 0 && totalScrollRange + a <= downNestedPreScrollRange) {
                return BitmapDescriptorFactory.HUE_RED;
            }
            totalScrollRange -= downNestedPreScrollRange;
            return totalScrollRange != 0 ? 1.0f + (((float) a) / ((float) totalScrollRange)) : BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: a */
        AppBarLayout m414a(List<View> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = (View) list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        /* renamed from: a */
        public /* bridge */ /* synthetic */ boolean mo62a(CoordinatorLayout coordinatorLayout, View view, int i) {
            return super.mo62a(coordinatorLayout, view, i);
        }

        /* renamed from: a */
        public /* bridge */ /* synthetic */ boolean mo74a(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return super.mo74a(coordinatorLayout, view, i, i2, i3, i4);
        }

        /* renamed from: a */
        public boolean mo83a(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout a = m414a(coordinatorLayout.m551c(view));
            if (a != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.a;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    a.m437a(false, !z);
                    return true;
                }
            }
            return false;
        }

        /* renamed from: b */
        public /* bridge */ /* synthetic */ int mo77b() {
            return super.mo77b();
        }

        /* renamed from: b */
        int mo84b(View view) {
            return view instanceof AppBarLayout ? ((AppBarLayout) view).getTotalScrollRange() : super.mo84b(view);
        }

        /* renamed from: b */
        /* synthetic */ View mo85b(List list) {
            return m414a(list);
        }

        /* renamed from: b */
        public boolean mo86b(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        /* renamed from: c */
        public boolean mo87c(CoordinatorLayout coordinatorLayout, View view, View view2) {
            m412e(coordinatorLayout, view, view2);
            return false;
        }
    }

    /* renamed from: android.support.design.widget.AppBarLayout$a */
    public static class C0091a extends LayoutParams {
        /* renamed from: a */
        int f242a = 1;
        /* renamed from: b */
        Interpolator f243b;

        public C0091a(int i, int i2) {
            super(i, i2);
        }

        public C0091a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.AppBarLayout_Layout);
            this.f242a = obtainStyledAttributes.getInt(C0072k.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (obtainStyledAttributes.hasValue(C0072k.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.f243b = AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(C0072k.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public C0091a(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public C0091a(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @TargetApi(19)
        public C0091a(LayoutParams layoutParams) {
            super(layoutParams);
        }

        /* renamed from: a */
        public int m424a() {
            return this.f242a;
        }

        /* renamed from: b */
        public Interpolator m425b() {
            return this.f243b;
        }

        /* renamed from: c */
        boolean m426c() {
            return (this.f242a & 1) == 1 && (this.f242a & 10) != 0;
        }
    }

    /* renamed from: android.support.design.widget.AppBarLayout$b */
    public interface C0092b {
        /* renamed from: a */
        void m427a(AppBarLayout appBarLayout, int i);
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f244a = -1;
        this.f245b = -1;
        this.f246c = -1;
        this.f248e = 0;
        this.f253j = new int[2];
        setOrientation(1);
        C0195v.m916a(context);
        if (VERSION.SDK_INT >= 21) {
            ae.m661a(this);
            ae.m663a(this, attributeSet, 0, C0071j.Widget_Design_AppBarLayout);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.AppBarLayout, 0, C0071j.Widget_Design_AppBarLayout);
        ah.m2781a((View) this, obtainStyledAttributes.getDrawable(C0072k.AppBarLayout_android_background));
        if (obtainStyledAttributes.hasValue(C0072k.AppBarLayout_expanded)) {
            m428a(obtainStyledAttributes.getBoolean(C0072k.AppBarLayout_expanded, false), false, false);
        }
        if (VERSION.SDK_INT >= 21 && obtainStyledAttributes.hasValue(C0072k.AppBarLayout_elevation)) {
            ae.m662a(this, (float) obtainStyledAttributes.getDimensionPixelSize(C0072k.AppBarLayout_elevation, 0));
        }
        obtainStyledAttributes.recycle();
        ah.m2785a((View) this, new C00821(this));
    }

    /* renamed from: a */
    private void m428a(boolean z, boolean z2, boolean z3) {
        int i = 0;
        int i2 = (z2 ? 4 : 0) | (z ? 1 : 2);
        if (z3) {
            i = 8;
        }
        this.f248e = i | i2;
        requestLayout();
    }

    /* renamed from: b */
    private boolean m429b(boolean z) {
        if (this.f251h == z) {
            return false;
        }
        this.f251h = z;
        refreshDrawableState();
        return true;
    }

    /* renamed from: e */
    private void m430e() {
        boolean z;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((C0091a) getChildAt(i).getLayoutParams()).m426c()) {
                z = true;
                break;
            }
        }
        z = false;
        m429b(z);
    }

    /* renamed from: f */
    private void m431f() {
        this.f244a = -1;
        this.f245b = -1;
        this.f246c = -1;
    }

    /* renamed from: a */
    protected C0091a m432a() {
        return new C0091a(-1, -2);
    }

    /* renamed from: a */
    public C0091a m433a(AttributeSet attributeSet) {
        return new C0091a(getContext(), attributeSet);
    }

    /* renamed from: a */
    protected C0091a m434a(ViewGroup.LayoutParams layoutParams) {
        return (VERSION.SDK_INT < 19 || !(layoutParams instanceof LayoutParams)) ? layoutParams instanceof MarginLayoutParams ? new C0091a((MarginLayoutParams) layoutParams) : new C0091a(layoutParams) : new C0091a((LayoutParams) layoutParams);
    }

    /* renamed from: a */
    be m435a(be beVar) {
        Object obj = null;
        if (ah.m2835y(this)) {
            obj = beVar;
        }
        if (!ad.m660a(this.f249f, obj)) {
            this.f249f = obj;
            m431f();
        }
        return beVar;
    }

    /* renamed from: a */
    void m436a(int i) {
        if (this.f250g != null) {
            int size = this.f250g.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0092b c0092b = (C0092b) this.f250g.get(i2);
                if (c0092b != null) {
                    c0092b.m427a(this, i);
                }
            }
        }
    }

    /* renamed from: a */
    public void m437a(boolean z, boolean z2) {
        m428a(z, z2, true);
    }

    /* renamed from: a */
    boolean m438a(boolean z) {
        if (this.f252i == z) {
            return false;
        }
        this.f252i = z;
        refreshDrawableState();
        return true;
    }

    /* renamed from: b */
    boolean m439b() {
        return this.f247d;
    }

    /* renamed from: c */
    boolean m440c() {
        return getTotalScrollRange() != 0;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0091a;
    }

    /* renamed from: d */
    void m441d() {
        this.f248e = 0;
    }

    protected /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return m432a();
    }

    /* renamed from: generateDefaultLayoutParams */
    protected /* synthetic */ LayoutParams m14408generateDefaultLayoutParams() {
        return m432a();
    }

    public /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return m433a(attributeSet);
    }

    protected /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return m434a(layoutParams);
    }

    /* renamed from: generateLayoutParams */
    public /* synthetic */ LayoutParams m14409generateLayoutParams(AttributeSet attributeSet) {
        return m433a(attributeSet);
    }

    /* renamed from: generateLayoutParams */
    protected /* synthetic */ LayoutParams m14410generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return m434a(layoutParams);
    }

    int getDownNestedPreScrollRange() {
        if (this.f245b != -1) {
            return this.f245b;
        }
        int i;
        int childCount = getChildCount() - 1;
        int i2 = 0;
        while (childCount >= 0) {
            View childAt = getChildAt(childCount);
            C0091a c0091a = (C0091a) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i3 = c0091a.f242a;
            if ((i3 & 5) == 5) {
                i = (c0091a.bottomMargin + c0091a.topMargin) + i2;
                i = (i3 & 8) != 0 ? i + ah.m2826p(childAt) : (i3 & 2) != 0 ? i + (measuredHeight - ah.m2826p(childAt)) : i + (measuredHeight - getTopInset());
            } else if (i2 > 0) {
                break;
            } else {
                i = i2;
            }
            childCount--;
            i2 = i;
        }
        i = Math.max(0, i2);
        this.f245b = i;
        return i;
    }

    int getDownNestedScrollRange() {
        if (this.f246c != -1) {
            return this.f246c;
        }
        int i;
        int childCount = getChildCount();
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            C0091a c0091a = (C0091a) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + (c0091a.topMargin + c0091a.bottomMargin);
            i = c0091a.f242a;
            if ((i & 1) == 0) {
                break;
            }
            i2 += measuredHeight;
            if ((i & 2) != 0) {
                i = i2 - (ah.m2826p(childAt) + getTopInset());
                break;
            }
        }
        i = i2;
        i = Math.max(0, i);
        this.f246c = i;
        return i;
    }

    final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int p = ah.m2826p(this);
        if (p != 0) {
            return (p * 2) + topInset;
        }
        p = getChildCount();
        p = p >= 1 ? ah.m2826p(getChildAt(p - 1)) : 0;
        return p != 0 ? (p * 2) + topInset : getHeight() / 3;
    }

    int getPendingAction() {
        return this.f248e;
    }

    @Deprecated
    public float getTargetElevation() {
        return BitmapDescriptorFactory.HUE_RED;
    }

    final int getTopInset() {
        return this.f249f != null ? this.f249f.m3078b() : 0;
    }

    public final int getTotalScrollRange() {
        if (this.f244a != -1) {
            return this.f244a;
        }
        int p;
        int childCount = getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            C0091a c0091a = (C0091a) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i3 = c0091a.f242a;
            if ((i3 & 1) == 0) {
                break;
            }
            i += c0091a.bottomMargin + (measuredHeight + c0091a.topMargin);
            if ((i3 & 2) != 0) {
                p = i - ah.m2826p(childAt);
                break;
            }
        }
        p = i;
        p = Math.max(0, p - getTopInset());
        this.f244a = p;
        return p;
    }

    int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    protected int[] onCreateDrawableState(int i) {
        int[] iArr = this.f253j;
        int[] onCreateDrawableState = super.onCreateDrawableState(iArr.length + i);
        iArr[0] = this.f251h ? C0063b.state_collapsible : -C0063b.state_collapsible;
        int i2 = (this.f251h && this.f252i) ? C0063b.state_collapsed : -C0063b.state_collapsed;
        iArr[1] = i2;
        return mergeDrawableStates(onCreateDrawableState, iArr);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        m431f();
        this.f247d = false;
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            if (((C0091a) getChildAt(i5).getLayoutParams()).m425b() != null) {
                this.f247d = true;
                break;
            }
        }
        m430e();
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        m431f();
    }

    public void setExpanded(boolean z) {
        m437a(z, ah.m2767G(this));
    }

    public void setOrientation(int i) {
        if (i != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i);
    }

    @Deprecated
    public void setTargetElevation(float f) {
        if (VERSION.SDK_INT >= 21) {
            ae.m662a(this, f);
        }
    }
}
