package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class CloseProgressDrawable2 extends Drawable {
    private float angle;
    private boolean animating;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private long lastFrameTime;
    private Paint paint = new Paint(1);
    private RectF rect = new RectF();

    public CloseProgressDrawable2() {
        this.paint.setColor(-1);
        this.paint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.paint.setStrokeCap(Cap.ROUND);
        this.paint.setStyle(Style.STROKE);
    }

    public void draw(Canvas canvas) {
        float f;
        float f2;
        float f3;
        float f4;
        long currentTimeMillis = System.currentTimeMillis();
        if (this.lastFrameTime != 0) {
            long j = currentTimeMillis - this.lastFrameTime;
            if (this.animating || this.angle != BitmapDescriptorFactory.HUE_RED) {
                this.angle = (((float) (j * 360)) / 500.0f) + this.angle;
                if (this.animating || this.angle < 720.0f) {
                    this.angle -= (float) (((int) (this.angle / 720.0f)) * 720);
                } else {
                    this.angle = BitmapDescriptorFactory.HUE_RED;
                }
                invalidateSelf();
            }
        }
        canvas.save();
        canvas.translate((float) (getIntrinsicWidth() / 2), (float) (getIntrinsicHeight() / 2));
        canvas.rotate(-45.0f);
        if (this.angle >= BitmapDescriptorFactory.HUE_RED && this.angle < 90.0f) {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = 1.0f;
            f3 = 1.0f;
            f4 = 1.0f - (this.angle / 90.0f);
        } else if (this.angle >= 90.0f && this.angle < 180.0f) {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = 1.0f;
            f3 = 1.0f - ((this.angle - 90.0f) / 90.0f);
            f4 = BitmapDescriptorFactory.HUE_RED;
        } else if (this.angle >= 180.0f && this.angle < 270.0f) {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = 1.0f - ((this.angle - 180.0f) / 90.0f);
            f3 = BitmapDescriptorFactory.HUE_RED;
            f4 = BitmapDescriptorFactory.HUE_RED;
        } else if (this.angle >= 270.0f && this.angle < 360.0f) {
            f = (this.angle - 270.0f) / 90.0f;
            f2 = BitmapDescriptorFactory.HUE_RED;
            f3 = BitmapDescriptorFactory.HUE_RED;
            f4 = BitmapDescriptorFactory.HUE_RED;
        } else if (this.angle >= 360.0f && this.angle < 450.0f) {
            f = 1.0f - ((this.angle - 360.0f) / 90.0f);
            f2 = BitmapDescriptorFactory.HUE_RED;
            f3 = BitmapDescriptorFactory.HUE_RED;
            f4 = BitmapDescriptorFactory.HUE_RED;
        } else if (this.angle >= 450.0f && this.angle < 540.0f) {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = BitmapDescriptorFactory.HUE_RED;
            f3 = BitmapDescriptorFactory.HUE_RED;
            f4 = (this.angle - 450.0f) / 90.0f;
        } else if (this.angle >= 540.0f && this.angle < 630.0f) {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = BitmapDescriptorFactory.HUE_RED;
            f3 = (this.angle - 540.0f) / 90.0f;
            f4 = 1.0f;
        } else if (this.angle < 630.0f || this.angle >= 720.0f) {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = 1.0f;
            f3 = 1.0f;
            f4 = 1.0f;
        } else {
            f = BitmapDescriptorFactory.HUE_RED;
            f2 = (this.angle - 630.0f) / 90.0f;
            f3 = 1.0f;
            f4 = 1.0f;
        }
        if (f4 != BitmapDescriptorFactory.HUE_RED) {
            canvas.drawLine(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, ((float) AndroidUtilities.dp(8.0f)) * f4, this.paint);
        }
        if (f3 != BitmapDescriptorFactory.HUE_RED) {
            canvas.drawLine(((float) (-AndroidUtilities.dp(8.0f))) * f3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.paint);
        }
        if (f2 != BitmapDescriptorFactory.HUE_RED) {
            canvas.drawLine(BitmapDescriptorFactory.HUE_RED, ((float) (-AndroidUtilities.dp(8.0f))) * f2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.paint);
        }
        if (f != 1.0f) {
            canvas.drawLine(((float) AndroidUtilities.dp(8.0f)) * f, BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(8.0f), BitmapDescriptorFactory.HUE_RED, this.paint);
        }
        canvas.restore();
        int centerX = getBounds().centerX();
        int centerY = getBounds().centerY();
        this.rect.set((float) (centerX - AndroidUtilities.dp(8.0f)), (float) (centerY - AndroidUtilities.dp(8.0f)), (float) (centerX + AndroidUtilities.dp(8.0f)), (float) (centerY + AndroidUtilities.dp(8.0f)));
        canvas.drawArc(this.rect, (this.angle < 360.0f ? BitmapDescriptorFactory.HUE_RED : this.angle - 360.0f) - 45.0f, this.angle < 360.0f ? this.angle : 720.0f - this.angle, false, this.paint);
        this.lastFrameTime = currentTimeMillis;
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(24.0f);
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(24.0f);
    }

    public int getOpacity() {
        return -2;
    }

    public void setAlpha(int i) {
    }

    public void setColor(int i) {
        this.paint.setColor(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    public void startAnimation() {
        this.animating = true;
        this.lastFrameTime = System.currentTimeMillis();
        invalidateSelf();
    }

    public void stopAnimation() {
        this.animating = false;
    }
}
