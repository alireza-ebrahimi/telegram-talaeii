package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;

public class FadeInRightAnimator extends BaseItemAnimator {
    public FadeInRightAnimator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX(((float) holder.itemView.getRootView().getWidth()) * 0.25f).alpha(0.0f).setDuration(getRemoveDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, ((float) holder.itemView.getRootView().getWidth()) * 0.25f);
        ViewCompat.setAlpha(holder.itemView, 0.0f);
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX(0.0f).alpha(1.0f).setDuration(getAddDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
