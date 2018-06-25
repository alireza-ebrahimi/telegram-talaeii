package jp.wasabeef.recyclerview.animators.holder;

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;

public interface AnimateViewHolder {
    void animateAddImpl(ViewHolder viewHolder, ViewPropertyAnimatorListener viewPropertyAnimatorListener);

    void animateRemoveImpl(ViewHolder viewHolder, ViewPropertyAnimatorListener viewPropertyAnimatorListener);

    void preAnimateAddImpl(ViewHolder viewHolder);

    void preAnimateRemoveImpl(ViewHolder viewHolder);
}
