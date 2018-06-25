package tellh.com.stickyheaderview_rv.adapter;

import android.support.annotation.IdRes;
import android.view.View;

public abstract class ViewBinder<T extends IViewBinderProvider, VH extends android.support.v7.widget.RecyclerView.ViewHolder> implements IViewBinder<T, VH> {

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ViewHolder(View rootView) {
            super(rootView);
        }

        protected <T extends View> T findViewById(@IdRes int id) {
            return this.itemView.findViewById(id);
        }
    }

    public abstract void bindView(StickyHeaderViewAdapter stickyHeaderViewAdapter, VH vh, int i, T t);

    public abstract VH provideViewHolder(View view);
}
