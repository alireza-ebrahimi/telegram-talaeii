package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.design.C0073a.C0071j;
import android.support.design.C0073a.C0072k;
import android.support.v4.content.C0235a;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.p022f.C0481j.C0478a;
import android.support.v4.p022f.C0481j.C0480c;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.C0081z;
import android.support.v4.view.C0107x;
import android.support.v4.view.C0625f;
import android.support.v4.view.C0659t;
import android.support.v4.view.C0662y;
import android.support.v4.view.ah;
import android.support.v4.view.be;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.ui.ActionBar.Theme;

public class CoordinatorLayout extends ViewGroup implements C0107x {
    /* renamed from: a */
    static final String f310a;
    /* renamed from: b */
    static final Class<?>[] f311b = new Class[]{Context.class, AttributeSet.class};
    /* renamed from: c */
    static final ThreadLocal<Map<String, Constructor<C0088a>>> f312c = new ThreadLocal();
    /* renamed from: d */
    static final Comparator<View> f313d;
    /* renamed from: f */
    private static final C0478a<Rect> f314f = new C0480c(12);
    /* renamed from: e */
    OnHierarchyChangeListener f315e;
    /* renamed from: g */
    private final List<View> f316g;
    /* renamed from: h */
    private final C0150f<View> f317h;
    /* renamed from: i */
    private final List<View> f318i;
    /* renamed from: j */
    private final List<View> f319j;
    /* renamed from: k */
    private final int[] f320k;
    /* renamed from: l */
    private Paint f321l;
    /* renamed from: m */
    private boolean f322m;
    /* renamed from: n */
    private boolean f323n;
    /* renamed from: o */
    private int[] f324o;
    /* renamed from: p */
    private View f325p;
    /* renamed from: q */
    private View f326q;
    /* renamed from: r */
    private View f327r;
    /* renamed from: s */
    private C0105e f328s;
    /* renamed from: t */
    private boolean f329t;
    /* renamed from: u */
    private be f330u;
    /* renamed from: v */
    private boolean f331v;
    /* renamed from: w */
    private Drawable f332w;
    /* renamed from: x */
    private C0081z f333x;
    /* renamed from: y */
    private final C0662y f334y;

    /* renamed from: android.support.design.widget.CoordinatorLayout$a */
    public static abstract class C0088a<V extends View> {
        public C0088a(Context context, AttributeSet attributeSet) {
        }

        /* renamed from: a */
        public be m321a(CoordinatorLayout coordinatorLayout, V v, be beVar) {
            return beVar;
        }

        /* renamed from: a */
        public void mo110a(C0104d c0104d) {
        }

        /* renamed from: a */
        public void mo69a(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        /* renamed from: a */
        public void mo70a(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        /* renamed from: a */
        public void mo71a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4) {
        }

        /* renamed from: a */
        public void mo72a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
        }

        /* renamed from: a */
        public boolean mo62a(CoordinatorLayout coordinatorLayout, V v, int i) {
            return false;
        }

        /* renamed from: a */
        public boolean mo74a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3, int i4) {
            return false;
        }

        /* renamed from: a */
        public boolean mo111a(CoordinatorLayout coordinatorLayout, V v, Rect rect) {
            return false;
        }

        /* renamed from: a */
        public boolean mo83a(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean z) {
            return false;
        }

