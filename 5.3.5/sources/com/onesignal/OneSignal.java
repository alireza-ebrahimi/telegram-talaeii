package com.onesignal;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.onesignal.OSNotification.DisplayType;
import com.onesignal.OSNotificationAction.ActionType;
import com.onesignal.PushRegistrator.RegisteredHandler;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;

public class OneSignal {
    static final long MIN_ON_FOCUS_TIME = 60;
    private static final long MIN_ON_SESSION_TIME = 30;
    public static final String VERSION = "030804";
    private static int androidParamsReties = 0;
    static Context appContext;
    static String appId;
    private static JSONObject awl;
    private static boolean awlFired;
    private static OSEmailSubscriptionState currentEmailSubscriptionState;
    private static OSPermissionState currentPermissionState;
    private static OSSubscriptionState currentSubscriptionState;
    private static int deviceType;
    private static String emailId = null;
    private static EmailUpdateHandler emailLogoutHandler;
    private static OSObservable<OSEmailSubscriptionObserver, OSEmailSubscriptionStateChanges> emailSubscriptionStateChangesObserver;
    private static EmailUpdateHandler emailUpdateHandler;
    private static boolean foreground;
    private static boolean getTagsCall;
    private static IAPUpdateJob iapUpdateJob;
    private static IdsAvailableHandler idsAvailableHandler;
    static boolean initDone;
    static OSEmailSubscriptionState lastEmailSubscriptionState;
    private static LocationPoint lastLocationPoint;
    static OSPermissionState lastPermissionState;
    private static String lastRegistrationId;
    static OSSubscriptionState lastSubscriptionState;
    static AtomicLong lastTaskId = new AtomicLong();
    private static long lastTrackedFocusTime = 1;
    private static boolean locationFired;
    private static LOG_LEVEL logCatLevel = LOG_LEVEL.WARN;
    static boolean mEnterp;
    private static String mGoogleProjectNumber;
    private static boolean mGoogleProjectNumberIsRemote;
    static Builder mInitBuilder;
    private static AdvertisingIdentifierProvider mainAdIdProvider = new AdvertisingIdProviderGPS();
    private static OSUtils osUtils;
    private static GetTagsHandler pendingGetTagsHandler;
    static ExecutorService pendingTaskExecutor;
    private static OSObservable<OSPermissionObserver, OSPermissionStateChanges> permissionStateChangesObserver;
    private static HashSet<String> postedOpenedNotifIds = new HashSet();
    private static boolean promptedLocation;
    private static boolean registerForPushFired;
    public static String sdkType = "native";
    private static boolean sendAsSession;
    static boolean shareLocation = true;
    private static int subscribableStatus;
    private static OSObservable<OSSubscriptionObserver, OSSubscriptionStateChanges> subscriptionStateChangesObserver;
    public static ConcurrentLinkedQueue<Runnable> taskQueueWaitingForInit = new ConcurrentLinkedQueue();
    private static TrackAmazonPurchase trackAmazonPurchase;
    private static TrackFirebaseAnalytics trackFirebaseAnalytics;
    private static TrackGooglePurchase trackGooglePurchase;
    private static long unSentActiveTime = -1;
    private static Collection<JSONArray> unprocessedOpenedNotifis = new ArrayList();
    private static boolean useEmailAuth;
    private static String userId = null;
    private static LOG_LEVEL visualLogLevel = LOG_LEVEL.NONE;
    private static boolean waitingToPostStateSync;

    /* renamed from: com.onesignal.OneSignal$1 */
    static class C06691 implements ThreadFactory {
        C06691() {
        }

        public Thread newThread(@NonNull Runnable runnable) {
            Thread newThread = new Thread(runnable);
            newThread.setName("OS_PENDING_EXECUTOR_" + newThread.getId());
            return newThread;
        }
    }

    /* renamed from: com.onesignal.OneSignal$2 */
    static class C06712 implements LocationHandler {
        C06712() {
        }

        public CALLBACK_TYPE getType() {
            return CALLBACK_TYPE.STARTUP;
        }

        public void complete(LocationPoint point) {
            OneSignal.lastLocationPoint = point;
            OneSignal.locationFired = true;
            OneSignal.registerUser();
        }
    }

    /* renamed from: com.onesignal.OneSignal$3 */
    static class C06723 implements RegisteredHandler {
        C06723() {
        }

        public void complete(String id, int status) {
            if (status < 1) {
                if (OneSignalStateSynchronizer.getRegistrationId() == null && (OneSignal.subscribableStatus == 1 || OneSignal.subscribableStatus < -6)) {
                    OneSignal.subscribableStatus = status;
                }
            } else if (OneSignal.subscribableStatus < -6) {
                OneSignal.subscribableStatus = status;
            }
            OneSignal.lastRegistrationId = id;
            OneSignal.registerForPushFired = true;
            OneSignal.getCurrentSubscriptionState(OneSignal.appContext).setPushToken(id);
            OneSignal.registerUser();
        }
    }

    /* renamed from: com.onesignal.OneSignal$4 */
    static class C06744 extends ResponseHandler {

        /* renamed from: com.onesignal.OneSignal$4$1 */
        class C06731 implements Runnable {
            C06731() {
            }

            public void run() {
                try {
                    int sleepTime = (OneSignal.androidParamsReties * 10000) + DefaultLoadControl.DEFAULT_MAX_BUFFER_MS;
                    if (sleepTime > 90000) {
                        sleepTime = 90000;
                    }
                    OneSignal.Log(LOG_LEVEL.INFO, "Failed to get Android parameters, trying again in " + (sleepTime / 1000) + " seconds.");
                    Thread.sleep((long) sleepTime);
                } catch (Throwable th) {
                }
                OneSignal.access$1208();
                OneSignal.makeAndroidParamsRequest();
            }
        }

        C06744() {
        }

        void onFailure(int statusCode, String response, Throwable throwable) {
            new Thread(new C06731(), "OS_PARAMS_REQUEST").start();
        }

        void onSuccess(String response) {
            try {
                JSONObject responseJson = new JSONObject(response);
                if (responseJson.has("android_sender_id")) {
                    OneSignal.mGoogleProjectNumberIsRemote = true;
                    OneSignal.mGoogleProjectNumber = responseJson.getString("android_sender_id");
                }
                OneSignal.mEnterp = responseJson.optBoolean("enterp", false);
                OneSignal.useEmailAuth = responseJson.optBoolean("use_email_auth", false);
                OneSignal.awl = responseJson.getJSONObject("awl_list");
                OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, "GT_FIREBASE_TRACKING_ENABLED", responseJson.optBoolean("fba", false));
                NotificationChannelManager.processChannelList(OneSignal.appContext, responseJson);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            OneSignal.awlFired = true;
            OneSignal.registerForPushToken();
        }
    }

    /* renamed from: com.onesignal.OneSignal$6 */
    static class C06766 extends ResponseHandler {
        C06766() {
        }

        void onFailure(int statusCode, String response, Throwable throwable) {
            OneSignal.logHttpError("sending on_focus Failed", statusCode, throwable, response);
        }

        void onSuccess(String response) {
            OneSignal.SaveUnsentActiveTime(0);
        }
    }

    /* renamed from: com.onesignal.OneSignal$7 */
    static class C06777 implements Runnable {
        C06777() {
        }

        public void run() {
            try {
                OneSignal.registerUserTask();
                OneSignalChromeTab.setup(OneSignal.appContext, OneSignal.appId, OneSignal.userId, AdvertisingIdProviderGPS.getLastValue());
            } catch (JSONException t) {
                OneSignal.Log(LOG_LEVEL.FATAL, "FATAL Error registering device!", t);
            }
        }
    }

    public static class Builder {
        Context mContext;
        boolean mDisableGmsMissingPrompt;
        OSInFocusDisplayOption mDisplayOption;
        boolean mDisplayOptionCarryOver;
        boolean mFilterOtherGCMReceivers;
        NotificationOpenedHandler mNotificationOpenedHandler;
        NotificationReceivedHandler mNotificationReceivedHandler;
        boolean mPromptLocation;
        boolean mUnsubscribeWhenNotificationsAreDisabled;

        private Builder() {
            this.mDisplayOption = OSInFocusDisplayOption.InAppAlert;
        }

        private Builder(Context context) {
            this.mDisplayOption = OSInFocusDisplayOption.InAppAlert;
            this.mContext = context;
        }

        private void setDisplayOptionCarryOver(boolean carryOver) {
            this.mDisplayOptionCarryOver = carryOver;
        }

        public Builder setNotificationOpenedHandler(NotificationOpenedHandler handler) {
            this.mNotificationOpenedHandler = handler;
            return this;
        }

        public Builder setNotificationReceivedHandler(NotificationReceivedHandler handler) {
            this.mNotificationReceivedHandler = handler;
            return this;
        }

        public Builder autoPromptLocation(boolean enable) {
            this.mPromptLocation = enable;
            return this;
        }

        public Builder disableGmsMissingPrompt(boolean disable) {
            this.mDisableGmsMissingPrompt = disable;
            return this;
        }

