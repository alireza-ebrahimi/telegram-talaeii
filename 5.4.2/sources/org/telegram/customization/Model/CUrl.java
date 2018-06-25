package org.telegram.customization.Model;

public class CUrl {
    boolean enable;
    long prd;
    String url;

    public long getPrd() {
        return this.prd;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public void setPrd(long j) {
        this.prd = j;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
