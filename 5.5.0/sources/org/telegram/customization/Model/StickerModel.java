package org.telegram.customization.Model;

public class StickerModel {
    long id;
    String name;
    String title;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
