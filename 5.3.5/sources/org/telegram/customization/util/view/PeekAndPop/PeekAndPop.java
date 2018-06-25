package org.telegram.customization.util.view.PeekAndPop;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import org.ir.talaeii.R;
import org.telegram.customization.util.view.PeekAndPop.model.HoldAndReleaseView;
import org.telegram.customization.util.view.PeekAndPop.model.LongHoldView;

public class PeekAndPop {
    protected static final int ANIMATION_PEEK_DURATION = 275;
    protected static final int ANIMATION_POP_DURATION = 250;
    public static final int FLING_DOWNWARDS = 1;
    public static final int FLING_UPWARDS = 0;
    private static final float FLING_VELOCITY_MAX = 1000.0f;
    private static final int FLING_VELOCITY_THRESHOLD = 3000;
    protected static final long HOLD_AND_RELEASE_DURATION = 50;
    protected static final long LONG_CLICK_DURATION = 200;
    protected static final long LONG_HOLD_DURATION = 850;
    private static final int PEEK_VIEW_MARGIN = 12;
    private boolean allowDownwardsFling;
    private boolean allowUpwardsFling;
    private boolean animateFling;
    private boolean blurBackground;
    protected Builder builder;
    protected ViewGroup contentView;
    protected HoldAndReleaseView currentHoldAndReleaseView;
    private int customLongHoldDuration = -1;
    protected int downX;
    protected int downY;
    private boolean enabled = true;
    protected GestureDetector gestureDetector;
    protected GestureListener gestureListener;
    protected ArrayList<HoldAndReleaseView> holdAndReleaseViews;
    private Timer longHoldTimer = new Timer();
    protected ArrayList<LongHoldView> longHoldViews;
    protected OnFlingToActionListener onFlingToActionListener;
    protected OnGeneralActionListener onGeneralActionListener;
    protected OnHoldAndReleaseListener onHoldAndReleaseListener;
    protected OnLongHoldListener onLongHoldListener;
    protected int orientation;
    protected PeekAnimationHelper peekAnimationHelper;
    protected ViewGroup peekLayout;
    protected View peekView;
    protected int peekViewMargin;
    protected float[] peekViewOriginalPosition;
    protected long popTime;

    /* renamed from: org.telegram.customization.util.view.PeekAndPop.PeekAndPop$1 */
    class C08971 implements OnGlobalLayoutListener {
        C08971() {
        }

        public void onGlobalLayout() {
            PeekAndPop.this.initialisePeekViewOriginalPosition();
        }
    }

    /* renamed from: org.telegram.customization.util.view.PeekAndPop.PeekAndPop$2 */
    class C08982 implements OnClickListener {
        C08982() {
        }

        public void onClick(View v) {
        }
    }

