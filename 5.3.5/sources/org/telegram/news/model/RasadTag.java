package org.telegram.news.model;

public class RasadTag {
    public static final String DEFAULT_TAG_COLOR = "47C653";
    public static final int TYPE_AGENCY = 2;
    public static final int TYPE_CATEGORY = 1;
    public static final int TYPE_EDITOR = 10;
    public static final int TYPE_IMAGE = 6;
    public static final int TYPE_LICENSE = 7;
    public static final int TYPE_MOST_VISITED = 11;
    public static final int TYPE_TEXT = 3;
    public static final int TYPE_VIDEO = 12;
    boolean activeStatus;
    String bgColor;
    String defaultImageUrl;
    String imageUrl;
    boolean lock;
    int lockStatus = 200;
    private String message;
    String name;
    int newsCount;
    boolean removable = true;
    String showName;
    int sortOrder;
    public boolean statisticsImportant;
    News[] stickyNews;
    private boolean succeeded;
    boolean synced;
    int tagId;
    int type;
    int value;

    public boolean isStatisticsImportant() {
        return this.statisticsImportant;
    }

    public void setStatisticsImportant(boolean statisticsImportant) {
        this.statisticsImportant = statisticsImportant;
    }

    public boolean isRemovable() {
        return this.removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isSynced() {
        return this.synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    public int getLockStatus() {
        return this.lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public News[] getStickyNews() {
        if (this.stickyNews == null) {
            this.stickyNews = new News[0];
        }
        return this.stickyNews;
    }

    public void setStickyNews(News[] stickyNews) {
        this.stickyNews = stickyNews;
    }

    public String getDefaultImageUrl() {
        return this.defaultImageUrl;
    }

    public void setDefaultImageUrl(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getDailyCount() {
        return this.newsCount;
    }

    public void setDailyCount(int dailyCount) {
        this.newsCount = dailyCount;
    }

    public String getTitle() {
        if (this.name == null) {
            this.name = "";
        }
        return this.name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public int getId() {
        return this.tagId;
    }

    public void setId(int id) {
        this.tagId = id;
    }

    public void setTagColor(String tagColor) {
        this.bgColor = tagColor;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActiveStatus() {
        return this.activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public boolean isLock() {
        if (getLockStatus() < 200) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        return this.succeeded;
    }

    public void setSuccess(boolean success) {
        this.succeeded = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
