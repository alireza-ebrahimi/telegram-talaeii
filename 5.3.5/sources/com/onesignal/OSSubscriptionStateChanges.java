package com.onesignal;

import org.json.JSONObject;

public class OSSubscriptionStateChanges {
    OSSubscriptionState from;
    OSSubscriptionState to;

    public OSSubscriptionState getTo() {
        return this.to;
    }

    public OSSubscriptionState getFrom() {
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
