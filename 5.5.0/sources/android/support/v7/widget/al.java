package android.support.v7.widget;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.v4.view.C0659t;
import android.support.v4.view.ah;
import android.support.v7.view.menu.C0867s;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public abstract class al implements OnTouchListener {
    /* renamed from: a */
    private final float f2053a;
    /* renamed from: b */
    private final int f2054b;
    /* renamed from: c */
    final View f2055c;
    /* renamed from: d */
    private final int f2056d;
    /* renamed from: e */
    private Runnable f2057e;
    /* renamed from: f */
    private Runnable f2058f;
    /* renamed from: g */
    private boolean f2059g;
    /* renamed from: h */
    private int f2060h;
    /* renamed from: i */
    private final int[] f2061i = new int[2];

    /* renamed from: android.support.v7.widget.al$1 */
    class C10101 implements OnAttachStateChangeListener {
        /* renamed from: a */
        final /* synthetic */ al f2853a;

        C10101(al alVar) {
            this.f2853a = alVar;
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            this.f2853a.m4077e();
        }
    }

    /* renamed from: android.support.v7.widget.al$2 */
    class C10112 implements OnGlobalLayoutListener {
        /* renamed from: a */
        boolean f2854a = ah.m2769I(this.f2855b.f2055c);
        /* renamed from: b */
        final /* synthetic */ al f2855b;

        C10112(al alVar) {
            this.f2855b = alVar;
        }

        public void onGlobalLayout() {
            boolean z = this.f2854a;
            this.f2854a = ah.m2769I(this.f2855b.f2055c);
            if (z && !this.f2854a) {
                this.f2855b.m4077e();
            }
        }
    }

    /* renamed from: android.support.v7.widget.al$a */
    private class C1012a implements Runnable {
        /* renamed from: a */
        final /* synthetic */ al f2856a;

        C1012a(al alVar) {
            this.f2856a = alVar;
        }

        public void run() {
            ViewParent parent = this.f2856a.f2055c.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    /* renamed from: android.support.v7.widget.al$b */
    private class C1013b implements Runnable {
        /* renamed from: a */
        final /* synthetic */ al f2857a;

        C1013b(al alVar) {
            this.f2857a = alVar;
        }

        public void run() {
            this.f2857a.m4082d();
        }
    }

    public al(View view) {
        this.f2055c = view;
        view.setLongClickable(true);
        if (VERSION.SDK_INT >= 12) {
            m4070a(view);
        } else {
            m4074b(view);
        }
        this.f2053a = (float) ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.f2054b = ViewConfiguration.getTapTimeout();
        this.f2056d = (this.f2054b + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    @TargetApi(12)
    /* renamed from: a */
    private void m4070a(View view) {
        view.addOnAttachStateChangeListener(new C10101(this));
    }

    /* renamed from: a */
    private boolean m4071a(MotionEvent motionEvent) {
        View view = this.f2055c;
        if (!view.isEnabled()) {
            return false;
        }
        switch (C0659t.m3205a(motionEvent)) {
            case 0:
                this.f2060h = motionEvent.getPointerId(0);
                if (this.f2057e == null) {
                    this.f2057e = new C1012a(this);
                }
                view.postDelayed(this.f2057e, (long) this.f2054b);
                if (this.f2058f == null) {
                    this.f2058f = new C1013b(this);
                }
                view.postDelayed(this.f2058f, (long) this.f2056d);
                return false;
            case 1:
            case 3:
                m4078f();
                return false;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.f2060h);
                if (findPointerIndex < 0 || m4072a(view, motionEvent.getX(findPointerIndex), motionEvent.getY(findPointerIndex), this.f2053a)) {
                    return false;
                }
                m4078f();
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            default:
                return false;
        }
    }

    /* renamed from: a */
    private static boolean m4072a(View view, float f, float f2, float f3) {
        return f >= (-f3) && f2 >= (-f3) && f < ((float) (view.getRight() - view.getLeft())) + f3 && f2 < ((float) (view.getBottom() - view.getTop())) + f3;
    }

    /* renamed from: a */
    private boolean m4073a(View view, MotionEvent motionEvent) {
        int[] iArr = this.f2061i;
        view.getLocationOnScreen(iArr);
        motionEvent.offsetLocation((float) (-iArr[0]), (float) (-iArr[1]));
        return true;
    }

    /* renamed from: b */
    private void m4074b(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new C10112(this));
    }

    /* renamed from: b */
    private boolean m4075b(MotionEvent motionEvent) {
        View view = this.f2055c;
        C0867s a = mo703a();
        if (a == null || !a.mo739d()) {
            return false;
        }
        aj ajVar = (aj) a.mo740e();
        if (ajVar == null || !ajVar.isShown()) {
            return false;
        }
        MotionEvent obtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
        m4076b(view, obtainNoHistory);
        m4073a(ajVar, obtainNoHistory);
        boolean a2 = ajVar.mo937a(obtainNoHistory, this.f2060h);
        obtainNoHistory.recycle();
        int a3 = C0659t.m3205a(motionEvent);
        boolean z = (a3 == 1 || a3 == 3) ? false : true;
        z = a2 && z;
        return z;
    }

    /* renamed from: b */
    private boolean m4076b(View view, MotionEvent motionEvent) {
        int[] iArr = this.f2061i;
        view.getLocationOnScreen(iArr);
        motionEvent.offsetLocation((float) iArr[0], (float) iArr[1]);
        return true;
    }

    /* renamed from: e */
    private void m4077e() {
        this.f2059g = false;
        this.f2060h = -1;
        if (this.f2057e != null) {
            this.f2055c.removeCallbacks(this.f2057e);
        }
    }

    /* renamed from: f */
    private void m4078f() {
        if (this.f2058f != null) {
            this.f2055c.removeCallbacks(this.f2058f);
        }
        if (this.f2057e != null) {
            this.f2055c.removeCallbacks(this.f2057e);
        }
    }

    /* renamed from: a */
    public abstract C0867s mo703a();

    /* renamed from: b */
    protected boolean mo704b() {
        C0867s a = mo703a();
        if (!(a == null || a.mo739d())) {
            a.mo729a();
        }
        return true;
    }

    /* renamed from: c */
    protected boolean mo997c() {
        C0867s a = mo703a();
        if (a != null && a.mo739d()) {
            a.mo736c();
        }
        return true;
    }

    /* renamed from: d */
    void m4082d() {
        m4078f();
        View view = this.f2055c;
        if (view.isEnabled() && !view.isLongClickable() && mo704b()) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
            view.onTouchEvent(obtain);
            obtain.recycle();
            this.f2059g = true;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        boolean z2 = this.f2059g;
        if (z2) {
            z = m4075b(motionEvent) || !mo997c();
        } else {
            boolean z3 = m4071a(motionEvent) && mo704b();
            if (z3) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                this.f2055c.onTouchEvent(obtain);
                obtain.recycle();
            }
            z = z3;
        }
        this.f2059g = z;
        return z || z2;
    }
}
