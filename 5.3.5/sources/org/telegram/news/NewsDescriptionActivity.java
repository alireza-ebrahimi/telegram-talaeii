package org.telegram.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Window;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.news.adapter.NewsDescriptionAdapter;
import org.telegram.news.model.News;
import org.telegram.news.pool.IDataChange;
import org.telegram.news.pool.NewsPoolMulti;
import utils.Utilities;
import utils.view.Constants;

public class NewsDescriptionActivity extends Activity implements IDataChange, Observer {
    NewsDescriptionAdapter adapter;
    private boolean backToHome;
    int curentPosition;
    private boolean inSpecialNewsMode;
    ScaleGestureDetector scaleGestureDetector;
    private int tagId;
    private ArrayList<News> tagNewsList;
    ViewPager viewPager;

    /* renamed from: org.telegram.news.NewsDescriptionActivity$1 */
    class C19271 implements OnPageChangeListener {
        C19271() {
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            NewsDescriptionActivity.this.curentPosition = position;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_description_activity);
        NewsPoolMulti.getObservable().addObserver(this);
        initializeAll();
    }

    private void initializeAll() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        this.curentPosition = intent.getIntExtra("id", 0);
        this.tagId = intent.getIntExtra("EXTRA_TAG_ID", 0);
        String specialNews = intent.getStringExtra(Constants.SPECIAL_NEWS);
        if (!TextUtils.isEmpty(specialNews)) {
            this.tagId = Constants.PUSH_TAG_ID;
            setInSpecialNewsMode(true);
            setBackToHome(intent.getBooleanExtra(Constants.EXTRA_BACK_TO_HOME, true));
            NewsPoolMulti.reset(this.tagId);
            NewsPoolMulti.setLimitedNews(this.tagId, true);
            News news = new News();
            news.setNewsId(specialNews);
            NewsPoolMulti.add(this.tagId, this, news);
        }
        if (this.curentPosition < 0) {
            this.curentPosition = 0;
        }
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.viewPager.setVisibility(0);
        this.adapter = new NewsDescriptionAdapter(this.viewPager, this, this.tagId);
        this.adapter.setCurrentItem(this.curentPosition);
        this.viewPager.setAdapter(this.adapter);
        this.scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener(this, this.adapter));
        this.viewPager.setCurrentItem(this.curentPosition);
        this.viewPager.setOffscreenPageLimit(1);
        this.viewPager.setOnPageChangeListener(new C19271());
    }

    public boolean isInSpecialNewsMode() {
        return this.inSpecialNewsMode;
    }

    public void setInSpecialNewsMode(boolean inSpecialNews) {
        this.inSpecialNewsMode = inSpecialNews;
    }

    public boolean isBackToHome() {
        return this.backToHome;
    }

    public void setBackToHome(boolean backToHome) {
        this.backToHome = backToHome;
    }

    protected void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }

    public void dataChanged() {
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.scaleGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.scaleGestureDetector.onTouchEvent(event);
        Log.d("TYPE", "TouchEvent");
        return super.onTouchEvent(event);
    }

    protected void onDestroy() {
        super.onDestroy();
        NewsPoolMulti.getObservable().deleteObserver(this);
    }

    public void update(Observable observable, Object o) {
        try {
            dataChanged();
        } catch (Exception e) {
        }
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Utilities.darker(color, 0.8f));
        }
    }
}
