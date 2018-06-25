package com.persianswitch.sdk.base.utils.strings;

import org.json.JSONException;

public interface Jsonable<T> {

    public static final class JsonParseException extends JSONException {
        public JsonParseException(String str) {
            super(str);
        }
    }

    public static final class JsonWriteException extends JSONException {
        public JsonWriteException(String str) {
            super(str);
        }
    }
}
