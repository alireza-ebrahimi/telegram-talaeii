package android.support.v4.p017a;

import android.annotation.TargetApi;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

@TargetApi(9)
/* renamed from: android.support.v4.a.e */
class C0218e implements C0213c {

    /* renamed from: android.support.v4.a.e$a */
    private static class C0217a implements C0216g {
        /* renamed from: a */
        List<C0212b> f703a = new ArrayList();
        /* renamed from: b */
        List<C0214d> f704b = new ArrayList();
        /* renamed from: c */
        View f705c;
        /* renamed from: d */
        private long f706d;
        /* renamed from: e */
        private long f707e = 200;
        /* renamed from: f */
        private float f708f = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: g */
        private boolean f709g = false;
        /* renamed from: h */
        private boolean f710h = false;
        /* renamed from: i */
        private Runnable f711i = new C02151(this);

        /* renamed from: android.support.v4.a.e$a$1 */
        class C02151 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C0217a f702a;

            C02151(C0217a c0217a) {
                this.f702a = c0217a;
            }

            public void run() {
                float a = (((float) (this.f702a.m1010e() - this.f702a.f706d)) * 1.0f) / ((float) this.f702a.f707e);
                if (a > 1.0f || this.f702a.f705c.getParent() == null) {
                    a = 1.0f;
                }
                this.f702a.f708f = a;
                this.f702a.m1007d();
                if (this.f702a.f708f >= 1.0f) {
                    this.f702a.m1014g();
                } else {
                    this.f702a.f705c.postDelayed(this.f702a.f711i, 16);
                }
            }
        }

        /* renamed from: d */
        private void m1007d() {
            for (int size = this.f704b.size() - 1; size >= 0; size--) {
                ((C0214d) this.f704b.get(size)).onAnimationUpdate(this);
            }
        }

        /* renamed from: e */
        private long m1010e() {
            return this.f705c.getDrawingTime();
        }

        /* renamed from: f */
        private void m1011f() {
            for (int size = this.f703a.size() - 1; size >= 0; size--) {
                ((C0212b) this.f703a.get(size)).onAnimationStart(this);
            }
        }

        /* renamed from: g */
        private void m1014g() {
            for (int size = this.f703a.size() - 1; size >= 0; size--) {
                ((C0212b) this.f703a.get(size)).onAnimationEnd(this);
            }
        }

        /* renamed from: h */
        private void m1015h() {
            for (int size = this.f703a.size() - 1; size >= 0; size--) {
                ((C0212b) this.f703a.get(size)).onAnimationCancel(this);
            }
        }

        /* renamed from: a */
        public void mo190a() {
            if (!this.f709g) {
                this.f709g = true;
                m1011f();
                this.f708f = BitmapDescriptorFactory.HUE_RED;
                this.f706d = m1010e();
                this.f705c.postDelayed(this.f711i, 16);
            }
        }

        /* renamed from: a */
        public void mo191a(long j) {
            if (!this.f709g) {
                this.f707e = j;
            }
        }

        /* renamed from: a */
        public void mo192a(C0212b c0212b) {
            this.f703a.add(c0212b);
        }

        /* renamed from: a */
        public void mo193a(C0214d c0214d) {
            this.f704b.add(c0214d);
        }

        /* renamed from: a */
        public void mo194a(View view) {
            this.f705c = view;
        }

        /* renamed from: b */
        public void mo195b() {
            if (!this.f710h) {
                this.f710h = true;
                if (this.f709g) {
                    m1015h();
                }
                m1014g();
            }
        }

        /* renamed from: c */
        public float mo196c() {
            return this.f708f;
        }
    }

    C0218e() {
    }

    /* renamed from: a */
    public C0216g mo197a() {
        return new C0217a();
    }

    /* renamed from: a */
    public void mo198a(View view) {
    }
}
