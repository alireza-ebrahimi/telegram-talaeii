package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.view.View;

@TargetApi(16)
class az {
    /* renamed from: a */
    public static void m3044a(final View view, final bb bbVar) {
        if (bbVar != null) {
            view.animate().setListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    bbVar.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animator) {
                    bbVar.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animator) {
                    bbVar.onAnimationStart(view);
                }
            });
        } else {
            view.animate().setListener(null);
        }
    }
}
