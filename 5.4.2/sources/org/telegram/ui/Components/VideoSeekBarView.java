package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class VideoSeekBarView extends View {
    private SeekBarDelegate delegate;
    private Paint paint = new Paint();
    private Paint paint2 = new Paint(1);
    private boolean pressed = false;
    private float progress = BitmapDescriptorFactory.HUE_RED;
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

    public float getProgress() {
        return this.progress;
    }

    protected void onDraw(Canvas canvas) {
        int measuredHeight = (getMeasuredHeight() - this.thumbHeight) / 2;
        int measuredWidth = (int) (((float) (getMeasuredWidth() - this.thumbWidth)) * this.progress);
        canvas.drawRect((float) (this.thumbWidth / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() - (this.thumbWidth / 2)), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.paint);
        canvas.drawCircle((float) ((this.thumbWidth / 2) + measuredWidth), (float) ((this.thumbHeight / 2) + measuredHeight), (float) (this.thumbWidth / 2), this.paint2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (motionEvent == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float measuredWidth = (float) ((int) (((float) (getMeasuredWidth() - this.thumbWidth)) * this.progress));
        if (motionEvent.getAction() == 0) {
            int measuredHeight = (getMeasuredHeight() - this.thumbWidth) / 2;
            if (measuredWidth - ((float) measuredHeight) <= x) {
                if (x <= ((float) measuredHeight) + (((float) this.thumbWidth) + measuredWidth) && y >= BitmapDescriptorFactory.HUE_RED && y <= ((float) getMeasuredHeight())) {
                    this.pressed = true;
                    this.thumbDX = (int) (x - measuredWidth);
                    getParent().requestDisallowInterceptTouchEvent(true);
                    invalidate();
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (this.pressed) {
                if (motionEvent.getAction() == 1 && this.delegate != null) {
                    this.delegate.onSeekBarDrag(measuredWidth / ((float) (getMeasuredWidth() - this.thumbWidth)));
                }
                this.pressed = false;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 2 && this.pressed) {
            float f2 = (float) ((int) (x - ((float) this.thumbDX)));
            if (f2 >= BitmapDescriptorFactory.HUE_RED) {
                f = f2 > ((float) (getMeasuredWidth() - this.thumbWidth)) ? (float) (getMeasuredWidth() - this.thumbWidth) : f2;
            }
            this.progress = f / ((float) (getMeasuredWidth() - this.thumbWidth));
            invalidate();
            return true;
        }
        return false;
    }

    public void setDelegate(SeekBarDelegate seekBarDelegate) {
        this.delegate = seekBarDelegate;
    }

    public void setProgress(float f) {
        if (f < BitmapDescriptorFactory.HUE_RED) {
            f = BitmapDescriptorFactory.HUE_RED;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.progress = f;
        invalidate();
    }
}
