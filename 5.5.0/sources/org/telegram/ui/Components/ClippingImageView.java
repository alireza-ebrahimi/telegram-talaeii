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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    public float getAnimationProgress() {
        return this.animationProgress;
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
        float f = 1.0f;
        if (getVisibility() == 0 && this.bmp != null) {
            float scaleY = getScaleY();
            canvas.save();
            if (this.needRadius) {
                int height;
                int width;
                this.shaderMatrix.reset();
                this.roundRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight());
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    height = this.bmp.getHeight();
                    width = this.bmp.getWidth();
                } else {
                    height = this.bmp.getWidth();
                    width = this.bmp.getHeight();
                }
                float width2 = getWidth() != 0 ? ((float) height) / ((float) getWidth()) : 1.0f;
                if (getHeight() != 0) {
                    f = ((float) width) / ((float) getHeight());
                }
                float min = Math.min(width2, f);
                if (Math.abs(width2 - f) > 1.0E-5f) {
                    int floor = (int) Math.floor((double) (((float) getWidth()) * min));
                    int floor2 = (int) Math.floor((double) (((float) getHeight()) * min));
                    this.bitmapRect.set((float) ((height - floor) / 2), (float) ((width - floor2) / 2), (float) floor, (float) floor2);
                    AndroidUtilities.setRectToRect(this.shaderMatrix, this.bitmapRect, this.roundRect, this.orientation, ScaleToFit.START);
                } else {
                    this.bitmapRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.bmp.getWidth(), (float) this.bmp.getHeight());
                    AndroidUtilities.setRectToRect(this.shaderMatrix, this.bitmapRect, this.roundRect, this.orientation, ScaleToFit.FILL);
                }
                this.bitmapShader.setLocalMatrix(this.shaderMatrix);
                canvas.clipRect(((float) this.clipLeft) / scaleY, ((float) this.clipTop) / scaleY, ((float) getWidth()) - (((float) this.clipRight) / scaleY), ((float) getHeight()) - (((float) this.clipBottom) / scaleY));
                canvas.drawRoundRect(this.roundRect, (float) this.radius, (float) this.radius, this.roundPaint);
            } else {
                if (this.orientation == 90 || this.orientation == 270) {
                    this.drawRect.set((float) ((-getHeight()) / 2), (float) ((-getWidth()) / 2), (float) (getHeight() / 2), (float) (getWidth() / 2));
                    this.matrix.setRectToRect(this.bitmapRect, this.drawRect, ScaleToFit.FILL);
                    this.matrix.postRotate((float) this.orientation, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    this.matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
                } else if (this.orientation == 180) {
                    this.drawRect.set((float) ((-getWidth()) / 2), (float) ((-getHeight()) / 2), (float) (getWidth() / 2), (float) (getHeight() / 2));
                    this.matrix.setRectToRect(this.bitmapRect, this.drawRect, ScaleToFit.FILL);
                    this.matrix.postRotate((float) this.orientation, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    this.matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
                } else {
                    this.drawRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight());
                    this.matrix.setRectToRect(this.bitmapRect, this.drawRect, ScaleToFit.FILL);
                }
                canvas.clipRect(((float) this.clipLeft) / scaleY, ((float) this.clipTop) / scaleY, ((float) getWidth()) - (((float) this.clipRight) / scaleY), ((float) getHeight()) - (((float) this.clipBottom) / scaleY));
                try {
                    canvas.drawBitmap(this.bmp, this.matrix, this.paint);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            canvas.restore();
        }
    }

    public void setAnimationProgress(float f) {
        this.animationProgress = f;
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

    public void setAnimationValues(float[][] fArr) {
        this.animationValues = fArr;
    }

    public void setClipBottom(int i) {
        this.clipBottom = i;
        invalidate();
    }

    public void setClipHorizontal(int i) {
        this.clipRight = i;
        this.clipLeft = i;
        invalidate();
    }

    public void setClipLeft(int i) {
        this.clipLeft = i;
        invalidate();
    }

    public void setClipRight(int i) {
        this.clipRight = i;
        invalidate();
    }

    public void setClipTop(int i) {
        this.clipTop = i;
        invalidate();
    }

    public void setClipVertical(int i) {
        this.clipBottom = i;
        this.clipTop = i;
        invalidate();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.bmp = bitmap;
        if (bitmap != null) {
            this.bitmapRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) bitmap.getWidth(), (float) bitmap.getHeight());
            if (this.needRadius) {
                this.bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                this.roundPaint.setShader(this.bitmapShader);
            }
        }
        invalidate();
    }

    public void setNeedRadius(boolean z) {
        this.needRadius = z;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    public void setRadius(int i) {
        this.radius = i;
    }
}
