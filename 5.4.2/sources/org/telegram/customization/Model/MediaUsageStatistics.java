package org.telegram.customization.Model;

public class MediaUsageStatistics {
    public static final int MEDIA_TYPE_CALLS = 5;
    public static final int MEDIA_TYPE_FILES = 4;
    public static final int MEDIA_TYPE_MESSAGE_AND_OTHER_DATA = 5;
    public static final int MEDIA_TYPE_PHOTOS = 1;
    public static final int MEDIA_TYPE_TOTAL = 6;
    public static final int MEDIA_TYPE_VIDEOS = 2;
    public static final int MEDIA_TYPE_VOICE_AND_VIDEO_MESSAGE = 3;
    public static final int NETWORK_TYPE_MOBILE = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    long bytesReceived;
    long bytesSent;
    int mediaType;
    int received;
    int sent;
    long totalTime;

    public long getBytesReceived() {
        return this.bytesReceived;
    }

    public long getBytesSent() {
        return this.bytesSent;
    }

    public int getMediaType() {
        return this.mediaType;
    }

    public int getReceived() {
        return this.received;
    }

    public int getSent() {
        return this.sent;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public void setBytesReceived(long j) {
        this.bytesReceived = j;
    }

    public void setBytesSent(long j) {
        this.bytesSent = j;
    }

    public void setMediaType(int i) {
        this.mediaType = i;
    }

    public void setReceived(int i) {
        this.received = i;
    }

    public void setSent(int i) {
        this.sent = i;
    }

    public void setTotalTime(long j) {
        this.totalTime = j;
    }
}
