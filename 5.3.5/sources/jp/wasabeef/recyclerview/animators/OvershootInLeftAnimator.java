package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.OvershootInterpolator;

public class OvershootInLeftAnimator extends BaseItemAnimator {
    private final float mTension;

    public OvershootInLeftAnimator() {
        this.mTension = 2.0f;
    }

    public OvershootInLeftAnimator(float mTension) {
        this.mTension = mTension;
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX((float) (-holder.itemView.getRootView().getWidth())).setDuration(getRemoveDuration()).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, (float) (-holder.itemView.getRootView().getWidth()));
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationX(0.0f).setDuration(getAddDuration()).setListener(new DefaultAddVpaListener(holder)).setInterpolator(new OvershootInterpolator(this.mTension)).setStartDelay(getAddDelay(holder)).start();
    }
}
