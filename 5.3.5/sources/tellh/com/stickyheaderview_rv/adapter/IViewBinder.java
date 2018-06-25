package tellh.com.stickyheaderview_rv.adapter;

import android.view.View;

public interface IViewBinder<T, VH> extends LayoutItemType {
    void bindView(StickyHeaderViewAdapter stickyHeaderViewAdapter, VH vh, int i, T t);

    VH provideViewHolder(View view);
}
