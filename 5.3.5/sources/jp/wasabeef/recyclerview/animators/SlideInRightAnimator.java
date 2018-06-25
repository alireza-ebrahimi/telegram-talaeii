package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;

public class SlideInRightAnimator extends BaseItemAnimator {
    public SlideInRightAnimator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX((float) holder.itemView.getRootView().getWidth()).setDuration(getRemoveDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, (float) holder.itemView.getRootView().getWidth());
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX(0.0f).setDuration(getAddDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
