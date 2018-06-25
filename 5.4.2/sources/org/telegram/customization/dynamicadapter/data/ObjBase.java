package org.telegram.customization.dynamicadapter.data;

public class ObjBase {
    public static final int ITEM_TYPE_DIFFERNT_TILE_SIZE = 7;
    public static final int ITEM_TYPE_IMAGE_SAME_TILE_SIZE = 6;
    public static final int ITEM_TYPE_MESSAGE = 5;
    public static final int ITEM_TYPE_SEARCH = 2;
    public static final int ITEM_TYPE_STATISTICS = 1;
    public static final int ITEM_TYPE_TAG_COLLECTION = 4;
    public static final int ITEM_TYPE_TITLE = 8;
    public static final int ITEM_TYPE_VIDEO = 3;
    protected String color;
    private long itemId;
    String localHelperField;
    String meta;
    int position;
    int row;
    protected String title;
    protected int type;

    public String getColor() {
        return this.color;
    }

    public long getItemId() {
        return this.itemId;
    }

    public String getLocalHelperField() {
        return this.localHelperField;
    }

    public String getMeta() {
        return this.meta;
    }

    public int getPosition() {
        return this.position;
    }

    public int getRow() {
        return this.row;
    }

    public String getTitle() {
        return this.title;
    }

    public int getType() {
        return this.type;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public void setItemId(long j) {
        this.itemId = j;
    }

    public void setLocalHelperField(String str) {
        this.localHelperField = str;
    }

    public void setMeta(String str) {
        this.meta = str;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public void setRow(int i) {
        this.row = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setType(int i) {
        this.type = i;
    }
}