    /* renamed from: org.telegram.customization.util.view.PeekAndPop.PeekAndPop$4 */
    class C09004 implements AnimatorListener {
        C09004() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            PeekAndPop.this.resetViews();
            animation.cancel();
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public static class Builder {
        protected final Activity activity;
        protected boolean allowDownwardsFling = true;
        protected boolean allowUpwardsFling = true;
        protected boolean animateFling = true;
        protected boolean blurBackground = true;
        protected ArrayList<View> longClickViews;
        protected OnFlingToActionListener onFlingToActionListener;
        protected OnGeneralActionListener onGeneralActionListener;
        protected OnHoldAndReleaseListener onHoldAndReleaseListener;
        protected OnLongHoldListener onLongHoldListener;
        protected ViewGroup parentViewGroup;
        protected int peekLayoutId = -1;
        protected View peekLayoutView;

        public Builder(@NonNull Activity activity) {
            this.activity = activity;
            this.longClickViews = new ArrayList();
        }

        public Builder peekLayout(@LayoutRes int peekLayoutId) {
            this.peekLayoutId = peekLayoutId;
            return this;
        }

        public Builder peekLayout(View peekLayoutView) {
            this.peekLayoutView = peekLayoutView;
            return this;
        }

        public Builder longClickViews(@NonNull View... longClickViews) {
            for (Object add : longClickViews) {
                this.longClickViews.add(add);
            }
            return this;
        }

        public Builder onFlingToActionListener(@NonNull OnFlingToActionListener onFlingToActionListener) {
            this.onFlingToActionListener = onFlingToActionListener;
            return this;
        }

        public Builder onGeneralActionListener(@NonNull OnGeneralActionListener onGeneralActionListener) {
            this.onGeneralActionListener = onGeneralActionListener;
            return this;
        }

        public Builder onLongHoldListener(@NonNull OnLongHoldListener onLongHoldListener) {
            this.onLongHoldListener = onLongHoldListener;
            return this;
        }

        public Builder onHoldAndReleaseListener(@NonNull OnHoldAndReleaseListener onHoldAndReleaseListener) {
            this.onHoldAndReleaseListener = onHoldAndReleaseListener;
            return this;
        }

        public Builder parentViewGroupToDisallowTouchEvents(@NonNull ViewGroup parentViewGroup) {
            this.parentViewGroup = parentViewGroup;
            return this;
        }

        public Builder blurBackground(boolean blurBackground) {
            this.blurBackground = blurBackground;
            return this;
        }

        public Builder animateFling(boolean animateFling) {
            this.animateFling = animateFling;
            return this;
        }

        public Builder flingTypes(boolean allowUpwardsFling, boolean allowDownwardsFling) {
            this.allowUpwardsFling = allowUpwardsFling;
            this.allowDownwardsFling = allowDownwardsFling;
            return this;
        }

        public PeekAndPop build() {
            if (this.peekLayoutId != -1 || this.peekLayoutView != null) {
                return new PeekAndPop(this);
            }
            throw new IllegalArgumentException("No peekLayoutId specified.");
        }
    }

    public @interface FlingDirections {
    }

    protected class GestureListener extends SimpleOnGestureListener {
        private int position;
        private View view;

        protected GestureListener() {
        }

        public void setView(View view) {
            this.view = view;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent firstEvent, MotionEvent secondEvent, float velocityX, float velocityY) {
            if (PeekAndPop.this.onFlingToActionListener != null) {
                return handleFling(velocityX, velocityY);
            }
            return true;
        }

        private boolean handleFling(float velocityX, float velocityY) {
            if (PeekAndPop.this.orientation == 1) {
                if (velocityY < -3000.0f && PeekAndPop.this.allowUpwardsFling) {
                    flingToAction(0, velocityX, velocityY);
                    return false;
                } else if (velocityY > 3000.0f && PeekAndPop.this.allowDownwardsFling) {
                    flingToAction(1, velocityX, velocityY);
                    return false;
                }
            } else if (PeekAndPop.this.orientation == 2) {
                if (velocityX < -3000.0f && PeekAndPop.this.allowUpwardsFling) {
                    flingToAction(0, velocityX, velocityY);
                    return false;
                } else if (velocityX > 3000.0f && PeekAndPop.this.allowDownwardsFling) {
                    flingToAction(1, velocityX, velocityY);
                    return false;
                }
            }
            return true;
        }

        private void flingToAction(@FlingDirections int direction, float velocityX, float velocityY) {
            PeekAndPop.this.onFlingToActionListener.onFlingToAction(this.view, this.position, direction);
            if (!PeekAndPop.this.animateFling) {
                return;
            }
            if (direction == 0) {
                PeekAndPop.this.peekAnimationHelper.animateExpand(250, PeekAndPop.this.popTime);
                PeekAndPop.this.peekAnimationHelper.animateFling(velocityX, velocityY, 250, PeekAndPop.this.popTime, -1000.0f);
                return;
            }
            PeekAndPop.this.peekAnimationHelper.animateFling(velocityX, velocityY, 250, PeekAndPop.this.popTime, PeekAndPop.FLING_VELOCITY_MAX);
        }
    }

