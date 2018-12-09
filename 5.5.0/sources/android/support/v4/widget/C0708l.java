package android.support.v4.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.p022f.C0482l;
import android.support.v4.view.C0074a;
import android.support.v4.view.ah;
import android.support.v4.view.au;
import android.support.v4.view.p023a.C0510a;
import android.support.v4.view.p023a.C0516c;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0546l;
import android.support.v4.view.p023a.C0556o;
import android.support.v4.widget.C0709m.C0703a;
import android.support.v4.widget.C0709m.C0705b;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.TLRPC;

/* renamed from: android.support.v4.widget.l */
public abstract class C0708l extends C0074a {
    /* renamed from: a */
    private static final Rect f1564a = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    /* renamed from: l */
    private static final C0703a<C0531e> f1565l = new C07041();
    /* renamed from: m */
    private static final C0705b<C0482l<C0531e>, C0531e> f1566m = new C07062();
    /* renamed from: b */
    private final Rect f1567b = new Rect();
    /* renamed from: c */
    private final Rect f1568c = new Rect();
    /* renamed from: d */
    private final Rect f1569d = new Rect();
    /* renamed from: e */
    private final int[] f1570e = new int[2];
    /* renamed from: f */
    private final AccessibilityManager f1571f;
    /* renamed from: g */
    private final View f1572g;
    /* renamed from: h */
    private C0707a f1573h;
    /* renamed from: i */
    private int f1574i = Integer.MIN_VALUE;
    /* renamed from: j */
    private int f1575j = Integer.MIN_VALUE;
    /* renamed from: k */
    private int f1576k = Integer.MIN_VALUE;

    /* renamed from: android.support.v4.widget.l$1 */
    static class C07041 implements C0703a<C0531e> {
        C07041() {
        }
    }

    /* renamed from: android.support.v4.widget.l$2 */
    static class C07062 implements C0705b<C0482l<C0531e>, C0531e> {
        C07062() {
        }
    }

    /* renamed from: android.support.v4.widget.l$a */
    private class C0707a extends C0546l {
        /* renamed from: a */
        final /* synthetic */ C0708l f1563a;

        C0707a(C0708l c0708l) {
            this.f1563a = c0708l;
        }

        /* renamed from: a */
        public C0531e mo583a(int i) {
            return C0531e.m2299a(this.f1563a.m3454a(i));
        }

        /* renamed from: a */
        public boolean mo584a(int i, int i2, Bundle bundle) {
            return this.f1563a.m3462a(i, i2, bundle);
        }

        /* renamed from: b */
        public C0531e mo585b(int i) {
            int a = i == 2 ? this.f1563a.f1574i : this.f1563a.f1575j;
            return a == Integer.MIN_VALUE ? null : mo583a(a);
        }
    }

    public C0708l(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.f1572g = view;
        this.f1571f = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        if (ah.m2803d(view) == 0) {
            ah.m2801c(view, 1);
        }
    }

    /* renamed from: a */
    private boolean m3440a(int i, Bundle bundle) {
        return ah.m2793a(this.f1572g, i, bundle);
    }

