package org.telegram.news.adapter;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.util.view.jakerecycler.RecyclingPagerAdapter;
import org.telegram.news.IFontSizeChanging;
import org.telegram.news.NewsPage;
import org.telegram.news.model.News;
import org.telegram.news.pool.IDataChange;
import org.telegram.news.pool.NewsPoolMulti;
import utils.view.Constants;

public class NewsDescriptionAdapter extends RecyclingPagerAdapter implements IResponseReceiver, IFontSizeChanging, IDataChange {
    public static Integer AddRepoLock = Integer.valueOf(1);
    private static LayoutInflater inflater = null;
    Activity activity;
    private int currentItem;
    private ArrayList<IFontSizeChanging> ifonts;
    int pxHeight;
    int pxWidth;
    boolean sentRequestForNewNews = false;
    private final int tagId;
    ViewPager viewPager;

    /* renamed from: org.telegram.news.adapter.NewsDescriptionAdapter$1 */
    class C19461 implements Runnable {
        C19461() {
        }

        public void run() {
            HandleRequest.getNew(NewsDescriptionAdapter.this.activity, NewsDescriptionAdapter.this).getNewsListByTagId((long) NewsDescriptionAdapter.this.tagId, NewsPoolMulti.getLastNewsID(NewsDescriptionAdapter.this.tagId), Constants.TYPE_DIR_PREV, 20);
        }
    }

    private static class ViewHolder {
        final NewsPage me;

        public ViewHolder(View view) {
            this.me = (NewsPage) view;
        }
    }

    public NewsDescriptionAdapter(ViewPager viewPager, Activity newActivity, int tagId) {
        this.activity = newActivity;
        inflater = (LayoutInflater) newActivity.getSystemService("layout_inflater");
        this.tagId = tagId;
        this.viewPager = viewPager;
        DisplayMetrics displayMetrics = newActivity.getResources().getDisplayMetrics();
        this.pxWidth = Math.round(180.0f * (displayMetrics.xdpi / 160.0f));
        this.pxHeight = Math.round(300.0f * (displayMetrics.xdpi / 160.0f));
    }

    public int getCount() {
        int count;
        synchronized (AddRepoLock) {
            count = getNewsList().size();
        }
        return count;
    }

    private List<News> getNewsList() {
        return NewsPoolMulti.getAllNews(this.tagId);
    }

    public View getView(int position, View view, ViewGroup container) {
        checkNewsCount(position);
        News singleNews = (News) getNewsList().get(position);
        Activity act = this.activity;
        if (view != null) {
            ((ViewHolder) view.getTag()).me.updateMe(singleNews, this.tagId, position);
            return view;
        }
        view = new NewsPage(act);
        ViewHolder holder = new ViewHolder(view);
        holder.me.initNewsDescPage2(this.activity, inflater, singleNews, container, this.tagId, this.currentItem);
        holder.me.setPos(position);
        getIfonts().add(holder.me);
        view.setTag(holder);
        this.viewPager.addOnPageChangeListener(holder.me);
        return view;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    private void checkNewsCount(int position) {
        if (!NewsPoolMulti.isLimitedNews(this.tagId) && position + 4 >= getNewsList().size() && !this.sentRequestForNewNews) {
            Log.d("srdc", "less than 4 item reminded. fuck u anooshe");
            this.sentRequestForNewNews = true;
            try {
                new Thread(new C19461()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 11:
                this.sentRequestForNewNews = false;
                return;
            case 12:
                this.sentRequestForNewNews = false;
                final News[] oldNewsList = (News[]) object;
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        NewsPoolMulti.addAll(NewsDescriptionAdapter.this.tagId, NewsDescriptionAdapter.this.activity, oldNewsList);
                    }
                });
                return;
            default:
                return;
        }
    }

    public void fontChanged(float f) {
        Iterator it = getIfonts().iterator();
        while (it.hasNext()) {
            IFontSizeChanging iFontChaning = (IFontSizeChanging) it.next();
            if (iFontChaning != null) {
                iFontChaning.fontChanged(f);
            }
        }
    }

    public ArrayList<IFontSizeChanging> getIfonts() {
        if (this.ifonts == null) {
            this.ifonts = new ArrayList();
        }
        return this.ifonts;
    }

    public void dataChanged() {
        notifyDataSetChanged();
    }
}
