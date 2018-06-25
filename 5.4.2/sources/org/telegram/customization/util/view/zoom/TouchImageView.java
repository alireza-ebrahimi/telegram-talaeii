package org.telegram.customization.util.view.zoom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.OverScroller;
import android.widget.Scroller;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class TouchImageView extends ImageView {
    /* renamed from: A */
    private OnTouchListener f9872A = null;
    /* renamed from: B */
    private C2967e f9873B = null;
    /* renamed from: a */
    private float f9874a;
    /* renamed from: b */
    private Matrix f9875b;
    /* renamed from: c */
    private Matrix f9876c;
    /* renamed from: d */
    private C2970h f9877d;
    /* renamed from: e */
    private float f9878e;
    /* renamed from: f */
    private float f9879f;
    /* renamed from: g */
    private float f9880g;
    /* renamed from: h */
    private float f9881h;
    /* renamed from: i */
    private float[] f9882i;
    /* renamed from: j */
    private Context f9883j;
    /* renamed from: k */
    private C2965c f9884k;
    /* renamed from: l */
    private ScaleType f9885l;
    /* renamed from: m */
    private boolean f9886m;
    /* renamed from: n */
    private boolean f9887n;
    /* renamed from: o */
    private C2971i f9888o;
    /* renamed from: p */
    private int f9889p;
    /* renamed from: q */
    private int f9890q;
    /* renamed from: r */
    private int f9891r;
    /* renamed from: s */
    private int f9892s;
    /* renamed from: t */
    private float f9893t;
    /* renamed from: u */
    private float f9894u;
    /* renamed from: v */
    private float f9895v;
    /* renamed from: w */
    private float f9896w;
    /* renamed from: x */
    private ScaleGestureDetector f9897x;
    /* renamed from: y */
    private GestureDetector f9898y;
    /* renamed from: z */
    private OnDoubleTapListener f9899z = null;

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$1 */
    static /* synthetic */ class C29621 {
        /* renamed from: a */
        static final /* synthetic */ int[] f9838a = new int[ScaleType.values().length];

        static {
            try {
                f9838a[ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f9838a[ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f9838a[ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f9838a[ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f9838a[ScaleType.FIT_XY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @TargetApi(9)
    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$a */
    private class C2963a {
        /* renamed from: a */
        Scroller f9839a;
        /* renamed from: b */
        OverScroller f9840b;
        /* renamed from: c */
        boolean f9841c;
        /* renamed from: d */
        final /* synthetic */ TouchImageView f9842d;

        public C2963a(TouchImageView touchImageView, Context context) {
            this.f9842d = touchImageView;
            if (VERSION.SDK_INT < 9) {
                this.f9841c = true;
                this.f9839a = new Scroller(context);
                return;
            }
            this.f9841c = false;
            this.f9840b = new OverScroller(context);
        }

        /* renamed from: a */
        public void m13670a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (this.f9841c) {
                this.f9839a.fling(i, i2, i3, i4, i5, i6, i7, i8);
            } else {
                this.f9840b.fling(i, i2, i3, i4, i5, i6, i7, i8);
            }
        }

        /* renamed from: a */
        public void m13671a(boolean z) {
            if (this.f9841c) {
                this.f9839a.forceFinished(z);
            } else {
                this.f9840b.forceFinished(z);
            }
        }

        /* renamed from: a */
        public boolean m13672a() {
            return this.f9841c ? this.f9839a.isFinished() : this.f9840b.isFinished();
        }

        /* renamed from: b */
        public boolean m13673b() {
            if (this.f9841c) {
                return this.f9839a.computeScrollOffset();
            }
            this.f9840b.computeScrollOffset();
            return this.f9840b.computeScrollOffset();
        }

        /* renamed from: c */
        public int m13674c() {
            return this.f9841c ? this.f9839a.getCurrX() : this.f9840b.getCurrX();
        }

        /* renamed from: d */
        public int m13675d() {
            return this.f9841c ? this.f9839a.getCurrY() : this.f9840b.getCurrY();
        }
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$b */
    private class C2964b implements Runnable {
        /* renamed from: a */
        final /* synthetic */ TouchImageView f9843a;
        /* renamed from: b */
        private long f9844b;
        /* renamed from: c */
        private float f9845c;
        /* renamed from: d */
        private float f9846d;
        /* renamed from: e */
        private float f9847e;
        /* renamed from: f */
        private float f9848f;
        /* renamed from: g */
        private boolean f9849g;
        /* renamed from: h */
        private AccelerateDecelerateInterpolator f9850h = new AccelerateDecelerateInterpolator();
        /* renamed from: i */
        private PointF f9851i;
        /* renamed from: j */
        private PointF f9852j;

        C2964b(TouchImageView touchImageView, float f, float f2, float f3, boolean z) {
            this.f9843a = touchImageView;
            touchImageView.setState(C2970h.ANIMATE_ZOOM);
            this.f9844b = System.currentTimeMillis();
            this.f9845c = touchImageView.f9874a;
            this.f9846d = f;
            this.f9849g = z;
            PointF a = touchImageView.m13684a(f2, f3, false);
            this.f9847e = a.x;
            this.f9848f = a.y;
            this.f9851i = touchImageView.m13683a(this.f9847e, this.f9848f);
            this.f9852j = new PointF((float) (touchImageView.f9889p / 2), (float) (touchImageView.f9890q / 2));
        }

        /* renamed from: a */
        private float m13676a() {
            return this.f9850h.getInterpolation(Math.min(1.0f, ((float) (System.currentTimeMillis() - this.f9844b)) / 500.0f));
        }

        /* renamed from: a */
        private void m13677a(float f) {
            float f2 = this.f9851i.x + ((this.f9852j.x - this.f9851i.x) * f);
            float f3 = this.f9851i.y + ((this.f9852j.y - this.f9851i.y) * f);
            PointF a = this.f9843a.m13683a(this.f9847e, this.f9848f);
            this.f9843a.f9875b.postTranslate(f2 - a.x, f3 - a.y);
        }

        /* renamed from: b */
        private double m13678b(float f) {
            return ((double) (this.f9845c + ((this.f9846d - this.f9845c) * f))) / ((double) this.f9843a.f9874a);
        }

        public void run() {
            float a = m13676a();
            this.f9843a.m13689a(m13678b(a), this.f9847e, this.f9848f, this.f9849g);
            m13677a(a);
            this.f9843a.m13704e();
            this.f9843a.setImageMatrix(this.f9843a.f9875b);
            if (this.f9843a.f9873B != null) {
                this.f9843a.f9873B.m13680a();
            }
            if (a < 1.0f) {
                this.f9843a.m13692a((Runnable) this);
            } else {
                this.f9843a.setState(C2970h.NONE);
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$c */
    private class C2965c implements Runnable {
        /* renamed from: a */
        C2963a f9853a;
        /* renamed from: b */
        int f9854b;
        /* renamed from: c */
        int f9855c;
        /* renamed from: d */
        final /* synthetic */ TouchImageView f9856d;

        C2965c(TouchImageView touchImageView, int i, int i2) {
            int i3;
            int i4;
            int k;
            int i5;
            this.f9856d = touchImageView;
            touchImageView.setState(C2970h.FLING);
            this.f9853a = new C2963a(touchImageView, touchImageView.f9883j);
            touchImageView.f9875b.getValues(touchImageView.f9882i);
            int i6 = (int) touchImageView.f9882i[2];
            int i7 = (int) touchImageView.f9882i[5];
            if (touchImageView.getImageWidth() > ((float) touchImageView.f9889p)) {
                i3 = touchImageView.f9889p - ((int) touchImageView.getImageWidth());
                i4 = 0;
            } else {
                i4 = i6;
                i3 = i6;
            }
            if (touchImageView.getImageHeight() > ((float) touchImageView.f9890q)) {
                k = touchImageView.f9890q - ((int) touchImageView.getImageHeight());
                i5 = 0;
            } else {
                i5 = i7;
                k = i7;
            }
            this.f9853a.m13670a(i6, i7, i, i2, i3, i4, k, i5);
            this.f9854b = i6;
            this.f9855c = i7;
        }

        /* renamed from: a */
        public void m13679a() {
            if (this.f9853a != null) {
                this.f9856d.setState(C2970h.NONE);
                this.f9853a.m13671a(true);
            }
        }

        public void run() {
            if (this.f9856d.f9873B != null) {
                this.f9856d.f9873B.m13680a();
            }
            if (this.f9853a.m13672a()) {
                this.f9853a = null;
            } else if (this.f9853a.m13673b()) {
                int c = this.f9853a.m13674c();
                int d = this.f9853a.m13675d();
                int i = c - this.f9854b;
                int i2 = d - this.f9855c;
                this.f9854b = c;
                this.f9855c = d;
                this.f9856d.f9875b.postTranslate((float) i, (float) i2);
                this.f9856d.m13702d();
                this.f9856d.setImageMatrix(this.f9856d.f9875b);
                this.f9856d.m13692a((Runnable) this);
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$d */
    private class C2966d extends SimpleOnGestureListener {
        /* renamed from: a */
        final /* synthetic */ TouchImageView f9857a;

        private C2966d(TouchImageView touchImageView) {
            this.f9857a = touchImageView;
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            boolean onDoubleTap = this.f9857a.f9899z != null ? this.f9857a.f9899z.onDoubleTap(motionEvent) : false;
            if (this.f9857a.f9877d != C2970h.NONE) {
                return onDoubleTap;
            }
            this.f9857a.m13692a(new C2964b(this.f9857a, this.f9857a.f9874a == this.f9857a.f9878e ? this.f9857a.f9879f : this.f9857a.f9878e, motionEvent.getX(), motionEvent.getY(), false));
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return this.f9857a.f9899z != null ? this.f9857a.f9899z.onDoubleTapEvent(motionEvent) : false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (this.f9857a.f9884k != null) {
                this.f9857a.f9884k.m13679a();
            }
            this.f9857a.f9884k = new C2965c(this.f9857a, (int) f, (int) f2);
            this.f9857a.m13692a(this.f9857a.f9884k);
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        public void onLongPress(MotionEvent motionEvent) {
            this.f9857a.performLongClick();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return this.f9857a.f9899z != null ? this.f9857a.f9899z.onSingleTapConfirmed(motionEvent) : this.f9857a.performClick();
        }
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$e */
    public interface C2967e {
        /* renamed from: a */
        void m13680a();
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$f */
    private class C2968f implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ TouchImageView f9858a;
        /* renamed from: b */
        private PointF f9859b;

        private C2968f(TouchImageView touchImageView) {
            this.f9858a = touchImageView;
            this.f9859b = new PointF();
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            this.f9858a.f9897x.onTouchEvent(motionEvent);
            this.f9858a.f9898y.onTouchEvent(motionEvent);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            if (this.f9858a.f9877d == C2970h.NONE || this.f9858a.f9877d == C2970h.DRAG || this.f9858a.f9877d == C2970h.FLING) {
                switch (motionEvent.getAction()) {
                    case 0:
                        this.f9859b.set(pointF);
                        if (this.f9858a.f9884k != null) {
                            this.f9858a.f9884k.m13679a();
                        }
                        this.f9858a.setState(C2970h.DRAG);
                        break;
                    case 1:
                    case 6:
                        this.f9858a.setState(C2970h.NONE);
                        break;
                    case 2:
                        if (this.f9858a.f9877d == C2970h.DRAG) {
                            float f = pointF.y - this.f9859b.y;
                            this.f9858a.f9875b.postTranslate(this.f9858a.m13698c(pointF.x - this.f9859b.x, (float) this.f9858a.f9889p, this.f9858a.getImageWidth()), this.f9858a.m13698c(f, (float) this.f9858a.f9890q, this.f9858a.getImageHeight()));
                            this.f9858a.m13702d();
                            this.f9859b.set(pointF.x, pointF.y);
                            break;
                        }
                        break;
                }
            }
            this.f9858a.setImageMatrix(this.f9858a.f9875b);
            if (this.f9858a.f9872A != null) {
                this.f9858a.f9872A.onTouch(view, motionEvent);
            }
            if (this.f9858a.f9873B != null) {
                this.f9858a.f9873B.m13680a();
            }
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$g */
    private class C2969g extends SimpleOnScaleGestureListener {
        /* renamed from: a */
        final /* synthetic */ TouchImageView f9860a;

        private C2969g(TouchImageView touchImageView) {
            this.f9860a = touchImageView;
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            this.f9860a.m13689a((double) scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (this.f9860a.f9873B != null) {
                this.f9860a.f9873B.m13680a();
            }
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            this.f9860a.setState(C2970h.ZOOM);
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(scaleGestureDetector);
            this.f9860a.setState(C2970h.NONE);
            boolean z = false;
            float d = this.f9860a.f9874a;
            if (this.f9860a.f9874a > this.f9860a.f9879f) {
                d = this.f9860a.f9879f;
                z = true;
            } else if (this.f9860a.f9874a < this.f9860a.f9878e) {
                d = this.f9860a.f9878e;
                z = true;
            }
            if (z) {
                this.f9860a.m13692a(new C2964b(this.f9860a, d, (float) (this.f9860a.f9889p / 2), (float) (this.f9860a.f9890q / 2), true));
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$h */
    private enum C2970h {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }

    /* renamed from: org.telegram.customization.util.view.zoom.TouchImageView$i */
    private class C2971i {
        /* renamed from: a */
        public float f9867a;
        /* renamed from: b */
        public float f9868b;
        /* renamed from: c */
        public float f9869c;
        /* renamed from: d */
        public ScaleType f9870d;
        /* renamed from: e */
        final /* synthetic */ TouchImageView f9871e;

        public C2971i(TouchImageView touchImageView, float f, float f2, float f3, ScaleType scaleType) {
            this.f9871e = touchImageView;
            this.f9867a = f;
            this.f9868b = f2;
            this.f9869c = f3;
            this.f9870d = scaleType;
        }
    }

    public TouchImageView(Context context) {
        super(context);
        m13691a(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13691a(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m13691a(context);
    }

    /* renamed from: a */
    private int m13682a(int i, int i2, int i3) {
        switch (i) {
            case Integer.MIN_VALUE:
                return Math.min(i3, i2);
            case 0:
                return i3;
            default:
                return i2;
        }
    }

    /* renamed from: a */
    private PointF m13683a(float f, float f2) {
        this.f9875b.getValues(this.f9882i);
        float intrinsicWidth = f / ((float) getDrawable().getIntrinsicWidth());
        float intrinsicHeight = f2 / ((float) getDrawable().getIntrinsicHeight());
        return new PointF((intrinsicWidth * getImageWidth()) + this.f9882i[2], (intrinsicHeight * getImageHeight()) + this.f9882i[5]);
    }

    /* renamed from: a */
    private PointF m13684a(float f, float f2, boolean z) {
        this.f9875b.getValues(this.f9882i);
        float intrinsicWidth = (float) getDrawable().getIntrinsicWidth();
        float intrinsicHeight = (float) getDrawable().getIntrinsicHeight();
        float f3 = this.f9882i[2];
        float imageWidth = ((f - f3) * intrinsicWidth) / getImageWidth();
        f3 = ((f2 - this.f9882i[5]) * intrinsicHeight) / getImageHeight();
        if (z) {
            imageWidth = Math.min(Math.max(imageWidth, BitmapDescriptorFactory.HUE_RED), intrinsicWidth);
            f3 = Math.min(Math.max(f3, BitmapDescriptorFactory.HUE_RED), intrinsicHeight);
        }
        return new PointF(imageWidth, f3);
    }

    /* renamed from: a */
    private void m13689a(double d, float f, float f2, boolean z) {
        float f3;
        float f4;
        if (z) {
            f3 = this.f9880g;
            f4 = this.f9881h;
        } else {
            f3 = this.f9878e;
            f4 = this.f9879f;
        }
        float f5 = this.f9874a;
        this.f9874a = (float) (((double) this.f9874a) * d);
        if (this.f9874a > f4) {
            this.f9874a = f4;
            d = (double) (f4 / f5);
        } else if (this.f9874a < f3) {
            this.f9874a = f3;
            d = (double) (f3 / f5);
        }
        this.f9875b.postScale((float) d, (float) d, f, f2);
        m13704e();
    }

    /* renamed from: a */
    private void m13690a(int i, float f, float f2, float f3, int i2, int i3, int i4) {
        if (f3 < ((float) i3)) {
            this.f9882i[i] = (((float) i3) - (((float) i4) * this.f9882i[0])) * 0.5f;
        } else if (f > BitmapDescriptorFactory.HUE_RED) {
            this.f9882i[i] = -((f3 - ((float) i3)) * 0.5f);
        } else {
            this.f9882i[i] = -((((Math.abs(f) + (((float) i2) * 0.5f)) / f2) * f3) - (((float) i3) * 0.5f));
        }
    }

    /* renamed from: a */
    private void m13691a(Context context) {
        super.setClickable(true);
        this.f9883j = context;
        this.f9897x = new ScaleGestureDetector(context, new C2969g());
        this.f9898y = new GestureDetector(context, new C2966d());
        this.f9875b = new Matrix();
        this.f9876c = new Matrix();
        this.f9882i = new float[9];
        this.f9874a = 1.0f;
        if (this.f9885l == null) {
            this.f9885l = ScaleType.FIT_CENTER;
        }
        this.f9878e = 1.0f;
        this.f9879f = 3.0f;
        this.f9880g = 0.75f * this.f9878e;
        this.f9881h = 1.25f * this.f9879f;
        setImageMatrix(this.f9875b);
        setScaleType(ScaleType.MATRIX);
        setState(C2970h.NONE);
        this.f9887n = false;
        super.setOnTouchListener(new C2968f());
    }

    @TargetApi(16)
    /* renamed from: a */
    private void m13692a(Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 16);
        }
    }

    /* renamed from: b */
    private float m13696b(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = BitmapDescriptorFactory.HUE_RED;
        } else {
            f5 = f2 - f3;
            f4 = BitmapDescriptorFactory.HUE_RED;
        }
        return f < f5 ? (-f) + f5 : f > f4 ? (-f) + f4 : BitmapDescriptorFactory.HUE_RED;
    }

    /* renamed from: c */
    private float m13698c(float f, float f2, float f3) {
        return f3 <= f2 ? BitmapDescriptorFactory.HUE_RED : f;
    }

    /* renamed from: c */
    private void m13700c() {
        if (this.f9875b != null && this.f9890q != 0 && this.f9889p != 0) {
            this.f9875b.getValues(this.f9882i);
            this.f9876c.setValues(this.f9882i);
            this.f9896w = this.f9894u;
            this.f9895v = this.f9893t;
            this.f9892s = this.f9890q;
            this.f9891r = this.f9889p;
        }
    }

    /* renamed from: d */
    private void m13702d() {
        this.f9875b.getValues(this.f9882i);
        float f = this.f9882i[2];
        float f2 = this.f9882i[5];
        f = m13696b(f, (float) this.f9889p, getImageWidth());
        f2 = m13696b(f2, (float) this.f9890q, getImageHeight());
        if (f != BitmapDescriptorFactory.HUE_RED || f2 != BitmapDescriptorFactory.HUE_RED) {
            this.f9875b.postTranslate(f, f2);
        }
    }

    /* renamed from: e */
    private void m13704e() {
        m13702d();
        this.f9875b.getValues(this.f9882i);
        if (getImageWidth() < ((float) this.f9889p)) {
            this.f9882i[2] = (((float) this.f9889p) - getImageWidth()) / 2.0f;
        }
        if (getImageHeight() < ((float) this.f9890q)) {
            this.f9882i[5] = (((float) this.f9890q) - getImageHeight()) / 2.0f;
        }
        this.f9875b.setValues(this.f9882i);
    }

    /* renamed from: f */
    private void m13706f() {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0 && this.f9875b != null && this.f9876c != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            float f = ((float) this.f9889p) / ((float) intrinsicWidth);
            float f2 = ((float) this.f9890q) / ((float) intrinsicHeight);
            switch (C29621.f9838a[this.f9885l.ordinal()]) {
                case 1:
                    f2 = 1.0f;
                    f = 1.0f;
                    break;
                case 2:
                    f2 = Math.max(f, f2);
                    f = f2;
                    break;
                case 3:
                    f2 = Math.min(1.0f, Math.min(f, f2));
                    f = f2;
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
            }
            f2 = Math.min(f, f2);
            f = f2;
            float f3 = ((float) this.f9889p) - (((float) intrinsicWidth) * f);
            float f4 = ((float) this.f9890q) - (((float) intrinsicHeight) * f2);
            this.f9893t = ((float) this.f9889p) - f3;
            this.f9894u = ((float) this.f9890q) - f4;
            if (m13722a() || this.f9886m) {
                if (this.f9895v == BitmapDescriptorFactory.HUE_RED || this.f9896w == BitmapDescriptorFactory.HUE_RED) {
                    m13700c();
                }
                this.f9876c.getValues(this.f9882i);
                this.f9882i[0] = (this.f9893t / ((float) intrinsicWidth)) * this.f9874a;
                this.f9882i[4] = (this.f9894u / ((float) intrinsicHeight)) * this.f9874a;
                f = this.f9882i[2];
                float f5 = this.f9882i[5];
                m13690a(2, f, this.f9874a * this.f9895v, getImageWidth(), this.f9891r, this.f9889p, intrinsicWidth);
                m13690a(5, f5, this.f9896w * this.f9874a, getImageHeight(), this.f9892s, this.f9890q, intrinsicHeight);
                this.f9875b.setValues(this.f9882i);
            } else {
                this.f9875b.setScale(f, f2);
                this.f9875b.postTranslate(f3 / 2.0f, f4 / 2.0f);
                this.f9874a = 1.0f;
            }
            m13702d();
            setImageMatrix(this.f9875b);
        }
    }

    private float getImageHeight() {
        return this.f9894u * this.f9874a;
    }

    private float getImageWidth() {
        return this.f9893t * this.f9874a;
    }

    private void setState(C2970h c2970h) {
        this.f9877d = c2970h;
    }

    /* renamed from: a */
    public void m13720a(float f, float f2, float f3) {
        m13721a(f, f2, f3, this.f9885l);
    }

    /* renamed from: a */
    public void m13721a(float f, float f2, float f3, ScaleType scaleType) {
        if (this.f9887n) {
            if (scaleType != this.f9885l) {
                setScaleType(scaleType);
            }
            m13724b();
            m13689a((double) f, (float) (this.f9889p / 2), (float) (this.f9890q / 2), true);
            this.f9875b.getValues(this.f9882i);
            this.f9882i[2] = -((getImageWidth() * f2) - (((float) this.f9889p) * 0.5f));
            this.f9882i[5] = -((getImageHeight() * f3) - (((float) this.f9890q) * 0.5f));
            this.f9875b.setValues(this.f9882i);
            m13702d();
            setImageMatrix(this.f9875b);
            return;
        }
        this.f9888o = new C2971i(this, f, f2, f3, scaleType);
    }

    /* renamed from: a */
    public boolean m13722a() {
        return this.f9874a != 1.0f;
    }

    /* renamed from: a */
    public boolean m13723a(int i) {
        return canScrollHorizontally(i);
    }

    /* renamed from: b */
    public void m13724b() {
        this.f9874a = 1.0f;
        m13706f();
    }

    public boolean canScrollHorizontally(int i) {
        this.f9875b.getValues(this.f9882i);
        float f = this.f9882i[2];
        return getImageWidth() < ((float) this.f9889p) ? false : (f < -1.0f || i >= 0) ? (Math.abs(f) + ((float) this.f9889p)) + 1.0f < getImageWidth() || i <= 0 : false;
    }

    public float getCurrentZoom() {
        return this.f9874a;
    }

    public float getMaxZoom() {
        return this.f9879f;
    }

    public float getMinZoom() {
        return this.f9878e;
    }

    public ScaleType getScaleType() {
        return this.f9885l;
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        PointF a = m13684a((float) (this.f9889p / 2), (float) (this.f9890q / 2), true);
        a.x /= (float) intrinsicWidth;
        a.y /= (float) intrinsicHeight;
        return a;
    }

    public RectF getZoomedRect() {
        if (this.f9885l == ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF a = m13684a((float) BitmapDescriptorFactory.HUE_RED, (float) BitmapDescriptorFactory.HUE_RED, true);
        PointF a2 = m13684a((float) this.f9889p, (float) this.f9890q, true);
        float intrinsicWidth = (float) getDrawable().getIntrinsicWidth();
        float intrinsicHeight = (float) getDrawable().getIntrinsicHeight();
        return new RectF(a.x / intrinsicWidth, a.y / intrinsicHeight, a2.x / intrinsicWidth, a2.y / intrinsicHeight);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m13700c();
    }

    protected void onDraw(Canvas canvas) {
        this.f9887n = true;
        this.f9886m = true;
        if (this.f9888o != null) {
            m13721a(this.f9888o.f9867a, this.f9888o.f9868b, this.f9888o.f9869c, this.f9888o.f9870d);
            this.f9888o = null;
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int i2) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int size = MeasureSpec.getSize(i);
        int mode = MeasureSpec.getMode(i);
        int size2 = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i2);
        this.f9889p = m13682a(mode, size, intrinsicWidth);
        this.f9890q = m13682a(mode2, size2, intrinsicHeight);
        setMeasuredDimension(this.f9889p, this.f9890q);
        m13706f();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.f9874a = bundle.getFloat("saveScale");
            this.f9882i = bundle.getFloatArray("matrix");
            this.f9876c.setValues(this.f9882i);
            this.f9896w = bundle.getFloat("matchViewHeight");
            this.f9895v = bundle.getFloat("matchViewWidth");
            this.f9892s = bundle.getInt("viewHeight");
            this.f9891r = bundle.getInt("viewWidth");
            this.f9886m = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Parcelable bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.f9874a);
        bundle.putFloat("matchViewHeight", this.f9894u);
        bundle.putFloat("matchViewWidth", this.f9893t);
        bundle.putInt("viewWidth", this.f9889p);
        bundle.putInt("viewHeight", this.f9890q);
        this.f9875b.getValues(this.f9882i);
        bundle.putFloatArray("matrix", this.f9882i);
        bundle.putBoolean("imageRendered", this.f9886m);
        return bundle;
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        m13700c();
        m13706f();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        m13700c();
        m13706f();
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        m13700c();
        m13706f();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        m13700c();
        m13706f();
    }

    public void setMaxZoom(float f) {
        this.f9879f = f;
        this.f9881h = 1.25f * this.f9879f;
    }

    public void setMinZoom(float f) {
        this.f9878e = f;
        this.f9880g = 0.75f * this.f9878e;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.f9899z = onDoubleTapListener;
    }

    public void setOnTouchImageViewListener(C2967e c2967e) {
        this.f9873B = c2967e;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.f9872A = onTouchListener;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.FIT_START || scaleType == ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        } else if (scaleType == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);
        } else {
            this.f9885l = scaleType;
            if (this.f9887n) {
                setZoom(this);
            }
        }
    }

    public void setZoom(float f) {
        m13720a(f, 0.5f, 0.5f);
    }

    public void setZoom(TouchImageView touchImageView) {
        PointF scrollPosition = touchImageView.getScrollPosition();
        m13721a(touchImageView.getCurrentZoom(), scrollPosition.x, scrollPosition.y, touchImageView.getScaleType());
    }
}