    /* renamed from: a */
    private boolean m3441a(Rect rect) {
        if (rect == null || rect.isEmpty() || this.f1572g.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent parent = this.f1572g.getParent();
        while (parent instanceof View) {
            View view = (View) parent;
            if (ah.m2806e(view) <= BitmapDescriptorFactory.HUE_RED || view.getVisibility() != 0) {
                return false;
            }
            parent = view.getParent();
        }
        return parent != null;
    }

    /* renamed from: c */
    private AccessibilityEvent m3443c(int i, int i2) {
        switch (i) {
            case -1:
                return mo3083e(i2);
            default:
                return m3446d(i, i2);
        }
    }

    /* renamed from: c */
    private boolean m3444c(int i, int i2, Bundle bundle) {
        switch (i2) {
            case 1:
                return m3466b(i);
            case 2:
                return m3469c(i);
            case 64:
                return m3450g(i);
            case 128:
                return m3451h(i);
            default:
                return mo3080b(i, i2, bundle);
        }
    }

    /* renamed from: d */
    private C0531e mo3081d() {
        C0531e a = C0531e.m2300a(this.f1572g);
        ah.m2782a(this.f1572g, a);
        List arrayList = new ArrayList();
        mo3079a(arrayList);
        if (a.m2316c() <= 0 || arrayList.size() <= 0) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                a.m2312b(this.f1572g, ((Integer) arrayList.get(i)).intValue());
            }
            return a;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    /* renamed from: d */
    private AccessibilityEvent m3446d(int i, int i2) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        C0556o a = C0510a.m2132a(obtain);
        C0531e a2 = m3454a(i);
        a.m2474a().add(a2.m2347q());
        a.m2480b(a2.m2348r());
        a.m2485d(a2.m2344n());
        a.m2483c(a2.m2343m());
        a.m2481b(a2.m2342l());
        a.m2478a(a2.m2331f());
        mo3078a(i, obtain);
        if (obtain.getText().isEmpty() && obtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        a.m2477a(a2.m2346p());
        a.m2476a(this.f1572g, i);
        obtain.setPackageName(this.f1572g.getContext().getPackageName());
        return obtain;
    }

    /* renamed from: d */
    private void mo3082d(int i) {
        if (this.f1576k != i) {
            int i2 = this.f1576k;
            this.f1576k = i;
            m3461a(i, 128);
            m3461a(i2, 256);
        }
    }

    /* renamed from: e */
    private AccessibilityEvent mo3083e(int i) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
        ah.m2786a(this.f1572g, obtain);
        return obtain;
    }

