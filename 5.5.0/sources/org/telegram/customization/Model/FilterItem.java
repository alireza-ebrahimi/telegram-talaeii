package org.telegram.customization.Model;

public class FilterItem {
    public static final int FILTER_TYPE_CATEGORY = 1;
    public static final int FILTER_TYPE_MEDIA_TYPE = 2;
    public static final int FILTER_TYPE_SORT = 3;
    long id;
    String name;
    boolean selected;
    int type;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public void setType(int i) {
        this.type = i;
    }
}
