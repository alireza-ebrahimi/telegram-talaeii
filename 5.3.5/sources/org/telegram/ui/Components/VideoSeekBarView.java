package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;

public class VideoSeekBarView extends View {
    private SeekBarDelegate delegate;
    private Paint paint = new Paint();
    private Paint paint2 = new Paint(1);
    private boolean pressed = false;
    private float progress = 0.0f;
    private int thumbDX = 0;
    private int thumbHeight = AndroidUtilities.dp(12.0f);
    private int thumbWidth = AndroidUtilities.dp(12.0f);

    public interface SeekBarDelegate {
        void onSeekBarDrag(float f);
    }

    public VideoSeekBarView(Context context) {
        super(context);
        this.paint.setColor(-10724260);
        this.paint2.setColor(-1);
    }

    public void setDelegate(SeekBarDelegate seekBarDelegate) {
        this.delegate = seekBarDelegate;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        float thumbX = (float) ((int) (((float) (getMeasuredWidth() - this.thumbWidth)) * this.progress));
        if (event.getAction() == 0) {
            int additionWidth = (getMeasuredHeight() - this.thumbWidth) / 2;
            if (thumbX - ((float) additionWidth) > x || x > (((float) this.thumbWidth) + thumbX) + ((float) additionWidth) || y < 0.0f || y > ((float) getMeasuredHeight())) {
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
            if (event.getAction() == 1 && this.delegate != null) {
                this.delegate.onSeekBarDrag(thumbX / ((float) (getMeasuredWidth() - this.thumbWidth)));
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
            } else if (thumbX > ((float) (getMeasuredWidth() - this.thumbWidth))) {
                thumbX = (float) (getMeasuredWidth() - this.thumbWidth);
            }
            this.progress = thumbX / ((float) (getMeasuredWidth() - this.thumbWidth));
            invalidate();
            return true;
        }
    }

    public void setProgress(float progress) {
        if (progress < 0.0f) {
            progress = 0.0f;
        } else if (progress > 1.0f) {
            progress = 1.0f;
        }
        this.progress = progress;
        invalidate();
    }

    public float getProgress() {
        return this.progress;
    }

    protected void onDraw(Canvas canvas) {
        int y = (getMeasuredHeight() - this.thumbHeight) / 2;
        int thumbX = (int) (((float) (getMeasuredWidth() - this.thumbWidth)) * this.progress);
        canvas.drawRect((float) (this.thumbWidth / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() - (this.thumbWidth / 2)), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.paint);
        canvas.drawCircle((float) ((this.thumbWidth / 2) + thumbX), (float) ((this.thumbHeight / 2) + y), (float) (this.thumbWidth / 2), this.paint2);
    }
}
