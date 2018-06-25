package com.onesignal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.onesignal.NotificationExtenderService.OverrideSettings;
import com.onesignal.OSNotificationPayload.ActionButton;
import com.onesignal.OSNotificationPayload.BackgroundImageLayout;
import com.onesignal.OneSignal.LOG_LEVEL;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

class NotificationBundleProcessor {
    static final String DEFAULT_ACTION = "__DEFAULT__";

    static class ProcessedBundleResult {
        boolean hasExtenderService;
        boolean isDup;
        boolean isOneSignalPayload;

        ProcessedBundleResult() {
        }

        boolean processed() {
            return !this.isOneSignalPayload || this.hasExtenderService || this.isDup;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void processCollapseKey(com.onesignal.NotificationGenerationJob r13) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0004 in list [B:12:0x006c]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r1 = r13.restoring;
        if (r1 == 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r1 = r13.jsonPayload;
        r2 = "collapse_key";
        r1 = r1.has(r2);
        if (r1 == 0) goto L_0x0004;
    L_0x0010:
        r1 = "do_not_collapse";
        r2 = r13.jsonPayload;
        r3 = "collapse_key";
        r2 = r2.optString(r3);
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0004;
    L_0x0022:
        r1 = r13.jsonPayload;
        r2 = "collapse_key";
        r9 = r1.optString(r2);
        r1 = r13.context;
        r11 = com.onesignal.OneSignalDbHelper.getInstance(r1);
        r10 = 0;
        r0 = r11.getReadableDbWithRetries();	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r1 = "notification";	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r2 = 1;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r2 = new java.lang.String[r2];	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r3 = 0;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r4 = "android_notification_id";	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r3 = "collapse_id = ? AND dismissed = 0 AND opened = 0 ";	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r4 = 1;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r5 = 0;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r4[r5] = r9;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r5 = 0;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r6 = 0;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r7 = 0;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r10 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r1 = r10.moveToFirst();	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        if (r1 == 0) goto L_0x006a;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
    L_0x0058:
        r1 = "android_notification_id";	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r1 = r10.getColumnIndex(r1);	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r8 = r10.getInt(r1);	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r1 = java.lang.Integer.valueOf(r8);	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r13.setAndroidIdWithOutOverriding(r1);	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
    L_0x006a:
        if (r10 == 0) goto L_0x0004;
    L_0x006c:
        r1 = r10.isClosed();
        if (r1 != 0) goto L_0x0004;
    L_0x0072:
        r10.close();
        goto L_0x0004;
    L_0x0076:
        r12 = move-exception;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        r2 = "Could not read DB to find existing collapse_key!";	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        com.onesignal.OneSignal.Log(r1, r2, r12);	 Catch:{ Throwable -> 0x0076, all -> 0x008c }
        if (r10 == 0) goto L_0x0004;
    L_0x0081:
        r1 = r10.isClosed();
        if (r1 != 0) goto L_0x0004;
    L_0x0087:
        r10.close();
        goto L_0x0004;
    L_0x008c:
        r1 = move-exception;
        if (r10 == 0) goto L_0x0098;
    L_0x008f:
        r2 = r10.isClosed();
        if (r2 != 0) goto L_0x0098;
    L_0x0095:
        r10.close();
    L_0x0098:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.NotificationBundleProcessor.processCollapseKey(com.onesignal.NotificationGenerationJob):void");
    }

    NotificationBundleProcessor() {
    }

    static void ProcessFromGCMIntentService(Context context, BundleCompat bundle, OverrideSettings overrideSettings) {
        try {
            String jsonStrPayload = bundle.getString("json_payload");
            if (jsonStrPayload == null) {
                OneSignal.Log(LOG_LEVEL.ERROR, "json_payload key is nonexistent from mBundle passed to ProcessFromGCMIntentService: " + bundle);
                return;
            }
            NotificationGenerationJob notifJob = new NotificationGenerationJob(context);
            notifJob.restoring = bundle.getBoolean("restoring", false);
            notifJob.shownTimeStamp = bundle.getLong(Param.TIMESTAMP);
            notifJob.jsonPayload = new JSONObject(jsonStrPayload);
            if (notifJob.restoring || !OneSignal.notValidOrDuplicated(context, notifJob.jsonPayload)) {
                if (bundle.containsKey("android_notif_id")) {
                    if (overrideSettings == null) {
                        overrideSettings = new OverrideSettings();
                    }
                    overrideSettings.androidNotificationId = bundle.getInt("android_notif_id");
                }
                notifJob.overrideSettings = overrideSettings;
                ProcessJobForDisplay(notifJob);
                if (notifJob.restoring) {
                    OSUtils.sleep(100);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static int ProcessJobForDisplay(NotificationGenerationJob notifJob) {
        boolean z;
        boolean doDisplay;
        if (OneSignal.getInAppAlertNotificationEnabled() && OneSignal.isAppActive()) {
            z = true;
        } else {
            z = false;
        }
        notifJob.showAsAlert = z;
        processCollapseKey(notifJob);
        if (notifJob.hasExtender() || shouldDisplay(notifJob.jsonPayload.optString("alert"))) {
            doDisplay = true;
        } else {
            doDisplay = false;
        }
        if (doDisplay) {
            GenerateNotification.fromJsonPayload(notifJob);
        }
        if (!notifJob.restoring) {
            saveNotification(notifJob, false);
            try {
                JSONObject jsonObject = new JSONObject(notifJob.jsonPayload.toString());
                jsonObject.put("notificationId", notifJob.getAndroidId());
                OneSignal.handleNotificationReceived(newJsonArray(jsonObject), true, notifJob.showAsAlert);
            } catch (Throwable th) {
            }
        }
        return notifJob.getAndroidId().intValue();
    }

    private static JSONArray bundleAsJsonArray(Bundle bundle) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bundleAsJSONObject(bundle));
        return jsonArray;
    }

    private static void saveNotification(Context context, Bundle bundle, boolean opened, int notificationId) {
        NotificationGenerationJob notifJob = new NotificationGenerationJob(context);
        notifJob.jsonPayload = bundleAsJSONObject(bundle);
        notifJob.overrideSettings = new OverrideSettings();
        notifJob.overrideSettings.androidNotificationId = Integer.valueOf(notificationId);
        saveNotification(notifJob, opened);
    }

    static void saveNotification(NotificationGenerationJob notifiJob, boolean opened) {
        int i = 1;
        Context context = notifiJob.context;
        JSONObject jsonPayload = notifiJob.jsonPayload;
        try {
            JSONObject customJSON = new JSONObject(notifiJob.jsonPayload.optString("custom"));
            SQLiteDatabase writableDb = null;
            try {
                ContentValues values;
                writableDb = OneSignalDbHelper.getInstance(notifiJob.context).getWritableDbWithRetries();
                writableDb.beginTransaction();
                deleteOldNotifications(writableDb);
                if (notifiJob.getAndroidIdWithoutCreate() != -1) {
                    String whereStr = "android_notification_id = " + notifiJob.getAndroidIdWithoutCreate();
                    values = new ContentValues();
                    values.put(NotificationTable.COLUMN_NAME_DISMISSED, Integer.valueOf(1));
                    writableDb.update(NotificationTable.TABLE_NAME, values, whereStr, null);
                    BadgeCountUpdater.update(writableDb, context);
                }
                values = new ContentValues();
                values.put(NotificationTable.COLUMN_NAME_NOTIFICATION_ID, customJSON.optString("i"));
                if (jsonPayload.has("grp")) {
                    values.put("group_id", jsonPayload.optString("grp"));
                }
                if (jsonPayload.has("collapse_key") && !"do_not_collapse".equals(jsonPayload.optString("collapse_key"))) {
                    values.put(NotificationTable.COLUMN_NAME_COLLAPSE_ID, jsonPayload.optString("collapse_key"));
                }
                String str = NotificationTable.COLUMN_NAME_OPENED;
                if (!opened) {
                    i = 0;
                }
                values.put(str, Integer.valueOf(i));
                if (!opened) {
                    values.put(NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID, Integer.valueOf(notifiJob.getAndroidIdWithoutCreate()));
                }
                if (notifiJob.getTitle() != null) {
                    values.put("title", notifiJob.getTitle().toString());
                }
                if (notifiJob.getBody() != null) {
                    values.put("message", notifiJob.getBody().toString());
                }
                values.put(NotificationTable.COLUMN_NAME_FULL_DATA, jsonPayload.toString());
                writableDb.insertOrThrow(NotificationTable.TABLE_NAME, null, values);
                if (!opened) {
                    BadgeCountUpdater.update(writableDb, context);
                }
                writableDb.setTransactionSuccessful();
                if (writableDb != null) {
                    try {
                        writableDb.endTransaction();
                    } catch (Throwable t) {
                        OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t);
                    }
                }
            } catch (Exception e) {
                OneSignal.Log(LOG_LEVEL.ERROR, "Error saving notification record! ", e);
                if (writableDb != null) {
                    writableDb.endTransaction();
                }
            } catch (Throwable t2) {
                OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t2);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        } catch (Throwable t22) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t22);
        }
    }

