package com.persianswitch.sdk.base.utils;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonParser<T> {
    T fromJson(String str) throws JSONException;

    JSONObject toJson(T t) throws JSONException;
}
