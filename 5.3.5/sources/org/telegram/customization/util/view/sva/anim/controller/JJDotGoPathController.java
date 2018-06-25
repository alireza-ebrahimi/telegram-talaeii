package org.telegram.customization.util.view.sva.anim.controller;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJDotGoPathController extends JJBaseController {
    private float cr;
    private float cx;
    private float cy;
    private boolean isDrawDot = true;
    private Paint mArcPaint = new Paint(1);
    private Path mArcPath = new Path();
    private float mArcTemp = 1.0f;
    private float mCircleScaleIn = 20.0f;
    private String mColor = "#4CAF50";
    private RectF mIntRectF = new RectF();
    private RectF mOutRectF = new RectF();
    private float mPadding = 25.0f;
    private Path mPath = new Path();
    private PathMeasure mPathMeasure;
    private float sign = 0.707f;

    public JJDotGoPathController() {
        this.mArcPaint.setStyle(Style.STROKE);
        this.mArcPaint.setStrokeWidth(10.0f);
        this.mArcPaint.setColor(-1);
        this.mPathMeasure = new PathMeasure(this.mPath, false);
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
        drawNormalView(paint, canvas);
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        if (((double) this.mPro) <= 0.2d) {
            canvas.drawCircle(this.cx, this.cy, this.cr - (this.mCircleScaleIn * this.mPro), paint);
            canvas.drawLine((((this.cr * this.sign) * this.mPro) * 5.0f) + (this.cx + (this.cr * this.sign)), (((this.cr * this.sign) * this.mPro) * 5.0f) + (this.cy + (this.cr * this.sign)), ((this.cr * 2.0f) * this.sign) + this.cx, ((this.cr * 2.0f) * this.sign) + this.cy, paint);
        } else if (((double) this.mPro) <= 0.2d || this.mPro >= this.mPathMeasure.getLength() - 20.0f) {
            canvas.drawCircle(this.cx, this.cy, this.cr, paint);
            canvas.drawLine((this.cr * this.sign) + this.cx, (this.cr * this.sign) + this.cy, ((this.cr * 2.0f) * this.sign) + this.cx, ((this.cr * 2.0f) * this.sign) + this.cy, paint);
        } else {
            canvas.drawCircle(this.cx, this.cy, this.cr, paint);
            if (this.mPos[0] < this.cx - 0.5f || this.mPos[0] > this.cx + 0.5f || this.mPos[1] != this.cy) {
                this.isDrawDot = true;
                canvas.drawCircle(this.mPos[0], this.mPos[1], 2.0f, paint);
            } else {
                if (this.isDrawDot) {
                    canvas.drawCircle(this.mPos[0], this.mPos[1], 2.0f, paint);
                    this.isDrawDot = false;
                } else {
                    this.isDrawDot = true;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (this.mPos[0] <= this.cx + (this.cr * this.sign)) {
                this.mArcPath.reset();
                this.mArcPath.moveTo(this.cx + this.cr, this.cy);
                this.mArcTemp += 2.0f;
                if (this.mArcTemp <= 20.0f) {
                    this.mArcPath.quadTo((this.cx + (this.cr * this.sign)) + this.mArcTemp, this.cy + (this.cr * this.sign), this.cx, this.cy + this.cr);
                }
                canvas.drawPath(this.mArcPath, this.mArcPaint);
            }
        }
        canvas.restore();
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        int i;
        this.cr = (float) (getWidth() / 10);
        this.cx = (float) (getWidth() / 2);
        this.cy = (float) (getHeight() / 2);
        this.mIntRectF.left = this.cx - this.cr;
        this.mIntRectF.right = this.cx + this.cr;
        this.mIntRectF.top = this.cy - this.cr;
        this.mIntRectF.bottom = this.cy + this.cr;
        this.mPath.moveTo(this.cx + ((this.cr * 2.0f) * this.sign), this.cy + ((this.cr * 2.0f) * this.sign));
        for (i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                this.mPath.lineTo(this.cx + 0.5f, this.cy);
            } else {
                this.mPath.lineTo(this.cx - 0.5f, this.cy);
            }
        }
        this.mPath.lineTo(this.cx, this.cy);
        this.mPath.lineTo(this.cx, this.cy);
        this.mPath.lineTo(this.cx - (this.cr * this.sign), (this.cy + (this.cr * this.sign)) - this.mPadding);
        this.mPath.lineTo(this.cx - (this.cr * this.sign), (this.cy - (this.cr * this.sign)) + this.mPadding);
        this.mPath.lineTo((this.cx + this.cr) - this.mPadding, this.cy);
        for (i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                this.mPath.lineTo(this.cx + 0.5f, this.cy);
            } else {
                this.mPath.lineTo(this.cx - 0.5f, this.cy);
            }
        }
        this.mPath.lineTo(this.cx, this.cy);
        this.mPath.lineTo(this.cx + ((this.cr * 2.0f) * this.sign), this.cy + ((this.cr * 2.0f) * this.sign));
        this.mPathMeasure.setPath(this.mPath, false);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        canvas.save();
        paint.setColor(-1);
        paint.setStrokeWidth(10.0f);
        paint.setStyle(Style.STROKE);
        canvas.drawCircle(this.cx, this.cy, this.cr, paint);
        canvas.drawLine((this.cr * this.sign) + this.cx, (this.cr * this.sign) + this.cy, ((this.cr * 2.0f) * this.sign) + this.cx, ((this.cr * 2.0f) * this.sign) + this.cy, paint);
        canvas.restore();
    }

    public void startAnim() {
        if (this.mState != 1) {
            this.mState = 1;
            AnimatorSet set = new AnimatorSet();
            ValueAnimator v2 = startSearchViewAnim(0.0f, this.mPathMeasure.getLength(), 3000, this.mPathMeasure);
            v2.setStartDelay(500);
            set.play(startSearchViewAnim()).before(v2);
        }
    }

    public void resetAnim() {
        if (this.mState != 2) {
            this.mState = 2;
            this.mArcTemp = 1.0f;
            startSearchViewAnim();
        }
    }
}
