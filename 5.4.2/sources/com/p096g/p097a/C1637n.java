package com.p096g.p097a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.widget.ImageView;
import com.p096g.p097a.C1636m.C1632b;

/* renamed from: com.g.a.n */
final class C1637n extends BitmapDrawable {
    /* renamed from: e */
    private static final Paint f5001e = new Paint();
    /* renamed from: a */
    Drawable f5002a;
    /* renamed from: b */
    long f5003b;
    /* renamed from: c */
    boolean f5004c;
    /* renamed from: d */
    int f5005d = 255;
    /* renamed from: f */
    private final boolean f5006f;
    /* renamed from: g */
    private final float f5007g;
    /* renamed from: h */
    private final C1632b f5008h;

    C1637n(Context context, Bitmap bitmap, Drawable drawable, C1632b c1632b, boolean z, boolean z2) {
        super(context.getResources(), bitmap);
        this.f5006f = z2;
        this.f5007g = context.getResources().getDisplayMetrics().density;
        this.f5008h = c1632b;
        boolean z3 = (c1632b == C1632b.MEMORY || z) ? false : true;
        if (z3) {
            this.f5002a = drawable;
            this.f5004c = true;
            this.f5003b = SystemClock.uptimeMillis();
        }
    }

    /* renamed from: a */
    private static Path m8020a(Point point, int i) {
        Point point2 = new Point(point.x + i, point.y);
        Point point3 = new Point(point.x, point.y + i);
        Path path = new Path();
        path.moveTo((float) point.x, (float) point.y);
        path.lineTo((float) point2.x, (float) point2.y);
        path.lineTo((float) point3.x, (float) point3.y);
        return path;
    }

    /* renamed from: a */
    private void m8021a(Canvas canvas) {
        f5001e.setColor(-1);
        canvas.drawPath(C1637n.m8020a(new Point(0, 0), (int) (16.0f * this.f5007g)), f5001e);
        f5001e.setColor(this.f5008h.f4982d);
        canvas.drawPath(C1637n.m8020a(new Point(0, 0), (int) (15.0f * this.f5007g)), f5001e);
    }

    /* renamed from: a */
    static void m8022a(ImageView imageView, Context context, Bitmap bitmap, C1632b c1632b, boolean z, boolean z2) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }
        imageView.setImageDrawable(new C1637n(context, bitmap, drawable, c1632b, z, z2));
    }

    /* renamed from: a */
    static void m8023a(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }
    }

    public void draw(Canvas canvas) {
        if (this.f5004c) {
            float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.f5003b)) / 200.0f;
            if (uptimeMillis >= 1.0f) {
                this.f5004c = false;
                this.f5002a = null;
                super.draw(canvas);
            } else {
                if (this.f5002a != null) {
                    this.f5002a.draw(canvas);
                }
                super.setAlpha((int) (uptimeMillis * ((float) this.f5005d)));
                super.draw(canvas);
                super.setAlpha(this.f5005d);
                if (VERSION.SDK_INT <= 10) {
                    invalidateSelf();
                }
            }
        } else {
            super.draw(canvas);
        }
        if (this.f5006f) {
            m8021a(canvas);
        }
    }

    protected void onBoundsChange(Rect rect) {
        if (this.f5002a != null) {
            this.f5002a.setBounds(rect);
        }
        super.onBoundsChange(rect);
    }

    public void setAlpha(int i) {
        this.f5005d = i;
        if (this.f5002a != null) {
            this.f5002a.setAlpha(i);
        }
        super.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.f5002a != null) {
            this.f5002a.setColorFilter(colorFilter);
        }
        super.setColorFilter(colorFilter);
    }
}
