package org.telegram.messenger.support.widget;

import android.util.Log;
import android.view.View;
import java.util.List;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.Recycler;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class LinearLayoutManager$LayoutState {
    static final int INVALID_LAYOUT = Integer.MIN_VALUE;
    static final int ITEM_DIRECTION_HEAD = -1;
    static final int ITEM_DIRECTION_TAIL = 1;
    static final int LAYOUT_END = 1;
    static final int LAYOUT_START = -1;
    static final int SCROLLING_OFFSET_NaN = Integer.MIN_VALUE;
    static final String TAG = "LLM#LayoutState";
    int mAvailable;
    int mCurrentPosition;
    int mExtra = 0;
    boolean mInfinite;
    boolean mIsPreLayout = false;
    int mItemDirection;
    int mLastScrollDelta;
    int mLayoutDirection;
    int mOffset;
    boolean mRecycle = true;
    List<ViewHolder> mScrapList = null;
    int mScrollingOffset;

    LinearLayoutManager$LayoutState() {
    }

    boolean hasMore(State state) {
        return this.mCurrentPosition >= 0 && this.mCurrentPosition < state.getItemCount();
    }

    View next(Recycler recycler) {
        if (this.mScrapList != null) {
            return nextViewFromScrapList();
        }
        View view = recycler.getViewForPosition(this.mCurrentPosition);
        this.mCurrentPosition += this.mItemDirection;
        return view;
    }

    private View nextViewFromScrapList() {
        int size = this.mScrapList.size();
        for (int i = 0; i < size; i++) {
            View view = ((ViewHolder) this.mScrapList.get(i)).itemView;
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (!lp.isItemRemoved() && this.mCurrentPosition == lp.getViewLayoutPosition()) {
                assignPositionFromScrapList(view);
                return view;
            }
        }
        return null;
    }

    public void assignPositionFromScrapList() {
        assignPositionFromScrapList(null);
    }

    public void assignPositionFromScrapList(View ignore) {
        View closest = nextViewInLimitedList(ignore);
        if (closest == null) {
            this.mCurrentPosition = -1;
        } else {
            this.mCurrentPosition = ((LayoutParams) closest.getLayoutParams()).getViewLayoutPosition();
        }
    }

    public View nextViewInLimitedList(View ignore) {
        int size = this.mScrapList.size();
        View closest = null;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            View view = ((ViewHolder) this.mScrapList.get(i)).itemView;
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (!(view == ignore || lp.isItemRemoved())) {
                int distance = (lp.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                if (distance >= 0 && distance < closestDistance) {
                    closest = view;
                    closestDistance = distance;
                    if (distance == 0) {
                        break;
                    }
                }
            }
        }
        return closest;
    }

    void log() {
        Log.d(TAG, "avail:" + this.mAvailable + ", ind:" + this.mCurrentPosition + ", dir:" + this.mItemDirection + ", offset:" + this.mOffset + ", layoutDir:" + this.mLayoutDirection);
    }
}
