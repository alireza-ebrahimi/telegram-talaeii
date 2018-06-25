package com.onesignal;

import android.os.Handler;
import android.os.HandlerThread;
import com.onesignal.OneSignal.LOG_LEVEL;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.ui.ChatActivity;

abstract class UserStateSynchronizer {
    protected UserState currentUserState;
    private final Object networkHandlerSyncLock = new C07042();
    HashMap<Integer, NetworkHandlerThread> networkHandlerThreads = new HashMap();
    protected boolean nextSyncIsSession = false;
    private AtomicBoolean runningSyncUserState = new AtomicBoolean();
    protected final Object syncLock = new C07031();
    protected UserState toSyncUserState;
    protected boolean waitingForSessionResponse = false;

    /* renamed from: com.onesignal.UserStateSynchronizer$1 */
    class C07031 {
        C07031() {
        }
    }

    /* renamed from: com.onesignal.UserStateSynchronizer$2 */
    class C07042 {
        C07042() {
        }
    }

    /* renamed from: com.onesignal.UserStateSynchronizer$3 */
    class C07053 extends ResponseHandler {
        C07053() {
        }

        void onFailure(int statusCode, String response, Throwable throwable) {
            OneSignal.Log(LOG_LEVEL.WARN, "Failed last request. statusCode: " + statusCode + "\nresponse: " + response);
            if (UserStateSynchronizer.this.response400WithErrorsContaining(statusCode, response, "already logged out of email")) {
                UserStateSynchronizer.this.logoutEmailSyncSuccess();
            } else if (UserStateSynchronizer.this.response400WithErrorsContaining(statusCode, response, "not a valid device_type")) {
                UserStateSynchronizer.this.handlePlayerDeletedFromServer();
            } else {
                UserStateSynchronizer.this.handleNetworkFailure();
            }
        }

        void onSuccess(String response) {
            UserStateSynchronizer.this.logoutEmailSyncSuccess();
        }
    }

    static class GetTagsResult {
        JSONObject result;
        boolean serverSuccess;

        GetTagsResult(boolean serverSuccess, JSONObject result) {
            this.serverSuccess = serverSuccess;
            this.result = result;
        }
    }

    class NetworkHandlerThread extends HandlerThread {
        static final int MAX_RETRIES = 3;
        static final int NETWORK_CALL_DELAY_TO_BUFFER_MS = 5000;
        protected static final int NETWORK_HANDLER_USERSTATE = 0;
        int currentRetry;
        Handler mHandler = null;
        int mType;

        /* renamed from: com.onesignal.UserStateSynchronizer$NetworkHandlerThread$1 */
        class C07081 implements Runnable {
            C07081() {
            }

            public void run() {
                if (!UserStateSynchronizer.this.runningSyncUserState.get()) {
                    UserStateSynchronizer.this.syncUserState(false);
                }
            }
        }

        NetworkHandlerThread(int type) {
            super("OSH_NetworkHandlerThread");
            this.mType = type;
            start();
            this.mHandler = new Handler(getLooper());
        }

