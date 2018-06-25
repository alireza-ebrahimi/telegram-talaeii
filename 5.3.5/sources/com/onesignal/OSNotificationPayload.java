package com.onesignal;

import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

public class OSNotificationPayload {
    public List<ActionButton> actionButtons;
    public JSONObject additionalData;
    public BackgroundImageLayout backgroundImageLayout;
    public String bigPicture;
    public String body;
    public String collapseId;
    public String fromProjectNumber;
    public String groupKey;
    public String groupMessage;
    public String largeIcon;
    public String launchURL;
    public String ledColor;
    public int lockScreenVisibility = 1;
    public String notificationID;
    public int priority;
    public String rawPayload;
    public String smallIcon;
    public String smallIconAccentColor;
    public String sound;
    public String templateId;
    public String templateName;
    public String title;

    public static class ActionButton {
        public String icon;
        public String id;
        public String text;

        public ActionButton(JSONObject jsonObject) {
            this.id = jsonObject.optString("id");
            this.text = jsonObject.optString("text");
            this.icon = jsonObject.optString(SettingsJsonConstants.APP_ICON_KEY);
        }

        public JSONObject toJSONObject() {
            JSONObject json = new JSONObject();
            try {
                json.put("id", this.id);
                json.put("text", this.text);
                json.put(SettingsJsonConstants.APP_ICON_KEY, this.icon);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return json;
        }
    }

    public static class BackgroundImageLayout {
        public String bodyTextColor;
        public String image;
        public String titleTextColor;
    }

    public OSNotificationPayload(JSONObject jsonObject) {
        this.notificationID = jsonObject.optString("notificationID");
        this.title = jsonObject.optString("title");
        this.body = jsonObject.optString(TtmlNode.TAG_BODY);
        this.additionalData = jsonObject.optJSONObject("additionalData");
        this.smallIcon = jsonObject.optString("smallIcon");
        this.largeIcon = jsonObject.optString("largeIcon");
        this.bigPicture = jsonObject.optString("bigPicture");
        this.smallIconAccentColor = jsonObject.optString("smallIconAccentColor");
        this.launchURL = jsonObject.optString("launchURL");
        this.sound = jsonObject.optString("sound");
        this.ledColor = jsonObject.optString("ledColor");
        this.lockScreenVisibility = jsonObject.optInt("lockScreenVisibility");
        this.groupKey = jsonObject.optString("groupKey");
        this.groupMessage = jsonObject.optString("groupMessage");
        if (jsonObject.has("actionButtons")) {
            this.actionButtons = new ArrayList();
            JSONArray jsonArray = jsonObject.optJSONArray("actionButtons");
            for (int i = 0; i < jsonArray.length(); i++) {
                this.actionButtons.add(new ActionButton(jsonArray.optJSONObject(i)));
            }
        }
        this.fromProjectNumber = jsonObject.optString("fromProjectNumber");
        this.collapseId = jsonObject.optString("collapseId");
        this.priority = jsonObject.optInt("priority");
        this.rawPayload = jsonObject.optString("rawPayload");
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("notificationID", this.notificationID);
            json.put("title", this.title);
            json.put(TtmlNode.TAG_BODY, this.body);
            if (this.additionalData != null) {
                json.put("additionalData", this.additionalData);
            }
            json.put("smallIcon", this.smallIcon);
            json.put("largeIcon", this.largeIcon);
            json.put("bigPicture", this.bigPicture);
            json.put("smallIconAccentColor", this.smallIconAccentColor);
            json.put("launchURL", this.launchURL);
            json.put("sound", this.sound);
            json.put("ledColor", this.ledColor);
            json.put("lockScreenVisibility", this.lockScreenVisibility);
            json.put("groupKey", this.groupKey);
            json.put("groupMessage", this.groupMessage);
            if (this.actionButtons != null) {
                JSONArray actionButtonJsonArray = new JSONArray();
                for (ActionButton actionButton : this.actionButtons) {
                    actionButtonJsonArray.put(actionButton.toJSONObject());
                }
                json.put("actionButtons", actionButtonJsonArray);
            }
            json.put("fromProjectNumber", this.fromProjectNumber);
            json.put("collapseId", this.collapseId);
            json.put("priority", this.priority);
            json.put("rawPayload", this.rawPayload);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return json;
    }
}
