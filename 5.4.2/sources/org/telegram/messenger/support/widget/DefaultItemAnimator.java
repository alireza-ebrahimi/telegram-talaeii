package org.telegram.messenger.support.widget;

import android.support.v4.p017a.C0211a;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v4.view.bb;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    ArrayList<ViewHolder> mAddAnimations = new ArrayList();
    ArrayList<ArrayList<ViewHolder>> mAdditionsList = new ArrayList();
    ArrayList<ViewHolder> mChangeAnimations = new ArrayList();
    ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList();
    ArrayList<ViewHolder> mMoveAnimations = new ArrayList();
    ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList();
    private ArrayList<ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<ViewHolder> mPendingRemovals = new ArrayList();
    ArrayList<ViewHolder> mRemoveAnimations = new ArrayList();

    private static class VpaListenerAdapter implements bb {
        VpaListenerAdapter() {
        }

        public void onAnimationCancel(View view) {
        }

        public void onAnimationEnd(View view) {
        }

        public void onAnimationStart(View view) {
        }
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public ViewHolder newHolder;
        public ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            this(viewHolder, viewHolder2);
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            this.holder = viewHolder;
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }
    }

    private void animateRemoveImpl(final ViewHolder viewHolder) {
        final ax q = ah.m2827q(viewHolder.itemView);
        this.mRemoveAnimations.add(viewHolder);
        q.m3021a(getRemoveDuration()).m3020a((float) BitmapDescriptorFactory.HUE_RED).m3022a(new VpaListenerAdapter() {
            public void onAnimationEnd(View view) {
                q.m3022a(null);
                ah.m2800c(view, 1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished(viewHolder);
                DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(View view) {
                DefaultItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }
        }).m3029c();
    }

    private void endChangeAnimation(List<ChangeInfo> list, ViewHolder viewHolder) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = (ChangeInfo) list.get(size);
            if (endChangeAnimationIfNecessary(changeInfo, viewHolder) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                list.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, ViewHolder viewHolder) {
        boolean z = false;
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != viewHolder) {
            return false;
        } else {
            changeInfo.oldHolder = null;
            z = true;
        }
        ah.m2800c(viewHolder.itemView, 1.0f);
        ah.m2775a(viewHolder.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        ah.m2795b(viewHolder.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        dispatchChangeFinished(viewHolder, z);
        return true;
    }

    private void resetAnimation(ViewHolder viewHolder) {
        C0211a.m993a(viewHolder.itemView);
        endAnimation(viewHolder);
    }

    public boolean animateAdd(ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        ah.m2800c(viewHolder.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    void animateAddImpl(final ViewHolder viewHolder) {
        final ax q = ah.m2827q(viewHolder.itemView);
        this.mAddAnimations.add(viewHolder);
        q.m3020a(1.0f).m3021a(getAddDuration()).m3022a(new VpaListenerAdapter() {
            public void onAnimationCancel(View view) {
                ah.m2800c(view, 1.0f);
            }

            public void onAnimationEnd(View view) {
                q.m3022a(null);
                DefaultItemAnimator.this.dispatchAddFinished(viewHolder);
                DefaultItemAnimator.this.mAddAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(View view) {
                DefaultItemAnimator.this.dispatchAddStarting(viewHolder);
            }
        }).m3029c();
    }

    public boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder, i, i2, i3, i4);
        }
        float l = ah.m2822l(viewHolder.itemView);
        float m = ah.m2823m(viewHolder.itemView);
        float e = ah.m2806e(viewHolder.itemView);
        resetAnimation(viewHolder);
        int i5 = (int) (((float) (i3 - i)) - l);
        int i6 = (int) (((float) (i4 - i2)) - m);
        ah.m2775a(viewHolder.itemView, l);
        ah.m2795b(viewHolder.itemView, m);
        ah.m2800c(viewHolder.itemView, e);
        if (viewHolder2 != null) {
            resetAnimation(viewHolder2);
            ah.m2775a(viewHolder2.itemView, (float) (-i5));
            ah.m2795b(viewHolder2.itemView, (float) (-i6));
            ah.m2800c(viewHolder2.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        }
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, i, i2, i3, i4));
        return true;
    }

    void animateChangeImpl(final ChangeInfo changeInfo) {
        View view = null;
        ViewHolder viewHolder = changeInfo.oldHolder;
        View view2 = viewHolder == null ? null : viewHolder.itemView;
        ViewHolder viewHolder2 = changeInfo.newHolder;
        if (viewHolder2 != null) {
            view = viewHolder2.itemView;
        }
        if (view2 != null) {
            final ax a = ah.m2827q(view2).m3021a(getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            a.m3025b((float) (changeInfo.toX - changeInfo.fromX));
            a.m3028c((float) (changeInfo.toY - changeInfo.fromY));
            a.m3020a((float) BitmapDescriptorFactory.HUE_RED).m3022a(new VpaListenerAdapter() {
                public void onAnimationEnd(View view) {
                    a.m3022a(null);
                    ah.m2800c(view, 1.0f);
                    ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                    ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                public void onAnimationStart(View view) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }
            }).m3029c();
        }
        if (view != null) {
            a = ah.m2827q(view);
            this.mChangeAnimations.add(changeInfo.newHolder);
            a.m3025b((float) BitmapDescriptorFactory.HUE_RED).m3028c(BitmapDescriptorFactory.HUE_RED).m3021a(getChangeDuration()).m3020a(1.0f).m3022a(new VpaListenerAdapter() {
                public void onAnimationEnd(View view) {
                    a.m3022a(null);
                    ah.m2800c(view, 1.0f);
                    ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                    ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                public void onAnimationStart(View view) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }
            }).m3029c();
        }
    }

    public boolean animateMove(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        View view = viewHolder.itemView;
        int l = (int) (((float) i) + ah.m2822l(viewHolder.itemView));
        int m = (int) (((float) i2) + ah.m2823m(viewHolder.itemView));
        resetAnimation(viewHolder);
        int i5 = i3 - l;
        int i6 = i4 - m;
        if (i5 == 0 && i6 == 0) {
            dispatchMoveFinished(viewHolder);
            return false;
        }
        if (i5 != 0) {
            ah.m2775a(view, (float) (-i5));
        }
        if (i6 != 0) {
            ah.m2795b(view, (float) (-i6));
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, l, m, i3, i4));
        return true;
    }

    void animateMoveImpl(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        View view = viewHolder.itemView;
        final int i5 = i3 - i;
        final int i6 = i4 - i2;
        if (i5 != 0) {
            ah.m2827q(view).m3025b((float) BitmapDescriptorFactory.HUE_RED);
        }
        if (i6 != 0) {
            ah.m2827q(view).m3028c(BitmapDescriptorFactory.HUE_RED);
        }
        final ax q = ah.m2827q(view);
        this.mMoveAnimations.add(viewHolder);
        final ViewHolder viewHolder2 = viewHolder;
        q.m3021a(getMoveDuration()).m3022a(new VpaListenerAdapter() {
            public void onAnimationCancel(View view) {
                if (i5 != 0) {
                    ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                }
                if (i6 != 0) {
                    ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                }
            }

            public void onAnimationEnd(View view) {
                q.m3022a(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder2);
                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder2);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(View view) {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder2);
            }
        }).m3029c();
    }

    public boolean animateRemove(ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        return true;
    }

    public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List<Object> list) {
        return !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
    }

    void cancelAll(List<ViewHolder> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ah.m2827q(((ViewHolder) list.get(size)).itemView).m3027b();
        }
    }

    void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimation(ViewHolder viewHolder) {
        int size;
        View view = viewHolder.itemView;
        ah.m2827q(view).m3027b();
        for (size = this.mPendingMoves.size() - 1; size >= 0; size--) {
            if (((MoveInfo) this.mPendingMoves.get(size)).holder == viewHolder) {
                ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                dispatchMoveFinished(viewHolder);
                this.mPendingMoves.remove(size);
            }
        }
        endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            ah.m2800c(view, 1.0f);
            dispatchRemoveFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            ah.m2800c(view, 1.0f);
            dispatchAddFinished(viewHolder);
        }
        for (size = this.mChangesList.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) this.mChangesList.get(size);
            endChangeAnimation(arrayList, viewHolder);
            if (arrayList.isEmpty()) {
                this.mChangesList.remove(size);
            }
        }
        for (int size2 = this.mMovesList.size() - 1; size2 >= 0; size2--) {
            arrayList = (ArrayList) this.mMovesList.get(size2);
            int size3 = arrayList.size() - 1;
            while (size3 >= 0) {
                if (((MoveInfo) arrayList.get(size3)).holder == viewHolder) {
                    ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                    ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                    dispatchMoveFinished(viewHolder);
                    arrayList.remove(size3);
                    if (arrayList.isEmpty()) {
                        this.mMovesList.remove(size2);
                    }
                } else {
                    size3--;
                }
            }
        }
        for (size = this.mAdditionsList.size() - 1; size >= 0; size--) {
            arrayList = (ArrayList) this.mAdditionsList.get(size);
            if (arrayList.remove(viewHolder)) {
                ah.m2800c(view, 1.0f);
                dispatchAddFinished(viewHolder);
                if (arrayList.isEmpty()) {
                    this.mAdditionsList.remove(size);
                }
            }
        }
        if (this.mRemoveAnimations.remove(viewHolder)) {
        }
        if (this.mAddAnimations.remove(viewHolder)) {
        }
        if (this.mChangeAnimations.remove(viewHolder)) {
        }
        if (this.mMoveAnimations.remove(viewHolder)) {
            dispatchFinishedWhenDone();
        } else {
            dispatchFinishedWhenDone();
        }
    }

    public void endAnimations() {
        int size;
        for (size = this.mPendingMoves.size() - 1; size >= 0; size--) {
            MoveInfo moveInfo = (MoveInfo) this.mPendingMoves.get(size);
            View view = moveInfo.holder.itemView;
            ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
            ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
            dispatchMoveFinished(moveInfo.holder);
            this.mPendingMoves.remove(size);
        }
        for (size = this.mPendingRemovals.size() - 1; size >= 0; size--) {
            dispatchRemoveFinished((ViewHolder) this.mPendingRemovals.get(size));
            this.mPendingRemovals.remove(size);
        }
        for (size = this.mPendingAdditions.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = (ViewHolder) this.mPendingAdditions.get(size);
            ah.m2800c(viewHolder.itemView, 1.0f);
            dispatchAddFinished(viewHolder);
            this.mPendingAdditions.remove(size);
        }
        for (size = this.mPendingChanges.size() - 1; size >= 0; size--) {
            endChangeAnimationIfNecessary((ChangeInfo) this.mPendingChanges.get(size));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int size2;
            ArrayList arrayList;
            int size3;
            for (size2 = this.mMovesList.size() - 1; size2 >= 0; size2--) {
                arrayList = (ArrayList) this.mMovesList.get(size2);
                for (size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    MoveInfo moveInfo2 = (MoveInfo) arrayList.get(size3);
                    View view2 = moveInfo2.holder.itemView;
                    ah.m2795b(view2, (float) BitmapDescriptorFactory.HUE_RED);
                    ah.m2775a(view2, (float) BitmapDescriptorFactory.HUE_RED);
                    dispatchMoveFinished(moveInfo2.holder);
                    arrayList.remove(size3);
                    if (arrayList.isEmpty()) {
                        this.mMovesList.remove(arrayList);
                    }
                }
            }
            for (size2 = this.mAdditionsList.size() - 1; size2 >= 0; size2--) {
                arrayList = (ArrayList) this.mAdditionsList.get(size2);
                for (size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    ViewHolder viewHolder2 = (ViewHolder) arrayList.get(size3);
                    ah.m2800c(viewHolder2.itemView, 1.0f);
                    dispatchAddFinished(viewHolder2);
                    arrayList.remove(size3);
                    if (arrayList.isEmpty()) {
                        this.mAdditionsList.remove(arrayList);
                    }
                }
            }
            for (size2 = this.mChangesList.size() - 1; size2 >= 0; size2--) {
                arrayList = (ArrayList) this.mChangesList.get(size2);
                for (size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    endChangeAnimationIfNecessary((ChangeInfo) arrayList.get(size3));
                    if (arrayList.isEmpty()) {
                        this.mChangesList.remove(arrayList);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    public boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    public void runPendingAnimations() {
        int i = !this.mPendingRemovals.isEmpty() ? 1 : 0;
        int i2 = !this.mPendingMoves.isEmpty() ? 1 : 0;
        int i3 = !this.mPendingChanges.isEmpty() ? 1 : 0;
        int i4 = !this.mPendingAdditions.isEmpty() ? 1 : 0;
        if (i != 0 || i2 != 0 || i4 != 0 || i3 != 0) {
            final ArrayList arrayList;
            Runnable c36591;
            Iterator it = this.mPendingRemovals.iterator();
            while (it.hasNext()) {
                animateRemoveImpl((ViewHolder) it.next());
            }
            this.mPendingRemovals.clear();
            if (i2 != 0) {
                arrayList = new ArrayList();
                arrayList.addAll(this.mPendingMoves);
                this.mMovesList.add(arrayList);
                this.mPendingMoves.clear();
                c36591 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            MoveInfo moveInfo = (MoveInfo) it.next();
                            DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                        }
                        arrayList.clear();
                        DefaultItemAnimator.this.mMovesList.remove(arrayList);
                    }
                };
                if (i != 0) {
                    ah.m2788a(((MoveInfo) arrayList.get(0)).holder.itemView, c36591, getRemoveDuration());
                } else {
                    c36591.run();
                }
            }
            if (i3 != 0) {
                arrayList = new ArrayList();
                arrayList.addAll(this.mPendingChanges);
                this.mChangesList.add(arrayList);
                this.mPendingChanges.clear();
                c36591 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            DefaultItemAnimator.this.animateChangeImpl((ChangeInfo) it.next());
                        }
                        arrayList.clear();
                        DefaultItemAnimator.this.mChangesList.remove(arrayList);
                    }
                };
                if (i != 0) {
                    ah.m2788a(((ChangeInfo) arrayList.get(0)).oldHolder.itemView, c36591, getRemoveDuration());
                } else {
                    c36591.run();
                }
            }
            if (i4 != 0) {
                final ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(arrayList2);
                this.mPendingAdditions.clear();
                Runnable c36613 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            DefaultItemAnimator.this.animateAddImpl((ViewHolder) it.next());
                        }
                        arrayList2.clear();
                        DefaultItemAnimator.this.mAdditionsList.remove(arrayList2);
                    }
                };
                if (i == 0 && i2 == 0 && i3 == 0) {
                    c36613.run();
                } else {
                    ah.m2788a(((ViewHolder) arrayList2.get(0)).itemView, c36613, (i != 0 ? getRemoveDuration() : 0) + Math.max(i2 != 0 ? getMoveDuration() : 0, i3 != 0 ? getChangeDuration() : 0));
                }
            }
        }
    }
}