        public Builder inFocusDisplaying(OSInFocusDisplayOption displayOption) {
            OneSignal.getCurrentOrNewInitBuilder().mDisplayOptionCarryOver = false;
            this.mDisplayOption = displayOption;
            return this;
        }

        public Builder unsubscribeWhenNotificationsAreDisabled(boolean set) {
            this.mUnsubscribeWhenNotificationsAreDisabled = set;
            return this;
        }

        public Builder filterOtherGCMReceivers(boolean set) {
            this.mFilterOtherGCMReceivers = set;
            return this;
        }

        public void init() {
            OneSignal.init(this);
        }
    }

    public enum EmailErrorType {
        VALIDATION,
        REQUIRES_EMAIL_AUTH,
        INVALID_OPERATION,
        NETWORK
    }

    public static class EmailUpdateError {
        private String message;
        private EmailErrorType type;

        EmailUpdateError(EmailErrorType type, String message) {
            this.type = type;
            this.message = message;
        }

        public EmailErrorType getType() {
            return this.type;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public interface EmailUpdateHandler {
        void onFailure(EmailUpdateError emailUpdateError);

        void onSuccess();
    }

    public interface GetTagsHandler {
        void tagsAvailable(JSONObject jSONObject);
    }

    private static class IAPUpdateJob {
        boolean newAsExisting;
        ResponseHandler restResponseHandler;
        JSONArray toReport;

        IAPUpdateJob(JSONArray toReport) {
            this.toReport = toReport;
        }
    }

    public interface IdsAvailableHandler {
        void idsAvailable(String str, String str2);
    }

    public enum LOG_LEVEL {
        NONE,
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        VERBOSE
    }

    public interface NotificationOpenedHandler {
        void notificationOpened(OSNotificationOpenResult oSNotificationOpenResult);
    }

    public interface NotificationReceivedHandler {
        void notificationReceived(OSNotification oSNotification);
    }

    public enum OSInFocusDisplayOption {
        None,
        InAppAlert,
        Notification
    }

    private static class PendingTaskRunnable implements Runnable {
        private Runnable innerTask;
        private long taskId;

        PendingTaskRunnable(Runnable innerTask) {
            this.innerTask = innerTask;
        }

        public void run() {
            this.innerTask.run();
            OneSignal.onTaskRan(this.taskId);
        }
    }

    public interface PostNotificationResponseHandler {
        void onFailure(JSONObject jSONObject);

        void onSuccess(JSONObject jSONObject);
    }

    static /* synthetic */ int access$1208() {
        int i = androidParamsReties;
        androidParamsReties = i + 1;
        return i;
    }

    private static OSPermissionState getCurrentPermissionState(Context context) {
        if (context == null) {
            return null;
        }
        if (currentPermissionState == null) {
            currentPermissionState = new OSPermissionState(false);
            currentPermissionState.observable.addObserverStrong(new OSPermissionChangedInternalObserver());
        }
        return currentPermissionState;
    }

    private static OSPermissionState getLastPermissionState(Context context) {
        if (context == null) {
            return null;
        }
        if (lastPermissionState == null) {
            lastPermissionState = new OSPermissionState(true);
        }
        return lastPermissionState;
    }

    static OSObservable<OSPermissionObserver, OSPermissionStateChanges> getPermissionStateChangesObserver() {
        if (permissionStateChangesObserver == null) {
            permissionStateChangesObserver = new OSObservable("onOSPermissionChanged", true);
        }
        return permissionStateChangesObserver;
    }

    private static OSSubscriptionState getCurrentSubscriptionState(Context context) {
        if (context == null) {
            return null;
        }
        if (currentSubscriptionState == null) {
            currentSubscriptionState = new OSSubscriptionState(false, getCurrentPermissionState(context).getEnabled());
            getCurrentPermissionState(context).observable.addObserver(currentSubscriptionState);
            currentSubscriptionState.observable.addObserverStrong(new OSSubscriptionChangedInternalObserver());
        }
        return currentSubscriptionState;
    }

    private static OSSubscriptionState getLastSubscriptionState(Context context) {
        if (context == null) {
            return null;
        }
        if (lastSubscriptionState == null) {
            lastSubscriptionState = new OSSubscriptionState(true, false);
        }
        return lastSubscriptionState;
    }

    static OSObservable<OSSubscriptionObserver, OSSubscriptionStateChanges> getSubscriptionStateChangesObserver() {
        if (subscriptionStateChangesObserver == null) {
            subscriptionStateChangesObserver = new OSObservable("onOSSubscriptionChanged", true);
        }
        return subscriptionStateChangesObserver;
    }

    private static OSEmailSubscriptionState getCurrentEmailSubscriptionState(Context context) {
        if (context == null) {
            return null;
        }
        if (currentEmailSubscriptionState == null) {
            currentEmailSubscriptionState = new OSEmailSubscriptionState(false);
            currentEmailSubscriptionState.observable.addObserverStrong(new OSEmailSubscriptionChangedInternalObserver());
        }
        return currentEmailSubscriptionState;
    }

    private static OSEmailSubscriptionState getLastEmailSubscriptionState(Context context) {
        if (context == null) {
            return null;
        }
        if (lastEmailSubscriptionState == null) {
            lastEmailSubscriptionState = new OSEmailSubscriptionState(true);
        }
        return lastEmailSubscriptionState;
    }

    static OSObservable<OSEmailSubscriptionObserver, OSEmailSubscriptionStateChanges> getEmailSubscriptionStateChangesObserver() {
        if (emailSubscriptionStateChangesObserver == null) {
            emailSubscriptionStateChangesObserver = new OSObservable("onOSEmailSubscriptionChanged", true);
        }
        return emailSubscriptionStateChangesObserver;
    }

    public static Builder getCurrentOrNewInitBuilder() {
        if (mInitBuilder == null) {
            mInitBuilder = new Builder();
        }
        return mInitBuilder;
    }

    public static Builder startInit(Context context) {
        return new Builder(context);
    }

    private static void init(Builder inBuilder) {
        if (getCurrentOrNewInitBuilder().mDisplayOptionCarryOver) {
            inBuilder.mDisplayOption = getCurrentOrNewInitBuilder().mDisplayOption;
        }
        mInitBuilder = inBuilder;
        Context context = mInitBuilder.mContext;
        mInitBuilder.mContext = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            String sender_id = bundle.getString("onesignal_google_project_number");
            if (sender_id != null && sender_id.length() > 4) {
                sender_id = sender_id.substring(4);
            }
            init(context, sender_id, bundle.getString("onesignal_app_id"), mInitBuilder.mNotificationOpenedHandler, mInitBuilder.mNotificationReceivedHandler);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void init(Context context, String googleProjectNumber, String oneSignalAppId) {
        init(context, googleProjectNumber, oneSignalAppId, null, null);
    }

    public static void init(Context context, String googleProjectNumber, String oneSignalAppId, NotificationOpenedHandler notificationOpenedHandler) {
        init(context, googleProjectNumber, oneSignalAppId, notificationOpenedHandler, null);
    }

    public static void init(Context context, String googleProjectNumber, String oneSignalAppId, NotificationOpenedHandler notificationOpenedHandler, NotificationReceivedHandler notificationReceivedHandler) {
        mInitBuilder = getCurrentOrNewInitBuilder();
        mInitBuilder.mDisplayOptionCarryOver = false;
        mInitBuilder.mNotificationOpenedHandler = notificationOpenedHandler;
        mInitBuilder.mNotificationReceivedHandler = notificationReceivedHandler;
        if (!mGoogleProjectNumberIsRemote) {
            mGoogleProjectNumber = googleProjectNumber;
        }
        osUtils = new OSUtils();
        deviceType = osUtils.getDeviceType();
        subscribableStatus = osUtils.initializationChecker(context, deviceType, oneSignalAppId);
        if (subscribableStatus != -999) {
            if (initDone) {
                if (context != null) {
                    appContext = context.getApplicationContext();
                }
                if (mInitBuilder.mNotificationOpenedHandler != null) {
                    fireCallbackForOpenedNotifications();
                    return;
                }
                return;
            }
            boolean contextIsActivity = context instanceof Activity;
            foreground = contextIsActivity;
            appId = oneSignalAppId;
            appContext = context.getApplicationContext();
            saveFilterOtherGCMReceivers(mInitBuilder.mFilterOtherGCMReceivers);
            if (contextIsActivity) {
                ActivityLifecycleHandler.curActivity = (Activity) context;
                NotificationRestorer.asyncRestore(appContext);
            } else {
                ActivityLifecycleHandler.nextResumeIsFirstActivity = true;
            }
            lastTrackedFocusTime = SystemClock.elapsedRealtime();
            OneSignalStateSynchronizer.initUserState();
            ((Application) appContext).registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
            try {
                Class.forName("com.amazon.device.iap.PurchasingListener");
                trackAmazonPurchase = new TrackAmazonPurchase(appContext);
            } catch (ClassNotFoundException e) {
            }
            String oldAppId = getSavedAppId();
            if (oldAppId == null) {
                BadgeCountUpdater.updateCount(0, appContext);
                SaveAppId(appId);
            } else if (!oldAppId.equals(appId)) {
                Log(LOG_LEVEL.DEBUG, "APP ID changed, clearing user id as it is no longer valid.");
                SaveAppId(appId);
                OneSignalStateSynchronizer.resetCurrentState();
            }
            OSPermissionChangedInternalObserver.handleInternalChanges(getCurrentPermissionState(appContext));
            if (foreground || getUserId() == null) {
                sendAsSession = isPastOnSessionTime();
                setLastSessionTime(System.currentTimeMillis());
                startRegistrationOrOnSession();
            }
            if (mInitBuilder.mNotificationOpenedHandler != null) {
                fireCallbackForOpenedNotifications();
            }
            if (TrackGooglePurchase.CanTrack(appContext)) {
                trackGooglePurchase = new TrackGooglePurchase(appContext);
            }
            if (TrackFirebaseAnalytics.CanTrack()) {
                trackFirebaseAnalytics = new TrackFirebaseAnalytics(appContext);
            }
            initDone = true;
            startPendingTasks();
        }
    }

    private static void onTaskRan(long taskId) {
        if (lastTaskId.get() == taskId) {
            Log(LOG_LEVEL.INFO, "Last Pending Task has ran, shutting down");
            pendingTaskExecutor.shutdown();
        }
    }

    private static void startPendingTasks() {
        if (!taskQueueWaitingForInit.isEmpty()) {
            pendingTaskExecutor = Executors.newSingleThreadExecutor(new C06691());
            while (!taskQueueWaitingForInit.isEmpty()) {
                pendingTaskExecutor.submit((Runnable) taskQueueWaitingForInit.poll());
            }
        }
    }

    private static void addTaskToQueue(PendingTaskRunnable task) {
        task.taskId = lastTaskId.incrementAndGet();
        if (pendingTaskExecutor == null) {
            Log(LOG_LEVEL.INFO, "Adding a task to the pending queue with ID: " + task.taskId);
            taskQueueWaitingForInit.add(task);
        } else if (!pendingTaskExecutor.isShutdown()) {
            Log(LOG_LEVEL.INFO, "Executor is still running, add to the executor with ID: " + task.taskId);
            pendingTaskExecutor.submit(task);
        }
    }

    private static boolean shouldRunTaskThroughQueue() {
        if (initDone && pendingTaskExecutor == null) {
            return false;
        }
        if (!initDone && pendingTaskExecutor == null) {
            return true;
        }
        if (pendingTaskExecutor == null || pendingTaskExecutor.isShutdown()) {
            return false;
        }
        return true;
    }

    private static void startRegistrationOrOnSession() {
        boolean z = false;
        if (!waitingToPostStateSync) {
            waitingToPostStateSync = true;
            registerForPushFired = false;
            if (sendAsSession) {
                locationFired = false;
            }
            startLocationUpdate();
            makeAndroidParamsRequest();
            if (promptedLocation || mInitBuilder.mPromptLocation) {
                z = true;
            }
            promptedLocation = z;
        }
    }

    private static void startLocationUpdate() {
        LocationHandler locationHandler = new C06712();
        boolean doPrompt = mInitBuilder.mPromptLocation && !promptedLocation;
        LocationGMS.getLocation(appContext, doPrompt, locationHandler);
    }

    private static void registerForPushToken() {
        PushRegistrator pushRegistrator;
        if (deviceType == 2) {
            pushRegistrator = new PushRegistratorADM();
        } else {
            pushRegistrator = new PushRegistratorGPS();
        }
        pushRegistrator.registerForPush(appContext, mGoogleProjectNumber, new C06723());
    }

    private static void makeAndroidParamsRequest() {
        if (awlFired) {
            registerForPushToken();
            return;
        }
        ResponseHandler responseHandler = new C06744();
        String awl_url = "apps/" + appId + "/android_params.js";
        String userId = getUserId();
        if (userId != null) {
            awl_url = awl_url + "?player_id=" + userId;
        }
        Log(LOG_LEVEL.DEBUG, "Starting request to get Android parameters.");
        OneSignalRestClient.get(awl_url, responseHandler);
    }

    private static void fireCallbackForOpenedNotifications() {
        for (JSONArray dataArray : unprocessedOpenedNotifis) {
            runNotificationOpenedCallback(dataArray, true, false);
        }
        unprocessedOpenedNotifis.clear();
    }

    public static void setLogLevel(LOG_LEVEL inLogCatLevel, LOG_LEVEL inVisualLogLevel) {
        logCatLevel = inLogCatLevel;
        visualLogLevel = inVisualLogLevel;
    }

    public static void setLogLevel(int inLogCatLevel, int inVisualLogLevel) {
        setLogLevel(getLogLevel(inLogCatLevel), getLogLevel(inVisualLogLevel));
    }

    private static LOG_LEVEL getLogLevel(int level) {
        switch (level) {
            case 0:
                return LOG_LEVEL.NONE;
            case 1:
                return LOG_LEVEL.FATAL;
            case 2:
                return LOG_LEVEL.ERROR;
            case 3:
                return LOG_LEVEL.WARN;
            case 4:
                return LOG_LEVEL.INFO;
            case 5:
                return LOG_LEVEL.DEBUG;
            case 6:
                return LOG_LEVEL.VERBOSE;
            default:
                if (level < 0) {
                    return LOG_LEVEL.NONE;
                }
                return LOG_LEVEL.VERBOSE;
        }
    }

    private static boolean atLogLevel(LOG_LEVEL level) {
        return level.compareTo(visualLogLevel) < 1 || level.compareTo(logCatLevel) < 1;
    }

    static void Log(LOG_LEVEL level, String message) {
        Log(level, message, null);
    }

    static void Log(final LOG_LEVEL level, String message, Throwable throwable) {
        String TAG = "OneSignal";
        if (level.compareTo(logCatLevel) < 1) {
            if (level == LOG_LEVEL.VERBOSE) {
                Log.v("OneSignal", message, throwable);
            } else if (level == LOG_LEVEL.DEBUG) {
                Log.d("OneSignal", message, throwable);
            } else if (level == LOG_LEVEL.INFO) {
                Log.i("OneSignal", message, throwable);
            } else if (level == LOG_LEVEL.WARN) {
                Log.w("OneSignal", message, throwable);
            } else if (level == LOG_LEVEL.ERROR || level == LOG_LEVEL.FATAL) {
                Log.e("OneSignal", message, throwable);
            }
        }
        if (level.compareTo(visualLogLevel) < 1 && ActivityLifecycleHandler.curActivity != null) {
            try {
                String fullMessage = message + LogCollector.LINE_SEPARATOR;
                if (throwable != null) {
                    fullMessage = fullMessage + throwable.getMessage();
                    StringWriter sw = new StringWriter();
                    throwable.printStackTrace(new PrintWriter(sw));
                    fullMessage = fullMessage + sw.toString();
                }
                final String finalFullMessage = fullMessage;
                OSUtils.runOnMainUIThread(new Runnable() {
                    public void run() {
                        if (ActivityLifecycleHandler.curActivity != null) {
                            new android.app.AlertDialog.Builder(ActivityLifecycleHandler.curActivity).setTitle(level.toString()).setMessage(finalFullMessage).show();
                        }
                    }
                });
            } catch (Throwable t) {
                Log.e("OneSignal", "Error showing logging message.", t);
            }
        }
    }

    private static void logHttpError(String errorString, int statusCode, Throwable throwable, String errorResponse) {
        String jsonError = "";
        if (errorResponse != null && atLogLevel(LOG_LEVEL.INFO)) {
            jsonError = LogCollector.LINE_SEPARATOR + errorResponse + LogCollector.LINE_SEPARATOR;
        }
        Log(LOG_LEVEL.WARN, "HTTP code: " + statusCode + " " + errorString + jsonError, throwable);
    }

    @WorkerThread
    static boolean onAppLostFocus() {
        foreground = false;
        LocationGMS.onFocusChange();
        if (!initDone) {
            return false;
        }
        if (trackAmazonPurchase != null) {
            trackAmazonPurchase.checkListener();
        }
        if (lastTrackedFocusTime == -1) {
            return false;
        }
        long time_elapsed = (long) ((((double) (SystemClock.elapsedRealtime() - lastTrackedFocusTime)) / 1000.0d) + 0.5d);
        lastTrackedFocusTime = SystemClock.elapsedRealtime();
        if (time_elapsed < 0 || time_elapsed > 86400) {
            return false;
        }
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "Android Context not found, please call OneSignal.init when your app starts.");
            return false;
        }
        boolean scheduleSyncService = scheduleSyncService();
        setLastSessionTime(System.currentTimeMillis());
        long totalTimeActive = GetUnsentActiveTime() + time_elapsed;
        SaveUnsentActiveTime(totalTimeActive);
        if (totalTimeActive >= MIN_ON_FOCUS_TIME && getUserId() != null) {
            if (!scheduleSyncService) {
                OneSignalSyncServiceUtils.scheduleSyncTask(appContext);
            }
            OneSignalSyncServiceUtils.syncOnFocusTime();
            return false;
        } else if (totalTimeActive >= MIN_ON_FOCUS_TIME) {
            return true;
        } else {
            return false;
        }
    }

