package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.AddAnimationInfo;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ChangeAnimationInfo;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemAddAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemChangeAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemMoveAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemRemoveAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.MoveAnimationInfo;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.RemoveAnimationInfo;
import java.util.List;

public class RefactoredDefaultItemAnimator extends GeneralItemAnimator {

    protected static class DefaultItemAddAnimationManager extends ItemAddAnimationManager {
        public DefaultItemAddAnimationManager(BaseItemAnimator itemAnimator) {
            super(itemAnimator);
        }

        protected void onCreateAnimation(AddAnimationInfo info) {
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(info.holder.itemView);
            animator.alpha(1.0f);
            animator.setDuration(getDuration());
            startActiveItemAnimation(info, info.holder, animator);
        }

        protected void onAnimationEndedSuccessfully(AddAnimationInfo info, ViewHolder item) {
        }

        protected void onAnimationEndedBeforeStarted(AddAnimationInfo info, ViewHolder item) {
            ViewCompat.setAlpha(item.itemView, 1.0f);
        }

        protected void onAnimationCancel(AddAnimationInfo info, ViewHolder item) {
            ViewCompat.setAlpha(item.itemView, 1.0f);
        }

        public boolean addPendingAnimation(ViewHolder item) {
            resetAnimation(item);
            ViewCompat.setAlpha(item.itemView, 0.0f);
            enqueuePendingAnimationInfo(new AddAnimationInfo(item));
            return true;
        }
    }

    protected static class DefaultItemChangeAnimationManager extends ItemChangeAnimationManager {
        public DefaultItemChangeAnimationManager(BaseItemAnimator itemAnimator) {
            super(itemAnimator);
        }

        protected void onCreateChangeAnimationForOldItem(ChangeAnimationInfo info) {
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(info.oldHolder.itemView);
            animator.setDuration(getDuration());
            animator.translationX((float) (info.toX - info.fromX));
            animator.translationY((float) (info.toY - info.fromY));
            animator.alpha(0.0f);
            startActiveItemAnimation(info, info.oldHolder, animator);
        }

        protected void onCreateChangeAnimationForNewItem(ChangeAnimationInfo info) {
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(info.newHolder.itemView);
            animator.translationX(0.0f);
            animator.translationY(0.0f);
            animator.setDuration(getDuration());
            animator.alpha(1.0f);
            startActiveItemAnimation(info, info.newHolder, animator);
        }

        protected void onAnimationEndedSuccessfully(ChangeAnimationInfo info, ViewHolder item) {
            View view = item.itemView;
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
        }

        protected void onAnimationEndedBeforeStarted(ChangeAnimationInfo info, ViewHolder item) {
            View view = item.itemView;
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
        }

        protected void onAnimationCancel(ChangeAnimationInfo info, ViewHolder item) {
        }

