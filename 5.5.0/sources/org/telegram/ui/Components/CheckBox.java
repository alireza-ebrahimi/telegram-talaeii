package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

public class CheckBox extends View {
    private static Paint backgroundPaint = null;
    private static Paint checkPaint = null;
    private static Paint eraser = null;
    private static Paint eraser2 = null;
    private static Paint paint = null;
    private static final float progressBounceDiff = 0.2f;
    private static TextPaint textPaint;
    private boolean attachedToWindow;
    private Canvas bitmapCanvas;
    private ObjectAnimator checkAnimator;
    private Bitmap checkBitmap;
    private Canvas checkCanvas;
    private Drawable checkDrawable;
    private int checkOffset;
    private String checkedText;
    private int color;
    private boolean drawBackground;
    private Bitmap drawBitmap;
    private boolean hasBorder;
    private boolean isCheckAnimation = true;
    private boolean isChecked;
    private float progress;
    private int size = 22;

    /* renamed from: org.telegram.ui.Components.CheckBox$1 */
    class C43781 extends AnimatorListenerAdapter {
        C43781() {
        }

        public void onAnimationEnd(Animator animator) {
            if (animator.equals(CheckBox.this.checkAnimator)) {
                CheckBox.this.checkAnimator = null;
            }
            if (!CheckBox.this.isChecked) {
                CheckBox.this.checkedText = null;
            }
        }
    }

    public CheckBox(Context context, int i) {
        super(context);
        if (paint == null) {
            paint = new Paint(1);
            eraser = new Paint(1);
            eraser.setColor(0);
            eraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            eraser2 = new Paint(1);
            eraser2.setColor(0);
            eraser2.setStyle(Style.STROKE);
            eraser2.setStrokeWidth((float) AndroidUtilities.dp(28.0f));
            eraser2.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            backgroundPaint = new Paint(1);
            backgroundPaint.setColor(-1);
            backgroundPaint.setStyle(Style.STROKE);
            backgroundPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            textPaint = new TextPaint(1);
            textPaint.setTextSize((float) AndroidUtilities.dp(18.0f));
            textPaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        }
        this.checkDrawable = context.getResources().getDrawable(i).mutate();
    }

    private void animateToCheckedState(boolean z) {
        this.isCheckAnimation = z;
        String str = "progress";
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        this.checkAnimator = ObjectAnimator.ofFloat(this, str, fArr);
        this.checkAnimator.addListener(new C43781());
        this.checkAnimator.setDuration(300);
        this.checkAnimator.start();
    }