    public interface OnFlingToActionListener {
        void onFlingToAction(View view, int i, int i2);
    }

    public interface OnGeneralActionListener {
        void onPeek(View view, int i);

        void onPop(View view, int i);
    }

    public interface OnHoldAndReleaseListener {
        void onHold(View view, int i);

        void onLeave(View view, int i);

        void onRelease(View view, int i);
    }

    public interface OnLongHoldListener {
        void onEnter(View view, int i);

        void onLongHold(View view, int i);
    }

    protected class PeekAndPopOnTouchListener implements OnTouchListener {
        private Runnable longHoldRunnable;
        private boolean peekShown;
        private int position;

        public PeekAndPopOnTouchListener(int position) {
            this.position = position;
        }

        public boolean onTouch(View view, MotionEvent event) {
            if (!PeekAndPop.this.enabled) {
                return false;
            }
            if (event.getAction() == 0) {
                this.peekShown = false;
                cancelPendingTimer(view);
                startTimer(view);
            } else if (event.getAction() == 1 || event.getAction() == 3) {
                cancelPendingTimer(view);
            }
            if (this.peekShown) {
                PeekAndPop.this.handleTouch(view, event, this.position);
            }
            return this.peekShown;
        }

        private void cancelPendingTimer(@NonNull View view) {
            PeekAndPop.this.longHoldTimer.cancel();
            if (this.longHoldRunnable != null) {
                this.longHoldRunnable = new PeekAndPop$PeekAndPopOnTouchListener$1(this, view);
                PeekAndPop.this.builder.activity.runOnUiThread(this.longHoldRunnable);
            }
        }

        private void startTimer(@NonNull View view) {
            PeekAndPop.this.longHoldTimer = new Timer();
            PeekAndPop.this.longHoldTimer.schedule(new PeekAndPop$PeekAndPopOnTouchListener$2(this, view), 200);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public PeekAndPop(Builder builder) {
        this.builder = builder;
        init();
    }

    protected void init() {
        this.onFlingToActionListener = this.builder.onFlingToActionListener;
        this.onGeneralActionListener = this.builder.onGeneralActionListener;
        this.onLongHoldListener = this.builder.onLongHoldListener;
        this.onHoldAndReleaseListener = this.builder.onHoldAndReleaseListener;
        this.gestureListener = new GestureListener();
        this.gestureDetector = new GestureDetector(this.builder.activity, this.gestureListener);
        initialiseGestureListeners();
        this.longHoldViews = new ArrayList();
        this.holdAndReleaseViews = new ArrayList();
        this.blurBackground = this.builder.blurBackground;
        this.animateFling = this.builder.animateFling;
        this.allowUpwardsFling = this.builder.allowUpwardsFling;
        this.allowDownwardsFling = this.builder.allowDownwardsFling;
        this.orientation = this.builder.activity.getResources().getConfiguration().orientation;
        this.peekViewMargin = DimensionUtil.convertDpToPx(this.builder.activity.getApplicationContext(), 12);
        initialisePeekView();
    }

    protected void initialisePeekView() {
        LayoutInflater inflater = LayoutInflater.from(this.builder.activity);
        this.contentView = (ViewGroup) this.builder.activity.findViewById(16908290).getRootView();
        this.peekLayout = (FrameLayout) inflater.inflate(R.layout.peek_background, this.contentView, false);
        if (this.builder.peekLayoutId != -1) {
            this.peekView = inflater.inflate(this.builder.peekLayoutId, this.peekLayout, false);
            this.peekView.setId(R.id.peek_view);
            LayoutParams layoutParams = (LayoutParams) this.peekView.getLayoutParams();
            layoutParams.gravity = 17;
            if (this.orientation == 2) {
                layoutParams.topMargin = this.peekViewMargin;
            }
            this.peekLayout.addView(this.peekView, layoutParams);
        } else {
            this.peekView = this.builder.peekLayoutView;
            this.peekView.setId(R.id.peek_view);
            this.peekLayout.addView(this.peekView);
        }
        this.contentView.addView(this.peekLayout);
        this.peekLayout.setVisibility(8);
        this.peekLayout.setAlpha(0.0f);
        this.peekLayout.requestLayout();
        this.peekAnimationHelper = new PeekAnimationHelper(this.builder.activity.getApplicationContext(), this.peekLayout, this.peekView);
        bringViewsToFront();
        initialiseViewTreeObserver();
        resetViews();
    }

    private void bringViewsToFront() {
        if (VERSION.SDK_INT >= 21) {
            this.peekLayout.setElevation(10.0f);
            this.peekView.setElevation(10.0f);
            return;
        }
        this.peekLayout.bringToFront();
        this.peekView.bringToFront();
        this.contentView.requestLayout();
        this.contentView.invalidate();
    }

    private void initialiseViewTreeObserver() {
        this.peekView.getViewTreeObserver().addOnGlobalLayoutListener(new C08971());
    }

    protected void initialiseGestureListeners() {
        for (int i = 0; i < this.builder.longClickViews.size(); i++) {
            initialiseGestureListener((View) this.builder.longClickViews.get(i), -1);
        }
        this.gestureDetector.setIsLongpressEnabled(false);
    }

    protected void initialiseGestureListener(@NonNull View view, int position) {
        view.setOnTouchListener(new PeekAndPopOnTouchListener(position));
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new C08982());
        }
    }

