package org.telegram.customization.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.MediaViewerAdapter;
import org.telegram.customization.DataPool.IDataChange;
import org.telegram.customization.DataPool.PostPoolMulti;
import org.telegram.customization.Interfaces.IPageChangedListener;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import utils.view.Constants;
import utils.view.VideoController.LightnessController;

public class SlsMediaViewActivity extends Activity implements OnPageChangeListener, IResponseReceiver, IDataChange, Observer {
    public static final String MEDIA_STATE_CHANGE_BUNDLE = "MEDIA_STATE_CHANGE_BUNDLE";
    MediaViewerAdapter mediaViewerAdapter;
    private int orginalLight;
    IPageChangedListener pageChangedListener;
    private int poolId;
    private boolean sentRequestForNewNews = false;
    private long tagId;
    ViewPager viewPager;

    /* renamed from: org.telegram.customization.Activities.SlsMediaViewActivity$1 */
    class C11071 implements Runnable {
        C11071() {
        }

        public void run() {
            if (SlsMediaViewActivity.this.poolId == 1) {
                HandleRequest.getNew(SlsMediaViewActivity.this, SlsMediaViewActivity.this).getHome(SlsMediaViewActivity.this.tagId, PostPoolMulti.getLastNewsID(SlsMediaViewActivity.this.poolId), true, PostPoolMulti.getMediaType(SlsMediaViewActivity.this.poolId), PostPoolMulti.getTakeNewsLimit(SlsMediaViewActivity.this.poolId));
            } else {
                HandleRequest.getNew(SlsMediaViewActivity.this, SlsMediaViewActivity.this).getSearchPost(SlsMediaViewActivity.this.tagId, PostPoolMulti.getLastNewsID(SlsMediaViewActivity.this.poolId), true, PostPoolMulti.getSearchTerm(SlsMediaViewActivity.this.poolId), PostPoolMulti.getMediaType(SlsMediaViewActivity.this.poolId), PostPoolMulti.getTakeNewsLimit(SlsMediaViewActivity.this.poolId), PostPoolMulti.getSortOrder(SlsMediaViewActivity.this.poolId), PostPoolMulti.isPhraseSearch(SlsMediaViewActivity.this.poolId));
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.tagId = 0;
        int currentPosition = 0;
        if (intent != null) {
            this.tagId = intent.getLongExtra("EXTRA_TAG_ID", 0);
            this.poolId = intent.getIntExtra(Constants.EXTRA_POOL_ID, 0);
            currentPosition = intent.getIntExtra(org.telegram.customization.util.Constants.EXTRA_CURRENT_POSITION, 0);
        }
        setContentView(R.layout.sls_media_viewr_activity);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.mediaViewerAdapter = new MediaViewerAdapter(this.viewPager, this, getApplicationContext(), this.poolId);
        this.viewPager.setAdapter(this.mediaViewerAdapter);
        this.orginalLight = LightnessController.getLightness(this);
        this.viewPager.addOnPageChangeListener(this);
        this.viewPager.setCurrentItem(currentPosition);
        PostPoolMulti.getObservable().addObserver(this);
        if (savedInstanceState != null) {
            this.mediaViewerAdapter.handleSaveInstanceBundle(savedInstanceState.getBundle(MEDIA_STATE_CHANGE_BUNDLE));
        }
    }

    protected void onPause() {
        super.onPause();
        this.orginalLight = LightnessController.getLightness(this);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        Log.d("LEE", "poolID:2  " + this.poolId);
        checkNewsCount(position);
    }

    public void onPageScrollStateChanged(int state) {
    }

    private void checkNewsCount(int position) {
        if (!PostPoolMulti.isLimitedNews(this.poolId) && position + 4 >= PostPoolMulti.size(this.poolId) && !this.sentRequestForNewNews) {
            this.sentRequestForNewNews = true;
            try {
                new Thread(new C11071()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 1:
                ArrayList<ObjBase> messages = (ArrayList) object;
                this.sentRequestForNewNews = false;
                PostPoolMulti.addAll(this.poolId, messages);
                this.mediaViewerAdapter.notifyDataSetChanged();
                return;
            default:
                return;
        }
    }

    public void dataChanged() {
        if (this.mediaViewerAdapter != null) {
            this.mediaViewerAdapter.notifyDataSetChanged();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        PostPoolMulti.getObservable().deleteObserver(this);
    }

    public void update(Observable observable, Object o) {
        try {
            dataChanged();
        } catch (Exception e) {
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(MEDIA_STATE_CHANGE_BUNDLE, this.mediaViewerAdapter.getSaveInstanceBundle());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
