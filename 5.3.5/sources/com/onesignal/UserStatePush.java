package com.onesignal;

import org.json.JSONException;

class UserStatePush extends UserState {
    UserStatePush(String inPersistKey, boolean load) {
        super(inPersistKey, load);
    }

    UserState newInstance(String persistKey) {
        return new UserStatePush(persistKey, false);
    }

    protected void addDependFields() {
        try {
            this.syncValues.put("notification_types", getNotificationTypes());
        } catch (JSONException e) {
        }
    }

    private int getNotificationTypes() {
        int subscribableStatus = this.dependValues.optInt("subscribableStatus", 1);
        if (subscribableStatus < -2) {
            return subscribableStatus;
        }
        if (!this.dependValues.optBoolean("androidPermission", true)) {
            return 0;
        }
        if (this.dependValues.optBoolean("userSubscribePref", true)) {
            return 1;
        }
        return -2;
    }

    boolean isSubscribed() {
        return getNotificationTypes() > 0;
    }
}
