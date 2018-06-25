package org.telegram.customization.Model.Ads;

public class Transaction {
    String date;
    String desc;
    String value;

    public String getDate() {
        return this.date;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getValue() {
        return this.value;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
