package org.telegram.news.pool;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.news.model.News;
import org.telegram.news.model.RasadTag;

public class NewsPoolNonStatic {
    private ArrayList<News> allNews;
    private boolean contentIsUgcVideo = false;
    private boolean contentIsUserVideo = false;
    private boolean haveToCacheNews = false;
    private boolean limitedNews = false;
    RasadTag tag;

    public RasadTag getTag() {
        if (this.tag == null) {
            this.tag = new RasadTag();
            this.tag.setTagColor("47C653");
            this.tag.setId(0);
            this.tag.setTitle("");
        }
        return this.tag;
    }

    public void setTag(RasadTag tag) {
        this.tag = tag;
    }

    public ArrayList<News> getAllNews() {
        if (this.allNews == null) {
            this.allNews = new ArrayList();
        }
        return this.allNews;
    }

    public News getFirstNews() {
        if (size() == 0) {
            return null;
        }
        return getNews(0);
    }

    public String getFirstNewsID() {
        if (getFirstNews() == null) {
            return "0";
        }
        return getFirstNews().getNewsId();
    }

    public String getLastNewsID() {
        if (getLastNews() == null) {
            return "0";
        }
        return getLastNews().getNewsId();
    }

    public News getLastNews() {
        if (size() == 0) {
            return null;
        }
        return getNews(size() - 1);
    }

    public News getNews(int position) {
        if (position >= size()) {
            return null;
        }
        return (News) getAllNews().get(position);
    }

    public void setAllNews(Context context, List<News> allNews, IDataChange iDataChange) {
        getAllNews().clear();
        if (allNews != null) {
            for (News news : allNews) {
                Log.d("LEE", "aaaaaa:aaaaa");
                add(context, news, iDataChange);
            }
        }
    }

    public synchronized void add(Context context, final News news, final IDataChange iDataChange) {
        if (canAddNews(context, news)) {
            news.setCreationDate(fixNewsDate(news).getCreationDate());
            news.setText("");
            news.setDescription("");
            if (iDataChange != null) {
                try {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {
                                NewsPoolNonStatic.this.getAllNews().add(news);
                                iDataChange.dataChanged();
                            }
                        });
                    } else {
                        iDataChange.dataChanged();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void add(Context context, News news, int position, IDataChange iDataChange) {
        if (canAddNews(context, news)) {
            getAllNews().add(position, fixNewsDate(news));
            if (iDataChange != null) {
                iDataChange.dataChanged();
            }
        }
    }

    public void addAll(Context context, ArrayList<News> news, IDataChange iDataChange) {
        if (news != null) {
            Iterator it = news.iterator();
            while (it.hasNext()) {
                add(context, (News) it.next(), iDataChange);
            }
        }
    }

    public void clear() {
        getAllNews().clear();
    }

    public News fixNewsDate(News news) {
        if (news != null && news.getCreationDate() > System.currentTimeMillis() / 1000) {
            news.setCreationDate(System.currentTimeMillis() / 1000);
        }
        return news;
    }

    public boolean canAddNews(Context context, News news) {
        List<News> copy = new ArrayList(getAllNews());
        boolean existNews = false;
        if (copy != null && copy.size() > 0) {
            for (News n : copy) {
                try {
                    if (n.getNewsId().equals(news.getNewsId())) {
                        existNews = true;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (existNews) {
                return false;
            }
        }
        return true;
    }

    public void reset(IDataChange iDataChange) {
        getAllNews().clear();
        setLimitedNews(false);
        setTagColor("D81B60");
        if (iDataChange != null) {
            iDataChange.dataChanged();
        }
    }

    public int size() {
        return getAllNews().size();
    }

    public int getTagId() {
        return getTag().getId();
    }

    public void setTagId(int tagId) {
        getTag().setId(tagId);
    }

    public String getTagTitle() {
        return getTag().getTitle();
    }

    public void setTagTitle(String tagName) {
        getTag().setTitle(tagName);
    }

    public void setTagColor(String tagColor) {
        getTag().setTagColor(tagColor);
    }

    public boolean isLimitedNews() {
        return this.limitedNews;
    }

    public void setLimitedNews(boolean limitedNews) {
        this.limitedNews = limitedNews;
    }

    public boolean isHaveToCacheNews() {
        return this.haveToCacheNews;
    }

    public void setHaveToCacheNews(boolean haveToCacheNews) {
        this.haveToCacheNews = haveToCacheNews;
    }

    public boolean isContentIsUserVideo() {
        return this.contentIsUserVideo;
    }

    public void setContentIsUserVideo(boolean contentIsUserVideo) {
        this.contentIsUserVideo = contentIsUserVideo;
    }

    public boolean isContentIsUgcVideo() {
        return this.contentIsUgcVideo;
    }

    public void setContentIsUgcVideo(boolean contentIsUgcVideo) {
        this.contentIsUgcVideo = contentIsUgcVideo;
    }
}
