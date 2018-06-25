package org.telegram.customization.dynamicadapter.data;

import com.google.p098a.p099a.C1662c;
import org.telegram.tgnet.TLRPC.Message;

public class SlsMessage extends Message {
    public static final int MEDIA_TYPE_AUDIO = 9;
    public static final int MEDIA_TYPE_FILE = 10;
    public static final int MEDIA_TYPE_GIF = 6;
    public static final int MEDIA_TYPE_IMAGE = 2;
    public static final int MEDIA_TYPE_TEXT = 1;
    public static final int MEDIA_TYPE_VIDEO = 8;
    private String channel_image;
    private String channel_name;
    private String fileUrl;
    @C1662c(a = "fs")
    private String freeState;
    private String image;
    @C1662c(a = "lbl")
    private String label;
    private int mediaType;
    private String streamUrl;
    private String thumbnailUrl;
    private String username;

    public String getChannel_image() {
        return this.channel_image;
    }

    public String getChannel_name() {
        return this.channel_name;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public String getFreeState() {
        return this.freeState;
    }

    public String getImage() {
        switch (getMediaType()) {
            case 2:
                return this.fileUrl;
            case 6:
                return this.thumbnailUrl;
            case 8:
                return this.thumbnailUrl;
            default:
                return this.image;
        }
    }

    public String getLabel() {
        return this.label;
    }

    public int getMediaType() {
        return this.mediaType;
    }

    public String getStreamUrl() {
        return this.fileUrl;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public void setChannel_image(String str) {
        this.channel_image = str;
    }

    public void setChannel_name(String str) {
        this.channel_name = str;
    }

    public void setFileUrl(String str) {
        this.fileUrl = str;
    }

    public void setFreeState(String str) {
        this.freeState = str;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setMediaType(int i) {
        this.mediaType = i;
    }

    public void setStreamUrl(String str) {
        this.streamUrl = str;
    }

    public void setThumbnailUrl(String str) {
        this.thumbnailUrl = str;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
