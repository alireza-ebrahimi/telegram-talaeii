package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class PhotoEditorSeekBar extends View {
    private PhotoEditorSeekBarDelegate delegate;
    private Paint innerPaint = new Paint();
    private int maxValue;
    private int minValue;
    private Paint outerPaint = new Paint(1);
    private boolean pressed = false;
    private float progress = BitmapDescriptorFactory.HUE_RED;
    private int thumbDX = 0;
    private int thumbSize = AndroidUtilities.dp(16.0f);

    public interface PhotoEditorSeekBarDelegate {
        void onProgressChanged(int i, int i2);
    }

    public PhotoEditorSeekBar(Context context) {
        super(context);
        this.innerPaint.setColor(-11711155);
        this.outerPaint.setColor(-1);
    }

    public int getProgress() {
        return (int) (((float) this.minValue) + (this.progress * ((float) (this.maxValue - this.minValue))));
    }

    protected void onDraw(Canvas canvas) {
        int measuredHeight = (getMeasuredHeight() - this.thumbSize) / 2;
        int measuredWidth = (int) (((float) (getMeasuredWidth() - this.thumbSize)) * this.progress);
        canvas.drawRect((float) (this.thumbSize / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() - (this.thumbSize / 2)), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.innerPaint);
        if (this.minValue == 0) {
            canvas.drawRect((float) (this.thumbSize / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) measuredWidth, (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint);
        } else if (this.progress > 0.5f) {
            canvas.drawRect((float) ((getMeasuredWidth() / 2) - AndroidUtilities.dp(1.0f)), (float) ((getMeasuredHeight() - this.thumbSize) / 2), (float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() + this.thumbSize) / 2), this.outerPaint);
            canvas.drawRect((float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) measuredWidth, (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint);
        } else {
            canvas.drawRect((float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() - this.thumbSize) / 2), (float) ((getMeasuredWidth() / 2) + AndroidUtilities.dp(1.0f)), (float) ((getMeasuredHeight() + this.thumbSize) / 2), this.outerPaint);
            canvas.drawRect((float) measuredWidth, (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint);
        }
        canvas.drawCircle((float) ((this.thumbSize / 2) + measuredWidth), (float) ((this.thumbSize / 2) + measuredHeight), (float) (this.thumbSize / 2), this.outerPaint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (motionEvent == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float measuredWidth = (float) ((int) (((float) (getMeasuredWidth() - this.thumbSize)) * this.progress));
        if (motionEvent.getAction() == 0) {
            int measuredHeight = (getMeasuredHeight() - this.thumbSize) / 2;
            if (measuredWidth - ((float) measuredHeight) <= x) {
                if (x <= ((float) measuredHeight) + (((float) this.thumbSize) + measuredWidth) && y >= BitmapDescriptorFactory.HUE_RED && y <= ((float) getMeasuredHeight())) {
                    this.pressed = true;
                    this.thumbDX = (int) (x - measuredWidth);
                    getParent().requestDisallowInterceptTouchEvent(true);
                    invalidate();
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (this.pressed) {
                this.pressed = false;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 2 && this.pressed) {
            float f2 = (float) ((int) (x - ((float) this.thumbDX)));
            if (f2 >= BitmapDescriptorFactory.HUE_RED) {
                f = f2 > ((float) (getMeasuredWidth() - this.thumbSize)) ? (float) (getMeasuredWidth() - this.thumbSize) : f2;
            }
            this.progress = f / ((float) (getMeasuredWidth() - this.thumbSize));
            if (this.delegate != null) {
                this.delegate.onProgressChanged(((Integer) getTag()).intValue(), getProgress());
            }
            invalidate();
            return true;
        }
        return false;
    }

    public void setDelegate(PhotoEditorSeekBarDelegate photoEditorSeekBarDelegate) {
        this.delegate = photoEditorSeekBarDelegate;
    }

    public void setMinMax(int i, int i2) {
        this.minValue = i;
        this.maxValue = i2;
    }

    public void setProgress(int i) {
        setProgress(i, true);
    }

    public void setProgress(int i, boolean z) {
        if (i < this.minValue) {
            i = this.minValue;
        } else if (i > this.maxValue) {
            i = this.maxValue;
        }
        this.progress = ((float) (i - this.minValue)) / ((float) (this.maxValue - this.minValue));
        invalidate();
        if (z && this.delegate != null) {
            this.delegate.onProgressChanged(((Integer) getTag()).intValue(), getProgress());
        }
    }
}
