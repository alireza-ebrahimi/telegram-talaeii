package org.telegram.customization.util.view.p171b;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: org.telegram.customization.util.view.b.d */
public class C2946d {
    /* renamed from: a */
    private View f9737a;
    /* renamed from: b */
    private View f9738b;
    /* renamed from: c */
    private Context f9739c;

    public C2946d(Context context, View view, View view2) {
        this.f9739c = context;
        this.f9737a = view;
        this.f9738b = view2;
    }

    /* renamed from: a */
    public void m13602a(float f, float f2, int i, long j, float f3) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        float max;
        ObjectAnimator ofFloat;
        if (this.f9739c.getResources().getConfiguration().orientation == 1) {
            max = Math.max(f2 / 8.0f, f3);
            ofFloat = ObjectAnimator.ofFloat(this.f9738b, "translationY", new float[]{max});
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.setDuration(Math.max(0, ((long) i) - currentTimeMillis));
            ofFloat.start();
        } else if (this.f9739c.getResources().getConfiguration().orientation == 2) {
            max = Math.max(f / 8.0f, f3);
            ofFloat = ObjectAnimator.ofFloat(this.f9738b, "translationX", new float[]{max});
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.setDuration(Math.max(0, ((long) i) - currentTimeMillis));
            ofFloat.start();
        }
    }

    /* renamed from: a */
    public void m13603a(int i) {
        this.f9738b.setAlpha(1.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.f9737a, "alpha", new float[]{1.0f});
        ofFloat.setInterpolator(new OvershootInterpolator(1.2f));
        ofFloat.setDuration((long) i);
        Animator ofFloat2 = ObjectAnimator.ofFloat(this.f9738b, "scaleX", new float[]{1.0f});
        ofFloat2.setDuration((long) i);
        Animator ofFloat3 = ObjectAnimator.ofFloat(this.f9738b, "scaleY", new float[]{1.0f});
        ofFloat3.setDuration((long) i);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator(1.2f));
        animatorSet.play(ofFloat2).with(ofFloat3);
        animatorSet.start();
        ofFloat.start();
    }

    /* renamed from: a */
    public void m13604a(int i, long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.f9738b, "scaleY", new float[]{1.025f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.f9738b, "scaleX", new float[]{1.025f});
        ofFloat2.setInterpolator(new DecelerateInterpolator());
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat2.setDuration(Math.max(0, ((long) i) - currentTimeMillis));
        ofFloat.setDuration(Math.max(0, ((long) i) - currentTimeMillis));
        ofFloat2.start();
        ofFloat.start();
    }

    /* renamed from: a */
    public void m13605a(AnimatorListener animatorListener, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.f9737a, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        ofFloat.setDuration((long) i);
        ofFloat.addListener(animatorListener);
        ofFloat.setInterpolator(new DecelerateInterpolator(1.5f));
        ofFloat.start();
        m13606b(i);
    }

    /* renamed from: b */
    public void m13606b(int i) {
        ObjectAnimator ofFloat;
        if (this.f9739c.getResources().getConfiguration().orientation == 1) {
            ofFloat = ObjectAnimator.ofFloat(this.f9738b, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
        } else {
            ofFloat = ObjectAnimator.ofFloat(this.f9738b, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
        }
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.f9738b, "scaleY", new float[]{0.75f});
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.f9738b, "scaleX", new float[]{0.75f});
        ofFloat3.setInterpolator(new DecelerateInterpolator());
        ofFloat2.setInterpolator(new DecelerateInterpolator());
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat3.setDuration((long) i);
        ofFloat2.setDuration((long) i);
        ofFloat.setDuration((long) i);
        ofFloat3.start();
        ofFloat2.start();
        ofFloat.start();
    }
}
