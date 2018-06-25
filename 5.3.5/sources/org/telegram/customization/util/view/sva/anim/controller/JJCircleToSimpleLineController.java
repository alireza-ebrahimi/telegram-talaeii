package org.telegram.customization.util.view.sva.anim.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJCircleToSimpleLineController extends JJBaseController {
    private int cr;
    private int cx;
    private int cy;
    /* renamed from: j */
    private int f63j = 10;
    private String mColor = "#4CAF50";
    private RectF mRectF = new RectF();

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
        float f = 0.2f;
        canvas.save();
        float f2 = (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j);
        if (this.mPro >= 0.2f) {
            f = this.mPro;
        }
        canvas.drawLine(f2 * f, (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), paint);
        if (this.mPro > 0.5f) {
            canvas.drawArc(this.mRectF, 45.0f, -(((this.mPro - 0.5f) * 360.0f) * 2.0f), false, paint);
            canvas.drawLine(this.mRectF.right - ((float) this.f63j), this.mRectF.bottom - ((float) this.f63j), (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), paint);
        } else {
            canvas.drawLine((((float) this.cr) * (1.0f - this.mPro)) + (this.mRectF.right - ((float) this.f63j)), (((float) this.cr) * (1.0f - this.mPro)) + (this.mRectF.bottom - ((float) this.f63j)), (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), paint);
        }
        canvas.restore();
        this.mRectF.left = ((float) (this.cx - this.cr)) + ((1.0f - this.mPro) * 240.0f);
        this.mRectF.right = ((float) (this.cx + this.cr)) + ((1.0f - this.mPro) * 240.0f);
        this.mRectF.top = (float) (this.cy - this.cr);
        this.mRectF.bottom = (float) (this.cy + this.cr);
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        if (this.mPro <= 0.5f) {
            canvas.drawArc(this.mRectF, 45.0f, -(360.0f - ((this.mPro == -1.0f ? 1.0f : this.mPro) * 720.0f)), false, paint);
            canvas.drawLine(this.mRectF.right - ((float) this.f63j), this.mRectF.bottom - ((float) this.f63j), (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), paint);
        } else {
            canvas.drawLine((((float) this.cr) * this.mPro) + (this.mRectF.right - ((float) this.f63j)), (((float) this.cr) * this.mPro) + (this.mRectF.bottom - ((float) this.f63j)), (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), paint);
        }
        canvas.drawLine((1.0f - (this.mPro * 0.8f)) * ((this.mRectF.right + ((float) this.cr)) - ((float) this.f63j)), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.right + ((float) this.cr)) - ((float) this.f63j), (this.mRectF.bottom + ((float) this.cr)) - ((float) this.f63j), paint);
        canvas.restore();
        this.mRectF.left = ((float) (this.cx - this.cr)) + (this.mPro * 240.0f);
        this.mRectF.right = ((float) (this.cx + this.cr)) + (this.mPro * 240.0f);
        this.mRectF.top = (float) (this.cy - this.cr);
        this.mRectF.bottom = (float) (this.cy + this.cr);
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        this.cr = getWidth() / 24;
        this.cx = getWidth() / 2;
        this.cy = getHeight() / 2;
        this.mRectF.left = (float) (this.cx - this.cr);
        this.mRectF.right = (float) (this.cx + this.cr);
        this.mRectF.top = (float) (this.cy - this.cr);
        this.mRectF.bottom = (float) (this.cy + this.cr);
        canvas.save();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        paint.setStrokeWidth(4.0f);
        paint.setStyle(Style.STROKE);
        canvas.rotate(45.0f, (float) this.cx, (float) this.cy);
        canvas.drawLine((float) (this.cx + this.cr), (float) this.cy, (float) (this.cx + (this.cr * 2)), (float) this.cy, paint);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, paint);
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
