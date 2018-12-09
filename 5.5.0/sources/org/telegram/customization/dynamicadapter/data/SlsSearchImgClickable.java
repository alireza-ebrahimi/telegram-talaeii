package org.telegram.customization.dynamicadapter.data;

public class SlsSearchImgClickable extends ObjBase {
    private String image;
    private long mediaType;
    private String searchText;
    private String showName;

    public SlsSearchImgClickable(String str, String str2, String str3) {
        this.showName = str;
        this.searchText = str2;
        this.image = str3;
        this.type = 112;
    }

    public String getImage() {
        return this.image;
    }

    public long getMediaType() {
        return this.mediaType;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public void setMediaType(long j) {
        this.mediaType = j;
    }

    public void setSearchText(String str) {
        this.searchText = str;
    }

    public void setShowName(String str) {
        this.showName = str;
    }
}
