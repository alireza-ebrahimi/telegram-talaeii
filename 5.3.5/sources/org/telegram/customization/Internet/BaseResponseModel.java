package org.telegram.customization.Internet;

import com.google.gson.annotations.SerializedName;

public class BaseResponseModel<TDataType> {
    private int code;
    @SerializedName("data")
    TDataType items;
    private String message;

    public TDataType getItems() {
        return this.items;
    }

    public void setItems(TDataType items) {
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
