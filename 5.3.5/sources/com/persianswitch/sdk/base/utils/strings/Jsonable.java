package com.persianswitch.sdk.base.utils.strings;

import org.json.JSONException;
import org.json.JSONObject;

public interface Jsonable<T> {

    public static final class JsonParseException extends JSONException {
        public JsonParseException(String msg) {
            super(msg);
        }
    }

    public static final class JsonWriteException extends JSONException {
        public JsonWriteException(String msg) {
            super(msg);
        }
    }

    JSONObject parseJson(T t, String str) throws JsonParseException;

    JSONObject toJson(T t) throws JsonWriteException;
}