        /* renamed from: a */
        public boolean mo63a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        /* renamed from: a */
        public boolean mo95a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
            return false;
        }

        /* renamed from: a */
        public boolean mo75a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, boolean z) {
            return false;
        }

        /* renamed from: a */
        public boolean mo76a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
            return false;
        }

        /* renamed from: b */
        public Parcelable mo79b(CoordinatorLayout coordinatorLayout, V v) {
            return BaseSavedState.EMPTY_STATE;
        }

        /* renamed from: b */
        public void m336b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        }

        /* renamed from: b */
        public boolean mo64b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        /* renamed from: b */
        public boolean mo86b(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        /* renamed from: c */
        public int m339c(CoordinatorLayout coordinatorLayout, V v) {
            return Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        }

        /* renamed from: c */
        public void m340c() {
        }

        /* renamed from: c */
        public boolean mo87c(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        /* renamed from: d */
        public float m342d(CoordinatorLayout coordinatorLayout, V v) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: d */
        public void m343d(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        /* renamed from: e */
        public boolean m344e(CoordinatorLayout coordinatorLayout, V v) {
            return m342d(coordinatorLayout, v) > BitmapDescriptorFactory.HUE_RED;
        }
    }

    /* renamed from: android.support.design.widget.CoordinatorLayout$1 */
    class C01001 implements C0081z {
        /* renamed from: a */
        final /* synthetic */ CoordinatorLayout f289a;

        C01001(CoordinatorLayout coordinatorLayout) {
            this.f289a = coordinatorLayout;
        }

        /* renamed from: a */
        public be mo57a(View view, be beVar) {
            return this.f289a.m538a(beVar);
        }
    }

    protected static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C01011());
        /* renamed from: a */
        SparseArray<Parcelable> f290a;

        /* renamed from: android.support.design.widget.CoordinatorLayout$SavedState$1 */
        static class C01011 implements C0085g<SavedState> {
            C01011() {
            }

            /* renamed from: a */
            public SavedState m489a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m490a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m489a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m490a(i);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int readInt = parcel.readInt();
            int[] iArr = new int[readInt];
            parcel.readIntArray(iArr);
            Parcelable[] readParcelableArray = parcel.readParcelableArray(classLoader);
            this.f290a = new SparseArray(readInt);
            for (int i = 0; i < readInt; i++) {
                this.f290a.append(iArr[i], readParcelableArray[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            int i2 = 0;
            super.writeToParcel(parcel, i);
            int size = this.f290a != null ? this.f290a.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            while (i2 < size) {
                iArr[i2] = this.f290a.keyAt(i2);
                parcelableArr[i2] = (Parcelable) this.f290a.valueAt(i2);
                i2++;
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    /* renamed from: android.support.design.widget.CoordinatorLayout$b */
    public @interface C0102b {
        /* renamed from: a */
        Class<? extends C0088a> m491a();
    }

    /* renamed from: android.support.design.widget.CoordinatorLayout$c */
    private class C0103c implements OnHierarchyChangeListener {
        /* renamed from: a */
        final /* synthetic */ CoordinatorLayout f291a;

        C0103c(CoordinatorLayout coordinatorLayout) {
            this.f291a = coordinatorLayout;
        }

        public void onChildViewAdded(View view, View view2) {
            if (this.f291a.f315e != null) {
                this.f291a.f315e.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            this.f291a.m540a(2);
            if (this.f291a.f315e != null) {
                this.f291a.f315e.onChildViewRemoved(view, view2);
            }
        }
    }

    /* renamed from: android.support.design.widget.CoordinatorLayout$d */
    public static class C0104d extends MarginLayoutParams {
        /* renamed from: a */
        C0088a f292a;
        /* renamed from: b */
        boolean f293b = false;
        /* renamed from: c */
        public int f294c = 0;
        /* renamed from: d */
        public int f295d = 0;
        /* renamed from: e */
        public int f296e = -1;
        /* renamed from: f */
        int f297f = -1;
        /* renamed from: g */
        public int f298g = 0;
        /* renamed from: h */
        public int f299h = 0;
        /* renamed from: i */
        int f300i;
        /* renamed from: j */
        int f301j;
        /* renamed from: k */
        View f302k;
        /* renamed from: l */
        View f303l;
        /* renamed from: m */
        final Rect f304m = new Rect();
        /* renamed from: n */
        Object f305n;
        /* renamed from: o */
        private boolean f306o;
        /* renamed from: p */
        private boolean f307p;
        /* renamed from: q */
        private boolean f308q;

        public C0104d(int i, int i2) {
            super(i, i2);
        }

        C0104d(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.CoordinatorLayout_Layout);
            this.f294c = obtainStyledAttributes.getInteger(C0072k.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.f297f = obtainStyledAttributes.getResourceId(C0072k.CoordinatorLayout_Layout_layout_anchor, -1);
            this.f295d = obtainStyledAttributes.getInteger(C0072k.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.f296e = obtainStyledAttributes.getInteger(C0072k.CoordinatorLayout_Layout_layout_keyline, -1);
            this.f298g = obtainStyledAttributes.getInt(C0072k.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.f299h = obtainStyledAttributes.getInt(C0072k.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.f293b = obtainStyledAttributes.hasValue(C0072k.CoordinatorLayout_Layout_layout_behavior);
            if (this.f293b) {
                this.f292a = CoordinatorLayout.m513a(context, attributeSet, obtainStyledAttributes.getString(C0072k.CoordinatorLayout_Layout_layout_behavior));
            }
            obtainStyledAttributes.recycle();
            if (this.f292a != null) {
                this.f292a.mo110a(this);
            }
        }

        public C0104d(C0104d c0104d) {
            super(c0104d);
        }

        public C0104d(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public C0104d(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        /* renamed from: a */
        private void m492a(View view, CoordinatorLayout coordinatorLayout) {
            this.f302k = coordinatorLayout.findViewById(this.f297f);
            if (this.f302k != null) {
                if (this.f302k != coordinatorLayout) {
                    View view2 = this.f302k;
                    View parent = this.f302k.getParent();
                    while (parent != coordinatorLayout && parent != null) {
                        if (parent != view) {
                            if (parent instanceof View) {
                                view2 = parent;
                            }
                            parent = parent.getParent();
                        } else if (coordinatorLayout.isInEditMode()) {
                            this.f303l = null;
                            this.f302k = null;
                            return;
                        } else {
                            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                        }
                    }
                    this.f303l = view2;
                } else if (coordinatorLayout.isInEditMode()) {
                    this.f303l = null;
                    this.f302k = null;
                } else {
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
            } else if (coordinatorLayout.isInEditMode()) {
                this.f303l = null;
                this.f302k = null;
            } else {
                throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(this.f297f) + " to anchor view " + view);
            }
        }

        /* renamed from: a */
        private boolean m493a(View view, int i) {
            int a = C0625f.m3120a(((C0104d) view.getLayoutParams()).f298g, i);
            return a != 0 && (C0625f.m3120a(this.f299h, i) & a) == a;
        }

        /* renamed from: b */
        private boolean m494b(View view, CoordinatorLayout coordinatorLayout) {
            if (this.f302k.getId() != this.f297f) {
                return false;
            }
            View view2 = this.f302k;
            View parent = this.f302k.getParent();
            while (parent != coordinatorLayout) {
                if (parent == null || parent == view) {
                    this.f303l = null;
                    this.f302k = null;
                    return false;
                }
                if (parent instanceof View) {
                    view2 = parent;
                }
                parent = parent.getParent();
            }
            this.f303l = view2;
            return true;
        }

        /* renamed from: a */
        public int m495a() {
            return this.f297f;
        }

        /* renamed from: a */
        void m496a(Rect rect) {
            this.f304m.set(rect);
        }

        /* renamed from: a */
        public void m497a(C0088a c0088a) {
            if (this.f292a != c0088a) {
                if (this.f292a != null) {
                    this.f292a.m340c();
                }
                this.f292a = c0088a;
                this.f305n = null;
                this.f293b = true;
                if (c0088a != null) {
                    c0088a.mo110a(this);
                }
            }
        }

        /* renamed from: a */
        void m498a(boolean z) {
            this.f307p = z;
        }

        /* renamed from: a */
        boolean m499a(CoordinatorLayout coordinatorLayout, View view) {
            if (this.f306o) {
                return true;
            }
            boolean e = (this.f292a != null ? this.f292a.m344e(coordinatorLayout, view) : 0) | this.f306o;
            this.f306o = e;
            return e;
        }

        /* renamed from: a */
        boolean m500a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 == this.f303l || m493a(view2, ah.m2812g(coordinatorLayout)) || (this.f292a != null && this.f292a.mo86b(coordinatorLayout, view, view2));
        }

        /* renamed from: b */
        public C0088a m501b() {
            return this.f292a;
        }

        /* renamed from: b */
        View m502b(CoordinatorLayout coordinatorLayout, View view) {
            if (this.f297f == -1) {
                this.f303l = null;
                this.f302k = null;
                return null;
            }
            if (this.f302k == null || !m494b(view, coordinatorLayout)) {
                m492a(view, coordinatorLayout);
            }
            return this.f302k;
        }

        /* renamed from: b */
        void m503b(boolean z) {
            this.f308q = z;
        }

        /* renamed from: c */
        Rect m504c() {
            return this.f304m;
        }

        /* renamed from: d */
        boolean m505d() {
            return this.f302k == null && this.f297f != -1;
        }

        /* renamed from: e */
        boolean m506e() {
            if (this.f292a == null) {
                this.f306o = false;
            }
            return this.f306o;
        }

        /* renamed from: f */
        void m507f() {
            this.f306o = false;
        }

        /* renamed from: g */
        void m508g() {
            this.f307p = false;
        }

        /* renamed from: h */
        boolean m509h() {
            return this.f307p;
        }

        /* renamed from: i */
        boolean m510i() {
            return this.f308q;
        }

        /* renamed from: j */
        void m511j() {
            this.f308q = false;
        }
    }

    /* renamed from: android.support.design.widget.CoordinatorLayout$e */
    class C0105e implements OnPreDrawListener {
        /* renamed from: a */
        final /* synthetic */ CoordinatorLayout f309a;

        C0105e(CoordinatorLayout coordinatorLayout) {
            this.f309a = coordinatorLayout;
        }

        public boolean onPreDraw() {
            this.f309a.m540a(0);
            return true;
        }
    }

    /* renamed from: android.support.design.widget.CoordinatorLayout$f */
    static class C0106f implements Comparator<View> {
        C0106f() {
        }

        /* renamed from: a */
        public int m512a(View view, View view2) {
            float H = ah.m2768H(view);
            float H2 = ah.m2768H(view2);
            return H > H2 ? -1 : H < H2 ? 1 : 0;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m512a((View) obj, (View) obj2);
        }
    }

    static {
        Package packageR = CoordinatorLayout.class.getPackage();
        f310a = packageR != null ? packageR.getName() : null;
        if (VERSION.SDK_INT >= 21) {
            f313d = new C0106f();
        } else {
            f313d = null;
        }
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        int i2 = 0;
        super(context, attributeSet, i);
        this.f316g = new ArrayList();
        this.f317h = new C0150f();
        this.f318i = new ArrayList();
        this.f319j = new ArrayList();
        this.f320k = new int[2];
        this.f334y = new C0662y(this);
        C0195v.m916a(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.CoordinatorLayout, i, C0071j.Widget_Design_CoordinatorLayout);
        int resourceId = obtainStyledAttributes.getResourceId(C0072k.CoordinatorLayout_keylines, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            this.f324o = resources.getIntArray(resourceId);
            float f = resources.getDisplayMetrics().density;
            int length = this.f324o.length;
            while (i2 < length) {
                int[] iArr = this.f324o;
                iArr[i2] = (int) (((float) iArr[i2]) * f);
                i2++;
            }
        }
        this.f332w = obtainStyledAttributes.getDrawable(C0072k.CoordinatorLayout_statusBarBackground);
        obtainStyledAttributes.recycle();
        m534h();
        super.setOnHierarchyChangeListener(new C0103c(this));
    }

    /* renamed from: a */
    static C0088a m513a(Context context, AttributeSet attributeSet, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(".")) {
            str = context.getPackageName() + str;
        } else if (str.indexOf(46) < 0 && !TextUtils.isEmpty(f310a)) {
            str = f310a + '.' + str;
        }
        try {
            Map map;
            Map map2 = (Map) f312c.get();
            if (map2 == null) {
                HashMap hashMap = new HashMap();
                f312c.set(hashMap);
                map = hashMap;
            } else {
                map = map2;
            }
            Constructor constructor = (Constructor) map.get(str);
            if (constructor == null) {
                constructor = Class.forName(str, true, context.getClassLoader()).getConstructor(f311b);
                constructor.setAccessible(true);
                map.put(str, constructor);
            }
            return (C0088a) constructor.newInstance(new Object[]{context, attributeSet});
        } catch (Throwable e) {
            throw new RuntimeException("Could not inflate Behavior subclass " + str, e);
        }
    }

    /* renamed from: a */
    private static void m514a(Rect rect) {
        rect.setEmpty();
        f314f.mo333a(rect);
    }

    /* renamed from: a */
    private void m515a(C0104d c0104d, Rect rect, int i, int i2) {
        int width = getWidth();
        int height = getHeight();
        width = Math.max(getPaddingLeft() + c0104d.leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - c0104d.rightMargin));
        height = Math.max(getPaddingTop() + c0104d.topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - c0104d.bottomMargin));
        rect.set(width, height, width + i, height + i2);
    }

    /* renamed from: a */
    private void m516a(View view, int i, Rect rect, Rect rect2, C0104d c0104d, int i2, int i3) {
        int a = C0625f.m3120a(m528e(c0104d.f294c), i);
        int a2 = C0625f.m3120a(m524c(c0104d.f295d), i);
        int i4 = a & 7;
        int i5 = a & 112;
        int i6 = a2 & 112;
        switch (a2 & 7) {
            case 1:
                a2 = (rect.width() / 2) + rect.left;
                break;
            case 5:
                a2 = rect.right;
                break;
            default:
                a2 = rect.left;
                break;
        }
        switch (i6) {
            case 16:
                a = rect.top + (rect.height() / 2);
                break;
            case 80:
                a = rect.bottom;
                break;
            default:
                a = rect.top;
                break;
        }
        switch (i4) {
            case 1:
                a2 -= i2 / 2;
                break;
            case 5:
                break;
            default:
                a2 -= i2;
                break;
        }
        switch (i5) {
            case 16:
                a -= i3 / 2;
                break;
            case 80:
                break;
            default:
                a -= i3;
                break;
        }
        rect2.set(a2, a, a2 + i2, a + i3);
    }

    /* renamed from: a */
    private void m517a(View view, Rect rect, int i) {
        if (ah.m2767G(view) && view.getWidth() > 0 && view.getHeight() > 0) {
            C0104d c0104d = (C0104d) view.getLayoutParams();
            C0088a b = c0104d.m501b();
            Rect e = m529e();
            Rect e2 = m529e();
            e2.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (b == null || !b.mo111a(this, view, e)) {
                e.set(e2);
            } else if (!e2.contains(e)) {
                throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + e.toShortString() + " | Bounds:" + e2.toShortString());
            }
            m514a(e2);
            if (e.isEmpty()) {
                m514a(e);
                return;
            }
            int i2;
            int height;
            int width;
            int a = C0625f.m3120a(c0104d.f299h, i);
            if ((a & 48) == 48) {
                i2 = (e.top - c0104d.topMargin) - c0104d.f301j;
                if (i2 < rect.top) {
                    m530e(view, rect.top - i2);
                    i2 = 1;
                    if ((a & 80) == 80) {
                        height = ((getHeight() - e.bottom) - c0104d.bottomMargin) + c0104d.f301j;
                        if (height < rect.bottom) {
                            m530e(view, height - rect.bottom);
                            i2 = 1;
                        }
                    }
                    if (i2 == 0) {
                        m530e(view, 0);
                    }
                    if ((a & 3) == 3) {
                        i2 = (e.left - c0104d.leftMargin) - c0104d.f300i;
                        if (i2 < rect.left) {
                            m527d(view, rect.left - i2);
                            i2 = 1;
                            if ((a & 5) == 5) {
                                width = c0104d.f300i + ((getWidth() - e.right) - c0104d.rightMargin);
                                if (width < rect.right) {
                                    m527d(view, width - rect.right);
                                    width = 1;
                                    if (width == 0) {
                                        m527d(view, 0);
                                    }
                                    m514a(e);
                                }
                            }
                            width = i2;
                            if (width == 0) {
                                m527d(view, 0);
                            }
                            m514a(e);
                        }
                    }
                    i2 = 0;
                    if ((a & 5) == 5) {
                        width = c0104d.f300i + ((getWidth() - e.right) - c0104d.rightMargin);
                        if (width < rect.right) {
                            m527d(view, width - rect.right);
                            width = 1;
                            if (width == 0) {
                                m527d(view, 0);
                            }
                            m514a(e);
                        }
                    }
                    width = i2;
                    if (width == 0) {
                        m527d(view, 0);
                    }
                    m514a(e);
                }
            }
            i2 = 0;
            if ((a & 80) == 80) {
                height = ((getHeight() - e.bottom) - c0104d.bottomMargin) + c0104d.f301j;
                if (height < rect.bottom) {
                    m530e(view, height - rect.bottom);
                    i2 = 1;
                }
            }
            if (i2 == 0) {
                m530e(view, 0);
            }
            if ((a & 3) == 3) {
                i2 = (e.left - c0104d.leftMargin) - c0104d.f300i;
                if (i2 < rect.left) {
                    m527d(view, rect.left - i2);
                    i2 = 1;
                    if ((a & 5) == 5) {
                        width = c0104d.f300i + ((getWidth() - e.right) - c0104d.rightMargin);
                        if (width < rect.right) {
                            m527d(view, width - rect.right);
                            width = 1;
                            if (width == 0) {
                                m527d(view, 0);
                            }
                            m514a(e);
                        }
                    }
                    width = i2;
                    if (width == 0) {
                        m527d(view, 0);
                    }
                    m514a(e);
                }
            }
            i2 = 0;
            if ((a & 5) == 5) {
                width = c0104d.f300i + ((getWidth() - e.right) - c0104d.rightMargin);
                if (width < rect.right) {
                    m527d(view, width - rect.right);
                    width = 1;
                    if (width == 0) {
                        m527d(view, 0);
                    }
                    m514a(e);
                }
            }
            width = i2;
            if (width == 0) {
                m527d(view, 0);
            }
            m514a(e);
        }
    }

    /* renamed from: a */
    private void m518a(View view, View view2, int i) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        Rect e = m529e();
        Rect e2 = m529e();
        try {
            m544a(view2, e);
            m543a(view, i, e, e2);
            view.layout(e2.left, e2.top, e2.right, e2.bottom);
        } finally {
            m514a(e);
            m514a(e2);
        }
    }

    /* renamed from: a */
    private void m519a(List<View> list) {
        list.clear();
        boolean isChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        int i = childCount - 1;
        while (i >= 0) {
            list.add(getChildAt(isChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i) : i));
            i--;
        }
        if (f313d != null) {
            Collections.sort(list, f313d);
        }
    }

    /* renamed from: a */
    private boolean m520a(MotionEvent motionEvent, int i) {
        boolean z;
        boolean z2 = false;
        Object obj = null;
        MotionEvent motionEvent2 = null;
        int a = C0659t.m3205a(motionEvent);
        List list = this.f318i;
        m519a(list);
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2;
            MotionEvent motionEvent3;
            View view = (View) list.get(i2);
            C0104d c0104d = (C0104d) view.getLayoutParams();
            C0088a b = c0104d.m501b();
            if ((!z2 && obj == null) || a == 0) {
                if (!(z2 || b == null)) {
                    switch (i) {
                        case 0:
                            z2 = b.mo63a(this, view, motionEvent);
                            break;
                        case 1:
                            z2 = b.mo64b(this, view, motionEvent);
                            break;
                    }
                    if (z2) {
                        this.f325p = view;
                    }
                }
                z = z2;
                boolean e = c0104d.m506e();
                boolean a2 = c0104d.m499a(this, view);
                Object obj3 = (!a2 || e) ? null : 1;
                if (a2 && obj3 == null) {
                    list.clear();
                    return z;
                }
                MotionEvent motionEvent4 = motionEvent2;
                obj2 = obj3;
                motionEvent3 = motionEvent4;
            } else if (b != null) {
                if (motionEvent2 == null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    motionEvent3 = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                } else {
                    motionEvent3 = motionEvent2;
                }
                switch (i) {
                    case 0:
                        b.mo63a(this, view, motionEvent3);
                        break;
                    case 1:
                        b.mo64b(this, view, motionEvent3);
                        break;
                }
                obj2 = obj;
                z = z2;
            } else {
                motionEvent3 = motionEvent2;
                z = z2;
                obj2 = obj;
            }
            i2++;
            obj = obj2;
            z2 = z;
            motionEvent2 = motionEvent3;
        }
        z = z2;
        list.clear();
        return z;
    }

    /* renamed from: b */
    private int m521b(int i) {
        if (this.f324o == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i);
            return 0;
        } else if (i >= 0 && i < this.f324o.length) {
            return this.f324o[i];
        } else {
            Log.e("CoordinatorLayout", "Keyline index " + i + " out of range for " + this);
            return 0;
        }
    }

    /* renamed from: b */
    private be m522b(be beVar) {
        if (beVar.m3081e()) {
            return beVar;
        }
        be a;
        int childCount = getChildCount();
        int i = 0;
        be beVar2 = beVar;
        while (i < childCount) {
            View childAt = getChildAt(i);
            if (ah.m2835y(childAt)) {
                C0088a b = ((C0104d) childAt.getLayoutParams()).m501b();
                if (b != null) {
                    a = b.m321a(this, childAt, beVar2);
                    if (a.m3081e()) {
                        break;
                    }
                    i++;
                    beVar2 = a;
                }
            }
            a = beVar2;
            i++;
            beVar2 = a;
        }
        a = beVar2;
        return a;
    }

    /* renamed from: b */
    private void m523b(View view, int i, int i2) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        int a = C0625f.m3120a(m526d(c0104d.f294c), i2);
        int i3 = a & 7;
        int i4 = a & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (i2 == 1) {
            i = width - i;
        }
        int b = m521b(i) - measuredWidth;
        a = 0;
        switch (i3) {
            case 1:
                b += measuredWidth / 2;
                break;
            case 5:
                b += measuredWidth;
                break;
        }
        switch (i4) {
            case 16:
                a = 0 + (measuredHeight / 2);
                break;
            case 80:
                a = 0 + measuredHeight;
                break;
        }
        b = Math.max(getPaddingLeft() + c0104d.leftMargin, Math.min(b, ((width - getPaddingRight()) - measuredWidth) - c0104d.rightMargin));
        int max = Math.max(getPaddingTop() + c0104d.topMargin, Math.min(a, ((height - getPaddingBottom()) - measuredHeight) - c0104d.bottomMargin));
        view.layout(b, max, b + measuredWidth, max + measuredHeight);
    }

    /* renamed from: c */
    private static int m524c(int i) {
        int i2 = (i & 7) == 0 ? 8388611 | i : i;
        return (i2 & 112) == 0 ? i2 | 48 : i2;
    }

    /* renamed from: c */
    private void m525c(View view, int i) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        Rect e = m529e();
        e.set(getPaddingLeft() + c0104d.leftMargin, getPaddingTop() + c0104d.topMargin, (getWidth() - getPaddingRight()) - c0104d.rightMargin, (getHeight() - getPaddingBottom()) - c0104d.bottomMargin);
        if (!(this.f330u == null || !ah.m2835y(this) || ah.m2835y(view))) {
            e.left += this.f330u.m3076a();
            e.top += this.f330u.m3078b();
            e.right -= this.f330u.m3079c();
            e.bottom -= this.f330u.m3080d();
        }
        Rect e2 = m529e();
        C0625f.m3121a(m524c(c0104d.f294c), view.getMeasuredWidth(), view.getMeasuredHeight(), e, e2, i);
        view.layout(e2.left, e2.top, e2.right, e2.bottom);
        m514a(e);
        m514a(e2);
    }

    /* renamed from: d */
    private static int m526d(int i) {
        return i == 0 ? 8388661 : i;
    }

    /* renamed from: d */
    private void m527d(View view, int i) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        if (c0104d.f300i != i) {
            ah.m2811f(view, i - c0104d.f300i);
            c0104d.f300i = i;
        }
    }

    /* renamed from: e */
    private static int m528e(int i) {
        return i == 0 ? 17 : i;
    }

    /* renamed from: e */
    private static Rect m529e() {
        Rect rect = (Rect) f314f.mo332a();
        return rect == null ? new Rect() : rect;
    }

    /* renamed from: e */
    private void m530e(View view, int i) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        if (c0104d.f301j != i) {
            ah.m2808e(view, i - c0104d.f301j);
            c0104d.f301j = i;
        }
    }

    /* renamed from: e */
    private boolean m531e(View view) {
        return this.f317h.m743e(view);
    }

    /* renamed from: f */
    private void m532f() {
        if (this.f325p != null) {
            C0088a b = ((C0104d) this.f325p.getLayoutParams()).m501b();
            if (b != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                b.mo64b(this, this.f325p, obtain);
                obtain.recycle();
            }
            this.f325p = null;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((C0104d) getChildAt(i).getLayoutParams()).m507f();
        }
        this.f322m = false;
    }

    /* renamed from: g */
    private void m533g() {
        this.f316g.clear();
        this.f317h.m736a();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Object childAt = getChildAt(i);
            m536a((View) childAt).m502b(this, (View) childAt);
            this.f317h.m737a(childAt);
            for (int i2 = 0; i2 < childCount; i2++) {
                if (i2 != i) {
                    Object childAt2 = getChildAt(i2);
                    if (m536a((View) childAt2).m500a(this, childAt2, childAt)) {
                        if (!this.f317h.m740b(childAt2)) {
                            this.f317h.m737a(childAt2);
                        }
                        this.f317h.m738a(childAt, childAt2);
                    }
                }
            }
        }
        this.f316g.addAll(this.f317h.m739b());
        Collections.reverse(this.f316g);
    }

    /* renamed from: h */
    private void m534h() {
        if (VERSION.SDK_INT >= 21) {
            if (ah.m2835y(this)) {
                if (this.f333x == null) {
                    this.f333x = new C01001(this);
                }
                ah.m2785a((View) this, this.f333x);
                setSystemUiVisibility(1280);
                return;
            }
            ah.m2785a((View) this, null);
        }
    }

    /* renamed from: a */
    public C0104d m535a(AttributeSet attributeSet) {
        return new C0104d(getContext(), attributeSet);
    }

    /* renamed from: a */
    C0104d m536a(View view) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        if (!c0104d.f293b) {
            C0102b c0102b = null;
            for (Class cls = view.getClass(); cls != null; cls = cls.getSuperclass()) {
                c0102b = (C0102b) cls.getAnnotation(C0102b.class);
                if (c0102b != null) {
                    break;
                }
            }
            C0102b c0102b2 = c0102b;
            if (c0102b2 != null) {
                try {
                    c0104d.m497a((C0088a) c0102b2.m491a().newInstance());
                } catch (Throwable e) {
                    Log.e("CoordinatorLayout", "Default behavior class " + c0102b2.m491a().getName() + " could not be instantiated. Did you forget a default constructor?", e);
                }
            }
            c0104d.f293b = true;
        }
        return c0104d;
    }

    /* renamed from: a */
    protected C0104d m537a(LayoutParams layoutParams) {
        return layoutParams instanceof C0104d ? new C0104d((C0104d) layoutParams) : layoutParams instanceof MarginLayoutParams ? new C0104d((MarginLayoutParams) layoutParams) : new C0104d(layoutParams);
    }

    /* renamed from: a */
    final be m538a(be beVar) {
        boolean z = true;
        if (ad.m660a(this.f330u, (Object) beVar)) {
            return beVar;
        }
        this.f330u = beVar;
        boolean z2 = beVar != null && beVar.m3078b() > 0;
        this.f331v = z2;
        if (this.f331v || getBackground() != null) {
            z = false;
        }
        setWillNotDraw(z);
        beVar = m522b(beVar);
        requestLayout();
        return beVar;
    }

    /* renamed from: a */
    void m539a() {
        boolean z = false;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (m531e(getChildAt(i))) {
                z = true;
                break;
            }
        }
        if (z == this.f329t) {
            return;
        }
        if (z) {
            m547b();
        } else {
            m552c();
        }
    }

    /* renamed from: a */
    final void m540a(int i) {
        int g = ah.m2812g(this);
        int size = this.f316g.size();
        Rect e = m529e();
        Rect e2 = m529e();
        Rect e3 = m529e();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) this.f316g.get(i2);
            C0104d c0104d = (C0104d) view.getLayoutParams();
            if (i != 0 || view.getVisibility() != 8) {
                int i3;
                for (i3 = 0; i3 < i2; i3++) {
                    if (c0104d.f303l == ((View) this.f316g.get(i3))) {
                        m549b(view, g);
                    }
                }
                m545a(view, true, e2);
                if (!(c0104d.f298g == 0 || e2.isEmpty())) {
                    int a = C0625f.m3120a(c0104d.f298g, g);
                    switch (a & 112) {
                        case 48:
                            e.top = Math.max(e.top, e2.bottom);
                            break;
                        case 80:
                            e.bottom = Math.max(e.bottom, getHeight() - e2.top);
                            break;
                    }
                    switch (a & 7) {
                        case 3:
                            e.left = Math.max(e.left, e2.right);
                            break;
                        case 5:
                            e.right = Math.max(e.right, getWidth() - e2.left);
                            break;
                    }
                }
                if (c0104d.f299h != 0 && view.getVisibility() == 0) {
                    m517a(view, e, g);
                }
                if (i != 2) {
                    m553c(view, e3);
                    if (!e3.equals(e2)) {
                        m550b(view, e2);
                    }
                }
                for (i3 = i2 + 1; i3 < size; i3++) {
                    View view2 = (View) this.f316g.get(i3);
                    C0104d c0104d2 = (C0104d) view2.getLayoutParams();
                    C0088a b = c0104d2.m501b();
                    if (b != null && b.mo86b(this, view2, view)) {
                        if (i == 0 && c0104d2.m510i()) {
                            c0104d2.m511j();
                        } else {
                            boolean z;
                            switch (i) {
                                case 2:
                                    b.m343d(this, view2, view);
                                    z = true;
                                    break;
                                default:
                                    z = b.mo87c(this, view2, view);
                                    break;
                            }
                            if (i == 1) {
                                c0104d2.m503b(z);
                            }
                        }
                    }
                }
            }
        }
        m514a(e);
        m514a(e2);
        m514a(e3);
    }

    /* renamed from: a */
    public void m541a(View view, int i) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        if (c0104d.m505d()) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        } else if (c0104d.f302k != null) {
            m518a(view, c0104d.f302k, i);
        } else if (c0104d.f296e >= 0) {
            m523b(view, c0104d.f296e, i);
        } else {
            m525c(view, i);
        }
    }

    /* renamed from: a */
    public void m542a(View view, int i, int i2, int i3, int i4) {
        measureChildWithMargins(view, i, i2, i3, i4);
    }

    /* renamed from: a */
    void m543a(View view, int i, Rect rect, Rect rect2) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        m516a(view, i, rect, rect2, c0104d, measuredWidth, measuredHeight);
        m515a(c0104d, rect2, measuredWidth, measuredHeight);
    }

    /* renamed from: a */
    void m544a(View view, Rect rect) {
        C0210z.m991b(this, view, rect);
    }

    /* renamed from: a */
    void m545a(View view, boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            m544a(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    /* renamed from: a */
    public boolean m546a(View view, int i, int i2) {
        Rect e = m529e();
        m544a(view, e);
        try {
            boolean contains = e.contains(i, i2);
            return contains;
        } finally {
            m514a(e);
        }
    }

    /* renamed from: b */
    void m547b() {
        if (this.f323n) {
            if (this.f328s == null) {
                this.f328s = new C0105e(this);
            }
            getViewTreeObserver().addOnPreDrawListener(this.f328s);
        }
        this.f329t = true;
    }

    /* renamed from: b */
    public void m548b(View view) {
        List c = this.f317h.m741c(view);
        if (c != null && !c.isEmpty()) {
            for (int i = 0; i < c.size(); i++) {
                View view2 = (View) c.get(i);
                C0088a b = ((C0104d) view2.getLayoutParams()).m501b();
                if (b != null) {
                    b.mo87c(this, view2, view);
                }
            }
        }
    }

    /* renamed from: b */
    void m549b(View view, int i) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        if (c0104d.f302k != null) {
            Rect e = m529e();
            Rect e2 = m529e();
            Rect e3 = m529e();
            m544a(c0104d.f302k, e);
            m545a(view, false, e2);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            m516a(view, i, e, e3, c0104d, measuredWidth, measuredHeight);
            boolean z = (e3.left == e2.left && e3.top == e2.top) ? false : true;
            m515a(c0104d, e3, measuredWidth, measuredHeight);
            int i2 = e3.left - e2.left;
            int i3 = e3.top - e2.top;
            if (i2 != 0) {
                ah.m2811f(view, i2);
            }
            if (i3 != 0) {
                ah.m2808e(view, i3);
            }
            if (z) {
                C0088a b = c0104d.m501b();
                if (b != null) {
                    b.mo87c(this, view, c0104d.f302k);
                }
            }
            m514a(e);
            m514a(e2);
            m514a(e3);
        }
    }

    /* renamed from: b */
    void m550b(View view, Rect rect) {
        ((C0104d) view.getLayoutParams()).m496a(rect);
    }

    /* renamed from: c */
    public List<View> m551c(View view) {
        Collection d = this.f317h.m742d(view);
        this.f319j.clear();
        if (d != null) {
            this.f319j.addAll(d);
        }
        return this.f319j;
    }

    /* renamed from: c */
    void m552c() {
        if (this.f323n && this.f328s != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.f328s);
        }
        this.f329t = false;
    }

    /* renamed from: c */
    void m553c(View view, Rect rect) {
        rect.set(((C0104d) view.getLayoutParams()).m504c());
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return (layoutParams instanceof C0104d) && super.checkLayoutParams(layoutParams);
    }

    /* renamed from: d */
    protected C0104d m554d() {
        return new C0104d(-2, -2);
    }

    /* renamed from: d */
    public List<View> m555d(View view) {
        Collection c = this.f317h.m741c(view);
        this.f319j.clear();
        if (c != null) {
            this.f319j.addAll(c);
        }
        return this.f319j;
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        C0104d c0104d = (C0104d) view.getLayoutParams();
        if (c0104d.f292a != null) {
            float d = c0104d.f292a.m342d(this, view);
            if (d > BitmapDescriptorFactory.HUE_RED) {
                if (this.f321l == null) {
                    this.f321l = new Paint();
                }
                this.f321l.setColor(c0104d.f292a.m339c(this, view));
                this.f321l.setAlpha(C0168n.m809a(Math.round(d * 255.0f), 0, 255));
                int save = canvas.save();
                if (view.isOpaque()) {
                    canvas.clipRect((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom(), Op.DIFFERENCE);
                }
                canvas.drawRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()), this.f321l);
                canvas.restoreToCount(save);
            }
        }
        return super.drawChild(canvas, view, j);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        int i = 0;
        Drawable drawable = this.f332w;
        if (drawable != null && drawable.isStateful()) {
            i = 0 | drawable.setState(drawableState);
        }
        if (i != 0) {
            invalidate();
        }
    }

    protected /* synthetic */ LayoutParams generateDefaultLayoutParams() {
        return m554d();
    }

    public /* synthetic */ LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return m535a(attributeSet);
    }

    protected /* synthetic */ LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return m537a(layoutParams);
    }

    final List<View> getDependencySortedChildren() {
        m533g();
        return Collections.unmodifiableList(this.f316g);
    }

    final be getLastWindowInsets() {
        return this.f330u;
    }

    public int getNestedScrollAxes() {
        return this.f334y.m3217a();
    }

    public Drawable getStatusBarBackground() {
        return this.f332w;
    }

    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
    }

    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        m532f();
        if (this.f329t) {
            if (this.f328s == null) {
                this.f328s = new C0105e(this);
            }
            getViewTreeObserver().addOnPreDrawListener(this.f328s);
        }
        if (this.f330u == null && ah.m2835y(this)) {
            ah.m2834x(this);
        }
        this.f323n = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        m532f();
        if (this.f329t && this.f328s != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.f328s);
        }
        if (this.f327r != null) {
            onStopNestedScroll(this.f327r);
        }
        this.f323n = false;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f331v && this.f332w != null) {
            int b = this.f330u != null ? this.f330u.m3078b() : 0;
            if (b > 0) {
                this.f332w.setBounds(0, 0, getWidth(), b);
                this.f332w.draw(canvas);
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = null;
        int a = C0659t.m3205a(motionEvent);
        if (a == 0) {
            m532f();
        }
        boolean a2 = m520a(motionEvent, 0);
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        if (a == 1 || a == 3) {
            m532f();
        }
        return a2;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int g = ah.m2812g(this);
        int size = this.f316g.size();
        for (int i5 = 0; i5 < size; i5++) {
            View view = (View) this.f316g.get(i5);
            if (view.getVisibility() != 8) {
                C0088a b = ((C0104d) view.getLayoutParams()).m501b();
                if (b == null || !b.mo62a(this, view, g)) {
                    m541a(view, g);
                }
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        m533g();
        m539a();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int g = ah.m2812g(this);
        Object obj = g == 1 ? 1 : null;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        int i3 = paddingLeft + paddingRight;
        int i4 = paddingTop + paddingBottom;
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        int i5 = 0;
        Object obj2 = (this.f330u == null || !ah.m2835y(this)) ? null : 1;
        int size3 = this.f316g.size();
        int i6 = 0;
        while (i6 < size3) {
            int i7;
            View view = (View) this.f316g.get(i6);
            if (view.getVisibility() == 8) {
                i7 = i5;
                paddingTop = suggestedMinimumHeight;
                paddingBottom = suggestedMinimumWidth;
            } else {
                int i8;
                int i9;
                C0104d c0104d = (C0104d) view.getLayoutParams();
                int i10 = 0;
                if (c0104d.f296e >= 0 && mode != 0) {
                    i7 = m521b(c0104d.f296e);
                    paddingTop = C0625f.m3120a(m526d(c0104d.f294c), g) & 7;
                    if ((paddingTop == 3 && obj == null) || (paddingTop == 5 && obj != null)) {
                        i10 = Math.max(0, (size - paddingRight) - i7);
                    } else if ((paddingTop == 5 && obj == null) || (paddingTop == 3 && obj != null)) {
                        i10 = Math.max(0, i7 - paddingLeft);
                    }
                }
                if (obj2 == null || ah.m2835y(view)) {
                    i8 = i2;
                    i9 = i;
                } else {
                    paddingTop = this.f330u.m3078b() + this.f330u.m3080d();
                    i9 = MeasureSpec.makeMeasureSpec(size - (this.f330u.m3076a() + this.f330u.m3079c()), mode);
                    i8 = MeasureSpec.makeMeasureSpec(size2 - paddingTop, mode2);
                }
                C0088a b = c0104d.m501b();
                if (b == null || !b.mo74a(this, view, i9, i10, i8, 0)) {
                    m542a(view, i9, i10, i8, 0);
                }
                i9 = Math.max(suggestedMinimumWidth, ((view.getMeasuredWidth() + i3) + c0104d.leftMargin) + c0104d.rightMargin);
                paddingTop = Math.max(suggestedMinimumHeight, ((view.getMeasuredHeight() + i4) + c0104d.topMargin) + c0104d.bottomMargin);
                i7 = ah.m2772a(i5, ah.m2816i(view));
                paddingBottom = i9;
            }
            i6++;
            i5 = i7;
            suggestedMinimumHeight = paddingTop;
            suggestedMinimumWidth = paddingBottom;
        }
        setMeasuredDimension(ah.m2773a(suggestedMinimumWidth, i, Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i5), ah.m2773a(suggestedMinimumHeight, i2, i5 << 16));
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        int childCount = getChildCount();
        int i = 0;
        boolean z2 = false;
        while (i < childCount) {
            boolean z3;
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 8) {
                z3 = z2;
            } else {
                C0104d c0104d = (C0104d) childAt.getLayoutParams();
                if (c0104d.m509h()) {
                    C0088a b = c0104d.m501b();
                    z3 = b != null ? b.mo75a(this, childAt, view, f, f2, z) | z2 : z2;
                } else {
                    z3 = z2;
                }
            }
            i++;
            z2 = z3;
        }
        if (z2) {
            m540a(1);
        }
        return z2;
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        int childCount = getChildCount();
        int i = 0;
        boolean z = false;
        while (i < childCount) {
            boolean z2;
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 8) {
                z2 = z;
            } else {
                C0104d c0104d = (C0104d) childAt.getLayoutParams();
                if (c0104d.m509h()) {
                    C0088a b = c0104d.m501b();
                    z2 = b != null ? b.mo95a(this, childAt, view, f, f2) | z : z;
                } else {
                    z2 = z;
                }
            }
            i++;
            z = z2;
        }
        return z;
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        int i3 = 0;
        int i4 = 0;
        Object obj = null;
        int childCount = getChildCount();
        int i5 = 0;
        while (i5 < childCount) {
            Object obj2;
            int i6;
            int i7;
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 8) {
                obj2 = obj;
                i6 = i3;
                i7 = i4;
            } else {
                C0104d c0104d = (C0104d) childAt.getLayoutParams();
                if (c0104d.m509h()) {
                    C0088a b = c0104d.m501b();
                    if (b != null) {
                        int[] iArr2 = this.f320k;
                        this.f320k[1] = 0;
                        iArr2[0] = 0;
                        b.mo72a(this, childAt, view, i, i2, this.f320k);
                        i6 = i > 0 ? Math.max(i3, this.f320k[0]) : Math.min(i3, this.f320k[0]);
                        i7 = i2 > 0 ? Math.max(i4, this.f320k[1]) : Math.min(i4, this.f320k[1]);
                        int i8 = 1;
                    } else {
                        obj2 = obj;
                        i6 = i3;
                        i7 = i4;
                    }
                } else {
                    obj2 = obj;
                    i6 = i3;
                    i7 = i4;
                }
            }
            i5++;
            i4 = i7;
            i3 = i6;
            obj = obj2;
        }
        iArr[0] = i3;
        iArr[1] = i4;
        if (obj != null) {
            m540a(1);
        }
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        Object obj = null;
        int i5 = 0;
        while (i5 < childCount) {
            Object obj2;
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 8) {
                obj2 = obj;
            } else {
                C0104d c0104d = (C0104d) childAt.getLayoutParams();
                if (c0104d.m509h()) {
                    C0088a b = c0104d.m501b();
                    if (b != null) {
                        b.mo71a(this, childAt, view, i, i2, i3, i4);
                        obj2 = 1;
                    } else {
                        obj2 = obj;
                    }
                } else {
                    obj2 = obj;
                }
            }
            i5++;
            obj = obj2;
        }
        if (obj != null) {
            m540a(1);
        }
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.f334y.m3219a(view, view2, i);
        this.f326q = view;
        this.f327r = view2;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            C0104d c0104d = (C0104d) childAt.getLayoutParams();
            if (c0104d.m509h()) {
                C0088a b = c0104d.m501b();
                if (b != null) {
                    b.m336b(this, childAt, view, view2, i);
                }
            }
        }
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            SparseArray sparseArray = savedState.f290a;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                int id = childAt.getId();
                C0088a b = m536a(childAt).m501b();
                if (!(id == -1 || b == null)) {
                    Parcelable parcelable2 = (Parcelable) sparseArray.get(id);
                    if (parcelable2 != null) {
                        b.mo69a(this, childAt, parcelable2);
                    }
                }
            }
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            C0088a b = ((C0104d) childAt.getLayoutParams()).m501b();
            if (!(id == -1 || b == null)) {
                Parcelable b2 = b.mo79b(this, childAt);
                if (b2 != null) {
                    sparseArray.append(id, b2);
                }
            }
        }
        savedState.f290a = sparseArray;
        return savedState;
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        int childCount = getChildCount();
        int i2 = 0;
        boolean z = false;
        while (i2 < childCount) {
            boolean z2;
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 8) {
                z2 = z;
            } else {
                C0104d c0104d = (C0104d) childAt.getLayoutParams();
                C0088a b = c0104d.m501b();
                if (b != null) {
                    boolean a = b.mo76a(this, childAt, view, view2, i);
                    z2 = z | a;
                    c0104d.m498a(a);
                } else {
                    c0104d.m498a(false);
                    z2 = z;
                }
            }
            i2++;
            z = z2;
        }
        return z;
    }

    public void onStopNestedScroll(View view) {
        this.f334y.m3218a(view);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            C0104d c0104d = (C0104d) childAt.getLayoutParams();
            if (c0104d.m509h()) {
                C0088a b = c0104d.m501b();
                if (b != null) {
                    b.mo70a(this, childAt, view);
                }
                c0104d.m508g();
                c0104d.m511j();
            }
        }
        this.f326q = null;
        this.f327r = null;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        MotionEvent motionEvent2 = null;
        int a = C0659t.m3205a(motionEvent);
        boolean z2;
        MotionEvent obtain;
        if (this.f325p == null) {
            boolean a2 = m520a(motionEvent, 1);
            if (a2) {
                z2 = a2;
            } else {
                z2 = a2;
                z = false;
                if (this.f325p == null) {
                    z |= super.onTouchEvent(motionEvent);
                } else if (z2) {
                    if (null != null) {
                        long uptimeMillis = SystemClock.uptimeMillis();
                        obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                    } else {
                        obtain = null;
                    }
                    super.onTouchEvent(obtain);
                    motionEvent2 = obtain;
                }
                if (!z || a == 0) {
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                    }
                    if (a == 1 || a == 3) {
                        m532f();
                    }
                    return z;
                }
                if (motionEvent2 != null) {
                    motionEvent2.recycle();
                }
                m532f();
                return z;
            }
        }
        z2 = false;
        C0088a b = ((C0104d) this.f325p.getLayoutParams()).m501b();
        z = b != null ? b.mo64b(this, this.f325p, motionEvent) : false;
        if (this.f325p == null) {
            z |= super.onTouchEvent(motionEvent);
        } else if (z2) {
            if (null != null) {
                obtain = null;
            } else {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                obtain = MotionEvent.obtain(uptimeMillis2, uptimeMillis2, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
            }
            super.onTouchEvent(obtain);
            motionEvent2 = obtain;
        }
        if (z) {
        }
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        m532f();
        return z;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        C0088a b = ((C0104d) view.getLayoutParams()).m501b();
        return (b == null || !b.mo83a(this, view, rect, z)) ? super.requestChildRectangleOnScreen(view, rect, z) : true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z && !this.f322m) {
            m532f();
            this.f322m = true;
        }
    }

    public void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        m534h();
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
        this.f315e = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(Drawable drawable) {
        Drawable drawable2 = null;
        if (this.f332w != drawable) {
            if (this.f332w != null) {
                this.f332w.setCallback(null);
            }
            if (drawable != null) {
                drawable2 = drawable.mutate();
            }
            this.f332w = drawable2;
            if (this.f332w != null) {
                if (this.f332w.isStateful()) {
                    this.f332w.setState(getDrawableState());
                }
                C0375a.m1779b(this.f332w, ah.m2812g(this));
                this.f332w.setVisible(getVisibility() == 0, false);
                this.f332w.setCallback(this);
            }
            ah.m2799c(this);
        }
    }

    public void setStatusBarBackgroundColor(int i) {
        setStatusBarBackground(new ColorDrawable(i));
    }

    public void setStatusBarBackgroundResource(int i) {
        setStatusBarBackground(i != 0 ? C0235a.m1066a(getContext(), i) : null);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        if (this.f332w != null && this.f332w.isVisible() != z) {
            this.f332w.setVisible(z, false);
        }
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f332w;
    }
}
