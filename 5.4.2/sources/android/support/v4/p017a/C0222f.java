package android.support.v4.p017a;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@TargetApi(12)
/* renamed from: android.support.v4.a.f */
class C0222f implements C0213c {
    /* renamed from: a */
    private TimeInterpolator f717a;

    /* renamed from: android.support.v4.a.f$a */
    static class C0219a implements AnimatorListener {
        /* renamed from: a */
        final C0212b f712a;
        /* renamed from: b */
        final C0216g f713b;

        public C0219a(C0212b c0212b, C0216g c0216g) {
            this.f712a = c0212b;
            this.f713b = c0216g;
        }

        public void onAnimationCancel(Animator animator) {
            this.f712a.onAnimationCancel(this.f713b);
        }

        public void onAnimationEnd(Animator animator) {
            this.f712a.onAnimationEnd(this.f713b);
        }

        public void onAnimationRepeat(Animator animator) {
            this.f712a.onAnimationRepeat(this.f713b);
        }

        public void onAnimationStart(Animator animator) {
            this.f712a.onAnimationStart(this.f713b);
        }
    }

    /* renamed from: android.support.v4.a.f$b */
    static class C0221b implements C0216g {
        /* renamed from: a */
        final Animator f716a;

        public C0221b(Animator animator) {
            this.f716a = animator;
        }

        /* renamed from: a */
        public void mo190a() {
            this.f716a.start();
        }

        /* renamed from: a */
        public void mo191a(long j) {
            this.f716a.setDuration(j);
        }

        /* renamed from: a */
        public void mo192a(C0212b c0212b) {
            this.f716a.addListener(new C0219a(c0212b, this));
        }

        /* renamed from: a */
        public void mo193a(final C0214d c0214d) {
            if (this.f716a instanceof ValueAnimator) {
                ((ValueAnimator) this.f716a).addUpdateListener(new AnimatorUpdateListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C0221b f715b;

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        c0214d.onAnimationUpdate(this.f715b);
                    }
                });
            }
        }

        /* renamed from: a */
        public void mo194a(View view) {
            this.f716a.setTarget(view);
        }

        /* renamed from: b */
        public void mo195b() {
            this.f716a.cancel();
        }

        /* renamed from: c */
        public float mo196c() {
            return ((ValueAnimator) this.f716a).getAnimatedFraction();
        }
    }

    C0222f() {
    }

    /* renamed from: a */
    public C0216g mo197a() {
        return new C0221b(ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
    }

    /* renamed from: a */
    public void mo198a(View view) {
        if (this.f717a == null) {
            this.f717a = new ValueAnimator().getInterpolator();
        }
        view.animate().setInterpolator(this.f717a);
    }
}
