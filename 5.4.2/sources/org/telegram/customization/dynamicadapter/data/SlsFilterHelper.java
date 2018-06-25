package org.telegram.customization.dynamicadapter.data;

import java.util.ArrayList;

public class SlsFilterHelper {
    int code;
    ArrayList<SlsFilter> items;
    String message;

    public int getCode() {
        return this.code;
    }

    public ArrayList<SlsFilter> getItems() {
        return this.items;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void setItems(ArrayList<SlsFilter> arrayList) {
        this.items = arrayList;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
