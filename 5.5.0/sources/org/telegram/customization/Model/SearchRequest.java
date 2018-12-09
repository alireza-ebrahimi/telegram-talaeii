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

    public int getDirection() {
        return this.direction;
    }

    public long getLastRow() {
        return this.lastRow;
    }

    public long getMediaType() {
        return this.mediaType;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public long getSortOrder() {
        return this.sortOrder;
    }

    public int getTagId() {
        return this.tagId;
    }

    public boolean isPhrasesearch() {
        return this.phrasesearch;
    }

    public void setDirection(int i) {
        this.direction = i;
    }

    public void setLastRow(long j) {
        this.lastRow = j;
    }

    public void setMediaType(long j) {
        this.mediaType = j;
    }

    public void setPageSize(int i) {
        this.pageSize = i;
    }

    public void setPhrasesearch(boolean z) {
        this.phrasesearch = z;
    }

    public void setSearchTerm(String str) {
        this.searchTerm = str;
    }

    public void setSortOrder(long j) {
        this.sortOrder = j;
    }

    public void setTagId(int i) {
        this.tagId = i;
    }
}
