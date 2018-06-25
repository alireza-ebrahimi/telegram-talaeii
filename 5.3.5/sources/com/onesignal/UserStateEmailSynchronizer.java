package com.onesignal;

import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import org.json.JSONException;
import org.json.JSONObject;

class UserStateEmailSynchronizer extends UserStateSynchronizer {
    UserStateEmailSynchronizer() {
    }

    protected UserState newUserState(String inPersistKey, boolean load) {
        return new UserStateEmail(inPersistKey, load);
    }

    boolean getSubscribed() {
        return false;
    }

    GetTagsResult getTags(boolean fromServer) {
        return null;
    }

    void setSubscription(boolean enable) {
    }

    public boolean getUserSubscribePreference() {
        return false;
    }

    public void setPermission(boolean enable) {
    }

    void updateState(JSONObject state) {
    }

    void refresh() {
        scheduleSyncToServer();
    }

    protected void scheduleSyncToServer() {
        boolean neverEmail;
        if (getId() == null && getRegistrationId() == null) {
            neverEmail = true;
        } else {
            neverEmail = false;
        }
        if (!neverEmail && OneSignal.getUserId() != null) {
            getNetworkHandlerThread(Integer.valueOf(0)).runNewJobDelayed();
        }
    }

    void setEmail(String email, String emailAuthHash) {
        boolean noChange;
        String existingEmail;
        JSONObject emailJSON;
        JSONObject syncValues = getUserStateForModification().syncValues;
        if (email.equals(syncValues.optString(SettingsJsonConstants.APP_IDENTIFIER_KEY))) {
            Object obj;
            String optString = syncValues.optString("email_auth_hash");
            if (emailAuthHash == null) {
                obj = "";
            } else {
                String str = emailAuthHash;
            }
            if (optString.equals(obj)) {
                noChange = true;
                if (noChange) {
                    existingEmail = syncValues.optString(SettingsJsonConstants.APP_IDENTIFIER_KEY, null);
                    if (existingEmail == null) {
                        setSyncAsNewSession();
                    }
                    try {
                        emailJSON = new JSONObject();
                        emailJSON.put(SettingsJsonConstants.APP_IDENTIFIER_KEY, email);
                        if (emailAuthHash != null) {
                            emailJSON.put("email_auth_hash", emailAuthHash);
                        }
                        if (!(emailAuthHash != null || existingEmail == null || existingEmail.equals(email))) {
                            OneSignal.saveEmailId("");
                            resetCurrentState();
                            setSyncAsNewSession();
                        }
                        generateJsonDiff(syncValues, emailJSON, syncValues, null);
                        scheduleSyncToServer();
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                OneSignal.fireEmailUpdateSuccess();
            }
        }
        noChange = false;
        if (noChange) {
            existingEmail = syncValues.optString(SettingsJsonConstants.APP_IDENTIFIER_KEY, null);
            if (existingEmail == null) {
                setSyncAsNewSession();
            }
            emailJSON = new JSONObject();
            emailJSON.put(SettingsJsonConstants.APP_IDENTIFIER_KEY, email);
            if (emailAuthHash != null) {
                emailJSON.put("email_auth_hash", emailAuthHash);
            }
            OneSignal.saveEmailId("");
            resetCurrentState();
            setSyncAsNewSession();
            generateJsonDiff(syncValues, emailJSON, syncValues, null);
            scheduleSyncToServer();
            return;
        }
        OneSignal.fireEmailUpdateSuccess();
    }

    protected String getId() {
        return OneSignal.getEmailId();
    }

    void updateIdDependents(String id) {
        OneSignal.updateEmailIdDependents(id);
    }

    protected void addOnSessionOrCreateExtras(JSONObject jsonBody) {
        try {
            jsonBody.put("device_type", 11);
            jsonBody.putOpt("device_player_id", OneSignal.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void logoutEmail() {
        OneSignal.saveEmailId("");
        resetCurrentState();
        this.toSyncUserState.syncValues.remove(SettingsJsonConstants.APP_IDENTIFIER_KEY);
        this.toSyncUserState.syncValues.remove("email_auth_hash");
        this.toSyncUserState.syncValues.remove("device_player_id");
        this.toSyncUserState.persistState();
        OneSignal.getPermissionSubscriptionState().emailSubscriptionStatus.clearEmailAndId();
    }

    protected void fireEventsForUpdateFailure(JSONObject jsonFields) {
        if (jsonFields.has(SettingsJsonConstants.APP_IDENTIFIER_KEY)) {
            OneSignal.fireEmailUpdateFailure();
        }
    }

    protected void onSuccessfulSync(JSONObject jsonFields) {
        if (jsonFields.has(SettingsJsonConstants.APP_IDENTIFIER_KEY)) {
            OneSignal.fireEmailUpdateSuccess();
        }
    }
}
