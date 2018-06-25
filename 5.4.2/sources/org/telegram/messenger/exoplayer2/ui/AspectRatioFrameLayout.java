package org.telegram.messenger.exoplayer2.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AspectRatioFrameLayout extends FrameLayout {
    private static final float MAX_ASPECT_RATIO_DEFORMATION_FRACTION = 0.01f;
    public static final int RESIZE_MODE_FILL = 3;
    public static final int RESIZE_MODE_FIT = 0;
    public static final int RESIZE_MODE_FIXED_HEIGHT = 2;
    public static final int RESIZE_MODE_FIXED_WIDTH = 1;
    public static final int RESIZE_MODE_ZOOM = 4;
    private boolean drawingReady;
    private Matrix matrix;
    private int resizeMode;
    private int rotation;
    private float videoAspectRatio;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResizeMode {
    }

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.matrix = new Matrix();
        this.resizeMode = 0;
    }

    public float getAspectRatio() {
        return this.videoAspectRatio;
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    public int getVideoRotation() {
        return this.rotation;
    }

    public boolean isDrawingReady() {
        return this.drawingReady;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.resizeMode != 3 && this.videoAspectRatio > BitmapDescriptorFactory.HUE_RED) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = (this.videoAspectRatio / (((float) measuredWidth) / ((float) measuredHeight))) - 1.0f;
            if (Math.abs(f) > MAX_ASPECT_RATIO_DEFORMATION_FRACTION) {
                switch (this.resizeMode) {
                    case 1:
                        measuredHeight = (int) (((float) measuredWidth) / this.videoAspectRatio);
                        break;
                    case 2:
                        measuredWidth = (int) (((float) measuredHeight) * this.videoAspectRatio);
                        break;
                    case 4:
                        if (f <= BitmapDescriptorFactory.HUE_RED) {
                            measuredHeight = (int) (((float) measuredWidth) / this.videoAspectRatio);
                            break;
                        } else {
                            measuredWidth = (int) (((float) measuredHeight) * this.videoAspectRatio);
                            break;
                        }
                    default:
                        if (f <= BitmapDescriptorFactory.HUE_RED) {
                            measuredWidth = (int) (((float) measuredHeight) * this.videoAspectRatio);
                            break;
                        } else {
                            measuredHeight = (int) (((float) measuredWidth) / this.videoAspectRatio);
                            break;
                        }
                }
                super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
                int childCount = getChildCount();
                for (measuredWidth = 0; measuredWidth < childCount; measuredWidth++) {
                    View childAt = getChildAt(measuredWidth);
                    if (childAt instanceof TextureView) {
                        this.matrix.reset();
                        measuredWidth = getWidth() / 2;
                        childCount = getHeight() / 2;
                        this.matrix.postRotate((float) this.rotation, (float) measuredWidth, (float) childCount);
                        if (this.rotation == 90 || this.rotation == 270) {
                            float height = ((float) getHeight()) / ((float) getWidth());
                            this.matrix.postScale(1.0f / height, height, (float) measuredWidth, (float) childCount);
                        }
                        ((TextureView) childAt).setTransform(this.matrix);
                        return;
                    }
                }
            }
        }
    }

    public void setAspectRatio(float f, int i) {
        if (this.videoAspectRatio != f || this.rotation != i) {
            this.videoAspectRatio = f;
            this.rotation = i;
            requestLayout();
        }
    }

    public void setDrawingReady(boolean z) {
        if (this.drawingReady != z) {
            this.drawingReady = z;
        }
    }

    public void setResizeMode(int i) {
        if (this.resizeMode != i) {
            this.resizeMode = i;
            requestLayout();
        }
    }
}
