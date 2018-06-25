package org.telegram.news.pool;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.telegram.customization.DataPool.NewsPoolObservable;
import org.telegram.news.model.News;
import org.telegram.news.model.RasadTag;

public class NewsPoolMulti {
    private static volatile HashMap<Integer, NewsPoolNonStatic> allNews;
    private static volatile IDataChange dataChanged;
    private static volatile NewsPoolObservable observable = new NewsPoolObservable();
    private static volatile RasadTag tag;

    /* renamed from: org.telegram.news.pool.NewsPoolMulti$1 */
    static class C19491 implements IDataChange {
        C19491() {
        }

        public void dataChanged() {
            NewsPoolMulti.observable.dataChanged();
        }
    }

    public static NewsPoolObservable getObservable() {
        if (observable == null) {
            observable = new NewsPoolObservable();
        }
        return observable;
    }

    static IDataChange getDataChanged() {
        if (dataChanged == null) {
            dataChanged = new C19491();
        }
        return dataChanged;
    }

    public static void setTag(int id, RasadTag newTag) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTag(newTag);
        }
    }

    public static RasadTag getTag(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getTag();
        }
        return null;
    }

    public static ArrayList<News> getAllNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getAllNews();
        }
        return new ArrayList();
    }

    public static News getFirstNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getFirstNews();
        }
        return null;
    }

    public static String getFirstNewsID(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getFirstNewsID();
        }
        return "0";
    }

    public static String getLastNewsID(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getLastNewsID();
        }
        return "";
    }

    public static News getLastNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getLastNews();
        }
        return null;
    }

    public static News getNews(int id, int position) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getNews(position);
        }
        return null;
    }

    public static void setAllNews(int id, Context context, List<News> an) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setAllNews(context, an, getDataChanged());
        }
    }

    public static void add(int id, Context context, News news) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).add(context, news, getDataChanged());
        }
    }

    public static void add(int id, Context context, News news, int position) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).add(context, news, position, getDataChanged());
        }
    }

    public static void addAll(int id, Context context, News[] news) {
        ArrayList tmp = new ArrayList();
        Collections.addAll(tmp, news);
        addAll(id, context, tmp);
    }

    public static void addAll(int id, Context context, ArrayList<News> news) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).addAll(context, news, getDataChanged());
        }
    }

    public static void clear(int id) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).clear();
        }
    }

    private static News fixNewsDate(int id, News news) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).fixNewsDate(news);
        }
        return null;
    }

    private static boolean canAddNews(Context context, int id, News news) {
        return makeOK(id) && ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).canAddNews(context, news);
    }

    private static boolean makeOK(int id) {
        if (allNews == null) {
            allNews = new HashMap();
        }
        if (!allNews.containsKey(Integer.valueOf(id))) {
            allNews.put(Integer.valueOf(id), new NewsPoolNonStatic());
        }
        if (allNews.get(Integer.valueOf(id)) == null) {
            allNews.remove(Integer.valueOf(id));
            allNews.put(Integer.valueOf(id), new NewsPoolNonStatic());
        }
        ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTagId(id);
        return true;
    }

    public static void reset(int id) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).reset(getDataChanged());
        } else {
            allNews.put(Integer.valueOf(id), new NewsPoolNonStatic());
        }
    }

    public static int size(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).size();
        }
        return 0;
    }

    public static int getTagId(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getTagId();
        }
        return -1;
    }

    public static void setTagId(int id) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTagId(id);
        }
    }

    public static void setTagTitle(int id, String tagName) {
        if (tagName == null) {
            tagName = "";
        }
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTagTitle(tagName);
        }
    }

    public static String getTagTitle(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getTagTitle();
        }
        return "";
    }

    public static void setTagColor(int id, String tagColor) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTagColor(tagColor);
        }
    }

    public static boolean isLimitedNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).isLimitedNews();
        }
        return false;
    }

    public static void setLimitedNews(int id, boolean limitedNews) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setLimitedNews(limitedNews);
        }
    }

    public static boolean isHaveToCacheNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).isHaveToCacheNews();
        }
        return false;
    }

    public static void setHaveToCacheNews(int id, boolean haveToCacheNews) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setHaveToCacheNews(haveToCacheNews);
        }
    }

    public static boolean isContentIsUgcVideo(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).isContentIsUgcVideo();
        }
        return false;
    }

    public static void setContentIsUgcVideo(int id, boolean contentIsUserVideo) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setContentIsUgcVideo(contentIsUserVideo);
        }
    }

    public static boolean isContentIsUserVideo(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).isContentIsUserVideo();
        }
        return false;
    }

    public static void setContentIsUserVideo(int id, boolean contentIsUserVideo) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setContentIsUserVideo(contentIsUserVideo);
        }
    }
}
