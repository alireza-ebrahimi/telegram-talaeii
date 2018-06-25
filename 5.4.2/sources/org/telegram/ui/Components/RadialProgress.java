package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RadialProgress {
    private static DecelerateInterpolator decelerateInterpolator;
    private boolean alphaForPrevious = true;
    private float animatedAlphaValue = 1.0f;
    private float animatedProgressValue = BitmapDescriptorFactory.HUE_RED;
    private float animationProgressStart = BitmapDescriptorFactory.HUE_RED;
    private Drawable checkBackgroundDrawable;
    private CheckDrawable checkDrawable;
    private RectF cicleRect = new RectF();
    private Drawable currentDrawable;
    private float currentProgress = BitmapDescriptorFactory.HUE_RED;
    private long currentProgressTime = 0;
    private boolean currentWithRound;
    private int diff = AndroidUtilities.dp(4.0f);
    private boolean drawCheckDrawable;
    private boolean hideCurrentDrawable;
    private long lastUpdateTime = 0;
    private float overrideAlpha = 1.0f;
    private View parent;
    private boolean previousCheckDrawable;
    private Drawable previousDrawable;
    private boolean previousWithRound;
    private int progressColor = -1;
    private Paint progressPaint;
    private RectF progressRect = new RectF();
    private float radOffset = BitmapDescriptorFactory.HUE_RED;

    private class CheckDrawable extends Drawable {
        private Paint paint = new Paint(1);
        private float progress;

        public CheckDrawable() {
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
            this.paint.setStrokeCap(Cap.ROUND);
            this.paint.setColor(-1);
        }

        public void draw(Canvas canvas) {
            int centerX = getBounds().centerX() - AndroidUtilities.dp(12.0f);
            int centerY = getBounds().centerY() - AndroidUtilities.dp(6.0f);
            float interpolation = this.progress != 1.0f ? RadialProgress.decelerateInterpolator.getInterpolation(this.progress) : 1.0f;
            canvas.drawLine((float) (AndroidUtilities.dp(7.0f) + centerX), (float) (((int) AndroidUtilities.dpf2(13.0f)) + centerY), (float) (((int) (((float) AndroidUtilities.dp(7.0f)) - (((float) AndroidUtilities.dp(6.0f)) * interpolation))) + centerX), (float) (centerY + ((int) (AndroidUtilities.dpf2(13.0f) - (((float) AndroidUtilities.dp(6.0f)) * interpolation)))), this.paint);
            canvas.drawLine((float) (((int) AndroidUtilities.dpf2(7.0f)) + centerX), (float) (((int) AndroidUtilities.dpf2(13.0f)) + centerY), (float) (((int) (AndroidUtilities.dpf2(7.0f) + (((float) AndroidUtilities.dp(13.0f)) * interpolation))) + centerX), (float) (centerY + ((int) (AndroidUtilities.dpf2(13.0f) - (((float) AndroidUtilities.dp(13.0f)) * interpolation)))), this.paint);
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(48.0f);
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp(48.0f);
        }

        public int getOpacity() {
            return -2;
        }

        public void resetProgress(boolean z) {
            this.progress = z ? BitmapDescriptorFactory.HUE_RED : 1.0f;
        }

        public void setAlpha(int i) {
            this.paint.setAlpha(i);
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.paint.setColorFilter(colorFilter);
        }

        public boolean updateAnimation(long j) {
            if (this.progress >= 1.0f) {
                return false;
            }
            this.progress += ((float) j) / 700.0f;
            if (this.progress > 1.0f) {
                this.progress = 1.0f;
            }
            return true;
        }
    }

    public RadialProgress(View view) {
        if (decelerateInterpolator == null) {
            decelerateInterpolator = new DecelerateInterpolator();
        }
        this.progressPaint = new Paint(1);
        this.progressPaint.setStyle(Style.STROKE);
        this.progressPaint.setStrokeCap(Cap.ROUND);
        this.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
        this.parent = view;
    }

    private void invalidateParent() {
        int dp = AndroidUtilities.dp(2.0f);
        this.parent.invalidate(((int) this.progressRect.left) - dp, ((int) this.progressRect.top) - dp, ((int) this.progressRect.right) + (dp * 2), (dp * 2) + ((int) this.progressRect.bottom));
    }

    private void updateAnimation(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (this.checkBackgroundDrawable != null && ((this.currentDrawable == this.checkBackgroundDrawable || this.previousDrawable == this.checkBackgroundDrawable) && this.checkDrawable.updateAnimation(j))) {
            invalidateParent();
        }
        if (z) {
            if (this.animatedProgressValue != 1.0f) {
                this.radOffset += ((float) (360 * j)) / 3000.0f;
                float f = this.currentProgress - this.animationProgressStart;
                if (f > BitmapDescriptorFactory.HUE_RED) {
                    this.currentProgressTime += j;
                    if (this.currentProgressTime >= 300) {
                        this.animatedProgressValue = this.currentProgress;
                        this.animationProgressStart = this.currentProgress;
                        this.currentProgressTime = 0;
                    } else {
                        this.animatedProgressValue = (f * decelerateInterpolator.getInterpolation(((float) this.currentProgressTime) / 300.0f)) + this.animationProgressStart;
                    }
                }
                invalidateParent();
            }
            if (this.animatedProgressValue >= 1.0f && this.previousDrawable != null) {
                this.animatedAlphaValue -= ((float) j) / 200.0f;
                if (this.animatedAlphaValue <= BitmapDescriptorFactory.HUE_RED) {
                    this.animatedAlphaValue = BitmapDescriptorFactory.HUE_RED;
                    this.previousDrawable = null;
                }
                invalidateParent();
            }
        } else if (this.previousDrawable != null) {
            this.animatedAlphaValue -= ((float) j) / 200.0f;
            if (this.animatedAlphaValue <= BitmapDescriptorFactory.HUE_RED) {
                this.animatedAlphaValue = BitmapDescriptorFactory.HUE_RED;
                this.previousDrawable = null;
            }
            invalidateParent();
        }
    }

    public void draw(Canvas canvas) {
        if (this.previousDrawable != null) {
            if (this.alphaForPrevious) {
                this.previousDrawable.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.overrideAlpha));
            } else {
                this.previousDrawable.setAlpha((int) (this.overrideAlpha * 255.0f));
            }
            this.previousDrawable.setBounds((int) this.progressRect.left, (int) this.progressRect.top, (int) this.progressRect.right, (int) this.progressRect.bottom);
            this.previousDrawable.draw(canvas);
        }
        if (!(this.hideCurrentDrawable || this.currentDrawable == null)) {
            if (this.previousDrawable != null) {
                this.currentDrawable.setAlpha((int) (((1.0f - this.animatedAlphaValue) * 255.0f) * this.overrideAlpha));
            } else {
                this.currentDrawable.setAlpha((int) (this.overrideAlpha * 255.0f));
            }
            this.currentDrawable.setBounds((int) this.progressRect.left, (int) this.progressRect.top, (int) this.progressRect.right, (int) this.progressRect.bottom);
            this.currentDrawable.draw(canvas);
        }
        if (this.currentWithRound || this.previousWithRound) {
            this.progressPaint.setColor(this.progressColor);
            if (this.previousWithRound) {
                this.progressPaint.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.overrideAlpha));
            } else {
                this.progressPaint.setAlpha((int) (this.overrideAlpha * 255.0f));
            }
            this.cicleRect.set(this.progressRect.left + ((float) this.diff), this.progressRect.top + ((float) this.diff), this.progressRect.right - ((float) this.diff), this.progressRect.bottom - ((float) this.diff));
            canvas.drawArc(this.cicleRect, this.radOffset - 0.049804688f, Math.max(4.0f, 360.0f * this.animatedProgressValue), false, this.progressPaint);
            updateAnimation(true);
            return;
        }
        updateAnimation(false);
    }

    public float getAlpha() {
        return (this.previousDrawable == null && this.currentDrawable == null) ? BitmapDescriptorFactory.HUE_RED : this.animatedAlphaValue;
    }

    public RectF getProgressRect() {
        return this.progressRect;
    }

    public void setAlphaForPrevious(boolean z) {
        this.alphaForPrevious = z;
    }

    public void setBackground(Drawable drawable, boolean z, boolean z2) {
        this.lastUpdateTime = System.currentTimeMillis();
        if (!z2 || this.currentDrawable == drawable) {
            this.previousDrawable = null;
            this.previousWithRound = false;
        } else {
            this.previousDrawable = this.currentDrawable;
            this.previousWithRound = this.currentWithRound;
            this.animatedAlphaValue = 1.0f;
            setProgress(1.0f, z2);
        }
        this.currentWithRound = z;
        this.currentDrawable = drawable;
        if (z2) {
            invalidateParent();
        } else {
            this.parent.invalidate();
        }
    }

    public void setCheckBackground(boolean z, boolean z2) {
        if (this.checkDrawable == null) {
            this.checkDrawable = new CheckDrawable();
            this.checkBackgroundDrawable = Theme.createCircleDrawableWithIcon(AndroidUtilities.dp(48.0f), this.checkDrawable, 0);
        }
        Theme.setCombinedDrawableColor(this.checkBackgroundDrawable, Theme.getColor(Theme.key_chat_mediaLoaderPhoto), false);
        Theme.setCombinedDrawableColor(this.checkBackgroundDrawable, Theme.getColor(Theme.key_chat_mediaLoaderPhotoIcon), true);
        if (this.currentDrawable != this.checkBackgroundDrawable) {
            setBackground(this.checkBackgroundDrawable, z, z2);
            this.checkDrawable.resetProgress(z2);
        }
    }

    public void setDiff(int i) {
        this.diff = i;
    }

    public void setHideCurrentDrawable(boolean z) {
        this.hideCurrentDrawable = z;
    }

    public void setOverrideAlpha(float f) {
        this.overrideAlpha = f;
    }

    public void setProgress(float f, boolean z) {
        if (!(f == 1.0f || this.animatedAlphaValue == BitmapDescriptorFactory.HUE_RED || this.previousDrawable == null)) {
            this.animatedAlphaValue = BitmapDescriptorFactory.HUE_RED;
            this.previousDrawable = null;
        }
        if (z) {
            if (this.animatedProgressValue > f) {
                this.animatedProgressValue = f;
            }
            this.animationProgressStart = this.animatedProgressValue;
        } else {
            this.animatedProgressValue = f;
            this.animationProgressStart = f;
        }
        this.currentProgress = f;
        this.currentProgressTime = 0;
        invalidateParent();
    }

    public void setProgressColor(int i) {
        this.progressColor = i;
    }

    public void setProgressRect(int i, int i2, int i3, int i4) {
        this.progressRect.set((float) i, (float) i2, (float) i3, (float) i4);
    }

    public void setStrikeWidth(int i) {
        this.progressPaint.setStrokeWidth((float) i);
    }

    public boolean swapBackground(Drawable drawable) {
        if (this.currentDrawable == drawable) {
            return false;
        }
        this.currentDrawable = drawable;
        return true;
    }
}