    static boolean scheduleSyncService() {
        boolean unsyncedChanges = OneSignalStateSynchronizer.persist();
        if (unsyncedChanges) {
            OneSignalSyncServiceUtils.scheduleSyncTask(appContext);
        }
        return LocationGMS.scheduleUpdate(appContext) || unsyncedChanges;
    }

    static void sendOnFocus(long totalTimeActive, boolean synchronous) {
        try {
            JSONObject jsonBody = new JSONObject().put("app_id", appId).put(Param.TYPE, 1).put("state", "ping").put("active_time", totalTimeActive);
            addNetType(jsonBody);
            sendOnFocusToPlayer(getUserId(), jsonBody, synchronous);
            String emailId = getEmailId();
            if (emailId != null) {
                sendOnFocusToPlayer(emailId, jsonBody, synchronous);
            }
        } catch (Throwable t) {
            Log(LOG_LEVEL.ERROR, "Generating on_focus:JSON Failed.", t);
        }
    }

    private static void sendOnFocusToPlayer(String userId, JSONObject jsonBody, boolean synchronous) {
        String url = "players/" + userId + "/on_focus";
        ResponseHandler responseHandler = new C06766();
        if (synchronous) {
            OneSignalRestClient.postSync(url, jsonBody, responseHandler);
        } else {
            OneSignalRestClient.post(url, jsonBody, responseHandler);
        }
    }

