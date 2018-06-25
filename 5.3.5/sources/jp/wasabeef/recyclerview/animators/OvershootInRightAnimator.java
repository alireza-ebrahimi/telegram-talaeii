package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.OvershootInterpolator;

public class OvershootInRightAnimator extends BaseItemAnimator {
    private final float mTension;

    public OvershootInRightAnimator() {
        this.mTension = 2.0f;
    }

    public OvershootInRightAnimator(float mTension) {
        this.mTension = mTension;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX((float) holder.itemView.getRootView().getWidth()).setDuration(getRemoveDuration()).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, (float) holder.itemView.getRootView().getWidth());
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX(0.0f).setDuration(getAddDuration()).setInterpolator(new OvershootInterpolator(this.mTension)).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
