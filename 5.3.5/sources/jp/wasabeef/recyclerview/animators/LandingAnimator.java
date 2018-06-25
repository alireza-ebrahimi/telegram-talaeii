package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;

public class LandingAnimator extends BaseItemAnimator {
    public LandingAnimator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).alpha(0.0f).scaleX(1.5f).scaleY(1.5f).setDuration(getRemoveDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 0.0f);
        ViewCompat.setScaleX(holder.itemView, 1.5f);
        ViewCompat.setScaleY(holder.itemView, 1.5f);
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(getAddDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