        void runNewJobDelayed() {
            synchronized (this.mHandler) {
                this.currentRetry = 0;
                this.mHandler.removeCallbacksAndMessages(null);
                this.mHandler.postDelayed(getNewRunnable(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            }
        }

        private Runnable getNewRunnable() {
            switch (this.mType) {
                case 0:
                    return new C07081();
                default:
                    return null;
            }
        }

        void stopScheduledRunnable() {
            this.mHandler.removeCallbacksAndMessages(null);
        }

        boolean doRetry() {
            boolean hasMessages;
            boolean doRetry = false;
            synchronized (this.mHandler) {
                if (this.currentRetry < 3) {
                    doRetry = true;
                }
                boolean futureSync = this.mHandler.hasMessages(0);
                if (doRetry && !futureSync) {
                    this.currentRetry++;
                    this.mHandler.postDelayed(getNewRunnable(), (long) (this.currentRetry * DefaultLoadControl.DEFAULT_MIN_BUFFER_MS));
                }
                hasMessages = this.mHandler.hasMessages(0);
            }
            return hasMessages;
        }
    }

    protected abstract void addOnSessionOrCreateExtras(JSONObject jSONObject);

    protected abstract void fireEventsForUpdateFailure(JSONObject jSONObject);

    protected abstract String getId();

    abstract boolean getSubscribed();

    abstract GetTagsResult getTags(boolean z);

    public abstract boolean getUserSubscribePreference();

    abstract void logoutEmail();

    protected abstract UserState newUserState(String str, boolean z);

    protected abstract void onSuccessfulSync(JSONObject jSONObject);

    protected abstract void scheduleSyncToServer();

    public abstract void setPermission(boolean z);

    abstract void setSubscription(boolean z);

    abstract void updateIdDependents(String str);

    abstract void updateState(JSONObject jSONObject);

    UserStateSynchronizer() {
    }

    String getRegistrationId() {
        return getToSyncUserState().syncValues.optString(SettingsJsonConstants.APP_IDENTIFIER_KEY, null);
    }

    protected JSONObject generateJsonDiff(JSONObject cur, JSONObject changedTo, JSONObject baseOutput, Set<String> includeFields) {
        JSONObject generateJsonDiff;
        synchronized (this.syncLock) {
            generateJsonDiff = JSONUtils.generateJsonDiff(cur, changedTo, baseOutput, includeFields);
        }
        return generateJsonDiff;
    }

    protected UserState getToSyncUserState() {
        if (this.toSyncUserState == null) {
            this.toSyncUserState = newUserState("TOSYNC_STATE", true);
        }
        return this.toSyncUserState;
    }

    void initUserState() {
        if (this.currentUserState == null) {
            this.currentUserState = newUserState("CURRENT_STATE", true);
        }
        if (this.toSyncUserState == null) {
            this.toSyncUserState = newUserState("TOSYNC_STATE", true);
        }
    }

    void clearLocation() {
        getToSyncUserState().clearLocation();
        getToSyncUserState().persistState();
    }

    boolean persist() {
        boolean unSynced = false;
        if (this.toSyncUserState != null) {
            synchronized (this.syncLock) {
                if (this.currentUserState.generateJsonDiff(this.toSyncUserState, isSessionCall()) != null) {
                    unSynced = true;
                }
                this.toSyncUserState.persistState();
            }
        }
        return unSynced;
    }

    private boolean isSessionCall() {
        return this.nextSyncIsSession && !this.waitingForSessionResponse;
    }

    private boolean syncEmailLogout() {
        return this.toSyncUserState.dependValues.optBoolean("logoutEmail", false);
    }

    synchronized void syncUserState(boolean fromSyncService) {
        this.runningSyncUserState.set(true);
        internalSyncUserState(fromSyncService);
        this.runningSyncUserState.set(false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void internalSyncUserState(boolean r10) {
        /*
        r9 = this;
        r3 = r9.getId();
        r4 = r9.syncEmailLogout();
        if (r4 == 0) goto L_0x0010;
    L_0x000a:
        if (r3 == 0) goto L_0x0010;
    L_0x000c:
        r9.doEmailLogout(r3);
    L_0x000f:
        return;
    L_0x0010:
        r1 = r9.isSessionCall();
        r5 = r9.syncLock;
        monitor-enter(r5);
        r4 = r9.currentUserState;	 Catch:{ all -> 0x0037 }
        r6 = r9.toSyncUserState;	 Catch:{ all -> 0x0037 }
        r2 = r4.generateJsonDiff(r6, r1);	 Catch:{ all -> 0x0037 }
        r4 = r9.currentUserState;	 Catch:{ all -> 0x0037 }
        r4 = r4.dependValues;	 Catch:{ all -> 0x0037 }
        r6 = r9.toSyncUserState;	 Catch:{ all -> 0x0037 }
        r6 = r6.dependValues;	 Catch:{ all -> 0x0037 }
        r7 = 0;
        r8 = 0;
        r0 = r9.generateJsonDiff(r4, r6, r7, r8);	 Catch:{ all -> 0x0037 }
        if (r2 != 0) goto L_0x003a;
    L_0x002f:
        r4 = r9.currentUserState;	 Catch:{ all -> 0x0037 }
        r6 = 0;
        r4.persistStateAfterSync(r0, r6);	 Catch:{ all -> 0x0037 }
        monitor-exit(r5);	 Catch:{ all -> 0x0037 }
        goto L_0x000f;
    L_0x0037:
        r4 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0037 }
        throw r4;
    L_0x003a:
        r4 = r9.toSyncUserState;	 Catch:{ all -> 0x0037 }
        r4.persistState();	 Catch:{ all -> 0x0037 }
        monitor-exit(r5);	 Catch:{ all -> 0x0037 }
        if (r1 == 0) goto L_0x0044;
    L_0x0042:
        if (r10 == 0) goto L_0x0048;
    L_0x0044:
        r9.doPutSync(r3, r2, r0);
        goto L_0x000f;
    L_0x0048:
        r9.doCreateOrNewSession(r3, r2, r0);
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.UserStateSynchronizer.internalSyncUserState(boolean):void");
    }

    private void doEmailLogout(String userId) {
        String urlStr = "players/" + userId + "/email_logout";
        JSONObject jsonBody = new JSONObject();
        try {
            JSONObject dependValues = this.currentUserState.dependValues;
            if (dependValues.has("email_auth_hash")) {
                jsonBody.put("email_auth_hash", dependValues.optString("email_auth_hash"));
            }
            JSONObject syncValues = this.currentUserState.syncValues;
            if (syncValues.has("parent_player_id")) {
                jsonBody.put("parent_player_id", syncValues.optString("parent_player_id"));
            }
            jsonBody.put("app_id", syncValues.optString("app_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OneSignalRestClient.postSync(urlStr, jsonBody, new C07053());
    }

    private void logoutEmailSyncSuccess() {
        this.toSyncUserState.dependValues.remove("logoutEmail");
        this.toSyncUserState.dependValues.remove("email_auth_hash");
        this.toSyncUserState.syncValues.remove("parent_player_id");
        this.toSyncUserState.persistState();
        this.currentUserState.dependValues.remove("email_auth_hash");
        this.currentUserState.syncValues.remove("parent_player_id");
        String emailLoggedOut = this.currentUserState.syncValues.optString("email");
        this.currentUserState.syncValues.remove("email");
        OneSignalStateSynchronizer.setSyncAsNewSessionForEmail();
        OneSignal.Log(LOG_LEVEL.INFO, "Device successfully logged out of email: " + emailLoggedOut);
        OneSignal.handleSuccessfulEmailLogout();
    }

    private void doPutSync(String userId, final JSONObject jsonBody, final JSONObject dependDiff) {
        if (userId != null) {
            OneSignalRestClient.putSync("players/" + userId, jsonBody, new ResponseHandler() {
                void onFailure(int statusCode, String response, Throwable throwable) {
                    OneSignal.Log(LOG_LEVEL.WARN, "Failed last request. statusCode: " + statusCode + "\nresponse: " + response);
                    if (UserStateSynchronizer.this.response400WithErrorsContaining(statusCode, response, "No user with this id found")) {
                        UserStateSynchronizer.this.handlePlayerDeletedFromServer();
                    } else {
                        UserStateSynchronizer.this.handleNetworkFailure();
                    }
                }

                void onSuccess(String response) {
                    UserStateSynchronizer.this.currentUserState.persistStateAfterSync(dependDiff, jsonBody);
                    UserStateSynchronizer.this.onSuccessfulSync(jsonBody);
                }
            });
        }
    }

    private void doCreateOrNewSession(final String userId, final JSONObject jsonBody, final JSONObject dependDiff) {
        String urlStr;
        if (userId == null) {
            urlStr = "players";
        } else {
            urlStr = "players/" + userId + "/on_session";
        }
        this.waitingForSessionResponse = true;
        addOnSessionOrCreateExtras(jsonBody);
        OneSignalRestClient.postSync(urlStr, jsonBody, new ResponseHandler() {
            void onFailure(int statusCode, String response, Throwable throwable) {
                UserStateSynchronizer.this.waitingForSessionResponse = false;
                OneSignal.Log(LOG_LEVEL.WARN, "Failed last request. statusCode: " + statusCode + "\nresponse: " + response);
                if (UserStateSynchronizer.this.response400WithErrorsContaining(statusCode, response, "not a valid device_type")) {
                    UserStateSynchronizer.this.handlePlayerDeletedFromServer();
                } else {
                    UserStateSynchronizer.this.handleNetworkFailure();
                }
            }

            void onSuccess(String response) {
                UserStateSynchronizer userStateSynchronizer = UserStateSynchronizer.this;
                UserStateSynchronizer.this.waitingForSessionResponse = false;
                userStateSynchronizer.nextSyncIsSession = false;
                UserStateSynchronizer.this.currentUserState.persistStateAfterSync(dependDiff, jsonBody);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("id")) {
                        String newUserId = jsonResponse.optString("id");
                        UserStateSynchronizer.this.updateIdDependents(newUserId);
                        OneSignal.Log(LOG_LEVEL.INFO, "Device registered, UserId = " + newUserId);
                    } else {
                        OneSignal.Log(LOG_LEVEL.INFO, "session sent, UserId = " + userId);
                    }
                    OneSignal.updateOnSessionDependents();
                    UserStateSynchronizer.this.onSuccessfulSync(jsonBody);
                } catch (Throwable t) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "ERROR parsing on_session or create JSON Response.", t);
                }
            }
        });
    }

    private void handleNetworkFailure() {
        if (!getNetworkHandlerThread(Integer.valueOf(0)).doRetry()) {
            JSONObject jsonBody = this.currentUserState.generateJsonDiff(this.toSyncUserState, false);
            if (jsonBody != null) {
                fireEventsForUpdateFailure(jsonBody);
            }
            if (this.toSyncUserState.dependValues.optBoolean("logoutEmail", false)) {
                OneSignal.handleFailedEmailLogout();
            }
        }
    }

    private boolean response400WithErrorsContaining(int statusCode, String response, String contains) {
        if (statusCode != ChatActivity.scheduleDownloads || response == null) {
            return false;
        }
        try {
            JSONObject responseJson = new JSONObject(response);
            if (responseJson.has("errors") && responseJson.optString("errors").contains(contains)) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    protected NetworkHandlerThread getNetworkHandlerThread(Integer type) {
        NetworkHandlerThread networkHandlerThread;
        synchronized (this.networkHandlerSyncLock) {
            if (!this.networkHandlerThreads.containsKey(type)) {
                this.networkHandlerThreads.put(type, new NetworkHandlerThread(type.intValue()));
            }
            networkHandlerThread = (NetworkHandlerThread) this.networkHandlerThreads.get(type);
        }
        return networkHandlerThread;
    }

    protected UserState getUserStateForModification() {
        if (this.toSyncUserState == null) {
            this.toSyncUserState = this.currentUserState.deepClone("TOSYNC_STATE");
        }
        scheduleSyncToServer();
        return this.toSyncUserState;
    }

    void updateDeviceInfo(JSONObject deviceInfo) {
        JSONObject toSync = getUserStateForModification().syncValues;
        generateJsonDiff(toSync, deviceInfo, toSync, null);
    }

    void setSyncAsNewSession() {
        this.nextSyncIsSession = true;
    }

    void sendTags(JSONObject tags) {
        JSONObject userStateTags = getUserStateForModification().syncValues;
        generateJsonDiff(userStateTags, tags, userStateTags, null);
    }

    void syncHashedEmail(JSONObject emailFields) {
        JSONObject syncValues = getUserStateForModification().syncValues;
        generateJsonDiff(syncValues, emailFields, syncValues, null);
    }

    private void handlePlayerDeletedFromServer() {
        OneSignal.handleSuccessfulEmailLogout();
        resetCurrentState();
        this.nextSyncIsSession = true;
        scheduleSyncToServer();
    }

    void resetCurrentState() {
        this.currentUserState.syncValues = new JSONObject();
        this.currentUserState.persistState();
    }

    void updateLocation(LocationPoint point) {
        getUserStateForModification().setLocation(point);
    }
}
