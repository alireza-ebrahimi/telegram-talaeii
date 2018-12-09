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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    public CheckBoxSquare(Context context, boolean z) {
        super(context);
        this.isAlert = z;
    }

    private void animateToCheckedState(boolean z) {
        String str = "progress";
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        this.checkAnimator = ObjectAnimator.ofFloat(this, str, fArr);
        this.checkAnimator.setDuration(300);
        this.checkAnimator.start();
    }

    private void cancelCheckAnimator() {
        if (this.checkAnimator != null) {
            this.checkAnimator.cancel();
        }
    }

    public float getProgress() {
        return this.progress;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
    }

    protected void onDraw(Canvas canvas) {
        if (getVisibility() == 0) {
            float f;
            float f2;
            int color = Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareUnchecked : Theme.key_checkboxSquareUnchecked);
            int color2 = Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareBackground : Theme.key_checkboxSquareBackground);
            if (this.progress <= 0.5f) {
                f = this.progress / 0.5f;
                Theme.checkboxSquare_backgroundPaint.setColor(Color.rgb(((int) (((float) (Color.red(color2) - Color.red(color))) * f)) + Color.red(color), ((int) (((float) (Color.green(color2) - Color.green(color))) * f)) + Color.green(color), Color.blue(color) + ((int) (((float) (Color.blue(color2) - Color.blue(color))) * f))));
                f2 = f;
            } else {
                f = 2.0f - (this.progress / 0.5f);
                Theme.checkboxSquare_backgroundPaint.setColor(color2);
                f2 = f;
                f = 1.0f;
            }
            if (this.isDisabled) {
                Theme.checkboxSquare_backgroundPaint.setColor(Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareDisabled : Theme.key_checkboxSquareDisabled));
            }
            float dp = ((float) AndroidUtilities.dp(1.0f)) * f2;
            this.rectF.set(dp, dp, ((float) AndroidUtilities.dp(18.0f)) - dp, ((float) AndroidUtilities.dp(18.0f)) - dp);
            this.drawBitmap.eraseColor(0);
            this.drawCanvas.drawRoundRect(this.rectF, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), Theme.checkboxSquare_backgroundPaint);
            if (f != 1.0f) {
                f = Math.min((float) AndroidUtilities.dp(7.0f), (f * ((float) AndroidUtilities.dp(7.0f))) + dp);
                this.rectF.set(((float) AndroidUtilities.dp(2.0f)) + f, ((float) AndroidUtilities.dp(2.0f)) + f, ((float) AndroidUtilities.dp(16.0f)) - f, ((float) AndroidUtilities.dp(16.0f)) - f);
                this.drawCanvas.drawRect(this.rectF, Theme.checkboxSquare_eraserPaint);
            }
            if (this.progress > 0.5f) {
                Theme.checkboxSquare_checkPaint.setColor(Theme.getColor(this.isAlert ? Theme.key_dialogCheckboxSquareCheck : Theme.key_checkboxSquareCheck));
                this.drawCanvas.drawLine((float) AndroidUtilities.dp(7.5f), (float) ((int) AndroidUtilities.dpf2(13.5f)), (float) ((int) (((float) AndroidUtilities.dp(7.5f)) - (((float) AndroidUtilities.dp(5.0f)) * (1.0f - f2)))), (float) ((int) (AndroidUtilities.dpf2(13.5f) - (((float) AndroidUtilities.dp(5.0f)) * (1.0f - f2)))), Theme.checkboxSquare_checkPaint);
                this.drawCanvas.drawLine((float) ((int) AndroidUtilities.dpf2(6.5f)), (float) ((int) AndroidUtilities.dpf2(13.5f)), (float) ((int) (AndroidUtilities.dpf2(6.5f) + (((float) AndroidUtilities.dp(9.0f)) * (1.0f - f2)))), (float) ((int) (AndroidUtilities.dpf2(13.5f) - (((float) AndroidUtilities.dp(9.0f)) * (1.0f - f2)))), Theme.checkboxSquare_checkPaint);
            }
            canvas.drawBitmap(this.drawBitmap, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, null);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setChecked(boolean z, boolean z2) {
        if (z != this.isChecked) {
            this.isChecked = z;
            if (this.attachedToWindow && z2) {
                animateToCheckedState(z);
                return;
            }
            cancelCheckAnimator();
            setProgress(z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
        }
    }

    public void setDisabled(boolean z) {
        this.isDisabled = z;
        invalidate();
    }

    @Keep
    public void setProgress(float f) {
        if (this.progress != f) {
            this.progress = f;
            invalidate();
        }
    }
}
