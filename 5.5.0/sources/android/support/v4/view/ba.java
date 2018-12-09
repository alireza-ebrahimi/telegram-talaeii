package android.support.v4.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.view.View;

@TargetApi(19)
class ba {
    /* renamed from: a */
    public static void m3054a(final View view, final bd bdVar) {
        AnimatorUpdateListener animatorUpdateListener = null;
        if (bdVar != null) {
            animatorUpdateListener = new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    bdVar.mo678a(view);
                }
            };
        }
        view.animate().setUpdateListener(animatorUpdateListener);
    }
}
