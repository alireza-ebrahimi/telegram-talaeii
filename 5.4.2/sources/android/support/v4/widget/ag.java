package android.support.v4.widget;

import android.content.Context;
import android.support.v4.view.C0659t;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;

public class ag {
    /* renamed from: v */
    private static final Interpolator f1518v = new C06791();
    /* renamed from: a */
    private int f1519a;
    /* renamed from: b */
    private int f1520b;
    /* renamed from: c */
    private int f1521c = -1;
    /* renamed from: d */
    private float[] f1522d;
    /* renamed from: e */
    private float[] f1523e;
    /* renamed from: f */
    private float[] f1524f;
    /* renamed from: g */
    private float[] f1525g;
    /* renamed from: h */
    private int[] f1526h;
    /* renamed from: i */
    private int[] f1527i;
    /* renamed from: j */
    private int[] f1528j;
    /* renamed from: k */
    private int f1529k;
    /* renamed from: l */
    private VelocityTracker f1530l;
    /* renamed from: m */
    private float f1531m;
    /* renamed from: n */
    private float f1532n;
    /* renamed from: o */
    private int f1533o;
    /* renamed from: p */
    private int f1534p;
    /* renamed from: q */
    private C0729x f1535q;
    /* renamed from: r */
    private final C0094a f1536r;
    /* renamed from: s */
    private View f1537s;
    /* renamed from: t */
    private boolean f1538t;
    /* renamed from: u */
    private final ViewGroup f1539u;
    /* renamed from: w */
    private final Runnable f1540w = new C06802(this);

    /* renamed from: android.support.v4.widget.ag$a */
    public static abstract class C0094a {
        /* renamed from: a */
        public int mo88a(View view) {
            return 0;
        }

        /* renamed from: a */
        public int mo89a(View view, int i, int i2) {
            return 0;
        }

        /* renamed from: a */
        public void mo90a(int i) {
        }

        /* renamed from: a */
        public void m445a(int i, int i2) {
        }

        /* renamed from: a */
        public void mo91a(View view, float f, float f2) {
        }

        /* renamed from: a */
        public void mo92a(View view, int i, int i2, int i3, int i4) {
        }

        /* renamed from: a */
        public abstract boolean mo93a(View view, int i);

        /* renamed from: b */
        public int mo117b(View view) {
            return 0;
        }

        /* renamed from: b */
        public int mo94b(View view, int i, int i2) {
            return 0;
        }

        /* renamed from: b */
        public void m451b(int i, int i2) {
        }

        /* renamed from: b */
        public void mo118b(View view, int i) {
        }

        /* renamed from: b */
        public boolean m453b(int i) {
            return false;
        }

        /* renamed from: c */
        public int m454c(int i) {
            return i;
        }
    }

