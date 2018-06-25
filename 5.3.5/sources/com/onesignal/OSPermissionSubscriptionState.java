package com.onesignal;

import org.json.JSONObject;

public class OSPermissionSubscriptionState {
    OSEmailSubscriptionState emailSubscriptionStatus;
    OSPermissionState permissionStatus;
    OSSubscriptionState subscriptionStatus;

    public OSPermissionState getPermissionStatus() {
        return this.permissionStatus;
    }

    public OSSubscriptionState getSubscriptionStatus() {
        return this.subscriptionStatus;
    }

    public OSEmailSubscriptionState getEmailSubscriptionStatus() {
        return this.emailSubscriptionStatus;
    }

    public JSONObject toJSONObject() {
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("permissionStatus", this.permissionStatus.toJSONObject());
            mainObj.put("subscriptionStatus", this.subscriptionStatus.toJSONObject());
            mainObj.put("emailSubscriptionStatus", this.emailSubscriptionStatus.toJSONObject());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return mainObj;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
