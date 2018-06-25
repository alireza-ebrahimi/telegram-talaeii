package org.telegram.customization.DataPool;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.dynamicadapter.data.SlsTag;

public class PostPoolMulti {
    private static volatile HashMap<Integer, NewsPoolNonStatic> allNews;
    private static volatile IDataChange dataChanged;
    private static volatile NewsPoolObservable observable = new NewsPoolObservable();
    private static volatile SlsTag tag;

    /* renamed from: org.telegram.customization.DataPool.PostPoolMulti$1 */
    static class C11241 implements IDataChange {
        C11241() {
        }

        public void dataChanged() {
            PostPoolMulti.observable.dataChanged();
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
            dataChanged = new C11241();
        }
        return dataChanged;
    }

    public static void setTag(int id, SlsTag newTag) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTag(newTag);
        }
    }

    public static SlsTag getTag(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getTag();
        }
        return null;
    }

    public static List<SlsBaseMessage> getAllNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getAllNews();
        }
        return new ArrayList();
    }

    public static SlsBaseMessage getFirstNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getFirstNews();
        }
        return null;
    }

    public static long getFirstNewsID(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getFirstNewsID();
        }
        return 0;
    }

    public static long getLastNewsID(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getLastNewsID();
        }
        return 0;
    }

    public static SlsBaseMessage getLastNews(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getLastNews();
        }
        return null;
    }

    public static SlsBaseMessage getNews(int id, int position) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getNews(position);
        }
        return null;
    }

    public static void setAllNews(int id, Context context, List<SlsBaseMessage> an) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setAllNews(context, an, getDataChanged());
        }
    }

    public static void add(int id, Context context, SlsBaseMessage news) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).add(context, news, getDataChanged(), true);
        }
    }

    public static void add(int id, Context context, SlsBaseMessage news, int position) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).add(context, news, position, getDataChanged(), true);
        }
    }

    public static void addAll(int id, Context context, SlsBaseMessage[] news) {
        ArrayList tmp = new ArrayList();
        Collections.addAll(tmp, news);
        addAll(id, context, tmp);
    }

    public static void addAll(int id, Context context, ArrayList<SlsBaseMessage> news) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).addAll(context, news, getDataChanged());
        }
    }

    public static void clear(int id) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).clear();
        }
    }

    private static boolean canAddNews(Context context, int id, SlsBaseMessage news) {
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

    public static long getTagId(int id) {
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

    public static String getTagColor(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getTagColor();
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

    public static void addAll(int poolId, ArrayList<ObjBase> messages) {
        if (messages != null) {
            Iterator it = messages.iterator();
            while (it.hasNext()) {
                add((ObjBase) it.next(), poolId);
            }
        }
    }

    public static void add(ObjBase msg, int poolId) {
        if (SlsBaseMessage.isMediaAvailable(msg)) {
            add(poolId, null, (SlsBaseMessage) msg);
        }
    }

    public static void setSearchTerm(int id, String term) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setSearchTerm(term);
        }
    }

    public static String getSearchTerm(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getSearchTerm();
        }
        return "";
    }

    public static void setPhraseSearch(int id, boolean phraseSearch) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setPhraseSearch(phraseSearch);
        }
    }

    public static boolean isPhraseSearch(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).isPhraseSearch();
        }
        return false;
    }

    public static void setTakeNewsLimit(int id, int limit) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setTakeNewsLimit(limit);
        }
    }

    public static int getTakeNewsLimit(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getTakeNewsLimit();
        }
        return 20;
    }

    public static void setMediaType(int id, long mediaType) {
        if (makeOK(id)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).setMediaType(mediaType);
        }
    }

    public static long getMediaType(int id) {
        if (makeOK(id)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(id))).getMediaType();
        }
        return 0;
    }

    public static void setSortOrder(int poolId, long sortOrder) {
        if (makeOK(poolId)) {
            ((NewsPoolNonStatic) allNews.get(Integer.valueOf(poolId))).setSortOrder(sortOrder);
        }
    }

    public static long getSortOrder(int poolId) {
        if (makeOK(poolId)) {
            return ((NewsPoolNonStatic) allNews.get(Integer.valueOf(poolId))).getSortOrder();
        }
        return 0;
    }
}
