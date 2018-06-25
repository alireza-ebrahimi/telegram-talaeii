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
import java.io.File;
import org.ir.talaeii.R;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p151g.C2820e;
import org.telegram.customization.p159b.C2666a;
import org.telegram.customization.service.ProxyService;
import org.telegram.customization.util.C2885i;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
import org.telegram.ui.Components.ForegroundDetector;
import utils.C3792d;

public class ApplicationLoader extends C2666a {
    @SuppressLint({"StaticFieldLeak"})
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;
    private static volatile boolean applicationInited = false;
    public static volatile boolean isScreenOn = false;
    public static volatile boolean mainInterfacePaused = true;
    public static volatile boolean mainInterfacePausedStageQueue = true;
    public static volatile long mainInterfacePausedStageQueueTime;
    public C2820e slsProxyHelper = null;

    /* renamed from: org.telegram.messenger.ApplicationLoader$1 */
    class C29781 implements Runnable {
        C29781() {
        }

        public void run() {
            if (ApplicationLoader.this.checkPlayServices()) {
                if (UserConfig.pushString == null || UserConfig.pushString.length() == 0) {
                    FileLog.m13725d("GCM Registration not found.");
                } else {
                    FileLog.m13725d("GCM regId = " + UserConfig.pushString);
                }
                ApplicationLoader.this.startService(new Intent(ApplicationLoader.applicationContext, GcmRegistrationIntentService.class));
                return;
            }
            FileLog.m13725d("No valid Google Play Services APK found.");
        }
    }

    private boolean checkPlayServices() {
        try {
            return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == 0;
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return true;
        }
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    public static File getFilesDirFixed() {
        for (int i = 0; i < 10; i++) {
            File filesDir = applicationContext.getFilesDir();
            if (filesDir != null) {
                return filesDir;
            }
        }
        try {
            filesDir = new File(applicationContext.getApplicationInfo().dataDir, "files");
            filesDir.mkdirs();
            return filesDir;
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return new File("/data/data/org.telegram.messenger/files");
        }
    }

    private void initPlayServices() {
        AndroidUtilities.runOnUIThread(new C29781(), 1000);
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
                IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                applicationContext.registerReceiver(new ScreenReceiver(), intentFilter);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                isScreenOn = ((PowerManager) applicationContext.getSystemService("power")).isScreenOn();
                FileLog.m13726e("screen state = " + isScreenOn);
            } catch (Throwable e3) {
                FileLog.m13728e(e3);
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
            FileLog.m13726e("app initied");
            ContactsController.getInstance().checkAppAccount();
            MediaController.getInstance();
        }
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
            file = new File(C3792d.m14076a() + File.separator + getString(R.string.APK_NAME));
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            File externalFilesDir = getApplicationContext().getExternalFilesDir(null);
            if (externalFilesDir.isDirectory()) {
                String[] list = externalFilesDir.list();
                for (String file2 : list) {
                    new File(externalFilesDir, file2).delete();
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

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        try {
            LocaleController.getInstance().onDeviceConfigurationChange(configuration);
            AndroidUtilities.checkDisplaySize(applicationContext, configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreate() {
        int i = 0;
        super.onCreate();
        applicationContext = getApplicationContext();
        NativeLoader.initNativeLibs(applicationContext);
        boolean z = VERSION.SDK_INT == 14 || VERSION.SDK_INT == 15;
        ConnectionsManager.native_setJava(z);
        ForegroundDetector foregroundDetector = new ForegroundDetector(this);
        applicationHandler = new Handler(applicationContext.getMainLooper());
        startPushService();
        if (BuildConfig.FLAVOR.contentEquals("gram")) {
            Editor edit = applicationContext.getSharedPreferences("plusconfig", 0).edit();
            edit.putBoolean("hideTabs", true);
            edit.apply();
        }
        ProxyService.m13192c(getApplicationContext());
        if (C2885i.m13380c(getApplicationContext())) {
            C2885i.m13375a(getApplicationContext(), false);
            C2885i.m13372a(getApplicationContext(), 0);
            C2818c.m13087a(getApplicationContext(), (C2497d) this).m13124b();
        }
        if (C2885i.m13382d(getApplicationContext()) || C2885i.m13390j(getApplicationContext())) {
            CharSequence charSequence;
            String str = TtmlNode.ANONYMOUS_REGION_ID;
            if (BuildConfig.FLAVOR.contentEquals("hotgram") || BuildConfig.FLAVOR.contentEquals("vip")) {
                charSequence = "hotgram";
            } else {
                Object obj = BuildConfig.FLAVOR.contentEquals(BuildConfig.FLAVOR) ? BuildConfig.FLAVOR : BuildConfig.FLAVOR.contentEquals("mowjgram") ? "mowjgram" : BuildConfig.FLAVOR.contentEquals("gram") ? "Default" : "Arabgram";
            }
            int i2 = 0;
            while (i2 < Theme.themes.size()) {
                try {
                    if (((ThemeInfo) Theme.themes.get(i2)).name.contentEquals(charSequence)) {
                        i = i2;
                        break;
                    }
                    i2++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Theme.applyTheme((ThemeInfo) Theme.themes.get(i));
            C2885i.m13378b(getApplicationContext(), false);
            C2885i.m13381d(getApplicationContext(), false);
        }
        removePreviousApkFile();
    }
}
