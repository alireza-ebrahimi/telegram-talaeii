package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.telegram.messenger.AndroidUtilities;

public class SeekBar {
    private static Paint innerPaint;
    private static Paint outerPaint;
    private static int thumbWidth;
    private SeekBarDelegate delegate;
    private int height;
    private int innerColor;
    private int outerColor;
    private boolean pressed = false;
    private boolean selected;
    private int selectedColor;
    private int thumbDX = 0;
    private int thumbX = 0;
    private int width;

    public interface SeekBarDelegate {
        void onSeekBarDrag(float f);
    }

    public SeekBar(Context context) {
        if (innerPaint == null) {
            innerPaint = new Paint(1);
            outerPaint = new Paint(1);
            thumbWidth = AndroidUtilities.dp(24.0f);
        }
    }

    public void setDelegate(SeekBarDelegate seekBarDelegate) {
        this.delegate = seekBarDelegate;
    }

    public boolean onTouch(int action, float x, float y) {
        if (action == 0) {
            int additionWidth = (this.height - thumbWidth) / 2;
            if (((float) (this.thumbX - additionWidth)) <= x && x <= ((float) ((this.thumbX + thumbWidth) + additionWidth)) && y >= 0.0f && y <= ((float) this.height)) {
                this.pressed = true;
                this.thumbDX = (int) (x - ((float) this.thumbX));
                return true;
            }
        } else if (action == 1 || action == 3) {
            if (this.pressed) {
                if (action == 1 && this.delegate != null) {
                    this.delegate.onSeekBarDrag(((float) this.thumbX) / ((float) (this.width - thumbWidth)));
                }
                this.pressed = false;
                return true;
            }
        } else if (action == 2 && this.pressed) {
            this.thumbX = (int) (x - ((float) this.thumbDX));
            if (this.thumbX < 0) {
                this.thumbX = 0;
                return true;
            } else if (this.thumbX <= this.width - thumbWidth) {
                return true;
            } else {
                this.thumbX = this.width - thumbWidth;
                return true;
            }
        }
        return false;
    }

    public void setColors(int inner, int outer, int selected) {
        this.innerColor = inner;
        this.outerColor = outer;
        this.selectedColor = selected;
    }

    public void setProgress(float progress) {
        this.thumbX = (int) Math.ceil((double) (((float) (this.width - thumbWidth)) * progress));
        if (this.thumbX < 0) {
            this.thumbX = 0;
        } else if (this.thumbX > this.width - thumbWidth) {
            this.thumbX = this.width - thumbWidth;
        }
    }

    public float getProgress() {
        return ((float) this.thumbX) / ((float) (this.width - thumbWidth));
    }

    public boolean isDragging() {
        return this.pressed;
    }

    public void setSelected(boolean value) {
        this.selected = value;
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public void draw(Canvas canvas) {
        innerPaint.setColor(this.selected ? this.selectedColor : this.innerColor);
        outerPaint.setColor(this.outerColor);
        canvas.drawRect((float) (thumbWidth / 2), (float) ((this.height / 2) - AndroidUtilities.dp(1.0f)), (float) (this.width - (thumbWidth / 2)), (float) ((this.height / 2) + AndroidUtilities.dp(1.0f)), innerPaint);
        canvas.drawRect((float) (thumbWidth / 2), (float) ((this.height / 2) - AndroidUtilities.dp(1.0f)), (float) ((thumbWidth / 2) + this.thumbX), (float) ((this.height / 2) + AndroidUtilities.dp(1.0f)), outerPaint);
        canvas.drawCircle((float) (this.thumbX + (thumbWidth / 2)), (float) (this.height / 2), (float) AndroidUtilities.dp(this.pressed ? 8.0f : 6.0f), outerPaint);
    }
}
