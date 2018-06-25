package org.telegram.customization.dynamicadapter.data;

import com.google.p098a.p099a.C1662c;

public class SlsTag extends ObjBase {
    public static final String DEFAULT_TAG_COLOR = "47C653";
    public static final int TAG_TYPE_CHANNEL = 1;
    boolean add;
    String description;
    @C1662c(a = "tagId")
    private long id;
    private String image;
    boolean isChannel;
    private long parentId;
    private String showName;
    private int tagType;
    private String username;

    public String getDescription() {
        return this.description;
    }

    public long getId() {
        return this.id;
    }

    public String getImage() {
        return this.image;
    }

    public long getParentId() {
        return this.parentId;
    }

    public String getShowName() {
        return this.showName;
    }

    public int getTagType() {
        return this.tagType;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAdd() {
        return this.add;
    }

    public boolean isChannel() {
        return this.isChannel;
    }

    public void setAdd(boolean z) {
        this.add = z;
    }

    public void setChannel(boolean z) {
        this.isChannel = z;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public void setParentId(long j) {
        this.parentId = j;
    }

    public void setShowName(String str) {
        this.showName = str;
    }

    public void setTagType(int i) {
        this.tagType = i;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
