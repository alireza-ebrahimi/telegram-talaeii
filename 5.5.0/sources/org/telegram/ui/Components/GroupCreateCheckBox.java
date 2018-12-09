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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    private void animateToCheckedState(boolean z) {
        this.isCheckAnimation = z;
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
        if (getVisibility() == 0 && this.progress != BitmapDescriptorFactory.HUE_RED) {
            int measuredWidth = getMeasuredWidth() / 2;
            int measuredHeight = getMeasuredHeight() / 2;
            eraser2.setStrokeWidth((float) AndroidUtilities.dp(30.0f));
            this.drawBitmap.eraseColor(0);
            float f = this.progress >= 0.5f ? 1.0f : this.progress / 0.5f;
            float f2 = this.progress < 0.5f ? BitmapDescriptorFactory.HUE_RED : (this.progress - 0.5f) / 0.5f;
            float f3 = this.isCheckAnimation ? this.progress : 1.0f - this.progress;
            f3 = f3 < progressBounceDiff ? (f3 * ((float) AndroidUtilities.dp(2.0f))) / progressBounceDiff : f3 < 0.4f ? ((float) AndroidUtilities.dp(2.0f)) - (((f3 - progressBounceDiff) * ((float) AndroidUtilities.dp(2.0f))) / progressBounceDiff) : BitmapDescriptorFactory.HUE_RED;
            if (f2 != BitmapDescriptorFactory.HUE_RED) {
                canvas.drawCircle((float) measuredWidth, (float) measuredHeight, (((float) (measuredWidth - AndroidUtilities.dp(2.0f))) + (((float) AndroidUtilities.dp(2.0f)) * f2)) - f3, backgroundPaint);
            }
            f3 = ((float) (measuredWidth - AndroidUtilities.dp(2.0f))) - f3;
            this.bitmapCanvas.drawCircle((float) measuredWidth, (float) measuredHeight, f3, backgroundInnerPaint);
            this.bitmapCanvas.drawCircle((float) measuredWidth, (float) measuredHeight, (1.0f - f) * f3, eraser);
            canvas.drawBitmap(this.drawBitmap, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, null);
            float dp = ((float) AndroidUtilities.dp(10.0f)) * f2;
            f = ((float) AndroidUtilities.dp(5.0f)) * f2;
            int dp2 = measuredWidth - AndroidUtilities.dp(1.0f);
            measuredHeight += AndroidUtilities.dp(4.0f);
            f = (float) Math.sqrt((double) ((f * f) / 2.0f));
            canvas.drawLine((float) dp2, (float) measuredHeight, ((float) dp2) - f, ((float) measuredHeight) - f, checkPaint);
            f = (float) Math.sqrt((double) ((dp * dp) / 2.0f));
            int dp3 = dp2 - AndroidUtilities.dp(1.2f);
            canvas.drawLine((float) dp3, (float) measuredHeight, ((float) dp3) + f, ((float) measuredHeight) - f, checkPaint);
        }
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

    public void setProgress(float f) {
        if (this.progress != f) {
            this.progress = f;
            invalidate();
        }
    }

    public void updateColors() {
        backgroundInnerPaint.setColor(Theme.getColor(Theme.key_groupcreate_checkbox));
        backgroundPaint.setColor(Theme.getColor(Theme.key_groupcreate_checkboxCheck));
        checkPaint.setColor(Theme.getColor(Theme.key_groupcreate_checkboxCheck));
        invalidate();
    }
}
