package org.telegram.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView;
import org.telegram.customization.util.view.observerRecyclerView.ScrollPositionChangesListener;
import org.telegram.customization.util.view.observerRecyclerView.ScrollState;
import org.telegram.news.adapter.NewsListAdapter;
import org.telegram.news.model.News;
import org.telegram.news.pool.IDataChange;
import org.telegram.news.pool.NewsPoolMulti;
import org.telegram.ui.LaunchActivity;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.SimpleDividerItemDecoration;

public class NewsListActivity extends FrameLayout implements ScrollPositionChangesListener, OnItemClickListener, IResponseReceiver, OnRefreshListener, Observer, OnClickListener, IDataChange {
    private NewsListAdapter adapter;
    Activity context;
    private View errorView;
    boolean haveNextPage = true;
    boolean onPausing = false;
    private ObservableRecyclerView recycler;
    private View rootView;
    private SwipeRefreshLayout swipeLayout;
    private int tagId = 0;
    boolean viewCreated = false;

    public NewsListActivity(Activity context) {
        super(context);
        init(context);
    }

    public NewsListActivity(Activity context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewsListActivity(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Activity ctx) {
        this.context = ctx;
        addView(onCreateView((LayoutInflater) this.context.getSystemService("layout_inflater"), null, null), -1, -1);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(-1);
        this.rootView = inflater.inflate(R.layout.activity_news_list, linearLayout, false);
        linearLayout.addView(this.rootView);
        initView();
        return linearLayout;
    }

    private void initView() {
        try {
            this.tagId = Integer.parseInt("" + AppPreferences.getNewsPartTagId(getContext()));
        } catch (Exception e) {
        }
        if (this.tagId != 0) {
            this.viewCreated = true;
            this.errorView = this.rootView.findViewById(R.id.error_layout);
            this.swipeLayout = (SwipeRefreshLayout) this.rootView.findViewById(R.id.refresher);
            this.swipeLayout.setOnRefreshListener(this);
            this.swipeLayout.setColorSchemeResources(new int[]{R.color.flag_text_color, R.color.elv_btn_pressed, R.color.pink});
            this.swipeLayout.setProgressViewOffset(true, 200, ScheduleDownloadActivity.CHECK_CELL2);
            this.recycler = (ObservableRecyclerView) this.rootView.findViewById(R.id.recycler);
            this.recycler.setScrollPositionChangesListener(this);
            this.recycler.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
            this.recycler.addItemDecoration(new SimpleDividerItemDecoration(this.context));
            new ExtraData().setTagId((long) this.tagId);
            this.adapter = new NewsListAdapter(this.context, this.tagId, this.recycler, this);
            SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(this.adapter);
            alphaAdapter.setDuration(ScheduleDownloadActivity.CHECK_CELL2);
            this.recycler.setAdapter(alphaAdapter);
            disableDrawer();
            callWebservice(Constants.TYPE_DIR_NEXT);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NewsPoolMulti.getObservable().addObserver(this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NewsPoolMulti.getObservable().deleteObserver(this);
    }

    private void callWebservice(String dir) {
        startLoading();
        String nId = "0";
        Object obj = -1;
        switch (dir.hashCode()) {
            case 3377907:
                if (dir.equals(Constants.TYPE_DIR_NEXT)) {
                    obj = null;
                    break;
                }
                break;
            case 3449395:
                if (dir.equals(Constants.TYPE_DIR_PREV)) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                nId = getFirstPostId();
                break;
            case 1:
                nId = getLastPostId();
                break;
        }
        HandleRequest.getNew(getContext(), this).getNewsListByTagId((long) getTagId(), nId, dir, 20);
    }

    private void startLoading() {
        this.recycler.setLoadingEnd(true);
    }

    private void endLoading() {
        findViewById(R.id.loading).setVisibility(8);
        this.recycler.setLoadingEnd(false);
        this.swipeLayout.setRefreshing(false);
        this.swipeLayout.setEnabled(false);
        this.recycler.setLoadingEnd(false);
        this.recycler.setLoadingStart(false);
        this.recycler.setVisibility(0);
    }

    private void disableDrawer() {
        ((LaunchActivity) this.context).drawerLayoutContainer.setAllowOpenDrawer(false, false);
    }

    private void enableDrawer() {
        ((LaunchActivity) this.context).drawerLayoutContainer.setAllowOpenDrawer(true, false);
    }

    public void ReachedEnd(View view) {
        this.swipeLayout.setEnabled(false);
        callWebservice(Constants.TYPE_DIR_PREV);
    }

    public void ReachedStart(View view) {
        this.recycler.setLoadingEnd(false);
        this.recycler.setLoadingStart(false);
        this.swipeLayout.setEnabled(true);
    }

    public void LeavedBoundaries(View view, int pos) {
        this.swipeLayout.setEnabled(false);
    }

    public void handleScrollState(ScrollState ss) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d("LEE", "Cliiiiick");
        Intent intent = new Intent(this.context, NewsDescriptionActivity.class);
        intent.putExtra("EXTRA_TAG_ID", getTagId());
        intent.putExtra("id", position);
        this.context.startActivity(intent);
    }

    public void onResult(Object object, int StatusCode) {
        endLoading();
        switch (StatusCode) {
            case Constants.ERROR_GET_NEWS_LIST /*-11*/:
                showErrorView(Constants.ERROR_GET_DATA);
                return;
            case 11:
                addNewsToMainContainer((ArrayList) object);
                return;
            default:
                return;
        }
    }

    private void showErrorView(int errorCode) {
        this.recycler.setVisibility(8);
        this.errorView.setVisibility(0);
        TextView tvMessage = (TextView) this.errorView.findViewById(R.id.txt_error);
        ImageView ivError = (ImageView) this.errorView.findViewById(R.id.iv_error);
        endLoading();
        switch (errorCode) {
            case Constants.ERROR_EMPTY /*-3000*/:
                tvMessage.setText(getResources().getString(R.string.err_empty));
                ivError.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.sad));
                break;
            case Constants.ERROR_GET_DATA /*-2000*/:
                tvMessage.setText(getResources().getString(R.string.err_get_data));
                ivError.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.sad));
                break;
            case -1000:
                tvMessage.setText(getResources().getString(R.string.network_error));
                ivError.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.wifi_off));
                break;
        }
        this.swipeLayout.setEnabled(true);
    }

    private void hideErrorView() {
        this.recycler.setVisibility(0);
        this.errorView.setVisibility(8);
    }

    public void onRefresh() {
        refresh();
        callWebservice(Constants.TYPE_DIR_NEXT);
    }

    public void update(Observable observable, Object data) {
        try {
            dataChanged();
        } catch (Exception e) {
        }
    }

    public void onClick(View v) {
    }

    public void onResume() {
        if (this.viewCreated) {
            disableDrawer();
        }
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    private String getFirstPostId() {
        if (this.adapter == null || this.adapter.getNewsList().isEmpty()) {
            return "0";
        }
        return ((News) this.adapter.getNewsList().get(0)).getNewsId();
    }

    private String getLastPostId() {
        if (this.adapter == null || this.adapter.getNewsList().isEmpty()) {
            return "0";
        }
        News news = (News) this.adapter.getNewsList().get((this.adapter.getItemCount() - 1) - (this.adapter.isHaveProgress() ? 1 : 0));
        Log.d("LEE", "dddddd:" + news.getTitle() + " " + news.getNewsId());
        return news.getNewsId();
    }

    private void refresh() {
        this.swipeLayout.setRefreshing(false);
        this.swipeLayout.setEnabled(false);
        this.recycler.setLoadingEnd(false);
        this.recycler.setLoadingStart(false);
    }

    private void addNewsToMainContainer(ArrayList<News> news) {
        if (news != null && news.size() < 20) {
            this.haveNextPage = false;
        }
        NewsPoolMulti.addAll(getTagId(), this.context, (ArrayList) news);
    }

    public void dataChanged() {
        this.adapter.notifyItemRangeChanged(this.adapter.getItemCount(), 1);
    }
}
