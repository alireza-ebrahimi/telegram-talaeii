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

    public int getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getSent() {
        return this.sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public int getReceived() {
        return this.received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public long getBytesSent() {
        return this.bytesSent;
    }

    public void setBytesSent(long bytesSent) {
        this.bytesSent = bytesSent;
    }

    public long getBytesReceived() {
        return this.bytesReceived;
    }

    public void setBytesReceived(long bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
