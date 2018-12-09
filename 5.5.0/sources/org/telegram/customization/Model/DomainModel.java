package org.telegram.customization.Model;

public class DomainModel {
    int status = 0;
    String url;

    public DomainModel(int i, String str) {
        this.status = i;
        this.url = str;
    }

    public int getStatus() {
        return this.status;
    }

    public String getUrl() {
        return this.url;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
