package org.telegram.customization.Model;

public class CAI {
    boolean enable = false;
    String msg = TtmlNode.ANONYMOUS_REGION_ID;
    String pkg = TtmlNode.ANONYMOUS_REGION_ID;

    public String getMsg() {
        return this.msg;
    }

    public String getPkg() {
        return this.pkg;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setPkg(String str) {
        this.pkg = str;
    }
}
