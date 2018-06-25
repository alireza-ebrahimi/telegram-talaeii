package org.telegram.ui.Components;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.Keep;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class CheckBoxSquare extends View {
    private static final float progressBounceDiff = 0.2f;
    private boolean attachedToWindow;
    private ObjectAnimator checkAnimator;
    private Bitmap drawBitmap = Bitmap.createBitmap(AndroidUtilities.dp(18.0f), AndroidUtilities.dp(18.0f), Config.ARGB_4444);
    private Canvas drawCanvas = new Canvas(this.drawBitmap);
    private boolean isAlert;
    private boolean isChecked;
    private boolean isDisabled;
    private float progress;
    private RectF rectF = new RectF();

    public CheckBoxSquare(Context context, boolean alert) {
        super(context);
        this.isAlert = alert;
    }

    @Keep
    public void setProgress(float value) {
        if (this.progress != value) {
            this.progress = value;
            invalidate();
        }
    }

    public float getProgress() {
        return this.progress;
    }

    private void cancelCheckAnimator() {
        if (this.checkAnimator != null) {
            this.checkAnimator.cancel();
        }
    }

    private void animateToCheckedState(boolean newCheckedState) {
        String str = "progress";
        float[] fArr = new float[1];
        fArr[0] = newCheckedState ? 1.0f : 0.0f;
        this.checkAnimator = ObjectAnimator.ofFloat(this, str, fArr);
        this.checkAnimator.setDuration(300);
        this.checkAnimator.start();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setChecked(boolean checked, boolean animated) {
        if (checked != this.isChecked) {
            this.isChecked = checked;
            if (this.attachedToWindow && animated) {
                animateToCheckedState(checked);
                return;
            }
            cancelCheckAnimator();
            setProgress(checked ? 1.0f : 0.0f);
        }
    }

    public void setDisabled(boolean disabled) {
        this.isDisabled = disabled;
        invalidate();
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    protected void onDraw(Canvas canvas) {
        if (getVisibility() == 0) {
            float checkProgress;
            float bounceProgress;
            int uncheckedColor = Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareUnchecked : Theme.key_checkboxSquareUnchecked);
            int color = Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareBackground : Theme.key_checkboxSquareBackground);
            if (this.progress <= 0.5f) {
                checkProgress = this.progress / 0.5f;
                bounceProgress = checkProgress;
                Theme.checkboxSquare_backgroundPaint.setColor(Color.rgb(Color.red(uncheckedColor) + ((int) (((float) (Color.red(color) - Color.red(uncheckedColor))) * checkProgress)), Color.green(uncheckedColor) + ((int) (((float) (Color.green(color) - Color.green(uncheckedColor))) * checkProgress)), Color.blue(uncheckedColor) + ((int) (((float) (Color.blue(color) - Color.blue(uncheckedColor))) * checkProgress))));
            } else {
                bounceProgress = 2.0f - (this.progress / 0.5f);
                checkProgress = 1.0f;
                Theme.checkboxSquare_backgroundPaint.setColor(color);
            }
            if (this.isDisabled) {
                Theme.checkboxSquare_backgroundPaint.setColor(Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareDisabled : Theme.key_checkboxSquareDisabled));
            }
            float bounce = ((float) AndroidUtilities.dp(1.0f)) * bounceProgress;
            this.rectF.set(bounce, bounce, ((float) AndroidUtilities.dp(18.0f)) - bounce, ((float) AndroidUtilities.dp(18.0f)) - bounce);
            this.drawBitmap.eraseColor(0);
            this.drawCanvas.drawRoundRect(this.rectF, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), Theme.checkboxSquare_backgroundPaint);
            if (checkProgress != 1.0f) {
                float rad = Math.min((float) AndroidUtilities.dp(7.0f), (((float) AndroidUtilities.dp(7.0f)) * checkProgress) + bounce);
                this.rectF.set(((float) AndroidUtilities.dp(2.0f)) + rad, ((float) AndroidUtilities.dp(2.0f)) + rad, ((float) AndroidUtilities.dp(16.0f)) - rad, ((float) AndroidUtilities.dp(16.0f)) - rad);
                this.drawCanvas.drawRect(this.rectF, Theme.checkboxSquare_eraserPaint);
            }
            if (this.progress > 0.5f) {
                Theme.checkboxSquare_checkPaint.setColor(Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareCheck : Theme.key_checkboxSquareCheck));
                this.drawCanvas.drawLine((float) AndroidUtilities.dp(7.5f), (float) ((int) AndroidUtilities.dpf2(13.5f)), (float) ((int) (((float) AndroidUtilities.dp(7.5f)) - (((float) AndroidUtilities.dp(5.0f)) * (1.0f - bounceProgress)))), (float) ((int) (AndroidUtilities.dpf2(13.5f) - (((float) AndroidUtilities.dp(5.0f)) * (1.0f - bounceProgress)))), Theme.checkboxSquare_checkPaint);
                this.drawCanvas.drawLine((float) ((int) AndroidUtilities.dpf2(6.5f)), (float) ((int) AndroidUtilities.dpf2(13.5f)), (float) ((int) (AndroidUtilities.dpf2(6.5f) + (((float) AndroidUtilities.dp(9.0f)) * (1.0f - bounceProgress)))), (float) ((int) (AndroidUtilities.dpf2(13.5f) - (((float) AndroidUtilities.dp(9.0f)) * (1.0f - bounceProgress)))), Theme.checkboxSquare_checkPaint);
            }
            canvas.drawBitmap(this.drawBitmap, 0.0f, 0.0f, null);
        }
    }
}
