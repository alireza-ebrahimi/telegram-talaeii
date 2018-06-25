package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class SeekBarView extends FrameLayout {
    private SeekBarViewDelegate delegate;
    private Paint innerPaint1 = new Paint(1);
    private Paint outerPaint1;
    private boolean pressed;
    private float progressToSet;
    private boolean reportChanges;
    private int thumbDX;
    private int thumbHeight;
    private int thumbWidth;
    private int thumbX;

    public interface SeekBarViewDelegate {
        void onSeekBarDrag(float f);
    }

    public SeekBarView(Context context) {
        super(context);
        setWillNotDraw(false);
        this.innerPaint1.setColor(Theme.getColor(Theme.key_player_progressBackground));
        this.outerPaint1 = new Paint(1);
        this.outerPaint1.setColor(Theme.getColor(Theme.key_player_progress));
        this.thumbWidth = AndroidUtilities.dp(24.0f);
        this.thumbHeight = AndroidUtilities.dp(24.0f);
    }

    public void setColors(int inner, int outer) {
        this.innerPaint1.setColor(inner);
        this.outerPaint1.setColor(outer);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouch(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return onTouch(event);
    }

    public void setReportChanges(boolean value) {
        this.reportChanges = value;
    }

    public void setDelegate(SeekBarViewDelegate seekBarViewDelegate) {
        this.delegate = seekBarViewDelegate;
    }

    boolean onTouch(MotionEvent ev) {
        if (ev.getAction() == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            int additionWidth = (getMeasuredHeight() - this.thumbWidth) / 2;
            if (((float) (this.thumbX - additionWidth)) <= ev.getX() && ev.getX() <= ((float) ((this.thumbX + this.thumbWidth) + additionWidth)) && ev.getY() >= 0.0f && ev.getY() <= ((float) getMeasuredHeight())) {
                this.pressed = true;
                this.thumbDX = (int) (ev.getX() - ((float) this.thumbX));
                invalidate();
                return true;
            }
        } else if (ev.getAction() == 1 || ev.getAction() == 3) {
            if (this.pressed) {
                if (ev.getAction() == 1) {
                    this.delegate.onSeekBarDrag(((float) this.thumbX) / ((float) (getMeasuredWidth() - this.thumbWidth)));
                }
                this.pressed = false;
                invalidate();
                return true;
            }
        } else if (ev.getAction() == 2 && this.pressed) {
            this.thumbX = (int) (ev.getX() - ((float) this.thumbDX));
            if (this.thumbX < 0) {
                this.thumbX = 0;
            } else if (this.thumbX > getMeasuredWidth() - this.thumbWidth) {
                this.thumbX = getMeasuredWidth() - this.thumbWidth;
            }
            if (this.reportChanges) {
                this.delegate.onSeekBarDrag(((float) this.thumbX) / ((float) (getMeasuredWidth() - this.thumbWidth)));
            }
            invalidate();
            return true;
        }
        return false;
    }

    public void setProgress(float progress) {
        if (getMeasuredWidth() == 0) {
            this.progressToSet = progress;
            return;
        }
        this.progressToSet = -1.0f;
        int newThumbX = (int) Math.ceil((double) (((float) (getMeasuredWidth() - this.thumbWidth)) * progress));
        if (this.thumbX != newThumbX) {
            this.thumbX = newThumbX;
            if (this.thumbX < 0) {
                this.thumbX = 0;
            } else if (this.thumbX > getMeasuredWidth() - this.thumbWidth) {
                this.thumbX = getMeasuredWidth() - this.thumbWidth;
            }
            invalidate();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.progressToSet >= 0.0f && getMeasuredWidth() > 0) {
            setProgress(this.progressToSet);
            this.progressToSet = -1.0f;
        }
    }

    public boolean isDragging() {
        return this.pressed;
    }

    protected void onDraw(Canvas canvas) {
        int y = (getMeasuredHeight() - this.thumbHeight) / 2;
        canvas.drawRect((float) (this.thumbWidth / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) (getMeasuredWidth() - (this.thumbWidth / 2)), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.innerPaint1);
        canvas.drawRect((float) (this.thumbWidth / 2), (float) ((getMeasuredHeight() / 2) - AndroidUtilities.dp(1.0f)), (float) ((this.thumbWidth / 2) + this.thumbX), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(1.0f)), this.outerPaint1);
        canvas.drawCircle((float) (this.thumbX + (this.thumbWidth / 2)), (float) ((this.thumbHeight / 2) + y), (float) AndroidUtilities.dp(this.pressed ? 8.0f : 6.0f), this.outerPaint1);
    }
}
