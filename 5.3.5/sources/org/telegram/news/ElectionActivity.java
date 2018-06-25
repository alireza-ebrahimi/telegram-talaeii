package org.telegram.news;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.customization.util.view.Poll.PollAdapter;
import org.telegram.customization.util.view.Poll.PollModel;
import org.telegram.customization.util.view.Poll.PollView;
import org.telegram.ui.LaunchActivity;
import utils.view.Constants;

public class ElectionActivity extends FrameLayout implements IResponseReceiver, OnRefreshListener {
    View container;
    Activity context;
    private View errorView;
    private TextView ftvTitle;
    private ImageView imageView;
    private PollAdapter pollAdapter;
    private PollView pollView;
    private View rootView;
    private SwipeRefreshLayout swipeLayout;
    private int tagId;
    TextView tvSubTitle;
    boolean viewCreated = false;

    /* renamed from: org.telegram.news.ElectionActivity$1 */
    class C19261 implements Runnable {
        C19261() {
        }

        public void run() {
            ElectionActivity.this.pollAdapter.onItemSelected(1, ElectionActivity.this.pollView);
        }
    }

    public ElectionActivity(Activity context) {
        super(context);
        init(context);
    }

    public ElectionActivity(Activity context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ElectionActivity(Activity context, AttributeSet attrs, int defStyleAttr) {
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
        this.rootView = inflater.inflate(R.layout.activity_election, linearLayout, false);
        linearLayout.addView(this.rootView);
        initView();
        return linearLayout;
    }

    private void initView() {
        this.viewCreated = true;
        this.container = this.rootView.findViewById(R.id.ll_container);
        this.errorView = this.rootView.findViewById(R.id.error_layout);
        this.swipeLayout = (SwipeRefreshLayout) this.rootView.findViewById(R.id.refresher);
        this.swipeLayout.setOnRefreshListener(this);
        this.swipeLayout.setColorSchemeResources(new int[]{R.color.flag_text_color, R.color.elv_btn_pressed, R.color.pink});
        this.swipeLayout.setProgressViewOffset(true, 200, ScheduleDownloadActivity.CHECK_CELL2);
        this.tvSubTitle = (TextView) this.rootView.findViewById(R.id.ftv_poll_sub);
        this.ftvTitle = (TextView) this.rootView.findViewById(R.id.ftv_poll_title);
        this.pollView = (PollView) this.rootView.findViewById(R.id.pollView);
        this.imageView = (ImageView) this.rootView.findViewById(R.id.tmp);
        callApi();
    }

    private void callApi() {
        startLoading();
        hideErrorView();
        HandleRequest.getNew(getContext(), this).getPollById("");
    }

    private void startLoading() {
        this.swipeLayout.setRefreshing(true);
    }

    private void disableDrawer() {
        ((LaunchActivity) this.context).drawerLayoutContainer.setAllowOpenDrawer(false, false);
    }

    private void enableDrawer() {
        ((LaunchActivity) this.context).drawerLayoutContainer.setAllowOpenDrawer(true, false);
    }

    public void onResult(Object object, int StatusCode) {
        endLoading();
        switch (StatusCode) {
            case Constants.ERROR_GET_POLL /*-13*/:
                showErrorView(StatusCode);
                return;
            case 13:
                PollModel pollModel = (PollModel) object;
                this.ftvTitle.setText(pollModel.getTitle() + "");
                this.tvSubTitle.setText(pollModel.getSubTitle());
                AppImageLoader.loadImage(this.imageView, pollModel.getImageUrl());
                ArrayList arrayList = new ArrayList();
                this.pollAdapter = new PollAdapter(getContext(), pollModel.getCandidateVoteResult(), Color.rgb(12, 89, 153), Color.rgb(12, 89, 153), Color.rgb(33, 33, 33));
                this.pollView.setAdapter(this.pollAdapter);
                new Handler().postDelayed(new C19261(), 500);
                return;
            default:
                return;
        }
    }

    private void showErrorView(int errorCode) {
        this.container.setVisibility(8);
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

    private void endLoading() {
        this.swipeLayout.setRefreshing(false);
    }

    private void hideErrorView() {
        this.errorView.setVisibility(8);
    }

    public void onRefresh() {
        callApi();
    }

    public void onResume() {
        if (this.viewCreated) {
            disableDrawer();
        }
    }
}
