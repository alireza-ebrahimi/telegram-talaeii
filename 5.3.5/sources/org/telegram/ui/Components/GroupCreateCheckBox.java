package org.telegram.ui.Components;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class GroupCreateCheckBox extends View {
    private static Paint backgroundInnerPaint = null;
    private static Paint backgroundPaint = null;
    private static Paint checkPaint = null;
    private static Paint eraser = null;
    private static Paint eraser2 = null;
    private static final float progressBounceDiff = 0.2f;
    private boolean attachedToWindow;
    private Canvas bitmapCanvas;
    private ObjectAnimator checkAnimator;
    private Bitmap drawBitmap;
    private boolean isCheckAnimation = true;
    private boolean isChecked;
    private float progress;

    public GroupCreateCheckBox(Context context) {
        super(context);
        if (backgroundPaint == null) {
            backgroundPaint = new Paint(1);
            backgroundInnerPaint = new Paint(1);
            checkPaint = new Paint(1);
            checkPaint.setStyle(Style.STROKE);
            eraser = new Paint(1);
            eraser.setColor(0);
            eraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            eraser2 = new Paint(1);
            eraser2.setColor(0);
            eraser2.setStyle(Style.STROKE);
            eraser2.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        }
        checkPaint.setStrokeWidth((float) AndroidUtilities.dp(1.5f));
        eraser2.setStrokeWidth((float) AndroidUtilities.dp(28.0f));
        this.drawBitmap = Bitmap.createBitmap(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f), Config.ARGB_4444);
        this.bitmapCanvas = new Canvas(this.drawBitmap);
        updateColors();
    }

    public void updateColors() {
        backgroundInnerPaint.setColor(Theme.getColor(Theme.key_groupcreate_checkbox));
        backgroundPaint.setColor(Theme.getColor(Theme.key_groupcreate_checkboxCheck));
        checkPaint.setColor(Theme.getColor(Theme.key_groupcreate_checkboxCheck));
        invalidate();
    }

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
        this.isCheckAnimation = newCheckedState;
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

    public boolean isChecked() {
        return this.isChecked;
    }

    protected void onDraw(Canvas canvas) {
        if (getVisibility() == 0 && this.progress != 0.0f) {
            float radDiff;
            int cx = getMeasuredWidth() / 2;
            int cy = getMeasuredHeight() / 2;
            eraser2.setStrokeWidth((float) AndroidUtilities.dp(30.0f));
            this.drawBitmap.eraseColor(0);
            float roundProgress = this.progress >= 0.5f ? 1.0f : this.progress / 0.5f;
            float checkProgress = this.progress < 0.5f ? 0.0f : (this.progress - 0.5f) / 0.5f;
            float roundProgressCheckState = this.isCheckAnimation ? this.progress : 1.0f - this.progress;
            if (roundProgressCheckState < progressBounceDiff) {
                radDiff = (((float) AndroidUtilities.dp(2.0f)) * roundProgressCheckState) / progressBounceDiff;
            } else if (roundProgressCheckState < 0.4f) {
                radDiff = ((float) AndroidUtilities.dp(2.0f)) - ((((float) AndroidUtilities.dp(2.0f)) * (roundProgressCheckState - progressBounceDiff)) / progressBounceDiff);
            } else {
                radDiff = 0.0f;
            }
            if (checkProgress != 0.0f) {
                canvas.drawCircle((float) cx, (float) cy, (((float) (cx - AndroidUtilities.dp(2.0f))) + (((float) AndroidUtilities.dp(2.0f)) * checkProgress)) - radDiff, backgroundPaint);
            }
            float innerRad = ((float) (cx - AndroidUtilities.dp(2.0f))) - radDiff;
            this.bitmapCanvas.drawCircle((float) cx, (float) cy, innerRad, backgroundInnerPaint);
            this.bitmapCanvas.drawCircle((float) cx, (float) cy, (1.0f - roundProgress) * innerRad, eraser);
            canvas.drawBitmap(this.drawBitmap, 0.0f, 0.0f, null);
            float checkSide = ((float) AndroidUtilities.dp(10.0f)) * checkProgress;
            float smallCheckSide = ((float) AndroidUtilities.dp(5.0f)) * checkProgress;
            int x = cx - AndroidUtilities.dp(1.0f);
            int y = cy + AndroidUtilities.dp(4.0f);
            float side = (float) Math.sqrt((double) ((smallCheckSide * smallCheckSide) / 2.0f));
            canvas.drawLine((float) x, (float) y, ((float) x) - side, ((float) y) - side, checkPaint);
            side = (float) Math.sqrt((double) ((checkSide * checkSide) / 2.0f));
            x -= AndroidUtilities.dp(1.2f);
            canvas.drawLine((float) x, (float) y, ((float) x) + side, ((float) y) - side, checkPaint);
        }
    }
}
