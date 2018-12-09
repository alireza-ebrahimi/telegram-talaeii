package org.telegram.ui.Components.Crop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class CropAreaView extends View {
    private Control activeControl;
    private RectF actualRect = new RectF();
    private Animator animator;
    private RectF bottomEdge = new RectF();
    private RectF bottomLeftCorner = new RectF();
    private float bottomPadding;
    private RectF bottomRightCorner = new RectF();
    Paint dimPaint = new Paint();
    private boolean dimVisibile = true;
    Paint framePaint;
    private boolean frameVisible = true;
    private Animator gridAnimator;
    private float gridProgress;
    private GridType gridType = GridType.NONE;
    Paint handlePaint;
    AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
    private boolean isDragging;
    private RectF leftEdge = new RectF();
    Paint linePaint;
    private AreaViewListener listener;
    private float lockAspectRatio;
    private float minWidth = ((float) AndroidUtilities.dp(32.0f));
    private GridType previousGridType;
    private int previousX;
    private int previousY;
    private RectF rightEdge = new RectF();
    Paint shadowPaint;
    private float sidePadding = ((float) AndroidUtilities.dp(16.0f));
    private RectF tempRect = new RectF();
    private RectF topEdge = new RectF();
    private RectF topLeftCorner = new RectF();
    private RectF topRightCorner = new RectF();

    /* renamed from: org.telegram.ui.Components.Crop.CropAreaView$1 */
    class C43791 extends AnimatorListenerAdapter {
        C43791() {
        }

        public void onAnimationEnd(Animator animator) {
            CropAreaView.this.gridAnimator = null;
        }
    }

    interface AreaViewListener {
        void onAreaChange();

        void onAreaChangeBegan();

        void onAreaChangeEnded();
    }

    private enum Control {
        NONE,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        LEFT,
        BOTTOM,
        RIGHT
    }

    enum GridType {
        NONE,
        MINOR,
        MAJOR
    }

    public CropAreaView(Context context) {
        super(context);
        this.dimPaint.setColor(-872415232);
        this.shadowPaint = new Paint();
        this.shadowPaint.setStyle(Style.FILL);
        this.shadowPaint.setColor(436207616);
        this.shadowPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.linePaint = new Paint();
        this.linePaint.setStyle(Style.FILL);
        this.linePaint.setColor(-1);
        this.linePaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
        this.handlePaint = new Paint();
        this.handlePaint.setStyle(Style.FILL);
        this.handlePaint.setColor(-1);
        this.framePaint = new Paint();
        this.framePaint.setStyle(Style.FILL);
        this.framePaint.setColor(-1291845633);
    }

    private void constrainRectByHeight(RectF rectF, float f) {
        float height = rectF.height();
        rectF.right = (height * f) + rectF.left;
        rectF.bottom = height + rectF.top;
    }

    private void constrainRectByWidth(RectF rectF, float f) {
        float width = rectF.width();
        float f2 = width / f;
        rectF.right = width + rectF.left;
        rectF.bottom = rectF.top + f2;
    }

    private float getGridProgress() {
        return this.gridProgress;
    }

    private void setCropBottom(float f) {
        this.actualRect.bottom = f;
        invalidate();
    }

    private void setCropLeft(float f) {
        this.actualRect.left = f;
        invalidate();
    }

    private void setCropRight(float f) {
        this.actualRect.right = f;
        invalidate();
    }

    private void setCropTop(float f) {
        this.actualRect.top = f;
        invalidate();
    }

    private void setGridProgress(float f) {
        this.gridProgress = f;
        invalidate();
    }

    private void updateTouchAreas() {
        int dp = AndroidUtilities.dp(16.0f);
        this.topLeftCorner.set(this.actualRect.left - ((float) dp), this.actualRect.top - ((float) dp), this.actualRect.left + ((float) dp), this.actualRect.top + ((float) dp));
        this.topRightCorner.set(this.actualRect.right - ((float) dp), this.actualRect.top - ((float) dp), this.actualRect.right + ((float) dp), this.actualRect.top + ((float) dp));
        this.bottomLeftCorner.set(this.actualRect.left - ((float) dp), this.actualRect.bottom - ((float) dp), this.actualRect.left + ((float) dp), this.actualRect.bottom + ((float) dp));
        this.bottomRightCorner.set(this.actualRect.right - ((float) dp), this.actualRect.bottom - ((float) dp), this.actualRect.right + ((float) dp), this.actualRect.bottom + ((float) dp));
        this.topEdge.set(this.actualRect.left + ((float) dp), this.actualRect.top - ((float) dp), this.actualRect.right - ((float) dp), this.actualRect.top + ((float) dp));
        this.leftEdge.set(this.actualRect.left - ((float) dp), this.actualRect.top + ((float) dp), this.actualRect.left + ((float) dp), this.actualRect.bottom - ((float) dp));
        this.rightEdge.set(this.actualRect.right - ((float) dp), this.actualRect.top + ((float) dp), this.actualRect.right + ((float) dp), this.actualRect.bottom - ((float) dp));
        this.bottomEdge.set(this.actualRect.left + ((float) dp), this.actualRect.bottom - ((float) dp), this.actualRect.right - ((float) dp), ((float) dp) + this.actualRect.bottom);
    }

    public void calculateRect(RectF rectF, float f) {
        float f2;
        float f3 = (float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
        float measuredHeight = (((float) getMeasuredHeight()) - this.bottomPadding) - f3;
        float measuredWidth = ((float) getMeasuredWidth()) / measuredHeight;
        float min = Math.min((float) getMeasuredWidth(), measuredHeight) - (this.sidePadding * 2.0f);
        float measuredWidth2 = ((float) getMeasuredWidth()) - (this.sidePadding * 2.0f);
        float f4 = measuredHeight - (this.sidePadding * 2.0f);
        float measuredWidth3 = ((float) getMeasuredWidth()) / 2.0f;
        f3 += measuredHeight / 2.0f;
        if (((double) Math.abs(1.0f - f)) < 1.0E-4d) {
            f2 = measuredWidth3 - (min / 2.0f);
            measuredWidth = f3 - (min / 2.0f);
            measuredHeight = (min / 2.0f) + measuredWidth3;
            f3 += min / 2.0f;
        } else if (f > measuredWidth) {
            f2 = measuredWidth3 - (measuredWidth2 / 2.0f);
            measuredWidth = f3 - ((measuredWidth2 / f) / 2.0f);
            measuredHeight = (measuredWidth2 / 2.0f) + measuredWidth3;
            f3 += (measuredWidth2 / f) / 2.0f;
        } else {
            f2 = measuredWidth3 - ((f4 * f) / 2.0f);
            measuredWidth = f3 - (f4 / 2.0f);
            measuredHeight = ((f4 * f) / 2.0f) + measuredWidth3;
            f3 += f4 / 2.0f;
        }
        rectF.set(f2, measuredWidth, measuredHeight, f3);
    }

    public void fill(final RectF rectF, Animator animator, boolean z) {
        if (z) {
            if (this.animator != null) {
                this.animator.cancel();
                this.animator = null;
            }
            Animator animatorSet = new AnimatorSet();
            this.animator = animatorSet;
            animatorSet.setDuration(300);
            Animator[] animatorArr = new Animator[5];
            animatorArr[0] = ObjectAnimator.ofFloat(this, "cropLeft", new float[]{rectF.left});
            animatorArr[0].setInterpolator(this.interpolator);
            animatorArr[1] = ObjectAnimator.ofFloat(this, "cropTop", new float[]{rectF.top});
            animatorArr[1].setInterpolator(this.interpolator);
            animatorArr[2] = ObjectAnimator.ofFloat(this, "cropRight", new float[]{rectF.right});
            animatorArr[2].setInterpolator(this.interpolator);
            animatorArr[3] = ObjectAnimator.ofFloat(this, "cropBottom", new float[]{rectF.bottom});
            animatorArr[3].setInterpolator(this.interpolator);
            animatorArr[4] = animator;
            animatorArr[4].setInterpolator(this.interpolator);
            animatorSet.playTogether(animatorArr);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    CropAreaView.this.setActualRect(rectF);
                    CropAreaView.this.animator = null;
                }
            });
            animatorSet.start();
            return;
        }
        setActualRect(rectF);
    }

    public float getAspectRatio() {
        return (this.actualRect.right - this.actualRect.left) / (this.actualRect.bottom - this.actualRect.top);
    }

    public float getCropBottom() {
        return this.actualRect.bottom;
    }

    public float getCropCenterX() {
        return this.actualRect.left + ((this.actualRect.right - this.actualRect.left) / 2.0f);
    }

    public float getCropCenterY() {
        return this.actualRect.top + ((this.actualRect.bottom - this.actualRect.top) / 2.0f);
    }

    public float getCropHeight() {
        return this.actualRect.bottom - this.actualRect.top;
    }

    public float getCropLeft() {
        return this.actualRect.left;
    }

    public void getCropRect(RectF rectF) {
        rectF.set(this.actualRect);
    }

    public float getCropRight() {
        return this.actualRect.right;
    }

    public float getCropTop() {
        return this.actualRect.top;
    }

    public float getCropWidth() {
        return this.actualRect.right - this.actualRect.left;
    }

    public Interpolator getInterpolator() {
        return this.interpolator;
    }

    public float getLockAspectRatio() {
        return this.lockAspectRatio;
    }

    public RectF getTargetRectToFill() {
        RectF rectF = new RectF();
        calculateRect(rectF, getAspectRatio());
        return rectF;
    }

    public boolean isDragging() {
        return this.isDragging;
    }

    protected void onDraw(Canvas canvas) {
        int dp = AndroidUtilities.dp(2.0f);
        int dp2 = AndroidUtilities.dp(16.0f);
        int dp3 = AndroidUtilities.dp(3.0f);
        int i = ((int) this.actualRect.left) - dp;
        int i2 = ((int) this.actualRect.top) - dp;
        int i3 = ((int) (this.actualRect.right - this.actualRect.left)) + (dp * 2);
        int i4 = ((int) (this.actualRect.bottom - this.actualRect.top)) + (dp * 2);
        if (this.dimVisibile) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) (i2 + dp), this.dimPaint);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) (i2 + dp), (float) (i + dp), (float) ((i2 + i4) - dp), this.dimPaint);
            canvas.drawRect((float) ((i + i3) - dp), (float) (i2 + dp), (float) getWidth(), (float) ((i2 + i4) - dp), this.dimPaint);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) ((i2 + i4) - dp), (float) getWidth(), (float) getHeight(), this.dimPaint);
        }
        if (this.frameVisible) {
            int i5 = dp3 - dp;
            int i6 = i3 - (dp3 * 2);
            int i7 = i4 - (dp3 * 2);
            GridType gridType = this.gridType;
            GridType gridType2 = (gridType != GridType.NONE || this.gridProgress <= BitmapDescriptorFactory.HUE_RED) ? gridType : this.previousGridType;
            this.shadowPaint.setAlpha((int) (this.gridProgress * 26.0f));
            this.linePaint.setAlpha((int) (this.gridProgress * 178.0f));
            int i8 = 0;
            while (i8 < 3) {
                if (gridType2 == GridType.MINOR) {
                    int i9 = 1;
                    while (i9 < 4) {
                        if (i8 != 2 || i9 != 3) {
                            canvas.drawLine((float) (((i + dp3) + (((i6 / 3) / 3) * i9)) + ((i6 / 3) * i8)), (float) (i2 + dp3), (float) (((i + dp3) + (((i6 / 3) / 3) * i9)) + ((i6 / 3) * i8)), (float) ((i2 + dp3) + i7), this.shadowPaint);
                            canvas.drawLine((float) (((i + dp3) + (((i6 / 3) / 3) * i9)) + ((i6 / 3) * i8)), (float) (i2 + dp3), (float) (((i + dp3) + (((i6 / 3) / 3) * i9)) + ((i6 / 3) * i8)), (float) ((i2 + dp3) + i7), this.linePaint);
                            canvas.drawLine((float) (i + dp3), (float) (((i2 + dp3) + (((i7 / 3) / 3) * i9)) + ((i7 / 3) * i8)), (float) ((i + dp3) + i6), (float) (((i2 + dp3) + (((i7 / 3) / 3) * i9)) + ((i7 / 3) * i8)), this.shadowPaint);
                            canvas.drawLine((float) (i + dp3), (float) (((i2 + dp3) + (((i7 / 3) / 3) * i9)) + ((i7 / 3) * i8)), (float) ((i + dp3) + i6), (float) (((i2 + dp3) + (((i7 / 3) / 3) * i9)) + ((i7 / 3) * i8)), this.linePaint);
                        }
                        i9++;
                    }
                } else if (gridType2 == GridType.MAJOR && i8 > 0) {
                    canvas.drawLine((float) ((i + dp3) + ((i6 / 3) * i8)), (float) (i2 + dp3), (float) ((i + dp3) + ((i6 / 3) * i8)), (float) ((i2 + dp3) + i7), this.shadowPaint);
                    canvas.drawLine((float) ((i + dp3) + ((i6 / 3) * i8)), (float) (i2 + dp3), (float) ((i + dp3) + ((i6 / 3) * i8)), (float) ((i2 + dp3) + i7), this.linePaint);
                    canvas.drawLine((float) (i + dp3), (float) ((i2 + dp3) + ((i7 / 3) * i8)), (float) ((i + dp3) + i6), (float) ((i2 + dp3) + ((i7 / 3) * i8)), this.shadowPaint);
                    canvas.drawLine((float) (i + dp3), (float) ((i2 + dp3) + ((i7 / 3) * i8)), (float) ((i + dp3) + i6), (float) ((i2 + dp3) + ((i7 / 3) * i8)), this.linePaint);
                }
                i8++;
            }
            canvas.drawRect((float) (i + i5), (float) (i2 + i5), (float) ((i + i3) - i5), (float) ((i2 + i5) + dp), this.framePaint);
            canvas.drawRect((float) (i + i5), (float) (i2 + i5), (float) ((i + i5) + dp), (float) ((i2 + i4) - i5), this.framePaint);
            canvas.drawRect((float) (i + i5), (float) (((i2 + i4) - i5) - dp), (float) ((i + i3) - i5), (float) ((i2 + i4) - i5), this.framePaint);
            canvas.drawRect((float) (((i + i3) - i5) - dp), (float) (i2 + i5), (float) ((i + i3) - i5), (float) ((i2 + i4) - i5), this.framePaint);
            canvas.drawRect((float) i, (float) i2, (float) (i + dp2), (float) (i2 + dp3), this.handlePaint);
            canvas.drawRect((float) i, (float) i2, (float) (i + dp3), (float) (i2 + dp2), this.handlePaint);
            canvas.drawRect((float) ((i + i3) - dp2), (float) i2, (float) (i + i3), (float) (i2 + dp3), this.handlePaint);
            canvas.drawRect((float) ((i + i3) - dp3), (float) i2, (float) (i + i3), (float) (i2 + dp2), this.handlePaint);
            canvas.drawRect((float) i, (float) ((i2 + i4) - dp3), (float) (i + dp2), (float) (i2 + i4), this.handlePaint);
            canvas.drawRect((float) i, (float) ((i2 + i4) - dp2), (float) (i + dp3), (float) (i2 + i4), this.handlePaint);
            canvas.drawRect((float) ((i + i3) - dp2), (float) ((i2 + i4) - dp3), (float) (i + i3), (float) (i2 + i4), this.handlePaint);
            canvas.drawRect((float) ((i + i3) - dp3), (float) ((i2 + i4) - dp2), (float) (i + i3), (float) (i2 + i4), this.handlePaint);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) (motionEvent.getX() - ((ViewGroup) getParent()).getX());
        int y = (int) (motionEvent.getY() - ((ViewGroup) getParent()).getY());
        float f = (float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (this.topLeftCorner.contains((float) x, (float) y)) {
                this.activeControl = Control.TOP_LEFT;
            } else if (this.topRightCorner.contains((float) x, (float) y)) {
                this.activeControl = Control.TOP_RIGHT;
            } else if (this.bottomLeftCorner.contains((float) x, (float) y)) {
                this.activeControl = Control.BOTTOM_LEFT;
            } else if (this.bottomRightCorner.contains((float) x, (float) y)) {
                this.activeControl = Control.BOTTOM_RIGHT;
            } else if (this.leftEdge.contains((float) x, (float) y)) {
                this.activeControl = Control.LEFT;
            } else if (this.topEdge.contains((float) x, (float) y)) {
                this.activeControl = Control.TOP;
            } else if (this.rightEdge.contains((float) x, (float) y)) {
                this.activeControl = Control.RIGHT;
            } else if (this.bottomEdge.contains((float) x, (float) y)) {
                this.activeControl = Control.BOTTOM;
            } else {
                this.activeControl = Control.NONE;
                return false;
            }
            this.previousX = x;
            this.previousY = y;
            setGridType(GridType.MAJOR, false);
            this.isDragging = true;
            if (this.listener != null) {
                this.listener.onAreaChangeBegan();
            }
            return true;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.isDragging = false;
            if (this.activeControl == Control.NONE) {
                return false;
            }
            this.activeControl = Control.NONE;
            if (this.listener != null) {
                this.listener.onAreaChangeEnded();
            }
            return true;
        } else if (actionMasked != 2 || this.activeControl == Control.NONE) {
            return false;
        } else {
            this.tempRect.set(this.actualRect);
            float f2 = (float) (x - this.previousX);
            float f3 = (float) (y - this.previousY);
            this.previousX = x;
            this.previousY = y;
            RectF rectF;
            float width;
            RectF rectF2;
            switch (this.activeControl) {
                case TOP_LEFT:
                    rectF = this.tempRect;
                    rectF.left += f2;
                    rectF = this.tempRect;
                    rectF.top += f3;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        width = this.tempRect.width();
                        float height = this.tempRect.height();
                        if (Math.abs(f2) > Math.abs(f3)) {
                            constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        } else {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        }
                        rectF2 = this.tempRect;
                        rectF2.left -= this.tempRect.width() - width;
                        rectF2 = this.tempRect;
                        rectF2.top -= this.tempRect.width() - height;
                        break;
                    }
                    break;
                case TOP_RIGHT:
                    rectF = this.tempRect;
                    rectF.right += f2;
                    rectF = this.tempRect;
                    rectF.top += f3;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        width = this.tempRect.height();
                        if (Math.abs(f2) > Math.abs(f3)) {
                            constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        } else {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        }
                        rectF2 = this.tempRect;
                        rectF2.top -= this.tempRect.width() - width;
                        break;
                    }
                    break;
                case BOTTOM_LEFT:
                    rectF = this.tempRect;
                    rectF.left += f2;
                    rectF = this.tempRect;
                    rectF.bottom += f3;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        width = this.tempRect.width();
                        if (Math.abs(f2) > Math.abs(f3)) {
                            constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        } else {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        }
                        rectF2 = this.tempRect;
                        rectF2.left -= this.tempRect.width() - width;
                        break;
                    }
                    break;
                case BOTTOM_RIGHT:
                    rectF = this.tempRect;
                    rectF.right += f2;
                    rectF = this.tempRect;
                    rectF.bottom += f3;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        if (Math.abs(f2) <= Math.abs(f3)) {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                            break;
                        }
                        constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case TOP:
                    rectF2 = this.tempRect;
                    rectF2.top += f3;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case LEFT:
                    rectF = this.tempRect;
                    rectF.left = f2 + rectF.left;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case RIGHT:
                    rectF = this.tempRect;
                    rectF.right = f2 + rectF.right;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case BOTTOM:
                    rectF2 = this.tempRect;
                    rectF2.bottom += f3;
                    if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                        constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
            }
            if (this.tempRect.left < this.sidePadding) {
                if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                    this.tempRect.bottom = this.tempRect.top + ((this.tempRect.right - this.sidePadding) / this.lockAspectRatio);
                }
                this.tempRect.left = this.sidePadding;
            } else if (this.tempRect.right > ((float) getWidth()) - this.sidePadding) {
                this.tempRect.right = ((float) getWidth()) - this.sidePadding;
                if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                    this.tempRect.bottom = this.tempRect.top + (this.tempRect.width() / this.lockAspectRatio);
                }
            }
            f += this.sidePadding;
            f2 = this.bottomPadding + this.sidePadding;
            if (this.tempRect.top < f) {
                if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                    this.tempRect.right = this.tempRect.left + ((this.tempRect.bottom - f) * this.lockAspectRatio);
                }
                this.tempRect.top = f;
            } else if (this.tempRect.bottom > ((float) getHeight()) - f2) {
                this.tempRect.bottom = ((float) getHeight()) - f2;
                if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                    this.tempRect.right = this.tempRect.left + (this.tempRect.height() * this.lockAspectRatio);
                }
            }
            if (this.tempRect.width() < this.minWidth) {
                this.tempRect.right = this.tempRect.left + this.minWidth;
            }
            if (this.tempRect.height() < this.minWidth) {
                this.tempRect.bottom = this.tempRect.top + this.minWidth;
            }
            if (this.lockAspectRatio > BitmapDescriptorFactory.HUE_RED) {
                if (this.lockAspectRatio < 1.0f) {
                    if (this.tempRect.width() <= this.minWidth) {
                        this.tempRect.right = this.tempRect.left + this.minWidth;
                        this.tempRect.bottom = this.tempRect.top + (this.tempRect.width() / this.lockAspectRatio);
                    }
                } else if (this.tempRect.height() <= this.minWidth) {
                    this.tempRect.bottom = this.tempRect.top + this.minWidth;
                    this.tempRect.right = this.tempRect.left + (this.tempRect.height() * this.lockAspectRatio);
                }
            }
            setActualRect(this.tempRect);
            if (this.listener != null) {
                this.listener.onAreaChange();
            }
            return true;
        }
    }

    public void resetAnimator() {
        if (this.animator != null) {
            this.animator.cancel();
            this.animator = null;
        }
    }

    public void setActualRect(float f) {
        calculateRect(this.actualRect, f);
        updateTouchAreas();
        invalidate();
    }

    public void setActualRect(RectF rectF) {
        this.actualRect.set(rectF);
        updateTouchAreas();
        invalidate();
    }

    public void setBitmap(Bitmap bitmap, boolean z, boolean z2) {
        float f = 1.0f;
        if (bitmap != null && !bitmap.isRecycled()) {
            float height = z ? ((float) bitmap.getHeight()) / ((float) bitmap.getWidth()) : ((float) bitmap.getWidth()) / ((float) bitmap.getHeight());
            if (z2) {
                f = height;
            } else {
                this.lockAspectRatio = 1.0f;
            }
            setActualRect(f);
        }
    }

    public void setBottomPadding(float f) {
        this.bottomPadding = f;
    }

    public void setDimVisibility(boolean z) {
        this.dimVisibile = z;
    }

    public void setFrameVisibility(boolean z) {
        this.frameVisible = z;
    }

    public void setGridType(GridType gridType, boolean z) {
        if (!(this.gridAnimator == null || (z && this.gridType == gridType))) {
            this.gridAnimator.cancel();
            this.gridAnimator = null;
        }
        if (this.gridType != gridType) {
            this.previousGridType = this.gridType;
            this.gridType = gridType;
            float f = gridType == GridType.NONE ? BitmapDescriptorFactory.HUE_RED : 1.0f;
            if (z) {
                this.gridAnimator = ObjectAnimator.ofFloat(this, "gridProgress", new float[]{this.gridProgress, f});
                this.gridAnimator.setDuration(200);
                this.gridAnimator.addListener(new C43791());
                if (gridType == GridType.NONE) {
                    this.gridAnimator.setStartDelay(200);
                }
                this.gridAnimator.start();
                return;
            }
            this.gridProgress = f;
            invalidate();
        }
    }

    public void setListener(AreaViewListener areaViewListener) {
        this.listener = areaViewListener;
    }

    public void setLockedAspectRatio(float f) {
        this.lockAspectRatio = f;
    }
}
