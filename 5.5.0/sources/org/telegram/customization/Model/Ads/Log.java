package org.telegram.customization.Model.Ads;

public class Log {
    public static final int ACTION_CLICK = 2;
    public static final int ACTION_VIEW = 4;
    int action;
    int channelId;
    int messageId;
    String url;

    public int getAction() {
        return this.action;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setAction(int i) {
        this.action = i;
    }

    public void setChannelId(int i) {
        this.channelId = i;
    }

    public void setMessageId(int i) {
        this.messageId = i;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
