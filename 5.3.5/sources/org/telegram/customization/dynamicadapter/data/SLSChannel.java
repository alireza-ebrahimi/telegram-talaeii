package org.telegram.customization.dynamicadapter.data;

import com.google.gson.annotations.SerializedName;

public class SLSChannel {
    @SerializedName("username")
    String channelId;
    @SerializedName("channel_id")
    long id;
    boolean isAdmin;
    boolean isInChat;
    @SerializedName("title")
    String name;
    int userId;

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public boolean isInChat() {
        return this.isInChat;
    }

    public void setInChat(boolean inChat) {
        this.isInChat = inChat;
    }
}
