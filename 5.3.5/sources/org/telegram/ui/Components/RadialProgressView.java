package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RadialProgressView extends View {
    private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
    private RectF cicleRect = new RectF();
    private float currentCircleLength;
    private float currentProgressTime;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private long lastUpdateTime;
    private int progressColor = Theme.getColor(Theme.key_progressCircle);
    private Paint progressPaint = new Paint(1);
    private float radOffset;
    private boolean risingCircleLength;
    private final float risingTime = 500.0f;
    private final float rotationTime = 2000.0f;
    private int size = AndroidUtilities.dp(40.0f);

    public RadialProgressView(Context context) {
        super(context);
        this.progressPaint.setStyle(Style.STROKE);
        this.progressPaint.setStrokeCap(Cap.ROUND);
        this.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
        this.progressPaint.setColor(this.progressColor);
    }

    private void updateAnimation() {
        long newTime = System.currentTimeMillis();
        long dt = newTime - this.lastUpdateTime;
        if (dt > 17) {
            dt = 17;
        }
        this.lastUpdateTime = newTime;
        this.radOffset += ((float) (360 * dt)) / 2000.0f;
        this.radOffset -= (float) (((int) (this.radOffset / 360.0f)) * 360);
        this.currentProgressTime += (float) dt;
        if (this.currentProgressTime >= 500.0f) {
            this.currentProgressTime = 500.0f;
        }
        if (this.risingCircleLength) {
            this.currentCircleLength = (266.0f * this.accelerateInterpolator.getInterpolation(this.currentProgressTime / 500.0f)) + 4.0f;
        } else {
            this.currentCircleLength = 4.0f - ((1.0f - this.decelerateInterpolator.getInterpolation(this.currentProgressTime / 500.0f)) * 270.0f);
        }
        if (this.currentProgressTime == 500.0f) {
            if (this.risingCircleLength) {
                this.radOffset += 270.0f;
                this.currentCircleLength = -266.0f;
            }
            this.risingCircleLength = !this.risingCircleLength;
            this.currentProgressTime = 0.0f;
        }
        invalidate();
    }

    public void setSize(int value) {
        this.size = value;
        invalidate();
    }

    public void setProgressColor(int color) {
        this.progressColor = color;
        this.progressPaint.setColor(this.progressColor);
    }

    protected void onDraw(Canvas canvas) {
        int x = (getMeasuredWidth() - this.size) / 2;
        int y = (getMeasuredHeight() - this.size) / 2;
        this.cicleRect.set((float) x, (float) y, (float) (this.size + x), (float) (this.size + y));
        canvas.drawArc(this.cicleRect, this.radOffset, this.currentCircleLength, false, this.progressPaint);
        updateAnimation();
    }
}
