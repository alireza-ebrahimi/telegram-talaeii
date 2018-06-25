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
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RadialProgress {
    private static DecelerateInterpolator decelerateInterpolator;
    private boolean alphaForPrevious = true;
    private float animatedAlphaValue = 1.0f;
    private float animatedProgressValue = 0.0f;
    private float animationProgressStart = 0.0f;
    private Drawable checkBackgroundDrawable;
    private CheckDrawable checkDrawable;
    private RectF cicleRect = new RectF();
    private Drawable currentDrawable;
    private float currentProgress = 0.0f;
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
    private float radOffset = 0.0f;

    private class CheckDrawable extends Drawable {
        private Paint paint = new Paint(1);
        private float progress;

        public CheckDrawable() {
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
            this.paint.setStrokeCap(Cap.ROUND);
            this.paint.setColor(-1);
        }

        public void resetProgress(boolean animated) {
            this.progress = animated ? 0.0f : 1.0f;
        }

        public boolean updateAnimation(long dt) {
            if (this.progress >= 1.0f) {
                return false;
            }
            this.progress += ((float) dt) / 700.0f;
            if (this.progress > 1.0f) {
                this.progress = 1.0f;
            }
            return true;
        }

        public void draw(Canvas canvas) {
            float p = 1.0f;
            int x = getBounds().centerX() - AndroidUtilities.dp(12.0f);
            int y = getBounds().centerY() - AndroidUtilities.dp(6.0f);
            if (this.progress != 1.0f) {
                p = RadialProgress.decelerateInterpolator.getInterpolation(this.progress);
            }
            Canvas canvas2 = canvas;
            canvas2.drawLine((float) (AndroidUtilities.dp(7.0f) + x), (float) (((int) AndroidUtilities.dpf2(13.0f)) + y), (float) (x + ((int) (((float) AndroidUtilities.dp(7.0f)) - (((float) AndroidUtilities.dp(6.0f)) * p)))), (float) (y + ((int) (AndroidUtilities.dpf2(13.0f) - (((float) AndroidUtilities.dp(6.0f)) * p)))), this.paint);
            canvas2 = canvas;
            canvas2.drawLine((float) (((int) AndroidUtilities.dpf2(7.0f)) + x), (float) (((int) AndroidUtilities.dpf2(13.0f)) + y), (float) (x + ((int) (AndroidUtilities.dpf2(7.0f) + (((float) AndroidUtilities.dp(13.0f)) * p)))), (float) (y + ((int) (AndroidUtilities.dpf2(13.0f) - (((float) AndroidUtilities.dp(13.0f)) * p)))), this.paint);
        }

        public void setAlpha(int alpha) {
            this.paint.setAlpha(alpha);
        }

        public void setColorFilter(ColorFilter cf) {
            this.paint.setColorFilter(cf);
        }

        public int getOpacity() {
            return -2;
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp(48.0f);
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(48.0f);
        }
    }

    public RadialProgress(View parentView) {
        if (decelerateInterpolator == null) {
            decelerateInterpolator = new DecelerateInterpolator();
        }
        this.progressPaint = new Paint(1);
        this.progressPaint.setStyle(Style.STROKE);
        this.progressPaint.setStrokeCap(Cap.ROUND);
        this.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
        this.parent = parentView;
    }

    public void setStrikeWidth(int width) {
        this.progressPaint.setStrokeWidth((float) width);
    }

    public void setProgressRect(int left, int top, int right, int bottom) {
        this.progressRect.set((float) left, (float) top, (float) right, (float) bottom);
    }

    public RectF getProgressRect() {
        return this.progressRect;
    }

    public void setAlphaForPrevious(boolean value) {
        this.alphaForPrevious = value;
    }

    private void updateAnimation(boolean progress) {
        long newTime = System.currentTimeMillis();
        long dt = newTime - this.lastUpdateTime;
        this.lastUpdateTime = newTime;
        if (this.checkBackgroundDrawable != null && ((this.currentDrawable == this.checkBackgroundDrawable || this.previousDrawable == this.checkBackgroundDrawable) && this.checkDrawable.updateAnimation(dt))) {
            invalidateParent();
        }
        if (progress) {
            if (this.animatedProgressValue != 1.0f) {
                this.radOffset += ((float) (360 * dt)) / 3000.0f;
                float progressDiff = this.currentProgress - this.animationProgressStart;
                if (progressDiff > 0.0f) {
                    this.currentProgressTime += dt;
                    if (this.currentProgressTime >= 300) {
                        this.animatedProgressValue = this.currentProgress;
                        this.animationProgressStart = this.currentProgress;
                        this.currentProgressTime = 0;
                    } else {
                        this.animatedProgressValue = this.animationProgressStart + (decelerateInterpolator.getInterpolation(((float) this.currentProgressTime) / 300.0f) * progressDiff);
                    }
                }
                invalidateParent();
            }
            if (this.animatedProgressValue >= 1.0f && this.previousDrawable != null) {
                this.animatedAlphaValue -= ((float) dt) / 200.0f;
                if (this.animatedAlphaValue <= 0.0f) {
                    this.animatedAlphaValue = 0.0f;
                    this.previousDrawable = null;
                }
                invalidateParent();
            }
        } else if (this.previousDrawable != null) {
            this.animatedAlphaValue -= ((float) dt) / 200.0f;
            if (this.animatedAlphaValue <= 0.0f) {
                this.animatedAlphaValue = 0.0f;
                this.previousDrawable = null;
            }
            invalidateParent();
        }
    }

    public void setDiff(int value) {
        this.diff = value;
    }

    public void setProgressColor(int color) {
        this.progressColor = color;
    }

    public void setHideCurrentDrawable(boolean value) {
        this.hideCurrentDrawable = value;
    }

    public void setProgress(float value, boolean animated) {
        if (!(value == 1.0f || this.animatedAlphaValue == 0.0f || this.previousDrawable == null)) {
            this.animatedAlphaValue = 0.0f;
            this.previousDrawable = null;
        }
        if (animated) {
            if (this.animatedProgressValue > value) {
                this.animatedProgressValue = value;
            }
            this.animationProgressStart = this.animatedProgressValue;
        } else {
            this.animatedProgressValue = value;
            this.animationProgressStart = value;
        }
        this.currentProgress = value;
        this.currentProgressTime = 0;
        invalidateParent();
    }

    private void invalidateParent() {
        int offset = AndroidUtilities.dp(2.0f);
        this.parent.invalidate(((int) this.progressRect.left) - offset, ((int) this.progressRect.top) - offset, ((int) this.progressRect.right) + (offset * 2), ((int) this.progressRect.bottom) + (offset * 2));
    }

    public void setCheckBackground(boolean withRound, boolean animated) {
        if (this.checkDrawable == null) {
            this.checkDrawable = new CheckDrawable();
            this.checkBackgroundDrawable = Theme.createCircleDrawableWithIcon(AndroidUtilities.dp(48.0f), this.checkDrawable, 0);
        }
        Theme.setCombinedDrawableColor(this.checkBackgroundDrawable, Theme.getColor(Theme.key_chat_mediaLoaderPhoto), false);
        Theme.setCombinedDrawableColor(this.checkBackgroundDrawable, Theme.getColor(Theme.key_chat_mediaLoaderPhotoIcon), true);
        if (this.currentDrawable != this.checkBackgroundDrawable) {
            setBackground(this.checkBackgroundDrawable, withRound, animated);
            this.checkDrawable.resetProgress(animated);
        }
    }

    public void setBackground(Drawable drawable, boolean withRound, boolean animated) {
        this.lastUpdateTime = System.currentTimeMillis();
        if (!animated || this.currentDrawable == drawable) {
            this.previousDrawable = null;
            this.previousWithRound = false;
        } else {
            this.previousDrawable = this.currentDrawable;
            this.previousWithRound = this.currentWithRound;
            this.animatedAlphaValue = 1.0f;
            setProgress(1.0f, animated);
        }
        this.currentWithRound = withRound;
        this.currentDrawable = drawable;
        if (animated) {
            invalidateParent();
        } else {
            this.parent.invalidate();
        }
    }

    public boolean swapBackground(Drawable drawable) {
        if (this.currentDrawable == drawable) {
            return false;
        }
        this.currentDrawable = drawable;
        return true;
    }

    public float getAlpha() {
        return (this.previousDrawable == null && this.currentDrawable == null) ? 0.0f : this.animatedAlphaValue;
    }

    public void setOverrideAlpha(float alpha) {
        this.overrideAlpha = alpha;
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
}
