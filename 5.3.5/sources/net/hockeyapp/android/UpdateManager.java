package net.hockeyapp.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.Date;
import net.hockeyapp.android.tasks.CheckUpdateTask;
import net.hockeyapp.android.tasks.CheckUpdateTaskWithUI;
import net.hockeyapp.android.utils.AsyncTaskUtils;
import net.hockeyapp.android.utils.Util;

public class UpdateManager {
    public static final String INSTALLER_ADB = "adb";
    public static final String INSTALLER_PACKAGE_INSTALLER_NOUGAT = "com.google.android.packageinstaller";
    public static final String INSTALLER_PACKAGE_INSTALLER_NOUGAT2 = "com.android.packageinstaller";
    private static UpdateManagerListener lastListener = null;
    private static CheckUpdateTask updateTask = null;

    public static void register(Activity activity) {
        String appIdentifier = Util.getAppIdentifier(activity);
        if (TextUtils.isEmpty(appIdentifier)) {
            throw new IllegalArgumentException("HockeyApp app identifier was not configured correctly in manifest or build configuration.");
        }
        register(activity, appIdentifier);
    }

    public static void register(Activity activity, String appIdentifier) {
        register(activity, appIdentifier, true);
    }

    public static void register(Activity activity, String appIdentifier, boolean isDialogRequired) {
        register(activity, appIdentifier, null, isDialogRequired);
    }

    public static void register(Activity activity, String appIdentifier, UpdateManagerListener listener) {
        register(activity, Constants.BASE_URL, appIdentifier, listener, true);
    }

    public static void register(Activity activity, String appIdentifier, UpdateManagerListener listener, boolean isDialogRequired) {
        register(activity, Constants.BASE_URL, appIdentifier, listener, isDialogRequired);
    }

    public static void register(Activity activity, String urlString, String appIdentifier, UpdateManagerListener listener) {
        register(activity, urlString, appIdentifier, listener, true);
    }

    public static void register(Activity activity, String urlString, String appIdentifier, UpdateManagerListener listener, boolean isDialogRequired) {
        appIdentifier = Util.sanitizeAppIdentifier(appIdentifier);
        lastListener = listener;
        WeakReference<Activity> weakActivity = new WeakReference(activity);
        if ((!Util.fragmentsSupported().booleanValue() || !dialogShown(weakActivity)) && !checkExpiryDate(weakActivity, listener)) {
            if ((listener != null && listener.canUpdateInMarket()) || !installedFromMarket(weakActivity)) {
                startUpdateTask(weakActivity, urlString, appIdentifier, listener, isDialogRequired);
            }
        }
    }

    public static void registerForBackground(Context appContext, String appIdentifier, UpdateManagerListener listener) {
        registerForBackground(appContext, Constants.BASE_URL, appIdentifier, listener);
    }

    public static void registerForBackground(Context appContext, String urlString, String appIdentifier, UpdateManagerListener listener) {
        appIdentifier = Util.sanitizeAppIdentifier(appIdentifier);
        lastListener = listener;
        WeakReference<Context> weakContext = new WeakReference(appContext);
        if (!checkExpiryDateForBackground(listener)) {
            if ((listener != null && listener.canUpdateInMarket()) || !installedFromMarket(weakContext)) {
                startUpdateTaskForBackground(weakContext, urlString, appIdentifier, listener);
            }
        }
    }

    public static void unregister() {
        if (updateTask != null) {
            updateTask.cancel(true);
            updateTask.detach();
            updateTask = null;
        }
        lastListener = null;
    }

    private static boolean checkExpiryDate(WeakReference<Activity> weakActivity, UpdateManagerListener listener) {
        boolean handle = false;
        boolean hasExpired = checkExpiryDateForBackground(listener);
        if (hasExpired) {
            handle = listener.onBuildExpired();
        }
        if (hasExpired && handle) {
            startExpiryInfoIntent(weakActivity);
        }
        return hasExpired;
    }

    private static boolean checkExpiryDateForBackground(UpdateManagerListener listener) {
        if (listener == null) {
            return false;
        }
        Date expiryDate = listener.getExpiryDate();
        return expiryDate != null && new Date().compareTo(expiryDate) > 0;
    }

    protected static boolean installedFromMarket(WeakReference<? extends Context> weakContext) {
        Context context = (Context) weakContext.get();
        if (context == null) {
            return false;
        }
        try {
            String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            if (TextUtils.isEmpty(installer)) {
                return false;
            }
            boolean result = true;
            if (VERSION.SDK_INT >= 24 && (TextUtils.equals(installer, INSTALLER_PACKAGE_INSTALLER_NOUGAT) || TextUtils.equals(installer, INSTALLER_PACKAGE_INSTALLER_NOUGAT2))) {
                result = false;
            }
            if (TextUtils.equals(installer, INSTALLER_ADB)) {
                return false;
            }
            return result;
        } catch (Throwable th) {
            return false;
        }
    }

    private static void startExpiryInfoIntent(WeakReference<Activity> weakActivity) {
        if (weakActivity != null) {
            Activity activity = (Activity) weakActivity.get();
            if (activity != null) {
                activity.finish();
                Intent intent = new Intent(activity, ExpiryInfoActivity.class);
                intent.addFlags(335544320);
                activity.startActivity(intent);
            }
        }
    }

    private static void startUpdateTask(WeakReference<Activity> weakActivity, String urlString, String appIdentifier, UpdateManagerListener listener, boolean isDialogRequired) {
        if (updateTask == null || updateTask.getStatus() == Status.FINISHED) {
            updateTask = new CheckUpdateTaskWithUI(weakActivity, urlString, appIdentifier, listener, isDialogRequired);
            AsyncTaskUtils.execute(updateTask);
            return;
        }
        updateTask.attach(weakActivity);
    }

    private static void startUpdateTaskForBackground(WeakReference<Context> weakContext, String urlString, String appIdentifier, UpdateManagerListener listener) {
        if (updateTask == null || updateTask.getStatus() == Status.FINISHED) {
            updateTask = new CheckUpdateTask(weakContext, urlString, appIdentifier, listener);
            AsyncTaskUtils.execute(updateTask);
            return;
        }
        updateTask.attach(weakContext);
    }

    @TargetApi(11)
    private static boolean dialogShown(WeakReference<Activity> weakActivity) {
        if (weakActivity == null) {
            return false;
        }
        Activity activity = (Activity) weakActivity.get();
        if (activity == null || activity.getFragmentManager().findFragmentByTag("hockey_update_dialog") == null) {
            return false;
        }
        return true;
    }

    public static UpdateManagerListener getLastListener() {
        return lastListener;
    }
}
