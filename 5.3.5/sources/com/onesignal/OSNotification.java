package com.onesignal;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OSNotification {
    public int androidNotificationId;
    public DisplayType displayType;
    public List<OSNotificationPayload> groupedNotifications;
    public boolean isAppInFocus;
    public OSNotificationPayload payload;
    public boolean shown;

    public enum DisplayType {
        Notification,
        InAppAlert,
        None
    }

    public OSNotification(JSONObject jsonObject) {
        this.isAppInFocus = jsonObject.optBoolean("isAppInFocus");
        this.shown = jsonObject.optBoolean("shown", this.shown);
        this.androidNotificationId = jsonObject.optInt("androidNotificationId");
        this.displayType = DisplayType.values()[jsonObject.optInt("displayType")];
        if (jsonObject.has("groupedNotifications")) {
            JSONArray jsonArray = jsonObject.optJSONArray("groupedNotifications");
            this.groupedNotifications = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                this.groupedNotifications.add(new OSNotificationPayload(jsonArray.optJSONObject(i)));
            }
        }
        if (jsonObject.has("payload")) {
            this.payload = new OSNotificationPayload(jsonObject.optJSONObject("payload"));
        }
    }

    @Deprecated
    public String stringify() {
        JSONObject mainObj = toJSONObject();
        try {
            if (mainObj.has("additionalData")) {
                mainObj.put("additionalData", mainObj.optJSONObject("additionalData").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    public JSONObject toJSONObject() {
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("isAppInFocus", this.isAppInFocus);
            mainObj.put("shown", this.shown);
            mainObj.put("androidNotificationId", this.androidNotificationId);
            mainObj.put("displayType", this.displayType.ordinal());
            if (this.groupedNotifications != null) {
                JSONArray payloadJsonArray = new JSONArray();
                for (OSNotificationPayload payload : this.groupedNotifications) {
                    payloadJsonArray.put(payload.toJSONObject());
                }
                mainObj.put("groupedNotifications", payloadJsonArray);
            }
            mainObj.put("payload", this.payload.toJSONObject());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return mainObj;
    }
}
