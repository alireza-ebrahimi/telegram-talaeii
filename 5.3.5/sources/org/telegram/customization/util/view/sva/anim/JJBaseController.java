package org.telegram.customization.util.view.sva.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public abstract class JJBaseController {
    public static final float DEFAULT_ANIM_ENDF = 1.0f;
    public static final float DEFAULT_ANIM_STARTF = 0.0f;
    public static final int DEFAULT_ANIM_TIME = 500;
    public static final int STATE_ANIM_NONE = 0;
    public static final int STATE_ANIM_START = 1;
    public static final int STATE_ANIM_STOP = 2;
    protected int bgColor;
    protected int color;
    protected float[] mPos = new float[2];
    protected float mPro = -1.0f;
    private WeakReference<View> mSearchView;
    protected int mState = 0;
    protected float scale;
    protected float size;

    /* renamed from: org.telegram.customization.util.view.sva.anim.JJBaseController$2 */
    class C09042 extends AnimatorListenerAdapter {
        C09042() {
        }

        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public void setColor(int color) {
        this.color = color;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public void setSearchView(View searchView) {
        this.mSearchView = new WeakReference(searchView);
    }

    public View getSearchView() {
        return this.mSearchView != null ? (View) this.mSearchView.get() : null;
    }

    public int getWidth() {
        return getSearchView() != null ? getSearchView().getWidth() : 0;
    }

    public int getHeight() {
        return getSearchView() != null ? getSearchView().getHeight() : 0;
    }

    public void startAnim() {
    }

    public void resetAnim() {
    }

    public ValueAnimator startSearchViewAnim() {
        return startSearchViewAnim(0.0f, 1.0f, 500);
    }

    public ValueAnimator startSearchViewAnim(float startF, float endF, long time) {
        return startSearchViewAnim(startF, endF, time, null);
    }

    public ValueAnimator startSearchViewAnim(float startF, float endF, long time, final PathMeasure pathMeasure) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[]{startF, endF});
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                JJBaseController.this.mPro = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (pathMeasure != null) {
                    pathMeasure.getPosTan(JJBaseController.this.mPro, JJBaseController.this.mPos, null);
                }
                JJBaseController.this.getSearchView().invalidate();
            }
        });
        valueAnimator.addListener(new C09042());
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        this.mPro = 0.0f;
        return valueAnimator;
    }
}
