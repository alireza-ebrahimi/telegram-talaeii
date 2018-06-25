package org.telegram.customization.util.view.sva.anim.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJCircleToLineAlphaController extends JJBaseController {
    private int cr;
    private int cx;
    private int cy;
    private String mColor = "#673AB7";
    private RectF mRectF = new RectF();
    private RectF mRectF2 = new RectF();
    private float sign = 0.707f;
    private float tran = 120.0f;

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.parseColor(this.mColor));
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
        canvas.save();
        if (((double) this.mPro) > 0.7d) {
            paint.setAlpha((int) (this.mPro * 255.0f));
            drawNormalView(paint, canvas);
        }
        canvas.restore();
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        canvas.drawLine((((float) this.cr) * this.sign) + (this.mRectF.left + ((float) this.cr)), (((float) this.cr) * this.sign) + (this.mRectF.top + ((float) this.cr)), (((float) (this.cr * 2)) * this.sign) + (this.mRectF.left + ((float) this.cr)), (((float) (this.cr * 2)) * this.sign) + (this.mRectF.top + ((float) this.cr)), paint);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, paint);
        canvas.drawArc(this.mRectF2, 90.0f, (1.0f - this.mPro) * -360.0f, false, paint);
        if (this.mPro >= 0.7f) {
            canvas.drawLine((this.mRectF2.right - ((float) (this.cr * 3))) * ((1.0f - this.mPro) + 0.7f), this.mRectF2.bottom, this.mRectF2.right - ((float) (this.cr * 3)), this.mRectF2.bottom, paint);
        }
        canvas.restore();
        this.mRectF.left = ((float) (this.cx - this.cr)) + (this.tran * this.mPro);
        this.mRectF.right = ((float) (this.cx + this.cr)) + (this.tran * this.mPro);
        this.mRectF2.left = ((float) (this.cx - (this.cr * 3))) + (this.tran * this.mPro);
        this.mRectF2.right = ((float) (this.cx + (this.cr * 3))) + (this.tran * this.mPro);
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        this.cr = getWidth() / 50;
        this.cx = getWidth() / 2;
        this.cy = getHeight() / 2;
        this.mRectF.left = (float) (this.cx - this.cr);
        this.mRectF.right = (float) (this.cx + this.cr);
        this.mRectF.top = (float) (this.cy - this.cr);
        this.mRectF.bottom = (float) (this.cy + this.cr);
        this.mRectF2.left = (float) (this.cx - (this.cr * 3));
        this.mRectF2.right = (float) (this.cx + (this.cr * 3));
        this.mRectF2.top = (float) (this.cy - (this.cr * 3));
        this.mRectF2.bottom = (float) (this.cy + (this.cr * 3));
        canvas.save();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        paint.setStrokeWidth(4.0f);
        paint.setStyle(Style.STROKE);
        canvas.rotate(45.0f, (float) this.cx, (float) this.cy);
        canvas.drawLine((float) (this.cx + this.cr), (float) this.cy, (float) (this.cx + (this.cr * 2)), (float) this.cy, paint);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, paint);
        canvas.drawArc(this.mRectF2, 0.0f, 360.0f, false, paint);
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
