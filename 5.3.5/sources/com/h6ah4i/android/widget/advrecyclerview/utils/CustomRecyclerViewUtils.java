package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

public class CustomRecyclerViewUtils {
    public static final int INVALID_SPAN_COUNT = -1;
    public static final int INVALID_SPAN_ID = -1;
    public static final int LAYOUT_TYPE_GRID_HORIZONTAL = 2;
    public static final int LAYOUT_TYPE_GRID_VERTICAL = 3;
    public static final int LAYOUT_TYPE_LINEAR_HORIZONTAL = 0;
    public static final int LAYOUT_TYPE_LINEAR_VERTICAL = 1;
    public static final int LAYOUT_TYPE_STAGGERED_GRID_HORIZONTAL = 4;
    public static final int LAYOUT_TYPE_STAGGERED_GRID_VERTICAL = 5;
    public static final int LAYOUT_TYPE_UNKNOWN = -1;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_UNKNOWN = -1;
    public static final int ORIENTATION_VERTICAL = 1;

    public static ViewHolder findChildViewHolderUnderWithoutTranslation(@NonNull RecyclerView rv, float x, float y) {
        View child = findChildViewUnderWithoutTranslation(rv, x, y);
        return child != null ? rv.getChildViewHolder(child) : null;
    }

    public static int getLayoutType(@NonNull RecyclerView rv) {
        return getLayoutType(rv.getLayoutManager());
    }

    public static int extractOrientation(int layoutType) {
        switch (layoutType) {
            case -1:
                return -1;
            case 0:
            case 2:
            case 4:
                return 0;
            case 1:
            case 3:
            case 5:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown layout type (= " + layoutType + ")");
        }
    }

