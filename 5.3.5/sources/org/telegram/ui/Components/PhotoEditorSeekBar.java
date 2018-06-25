package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;

public class PhotoEditorSeekBar extends View {
    private PhotoEditorSeekBarDelegate delegate;
    private Paint innerPaint = new Paint();
    private int maxValue;
    private int minValue;
    private Paint outerPaint = new Paint(1);
    private boolean pressed = false;
    private float progress = 0.0f;
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

    public void setDelegate(PhotoEditorSeekBarDelegate delegate) {
        this.delegate = delegate;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        float thumbX = (float) ((int) (((float) (getMeasuredWidth() - this.thumbSize)) * this.progress));
        if (event.getAction() == 0) {
            int additionWidth = (getMeasuredHeight() - this.thumbSize) / 2;
            if (thumbX - ((float) additionWidth) > x || x > (((float) this.thumbSize) + thumbX) + ((float) additionWidth) || y < 0.0f || y > ((float) getMeasuredHeight())) {
                return false;
            }
            this.pressed = true;
            this.thumbDX = (int) (x - thumbX);
            getParent().requestDisallowInterceptTouchEvent(true);
            invalidate();
            return true;
        } else if (event.getAction() == 1 || event.getAction() == 3) {
            if (!this.pressed) {
                return false;
            }
            this.pressed = false;
            invalidate();
            return true;
        } else if (event.getAction() != 2 || !this.pressed) {
            return false;
        } else {
            thumbX = (float) ((int) (x - ((float) this.thumbDX)));
            if (thumbX < 0.0f) {
                thumbX = 0.0f;
            } else if (thumbX > ((float) (getMeasuredWidth() - this.thumbSize))) {
                thumbX = (float) (getMeasuredWidth() - this.thumbSize);
            }
            this.progress = thumbX / ((float) (getMeasuredWidth() - this.thumbSize));
            if (this.delegate != null) {
                this.delegate.onProgressChanged(((Integer) getTag()).intValue(), getProgress());
            }
            invalidate();
            return true;
        }
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    public void setProgress(int progress, boolean notify) {
        if (progress < this.minValue) {
            progress = this.minValue;
        } else if (progress > this.maxValue) {
            progress = this.maxValue;
        }
        this.progress = ((float) (progress - this.minValue)) / ((float) (this.maxValue - this.minValue));
        invalidate();
        if (notify && this.delegate != null) {
            this.delegate.onProgressChanged(((Integer) getTag()).intValue(), getProgress());
        }
    }

    public int getProgress() {
        return (int) (((float) this.minValue) + (this.progress * ((float) (this.maxValue - this.minValue))));
    }

    public void setMinMax(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
    }

    protected void onDraw(Canvas canvas) {
        int y = (getMeasuredHeight() - this.thumbSize) / 2;
        int thumbX = (int) (((float) (getMeasuredWidth() - this.thumbSize)) * this.progress);
        canvas.drawRect((float) (this.thumbSize / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() - (this.thumbSize / 2)), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.innerPaint);
        if (this.minValue == 0) {
            canvas.drawRect((float) (this.thumbSize / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) thumbX, (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint);
        } else if (this.progress > 0.5f) {
            canvas.drawRect((float) ((getMeasuredWidth() / 2) - AndroidUtilities.dp(1.0f)), (float) ((getMeasuredHeight() - this.thumbSize) / 2), (float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() + this.thumbSize) / 2), this.outerPaint);
            canvas.drawRect((float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) thumbX, (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint);
        } else {
            canvas.drawRect((float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() - this.thumbSize) / 2), (float) ((getMeasuredWidth() / 2) + AndroidUtilities.dp(1.0f)), (float) ((getMeasuredHeight() + this.thumbSize) / 2), this.outerPaint);
            canvas.drawRect((float) thumbX, (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() / 2), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint);
        }
        canvas.drawCircle((float) ((this.thumbSize / 2) + thumbX), (float) ((this.thumbSize / 2) + y), (float) (this.thumbSize / 2), this.outerPaint);
    }
}
