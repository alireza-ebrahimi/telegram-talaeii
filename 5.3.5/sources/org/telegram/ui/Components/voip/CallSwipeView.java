package org.telegram.ui.Components.voip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

public class CallSwipeView extends View {
    private boolean animatingArrows = false;
    private Path arrow = new Path();
    private int[] arrowAlphas = new int[]{64, 64, 64};
    private AnimatorSet arrowAnim;
    private Paint arrowsPaint;
    private boolean canceled = false;
    private boolean dragFromRight;
    private float dragStartX;
    private boolean dragging = false;
    private Listener listener;
    private Paint pullBgPaint;
    private RectF tmpRect = new RectF();
    private View viewToDrag;

    /* renamed from: org.telegram.ui.Components.voip.CallSwipeView$1 */
    class C28221 extends AnimatorListenerAdapter {
        private Runnable restarter = new C28211();
        private long startTime;

        /* renamed from: org.telegram.ui.Components.voip.CallSwipeView$1$1 */
        class C28211 implements Runnable {
            C28211() {
            }

            public void run() {
                if (CallSwipeView.this.arrowAnim != null) {
                    CallSwipeView.this.arrowAnim.start();
                }
            }
        }

        C28221() {
        }

        public void onAnimationEnd(Animator animation) {
            if (System.currentTimeMillis() - this.startTime < animation.getDuration() / 4) {
                FileLog.w("Not repeating animation because previous loop was too fast");
            } else if (!CallSwipeView.this.canceled && CallSwipeView.this.animatingArrows) {
                CallSwipeView.this.post(this.restarter);
            }
        }

        public void onAnimationCancel(Animator animation) {
            CallSwipeView.this.canceled = true;
        }

        public void onAnimationStart(Animator animation) {
            this.startTime = System.currentTimeMillis();
        }
    }

    private class ArrowAnimWrapper {
        private int index;

        public ArrowAnimWrapper(int value) {
            this.index = value;
        }

        public int getArrowAlpha() {
            return CallSwipeView.this.arrowAlphas[this.index];
        }

        public void setArrowAlpha(int value) {
            CallSwipeView.this.arrowAlphas[this.index] = value;
        }
    }

    public interface Listener {
        void onDragCancel();

        void onDragComplete();

        void onDragStart();
    }

