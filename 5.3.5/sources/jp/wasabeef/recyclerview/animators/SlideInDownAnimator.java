package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;

public class SlideInDownAnimator extends BaseItemAnimator {
    public SlideInDownAnimator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationY((float) (-holder.itemView.getHeight())).alpha(0.0f).setDuration(getRemoveDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, (float) (-holder.itemView.getHeight()));
        ViewCompat.setAlpha(holder.itemView, 0.0f);
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationY(0.0f).alpha(1.0f).setDuration(getAddDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