    static void onAppFocus() {
        foreground = true;
        LocationGMS.onFocusChange();
        lastTrackedFocusTime = SystemClock.elapsedRealtime();
        sendAsSession = isPastOnSessionTime();
        setLastSessionTime(System.currentTimeMillis());
        startRegistrationOrOnSession();
        if (trackGooglePurchase != null) {
            trackGooglePurchase.trackIAP();
        }
        NotificationRestorer.asyncRestore(appContext);
        getCurrentPermissionState(appContext).refreshAsTo();
        if (trackFirebaseAnalytics != null && getFirebaseAnalyticsEnabled(appContext)) {
            trackFirebaseAnalytics.trackInfluenceOpenEvent();
        }
        OneSignalSyncServiceUtils.cancelSyncTask(appContext);
    }

    static boolean isForeground() {
        return foreground;
    }

    private static void addNetType(JSONObject jsonObj) {
        try {
            jsonObj.put("net_type", osUtils.getNetType());
        } catch (Throwable th) {
        }
    }

    private static int getTimeZoneOffset() {
        TimeZone timezone = Calendar.getInstance().getTimeZone();
        int offset = timezone.getRawOffset();
        if (timezone.inDaylightTime(new Date())) {
            offset += timezone.getDSTSavings();
        }
        return offset / 1000;
    }

    private static void registerUser() {
        Log(LOG_LEVEL.DEBUG, "registerUser: registerForPushFired:" + registerForPushFired + ", locationFired: " + locationFired + ", awlFired: " + awlFired);
        if (registerForPushFired && locationFired && awlFired) {
            new Thread(new C06777(), "OS_REG_USER").start();
        }
    }

    private static void registerUserTask() throws JSONException {
        String packageName = appContext.getPackageName();
        PackageManager packageManager = appContext.getPackageManager();
        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("app_id", appId);
        String adId = mainAdIdProvider.getIdentifier(appContext);
        if (adId != null) {
            deviceInfo.put("ad_id", adId);
        }
        deviceInfo.put("device_os", VERSION.RELEASE);
        deviceInfo.put("timezone", getTimeZoneOffset());
        deviceInfo.put("language", OSUtils.getCorrectedLanguage());
        deviceInfo.put(CommonUtils.SDK, VERSION);
        deviceInfo.put("sdk_type", sdkType);
        deviceInfo.put("android_package", packageName);
        deviceInfo.put("device_model", Build.MODEL);
        try {
            deviceInfo.put("game_version", packageManager.getPackageInfo(packageName, 0).versionCode);
        } catch (NameNotFoundException e) {
        }
        try {
            List<PackageInfo> packList = packageManager.getInstalledPackages(0);
            JSONArray pkgs = new JSONArray();
            MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA256_INSTANCE);
            for (int i = 0; i < packList.size(); i++) {
                md.update(((PackageInfo) packList.get(i)).packageName.getBytes());
                String pck = Base64.encodeToString(md.digest(), 2);
                if (awl.has(pck)) {
                    pkgs.put(pck);
                }
            }
            deviceInfo.put("pkgs", pkgs);
        } catch (Throwable th) {
        }
        deviceInfo.put("net_type", osUtils.getNetType());
        deviceInfo.put("carrier", osUtils.getCarrierName());
        deviceInfo.put("rooted", RootToolsInternalMethods.isRooted());
        OneSignalStateSynchronizer.updateDeviceInfo(deviceInfo);
        JSONObject pushState = new JSONObject();
        pushState.put(SettingsJsonConstants.APP_IDENTIFIER_KEY, lastRegistrationId);
        pushState.put("subscribableStatus", subscribableStatus);
        pushState.put("androidPermission", areNotificationsEnabledForSubscribedState());
        pushState.put("device_type", deviceType);
        OneSignalStateSynchronizer.updatePushState(pushState);
        if (shareLocation && lastLocationPoint != null) {
            OneSignalStateSynchronizer.updateLocation(lastLocationPoint);
        }
        if (sendAsSession) {
            OneSignalStateSynchronizer.setSyncAsNewSession();
        }
        waitingToPostStateSync = false;
    }

