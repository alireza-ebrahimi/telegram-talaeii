package org.telegram.customization.util.view.sva.anim.controller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJChangeArrowController extends JJBaseController {
    private float cr;
    private float cx;
    private float cy;
    private RectF mOutRectF = new RectF();
    private RectF mRectF = new RectF();
    private float scr;
    private float scx;
    private float scy;
    private float sign = 0.707f;

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(this.color);
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
        if (((double) this.mPro) <= 0.25d) {
            canvas.drawLine(this.cx - this.cr, this.scy - this.scr, this.scx, this.scy - this.scr, paint);
            canvas.drawLine(this.cx - this.cr, this.scy - this.scr, (this.scr * (0.25f - this.mPro)) + (this.cx - this.cr), (this.scy - this.scr) - (this.scr * (0.25f - this.mPro)), paint);
            canvas.drawLine(this.cx - this.cr, this.scy - this.scr, (this.scr * (0.25f - this.mPro)) + (this.cx - this.cr), (this.scr * (0.25f - this.mPro)) + (this.scy - this.scr), paint);
        } else if (((double) this.mPro) <= 0.25d || this.mPro > 0.5f) {
            canvas.drawLine(((this.cr * this.sign) * (1.0f - ((this.mPro - 0.5f) * 2.0f))) + (this.cx + (this.cr * this.sign)), ((this.cr * this.sign) * (1.0f - ((this.mPro - 0.5f) * 2.0f))) + (this.cy + (this.cr * this.sign)), ((this.cr * 2.0f) * this.sign) + this.cx, ((this.cr * 2.0f) * this.sign) + this.cy, paint);
            canvas.drawArc(this.mOutRectF, 45.0f, (this.mPro - 0.5f) * 720.0f, false, paint);
        } else {
            canvas.drawArc(this.mRectF, -90.0f, 4.0f * (180.0f * (this.mPro - 0.25f)), false, paint);
            canvas.drawLine(((((this.scx - this.cx) + this.cr) * (this.mPro - 0.25f)) * 4.0f) + (this.cx - this.cr), this.scy - this.scr, this.scx, this.scy - this.scr, paint);
        }
        canvas.restore();
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        if (((double) this.mPro) <= 0.75d) {
            canvas.drawArc(this.mOutRectF, 45.0f, (1.0f - (this.mPro / 0.75f)) * 360.0f, false, paint);
        }
        if (((double) this.mPro) <= 0.25d) {
            canvas.drawLine((this.cx + (this.cr * this.sign)) + (((this.cr * this.sign) * this.mPro) * 4.0f), (this.cy + (this.cr * this.sign)) + (((this.cr * this.sign) * this.mPro) * 4.0f), this.cx + ((this.cr * 2.0f) * this.sign), this.cy + ((this.cr * 2.0f) * this.sign), paint);
            canvas.drawArc(this.mRectF, 90.0f, (-180.0f * this.mPro) * 4.0f, false, paint);
        } else if (((double) this.mPro) > 0.25d && this.mPro <= 0.5f) {
            canvas.drawArc(this.mRectF, -90.0f, (1.0f - ((this.mPro - 0.25f) * 4.0f)) * 180.0f, false, paint);
            canvas.drawLine(this.cx - ((this.cr * (this.mPro - 0.25f)) * 4.0f), this.scy - this.scr, this.scx, this.scy - this.scr, paint);
        } else if (this.mPro <= 0.5f || this.mPro >= 0.75f) {
            canvas.drawLine(this.cx - this.cr, this.scy - this.scr, this.scx - 20.0f, this.scy - this.scr, paint);
            canvas.drawLine(this.cx - this.cr, this.scy - this.scr, (this.scr * this.mPro) + (this.cx - this.cr), (this.scy - this.scr) - (this.scr * this.mPro), paint);
            canvas.drawLine(this.cx - this.cr, this.scy - this.scr, (this.scr * this.mPro) + (this.cx - this.cr), (this.scr * this.mPro) + (this.scy - this.scr), paint);
        } else {
            canvas.drawLine(this.cx - ((this.cr * (this.mPro - 0.5f)) * 4.0f), this.scy - this.scr, this.scx - 20.0f, this.scy - this.scr, paint);
        }
        canvas.restore();
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        this.cr = (float) (getWidth() / 10);
        this.scr = (float) (getWidth() / 15);
        this.cr *= this.scale;
        this.scr *= this.scale;
        this.cx = (float) (getWidth() / 2);
        this.cy = (float) (getHeight() / 2);
        this.scx = this.cx + ((this.cr * 2.0f) * this.sign);
        this.scy = this.cy + (((this.cr * 2.0f) * this.sign) - this.scr);
        this.mRectF.left = this.scx - this.scr;
        this.mRectF.right = this.scx + this.scr;
        this.mRectF.top = this.scy - this.scr;
        this.mRectF.bottom = this.scy + this.scr;
        this.mOutRectF.left = this.cx - this.cr;
        this.mOutRectF.right = this.cx + this.cr;
        this.mOutRectF.top = this.cy - this.cr;
        this.mOutRectF.bottom = this.cy + this.cr;
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        canvas.save();
        paint.setColor(this.color);
        paint.setStrokeWidth(this.size);
        paint.setStyle(Style.STROKE);
        canvas.drawCircle(this.cx, this.cy, this.cr, paint);
        canvas.drawLine((this.cr * this.sign) + this.cx, (this.cr * this.sign) + this.cy, this.scx, ((this.cr * 2.0f) * this.sign) + this.cy, paint);
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
