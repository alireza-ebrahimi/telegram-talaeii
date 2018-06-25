package org.telegram.customization.Model.Ads;

public class Log {
    public static final int ACTION_CLICK = 2;
    public static final int ACTION_VIEW = 4;
    int action;
    int channelId;
    int messageId;
    String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
