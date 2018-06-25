package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.design.widget.C0201w.C0127d;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@TargetApi(21)
/* renamed from: android.support.design.widget.k */
class C0166k extends C0164i {
    /* renamed from: q */
    private InsetDrawable f564q;

    C0166k(af afVar, C0111p c0111p, C0127d c0127d) {
        super(afVar, c0111p, c0127d);
    }

    /* renamed from: a */
    public float mo145a() {
        return this.n.getElevation();
    }

    /* renamed from: a */
    void mo146a(float f, float f2) {
        if (VERSION.SDK_INT != 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            Animator animatorSet = new AnimatorSet();
            animatorSet.play(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
            animatorSet.setInterpolator(b);
            stateListAnimator.addState(j, animatorSet);
            animatorSet = new AnimatorSet();
            animatorSet.play(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
            animatorSet.setInterpolator(b);
            stateListAnimator.addState(k, animatorSet);
            animatorSet = new AnimatorSet();
            r2 = new Animator[3];
            r2[0] = ObjectAnimator.ofFloat(this.n, "elevation", new float[]{f}).setDuration(0);
            r2[1] = ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{this.n.getTranslationZ()}).setDuration(100);
            r2[2] = ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(100);
            animatorSet.playSequentially(r2);
            animatorSet.setInterpolator(b);
            stateListAnimator.addState(l, animatorSet);
            animatorSet = new AnimatorSet();
            animatorSet.play(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(0)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(0));
            animatorSet.setInterpolator(b);
            stateListAnimator.addState(m, animatorSet);
            this.n.setStateListAnimator(stateListAnimator);
        } else if (this.n.isEnabled()) {
            this.n.setElevation(f);
            if (this.n.isFocused() || this.n.isPressed()) {
                this.n.setTranslationZ(f2);
            } else {
                this.n.setTranslationZ(BitmapDescriptorFactory.HUE_RED);
            }
        } else {
            this.n.setElevation(BitmapDescriptorFactory.HUE_RED);
            this.n.setTranslationZ(BitmapDescriptorFactory.HUE_RED);
        }
        if (this.o.mo115b()) {
            m775g();
        }
    }

    /* renamed from: a */
    void mo147a(int i) {
        if (this.e instanceof RippleDrawable) {
            ((RippleDrawable) this.e).setColor(ColorStateList.valueOf(i));
        } else {
            super.mo147a(i);
        }
    }

    /* renamed from: a */
    void mo150a(Rect rect) {
        if (this.o.mo115b()) {
            float a = this.o.mo112a();
            float a2 = mo145a() + this.i;
            int ceil = (int) Math.ceil((double) C0170o.m815b(a2, a, false));
            int ceil2 = (int) Math.ceil((double) C0170o.m812a(a2, a, false));
            rect.set(ceil, ceil2, ceil, ceil2);
            return;
        }
        rect.set(0, 0, 0, 0);
    }

    /* renamed from: a */
    void mo152a(int[] iArr) {
    }

    /* renamed from: b */
    void mo153b() {
    }

    /* renamed from: b */
    void mo158b(Rect rect) {
        if (this.o.mo115b()) {
            this.f564q = new InsetDrawable(this.e, rect.left, rect.top, rect.right, rect.bottom);
            this.o.mo114a(this.f564q);
            return;
        }
        this.o.mo114a(this.e);
    }

    /* renamed from: c */
    void mo155c() {
        m775g();
    }

    /* renamed from: d */
    boolean mo156d() {
        return false;
    }
}
