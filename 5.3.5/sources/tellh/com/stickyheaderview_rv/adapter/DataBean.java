package tellh.com.stickyheaderview_rv.adapter;

import android.support.v4.util.SparseArrayCompat;

public abstract class DataBean implements IViewBinderProvider, LayoutItemType {
    private IViewBinder viewBinder;

    public final IViewBinder provideViewBinder(StickyHeaderViewAdapter adapter, SparseArrayCompat<? extends IViewBinder> viewBinderPool, int position) {
        if (this.viewBinder == null) {
            this.viewBinder = (IViewBinder) viewBinderPool.get(getItemLayoutId(adapter));
        }
        return this.viewBinder;
    }

    public boolean shouldSticky() {
        return false;
    }
}
