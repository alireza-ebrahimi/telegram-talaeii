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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collection;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

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
    class C46601 extends AnimatorListenerAdapter {
        private Runnable restarter = new C46591();
        private long startTime;

        /* renamed from: org.telegram.ui.Components.voip.CallSwipeView$1$1 */
        class C46591 implements Runnable {
            C46591() {
            }

            public void run() {
                if (CallSwipeView.this.arrowAnim != null) {
                    CallSwipeView.this.arrowAnim.start();
                }
            }
        }

        C46601() {
        }

        public void onAnimationCancel(Animator animator) {
            CallSwipeView.this.canceled = true;
        }

        public void onAnimationEnd(Animator animator) {
            if (System.currentTimeMillis() - this.startTime < animator.getDuration() / 4) {
                FileLog.w("Not repeating animation because previous loop was too fast");
            } else if (!CallSwipeView.this.canceled && CallSwipeView.this.animatingArrows) {
                CallSwipeView.this.post(this.restarter);
            }
        }

        public void onAnimationStart(Animator animator) {
            this.startTime = System.currentTimeMillis();
        }
    }

    private class ArrowAnimWrapper {
        private int index;

        public ArrowAnimWrapper(int i) {
            this.index = i;
        }

        public int getArrowAlpha() {
            return CallSwipeView.this.arrowAlphas[this.index];
        }

        public void setArrowAlpha(int i) {
            CallSwipeView.this.arrowAlphas[this.index] = i;
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

    private int getDraggedViewWidth() {
        return getHeight();
    }

    private void init() {
        this.arrowsPaint = new Paint(1);
        this.arrowsPaint.setColor(-1);
        this.arrowsPaint.setStyle(Style.STROKE);
        this.arrowsPaint.setStrokeWidth((float) AndroidUtilities.dp(2.5f));
        this.pullBgPaint = new Paint(1);
        Collection arrayList = new ArrayList();
        for (int i = 0; i < this.arrowAlphas.length; i++) {
            ObjectAnimator ofInt = ObjectAnimator.ofInt(new ArrowAnimWrapper(i), "arrowAlpha", new int[]{64, 255, 64});
            ofInt.setDuration(700);
            ofInt.setStartDelay((long) (i * Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            arrayList.add(ofInt);
        }
        this.arrowAnim = new AnimatorSet();
        this.arrowAnim.playTogether(arrayList);
        this.arrowAnim.addListener(new C46601());
    }

    private void updateArrowPath() {
        this.arrow.reset();
        int dp = AndroidUtilities.dp(6.0f);
        if (this.dragFromRight) {
            this.arrow.moveTo((float) dp, (float) (-dp));
            this.arrow.lineTo(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            this.arrow.lineTo((float) dp, (float) dp);
            return;
        }
        this.arrow.moveTo(BitmapDescriptorFactory.HUE_RED, (float) (-dp));
        this.arrow.lineTo((float) dp, BitmapDescriptorFactory.HUE_RED);
        this.arrow.lineTo(BitmapDescriptorFactory.HUE_RED, (float) dp);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.arrowAnim != null) {
            this.canceled = true;
            this.arrowAnim.cancel();
            this.arrowAnim = null;
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.viewToDrag.getTranslationX() != BitmapDescriptorFactory.HUE_RED) {
            if (this.dragFromRight) {
                this.tmpRect.set((((float) getWidth()) + this.viewToDrag.getTranslationX()) - ((float) getDraggedViewWidth()), BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight());
            } else {
                this.tmpRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.viewToDrag.getTranslationX() + ((float) getDraggedViewWidth()), (float) getHeight());
            }
            canvas.drawRoundRect(this.tmpRect, (float) (getHeight() / 2), (float) (getHeight() / 2), this.pullBgPaint);
        }
        canvas.save();
        if (this.dragFromRight) {
            canvas.translate((float) ((getWidth() - getHeight()) - AndroidUtilities.dp(18.0f)), (float) (getHeight() / 2));
        } else {
            canvas.translate((float) (getHeight() + AndroidUtilities.dp(12.0f)), (float) (getHeight() / 2));
        }
        float abs = Math.abs(this.viewToDrag.getTranslationX());
        for (int i = 0; i < 3; i++) {
            this.arrowsPaint.setAlpha(Math.round((abs > ((float) AndroidUtilities.dp((float) (i * 16))) ? 1.0f - Math.min(1.0f, Math.max(BitmapDescriptorFactory.HUE_RED, (abs - ((float) (AndroidUtilities.dp(16.0f) * i))) / ((float) AndroidUtilities.dp(16.0f)))) : 1.0f) * ((float) this.arrowAlphas[i])));
            canvas.drawPath(this.arrow, this.arrowsPaint);
            canvas.translate((float) AndroidUtilities.dp(this.dragFromRight ? -16.0f : 16.0f), BitmapDescriptorFactory.HUE_RED);
        }
        canvas.restore();
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (!isEnabled()) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            if ((!this.dragFromRight && motionEvent.getX() < ((float) getDraggedViewWidth())) || (this.dragFromRight && motionEvent.getX() > ((float) (getWidth() - getDraggedViewWidth())))) {
                this.dragging = true;
                this.dragStartX = motionEvent.getX();
                getParent().requestDisallowInterceptTouchEvent(true);
                this.listener.onDragStart();
                stopAnimatingArrows();
            }
        } else if (motionEvent.getAction() == 2) {
            View view = this.viewToDrag;
            float f2 = this.dragFromRight ? (float) (-(getWidth() - getDraggedViewWidth())) : BitmapDescriptorFactory.HUE_RED;
            float x = motionEvent.getX() - this.dragStartX;
            if (!this.dragFromRight) {
                f = (float) (getWidth() - getDraggedViewWidth());
            }
            view.setTranslationX(Math.max(f2, Math.min(x, f)));
            invalidate();
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (Math.abs(this.viewToDrag.getTranslationX()) < ((float) (getWidth() - getDraggedViewWidth())) || motionEvent.getAction() != 1) {
                this.listener.onDragCancel();
                this.viewToDrag.animate().translationX(BitmapDescriptorFactory.HUE_RED).setDuration(200).start();
                invalidate();
                startAnimatingArrows();
                this.dragging = false;
            } else {
                this.listener.onDragComplete();
            }
        }
        return this.dragging;
    }

    public void reset() {
        if (this.arrowAnim != null && !this.canceled) {
            this.listener.onDragCancel();
            this.viewToDrag.animate().translationX(BitmapDescriptorFactory.HUE_RED).setDuration(200).start();
            invalidate();
            startAnimatingArrows();
            this.dragging = false;
        }
    }

    public void setColor(int i) {
        this.pullBgPaint.setColor(i);
        this.pullBgPaint.setAlpha(178);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setViewToDrag(View view, boolean z) {
        this.viewToDrag = view;
        this.dragFromRight = z;
        updateArrowPath();
    }

    public void startAnimatingArrows() {
        if (!this.animatingArrows && this.arrowAnim != null) {
            this.animatingArrows = true;
            if (this.arrowAnim != null) {
                this.arrowAnim.start();
            }
        }
    }

    public void stopAnimatingArrows() {
        this.animatingArrows = false;
    }
}
