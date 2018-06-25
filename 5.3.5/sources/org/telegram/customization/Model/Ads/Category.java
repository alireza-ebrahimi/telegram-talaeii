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

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getParticipant() {
        return this.participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }
}
