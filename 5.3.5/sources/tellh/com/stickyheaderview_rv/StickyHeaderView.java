package tellh.com.stickyheaderview_rv;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.IViewBinderProvider;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter.DataSetChangeListener;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

public class StickyHeaderView extends FrameLayout implements DataSetChangeListener {
    private StickyHeaderViewAdapter adapter;
    private boolean hasInit = false;
    private LinearLayoutManager layoutManager;
    private FrameLayout mHeaderContainer;
    private int mHeaderHeight = -1;
    private RecyclerView mRecyclerView;
    private SparseArray<ViewHolder> mViewHolderCache;
    private LinkListStack<DataBean> stickyHeaderStack = new LinkListStack();

    /* renamed from: tellh.com.stickyheaderview_rv.StickyHeaderView$1 */
    class C34621 extends OnScrollListener {
        C34621() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (StickyHeaderView.this.mHeaderHeight == -1 || StickyHeaderView.this.adapter == null || StickyHeaderView.this.layoutManager == null) {
                StickyHeaderView.this.mHeaderHeight = StickyHeaderView.this.mHeaderContainer.getHeight();
                Adapter adapter = StickyHeaderView.this.mRecyclerView.getAdapter();
                if (adapter instanceof StickyHeaderViewAdapter) {
                    StickyHeaderView.this.adapter = (StickyHeaderViewAdapter) adapter;
                    StickyHeaderView.this.adapter.setDataSetChangeListener(StickyHeaderView.this);
                    StickyHeaderView.this.layoutManager = (LinearLayoutManager) StickyHeaderView.this.mRecyclerView.getLayoutManager();
                    StickyHeaderView.this.mViewHolderCache = new SparseArray();
                    return;
                }
                throw new RuntimeException("Your RecyclerView.Adapter should be the type of StickyHeaderViewAdapter.");
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (StickyHeaderView.this.mHeaderHeight != -1 && StickyHeaderView.this.adapter != null && StickyHeaderView.this.layoutManager != null) {
                if (StickyHeaderView.this.stickyHeaderStack.isEmpty() && StickyHeaderView.this.adapter.getDisplayListSize() != 0) {
                    StickyHeaderView.this.stickyHeaderStack.push(StickyHeaderView.this.adapter.get(StickyHeaderView.this.findFirstVisibleStickyHeaderPosition(0)));
                }
                int firstVisibleItemPosition = StickyHeaderView.this.layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != -1) {
                    int firstVisibleStickyHeaderPosition = StickyHeaderView.this.findFirstVisibleStickyHeaderPosition(firstVisibleItemPosition);
                    int currentStickyHeaderPosition = StickyHeaderView.this.adapter.getPosition((DataBean) StickyHeaderView.this.stickyHeaderStack.peek());
                    if (firstVisibleStickyHeaderPosition - firstVisibleItemPosition <= 1) {
                        DataBean dataBean = StickyHeaderView.this.adapter.get(firstVisibleStickyHeaderPosition + 1);
                        if (dataBean != null && dataBean.shouldSticky()) {
                            firstVisibleStickyHeaderPosition++;
                        }
                        View firstVisibleStickyHeader = StickyHeaderView.this.layoutManager.findViewByPosition(firstVisibleStickyHeaderPosition);
                        if (firstVisibleStickyHeader != null) {
                            int headerTop = firstVisibleStickyHeader.getTop();
                            if (headerTop > 0 && headerTop <= StickyHeaderView.this.mHeaderHeight) {
                                StickyHeaderView.this.mHeaderContainer.setY((float) (-(StickyHeaderView.this.mHeaderHeight - headerTop)));
                                if (firstVisibleStickyHeaderPosition == currentStickyHeaderPosition) {
                                    StickyHeaderView.this.stickyHeaderStack.pop();
                                    if (!StickyHeaderView.this.stickyHeaderStack.isEmpty()) {
                                        StickyHeaderView.this.updateHeaderView((DataBean) StickyHeaderView.this.stickyHeaderStack.peek());
                                    }
                                }
                            } else if (headerTop <= 0) {
                                StickyHeaderView.this.mHeaderContainer.setY(0.0f);
                                StickyHeaderView.this.updateHeaderView(StickyHeaderView.this.adapter.get(firstVisibleItemPosition));
                            }
                            if (firstVisibleStickyHeaderPosition > currentStickyHeaderPosition) {
                                StickyHeaderView.this.stickyHeaderStack.push(StickyHeaderView.this.adapter.get(firstVisibleStickyHeaderPosition));
                            } else if (firstVisibleStickyHeaderPosition < currentStickyHeaderPosition) {
                                StickyHeaderView.this.stickyHeaderStack.pop();
                            }
                        }
                    }
                }
            }
        }
    }

    public StickyHeaderView(Context context) {
        super(context);
    }

    public StickyHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        if (!this.hasInit) {
            this.hasInit = true;
            View view = getChildAt(0);
            if (view instanceof RecyclerView) {
                this.mRecyclerView = (RecyclerView) view;
                this.mHeaderContainer = new FrameLayout(getContext());
                this.mHeaderContainer.setBackgroundColor(-1);
                this.mHeaderContainer.setLayoutParams(new LayoutParams(-1, -2));
                addView(this.mHeaderContainer);
                this.mRecyclerView.addOnScrollListener(new C34621());
                return;
            }
            throw new RuntimeException("RecyclerView should be the first child view.");
        }
    }

    private int findFirstVisibleStickyHeaderPosition(int firstVisibleItemPosition) {
        int i = firstVisibleItemPosition;
        while (i < this.adapter.getDisplayListSize() && !this.adapter.get(i).shouldSticky()) {
            i++;
        }
        return i;
    }

    private void updateHeaderView(DataBean entity) {
        int layoutId = entity.getItemLayoutId(this.adapter);
        clearHeaderView();
        ViewHolder viewHolder = (ViewHolder) this.mViewHolderCache.get(layoutId);
        ViewBinder headerViewBinder = this.adapter.getViewBinder(layoutId);
        if (viewHolder == null) {
            viewHolder = headerViewBinder.provideViewHolder(LayoutInflater.from(this.mHeaderContainer.getContext()).inflate(layoutId, this.mHeaderContainer, false));
            this.mViewHolderCache.put(layoutId, viewHolder);
        }
        this.mHeaderContainer.addView(viewHolder.itemView);
        this.mHeaderHeight = this.mHeaderContainer.getHeight();
        headerViewBinder.bindView(this.adapter, viewHolder, this.adapter.getPosition(entity), (IViewBinderProvider) entity);
    }

    private void clearHeaderView() {
        this.mHeaderContainer.removeAllViews();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        initView();
        super.onLayout(changed, left, top, right, bottom);
    }

    public void onClearAll() {
        this.stickyHeaderStack.clear();
        clearHeaderView();
    }

    public void remove(int pos) {
        DataBean entity = this.adapter.get(pos);
        if (this.stickyHeaderStack.peek() == entity) {
            clearHeaderView();
        }
        if (entity != null && entity.shouldSticky()) {
            this.stickyHeaderStack.remove(entity);
        }
    }
}
