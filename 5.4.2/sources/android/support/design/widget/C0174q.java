package android.support.design.widget;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import java.lang.ref.WeakReference;

/* renamed from: android.support.design.widget.q */
class C0174q {
    /* renamed from: a */
    private static C0174q f590a;
    /* renamed from: b */
    private final Object f591b = new Object();
    /* renamed from: c */
    private final Handler f592c = new Handler(Looper.getMainLooper(), new C01711(this));
    /* renamed from: d */
    private C0173b f593d;
    /* renamed from: e */
    private C0173b f594e;

    /* renamed from: android.support.design.widget.q$1 */
    class C01711 implements Callback {
        /* renamed from: a */
        final /* synthetic */ C0174q f586a;

        C01711(C0174q c0174q) {
            this.f586a = c0174q;
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    this.f586a.m833a((C0173b) message.obj);
                    return true;
                default:
                    return false;
            }
        }
    }

    /* renamed from: android.support.design.widget.q$a */
    interface C0172a {
        /* renamed from: a */
        void m822a();

        /* renamed from: a */
        void m823a(int i);
    }

    /* renamed from: android.support.design.widget.q$b */
    private static class C0173b {
        /* renamed from: a */
        final WeakReference<C0172a> f587a;
        /* renamed from: b */
        int f588b;
        /* renamed from: c */
        boolean f589c;

        /* renamed from: a */
        boolean m824a(C0172a c0172a) {
            return c0172a != null && this.f587a.get() == c0172a;
        }
    }

    private C0174q() {
    }

    /* renamed from: a */
    static C0174q m825a() {
        if (f590a == null) {
            f590a = new C0174q();
        }
        return f590a;
    }

    /* renamed from: a */
    private boolean m826a(C0173b c0173b, int i) {
        C0172a c0172a = (C0172a) c0173b.f587a.get();
        if (c0172a == null) {
            return false;
        }
        this.f592c.removeCallbacksAndMessages(c0173b);
        c0172a.m823a(i);
        return true;
    }

    /* renamed from: b */
    private void m827b() {
        if (this.f594e != null) {
            this.f593d = this.f594e;
            this.f594e = null;
            C0172a c0172a = (C0172a) this.f593d.f587a.get();
            if (c0172a != null) {
                c0172a.m822a();
            } else {
                this.f593d = null;
            }
        }
    }

    /* renamed from: b */
    private void m828b(C0173b c0173b) {
        if (c0173b.f588b != -2) {
            int i = 2750;
            if (c0173b.f588b > 0) {
                i = c0173b.f588b;
            } else if (c0173b.f588b == -1) {
                i = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
            }
            this.f592c.removeCallbacksAndMessages(c0173b);
            this.f592c.sendMessageDelayed(Message.obtain(this.f592c, 0, c0173b), (long) i);
        }
    }

    /* renamed from: f */
    private boolean m829f(C0172a c0172a) {
        return this.f593d != null && this.f593d.m824a(c0172a);
    }

    /* renamed from: g */
    private boolean m830g(C0172a c0172a) {
        return this.f594e != null && this.f594e.m824a(c0172a);
    }

    /* renamed from: a */
    public void m831a(C0172a c0172a) {
        synchronized (this.f591b) {
            if (m829f(c0172a)) {
                this.f593d = null;
                if (this.f594e != null) {
                    m827b();
                }
            }
        }
    }

    /* renamed from: a */
    public void m832a(C0172a c0172a, int i) {
        synchronized (this.f591b) {
            if (m829f(c0172a)) {
                m826a(this.f593d, i);
            } else if (m830g(c0172a)) {
                m826a(this.f594e, i);
            }
        }
    }

    /* renamed from: a */
    void m833a(C0173b c0173b) {
        synchronized (this.f591b) {
            if (this.f593d == c0173b || this.f594e == c0173b) {
                m826a(c0173b, 2);
            }
        }
    }

    /* renamed from: b */
    public void m834b(C0172a c0172a) {
        synchronized (this.f591b) {
            if (m829f(c0172a)) {
                m828b(this.f593d);
            }
        }
    }

    /* renamed from: c */
    public void m835c(C0172a c0172a) {
        synchronized (this.f591b) {
            if (m829f(c0172a) && !this.f593d.f589c) {
                this.f593d.f589c = true;
                this.f592c.removeCallbacksAndMessages(this.f593d);
            }
        }
    }

    /* renamed from: d */
    public void m836d(C0172a c0172a) {
        synchronized (this.f591b) {
            if (m829f(c0172a) && this.f593d.f589c) {
                this.f593d.f589c = false;
                m828b(this.f593d);
            }
        }
    }

    /* renamed from: e */
    public boolean m837e(C0172a c0172a) {
        boolean z;
        synchronized (this.f591b) {
            z = m829f(c0172a) || m830g(c0172a);
        }
        return z;
    }
}