    @Deprecated
    public static void syncHashedEmail(final String email) {
        if (OSUtils.isValidEmail(email)) {
            Runnable runSyncHashedEmail = new Runnable() {
                public void run() {
                    OneSignalStateSynchronizer.syncHashedEmail(email.trim().toLowerCase());
                }
            };
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "You should initialize OneSignal before calling syncHashedEmail! Moving this operation to a pending task queue.");
                addTaskToQueue(new PendingTaskRunnable(runSyncHashedEmail));
                return;
            }
            runSyncHashedEmail.run();
        }
    }

    public static void setEmail(@NonNull String email, EmailUpdateHandler callback) {
        setEmail(email, null, callback);
    }

    public static void setEmail(@NonNull String email) {
        setEmail(email, null, null);
    }

    public static void setEmail(@NonNull String email, @Nullable String emailAuthHash) {
        setEmail(email, emailAuthHash, null);
    }

    public static void setEmail(@NonNull final String email, @Nullable final String emailAuthHash, @Nullable EmailUpdateHandler callback) {
        String errorMessage;
        if (!OSUtils.isValidEmail(email)) {
            errorMessage = "Email is invalid";
            if (callback != null) {
                callback.onFailure(new EmailUpdateError(EmailErrorType.VALIDATION, errorMessage));
            }
            Log(LOG_LEVEL.ERROR, errorMessage);
        } else if (useEmailAuth && emailAuthHash == null) {
            errorMessage = "Email authentication (auth token) is set to REQUIRED for this application. Please provide an auth token from your backend server or change the setting in the OneSignal dashboard.";
            if (callback != null) {
                callback.onFailure(new EmailUpdateError(EmailErrorType.REQUIRES_EMAIL_AUTH, errorMessage));
            }
            Log(LOG_LEVEL.ERROR, errorMessage);
        } else {
            emailUpdateHandler = callback;
            Runnable runSetEmail = new Runnable() {
                public void run() {
                    String trimmedEmail = email.trim();
                    String internalEmailAuthHash = emailAuthHash;
                    if (internalEmailAuthHash != null) {
                        internalEmailAuthHash.toLowerCase();
                    }
                    OneSignal.getCurrentEmailSubscriptionState(OneSignal.appContext).setEmailAddress(trimmedEmail);
                    OneSignalStateSynchronizer.setEmail(trimmedEmail.toLowerCase(), internalEmailAuthHash);
                }
            };
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "You should initialize OneSignal before calling setEmail! Moving this operation to a pending task queue.");
                addTaskToQueue(new PendingTaskRunnable(runSetEmail));
                return;
            }
            runSetEmail.run();
        }
    }

    public static void logoutEmail() {
        logoutEmail(null);
    }

    public static void logoutEmail(@Nullable EmailUpdateHandler callback) {
        if (getEmailId() == null) {
            String message = "logoutEmail not valid as email was not set or already logged out!";
            if (callback != null) {
                callback.onFailure(new EmailUpdateError(EmailErrorType.INVALID_OPERATION, "logoutEmail not valid as email was not set or already logged out!"));
            }
            Log(LOG_LEVEL.ERROR, "logoutEmail not valid as email was not set or already logged out!");
            return;
        }
        emailLogoutHandler = callback;
        Runnable emailLogout = new Runnable() {
            public void run() {
                OneSignalStateSynchronizer.logoutEmail();
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "You should initialize OneSignal before calling logoutEmail! Moving this operation to a pending task queue.");
            addTaskToQueue(new PendingTaskRunnable(emailLogout));
            return;
        }
        emailLogout.run();
    }

    public static void sendTag(String key, String value) {
        try {
            sendTags(new JSONObject().put(key, value));
        } catch (JSONException t) {
            t.printStackTrace();
        }
    }

    public static void sendTags(String jsonString) {
        try {
            sendTags(new JSONObject(jsonString));
        } catch (JSONException t) {
            Log(LOG_LEVEL.ERROR, "Generating JSONObject for sendTags failed!", t);
        }
    }

    public static void sendTags(final JSONObject keyValues) {
        Runnable sendTagsRunnable = new Runnable() {
            public void run() {
                if (keyValues != null) {
                    JSONObject existingKeys = OneSignalStateSynchronizer.getTags(false).result;
                    JSONObject toSend = new JSONObject();
                    Iterator<String> keys = keyValues.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        try {
                            Object value = keyValues.opt(key);
                            if ((value instanceof JSONArray) || (value instanceof JSONObject)) {
                                OneSignal.Log(LOG_LEVEL.ERROR, "Omitting key '" + key + "'! sendTags DO NOT supported nested values!");
                            } else if (!keyValues.isNull(key) && !"".equals(value)) {
                                toSend.put(key, value.toString());
                            } else if (existingKeys != null && existingKeys.has(key)) {
                                toSend.put(key, "");
                            }
                        } catch (Throwable th) {
                        }
                    }
                    if (!toSend.toString().equals("{}")) {
                        OneSignalStateSynchronizer.sendTags(toSend);
                    }
                }
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "You must initialize OneSignal before modifying tags!Moving this operation to a pending task queue.");
            addTaskToQueue(new PendingTaskRunnable(sendTagsRunnable));
            return;
        }
        sendTagsRunnable.run();
    }

    public static void postNotification(String json, PostNotificationResponseHandler handler) {
        try {
            postNotification(new JSONObject(json), handler);
        } catch (JSONException e) {
            Log(LOG_LEVEL.ERROR, "Invalid postNotification JSON format: " + json);
        }
    }

    public static void postNotification(JSONObject json, final PostNotificationResponseHandler handler) {
        try {
            if (!json.has("app_id")) {
                json.put("app_id", getSavedAppId());
            }
            OneSignalRestClient.post("notifications/", json, new ResponseHandler() {
                public void onSuccess(String response) {
                    OneSignal.Log(LOG_LEVEL.DEBUG, "HTTP create notification success: " + (response != null ? response : "null"));
                    if (handler != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("errors")) {
                                handler.onFailure(jsonObject);
                            } else {
                                handler.onSuccess(new JSONObject(response));
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }

                void onFailure(int statusCode, String response, Throwable throwable) {
                    OneSignal.logHttpError("create notification failed", statusCode, throwable, response);
                    if (handler != null) {
                        if (statusCode == 0) {
                            try {
                                response = "{\"error\": \"HTTP no response error\"}";
                            } catch (Throwable th) {
                                try {
                                    handler.onFailure(new JSONObject("{\"error\": \"Unknown response!\"}"));
                                    return;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        }
                        handler.onFailure(new JSONObject(response));
                    }
                }
            });
        } catch (JSONException e) {
            Log(LOG_LEVEL.ERROR, "HTTP create notification json exception!", e);
            if (handler != null) {
                try {
                    handler.onFailure(new JSONObject("{'error': 'HTTP create notification json exception!'}"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void getTags(final GetTagsHandler getTagsHandler) {
        pendingGetTagsHandler = getTagsHandler;
        Runnable getTagsRunnable = new Runnable() {
            public void run() {
                if (getTagsHandler == null) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "getTagsHandler is null!");
                } else if (OneSignal.getUserId() != null) {
                    OneSignal.internalFireGetTagsCallback(OneSignal.pendingGetTagsHandler);
                }
            }
        };
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "You must initialize OneSignal before getting tags! Moving this tag operation to a pending queue.");
            taskQueueWaitingForInit.add(getTagsRunnable);
            return;
        }
        getTagsRunnable.run();
    }

    private static void internalFireGetTagsCallback(final GetTagsHandler getTagsHandler) {
        if (getTagsHandler != null) {
            new Thread(new Runnable() {
                public void run() {
                    GetTagsResult tags = OneSignalStateSynchronizer.getTags(!OneSignal.getTagsCall);
                    if (tags.serverSuccess) {
                        OneSignal.getTagsCall = true;
                    }
                    if (tags.result == null || tags.toString().equals("{}")) {
                        getTagsHandler.tagsAvailable(null);
                    } else {
                        getTagsHandler.tagsAvailable(tags.result);
                    }
                }
            }, "OS_GETTAGS_CALLBACK").start();
        }
    }

    public static void deleteTag(String key) {
        Collection tempList = new ArrayList(1);
        tempList.add(key);
        deleteTags(tempList);
    }

    public static void deleteTags(Collection<String> keys) {
        try {
            JSONObject jsonTags = new JSONObject();
            for (String key : keys) {
                jsonTags.put(key, "");
            }
            sendTags(jsonTags);
        } catch (Throwable t) {
            Log(LOG_LEVEL.ERROR, "Failed to generate JSON for deleteTags.", t);
        }
    }

    public static void deleteTags(String jsonArrayString) {
        try {
            JSONObject jsonTags = new JSONObject();
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonTags.put(jsonArray.getString(i), "");
            }
            sendTags(jsonTags);
        } catch (Throwable t) {
            Log(LOG_LEVEL.ERROR, "Failed to generate JSON for deleteTags.", t);
        }
    }

    public static void idsAvailable(IdsAvailableHandler inIdsAvailableHandler) {
        idsAvailableHandler = inIdsAvailableHandler;
        Runnable runIdsAvailable = new Runnable() {

            /* renamed from: com.onesignal.OneSignal$15$1 */
            class C06681 implements Runnable {
                C06681() {
                }

                public void run() {
                    OneSignal.internalFireIdsAvailableCallback();
                }
            }

            public void run() {
                if (OneSignal.getUserId() != null) {
                    OSUtils.runOnMainUIThread(new C06681());
                }
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "You must initialize OneSignal before getting tags! Moving this tag operation to a pending queue.");
            addTaskToQueue(new PendingTaskRunnable(runIdsAvailable));
            return;
        }
        runIdsAvailable.run();
    }

    private static void fireIdsAvailableCallback() {
        if (idsAvailableHandler != null) {
            OSUtils.runOnMainUIThread(new Runnable() {
                public void run() {
                    OneSignal.internalFireIdsAvailableCallback();
                }
            });
        }
    }

    private static synchronized void internalFireIdsAvailableCallback() {
        synchronized (OneSignal.class) {
            if (idsAvailableHandler != null) {
                String regId = OneSignalStateSynchronizer.getRegistrationId();
                if (!OneSignalStateSynchronizer.getSubscribed()) {
                    regId = null;
                }
                String userId = getUserId();
                if (userId != null) {
                    idsAvailableHandler.idsAvailable(userId, regId);
                    if (regId != null) {
                        idsAvailableHandler = null;
                    }
                }
            }
        }
    }

    static void sendPurchases(JSONArray purchases, boolean newAsExisting, ResponseHandler responseHandler) {
        if (getUserId() == null) {
            iapUpdateJob = new IAPUpdateJob(purchases);
            iapUpdateJob.newAsExisting = newAsExisting;
            iapUpdateJob.restResponseHandler = responseHandler;
            return;
        }
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("app_id", appId);
            if (newAsExisting) {
                jsonBody.put("existing", true);
            }
            jsonBody.put("purchases", purchases);
            OneSignalRestClient.post("players/" + getUserId() + "/on_purchase", jsonBody, responseHandler);
            if (getEmailId() != null) {
                OneSignalRestClient.post("players/" + getEmailId() + "/on_purchase", jsonBody, null);
            }
        } catch (Throwable t) {
            Log(LOG_LEVEL.ERROR, "Failed to generate JSON for sendPurchases.", t);
        }
    }

    private static boolean openURLFromNotification(Context context, JSONArray dataArray) {
        int jsonArraySize = dataArray.length();
        boolean urlOpened = false;
        for (int i = 0; i < jsonArraySize; i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                if (data.has("custom")) {
                    JSONObject customJSON = new JSONObject(data.optString("custom"));
                    if (customJSON.has("u")) {
                        String url = customJSON.optString("u", null);
                        if (!url.contains("://")) {
                            url = "http://" + url;
                        }
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url.trim()));
                        intent.addFlags(1476919296);
                        context.startActivity(intent);
                        urlOpened = true;
                    }
                }
            } catch (Throwable t) {
                Log(LOG_LEVEL.ERROR, "Error parsing JSON item " + i + "/" + jsonArraySize + " for launching a web URL.", t);
            }
        }
        return urlOpened;
    }

    private static void runNotificationOpenedCallback(JSONArray dataArray, boolean shown, boolean fromAlert) {
        if (mInitBuilder == null || mInitBuilder.mNotificationOpenedHandler == null) {
            unprocessedOpenedNotifis.add(dataArray);
        } else {
            fireNotificationOpenedHandler(generateOsNotificationOpenResult(dataArray, shown, fromAlert));
        }
    }

    @NonNull
    private static OSNotificationOpenResult generateOsNotificationOpenResult(JSONArray dataArray, boolean shown, boolean fromAlert) {
        int jsonArraySize = dataArray.length();
        boolean firstMessage = true;
        OSNotificationOpenResult openResult = new OSNotificationOpenResult();
        OSNotification notification = new OSNotification();
        notification.isAppInFocus = isAppActive();
        notification.shown = shown;
        notification.androidNotificationId = dataArray.optJSONObject(0).optInt("notificationId");
        String actionSelected = null;
        for (int i = 0; i < jsonArraySize; i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                notification.payload = NotificationBundleProcessor.OSNotificationPayloadFrom(data);
                if (actionSelected == null && data.has("actionSelected")) {
                    actionSelected = data.optString("actionSelected", null);
                }
                if (firstMessage) {
                    firstMessage = false;
                } else {
                    if (notification.groupedNotifications == null) {
                        notification.groupedNotifications = new ArrayList();
                    }
                    notification.groupedNotifications.add(notification.payload);
                }
            } catch (Throwable t) {
                Log(LOG_LEVEL.ERROR, "Error parsing JSON item " + i + "/" + jsonArraySize + " for callback.", t);
            }
        }
        openResult.notification = notification;
        openResult.action = new OSNotificationAction();
        openResult.action.actionID = actionSelected;
        openResult.action.type = actionSelected != null ? ActionType.ActionTaken : ActionType.Opened;
        if (fromAlert) {
            openResult.notification.displayType = DisplayType.InAppAlert;
        } else {
            openResult.notification.displayType = DisplayType.Notification;
        }
        return openResult;
    }

    private static void fireNotificationOpenedHandler(final OSNotificationOpenResult openedResult) {
        OSUtils.runOnMainUIThread(new Runnable() {
            public void run() {
                OneSignal.mInitBuilder.mNotificationOpenedHandler.notificationOpened(openedResult);
            }
        });
    }

    static void handleNotificationReceived(JSONArray data, boolean displayed, boolean fromAlert) {
        OSNotificationOpenResult openResult = generateOsNotificationOpenResult(data, displayed, fromAlert);
        if (trackFirebaseAnalytics != null && getFirebaseAnalyticsEnabled(appContext)) {
            trackFirebaseAnalytics.trackReceivedEvent(openResult);
        }
        if (mInitBuilder != null && mInitBuilder.mNotificationReceivedHandler != null) {
            mInitBuilder.mNotificationReceivedHandler.notificationReceived(openResult.notification);
        }
    }

    public static void handleNotificationOpen(Context inContext, JSONArray data, boolean fromAlert) {
        notificationOpenedRESTCall(inContext, data);
        if (trackFirebaseAnalytics != null && getFirebaseAnalyticsEnabled(appContext)) {
            trackFirebaseAnalytics.trackOpenedEvent(generateOsNotificationOpenResult(data, true, fromAlert));
        }
        boolean urlOpened = false;
        boolean defaultOpenActionDisabled = "DISABLE".equals(OSUtils.getManifestMeta(inContext, "com.onesignal.NotificationOpened.DEFAULT"));
        if (!defaultOpenActionDisabled) {
            urlOpened = openURLFromNotification(inContext, data);
        }
        runNotificationOpenedCallback(data, true, fromAlert);
        if (!fromAlert && !urlOpened && !defaultOpenActionDisabled) {
            fireIntentFromNotificationOpen(inContext);
        }
    }

    private static void fireIntentFromNotificationOpen(Context inContext) {
        Intent launchIntent = inContext.getPackageManager().getLaunchIntentForPackage(inContext.getPackageName());
        if (launchIntent != null) {
            launchIntent.setFlags(268566528);
            inContext.startActivity(launchIntent);
        }
    }

    private static void notificationOpenedRESTCall(Context inContext, JSONArray dataArray) {
        for (int i = 0; i < dataArray.length(); i++) {
            try {
                String notificationId = new JSONObject(dataArray.getJSONObject(i).optString("custom", null)).optString("i", null);
                if (!postedOpenedNotifIds.contains(notificationId)) {
                    postedOpenedNotifIds.add(notificationId);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("app_id", getSavedAppId(inContext));
                    jsonBody.put("player_id", getSavedUserId(inContext));
                    jsonBody.put(NotificationTable.COLUMN_NAME_OPENED, true);
                    OneSignalRestClient.put("notifications/" + notificationId, jsonBody, new ResponseHandler() {
                        void onFailure(int statusCode, String response, Throwable throwable) {
                            OneSignal.logHttpError("sending Notification Opened Failed", statusCode, throwable, response);
                        }
                    });
                }
            } catch (Throwable t) {
                Log(LOG_LEVEL.ERROR, "Failed to generate JSON to send notification opened.", t);
            }
        }
    }

    private static void SaveAppId(String appId) {
        if (appContext != null) {
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "GT_APP_ID", appId);
        }
    }

    static String getSavedAppId() {
        return getSavedAppId(appContext);
    }

    private static String getSavedAppId(Context inContext) {
        if (inContext == null) {
            return "";
        }
        return OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "GT_APP_ID", null);
    }

    private static String getSavedUserId(Context inContext) {
        if (inContext == null) {
            return "";
        }
        return OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "GT_PLAYER_ID", null);
    }

    static String getUserId() {
        if (userId == null && appContext != null) {
            userId = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "GT_PLAYER_ID", null);
        }
        return userId;
    }

    static void saveUserId(String id) {
        userId = id;
        if (appContext != null) {
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "GT_PLAYER_ID", userId);
        }
    }

    static String getEmailId() {
        if ("".equals(emailId)) {
            return null;
        }
        if (emailId == null && appContext != null) {
            emailId = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "OS_EMAIL_ID", null);
        }
        return emailId;
    }

    static void saveEmailId(String id) {
        emailId = id;
        if (appContext != null) {
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "OS_EMAIL_ID", "".equals(emailId) ? null : emailId);
        }
    }

    static boolean getFilterOtherGCMReceivers(Context context) {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, "OS_FILTER_OTHER_GCM_RECEIVERS", false);
    }

    static void saveFilterOtherGCMReceivers(boolean set) {
        if (appContext != null) {
            OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, "OS_FILTER_OTHER_GCM_RECEIVERS", set);
        }
    }

    static void updateUserIdDependents(String userId) {
        saveUserId(userId);
        fireIdsAvailableCallback();
        internalFireGetTagsCallback(pendingGetTagsHandler);
        getCurrentSubscriptionState(appContext).setUserId(userId);
        if (iapUpdateJob != null) {
            sendPurchases(iapUpdateJob.toReport, iapUpdateJob.newAsExisting, iapUpdateJob.restResponseHandler);
            iapUpdateJob = null;
        }
        OneSignalStateSynchronizer.refreshEmailState();
        OneSignalChromeTab.setup(appContext, appId, userId, AdvertisingIdProviderGPS.getLastValue());
    }

    static void updateEmailIdDependents(String emailId) {
        saveEmailId(emailId);
        getCurrentEmailSubscriptionState(appContext).setEmailUserId(emailId);
        try {
            OneSignalStateSynchronizer.updatePushState(new JSONObject().put("parent_player_id", emailId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static boolean getFirebaseAnalyticsEnabled(Context context) {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, "GT_FIREBASE_TRACKING_ENABLED", false);
    }

    public static void enableVibrate(boolean enable) {
        if (appContext != null) {
            OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, "GT_VIBRATE_ENABLED", enable);
        }
    }

    static boolean getVibrate(Context context) {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, "GT_VIBRATE_ENABLED", true);
    }

    public static void enableSound(boolean enable) {
        if (appContext != null) {
            OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, "GT_SOUND_ENABLED", enable);
        }
    }

    static boolean getSoundEnabled(Context context) {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, "GT_SOUND_ENABLED", true);
    }

    static void setLastSessionTime(long time) {
        OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, "OS_LAST_SESSION_TIME", time);
    }

    private static long getLastSessionTime(Context context) {
        return OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, "OS_LAST_SESSION_TIME", -31000);
    }

    public static void setInFocusDisplaying(OSInFocusDisplayOption displayOption) {
        getCurrentOrNewInitBuilder().mDisplayOptionCarryOver = true;
        getCurrentOrNewInitBuilder().mDisplayOption = displayOption;
    }

    public static void setInFocusDisplaying(int displayOption) {
        setInFocusDisplaying(getInFocusDisplaying(displayOption));
    }

    private static OSInFocusDisplayOption getInFocusDisplaying(int displayOption) {
        switch (displayOption) {
            case 0:
                return OSInFocusDisplayOption.None;
            case 1:
                return OSInFocusDisplayOption.InAppAlert;
            case 2:
                return OSInFocusDisplayOption.Notification;
            default:
                if (displayOption < 0) {
                    return OSInFocusDisplayOption.None;
                }
                return OSInFocusDisplayOption.Notification;
        }
    }

    static boolean getNotificationsWhenActiveEnabled() {
        if (mInitBuilder == null || mInitBuilder.mDisplayOption == OSInFocusDisplayOption.Notification) {
            return true;
        }
        return false;
    }

    static boolean getInAppAlertNotificationEnabled() {
        if (mInitBuilder != null && mInitBuilder.mDisplayOption == OSInFocusDisplayOption.InAppAlert) {
            return true;
        }
        return false;
    }

    public static void setSubscription(final boolean enable) {
        Runnable runSetSubscription = new Runnable() {
            public void run() {
                OneSignal.getCurrentSubscriptionState(OneSignal.appContext).setUserSubscriptionSetting(enable);
                OneSignalStateSynchronizer.setSubscription(enable);
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Moving subscription action to a waiting task queue.");
            addTaskToQueue(new PendingTaskRunnable(runSetSubscription));
            return;
        }
        runSetSubscription.run();
    }

    public static void setLocationShared(boolean enable) {
        shareLocation = enable;
        if (!enable) {
            OneSignalStateSynchronizer.clearLocation();
        }
        Log(LOG_LEVEL.DEBUG, "shareLocation:" + shareLocation);
    }

    public static void promptLocation() {
        Runnable runPromptLocation = new Runnable() {

            /* renamed from: com.onesignal.OneSignal$20$1 */
            class C06701 implements LocationHandler {
                C06701() {
                }

                public CALLBACK_TYPE getType() {
                    return CALLBACK_TYPE.PROMPT_LOCATION;
                }

                public void complete(LocationPoint point) {
                    if (point != null) {
                        OneSignalStateSynchronizer.updateLocation(point);
                    }
                }
            }

            public void run() {
                LocationGMS.getLocation(OneSignal.appContext, true, new C06701());
                OneSignal.promptedLocation = true;
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not prompt for location at this time - moving this operation to awaiting queue.");
            addTaskToQueue(new PendingTaskRunnable(runPromptLocation));
            return;
        }
        runPromptLocation.run();
    }

    public static void clearOneSignalNotifications() {
        Runnable runClearOneSignalNotifications = new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x007c in list [B:26:0x0092]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                /*
                r16 = this;
                r1 = com.onesignal.OneSignal.appContext;
                r3 = "notification";
                r11 = r1.getSystemService(r3);
                r11 = (android.app.NotificationManager) r11;
                r1 = com.onesignal.OneSignal.appContext;
                r9 = com.onesignal.OneSignalDbHelper.getInstance(r1);
                r8 = 0;
                r0 = r9.getReadableDbWithRetries();	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r1 = 1;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r2 = new java.lang.String[r1];	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r1 = 0;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r3 = "android_notification_id";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r2[r1] = r3;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r1 = "notification";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r3 = "dismissed = 0 AND opened = 0";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r4 = 0;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r5 = 0;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r6 = 0;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r7 = 0;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r8 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r1 = r8.moveToFirst();	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                if (r1 == 0) goto L_0x0047;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
            L_0x0033:
                r1 = "android_notification_id";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r1 = r8.getColumnIndex(r1);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r10 = r8.getInt(r1);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r11.cancel(r10);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r1 = r8.moveToNext();	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                if (r1 != 0) goto L_0x0033;
            L_0x0047:
                r15 = 0;
                r15 = r9.getWritableDbWithRetries();	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r15.beginTransaction();	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r14 = "opened = 0";	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r13 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r13.<init>();	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r1 = "dismissed";	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r3 = 1;	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r13.put(r1, r3);	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r1 = "notification";	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r3 = 0;	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r15.update(r1, r13, r14, r3);	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r15.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                if (r15 == 0) goto L_0x0071;
            L_0x006e:
                r15.endTransaction();	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
            L_0x0071:
                r1 = 0;
                r3 = com.onesignal.OneSignal.appContext;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                com.onesignal.BadgeCountUpdater.updateCount(r1, r3);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                if (r8 == 0) goto L_0x007c;
            L_0x0079:
                r8.close();
            L_0x007c:
                return;
            L_0x007d:
                r12 = move-exception;
                r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r3 = "Error closing transaction! ";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                com.onesignal.OneSignal.Log(r1, r3, r12);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                goto L_0x0071;
            L_0x0087:
                r12 = move-exception;
                r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ all -> 0x00af }
                r3 = "Error canceling all notifications! ";	 Catch:{ all -> 0x00af }
                com.onesignal.OneSignal.Log(r1, r3, r12);	 Catch:{ all -> 0x00af }
                if (r8 == 0) goto L_0x007c;
            L_0x0092:
                r8.close();
                goto L_0x007c;
            L_0x0096:
                r12 = move-exception;
                r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                r3 = "Error marking all notifications as dismissed! ";	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                com.onesignal.OneSignal.Log(r1, r3, r12);	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                if (r15 == 0) goto L_0x0071;
            L_0x00a1:
                r15.endTransaction();	 Catch:{ Throwable -> 0x0096, all -> 0x00b6, Throwable -> 0x00a5 }
                goto L_0x0071;
            L_0x00a5:
                r12 = move-exception;
                r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r3 = "Error closing transaction! ";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                com.onesignal.OneSignal.Log(r1, r3, r12);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                goto L_0x0071;
            L_0x00af:
                r1 = move-exception;
                if (r8 == 0) goto L_0x00b5;
            L_0x00b2:
                r8.close();
            L_0x00b5:
                throw r1;
            L_0x00b6:
                r1 = move-exception;
                if (r15 == 0) goto L_0x00bc;
            L_0x00b9:
                r15.endTransaction();	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
            L_0x00bc:
                throw r1;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
            L_0x00bd:
                r12 = move-exception;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r3 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                r4 = "Error closing transaction! ";	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                com.onesignal.OneSignal.Log(r3, r4, r12);	 Catch:{ Throwable -> 0x00bd, Throwable -> 0x007d, Throwable -> 0x0087 }
                goto L_0x00bc;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignal.21.run():void");
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not clear notifications at this time - moving this operation toa waiting task queue.");
            addTaskToQueue(new PendingTaskRunnable(runClearOneSignalNotifications));
            return;
        }
        runClearOneSignalNotifications.run();
    }

    public static void cancelNotification(final int id) {
        Runnable runCancelNotification = new Runnable() {
            public void run() {
                SQLiteDatabase writableDb = null;
                try {
                    writableDb = OneSignalDbHelper.getInstance(OneSignal.appContext).getWritableDbWithRetries();
                    writableDb.beginTransaction();
                    String whereStr = "android_notification_id = " + id + " AND " + NotificationTable.COLUMN_NAME_OPENED + " = 0 AND " + NotificationTable.COLUMN_NAME_DISMISSED + " = 0";
                    ContentValues values = new ContentValues();
                    values.put(NotificationTable.COLUMN_NAME_DISMISSED, Integer.valueOf(1));
                    if (writableDb.update(NotificationTable.TABLE_NAME, values, whereStr, null) > 0) {
                        NotificationSummaryManager.updatePossibleDependentSummaryOnDismiss(OneSignal.appContext, writableDb, id);
                    }
                    BadgeCountUpdater.update(writableDb, OneSignal.appContext);
                    writableDb.setTransactionSuccessful();
                    if (writableDb != null) {
                        try {
                            writableDb.endTransaction();
                        } catch (Throwable t) {
                            OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t);
                        }
                    }
                } catch (Throwable t2) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t2);
                }
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not clear notification id: " + id + " at this time - movingthis operation to a waiting task queue. The notification will still be canceledfrom NotificationManager at this time.");
            taskQueueWaitingForInit.add(runCancelNotification);
            return;
        }
        runCancelNotification.run();
        ((NotificationManager) appContext.getSystemService(NotificationTable.TABLE_NAME)).cancel(id);
    }

    public static void cancelGroupedNotifications(final String group) {
        Runnable runCancelGroupedNotifications = new Runnable() {
            public void run() {
                String[] whereArgs;
                SQLiteDatabase writableDb;
                NotificationManager notificationManager = (NotificationManager) OneSignal.appContext.getSystemService(NotificationTable.TABLE_NAME);
                OneSignalDbHelper dbHelper = OneSignalDbHelper.getInstance(OneSignal.appContext);
                Cursor cursor = null;
                try {
                    String[] retColumn = new String[]{NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID};
                    whereArgs = new String[]{group};
                    cursor = dbHelper.getReadableDbWithRetries().query(NotificationTable.TABLE_NAME, retColumn, "group_id = ? AND dismissed = 0 AND opened = 0", whereArgs, null, null, null);
                    while (cursor.moveToNext()) {
                        int notifId = cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID));
                        if (notifId != -1) {
                            notificationManager.cancel(notifId);
                        }
                    }
                    if (!(cursor == null || cursor.isClosed())) {
                        cursor.close();
                    }
                } catch (Throwable t) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Error getting android notifications part of group: " + group, t);
                    writableDb = null;
                    writableDb = dbHelper.getWritableDbWithRetries();
                    writableDb.beginTransaction();
                    whereArgs = new String[]{group};
                    ContentValues values = new ContentValues();
                    values.put(NotificationTable.COLUMN_NAME_DISMISSED, Integer.valueOf(1));
                    writableDb.update(NotificationTable.TABLE_NAME, values, "group_id = ? AND opened = 0 AND dismissed = 0", whereArgs);
                    BadgeCountUpdater.update(writableDb, OneSignal.appContext);
                    writableDb.setTransactionSuccessful();
                    if (writableDb != null) {
                        try {
                            writableDb.endTransaction();
                        } catch (Throwable t2) {
                            OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t2);
                            return;
                        }
                    }
                } finally {
                    if (!(cursor == null || cursor.isClosed())) {
                        cursor.close();
                    }
                }
                writableDb = null;
                try {
                    writableDb = dbHelper.getWritableDbWithRetries();
                    writableDb.beginTransaction();
                    whereArgs = new String[]{group};
                    ContentValues values2 = new ContentValues();
                    values2.put(NotificationTable.COLUMN_NAME_DISMISSED, Integer.valueOf(1));
                    writableDb.update(NotificationTable.TABLE_NAME, values2, "group_id = ? AND opened = 0 AND dismissed = 0", whereArgs);
                    BadgeCountUpdater.update(writableDb, OneSignal.appContext);
                    writableDb.setTransactionSuccessful();
                    if (writableDb != null) {
                        writableDb.endTransaction();
                    }
                } catch (Throwable t22) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t22);
                }
            }
        };
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not clear notifications part of group " + group + " - movingthis operation to a waiting task queue.");
            addTaskToQueue(new PendingTaskRunnable(runCancelGroupedNotifications));
            return;
        }
        runCancelGroupedNotifications.run();
    }

    public static void removeNotificationOpenedHandler() {
        getCurrentOrNewInitBuilder().mNotificationOpenedHandler = null;
    }

    public static void removeNotificationReceivedHandler() {
        getCurrentOrNewInitBuilder().mNotificationReceivedHandler = null;
    }

    public static void addPermissionObserver(OSPermissionObserver observer) {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not add permission observer");
            return;
        }
        getPermissionStateChangesObserver().addObserver(observer);
        if (getCurrentPermissionState(appContext).compare(getLastPermissionState(appContext))) {
            OSPermissionChangedInternalObserver.fireChangesToPublicObserver(getCurrentPermissionState(appContext));
        }
    }

    public static void removePermissionObserver(OSPermissionObserver observer) {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not modify permission observer");
        } else {
            getPermissionStateChangesObserver().removeObserver(observer);
        }
    }

    public static void addSubscriptionObserver(OSSubscriptionObserver observer) {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not add subscription observer");
            return;
        }
        getSubscriptionStateChangesObserver().addObserver(observer);
        if (getCurrentSubscriptionState(appContext).compare(getLastSubscriptionState(appContext))) {
            OSSubscriptionChangedInternalObserver.fireChangesToPublicObserver(getCurrentSubscriptionState(appContext));
        }
    }

    public static void removeSubscriptionObserver(OSSubscriptionObserver observer) {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not modify subscription observer");
        } else {
            getSubscriptionStateChangesObserver().removeObserver(observer);
        }
    }

    public static void addEmailSubscriptionObserver(@NonNull OSEmailSubscriptionObserver observer) {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not add email subscription observer");
            return;
        }
        getEmailSubscriptionStateChangesObserver().addObserver(observer);
        if (getCurrentEmailSubscriptionState(appContext).compare(getLastEmailSubscriptionState(appContext))) {
            OSEmailSubscriptionChangedInternalObserver.fireChangesToPublicObserver(getCurrentEmailSubscriptionState(appContext));
        }
    }

    public static void removeEmailSubscriptionObserver(@NonNull OSEmailSubscriptionObserver observer) {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not modify email subscription observer");
        } else {
            getEmailSubscriptionStateChangesObserver().removeObserver(observer);
        }
    }

    public static OSPermissionSubscriptionState getPermissionSubscriptionState() {
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not get OSPermissionSubscriptionState");
            return null;
        }
        OSPermissionSubscriptionState status = new OSPermissionSubscriptionState();
        status.subscriptionStatus = getCurrentSubscriptionState(appContext);
        status.permissionStatus = getCurrentPermissionState(appContext);
        status.emailSubscriptionStatus = getCurrentEmailSubscriptionState(appContext);
        return status;
    }

    static long GetUnsentActiveTime() {
        if (unSentActiveTime == -1 && appContext != null) {
            unSentActiveTime = OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, "GT_UNSENT_ACTIVE_TIME", 0);
        }
        Log(LOG_LEVEL.INFO, "GetUnsentActiveTime: " + unSentActiveTime);
        return unSentActiveTime;
    }

    private static void SaveUnsentActiveTime(long time) {
        unSentActiveTime = time;
        if (appContext != null) {
            Log(LOG_LEVEL.INFO, "SaveUnsentActiveTime: " + unSentActiveTime);
            OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, "GT_UNSENT_ACTIVE_TIME", time);
        }
    }

    private static boolean isDuplicateNotification(String id, Context context) {
        if (id == null || "".equals(id)) {
            return false;
        }
        boolean exists = false;
        Cursor cursor = null;
        try {
            cursor = OneSignalDbHelper.getInstance(context).getReadableDbWithRetries().query(NotificationTable.TABLE_NAME, new String[]{NotificationTable.COLUMN_NAME_NOTIFICATION_ID}, "notification_id = ?", new String[]{id}, null, null, null);
            exists = cursor.moveToFirst();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (!exists) {
            return false;
        }
        Log(LOG_LEVEL.DEBUG, "Duplicate GCM message received, skip processing of " + id);
        return true;
    }

    static boolean notValidOrDuplicated(Context context, JSONObject jsonPayload) {
        String id = getNotificationIdFromGCMJsonPayload(jsonPayload);
        return id == null || isDuplicateNotification(id, context);
    }

    static String getNotificationIdFromGCMBundle(Bundle bundle) {
        if (bundle.isEmpty()) {
            return null;
        }
        try {
            if (bundle.containsKey("custom")) {
                JSONObject customJSON = new JSONObject(bundle.getString("custom"));
                if (customJSON.has("i")) {
                    return customJSON.optString("i", null);
                }
                Log(LOG_LEVEL.DEBUG, "Not a OneSignal formatted GCM message. No 'i' field in custom.");
                return null;
            }
            Log(LOG_LEVEL.DEBUG, "Not a OneSignal formatted GCM message. No 'custom' field in the bundle.");
            return null;
        } catch (Throwable t) {
            Log(LOG_LEVEL.DEBUG, "Could not parse bundle, probably not a OneSignal notification.", t);
            return null;
        }
    }

    private static String getNotificationIdFromGCMJsonPayload(JSONObject jsonPayload) {
        String str = null;
        try {
            str = new JSONObject(jsonPayload.optString("custom")).optString("i", null);
        } catch (Throwable th) {
        }
        return str;
    }

    static boolean isAppActive() {
        return initDone && isForeground();
    }

    static void updateOnSessionDependents() {
        sendAsSession = false;
        setLastSessionTime(System.currentTimeMillis());
    }

    private static boolean isPastOnSessionTime() {
        if (!sendAsSession && (System.currentTimeMillis() - getLastSessionTime(appContext)) / 1000 < MIN_ON_SESSION_TIME) {
            return false;
        }
        return true;
    }

    static boolean areNotificationsEnabledForSubscribedState() {
        if (mInitBuilder.mUnsubscribeWhenNotificationsAreDisabled) {
            return OSUtils.areNotificationsEnabled(appContext);
        }
        return true;
    }

    static void handleSuccessfulEmailLogout() {
        if (emailLogoutHandler != null) {
            emailLogoutHandler.onSuccess();
            emailLogoutHandler = null;
        }
    }

    static void handleFailedEmailLogout() {
        if (emailLogoutHandler != null) {
            emailLogoutHandler.onFailure(new EmailUpdateError(EmailErrorType.NETWORK, "Failed due to network failure. Will retry on next sync."));
            emailLogoutHandler = null;
        }
    }

    static void fireEmailUpdateSuccess() {
        if (emailUpdateHandler != null) {
            emailUpdateHandler.onSuccess();
            emailUpdateHandler = null;
        }
    }

    static void fireEmailUpdateFailure() {
        if (emailUpdateHandler != null) {
            emailUpdateHandler.onFailure(new EmailUpdateError(EmailErrorType.NETWORK, "Failed due to network failure. Will retry on next sync."));
            emailUpdateHandler = null;
        }
    }
}
