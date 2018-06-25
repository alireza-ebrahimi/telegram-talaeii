package org.telegram.customization.dynamicadapter.data;

import java.util.ArrayList;

public class SlsFilterHelper {
    int code;
    ArrayList<SlsFilter> items;
    String message;

    public ArrayList<SlsFilter> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<SlsFilter> items) {
        this.items = items;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
