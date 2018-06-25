package tellh.com.stickyheaderview_rv.adapter;

import android.support.annotation.LayoutRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class StickyHeaderViewAdapter extends Adapter<ViewHolder> {
    private static int SIZE_VIEW_BINDER_POOL = 10;
    protected List<DataBean> displayList = new ArrayList();
    private DataSetChangeListener onDataSetChangeListener;
    private SparseArrayCompat<ViewBinder> viewBinderPool = new SparseArrayCompat(SIZE_VIEW_BINDER_POOL);

    public interface DataSetChangeListener {
        void onClearAll();

        void remove(int i);
    }

    public StickyHeaderViewAdapter(List<? extends DataBean> displayList) {
        this.displayList.addAll(displayList);
    }

    private List<DataBean> getDisplayList() {
        return this.displayList;
    }

    public int getDisplayListSize() {
        return this.displayList == null ? 0 : this.displayList.size();
    }

    public DataBean get(int i) {
        if (i < getDisplayListSize()) {
            return (DataBean) this.displayList.get(i);
        }
        return null;
    }

    public int getPosition(DataBean dataBean) {
        return this.displayList.indexOf(dataBean);
    }

    public int getItemViewType(int position) {
        return ((DataBean) this.displayList.get(position)).getItemLayoutId(this);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewBinder viewBinder = getViewBinder(viewType);
        return viewBinder.provideViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewBinder.getItemLayoutId(this), parent, false));
    }

    public ViewBinder getViewBinder(int viewType) {
        return (ViewBinder) this.viewBinderPool.get(viewType);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBean dataBean = (DataBean) this.displayList.get(position);
        dataBean.provideViewBinder(this, this.viewBinderPool, position).bindView(this, holder, position, dataBean);
    }

    public int getItemCount() {
        return this.displayList == null ? 0 : this.displayList.size();
    }

    public ViewBinder findViewBinderFromPool(@LayoutRes int layoutId) {
        return (ViewBinder) this.viewBinderPool.get(layoutId);
    }

    public static void setViewBinderPoolInitSize(int size) {
        SIZE_VIEW_BINDER_POOL = size;
    }

    public void clearViewBinderCache() {
        this.viewBinderPool.clear();
    }

    public void append(List<? extends DataBean> list) {
        if (list != null) {
            this.displayList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void append(DataBean dataBean) {
        this.displayList.add(dataBean);
        notifyItemInserted(this.displayList.size() - 1);
    }

    public void refresh(List<? extends DataBean> list) {
        if (list != null) {
            this.onDataSetChangeListener.onClearAll();
            this.displayList.clear();
            this.displayList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void delete(int pos) {
        this.onDataSetChangeListener.remove(pos);
        this.displayList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clear(RecyclerView recyclerView) {
        this.onDataSetChangeListener.onClearAll();
        this.displayList.clear();
        notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }

    public StickyHeaderViewAdapter RegisterItemType(ViewBinder viewBinder) {
        this.viewBinderPool.put(viewBinder.getItemLayoutId(this), viewBinder);
        return this;
    }

    public void setDataSetChangeListener(DataSetChangeListener listener) {
        this.onDataSetChangeListener = listener;
    }
}
