package org.telegram.customization.dynamicadapter.data;

public class More extends ObjBase {
    boolean showMoreButtonVisibility;
    String sortTitle;
    int tagCount;

    public More() {
        this.type = 108;
    }

    public int getTagCount() {
        return this.tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public String getSortTitle() {
        return this.sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }

    public boolean isShowMoreButtonVisibility() {
        return this.showMoreButtonVisibility;
    }

    public void setShowMoreButtonVisibility(boolean showMoreButtonVisibility) {
        this.showMoreButtonVisibility = showMoreButtonVisibility;
    }
}
