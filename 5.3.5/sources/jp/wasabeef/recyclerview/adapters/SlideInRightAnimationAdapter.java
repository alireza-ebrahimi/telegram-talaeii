package jp.wasabeef.recyclerview.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;

public class SlideInRightAnimationAdapter extends AnimationAdapter {
    public SlideInRightAnimationAdapter(Adapter adapter) {
        super(adapter);
    }

    protected Animator[] getAnimators(View view) {
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(view, "translationX", new float[]{(float) view.getRootView().getWidth(), 0.0f});
        return animatorArr;
    }
}
