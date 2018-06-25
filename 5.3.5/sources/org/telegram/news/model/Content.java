package org.telegram.news.model;

public class Content {
    public static final int ACCESS_STATUS_NOK = 2;
    public static final int ACCESS_STATUS_OK = 1;
    public static final int ACTION_TYPE_NO_ACTION = 3;
    public static final int ACTION_TYPE_OPEN_TAG = 1;
    public static final int ACTION_TYPE_UPGRADE_TO_TAG_ID = 2;
    int accessStatus;
    int actionType;
    String buttonName;
    String description;
    String duration;
    String newsId;
    String targetId;
    int type;
    String[] urls;
    String videoImage;
    String videoUrl;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getUrls() {
        return this.urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVideoImage() {
        return this.videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getTextSize() {
        return this.duration;
    }

    public void setTextSize(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getButtonName() {
        return this.buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getTargetId() {
        return this.targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public int getAccessStatus() {
        return this.accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public int getActionType() {
        return this.actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
}
