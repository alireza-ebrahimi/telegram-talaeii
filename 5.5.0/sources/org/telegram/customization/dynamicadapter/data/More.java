package org.telegram.customization.dynamicadapter.data;

public class More extends ObjBase {
    boolean showMoreButtonVisibility;
    String sortTitle;
    int tagCount;

    public More() {
        this.type = 108;
    }

    public String getSortTitle() {
        return this.sortTitle;
    }

    public int getTagCount() {
        return this.tagCount;
    }

    public boolean isShowMoreButtonVisibility() {
        return this.showMoreButtonVisibility;
    }

    public void setShowMoreButtonVisibility(boolean z) {
        this.showMoreButtonVisibility = z;
    }

    public void setSortTitle(String str) {
        this.sortTitle = str;
    }

    public void setTagCount(int i) {
        this.tagCount = i;
    }
}
