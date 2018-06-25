package jp.wasabeef.recyclerview.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;

public class ScaleInAnimationAdapter extends AnimationAdapter {
    private static final float DEFAULT_SCALE_FROM = 0.5f;
    private final float mFrom;

    public ScaleInAnimationAdapter(Adapter adapter) {
        this(adapter, DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimationAdapter(Adapter adapter, float from) {
        super(adapter);
        this.mFrom = from;
    }

    protected Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", new float[]{this.mFrom, 1.0f});
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", new float[]{this.mFrom, 1.0f});
        return new ObjectAnimator[]{scaleX, scaleY};
    }
}
