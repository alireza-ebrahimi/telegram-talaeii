package org.telegram.customization.util.view.sva.anim.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJBarWithErrorIconController extends JJBaseController {
    private float cr;
    private float cx;
    private float cy;
    private float mCirCleDis = 200.0f;
    private float mCircleBig = 10.0f;
    private String mColor = "#E91E63";
    private Paint mFontPaint = new Paint(1);
    private RectF mRectF = new RectF();
    private RectF mRectF2 = new RectF();
    private float sign = 0.707f;

    public JJBarWithErrorIconController() {
        this.mFontPaint.setStrokeWidth(1.0f);
        this.mFontPaint.setColor(-1);
        this.mFontPaint.setStyle(Style.FILL);
        this.mFontPaint.setTextSize(40.0f);
    }

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
        if (((double) this.mPro) <= 0.25d) {
            canvas.drawArc(this.mRectF, 90.0f, -180.0f, false, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.top, this.mRectF.right - this.cr, this.mRectF.top, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.bottom, this.mRectF.right - this.cr, this.mRectF.bottom, paint);
            canvas.drawArc(this.mRectF2, 90.0f, 180.0f, false, paint);
            canvas.drawLine((this.mRectF.left + this.cr) - ((this.sign * this.cr) / 2.0f), ((this.cr * this.sign) / 2.0f) + this.cy, ((this.sign * this.cr) / 2.0f) + (this.mRectF.left + this.cr), this.cy - ((this.cr * this.sign) / 2.0f), paint);
        } else if (((double) this.mPro) > 0.25d && this.mPro <= 0.5f) {
            canvas.drawArc(this.mRectF, 90.0f, -180.0f, false, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.top, this.mRectF.right - this.cr, this.mRectF.top, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.bottom, this.mRectF.right - this.cr, this.mRectF.bottom, paint);
            canvas.drawArc(this.mRectF2, 90.0f, 180.0f, false, paint);
            canvas.drawArc(this.mRectF2, 90.0f, 180.0f, false, paint);
        } else if (this.mPro <= 0.5f || this.mPro > 0.75f) {
            canvas.drawLine((this.cr * this.sign) + this.cx, (this.cr * this.sign) + this.cy, (((this.cr * this.sign) * (this.mPro - 0.75f)) * 4.0f) + (this.cx + (this.cr * this.sign)), (((this.cr * this.sign) * (this.mPro - 0.75f)) * 4.0f) + (this.cy + (this.cr * this.sign)), paint);
            canvas.drawCircle(this.cx, this.cy, this.cr, paint);
        } else {
            canvas.drawCircle(this.cx, this.cy, this.cr, paint);
        }
        canvas.restore();
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        if (((double) this.mPro) <= 0.25d) {
            canvas.drawLine((this.cr * this.sign) + this.cx, (this.cr * this.sign) + this.cy, ((this.cr * this.sign) * (1.0f - (this.mPro * 4.0f))) + (this.cx + (this.cr * this.sign)), ((this.cr * this.sign) * (1.0f - (this.mPro * 4.0f))) + (this.cy + (this.cr * this.sign)), paint);
            canvas.drawCircle(this.cx, this.cy, this.cr, paint);
        } else if (((double) this.mPro) > 0.25d && this.mPro <= 0.5f) {
            this.mRectF.left = (this.cx - this.cr) + ((this.mCirCleDis * (this.mPro - 0.25f)) * 4.0f);
            this.mRectF.right = (this.cx + this.cr) + ((this.mCirCleDis * (this.mPro - 0.25f)) * 4.0f);
            this.mRectF2.left = (this.cx - this.cr) - ((this.mCirCleDis * (this.mPro - 0.25f)) * 4.0f);
            this.mRectF2.right = (this.cx + this.cr) - ((this.mCirCleDis * (this.mPro - 0.25f)) * 4.0f);
            canvas.drawArc(this.mRectF, 90.0f, -180.0f, false, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.top, this.mRectF.right - this.cr, this.mRectF.top, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.bottom, this.mRectF.right - this.cr, this.mRectF.bottom, paint);
            canvas.drawArc(this.mRectF2, 90.0f, 180.0f, false, paint);
        } else if (this.mPro <= 0.5f || this.mPro > 0.75f) {
            canvas.drawArc(this.mRectF, 90.0f, -180.0f, false, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.top, this.mRectF.right - this.cr, this.mRectF.top, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.bottom, this.mRectF.right - this.cr, this.mRectF.bottom, paint);
            canvas.drawArc(this.mRectF2, 90.0f, 180.0f, false, paint);
            canvas.drawLine((this.mRectF.left + this.cr) - ((this.sign * this.cr) / 2.0f), ((this.cr * this.sign) / 2.0f) + this.cy, ((this.sign * this.cr) / 2.0f) + (this.mRectF.left + this.cr), this.cy - ((this.cr * this.sign) / 2.0f), paint);
            canvas.drawLine((this.mRectF.left + this.cr) - ((((this.sign * this.cr) / 2.0f) * (this.mPro - 0.75f)) * 4.0f), this.cy - ((((this.cr * this.sign) / 2.0f) * (this.mPro - 0.75f)) * 4.0f), (((this.sign * this.cr) / 2.0f) * (1.0f - ((this.mPro - 0.75f) * 4.0f))) + ((this.mRectF.left + this.cr) + ((this.sign * this.cr) / 2.0f)), (((this.sign * this.cr) / 2.0f) * (1.0f - ((this.mPro - 0.75f) * 4.0f))) + (this.cy + ((this.cr * this.sign) / 2.0f)), paint);
        } else {
            canvas.drawArc(this.mRectF, 90.0f, -180.0f, false, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.top, this.mRectF.right - this.cr, this.mRectF.top, paint);
            canvas.drawLine(this.mRectF2.left + this.cr, this.mRectF.bottom, this.mRectF.right - this.cr, this.mRectF.bottom, paint);
            canvas.drawArc(this.mRectF2, 90.0f, 180.0f, false, paint);
            canvas.drawLine((this.mRectF.left + this.cr) - ((((this.sign * this.cr) / 2.0f) * (this.mPro - 0.5f)) * 4.0f), ((((this.cr * this.sign) / 2.0f) * (this.mPro - 0.5f)) * 4.0f) + this.cy, (((this.sign * this.cr) / 2.0f) * (1.0f - ((this.mPro - 0.5f) * 4.0f))) + ((this.mRectF.left + this.cr) + ((this.sign * this.cr) / 2.0f)), (this.cy - ((this.cr * this.sign) / 2.0f)) - (((this.sign * this.cr) / 2.0f) * (1.0f - ((this.mPro - 0.5f) * 4.0f))), paint);
        }
        canvas.restore();
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        this.cr = (float) (getWidth() / 15);
        this.cx = (float) (getWidth() / 2);
        this.cy = (float) (getHeight() / 2);
        this.mRectF.top = this.cy - this.cr;
        this.mRectF.bottom = this.cy + this.cr;
        this.mRectF2.top = this.cy - this.cr;
        this.mRectF2.bottom = this.cy + this.cr;
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        canvas.save();
        paint.setColor(-1);
        paint.setStrokeWidth(8.0f);
        paint.setStyle(Style.STROKE);
        canvas.drawCircle(this.cx, this.cy, this.cr, paint);
        canvas.drawLine((this.cr * this.sign) + this.cx, (this.cr * this.sign) + this.cy, ((this.cr * 2.0f) * this.sign) + this.cx, ((this.cr * 2.0f) * this.sign) + this.cy, paint);
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
