package org.telegram.customization.util.view.p171b;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.customization.util.view.p171b.p172a.C2924a;
import org.telegram.customization.util.view.p171b.p172a.C2926b;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: org.telegram.customization.util.view.b.c */
public class C2945c {
    /* renamed from: A */
    private Timer f9710A = new Timer();
    /* renamed from: a */
    protected C2935a f9711a;
    /* renamed from: b */
    protected View f9712b;
    /* renamed from: c */
    protected ViewGroup f9713c;
    /* renamed from: d */
    protected ViewGroup f9714d;
    /* renamed from: e */
    protected C2946d f9715e;
    /* renamed from: f */
    protected ArrayList<C2926b> f9716f;
    /* renamed from: g */
    protected ArrayList<C2924a> f9717g;
    /* renamed from: h */
    protected C2924a f9718h;
    /* renamed from: i */
    protected C2937c f9719i;
    /* renamed from: j */
    protected C2938d f9720j;
    /* renamed from: k */
    protected C2940f f9721k;
    /* renamed from: l */
    protected C2939e f9722l;
    /* renamed from: m */
    protected C2936b f9723m;
    /* renamed from: n */
    protected GestureDetector f9724n;
    /* renamed from: o */
    protected int f9725o;
    /* renamed from: p */
    protected float[] f9726p;
    /* renamed from: q */
    protected int f9727q;
    /* renamed from: r */
    protected int f9728r;
    /* renamed from: s */
    protected int f9729s;
    /* renamed from: t */
    protected long f9730t;
    /* renamed from: u */
    private boolean f9731u;
    /* renamed from: v */
    private boolean f9732v;
    /* renamed from: w */
    private boolean f9733w;
    /* renamed from: x */
    private boolean f9734x;
    /* renamed from: y */
    private int f9735y = -1;
    /* renamed from: z */
    private boolean f9736z = true;

    /* renamed from: org.telegram.customization.util.view.b.c$1 */
    class C29291 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ C2945c f9673a;

        C29291(C2945c c2945c) {
            this.f9673a = c2945c;
        }