    public CallSwipeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.arrowsPaint = new Paint(1);
        this.arrowsPaint.setColor(-1);
        this.arrowsPaint.setStyle(Style.STROKE);
        this.arrowsPaint.setStrokeWidth((float) AndroidUtilities.dp(2.5f));
        this.pullBgPaint = new Paint(1);
        ArrayList<Animator> anims = new ArrayList();
        for (int i = 0; i < this.arrowAlphas.length; i++) {
            ObjectAnimator anim = ObjectAnimator.ofInt(new ArrowAnimWrapper(i), "arrowAlpha", new int[]{64, 255, 64});
            anim.setDuration(700);
            anim.setStartDelay((long) (i * 200));
            anims.add(anim);
        }
        this.arrowAnim = new AnimatorSet();
        this.arrowAnim.playTogether(anims);
        this.arrowAnim.addListener(new C28221());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.arrowAnim != null) {
            this.canceled = true;
            this.arrowAnim.cancel();
            this.arrowAnim = null;
        }
    }

    public void setColor(int color) {
        this.pullBgPaint.setColor(color);
        this.pullBgPaint.setAlpha(178);
    }

    public void setViewToDrag(View viewToDrag, boolean dragFromRight) {
        this.viewToDrag = viewToDrag;
        this.dragFromRight = dragFromRight;
        updateArrowPath();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private int getDraggedViewWidth() {
        return getHeight();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        float f = 0.0f;
        if (!isEnabled()) {
            return false;
        }
        if (ev.getAction() == 0) {
            if ((!this.dragFromRight && ev.getX() < ((float) getDraggedViewWidth())) || (this.dragFromRight && ev.getX() > ((float) (getWidth() - getDraggedViewWidth())))) {
                this.dragging = true;
                this.dragStartX = ev.getX();
                getParent().requestDisallowInterceptTouchEvent(true);
                this.listener.onDragStart();
                stopAnimatingArrows();
            }
        } else if (ev.getAction() == 2) {
            float f2;
            View view = this.viewToDrag;
            if (this.dragFromRight) {
                f2 = (float) (-(getWidth() - getDraggedViewWidth()));
            } else {
                f2 = 0.0f;
            }
            float x = ev.getX() - this.dragStartX;
            if (!this.dragFromRight) {
                f = (float) (getWidth() - getDraggedViewWidth());
            }
            view.setTranslationX(Math.max(f2, Math.min(x, f)));
            invalidate();
        } else if (ev.getAction() == 1 || ev.getAction() == 3) {
            if (Math.abs(this.viewToDrag.getTranslationX()) < ((float) (getWidth() - getDraggedViewWidth())) || ev.getAction() != 1) {
                this.listener.onDragCancel();
                this.viewToDrag.animate().translationX(0.0f).setDuration(200).start();
                invalidate();
                startAnimatingArrows();
                this.dragging = false;
            } else {
                this.listener.onDragComplete();
            }
        }
        return this.dragging;
    }

    public void stopAnimatingArrows() {
        this.animatingArrows = false;
    }

    public void startAnimatingArrows() {
        if (!this.animatingArrows && this.arrowAnim != null) {
            this.animatingArrows = true;
            if (this.arrowAnim != null) {
                this.arrowAnim.start();
            }
        }
    }

    public void reset() {
        if (this.arrowAnim != null && !this.canceled) {
            this.listener.onDragCancel();
            this.viewToDrag.animate().translationX(0.0f).setDuration(200).start();
            invalidate();
            startAnimatingArrows();
            this.dragging = false;
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.viewToDrag.getTranslationX() != 0.0f) {
            if (this.dragFromRight) {
                this.tmpRect.set((((float) getWidth()) + this.viewToDrag.getTranslationX()) - ((float) getDraggedViewWidth()), 0.0f, (float) getWidth(), (float) getHeight());
            } else {
                this.tmpRect.set(0.0f, 0.0f, this.viewToDrag.getTranslationX() + ((float) getDraggedViewWidth()), (float) getHeight());
            }
            canvas.drawRoundRect(this.tmpRect, (float) (getHeight() / 2), (float) (getHeight() / 2), this.pullBgPaint);
        }
        canvas.save();
        if (this.dragFromRight) {
            canvas.translate((float) ((getWidth() - getHeight()) - AndroidUtilities.dp(18.0f)), (float) (getHeight() / 2));
        } else {
            canvas.translate((float) (getHeight() + AndroidUtilities.dp(12.0f)), (float) (getHeight() / 2));
        }
        float offsetX = Math.abs(this.viewToDrag.getTranslationX());
        for (int i = 0; i < 3; i++) {
            float f;
            float masterAlpha = 1.0f;
            if (offsetX > ((float) AndroidUtilities.dp((float) (i * 16)))) {
                masterAlpha = 1.0f - Math.min(1.0f, Math.max(0.0f, (offsetX - ((float) (AndroidUtilities.dp(16.0f) * i))) / ((float) AndroidUtilities.dp(16.0f))));
            }
            this.arrowsPaint.setAlpha(Math.round(((float) this.arrowAlphas[i]) * masterAlpha));
            canvas.drawPath(this.arrow, this.arrowsPaint);
            if (this.dragFromRight) {
                f = -16.0f;
            } else {
                f = 16.0f;
            }
            canvas.translate((float) AndroidUtilities.dp(f), 0.0f);
        }
        canvas.restore();
        invalidate();
    }

    private void updateArrowPath() {
        this.arrow.reset();
        int size = AndroidUtilities.dp(6.0f);
        if (this.dragFromRight) {
            this.arrow.moveTo((float) size, (float) (-size));
            this.arrow.lineTo(0.0f, 0.0f);
            this.arrow.lineTo((float) size, (float) size);
            return;
        }
        this.arrow.moveTo(0.0f, (float) (-size));
        this.arrow.lineTo((float) size, 0.0f);
        this.arrow.lineTo(0.0f, (float) size);
    }
}