    /* renamed from: android.support.v4.widget.ag$1 */
    static class C06791 implements Interpolator {
        C06791() {
        }

        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
        }
    }

    /* renamed from: android.support.v4.widget.ag$2 */
    class C06802 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ag f1517a;

        C06802(ag agVar) {
            this.f1517a = agVar;
        }

        public void run() {
            this.f1517a.m3350b(0);
        }
    }

    private ag(Context context, ViewGroup viewGroup, C0094a c0094a) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (c0094a == null) {
            throw new IllegalArgumentException("Callback may not be null");
        } else {
            this.f1539u = viewGroup;
            this.f1536r = c0094a;
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.f1533o = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
            this.f1520b = viewConfiguration.getScaledTouchSlop();
            this.f1531m = (float) viewConfiguration.getScaledMaximumFlingVelocity();
            this.f1532n = (float) viewConfiguration.getScaledMinimumFlingVelocity();
            this.f1535q = C0729x.m3542a(context, f1518v);
        }
    }

    /* renamed from: a */
    private float m3321a(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    /* renamed from: a */
    private float m3322a(float f, float f2, float f3) {
        float abs = Math.abs(f);
        return abs < f2 ? BitmapDescriptorFactory.HUE_RED : abs > f3 ? f <= BitmapDescriptorFactory.HUE_RED ? -f3 : f3 : f;
    }

    /* renamed from: a */
    private int m3323a(int i, int i2, int i3) {
        if (i == 0) {
            return 0;
        }
        int width = this.f1539u.getWidth();
        int i4 = width / 2;
        float a = (m3321a(Math.min(1.0f, ((float) Math.abs(i)) / ((float) width))) * ((float) i4)) + ((float) i4);
        i4 = Math.abs(i2);
        return Math.min(i4 > 0 ? Math.round(Math.abs(a / ((float) i4)) * 1000.0f) * 4 : (int) (((((float) Math.abs(i)) / ((float) i3)) + 1.0f) * 256.0f), 600);
    }

    /* renamed from: a */
    private int m3324a(View view, int i, int i2, int i3, int i4) {
        int b = m3332b(i3, (int) this.f1532n, (int) this.f1531m);
        int b2 = m3332b(i4, (int) this.f1532n, (int) this.f1531m);
        int abs = Math.abs(i);
        int abs2 = Math.abs(i2);
        int abs3 = Math.abs(b);
        int abs4 = Math.abs(b2);
        int i5 = abs3 + abs4;
        int i6 = abs + abs2;
        return (int) (((b2 != 0 ? ((float) abs4) / ((float) i5) : ((float) abs2) / ((float) i6)) * ((float) m3323a(i2, b2, this.f1536r.mo88a(view)))) + ((b != 0 ? ((float) abs3) / ((float) i5) : ((float) abs) / ((float) i6)) * ((float) m3323a(i, b, this.f1536r.mo117b(view)))));
    }

    /* renamed from: a */
    public static ag m3325a(ViewGroup viewGroup, float f, C0094a c0094a) {
        ag a = m3326a(viewGroup, c0094a);
        a.f1520b = (int) (((float) a.f1520b) * (1.0f / f));
        return a;
    }

    /* renamed from: a */
    public static ag m3326a(ViewGroup viewGroup, C0094a c0094a) {
        return new ag(viewGroup.getContext(), viewGroup, c0094a);
    }

    /* renamed from: a */
    private void m3327a(float f, float f2) {
        this.f1538t = true;
        this.f1536r.mo91a(this.f1537s, f, f2);
        this.f1538t = false;
        if (this.f1519a == 1) {
            m3350b(0);
        }
    }

    /* renamed from: a */
    private void m3328a(float f, float f2, int i) {
        m3340d(i);
        float[] fArr = this.f1522d;
        this.f1524f[i] = f;
        fArr[i] = f;
        fArr = this.f1523e;
        this.f1525g[i] = f2;
        fArr[i] = f2;
        this.f1526h[i] = m3338d((int) f, (int) f2);
        this.f1529k |= 1 << i;
    }

    /* renamed from: a */
    private boolean m3329a(float f, float f2, int i, int i2) {
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        if ((this.f1526h[i] & i2) != i2 || (this.f1534p & i2) == 0 || (this.f1528j[i] & i2) == i2 || (this.f1527i[i] & i2) == i2) {
            return false;
        }
        if (abs <= ((float) this.f1520b) && abs2 <= ((float) this.f1520b)) {
            return false;
        }
        if (abs >= abs2 * 0.5f || !this.f1536r.m453b(i2)) {
            return (this.f1527i[i] & i2) == 0 && abs > ((float) this.f1520b);
        } else {
            int[] iArr = this.f1528j;
            iArr[i] = iArr[i] | i2;
            return false;
        }
    }

    /* renamed from: a */
    private boolean m3330a(int i, int i2, int i3, int i4) {
        int left = this.f1537s.getLeft();
        int top = this.f1537s.getTop();
        int i5 = i - left;
        int i6 = i2 - top;
        if (i5 == 0 && i6 == 0) {
            this.f1535q.m3555h();
            m3350b(0);
            return false;
        }
        this.f1535q.m3544a(left, top, i5, i6, m3324a(this.f1537s, i5, i6, i3, i4));
        m3350b(2);
        return true;
    }

    /* renamed from: a */
    private boolean m3331a(View view, float f, float f2) {
        if (view == null) {
            return false;
        }
        boolean z = this.f1536r.mo117b(view) > 0;
        boolean z2 = this.f1536r.mo88a(view) > 0;
        return (z && z2) ? (f * f) + (f2 * f2) > ((float) (this.f1520b * this.f1520b)) : z ? Math.abs(f) > ((float) this.f1520b) : z2 ? Math.abs(f2) > ((float) this.f1520b) : false;
    }

    /* renamed from: b */
    private int m3332b(int i, int i2, int i3) {
        int abs = Math.abs(i);
        return abs < i2 ? 0 : abs > i3 ? i <= 0 ? -i3 : i3 : i;
    }

    /* renamed from: b */
    private void m3333b(float f, float f2, int i) {
        int i2 = 1;
        if (!m3329a(f, f2, i, 1)) {
            i2 = 0;
        }
        if (m3329a(f2, f, i, 4)) {
            i2 |= 4;
        }
        if (m3329a(f, f2, i, 2)) {
            i2 |= 2;
        }
        if (m3329a(f2, f, i, 8)) {
            i2 |= 8;
        }
        if (i2 != 0) {
            int[] iArr = this.f1527i;
            iArr[i] = iArr[i] | i2;
            this.f1536r.m451b(i2, i);
        }
    }

    /* renamed from: b */
    private void m3334b(int i, int i2, int i3, int i4) {
        int b;
        int a;
        int left = this.f1537s.getLeft();
        int top = this.f1537s.getTop();
        if (i3 != 0) {
            b = this.f1536r.mo94b(this.f1537s, i, i3);
            ah.m2811f(this.f1537s, b - left);
        } else {
            b = i;
        }
        if (i4 != 0) {
            a = this.f1536r.mo89a(this.f1537s, i2, i4);
            ah.m2808e(this.f1537s, a - top);
        } else {
            a = i2;
        }
        if (i3 != 0 || i4 != 0) {
            this.f1536r.mo92a(this.f1537s, b, a, b - left, a - top);
        }
    }

    /* renamed from: c */
    private void m3335c() {
        if (this.f1522d != null) {
            Arrays.fill(this.f1522d, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.f1523e, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.f1524f, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.f1525g, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.f1526h, 0);
            Arrays.fill(this.f1527i, 0);
            Arrays.fill(this.f1528j, 0);
            this.f1529k = 0;
        }
    }

    /* renamed from: c */
    private void m3336c(int i) {
        if (this.f1522d != null && m3344a(i)) {
            this.f1522d[i] = BitmapDescriptorFactory.HUE_RED;
            this.f1523e[i] = BitmapDescriptorFactory.HUE_RED;
            this.f1524f[i] = BitmapDescriptorFactory.HUE_RED;
            this.f1525g[i] = BitmapDescriptorFactory.HUE_RED;
            this.f1526h[i] = 0;
            this.f1527i[i] = 0;
            this.f1528j[i] = 0;
            this.f1529k &= (1 << i) ^ -1;
        }
    }

    /* renamed from: c */
    private void m3337c(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            if (m3341e(pointerId)) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                this.f1524f[pointerId] = x;
                this.f1525g[pointerId] = y;
            }
        }
    }

    /* renamed from: d */
    private int m3338d(int i, int i2) {
        int i3 = 0;
        if (i < this.f1539u.getLeft() + this.f1533o) {
            i3 = 1;
        }
        if (i2 < this.f1539u.getTop() + this.f1533o) {
            i3 |= 4;
        }
        if (i > this.f1539u.getRight() - this.f1533o) {
            i3 |= 2;
        }
        return i2 > this.f1539u.getBottom() - this.f1533o ? i3 | 8 : i3;
    }

    /* renamed from: d */
    private void m3339d() {
        this.f1530l.computeCurrentVelocity(1000, this.f1531m);
        m3327a(m3322a(af.m2516a(this.f1530l, this.f1521c), this.f1532n, this.f1531m), m3322a(af.m2517b(this.f1530l, this.f1521c), this.f1532n, this.f1531m));
    }

    /* renamed from: d */
    private void m3340d(int i) {
        if (this.f1522d == null || this.f1522d.length <= i) {
            Object obj = new float[(i + 1)];
            Object obj2 = new float[(i + 1)];
            Object obj3 = new float[(i + 1)];
            Object obj4 = new float[(i + 1)];
            Object obj5 = new int[(i + 1)];
            Object obj6 = new int[(i + 1)];
            Object obj7 = new int[(i + 1)];
            if (this.f1522d != null) {
                System.arraycopy(this.f1522d, 0, obj, 0, this.f1522d.length);
                System.arraycopy(this.f1523e, 0, obj2, 0, this.f1523e.length);
                System.arraycopy(this.f1524f, 0, obj3, 0, this.f1524f.length);
                System.arraycopy(this.f1525g, 0, obj4, 0, this.f1525g.length);
                System.arraycopy(this.f1526h, 0, obj5, 0, this.f1526h.length);
                System.arraycopy(this.f1527i, 0, obj6, 0, this.f1527i.length);
                System.arraycopy(this.f1528j, 0, obj7, 0, this.f1528j.length);
            }
            this.f1522d = obj;
            this.f1523e = obj2;
            this.f1524f = obj3;
            this.f1525g = obj4;
            this.f1526h = obj5;
            this.f1527i = obj6;
            this.f1528j = obj7;
        }
    }

    /* renamed from: e */
    private boolean m3341e(int i) {
        if (m3344a(i)) {
            return true;
        }
        Log.e("ViewDragHelper", "Ignoring pointerId=" + i + " because ACTION_DOWN was not received " + "for this pointer before ACTION_MOVE. It likely happened because " + " ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }

    /* renamed from: a */
    public int m3342a() {
        return this.f1520b;
    }

    /* renamed from: a */
    public void m3343a(View view, int i) {
        if (view.getParent() != this.f1539u) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.f1539u + ")");
        }
        this.f1537s = view;
        this.f1521c = i;
        this.f1536r.mo118b(view, i);
        m3350b(1);
    }

    /* renamed from: a */
    public boolean m3344a(int i) {
        return (this.f1529k & (1 << i)) != 0;
    }

    /* renamed from: a */
    public boolean m3345a(int i, int i2) {
        if (this.f1538t) {
            return m3330a(i, i2, (int) af.m2516a(this.f1530l, this.f1521c), (int) af.m2517b(this.f1530l, this.f1521c));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public boolean m3346a(android.view.MotionEvent r14) {
        /*
        r13 = this;
        r0 = android.support.v4.view.C0659t.m3205a(r14);
        r1 = android.support.v4.view.C0659t.m3206b(r14);
        if (r0 != 0) goto L_0x000d;
    L_0x000a:
        r13.m3349b();
    L_0x000d:
        r2 = r13.f1530l;
        if (r2 != 0) goto L_0x0017;
    L_0x0011:
        r2 = android.view.VelocityTracker.obtain();
        r13.f1530l = r2;
    L_0x0017:
        r2 = r13.f1530l;
        r2.addMovement(r14);
        switch(r0) {
            case 0: goto L_0x0026;
            case 1: goto L_0x0128;
            case 2: goto L_0x0092;
            case 3: goto L_0x0128;
            case 4: goto L_0x001f;
            case 5: goto L_0x005a;
            case 6: goto L_0x011f;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = r13.f1519a;
        r1 = 1;
        if (r0 != r1) goto L_0x012d;
    L_0x0024:
        r0 = 1;
    L_0x0025:
        return r0;
    L_0x0026:
        r0 = r14.getX();
        r1 = r14.getY();
        r2 = 0;
        r2 = r14.getPointerId(r2);
        r13.m3328a(r0, r1, r2);
        r0 = (int) r0;
        r1 = (int) r1;
        r0 = r13.m3355c(r0, r1);
        r1 = r13.f1537s;
        if (r0 != r1) goto L_0x0048;
    L_0x0040:
        r1 = r13.f1519a;
        r3 = 2;
        if (r1 != r3) goto L_0x0048;
    L_0x0045:
        r13.m3353b(r0, r2);
    L_0x0048:
        r0 = r13.f1526h;
        r0 = r0[r2];
        r1 = r13.f1534p;
        r1 = r1 & r0;
        if (r1 == 0) goto L_0x001f;
    L_0x0051:
        r1 = r13.f1536r;
        r3 = r13.f1534p;
        r0 = r0 & r3;
        r1.m445a(r0, r2);
        goto L_0x001f;
    L_0x005a:
        r0 = r14.getPointerId(r1);
        r2 = r14.getX(r1);
        r1 = r14.getY(r1);
        r13.m3328a(r2, r1, r0);
        r3 = r13.f1519a;
        if (r3 != 0) goto L_0x007f;
    L_0x006d:
        r1 = r13.f1526h;
        r1 = r1[r0];
        r2 = r13.f1534p;
        r2 = r2 & r1;
        if (r2 == 0) goto L_0x001f;
    L_0x0076:
        r2 = r13.f1536r;
        r3 = r13.f1534p;
        r1 = r1 & r3;
        r2.m445a(r1, r0);
        goto L_0x001f;
    L_0x007f:
        r3 = r13.f1519a;
        r4 = 2;
        if (r3 != r4) goto L_0x001f;
    L_0x0084:
        r2 = (int) r2;
        r1 = (int) r1;
        r1 = r13.m3355c(r2, r1);
        r2 = r13.f1537s;
        if (r1 != r2) goto L_0x001f;
    L_0x008e:
        r13.m3353b(r1, r0);
        goto L_0x001f;
    L_0x0092:
        r0 = r13.f1522d;
        if (r0 == 0) goto L_0x001f;
    L_0x0096:
        r0 = r13.f1523e;
        if (r0 == 0) goto L_0x001f;
    L_0x009a:
        r2 = r14.getPointerCount();
        r0 = 0;
        r1 = r0;
    L_0x00a0:
        if (r1 >= r2) goto L_0x0107;
    L_0x00a2:
        r3 = r14.getPointerId(r1);
        r0 = r13.m3341e(r3);
        if (r0 != 0) goto L_0x00b0;
    L_0x00ac:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x00a0;
    L_0x00b0:
        r0 = r14.getX(r1);
        r4 = r14.getY(r1);
        r5 = r13.f1522d;
        r5 = r5[r3];
        r5 = r0 - r5;
        r6 = r13.f1523e;
        r6 = r6[r3];
        r6 = r4 - r6;
        r0 = (int) r0;
        r4 = (int) r4;
        r4 = r13.m3355c(r0, r4);
        if (r4 == 0) goto L_0x010c;
    L_0x00cc:
        r0 = r13.m3331a(r4, r5, r6);
        if (r0 == 0) goto L_0x010c;
    L_0x00d2:
        r0 = 1;
    L_0x00d3:
        if (r0 == 0) goto L_0x010e;
    L_0x00d5:
        r7 = r4.getLeft();
        r8 = (int) r5;
        r8 = r8 + r7;
        r9 = r13.f1536r;
        r10 = (int) r5;
        r8 = r9.mo94b(r4, r8, r10);
        r9 = r4.getTop();
        r10 = (int) r6;
        r10 = r10 + r9;
        r11 = r13.f1536r;
        r12 = (int) r6;
        r10 = r11.mo89a(r4, r10, r12);
        r11 = r13.f1536r;
        r11 = r11.mo117b(r4);
        r12 = r13.f1536r;
        r12 = r12.mo88a(r4);
        if (r11 == 0) goto L_0x0101;
    L_0x00fd:
        if (r11 <= 0) goto L_0x010e;
    L_0x00ff:
        if (r8 != r7) goto L_0x010e;
    L_0x0101:
        if (r12 == 0) goto L_0x0107;
    L_0x0103:
        if (r12 <= 0) goto L_0x010e;
    L_0x0105:
        if (r10 != r9) goto L_0x010e;
    L_0x0107:
        r13.m3337c(r14);
        goto L_0x001f;
    L_0x010c:
        r0 = 0;
        goto L_0x00d3;
    L_0x010e:
        r13.m3333b(r5, r6, r3);
        r5 = r13.f1519a;
        r6 = 1;
        if (r5 == r6) goto L_0x0107;
    L_0x0116:
        if (r0 == 0) goto L_0x00ac;
    L_0x0118:
        r0 = r13.m3353b(r4, r3);
        if (r0 == 0) goto L_0x00ac;
    L_0x011e:
        goto L_0x0107;
    L_0x011f:
        r0 = r14.getPointerId(r1);
        r13.m3336c(r0);
        goto L_0x001f;
    L_0x0128:
        r13.m3349b();
        goto L_0x001f;
    L_0x012d:
        r0 = 0;
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ag.a(android.view.MotionEvent):boolean");
    }

    /* renamed from: a */
    public boolean m3347a(View view, int i, int i2) {
        this.f1537s = view;
        this.f1521c = -1;
        boolean a = m3330a(i, i2, 0, 0);
        if (!(a || this.f1519a != 0 || this.f1537s == null)) {
            this.f1537s = null;
        }
        return a;
    }

    /* renamed from: a */
    public boolean m3348a(boolean z) {
        if (this.f1519a == 2) {
            int i;
            boolean g = this.f1535q.m3554g();
            int b = this.f1535q.m3549b();
            int c = this.f1535q.m3550c();
            int left = b - this.f1537s.getLeft();
            int top = c - this.f1537s.getTop();
            if (left != 0) {
                ah.m2811f(this.f1537s, left);
            }
            if (top != 0) {
                ah.m2808e(this.f1537s, top);
            }
            if (!(left == 0 && top == 0)) {
                this.f1536r.mo92a(this.f1537s, b, c, left, top);
            }
            if (g && b == this.f1535q.m3551d() && c == this.f1535q.m3552e()) {
                this.f1535q.m3555h();
                i = 0;
            } else {
                boolean z2 = g;
            }
            if (i == 0) {
                if (z) {
                    this.f1539u.post(this.f1540w);
                } else {
                    m3350b(0);
                }
            }
        }
        return this.f1519a == 2;
    }

    /* renamed from: b */
    public void m3349b() {
        this.f1521c = -1;
        m3335c();
        if (this.f1530l != null) {
            this.f1530l.recycle();
            this.f1530l = null;
        }
    }

    /* renamed from: b */
    void m3350b(int i) {
        this.f1539u.removeCallbacks(this.f1540w);
        if (this.f1519a != i) {
            this.f1519a = i;
            this.f1536r.mo90a(i);
            if (this.f1519a == 0) {
                this.f1537s = null;
            }
        }
    }

    /* renamed from: b */
    public void m3351b(MotionEvent motionEvent) {
        int i = 0;
        int a = C0659t.m3205a(motionEvent);
        int b = C0659t.m3206b(motionEvent);
        if (a == 0) {
            m3349b();
        }
        if (this.f1530l == null) {
            this.f1530l = VelocityTracker.obtain();
        }
        this.f1530l.addMovement(motionEvent);
        float x;
        float y;
        View c;
        int i2;
        switch (a) {
            case 0:
                x = motionEvent.getX();
                y = motionEvent.getY();
                i = motionEvent.getPointerId(0);
                c = m3355c((int) x, (int) y);
                m3328a(x, y, i);
                m3353b(c, i);
                i2 = this.f1526h[i];
                if ((this.f1534p & i2) != 0) {
                    this.f1536r.m445a(i2 & this.f1534p, i);
                    return;
                }
                return;
            case 1:
                if (this.f1519a == 1) {
                    m3339d();
                }
                m3349b();
                return;
            case 2:
                if (this.f1519a != 1) {
                    i2 = motionEvent.getPointerCount();
                    while (i < i2) {
                        a = motionEvent.getPointerId(i);
                        if (m3341e(a)) {
                            float x2 = motionEvent.getX(i);
                            float y2 = motionEvent.getY(i);
                            float f = x2 - this.f1522d[a];
                            float f2 = y2 - this.f1523e[a];
                            m3333b(f, f2, a);
                            if (this.f1519a != 1) {
                                c = m3355c((int) x2, (int) y2);
                                if (m3331a(c, f, f2) && m3353b(c, a)) {
                                }
                            }
                            m3337c(motionEvent);
                            return;
                        }
                        i++;
                    }
                    m3337c(motionEvent);
                    return;
                } else if (m3341e(this.f1521c)) {
                    i = motionEvent.findPointerIndex(this.f1521c);
                    x = motionEvent.getX(i);
                    i2 = (int) (x - this.f1524f[this.f1521c]);
                    i = (int) (motionEvent.getY(i) - this.f1525g[this.f1521c]);
                    m3334b(this.f1537s.getLeft() + i2, this.f1537s.getTop() + i, i2, i);
                    m3337c(motionEvent);
                    return;
                } else {
                    return;
                }
            case 3:
                if (this.f1519a == 1) {
                    m3327a((float) BitmapDescriptorFactory.HUE_RED, (float) BitmapDescriptorFactory.HUE_RED);
                }
                m3349b();
                return;
            case 5:
                i = motionEvent.getPointerId(b);
                x = motionEvent.getX(b);
                y = motionEvent.getY(b);
                m3328a(x, y, i);
                if (this.f1519a == 0) {
                    m3353b(m3355c((int) x, (int) y), i);
                    i2 = this.f1526h[i];
                    if ((this.f1534p & i2) != 0) {
                        this.f1536r.m445a(i2 & this.f1534p, i);
                        return;
                    }
                    return;
                } else if (m3352b((int) x, (int) y)) {
                    m3353b(this.f1537s, i);
                    return;
                } else {
                    return;
                }
            case 6:
                a = motionEvent.getPointerId(b);
                if (this.f1519a == 1 && a == this.f1521c) {
                    b = motionEvent.getPointerCount();
                    while (i < b) {
                        int pointerId = motionEvent.getPointerId(i);
                        if (pointerId != this.f1521c) {
                            if (m3355c((int) motionEvent.getX(i), (int) motionEvent.getY(i)) == this.f1537s && m3353b(this.f1537s, pointerId)) {
                                i = this.f1521c;
                                if (i == -1) {
                                    m3339d();
                                }
                            }
                        }
                        i++;
                    }
                    i = -1;
                    if (i == -1) {
                        m3339d();
                    }
                }
                m3336c(a);
                return;
            default:
                return;
        }
    }

    /* renamed from: b */
    public boolean m3352b(int i, int i2) {
        return m3354b(this.f1537s, i, i2);
    }

    /* renamed from: b */
    boolean m3353b(View view, int i) {
        if (view == this.f1537s && this.f1521c == i) {
            return true;
        }
        if (view == null || !this.f1536r.mo93a(view, i)) {
            return false;
        }
        this.f1521c = i;
        m3343a(view, i);
        return true;
    }

    /* renamed from: b */
    public boolean m3354b(View view, int i, int i2) {
        return view != null && i >= view.getLeft() && i < view.getRight() && i2 >= view.getTop() && i2 < view.getBottom();
    }

    /* renamed from: c */
    public View m3355c(int i, int i2) {
        for (int childCount = this.f1539u.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.f1539u.getChildAt(this.f1536r.m454c(childCount));
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }
}
