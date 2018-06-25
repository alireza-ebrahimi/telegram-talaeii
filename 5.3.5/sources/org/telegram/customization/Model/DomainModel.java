package org.telegram.customization.Model;

public class DomainModel {
    int status = 0;
    String url;

    public DomainModel(int status, String url) {
        this.status = status;
        this.url = url;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
