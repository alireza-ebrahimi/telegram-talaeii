package org.telegram.news.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.news.model.News;
import org.telegram.news.pool.NewsPoolMulti;
import utils.view.CircularProgress;

public class NewsListAdapter extends Adapter<MyBaseViewHolder> implements Observer {
    public static final int VIEW_TYPE_LARG_IMG = 1;
    public static final int VIEW_TYPE_LOADING = 3;
    public static final int VIEW_TYPE_SMALL_IMG = 0;
    private Activity context;
    boolean haveProgress = true;
    private LayoutInflater inflater = null;
    OnItemClickListener listener;
    ArrayList<News> newsList = new ArrayList();
    ProgressDialog progress;
    RecyclerView recyclerView;
    int tagId;

    class MyBaseViewHolder extends ViewHolder {
        View view;

        public MyBaseViewHolder(View view) {
            super(view);
            this.view = view;
            this.view.setOnClickListener(new OnClickListener(NewsListAdapter.this) {
                public void onClick(View view) {
                    try {
                        NewsListAdapter.this.listener.onItemClick(null, view, NewsListAdapter.this.recyclerView.getChildPosition(view), System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            });
        }

        public View getView() {
            return this.view;
        }
    }

    class ViewHolderLargImage extends MyBaseViewHolder {
        TextView ftvNewsAgency;
        TextView ftvNewsCreationDate;
        TextView ftvNewsTitle;
        ImageView ivAgency;
        ImageView ivNewsImge;

        public ViewHolderLargImage(View view) {
            super(view);
            this.ftvNewsTitle = (TextView) view.findViewById(R.id.txt_title);
            this.ftvNewsCreationDate = (TextView) view.findViewById(R.id.txt_time);
            this.ftvNewsAgency = (TextView) view.findViewById(R.id.txt_source);
            this.ivAgency = (ImageView) view.findViewById(R.id.iv_agency);
            this.ivNewsImge = (ImageView) view.findViewById(R.id.iv_news_image);
        }
    }

    class ViewHolderLoading extends MyBaseViewHolder {
        CircularProgress progress;

        public ViewHolderLoading(View view) {
            super(view);
            this.progress = (CircularProgress) view.findViewById(R.id.loading);
        }
    }

    class ViewHolderSmallImage extends MyBaseViewHolder {
        TextView ftvNewsAgency;
        TextView ftvNewsCreationDate;
        TextView ftvNewsTitle;
        View imageContainer;
        ImageView ivAgency;
        ImageView ivNewsImge;

        public ViewHolderSmallImage(View view) {
            super(view);
            this.ftvNewsTitle = (TextView) view.findViewById(R.id.txt_title);
            this.ftvNewsCreationDate = (TextView) view.findViewById(R.id.txt_time);
            this.ftvNewsAgency = (TextView) view.findViewById(R.id.txt_source);
            this.ivAgency = (ImageView) view.findViewById(R.id.iv_agency);
            this.ivNewsImge = (ImageView) view.findViewById(R.id.iv_news_image);
            this.imageContainer = view.findViewById(R.id.iv_news_image2);
        }
    }

    public NewsListAdapter(Activity c, int tagId, RecyclerView rv, OnItemClickListener listener) {
        this.context = c;
        this.tagId = tagId;
        this.listener = listener;
        this.recyclerView = rv;
        this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
    }

    public boolean isHaveProgress() {
        return this.haveProgress;
    }

    public void setHaveProgress(boolean haveProgress) {
        Log.d("avaz shodam", "pp " + haveProgress);
        this.haveProgress = haveProgress;
    }

    public List<News> getNewsList() {
        if (this.newsList == null) {
            this.newsList = new ArrayList();
        }
        this.newsList = NewsPoolMulti.getAllNews(this.tagId);
        return this.newsList;
    }

    public void addItem(News news) {
        getNewsList().add(news);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<News> newses) {
        int start = getItemCount();
        getNewsList().addAll(newses);
        notifyItemRangeInserted(start, (newses.size() + start) - 1);
    }

    public int getItemViewType(int position) {
        if (position == getNewsList().size()) {
            return 3;
        }
        switch (position % 5) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return 0;
            case 6:
                return 1;
            default:
                return 0;
        }
    }

    public int getItemCount() {
        int i = 0;
        if (getNewsList().size() == 0) {
            return 0;
        }
        int size = getNewsList().size();
        if (isHaveProgress()) {
            i = 1;
        }
        return size + i;
    }

    public void onBindViewHolder(MyBaseViewHolder vh, int position) {
        News singleNews;
        switch (getItemViewType(position)) {
            case 0:
                ViewHolderSmallImage smallImageHolder = (ViewHolderSmallImage) vh;
                singleNews = getItem(position);
                smallImageHolder.ftvNewsTitle.setText(singleNews.getTitle() + "");
                AppImageLoader.loadImage(smallImageHolder.ivNewsImge, singleNews.getImageUrl());
                smallImageHolder.ftvNewsAgency.setText(singleNews.getAgencyName() + "");
                smallImageHolder.ftvNewsCreationDate.setText(singleNews.getComputedCreationDate() + "");
                AppImageLoader.loadImage(smallImageHolder.ivAgency, singleNews.getAgencyLogo());
                return;
            case 1:
                ViewHolderLargImage largeImageHolder = (ViewHolderLargImage) vh;
                singleNews = getItem(position);
                largeImageHolder.ftvNewsTitle.setText(singleNews.getTitle());
                largeImageHolder.ftvNewsAgency.setText(singleNews.getAgencyName());
                largeImageHolder.ftvNewsCreationDate.setText(singleNews.getComputedCreationDate());
                AppImageLoader.loadImage(largeImageHolder.ivNewsImge, singleNews.getImageUrl());
                AppImageLoader.loadImage(largeImageHolder.ivAgency, singleNews.getAgencyLogo());
                return;
            default:
                return;
        }
    }

    public MyBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case 0:
                return new ViewHolderSmallImage(this.inflater.inflate(R.layout.small_news_item_new, viewGroup, false));
            case 1:
                return new ViewHolderLargImage(this.inflater.inflate(R.layout.larg_news_item, viewGroup, false));
            case 3:
                return new ViewHolderLoading(this.inflater.inflate(R.layout.loading, viewGroup, false));
            default:
                return new ViewHolderSmallImage(this.inflater.inflate(R.layout.small_news_item_new, viewGroup, false));
        }
    }

    public News getItem(int position) {
        try {
            return (News) getNewsList().get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Observable observable, Object data) {
        notifyDataSetChanged();
    }
}
