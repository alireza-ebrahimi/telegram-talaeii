package org.telegram.customization.Model;

public class SearchRequest {
    int direction;
    long lastRow;
    long mediaType;
    int pageSize;
    boolean phrasesearch;
    String searchTerm;
    long sortOrder;
    int tagId;

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(long mediaType) {
        this.mediaType = mediaType;
    }

    public long getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(long lastRow) {
        this.lastRow = lastRow;
    }

    public long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isPhrasesearch() {
        return this.phrasesearch;
    }

    public void setPhrasesearch(boolean phrasesearch) {
        this.phrasesearch = phrasesearch;
    }
}