        public void onGlobalLayout() {
            this.f9673a.m13585f();
        }
    }

    /* renamed from: org.telegram.customization.util.view.b.c$2 */
    class C29302 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2945c f9674a;

        C29302(C2945c c2945c) {
            this.f9674a = c2945c;
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: org.telegram.customization.util.view.b.c$4 */
    class C29324 implements AnimatorListener {
        /* renamed from: a */
        final /* synthetic */ C2945c f9678a;

        C29324(C2945c c2945c) {
            this.f9678a = c2945c;
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
            this.f9678a.m13589h();
            animator.cancel();
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }
    }

    /* renamed from: org.telegram.customization.util.view.b.c$a */
    public static class C2935a {
        /* renamed from: a */
        protected final Activity f9685a;
        /* renamed from: b */
        protected int f9686b = -1;
        /* renamed from: c */
        protected View f9687c;
        /* renamed from: d */
        protected ViewGroup f9688d;
        /* renamed from: e */
        protected ArrayList<View> f9689e;
        /* renamed from: f */
        protected C2937c f9690f;
        /* renamed from: g */
        protected C2938d f9691g;
        /* renamed from: h */
        protected C2940f f9692h;
        /* renamed from: i */
        protected C2939e f9693i;
        /* renamed from: j */
        protected boolean f9694j = true;
        /* renamed from: k */
        protected boolean f9695k = true;
        /* renamed from: l */
        protected boolean f9696l = true;
        /* renamed from: m */
        protected boolean f9697m = true;

        public C2935a(Activity activity) {
            this.f9685a = activity;
            this.f9689e = new ArrayList();
        }

        /* renamed from: a */
        public C2935a m13551a(View view) {
            this.f9687c = view;
            return this;
        }

        /* renamed from: a */
        public C2935a m13552a(boolean z) {
            this.f9694j = z;
            return this;
        }

        /* renamed from: a */
        public C2935a m13553a(View... viewArr) {
            for (Object add : viewArr) {
                this.f9689e.add(add);
            }
            return this;
        }

        /* renamed from: a */
        public C2945c m13554a() {
            if (this.f9686b != -1 || this.f9687c != null) {
                return new C2945c(this);
            }
            throw new IllegalArgumentException("No peekLayoutId specified.");
        }
    }

    /* renamed from: org.telegram.customization.util.view.b.c$b */
    protected class C2936b extends SimpleOnGestureListener {
        /* renamed from: a */
        final /* synthetic */ C2945c f9698a;
        /* renamed from: b */
        private int f9699b;
        /* renamed from: c */
        private View f9700c;

        protected C2936b(C2945c c2945c) {
            this.f9698a = c2945c;
        }

        /* renamed from: a */
        private void m13555a(int i, float f, float f2) {
            this.f9698a.f9719i.m13559a(this.f9700c, this.f9699b, i);
            if (!this.f9698a.f9732v) {
                return;
            }
            if (i == 0) {
                this.f9698a.f9715e.m13604a((int) Callback.DEFAULT_SWIPE_ANIMATION_DURATION, this.f9698a.f9730t);
                this.f9698a.f9715e.m13602a(f, f2, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, this.f9698a.f9730t, -1000.0f);
                return;
            }
            this.f9698a.f9715e.m13602a(f, f2, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, this.f9698a.f9730t, 1000.0f);
        }

        /* renamed from: a */
        private boolean m13556a(float f, float f2) {
            if (this.f9698a.f9725o == 1) {
                if (f2 < -3000.0f && this.f9698a.f9733w) {
                    m13555a(0, f, f2);
                    return false;
                } else if (f2 > 3000.0f && this.f9698a.f9734x) {
                    m13555a(1, f, f2);
                    return false;
                }
            } else if (this.f9698a.f9725o == 2) {
                if (f < -3000.0f && this.f9698a.f9733w) {
                    m13555a(0, f, f2);
                    return false;
                } else if (f > 3000.0f && this.f9698a.f9734x) {
                    m13555a(1, f, f2);
                    return false;
                }
            }
            return true;
        }

        /* renamed from: a */
        public void m13557a(int i) {
            this.f9699b = i;
        }

        /* renamed from: a */
        public void m13558a(View view) {
            this.f9700c = view;
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return this.f9698a.f9719i != null ? m13556a(f, f2) : true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.b.c$c */
    public interface C2937c {
        /* renamed from: a */
        void m13559a(View view, int i, int i2);
    }

    /* renamed from: org.telegram.customization.util.view.b.c$d */
    public interface C2938d {
        /* renamed from: a */
        void m13560a(View view, int i);

        /* renamed from: b */
        void m13561b(View view, int i);
    }

    /* renamed from: org.telegram.customization.util.view.b.c$e */
    public interface C2939e {
        /* renamed from: a */
        void m13562a(View view, int i);

        /* renamed from: b */
        void m13563b(View view, int i);

        /* renamed from: c */
        void m13564c(View view, int i);
    }

    /* renamed from: org.telegram.customization.util.view.b.c$f */
    public interface C2940f {
        /* renamed from: a */
        void m13565a(View view, int i);

        /* renamed from: b */
        void m13566b(View view, int i);
    }

    /* renamed from: org.telegram.customization.util.view.b.c$g */
    protected class C2944g implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ C2945c f9706a;
        /* renamed from: b */
        private int f9707b;
        /* renamed from: c */
        private Runnable f9708c;
        /* renamed from: d */
        private boolean f9709d;

        public C2944g(C2945c c2945c, int i) {
            this.f9706a = c2945c;
            this.f9707b = i;
        }

        /* renamed from: a */
        private void m13569a(final View view) {
            this.f9706a.f9710A.cancel();
            if (this.f9708c != null) {
                this.f9708c = new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2944g f9702b;

                    public void run() {
                        this.f9702b.f9709d = false;
                        this.f9702b.f9706a.m13599d(view, this.f9702b.f9707b);
                        this.f9702b.f9708c = null;
                    }
                };
                this.f9706a.f9711a.f9685a.runOnUiThread(this.f9708c);
            }
        }

        /* renamed from: b */
        private void m13571b(final View view) {
            this.f9706a.f9710A = new Timer();
            this.f9706a.f9710A.schedule(new TimerTask(this) {
                /* renamed from: b */
                final /* synthetic */ C2944g f9705b;

                /* renamed from: org.telegram.customization.util.view.b.c$g$2$1 */
                class C29421 implements Runnable {
                    /* renamed from: a */
                    final /* synthetic */ C29432 f9703a;

                    C29421(C29432 c29432) {
                        this.f9703a = c29432;
                    }

                    public void run() {
                        if (this.f9703a.f9705b.f9709d) {
                            this.f9703a.f9705b.f9706a.m13598c(view, this.f9703a.f9705b.f9707b);
                            this.f9703a.f9705b.f9708c = null;
                        }
                    }
                }

                public void run() {
                    this.f9705b.f9709d = true;
                    this.f9705b.f9708c = new C29421(this);
                    this.f9705b.f9706a.f9711a.f9685a.runOnUiThread(this.f9705b.f9708c);
                }
            }, 200);
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!this.f9706a.f9736z) {
                return false;
            }
            if (motionEvent.getAction() == 0) {
                this.f9709d = false;
                m13569a(view);
                m13571b(view);
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                m13569a(view);
            }
            if (this.f9709d) {
                this.f9706a.m13593a(view, motionEvent, this.f9707b);
            }
            return this.f9709d;
        }
    }

    public C2945c(C2935a c2935a) {
        this.f9711a = c2935a;
        m13591a();
    }

    /* renamed from: a */
    private void m13575a(int i) {
        for (int i2 = 0; i2 < this.f9716f.size(); i2++) {
            C2926b c2926b = (C2926b) this.f9716f.get(i2);
            boolean a = C2928b.m13550a(c2926b.m13541a(), this.f9728r, this.f9729s);
            if (a && c2926b.m13544b() == null) {
                c2926b.m13543a(this, i, this.f9735y != -1 ? (long) this.f9735y : 850);
                this.f9721k.m13565a(c2926b.m13541a(), i);
            } else if (!(a || c2926b.m13544b() == null)) {
                c2926b.m13544b().cancel();
                c2926b.m13542a(null);
            }
        }
    }

    /* renamed from: a */
    private void m13576a(View view) {
        MotionEvent obtain = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        view.onTouchEvent(obtain);
        obtain.recycle();
    }

    /* renamed from: b */
    private void m13578b(int i) {
        for (int i2 = 0; i2 < this.f9717g.size(); i2++) {
            C2924a c2924a = (C2924a) this.f9717g.get(i2);
            boolean a = C2928b.m13550a(c2924a.m13537b(), this.f9728r, this.f9729s);
            if (a && c2924a.m13538c() == null) {
                c2924a.m13536a(this, i, 50);
            } else if (!(a || c2924a.m13538c() == null)) {
                c2924a.m13538c().cancel();
                c2924a.m13535a(null);
                if (c2924a == this.f9718h) {
                    m13601f(c2924a.m13537b(), c2924a.m13533a());
                    c2924a.m13534a(-1);
                    this.f9718h = null;
                }
            }
        }
    }

    /* renamed from: d */
    private void m13582d() {
        if (VERSION.SDK_INT >= 21) {
            this.f9714d.setElevation(10.0f);
            this.f9712b.setElevation(10.0f);
            return;
        }
        this.f9714d.bringToFront();
        this.f9712b.bringToFront();
        this.f9713c.requestLayout();
        this.f9713c.invalidate();
    }

    /* renamed from: e */
    private void m13583e() {
        this.f9712b.getViewTreeObserver().addOnGlobalLayoutListener(new C29291(this));
    }

    /* renamed from: f */
    private void m13585f() {
        this.f9726p = new float[2];
        this.f9726p[0] = (float) ((this.f9714d.getWidth() / 2) - (this.f9712b.getWidth() / 2));
        this.f9726p[1] = (float) (((this.f9714d.getHeight() / 2) - (this.f9712b.getHeight() / 2)) + this.f9727q);
    }

    /* renamed from: g */
    private void m13587g() {
        if (VERSION.SDK_INT >= 16) {
            this.f9714d.setBackground(null);
            this.f9714d.setBackground(new BitmapDrawable(this.f9711a.f9685a.getResources(), C2927a.m13547a(this.f9713c)));
            return;
        }
        this.f9714d.setBackgroundDrawable(null);
        this.f9714d.setBackgroundDrawable(new BitmapDrawable(this.f9711a.f9685a.getResources(), C2927a.m13547a(this.f9713c)));
    }

    /* renamed from: h */
    private void m13589h() {
        this.f9714d.setVisibility(8);
        this.f9728r = 0;
        this.f9729s = 0;
        for (int i = 0; i < this.f9716f.size(); i++) {
            Timer b = ((C2926b) this.f9716f.get(i)).m13544b();
            if (b != null) {
                b.cancel();
                ((C2926b) this.f9716f.get(i)).m13542a(null);
            }
        }
        if (this.f9726p != null) {
            this.f9712b.setX(this.f9726p[0]);
            this.f9712b.setY(this.f9726p[1]);
        }
        this.f9712b.setScaleX(0.85f);
        this.f9712b.setScaleY(0.85f);
    }

    /* renamed from: i */
    private void m13590i() {
        this.f9718h = null;
        Iterator it = this.f9717g.iterator();
        while (it.hasNext()) {
            C2924a c2924a = (C2924a) it.next();
            if (c2924a.m13538c() != null) {
                c2924a.m13538c().cancel();
            }
        }
        it = this.f9716f.iterator();
        while (it.hasNext()) {
            C2926b c2926b = (C2926b) it.next();
            if (c2926b.m13544b() != null) {
                c2926b.m13544b().cancel();
            }
        }
    }

    /* renamed from: a */
    protected void m13591a() {
        this.f9719i = this.f9711a.f9690f;
        this.f9720j = this.f9711a.f9691g;
        this.f9721k = this.f9711a.f9692h;
        this.f9722l = this.f9711a.f9693i;
        this.f9723m = new C2936b(this);
        this.f9724n = new GestureDetector(this.f9711a.f9685a, this.f9723m);
        m13597c();
        this.f9716f = new ArrayList();
        this.f9717g = new ArrayList();
        this.f9731u = this.f9711a.f9694j;
        this.f9732v = this.f9711a.f9695k;
        this.f9733w = this.f9711a.f9696l;
        this.f9734x = this.f9711a.f9697m;
        this.f9725o = this.f9711a.f9685a.getResources().getConfiguration().orientation;
        this.f9727q = C2928b.m13549a(this.f9711a.f9685a.getApplicationContext(), 12);
        m13595b();
    }

    /* renamed from: a */
    protected void m13592a(View view, int i) {
        view.setOnTouchListener(new C2944g(this, i));
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new C29302(this));
        }
    }

    /* renamed from: a */
    protected void m13593a(View view, MotionEvent motionEvent, int i) {
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            m13599d(view, i);
        } else if (motionEvent.getAction() == 2) {
            this.f9728r = (int) motionEvent.getRawX();
            this.f9729s = (int) motionEvent.getRawY();
            if (this.f9721k != null) {
                m13575a(i);
            }
            if (this.f9722l != null) {
                m13578b(i);
            }
        }
        if (this.f9724n != null) {
            this.f9724n.onTouchEvent(motionEvent);
        }
    }

    /* renamed from: a */
    public void m13594a(C2924a c2924a) {
        this.f9718h = c2924a;
    }

    /* renamed from: b */
    protected void m13595b() {
        LayoutInflater from = LayoutInflater.from(this.f9711a.f9685a);
        this.f9713c = (ViewGroup) this.f9711a.f9685a.findViewById(16908290).getRootView();
        this.f9714d = (FrameLayout) from.inflate(R.layout.peek_background, this.f9713c, false);
        if (this.f9711a.f9686b != -1) {
            this.f9712b = from.inflate(this.f9711a.f9686b, this.f9714d, false);
            this.f9712b.setId(R.id.peek_view);
            LayoutParams layoutParams = (LayoutParams) this.f9712b.getLayoutParams();
            layoutParams.gravity = 17;
            if (this.f9725o == 2) {
                layoutParams.topMargin = this.f9727q;
            }
            this.f9714d.addView(this.f9712b, layoutParams);
        } else {
            this.f9712b = this.f9711a.f9687c;
            this.f9712b.setId(R.id.peek_view);
            this.f9714d.addView(this.f9712b);
        }
        this.f9713c.addView(this.f9714d);
        this.f9714d.setVisibility(8);
        this.f9714d.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.f9714d.requestLayout();
        this.f9715e = new C2946d(this.f9711a.f9685a.getApplicationContext(), this.f9714d, this.f9712b);
        m13582d();
        m13583e();
        m13589h();
    }

    /* renamed from: b */
    public void m13596b(final View view, final int i) {
        this.f9711a.f9685a.runOnUiThread(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C2945c f9677c;

            public void run() {
                this.f9677c.f9721k.m13566b(view, i);
            }
        });
    }

    /* renamed from: c */
    protected void m13597c() {
        for (int i = 0; i < this.f9711a.f9689e.size(); i++) {
            m13592a((View) this.f9711a.f9689e.get(i), -1);
        }
        this.f9724n.setIsLongpressEnabled(false);
    }

    /* renamed from: c */
    public void m13598c(View view, int i) {
        if (this.f9720j != null) {
            this.f9720j.m13560a(view, i);
        }
        this.f9714d.setVisibility(0);
        m13576a(view);
        if (VERSION.SDK_INT >= 17 && this.f9731u) {
            m13587g();
        } else if (VERSION.SDK_INT < 17 && this.f9731u) {
            Log.e("PeekAndPop", "Unable to blur background, device version below 17");
        }
        this.f9715e.m13603a(275);
        if (this.f9711a.f9688d != null) {
            this.f9711a.f9688d.requestDisallowInterceptTouchEvent(true);
        }
        this.f9728r = 0;
        this.f9729s = 0;
        this.f9723m.m13558a(view);
        this.f9723m.m13557a(i);
    }

    /* renamed from: d */
    protected void m13599d(View view, int i) {
        if (this.f9720j != null) {
            this.f9720j.m13561b(view, i);
        }
        if (!(this.f9718h == null || this.f9722l == null)) {
            this.f9722l.m13564c(this.f9718h.m13537b(), this.f9718h.m13533a());
        }
        m13590i();
        this.f9715e.m13605a(new C29324(this), (int) Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.f9730t = System.currentTimeMillis();
    }

    /* renamed from: e */
    public void m13600e(final View view, final int i) {
        new Handler(Looper.getMainLooper()).post(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C2945c f9681c;

            public void run() {
                this.f9681c.f9722l.m13562a(view, i);
            }
        });
    }

    /* renamed from: f */
    protected void m13601f(final View view, final int i) {
        new Handler(Looper.getMainLooper()).post(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C2945c f9684c;

            public void run() {
                this.f9684c.f9722l.m13563b(view, i);
            }
        });
    }
}
