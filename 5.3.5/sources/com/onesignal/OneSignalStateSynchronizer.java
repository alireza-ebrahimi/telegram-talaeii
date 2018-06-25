package com.onesignal;

import io.fabric.sdk.android.services.common.CommonUtils;
import org.json.JSONException;
import org.json.JSONObject;

class OneSignalStateSynchronizer {
    private static UserStateEmailSynchronizer userStateEmailSynchronizer;
    private static UserStatePushSynchronizer userStatePushSynchronizer;

    OneSignalStateSynchronizer() {
    }

    static UserStatePushSynchronizer getPushStateSynchronizer() {
        if (userStatePushSynchronizer == null) {
            userStatePushSynchronizer = new UserStatePushSynchronizer();
        }
        return userStatePushSynchronizer;
    }

    static UserStateEmailSynchronizer getEmailStateSynchronizer() {
        if (userStateEmailSynchronizer == null) {
            userStateEmailSynchronizer = new UserStateEmailSynchronizer();
        }
        return userStateEmailSynchronizer;
    }

    static boolean persist() {
        boolean pushPersisted = getPushStateSynchronizer().persist();
        boolean emailPersisted = getEmailStateSynchronizer().persist();
        if (emailPersisted) {
            if (getEmailStateSynchronizer().getRegistrationId() != null) {
                emailPersisted = true;
            } else {
                emailPersisted = false;
            }
        }
        if (pushPersisted || emailPersisted) {
            return true;
        }
        return false;
    }

    static void clearLocation() {
        getPushStateSynchronizer().clearLocation();
        getEmailStateSynchronizer().clearLocation();
    }

    static void initUserState() {
        getPushStateSynchronizer().initUserState();
        getEmailStateSynchronizer().initUserState();
    }

    static void syncUserState(boolean fromSyncService) {
        getPushStateSynchronizer().syncUserState(fromSyncService);
        getEmailStateSynchronizer().syncUserState(fromSyncService);
    }

    static void sendTags(JSONObject newTags) {
        try {
            JSONObject jsonField = new JSONObject().put("tags", newTags);
            getPushStateSynchronizer().sendTags(jsonField);
            getEmailStateSynchronizer().sendTags(jsonField);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void syncHashedEmail(String email) {
        try {
            JSONObject emailFields = new JSONObject();
            emailFields.put("em_m", OSUtils.hexDigest(email, "MD5"));
            emailFields.put("em_s", OSUtils.hexDigest(email, CommonUtils.SHA1_INSTANCE));
            getPushStateSynchronizer().syncHashedEmail(emailFields);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    static void setEmail(String email, String emailAuthHash) {
        getPushStateSynchronizer().setEmail(email, emailAuthHash);
        getEmailStateSynchronizer().setEmail(email, emailAuthHash);
    }

    static void setSubscription(boolean enable) {
        getPushStateSynchronizer().setSubscription(enable);
    }

    static boolean getUserSubscribePreference() {
        return getPushStateSynchronizer().getUserSubscribePreference();
    }

    static void setPermission(boolean enable) {
        getPushStateSynchronizer().setPermission(enable);
    }

    static void updateLocation(LocationPoint point) {
        getPushStateSynchronizer().updateLocation(point);
        getEmailStateSynchronizer().updateLocation(point);
    }

    static boolean getSubscribed() {
        return getPushStateSynchronizer().getSubscribed();
    }

    static String getRegistrationId() {
        return getPushStateSynchronizer().getRegistrationId();
    }

    static GetTagsResult getTags(boolean fromServer) {
        return getPushStateSynchronizer().getTags(fromServer);
    }

    static void resetCurrentState() {
        getPushStateSynchronizer().resetCurrentState();
        getEmailStateSynchronizer().resetCurrentState();
        OneSignal.saveUserId(null);
        OneSignal.saveEmailId(null);
        OneSignal.setLastSessionTime(-3660);
    }

    static void updateDeviceInfo(JSONObject deviceInfo) {
        getPushStateSynchronizer().updateDeviceInfo(deviceInfo);
        getEmailStateSynchronizer().updateDeviceInfo(deviceInfo);
    }

    static void updatePushState(JSONObject pushState) {
        getPushStateSynchronizer().updateState(pushState);
    }

    static void refreshEmailState() {
        getEmailStateSynchronizer().refresh();
    }

    static void setSyncAsNewSession() {
        getPushStateSynchronizer().setSyncAsNewSession();
        getEmailStateSynchronizer().setSyncAsNewSession();
    }

    static void setSyncAsNewSessionForEmail() {
        getEmailStateSynchronizer().setSyncAsNewSession();
    }

    static void logoutEmail() {
        getPushStateSynchronizer().logoutEmail();
        getEmailStateSynchronizer().logoutEmail();
    }
}
