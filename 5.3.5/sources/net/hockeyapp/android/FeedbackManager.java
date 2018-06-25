package net.hockeyapp.android;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import com.onesignal.OneSignalDbContract.NotificationTable;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.FileOutputStream;
import net.hockeyapp.android.objects.FeedbackUserDataElement;
import net.hockeyapp.android.tasks.ParseFeedbackTask;
import net.hockeyapp.android.tasks.SendFeedbackTask;
import net.hockeyapp.android.utils.AsyncTaskUtils;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.PrefsUtil;
import net.hockeyapp.android.utils.Util;

public class FeedbackManager {
    private static final String BROADCAST_ACTION = "net.hockeyapp.android.SCREENSHOT";
    private static final int BROADCAST_REQUEST_CODE = 1;
    private static final int SCREENSHOT_NOTIFICATION_ID = 1;
    private static Activity currentActivity;
    private static String identifier = null;
    private static FeedbackManagerListener lastListener = null;
    private static boolean notificationActive = false;
    private static BroadcastReceiver receiver = null;
    private static FeedbackUserDataElement requireUserEmail = FeedbackUserDataElement.REQUIRED;
    private static FeedbackUserDataElement requireUserName = FeedbackUserDataElement.REQUIRED;
    private static String urlString = null;
    private static String userEmail;
    private static String userName;

    /* renamed from: net.hockeyapp.android.FeedbackManager$3 */
    static class C09583 extends BroadcastReceiver {
        C09583() {
        }

        public void onReceive(Context context, Intent intent) {
            FeedbackManager.takeScreenshot(context);
        }
    }

    private static class MediaScannerClient implements MediaScannerConnectionClient {
        private MediaScannerConnection connection;
        private String path;

        private MediaScannerClient(String path) {
            this.connection = null;
            this.path = path;
        }

        public void setConnection(MediaScannerConnection connection) {
            this.connection = connection;
        }

        public void onMediaScannerConnected() {
            if (this.connection != null) {
                this.connection.scanFile(this.path, null);
            }
        }

        public void onScanCompleted(String path, Uri uri) {
            HockeyLog.verbose(String.format("Scanned path %s -> URI = %s", new Object[]{path, uri.toString()}));
            this.connection.disconnect();
        }
    }

    public static void register(Context context) {
        String appIdentifier = Util.getAppIdentifier(context);
        if (appIdentifier == null || appIdentifier.length() == 0) {
            throw new IllegalArgumentException("HockeyApp app identifier was not configured correctly in manifest or build configuration.");
        }
        register(context, appIdentifier);
    }

    public static void register(Context context, String appIdentifier) {
        register(context, appIdentifier, null);
    }

    public static void register(Context context, String appIdentifier, FeedbackManagerListener listener) {
        register(context, Constants.BASE_URL, appIdentifier, listener);
    }

    public static void register(Context context, String urlString, String appIdentifier, FeedbackManagerListener listener) {
        if (context != null) {
            identifier = Util.sanitizeAppIdentifier(appIdentifier);
            urlString = urlString;
            lastListener = listener;
            Constants.loadFromContext(context);
        }
    }

    public static void unregister() {
        lastListener = null;
    }

    public static void showFeedbackActivity(Context context, Uri... attachments) {
        showFeedbackActivity(context, null, attachments);
    }

    public static void showFeedbackActivity(Context context, Bundle extras, Uri... attachments) {
        if (context != null) {
            Class<?> activityClass = null;
            if (lastListener != null) {
                activityClass = lastListener.getFeedbackActivityClass();
            }
            if (activityClass == null) {
                activityClass = FeedbackActivity.class;
            }
            boolean forceNewThread = lastListener != null && lastListener.shouldCreateNewFeedbackThread();
            Intent intent = new Intent();
            if (!(extras == null || extras.isEmpty())) {
                intent.putExtras(extras);
            }
            intent.setFlags(268435456);
            intent.setClass(context, activityClass);
            intent.putExtra("url", getURLString(context));
            intent.putExtra(FeedbackActivity.EXTRA_FORCE_NEW_THREAD, forceNewThread);
            intent.putExtra(FeedbackActivity.EXTRA_INITIAL_USER_NAME, userName);
            intent.putExtra(FeedbackActivity.EXTRA_INITIAL_USER_EMAIL, userEmail);
            intent.putExtra(FeedbackActivity.EXTRA_INITIAL_ATTACHMENTS, attachments);
            context.startActivity(intent);
        }
    }

