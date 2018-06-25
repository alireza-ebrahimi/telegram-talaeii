package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;

public class CloseProgressDrawable extends Drawable {
    private int currentAnimationTime;
    private int currentSegment;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private long lastFrameTime;
    private Paint paint = new Paint(1);

    public CloseProgressDrawable() {
        this.paint.setColor(-9079435);
        this.paint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.paint.setStrokeCap(Cap.ROUND);
    }

    public void draw(Canvas canvas) {
        long newTime = System.currentTimeMillis();
        if (this.lastFrameTime != 0) {
            this.currentAnimationTime = (int) (((long) this.currentAnimationTime) + (newTime - this.lastFrameTime));
            if (this.currentAnimationTime > 200) {
                this.currentAnimationTime = 0;
                this.currentSegment++;
                if (this.currentSegment == 4) {
                    this.currentSegment -= 4;
                }
            }
        }
        canvas.save();
        canvas.translate((float) (getIntrinsicWidth() / 2), (float) (getIntrinsicHeight() / 2));
        canvas.rotate(45.0f);
        this.paint.setAlpha(255 - ((this.currentSegment % 4) * 40));
        canvas.drawLine((float) (-AndroidUtilities.dp(8.0f)), 0.0f, 0.0f, 0.0f, this.paint);
        this.paint.setAlpha(255 - (((this.currentSegment + 1) % 4) * 40));
        canvas.drawLine(0.0f, (float) (-AndroidUtilities.dp(8.0f)), 0.0f, 0.0f, this.paint);
        this.paint.setAlpha(255 - (((this.currentSegment + 2) % 4) * 40));
        canvas.drawLine(0.0f, 0.0f, (float) AndroidUtilities.dp(8.0f), 0.0f, this.paint);
        this.paint.setAlpha(255 - (((this.currentSegment + 3) % 4) * 40));
        canvas.drawLine(0.0f, 0.0f, 0.0f, (float) AndroidUtilities.dp(8.0f), this.paint);
        canvas.restore();
        this.lastFrameTime = newTime;
        invalidateSelf();
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -2;
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(24.0f);
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(24.0f);
    }
}
