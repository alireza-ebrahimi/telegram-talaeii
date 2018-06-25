package uk.co.samuelwall.materialtaptargetprompt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.View;

class MaterialTapTargetPrompt$PromptView extends View {
    boolean mBackButtonDismissEnabled;
    PointF mBackgroundPosition = new PointF();
    float mBackgroundRadius;
    boolean mCaptureTouchEventOnFocal;
    boolean mCaptureTouchEventOutsidePrompt;
    Rect mClipBounds;
    boolean mClipToBounds;
    boolean mDrawRipple;
    PointF mFocalCentre = new PointF();
    float mFocalRadius;
    int mFocalRippleAlpha;
    float mFocalRippleSize;
    Drawable mIconDrawable;
    float mIconDrawableLeft;
    float mIconDrawableTop;
    Paint mPaintBackground;
    Paint mPaintFocal;
    Layout mPrimaryTextLayout;
    float mPrimaryTextLeft;
    float mPrimaryTextLeftChange;
    float mPrimaryTextTop;
    MaterialTapTargetPrompt$PromptView$PromptTouchedListener mPromptTouchedListener;
    Layout mSecondaryTextLayout;
    float mSecondaryTextLeft;
    float mSecondaryTextLeftChange;
    float mSecondaryTextOffsetTop;
    View mTargetRenderView;
    View mTargetView;
    float mTextSeparation;

    public MaterialTapTargetPrompt$PromptView(Context context) {
        super(context);
        this.mDrawRipple = VERSION.SDK_INT >= 11;
        this.mClipBounds = new Rect();
        setId(C3463R.id.material_target_prompt_view);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    public void onDraw(Canvas canvas) {
        if (this.mBackgroundRadius > 0.0f) {
            if (this.mClipToBounds) {
                canvas.clipRect(this.mClipBounds);
            }
            canvas.drawCircle(this.mBackgroundPosition.x, this.mBackgroundPosition.y, this.mBackgroundRadius, this.mPaintBackground);
            if (this.mDrawRipple) {
                int oldAlpha = this.mPaintFocal.getAlpha();
                this.mPaintFocal.setAlpha(this.mFocalRippleAlpha);
                canvas.drawCircle(this.mFocalCentre.x, this.mFocalCentre.y, this.mFocalRippleSize, this.mPaintFocal);
                this.mPaintFocal.setAlpha(oldAlpha);
            }
            canvas.drawCircle(this.mFocalCentre.x, this.mFocalCentre.y, this.mFocalRadius, this.mPaintFocal);
            if (this.mIconDrawable != null) {
                canvas.translate(this.mIconDrawableLeft, this.mIconDrawableTop);
                this.mIconDrawable.draw(canvas);
                canvas.translate(-this.mIconDrawableLeft, -this.mIconDrawableTop);
            } else if (this.mTargetRenderView != null) {
                canvas.translate(this.mIconDrawableLeft, this.mIconDrawableTop);
                this.mTargetRenderView.draw(canvas);
                canvas.translate(-this.mIconDrawableLeft, -this.mIconDrawableTop);
            }
            canvas.translate(this.mPrimaryTextLeft - this.mPrimaryTextLeftChange, this.mPrimaryTextTop);
            if (this.mPrimaryTextLayout != null) {
                this.mPrimaryTextLayout.draw(canvas);
            }
            if (this.mSecondaryTextLayout != null) {
                canvas.translate(((-(this.mPrimaryTextLeft - this.mPrimaryTextLeftChange)) + this.mSecondaryTextLeft) - this.mSecondaryTextLeftChange, this.mSecondaryTextOffsetTop);
                this.mSecondaryTextLayout.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean captureEvent;
        float x = event.getX();
        float y = event.getY();
        if ((!this.mClipToBounds || this.mClipBounds.contains((int) x, (int) y)) && pointInCircle(x, y, this.mBackgroundPosition, this.mBackgroundRadius)) {
            captureEvent = true;
        } else {
            captureEvent = false;
        }
        if (captureEvent && pointInCircle(x, y, this.mFocalCentre, this.mFocalRadius)) {
            captureEvent = this.mCaptureTouchEventOnFocal;
            if (this.mPromptTouchedListener != null) {
                this.mPromptTouchedListener.onFocalPressed();
                this.mPromptTouchedListener.onPromptTouched(event, true);
            }
        } else {
            if (!captureEvent) {
                captureEvent = this.mCaptureTouchEventOutsidePrompt;
            }
            if (this.mPromptTouchedListener != null) {
                this.mPromptTouchedListener.onNonFocalPressed();
                this.mPromptTouchedListener.onPromptTouched(event, false);
            }
        }
        return captureEvent;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (this.mBackButtonDismissEnabled && event.getKeyCode() == 4) {
            DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                    state.startTracking(event, this);
                    return true;
                } else if (event.getAction() == 1 && !event.isCanceled() && state.isTracking(event)) {
                    if (this.mPromptTouchedListener == null) {
                        return true;
                    }
                    this.mPromptTouchedListener.onNonFocalPressed();
                    this.mPromptTouchedListener.onPromptTouched(null, false);
                    return true;
                }
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }

    boolean pointInCircle(float x, float y, PointF circleCentre, float radius) {
        return Math.pow((double) (x - circleCentre.x), 2.0d) + Math.pow((double) (y - circleCentre.y), 2.0d) < Math.pow((double) radius, 2.0d);
    }
}
