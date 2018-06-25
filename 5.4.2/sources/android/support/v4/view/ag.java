package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.VelocityTracker;

@TargetApi(11)
class ag {
    /* renamed from: a */
    public static float m2518a(VelocityTracker velocityTracker, int i) {
        return velocityTracker.getXVelocity(i);
    }

    /* renamed from: b */
    public static float m2519b(VelocityTracker velocityTracker, int i) {
        return velocityTracker.getYVelocity(i);
    }
}
