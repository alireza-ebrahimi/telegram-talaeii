package org.telegram.customization.Model.Payment;

import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("ManuId")
    int id;
    int parentMenuId;
    @SerializedName("Status")
    int status;
    @SerializedName("Title")
    String title;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentMenuId() {
        return this.parentMenuId;
    }

    public void setParentMenuId(int parentMenuId) {
        this.parentMenuId = parentMenuId;
    }
}
