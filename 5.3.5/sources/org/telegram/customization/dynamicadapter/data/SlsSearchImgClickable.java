package org.telegram.customization.dynamicadapter.data;

public class SlsSearchImgClickable extends ObjBase {
    private String image;
    private long mediaType;
    private String searchText;
    private String showName;

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public long getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(long mediaType) {
        this.mediaType = mediaType;
    }

    public SlsSearchImgClickable(String showName, String searchTxt, String image) {
        this.showName = showName;
        this.searchText = searchTxt;
        this.image = image;
        this.type = 112;
    }
}
