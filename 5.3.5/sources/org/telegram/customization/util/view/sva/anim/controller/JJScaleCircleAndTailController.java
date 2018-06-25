package org.telegram.customization.util.view.sva.anim.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJScaleCircleAndTailController extends JJBaseController {
    private int cr;
    private int cx;
    private int cy;
    private String mColor = "#2196F3";
    private int scr;

    public void draw(Canvas canvas, Paint paint) {
        switch (this.mState) {
            case 0:
                drawNormalView(paint, canvas);
                return;
            case 1:
                drawStartAnimView(paint, canvas);
                return;
            case 2:
                drawStopAnimView(paint, canvas);
                return;
            default:
                return;
        }
    }

    private void drawStopAnimView(Paint paint, Canvas canvas) {
        drawNormalView(paint, canvas);
        drawSearchView(paint, canvas, false);
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        drawNormalView(paint, canvas);
        drawSearchView(paint, canvas, true);
    }

    private void drawSearchView(Paint paint, Canvas canvas, boolean start) {
        drawNormalView(paint, canvas);
        paint.setStyle(Style.FILL);
        canvas.drawCircle((float) this.cx, (float) this.cy, (start ? this.mPro : 1.0f - this.mPro) * ((float) (this.cr - 25)), paint);
        canvas.rotate(130.0f, (float) this.cx, (float) this.cy);
        canvas.save();
        paint.setColor(-1);
        paint.setStrokeWidth(25.0f);
        canvas.drawLine(20.0f + (((start ? this.mPro : 1.0f - this.mPro) * ((float) (this.cr / 2))) + ((float) (this.cx + (this.cr / 2)))), (float) this.cy, ((float) (this.cx + this.cr)) + ((start ? this.mPro : 1.0f - this.mPro) * ((float) (getWidth() / 5))), (float) this.cy, paint);
        canvas.restore();
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        this.cr = getWidth() / 6;
        this.cx = getWidth() / 2;
        this.cy = getHeight() / 2;
        this.scr = getWidth() / 18;
        canvas.drawColor(Color.parseColor(this.mColor));
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        canvas.drawCircle((float) this.cx, (float) this.cy, (float) this.cr, paint);
        canvas.save();
        paint.setColor(Color.parseColor(this.mColor));
        paint.setStrokeWidth(10.0f);
        paint.setStyle(Style.STROKE);
        canvas.rotate(130.0f, (float) this.cx, (float) this.cy);
        canvas.drawLine((float) ((this.cx + this.scr) + 10), (float) this.cy, (float) (this.cx + (this.scr * 2)), (float) this.cy, paint);
        canvas.drawCircle((float) this.cx, (float) this.cy, (float) this.scr, paint);
        canvas.restore();
    }

    public void startAnim() {
        if (this.mState != 1) {
            this.mState = 1;
            startSearchViewAnim();
        }
    }

    public void resetAnim() {
        if (this.mState != 2) {
            this.mState = 2;
            startSearchViewAnim();
        }
    }
}
