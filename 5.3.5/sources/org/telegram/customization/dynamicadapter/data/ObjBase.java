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

    public String getMeta() {
        return this.meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getLocalHelperField() {
        return this.localHelperField;
    }

    public void setLocalHelperField(String localHelperField) {
        this.localHelperField = localHelperField;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getItemId() {
        return this.itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
