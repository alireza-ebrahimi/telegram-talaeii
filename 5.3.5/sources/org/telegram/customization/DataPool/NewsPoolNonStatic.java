package org.telegram.customization.DataPool;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.dynamicadapter.data.SlsTag;

public class NewsPoolNonStatic {
    private List<SlsBaseMessage> allNews;
    private boolean limitedNews = false;
    private long mediaType;
    private boolean phraseSearch = false;
    private String searchTerm;
    private long sortOrder;
    SlsTag tag;
    private int takeNewsLimit;

    public SlsTag getTag() {
        if (this.tag == null) {
            this.tag = new SlsTag();
            this.tag.setId(0);
            this.tag.setTitle("");
        }
        return this.tag;
    }

    public void setTag(SlsTag tag) {
        this.tag = tag;
    }

    public List<SlsBaseMessage> getAllNews() {
        if (this.allNews == null) {
            this.allNews = new ArrayList();
        }
        return this.allNews;
    }

    public SlsBaseMessage getFirstNews() {
        if (size() == 0) {
            return null;
        }
        return getNews(0);
    }

    public long getFirstNewsID() {
        if (getFirstNews() == null) {
            return 0;
        }
        return (long) getFirstNews().getRow();
    }

    public long getLastNewsID() {
        if (getLastNews() == null) {
            return 0;
        }
        return (long) getLastNews().getRow();
    }

    public SlsBaseMessage getLastNews() {
        if (size() == 0) {
            return null;
        }
        return getNews(size() - 1);
    }

    public SlsBaseMessage getNews(int position) {
        if (position >= size()) {
            return null;
        }
        return (SlsBaseMessage) getAllNews().get(position);
    }

    public void setAllNews(Context context, List<SlsBaseMessage> allNews, IDataChange iDataChange) {
        getAllNews().clear();
        if (allNews != null) {
            for (SlsBaseMessage news : allNews) {
                add(context, news, iDataChange, true);
            }
        }
    }

    public synchronized void add(Context context, final SlsBaseMessage news, final IDataChange iDataChange, boolean haveToGoAnotherThread) {
        if (canAddNews(context, news) && iDataChange != null) {
            if (context != null) {
                try {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {
                                NewsPoolNonStatic.this.getAllNews().add(news);
                                iDataChange.dataChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
            getAllNews().add(news);
            iDataChange.dataChanged();
        }
    }

    public void add(Context context, SlsBaseMessage news, int position, IDataChange iDataChange, boolean haveToGoAnotherThread) {
        if (canAddNews(context, news)) {
            getAllNews().add(position, news);
            if (iDataChange != null) {
                iDataChange.dataChanged();
            }
        }
    }

    public void addAll(Context context, ArrayList<SlsBaseMessage> news, IDataChange iDataChange) {
        if (news != null) {
            Iterator it = news.iterator();
            while (it.hasNext()) {
                add(context, (SlsBaseMessage) it.next(), iDataChange, true);
            }
        }
    }

    public void clear() {
        getAllNews().clear();
    }

    public boolean canAddNews(Context context, SlsBaseMessage news) {
        List<SlsBaseMessage> copy = new ArrayList(getAllNews());
        boolean existNews = false;
        if (copy != null && copy.size() > 0) {
            for (SlsBaseMessage n : copy) {
                try {
                    if (n.getRow() == news.getRow()) {
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

    public long getTagId() {
        return getTag().getId();
    }

    public void setTagId(int tagId) {
        getTag().setId((long) tagId);
    }

    public String getTagTitle() {
        return getTag().getTitle();
    }

    public void setTagTitle(String tagName) {
        getTag().setTitle(tagName);
    }

    public String getTagColor() {
        if (TextUtils.isEmpty(getTag().getColor())) {
            setTagColor("47C653");
        }
        return getTag().getColor();
    }

    public void setTagColor(String tagColor) {
        getTag().setColor(tagColor);
    }

    public boolean isLimitedNews() {
        return this.limitedNews;
    }

    public void setLimitedNews(boolean limitedNews) {
        this.limitedNews = limitedNews;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public void setTakeNewsLimit(int takeNewsLimit) {
        this.takeNewsLimit = takeNewsLimit;
    }

    public int getTakeNewsLimit() {
        return this.takeNewsLimit;
    }

    public void setMediaType(long mediaType) {
        this.mediaType = mediaType;
    }

    public long getMediaType() {
        return this.mediaType;
    }

    public long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isPhraseSearch() {
        return this.phraseSearch;
    }

    public void setPhraseSearch(boolean phraseSearch) {
        this.phraseSearch = phraseSearch;
    }
}
