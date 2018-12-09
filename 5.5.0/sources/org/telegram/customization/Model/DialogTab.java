package org.telegram.customization.Model;

public class DialogTab {
    int dialogType;
    boolean hidden;
    String showName;
    int tabDrawable;
    int tabLayoutResource;
    String tag;

    public DialogTab(int i, int i2, String str, boolean z, int i3, String str2) {
        this.tabLayoutResource = i;
        this.tabDrawable = i2;
        this.tag = str;
        this.hidden = z;
        this.dialogType = i3;
        this.showName = str2;
    }

    public int getDialogType() {
        return this.dialogType;
    }

    public String getShowName() {
        return this.showName;
    }

    public int getTabDrawable() {
        return this.tabDrawable;
    }

    public int getTabLayoutResource() {
        return this.tabLayoutResource;
    }

    public String getTag() {
        return this.tag;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setDialogType(int i) {
        this.dialogType = i;
    }

    public void setHidden(boolean z) {
        this.hidden = z;
    }

    public void setShowName(String str) {
        this.showName = str;
    }

    public void setTabDrawable(int i) {
        this.tabDrawable = i;
    }

    public void setTabLayoutResource(int i) {
        this.tabLayoutResource = i;
    }

    public void setTag(String str) {
        this.tag = str;
    }
}
