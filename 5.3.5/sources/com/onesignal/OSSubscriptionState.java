package com.onesignal;

import org.json.JSONObject;

public class OSSubscriptionState implements Cloneable {
    private boolean accepted;
    OSObservable<Object, OSSubscriptionState> observable = new OSObservable("changed", false);
    private String pushToken;
    private String userId;
    private boolean userSubscriptionSetting;

    OSSubscriptionState(boolean asFrom, boolean permissionAccepted) {
        if (asFrom) {
            this.userSubscriptionSetting = OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_SUBSCRIPTION_LAST", false);
            this.userId = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_PLAYER_ID_LAST", null);
            this.pushToken = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_PUSH_TOKEN_LAST", null);
            this.accepted = OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_PERMISSION_ACCEPTED_LAST", false);
            return;
        }
        this.userSubscriptionSetting = OneSignalStateSynchronizer.getUserSubscribePreference();
        this.userId = OneSignal.getUserId();
        this.pushToken = OneSignalStateSynchronizer.getRegistrationId();
        this.accepted = permissionAccepted;
    }

    void changed(OSPermissionState state) {
        setAccepted(state.getEnabled());
    }

    void setUserId(String id) {
        boolean changed = !id.equals(this.userId);
        this.userId = id;
        if (changed) {
            this.observable.notifyChange(this);
        }
    }

    public String getUserId() {
        return this.userId;
    }

    void setPushToken(String id) {
        if (id != null) {
            boolean changed = !id.equals(this.pushToken);
            this.pushToken = id;
            if (changed) {
                this.observable.notifyChange(this);
            }
        }
    }

    public String getPushToken() {
        return this.pushToken;
    }

    void setUserSubscriptionSetting(boolean set) {
        boolean changed = this.userSubscriptionSetting != set;
        this.userSubscriptionSetting = set;
        if (changed) {
            this.observable.notifyChange(this);
        }
    }

    public boolean getUserSubscriptionSetting() {
        return this.userSubscriptionSetting;
    }

    private void setAccepted(boolean set) {
        boolean lastSubscribed = getSubscribed();
        this.accepted = set;
        if (lastSubscribed != getSubscribed()) {
            this.observable.notifyChange(this);
        }
    }

    public boolean getSubscribed() {
        return this.userId != null && this.pushToken != null && this.userSubscriptionSetting && this.accepted;
    }

    void persistAsFrom() {
        OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_SUBSCRIPTION_LAST", this.userSubscriptionSetting);
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_PLAYER_ID_LAST", this.userId);
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_PUSH_TOKEN_LAST", this.pushToken);
        OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, "ONESIGNAL_PERMISSION_ACCEPTED_LAST", this.accepted);
    }

    boolean compare(OSSubscriptionState from) {
        if (this.userSubscriptionSetting == from.userSubscriptionSetting) {
            if ((this.userId != null ? this.userId : "").equals(from.userId != null ? from.userId : "")) {
                if ((this.pushToken != null ? this.pushToken : "").equals(from.pushToken != null ? from.pushToken : "") && this.accepted == from.accepted) {
                    return false;
                }
            }
        }
        return true;
    }

    protected Object clone() {
        try {
            return super.clone();
        } catch (Throwable th) {
            return null;
        }
    }

    public JSONObject toJSONObject() {
        JSONObject mainObj = new JSONObject();
        try {
            if (this.userId != null) {
                mainObj.put("userId", this.userId);
            } else {
                mainObj.put("userId", JSONObject.NULL);
            }
            if (this.pushToken != null) {
                mainObj.put("pushToken", this.pushToken);
            } else {
                mainObj.put("pushToken", JSONObject.NULL);
            }
            mainObj.put("userSubscriptionSetting", this.userSubscriptionSetting);
            mainObj.put("subscribed", getSubscribed());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return mainObj;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
