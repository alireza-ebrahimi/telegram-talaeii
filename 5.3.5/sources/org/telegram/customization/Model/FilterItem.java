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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
