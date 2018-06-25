package org.telegram.news.model;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import org.ocpsoft.prettytime.PrettyTime;

public class News {
    private int accessCommenting;
    String agencyLogo;
    String agencyName;
    private int agencyTagId;
    int commentCount;
    @SerializedName("irCreationDate")
    String computedCreationDate;
    Content[] contents;
    long creationDate;
    private String description;
    String duration;
    Content[] extraContents;
    private boolean fav;
    private long favTime;
    public boolean goToList = false;
    private int id;
    int imageCount;
    private String imageUrl;
    ArrayList<String> imageUrls;
    private String jsonObject;
    long lastSeenTime;
    int lockStatus;
    String meta;
    long monoVideoId;
    String newsId;
    private boolean newsSeen = false;
    private int newsTagId;
    private String newsTagName;
    int newsType;
    private String newsUrl;
    String politicalColor;
    private ArrayList<News> relatedNews;
    private ShareContent shareContent;
    private int status;
    private int tagId;
    private ArrayList<RasadTag> tags;
    private String text;
    String title;
    int videoCount;
    String videoDuration;
    String videoImageUrl;

    public String getPoliticalColor() {
        return this.politicalColor;
    }

    public void setPoliticalColor(String politicalColor) {
        this.politicalColor = politicalColor;
    }

    public long getMonoVideoId() {
        return this.monoVideoId;
    }

    public void setMonoVideoId(long monoVideoId) {
        this.monoVideoId = monoVideoId;
    }

    public String getMeta() {
        return this.meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isGoToList() {
        return this.goToList;
    }

    public void setGoToList(boolean goToList) {
        this.goToList = goToList;
    }

    public String getCommentCountString() {
        if (this.commentCount > 99) {
            return "+99";
        }
        return this.commentCount + "";
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList<News> getRelatedNews() {
        return this.relatedNews;
    }

    public void setRelatedNews(ArrayList<News> relatedNews) {
        this.relatedNews = relatedNews;
    }

    public News(News s) {
        this.newsSeen = s.newsSeen;
        this.favTime = s.favTime;
        this.fav = s.fav;
        this.text = s.text;
        this.newsTagId = s.newsTagId;
        this.newsUrl = s.newsUrl;
        this.description = s.description;
        this.imageUrl = s.imageUrl;
        this.id = s.id;
        this.newsType = s.newsType;
        this.imageCount = s.imageCount;
        this.agencyLogo = s.agencyLogo;
        this.imageUrls = s.imageUrls;
        this.agencyName = s.agencyName;
        this.creationDate = s.creationDate;
        this.title = s.title;
        this.newsId = s.newsId;
        this.newsSeen = s.newsSeen;
        this.newsTagName = s.newsTagName;
        this.politicalColor = s.politicalColor;
    }

    public String getVideoImageUrl() {
        return this.videoImageUrl;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getVideoDuration() {
        return this.videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public int getLockStatus() {
        return this.lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Content[] getExtraContents() {
        if (this.extraContents == null) {
            this.extraContents = new Content[0];
        }
        return this.extraContents;
    }

    public void setExtraContents(Content[] extraContents) {
        this.extraContents = extraContents;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Content[] getContent() {
        if (this.contents == null) {
            this.contents = new Content[0];
        }
        return this.contents;
    }

    public void setContent(Content[] content) {
        this.contents = content;
    }

    public int getVideoCount() {
        if (this.videoCount == 0 && getContent().length > 0) {
            for (Content c : getContent()) {
                if (c.getType() == 3) {
                    this.videoCount++;
                }
            }
        }
        return this.videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public long isFavTime() {
        return this.favTime;
    }

    public void setFavTime(long favTime) {
        this.favTime = favTime;
    }

    public boolean isFav() {
        return this.fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getAgencyLogo() {
        return this.agencyLogo;
    }

    public void setAgencyLogo(String agencyLogo) {
        this.agencyLogo = agencyLogo;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getAgencyName() {
        return this.agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getNewsTagName() {
        return this.newsTagName;
    }

    public void setNewsTagName(String newsTagName) {
        this.newsTagName = newsTagName;
    }

    public ArrayList<String> getImageUrls() {
        if (this.imageUrls == null) {
            this.imageUrls = new ArrayList();
            if (this.imageUrls.size() == 0 && getContent().length > 0) {
                for (Content c : getContent()) {
                    if (c.getType() == 2) {
                        Collections.addAll(this.imageUrls, c.getUrls());
                    }
                }
            }
        }
        return this.imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getImageCount() {
        if (this.imageCount == 0 && getContent().length > 0) {
            for (Content c : getContent()) {
                if (c.getType() == 2) {
                    this.imageCount += c.getUrls().length;
                }
            }
        }
        return this.imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getNewsType() {
        return this.newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsUrl() {
        return this.newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<RasadTag> getTags() {
        if (this.tags == null) {
            this.tags = new ArrayList();
        }
        return this.tags;
    }

    public void setTags(ArrayList<RasadTag> tags) {
        this.tags = tags;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNewsTagId() {
        return this.newsTagId;
    }

    public void setNewsTagId(int newsTagId) {
        this.newsTagId = newsTagId;
    }

    public boolean isNewsSeen() {
        return this.newsSeen;
    }

    public void setNewsSeen(boolean newsSeen) {
        Log.d("sadegh", "setting seen for news: " + newsSeen);
        this.newsSeen = newsSeen;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
    }

    public String getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getAgencyTagId() {
        return this.agencyTagId;
    }

    public void setAgencyTagId(int agencyTagId) {
        this.agencyTagId = agencyTagId;
    }

    public ArrayList<Content> getVideoContent() {
        ArrayList<Content> videoContent = new ArrayList();
        if (getContent() != null) {
            for (Content content : getContent()) {
                if (content.getType() == 3) {
                    videoContent.add(content);
                }
            }
        }
        return videoContent;
    }

    public ArrayList<Content> getImageContent() {
        ArrayList<Content> videoContent = new ArrayList();
        if (getContent() != null) {
            for (Content content : getContent()) {
                if (content.getType() == 2) {
                    videoContent.add(content);
                }
            }
        }
        return videoContent;
    }

    public String getContentString() {
        String result = "";
        if (getContent() != null) {
            for (Content content : getContent()) {
                if (content.getType() == 1) {
                    result = result + LogCollector.LINE_SEPARATOR + content.getDescription();
                }
            }
        }
        return result;
    }

    public Content getActionButtonContent() {
        Content content = null;
        if (getContent() != null) {
            for (Content singleContent : getContent()) {
                if (singleContent.getType() == 8) {
                    content = singleContent;
                }
            }
        }
        return content;
    }

    public int getAccessCommenting() {
        return this.accessCommenting;
    }

    public void setAccessCommenting(int accessCommenting) {
        this.accessCommenting = accessCommenting;
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public long getLastSeenTime() {
        return this.lastSeenTime;
    }

    public void setLastSeenTime(long lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }

    public String getComputedCreationDate() {
        return this.computedCreationDate;
    }

    public void setComputedCreationDate(String computedCreationDate) {
        this.computedCreationDate = computedCreationDate;
    }

    public static void setNewsPublishTime(TextView newsPublishTime, News tagNews) {
        if (TextUtils.isEmpty(tagNews.getComputedCreationDate())) {
            try {
                newsPublishTime.setText(new PrettyTime(new Locale("FA")).format(new Date((long) (Double.valueOf((double) tagNews.getCreationDate()).doubleValue() * 1000.0d))));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        newsPublishTime.setText(tagNews.getComputedCreationDate() + "");
    }
}
