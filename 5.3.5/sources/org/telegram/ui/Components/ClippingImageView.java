package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

public class ClippingImageView extends View {
    private float animationProgress;
    private float[][] animationValues;
    private RectF bitmapRect;
    private BitmapShader bitmapShader;
    private Bitmap bmp;
    private int clipBottom;
    private int clipLeft;
    private int clipRight;
    private int clipTop;
    private RectF drawRect;
    private Matrix matrix;
    private boolean needRadius;
    private int orientation;
    private Paint paint = new Paint();
    private int radius;
    private Paint roundPaint;
    private RectF roundRect;
    private Matrix shaderMatrix;

    public ClippingImageView(Context context) {
        super(context);
        this.paint.setFilterBitmap(true);
        this.matrix = new Matrix();
        this.drawRect = new RectF();
        this.bitmapRect = new RectF();
        this.roundPaint = new Paint(1);
        this.roundRect = new RectF();
        this.shaderMatrix = new Matrix();
    }

    public void setAnimationValues(float[][] values) {
        this.animationValues = values;
    }

    public float getAnimationProgress() {
        return this.animationProgress;
    }

    public void setAnimationProgress(float progress) {
        this.animationProgress = progress;
        setScaleX(this.animationValues[0][0] + ((this.animationValues[1][0] - this.animationValues[0][0]) * this.animationProgress));
        setScaleY(this.animationValues[0][1] + ((this.animationValues[1][1] - this.animationValues[0][1]) * this.animationProgress));
        setTranslationX(this.animationValues[0][2] + ((this.animationValues[1][2] - this.animationValues[0][2]) * this.animationProgress));
        setTranslationY(this.animationValues[0][3] + ((this.animationValues[1][3] - this.animationValues[0][3]) * this.animationProgress));
        setClipHorizontal((int) (this.animationValues[0][4] + ((this.animationValues[1][4] - this.animationValues[0][4]) * this.animationProgress)));
        setClipTop((int) (this.animationValues[0][5] + ((this.animationValues[1][5] - this.animationValues[0][5]) * this.animationProgress)));
        setClipBottom((int) (this.animationValues[0][6] + ((this.animationValues[1][6] - this.animationValues[0][6]) * this.animationProgress)));
        setRadius((int) (this.animationValues[0][7] + ((this.animationValues[1][7] - this.animationValues[0][7]) * this.animationProgress)));
        invalidate();
    }

    public int getClipBottom() {
        return this.clipBottom;
    }

    public int getClipHorizontal() {
        return this.clipRight;
    }

    public int getClipLeft() {
        return this.clipLeft;
    }

    public int getClipRight() {
        return this.clipRight;
    }

    public int getClipTop() {
        return this.clipTop;
    }

    public int getRadius() {
        return this.radius;
    }

    public void onDraw(Canvas canvas) {
        if (getVisibility() == 0 && this.bmp != null) {
            float scaleY = getScaleY();
            canvas.save();
            if (this.needRadius) {
                int bitmapW;
                int bitmapH;
                this.shaderMatrix.reset();
                this.roundRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    bitmapW = this.bmp.getHeight();
                    bitmapH = this.bmp.getWidth();
                } else {
                    bitmapW = this.bmp.getWidth();
                    bitmapH = this.bmp.getHeight();
                }
                float scaleW = getWidth() != 0 ? ((float) bitmapW) / ((float) getWidth()) : 1.0f;
                float scaleH = getHeight() != 0 ? ((float) bitmapH) / ((float) getHeight()) : 1.0f;
                float scale = Math.min(scaleW, scaleH);
                if (Math.abs(scaleW - scaleH) > 1.0E-5f) {
                    int w = (int) Math.floor((double) (((float) getWidth()) * scale));
                    int h = (int) Math.floor((double) (((float) getHeight()) * scale));
                    this.bitmapRect.set((float) ((bitmapW - w) / 2), (float) ((bitmapH - h) / 2), (float) w, (float) h);
                    AndroidUtilities.setRectToRect(this.shaderMatrix, this.bitmapRect, this.roundRect, this.orientation, ScaleToFit.START);
                } else {
                    this.bitmapRect.set(0.0f, 0.0f, (float) this.bmp.getWidth(), (float) this.bmp.getHeight());
                    AndroidUtilities.setRectToRect(this.shaderMatrix, this.bitmapRect, this.roundRect, this.orientation, ScaleToFit.FILL);
                }
                this.bitmapShader.setLocalMatrix(this.shaderMatrix);
                canvas.clipRect(((float) this.clipLeft) / scaleY, ((float) this.clipTop) / scaleY, ((float) getWidth()) - (((float) this.clipRight) / scaleY), ((float) getHeight()) - (((float) this.clipBottom) / scaleY));
                canvas.drawRoundRect(this.roundRect, (float) this.radius, (float) this.radius, this.roundPaint);
            } else {
                if (this.orientation == 90 || this.orientation == 270) {
                    this.drawRect.set((float) ((-getHeight()) / 2), (float) ((-getWidth()) / 2), (float) (getHeight() / 2), (float) (getWidth() / 2));
                    this.matrix.setRectToRect(this.bitmapRect, this.drawRect, ScaleToFit.FILL);
                    this.matrix.postRotate((float) this.orientation, 0.0f, 0.0f);
                    this.matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
                } else if (this.orientation == 180) {
                    this.drawRect.set((float) ((-getWidth()) / 2), (float) ((-getHeight()) / 2), (float) (getWidth() / 2), (float) (getHeight() / 2));
                    this.matrix.setRectToRect(this.bitmapRect, this.drawRect, ScaleToFit.FILL);
                    this.matrix.postRotate((float) this.orientation, 0.0f, 0.0f);
                    this.matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
                } else {
                    this.drawRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
                    this.matrix.setRectToRect(this.bitmapRect, this.drawRect, ScaleToFit.FILL);
                }
                canvas.clipRect(((float) this.clipLeft) / scaleY, ((float) this.clipTop) / scaleY, ((float) getWidth()) - (((float) this.clipRight) / scaleY), ((float) getHeight()) - (((float) this.clipBottom) / scaleY));
                try {
                    canvas.drawBitmap(this.bmp, this.matrix, this.paint);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            canvas.restore();
        }
    }

    public void setClipBottom(int value) {
        this.clipBottom = value;
        invalidate();
    }

    public void setClipHorizontal(int value) {
        this.clipRight = value;
        this.clipLeft = value;
        invalidate();
    }

    public void setClipLeft(int value) {
        this.clipLeft = value;
        invalidate();
    }

    public void setClipRight(int value) {
        this.clipRight = value;
        invalidate();
    }

    public void setClipTop(int value) {
        this.clipTop = value;
        invalidate();
    }

    public void setClipVertical(int value) {
        this.clipBottom = value;
        this.clipTop = value;
        invalidate();
    }

    public void setOrientation(int angle) {
        this.orientation = angle;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.bmp = bitmap;
        if (bitmap != null) {
            this.bitmapRect.set(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
            if (this.needRadius) {
                this.bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                this.roundPaint.setShader(this.bitmapShader);
            }
        }
        invalidate();
    }

    public void setNeedRadius(boolean value) {
        this.needRadius = value;
    }

    public void setRadius(int value) {
        this.radius = value;
    }
}
