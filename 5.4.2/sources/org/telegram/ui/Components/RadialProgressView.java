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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
        long j = 17;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        if (j2 <= 17) {
            j = j2;
        }
        this.lastUpdateTime = currentTimeMillis;
        this.radOffset += ((float) (360 * j)) / 2000.0f;
        this.radOffset -= (float) (((int) (this.radOffset / 360.0f)) * 360);
        this.currentProgressTime = ((float) j) + this.currentProgressTime;
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
            this.currentProgressTime = BitmapDescriptorFactory.HUE_RED;
        }
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int measuredWidth = (getMeasuredWidth() - this.size) / 2;
        int measuredHeight = (getMeasuredHeight() - this.size) / 2;
        this.cicleRect.set((float) measuredWidth, (float) measuredHeight, (float) (measuredWidth + this.size), (float) (measuredHeight + this.size));
        canvas.drawArc(this.cicleRect, this.radOffset, this.currentCircleLength, false, this.progressPaint);
        updateAnimation();
    }

    public void setProgressColor(int i) {
        this.progressColor = i;
        this.progressPaint.setColor(this.progressColor);
    }

    public void setSize(int i) {
        this.size = i;
        invalidate();
    }
}
