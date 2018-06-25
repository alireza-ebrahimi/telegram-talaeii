package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.os.StrictMode.VmPolicy;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;
import com.onesignal.OneSignal.OSInFocusDisplayOption;
import java.io.File;
import org.ir.talaeii.R;
import org.telegram.customization.Application.AppApplication;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.SLSProxyHelper;
import org.telegram.customization.recievers.HotgramOneSignalNotificationReceivedHandler;
import org.telegram.customization.service.ProxyService;
import org.telegram.customization.util.Prefs;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
import org.telegram.ui.Components.ForegroundDetector;
import utils.Utilities;

public class ApplicationLoader extends AppApplication {
    @SuppressLint({"StaticFieldLeak"})
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;
    private static volatile boolean applicationInited = false;
    public static volatile boolean isScreenOn = false;
    public static volatile boolean mainInterfacePaused = true;
    public static volatile boolean mainInterfacePausedStageQueue = true;
    public static volatile long mainInterfacePausedStageQueueTime;
    private FirebaseAnalytics mFirebaseAnalytics;
    public SLSProxyHelper slsProxyHelper = null;

    /* renamed from: org.telegram.messenger.ApplicationLoader$1 */
    class C09051 implements Runnable {
        C09051() {
        }

        public void run() {
            if (ApplicationLoader.this.checkPlayServices()) {
                if (UserConfig.pushString == null || UserConfig.pushString.length() == 0) {
                    FileLog.m91d("GCM Registration not found.");
                } else {
                    FileLog.m91d("GCM regId = " + UserConfig.pushString);
                }
                ApplicationLoader.this.startService(new Intent(ApplicationLoader.applicationContext, GcmRegistrationIntentService.class));
                return;
            }
            FileLog.m91d("No valid Google Play Services APK found.");
        }
    }

    public static File getFilesDirFixed() {
        for (int a = 0; a < 10; a++) {
            File path = applicationContext.getFilesDir();
            if (path != null) {
                return path;
            }
        }
        try {
            path = new File(applicationContext.getApplicationInfo().dataDir, "files");
            path.mkdirs();
            return path;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return new File("/data/data/org.telegram.messenger/files");
        }
    }

    public static void postInitApplication() {
        if (!applicationInited) {
            applicationInited = true;
            try {
                LocaleController.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_ON");
                filter.addAction("android.intent.action.SCREEN_OFF");
                applicationContext.registerReceiver(new ScreenReceiver(), filter);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                isScreenOn = ((PowerManager) applicationContext.getSystemService("power")).isScreenOn();
                FileLog.m92e("screen state = " + isScreenOn);
            } catch (Throwable e3) {
                FileLog.m94e(e3);
            }
            UserConfig.loadConfig();
            MessagesController.getInstance();
            ConnectionsManager.getInstance();
            if (UserConfig.getCurrentUser() != null) {
                MessagesController.getInstance().putUser(UserConfig.getCurrentUser(), true);
                MessagesController.getInstance().getBlockedUsers(true);
                SendMessagesHelper.getInstance().checkUnsentMessages();
            }
            ((ApplicationLoader) applicationContext).initPlayServices();
            FileLog.m92e("app initied");
            ContactsController.getInstance().checkAppAccount();
            MediaController.getInstance();
        }
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        NativeLoader.initNativeLibs(applicationContext);
        boolean z = VERSION.SDK_INT == 14 || VERSION.SDK_INT == 15;
        ConnectionsManager.native_setJava(z);
        ForegroundDetector foregroundDetector = new ForegroundDetector(this);
        applicationHandler = new Handler(applicationContext.getMainLooper());
        startPushService();
        OneSignal.init(applicationContext, getString(R.string.GOOGLE_APP_ID), getString(R.string.ONE_SIGNAL_APP_ID));
        OneSignal.getCurrentOrNewInitBuilder().inFocusDisplaying(OSInFocusDisplayOption.None);
        OneSignal.getCurrentOrNewInitBuilder().setNotificationReceivedHandler(new HotgramOneSignalNotificationReceivedHandler());
        OneSignal.getCurrentOrNewInitBuilder().unsubscribeWhenNotificationsAreDisabled(true);
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (BuildConfig.FLAVOR.contentEquals("gram")) {
            Editor editor = applicationContext.getSharedPreferences("plusconfig", 0).edit();
            editor.putBoolean("hideTabs", true);
            editor.apply();
        }
        ProxyService.startProxyService(getApplicationContext());
        if (Prefs.getIsFirstTime(getApplicationContext())) {
            Prefs.setSpIsFirstTime(getApplicationContext(), false);
            Prefs.setGhostMode(getApplicationContext(), 0);
            HandleRequest.getNew(getApplicationContext(), this).getFilters();
        }
        if (Prefs.getIsSecondTime(getApplicationContext()) || Prefs.getIs3Time(getApplicationContext())) {
            String themeName = "";
            if (BuildConfig.FLAVOR.contentEquals("hotgram") || BuildConfig.FLAVOR.contentEquals("vip")) {
                themeName = "hotgram";
            } else if (BuildConfig.FLAVOR.contentEquals(BuildConfig.FLAVOR)) {
                themeName = BuildConfig.FLAVOR;
            } else if (BuildConfig.FLAVOR.contentEquals("mowjgram")) {
                themeName = "mowjgram";
            } else if (BuildConfig.FLAVOR.contentEquals("gram")) {
                themeName = "Default";
            } else {
                themeName = "Arabgram";
            }
            int pos = 0;
            int i = 0;
            while (i < Theme.themes.size()) {
                try {
                    if (((ThemeInfo) Theme.themes.get(i)).name.contentEquals(themeName)) {
                        pos = i;
                        break;
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Theme.applyTheme((ThemeInfo) Theme.themes.get(pos));
            Prefs.setSpIsSecondTime(getApplicationContext(), false);
            Prefs.setSpIs3Time(getApplicationContext(), false);
        }
        removePreviousApkFile();
    }

    private void removePreviousApkFile() {
        File file;
        try {
            file = new File(getApplicationContext().getExternalFilesDir(null) + File.separator + getString(R.string.APK_NAME));
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }
        try {
            file = new File(Utilities.getRootFolder() + File.separator + getString(R.string.APK_NAME));
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            File dir = getApplicationContext().getExternalFilesDir(null);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (String file2 : children) {
                    new File(dir, file2).delete();
                }
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }

    public static void startPushService() {
        if (applicationContext.getSharedPreferences("Notifications", 0).getBoolean("pushService", true)) {
            applicationContext.startService(new Intent(applicationContext, NotificationsService.class));
        } else {
            stopPushService();
        }
    }

    public static void stopPushService() {
        applicationContext.stopService(new Intent(applicationContext, NotificationsService.class));
        ((AlarmManager) applicationContext.getSystemService("alarm")).cancel(PendingIntent.getService(applicationContext, 0, new Intent(applicationContext, NotificationsService.class), 0));
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            LocaleController.getInstance().onDeviceConfigurationChange(newConfig);
            AndroidUtilities.checkDisplaySize(applicationContext, newConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPlayServices() {
        AndroidUtilities.runOnUIThread(new C09051(), 1000);
    }

    private boolean checkPlayServices() {
        try {
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == 0) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return true;
        }
    }
}
