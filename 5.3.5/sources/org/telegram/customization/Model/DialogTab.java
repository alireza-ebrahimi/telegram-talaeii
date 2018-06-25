package org.telegram.customization.Model;

public class DialogTab {
    int dialogType;
    boolean hidden;
    String showName;
    int tabDrawable;
    int tabLayoutResource;
    String tag;

    public DialogTab(int mTabLayoutRes, int mTabDrawable, String mTag, boolean mHide, int mDialogType, String mShowName) {
        this.tabLayoutResource = mTabLayoutRes;
        this.tabDrawable = mTabDrawable;
        this.tag = mTag;
        this.hidden = mHide;
        this.dialogType = mDialogType;
        this.showName = mShowName;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getTabLayoutResource() {
        return this.tabLayoutResource;
    }

    public void setTabLayoutResource(int tabLayoutResource) {
        this.tabLayoutResource = tabLayoutResource;
    }

    public int getTabDrawable() {
        return this.tabDrawable;
    }

    public void setTabDrawable(int tabDrawable) {
        this.tabDrawable = tabDrawable;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getDialogType() {
        return this.dialogType;
    }

    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }
}
