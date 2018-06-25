package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.design.C0073a.C0062a;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.C0174q.C0172a;
import android.support.design.widget.CoordinatorLayout.C0088a;
import android.support.design.widget.CoordinatorLayout.C0104d;
import android.support.design.widget.SwipeDismissBehavior.C0116a;
import android.support.v4.view.ah;
import android.support.v4.view.bc;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;

/* renamed from: android.support.design.widget.b */
public abstract class C0114b<B extends C0114b<B>> {
    /* renamed from: a */
    static final Handler f355a = new Handler(Looper.getMainLooper(), new C01291());
    /* renamed from: b */
    final C0113f f356b;
    /* renamed from: c */
    final C0172a f357c;
    /* renamed from: d */
    private final ViewGroup f358d;
    /* renamed from: e */
    private final C0080c f359e;
    /* renamed from: f */
    private List<C0140a<B>> f360f;
    /* renamed from: g */
    private final AccessibilityManager f361g;

    /* renamed from: android.support.design.widget.b$c */
    public interface C0080c {
        /* renamed from: a */
        void mo55a(int i, int i2);

        /* renamed from: b */
        void mo56b(int i, int i2);
    }

    /* renamed from: android.support.design.widget.b$f */
    static class C0113f extends FrameLayout {
        /* renamed from: a */
        private C0135e f353a;
        /* renamed from: b */
        private C0133d f354b;

        C0113f(Context context) {
            this(context, null);
        }

