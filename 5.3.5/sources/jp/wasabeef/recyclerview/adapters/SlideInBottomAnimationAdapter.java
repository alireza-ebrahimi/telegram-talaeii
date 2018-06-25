package jp.wasabeef.recyclerview.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;

public class SlideInBottomAnimationAdapter extends AnimationAdapter {
    public SlideInBottomAnimationAdapter(Adapter adapter) {
        super(adapter);
    }

    protected Animator[] getAnimators(View view) {
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getMeasuredHeight(), 0.0f});
        return animatorArr;
    }
}
