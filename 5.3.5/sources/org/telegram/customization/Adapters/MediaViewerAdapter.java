package org.telegram.customization.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.customization.DataPool.PostPoolMulti;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.util.view.MediaViewPage;
import org.telegram.customization.util.view.jakerecycler.RecyclingPagerAdapter;

public class MediaViewerAdapter extends RecyclingPagerAdapter {
    public static final String MEDIA_PLAYING = "MEDIA_PLAYING";
    public static final String MEDIA_POSITION = "MEDIA_POSITION";
    private static LayoutInflater inflater = null;
    Activity activity;
    Context context;
    private boolean isPlaying = true;
    private int mediaPos = 0;
    private final int poolId;
    ViewPager viewPager;

    /* renamed from: org.telegram.customization.Adapters.MediaViewerAdapter$1 */
    class C11181 implements OnPageChangeListener {
        C11181() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    private static class ViewHolder {
        final MediaViewPage me;

        public ViewHolder(View view) {
            this.me = (MediaViewPage) view;
        }
    }

    public MediaViewerAdapter(ViewPager mViewPager, Activity mActivity, Context mContext, int poolId) {
        this.viewPager = mViewPager;
        this.activity = mActivity;
        this.context = mContext;
        this.poolId = poolId;
        inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        this.viewPager.addOnPageChangeListener(new C11181());
    }

    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        SlsBaseMessage message = PostPoolMulti.getNews(this.poolId, position);
        if (view == null) {
            view = new MediaViewPage(this.context);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.me.initMediaViewPage(this.context, this.activity, inflater, message, PostPoolMulti.size(this.poolId), position);
        this.viewPager.addOnPageChangeListener(holder.me);
        if (message.getMessage().getMediaType() == 8 || message.getMessage().getMediaType() == 9 || message.getMessage().getMediaType() == 6) {
            try {
                if (this.viewPager.getCurrentItem() == position) {
                    if (this.isPlaying) {
                        this.isPlaying = false;
                        holder.me.playVideo(message.getMessage().getFileUrl());
                    }
                    holder.me.setPlayTime(this.mediaPos);
                    this.mediaPos = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    public int getCount() {
        return PostPoolMulti.size(this.poolId);
    }

    public void handleSaveInstanceBundle(Bundle bundle) {
        try {
            this.isPlaying = bundle.getBoolean(MEDIA_PLAYING, false);
            this.mediaPos = bundle.getInt(MEDIA_POSITION, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bundle getSaveInstanceBundle() {
        Bundle bundle = new Bundle();
        boolean isPlaying = false;
        int mediaPos = 0;
        try {
            ViewHolder holder = (ViewHolder) this.viewPager.getFocusedChild().getTag();
            isPlaying = holder.me.getVideoView().isPlaying();
            mediaPos = holder.me.getVideoView().getCurrentPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bundle.putBoolean(MEDIA_PLAYING, isPlaying);
        bundle.putInt(MEDIA_POSITION, mediaPos);
        return bundle;
    }
}
