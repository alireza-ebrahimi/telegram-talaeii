package org.telegram.customization.Model;

public class FilterResponse {
    long channelId;
    boolean isFilter;
    boolean showDialog = false;

    public long getChannelId() {
        return this.channelId;
    }

    public boolean isFilter() {
        return this.isFilter;
    }

    public boolean isShowDialog() {
        return this.showDialog;
    }

    public void setChannelId(long j) {
        this.channelId = j;
    }

    public void setFilter(boolean z) {
        this.isFilter = z;
    }

    public void setShowDialog(boolean z) {
        this.showDialog = z;
    }
}