        C0113f(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.SnackbarLayout);
            if (obtainStyledAttributes.hasValue(C0072k.SnackbarLayout_elevation)) {
                ah.m2821k(this, (float) obtainStyledAttributes.getDimensionPixelSize(C0072k.SnackbarLayout_elevation, 0));
            }
            obtainStyledAttributes.recycle();
            setClickable(true);
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.f354b != null) {
                this.f354b.mo126a(this);
            }
            ah.m2834x(this);
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.f354b != null) {
                this.f354b.mo127b(this);
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            if (this.f353a != null) {
                this.f353a.mo128a(this, i, i2, i3, i4);
            }
        }

        void setOnAttachStateChangeListener(C0133d c0133d) {
            this.f354b = c0133d;
        }

        void setOnLayoutChangeListener(C0135e c0135e) {
            this.f353a = c0135e;
        }
    }

    /* renamed from: android.support.design.widget.b$1 */
    static class C01291 implements Callback {
        C01291() {
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ((C0114b) message.obj).m595b();
                    return true;
                case 1:
                    ((C0114b) message.obj).m596b(message.arg1);
                    return true;
                default:
                    return false;
            }
        }
    }

    /* renamed from: android.support.design.widget.b$3 */
    class C01313 implements C0116a {
        /* renamed from: a */
        final /* synthetic */ C0114b f442a;

        C01313(C0114b c0114b) {
            this.f442a = c0114b;
        }

        /* renamed from: a */
        public void mo124a(int i) {
            switch (i) {
                case 0:
                    C0174q.m825a().m836d(this.f442a.f357c);
                    return;
                case 1:
                case 2:
                    C0174q.m825a().m835c(this.f442a.f357c);
                    return;
                default:
                    return;
            }
        }

        /* renamed from: a */
        public void mo125a(View view) {
            view.setVisibility(8);
            this.f442a.m593a(0);
        }
    }

    /* renamed from: android.support.design.widget.b$d */
    interface C0133d {
        /* renamed from: a */
        void mo126a(View view);

        /* renamed from: b */
        void mo127b(View view);
    }

    /* renamed from: android.support.design.widget.b$4 */
    class C01344 implements C0133d {
        /* renamed from: a */
        final /* synthetic */ C0114b f444a;

        /* renamed from: android.support.design.widget.b$4$1 */
        class C01321 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C01344 f443a;

            C01321(C01344 c01344) {
                this.f443a = c01344;
            }

            public void run() {
                this.f443a.f444a.m598c(3);
            }
        }

        C01344(C0114b c0114b) {
            this.f444a = c0114b;
        }

        /* renamed from: a */
        public void mo126a(View view) {
        }

        /* renamed from: b */
        public void mo127b(View view) {
            if (this.f444a.m594a()) {
                C0114b.f355a.post(new C01321(this));
            }
        }
    }

    /* renamed from: android.support.design.widget.b$e */
    interface C0135e {
        /* renamed from: a */
        void mo128a(View view, int i, int i2, int i3, int i4);
    }

    /* renamed from: android.support.design.widget.b$5 */
    class C01365 implements C0135e {
        /* renamed from: a */
        final /* synthetic */ C0114b f445a;

        C01365(C0114b c0114b) {
            this.f445a = c0114b;
        }

        /* renamed from: a */
        public void mo128a(View view, int i, int i2, int i3, int i4) {
            this.f445a.f356b.setOnLayoutChangeListener(null);
            if (this.f445a.m600e()) {
                this.f445a.m597c();
            } else {
                this.f445a.m599d();
            }
        }
    }

    /* renamed from: android.support.design.widget.b$6 */
    class C01376 extends bc {
        /* renamed from: a */
        final /* synthetic */ C0114b f446a;

        C01376(C0114b c0114b) {
            this.f446a = c0114b;
        }

        public void onAnimationEnd(View view) {
            this.f446a.m599d();
        }

        public void onAnimationStart(View view) {
            this.f446a.f359e.mo55a(70, 180);
        }
    }

    /* renamed from: android.support.design.widget.b$7 */
    class C01387 implements AnimationListener {
        /* renamed from: a */
        final /* synthetic */ C0114b f447a;

        C01387(C0114b c0114b) {
            this.f447a = c0114b;
        }

        public void onAnimationEnd(Animation animation) {
            this.f447a.m599d();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: android.support.design.widget.b$a */
    public static abstract class C0140a<B> {
        /* renamed from: a */
        public void m672a(B b) {
        }

        /* renamed from: a */
        public void m673a(B b, int i) {
        }
    }

    /* renamed from: android.support.design.widget.b$b */
    final class C0141b extends SwipeDismissBehavior<C0113f> {
        /* renamed from: a */
        final /* synthetic */ C0114b f450a;

        C0141b(C0114b c0114b) {
            this.f450a = c0114b;
        }

        /* renamed from: a */
        public boolean m674a(CoordinatorLayout coordinatorLayout, C0113f c0113f, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    if (coordinatorLayout.m546a((View) c0113f, (int) motionEvent.getX(), (int) motionEvent.getY())) {
                        C0174q.m825a().m835c(this.f450a.f357c);
                        break;
                    }
                    break;
                case 1:
                case 3:
                    C0174q.m825a().m836d(this.f450a.f357c);
                    break;
            }
            return super.mo63a(coordinatorLayout, (View) c0113f, motionEvent);
        }

        /* renamed from: a */
        public boolean mo129a(View view) {
            return view instanceof C0113f;
        }
    }

    /* renamed from: d */
    private void m592d(final int i) {
        if (VERSION.SDK_INT >= 14) {
            ah.m2827q(this.f356b).m3028c((float) this.f356b.getHeight()).m3024a(C0126a.f427b).m3021a(250).m3022a(new bc(this) {
                /* renamed from: b */
                final /* synthetic */ C0114b f449b;

                public void onAnimationEnd(View view) {
                    this.f449b.m598c(i);
                }

                public void onAnimationStart(View view) {
                    this.f449b.f359e.mo56b(0, 180);
                }
            }).m3029c();
            return;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.f356b.getContext(), C0062a.design_snackbar_out);
        loadAnimation.setInterpolator(C0126a.f427b);
        loadAnimation.setDuration(250);
        loadAnimation.setAnimationListener(new AnimationListener(this) {
            /* renamed from: b */
            final /* synthetic */ C0114b f441b;

            public void onAnimationEnd(Animation animation) {
                this.f441b.m598c(i);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        this.f356b.startAnimation(loadAnimation);
    }

    /* renamed from: a */
    void m593a(int i) {
        C0174q.m825a().m832a(this.f357c, i);
    }

    /* renamed from: a */
    public boolean m594a() {
        return C0174q.m825a().m837e(this.f357c);
    }

    /* renamed from: b */
    final void m595b() {
        if (this.f356b.getParent() == null) {
            LayoutParams layoutParams = this.f356b.getLayoutParams();
            if (layoutParams instanceof C0104d) {
                C0104d c0104d = (C0104d) layoutParams;
                C0088a c0141b = new C0141b(this);
                c0141b.m616a(0.1f);
                c0141b.m621b(0.6f);
                c0141b.m617a(0);
                c0141b.m618a(new C01313(this));
                c0104d.m497a(c0141b);
                c0104d.f298g = 80;
            }
            this.f358d.addView(this.f356b);
        }
        this.f356b.setOnAttachStateChangeListener(new C01344(this));
        if (!ah.m2767G(this.f356b)) {
            this.f356b.setOnLayoutChangeListener(new C01365(this));
        } else if (m600e()) {
            m597c();
        } else {
            m599d();
        }
    }

    /* renamed from: b */
    final void m596b(int i) {
        if (m600e() && this.f356b.getVisibility() == 0) {
            m592d(i);
        } else {
            m598c(i);
        }
    }

    /* renamed from: c */
    void m597c() {
        if (VERSION.SDK_INT >= 14) {
            ah.m2795b(this.f356b, (float) this.f356b.getHeight());
            ah.m2827q(this.f356b).m3028c(BitmapDescriptorFactory.HUE_RED).m3024a(C0126a.f427b).m3021a(250).m3022a(new C01376(this)).m3029c();
            return;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.f356b.getContext(), C0062a.design_snackbar_in);
        loadAnimation.setInterpolator(C0126a.f427b);
        loadAnimation.setDuration(250);
        loadAnimation.setAnimationListener(new C01387(this));
        this.f356b.startAnimation(loadAnimation);
    }

    /* renamed from: c */
    void m598c(int i) {
        C0174q.m825a().m831a(this.f357c);
        if (this.f360f != null) {
            for (int size = this.f360f.size() - 1; size >= 0; size--) {
                ((C0140a) this.f360f.get(size)).m673a(this, i);
            }
        }
        if (VERSION.SDK_INT < 11) {
            this.f356b.setVisibility(8);
        }
        ViewParent parent = this.f356b.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.f356b);
        }
    }

    /* renamed from: d */
    void m599d() {
        C0174q.m825a().m834b(this.f357c);
        if (this.f360f != null) {
            for (int size = this.f360f.size() - 1; size >= 0; size--) {
                ((C0140a) this.f360f.get(size)).m672a(this);
            }
        }
    }

    /* renamed from: e */
    boolean m600e() {
        return !this.f361g.isEnabled();
    }
}
