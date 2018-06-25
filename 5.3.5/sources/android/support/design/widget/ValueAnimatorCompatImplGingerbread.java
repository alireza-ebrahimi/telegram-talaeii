package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;

class ValueAnimatorCompatImplGingerbread extends Impl {
    private static final int DEFAULT_DURATION = 200;
    private static final int HANDLER_DELAY = 10;
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private float mAnimatedFraction;
    private long mDuration = 200;
    private final float[] mFloatValues = new float[2];
    private final int[] mIntValues = new int[2];
    private Interpolator mInterpolator;
    private boolean mIsRunning;
    private ArrayList<AnimatorListenerProxy> mListeners;
    private final Runnable mRunnable = new C00731();
    private long mStartTime;
    private ArrayList<AnimatorUpdateListenerProxy> mUpdateListeners;

    /* renamed from: android.support.design.widget.ValueAnimatorCompatImplGingerbread$1 */
    class C00731 implements Runnable {
        C00731() {
        }

        public void run() {
            ValueAnimatorCompatImplGingerbread.this.update();
        }
    }

    ValueAnimatorCompatImplGingerbread() {
    }

    public void start() {
        if (!this.mIsRunning) {
            if (this.mInterpolator == null) {
                this.mInterpolator = new AccelerateDecelerateInterpolator();
            }
            this.mIsRunning = true;
            this.mAnimatedFraction = 0.0f;
            startInternal();
        }
    }

    final void startInternal() {
        this.mStartTime = SystemClock.uptimeMillis();
        dispatchAnimationUpdate();
        dispatchAnimationStart();
        sHandler.postDelayed(this.mRunnable, 10);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void addListener(AnimatorListenerProxy listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(listener);
    }

    public void addUpdateListener(AnimatorUpdateListenerProxy updateListener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList();
        }
        this.mUpdateListeners.add(updateListener);
    }

    public void setIntValues(int from, int to) {
        this.mIntValues[0] = from;
        this.mIntValues[1] = to;
    }

    public int getAnimatedIntValue() {
        return AnimationUtils.lerp(this.mIntValues[0], this.mIntValues[1], getAnimatedFraction());
    }

    public void setFloatValues(float from, float to) {
        this.mFloatValues[0] = from;
        this.mFloatValues[1] = to;
    }

    public float getAnimatedFloatValue() {
        return AnimationUtils.lerp(this.mFloatValues[0], this.mFloatValues[1], getAnimatedFraction());
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public void cancel() {
        this.mIsRunning = false;
        sHandler.removeCallbacks(this.mRunnable);
        dispatchAnimationCancel();
        dispatchAnimationEnd();
    }

    public float getAnimatedFraction() {
        return this.mAnimatedFraction;
    }

    public void end() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            sHandler.removeCallbacks(this.mRunnable);
            this.mAnimatedFraction = 1.0f;
            dispatchAnimationUpdate();
            dispatchAnimationEnd();
        }
    }

    public long getDuration() {
        return this.mDuration;
    }

    final void update() {
        if (this.mIsRunning) {
            float linearFraction = MathUtils.constrain(((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mDuration), 0.0f, 1.0f);
            if (this.mInterpolator != null) {
                linearFraction = this.mInterpolator.getInterpolation(linearFraction);
            }
            this.mAnimatedFraction = linearFraction;
            dispatchAnimationUpdate();
            if (SystemClock.uptimeMillis() >= this.mStartTime + this.mDuration) {
                this.mIsRunning = false;
                dispatchAnimationEnd();
            }
        }
        if (this.mIsRunning) {
            sHandler.postDelayed(this.mRunnable, 10);
        }
    }

    private void dispatchAnimationUpdate() {
        if (this.mUpdateListeners != null) {
            int count = this.mUpdateListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorUpdateListenerProxy) this.mUpdateListeners.get(i)).onAnimationUpdate();
            }
        }
    }

    private void dispatchAnimationStart() {
        if (this.mListeners != null) {
            int count = this.mListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorListenerProxy) this.mListeners.get(i)).onAnimationStart();
            }
        }
    }

    private void dispatchAnimationCancel() {
        if (this.mListeners != null) {
            int count = this.mListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorListenerProxy) this.mListeners.get(i)).onAnimationCancel();
            }
        }
    }

    private void dispatchAnimationEnd() {
        if (this.mListeners != null) {
            int count = this.mListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorListenerProxy) this.mListeners.get(i)).onAnimationEnd();
            }
        }
    }
}