    /* renamed from: f */
    private C0531e m3449f(int i) {
        C0531e b = C0531e.m2302b();
        b.m2334h(true);
        b.m2322c(true);
        b.m2313b((CharSequence) "android.view.View");
        b.m2310b(f1564a);
        b.m2324d(f1564a);
        b.m2311b(this.f1572g);
        mo3077a(i, b);
        if (b.m2347q() == null && b.m2348r() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        b.m2306a(this.f1568c);
        if (this.f1568c.equals(f1564a)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int d = b.m2323d();
        if ((d & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else if ((d & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else {
            b.m2308a(this.f1572g.getContext().getPackageName());
            b.m2307a(this.f1572g, i);
            if (this.f1574i == i) {
                b.m2330f(true);
                b.m2305a(128);
            } else {
                b.m2330f(false);
                b.m2305a(64);
            }
            boolean z = this.f1575j == i;
            if (z) {
                b.m2305a(2);
            } else if (b.m2333g()) {
                b.m2305a(1);
            }
            b.m2326d(z);
            this.f1572g.getLocationOnScreen(this.f1570e);
            b.m2317c(this.f1567b);
            if (this.f1567b.equals(f1564a)) {
                b.m2306a(this.f1567b);
                if (b.f1302b != -1) {
                    C0531e b2 = C0531e.m2302b();
                    for (d = b.f1302b; d != -1; d = b2.f1302b) {
                        b2.m2319c(this.f1572g, -1);
                        b2.m2310b(f1564a);
                        mo3077a(d, b2);
                        b2.m2306a(this.f1568c);
                        this.f1567b.offset(this.f1568c.left, this.f1568c.top);
                    }
                    b2.m2349s();
                }
                this.f1567b.offset(this.f1570e[0] - this.f1572g.getScrollX(), this.f1570e[1] - this.f1572g.getScrollY());
            }
            if (this.f1572g.getLocalVisibleRect(this.f1569d)) {
                this.f1569d.offset(this.f1570e[0] - this.f1572g.getScrollX(), this.f1570e[1] - this.f1572g.getScrollY());
                this.f1567b.intersect(this.f1569d);
                b.m2324d(this.f1567b);
                if (m3441a(this.f1567b)) {
                    b.m2328e(true);
                }
            }
            return b;
        }
    }

    /* renamed from: g */
    private boolean m3450g(int i) {
        if (!this.f1571f.isEnabled() || !C0516c.m2140a(this.f1571f) || this.f1574i == i) {
            return false;
        }
        if (this.f1574i != Integer.MIN_VALUE) {
            m3451h(this.f1574i);
        }
        this.f1574i = i;
        this.f1572g.invalidate();
        m3461a(i, (int) TLRPC.MESSAGE_FLAG_EDITED);
        return true;
    }

    /* renamed from: h */
    private boolean m3451h(int i) {
        if (this.f1574i != i) {
            return false;
        }
        this.f1574i = Integer.MIN_VALUE;
        this.f1572g.invalidate();
        m3461a(i, (int) C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
        return true;
    }

    /* renamed from: a */
    public final int m3452a() {
        return this.f1574i;
    }

    /* renamed from: a */
    protected abstract int mo3076a(float f, float f2);

    /* renamed from: a */
    C0531e m3454a(int i) {
        return i == -1 ? mo3081d() : m3449f(i);
    }

    /* renamed from: a */
    protected abstract void mo3077a(int i, C0531e c0531e);

    /* renamed from: a */
    protected void mo3078a(int i, AccessibilityEvent accessibilityEvent) {
    }

    /* renamed from: a */
    protected void m3457a(int i, boolean z) {
    }

    /* renamed from: a */
    protected void m3458a(C0531e c0531e) {
    }

    /* renamed from: a */
    protected void m3459a(AccessibilityEvent accessibilityEvent) {
    }

    /* renamed from: a */
    protected abstract void mo3079a(List<Integer> list);

    /* renamed from: a */
    public final boolean m3461a(int i, int i2) {
        if (i == Integer.MIN_VALUE || !this.f1571f.isEnabled()) {
            return false;
        }
        ViewParent parent = this.f1572g.getParent();
        if (parent == null) {
            return false;
        }
        return au.m2966a(parent, this.f1572g, m3443c(i, i2));
    }

    /* renamed from: a */
    boolean m3462a(int i, int i2, Bundle bundle) {
        switch (i) {
            case -1:
                return m3440a(i2, bundle);
            default:
                return m3444c(i, i2, bundle);
        }
    }

    /* renamed from: a */
    public final boolean m3463a(MotionEvent motionEvent) {
        boolean z = true;
        if (!this.f1571f.isEnabled() || !C0516c.m2140a(this.f1571f)) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 7:
            case 9:
                int a = mo3076a(motionEvent.getX(), motionEvent.getY());
                mo3082d(a);
                if (a == Integer.MIN_VALUE) {
                    z = false;
                }
                return z;
            case 10:
                if (this.f1574i == Integer.MIN_VALUE) {
                    return false;
                }
                mo3082d(Integer.MIN_VALUE);
                return true;
            default:
                return false;
        }
    }

    /* renamed from: b */
    public final void m3464b() {
        m3465b(-1, 1);
    }

    /* renamed from: b */
    public final void m3465b(int i, int i2) {
        if (i != Integer.MIN_VALUE && this.f1571f.isEnabled()) {
            ViewParent parent = this.f1572g.getParent();
            if (parent != null) {
                AccessibilityEvent c = m3443c(i, 2048);
                C0510a.m2133a(c, i2);
                au.m2966a(parent, this.f1572g, c);
            }
        }
    }

    /* renamed from: b */
    public final boolean m3466b(int i) {
        if ((!this.f1572g.isFocused() && !this.f1572g.requestFocus()) || this.f1575j == i) {
            return false;
        }
        if (this.f1575j != Integer.MIN_VALUE) {
            m3469c(this.f1575j);
        }
        this.f1575j = i;
        m3457a(i, true);
        m3461a(i, 8);
        return true;
    }

    /* renamed from: b */
    protected abstract boolean mo3080b(int i, int i2, Bundle bundle);

    @Deprecated
    /* renamed from: c */
    public int m3468c() {
        return m3452a();
    }

    /* renamed from: c */
    public final boolean m3469c(int i) {
        if (this.f1575j != i) {
            return false;
        }
        this.f1575j = Integer.MIN_VALUE;
        m3457a(i, false);
        m3461a(i, 8);
        return true;
    }

    public C0546l getAccessibilityNodeProvider(View view) {
        if (this.f1573h == null) {
            this.f1573h = new C0707a(this);
        }
        return this.f1573h;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        m3459a(accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
        super.onInitializeAccessibilityNodeInfo(view, c0531e);
        m3458a(c0531e);
    }
}
