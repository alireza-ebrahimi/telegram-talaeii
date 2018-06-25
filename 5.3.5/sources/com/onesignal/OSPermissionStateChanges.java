package com.onesignal;

import org.json.JSONObject;

public class OSPermissionStateChanges {
    OSPermissionState from;
    OSPermissionState to;

    public OSPermissionState getTo() {
        return this.to;
    }

    public OSPermissionState getFrom() {
        return this.from;
    }

    public JSONObject toJSONObject() {
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("from", this.from.toJSONObject());
            mainObj.put("to", this.to.toJSONObject());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return mainObj;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
