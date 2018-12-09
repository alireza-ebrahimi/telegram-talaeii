package org.telegram.customization.Model.Ads;

public class Category {
    public static final int STATUS_SELECTED = 1;
    public static final int STATUS_UNSELECTED = 2;
    int channelId;
    String desc;
    Long id;
    String image;
    boolean isSelected;
    int participant;
    int status;
    String title;
    String userName;

    public int getChannelId() {
        return this.channelId;
    }

    public String getDesc() {
        return this.desc;
    }

    public Long getId() {
        return this.id;
    }

    public String getImage() {
        return this.image;
    }

    public int getParticipant() {
        return this.participant;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUserName() {
        return this.userName;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setChannelId(int i) {
        this.channelId = i;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public void setParticipant(int i) {
        this.participant = i;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setUserName(String str) {
        this.userName = str;
    }
}
