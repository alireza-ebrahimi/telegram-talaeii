package org.telegram.news.pool;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.news.model.News;

public class NewsPool {
    private static ArrayList<News> allNews;
    private static boolean limitedNews = false;
    private static String tagColor = "47C653";
    private static int tagId = 0;

    public static ArrayList<News> getAllNews() {
        if (allNews == null) {
            allNews = new ArrayList();
        }
        return allNews;
    }

    public static News getFirstNews() {
        if (size() == 0) {
            return null;
        }
        return getNews(0);
    }

    public static String getFirstNewsID() {
        if (getFirstNews() == null) {
            return "0";
        }
        return getFirstNews().getNewsId();
    }

    public static String getLastNewsID() {
        if (getLastNews() == null) {
            return "0";
        }
        return getLastNews().getNewsId();
    }

    public static News getLastNews() {
        if (size() == 0) {
            return null;
        }
        return getNews(size() - 1);
    }

    public static News getNews(int position) {
        return (News) getAllNews().get(position);
    }

    public static void setAllNews(Context context, List<News> allNews, IDataChange iDataChange) {
        getAllNews().clear();
        if (allNews != null) {
            for (News news : allNews) {
                add(context, news, iDataChange);
            }
        }
    }

    public static void add(Context context, News news, IDataChange iDataChange) {
        if (canAddNews(news)) {
            getAllNews().add(fixNewsDate(news));
            if (iDataChange != null) {
                iDataChange.dataChanged();
            }
        }
    }

    public static void add(Context context, News news, int position, IDataChange iDataChange) {
        if (canAddNews(news)) {
            getAllNews().add(position, fixNewsDate(news));
            if (iDataChange != null) {
                iDataChange.dataChanged();
            }
        }
    }

    public static void addAll(Context context, ArrayList<News> news, IDataChange iDataChange) {
        if (news != null) {
            Iterator it = news.iterator();
            while (it.hasNext()) {
                add(context, (News) it.next(), iDataChange);
            }
        }
    }

    private static News fixNewsDate(News news) {
        if (news.getCreationDate() > System.currentTimeMillis() / 1000) {
            news.setCreationDate(System.currentTimeMillis() / 1000);
        }
        return news;
    }

    private static boolean canAddNews(News news) {
        boolean existNews = false;
        Iterator it = getAllNews().iterator();
        while (it.hasNext()) {
            if (((News) it.next()).getNewsId().equals(news.getNewsId())) {
                existNews = true;
            }
        }
        if (existNews) {
            return false;
        }
        return true;
    }

    public static void reset() {
        getAllNews().clear();
        setLimitedNews(false);
        setTagColor("D81B60");
    }

    public static int size() {
        return getAllNews().size();
    }

    public static int getTagId() {
        return tagId;
    }

    public static void setTagId(int tagId) {
        tagId = tagId;
    }

    public static String getTagColor() {
        if (TextUtils.isEmpty(tagColor)) {
            setTagColor("D81B60");
        }
        return tagColor;
    }

    public static void setTagColor(String tagColor) {
        tagColor = tagColor;
    }

    public static boolean isLimitedNews() {
        return limitedNews;
    }

    public static void setLimitedNews(boolean limitedNews) {
        limitedNews = limitedNews;
    }
}
