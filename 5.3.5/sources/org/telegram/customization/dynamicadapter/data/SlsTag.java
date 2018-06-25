package org.telegram.customization.dynamicadapter.data;

import com.google.gson.annotations.SerializedName;

public class SlsTag extends ObjBase {
    public static final String DEFAULT_TAG_COLOR = "47C653";
    public static final int TAG_TYPE_CHANNEL = 1;
    boolean add;
    String description;
    @SerializedName("tagId")
    private long id;
    private String image;
    boolean isChannel;
    private long parentId;
    private String showName;
    private int tagType;
    private String username;

    public boolean isChannel() {
        return this.isChannel;
    }

    public void setChannel(boolean channel) {
        this.isChannel = channel;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTagType() {
        return this.tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public boolean isAdd() {
        return this.add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
