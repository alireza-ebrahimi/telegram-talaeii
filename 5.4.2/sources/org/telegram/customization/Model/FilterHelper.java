package org.telegram.customization.Model;

public class FilterHelper {
    long categories = 0;
    int mediaTypes = 0;
    int sortOption;

    public long getCategories() {
        return this.categories;
    }

    public int getMediaTypes() {
        return this.mediaTypes;
    }

    public int getSortOption() {
        return this.sortOption;
    }

    public void setCategories(long j) {
        this.categories = j;
    }

    public void setMediaTypes(int i) {
        this.mediaTypes = i;
    }

    public void setSortOption(int i) {
        this.sortOption = i;
    }
}
