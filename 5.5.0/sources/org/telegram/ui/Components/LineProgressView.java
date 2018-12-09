package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class LineProgressView extends View {
    private static DecelerateInterpolator decelerateInterpolator = null;
    private static Paint progressPaint = null;
    private float animatedAlphaValue = 1.0f;
    private float animatedProgressValue = BitmapDescriptorFactory.HUE_RED;
    private float animationProgressStart = BitmapDescriptorFactory.HUE_RED;
    private int backColor;
    private float currentProgress = BitmapDescriptorFactory.HUE_RED;
    private long currentProgressTime = 0;
    private long lastUpdateTime = 0;
    private int progressColor;

    public LineProgressView(Context context) {
        super(context);
        if (decelerateInterpolator == null) {
            decelerateInterpolator = new DecelerateInterpolator();
            progressPaint = new Paint(1);
            progressPaint.setStrokeCap(Cap.ROUND);
            progressPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        }
    }

    private void updateAnimation() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (!(this.animatedProgressValue == 1.0f || this.animatedProgressValue == this.currentProgress)) {
            float f = this.currentProgress - this.animationProgressStart;
            if (f > BitmapDescriptorFactory.HUE_RED) {
                this.currentProgressTime += j;
                if (this.currentProgressTime >= 300) {
                    this.animatedProgressValue = this.currentProgress;
                    this.animationProgressStart = this.currentProgress;
                    this.currentProgressTime = 0;
                } else {
                    this.animatedProgressValue = (f * decelerateInterpolator.getInterpolation(((float) this.currentProgressTime) / 300.0f)) + this.animationProgressStart;
                }
            }
            invalidate();
        }
        if (this.animatedProgressValue >= 1.0f && this.animatedProgressValue == 1.0f && this.animatedAlphaValue != BitmapDescriptorFactory.HUE_RED) {
            this.animatedAlphaValue -= ((float) j) / 200.0f;
            if (this.animatedAlphaValue <= BitmapDescriptorFactory.HUE_RED) {
                this.animatedAlphaValue = BitmapDescriptorFactory.HUE_RED;
            }
            invalidate();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!(this.backColor == 0 || this.animatedProgressValue == 1.0f)) {
            progressPaint.setColor(this.backColor);
            progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f));
            canvas.drawRect((float) ((int) (((float) getWidth()) * this.animatedProgressValue)), BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight(), progressPaint);
        }
        progressPaint.setColor(this.progressColor);
        progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f));
        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, ((float) getWidth()) * this.animatedProgressValue, (float) getHeight(), progressPaint);
        updateAnimation();
    }

    public void setBackColor(int i) {
        this.backColor = i;
    }

    public void setProgress(float f, boolean z) {
        if (z) {
            this.animationProgressStart = this.animatedProgressValue;
        } else {
            this.animatedProgressValue = f;
            this.animationProgressStart = f;
        }
        if (f != 1.0f) {
            this.animatedAlphaValue = 1.0f;
        }
        this.currentProgress = f;
        this.currentProgressTime = 0;
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    public void setProgressColor(int i) {
        this.progressColor = i;
    }
}
