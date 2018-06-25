package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.support.v4.view.ah;
import android.view.View;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v4.widget.b */
class C0682b extends ImageView {
    /* renamed from: a */
    int f1544a;
    /* renamed from: b */
    private AnimationListener f1545b;

    /* renamed from: android.support.v4.widget.b$a */
    private class C0681a extends OvalShape {
        /* renamed from: a */
        final /* synthetic */ C0682b f1541a;
        /* renamed from: b */
        private RadialGradient f1542b;
        /* renamed from: c */
        private Paint f1543c = new Paint();

        C0681a(C0682b c0682b, int i) {
            this.f1541a = c0682b;
            c0682b.f1544a = i;
            m3356a((int) rect().width());
        }

        /* renamed from: a */
        private void m3356a(int i) {
            this.f1542b = new RadialGradient((float) (i / 2), (float) (i / 2), (float) this.f1541a.f1544a, new int[]{1023410176, 0}, null, TileMode.CLAMP);
            this.f1543c.setShader(this.f1542b);
        }

        public void draw(Canvas canvas, Paint paint) {
            int width = this.f1541a.getWidth();
            int height = this.f1541a.getHeight();
            canvas.drawCircle((float) (width / 2), (float) (height / 2), (float) (width / 2), this.f1543c);
            canvas.drawCircle((float) (width / 2), (float) (height / 2), (float) ((width / 2) - this.f1541a.f1544a), paint);
        }

        protected void onResize(float f, float f2) {
            super.onResize(f, f2);
            m3356a((int) f);
        }
    }

    C0682b(Context context, int i) {
        Drawable shapeDrawable;
        super(context);
        float f = getContext().getResources().getDisplayMetrics().density;
        int i2 = (int) (1.75f * f);
        int i3 = (int) (BitmapDescriptorFactory.HUE_RED * f);
        this.f1544a = (int) (3.5f * f);
        if (m3357a()) {
            shapeDrawable = new ShapeDrawable(new OvalShape());
            ah.m2821k(this, f * 4.0f);
        } else {
            shapeDrawable = new ShapeDrawable(new C0681a(this, this.f1544a));
            ah.m2778a((View) this, 1, shapeDrawable.getPaint());
            shapeDrawable.getPaint().setShadowLayer((float) this.f1544a, (float) i3, (float) i2, 503316480);
            int i4 = this.f1544a;
            setPadding(i4, i4, i4, i4);
        }
        shapeDrawable.getPaint().setColor(i);
        ah.m2781a((View) this, shapeDrawable);
    }

    /* renamed from: a */
    private boolean m3357a() {
        return VERSION.SDK_INT >= 21;
    }

    /* renamed from: a */
    public void m3358a(AnimationListener animationListener) {
        this.f1545b = animationListener;
    }

    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (this.f1545b != null) {
            this.f1545b.onAnimationEnd(getAnimation());
        }
    }

    public void onAnimationStart() {
        super.onAnimationStart();
        if (this.f1545b != null) {
            this.f1545b.onAnimationStart(getAnimation());
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!m3357a()) {
            setMeasuredDimension(getMeasuredWidth() + (this.f1544a * 2), getMeasuredHeight() + (this.f1544a * 2));
        }
    }

    public void setBackgroundColor(int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i);
        }
    }
}
