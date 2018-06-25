package org.telegram.customization.Model;

public class FilterHelper {
    long categories = 0;
    int mediaTypes = 0;
    int sortOption;

    public long getCategories() {
        return this.categories;
    }

    public void setCategories(long categories) {
        this.categories = categories;
    }

    public int getMediaTypes() {
        return this.mediaTypes;
    }

    public void setMediaTypes(int mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public int getSortOption() {
        return this.sortOption;
    }

    public void setSortOption(int sortOption) {
        this.sortOption = sortOption;
    }
}
