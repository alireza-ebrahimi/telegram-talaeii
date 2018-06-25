package org.telegram.customization.Model.Payment;

public class Channel {
    long channelId;
    String username;

    public long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