    protected void handleTouch(@NonNull View view, @NonNull MotionEvent event, int position) {
        if (event.getAction() == 1 || event.getAction() == 3) {
            pop(view, position);
        } else if (event.getAction() == 2) {
            this.downX = (int) event.getRawX();
            this.downY = (int) event.getRawY();
            if (this.onLongHoldListener != null) {
                checkLongHoldViews(position);
            }
            if (this.onHoldAndReleaseListener != null) {
                checkHoldAndReleaseViews(position);
            }
        }
        if (this.gestureDetector != null) {
            this.gestureDetector.onTouchEvent(event);
        }
    }

    private void checkLongHoldViews(int position) {
        for (int i = 0; i < this.longHoldViews.size(); i++) {
            LongHoldView longHoldView = (LongHoldView) this.longHoldViews.get(i);
            boolean viewInBounds = DimensionUtil.pointInViewBounds(longHoldView.getView(), this.downX, this.downY);
            if (viewInBounds && longHoldView.getLongHoldTimer() == null) {
                longHoldView.startLongHoldViewTimer(this, position, this.customLongHoldDuration != -1 ? (long) this.customLongHoldDuration : LONG_HOLD_DURATION);
                this.onLongHoldListener.onEnter(longHoldView.getView(), position);
            } else if (!(viewInBounds || longHoldView.getLongHoldTimer() == null)) {
                longHoldView.getLongHoldTimer().cancel();
                longHoldView.setLongHoldTimer(null);
            }
        }
    }

    private void checkHoldAndReleaseViews(int position) {
        for (int i = 0; i < this.holdAndReleaseViews.size(); i++) {
            HoldAndReleaseView holdAndReleaseView = (HoldAndReleaseView) this.holdAndReleaseViews.get(i);
            boolean viewInBounds = DimensionUtil.pointInViewBounds(holdAndReleaseView.getView(), this.downX, this.downY);
            if (viewInBounds && holdAndReleaseView.getHoldAndReleaseTimer() == null) {
                holdAndReleaseView.startHoldAndReleaseTimer(this, position, HOLD_AND_RELEASE_DURATION);
            } else if (!(viewInBounds || holdAndReleaseView.getHoldAndReleaseTimer() == null)) {
                holdAndReleaseView.getHoldAndReleaseTimer().cancel();
                holdAndReleaseView.setHoldAndReleaseTimer(null);
                if (holdAndReleaseView == this.currentHoldAndReleaseView) {
                    triggerOnLeaveEvent(holdAndReleaseView.getView(), holdAndReleaseView.getPosition());
                    holdAndReleaseView.setPosition(-1);
                    this.currentHoldAndReleaseView = null;
                }
            }
        }
    }

