package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v4.widget.x */
public final class C0729x {
    /* renamed from: a */
    OverScroller f1628a;
    /* renamed from: b */
    private final boolean f1629b;

    C0729x(boolean z, Context context, Interpolator interpolator) {
        this.f1629b = z;
        this.f1628a = interpolator != null ? new OverScroller(context, interpolator) : new OverScroller(context);
    }

    /* renamed from: a */
    public static C0729x m3541a(Context context) {
        return C0729x.m3542a(context, null);
    }

    /* renamed from: a */
    public static C0729x m3542a(Context context, Interpolator interpolator) {
        return new C0729x(VERSION.SDK_INT >= 14, context, interpolator);
    }

    /* renamed from: a */
    public void m3543a(int i, int i2, int i3, int i4) {
        this.f1628a.startScroll(i, i2, i3, i4);
    }

    /* renamed from: a */
    public void m3544a(int i, int i2, int i3, int i4, int i5) {
        this.f1628a.startScroll(i, i2, i3, i4, i5);
    }

    /* renamed from: a */
    public void m3545a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f1628a.fling(i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* renamed from: a */
    public void m3546a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.f1628a.fling(i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }

    /* renamed from: a */
    public boolean m3547a() {
        return this.f1628a.isFinished();
    }

    /* renamed from: a */
    public boolean m3548a(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.f1628a.springBack(i, i2, i3, i4, i5, i6);
    }

    /* renamed from: b */
    public int m3549b() {
        return this.f1628a.getCurrX();
    }

    /* renamed from: c */
    public int m3550c() {
        return this.f1628a.getCurrY();
    }

    /* renamed from: d */
    public int m3551d() {
        return this.f1628a.getFinalX();
    }

    /* renamed from: e */
    public int m3552e() {
        return this.f1628a.getFinalY();
    }

    /* renamed from: f */
    public float m3553f() {
        return this.f1629b ? C0730y.m3556a(this.f1628a) : BitmapDescriptorFactory.HUE_RED;
    }

    /* renamed from: g */
    public boolean m3554g() {
        return this.f1628a.computeScrollOffset();
    }

    /* renamed from: h */
    public void m3555h() {
        this.f1628a.abortAnimation();
    }
}
