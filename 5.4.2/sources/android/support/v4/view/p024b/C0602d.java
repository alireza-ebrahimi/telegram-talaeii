package android.support.v4.view.p024b;

import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v4.view.b.d */
abstract class C0602d implements Interpolator {
    /* renamed from: a */
    private final float[] f1349a;
    /* renamed from: b */
    private final float f1350b = (1.0f / ((float) (this.f1349a.length - 1)));

    public C0602d(float[] fArr) {
        this.f1349a = fArr;
    }

    public float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int min = Math.min((int) (((float) (this.f1349a.length - 1)) * f), this.f1349a.length - 2);
        float f2 = (f - (((float) min) * this.f1350b)) / this.f1350b;
        return ((this.f1349a[min + 1] - this.f1349a[min]) * f2) + this.f1349a[min];
    }
}