    public void sendOnLongHoldEvent(final View view, final int position) {
        this.builder.activity.runOnUiThread(new Runnable() {
            public void run() {
                PeekAndPop.this.onLongHoldListener.onLongHold(view, position);
            }
        });
    }

    private void initialisePeekViewOriginalPosition() {
        this.peekViewOriginalPosition = new float[2];
        this.peekViewOriginalPosition[0] = (float) ((this.peekLayout.getWidth() / 2) - (this.peekView.getWidth() / 2));
        this.peekViewOriginalPosition[1] = (float) (((this.peekLayout.getHeight() / 2) - (this.peekView.getHeight() / 2)) + this.peekViewMargin);
    }

    public void peek(@NonNull View longClickView, int index) {
        if (this.onGeneralActionListener != null) {
            this.onGeneralActionListener.onPeek(longClickView, index);
        }
        this.peekLayout.setVisibility(0);
        cancelClick(longClickView);
        if (VERSION.SDK_INT >= 17 && this.blurBackground) {
            blurBackground();
        } else if (VERSION.SDK_INT < 17 && this.blurBackground) {
            Log.e("PeekAndPop", "Unable to blur background, device version below 17");
        }
        this.peekAnimationHelper.animatePeek(ANIMATION_PEEK_DURATION);
        if (this.builder.parentViewGroup != null) {
            this.builder.parentViewGroup.requestDisallowInterceptTouchEvent(true);
        }
        this.downX = 0;
        this.downY = 0;
        this.gestureListener.setView(longClickView);
        this.gestureListener.setPosition(index);
    }

    private void cancelClick(@NonNull View longClickView) {
        MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 3, 0.0f, 0.0f, 0);
        longClickView.onTouchEvent(e);
        e.recycle();
    }

    private void blurBackground() {
        if (VERSION.SDK_INT >= 16) {
            this.peekLayout.setBackground(null);
            this.peekLayout.setBackground(new BitmapDrawable(this.builder.activity.getResources(), BlurBuilder.blur(this.contentView)));
            return;
        }
        this.peekLayout.setBackgroundDrawable(null);
        this.peekLayout.setBackgroundDrawable(new BitmapDrawable(this.builder.activity.getResources(), BlurBuilder.blur(this.contentView)));
    }

    protected void pop(@NonNull View longClickView, int index) {
        if (this.onGeneralActionListener != null) {
            this.onGeneralActionListener.onPop(longClickView, index);
        }
        if (!(this.currentHoldAndReleaseView == null || this.onHoldAndReleaseListener == null)) {
            this.onHoldAndReleaseListener.onRelease(this.currentHoldAndReleaseView.getView(), this.currentHoldAndReleaseView.getPosition());
        }
        resetTimers();
        this.peekAnimationHelper.animatePop(new C09004(), 250);
        this.popTime = System.currentTimeMillis();
    }

    private void resetViews() {
        this.peekLayout.setVisibility(8);
        this.downX = 0;
        this.downY = 0;
        for (int i = 0; i < this.longHoldViews.size(); i++) {
            Timer longHoldTimer = ((LongHoldView) this.longHoldViews.get(i)).getLongHoldTimer();
            if (longHoldTimer != null) {
                longHoldTimer.cancel();
                ((LongHoldView) this.longHoldViews.get(i)).setLongHoldTimer(null);
            }
        }
        if (this.peekViewOriginalPosition != null) {
            this.peekView.setX(this.peekViewOriginalPosition[0]);
            this.peekView.setY(this.peekViewOriginalPosition[1]);
        }
        this.peekView.setScaleX(0.85f);
        this.peekView.setScaleY(0.85f);
    }

