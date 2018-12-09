package org.telegram.customization.Model.Payment;

public class Channel {
    long channelId;
    String username;

    public long getChannelId() {
        return this.channelId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setChannelId(long j) {
        this.channelId = j;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