        public boolean addPendingAnimation(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            float prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView);
            float prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView);
            float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);
            resetAnimation(oldHolder);
            int deltaX = (int) (((float) (toX - fromX)) - prevTranslationX);
            int deltaY = (int) (((float) (toY - fromY)) - prevTranslationY);
            ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX);
            ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY);
            ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);
            if (newHolder != null) {
                resetAnimation(newHolder);
                ViewCompat.setTranslationX(newHolder.itemView, (float) (-deltaX));
                ViewCompat.setTranslationY(newHolder.itemView, (float) (-deltaY));
                ViewCompat.setAlpha(newHolder.itemView, 0.0f);
            }
            enqueuePendingAnimationInfo(new ChangeAnimationInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
            return true;
        }
    }

    protected static class DefaultItemMoveAnimationManager extends ItemMoveAnimationManager {
        public DefaultItemMoveAnimationManager(BaseItemAnimator itemAnimator) {
            super(itemAnimator);
        }

        protected void onCreateAnimation(MoveAnimationInfo info) {
            View view = info.holder.itemView;
            int deltaY = info.toY - info.fromY;
            if (info.toX - info.fromX != 0) {
                ViewCompat.animate(view).translationX(0.0f);
            }
            if (deltaY != 0) {
                ViewCompat.animate(view).translationY(0.0f);
            }
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(view);
            animator.setDuration(getDuration());
            startActiveItemAnimation(info, info.holder, animator);
        }

        protected void onAnimationEndedSuccessfully(MoveAnimationInfo info, ViewHolder item) {
        }

        protected void onAnimationEndedBeforeStarted(MoveAnimationInfo info, ViewHolder item) {
            View view = item.itemView;
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setTranslationX(view, 0.0f);
        }

        protected void onAnimationCancel(MoveAnimationInfo info, ViewHolder item) {
            View view = item.itemView;
            int deltaX = info.toX - info.fromX;
            int deltaY = info.toY - info.fromY;
            if (deltaX != 0) {
                ViewCompat.animate(view).translationX(0.0f);
            }
            if (deltaY != 0) {
                ViewCompat.animate(view).translationY(0.0f);
            }
            if (deltaX != 0) {
                ViewCompat.setTranslationX(view, 0.0f);
            }
            if (deltaY != 0) {
                ViewCompat.setTranslationY(view, 0.0f);
            }
        }

        public boolean addPendingAnimation(ViewHolder item, int fromX, int fromY, int toX, int toY) {
            View view = item.itemView;
            fromX = (int) (((float) fromX) + ViewCompat.getTranslationX(item.itemView));
            fromY = (int) (((float) fromY) + ViewCompat.getTranslationY(item.itemView));
            resetAnimation(item);
            int deltaX = toX - fromX;
            int deltaY = toY - fromY;
            MoveAnimationInfo info = new MoveAnimationInfo(item, fromX, fromY, toX, toY);
            if (deltaX == 0 && deltaY == 0) {
                dispatchFinished(info, info.holder);
                info.clear(info.holder);
                return false;
            }
            if (deltaX != 0) {
                ViewCompat.setTranslationX(view, (float) (-deltaX));
            }
            if (deltaY != 0) {
                ViewCompat.setTranslationY(view, (float) (-deltaY));
            }
            enqueuePendingAnimationInfo(info);
            return true;
        }
    }

    protected static class DefaultItemRemoveAnimationManager extends ItemRemoveAnimationManager {
        public DefaultItemRemoveAnimationManager(BaseItemAnimator itemAnimator) {
            super(itemAnimator);
        }

        protected void onCreateAnimation(RemoveAnimationInfo info) {
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(info.holder.itemView);
            animator.setDuration(getDuration());
            animator.alpha(0.0f);
            startActiveItemAnimation(info, info.holder, animator);
        }

        protected void onAnimationEndedSuccessfully(RemoveAnimationInfo info, ViewHolder item) {
            ViewCompat.setAlpha(item.itemView, 1.0f);
        }

        protected void onAnimationEndedBeforeStarted(RemoveAnimationInfo info, ViewHolder item) {
            ViewCompat.setAlpha(item.itemView, 1.0f);
        }

        protected void onAnimationCancel(RemoveAnimationInfo info, ViewHolder item) {
        }

        public boolean addPendingAnimation(ViewHolder holder) {
            resetAnimation(holder);
            enqueuePendingAnimationInfo(new RemoveAnimationInfo(holder));
            return true;
        }
    }

    protected void onSetup() {
        setItemAddAnimationsManager(new DefaultItemAddAnimationManager(this));
        setItemRemoveAnimationManager(new DefaultItemRemoveAnimationManager(this));
        setItemChangeAnimationsManager(new DefaultItemChangeAnimationManager(this));
        setItemMoveAnimationsManager(new DefaultItemMoveAnimationManager(this));
    }

    protected void onSchedulePendingAnimations() {
        schedulePendingAnimationsByDefaultRule();
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> payloads) {
        return !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads);
    }
}
