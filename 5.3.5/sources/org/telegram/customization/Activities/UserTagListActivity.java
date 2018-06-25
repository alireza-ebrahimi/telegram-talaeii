package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ProgressBar;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.util.AppUtilities;
import org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView;
import org.telegram.messenger.ApplicationLoader;
import utils.view.Constants;
import utils.view.ToastUtil;

public class UserTagListActivity extends Activity implements IResponseReceiver, OnClickListener {
    DynamicAdapter adapter;
    View emptyList;
    View ivBack;
    ProgressBar progressBar;
    ObservableRecyclerView recycler;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tag_list_activity);
        initView();
        getUserTags();
    }

    private void initView() {
        this.recycler = (ObservableRecyclerView) findViewById(R.id.recycler);
        this.recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.recycler.setAdapter(this.adapter);
        this.progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        this.emptyList = findViewById(R.id.ftv_empty_list);
        this.ivBack = findViewById(R.id.iv_back);
        this.ivBack.setOnClickListener(this);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        int color = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0).getInt("themeColor", AppUtilities.defColor);
        int darkColor = AppUtilities.setDarkColor(color, 21);
        this.toolbar.setBackgroundColor(color);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(darkColor);
        }
    }

    private void getUserTags() {
        HandleRequest.getNew(getApplicationContext(), this).getUserTags();
    }

    public void onResult(Object object, int StatusCode) {
        this.progressBar.setVisibility(8);
        switch (StatusCode) {
            case Constants.ERROR_GET_USER_TAGS /*-9*/:
                ToastUtil.AppToast(getApplicationContext(), getString(R.string.err_get_data)).show();
                finish();
                return;
            case 9:
                ArrayList<ObjBase> tags = (ArrayList) object;
                if (tags.size() > 0) {
                    this.adapter.setItems(tags);
                    this.adapter.notifyDataSetChanged();
                    return;
                }
                this.emptyList.setVisibility(0);
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }
}
