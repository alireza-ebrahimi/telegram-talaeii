package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;

public class ShutterButton extends View {
    private static final int LONG_PRESS_TIME = 800;
    private ShutterButtonDelegate delegate;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private long lastUpdateTime;
    private Runnable longPressed = new C27551();
    private boolean pressed;
    private boolean processRelease;
    private Paint redPaint;
    private float redProgress;
    private Drawable shadowDrawable = getResources().getDrawable(R.drawable.camera_btn);
    private State state;
    private long totalTime;
    private Paint whitePaint = new Paint(1);

    public interface ShutterButtonDelegate {
        void shutterCancel();

        boolean shutterLongPressed();

        void shutterReleased();
    }

    /* renamed from: org.telegram.ui.Components.ShutterButton$1 */
    class C27551 implements Runnable {
        C27551() {
        }

        public void run() {
            if (ShutterButton.this.delegate != null && !ShutterButton.this.delegate.shutterLongPressed()) {
                ShutterButton.this.processRelease = false;
            }
        }
    }

    public enum State {
        DEFAULT,
        RECORDING
    }

    public ShutterButton(Context context) {
        super(context);
        this.whitePaint.setStyle(Style.FILL);
        this.whitePaint.setColor(-1);
        this.redPaint = new Paint(1);
        this.redPaint.setStyle(Style.FILL);
        this.redPaint.setColor(-3324089);
        this.state = State.DEFAULT;
    }

    public void setDelegate(ShutterButtonDelegate shutterButtonDelegate) {
        this.delegate = shutterButtonDelegate;
    }

    public ShutterButtonDelegate getDelegate() {
        return this.delegate;
    }

    private void setHighlighted(boolean value) {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr;
        if (value) {
            animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.06f});
            animatorArr[1] = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.06f});
            animatorSet.playTogether(animatorArr);
        } else {
            animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.0f});
            animatorArr[1] = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.0f});
            animatorSet.playTogether(animatorArr);
            animatorSet.setStartDelay(40);
        }
        animatorSet.setDuration(120);
        animatorSet.setInterpolator(this.interpolator);
        animatorSet.start();
    }

    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        invalidate();
    }

    public State getState() {
        return this.state;
    }

    protected void onDraw(Canvas canvas) {
        int cx = getMeasuredWidth() / 2;
        int cy = getMeasuredHeight() / 2;
        this.shadowDrawable.setBounds(cx - AndroidUtilities.dp(36.0f), cy - AndroidUtilities.dp(36.0f), AndroidUtilities.dp(36.0f) + cx, AndroidUtilities.dp(36.0f) + cy);
        this.shadowDrawable.draw(canvas);
        if (this.pressed || getScaleX() != 1.0f) {
            float scale = (getScaleX() - 1.0f) / 0.06f;
            this.whitePaint.setAlpha((int) (255.0f * scale));
            canvas.drawCircle((float) cx, (float) cy, (float) AndroidUtilities.dp(26.0f), this.whitePaint);
            if (this.state == State.RECORDING) {
                if (this.redProgress != 1.0f) {
                    long dt = Math.abs(System.currentTimeMillis() - this.lastUpdateTime);
                    if (dt > 17) {
                        dt = 17;
                    }
                    this.totalTime += dt;
                    if (this.totalTime > 120) {
                        this.totalTime = 120;
                    }
                    this.redProgress = this.interpolator.getInterpolation(((float) this.totalTime) / 120.0f);
                    invalidate();
                }
                canvas.drawCircle((float) cx, (float) cy, (((float) AndroidUtilities.dp(26.0f)) * scale) * this.redProgress, this.redPaint);
            } else if (this.redProgress != 0.0f) {
                canvas.drawCircle((float) cx, (float) cy, ((float) AndroidUtilities.dp(26.0f)) * scale, this.redPaint);
            }
        } else if (this.redProgress != 0.0f) {
            this.redProgress = 0.0f;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(AndroidUtilities.dp(84.0f), AndroidUtilities.dp(84.0f));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getX();
        switch (motionEvent.getAction()) {
            case 0:
                AndroidUtilities.runOnUIThread(this.longPressed, 800);
                this.pressed = true;
                this.processRelease = true;
                setHighlighted(true);
                break;
            case 1:
                setHighlighted(false);
                AndroidUtilities.cancelRunOnUIThread(this.longPressed);
                if (this.processRelease && x >= 0.0f && y >= 0.0f && x <= ((float) getMeasuredWidth()) && y <= ((float) getMeasuredHeight())) {
                    this.delegate.shutterReleased();
                    break;
                }
            case 2:
                if (x < 0.0f || y < 0.0f || x > ((float) getMeasuredWidth()) || y > ((float) getMeasuredHeight())) {
                    AndroidUtilities.cancelRunOnUIThread(this.longPressed);
                    if (this.state == State.RECORDING) {
                        setHighlighted(false);
                        this.delegate.shutterCancel();
                        setState(State.DEFAULT, true);
                        break;
                    }
                }
                break;
            case 3:
                setHighlighted(false);
                this.pressed = false;
                break;
        }
        return true;
    }

    public void setState(State value, boolean animated) {
        if (this.state != value) {
            this.state = value;
            if (animated) {
                this.lastUpdateTime = System.currentTimeMillis();
                this.totalTime = 0;
                if (this.state != State.RECORDING) {
                    this.redProgress = 0.0f;
                }
            } else if (this.state == State.RECORDING) {
                this.redProgress = 1.0f;
            } else {
                this.redProgress = 0.0f;
            }
            invalidate();
        }
    }
}
