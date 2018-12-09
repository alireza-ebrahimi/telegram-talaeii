package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.p014d.C0440j;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.C0078v;
import android.support.v4.view.C0659t;
import android.support.v4.view.C0661w;
import android.support.v4.view.ad;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.support.v4.view.p023a.C0510a;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0531e.C0529l;
import android.support.v4.view.p023a.C0531e.C0530m;
import android.support.v4.view.p023a.C0556o;
import android.support.v4.widget.C0700i;
import android.support.v4.widget.C0729x;
import android.support.v7.p029e.C0839a.C0838c;
import android.support.v7.widget.C1060f.C0923a;
import android.support.v7.widget.C1060f.C1059b;
import android.support.v7.widget.ae.C0921b;
import android.support.v7.widget.am.C1015a;
import android.support.v7.widget.bn.C0936b;
import android.support.v7.widget.bo.C0919b;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public class RecyclerView extends ViewGroup implements ad, C0078v {
    /* renamed from: G */
    static final Interpolator f159G = new C09183();
    /* renamed from: H */
    private static final int[] f160H = new int[]{16843830};
    /* renamed from: I */
    private static final int[] f161I = new int[]{16842987};
    /* renamed from: J */
    private static final boolean f162J = (VERSION.SDK_INT >= 21);
    /* renamed from: K */
    private static final boolean f163K = (VERSION.SDK_INT <= 15);
    /* renamed from: L */
    private static final boolean f164L = (VERSION.SDK_INT <= 15);
    /* renamed from: M */
    private static final Class<?>[] f165M = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
    /* renamed from: a */
    static final boolean f166a;
    /* renamed from: b */
    static final boolean f167b = (VERSION.SDK_INT >= 23);
    /* renamed from: c */
    static final boolean f168c = (VERSION.SDK_INT >= 16);
    /* renamed from: A */
    final C0952s f169A;
    /* renamed from: B */
    boolean f170B;
    /* renamed from: C */
    boolean f171C;
    /* renamed from: D */
    boolean f172D;
    /* renamed from: E */
    aw f173E;
    /* renamed from: F */
    final List<C0955v> f174F;
    /* renamed from: N */
    private final C0949q f175N;
    /* renamed from: O */
    private SavedState f176O;
    /* renamed from: P */
    private final Rect f177P;
    /* renamed from: Q */
    private final ArrayList<C0943l> f178Q;
    /* renamed from: R */
    private C0943l f179R;
    /* renamed from: S */
    private int f180S;
    /* renamed from: T */
    private boolean f181T;
    /* renamed from: U */
    private int f182U;
    /* renamed from: V */
    private final AccessibilityManager f183V;
    /* renamed from: W */
    private List<C0941j> f184W;
    private final int[] aA;
    private final int[] aB;
    private Runnable aC;
    private final C0919b aD;
    private int aa;
    private int ab;
    private C0700i ac;
    private C0700i ad;
    private C0700i ae;
    private C0700i af;
    private int ag;
    private int ah;
    private VelocityTracker ai;
    private int aj;
    private int ak;
    private int al;
    private int am;
    private int an;
    private C0942k ao;
    private final int ap;
    private final int aq;
    private float ar;
    private boolean as;
    private C0944m at;
    private List<C0944m> au;
    private C0931b av;
    private C0929d aw;
    private final int[] ax;
    private C0661w ay;
    private final int[] az;
    /* renamed from: d */
    final C0947o f185d;
    /* renamed from: e */
    C1060f f186e;
    /* renamed from: f */
    ae f187f;
    /* renamed from: g */
    final bo f188g;
    /* renamed from: h */
    boolean f189h;
    /* renamed from: i */
    final Runnable f190i;
    /* renamed from: j */
    final Rect f191j;
    /* renamed from: k */
    final RectF f192k;
    /* renamed from: l */
    C0926a f193l;
    /* renamed from: m */
    C0910h f194m;
    /* renamed from: n */
    C0948p f195n;
    /* renamed from: o */
    final ArrayList<C0935g> f196o;
    /* renamed from: p */
    boolean f197p;
    /* renamed from: q */
    boolean f198q;
    /* renamed from: r */
    boolean f199r;
    /* renamed from: s */
    boolean f200s;
    /* renamed from: t */
    boolean f201t;
    /* renamed from: u */
    boolean f202u;
    /* renamed from: v */
    boolean f203v;
    /* renamed from: w */
    C0933e f204w;
    /* renamed from: x */
    final C0954u f205x;
    /* renamed from: y */
    am f206y;
    /* renamed from: z */
    C1015a f207z;

    /* renamed from: android.support.v7.widget.RecyclerView$i */
    public static class C0908i extends MarginLayoutParams {
        /* renamed from: c */
        C0955v f2403c;
        /* renamed from: d */
        final Rect f2404d = new Rect();
        /* renamed from: e */
        boolean f2405e = true;
        /* renamed from: f */
        boolean f2406f = false;

        public C0908i(int i, int i2) {
            super(i, i2);
        }

        public C0908i(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public C0908i(C0908i c0908i) {
            super(c0908i);
        }

        public C0908i(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public C0908i(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        /* renamed from: c */
        public boolean m4473c() {
            return this.f2403c.isInvalid();
        }

        /* renamed from: d */
        public boolean m4474d() {
            return this.f2403c.isRemoved();
        }

        /* renamed from: e */
        public boolean m4475e() {
            return this.f2403c.isUpdated();
        }

        /* renamed from: f */
        public int m4476f() {
            return this.f2403c.getLayoutPosition();
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$h */
    public static abstract class C0910h {
        /* renamed from: a */
        private final C0936b f2409a = new C09371(this);
        /* renamed from: b */
        private final C0936b f2410b = new C09382(this);
        /* renamed from: c */
        private boolean f2411c = true;
        /* renamed from: d */
        private boolean f2412d = true;
        /* renamed from: e */
        private int f2413e;
        /* renamed from: f */
        private int f2414f;
        /* renamed from: g */
        private int f2415g;
        /* renamed from: h */
        private int f2416h;
        /* renamed from: p */
        ae f2417p;
        /* renamed from: q */
        RecyclerView f2418q;
        /* renamed from: r */
        bn f2419r = new bn(this.f2409a);
        /* renamed from: s */
        bn f2420s = new bn(this.f2410b);
        /* renamed from: t */
        C0951r f2421t;
        /* renamed from: u */
        boolean f2422u = false;
        /* renamed from: v */
        boolean f2423v = false;
        /* renamed from: w */
        boolean f2424w = false;
        /* renamed from: x */
        int f2425x;
        /* renamed from: y */
        boolean f2426y;

        /* renamed from: android.support.v7.widget.RecyclerView$h$1 */
        class C09371 implements C0936b {
            /* renamed from: a */
            final /* synthetic */ C0910h f2491a;

            C09371(C0910h c0910h) {
                this.f2491a = c0910h;
            }

            /* renamed from: a */
            public int mo870a() {
                return this.f2491a.m4491B();
            }

            /* renamed from: a */
            public int mo871a(View view) {
                return this.f2491a.m4595h(view) - ((C0908i) view.getLayoutParams()).leftMargin;
            }

            /* renamed from: a */
            public View mo872a(int i) {
                return this.f2491a.m4596h(i);
            }

            /* renamed from: b */
            public int mo873b() {
                return this.f2491a.m4618z() - this.f2491a.m4493D();
            }

            /* renamed from: b */
            public int mo874b(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.rightMargin + this.f2491a.m4599j(view);
            }
        }

        /* renamed from: android.support.v7.widget.RecyclerView$h$2 */
        class C09382 implements C0936b {
            /* renamed from: a */
            final /* synthetic */ C0910h f2492a;

            C09382(C0910h c0910h) {
                this.f2492a = c0910h;
            }

            /* renamed from: a */
            public int mo870a() {
                return this.f2492a.m4492C();
            }

            /* renamed from: a */
            public int mo871a(View view) {
                return this.f2492a.m4597i(view) - ((C0908i) view.getLayoutParams()).topMargin;
            }

            /* renamed from: a */
            public View mo872a(int i) {
                return this.f2492a.m4596h(i);
            }

            /* renamed from: b */
            public int mo873b() {
                return this.f2492a.m4490A() - this.f2492a.m4494E();
            }

            /* renamed from: b */
            public int mo874b(View view) {
                C0908i c0908i = (C0908i) view.getLayoutParams();
                return c0908i.bottomMargin + this.f2492a.m4601k(view);
            }
        }

        /* renamed from: android.support.v7.widget.RecyclerView$h$a */
        public interface C0939a {
            /* renamed from: b */
            void mo932b(int i, int i2);
        }

        /* renamed from: android.support.v7.widget.RecyclerView$h$b */
        public static class C0940b {
            /* renamed from: a */
            public int f2493a;
            /* renamed from: b */
            public int f2494b;
            /* renamed from: c */
            public boolean f2495c;
            /* renamed from: d */
            public boolean f2496d;
        }

        /* renamed from: a */
        public static int m4479a(int i, int i2, int i3) {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            switch (mode) {
                case Integer.MIN_VALUE:
                    return Math.min(size, Math.max(i2, i3));
                case 1073741824:
                    return size;
                default:
                    return Math.max(i2, i3);
            }
        }

        /* renamed from: a */
        public static int m4480a(int i, int i2, int i3, int i4, boolean z) {
            int i5 = 0;
            int max = Math.max(0, i - i3);
            if (z) {
                if (i4 >= 0) {
                    i5 = 1073741824;
                    max = i4;
                } else if (i4 == -1) {
                    switch (i2) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            i5 = max;
                            break;
                        case 0:
                            i2 = 0;
                            break;
                        default:
                            i2 = 0;
                            break;
                    }
                    max = i5;
                    i5 = i2;
                } else {
                    if (i4 == -2) {
                        max = 0;
                    }
                    max = 0;
                }
            } else if (i4 >= 0) {
                i5 = 1073741824;
                max = i4;
            } else if (i4 == -1) {
                i5 = i2;
            } else {
                if (i4 == -2) {
                    if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                        i5 = Integer.MIN_VALUE;
                    }
                }
                max = 0;
            }
            return MeasureSpec.makeMeasureSpec(max, i5);
        }

        /* renamed from: a */
        public static C0940b m4481a(Context context, AttributeSet attributeSet, int i, int i2) {
            C0940b c0940b = new C0940b();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0838c.RecyclerView, i, i2);
            c0940b.f2493a = obtainStyledAttributes.getInt(C0838c.RecyclerView_android_orientation, 1);
            c0940b.f2494b = obtainStyledAttributes.getInt(C0838c.RecyclerView_spanCount, 1);
            c0940b.f2495c = obtainStyledAttributes.getBoolean(C0838c.RecyclerView_reverseLayout, false);
            c0940b.f2496d = obtainStyledAttributes.getBoolean(C0838c.RecyclerView_stackFromEnd, false);
            obtainStyledAttributes.recycle();
            return c0940b;
        }

        /* renamed from: a */
        private void m4482a(int i, View view) {
            this.f2417p.m5352e(i);
        }

        /* renamed from: a */
        private void m4484a(C0947o c0947o, int i, View view) {
            C0955v e = RecyclerView.m222e(view);
            if (!e.shouldIgnore()) {
                if (!e.isInvalid() || e.isRemoved() || this.f2418q.f193l.hasStableIds()) {
                    m4592g(i);
                    c0947o.m4917c(view);
                    this.f2418q.f188g.m5744h(e);
                    return;
                }
                m4587f(i);
                c0947o.m4911b(e);
            }
        }

        /* renamed from: a */
        private void m4485a(C0951r c0951r) {
            if (this.f2421t == c0951r) {
                this.f2421t = null;
            }
        }

        /* renamed from: a */
        private void m4486a(View view, int i, boolean z) {
            C0955v e = RecyclerView.m222e(view);
            if (z || e.isRemoved()) {
                this.f2418q.f188g.m5741e(e);
            } else {
                this.f2418q.f188g.m5742f(e);
            }
            C0908i c0908i = (C0908i) view.getLayoutParams();
            if (e.wasReturnedFromScrap() || e.isScrap()) {
                if (e.isScrap()) {
                    e.unScrap();
                } else {
                    e.clearReturnedFromScrapFlag();
                }
                this.f2417p.m5341a(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.f2418q) {
                int b = this.f2417p.m5345b(view);
                if (i == -1) {
                    i = this.f2417p.m5344b();
                }
                if (b == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.f2418q.indexOfChild(view));
                } else if (b != i) {
                    this.f2418q.f194m.m4588f(b, i);
                }
            } else {
                this.f2417p.m5342a(view, i, false);
                c0908i.f2405e = true;
                if (this.f2421t != null && this.f2421t.m4949c()) {
                    this.f2421t.m4947b(view);
                }
            }
            if (c0908i.f2406f) {
                e.itemView.invalidate();
                c0908i.f2406f = false;
            }
        }

        /* renamed from: b */
        private static boolean m4487b(int i, int i2, int i3) {
            int mode = MeasureSpec.getMode(i2);
            int size = MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            switch (mode) {
                case Integer.MIN_VALUE:
                    return size >= i;
                case 0:
                    return true;
                case 1073741824:
                    return size == i;
                default:
                    return false;
            }
        }

        /* renamed from: b */
        private int[] m4488b(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            int[] iArr = new int[2];
            int B = m4491B();
            int C = m4492C();
            int z2 = m4618z() - m4493D();
            int A = m4490A() - m4494E();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width = left + rect.width();
            int height = top + rect.height();
            int min = Math.min(0, left - B);
            int min2 = Math.min(0, top - C);
            int max = Math.max(0, width - z2);
            A = Math.max(0, height - A);
            if (m4613u() == 1) {
                if (max == 0) {
                    max = Math.max(min, width - z2);
                }
                min = max;
            } else {
                min = min != 0 ? min : Math.min(left - B, max);
            }
            max = min2 != 0 ? min2 : Math.min(top - C, A);
            iArr[0] = min;
            iArr[1] = max;
            return iArr;
        }

        /* renamed from: d */
        private boolean m4489d(RecyclerView recyclerView, int i, int i2) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int B = m4491B();
            int C = m4492C();
            int z = m4618z() - m4493D();
            int A = m4490A() - m4494E();
            Rect rect = this.f2418q.f191j;
            m4531a(focusedChild, rect);
            return rect.left - i < z && rect.right - i > B && rect.top - i2 < A && rect.bottom - i2 > C;
        }

        /* renamed from: A */
        public int m4490A() {
            return this.f2416h;
        }

        /* renamed from: B */
        public int m4491B() {
            return this.f2418q != null ? this.f2418q.getPaddingLeft() : 0;
        }

        /* renamed from: C */
        public int m4492C() {
            return this.f2418q != null ? this.f2418q.getPaddingTop() : 0;
        }

        /* renamed from: D */
        public int m4493D() {
            return this.f2418q != null ? this.f2418q.getPaddingRight() : 0;
        }

        /* renamed from: E */
        public int m4494E() {
            return this.f2418q != null ? this.f2418q.getPaddingBottom() : 0;
        }

        /* renamed from: F */
        public View m4495F() {
            if (this.f2418q == null) {
                return null;
            }
            View focusedChild = this.f2418q.getFocusedChild();
            return (focusedChild == null || this.f2417p.m5349c(focusedChild)) ? null : focusedChild;
        }

        /* renamed from: G */
        public int m4496G() {
            C0926a adapter = this.f2418q != null ? this.f2418q.getAdapter() : null;
            return adapter != null ? adapter.getItemCount() : 0;
        }

        /* renamed from: H */
        public int m4497H() {
            return ah.m2825o(this.f2418q);
        }

        /* renamed from: I */
        public int m4498I() {
            return ah.m2826p(this.f2418q);
        }

        /* renamed from: J */
        void m4499J() {
            if (this.f2421t != null) {
                this.f2421t.m4943a();
            }
        }

        /* renamed from: K */
        public void m4500K() {
            this.f2422u = true;
        }

        /* renamed from: L */
        boolean m4501L() {
            int w = m4615w();
            for (int i = 0; i < w; i++) {
                LayoutParams layoutParams = m4596h(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }

        /* renamed from: a */
        public int mo802a(int i, C0947o c0947o, C0952s c0952s) {
            return 0;
        }

        /* renamed from: a */
        public int mo829a(C0947o c0947o, C0952s c0952s) {
            return (this.f2418q == null || this.f2418q.f193l == null || !mo823e()) ? 1 : this.f2418q.f193l.getItemCount();
        }

        /* renamed from: a */
        public abstract C0908i mo803a();

        /* renamed from: a */
        public C0908i mo830a(Context context, AttributeSet attributeSet) {
            return new C0908i(context, attributeSet);
        }

        /* renamed from: a */
        public C0908i mo831a(LayoutParams layoutParams) {
            return layoutParams instanceof C0908i ? new C0908i((C0908i) layoutParams) : layoutParams instanceof MarginLayoutParams ? new C0908i((MarginLayoutParams) layoutParams) : new C0908i(layoutParams);
        }

        /* renamed from: a */
        public View mo804a(View view, int i, C0947o c0947o, C0952s c0952s) {
            return null;
        }

        /* renamed from: a */
        public void mo805a(int i, int i2, C0952s c0952s, C0939a c0939a) {
        }

        /* renamed from: a */
        public void mo806a(int i, C0939a c0939a) {
        }

        /* renamed from: a */
        public void m4510a(int i, C0947o c0947o) {
            View h = m4596h(i);
            m4587f(i);
            c0947o.m4905a(h);
        }

        /* renamed from: a */
        public void mo833a(Rect rect, int i, int i2) {
            m4593g(C0910h.m4479a(i, (rect.width() + m4491B()) + m4493D(), m4497H()), C0910h.m4479a(i2, (rect.height() + m4492C()) + m4494E(), m4498I()));
        }

        /* renamed from: a */
        public void mo807a(Parcelable parcelable) {
        }

        /* renamed from: a */
        void m4513a(C0531e c0531e) {
            m4517a(this.f2418q.f185d, this.f2418q.f169A, c0531e);
        }

        /* renamed from: a */
        public void m4514a(C0926a c0926a, C0926a c0926a2) {
        }

        /* renamed from: a */
        public void m4515a(C0947o c0947o) {
            for (int w = m4615w() - 1; w >= 0; w--) {
                m4484a(c0947o, w, m4596h(w));
            }
        }

        /* renamed from: a */
        public void m4516a(C0947o c0947o, C0952s c0952s, int i, int i2) {
            this.f2418q.m272e(i, i2);
        }

        /* renamed from: a */
        public void m4517a(C0947o c0947o, C0952s c0952s, C0531e c0531e) {
            if (ah.m2798b(this.f2418q, -1) || ah.m2792a(this.f2418q, -1)) {
                c0531e.m2305a((int) MessagesController.UPDATE_MASK_CHANNEL);
                c0531e.m2336i(true);
            }
            if (ah.m2798b(this.f2418q, 1) || ah.m2792a(this.f2418q, 1)) {
                c0531e.m2305a(4096);
                c0531e.m2336i(true);
            }
            c0531e.m2314b(C0529l.m2297a(mo829a(c0947o, c0952s), mo844b(c0947o, c0952s), m4584e(c0947o, c0952s), m4571d(c0947o, c0952s)));
        }

        /* renamed from: a */
        public void mo836a(C0947o c0947o, C0952s c0952s, View view, C0531e c0531e) {
            c0531e.m2321c(C0530m.m2298a(mo823e() ? m4573d(view) : 0, 1, mo821d() ? m4573d(view) : 0, 1, false, false));
        }

        /* renamed from: a */
        public void m4519a(C0947o c0947o, C0952s c0952s, AccessibilityEvent accessibilityEvent) {
            boolean z = true;
            C0556o a = C0510a.m2132a(accessibilityEvent);
            if (this.f2418q != null && a != null) {
                if (!(ah.m2798b(this.f2418q, 1) || ah.m2798b(this.f2418q, -1) || ah.m2792a(this.f2418q, -1) || ah.m2792a(this.f2418q, 1))) {
                    z = false;
                }
                a.m2485d(z);
                if (this.f2418q.f193l != null) {
                    a.m2475a(this.f2418q.f193l.getItemCount());
                }
            }
        }

        /* renamed from: a */
        public void mo808a(C0952s c0952s) {
        }

        /* renamed from: a */
        public void mo838a(RecyclerView recyclerView) {
        }

        /* renamed from: a */
        public void mo839a(RecyclerView recyclerView, int i, int i2) {
        }

        /* renamed from: a */
        public void mo840a(RecyclerView recyclerView, int i, int i2, int i3) {
        }

        /* renamed from: a */
        public void mo841a(RecyclerView recyclerView, int i, int i2, Object obj) {
            m4567c(recyclerView, i, i2);
        }

        /* renamed from: a */
        public void mo809a(RecyclerView recyclerView, C0947o c0947o) {
            m4582e(recyclerView);
        }

        /* renamed from: a */
        public void m4526a(View view) {
            m4527a(view, -1);
        }

        /* renamed from: a */
        public void m4527a(View view, int i) {
            m4486a(view, i, true);
        }

        /* renamed from: a */
        public void m4528a(View view, int i, int i2) {
            C0908i c0908i = (C0908i) view.getLayoutParams();
            Rect k = this.f2418q.m289k(view);
            int i3 = (k.left + k.right) + i;
            int i4 = (k.bottom + k.top) + i2;
            i3 = C0910h.m4480a(m4618z(), m4616x(), i3 + (((m4491B() + m4493D()) + c0908i.leftMargin) + c0908i.rightMargin), c0908i.width, mo821d());
            i4 = C0910h.m4480a(m4490A(), m4617y(), i4 + (((m4492C() + m4494E()) + c0908i.topMargin) + c0908i.bottomMargin), c0908i.height, mo823e());
            if (m4560b(view, i3, i4, c0908i)) {
                view.measure(i3, i4);
            }
        }

        /* renamed from: a */
        public void m4529a(View view, int i, int i2, int i3, int i4) {
            C0908i c0908i = (C0908i) view.getLayoutParams();
            Rect rect = c0908i.f2404d;
            view.layout((rect.left + i) + c0908i.leftMargin, (rect.top + i2) + c0908i.topMargin, (i3 - rect.right) - c0908i.rightMargin, (i4 - rect.bottom) - c0908i.bottomMargin);
        }

        /* renamed from: a */
        public void m4530a(View view, int i, C0908i c0908i) {
            C0955v e = RecyclerView.m222e(view);
            if (e.isRemoved()) {
                this.f2418q.f188g.m5741e(e);
            } else {
                this.f2418q.f188g.m5742f(e);
            }
            this.f2417p.m5341a(view, i, c0908i, e.isRemoved());
        }

        /* renamed from: a */
        public void m4531a(View view, Rect rect) {
            RecyclerView.m212a(view, rect);
        }

        /* renamed from: a */
        void m4532a(View view, C0531e c0531e) {
            C0955v e = RecyclerView.m222e(view);
            if (e != null && !e.isRemoved() && !this.f2417p.m5349c(e.itemView)) {
                mo836a(this.f2418q.f185d, this.f2418q.f169A, view, c0531e);
            }
        }

        /* renamed from: a */
        public void m4533a(View view, C0947o c0947o) {
            m4568c(view);
            c0947o.m4905a(view);
        }

        /* renamed from: a */
        public void m4534a(View view, boolean z, Rect rect) {
            if (z) {
                Rect rect2 = ((C0908i) view.getLayoutParams()).f2404d;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, rect2.bottom + view.getHeight());
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.f2418q != null) {
                Matrix n = ah.m2824n(view);
                if (!(n == null || n.isIdentity())) {
                    RectF rectF = this.f2418q.f192k;
                    rectF.set(rect);
                    n.mapRect(rectF);
                    rect.set((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
                }
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        /* renamed from: a */
        public void mo811a(AccessibilityEvent accessibilityEvent) {
            m4519a(this.f2418q.f185d, this.f2418q.f169A, accessibilityEvent);
        }

        /* renamed from: a */
        public void mo812a(String str) {
            if (this.f2418q != null) {
                this.f2418q.m246a(str);
            }
        }

        /* renamed from: a */
        boolean m4537a(int i, Bundle bundle) {
            return m4539a(this.f2418q.f185d, this.f2418q.f169A, i, bundle);
        }

        /* renamed from: a */
        public boolean mo843a(C0908i c0908i) {
            return c0908i != null;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        /* renamed from: a */
        public boolean m4539a(android.support.v7.widget.RecyclerView.C0947o r7, android.support.v7.widget.RecyclerView.C0952s r8, int r9, android.os.Bundle r10) {
            /*
            r6 = this;
            r4 = -1;
            r2 = 1;
            r1 = 0;
            r0 = r6.f2418q;
            if (r0 != 0) goto L_0x0008;
        L_0x0007:
            return r1;
        L_0x0008:
            switch(r9) {
                case 4096: goto L_0x004a;
                case 8192: goto L_0x0018;
                default: goto L_0x000b;
            };
        L_0x000b:
            r0 = r1;
            r3 = r1;
        L_0x000d:
            if (r3 != 0) goto L_0x0011;
        L_0x000f:
            if (r0 == 0) goto L_0x0007;
        L_0x0011:
            r1 = r6.f2418q;
            r1.scrollBy(r0, r3);
            r1 = r2;
            goto L_0x0007;
        L_0x0018:
            r0 = r6.f2418q;
            r0 = android.support.v4.view.ah.m2798b(r0, r4);
            if (r0 == 0) goto L_0x007f;
        L_0x0020:
            r0 = r6.m4490A();
            r3 = r6.m4492C();
            r0 = r0 - r3;
            r3 = r6.m4494E();
            r0 = r0 - r3;
            r0 = -r0;
        L_0x002f:
            r3 = r6.f2418q;
            r3 = android.support.v4.view.ah.m2792a(r3, r4);
            if (r3 == 0) goto L_0x007a;
        L_0x0037:
            r3 = r6.m4618z();
            r4 = r6.m4491B();
            r3 = r3 - r4;
            r4 = r6.m4493D();
            r3 = r3 - r4;
            r3 = -r3;
            r5 = r3;
            r3 = r0;
            r0 = r5;
            goto L_0x000d;
        L_0x004a:
            r0 = r6.f2418q;
            r0 = android.support.v4.view.ah.m2798b(r0, r2);
            if (r0 == 0) goto L_0x007d;
        L_0x0052:
            r0 = r6.m4490A();
            r3 = r6.m4492C();
            r0 = r0 - r3;
            r3 = r6.m4494E();
            r0 = r0 - r3;
        L_0x0060:
            r3 = r6.f2418q;
            r3 = android.support.v4.view.ah.m2792a(r3, r2);
            if (r3 == 0) goto L_0x007a;
        L_0x0068:
            r3 = r6.m4618z();
            r4 = r6.m4491B();
            r3 = r3 - r4;
            r4 = r6.m4493D();
            r3 = r3 - r4;
            r5 = r3;
            r3 = r0;
            r0 = r5;
            goto L_0x000d;
        L_0x007a:
            r3 = r0;
            r0 = r1;
            goto L_0x000d;
        L_0x007d:
            r0 = r1;
            goto L_0x0060;
        L_0x007f:
            r0 = r1;
            goto L_0x002f;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.h.a(android.support.v7.widget.RecyclerView$o, android.support.v7.widget.RecyclerView$s, int, android.os.Bundle):boolean");
        }

        /* renamed from: a */
        public boolean m4540a(C0947o c0947o, C0952s c0952s, View view, int i, Bundle bundle) {
            return false;
        }

        /* renamed from: a */
        public boolean m4541a(RecyclerView recyclerView, C0952s c0952s, View view, View view2) {
            return m4544a(recyclerView, view, view2);
        }

        /* renamed from: a */
        public boolean m4542a(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return m4543a(recyclerView, view, rect, z, false);
        }

        /* renamed from: a */
        public boolean m4543a(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            int[] b = m4488b(recyclerView, view, rect, z);
            int i = b[0];
            int i2 = b[1];
            if (z2 && !m4489d(recyclerView, i, i2)) {
                return false;
            }
            if (i == 0 && i2 == 0) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(i, i2);
            } else {
                recyclerView.m235a(i, i2);
            }
            return true;
        }

        @Deprecated
        /* renamed from: a */
        public boolean m4544a(RecyclerView recyclerView, View view, View view2) {
            return m4612t() || recyclerView.m295n();
        }

        /* renamed from: a */
        public boolean m4545a(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        /* renamed from: a */
        boolean m4546a(View view, int i, int i2, C0908i c0908i) {
            return (this.f2411c && C0910h.m4487b(view.getMeasuredWidth(), i, c0908i.width) && C0910h.m4487b(view.getMeasuredHeight(), i2, c0908i.height)) ? false : true;
        }

        /* renamed from: a */
        boolean m4547a(View view, int i, Bundle bundle) {
            return m4540a(this.f2418q.f185d, this.f2418q.f169A, view, i, bundle);
        }

        /* renamed from: a */
        public boolean m4548a(View view, boolean z, boolean z2) {
            boolean z3 = this.f2419r.m5724a(view, 24579) && this.f2420s.m5724a(view, 24579);
            return z ? z3 : !z3;
        }

        /* renamed from: a */
        public boolean m4549a(Runnable runnable) {
            return this.f2418q != null ? this.f2418q.removeCallbacks(runnable) : false;
        }

        /* renamed from: b */
        public int mo813b(int i, C0947o c0947o, C0952s c0952s) {
            return 0;
        }

        /* renamed from: b */
        public int mo844b(C0947o c0947o, C0952s c0952s) {
            return (this.f2418q == null || this.f2418q.f193l == null || !mo821d()) ? 1 : this.f2418q.f193l.getItemCount();
        }

        /* renamed from: b */
        void m4552b(C0947o c0947o) {
            int e = c0947o.m4921e();
            for (int i = e - 1; i >= 0; i--) {
                View e2 = c0947o.m4922e(i);
                C0955v e3 = RecyclerView.m222e(e2);
                if (!e3.shouldIgnore()) {
                    e3.setIsRecyclable(false);
                    if (e3.isTmpDetached()) {
                        this.f2418q.removeDetachedView(e2, false);
                    }
                    if (this.f2418q.f204w != null) {
                        this.f2418q.f204w.mo930d(e3);
                    }
                    e3.setIsRecyclable(true);
                    c0947o.m4912b(e2);
                }
            }
            c0947o.m4924f();
            if (e > 0) {
                this.f2418q.invalidate();
            }
        }

        /* renamed from: b */
        void m4553b(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.f2418q = null;
                this.f2417p = null;
                this.f2415g = 0;
                this.f2416h = 0;
            } else {
                this.f2418q = recyclerView;
                this.f2417p = recyclerView.f187f;
                this.f2415g = recyclerView.getWidth();
                this.f2416h = recyclerView.getHeight();
            }
            this.f2413e = 1073741824;
            this.f2414f = 1073741824;
        }

        /* renamed from: b */
        public void mo845b(RecyclerView recyclerView, int i, int i2) {
        }

        /* renamed from: b */
        void m4555b(RecyclerView recyclerView, C0947o c0947o) {
            this.f2423v = false;
            mo809a(recyclerView, c0947o);
        }

        /* renamed from: b */
        public void m4556b(View view) {
            m4557b(view, -1);
        }

        /* renamed from: b */
        public void m4557b(View view, int i) {
            m4486a(view, i, false);
        }

        /* renamed from: b */
        public void m4558b(View view, Rect rect) {
            if (this.f2418q == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(this.f2418q.m289k(view));
            }
        }

        /* renamed from: b */
        public boolean mo814b() {
            return false;
        }

        /* renamed from: b */
        boolean m4560b(View view, int i, int i2, C0908i c0908i) {
            return (!view.isLayoutRequested() && this.f2411c && C0910h.m4487b(view.getWidth(), i, c0908i.width) && C0910h.m4487b(view.getHeight(), i2, c0908i.height)) ? false : true;
        }

        /* renamed from: c */
        public int mo815c(C0952s c0952s) {
            return 0;
        }

        /* renamed from: c */
        public Parcelable mo816c() {
            return null;
        }

        /* renamed from: c */
        public View mo817c(int i) {
            int w = m4615w();
            for (int i2 = 0; i2 < w; i2++) {
                View h = m4596h(i2);
                C0955v e = RecyclerView.m222e(h);
                if (e != null && e.getLayoutPosition() == i && !e.shouldIgnore() && (this.f2418q.f169A.m4955a() || !e.isRemoved())) {
                    return h;
                }
            }
            return null;
        }

        /* renamed from: c */
        public void m4564c(C0947o c0947o) {
            for (int w = m4615w() - 1; w >= 0; w--) {
                if (!RecyclerView.m222e(m4596h(w)).shouldIgnore()) {
                    m4510a(w, c0947o);
                }
            }
        }

        /* renamed from: c */
        public void mo818c(C0947o c0947o, C0952s c0952s) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        /* renamed from: c */
        void m4566c(RecyclerView recyclerView) {
            this.f2423v = true;
            m4577d(recyclerView);
        }

        /* renamed from: c */
        public void m4567c(RecyclerView recyclerView, int i, int i2) {
        }

        /* renamed from: c */
        public void m4568c(View view) {
            this.f2417p.m5340a(view);
        }

        /* renamed from: c */
        public void m4569c(View view, int i) {
            m4530a(view, i, (C0908i) view.getLayoutParams());
        }

        /* renamed from: c */
        public void m4570c(boolean z) {
            this.f2424w = z;
        }

        /* renamed from: d */
        public int m4571d(C0947o c0947o, C0952s c0952s) {
            return 0;
        }

        /* renamed from: d */
        public int mo819d(C0952s c0952s) {
            return 0;
        }

        /* renamed from: d */
        public int m4573d(View view) {
            return ((C0908i) view.getLayoutParams()).m4476f();
        }

        /* renamed from: d */
        public View m4574d(View view, int i) {
            return null;
        }

        /* renamed from: d */
        public void mo820d(int i) {
        }

        /* renamed from: d */
        void m4576d(int i, int i2) {
            this.f2415g = MeasureSpec.getSize(i);
            this.f2413e = MeasureSpec.getMode(i);
            if (this.f2413e == 0 && !RecyclerView.f167b) {
                this.f2415g = 0;
            }
            this.f2416h = MeasureSpec.getSize(i2);
            this.f2414f = MeasureSpec.getMode(i2);
            if (this.f2414f == 0 && !RecyclerView.f167b) {
                this.f2416h = 0;
            }
        }

        /* renamed from: d */
        public void m4577d(RecyclerView recyclerView) {
        }

        /* renamed from: d */
        public boolean mo821d() {
            return false;
        }

        /* renamed from: e */
        public int mo822e(C0952s c0952s) {
            return 0;
        }

        /* renamed from: e */
        public View m4580e(View view) {
            if (this.f2418q == null) {
                return null;
            }
            View c = this.f2418q.m262c(view);
            return (c == null || this.f2417p.m5349c(c)) ? null : c;
        }

        /* renamed from: e */
        void m4581e(int i, int i2) {
            int i3 = Integer.MAX_VALUE;
            int i4 = Integer.MIN_VALUE;
            int w = m4615w();
            if (w == 0) {
                this.f2418q.m272e(i, i2);
                return;
            }
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MAX_VALUE;
            for (int i7 = 0; i7 < w; i7++) {
                View h = m4596h(i7);
                Rect rect = this.f2418q.f191j;
                m4531a(h, rect);
                if (rect.left < i6) {
                    i6 = rect.left;
                }
                if (rect.right > i5) {
                    i5 = rect.right;
                }
                if (rect.top < i3) {
                    i3 = rect.top;
                }
                if (rect.bottom > i4) {
                    i4 = rect.bottom;
                }
            }
            this.f2418q.f191j.set(i6, i3, i5, i4);
            mo833a(this.f2418q.f191j, i, i2);
        }

        @Deprecated
        /* renamed from: e */
        public void m4582e(RecyclerView recyclerView) {
        }

        /* renamed from: e */
        public boolean mo823e() {
            return false;
        }

        /* renamed from: e */
        public boolean m4584e(C0947o c0947o, C0952s c0952s) {
            return false;
        }

        /* renamed from: f */
        public int mo824f(C0952s c0952s) {
            return 0;
        }

        /* renamed from: f */
        public int m4586f(View view) {
            Rect rect = ((C0908i) view.getLayoutParams()).f2404d;
            return rect.right + (view.getMeasuredWidth() + rect.left);
        }

        /* renamed from: f */
        public void m4587f(int i) {
            if (m4596h(i) != null) {
                this.f2417p.m5339a(i);
            }
        }

        /* renamed from: f */
        public void m4588f(int i, int i2) {
            View h = m4596h(i);
            if (h == null) {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i);
            }
            m4592g(i);
            m4569c(h, i2);
        }

        /* renamed from: f */
        void m4589f(RecyclerView recyclerView) {
            m4576d(MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        /* renamed from: g */
        public int mo825g(C0952s c0952s) {
            return 0;
        }

        /* renamed from: g */
        public int m4591g(View view) {
            Rect rect = ((C0908i) view.getLayoutParams()).f2404d;
            return rect.bottom + (view.getMeasuredHeight() + rect.top);
        }

        /* renamed from: g */
        public void m4592g(int i) {
            m4482a(i, m4596h(i));
        }

        /* renamed from: g */
        public void m4593g(int i, int i2) {
            this.f2418q.setMeasuredDimension(i, i2);
        }

        /* renamed from: h */
        public int mo826h(C0952s c0952s) {
            return 0;
        }

        /* renamed from: h */
        public int m4595h(View view) {
            return view.getLeft() - m4606n(view);
        }

        /* renamed from: h */
        public View m4596h(int i) {
            return this.f2417p != null ? this.f2417p.m5346b(i) : null;
        }

        /* renamed from: i */
        public int m4597i(View view) {
            return view.getTop() - m4604l(view);
        }

        /* renamed from: i */
        public void mo880i(int i) {
            if (this.f2418q != null) {
                this.f2418q.m271e(i);
            }
        }

        /* renamed from: j */
        public int m4599j(View view) {
            return view.getRight() + m4607o(view);
        }

        /* renamed from: j */
        public void mo881j(int i) {
            if (this.f2418q != null) {
                this.f2418q.m268d(i);
            }
        }

        /* renamed from: k */
        public int m4601k(View view) {
            return view.getBottom() + m4605m(view);
        }

        /* renamed from: k */
        public void mo882k(int i) {
        }

        /* renamed from: k */
        boolean mo827k() {
            return false;
        }

        /* renamed from: l */
        public int m4604l(View view) {
            return ((C0908i) view.getLayoutParams()).f2404d.top;
        }

        /* renamed from: m */
        public int m4605m(View view) {
            return ((C0908i) view.getLayoutParams()).f2404d.bottom;
        }

        /* renamed from: n */
        public int m4606n(View view) {
            return ((C0908i) view.getLayoutParams()).f2404d.left;
        }

        /* renamed from: o */
        public int m4607o(View view) {
            return ((C0908i) view.getLayoutParams()).f2404d.right;
        }

        /* renamed from: p */
        public void m4608p() {
            if (this.f2418q != null) {
                this.f2418q.requestLayout();
            }
        }

        /* renamed from: q */
        public final boolean m4609q() {
            return this.f2412d;
        }

        /* renamed from: r */
        public boolean m4610r() {
            return this.f2423v;
        }

        /* renamed from: s */
        public boolean m4611s() {
            return this.f2418q != null && this.f2418q.f189h;
        }

        /* renamed from: t */
        public boolean m4612t() {
            return this.f2421t != null && this.f2421t.m4949c();
        }

        /* renamed from: u */
        public int m4613u() {
            return ah.m2812g(this.f2418q);
        }

        /* renamed from: v */
        public int m4614v() {
            return -1;
        }

        /* renamed from: w */
        public int m4615w() {
            return this.f2417p != null ? this.f2417p.m5344b() : 0;
        }

        /* renamed from: x */
        public int m4616x() {
            return this.f2413e;
        }

        /* renamed from: y */
        public int m4617y() {
            return this.f2414f;
        }

        /* renamed from: z */
        public int m4618z() {
            return this.f2415g;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$1 */
    class C09161 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2474a;

        C09161(RecyclerView recyclerView) {
            this.f2474a = recyclerView;
        }

        public void run() {
            if (this.f2474a.f199r && !this.f2474a.isLayoutRequested()) {
                if (!this.f2474a.f197p) {
                    this.f2474a.requestLayout();
                } else if (this.f2474a.f201t) {
                    this.f2474a.f200s = true;
                } else {
                    this.f2474a.m263c();
                }
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$2 */
    class C09172 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2475a;

        C09172(RecyclerView recyclerView) {
            this.f2475a = recyclerView;
        }

        public void run() {
            if (this.f2475a.f204w != null) {
                this.f2475a.f204w.mo922a();
            }
            this.f2475a.f172D = false;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$3 */
    static class C09183 implements Interpolator {
        C09183() {
        }

        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$4 */
    class C09204 implements C0919b {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2476a;

        C09204(RecyclerView recyclerView) {
            this.f2476a = recyclerView;
        }

        /* renamed from: a */
        public void mo846a(C0955v c0955v) {
            this.f2476a.f194m.m4533a(c0955v.itemView, this.f2476a.f185d);
        }

        /* renamed from: a */
        public void mo847a(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
            this.f2476a.f185d.m4916c(c0955v);
            this.f2476a.m258b(c0955v, c0932c, c0932c2);
        }

        /* renamed from: b */
        public void mo848b(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
            this.f2476a.m245a(c0955v, c0932c, c0932c2);
        }

        /* renamed from: c */
        public void mo849c(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
            c0955v.setIsRecyclable(false);
            if (this.f2476a.f203v) {
                if (this.f2476a.f204w.mo918a(c0955v, c0955v, c0932c, c0932c2)) {
                    this.f2476a.m296o();
                }
            } else if (this.f2476a.f204w.mo920c(c0955v, c0932c, c0932c2)) {
                this.f2476a.m296o();
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$5 */
    class C09225 implements C0921b {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2477a;

        C09225(RecyclerView recyclerView) {
            this.f2477a = recyclerView;
        }

        /* renamed from: a */
        public int mo850a() {
            return this.f2477a.getChildCount();
        }

        /* renamed from: a */
        public int mo851a(View view) {
            return this.f2477a.indexOfChild(view);
        }

        /* renamed from: a */
        public void mo852a(int i) {
            View childAt = this.f2477a.getChildAt(i);
            if (childAt != null) {
                this.f2477a.m292m(childAt);
            }
            this.f2477a.removeViewAt(i);
        }

        /* renamed from: a */
        public void mo853a(View view, int i) {
            this.f2477a.addView(view, i);
            this.f2477a.m294n(view);
        }

        /* renamed from: a */
        public void mo854a(View view, int i, LayoutParams layoutParams) {
            C0955v e = RecyclerView.m222e(view);
            if (e != null) {
                if (e.isTmpDetached() || e.shouldIgnore()) {
                    e.clearTmpDetachFlag();
                } else {
                    throw new IllegalArgumentException("Called attach on a child which is not detached: " + e);
                }
            }
            this.f2477a.attachViewToParent(view, i, layoutParams);
        }

        /* renamed from: b */
        public C0955v mo855b(View view) {
            return RecyclerView.m222e(view);
        }

        /* renamed from: b */
        public View mo856b(int i) {
            return this.f2477a.getChildAt(i);
        }

        /* renamed from: b */
        public void mo857b() {
            int a = mo850a();
            for (int i = 0; i < a; i++) {
                this.f2477a.m292m(mo856b(i));
            }
            this.f2477a.removeAllViews();
        }

        /* renamed from: c */
        public void mo858c(int i) {
            View b = mo856b(i);
            if (b != null) {
                C0955v e = RecyclerView.m222e(b);
                if (e != null) {
                    if (!e.isTmpDetached() || e.shouldIgnore()) {
                        e.addFlags(256);
                    } else {
                        throw new IllegalArgumentException("called detach on an already detached child " + e);
                    }
                }
            }
            this.f2477a.detachViewFromParent(i);
        }

        /* renamed from: c */
        public void mo859c(View view) {
            C0955v e = RecyclerView.m222e(view);
            if (e != null) {
                e.onEnteredHiddenState(this.f2477a);
            }
        }

        /* renamed from: d */
        public void mo860d(View view) {
            C0955v e = RecyclerView.m222e(view);
            if (e != null) {
                e.onLeftHiddenState(this.f2477a);
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$6 */
    class C09246 implements C0923a {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2478a;

        C09246(RecyclerView recyclerView) {
            this.f2478a = recyclerView;
        }

        /* renamed from: a */
        public C0955v mo861a(int i) {
            C0955v a = this.f2478a.m230a(i, true);
            return (a == null || this.f2478a.f187f.m5349c(a.itemView)) ? null : a;
        }

        /* renamed from: a */
        public void mo862a(int i, int i2) {
            this.f2478a.m238a(i, i2, true);
            this.f2478a.f170B = true;
            C0952s c0952s = this.f2478a.f169A;
            c0952s.f2528b += i2;
        }

        /* renamed from: a */
        public void mo863a(int i, int i2, Object obj) {
            this.f2478a.m237a(i, i2, obj);
            this.f2478a.f171C = true;
        }

        /* renamed from: a */
        public void mo864a(C1059b c1059b) {
            m4801c(c1059b);
        }

        /* renamed from: b */
        public void mo865b(int i, int i2) {
            this.f2478a.m238a(i, i2, false);
            this.f2478a.f170B = true;
        }

        /* renamed from: b */
        public void mo866b(C1059b c1059b) {
            m4801c(c1059b);
        }

        /* renamed from: c */
        public void mo867c(int i, int i2) {
            this.f2478a.m280g(i, i2);
            this.f2478a.f170B = true;
        }

        /* renamed from: c */
        void m4801c(C1059b c1059b) {
            switch (c1059b.f3137a) {
                case 1:
                    this.f2478a.f194m.mo839a(this.f2478a, c1059b.f3138b, c1059b.f3140d);
                    return;
                case 2:
                    this.f2478a.f194m.mo845b(this.f2478a, c1059b.f3138b, c1059b.f3140d);
                    return;
                case 4:
                    this.f2478a.f194m.mo841a(this.f2478a, c1059b.f3138b, c1059b.f3140d, c1059b.f3139c);
                    return;
                case 8:
                    this.f2478a.f194m.mo840a(this.f2478a, c1059b.f3138b, c1059b.f3140d, 1);
                    return;
                default:
                    return;
            }
        }

        /* renamed from: d */
        public void mo868d(int i, int i2) {
            this.f2478a.m276f(i, i2);
            this.f2478a.f170B = true;
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C09251());
        /* renamed from: a */
        Parcelable f2479a;

        /* renamed from: android.support.v7.widget.RecyclerView$SavedState$1 */
        static class C09251 implements C0085g<SavedState> {
            C09251() {
            }

            /* renamed from: a */
            public SavedState m4803a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m4804a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m4803a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m4804a(i);
            }
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = C0910h.class.getClassLoader();
            }
            this.f2479a = parcel.readParcelable(classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /* renamed from: a */
        void m4805a(SavedState savedState) {
            this.f2479a = savedState.f2479a;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.f2479a, 0);
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$a */
    public static abstract class C0926a<VH extends C0955v> {
        private boolean mHasStableIds = false;
        private final C0927b mObservable = new C0927b();

        public final void bindViewHolder(VH vh, int i) {
            vh.mPosition = i;
            if (hasStableIds()) {
                vh.mItemId = getItemId(i);
            }
            vh.setFlags(1, 519);
            C0440j.m1922a("RV OnBindView");
            onBindViewHolder(vh, i, vh.getUnmodifiedPayloads());
            vh.clearPayload();
            LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof C0908i) {
                ((C0908i) layoutParams).f2405e = true;
            }
            C0440j.m1921a();
        }

        public final VH createViewHolder(ViewGroup viewGroup, int i) {
            C0440j.m1922a("RV CreateView");
            VH onCreateViewHolder = onCreateViewHolder(viewGroup, i);
            onCreateViewHolder.mItemViewType = i;
            C0440j.m1921a();
            return onCreateViewHolder;
        }

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.m4808a();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.m4809b();
        }

        public final void notifyItemChanged(int i) {
            this.mObservable.m4806a(i, 1);
        }

        public final void notifyItemChanged(int i, Object obj) {
            this.mObservable.m4807a(i, 1, obj);
        }

        public final void notifyItemInserted(int i) {
            this.mObservable.m4810b(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.m4812d(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.mObservable.m4806a(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            this.mObservable.m4807a(i, i2, obj);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.m4810b(i, i2);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.m4811c(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.mObservable.m4811c(i, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH vh, int i);

        public void onBindViewHolder(VH vh, int i, List<Object> list) {
            onBindViewHolder(vh, i);
        }

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public void onViewRecycled(VH vh) {
        }

        public void registerAdapterDataObserver(C0928c c0928c) {
            this.mObservable.registerObserver(c0928c);
        }

        public void setHasStableIds(boolean z) {
            if (hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = z;
        }

        public void unregisterAdapterDataObserver(C0928c c0928c) {
            this.mObservable.unregisterObserver(c0928c);
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$b */
    static class C0927b extends Observable<C0928c> {
        C0927b() {
        }

        /* renamed from: a */
        public void m4806a(int i, int i2) {
            m4807a(i, i2, null);
        }

        /* renamed from: a */
        public void m4807a(int i, int i2, Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((C0928c) this.mObservers.get(size)).mo877a(i, i2, obj);
            }
        }

        /* renamed from: a */
        public boolean m4808a() {
            return !this.mObservers.isEmpty();
        }

        /* renamed from: b */
        public void m4809b() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((C0928c) this.mObservers.get(size)).mo875a();
            }
        }

        /* renamed from: b */
        public void m4810b(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((C0928c) this.mObservers.get(size)).mo878b(i, i2);
            }
        }

        /* renamed from: c */
        public void m4811c(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((C0928c) this.mObservers.get(size)).mo879c(i, i2);
            }
        }

        /* renamed from: d */
        public void m4812d(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((C0928c) this.mObservers.get(size)).mo876a(i, i2, 1);
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$c */
    public static abstract class C0928c {
        /* renamed from: a */
        public void mo875a() {
        }

        /* renamed from: a */
        public void m4814a(int i, int i2) {
        }

        /* renamed from: a */
        public void mo876a(int i, int i2, int i3) {
        }

        /* renamed from: a */
        public void mo877a(int i, int i2, Object obj) {
            m4814a(i, i2);
        }

        /* renamed from: b */
        public void mo878b(int i, int i2) {
        }

        /* renamed from: c */
        public void mo879c(int i, int i2) {
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$d */
    public interface C0929d {
        /* renamed from: a */
        int mo891a(int i, int i2);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$e */
    public static abstract class C0933e {
        /* renamed from: a */
        private C0931b f2484a = null;
        /* renamed from: b */
        private ArrayList<C0930a> f2485b = new ArrayList();
        /* renamed from: c */
        private long f2486c = 120;
        /* renamed from: d */
        private long f2487d = 120;
        /* renamed from: e */
        private long f2488e = 250;
        /* renamed from: f */
        private long f2489f = 250;

        /* renamed from: android.support.v7.widget.RecyclerView$e$a */
        public interface C0930a {
            /* renamed from: a */
            void m4820a();
        }

        /* renamed from: android.support.v7.widget.RecyclerView$e$b */
        interface C0931b {
            /* renamed from: a */
            void mo869a(C0955v c0955v);
        }

        /* renamed from: android.support.v7.widget.RecyclerView$e$c */
        public static class C0932c {
            /* renamed from: a */
            public int f2480a;
            /* renamed from: b */
            public int f2481b;
            /* renamed from: c */
            public int f2482c;
            /* renamed from: d */
            public int f2483d;

            /* renamed from: a */
            public C0932c m4822a(C0955v c0955v) {
                return m4823a(c0955v, 0);
            }

            /* renamed from: a */
            public C0932c m4823a(C0955v c0955v, int i) {
                View view = c0955v.itemView;
                this.f2480a = view.getLeft();
                this.f2481b = view.getTop();
                this.f2482c = view.getRight();
                this.f2483d = view.getBottom();
                return this;
            }
        }

        /* renamed from: e */
        static int m4824e(C0955v c0955v) {
            int access$1400 = c0955v.mFlags & 14;
            if (c0955v.isInvalid()) {
                return 4;
            }
            if ((access$1400 & 4) != 0) {
                return access$1400;
            }
            int oldPosition = c0955v.getOldPosition();
            int adapterPosition = c0955v.getAdapterPosition();
            return (oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition) ? access$1400 : access$1400 | 2048;
        }

        /* renamed from: a */
        public C0932c m4825a(C0952s c0952s, C0955v c0955v) {
            return m4846j().m4822a(c0955v);
        }

        /* renamed from: a */
        public C0932c m4826a(C0952s c0952s, C0955v c0955v, int i, List<Object> list) {
            return m4846j().m4822a(c0955v);
        }

        /* renamed from: a */
        public abstract void mo922a();

        /* renamed from: a */
        void m4828a(C0931b c0931b) {
            this.f2484a = c0931b;
        }

        /* renamed from: a */
        public final boolean m4829a(C0930a c0930a) {
            boolean b = mo927b();
            if (c0930a != null) {
                if (b) {
                    this.f2485b.add(c0930a);
                } else {
                    c0930a.m4820a();
                }
            }
            return b;
        }

        /* renamed from: a */
        public abstract boolean mo917a(C0955v c0955v, C0932c c0932c, C0932c c0932c2);

        /* renamed from: a */
        public abstract boolean mo918a(C0955v c0955v, C0955v c0955v2, C0932c c0932c, C0932c c0932c2);

        /* renamed from: a */
        public boolean mo926a(C0955v c0955v, List<Object> list) {
            return mo921h(c0955v);
        }

        /* renamed from: b */
        public abstract boolean mo927b();

        /* renamed from: b */
        public abstract boolean mo919b(C0955v c0955v, C0932c c0932c, C0932c c0932c2);

        /* renamed from: c */
        public abstract boolean mo920c(C0955v c0955v, C0932c c0932c, C0932c c0932c2);

        /* renamed from: d */
        public abstract void mo929d();

        /* renamed from: d */
        public abstract void mo930d(C0955v c0955v);

        /* renamed from: e */
        public long m4838e() {
            return this.f2488e;
        }

        /* renamed from: f */
        public long m4839f() {
            return this.f2486c;
        }

        /* renamed from: f */
        public final void m4840f(C0955v c0955v) {
            m4842g(c0955v);
            if (this.f2484a != null) {
                this.f2484a.mo869a(c0955v);
            }
        }

        /* renamed from: g */
        public long m4841g() {
            return this.f2487d;
        }

        /* renamed from: g */
        public void m4842g(C0955v c0955v) {
        }

        /* renamed from: h */
        public long m4843h() {
            return this.f2489f;
        }

        /* renamed from: h */
        public boolean mo921h(C0955v c0955v) {
            return true;
        }

        /* renamed from: i */
        public final void m4845i() {
            int size = this.f2485b.size();
            for (int i = 0; i < size; i++) {
                ((C0930a) this.f2485b.get(i)).m4820a();
            }
            this.f2485b.clear();
        }

        /* renamed from: j */
        public C0932c m4846j() {
            return new C0932c();
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$f */
    private class C0934f implements C0931b {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2490a;

        C0934f(RecyclerView recyclerView) {
            this.f2490a = recyclerView;
        }

        /* renamed from: a */
        public void mo869a(C0955v c0955v) {
            c0955v.setIsRecyclable(true);
            if (c0955v.mShadowedHolder != null && c0955v.mShadowingHolder == null) {
                c0955v.mShadowedHolder = null;
            }
            c0955v.mShadowingHolder = null;
            if (!c0955v.shouldBeKeptAsChild() && !this.f2490a.m250a(c0955v.itemView) && c0955v.isTmpDetached()) {
                this.f2490a.removeDetachedView(c0955v.itemView, false);
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$g */
    public static abstract class C0935g {
        @Deprecated
        /* renamed from: a */
        public void m4848a(Canvas canvas, RecyclerView recyclerView) {
        }

        /* renamed from: a */
        public void mo893a(Canvas canvas, RecyclerView recyclerView, C0952s c0952s) {
            m4848a(canvas, recyclerView);
        }

        @Deprecated
        /* renamed from: a */
        public void m4850a(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        /* renamed from: a */
        public void mo894a(Rect rect, View view, RecyclerView recyclerView, C0952s c0952s) {
            m4850a(rect, ((C0908i) view.getLayoutParams()).m4476f(), recyclerView);
        }

        @Deprecated
        /* renamed from: b */
        public void m4852b(Canvas canvas, RecyclerView recyclerView) {
        }

        /* renamed from: b */
        public void mo896b(Canvas canvas, RecyclerView recyclerView, C0952s c0952s) {
            m4852b(canvas, recyclerView);
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$j */
    public interface C0941j {
        /* renamed from: a */
        void mo895a(View view);

        /* renamed from: b */
        void mo897b(View view);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$k */
    public static abstract class C0942k {
        /* renamed from: a */
        public abstract boolean m4872a(int i, int i2);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$l */
    public interface C0943l {
        /* renamed from: a */
        void mo884a(boolean z);

        /* renamed from: a */
        boolean mo885a(RecyclerView recyclerView, MotionEvent motionEvent);

        /* renamed from: b */
        void mo886b(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$m */
    public static abstract class C0944m {
        /* renamed from: a */
        public void m4876a(RecyclerView recyclerView, int i) {
        }

        /* renamed from: a */
        public void m4877a(RecyclerView recyclerView, int i, int i2) {
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$n */
    public static class C0946n {
        /* renamed from: a */
        SparseArray<C0945a> f2501a = new SparseArray();
        /* renamed from: b */
        private int f2502b = 0;

        /* renamed from: android.support.v7.widget.RecyclerView$n$a */
        static class C0945a {
            /* renamed from: a */
            ArrayList<C0955v> f2497a = new ArrayList();
            /* renamed from: b */
            int f2498b = 5;
            /* renamed from: c */
            long f2499c = 0;
            /* renamed from: d */
            long f2500d = 0;

            C0945a() {
            }
        }

        /* renamed from: b */
        private C0945a m4878b(int i) {
            C0945a c0945a = (C0945a) this.f2501a.get(i);
            if (c0945a != null) {
                return c0945a;
            }
            c0945a = new C0945a();
            this.f2501a.put(i, c0945a);
            return c0945a;
        }

        /* renamed from: a */
        long m4879a(long j, long j2) {
            return j == 0 ? j2 : ((j / 4) * 3) + (j2 / 4);
        }

        /* renamed from: a */
        public C0955v m4880a(int i) {
            C0945a c0945a = (C0945a) this.f2501a.get(i);
            if (c0945a == null || c0945a.f2497a.isEmpty()) {
                return null;
            }
            ArrayList arrayList = c0945a.f2497a;
            return (C0955v) arrayList.remove(arrayList.size() - 1);
        }

        /* renamed from: a */
        public void m4881a() {
            for (int i = 0; i < this.f2501a.size(); i++) {
                ((C0945a) this.f2501a.valueAt(i)).f2497a.clear();
            }
        }

        /* renamed from: a */
        void m4882a(int i, long j) {
            C0945a b = m4878b(i);
            b.f2499c = m4879a(b.f2499c, j);
        }

        /* renamed from: a */
        void m4883a(C0926a c0926a) {
            this.f2502b++;
        }

        /* renamed from: a */
        void m4884a(C0926a c0926a, C0926a c0926a2, boolean z) {
            if (c0926a != null) {
                m4887b();
            }
            if (!z && this.f2502b == 0) {
                m4881a();
            }
            if (c0926a2 != null) {
                m4883a(c0926a2);
            }
        }

        /* renamed from: a */
        public void m4885a(C0955v c0955v) {
            int itemViewType = c0955v.getItemViewType();
            ArrayList arrayList = m4878b(itemViewType).f2497a;
            if (((C0945a) this.f2501a.get(itemViewType)).f2498b > arrayList.size()) {
                c0955v.resetInternal();
                arrayList.add(c0955v);
            }
        }

        /* renamed from: a */
        boolean m4886a(int i, long j, long j2) {
            long j3 = m4878b(i).f2499c;
            return j3 == 0 || j3 + j < j2;
        }

        /* renamed from: b */
        void m4887b() {
            this.f2502b--;
        }

        /* renamed from: b */
        void m4888b(int i, long j) {
            C0945a b = m4878b(i);
            b.f2500d = m4879a(b.f2500d, j);
        }

        /* renamed from: b */
        boolean m4889b(int i, long j, long j2) {
            long j3 = m4878b(i).f2500d;
            return j3 == 0 || j3 + j < j2;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$o */
    public final class C0947o {
        /* renamed from: a */
        final ArrayList<C0955v> f2503a = new ArrayList();
        /* renamed from: b */
        ArrayList<C0955v> f2504b = null;
        /* renamed from: c */
        final ArrayList<C0955v> f2505c = new ArrayList();
        /* renamed from: d */
        int f2506d = 2;
        /* renamed from: e */
        C0946n f2507e;
        /* renamed from: f */
        final /* synthetic */ RecyclerView f2508f;
        /* renamed from: g */
        private final List<C0955v> f2509g = Collections.unmodifiableList(this.f2503a);
        /* renamed from: h */
        private int f2510h = 2;
        /* renamed from: i */
        private C0953t f2511i;

        public C0947o(RecyclerView recyclerView) {
            this.f2508f = recyclerView;
        }

        /* renamed from: a */
        private void m4890a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    m4890a((ViewGroup) childAt, true);
                }
            }
            if (!z) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
                return;
            }
            int visibility = viewGroup.getVisibility();
            viewGroup.setVisibility(4);
            viewGroup.setVisibility(visibility);
        }

        /* renamed from: a */
        private boolean m4891a(C0955v c0955v, int i, int i2, long j) {
            c0955v.mOwnerRecyclerView = this.f2508f;
            int itemViewType = c0955v.getItemViewType();
            long nanoTime = this.f2508f.getNanoTime();
            if (j != Long.MAX_VALUE && !this.f2507e.m4889b(itemViewType, nanoTime, j)) {
                return false;
            }
            this.f2508f.f193l.bindViewHolder(c0955v, i);
            this.f2507e.m4888b(c0955v.getItemViewType(), this.f2508f.getNanoTime() - nanoTime);
            m4892d(c0955v.itemView);
            if (this.f2508f.f169A.m4955a()) {
                c0955v.mPreLayoutPosition = i2;
            }
            return true;
        }

        /* renamed from: d */
        private void m4892d(View view) {
            if (this.f2508f.m293m()) {
                if (ah.m2803d(view) == 0) {
                    ah.m2801c(view, 1);
                }
                if (!ah.m2791a(view)) {
                    ah.m2783a(view, this.f2508f.f173E.m5570b());
                }
            }
        }

        /* renamed from: e */
        private void m4893e(C0955v c0955v) {
            if (c0955v.itemView instanceof ViewGroup) {
                m4890a((ViewGroup) c0955v.itemView, false);
            }
        }

        /* renamed from: a */
        C0955v m4894a(int i, boolean z, long j) {
            boolean z2 = true;
            if (i < 0 || i >= this.f2508f.f169A.m4959e()) {
                throw new IndexOutOfBoundsException("Invalid item position " + i + "(" + i + "). Item count:" + this.f2508f.f169A.m4959e());
            }
            C0955v f;
            boolean z3;
            C0955v c0955v;
            boolean z4;
            C0908i c0908i;
            if (this.f2508f.f169A.m4955a()) {
                f = m4923f(i);
                z3 = f != null;
                c0955v = f;
            } else {
                c0955v = null;
                z3 = false;
            }
            if (c0955v == null) {
                c0955v = m4908b(i, z);
                if (c0955v != null) {
                    if (m4906a(c0955v)) {
                        z3 = true;
                    } else {
                        if (!z) {
                            c0955v.addFlags(4);
                            if (c0955v.isScrap()) {
                                this.f2508f.removeDetachedView(c0955v.itemView, false);
                                c0955v.unScrap();
                            } else if (c0955v.wasReturnedFromScrap()) {
                                c0955v.clearReturnedFromScrapFlag();
                            }
                            m4911b(c0955v);
                        }
                        c0955v = null;
                    }
                }
            }
            if (c0955v == null) {
                int b = this.f2508f.f186e.m5825b(i);
                if (b < 0 || b >= this.f2508f.f193l.getItemCount()) {
                    throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + b + ")." + "state:" + this.f2508f.f169A.m4959e());
                }
                boolean z5;
                View a;
                long nanoTime;
                RecyclerView l;
                int itemViewType = this.f2508f.f193l.getItemViewType(b);
                if (this.f2508f.f193l.hasStableIds()) {
                    c0955v = m4895a(this.f2508f.f193l.getItemId(b), itemViewType, z);
                    if (c0955v != null) {
                        c0955v.mPosition = b;
                        z5 = true;
                        if (c0955v == null && this.f2511i != null) {
                            a = this.f2511i.m4960a(this, i, itemViewType);
                            if (a != null) {
                                c0955v = this.f2508f.m252b(a);
                                if (c0955v == null) {
                                    throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
                                } else if (c0955v.shouldIgnore()) {
                                    throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                                }
                            }
                        }
                        if (c0955v == null) {
                            c0955v = m4925g().m4880a(itemViewType);
                            if (c0955v != null) {
                                c0955v.resetInternal();
                                if (RecyclerView.f166a) {
                                    m4893e(c0955v);
                                }
                            }
                        }
                        if (c0955v == null) {
                            nanoTime = this.f2508f.getNanoTime();
                            if (j == Long.MAX_VALUE && !this.f2507e.m4886a(itemViewType, nanoTime, j)) {
                                return null;
                            }
                            c0955v = this.f2508f.f193l.createViewHolder(this.f2508f, itemViewType);
                            if (RecyclerView.f162J) {
                                l = RecyclerView.m225l(c0955v.itemView);
                                if (l != null) {
                                    c0955v.mNestedRecyclerView = new WeakReference(l);
                                }
                            }
                            this.f2507e.m4882a(itemViewType, this.f2508f.getNanoTime() - nanoTime);
                        }
                        f = c0955v;
                        z4 = z5;
                    }
                }
                z5 = z3;
                a = this.f2511i.m4960a(this, i, itemViewType);
                if (a != null) {
                    c0955v = this.f2508f.m252b(a);
                    if (c0955v == null) {
                        throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
                    } else if (c0955v.shouldIgnore()) {
                        throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                    }
                }
                if (c0955v == null) {
                    c0955v = m4925g().m4880a(itemViewType);
                    if (c0955v != null) {
                        c0955v.resetInternal();
                        if (RecyclerView.f166a) {
                            m4893e(c0955v);
                        }
                    }
                }
                if (c0955v == null) {
                    nanoTime = this.f2508f.getNanoTime();
                    if (j == Long.MAX_VALUE) {
                    }
                    c0955v = this.f2508f.f193l.createViewHolder(this.f2508f, itemViewType);
                    if (RecyclerView.f162J) {
                        l = RecyclerView.m225l(c0955v.itemView);
                        if (l != null) {
                            c0955v.mNestedRecyclerView = new WeakReference(l);
                        }
                    }
                    this.f2507e.m4882a(itemViewType, this.f2508f.getNanoTime() - nanoTime);
                }
                f = c0955v;
                z4 = z5;
            } else {
                f = c0955v;
                z4 = z3;
            }
            if (z4 && !this.f2508f.f169A.m4955a() && f.hasAnyOfTheFlags(MessagesController.UPDATE_MASK_CHANNEL)) {
                f.setFlags(0, MessagesController.UPDATE_MASK_CHANNEL);
                if (this.f2508f.f169A.f2535i) {
                    this.f2508f.m244a(f, this.f2508f.f204w.m4826a(this.f2508f.f169A, f, C0933e.m4824e(f) | 4096, f.getUnmodifiedPayloads()));
                }
            }
            if (this.f2508f.f169A.m4955a() && f.isBound()) {
                f.mPreLayoutPosition = i;
                z3 = false;
            } else {
                z3 = (!f.isBound() || f.needsUpdate() || f.isInvalid()) ? m4891a(f, this.f2508f.f186e.m5825b(i), i, j) : false;
            }
            LayoutParams layoutParams = f.itemView.getLayoutParams();
            if (layoutParams == null) {
                c0908i = (C0908i) this.f2508f.generateDefaultLayoutParams();
                f.itemView.setLayoutParams(c0908i);
            } else if (this.f2508f.checkLayoutParams(layoutParams)) {
                c0908i = (C0908i) layoutParams;
            } else {
                c0908i = (C0908i) this.f2508f.generateLayoutParams(layoutParams);
                f.itemView.setLayoutParams(c0908i);
            }
            c0908i.f2403c = f;
            if (!(z4 && r2)) {
                z2 = false;
            }
            c0908i.f2406f = z2;
            return f;
        }

        /* renamed from: a */
        C0955v m4895a(long j, int i, boolean z) {
            int size;
            for (size = this.f2503a.size() - 1; size >= 0; size--) {
                C0955v c0955v = (C0955v) this.f2503a.get(size);
                if (c0955v.getItemId() == j && !c0955v.wasReturnedFromScrap()) {
                    if (i == c0955v.getItemViewType()) {
                        c0955v.addFlags(32);
                        if (!c0955v.isRemoved() || this.f2508f.f169A.m4955a()) {
                            return c0955v;
                        }
                        c0955v.setFlags(2, 14);
                        return c0955v;
                    } else if (!z) {
                        this.f2503a.remove(size);
                        this.f2508f.removeDetachedView(c0955v.itemView, false);
                        m4912b(c0955v.itemView);
                    }
                }
            }
            for (size = this.f2505c.size() - 1; size >= 0; size--) {
                c0955v = (C0955v) this.f2505c.get(size);
                if (c0955v.getItemId() == j) {
                    if (i == c0955v.getItemViewType()) {
                        if (z) {
                            return c0955v;
                        }
                        this.f2505c.remove(size);
                        return c0955v;
                    } else if (!z) {
                        m4919d(size);
                        return null;
                    }
                }
            }
            return null;
        }

        /* renamed from: a */
        View m4896a(int i, boolean z) {
            return m4894a(i, z, Long.MAX_VALUE).itemView;
        }

        /* renamed from: a */
        public void m4897a() {
            this.f2503a.clear();
            m4918d();
        }

        /* renamed from: a */
        public void m4898a(int i) {
            this.f2510h = i;
            m4909b();
        }

        /* renamed from: a */
        void m4899a(int i, int i2) {
            int i3;
            int i4;
            int i5;
            if (i < i2) {
                i3 = -1;
                i4 = i2;
                i5 = i;
            } else {
                i3 = 1;
                i4 = i;
                i5 = i2;
            }
            int size = this.f2505c.size();
            for (int i6 = 0; i6 < size; i6++) {
                C0955v c0955v = (C0955v) this.f2505c.get(i6);
                if (c0955v != null && c0955v.mPosition >= r3 && c0955v.mPosition <= r2) {
                    if (c0955v.mPosition == i) {
                        c0955v.offsetPosition(i2 - i, false);
                    } else {
                        c0955v.offsetPosition(i3, false);
                    }
                }
            }
        }

        /* renamed from: a */
        void m4900a(int i, int i2, boolean z) {
            int i3 = i + i2;
            for (int size = this.f2505c.size() - 1; size >= 0; size--) {
                C0955v c0955v = (C0955v) this.f2505c.get(size);
                if (c0955v != null) {
                    if (c0955v.mPosition >= i3) {
                        c0955v.offsetPosition(-i2, z);
                    } else if (c0955v.mPosition >= i) {
                        c0955v.addFlags(8);
                        m4919d(size);
                    }
                }
            }
        }

        /* renamed from: a */
        void m4901a(C0926a c0926a, C0926a c0926a2, boolean z) {
            m4897a();
            m4925g().m4884a(c0926a, c0926a2, z);
        }

        /* renamed from: a */
        void m4902a(C0946n c0946n) {
            if (this.f2507e != null) {
                this.f2507e.m4887b();
            }
            this.f2507e = c0946n;
            if (c0946n != null) {
                this.f2507e.m4883a(this.f2508f.getAdapter());
            }
        }

        /* renamed from: a */
        void m4903a(C0953t c0953t) {
            this.f2511i = c0953t;
        }

        /* renamed from: a */
        void m4904a(C0955v c0955v, boolean z) {
            RecyclerView.m220c(c0955v);
            ah.m2783a(c0955v.itemView, null);
            if (z) {
                m4920d(c0955v);
            }
            c0955v.mOwnerRecyclerView = null;
            m4925g().m4885a(c0955v);
        }

        /* renamed from: a */
        public void m4905a(View view) {
            C0955v e = RecyclerView.m222e(view);
            if (e.isTmpDetached()) {
                this.f2508f.removeDetachedView(view, false);
            }
            if (e.isScrap()) {
                e.unScrap();
            } else if (e.wasReturnedFromScrap()) {
                e.clearReturnedFromScrapFlag();
            }
            m4911b(e);
        }

        /* renamed from: a */
        boolean m4906a(C0955v c0955v) {
            if (c0955v.isRemoved()) {
                return this.f2508f.f169A.m4955a();
            }
            if (c0955v.mPosition >= 0 && c0955v.mPosition < this.f2508f.f193l.getItemCount()) {
                return (this.f2508f.f169A.m4955a() || this.f2508f.f193l.getItemViewType(c0955v.mPosition) == c0955v.getItemViewType()) ? !this.f2508f.f193l.hasStableIds() || c0955v.getItemId() == this.f2508f.f193l.getItemId(c0955v.mPosition) : false;
            } else {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + c0955v);
            }
        }

        /* renamed from: b */
        public int m4907b(int i) {
            if (i >= 0 && i < this.f2508f.f169A.m4959e()) {
                return !this.f2508f.f169A.m4955a() ? i : this.f2508f.f186e.m5825b(i);
            } else {
                throw new IndexOutOfBoundsException("invalid position " + i + ". State " + "item count is " + this.f2508f.f169A.m4959e());
            }
        }

        /* renamed from: b */
        C0955v m4908b(int i, boolean z) {
            int i2 = 0;
            int size = this.f2503a.size();
            int i3 = 0;
            while (i3 < size) {
                C0955v c0955v = (C0955v) this.f2503a.get(i3);
                if (c0955v.wasReturnedFromScrap() || c0955v.getLayoutPosition() != i || c0955v.isInvalid() || (!this.f2508f.f169A.f2532f && c0955v.isRemoved())) {
                    i3++;
                } else {
                    c0955v.addFlags(32);
                    return c0955v;
                }
            }
            if (!z) {
                View c = this.f2508f.f187f.m5348c(i);
                if (c != null) {
                    c0955v = RecyclerView.m222e(c);
                    this.f2508f.f187f.m5353e(c);
                    i2 = this.f2508f.f187f.m5345b(c);
                    if (i2 == -1) {
                        throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + c0955v);
                    }
                    this.f2508f.f187f.m5352e(i2);
                    m4917c(c);
                    c0955v.addFlags(8224);
                    return c0955v;
                }
            }
            i3 = this.f2505c.size();
            while (i2 < i3) {
                c0955v = (C0955v) this.f2505c.get(i2);
                if (c0955v.isInvalid() || c0955v.getLayoutPosition() != i) {
                    i2++;
                } else if (z) {
                    return c0955v;
                } else {
                    this.f2505c.remove(i2);
                    return c0955v;
                }
            }
            return null;
        }

        /* renamed from: b */
        void m4909b() {
            this.f2506d = (this.f2508f.f194m != null ? this.f2508f.f194m.f2425x : 0) + this.f2510h;
            for (int size = this.f2505c.size() - 1; size >= 0 && this.f2505c.size() > this.f2506d; size--) {
                m4919d(size);
            }
        }

        /* renamed from: b */
        void m4910b(int i, int i2) {
            int size = this.f2505c.size();
            for (int i3 = 0; i3 < size; i3++) {
                C0955v c0955v = (C0955v) this.f2505c.get(i3);
                if (c0955v != null && c0955v.mPosition >= i) {
                    c0955v.offsetPosition(i2, true);
                }
            }
        }

        /* renamed from: b */
        void m4911b(C0955v c0955v) {
            int i = 0;
            if (c0955v.isScrap() || c0955v.itemView.getParent() != null) {
                throw new IllegalArgumentException("Scrapped or attached views may not be recycled. isScrap:" + c0955v.isScrap() + " isAttached:" + (c0955v.itemView.getParent() != null));
            } else if (c0955v.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + c0955v);
            } else if (c0955v.shouldIgnore()) {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
            } else {
                int size;
                boolean access$700 = c0955v.doesTransientStatePreventRecycling();
                boolean z = this.f2508f.f193l != null && access$700 && this.f2508f.f193l.onFailedToRecycleView(c0955v);
                if (z || c0955v.isRecyclable()) {
                    if (this.f2506d <= 0 || c0955v.hasAnyOfTheFlags(526)) {
                        z = false;
                    } else {
                        size = this.f2505c.size();
                        if (size >= this.f2506d && size > 0) {
                            m4919d(0);
                            size--;
                        }
                        if (RecyclerView.f162J && size > 0 && !this.f2508f.f207z.m5451a(c0955v.mPosition)) {
                            int i2 = size - 1;
                            while (i2 >= 0) {
                                if (!this.f2508f.f207z.m5451a(((C0955v) this.f2505c.get(i2)).mPosition)) {
                                    break;
                                }
                                i2--;
                            }
                            size = i2 + 1;
                        }
                        this.f2505c.add(size, c0955v);
                        size = true;
                    }
                    if (!size != false) {
                        m4904a(c0955v, true);
                        i = 1;
                    }
                } else {
                    size = 0;
                }
                this.f2508f.f188g.m5743g(c0955v);
                if (size == 0 && r2 == 0 && access$700) {
                    c0955v.mOwnerRecyclerView = null;
                }
            }
        }

        /* renamed from: b */
        void m4912b(View view) {
            C0955v e = RecyclerView.m222e(view);
            e.mScrapContainer = null;
            e.mInChangeScrap = false;
            e.clearReturnedFromScrapFlag();
            m4911b(e);
        }

        /* renamed from: c */
        public View m4913c(int i) {
            return m4896a(i, false);
        }

        /* renamed from: c */
        public List<C0955v> m4914c() {
            return this.f2509g;
        }

        /* renamed from: c */
        void m4915c(int i, int i2) {
            int i3 = i + i2;
            for (int size = this.f2505c.size() - 1; size >= 0; size--) {
                C0955v c0955v = (C0955v) this.f2505c.get(size);
                if (c0955v != null) {
                    int layoutPosition = c0955v.getLayoutPosition();
                    if (layoutPosition >= i && layoutPosition < i3) {
                        c0955v.addFlags(2);
                        m4919d(size);
                    }
                }
            }
        }

        /* renamed from: c */
        void m4916c(C0955v c0955v) {
            if (c0955v.mInChangeScrap) {
                this.f2504b.remove(c0955v);
            } else {
                this.f2503a.remove(c0955v);
            }
            c0955v.mScrapContainer = null;
            c0955v.mInChangeScrap = false;
            c0955v.clearReturnedFromScrapFlag();
        }

        /* renamed from: c */
        void m4917c(View view) {
            C0955v e = RecyclerView.m222e(view);
            if (!e.hasAnyOfTheFlags(12) && e.isUpdated() && !this.f2508f.m260b(e)) {
                if (this.f2504b == null) {
                    this.f2504b = new ArrayList();
                }
                e.setScrapContainer(this, true);
                this.f2504b.add(e);
            } else if (!e.isInvalid() || e.isRemoved() || this.f2508f.f193l.hasStableIds()) {
                e.setScrapContainer(this, false);
                this.f2503a.add(e);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
            }
        }

        /* renamed from: d */
        void m4918d() {
            for (int size = this.f2505c.size() - 1; size >= 0; size--) {
                m4919d(size);
            }
            this.f2505c.clear();
            if (RecyclerView.f162J) {
                this.f2508f.f207z.m5448a();
            }
        }

        /* renamed from: d */
        void m4919d(int i) {
            m4904a((C0955v) this.f2505c.get(i), true);
            this.f2505c.remove(i);
        }

        /* renamed from: d */
        void m4920d(C0955v c0955v) {
            if (this.f2508f.f195n != null) {
                this.f2508f.f195n.m4930a(c0955v);
            }
            if (this.f2508f.f193l != null) {
                this.f2508f.f193l.onViewRecycled(c0955v);
            }
            if (this.f2508f.f169A != null) {
                this.f2508f.f188g.m5743g(c0955v);
            }
        }

        /* renamed from: e */
        int m4921e() {
            return this.f2503a.size();
        }

        /* renamed from: e */
        View m4922e(int i) {
            return ((C0955v) this.f2503a.get(i)).itemView;
        }

        /* renamed from: f */
        C0955v m4923f(int i) {
            int i2 = 0;
            if (this.f2504b != null) {
                int size = this.f2504b.size();
                if (size != 0) {
                    C0955v c0955v;
                    int i3 = 0;
                    while (i3 < size) {
                        c0955v = (C0955v) this.f2504b.get(i3);
                        if (c0955v.wasReturnedFromScrap() || c0955v.getLayoutPosition() != i) {
                            i3++;
                        } else {
                            c0955v.addFlags(32);
                            return c0955v;
                        }
                    }
                    if (this.f2508f.f193l.hasStableIds()) {
                        int b = this.f2508f.f186e.m5825b(i);
                        if (b > 0 && b < this.f2508f.f193l.getItemCount()) {
                            long itemId = this.f2508f.f193l.getItemId(b);
                            while (i2 < size) {
                                c0955v = (C0955v) this.f2504b.get(i2);
                                if (c0955v.wasReturnedFromScrap() || c0955v.getItemId() != itemId) {
                                    i2++;
                                } else {
                                    c0955v.addFlags(32);
                                    return c0955v;
                                }
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }

        /* renamed from: f */
        void m4924f() {
            this.f2503a.clear();
            if (this.f2504b != null) {
                this.f2504b.clear();
            }
        }

        /* renamed from: g */
        C0946n m4925g() {
            if (this.f2507e == null) {
                this.f2507e = new C0946n();
            }
            return this.f2507e;
        }

        /* renamed from: h */
        void m4926h() {
            int size = this.f2505c.size();
            for (int i = 0; i < size; i++) {
                C0955v c0955v = (C0955v) this.f2505c.get(i);
                if (c0955v != null) {
                    c0955v.addFlags(512);
                }
            }
        }

        /* renamed from: i */
        void m4927i() {
            if (this.f2508f.f193l == null || !this.f2508f.f193l.hasStableIds()) {
                m4918d();
                return;
            }
            int size = this.f2505c.size();
            for (int i = 0; i < size; i++) {
                C0955v c0955v = (C0955v) this.f2505c.get(i);
                if (c0955v != null) {
                    c0955v.addFlags(6);
                    c0955v.addChangePayload(null);
                }
            }
        }

        /* renamed from: j */
        void m4928j() {
            int i;
            int i2 = 0;
            int size = this.f2505c.size();
            for (i = 0; i < size; i++) {
                ((C0955v) this.f2505c.get(i)).clearOldPosition();
            }
            size = this.f2503a.size();
            for (i = 0; i < size; i++) {
                ((C0955v) this.f2503a.get(i)).clearOldPosition();
            }
            if (this.f2504b != null) {
                i = this.f2504b.size();
                while (i2 < i) {
                    ((C0955v) this.f2504b.get(i2)).clearOldPosition();
                    i2++;
                }
            }
        }

        /* renamed from: k */
        void m4929k() {
            int size = this.f2505c.size();
            for (int i = 0; i < size; i++) {
                C0908i c0908i = (C0908i) ((C0955v) this.f2505c.get(i)).itemView.getLayoutParams();
                if (c0908i != null) {
                    c0908i.f2405e = true;
                }
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$p */
    public interface C0948p {
        /* renamed from: a */
        void m4930a(C0955v c0955v);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$q */
    private class C0949q extends C0928c {
        /* renamed from: a */
        final /* synthetic */ RecyclerView f2512a;

        C0949q(RecyclerView recyclerView) {
            this.f2512a = recyclerView;
        }

        /* renamed from: a */
        public void mo875a() {
            this.f2512a.m246a(null);
            this.f2512a.f169A.f2531e = true;
            this.f2512a.m301t();
            if (!this.f2512a.f186e.m5831d()) {
                this.f2512a.requestLayout();
            }
        }

        /* renamed from: a */
        public void mo876a(int i, int i2, int i3) {
            this.f2512a.m246a(null);
            if (this.f2512a.f186e.m5823a(i, i2, i3)) {
                m4934b();
            }
        }

        /* renamed from: a */
        public void mo877a(int i, int i2, Object obj) {
            this.f2512a.m246a(null);
            if (this.f2512a.f186e.m5824a(i, i2, obj)) {
                m4934b();
            }
        }

        /* renamed from: b */
        void m4934b() {
            if (RecyclerView.f168c && this.f2512a.f198q && this.f2512a.f197p) {
                ah.m2787a(this.f2512a, this.f2512a.f190i);
                return;
            }
            this.f2512a.f202u = true;
            this.f2512a.requestLayout();
        }

        /* renamed from: b */
        public void mo878b(int i, int i2) {
            this.f2512a.m246a(null);
            if (this.f2512a.f186e.m5827b(i, i2)) {
                m4934b();
            }
        }

        /* renamed from: c */
        public void mo879c(int i, int i2) {
            this.f2512a.m246a(null);
            if (this.f2512a.f186e.m5830c(i, i2)) {
                m4934b();
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$r */
    public static abstract class C0951r {
        /* renamed from: a */
        private int f2520a;
        /* renamed from: b */
        private RecyclerView f2521b;
        /* renamed from: c */
        private C0910h f2522c;
        /* renamed from: d */
        private boolean f2523d;
        /* renamed from: e */
        private boolean f2524e;
        /* renamed from: f */
        private View f2525f;
        /* renamed from: g */
        private final C0950a f2526g;

        /* renamed from: android.support.v7.widget.RecyclerView$r$a */
        public static class C0950a {
            /* renamed from: a */
            private int f2513a;
            /* renamed from: b */
            private int f2514b;
            /* renamed from: c */
            private int f2515c;
            /* renamed from: d */
            private int f2516d;
            /* renamed from: e */
            private Interpolator f2517e;
            /* renamed from: f */
            private boolean f2518f;
            /* renamed from: g */
            private int f2519g;

            /* renamed from: b */
            private void m4937b() {
                if (this.f2517e != null && this.f2515c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.f2515c < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            /* renamed from: a */
            void m4938a(RecyclerView recyclerView) {
                if (this.f2516d >= 0) {
                    int i = this.f2516d;
                    this.f2516d = -1;
                    recyclerView.m254b(i);
                    this.f2518f = false;
                } else if (this.f2518f) {
                    m4937b();
                    if (this.f2517e != null) {
                        recyclerView.f205x.m4969a(this.f2513a, this.f2514b, this.f2515c, this.f2517e);
                    } else if (this.f2515c == Integer.MIN_VALUE) {
                        recyclerView.f205x.m4972b(this.f2513a, this.f2514b);
                    } else {
                        recyclerView.f205x.m4967a(this.f2513a, this.f2514b, this.f2515c);
                    }
                    this.f2519g++;
                    if (this.f2519g > 10) {
                        Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.f2518f = false;
                } else {
                    this.f2519g = 0;
                }
            }

            /* renamed from: a */
            boolean m4939a() {
                return this.f2516d >= 0;
            }
        }

        /* renamed from: a */
        private void m4940a(int i, int i2) {
            RecyclerView recyclerView = this.f2521b;
            if (!this.f2524e || this.f2520a == -1 || recyclerView == null) {
                m4943a();
            }
            this.f2523d = false;
            if (this.f2525f != null) {
                if (m4942a(this.f2525f) == this.f2520a) {
                    m4946a(this.f2525f, recyclerView.f169A, this.f2526g);
                    this.f2526g.m4938a(recyclerView);
                    m4943a();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    this.f2525f = null;
                }
            }
            if (this.f2524e) {
                m4945a(i, i2, recyclerView.f169A, this.f2526g);
                boolean a = this.f2526g.m4939a();
                this.f2526g.m4938a(recyclerView);
                if (!a) {
                    return;
                }
                if (this.f2524e) {
                    this.f2523d = true;
                    recyclerView.f205x.m4965a();
                    return;
                }
                m4943a();
            }
        }

        /* renamed from: a */
        public int m4942a(View view) {
            return this.f2521b.m281h(view);
        }

        /* renamed from: a */
        protected final void m4943a() {
            if (this.f2524e) {
                m4951e();
                this.f2521b.f169A.f2540n = -1;
                this.f2525f = null;
                this.f2520a = -1;
                this.f2523d = false;
                this.f2524e = false;
                this.f2522c.m4485a(this);
                this.f2522c = null;
                this.f2521b = null;
            }
        }

        /* renamed from: a */
        public void m4944a(int i) {
            this.f2520a = i;
        }

        /* renamed from: a */
        protected abstract void m4945a(int i, int i2, C0952s c0952s, C0950a c0950a);

        /* renamed from: a */
        protected abstract void m4946a(View view, C0952s c0952s, C0950a c0950a);

        /* renamed from: b */
        protected void m4947b(View view) {
            if (m4942a(view) == m4950d()) {
                this.f2525f = view;
            }
        }

        /* renamed from: b */
        public boolean m4948b() {
            return this.f2523d;
        }

        /* renamed from: c */
        public boolean m4949c() {
            return this.f2524e;
        }

        /* renamed from: d */
        public int m4950d() {
            return this.f2520a;
        }

        /* renamed from: e */
        protected abstract void m4951e();
    }

    /* renamed from: android.support.v7.widget.RecyclerView$s */
    public static class C0952s {
        /* renamed from: a */
        int f2527a = 0;
        /* renamed from: b */
        int f2528b = 0;
        /* renamed from: c */
        int f2529c = 1;
        /* renamed from: d */
        int f2530d = 0;
        /* renamed from: e */
        boolean f2531e = false;
        /* renamed from: f */
        boolean f2532f = false;
        /* renamed from: g */
        boolean f2533g = false;
        /* renamed from: h */
        boolean f2534h = false;
        /* renamed from: i */
        boolean f2535i = false;
        /* renamed from: j */
        boolean f2536j = false;
        /* renamed from: k */
        int f2537k;
        /* renamed from: l */
        long f2538l;
        /* renamed from: m */
        int f2539m;
        /* renamed from: n */
        private int f2540n = -1;
        /* renamed from: o */
        private SparseArray<Object> f2541o;

        /* renamed from: a */
        void m4953a(int i) {
            if ((this.f2529c & i) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i) + " but it is " + Integer.toBinaryString(this.f2529c));
            }
        }

        /* renamed from: a */
        void m4954a(C0926a c0926a) {
            this.f2529c = 1;
            this.f2530d = c0926a.getItemCount();
            this.f2531e = false;
            this.f2532f = false;
            this.f2533g = false;
            this.f2534h = false;
        }

        /* renamed from: a */
        public boolean m4955a() {
            return this.f2532f;
        }

        /* renamed from: b */
        public boolean m4956b() {
            return this.f2536j;
        }

        /* renamed from: c */
        public int m4957c() {
            return this.f2540n;
        }

        /* renamed from: d */
        public boolean m4958d() {
            return this.f2540n != -1;
        }

        /* renamed from: e */
        public int m4959e() {
            return this.f2532f ? this.f2527a - this.f2528b : this.f2530d;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.f2540n + ", mData=" + this.f2541o + ", mItemCount=" + this.f2530d + ", mPreviousLayoutItemCount=" + this.f2527a + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.f2528b + ", mStructureChanged=" + this.f2531e + ", mInPreLayout=" + this.f2532f + ", mRunSimpleAnimations=" + this.f2535i + ", mRunPredictiveAnimations=" + this.f2536j + '}';
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$t */
    public static abstract class C0953t {
        /* renamed from: a */
        public abstract View m4960a(C0947o c0947o, int i, int i2);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$u */
    class C0954u implements Runnable {
        /* renamed from: a */
        Interpolator f2542a = RecyclerView.f159G;
        /* renamed from: b */
        final /* synthetic */ RecyclerView f2543b;
        /* renamed from: c */
        private int f2544c;
        /* renamed from: d */
        private int f2545d;
        /* renamed from: e */
        private C0729x f2546e;
        /* renamed from: f */
        private boolean f2547f = false;
        /* renamed from: g */
        private boolean f2548g = false;

        public C0954u(RecyclerView recyclerView) {
            this.f2543b = recyclerView;
            this.f2546e = C0729x.m3542a(recyclerView.getContext(), RecyclerView.f159G);
        }

        /* renamed from: a */
        private float m4961a(float f) {
            return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
        }

        /* renamed from: b */
        private int m4962b(int i, int i2, int i3, int i4) {
            int round;
            int abs = Math.abs(i);
            int abs2 = Math.abs(i2);
            Object obj = abs > abs2 ? 1 : null;
            int sqrt = (int) Math.sqrt((double) ((i3 * i3) + (i4 * i4)));
            int sqrt2 = (int) Math.sqrt((double) ((i * i) + (i2 * i2)));
            int width = obj != null ? this.f2543b.getWidth() : this.f2543b.getHeight();
            int i5 = width / 2;
            float a = (m4961a(Math.min(1.0f, (((float) sqrt2) * 1.0f) / ((float) width))) * ((float) i5)) + ((float) i5);
            if (sqrt > 0) {
                round = Math.round(1000.0f * Math.abs(a / ((float) sqrt))) * 4;
            } else {
                round = (int) (((((float) (obj != null ? abs : abs2)) / ((float) width)) + 1.0f) * 300.0f);
            }
            return Math.min(round, 2000);
        }

        /* renamed from: c */
        private void m4963c() {
            this.f2548g = false;
            this.f2547f = true;
        }

        /* renamed from: d */
        private void m4964d() {
            this.f2547f = false;
            if (this.f2548g) {
                m4965a();
            }
        }

        /* renamed from: a */
        void m4965a() {
            if (this.f2547f) {
                this.f2548g = true;
                return;
            }
            this.f2543b.removeCallbacks(this);
            ah.m2787a(this.f2543b, (Runnable) this);
        }

        /* renamed from: a */
        public void m4966a(int i, int i2) {
            this.f2543b.setScrollState(2);
            this.f2545d = 0;
            this.f2544c = 0;
            this.f2546e.m3545a(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            m4965a();
        }

        /* renamed from: a */
        public void m4967a(int i, int i2, int i3) {
            m4969a(i, i2, i3, RecyclerView.f159G);
        }

        /* renamed from: a */
        public void m4968a(int i, int i2, int i3, int i4) {
            m4967a(i, i2, m4962b(i, i2, i3, i4));
        }

        /* renamed from: a */
        public void m4969a(int i, int i2, int i3, Interpolator interpolator) {
            if (this.f2542a != interpolator) {
                this.f2542a = interpolator;
                this.f2546e = C0729x.m3542a(this.f2543b.getContext(), interpolator);
            }
            this.f2543b.setScrollState(2);
            this.f2545d = 0;
            this.f2544c = 0;
            this.f2546e.m3544a(0, 0, i, i2, i3);
            m4965a();
        }

        /* renamed from: a */
        public void m4970a(int i, int i2, Interpolator interpolator) {
            int b = m4962b(i, i2, 0, 0);
            if (interpolator == null) {
                interpolator = RecyclerView.f159G;
            }
            m4969a(i, i2, b, interpolator);
        }

        /* renamed from: b */
        public void m4971b() {
            this.f2543b.removeCallbacks(this);
            this.f2546e.m3555h();
        }

        /* renamed from: b */
        public void m4972b(int i, int i2) {
            m4968a(i, i2, 0, 0);
        }

        public void run() {
            if (this.f2543b.f194m == null) {
                m4971b();
                return;
            }
            m4963c();
            this.f2543b.m263c();
            C0729x c0729x = this.f2546e;
            C0951r c0951r = this.f2543b.f194m.f2421t;
            if (c0729x.m3554g()) {
                int e;
                int i;
                int f;
                int i2;
                Object obj;
                Object obj2;
                int b = c0729x.m3549b();
                int c = c0729x.m3550c();
                int i3 = b - this.f2544c;
                int i4 = c - this.f2545d;
                int i5 = 0;
                int i6 = 0;
                this.f2544c = b;
                this.f2545d = c;
                int i7 = 0;
                int i8 = 0;
                if (this.f2543b.f193l != null) {
                    this.f2543b.m267d();
                    this.f2543b.m290k();
                    C0440j.m1922a("RV Scroll");
                    if (i3 != 0) {
                        i5 = this.f2543b.f194m.mo802a(i3, this.f2543b.f185d, this.f2543b.f169A);
                        i7 = i3 - i5;
                    }
                    if (i4 != 0) {
                        i6 = this.f2543b.f194m.mo813b(i4, this.f2543b.f185d, this.f2543b.f169A);
                        i8 = i4 - i6;
                    }
                    C0440j.m1921a();
                    this.f2543b.m304w();
                    this.f2543b.m291l();
                    this.f2543b.m247a(false);
                    if (!(c0951r == null || c0951r.m4948b() || !c0951r.m4949c())) {
                        e = this.f2543b.f169A.m4959e();
                        if (e == 0) {
                            c0951r.m4943a();
                            i = i7;
                            i7 = i6;
                            i6 = i;
                        } else if (c0951r.m4950d() >= e) {
                            c0951r.m4944a(e - 1);
                            c0951r.m4940a(i3 - i7, i4 - i8);
                            i = i7;
                            i7 = i6;
                            i6 = i;
                        } else {
                            c0951r.m4940a(i3 - i7, i4 - i8);
                        }
                        if (!this.f2543b.f196o.isEmpty()) {
                            this.f2543b.invalidate();
                        }
                        if (this.f2543b.getOverScrollMode() != 2) {
                            this.f2543b.m264c(i3, i4);
                        }
                        if (!(i6 == 0 && i8 == 0)) {
                            f = (int) c0729x.m3553f();
                            if (i6 == b) {
                                e = i6 >= 0 ? -f : i6 <= 0 ? f : 0;
                                i2 = e;
                            } else {
                                i2 = 0;
                            }
                            if (i8 != c) {
                                f = 0;
                            } else if (i8 < 0) {
                                f = -f;
                            } else if (i8 <= 0) {
                                f = 0;
                            }
                            if (this.f2543b.getOverScrollMode() != 2) {
                                this.f2543b.m269d(i2, f);
                            }
                            if ((i2 != 0 || i6 == b || c0729x.m3551d() == 0) && (f != 0 || i8 == c || c0729x.m3552e() == 0)) {
                                c0729x.m3555h();
                            }
                        }
                        if (!(i5 == 0 && i7 == 0)) {
                            this.f2543b.m285i(i5, i7);
                        }
                        if (!this.f2543b.awakenScrollBars()) {
                            this.f2543b.invalidate();
                        }
                        obj = (i4 == 0 && this.f2543b.f194m.mo823e() && i7 == i4) ? 1 : null;
                        obj2 = (i3 == 0 && this.f2543b.f194m.mo821d() && i5 == i3) ? 1 : null;
                        obj2 = ((i3 == 0 || i4 != 0) && obj2 == null && obj == null) ? null : 1;
                        if (!c0729x.m3547a() || obj2 == null) {
                            this.f2543b.setScrollState(0);
                            if (RecyclerView.f162J) {
                                this.f2543b.f207z.m5448a();
                            }
                        } else {
                            m4965a();
                            if (this.f2543b.f206y != null) {
                                this.f2543b.f206y.m5462a(this.f2543b, i3, i4);
                            }
                        }
                    }
                }
                i = i7;
                i7 = i6;
                i6 = i;
                if (this.f2543b.f196o.isEmpty()) {
                    this.f2543b.invalidate();
                }
                if (this.f2543b.getOverScrollMode() != 2) {
                    this.f2543b.m264c(i3, i4);
                }
                f = (int) c0729x.m3553f();
                if (i6 == b) {
                    i2 = 0;
                } else {
                    if (i6 >= 0) {
                        if (i6 <= 0) {
                        }
                    }
                    i2 = e;
                }
                if (i8 != c) {
                    f = 0;
                } else if (i8 < 0) {
                    f = -f;
                } else if (i8 <= 0) {
                    f = 0;
                }
                if (this.f2543b.getOverScrollMode() != 2) {
                    this.f2543b.m269d(i2, f);
                }
                c0729x.m3555h();
                this.f2543b.m285i(i5, i7);
                if (this.f2543b.awakenScrollBars()) {
                    this.f2543b.invalidate();
                }
                if (i4 == 0) {
                }
                if (i3 == 0) {
                }
                if (i3 == 0) {
                }
                if (c0729x.m3547a()) {
                }
                this.f2543b.setScrollState(0);
                if (RecyclerView.f162J) {
                    this.f2543b.f207z.m5448a();
                }
            }
            if (c0951r != null) {
                if (c0951r.m4948b()) {
                    c0951r.m4940a(0, 0);
                }
                if (!this.f2548g) {
                    c0951r.m4943a();
                }
            }
            m4964d();
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$v */
    public static abstract class C0955v {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private C0947o mScrapContainer = null;
        C0955v mShadowedHolder = null;
        C0955v mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public C0955v(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        private boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && ah.m2797b(this.itemView);
        }

        private void onEnteredHiddenState(RecyclerView recyclerView) {
            this.mWasImportantForAccessibilityBeforeHidden = ah.m2803d(this.itemView);
            recyclerView.m249a(this, 4);
        }

        private void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.m249a(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        private boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(1024);
            } else if ((this.mFlags & 1024) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(obj);
            }
        }

        void addFlags(int i) {
            this.mFlags |= i;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= -1025;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        void flagRemovedAndOffsetPosition(int i, int i2, boolean z) {
            addFlags(8);
            offsetPosition(i2, z);
            this.mPosition = i;
        }

        public final int getAdapterPosition() {
            return this.mOwnerRecyclerView == null ? -1 : this.mOwnerRecyclerView.m265d(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        List<Object> getUnmodifiedPayloads() {
            return (this.mFlags & 1024) == 0 ? (this.mPayloads == null || this.mPayloads.size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads : FULLUPDATE_PAYLOADS;
        }

        boolean hasAnyOfTheFlags(int i) {
            return (this.mFlags & i) != 0;
        }

        boolean isAdapterPositionUnknown() {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !ah.m2797b(this.itemView);
        }

        boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        boolean isScrap() {
            return this.mScrapContainer != null;
        }

        boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }

        boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            if (this.itemView.getLayoutParams() != null) {
                ((C0908i) this.itemView.getLayoutParams()).f2405e = true;
            }
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.m220c(this);
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        void setFlags(int i, int i2) {
            this.mFlags = (this.mFlags & (i2 ^ -1)) | (i & i2);
        }

        public final void setIsRecyclable(boolean z) {
            this.mIsRecyclableCount = z ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && this.mIsRecyclableCount == 1) {
                this.mFlags |= 16;
            } else if (z && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        void setScrapContainer(C0947o c0947o, boolean z) {
            this.mScrapContainer = c0947o;
            this.mInChangeScrap = z;
        }

        boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                stringBuilder.append(" scrap ").append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                stringBuilder.append(" invalid");
            }
            if (!isBound()) {
                stringBuilder.append(" unbound");
            }
            if (needsUpdate()) {
                stringBuilder.append(" update");
            }
            if (isRemoved()) {
                stringBuilder.append(" removed");
            }
            if (shouldIgnore()) {
                stringBuilder.append(" ignored");
            }
            if (isTmpDetached()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                stringBuilder.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unScrap() {
            this.mScrapContainer.m4916c(this);
        }

        boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }
    }

    static {
        boolean z = VERSION.SDK_INT == 18 || VERSION.SDK_INT == 19 || VERSION.SDK_INT == 20;
        f166a = z;
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int i) {
        boolean z = true;
        super(context, attributeSet, i);
        this.f175N = new C0949q(this);
        this.f185d = new C0947o(this);
        this.f188g = new bo();
        this.f190i = new C09161(this);
        this.f191j = new Rect();
        this.f177P = new Rect();
        this.f192k = new RectF();
        this.f196o = new ArrayList();
        this.f178Q = new ArrayList();
        this.f180S = 0;
        this.f203v = false;
        this.aa = 0;
        this.ab = 0;
        this.f204w = new ah();
        this.ag = 0;
        this.ah = -1;
        this.ar = Float.MIN_VALUE;
        this.as = true;
        this.f205x = new C0954u(this);
        this.f207z = f162J ? new C1015a() : null;
        this.f169A = new C0952s();
        this.f170B = false;
        this.f171C = false;
        this.av = new C0934f(this);
        this.f172D = false;
        this.ax = new int[2];
        this.az = new int[2];
        this.aA = new int[2];
        this.aB = new int[2];
        this.f174F = new ArrayList();
        this.aC = new C09172(this);
        this.aD = new C09204(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f161I, i, 0);
            this.f189h = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        } else {
            this.f189h = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.an = viewConfiguration.getScaledTouchSlop();
        this.ap = viewConfiguration.getScaledMinimumFlingVelocity();
        this.aq = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.f204w.m4828a(this.av);
        m233a();
        mo3515z();
        if (ah.m2803d(this) == 0) {
            ah.m2801c((View) this, 1);
        }
        this.f183V = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new aw(this));
        if (attributeSet != null) {
            obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0838c.RecyclerView, i, 0);
            String string = obtainStyledAttributes.getString(C0838c.RecyclerView_layoutManager);
            if (obtainStyledAttributes.getInt(C0838c.RecyclerView_android_descendantFocusability, -1) == -1) {
                setDescendantFocusability(262144);
            }
            obtainStyledAttributes.recycle();
            m206a(context, string, attributeSet, i, 0);
            if (VERSION.SDK_INT >= 21) {
                obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f160H, i, 0);
                z = obtainStyledAttributes.getBoolean(0, true);
                obtainStyledAttributes.recycle();
            }
        } else {
            setDescendantFocusability(262144);
        }
        setNestedScrollingEnabled(z);
    }

    /* renamed from: A */
    private boolean m188A() {
        int b = this.f187f.m5344b();
        for (int i = 0; i < b; i++) {
            C0955v e = m222e(this.f187f.m5346b(i));
            if (e != null && !e.shouldIgnore() && e.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: B */
    private void m189B() {
        this.f205x.m4971b();
        if (this.f194m != null) {
            this.f194m.m4499J();
        }
    }

    /* renamed from: C */
    private void m190C() {
        int i = 0;
        if (this.ac != null) {
            i = this.ac.m3426c();
        }
        if (this.ad != null) {
            i |= this.ad.m3426c();
        }
        if (this.ae != null) {
            i |= this.ae.m3426c();
        }
        if (this.af != null) {
            i |= this.af.m3426c();
        }
        if (i != 0) {
            ah.m2799c(this);
        }
    }

    /* renamed from: D */
    private void m191D() {
        if (this.ai != null) {
            this.ai.clear();
        }
        stopNestedScroll();
        m190C();
    }

    /* renamed from: E */
    private void m192E() {
        m191D();
        setScrollState(0);
    }

    /* renamed from: F */
    private void m193F() {
        int i = this.f182U;
        this.f182U = 0;
        if (i != 0 && m293m()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            C0510a.m2133a(obtain, i);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    /* renamed from: G */
    private boolean m194G() {
        return this.f204w != null && this.f194m.mo814b();
    }

    /* renamed from: H */
    private void m195H() {
        boolean z = true;
        if (this.f203v) {
            this.f186e.m5818a();
            this.f194m.mo838a(this);
        }
        if (m194G()) {
            this.f186e.m5826b();
        } else {
            this.f186e.m5832e();
        }
        boolean z2 = this.f170B || this.f171C;
        C0952s c0952s = this.f169A;
        boolean z3 = this.f199r && this.f204w != null && ((this.f203v || z2 || this.f194m.f2422u) && (!this.f203v || this.f193l.hasStableIds()));
        c0952s.f2535i = z3;
        C0952s c0952s2 = this.f169A;
        if (!(this.f169A.f2535i && z2 && !this.f203v && m194G())) {
            z = false;
        }
        c0952s2.f2536j = z;
    }

    /* renamed from: I */
    private void m196I() {
        View focusedChild = (this.as && hasFocus() && this.f193l != null) ? getFocusedChild() : null;
        C0955v d = focusedChild == null ? null : m266d(focusedChild);
        if (d == null) {
            m197J();
            return;
        }
        this.f169A.f2538l = this.f193l.hasStableIds() ? d.getItemId() : -1;
        C0952s c0952s = this.f169A;
        int adapterPosition = this.f203v ? -1 : d.isRemoved() ? d.mOldPosition : d.getAdapterPosition();
        c0952s.f2537k = adapterPosition;
        this.f169A.f2539m = m226o(d.itemView);
    }

    /* renamed from: J */
    private void m197J() {
        this.f169A.f2538l = -1;
        this.f169A.f2537k = -1;
        this.f169A.f2539m = -1;
    }

    /* renamed from: K */
    private View m198K() {
        int i = this.f169A.f2537k != -1 ? this.f169A.f2537k : 0;
        int e = this.f169A.m4959e();
        int i2 = i;
        while (i2 < e) {
            C0955v c = m261c(i2);
            if (c == null) {
                break;
            } else if (c.itemView.hasFocusable()) {
                return c.itemView;
            } else {
                i2++;
            }
        }
        for (i = Math.min(e, i) - 1; i >= 0; i--) {
            C0955v c2 = m261c(i);
            if (c2 == null) {
                return null;
            }
            if (c2.itemView.hasFocusable()) {
                return c2.itemView;
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: L */
    private void m199L() {
        /*
        r6 = this;
        r4 = -1;
        r1 = 0;
        r0 = r6.as;
        if (r0 == 0) goto L_0x0027;
    L_0x0007:
        r0 = r6.f193l;
        if (r0 == 0) goto L_0x0027;
    L_0x000b:
        r0 = r6.hasFocus();
        if (r0 == 0) goto L_0x0027;
    L_0x0011:
        r0 = r6.getDescendantFocusability();
        r2 = 393216; // 0x60000 float:5.51013E-40 double:1.942745E-318;
        if (r0 == r2) goto L_0x0027;
    L_0x0019:
        r0 = r6.getDescendantFocusability();
        r2 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        if (r0 != r2) goto L_0x0028;
    L_0x0021:
        r0 = r6.isFocused();
        if (r0 == 0) goto L_0x0028;
    L_0x0027:
        return;
    L_0x0028:
        r0 = r6.isFocused();
        if (r0 != 0) goto L_0x0056;
    L_0x002e:
        r0 = r6.getFocusedChild();
        r2 = f164L;
        if (r2 == 0) goto L_0x004e;
    L_0x0036:
        r2 = r0.getParent();
        if (r2 == 0) goto L_0x0042;
    L_0x003c:
        r2 = r0.hasFocus();
        if (r2 != 0) goto L_0x004e;
    L_0x0042:
        r0 = r6.f187f;
        r0 = r0.m5344b();
        if (r0 != 0) goto L_0x0056;
    L_0x004a:
        r6.requestFocus();
        goto L_0x0027;
    L_0x004e:
        r2 = r6.f187f;
        r0 = r2.m5349c(r0);
        if (r0 == 0) goto L_0x0027;
    L_0x0056:
        r0 = r6.f169A;
        r2 = r0.f2538l;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x00b3;
    L_0x005e:
        r0 = r6.f193l;
        r0 = r0.hasStableIds();
        if (r0 == 0) goto L_0x00b3;
    L_0x0066:
        r0 = r6.f169A;
        r2 = r0.f2538l;
        r0 = r6.m231a(r2);
    L_0x006e:
        if (r0 == 0) goto L_0x0082;
    L_0x0070:
        r2 = r6.f187f;
        r3 = r0.itemView;
        r2 = r2.m5349c(r3);
        if (r2 != 0) goto L_0x0082;
    L_0x007a:
        r2 = r0.itemView;
        r2 = r2.hasFocusable();
        if (r2 != 0) goto L_0x00ae;
    L_0x0082:
        r0 = r6.f187f;
        r0 = r0.m5344b();
        if (r0 <= 0) goto L_0x008e;
    L_0x008a:
        r1 = r6.m198K();
    L_0x008e:
        if (r1 == 0) goto L_0x0027;
    L_0x0090:
        r0 = r6.f169A;
        r0 = r0.f2539m;
        r2 = (long) r0;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x00b1;
    L_0x0099:
        r0 = r6.f169A;
        r0 = r0.f2539m;
        r0 = r1.findViewById(r0);
        if (r0 == 0) goto L_0x00b1;
    L_0x00a3:
        r2 = r0.isFocusable();
        if (r2 == 0) goto L_0x00b1;
    L_0x00a9:
        r0.requestFocus();
        goto L_0x0027;
    L_0x00ae:
        r1 = r0.itemView;
        goto L_0x008e;
    L_0x00b1:
        r0 = r1;
        goto L_0x00a9;
    L_0x00b3:
        r0 = r1;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.L():void");
    }

    /* renamed from: M */
    private void m200M() {
        int b;
        int i;
        C0955v e;
        boolean z = true;
        this.f169A.m4953a(1);
        this.f169A.f2534h = false;
        m267d();
        this.f188g.m5730a();
        m290k();
        m195H();
        m196I();
        C0952s c0952s = this.f169A;
        if (!(this.f169A.f2535i && this.f171C)) {
            z = false;
        }
        c0952s.f2533g = z;
        this.f171C = false;
        this.f170B = false;
        this.f169A.f2532f = this.f169A.f2536j;
        this.f169A.f2530d = this.f193l.getItemCount();
        m214a(this.ax);
        if (this.f169A.f2535i) {
            b = this.f187f.m5344b();
            for (i = 0; i < b; i++) {
                e = m222e(this.f187f.m5346b(i));
                if (!e.shouldIgnore() && (!e.isInvalid() || this.f193l.hasStableIds())) {
                    this.f188g.m5732a(e, this.f204w.m4826a(this.f169A, e, C0933e.m4824e(e), e.getUnmodifiedPayloads()));
                    if (!(!this.f169A.f2533g || !e.isUpdated() || e.isRemoved() || e.shouldIgnore() || e.isInvalid())) {
                        this.f188g.m5731a(m229a(e), e);
                    }
                }
            }
        }
        if (this.f169A.f2536j) {
            m299r();
            z = this.f169A.f2531e;
            this.f169A.f2531e = false;
            this.f194m.mo818c(this.f185d, this.f169A);
            this.f169A.f2531e = z;
            for (i = 0; i < this.f187f.m5344b(); i++) {
                e = m222e(this.f187f.m5346b(i));
                if (!(e.shouldIgnore() || this.f188g.m5740d(e))) {
                    b = C0933e.m4824e(e);
                    boolean hasAnyOfTheFlags = e.hasAnyOfTheFlags(MessagesController.UPDATE_MASK_CHANNEL);
                    if (!hasAnyOfTheFlags) {
                        b |= 4096;
                    }
                    C0932c a = this.f204w.m4826a(this.f169A, e, b, e.getUnmodifiedPayloads());
                    if (hasAnyOfTheFlags) {
                        m244a(e, a);
                    } else {
                        this.f188g.m5737b(e, a);
                    }
                }
            }
            m300s();
        } else {
            m300s();
        }
        m291l();
        m247a(false);
        this.f169A.f2529c = 2;
    }

    /* renamed from: N */
    private void m201N() {
        m267d();
        m290k();
        this.f169A.m4953a(6);
        this.f186e.m5832e();
        this.f169A.f2530d = this.f193l.getItemCount();
        this.f169A.f2528b = 0;
        this.f169A.f2532f = false;
        this.f194m.mo818c(this.f185d, this.f169A);
        this.f169A.f2531e = false;
        this.f176O = null;
        C0952s c0952s = this.f169A;
        boolean z = this.f169A.f2535i && this.f204w != null;
        c0952s.f2535i = z;
        this.f169A.f2529c = 4;
        m291l();
        m247a(false);
    }

    /* renamed from: O */
    private void m202O() {
        this.f169A.m4953a(4);
        m267d();
        m290k();
        this.f169A.f2529c = 1;
        if (this.f169A.f2535i) {
            for (int b = this.f187f.m5344b() - 1; b >= 0; b--) {
                C0955v e = m222e(this.f187f.m5346b(b));
                if (!e.shouldIgnore()) {
                    long a = m229a(e);
                    C0932c a2 = this.f204w.m4825a(this.f169A, e);
                    C0955v a3 = this.f188g.m5729a(a);
                    if (a3 == null || a3.shouldIgnore()) {
                        this.f188g.m5739c(e, a2);
                    } else {
                        boolean a4 = this.f188g.m5734a(a3);
                        boolean a5 = this.f188g.m5734a(e);
                        if (a4 && a3 == e) {
                            this.f188g.m5739c(e, a2);
                        } else {
                            C0932c b2 = this.f188g.m5735b(a3);
                            this.f188g.m5739c(e, a2);
                            C0932c c = this.f188g.m5738c(e);
                            if (b2 == null) {
                                m205a(a, e, a3);
                            } else {
                                m208a(a3, e, b2, c, a4, a5);
                            }
                        }
                    }
                }
            }
            this.f188g.m5733a(this.aD);
        }
        this.f194m.m4552b(this.f185d);
        this.f169A.f2527a = this.f169A.f2530d;
        this.f203v = false;
        this.f169A.f2535i = false;
        this.f169A.f2536j = false;
        this.f194m.f2422u = false;
        if (this.f185d.f2504b != null) {
            this.f185d.f2504b.clear();
        }
        if (this.f194m.f2426y) {
            this.f194m.f2425x = 0;
            this.f194m.f2426y = false;
            this.f185d.m4909b();
        }
        this.f194m.mo808a(this.f169A);
        m291l();
        m247a(false);
        this.f188g.m5730a();
        if (m224j(this.ax[0], this.ax[1])) {
            m285i(0, 0);
        }
        m199L();
        m197J();
    }

    /* renamed from: a */
    private String m203a(Context context, String str) {
        return str.charAt(0) == '.' ? context.getPackageName() + str : !str.contains(".") ? RecyclerView.class.getPackage().getName() + '.' + str : str;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private void m204a(float r8, float r9, float r10, float r11) {
        /*
        r7 = this;
        r6 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = 1;
        r5 = 0;
        r1 = 0;
        r2 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1));
        if (r2 >= 0) goto L_0x0050;
    L_0x0009:
        r7.m274f();
        r2 = r7.ac;
        r3 = -r9;
        r4 = r7.getWidth();
        r4 = (float) r4;
        r3 = r3 / r4;
        r4 = r7.getHeight();
        r4 = (float) r4;
        r4 = r10 / r4;
        r4 = r6 - r4;
        r2 = r2.m3422a(r3, r4);
        if (r2 == 0) goto L_0x0025;
    L_0x0024:
        r1 = r0;
    L_0x0025:
        r2 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1));
        if (r2 >= 0) goto L_0x006f;
    L_0x0029:
        r7.m282h();
        r2 = r7.ad;
        r3 = -r11;
        r4 = r7.getHeight();
        r4 = (float) r4;
        r3 = r3 / r4;
        r4 = r7.getWidth();
        r4 = (float) r4;
        r4 = r8 / r4;
        r2 = r2.m3422a(r3, r4);
        if (r2 == 0) goto L_0x008e;
    L_0x0042:
        if (r0 != 0) goto L_0x004c;
    L_0x0044:
        r0 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1));
        if (r0 != 0) goto L_0x004c;
    L_0x0048:
        r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1));
        if (r0 == 0) goto L_0x004f;
    L_0x004c:
        android.support.v4.view.ah.m2799c(r7);
    L_0x004f:
        return;
    L_0x0050:
        r2 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1));
        if (r2 <= 0) goto L_0x0025;
    L_0x0054:
        r7.m278g();
        r2 = r7.ae;
        r3 = r7.getWidth();
        r3 = (float) r3;
        r3 = r9 / r3;
        r4 = r7.getHeight();
        r4 = (float) r4;
        r4 = r10 / r4;
        r2 = r2.m3422a(r3, r4);
        if (r2 == 0) goto L_0x0025;
    L_0x006d:
        r1 = r0;
        goto L_0x0025;
    L_0x006f:
        r2 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1));
        if (r2 <= 0) goto L_0x008e;
    L_0x0073:
        r7.m284i();
        r2 = r7.af;
        r3 = r7.getHeight();
        r3 = (float) r3;
        r3 = r11 / r3;
        r4 = r7.getWidth();
        r4 = (float) r4;
        r4 = r8 / r4;
        r4 = r6 - r4;
        r2 = r2.m3422a(r3, r4);
        if (r2 != 0) goto L_0x0042;
    L_0x008e:
        r0 = r1;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.a(float, float, float, float):void");
    }

    /* renamed from: a */
    private void m205a(long j, C0955v c0955v, C0955v c0955v2) {
        int b = this.f187f.m5344b();
        int i = 0;
        while (i < b) {
            C0955v e = m222e(this.f187f.m5346b(i));
            if (e == c0955v || m229a(e) != j) {
                i++;
            } else if (this.f193l == null || !this.f193l.hasStableIds()) {
                throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + e + " \n View Holder 2:" + c0955v);
            } else {
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + e + " \n View Holder 2:" + c0955v);
            }
        }
        Log.e("RecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + c0955v2 + " cannot be found but it is necessary for " + c0955v);
    }

    /* renamed from: a */
    private void m206a(Context context, String str, AttributeSet attributeSet, int i, int i2) {
        if (str != null) {
            String trim = str.trim();
            if (trim.length() != 0) {
                String a = m203a(context, trim);
                try {
                    Object[] objArr;
                    Constructor constructor;
                    Class asSubclass = (isInEditMode() ? getClass().getClassLoader() : context.getClassLoader()).loadClass(a).asSubclass(C0910h.class);
                    try {
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i), Integer.valueOf(i2)};
                        constructor = asSubclass.getConstructor(f165M);
                    } catch (Throwable e) {
                        constructor = asSubclass.getConstructor(new Class[0]);
                        objArr = null;
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((C0910h) constructor.newInstance(objArr));
                } catch (Throwable e2) {
                    e2.initCause(e);
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + a, e2);
                } catch (Throwable e3) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + a, e3);
                } catch (Throwable e32) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + a, e32);
                } catch (Throwable e322) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + a, e322);
                } catch (Throwable e3222) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + a, e3222);
                } catch (Throwable e32222) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + a, e32222);
                }
            }
        }
    }

    /* renamed from: a */
    private void m207a(C0926a c0926a, boolean z, boolean z2) {
        if (this.f193l != null) {
            this.f193l.unregisterAdapterDataObserver(this.f175N);
            this.f193l.onDetachedFromRecyclerView(this);
        }
        if (!z || z2) {
            m253b();
        }
        this.f186e.m5818a();
        C0926a c0926a2 = this.f193l;
        this.f193l = c0926a;
        if (c0926a != null) {
            c0926a.registerAdapterDataObserver(this.f175N);
            c0926a.onAttachedToRecyclerView(this);
        }
        if (this.f194m != null) {
            this.f194m.m4514a(c0926a2, this.f193l);
        }
        this.f185d.m4901a(c0926a2, this.f193l, z);
        this.f169A.f2531e = true;
        m302u();
    }

    /* renamed from: a */
    private void m208a(C0955v c0955v, C0955v c0955v2, C0932c c0932c, C0932c c0932c2, boolean z, boolean z2) {
        c0955v.setIsRecyclable(false);
        if (z) {
            m223e(c0955v);
        }
        if (c0955v != c0955v2) {
            if (z2) {
                m223e(c0955v2);
            }
            c0955v.mShadowedHolder = c0955v2;
            m223e(c0955v);
            this.f185d.m4916c(c0955v);
            c0955v2.setIsRecyclable(false);
            c0955v2.mShadowingHolder = c0955v;
        }
        if (this.f204w.mo918a(c0955v, c0955v2, c0932c, c0932c2)) {
            m296o();
        }
    }

    /* renamed from: a */
    static void m212a(View view, Rect rect) {
        C0908i c0908i = (C0908i) view.getLayoutParams();
        Rect rect2 = c0908i.f2404d;
        rect.set((view.getLeft() - rect2.left) - c0908i.leftMargin, (view.getTop() - rect2.top) - c0908i.topMargin, (view.getRight() + rect2.right) + c0908i.rightMargin, c0908i.bottomMargin + (rect2.bottom + view.getBottom()));
    }

    /* renamed from: a */
    private void m213a(View view, View view2) {
        boolean z = true;
        View view3 = view2 != null ? view2 : view;
        this.f191j.set(0, 0, view3.getWidth(), view3.getHeight());
        LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof C0908i) {
            C0908i c0908i = (C0908i) layoutParams;
            if (!c0908i.f2405e) {
                Rect rect = c0908i.f2404d;
                Rect rect2 = this.f191j;
                rect2.left -= rect.left;
                rect2 = this.f191j;
                rect2.right += rect.right;
                rect2 = this.f191j;
                rect2.top -= rect.top;
                rect2 = this.f191j;
                rect2.bottom = rect.bottom + rect2.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.f191j);
            offsetRectIntoDescendantCoords(view, this.f191j);
        }
        C0910h c0910h = this.f194m;
        Rect rect3 = this.f191j;
        boolean z2 = !this.f199r;
        if (view2 != null) {
            z = false;
        }
        c0910h.m4543a(this, view, rect3, z2, z);
    }

    /* renamed from: a */
    private void m214a(int[] iArr) {
        int b = this.f187f.m5344b();
        if (b == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        int i3 = 0;
        while (i3 < b) {
            int i4;
            C0955v e = m222e(this.f187f.m5346b(i3));
            if (e.shouldIgnore()) {
                i4 = i;
            } else {
                i4 = e.getLayoutPosition();
                if (i4 < i) {
                    i = i4;
                }
                if (i4 > i2) {
                    i2 = i4;
                    i4 = i;
                } else {
                    i4 = i;
                }
            }
            i3++;
            i = i4;
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    /* renamed from: a */
    private boolean m216a(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.f179R = null;
        }
        int size = this.f178Q.size();
        int i = 0;
        while (i < size) {
            C0943l c0943l = (C0943l) this.f178Q.get(i);
            if (!c0943l.mo885a(this, motionEvent) || action == 3) {
                i++;
            } else {
                this.f179R = c0943l;
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private boolean m217a(View view, View view2, int i) {
        int i2 = 0;
        if (view2 == null || view2 == this) {
            return false;
        }
        if (view == null) {
            return true;
        }
        if (i != 2 && i != 1) {
            return m219b(view, view2, i);
        }
        int i3 = this.f194m.m4613u() == 1 ? 1 : 0;
        if (i == 2) {
            i2 = 1;
        }
        return m219b(view, view2, (i2 ^ i3) != 0 ? 66 : 17) ? true : i == 2 ? m219b(view, view2, (int) TsExtractor.TS_STREAM_TYPE_HDMV_DTS) : m219b(view, view2, 33);
    }

    /* renamed from: b */
    private boolean m218b(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.f179R != null) {
            if (action == 0) {
                this.f179R = null;
            } else {
                this.f179R.mo886b(this, motionEvent);
                if (action == 3 || action == 1) {
                    this.f179R = null;
                }
                return true;
            }
        }
        if (action != 0) {
            int size = this.f178Q.size();
            for (int i = 0; i < size; i++) {
                C0943l c0943l = (C0943l) this.f178Q.get(i);
                if (c0943l.mo885a(this, motionEvent)) {
                    this.f179R = c0943l;
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b */
    private boolean m219b(View view, View view2, int i) {
        this.f191j.set(0, 0, view.getWidth(), view.getHeight());
        this.f177P.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.f191j);
        offsetDescendantRectToMyCoords(view2, this.f177P);
        switch (i) {
            case 17:
                return (this.f191j.right > this.f177P.right || this.f191j.left >= this.f177P.right) && this.f191j.left > this.f177P.left;
            case 33:
                return (this.f191j.bottom > this.f177P.bottom || this.f191j.top >= this.f177P.bottom) && this.f191j.top > this.f177P.top;
            case 66:
                return (this.f191j.left < this.f177P.left || this.f191j.right <= this.f177P.left) && this.f191j.right < this.f177P.right;
            case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
                return (this.f191j.top < this.f177P.top || this.f191j.bottom <= this.f177P.top) && this.f191j.bottom < this.f177P.bottom;
            default:
                throw new IllegalArgumentException("direction must be absolute. received:" + i);
        }
    }

    /* renamed from: c */
    static void m220c(C0955v c0955v) {
        if (c0955v.mNestedRecyclerView != null) {
            View view = (View) c0955v.mNestedRecyclerView.get();
            while (view != null) {
                if (view != c0955v.itemView) {
                    ViewParent parent = view.getParent();
                    view = parent instanceof View ? (View) parent : null;
                } else {
                    return;
                }
            }
            c0955v.mNestedRecyclerView = null;
        }
    }

    /* renamed from: c */
    private void m221c(MotionEvent motionEvent) {
        int b = C0659t.m3206b(motionEvent);
        if (motionEvent.getPointerId(b) == this.ah) {
            b = b == 0 ? 1 : 0;
            this.ah = motionEvent.getPointerId(b);
            int x = (int) (motionEvent.getX(b) + 0.5f);
            this.al = x;
            this.aj = x;
            b = (int) (motionEvent.getY(b) + 0.5f);
            this.am = b;
            this.ak = b;
        }
    }

    /* renamed from: e */
    static C0955v m222e(View view) {
        return view == null ? null : ((C0908i) view.getLayoutParams()).f2403c;
    }

    /* renamed from: e */
    private void m223e(C0955v c0955v) {
        View view = c0955v.itemView;
        boolean z = view.getParent() == this;
        this.f185d.m4916c(m252b(view));
        if (c0955v.isTmpDetached()) {
            this.f187f.m5341a(view, -1, view.getLayoutParams(), true);
        } else if (z) {
            this.f187f.m5351d(view);
        } else {
            this.f187f.m5343a(view, true);
        }
    }

    private float getScrollFactor() {
        if (this.ar == Float.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            if (!getContext().getTheme().resolveAttribute(16842829, typedValue, true)) {
                return BitmapDescriptorFactory.HUE_RED;
            }
            this.ar = typedValue.getDimension(getContext().getResources().getDisplayMetrics());
        }
        return this.ar;
    }

    private C0661w getScrollingChildHelper() {
        if (this.ay == null) {
            this.ay = new C0661w(this);
        }
        return this.ay;
    }

    /* renamed from: j */
    private boolean m224j(int i, int i2) {
        m214a(this.ax);
        return (this.ax[0] == i && this.ax[1] == i2) ? false : true;
    }

    /* renamed from: l */
    static RecyclerView m225l(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView l = m225l(viewGroup.getChildAt(i));
            if (l != null) {
                return l;
            }
        }
        return null;
    }

    /* renamed from: o */
    private int m226o(View view) {
        int id = view.getId();
        View view2 = view;
        while (!view2.isFocused() && (view2 instanceof ViewGroup) && view2.hasFocus()) {
            view = ((ViewGroup) view2).getFocusedChild();
            id = view.getId() != -1 ? view.getId() : id;
            view2 = view;
        }
        return id;
    }

    /* renamed from: z */
    private void mo3515z() {
        this.f187f = new ae(new C09225(this));
    }

    /* renamed from: a */
    long m229a(C0955v c0955v) {
        return this.f193l.hasStableIds() ? c0955v.getItemId() : (long) c0955v.mPosition;
    }

    /* renamed from: a */
    C0955v m230a(int i, boolean z) {
        int c = this.f187f.m5347c();
        C0955v c0955v = null;
        for (int i2 = 0; i2 < c; i2++) {
            C0955v e = m222e(this.f187f.m5350d(i2));
            if (!(e == null || e.isRemoved())) {
                if (z) {
                    if (e.mPosition != i) {
                        continue;
                    }
                } else if (e.getLayoutPosition() != i) {
                    continue;
                }
                if (!this.f187f.m5349c(e.itemView)) {
                    return e;
                }
                c0955v = e;
            }
        }
        return c0955v;
    }

    /* renamed from: a */
    public C0955v m231a(long j) {
        if (this.f193l == null || !this.f193l.hasStableIds()) {
            return null;
        }
        int c = this.f187f.m5347c();
        int i = 0;
        C0955v c0955v = null;
        while (i < c) {
            C0955v e = m222e(this.f187f.m5350d(i));
            if (e == null || e.isRemoved() || e.getItemId() != j) {
                e = c0955v;
            } else if (!this.f187f.m5349c(e.itemView)) {
                return e;
            }
            i++;
            c0955v = e;
        }
        return c0955v;
    }

    /* renamed from: a */
    public View m232a(float f, float f2) {
        for (int b = this.f187f.m5344b() - 1; b >= 0; b--) {
            View b2 = this.f187f.m5346b(b);
            float l = ah.m2822l(b2);
            float m = ah.m2823m(b2);
            if (f >= ((float) b2.getLeft()) + l && f <= l + ((float) b2.getRight()) && f2 >= ((float) b2.getTop()) + m && f2 <= ((float) b2.getBottom()) + m) {
                return b2;
            }
        }
        return null;
    }

    /* renamed from: a */
    void m233a() {
        this.f186e = new C1060f(new C09246(this));
    }

    /* renamed from: a */
    public void m234a(int i) {
        if (!this.f201t) {
            m270e();
            if (this.f194m == null) {
                Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            this.f194m.mo820d(i);
            awakenScrollBars();
        }
    }

    /* renamed from: a */
    public void m235a(int i, int i2) {
        m236a(i, i2, null);
    }

    /* renamed from: a */
    public void m236a(int i, int i2, Interpolator interpolator) {
        int i3 = 0;
        if (this.f194m == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.f201t) {
            if (!this.f194m.mo821d()) {
                i = 0;
            }
            if (this.f194m.mo823e()) {
                i3 = i2;
            }
            if (i != 0 || i3 != 0) {
                this.f205x.m4970a(i, i3, interpolator);
            }
        }
    }

    /* renamed from: a */
    void m237a(int i, int i2, Object obj) {
        int c = this.f187f.m5347c();
        int i3 = i + i2;
        for (int i4 = 0; i4 < c; i4++) {
            View d = this.f187f.m5350d(i4);
            C0955v e = m222e(d);
            if (e != null && !e.shouldIgnore() && e.mPosition >= i && e.mPosition < i3) {
                e.addFlags(2);
                e.addChangePayload(obj);
                ((C0908i) d.getLayoutParams()).f2405e = true;
            }
        }
        this.f185d.m4915c(i, i2);
    }

    /* renamed from: a */
    void m238a(int i, int i2, boolean z) {
        int i3 = i + i2;
        int c = this.f187f.m5347c();
        for (int i4 = 0; i4 < c; i4++) {
            C0955v e = m222e(this.f187f.m5350d(i4));
            if (!(e == null || e.shouldIgnore())) {
                if (e.mPosition >= i3) {
                    e.offsetPosition(-i2, z);
                    this.f169A.f2531e = true;
                } else if (e.mPosition >= i) {
                    e.flagRemovedAndOffsetPosition(i - 1, -i2, z);
                    this.f169A.f2531e = true;
                }
            }
        }
        this.f185d.m4900a(i, i2, z);
        requestLayout();
    }

    /* renamed from: a */
    public void m239a(C0935g c0935g) {
        m240a(c0935g, -1);
    }

    /* renamed from: a */
    public void m240a(C0935g c0935g, int i) {
        if (this.f194m != null) {
            this.f194m.mo812a("Cannot add item decoration during a scroll  or layout");
        }
        if (this.f196o.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i < 0) {
            this.f196o.add(c0935g);
        } else {
            this.f196o.add(i, c0935g);
        }
        m298q();
        requestLayout();
    }

    /* renamed from: a */
    public void m241a(C0941j c0941j) {
        if (this.f184W == null) {
            this.f184W = new ArrayList();
        }
        this.f184W.add(c0941j);
    }

    /* renamed from: a */
    public void m242a(C0943l c0943l) {
        this.f178Q.add(c0943l);
    }

    /* renamed from: a */
    public void m243a(C0944m c0944m) {
        if (this.au == null) {
            this.au = new ArrayList();
        }
        this.au.add(c0944m);
    }

    /* renamed from: a */
    void m244a(C0955v c0955v, C0932c c0932c) {
        c0955v.setFlags(0, MessagesController.UPDATE_MASK_CHANNEL);
        if (this.f169A.f2533g && c0955v.isUpdated() && !c0955v.isRemoved() && !c0955v.shouldIgnore()) {
            this.f188g.m5731a(m229a(c0955v), c0955v);
        }
        this.f188g.m5732a(c0955v, c0932c);
    }

    /* renamed from: a */
    void m245a(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
        c0955v.setIsRecyclable(false);
        if (this.f204w.mo919b(c0955v, c0932c, c0932c2)) {
            m296o();
        }
    }

    /* renamed from: a */
    void m246a(String str) {
        if (m295n()) {
            if (str == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
            }
            throw new IllegalStateException(str);
        } else if (this.ab > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(TtmlNode.ANONYMOUS_REGION_ID));
        }
    }

    /* renamed from: a */
    void m247a(boolean z) {
        if (this.f180S < 1) {
            this.f180S = 1;
        }
        if (!z) {
            this.f200s = false;
        }
        if (this.f180S == 1) {
            if (!(!z || !this.f200s || this.f201t || this.f194m == null || this.f193l == null)) {
                m297p();
            }
            if (!this.f201t) {
                this.f200s = false;
            }
        }
        this.f180S--;
    }

    /* renamed from: a */
    boolean m248a(int i, int i2, MotionEvent motionEvent) {
        int a;
        int i3;
        int i4;
        int i5;
        m263c();
        if (this.f193l != null) {
            int b;
            m267d();
            m290k();
            C0440j.m1922a("RV Scroll");
            if (i != 0) {
                a = this.f194m.mo802a(i, this.f185d, this.f169A);
                i3 = i - a;
            } else {
                a = 0;
                i3 = 0;
            }
            if (i2 != 0) {
                b = this.f194m.mo813b(i2, this.f185d, this.f169A);
                i4 = i2 - b;
            } else {
                b = 0;
                i4 = 0;
            }
            C0440j.m1921a();
            m304w();
            m291l();
            m247a(false);
            i5 = i4;
            i4 = a;
            a = b;
        } else {
            a = 0;
            i4 = 0;
            i5 = 0;
            i3 = 0;
        }
        if (!this.f196o.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll(i4, a, i3, i5, this.az)) {
            this.al -= this.az[0];
            this.am -= this.az[1];
            if (motionEvent != null) {
                motionEvent.offsetLocation((float) this.az[0], (float) this.az[1]);
            }
            int[] iArr = this.aB;
            iArr[0] = iArr[0] + this.az[0];
            iArr = this.aB;
            iArr[1] = iArr[1] + this.az[1];
        } else if (getOverScrollMode() != 2) {
            if (motionEvent != null) {
                m204a(motionEvent.getX(), (float) i3, motionEvent.getY(), (float) i5);
            }
            m264c(i, i2);
        }
        if (!(i4 == 0 && a == 0)) {
            m285i(i4, a);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        return (i4 == 0 && a == 0) ? false : true;
    }

    /* renamed from: a */
    boolean m249a(C0955v c0955v, int i) {
        if (m295n()) {
            c0955v.mPendingAccessibilityState = i;
            this.f174F.add(c0955v);
            return false;
        }
        ah.m2801c(c0955v.itemView, i);
        return true;
    }

    /* renamed from: a */
    boolean m250a(View view) {
        m267d();
        boolean f = this.f187f.m5354f(view);
        if (f) {
            C0955v e = m222e(view);
            this.f185d.m4916c(e);
            this.f185d.m4911b(e);
        }
        m247a(!f);
        return f;
    }

    /* renamed from: a */
    boolean m251a(AccessibilityEvent accessibilityEvent) {
        int i = 0;
        if (!m295n()) {
            return false;
        }
        int b = accessibilityEvent != null ? C0510a.m2134b(accessibilityEvent) : 0;
        if (b != 0) {
            i = b;
        }
        this.f182U = i | this.f182U;
        return true;
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (this.f194m == null || !this.f194m.m4545a(this, (ArrayList) arrayList, i, i2)) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    /* renamed from: b */
    public C0955v m252b(View view) {
        Object parent = view.getParent();
        if (parent == null || parent == this) {
            return m222e(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    /* renamed from: b */
    void m253b() {
        if (this.f204w != null) {
            this.f204w.mo929d();
        }
        if (this.f194m != null) {
            this.f194m.m4564c(this.f185d);
            this.f194m.m4552b(this.f185d);
        }
        this.f185d.m4897a();
    }

    /* renamed from: b */
    void m254b(int i) {
        if (this.f194m != null) {
            this.f194m.mo820d(i);
            awakenScrollBars();
        }
    }

    /* renamed from: b */
    public void m255b(C0935g c0935g) {
        if (this.f194m != null) {
            this.f194m.mo812a("Cannot remove item decoration during a scroll  or layout");
        }
        this.f196o.remove(c0935g);
        if (this.f196o.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        m298q();
        requestLayout();
    }

    /* renamed from: b */
    public void m256b(C0941j c0941j) {
        if (this.f184W != null) {
            this.f184W.remove(c0941j);
        }
    }

    /* renamed from: b */
    public void m257b(C0943l c0943l) {
        this.f178Q.remove(c0943l);
        if (this.f179R == c0943l) {
            this.f179R = null;
        }
    }

    /* renamed from: b */
    void m258b(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
        m223e(c0955v);
        c0955v.setIsRecyclable(false);
        if (this.f204w.mo917a(c0955v, c0932c, c0932c2)) {
            m296o();
        }
    }

    /* renamed from: b */
    public boolean m259b(int i, int i2) {
        if (this.f194m == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.f201t) {
            return false;
        } else {
            boolean d = this.f194m.mo821d();
            boolean e = this.f194m.mo823e();
            if (!d || Math.abs(i) < this.ap) {
                i = 0;
            }
            if (!e || Math.abs(i2) < this.ap) {
                i2 = 0;
            }
            if ((i == 0 && i2 == 0) || dispatchNestedPreFling((float) i, (float) i2)) {
                return false;
            }
            d = d || e;
            dispatchNestedFling((float) i, (float) i2, d);
            if (this.ao != null && this.ao.m4872a(i, i2)) {
                return true;
            }
            if (!d) {
                return false;
            }
            this.f205x.m4966a(Math.max(-this.aq, Math.min(i, this.aq)), Math.max(-this.aq, Math.min(i2, this.aq)));
            return true;
        }
    }

    /* renamed from: b */
    boolean m260b(C0955v c0955v) {
        return this.f204w == null || this.f204w.mo926a(c0955v, c0955v.getUnmodifiedPayloads());
    }

    /* renamed from: c */
    public C0955v m261c(int i) {
        if (this.f203v) {
            return null;
        }
        int c = this.f187f.m5347c();
        int i2 = 0;
        C0955v c0955v = null;
        while (i2 < c) {
            C0955v e = m222e(this.f187f.m5350d(i2));
            if (e == null || e.isRemoved() || m265d(e) != i) {
                e = c0955v;
            } else if (!this.f187f.m5349c(e.itemView)) {
                return e;
            }
            i2++;
            c0955v = e;
        }
        return c0955v;
    }

    /* renamed from: c */
    public View m262c(View view) {
        RecyclerView parent = view.getParent();
        View view2 = view;
        while (parent != null && parent != this && (parent instanceof View)) {
            View view3 = parent;
            view2 = view3;
            ViewParent parent2 = view3.getParent();
        }
        return parent == this ? view2 : null;
    }

    /* renamed from: c */
    void m263c() {
        if (!this.f199r || this.f203v) {
            C0440j.m1922a("RV FullInvalidate");
            m297p();
            C0440j.m1921a();
        } else if (!this.f186e.m5831d()) {
        } else {
            if (this.f186e.m5822a(4) && !this.f186e.m5822a(11)) {
                C0440j.m1922a("RV PartialInvalidate");
                m267d();
                m290k();
                this.f186e.m5826b();
                if (!this.f200s) {
                    if (m188A()) {
                        m297p();
                    } else {
                        this.f186e.m5829c();
                    }
                }
                m247a(true);
                m291l();
                C0440j.m1921a();
            } else if (this.f186e.m5831d()) {
                C0440j.m1922a("RV FullInvalidate");
                m297p();
                C0440j.m1921a();
            }
        }
    }

    /* renamed from: c */
    void m264c(int i, int i2) {
        int i3 = 0;
        if (!(this.ac == null || this.ac.m3420a() || i <= 0)) {
            i3 = this.ac.m3426c();
        }
        if (!(this.ae == null || this.ae.m3420a() || i >= 0)) {
            i3 |= this.ae.m3426c();
        }
        if (!(this.ad == null || this.ad.m3420a() || i2 <= 0)) {
            i3 |= this.ad.m3426c();
        }
        if (!(this.af == null || this.af.m3420a() || i2 >= 0)) {
            i3 |= this.af.m3426c();
        }
        if (i3 != 0) {
            ah.m2799c(this);
        }
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return (layoutParams instanceof C0908i) && this.f194m.mo843a((C0908i) layoutParams);
    }

    public int computeHorizontalScrollExtent() {
        return (this.f194m != null && this.f194m.mo821d()) ? this.f194m.mo822e(this.f169A) : 0;
    }

    public int computeHorizontalScrollOffset() {
        return (this.f194m != null && this.f194m.mo821d()) ? this.f194m.mo815c(this.f169A) : 0;
    }

    public int computeHorizontalScrollRange() {
        return (this.f194m != null && this.f194m.mo821d()) ? this.f194m.mo825g(this.f169A) : 0;
    }

    public int computeVerticalScrollExtent() {
        return (this.f194m != null && this.f194m.mo823e()) ? this.f194m.mo824f(this.f169A) : 0;
    }

    public int computeVerticalScrollOffset() {
        return (this.f194m != null && this.f194m.mo823e()) ? this.f194m.mo819d(this.f169A) : 0;
    }

    public int computeVerticalScrollRange() {
        return (this.f194m != null && this.f194m.mo823e()) ? this.f194m.mo826h(this.f169A) : 0;
    }

    /* renamed from: d */
    int m265d(C0955v c0955v) {
        return (c0955v.hasAnyOfTheFlags(524) || !c0955v.isBound()) ? -1 : this.f186e.m5828c(c0955v.mPosition);
    }

    /* renamed from: d */
    public C0955v m266d(View view) {
        View c = m262c(view);
        return c == null ? null : m252b(c);
    }

    /* renamed from: d */
    void m267d() {
        this.f180S++;
        if (this.f180S == 1 && !this.f201t) {
            this.f200s = false;
        }
    }

    /* renamed from: d */
    public void m268d(int i) {
        int b = this.f187f.m5344b();
        for (int i2 = 0; i2 < b; i2++) {
            this.f187f.m5346b(i2).offsetTopAndBottom(i);
        }
    }

    /* renamed from: d */
    void m269d(int i, int i2) {
        if (i < 0) {
            m274f();
            this.ac.m3423a(-i);
        } else if (i > 0) {
            m278g();
            this.ae.m3423a(i);
        }
        if (i2 < 0) {
            m282h();
            this.ad.m3423a(-i2);
        } else if (i2 > 0) {
            m284i();
            this.af.m3423a(i2);
        }
        if (i != 0 || i2 != 0) {
            ah.m2799c(this);
        }
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return getScrollingChildHelper().m3211a(f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return getScrollingChildHelper().m3210a(f, f2);
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().m3214a(i, i2, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return getScrollingChildHelper().m3213a(i, i2, i3, i4, iArr);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    public void draw(Canvas canvas) {
        int i;
        int i2;
        int i3 = 1;
        int i4 = 0;
        super.draw(canvas);
        int size = this.f196o.size();
        for (i = 0; i < size; i++) {
            ((C0935g) this.f196o.get(i)).mo896b(canvas, this, this.f169A);
        }
        if (this.ac == null || this.ac.m3420a()) {
            i2 = 0;
        } else {
            i = canvas.save();
            i2 = this.f189h ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float) (i2 + (-getHeight())), BitmapDescriptorFactory.HUE_RED);
            i2 = (this.ac == null || !this.ac.m3424a(canvas)) ? 0 : 1;
            canvas.restoreToCount(i);
        }
        if (!(this.ad == null || this.ad.m3420a())) {
            size = canvas.save();
            if (this.f189h) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            i = (this.ad == null || !this.ad.m3424a(canvas)) ? 0 : 1;
            i2 |= i;
            canvas.restoreToCount(size);
        }
        if (!(this.ae == null || this.ae.m3420a())) {
            size = canvas.save();
            int width = getWidth();
            i = this.f189h ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate((float) (-i), (float) (-width));
            i = (this.ae == null || !this.ae.m3424a(canvas)) ? 0 : 1;
            i2 |= i;
            canvas.restoreToCount(size);
        }
        if (!(this.af == null || this.af.m3420a())) {
            i = canvas.save();
            canvas.rotate(180.0f);
            if (this.f189h) {
                canvas.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            } else {
                canvas.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            if (this.af != null && this.af.m3424a(canvas)) {
                i4 = 1;
            }
            i2 |= i4;
            canvas.restoreToCount(i);
        }
        if (i2 != 0 || this.f204w == null || this.f196o.size() <= 0 || !this.f204w.mo927b()) {
            i3 = i2;
        }
        if (i3 != 0) {
            ah.m2799c(this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    /* renamed from: e */
    public void m270e() {
        setScrollState(0);
        m189B();
    }

    /* renamed from: e */
    public void m271e(int i) {
        int b = this.f187f.m5344b();
        for (int i2 = 0; i2 < b; i2++) {
            this.f187f.m5346b(i2).offsetLeftAndRight(i);
        }
    }

    /* renamed from: e */
    void m272e(int i, int i2) {
        setMeasuredDimension(C0910h.m4479a(i, getPaddingLeft() + getPaddingRight(), ah.m2825o(this)), C0910h.m4479a(i2, getPaddingTop() + getPaddingBottom(), ah.m2826p(this)));
    }

    @Deprecated
    /* renamed from: f */
    public int m273f(View view) {
        return m277g(view);
    }

    /* renamed from: f */
    void m274f() {
        if (this.ac == null) {
            this.ac = new C0700i(getContext());
            if (this.f189h) {
                this.ac.m3419a((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.ac.m3419a(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* renamed from: f */
    public void m275f(int i) {
    }

    /* renamed from: f */
    void m276f(int i, int i2) {
        int i3;
        int c = this.f187f.m5347c();
        int i4;
        int i5;
        if (i < i2) {
            i3 = -1;
            i4 = i2;
            i5 = i;
        } else {
            i3 = 1;
            i4 = i;
            i5 = i2;
        }
        for (int i6 = 0; i6 < c; i6++) {
            C0955v e = m222e(this.f187f.m5350d(i6));
            if (e != null && e.mPosition >= r3 && e.mPosition <= r2) {
                if (e.mPosition == i) {
                    e.offsetPosition(i2 - i, false);
                } else {
                    e.offsetPosition(i3, false);
                }
                this.f169A.f2531e = true;
            }
        }
        this.f185d.m4899a(i, i2);
        requestLayout();
    }

    public View focusSearch(View view, int i) {
        boolean z = true;
        View d = this.f194m.m4574d(view, i);
        if (d != null) {
            return d;
        }
        boolean z2 = (this.f193l == null || this.f194m == null || m295n() || this.f201t) ? false : true;
        FocusFinder instance = FocusFinder.getInstance();
        if (z2 && (i == 2 || i == 1)) {
            int i2;
            if (this.f194m.mo823e()) {
                i2 = i == 2 ? TsExtractor.TS_STREAM_TYPE_HDMV_DTS : 33;
                boolean z3 = instance.findNextFocus(this, view, i2) == null;
                if (f163K) {
                    i = i2;
                    z2 = z3;
                } else {
                    z2 = z3;
                }
            } else {
                z2 = false;
            }
            if (z2 || !this.f194m.mo821d()) {
                z = z2;
            } else {
                i2 = ((i == 2 ? 1 : 0) ^ (this.f194m.m4613u() == 1 ? 1 : 0)) != 0 ? 66 : 17;
                if (instance.findNextFocus(this, view, i2) != null) {
                    z = false;
                }
                if (f163K) {
                    i = i2;
                }
            }
            if (z) {
                m263c();
                if (m262c(view) == null) {
                    return null;
                }
                m267d();
                this.f194m.mo804a(view, i, this.f185d, this.f169A);
                m247a(false);
            }
            d = instance.findNextFocus(this, view, i);
        } else {
            View findNextFocus = instance.findNextFocus(this, view, i);
            if (findNextFocus == null && z2) {
                m263c();
                if (m262c(view) == null) {
                    return null;
                }
                m267d();
                d = this.f194m.mo804a(view, i, this.f185d, this.f169A);
                m247a(false);
            } else {
                d = findNextFocus;
            }
        }
        if (d == null || d.hasFocusable()) {
            if (!m217a(view, d, i)) {
                d = super.focusSearch(view, i);
            }
            return d;
        } else if (getFocusedChild() == null) {
            return super.focusSearch(view, i);
        } else {
            m213a(d, null);
            return view;
        }
    }

    /* renamed from: g */
    public int m277g(View view) {
        C0955v e = m222e(view);
        return e != null ? e.getAdapterPosition() : -1;
    }

    /* renamed from: g */
    void m278g() {
        if (this.ae == null) {
            this.ae = new C0700i(getContext());
            if (this.f189h) {
                this.ae.m3419a((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.ae.m3419a(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* renamed from: g */
    void m279g(int i) {
        if (this.f194m != null) {
            this.f194m.mo882k(i);
        }
        m275f(i);
        if (this.at != null) {
            this.at.m4876a(this, i);
        }
        if (this.au != null) {
            for (int size = this.au.size() - 1; size >= 0; size--) {
                ((C0944m) this.au.get(size)).m4876a(this, i);
            }
        }
    }

    /* renamed from: g */
    void m280g(int i, int i2) {
        int c = this.f187f.m5347c();
        for (int i3 = 0; i3 < c; i3++) {
            C0955v e = m222e(this.f187f.m5350d(i3));
            if (!(e == null || e.shouldIgnore() || e.mPosition < i)) {
                e.offsetPosition(i2, false);
                this.f169A.f2531e = true;
            }
        }
        this.f185d.m4910b(i, i2);
        requestLayout();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        if (this.f194m != null) {
            return this.f194m.mo803a();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        if (this.f194m != null) {
            return this.f194m.mo830a(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    protected LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        if (this.f194m != null) {
            return this.f194m.mo831a(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public C0926a getAdapter() {
        return this.f193l;
    }

    public int getBaseline() {
        return this.f194m != null ? this.f194m.m4614v() : super.getBaseline();
    }

    protected int getChildDrawingOrder(int i, int i2) {
        return this.aw == null ? super.getChildDrawingOrder(i, i2) : this.aw.mo891a(i, i2);
    }

    public boolean getClipToPadding() {
        return this.f189h;
    }

    public aw getCompatAccessibilityDelegate() {
        return this.f173E;
    }

    public C0933e getItemAnimator() {
        return this.f204w;
    }

    public C0910h getLayoutManager() {
        return this.f194m;
    }

    public int getMaxFlingVelocity() {
        return this.aq;
    }

    public int getMinFlingVelocity() {
        return this.ap;
    }

    long getNanoTime() {
        return f162J ? System.nanoTime() : 0;
    }

    public C0942k getOnFlingListener() {
        return this.ao;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.as;
    }

    public C0946n getRecycledViewPool() {
        return this.f185d.m4925g();
    }

    public int getScrollState() {
        return this.ag;
    }

    /* renamed from: h */
    public int m281h(View view) {
        C0955v e = m222e(view);
        return e != null ? e.getLayoutPosition() : -1;
    }

    /* renamed from: h */
    void m282h() {
        if (this.ad == null) {
            this.ad = new C0700i(getContext());
            if (this.f189h) {
                this.ad.m3419a((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.ad.m3419a(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* renamed from: h */
    public void m283h(int i, int i2) {
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().m3215b();
    }

    /* renamed from: i */
    void m284i() {
        if (this.af == null) {
            this.af = new C0700i(getContext());
            if (this.f189h) {
                this.af.m3419a((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.af.m3419a(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* renamed from: i */
    void m285i(int i, int i2) {
        this.ab++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        m283h(i, i2);
        if (this.at != null) {
            this.at.m4877a(this, i, i2);
        }
        if (this.au != null) {
            for (scrollY = this.au.size() - 1; scrollY >= 0; scrollY--) {
                ((C0944m) this.au.get(scrollY)).m4877a(this, i, i2);
            }
        }
        this.ab--;
    }

    /* renamed from: i */
    public void m286i(View view) {
    }

    public boolean isAttachedToWindow() {
        return this.f197p;
    }

    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().m3209a();
    }

    /* renamed from: j */
    void m287j() {
        this.af = null;
        this.ad = null;
        this.ae = null;
        this.ac = null;
    }

    /* renamed from: j */
    public void m288j(View view) {
    }

    /* renamed from: k */
    Rect m289k(View view) {
        C0908i c0908i = (C0908i) view.getLayoutParams();
        if (!c0908i.f2405e) {
            return c0908i.f2404d;
        }
        if (this.f169A.m4955a() && (c0908i.m4475e() || c0908i.m4473c())) {
            return c0908i.f2404d;
        }
        Rect rect = c0908i.f2404d;
        rect.set(0, 0, 0, 0);
        int size = this.f196o.size();
        for (int i = 0; i < size; i++) {
            this.f191j.set(0, 0, 0, 0);
            ((C0935g) this.f196o.get(i)).mo894a(this.f191j, view, this, this.f169A);
            rect.left += this.f191j.left;
            rect.top += this.f191j.top;
            rect.right += this.f191j.right;
            rect.bottom += this.f191j.bottom;
        }
        c0908i.f2405e = false;
        return rect;
    }

    /* renamed from: k */
    void m290k() {
        this.aa++;
    }

    /* renamed from: l */
    void m291l() {
        this.aa--;
        if (this.aa < 1) {
            this.aa = 0;
            m193F();
            m305x();
        }
    }

    /* renamed from: m */
    void m292m(View view) {
        C0955v e = m222e(view);
        m288j(view);
        if (!(this.f193l == null || e == null)) {
            this.f193l.onViewDetachedFromWindow(e);
        }
        if (this.f184W != null) {
            for (int size = this.f184W.size() - 1; size >= 0; size--) {
                ((C0941j) this.f184W.get(size)).mo897b(view);
            }
        }
    }

    /* renamed from: m */
    boolean m293m() {
        return this.f183V != null && this.f183V.isEnabled();
    }

    /* renamed from: n */
    void m294n(View view) {
        C0955v e = m222e(view);
        m286i(view);
        if (!(this.f193l == null || e == null)) {
            this.f193l.onViewAttachedToWindow(e);
        }
        if (this.f184W != null) {
            for (int size = this.f184W.size() - 1; size >= 0; size--) {
                ((C0941j) this.f184W.get(size)).mo895a(view);
            }
        }
    }

    /* renamed from: n */
    public boolean m295n() {
        return this.aa > 0;
    }

    /* renamed from: o */
    void m296o() {
        if (!this.f172D && this.f197p) {
            ah.m2787a((View) this, this.aC);
            this.f172D = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onAttachedToWindow() {
        /*
        r4 = this;
        r0 = 1;
        r1 = 0;
        super.onAttachedToWindow();
        r4.aa = r1;
        r4.f197p = r0;
        r2 = r4.f199r;
        if (r2 == 0) goto L_0x0068;
    L_0x000d:
        r2 = r4.isLayoutRequested();
        if (r2 != 0) goto L_0x0068;
    L_0x0013:
        r4.f199r = r0;
        r0 = r4.f194m;
        if (r0 == 0) goto L_0x001e;
    L_0x0019:
        r0 = r4.f194m;
        r0.m4566c(r4);
    L_0x001e:
        r4.f172D = r1;
        r0 = f162J;
        if (r0 == 0) goto L_0x0067;
    L_0x0024:
        r0 = android.support.v7.widget.am.f2867a;
        r0 = r0.get();
        r0 = (android.support.v7.widget.am) r0;
        r4.f206y = r0;
        r0 = r4.f206y;
        if (r0 != 0) goto L_0x0062;
    L_0x0032:
        r0 = new android.support.v7.widget.am;
        r0.<init>();
        r4.f206y = r0;
        r0 = android.support.v4.view.ah.m2771K(r4);
        r1 = 1114636288; // 0x42700000 float:60.0 double:5.507034975E-315;
        r2 = r4.isInEditMode();
        if (r2 != 0) goto L_0x006a;
    L_0x0045:
        if (r0 == 0) goto L_0x006a;
    L_0x0047:
        r0 = r0.getRefreshRate();
        r2 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 < 0) goto L_0x006a;
    L_0x0051:
        r1 = r4.f206y;
        r2 = 1315859240; // 0x4e6e6b28 float:1.0E9 double:6.50120845E-315;
        r0 = r2 / r0;
        r2 = (long) r0;
        r1.f2871d = r2;
        r0 = android.support.v7.widget.am.f2867a;
        r1 = r4.f206y;
        r0.set(r1);
    L_0x0062:
        r0 = r4.f206y;
        r0.m5461a(r4);
    L_0x0067:
        return;
    L_0x0068:
        r0 = r1;
        goto L_0x0013;
    L_0x006a:
        r0 = r1;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.onAttachedToWindow():void");
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f204w != null) {
            this.f204w.mo929d();
        }
        m270e();
        this.f197p = false;
        if (this.f194m != null) {
            this.f194m.m4555b(this, this.f185d);
        }
        this.f174F.clear();
        removeCallbacks(this.aC);
        this.f188g.m5736b();
        if (f162J) {
            this.f206y.m5463b(this);
            this.f206y = null;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.f196o.size();
        for (int i = 0; i < size; i++) {
            ((C0935g) this.f196o.get(i)).mo893a(canvas, this, this.f169A);
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!(this.f194m == null || this.f201t || (motionEvent.getSource() & 2) == 0 || motionEvent.getAction() != 8)) {
            float f = this.f194m.mo823e() ? -C0659t.m3204a(motionEvent, 9) : BitmapDescriptorFactory.HUE_RED;
            float a = this.f194m.mo821d() ? C0659t.m3204a(motionEvent, 10) : BitmapDescriptorFactory.HUE_RED;
            if (!(f == BitmapDescriptorFactory.HUE_RED && a == BitmapDescriptorFactory.HUE_RED)) {
                float scrollFactor = getScrollFactor();
                m248a((int) (a * scrollFactor), (int) (f * scrollFactor), motionEvent);
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = -1;
        boolean z = true;
        if (this.f201t) {
            return false;
        }
        if (m216a(motionEvent)) {
            m192E();
            return true;
        } else if (this.f194m == null) {
            return false;
        } else {
            boolean d = this.f194m.mo821d();
            boolean e = this.f194m.mo823e();
            if (this.ai == null) {
                this.ai = VelocityTracker.obtain();
            }
            this.ai.addMovement(motionEvent);
            int a = C0659t.m3205a(motionEvent);
            int b = C0659t.m3206b(motionEvent);
            int i2;
            switch (a) {
                case 0:
                    if (this.f181T) {
                        this.f181T = false;
                    }
                    this.ah = motionEvent.getPointerId(0);
                    i = (int) (motionEvent.getX() + 0.5f);
                    this.al = i;
                    this.aj = i;
                    i = (int) (motionEvent.getY() + 0.5f);
                    this.am = i;
                    this.ak = i;
                    if (this.ag == 2) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                    }
                    int[] iArr = this.aB;
                    this.aB[1] = 0;
                    iArr[0] = 0;
                    i2 = d ? 1 : 0;
                    if (e) {
                        i2 |= 2;
                    }
                    startNestedScroll(i2);
                    break;
                case 1:
                    this.ai.clear();
                    stopNestedScroll();
                    break;
                case 2:
                    a = motionEvent.findPointerIndex(this.ah);
                    if (a >= 0) {
                        b = (int) (motionEvent.getX(a) + 0.5f);
                        a = (int) (motionEvent.getY(a) + 0.5f);
                        if (this.ag != 1) {
                            b -= this.aj;
                            a -= this.ak;
                            if (!d || Math.abs(b) <= this.an) {
                                d = false;
                            } else {
                                this.al = ((b < 0 ? -1 : 1) * this.an) + this.aj;
                                d = true;
                            }
                            if (e && Math.abs(a) > this.an) {
                                i2 = this.ak;
                                int i3 = this.an;
                                if (a >= 0) {
                                    i = 1;
                                }
                                this.am = i2 + (i * i3);
                                d = true;
                            }
                            if (d) {
                                setScrollState(1);
                                break;
                            }
                        }
                    }
                    Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.ah + " not found. Did any MotionEvents get skipped?");
                    return false;
                    break;
                case 3:
                    m192E();
                    break;
                case 5:
                    this.ah = motionEvent.getPointerId(b);
                    i2 = (int) (motionEvent.getX(b) + 0.5f);
                    this.al = i2;
                    this.aj = i2;
                    i2 = (int) (motionEvent.getY(b) + 0.5f);
                    this.am = i2;
                    this.ak = i2;
                    break;
                case 6:
                    m221c(motionEvent);
                    break;
            }
            if (this.ag != 1) {
                z = false;
            }
            return z;
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        C0440j.m1922a("RV OnLayout");
        m297p();
        C0440j.m1921a();
        this.f199r = true;
    }

    protected void onMeasure(int i, int i2) {
        boolean z = false;
        if (this.f194m == null) {
            m272e(i, i2);
        } else if (this.f194m.f2424w) {
            int mode = MeasureSpec.getMode(i);
            int mode2 = MeasureSpec.getMode(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            this.f194m.m4516a(this.f185d, this.f169A, i, i2);
            if (!z && this.f193l != null) {
                if (this.f169A.f2529c == 1) {
                    m200M();
                }
                this.f194m.m4576d(i, i2);
                this.f169A.f2534h = true;
                m201N();
                this.f194m.m4581e(i, i2);
                if (this.f194m.mo827k()) {
                    this.f194m.m4576d(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.f169A.f2534h = true;
                    m201N();
                    this.f194m.m4581e(i, i2);
                }
            }
        } else if (this.f198q) {
            this.f194m.m4516a(this.f185d, this.f169A, i, i2);
        } else {
            if (this.f202u) {
                m267d();
                m290k();
                m195H();
                m291l();
                if (this.f169A.f2536j) {
                    this.f169A.f2532f = true;
                } else {
                    this.f186e.m5832e();
                    this.f169A.f2532f = false;
                }
                this.f202u = false;
                m247a(false);
            }
            if (this.f193l != null) {
                this.f169A.f2530d = this.f193l.getItemCount();
            } else {
                this.f169A.f2530d = 0;
            }
            m267d();
            this.f194m.m4516a(this.f185d, this.f169A, i, i2);
            m247a(false);
            this.f169A.f2532f = false;
        }
    }

    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        return m295n() ? false : super.onRequestFocusInDescendants(i, rect);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.f176O = (SavedState) parcelable;
            super.onRestoreInstanceState(this.f176O.getSuperState());
            if (this.f194m != null && this.f176O.f2479a != null) {
                this.f194m.mo807a(this.f176O.f2479a);
                return;
            }
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.f176O != null) {
            savedState.m4805a(this.f176O);
        } else if (this.f194m != null) {
            savedState.f2479a = this.f194m.mo816c();
        } else {
            savedState.f2479a = null;
        }
        return savedState;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            m287j();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (this.f201t || this.f181T) {
            return false;
        }
        if (m218b(motionEvent)) {
            m192E();
            return true;
        } else if (this.f194m == null) {
            return false;
        } else {
            boolean d = this.f194m.mo821d();
            boolean e = this.f194m.mo823e();
            if (this.ai == null) {
                this.ai = VelocityTracker.obtain();
            }
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            int a = C0659t.m3205a(motionEvent);
            int b = C0659t.m3206b(motionEvent);
            if (a == 0) {
                int[] iArr = this.aB;
                this.aB[1] = 0;
                iArr[0] = 0;
            }
            obtain.offsetLocation((float) this.aB[0], (float) this.aB[1]);
            switch (a) {
                case 0:
                    this.ah = motionEvent.getPointerId(0);
                    a = (int) (motionEvent.getX() + 0.5f);
                    this.al = a;
                    this.aj = a;
                    a = (int) (motionEvent.getY() + 0.5f);
                    this.am = a;
                    this.ak = a;
                    a = d ? 1 : 0;
                    if (e) {
                        a |= 2;
                    }
                    startNestedScroll(a);
                    break;
                case 1:
                    this.ai.addMovement(obtain);
                    this.ai.computeCurrentVelocity(1000, (float) this.aq);
                    float f = d ? -af.m2516a(this.ai, this.ah) : BitmapDescriptorFactory.HUE_RED;
                    float f2 = e ? -af.m2517b(this.ai, this.ah) : BitmapDescriptorFactory.HUE_RED;
                    if ((f == BitmapDescriptorFactory.HUE_RED && f2 == BitmapDescriptorFactory.HUE_RED) || !m259b((int) f, (int) f2)) {
                        setScrollState(0);
                    }
                    m191D();
                    z = true;
                    break;
                case 2:
                    a = motionEvent.findPointerIndex(this.ah);
                    if (a >= 0) {
                        int x = (int) (motionEvent.getX(a) + 0.5f);
                        int y = (int) (motionEvent.getY(a) + 0.5f);
                        int i = this.al - x;
                        a = this.am - y;
                        if (dispatchNestedPreScroll(i, a, this.aA, this.az)) {
                            i -= this.aA[0];
                            a -= this.aA[1];
                            obtain.offsetLocation((float) this.az[0], (float) this.az[1]);
                            int[] iArr2 = this.aB;
                            iArr2[0] = iArr2[0] + this.az[0];
                            iArr2 = this.aB;
                            iArr2[1] = iArr2[1] + this.az[1];
                        }
                        if (this.ag != 1) {
                            boolean z2;
                            if (!d || Math.abs(i) <= this.an) {
                                z2 = false;
                            } else {
                                i = i > 0 ? i - this.an : i + this.an;
                                z2 = true;
                            }
                            if (e && Math.abs(a) > this.an) {
                                a = a > 0 ? a - this.an : a + this.an;
                                z2 = true;
                            }
                            if (z2) {
                                setScrollState(1);
                            }
                        }
                        if (this.ag == 1) {
                            this.al = x - this.az[0];
                            this.am = y - this.az[1];
                            if (m248a(d ? i : 0, e ? a : 0, obtain)) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                            }
                            if (!(this.f206y == null || (i == 0 && a == 0))) {
                                this.f206y.m5462a(this, i, a);
                                break;
                            }
                        }
                    }
                    Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.ah + " not found. Did any MotionEvents get skipped?");
                    return false;
                    break;
                case 3:
                    m192E();
                    break;
                case 5:
                    this.ah = motionEvent.getPointerId(b);
                    a = (int) (motionEvent.getX(b) + 0.5f);
                    this.al = a;
                    this.aj = a;
                    a = (int) (motionEvent.getY(b) + 0.5f);
                    this.am = a;
                    this.ak = a;
                    break;
                case 6:
                    m221c(motionEvent);
                    break;
            }
            if (!z) {
                this.ai.addMovement(obtain);
            }
            obtain.recycle();
            return true;
        }
    }

    /* renamed from: p */
    void m297p() {
        if (this.f193l == null) {
            Log.e("RecyclerView", "No adapter attached; skipping layout");
        } else if (this.f194m == null) {
            Log.e("RecyclerView", "No layout manager attached; skipping layout");
        } else {
            this.f169A.f2534h = false;
            if (this.f169A.f2529c == 1) {
                m200M();
                this.f194m.m4589f(this);
                m201N();
            } else if (!this.f186e.m5833f() && this.f194m.m4618z() == getWidth() && this.f194m.m4490A() == getHeight()) {
                this.f194m.m4589f(this);
            } else {
                this.f194m.m4589f(this);
                m201N();
            }
            m202O();
        }
    }

    /* renamed from: q */
    void m298q() {
        int c = this.f187f.m5347c();
        for (int i = 0; i < c; i++) {
            ((C0908i) this.f187f.m5350d(i).getLayoutParams()).f2405e = true;
        }
        this.f185d.m4929k();
    }

    /* renamed from: r */
    void m299r() {
        int c = this.f187f.m5347c();
        for (int i = 0; i < c; i++) {
            C0955v e = m222e(this.f187f.m5350d(i));
            if (!e.shouldIgnore()) {
                e.saveOldPosition();
            }
        }
    }

    protected void removeDetachedView(View view, boolean z) {
        C0955v e = m222e(view);
        if (e != null) {
            if (e.isTmpDetached()) {
                e.clearTmpDetachFlag();
            } else if (!e.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + e);
            }
        }
        m292m(view);
        super.removeDetachedView(view, z);
    }

    public void requestChildFocus(View view, View view2) {
        if (!(this.f194m.m4541a(this, this.f169A, view, view2) || view2 == null)) {
            m213a(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.f194m.m4542a(this, view, rect, z);
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.f178Q.size();
        for (int i = 0; i < size; i++) {
            ((C0943l) this.f178Q.get(i)).mo884a(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public void requestLayout() {
        if (this.f180S != 0 || this.f201t) {
            this.f200s = true;
        } else {
            super.requestLayout();
        }
    }

    /* renamed from: s */
    void m300s() {
        int c = this.f187f.m5347c();
        for (int i = 0; i < c; i++) {
            C0955v e = m222e(this.f187f.m5350d(i));
            if (!e.shouldIgnore()) {
                e.clearOldPosition();
            }
        }
        this.f185d.m4928j();
    }

    public void scrollBy(int i, int i2) {
        if (this.f194m == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.f201t) {
            boolean d = this.f194m.mo821d();
            boolean e = this.f194m.mo823e();
            if (d || e) {
                if (!d) {
                    i = 0;
                }
                if (!e) {
                    i2 = 0;
                }
                m248a(i, i2, null);
            }
        }
    }

    public void scrollTo(int i, int i2) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!m251a(accessibilityEvent)) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public void setAccessibilityDelegateCompat(aw awVar) {
        this.f173E = awVar;
        ah.m2783a((View) this, this.f173E);
    }

    public void setAdapter(C0926a c0926a) {
        setLayoutFrozen(false);
        m207a(c0926a, false, true);
        requestLayout();
    }

    public void setChildDrawingOrderCallback(C0929d c0929d) {
        if (c0929d != this.aw) {
            this.aw = c0929d;
            setChildrenDrawingOrderEnabled(this.aw != null);
        }
    }

    public void setClipToPadding(boolean z) {
        if (z != this.f189h) {
            m287j();
        }
        this.f189h = z;
        super.setClipToPadding(z);
        if (this.f199r) {
            requestLayout();
        }
    }

    public void setHasFixedSize(boolean z) {
        this.f198q = z;
    }

    public void setItemAnimator(C0933e c0933e) {
        if (this.f204w != null) {
            this.f204w.mo929d();
            this.f204w.m4828a(null);
        }
        this.f204w = c0933e;
        if (this.f204w != null) {
            this.f204w.m4828a(this.av);
        }
    }

    public void setItemViewCacheSize(int i) {
        this.f185d.m4898a(i);
    }

    public void setLayoutFrozen(boolean z) {
        if (z != this.f201t) {
            m246a("Do not setLayoutFrozen in layout or scroll");
            if (z) {
                long uptimeMillis = SystemClock.uptimeMillis();
                onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0));
                this.f201t = true;
                this.f181T = true;
                m270e();
                return;
            }
            this.f201t = false;
            if (!(!this.f200s || this.f194m == null || this.f193l == null)) {
                requestLayout();
            }
            this.f200s = false;
        }
    }

    public void setLayoutManager(C0910h c0910h) {
        if (c0910h != this.f194m) {
            m270e();
            if (this.f194m != null) {
                if (this.f204w != null) {
                    this.f204w.mo929d();
                }
                this.f194m.m4564c(this.f185d);
                this.f194m.m4552b(this.f185d);
                this.f185d.m4897a();
                if (this.f197p) {
                    this.f194m.m4555b(this, this.f185d);
                }
                this.f194m.m4553b(null);
                this.f194m = null;
            } else {
                this.f185d.m4897a();
            }
            this.f187f.m5338a();
            this.f194m = c0910h;
            if (c0910h != null) {
                if (c0910h.f2418q != null) {
                    throw new IllegalArgumentException("LayoutManager " + c0910h + " is already attached to a RecyclerView: " + c0910h.f2418q);
                }
                this.f194m.m4553b(this);
                if (this.f197p) {
                    this.f194m.m4566c(this);
                }
            }
            this.f185d.m4909b();
            requestLayout();
        }
    }

    public void setNestedScrollingEnabled(boolean z) {
        getScrollingChildHelper().m3208a(z);
    }

    public void setOnFlingListener(C0942k c0942k) {
        this.ao = c0942k;
    }

    @Deprecated
    public void setOnScrollListener(C0944m c0944m) {
        this.at = c0944m;
    }

    public void setPreserveFocusAfterLayout(boolean z) {
        this.as = z;
    }

    public void setRecycledViewPool(C0946n c0946n) {
        this.f185d.m4902a(c0946n);
    }

    public void setRecyclerListener(C0948p c0948p) {
        this.f195n = c0948p;
    }

    void setScrollState(int i) {
        if (i != this.ag) {
            this.ag = i;
            if (i != 2) {
                m189B();
            }
            m279g(i);
        }
    }

    public void setScrollingTouchSlop(int i) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        switch (i) {
            case 0:
                break;
            case 1:
                this.an = viewConfiguration.getScaledPagingTouchSlop();
                return;
            default:
                Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i + "; using default value");
                break;
        }
        this.an = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(C0953t c0953t) {
        this.f185d.m4903a(c0953t);
    }

    public boolean startNestedScroll(int i) {
        return getScrollingChildHelper().m3212a(i);
    }

    public void stopNestedScroll() {
        getScrollingChildHelper().m3216c();
    }

    /* renamed from: t */
    void m301t() {
        if (!this.f203v) {
            this.f203v = true;
            int c = this.f187f.m5347c();
            for (int i = 0; i < c; i++) {
                C0955v e = m222e(this.f187f.m5350d(i));
                if (!(e == null || e.shouldIgnore())) {
                    e.addFlags(512);
                }
            }
            this.f185d.m4926h();
            m302u();
        }
    }

    /* renamed from: u */
    void m302u() {
        int c = this.f187f.m5347c();
        for (int i = 0; i < c; i++) {
            C0955v e = m222e(this.f187f.m5350d(i));
            if (!(e == null || e.shouldIgnore())) {
                e.addFlags(6);
            }
        }
        m298q();
        this.f185d.m4927i();
    }

    /* renamed from: v */
    public boolean m303v() {
        return !this.f199r || this.f203v || this.f186e.m5831d();
    }

    /* renamed from: w */
    void m304w() {
        int b = this.f187f.m5344b();
        for (int i = 0; i < b; i++) {
            View b2 = this.f187f.m5346b(i);
            C0955v b3 = m252b(b2);
            if (!(b3 == null || b3.mShadowingHolder == null)) {
                View view = b3.mShadowingHolder.itemView;
                int left = b2.getLeft();
                int top = b2.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    /* renamed from: x */
    void m305x() {
        for (int size = this.f174F.size() - 1; size >= 0; size--) {
            C0955v c0955v = (C0955v) this.f174F.get(size);
            if (c0955v.itemView.getParent() == this && !c0955v.shouldIgnore()) {
                int i = c0955v.mPendingAccessibilityState;
                if (i != -1) {
                    ah.m2801c(c0955v.itemView, i);
                    c0955v.mPendingAccessibilityState = -1;
                }
            }
        }
        this.f174F.clear();
    }
}