    private void resetTimers() {
        this.currentHoldAndReleaseView = null;
        Iterator it = this.holdAndReleaseViews.iterator();
        while (it.hasNext()) {
            HoldAndReleaseView holdAndReleaseView = (HoldAndReleaseView) it.next();
            if (holdAndReleaseView.getHoldAndReleaseTimer() != null) {
                holdAndReleaseView.getHoldAndReleaseTimer().cancel();
            }
        }
        it = this.longHoldViews.iterator();
        while (it.hasNext()) {
            LongHoldView longHoldView = (LongHoldView) it.next();
            if (longHoldView.getLongHoldTimer() != null) {
                longHoldView.getLongHoldTimer().cancel();
            }
        }
    }

    public void destroy() {
        if (!(this.currentHoldAndReleaseView == null || this.onHoldAndReleaseListener == null)) {
            this.currentHoldAndReleaseView.getHoldAndReleaseTimer().cancel();
            this.currentHoldAndReleaseView = null;
        }
        for (int i = 0; i < this.longHoldViews.size(); i++) {
            Timer longHoldTimer = ((LongHoldView) this.longHoldViews.get(i)).getLongHoldTimer();
            if (longHoldTimer != null) {
                longHoldTimer.cancel();
                ((LongHoldView) this.longHoldViews.get(i)).setLongHoldTimer(null);
            }
        }
        this.builder = null;
    }

    public void setFlingTypes(boolean allowUpwardsFling, boolean allowDownwardsFling) {
        this.allowUpwardsFling = allowUpwardsFling;
        this.allowDownwardsFling = allowDownwardsFling;
    }

    public void setCurrentHoldAndReleaseView(@Nullable HoldAndReleaseView currentHoldAndReleaseView) {
        this.currentHoldAndReleaseView = currentHoldAndReleaseView;
    }

    public void setOnFlingToActionListener(@Nullable OnFlingToActionListener onFlingToActionListener) {
        this.onFlingToActionListener = onFlingToActionListener;
    }

    public void setOnGeneralActionListener(@Nullable OnGeneralActionListener onGeneralActionListener) {
        this.onGeneralActionListener = onGeneralActionListener;
    }

    public void setOnLongHoldListener(@Nullable OnLongHoldListener onLongHoldListener) {
        this.onLongHoldListener = onLongHoldListener;
    }

    public void setOnHoldAndReleaseListener(@Nullable OnHoldAndReleaseListener onHoldAndReleaseListener) {
        this.onHoldAndReleaseListener = onHoldAndReleaseListener;
    }

    public void addLongClickView(@NonNull View view, int position) {
        initialiseGestureListener(view, position);
    }

    public void addLongHoldView(@IdRes int longHoldViewId, boolean receiveMultipleEvents) {
        this.longHoldViews.add(new LongHoldView(this.peekView.findViewById(longHoldViewId), receiveMultipleEvents));
    }

    public void addHoldAndReleaseView(@IdRes int holdAndReleaseViewId) {
        this.holdAndReleaseViews.add(new HoldAndReleaseView(this.peekView.findViewById(holdAndReleaseViewId)));
    }

    public void triggerOnHoldEvent(@NonNull final View view, final int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                PeekAndPop.this.onHoldAndReleaseListener.onHold(view, position);
            }
        });
    }

    protected void triggerOnLeaveEvent(@NonNull final View view, final int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                PeekAndPop.this.onHoldAndReleaseListener.onLeave(view, position);
            }
        });
    }

    public View getPeekView() {
        return this.peekView;
    }

    public boolean isBlurBackground() {
        return this.blurBackground;
    }

    public void setBlurBackground(boolean blurBackground) {
        this.blurBackground = blurBackground;
    }

    public boolean isAnimateFling() {
        return this.animateFling;
    }

    public void setAnimateFling(boolean animateFling) {
        this.animateFling = animateFling;
    }

    public void setLongHoldDuration(int duration) {
        this.customLongHoldDuration = duration;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
