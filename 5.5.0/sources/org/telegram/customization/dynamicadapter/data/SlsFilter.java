package org.telegram.customization.dynamicadapter.data;

public class SlsFilter {
    public static final int TYPE_FILTER = 1;
    public static final int TYPE_SORT = 2;
    boolean clickable = true;
    boolean deletable;
    String description;
    int id;
    String image;
    String name;
    boolean selected;
    private int type;
    int value;

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.value;
    }

    public String getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setClickable(boolean z) {
        this.clickable = z;
    }

    public void setDeletable(boolean z) {
        this.deletable = z;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setImage(String str) {
        this.image = str;
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

    public void setValue(int i) {
        this.value = i;
    }
}
