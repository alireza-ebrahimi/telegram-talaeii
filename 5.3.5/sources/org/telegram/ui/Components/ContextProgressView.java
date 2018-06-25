package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class ContextProgressView extends View {
    private RectF cicleRect = new RectF();
    private int currentColorType;
    private Paint innerPaint = new Paint(1);
    private long lastUpdateTime;
    private Paint outerPaint = new Paint(1);
    private int radOffset = 0;

    public ContextProgressView(Context context, int colorType) {
        super(context);
        this.innerPaint.setStyle(Style.STROKE);
        this.innerPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.outerPaint.setStyle(Style.STROKE);
        this.outerPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.outerPaint.setStrokeCap(Cap.ROUND);
        this.currentColorType = colorType;
        updateColors();
    }

    public void updateColors() {
        if (this.currentColorType == 0) {
            this.innerPaint.setColor(Theme.getColor(Theme.key_contextProgressInner1));
            this.outerPaint.setColor(Theme.getColor(Theme.key_contextProgressOuter1));
        } else if (this.currentColorType == 1) {
            this.innerPaint.setColor(Theme.getColor(Theme.key_contextProgressInner2));
            this.outerPaint.setColor(Theme.getColor(Theme.key_contextProgressOuter2));
        } else if (this.currentColorType == 2) {
            this.innerPaint.setColor(Theme.getColor(Theme.key_contextProgressInner3));
            this.outerPaint.setColor(Theme.getColor(Theme.key_contextProgressOuter3));
        }
        invalidate();
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        if (getVisibility() == 0) {
            long newTime = System.currentTimeMillis();
            long dt = newTime - this.lastUpdateTime;
            this.lastUpdateTime = newTime;
            this.radOffset = (int) (((float) this.radOffset) + (((float) (360 * dt)) / 1000.0f));
            int x = (getMeasuredWidth() / 2) - AndroidUtilities.dp(9.0f);
            int y = (getMeasuredHeight() / 2) - AndroidUtilities.dp(9.0f);
            this.cicleRect.set((float) x, (float) y, (float) (AndroidUtilities.dp(18.0f) + x), (float) (AndroidUtilities.dp(18.0f) + y));
            canvas.drawCircle((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(9.0f), this.innerPaint);
            canvas.drawArc(this.cicleRect, (float) (this.radOffset - 90), 90.0f, false, this.outerPaint);
            invalidate();
        }
    }
}