    public static int getLayoutType(@Nullable LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            if (((GridLayoutManager) layoutManager).getOrientation() == 0) {
                return 2;
            }
            return 3;
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == 0) {
                return 0;
            }
            return 1;
        } else if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return -1;
        } else {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation() == 0) {
                return 4;
            }
            return 5;
        }
    }

    private static View findChildViewUnderWithoutTranslation(@NonNull ViewGroup parent, float x, float y) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View child = parent.getChildAt(i);
            if (x >= ((float) child.getLeft()) && x <= ((float) child.getRight()) && y >= ((float) child.getTop()) && y <= ((float) child.getBottom())) {
                return child;
            }
        }
        return null;
    }

    public static ViewHolder findChildViewHolderUnderWithTranslation(@NonNull RecyclerView rv, float x, float y) {
        View child = rv.findChildViewUnder(x, y);
        return child != null ? rv.getChildViewHolder(child) : null;
    }

    public static Rect getLayoutMargins(View v, Rect outMargins) {
        LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
            outMargins.left = marginLayoutParams.leftMargin;
            outMargins.right = marginLayoutParams.rightMargin;
            outMargins.top = marginLayoutParams.topMargin;
            outMargins.bottom = marginLayoutParams.bottomMargin;
        } else {
            outMargins.bottom = 0;
            outMargins.top = 0;
            outMargins.right = 0;
            outMargins.left = 0;
        }
        return outMargins;
    }

    public static Rect getDecorationOffsets(@NonNull LayoutManager layoutManager, View view, Rect outDecorations) {
        outDecorations.left = layoutManager.getLeftDecorationWidth(view);
        outDecorations.right = layoutManager.getRightDecorationWidth(view);
        outDecorations.top = layoutManager.getTopDecorationHeight(view);
        outDecorations.bottom = layoutManager.getBottomDecorationHeight(view);
        return outDecorations;
    }

    public static Rect getViewBounds(@NonNull View v, @NonNull Rect outBounds) {
        outBounds.left = v.getLeft();
        outBounds.right = v.getRight();
        outBounds.top = v.getTop();
        outBounds.bottom = v.getBottom();
        return outBounds;
    }

    public static int findFirstVisibleItemPosition(@NonNull RecyclerView rv, boolean includesPadding) {
        LayoutManager layoutManager = rv.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return -1;
        }
        if (includesPadding) {
            return findFirstVisibleItemPositionIncludesPadding((LinearLayoutManager) layoutManager);
        }
        return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
    }

    public static int findLastVisibleItemPosition(@NonNull RecyclerView rv, boolean includesPadding) {
        LayoutManager layoutManager = rv.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return -1;
        }
        if (includesPadding) {
            return findLastVisibleItemPositionIncludesPadding((LinearLayoutManager) layoutManager);
        }
        return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
    }

    public static int findFirstCompletelyVisibleItemPosition(@NonNull RecyclerView rv) {
        LayoutManager layoutManager = rv.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        return -1;
    }

    public static int findLastCompletelyVisibleItemPosition(@NonNull RecyclerView rv) {
        LayoutManager layoutManager = rv.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        return -1;
    }

    public static int getSynchronizedPosition(@NonNull ViewHolder holder) {
        int pos1 = holder.getLayoutPosition();
        return pos1 == holder.getAdapterPosition() ? pos1 : -1;
    }

    public static int getSpanCount(@NonNull RecyclerView rv) {
        LayoutManager layoutManager = rv.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }

    public static int getOrientation(@NonNull RecyclerView rv) {
        return getOrientation(rv.getLayoutManager());
    }

    public static int getOrientation(@NonNull LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getOrientation();
        }
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return -1;
    }

    private static int findFirstVisibleItemPositionIncludesPadding(LinearLayoutManager lm) {
        View child = findOneVisibleChildIncludesPadding(lm, 0, lm.getChildCount(), false, true);
        return child == null ? -1 : lm.getPosition(child);
    }

    private static int findLastVisibleItemPositionIncludesPadding(LinearLayoutManager lm) {
        View child = findOneVisibleChildIncludesPadding(lm, lm.getChildCount() - 1, -1, false, true);
        if (child == null) {
            return -1;
        }
        return lm.getPosition(child);
    }

    private static View findOneVisibleChildIncludesPadding(LinearLayoutManager lm, int fromIndex, int toIndex, boolean completelyVisible, boolean acceptPartiallyVisible) {
        int next = 1;
        boolean isVertical = lm.getOrientation() == 1;
        int end = isVertical ? lm.getHeight() : lm.getWidth();
        if (toIndex <= fromIndex) {
            next = -1;
        }
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            View child = lm.getChildAt(i);
            int childStart = isVertical ? child.getTop() : child.getLeft();
            int childEnd = isVertical ? child.getBottom() : child.getRight();
            if (childStart < end && childEnd > 0) {
                if (!completelyVisible) {
                    return child;
                }
                if (childStart >= 0 && childEnd <= end) {
                    return child;
                }
                if (acceptPartiallyVisible && partiallyVisible == null) {
                    partiallyVisible = child;
                }
            }
        }
        return partiallyVisible;
    }

    public static int safeGetAdapterPosition(@Nullable ViewHolder holder) {
        return holder != null ? holder.getAdapterPosition() : -1;
    }

    public static int safeGetLayoutPosition(@Nullable ViewHolder holder) {
        return holder != null ? holder.getLayoutPosition() : -1;
    }

    public static View findViewByPosition(LayoutManager layoutManager, int position) {
        return position != -1 ? layoutManager.findViewByPosition(position) : null;
    }

    public static int getSpanIndex(@Nullable ViewHolder holder) {
        View itemView = getLaidOutItemView(holder);
        if (itemView == null) {
            return -1;
        }
        LayoutParams lp = itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            return ((StaggeredGridLayoutManager.LayoutParams) lp).getSpanIndex();
        }
        if (lp instanceof GridLayoutManager.LayoutParams) {
            return ((GridLayoutManager.LayoutParams) lp).getSpanIndex();
        }
        if (lp instanceof RecyclerView.LayoutParams) {
            return 0;
        }
        return -1;
    }

    public static int getSpanSize(@Nullable ViewHolder holder) {
        View itemView = getLaidOutItemView(holder);
        if (itemView == null) {
            return -1;
        }
        LayoutParams lp = itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (((StaggeredGridLayoutManager.LayoutParams) lp).isFullSpan()) {
                return getSpanCount((RecyclerView) itemView.getParent());
            }
            return 1;
        } else if (lp instanceof GridLayoutManager.LayoutParams) {
            return ((GridLayoutManager.LayoutParams) lp).getSpanSize();
        } else {
            if (lp instanceof RecyclerView.LayoutParams) {
                return 1;
            }
            return -1;
        }
    }

    public static boolean isFullSpan(@Nullable ViewHolder holder) {
        View itemView = getLaidOutItemView(holder);
        if (itemView == null) {
            return true;
        }
        LayoutParams lp = itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            return ((StaggeredGridLayoutManager.LayoutParams) lp).isFullSpan();
        }
        if (!(lp instanceof GridLayoutManager.LayoutParams)) {
            return lp instanceof RecyclerView.LayoutParams ? true : true;
        } else {
            if (getSpanCount((RecyclerView) itemView.getParent()) != ((GridLayoutManager.LayoutParams) lp).getSpanSize()) {
                return false;
            }
            return true;
        }
    }

    private static View getLaidOutItemView(@Nullable ViewHolder holder) {
        if (holder == null) {
            return null;
        }
        View itemView = holder.itemView;
        if (ViewCompat.isLaidOut(itemView)) {
            return itemView;
        }
        return null;
    }

    public static boolean isLinearLayout(int layoutType) {
        return layoutType == 1 || layoutType == 0;
    }

    public static boolean isGridLayout(int layoutType) {
        return layoutType == 3 || layoutType == 2;
    }

    public static boolean isStaggeredGridLayout(int layoutType) {
        return layoutType == 5 || layoutType == 4;
    }
}
