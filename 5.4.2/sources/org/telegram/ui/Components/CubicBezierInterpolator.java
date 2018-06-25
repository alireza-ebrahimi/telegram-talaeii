package org.telegram.ui.Components;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class CubicBezierInterpolator implements Interpolator {
    public static final CubicBezierInterpolator DEFAULT = new CubicBezierInterpolator(0.25d, 0.1d, 0.25d, 1.0d);
    public static final CubicBezierInterpolator EASE_BOTH = new CubicBezierInterpolator(0.42d, 0.0d, 0.58d, 1.0d);
    public static final CubicBezierInterpolator EASE_IN = new CubicBezierInterpolator(0.42d, 0.0d, 1.0d, 1.0d);
    public static final CubicBezierInterpolator EASE_OUT = new CubicBezierInterpolator(0.0d, 0.0d, 0.58d, 1.0d);
    public static final CubicBezierInterpolator EASE_OUT_QUINT = new CubicBezierInterpolator(0.23d, 1.0d, 0.32d, 1.0d);
    /* renamed from: a */
    protected PointF f10177a;
    /* renamed from: b */
    protected PointF f10178b;
    /* renamed from: c */
    protected PointF f10179c;
    protected PointF end;
    protected PointF start;

    public CubicBezierInterpolator(double d, double d2, double d3, double d4) {
        this((float) d, (float) d2, (float) d3, (float) d4);
    }

    public CubicBezierInterpolator(float f, float f2, float f3, float f4) {
        this(new PointF(f, f2), new PointF(f3, f4));
    }

    public CubicBezierInterpolator(PointF pointF, PointF pointF2) {
        this.f10177a = new PointF();
        this.f10178b = new PointF();
        this.f10179c = new PointF();
        if (pointF.x < BitmapDescriptorFactory.HUE_RED || pointF.x > 1.0f) {
            throw new IllegalArgumentException("startX value must be in the range [0, 1]");
        } else if (pointF2.x < BitmapDescriptorFactory.HUE_RED || pointF2.x > 1.0f) {
            throw new IllegalArgumentException("endX value must be in the range [0, 1]");
        } else {
            this.start = pointF;
            this.end = pointF2;
        }
    }

    private float getBezierCoordinateX(float f) {
        this.f10179c.x = this.start.x * 3.0f;
        this.f10178b.x = ((this.end.x - this.start.x) * 3.0f) - this.f10179c.x;
        this.f10177a.x = (1.0f - this.f10179c.x) - this.f10178b.x;
        return (this.f10179c.x + ((this.f10178b.x + (this.f10177a.x * f)) * f)) * f;
    }

    private float getXDerivate(float f) {
        return this.f10179c.x + (((2.0f * this.f10178b.x) + ((3.0f * this.f10177a.x) * f)) * f);
    }

    protected float getBezierCoordinateY(float f) {
        this.f10179c.y = this.start.y * 3.0f;
        this.f10178b.y = ((this.end.y - this.start.y) * 3.0f) - this.f10179c.y;
        this.f10177a.y = (1.0f - this.f10179c.y) - this.f10178b.y;
        return (this.f10179c.y + ((this.f10178b.y + (this.f10177a.y * f)) * f)) * f;
    }

    public float getInterpolation(float f) {
        return getBezierCoordinateY(getXForTime(f));
    }

    protected float getXForTime(float f) {
        float f2 = f;
        for (int i = 1; i < 14; i++) {
            float bezierCoordinateX = getBezierCoordinateX(f2) - f;
            if (((double) Math.abs(bezierCoordinateX)) < 0.001d) {
                break;
            }
            f2 -= bezierCoordinateX / getXDerivate(f2);
        }
        return f2;
    }
}
