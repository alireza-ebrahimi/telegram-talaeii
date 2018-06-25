package com.onesignal;

import com.google.android.gms.measurement.AppMeasurement.Param;
import org.json.JSONException;
import org.json.JSONObject;

public class OSNotificationOpenResult {
    public OSNotificationAction action;
    public OSNotification notification;

    @Deprecated
    public String stringify() {
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject ac = new JSONObject();
            ac.put("actionID", this.action.actionID);
            ac.put(Param.TYPE, this.action.type.ordinal());
            mainObj.put("action", ac);
            mainObj.put(NotificationTable.TABLE_NAME, new JSONObject(this.notification.stringify()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    public JSONObject toJSONObject() {
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject jsonObjAction = new JSONObject();
            jsonObjAction.put("actionID", this.action.actionID);
            jsonObjAction.put(Param.TYPE, this.action.type.ordinal());
            mainObj.put("action", jsonObjAction);
            mainObj.put(NotificationTable.TABLE_NAME, this.notification.toJSONObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj;
    }
}
