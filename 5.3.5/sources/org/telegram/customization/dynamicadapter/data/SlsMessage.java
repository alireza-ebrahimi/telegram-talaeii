package org.telegram.customization.dynamicadapter.data;

import com.google.gson.annotations.SerializedName;
import org.telegram.tgnet.TLRPC$Message;

public class SlsMessage extends TLRPC$Message {
    public static final int MEDIA_TYPE_AUDIO = 9;
    public static final int MEDIA_TYPE_FILE = 10;
    public static final int MEDIA_TYPE_GIF = 6;
    public static final int MEDIA_TYPE_IMAGE = 2;
    public static final int MEDIA_TYPE_TEXT = 1;
    public static final int MEDIA_TYPE_VIDEO = 8;
    private String channel_image;
    private String channel_name;
    private String fileUrl;
    @SerializedName("fs")
    private String freeState;
    private String image;
    @SerializedName("lbl")
    private String label;
    private int mediaType;
    private String streamUrl;
    private String thumbnailUrl;
    private String username;

    public String getFreeState() {
        return this.freeState;
    }

    public void setFreeState(String freeState) {
        this.freeState = freeState;
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getChannel_name() {
        return this.channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getStreamUrl() {
        return this.fileUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getChannel_image() {
        return this.channel_image;
    }

    public void setChannel_image(String channel_image) {
        this.channel_image = channel_image;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