    private void cancelCheckAnimator() {
        if (this.checkAnimator != null) {
            this.checkAnimator.cancel();
            this.checkAnimator = null;
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
            if (this.drawBackground || this.progress != BitmapDescriptorFactory.HUE_RED) {
                eraser2.setStrokeWidth((float) AndroidUtilities.dp((float) (this.size + 6)));
                this.drawBitmap.eraseColor(0);
                float measuredWidth = (float) (getMeasuredWidth() / 2);
                float f = this.progress >= 0.5f ? 1.0f : this.progress / 0.5f;
                float f2 = this.progress < 0.5f ? BitmapDescriptorFactory.HUE_RED : (this.progress - 0.5f) / 0.5f;
                float f3 = this.isCheckAnimation ? this.progress : 1.0f - this.progress;
                if (f3 < progressBounceDiff) {
                    measuredWidth -= (f3 * ((float) AndroidUtilities.dp(2.0f))) / progressBounceDiff;
                } else if (f3 < 0.4f) {
                    measuredWidth -= ((float) AndroidUtilities.dp(2.0f)) - (((f3 - progressBounceDiff) * ((float) AndroidUtilities.dp(2.0f))) / progressBounceDiff);
                }
                if (this.drawBackground) {
                    paint.setColor(1140850688);
                    canvas.drawCircle((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2), measuredWidth - ((float) AndroidUtilities.dp(1.0f)), paint);
                    canvas.drawCircle((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2), measuredWidth - ((float) AndroidUtilities.dp(1.0f)), backgroundPaint);
                }
                paint.setColor(this.color);
                if (this.hasBorder) {
                    measuredWidth -= (float) AndroidUtilities.dp(2.0f);
                }
                this.bitmapCanvas.drawCircle((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2), measuredWidth, paint);
                this.bitmapCanvas.drawCircle((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2), (1.0f - f) * measuredWidth, eraser);
                canvas.drawBitmap(this.drawBitmap, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, null);
                this.checkBitmap.eraseColor(0);
                if (this.checkedText != null) {
                    this.checkCanvas.drawText(this.checkedText, (float) ((getMeasuredWidth() - ((int) Math.ceil((double) textPaint.measureText(this.checkedText)))) / 2), (float) AndroidUtilities.dp(21.0f), textPaint);
                } else {
                    int intrinsicWidth = this.checkDrawable.getIntrinsicWidth();
                    int intrinsicHeight = this.checkDrawable.getIntrinsicHeight();
                    int measuredWidth2 = (getMeasuredWidth() - intrinsicWidth) / 2;
                    int measuredHeight = (getMeasuredHeight() - intrinsicHeight) / 2;
                    this.checkDrawable.setBounds(measuredWidth2, this.checkOffset + measuredHeight, intrinsicWidth + measuredWidth2, (intrinsicHeight + measuredHeight) + this.checkOffset);
                    this.checkDrawable.draw(this.checkCanvas);
                }
                this.checkCanvas.drawCircle((float) ((getMeasuredWidth() / 2) - AndroidUtilities.dp(2.5f)), (float) ((getMeasuredHeight() / 2) + AndroidUtilities.dp(4.0f)), (1.0f - f2) * ((float) ((getMeasuredWidth() + AndroidUtilities.dp(6.0f)) / 2)), eraser2);
                canvas.drawBitmap(this.checkBitmap, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, null);
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setBackgroundColor(int i) {
        this.color = i;
        invalidate();
    }

    public void setCheckColor(int i) {
        this.checkDrawable.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
        textPaint.setColor(i);
        invalidate();
    }

    public void setCheckOffset(int i) {
        this.checkOffset = i;
    }

    public void setChecked(int i, boolean z, boolean z2) {
        if (i >= 0) {
            this.checkedText = TtmlNode.ANONYMOUS_REGION_ID + (i + 1);
        }
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

    public void setChecked(boolean z, boolean z2) {
        setChecked(-1, z, z2);
    }

    public void setColor(int i, int i2) {
        this.color = i;
        this.checkDrawable.setColorFilter(new PorterDuffColorFilter(i2, Mode.MULTIPLY));
        textPaint.setColor(i2);
        invalidate();
    }

    public void setDrawBackground(boolean z) {
        this.drawBackground = z;
    }

    public void setHasBorder(boolean z) {
        this.hasBorder = z;
    }

    public void setNum(int i) {
        if (i >= 0) {
            this.checkedText = TtmlNode.ANONYMOUS_REGION_ID + (i + 1);
        } else if (this.checkAnimator == null) {
            this.checkedText = null;
        }
        invalidate();
    }

    public void setProgress(float f) {
        if (this.progress != f) {
            this.progress = f;
            invalidate();
        }
    }

    public void setSize(int i) {
        this.size = i;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0 && this.drawBitmap == null) {
            this.drawBitmap = Bitmap.createBitmap(AndroidUtilities.dp((float) this.size), AndroidUtilities.dp((float) this.size), Config.ARGB_4444);
            this.bitmapCanvas = new Canvas(this.drawBitmap);
            this.checkBitmap = Bitmap.createBitmap(AndroidUtilities.dp((float) this.size), AndroidUtilities.dp((float) this.size), Config.ARGB_4444);
            this.checkCanvas = new Canvas(this.checkBitmap);
        }
    }
}