    static void markRestoredNotificationAsDismissed(NotificationGenerationJob notifiJob) {
        if (notifiJob.getAndroidIdWithoutCreate() != -1) {
            String whereStr = "android_notification_id = " + notifiJob.getAndroidIdWithoutCreate();
            SQLiteDatabase writableDb = null;
            try {
                writableDb = OneSignalDbHelper.getInstance(notifiJob.context).getWritableDbWithRetries();
                writableDb.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(NotificationTable.COLUMN_NAME_DISMISSED, Integer.valueOf(1));
                writableDb.update(NotificationTable.TABLE_NAME, values, whereStr, null);
                BadgeCountUpdater.update(writableDb, notifiJob.context);
                writableDb.setTransactionSuccessful();
                if (writableDb != null) {
                    try {
                        writableDb.endTransaction();
                    } catch (Throwable t) {
                        OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t);
                    }
                }
            } catch (Exception e) {
                OneSignal.Log(LOG_LEVEL.ERROR, "Error saving notification record! ", e);
                if (writableDb != null) {
                    writableDb.endTransaction();
                }
            } catch (Throwable t2) {
                OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t2);
            }
        }
    }

    static void deleteOldNotifications(SQLiteDatabase writableDb) {
        writableDb.delete(NotificationTable.TABLE_NAME, "created_time < " + ((System.currentTimeMillis() / 1000) - 604800), null);
    }

    static JSONObject bundleAsJSONObject(Bundle bundle) {
        JSONObject json = new JSONObject();
        for (String key : bundle.keySet()) {
            try {
                json.put(key, bundle.get(key));
            } catch (JSONException e) {
                OneSignal.Log(LOG_LEVEL.ERROR, "bundleAsJSONObject error for key: " + key, e);
            }
        }
        return json;
    }

    private static void unMinifyBundle(Bundle gcmBundle) {
        if (gcmBundle.containsKey("o")) {
            try {
                JSONObject additionalDataJSON;
                JSONObject customJSON = new JSONObject(gcmBundle.getString("custom"));
                if (customJSON.has("a")) {
                    additionalDataJSON = customJSON.getJSONObject("a");
                } else {
                    additionalDataJSON = new JSONObject();
                }
                JSONArray buttons = new JSONArray(gcmBundle.getString("o"));
                gcmBundle.remove("o");
                for (int i = 0; i < buttons.length(); i++) {
                    String buttonId;
                    JSONObject button = buttons.getJSONObject(i);
                    String buttonText = button.getString("n");
                    button.remove("n");
                    if (button.has("i")) {
                        buttonId = button.getString("i");
                        button.remove("i");
                    } else {
                        buttonId = buttonText;
                    }
                    button.put("id", buttonId);
                    button.put("text", buttonText);
                    if (button.has(TtmlNode.TAG_P)) {
                        button.put(SettingsJsonConstants.APP_ICON_KEY, button.getString(TtmlNode.TAG_P));
                        button.remove(TtmlNode.TAG_P);
                    }
                }
                additionalDataJSON.put("actionButtons", buttons);
                additionalDataJSON.put("actionSelected", DEFAULT_ACTION);
                if (!customJSON.has("a")) {
                    customJSON.put("a", additionalDataJSON);
                }
                gcmBundle.putString("custom", customJSON.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    static OSNotificationPayload OSNotificationPayloadFrom(JSONObject currentJsonPayload) {
        OSNotificationPayload notification = new OSNotificationPayload();
        try {
            JSONObject customJson = new JSONObject(currentJsonPayload.optString("custom"));
            notification.notificationID = customJson.optString("i");
            notification.templateId = customJson.optString("ti");
            notification.templateName = customJson.optString("tn");
            notification.rawPayload = currentJsonPayload.toString();
            notification.additionalData = customJson.optJSONObject("a");
            notification.launchURL = customJson.optString("u", null);
            notification.body = currentJsonPayload.optString("alert", null);
            notification.title = currentJsonPayload.optString("title", null);
            notification.smallIcon = currentJsonPayload.optString("sicon", null);
            notification.bigPicture = currentJsonPayload.optString("bicon", null);
            notification.largeIcon = currentJsonPayload.optString("licon", null);
            notification.sound = currentJsonPayload.optString("sound", null);
            notification.groupKey = currentJsonPayload.optString("grp", null);
            notification.groupMessage = currentJsonPayload.optString("grp_msg", null);
            notification.smallIconAccentColor = currentJsonPayload.optString("bgac", null);
            notification.ledColor = currentJsonPayload.optString("ledc", null);
            String visibility = currentJsonPayload.optString("vis", null);
            if (visibility != null) {
                notification.lockScreenVisibility = Integer.parseInt(visibility);
            }
            notification.fromProjectNumber = currentJsonPayload.optString("from", null);
            notification.priority = currentJsonPayload.optInt("pri", 0);
            String collapseKey = currentJsonPayload.optString("collapse_key", null);
            if (!"do_not_collapse".equals(collapseKey)) {
                notification.collapseId = collapseKey;
            }
            setActionButtons(notification);
        } catch (Throwable t) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error assigning OSNotificationPayload values!", t);
        }
        try {
            setBackgroundImageLayout(notification, currentJsonPayload);
        } catch (Throwable t2) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error assigning OSNotificationPayload.backgroundImageLayout values!", t2);
        }
        return notification;
    }

    private static void setActionButtons(OSNotificationPayload notification) throws Throwable {
        if (notification.additionalData != null && notification.additionalData.has("actionButtons")) {
            JSONArray jsonActionButtons = notification.additionalData.getJSONArray("actionButtons");
            notification.actionButtons = new ArrayList();
            for (int i = 0; i < jsonActionButtons.length(); i++) {
                JSONObject jsonActionButton = jsonActionButtons.getJSONObject(i);
                ActionButton actionButton = new ActionButton();
                actionButton.id = jsonActionButton.optString("id", null);
                actionButton.text = jsonActionButton.optString("text", null);
                actionButton.icon = jsonActionButton.optString(SettingsJsonConstants.APP_ICON_KEY, null);
                notification.actionButtons.add(actionButton);
            }
            notification.additionalData.remove("actionSelected");
            notification.additionalData.remove("actionButtons");
        }
    }

    private static void setBackgroundImageLayout(OSNotificationPayload notification, JSONObject currentJsonPayload) throws Throwable {
        String jsonStrBgImage = currentJsonPayload.optString("bg_img", null);
        if (jsonStrBgImage != null) {
            JSONObject jsonBgImage = new JSONObject(jsonStrBgImage);
            notification.backgroundImageLayout = new BackgroundImageLayout();
            notification.backgroundImageLayout.image = jsonBgImage.optString("img");
            notification.backgroundImageLayout.titleTextColor = jsonBgImage.optString("tc");
            notification.backgroundImageLayout.bodyTextColor = jsonBgImage.optString("bc");
        }
    }

    static ProcessedBundleResult processBundleFromReceiver(Context context, final Bundle bundle) {
        ProcessedBundleResult result = new ProcessedBundleResult();
        if (OneSignal.getNotificationIdFromGCMBundle(bundle) != null) {
            result.isOneSignalPayload = true;
            unMinifyBundle(bundle);
            if (!startExtenderService(context, bundle, result)) {
                result.isDup = OneSignal.notValidOrDuplicated(context, bundleAsJSONObject(bundle));
                if (!(result.isDup || shouldDisplay(bundle.getString("alert")))) {
                    saveNotification(context, bundle, true, -1);
                    new Thread(new Runnable() {
                        public void run() {
                            OneSignal.handleNotificationReceived(NotificationBundleProcessor.bundleAsJsonArray(bundle), false, false);
                        }
                    }, "OS_PROC_BUNDLE").start();
                }
            }
        }
        return result;
    }

    private static boolean startExtenderService(Context context, Bundle bundle, ProcessedBundleResult result) {
        Intent intent = NotificationExtenderService.getIntent(context);
        if (intent == null) {
            return false;
        }
        intent.putExtra("json_payload", bundleAsJSONObject(bundle).toString());
        intent.putExtra(Param.TIMESTAMP, System.currentTimeMillis() / 1000);
        if (VERSION.SDK_INT >= 21) {
            JobIntentService.enqueueWork(context, intent.getComponent(), 2071862121, intent);
        } else {
            context.startService(intent);
        }
        result.hasExtenderService = true;
        return true;
    }

    static boolean shouldDisplay(String body) {
        boolean hasBody;
        if (body == null || "".equals(body)) {
            hasBody = false;
        } else {
            hasBody = true;
        }
        return hasBody && (OneSignal.getNotificationsWhenActiveEnabled() || OneSignal.getInAppAlertNotificationEnabled() || !OneSignal.isAppActive());
    }

    static JSONArray newJsonArray(JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        return jsonArray;
    }

    static boolean hasRemoteResource(Bundle bundle) {
        return isBuildKeyRemote(bundle, "licon") || isBuildKeyRemote(bundle, "bicon") || bundle.getString("bg_img", null) != null;
    }

    private static boolean isBuildKeyRemote(Bundle bundle, String key) {
        String value = bundle.getString(key, "").trim();
        return value.startsWith("http://") || value.startsWith("https://");
    }
}
