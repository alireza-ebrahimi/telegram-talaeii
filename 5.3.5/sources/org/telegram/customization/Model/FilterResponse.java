package org.telegram.customization.Model;

public class FilterResponse {
    long channelId;
    boolean isFilter;

    public long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public boolean isFilter() {
        return this.isFilter;
    }

    public void setFilter(boolean filter) {
        this.isFilter = filter;
    }
}
