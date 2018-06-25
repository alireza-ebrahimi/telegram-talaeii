package org.telegram.customization.util.view.Poll;

public class PollItem {
    String color;
    int count;
    int id;
    String imageUrl;
    int percent;
    int sortOrder;
    String title;

    public PollItem(int id, String imageUrl, String title, int count, int percent) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.count = count;
        this.percent = percent;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPercent() {
        return this.percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void increaseCount(int val) {
        this.count += val;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
