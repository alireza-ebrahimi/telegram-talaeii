package utils.p178a;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/* renamed from: utils.a.a */
public class C5312a {
    /* renamed from: a */
    public static void m14122a(final View view) {
        view.measure(-1, -1);
        final int measuredHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 1;
        view.setVisibility(0);
        Animation c53101 = new Animation() {
            protected void applyTransformation(float f, Transformation transformation) {
                view.getLayoutParams().height = f == 1.0f ? -1 : (int) (((float) measuredHeight) * f);
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        c53101.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(c53101);
    }

    /* renamed from: b */
    public static void m14123b(final View view) {
        final int measuredHeight = view.getMeasuredHeight();
        Animation c53112 = new Animation() {
            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 1.0f) {
                    view.setVisibility(8);
                    return;
                }
                view.getLayoutParams().height = measuredHeight - ((int) (((float) measuredHeight) * f));
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        c53112.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(c53112);
    }
}