    public static void checkForAnswersAndNotify(final Context context) {
        String token = PrefsUtil.getInstance().getFeedbackTokenFromPrefs(context);
        if (token != null) {
            int lastMessageId = context.getSharedPreferences(ParseFeedbackTask.PREFERENCES_NAME, 0).getInt(ParseFeedbackTask.ID_LAST_MESSAGE_SEND, -1);
            SendFeedbackTask sendFeedbackTask = new SendFeedbackTask(context, getURLString(context), null, null, null, null, null, token, new Handler() {
                public void handleMessage(Message msg) {
                    String responseString = msg.getData().getString(SendFeedbackTask.BUNDLE_FEEDBACK_RESPONSE);
                    if (responseString != null) {
                        ParseFeedbackTask task = new ParseFeedbackTask(context, responseString, null, "fetch");
                        task.setUrlString(FeedbackManager.getURLString(context));
                        AsyncTaskUtils.execute(task);
                    }
                }
            }, true);
            sendFeedbackTask.setShowProgressDialog(false);
            sendFeedbackTask.setLastMessageId(lastMessageId);
            AsyncTaskUtils.execute(sendFeedbackTask);
        }
    }

    public static FeedbackManagerListener getLastListener() {
        return lastListener;
    }

    private static String getURLString(Context context) {
        return urlString + "api/2/apps/" + identifier + "/feedback/";
    }

    public static FeedbackUserDataElement getRequireUserName() {
        return requireUserName;
    }

    public static void setRequireUserName(FeedbackUserDataElement requireUserName) {
        requireUserName = requireUserName;
    }

    public static FeedbackUserDataElement getRequireUserEmail() {
        return requireUserEmail;
    }

    public static void setRequireUserEmail(FeedbackUserDataElement requireUserEmail) {
        requireUserEmail = requireUserEmail;
    }

    public static void setUserName(String userName) {
        userName = userName;
    }

    public static void setUserEmail(String userEmail) {
        userEmail = userEmail;
    }

    public static void setActivityForScreenshot(Activity activity) {
        currentActivity = activity;
        if (!notificationActive) {
            startNotification();
        }
    }

    public static void unsetCurrentActivityForScreenshot(Activity activity) {
        if (currentActivity != null && currentActivity == activity) {
            endNotification();
            currentActivity = null;
        }
    }

    public static void takeScreenshot(final Context context) {
        View view = currentActivity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        final Bitmap bitmap = view.getDrawingCache();
        String filename = currentActivity.getLocalClassName();
        File dir = Constants.getHockeyAppStorageDir();
        File result = new File(dir, filename + ".jpg");
        int suffix = 1;
        while (result.exists()) {
            result = new File(dir, filename + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + suffix + ".jpg");
            suffix++;
        }
        new AsyncTask<File, Void, Boolean>() {
            protected Boolean doInBackground(File... args) {
                try {
                    FileOutputStream out = new FileOutputStream(args[0]);
                    bitmap.compress(CompressFormat.JPEG, 100, out);
                    out.close();
                    return Boolean.valueOf(true);
                } catch (Throwable e) {
                    HockeyLog.error("Could not save screenshot.", e);
                    return Boolean.valueOf(false);
                }
            }

            protected void onPostExecute(Boolean success) {
                if (!success.booleanValue()) {
                    Toast.makeText(context, "Screenshot could not be created. Sorry.", 1).show();
                }
            }
        }.execute(new File[]{result});
        MediaScannerClient client = new MediaScannerClient(result.getAbsolutePath());
        MediaScannerConnection connection = new MediaScannerConnection(currentActivity, client);
        client.setConnection(connection);
        connection.connect();
        Toast.makeText(context, "Screenshot '" + result.getName() + "' is available in gallery.", 1).show();
    }

    private static void startNotification() {
        notificationActive = true;
        NotificationManager notificationManager = (NotificationManager) currentActivity.getSystemService(NotificationTable.TABLE_NAME);
        int iconId = currentActivity.getResources().getIdentifier("ic_menu_camera", "drawable", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        notificationManager.notify(1, Util.createNotification(currentActivity, PendingIntent.getBroadcast(currentActivity, 1, intent, 1073741824), "HockeyApp Feedback", "Take a screenshot for your feedback.", iconId));
        if (receiver == null) {
            receiver = new C09583();
        }
        currentActivity.registerReceiver(receiver, new IntentFilter(BROADCAST_ACTION));
    }

    private static void endNotification() {
        notificationActive = false;
        currentActivity.unregisterReceiver(receiver);
        ((NotificationManager) currentActivity.getSystemService(NotificationTable.TABLE_NAME)).cancel(1);
    }
}
