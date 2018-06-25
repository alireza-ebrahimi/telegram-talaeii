package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemRemoveAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.RemoveAnimationInfo;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemViewHolder;

public class SwipeDismissItemAnimator extends DraggableItemAnimator {
    public static final Interpolator MOVE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    protected static class SwipeDismissItemRemoveAnimationManager extends ItemRemoveAnimationManager {
        protected static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();

        public SwipeDismissItemRemoveAnimationManager(BaseItemAnimator itemAnimator) {
            super(itemAnimator);
        }

        protected void onCreateAnimation(RemoveAnimationInfo info) {
            ViewPropertyAnimatorCompat animator;
            if (isSwipeDismissed(info.holder)) {
                animator = ViewCompat.animate(info.holder.itemView);
                animator.setDuration(getDuration());
            } else {
                animator = ViewCompat.animate(info.holder.itemView);
                animator.setDuration(getDuration());
                animator.setInterpolator(DEFAULT_INTERPOLATOR);
                animator.alpha(0.0f);
            }
            startActiveItemAnimation(info, info.holder, animator);
        }

        protected static boolean isSwipeDismissed(ViewHolder item) {
            if (!(item instanceof SwipeableItemViewHolder)) {
                return false;
            }
            SwipeableItemViewHolder item2 = (SwipeableItemViewHolder) item;
            int result = item2.getSwipeResult();
            int reaction = item2.getAfterSwipeReaction();
            if ((result == 2 || result == 3 || result == 4 || result == 5) && reaction == 1) {
                return true;
            }
            return false;
        }

        protected static boolean isSwipeDismissed(RemoveAnimationInfo info) {
            return info instanceof SwipeDismissRemoveAnimationInfo;
        }

        protected void onAnimationEndedSuccessfully(RemoveAnimationInfo info, ViewHolder item) {
            View view = item.itemView;
            if (isSwipeDismissed(info)) {
                ViewCompat.setTranslationX(view, 0.0f);
                ViewCompat.setTranslationY(view, 0.0f);
                return;
            }
            ViewCompat.setAlpha(view, 1.0f);
        }

        protected void onAnimationEndedBeforeStarted(RemoveAnimationInfo info, ViewHolder item) {
            View view = item.itemView;
            if (isSwipeDismissed(info)) {
                ViewCompat.setTranslationX(view, 0.0f);
                ViewCompat.setTranslationY(view, 0.0f);
                return;
            }
            ViewCompat.setAlpha(view, 1.0f);
        }

        protected void onAnimationCancel(RemoveAnimationInfo info, ViewHolder item) {
        }

        public boolean addPendingAnimation(ViewHolder holder) {
            if (isSwipeDismissed(holder)) {
                View itemView = holder.itemView;
                int prevItemX = (int) (ViewCompat.getTranslationX(itemView) + 0.5f);
                int prevItemY = (int) (ViewCompat.getTranslationY(itemView) + 0.5f);
                endAnimation(holder);
                ViewCompat.setTranslationX(itemView, (float) prevItemX);
                ViewCompat.setTranslationY(itemView, (float) prevItemY);
                enqueuePendingAnimationInfo(new SwipeDismissRemoveAnimationInfo(holder));
            } else {
                endAnimation(holder);
                enqueuePendingAnimationInfo(new RemoveAnimationInfo(holder));
            }
            return true;
        }
    }

    protected static class SwipeDismissRemoveAnimationInfo extends RemoveAnimationInfo {
        public SwipeDismissRemoveAnimationInfo(ViewHolder holder) {
            super(holder);
        }
    }

    protected void onSetup() {
        setItemAddAnimationsManager(new DefaultItemAddAnimationManager(this));
        setItemRemoveAnimationManager(new SwipeDismissItemRemoveAnimationManager(this));
        setItemChangeAnimationsManager(new DefaultItemChangeAnimationManager(this));
        setItemMoveAnimationsManager(new DefaultItemMoveAnimationManager(this));
        setRemoveDuration(150);
        setMoveDuration(150);
    }
}
