package org.telegram.customization.Model.Payment;

import com.google.p098a.p099a.C1662c;

public class Menu {
    @C1662c(a = "ManuId")
    int id;
    int parentMenuId;
    @C1662c(a = "Status")
    int status;
    @C1662c(a = "Title")
    String title;

    public int getId() {
        return this.id;
    }

    public int getParentMenuId() {
        return this.parentMenuId;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setParentMenuId(int i) {
        this.parentMenuId = i;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
