package org.telegram.customization.util.view.sva.anim.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.customization.util.view.sva.anim.JJBaseController;

public class JJAroundCircleBornTailController extends JJBaseController {
    private int cr;
    private int cx;
    private int cy;
    private int mAngle = 10;
    private String mColor = "#2196F3";
    private String mColorTran = "#50FFFFFF";
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
        paint.reset();
        canvas.save();
        paint.setColor(-1);
        paint.setStrokeWidth(14.0f);
        paint.setStyle(Style.STROKE);
        canvas.rotate(45.0f, (float) this.cx, (float) this.cy);
        canvas.drawLine((float) (this.cx + this.cr), (float) this.cy, (float) (this.cx + (this.cr * 2)), (float) this.cy, paint);
        canvas.drawCircle((float) this.cx, (float) this.cy, (float) this.cr, paint);
        canvas.restore();
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(this.mColorTran));
        paint.setStrokeWidth(10.0f);
        paint.setStyle(Style.STROKE);
        canvas.rotate(45.0f, (float) this.cx, (float) this.cy);
        canvas.drawCircle((float) this.cx, (float) this.cy, (float) this.cr, paint);
        this.mRectF.left = (float) (this.cx - this.cr);
        this.mRectF.right = (float) (this.cx + this.cr);
        this.mRectF.top = (float) (this.cy - this.cr);
        this.mRectF.bottom = (float) (this.cy + this.cr);
        if (((double) this.mPro) <= 0.2d) {
            canvas.drawLine((float) (this.cx + this.cr), (float) this.cy, (((float) this.cr) * (0.2f - this.mPro)) + ((float) (this.cx + this.cr)), (float) this.cy, paint);
            canvas.save();
            paint.setAntiAlias(true);
            paint.setColor(-1);
            canvas.drawArc(this.mRectF, 6.0f, -14.0f, false, paint);
            canvas.restore();
        } else if (((double) this.mPro) <= 0.2d || ((double) this.mPro) >= 4.5d) {
            canvas.save();
            paint.setAntiAlias(true);
            paint.setColor(-1);
            paint.setStrokeWidth(14.0f);
            paint.setStyle(Style.STROKE);
            canvas.drawLine((float) (this.cx + this.cr), (float) this.cy, (((float) this.cr) * ((this.mPro - 4.5f) * 2.0f)) + ((float) (this.cx + this.cr)), (float) this.cy, paint);
            canvas.drawCircle((float) this.cx, (float) this.cy, (float) this.cr, paint);
            canvas.restore();
        } else {
            canvas.save();
            paint.setColor(-1);
            this.mAngle += 20;
            canvas.rotate((float) this.mAngle, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.drawArc(this.mRectF, 0.0f, (float) (this.mAngle / 4), false, paint);
            canvas.restore();
        }
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        this.cr = getWidth() / 15;
        this.cx = getWidth() / 2;
        this.cy = getHeight() / 2;
        paint.reset();
        paint.setAntiAlias(true);
        canvas.save();
        paint.setColor(-1);
        paint.setStrokeWidth(14.0f);
        paint.setStyle(Style.STROKE);
        canvas.rotate(45.0f, (float) this.cx, (float) this.cy);
        canvas.drawLine((float) (this.cx + this.cr), (float) this.cy, (float) (this.cx + (this.cr * 2)), (float) this.cy, paint);
        canvas.drawCircle((float) this.cx, (float) this.cy, (float) this.cr, paint);
        canvas.restore();
    }

    public void startAnim() {
        if (this.mState != 1) {
            this.mState = 1;
            startSearchViewAnim(0.0f, 5.0f, FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
        }
    }

    public void resetAnim() {
        if (this.mState != 2) {
            this.mState = 2;
            this.mAngle = 0;
            startSearchViewAnim();
        }
    }
}
