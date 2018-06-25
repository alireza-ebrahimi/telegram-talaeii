package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;

public class FlipInBottomXAnimator extends BaseItemAnimator {
    public FlipInBottomXAnimator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).rotationX(-90.0f).setDuration(getRemoveDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setRotationX(holder.itemView, -90.0f);
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).rotationX(0.0f).setDuration(getAddDuration()).setInterpolator(this.mInterpolator).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
