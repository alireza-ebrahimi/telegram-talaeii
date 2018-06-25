package org.telegram.ui.Components;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Keep;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class Switch2 extends View {
    private static Bitmap drawBitmap;
    private boolean attachedToWindow;
    private ObjectAnimator checkAnimator;
    private boolean isChecked;
    private boolean isDisabled;
    private Paint paint;
    private Paint paint2;
    private float progress;
    private RectF rectF = new RectF();

    public Switch2(Context context) {
        super(context);
        if (drawBitmap == null || drawBitmap.getWidth() != AndroidUtilities.dp(24.0f)) {
            drawBitmap = Bitmap.createBitmap(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f), Config.ARGB_8888);
            Canvas canvas = new Canvas(drawBitmap);
            Paint paint = new Paint(1);
            paint.setShadowLayer((float) AndroidUtilities.dp(2.0f), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            canvas.drawCircle((float) AndroidUtilities.dp(12.0f), (float) AndroidUtilities.dp(12.0f), (float) AndroidUtilities.dp(9.0f), paint);
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
        }
        this.paint = new Paint(1);
        this.paint2 = new Paint(1);
        this.paint2.setStyle(Style.STROKE);
        this.paint2.setStrokeCap(Cap.ROUND);
        this.paint2.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
    }

    private void animateToCheckedState(boolean z) {
        String str = "progress";
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        this.checkAnimator = ObjectAnimator.ofFloat(this, str, fArr);
        this.checkAnimator.setDuration(250);
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
            int dp = AndroidUtilities.dp(36.0f);
            AndroidUtilities.dp(20.0f);
            int measuredWidth = (getMeasuredWidth() - dp) / 2;
            int measuredHeight = (getMeasuredHeight() - AndroidUtilities.dp(14.0f)) / 2;
            int dp2 = (((int) (((float) (dp - AndroidUtilities.dp(14.0f))) * this.progress)) + measuredWidth) + AndroidUtilities.dp(7.0f);
            int measuredHeight2 = getMeasuredHeight() / 2;
            this.paint.setColor(((((((int) (255.0f + (-95.0f * this.progress))) & 255) << 16) | Theme.ACTION_BAR_VIDEO_EDIT_COLOR) | ((((int) (176.0f + (38.0f * this.progress))) & 255) << 8)) | (((int) (173.0f + (77.0f * this.progress))) & 255));
            this.rectF.set((float) measuredWidth, (float) measuredHeight, (float) (dp + measuredWidth), (float) (AndroidUtilities.dp(14.0f) + measuredHeight));
            canvas.drawRoundRect(this.rectF, (float) AndroidUtilities.dp(7.0f), (float) AndroidUtilities.dp(7.0f), this.paint);
            this.paint.setColor(((((((int) (219.0f + (-151.0f * this.progress))) & 255) << 16) | Theme.ACTION_BAR_VIDEO_EDIT_COLOR) | ((((int) (88.0f + (80.0f * this.progress))) & 255) << 8)) | (((int) (92.0f + (142.0f * this.progress))) & 255));
            canvas.drawBitmap(drawBitmap, (float) (dp2 - AndroidUtilities.dp(12.0f)), (float) (measuredHeight2 - AndroidUtilities.dp(11.0f)), null);
            canvas.drawCircle((float) dp2, (float) measuredHeight2, (float) AndroidUtilities.dp(10.0f), this.paint);
            this.paint2.setColor(-1);
            int dp3 = (int) (((float) dp2) - (((float) AndroidUtilities.dp(10.8f)) - (((float) AndroidUtilities.dp(1.3f)) * this.progress)));
            int dp4 = (int) (((float) measuredHeight2) - (((float) AndroidUtilities.dp(8.5f)) - (((float) AndroidUtilities.dp(0.5f)) * this.progress)));
            dp = ((int) AndroidUtilities.dpf2(4.6f)) + dp3;
            measuredWidth = (int) (AndroidUtilities.dpf2(9.5f) + ((float) dp4));
            measuredHeight = AndroidUtilities.dp(2.0f) + dp;
            measuredHeight2 = ((int) AndroidUtilities.dpf2(7.5f)) + dp3;
            int dpf2 = ((int) AndroidUtilities.dpf2(5.4f)) + dp4;
            int dp5 = AndroidUtilities.dp(7.0f) + measuredHeight2;
            int dp6 = AndroidUtilities.dp(7.0f) + dpf2;
            Canvas canvas2 = canvas;
            canvas2.drawLine((float) ((int) ((((float) (dp - measuredHeight2)) * this.progress) + ((float) measuredHeight2))), (float) ((int) ((((float) (measuredWidth - dpf2)) * this.progress) + ((float) dpf2))), (float) ((int) (((float) dp5) + (((float) (measuredHeight - dp5)) * this.progress))), (float) ((int) (((float) dp6) + (((float) ((AndroidUtilities.dp(2.0f) + measuredWidth) - dp6)) * this.progress))), this.paint2);
            dp = ((int) AndroidUtilities.dpf2(7.5f)) + dp3;
            measuredHeight = ((int) AndroidUtilities.dpf2(12.5f)) + dp4;
            canvas2 = canvas;
            canvas2.drawLine((float) dp, (float) measuredHeight, (float) (dp + AndroidUtilities.dp(7.0f)), (float) (measuredHeight - AndroidUtilities.dp(7.0f)), this.paint2);
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
