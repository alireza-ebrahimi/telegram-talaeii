package com.persianswitch.sdk.base.utils;

import java.util.Map;
import org.json.JSONObject;

public final class Json {
    public static JSONObject toJson(Map<String, Object> map) {
        return new JSONObject(map);
    }
}
