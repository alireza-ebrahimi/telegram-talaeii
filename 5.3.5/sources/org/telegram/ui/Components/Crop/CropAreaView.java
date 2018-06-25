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
    class C25411 extends AnimatorListenerAdapter {
        C25411() {
        }

        public void onAnimationEnd(Animator animation) {
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

    public boolean isDragging() {
        return this.isDragging;
    }

    public void setDimVisibility(boolean visible) {
        this.dimVisibile = visible;
    }

    public void setFrameVisibility(boolean visible) {
        this.frameVisible = visible;
    }

    public void setBottomPadding(float value) {
        this.bottomPadding = value;
    }

    public Interpolator getInterpolator() {
        return this.interpolator;
    }

    public void setListener(AreaViewListener l) {
        this.listener = l;
    }

    public void setBitmap(Bitmap bitmap, boolean sideward, boolean freeform) {
        if (bitmap != null && !bitmap.isRecycled()) {
            float aspectRatio;
            if (sideward) {
                aspectRatio = ((float) bitmap.getHeight()) / ((float) bitmap.getWidth());
            } else {
                aspectRatio = ((float) bitmap.getWidth()) / ((float) bitmap.getHeight());
            }
            if (!freeform) {
                aspectRatio = 1.0f;
                this.lockAspectRatio = 1.0f;
            }
            setActualRect(aspectRatio);
        }
    }

    public void setActualRect(float aspectRatio) {
        calculateRect(this.actualRect, aspectRatio);
        updateTouchAreas();
        invalidate();
    }

    public void setActualRect(RectF rect) {
        this.actualRect.set(rect);
        updateTouchAreas();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int lineThickness = AndroidUtilities.dp(2.0f);
        int handleSize = AndroidUtilities.dp(16.0f);
        int handleThickness = AndroidUtilities.dp(3.0f);
        int originX = ((int) this.actualRect.left) - lineThickness;
        int originY = ((int) this.actualRect.top) - lineThickness;
        int width = ((int) (this.actualRect.right - this.actualRect.left)) + (lineThickness * 2);
        int height = ((int) (this.actualRect.bottom - this.actualRect.top)) + (lineThickness * 2);
        if (this.dimVisibile) {
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) (originY + lineThickness), this.dimPaint);
            canvas.drawRect(0.0f, (float) (originY + lineThickness), (float) (originX + lineThickness), (float) ((originY + height) - lineThickness), this.dimPaint);
            canvas.drawRect((float) ((originX + width) - lineThickness), (float) (originY + lineThickness), (float) getWidth(), (float) ((originY + height) - lineThickness), this.dimPaint);
            canvas.drawRect(0.0f, (float) ((originY + height) - lineThickness), (float) getWidth(), (float) getHeight(), this.dimPaint);
        }
        if (this.frameVisible) {
            int inset = handleThickness - lineThickness;
            int gridWidth = width - (handleThickness * 2);
            int gridHeight = height - (handleThickness * 2);
            GridType type = this.gridType;
            if (type == GridType.NONE && this.gridProgress > 0.0f) {
                type = this.previousGridType;
            }
            this.shadowPaint.setAlpha((int) (this.gridProgress * 26.0f));
            this.linePaint.setAlpha((int) (this.gridProgress * 178.0f));
            int i = 0;
            while (i < 3) {
                if (type == GridType.MINOR) {
                    int j = 1;
                    while (j < 4) {
                        if (i != 2 || j != 3) {
                            canvas.drawLine((float) (((originX + handleThickness) + (((gridWidth / 3) / 3) * j)) + ((gridWidth / 3) * i)), (float) (originY + handleThickness), (float) (((originX + handleThickness) + (((gridWidth / 3) / 3) * j)) + ((gridWidth / 3) * i)), (float) ((originY + handleThickness) + gridHeight), this.shadowPaint);
                            canvas.drawLine((float) (((originX + handleThickness) + (((gridWidth / 3) / 3) * j)) + ((gridWidth / 3) * i)), (float) (originY + handleThickness), (float) (((originX + handleThickness) + (((gridWidth / 3) / 3) * j)) + ((gridWidth / 3) * i)), (float) ((originY + handleThickness) + gridHeight), this.linePaint);
                            canvas.drawLine((float) (originX + handleThickness), (float) (((originY + handleThickness) + (((gridHeight / 3) / 3) * j)) + ((gridHeight / 3) * i)), (float) ((originX + handleThickness) + gridWidth), (float) (((originY + handleThickness) + (((gridHeight / 3) / 3) * j)) + ((gridHeight / 3) * i)), this.shadowPaint);
                            canvas.drawLine((float) (originX + handleThickness), (float) (((originY + handleThickness) + (((gridHeight / 3) / 3) * j)) + ((gridHeight / 3) * i)), (float) ((originX + handleThickness) + gridWidth), (float) (((originY + handleThickness) + (((gridHeight / 3) / 3) * j)) + ((gridHeight / 3) * i)), this.linePaint);
                        }
                        j++;
                    }
                } else if (type == GridType.MAJOR && i > 0) {
                    canvas.drawLine((float) ((originX + handleThickness) + ((gridWidth / 3) * i)), (float) (originY + handleThickness), (float) ((originX + handleThickness) + ((gridWidth / 3) * i)), (float) ((originY + handleThickness) + gridHeight), this.shadowPaint);
                    canvas.drawLine((float) ((originX + handleThickness) + ((gridWidth / 3) * i)), (float) (originY + handleThickness), (float) ((originX + handleThickness) + ((gridWidth / 3) * i)), (float) ((originY + handleThickness) + gridHeight), this.linePaint);
                    canvas.drawLine((float) (originX + handleThickness), (float) ((originY + handleThickness) + ((gridHeight / 3) * i)), (float) ((originX + handleThickness) + gridWidth), (float) ((originY + handleThickness) + ((gridHeight / 3) * i)), this.shadowPaint);
                    canvas.drawLine((float) (originX + handleThickness), (float) ((originY + handleThickness) + ((gridHeight / 3) * i)), (float) ((originX + handleThickness) + gridWidth), (float) ((originY + handleThickness) + ((gridHeight / 3) * i)), this.linePaint);
                }
                i++;
            }
            canvas.drawRect((float) (originX + inset), (float) (originY + inset), (float) ((originX + width) - inset), (float) ((originY + inset) + lineThickness), this.framePaint);
            canvas.drawRect((float) (originX + inset), (float) (originY + inset), (float) ((originX + inset) + lineThickness), (float) ((originY + height) - inset), this.framePaint);
            canvas.drawRect((float) (originX + inset), (float) (((originY + height) - inset) - lineThickness), (float) ((originX + width) - inset), (float) ((originY + height) - inset), this.framePaint);
            canvas.drawRect((float) (((originX + width) - inset) - lineThickness), (float) (originY + inset), (float) ((originX + width) - inset), (float) ((originY + height) - inset), this.framePaint);
            canvas.drawRect((float) originX, (float) originY, (float) (originX + handleSize), (float) (originY + handleThickness), this.handlePaint);
            canvas.drawRect((float) originX, (float) originY, (float) (originX + handleThickness), (float) (originY + handleSize), this.handlePaint);
            canvas.drawRect((float) ((originX + width) - handleSize), (float) originY, (float) (originX + width), (float) (originY + handleThickness), this.handlePaint);
            canvas.drawRect((float) ((originX + width) - handleThickness), (float) originY, (float) (originX + width), (float) (originY + handleSize), this.handlePaint);
            canvas.drawRect((float) originX, (float) ((originY + height) - handleThickness), (float) (originX + handleSize), (float) (originY + height), this.handlePaint);
            canvas.drawRect((float) originX, (float) ((originY + height) - handleSize), (float) (originX + handleThickness), (float) (originY + height), this.handlePaint);
            canvas.drawRect((float) ((originX + width) - handleSize), (float) ((originY + height) - handleThickness), (float) (originX + width), (float) (originY + height), this.handlePaint);
            canvas.drawRect((float) ((originX + width) - handleThickness), (float) ((originY + height) - handleSize), (float) (originX + width), (float) (originY + height), this.handlePaint);
        }
    }

    private void updateTouchAreas() {
        int touchPadding = AndroidUtilities.dp(16.0f);
        this.topLeftCorner.set(this.actualRect.left - ((float) touchPadding), this.actualRect.top - ((float) touchPadding), this.actualRect.left + ((float) touchPadding), this.actualRect.top + ((float) touchPadding));
        this.topRightCorner.set(this.actualRect.right - ((float) touchPadding), this.actualRect.top - ((float) touchPadding), this.actualRect.right + ((float) touchPadding), this.actualRect.top + ((float) touchPadding));
        this.bottomLeftCorner.set(this.actualRect.left - ((float) touchPadding), this.actualRect.bottom - ((float) touchPadding), this.actualRect.left + ((float) touchPadding), this.actualRect.bottom + ((float) touchPadding));
        this.bottomRightCorner.set(this.actualRect.right - ((float) touchPadding), this.actualRect.bottom - ((float) touchPadding), this.actualRect.right + ((float) touchPadding), this.actualRect.bottom + ((float) touchPadding));
        this.topEdge.set(this.actualRect.left + ((float) touchPadding), this.actualRect.top - ((float) touchPadding), this.actualRect.right - ((float) touchPadding), this.actualRect.top + ((float) touchPadding));
        this.leftEdge.set(this.actualRect.left - ((float) touchPadding), this.actualRect.top + ((float) touchPadding), this.actualRect.left + ((float) touchPadding), this.actualRect.bottom - ((float) touchPadding));
        this.rightEdge.set(this.actualRect.right - ((float) touchPadding), this.actualRect.top + ((float) touchPadding), this.actualRect.right + ((float) touchPadding), this.actualRect.bottom - ((float) touchPadding));
        this.bottomEdge.set(this.actualRect.left + ((float) touchPadding), this.actualRect.bottom - ((float) touchPadding), this.actualRect.right - ((float) touchPadding), this.actualRect.bottom + ((float) touchPadding));
    }

    public float getLockAspectRatio() {
        return this.lockAspectRatio;
    }

    public void setLockedAspectRatio(float aspectRatio) {
        this.lockAspectRatio = aspectRatio;
    }

    public void setGridType(GridType type, boolean animated) {
        if (!(this.gridAnimator == null || (animated && this.gridType == type))) {
            this.gridAnimator.cancel();
            this.gridAnimator = null;
        }
        if (this.gridType != type) {
            this.previousGridType = this.gridType;
            this.gridType = type;
            float targetProgress = type == GridType.NONE ? 0.0f : 1.0f;
            if (animated) {
                this.gridAnimator = ObjectAnimator.ofFloat(this, "gridProgress", new float[]{this.gridProgress, targetProgress});
                this.gridAnimator.setDuration(200);
                this.gridAnimator.addListener(new C25411());
                if (type == GridType.NONE) {
                    this.gridAnimator.setStartDelay(200);
                }
                this.gridAnimator.start();
                return;
            }
            this.gridProgress = targetProgress;
            invalidate();
        }
    }

    private void setGridProgress(float value) {
        this.gridProgress = value;
        invalidate();
    }

    private float getGridProgress() {
        return this.gridProgress;
    }

    public float getAspectRatio() {
        return (this.actualRect.right - this.actualRect.left) / (this.actualRect.bottom - this.actualRect.top);
    }

    public void fill(final RectF targetRect, Animator scaleAnimator, boolean animated) {
        if (animated) {
            if (this.animator != null) {
                this.animator.cancel();
                this.animator = null;
            }
            AnimatorSet set = new AnimatorSet();
            this.animator = set;
            set.setDuration(300);
            Animator[] animators = new Animator[5];
            animators[0] = ObjectAnimator.ofFloat(this, "cropLeft", new float[]{targetRect.left});
            animators[0].setInterpolator(this.interpolator);
            animators[1] = ObjectAnimator.ofFloat(this, "cropTop", new float[]{targetRect.top});
            animators[1].setInterpolator(this.interpolator);
            animators[2] = ObjectAnimator.ofFloat(this, "cropRight", new float[]{targetRect.right});
            animators[2].setInterpolator(this.interpolator);
            animators[3] = ObjectAnimator.ofFloat(this, "cropBottom", new float[]{targetRect.bottom});
            animators[3].setInterpolator(this.interpolator);
            animators[4] = scaleAnimator;
            animators[4].setInterpolator(this.interpolator);
            set.playTogether(animators);
            set.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    CropAreaView.this.setActualRect(targetRect);
                    CropAreaView.this.animator = null;
                }
            });
            set.start();
            return;
        }
        setActualRect(targetRect);
    }

    public void resetAnimator() {
        if (this.animator != null) {
            this.animator.cancel();
            this.animator = null;
        }
    }

    private void setCropLeft(float value) {
        this.actualRect.left = value;
        invalidate();
    }

    public float getCropLeft() {
        return this.actualRect.left;
    }

    private void setCropTop(float value) {
        this.actualRect.top = value;
        invalidate();
    }

    public float getCropTop() {
        return this.actualRect.top;
    }

    private void setCropRight(float value) {
        this.actualRect.right = value;
        invalidate();
    }

    public float getCropRight() {
        return this.actualRect.right;
    }

    private void setCropBottom(float value) {
        this.actualRect.bottom = value;
        invalidate();
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

    public float getCropWidth() {
        return this.actualRect.right - this.actualRect.left;
    }

    public float getCropHeight() {
        return this.actualRect.bottom - this.actualRect.top;
    }

    public RectF getTargetRectToFill() {
        RectF rect = new RectF();
        calculateRect(rect, getAspectRatio());
        return rect;
    }

    public void calculateRect(RectF rect, float cropAspectRatio) {
        float left;
        float top;
        float right;
        float bottom;
        float statusBarHeight = (float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
        float measuredHeight = (((float) getMeasuredHeight()) - this.bottomPadding) - statusBarHeight;
        float aspectRatio = ((float) getMeasuredWidth()) / measuredHeight;
        float minSide = Math.min((float) getMeasuredWidth(), measuredHeight) - (2.0f * this.sidePadding);
        float width = ((float) getMeasuredWidth()) - (2.0f * this.sidePadding);
        float height = measuredHeight - (2.0f * this.sidePadding);
        float centerX = ((float) getMeasuredWidth()) / 2.0f;
        float centerY = statusBarHeight + (measuredHeight / 2.0f);
        if (((double) Math.abs(1.0f - cropAspectRatio)) < 1.0E-4d) {
            left = centerX - (minSide / 2.0f);
            top = centerY - (minSide / 2.0f);
            right = centerX + (minSide / 2.0f);
            bottom = centerY + (minSide / 2.0f);
        } else if (cropAspectRatio > aspectRatio) {
            left = centerX - (width / 2.0f);
            top = centerY - ((width / cropAspectRatio) / 2.0f);
            right = centerX + (width / 2.0f);
            bottom = centerY + ((width / cropAspectRatio) / 2.0f);
        } else {
            left = centerX - ((height * cropAspectRatio) / 2.0f);
            top = centerY - (height / 2.0f);
            right = centerX + ((height * cropAspectRatio) / 2.0f);
            bottom = centerY + (height / 2.0f);
        }
        rect.set(left, top, right, bottom);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX() - ((ViewGroup) getParent()).getX());
        int y = (int) (event.getY() - ((ViewGroup) getParent()).getY());
        float statusBarHeight = (float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
        int action = event.getActionMasked();
        if (action == 0) {
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
        } else if (action == 1 || action == 3) {
            this.isDragging = false;
            if (this.activeControl == Control.NONE) {
                return false;
            }
            this.activeControl = Control.NONE;
            if (this.listener != null) {
                this.listener.onAreaChangeEnded();
            }
            return true;
        } else if (action != 2) {
            return false;
        } else {
            if (this.activeControl == Control.NONE) {
                return false;
            }
            this.tempRect.set(this.actualRect);
            float translationX = (float) (x - this.previousX);
            float translationY = (float) (y - this.previousY);
            this.previousX = x;
            this.previousY = y;
            RectF rectF;
            float w;
            float h;
            switch (this.activeControl) {
                case TOP_LEFT:
                    rectF = this.tempRect;
                    rectF.left += translationX;
                    rectF = this.tempRect;
                    rectF.top += translationY;
                    if (this.lockAspectRatio > 0.0f) {
                        w = this.tempRect.width();
                        h = this.tempRect.height();
                        if (Math.abs(translationX) > Math.abs(translationY)) {
                            constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        } else {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        }
                        rectF = this.tempRect;
                        rectF.left -= this.tempRect.width() - w;
                        rectF = this.tempRect;
                        rectF.top -= this.tempRect.width() - h;
                        break;
                    }
                    break;
                case TOP_RIGHT:
                    rectF = this.tempRect;
                    rectF.right += translationX;
                    rectF = this.tempRect;
                    rectF.top += translationY;
                    if (this.lockAspectRatio > 0.0f) {
                        h = this.tempRect.height();
                        if (Math.abs(translationX) > Math.abs(translationY)) {
                            constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        } else {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        }
                        rectF = this.tempRect;
                        rectF.top -= this.tempRect.width() - h;
                        break;
                    }
                    break;
                case BOTTOM_LEFT:
                    rectF = this.tempRect;
                    rectF.left += translationX;
                    rectF = this.tempRect;
                    rectF.bottom += translationY;
                    if (this.lockAspectRatio > 0.0f) {
                        w = this.tempRect.width();
                        if (Math.abs(translationX) > Math.abs(translationY)) {
                            constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        } else {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        }
                        rectF = this.tempRect;
                        rectF.left -= this.tempRect.width() - w;
                        break;
                    }
                    break;
                case BOTTOM_RIGHT:
                    rectF = this.tempRect;
                    rectF.right += translationX;
                    rectF = this.tempRect;
                    rectF.bottom += translationY;
                    if (this.lockAspectRatio > 0.0f) {
                        if (Math.abs(translationX) <= Math.abs(translationY)) {
                            constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                            break;
                        }
                        constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case TOP:
                    rectF = this.tempRect;
                    rectF.top += translationY;
                    if (this.lockAspectRatio > 0.0f) {
                        constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case LEFT:
                    rectF = this.tempRect;
                    rectF.left += translationX;
                    if (this.lockAspectRatio > 0.0f) {
                        constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case RIGHT:
                    rectF = this.tempRect;
                    rectF.right += translationX;
                    if (this.lockAspectRatio > 0.0f) {
                        constrainRectByWidth(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
                case BOTTOM:
                    rectF = this.tempRect;
                    rectF.bottom += translationY;
                    if (this.lockAspectRatio > 0.0f) {
                        constrainRectByHeight(this.tempRect, this.lockAspectRatio);
                        break;
                    }
                    break;
            }
            if (this.tempRect.left < this.sidePadding) {
                if (this.lockAspectRatio > 0.0f) {
                    this.tempRect.bottom = this.tempRect.top + ((this.tempRect.right - this.sidePadding) / this.lockAspectRatio);
                }
                this.tempRect.left = this.sidePadding;
            } else if (this.tempRect.right > ((float) getWidth()) - this.sidePadding) {
                this.tempRect.right = ((float) getWidth()) - this.sidePadding;
                if (this.lockAspectRatio > 0.0f) {
                    this.tempRect.bottom = this.tempRect.top + (this.tempRect.width() / this.lockAspectRatio);
                }
            }
            float topPadding = statusBarHeight + this.sidePadding;
            float finalBottomPadidng = this.bottomPadding + this.sidePadding;
            if (this.tempRect.top < topPadding) {
                if (this.lockAspectRatio > 0.0f) {
                    this.tempRect.right = this.tempRect.left + ((this.tempRect.bottom - topPadding) * this.lockAspectRatio);
                }
                this.tempRect.top = topPadding;
            } else if (this.tempRect.bottom > ((float) getHeight()) - finalBottomPadidng) {
                this.tempRect.bottom = ((float) getHeight()) - finalBottomPadidng;
                if (this.lockAspectRatio > 0.0f) {
                    this.tempRect.right = this.tempRect.left + (this.tempRect.height() * this.lockAspectRatio);
                }
            }
            if (this.tempRect.width() < this.minWidth) {
                this.tempRect.right = this.tempRect.left + this.minWidth;
            }
            if (this.tempRect.height() < this.minWidth) {
                this.tempRect.bottom = this.tempRect.top + this.minWidth;
            }
            if (this.lockAspectRatio > 0.0f) {
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

    private void constrainRectByWidth(RectF rect, float aspectRatio) {
        float w = rect.width();
        float h = w / aspectRatio;
        rect.right = rect.left + w;
        rect.bottom = rect.top + h;
    }

    private void constrainRectByHeight(RectF rect, float aspectRatio) {
        float h = rect.height();
        rect.right = rect.left + (h * aspectRatio);
        rect.bottom = rect.top + h;
    }

    public void getCropRect(RectF rect) {
        rect.set(this.actualRect);
    }
}
