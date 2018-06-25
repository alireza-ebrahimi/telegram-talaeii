package com.onesignal;

import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract class UserState {
    private static final String[] LOCATION_FIELDS = new String[]{"lat", "long", "loc_acc", "loc_type", "loc_bg", "loc_time_stamp", "ad_id"};
    private static final Set<String> LOCATION_FIELDS_SET = new HashSet(Arrays.asList(LOCATION_FIELDS));
    private static final Object syncLock = new C07011();
    protected final int NOTIFICATION_TYPES_NO_PERMISSION = 0;
    protected final int NOTIFICATION_TYPES_SUBSCRIBED = 1;
    protected final int NOTIFICATION_TYPES_UNSUBSCRIBE = -2;
    JSONObject dependValues;
    private String persistKey;
    JSONObject syncValues;

    /* renamed from: com.onesignal.UserState$1 */
    static class C07011 {
        C07011() {
        }
    }

    protected abstract void addDependFields();

    abstract boolean isSubscribed();

    abstract UserState newInstance(String str);

    UserState(String inPersistKey, boolean load) {
        this.persistKey = inPersistKey;
        if (load) {
            loadState();
            return;
        }
        this.dependValues = new JSONObject();
        this.syncValues = new JSONObject();
    }

    UserState deepClone(String persistKey) {
        UserState clonedUserState = newInstance(persistKey);
        try {
            clonedUserState.dependValues = new JSONObject(this.dependValues.toString());
            clonedUserState.syncValues = new JSONObject(this.syncValues.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clonedUserState;
    }

    private Set<String> getGroupChangeFields(UserState changedTo) {
        try {
            if (this.dependValues.optLong("loc_time_stamp") != changedTo.dependValues.getLong("loc_time_stamp")) {
                changedTo.syncValues.put("loc_bg", changedTo.dependValues.opt("loc_bg"));
                changedTo.syncValues.put("loc_time_stamp", changedTo.dependValues.opt("loc_time_stamp"));
                return LOCATION_FIELDS_SET;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    void setLocation(LocationPoint point) {
        try {
            this.syncValues.put("lat", point.lat);
            this.syncValues.put("long", point.log);
            this.syncValues.put("loc_acc", point.accuracy);
            this.syncValues.put("loc_type", point.type);
            this.dependValues.put("loc_bg", point.bg);
            this.dependValues.put("loc_time_stamp", point.timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void clearLocation() {
        try {
            this.syncValues.put("lat", null);
            this.syncValues.put("long", null);
            this.syncValues.put("loc_acc", null);
            this.syncValues.put("loc_type", null);
            this.syncValues.put("loc_bg", null);
            this.syncValues.put("loc_time_stamp", null);
            this.dependValues.put("loc_bg", null);
            this.dependValues.put("loc_time_stamp", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject generateJsonDiff(UserState newState, boolean isSessionCall) {
        addDependFields();
        newState.addDependFields();
        JSONObject sendJson = generateJsonDiff(this.syncValues, newState.syncValues, null, getGroupChangeFields(newState));
        if (!isSessionCall && sendJson.toString().equals("{}")) {
            return null;
        }
        try {
            if (!sendJson.has("app_id")) {
                sendJson.put("app_id", this.syncValues.optString("app_id"));
            }
            if (!this.syncValues.has("email_auth_hash")) {
                return sendJson;
            }
            sendJson.put("email_auth_hash", this.syncValues.optString("email_auth_hash"));
            return sendJson;
        } catch (JSONException e) {
            e.printStackTrace();
            return sendJson;
        }
    }

    void set(String key, Object value) {
        try {
            this.syncValues.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadState() {
        String dependValuesStr = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_USERSTATE_DEPENDVALYES_" + this.persistKey, null);
        if (dependValuesStr == null) {
            this.dependValues = new JSONObject();
            boolean userSubscribePref = true;
            try {
                int subscribableStatus;
                if (this.persistKey.equals("CURRENT_STATE")) {
                    subscribableStatus = OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_SUBSCRIPTION", 1);
                } else {
                    subscribableStatus = OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_SYNCED_SUBSCRIPTION", 1);
                }
                if (subscribableStatus == -2) {
                    subscribableStatus = 1;
                    userSubscribePref = false;
                }
                this.dependValues.put("subscribableStatus", subscribableStatus);
                this.dependValues.put("userSubscribePref", userSubscribePref);
            } catch (JSONException e) {
            }
        } else {
            try {
                this.dependValues = new JSONObject(dependValuesStr);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        String syncValuesStr = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_USERSTATE_SYNCVALYES_" + this.persistKey, null);
        if (syncValuesStr == null) {
            try {
                this.syncValues = new JSONObject();
                this.syncValues.put(SettingsJsonConstants.APP_IDENTIFIER_KEY, OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "GT_REGISTRATION_ID", null));
                return;
            } catch (JSONException e22) {
                e22.printStackTrace();
                return;
            }
        }
        this.syncValues = new JSONObject(syncValuesStr);
    }

    void persistState() {
        synchronized (syncLock) {
            modifySyncValuesJsonArray("pkgs");
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_USERSTATE_SYNCVALYES_" + this.persistKey, this.syncValues.toString());
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_USERSTATE_DEPENDVALYES_" + this.persistKey, this.dependValues.toString());
        }
    }

    private void modifySyncValuesJsonArray(String baseKey) {
        try {
            JSONArray orgArray;
            int i;
            if (this.syncValues.has(baseKey)) {
                orgArray = this.syncValues.getJSONArray(baseKey);
            } else {
                orgArray = new JSONArray();
            }
            JSONArray tempArray = new JSONArray();
            if (this.syncValues.has(baseKey + "_d")) {
                String remArrayStr = JSONUtils.toStringNE(this.syncValues.getJSONArray(baseKey + "_d"));
                for (i = 0; i < orgArray.length(); i++) {
                    if (!remArrayStr.contains(orgArray.getString(i))) {
                        tempArray.put(orgArray.get(i));
                    }
                }
            } else {
                tempArray = orgArray;
            }
            if (this.syncValues.has(baseKey + "_a")) {
                JSONArray newArray = this.syncValues.getJSONArray(baseKey + "_a");
                for (i = 0; i < newArray.length(); i++) {
                    tempArray.put(newArray.get(i));
                }
            }
            this.syncValues.put(baseKey, tempArray);
            this.syncValues.remove(baseKey + "_a");
            this.syncValues.remove(baseKey + "_d");
        } catch (Throwable th) {
        }
    }

    void persistStateAfterSync(JSONObject inDependValues, JSONObject inSyncValues) {
        if (inDependValues != null) {
            generateJsonDiff(this.dependValues, inDependValues, this.dependValues, null);
        }
        if (inSyncValues != null) {
            generateJsonDiff(this.syncValues, inSyncValues, this.syncValues, null);
            mergeTags(inSyncValues, null);
        }
        if (inDependValues != null || inSyncValues != null) {
            persistState();
        }
    }

    void mergeTags(JSONObject inSyncValues, JSONObject omitKeys) {
        synchronized (syncLock) {
            if (inSyncValues.has("tags")) {
                JSONObject newTags;
                if (this.syncValues.has("tags")) {
                    try {
                        newTags = new JSONObject(this.syncValues.optString("tags"));
                    } catch (JSONException e) {
                        newTags = new JSONObject();
                    }
                } else {
                    newTags = new JSONObject();
                }
                JSONObject curTags = inSyncValues.optJSONObject("tags");
                Iterator<String> keys = curTags.keys();
                while (keys.hasNext()) {
                    try {
                        String key = (String) keys.next();
                        if ("".equals(curTags.optString(key))) {
                            newTags.remove(key);
                        } else {
                            if (omitKeys != null) {
                                if (omitKeys.has(key)) {
                                }
                            }
                            newTags.put(key, curTags.optString(key));
                        }
                    } catch (Throwable th) {
                    }
                }
                if (newTags.toString().equals("{}")) {
                    this.syncValues.remove("tags");
                } else {
                    this.syncValues.put("tags", newTags);
                }
            }
        }
    }

    private static JSONObject generateJsonDiff(JSONObject cur, JSONObject changedTo, JSONObject baseOutput, Set<String> includeFields) {
        JSONObject generateJsonDiff;
        synchronized (syncLock) {
            generateJsonDiff = JSONUtils.generateJsonDiff(cur, changedTo, baseOutput, includeFields);
        }
        return generateJsonDiff;
    }
}
