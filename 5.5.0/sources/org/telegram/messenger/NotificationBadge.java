package org.telegram.messenger;

import android.annotation.TargetApi;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;

public class NotificationBadge {
    private static final List<Class<? extends Badger>> BADGERS = new LinkedList();
    private static Badger badger;
    private static ComponentName componentName;
    private static boolean initied;

    public interface Badger {
        void executeBadge(int i);

        List<String> getSupportLaunchers();
    }

    public static class AdwHomeBadger implements Badger {
        public static final String CLASSNAME = "CNAME";
        public static final String COUNT = "COUNT";
        public static final String INTENT_UPDATE_COUNTER = "org.adw.launcher.counter.SEND";
        public static final String PACKAGENAME = "PNAME";

        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_UPDATE_COUNTER);
            intent.putExtra(PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(CLASSNAME, NotificationBadge.componentName.getClassName());
            intent.putExtra(COUNT, i);
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ApplicationLoader.applicationContext.sendBroadcast(intent);
                    }
                });
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"org.adw.launcher", "org.adwfreak.launcher"});
        }
    }

    public static class ApexHomeBadger implements Badger {
        private static final String CLASS = "class";
        private static final String COUNT = "count";
        private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
        private static final String PACKAGENAME = "package";

        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_UPDATE_COUNTER);
            intent.putExtra(PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra("count", i);
            intent.putExtra(CLASS, NotificationBadge.componentName.getClassName());
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ApplicationLoader.applicationContext.sendBroadcast(intent);
                    }
                });
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.anddoes.launcher"});
        }
    }

    public static class AsusHomeBadger implements Badger {
        private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
        private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
        private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
        private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
            intent.putExtra(INTENT_EXTRA_PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, NotificationBadge.componentName.getClassName());
            intent.putExtra("badge_vip_count", 0);
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ApplicationLoader.applicationContext.sendBroadcast(intent);
                    }
                });
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.asus.launcher"});
        }
    }

    public static class DefaultBadger implements Badger {
        private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
        private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
        private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
        private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
            intent.putExtra(INTENT_EXTRA_PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, NotificationBadge.componentName.getClassName());
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    try {
                        ApplicationLoader.applicationContext.sendBroadcast(intent);
                    } catch (Exception e) {
                    }
                }
            });
        }

        public List<String> getSupportLaunchers() {
            return new ArrayList(0);
        }
    }

    public static class HuaweiHomeBadger implements Badger {
        public void executeBadge(int i) {
            final Bundle bundle = new Bundle();
            bundle.putString("package", ApplicationLoader.applicationContext.getPackageName());
            bundle.putString("class", NotificationBadge.componentName.getClassName());
            bundle.putInt("badgenumber", i);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    try {
                        ApplicationLoader.applicationContext.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.huawei.android.launcher"});
        }
    }

    public static class NewHtcHomeBadger implements Badger {
        public static final String COUNT = "count";
        public static final String EXTRA_COMPONENT = "com.htc.launcher.extra.COMPONENT";
        public static final String EXTRA_COUNT = "com.htc.launcher.extra.COUNT";
        public static final String INTENT_SET_NOTIFICATION = "com.htc.launcher.action.SET_NOTIFICATION";
        public static final String INTENT_UPDATE_SHORTCUT = "com.htc.launcher.action.UPDATE_SHORTCUT";
        public static final String PACKAGENAME = "packagename";

        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_SET_NOTIFICATION);
            intent.putExtra(EXTRA_COMPONENT, NotificationBadge.componentName.flattenToShortString());
            intent.putExtra(EXTRA_COUNT, i);
            final Intent intent2 = new Intent(INTENT_UPDATE_SHORTCUT);
            intent2.putExtra(PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent2.putExtra(COUNT, i);
            if (NotificationBadge.canResolveBroadcast(intent) || NotificationBadge.canResolveBroadcast(intent2)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ApplicationLoader.applicationContext.sendBroadcast(intent);
                        ApplicationLoader.applicationContext.sendBroadcast(intent2);
                    }
                });
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.htc.launcher"});
        }
    }

    public static class NovaHomeBadger implements Badger {
        private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
        private static final String COUNT = "count";
        private static final String TAG = "tag";

        public void executeBadge(int i) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TAG, NotificationBadge.componentName.getPackageName() + "/" + NotificationBadge.componentName.getClassName());
            contentValues.put("count", Integer.valueOf(i));
            ApplicationLoader.applicationContext.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.teslacoilsw.launcher"});
        }
    }

    public static class OPPOHomeBader implements Badger {
        private static final String INTENT_ACTION = "com.oppo.unsettledevent";
        private static final String INTENT_EXTRA_BADGEUPGRADE_COUNT = "app_badge_count";
        private static final String INTENT_EXTRA_BADGE_COUNT = "number";
        private static final String INTENT_EXTRA_BADGE_UPGRADENUMBER = "upgradeNumber";
        private static final String INTENT_EXTRA_PACKAGENAME = "pakeageName";
        private static final String PROVIDER_CONTENT_URI = "content://com.android.badge/badge";
        private static int ROMVERSION = -1;

        private boolean checkObjExists(Object obj) {
            return obj == null || obj.toString().equals(TtmlNode.ANONYMOUS_REGION_ID) || obj.toString().trim().equals("null");
        }

        private Object executeClassLoad(Class cls, String str, Class[] clsArr, Object[] objArr) {
            Object obj = null;
            if (!(cls == null || checkObjExists(str))) {
                Method method = getMethod(cls, str, clsArr);
                if (method != null) {
                    method.setAccessible(true);
                    try {
                        obj = method.invoke(null, objArr);
                    } catch (Throwable th) {
                    }
                }
            }
            return obj;
        }

        private Class getClass(String str) {
            Class cls = null;
            try {
                cls = Class.forName(str);
            } catch (ClassNotFoundException e) {
            }
            return cls;
        }

        private Method getMethod(Class cls, String str, Class[] clsArr) {
            Method method = null;
            if (cls == null || checkObjExists(str)) {
                return method;
            }
            try {
                cls.getMethods();
                cls.getDeclaredMethods();
                return cls.getDeclaredMethod(str, clsArr);
            } catch (Exception e) {
                try {
                    return cls.getMethod(str, clsArr);
                } catch (Exception e2) {
                    return cls.getSuperclass() != null ? getMethod(cls.getSuperclass(), str, clsArr) : method;
                }
            }
        }

        private int getSupportVersion() {
            int i = ROMVERSION;
            if (i >= 0) {
                return ROMVERSION;
            }
            try {
                i = ((Integer) executeClassLoad(getClass("com.color.os.ColorBuild"), "getColorOSVERSION", null, null)).intValue();
            } catch (Exception e) {
                i = 0;
            }
            if (i == 0) {
                try {
                    String systemProperty = getSystemProperty("ro.build.version.opporom");
                    if (systemProperty.startsWith("V1.4")) {
                        return 3;
                    }
                    if (systemProperty.startsWith("V2.0")) {
                        return 4;
                    }
                    if (systemProperty.startsWith("V2.1")) {
                        return 5;
                    }
                } catch (Exception e2) {
                }
            }
            ROMVERSION = i;
            return ROMVERSION;
        }

        private String getSystemProperty(String str) {
            Closeable closeable;
            Throwable th;
            Closeable closeable2 = null;
            try {
                Closeable bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop " + str).getInputStream()), 1024);
                try {
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    NotificationBadge.closeQuietly(bufferedReader);
                    return readLine;
                } catch (Throwable th2) {
                    th = th2;
                    closeable2 = bufferedReader;
                    NotificationBadge.closeQuietly(closeable2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                NotificationBadge.closeQuietly(closeable2);
                throw th;
            }
        }

        @TargetApi(11)
        public void executeBadge(int i) {
            if (i == 0) {
                i = -1;
            }
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
            intent.putExtra(INTENT_EXTRA_BADGE_UPGRADENUMBER, i);
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ApplicationLoader.applicationContext.sendBroadcast(intent);
                    }
                });
            } else if (getSupportVersion() == 6) {
                try {
                    final Bundle bundle = new Bundle();
                    bundle.putInt(INTENT_EXTRA_BADGEUPGRADE_COUNT, i);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            try {
                                ApplicationLoader.applicationContext.getContentResolver().call(Uri.parse(OPPOHomeBader.PROVIDER_CONTENT_URI), "setAppBadgeCount", null, bundle);
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    });
                } catch (Throwable th) {
                }
            }
        }

        public List<String> getSupportLaunchers() {
            return Collections.singletonList("com.oppo.launcher");
        }
    }

    public static class SamsungHomeBadger implements Badger {
        private static final String[] CONTENT_PROJECTION = new String[]{"_id", "class"};
        private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";
        private static DefaultBadger defaultBadger;

        private ContentValues getContentValues(ComponentName componentName, int i, boolean z) {
            ContentValues contentValues = new ContentValues();
            if (z) {
                contentValues.put("package", componentName.getPackageName());
                contentValues.put("class", componentName.getClassName());
            }
            contentValues.put("badgecount", Integer.valueOf(i));
            return contentValues;
        }

        public void executeBadge(int i) {
            Throwable th;
            Cursor cursor;
            try {
                if (defaultBadger == null) {
                    defaultBadger = new DefaultBadger();
                }
                defaultBadger.executeBadge(i);
            } catch (Exception e) {
            }
            Uri parse = Uri.parse(CONTENT_URI);
            ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
            try {
                Cursor query = contentResolver.query(parse, CONTENT_PROJECTION, "package=?", new String[]{NotificationBadge.componentName.getPackageName()}, null);
                if (query != null) {
                    try {
                        String className = NotificationBadge.componentName.getClassName();
                        Object obj = null;
                        while (query.moveToNext()) {
                            int i2 = query.getInt(0);
                            contentResolver.update(parse, getContentValues(NotificationBadge.componentName, i, false), "_id=?", new String[]{String.valueOf(i2)});
                            if (className.equals(query.getString(query.getColumnIndex("class")))) {
                                obj = 1;
                            }
                        }
                        if (obj == null) {
                            contentResolver.insert(parse, getContentValues(NotificationBadge.componentName, i, true));
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = query;
                        NotificationBadge.close(cursor);
                        throw th;
                    }
                }
                NotificationBadge.close(query);
            } catch (Throwable th3) {
                th = th3;
                cursor = null;
                NotificationBadge.close(cursor);
                throw th;
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.sec.android.app.launcher", "com.sec.android.app.twlauncher"});
        }
    }

    public static class SonyHomeBadger implements Badger {
        private static final String INTENT_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE";
        private static final String INTENT_EXTRA_ACTIVITY_NAME = "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME";
        private static final String INTENT_EXTRA_MESSAGE = "com.sonyericsson.home.intent.extra.badge.MESSAGE";
        private static final String INTENT_EXTRA_PACKAGE_NAME = "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME";
        private static final String INTENT_EXTRA_SHOW_MESSAGE = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE";
        private static final String PROVIDER_COLUMNS_ACTIVITY_NAME = "activity_name";
        private static final String PROVIDER_COLUMNS_BADGE_COUNT = "badge_count";
        private static final String PROVIDER_COLUMNS_PACKAGE_NAME = "package_name";
        private static final String PROVIDER_CONTENT_URI = "content://com.sonymobile.home.resourceprovider/badge";
        private static final String SONY_HOME_PROVIDER_NAME = "com.sonymobile.home.resourceprovider";
        private static AsyncQueryHandler mQueryHandler;
        private final Uri BADGE_CONTENT_URI = Uri.parse(PROVIDER_CONTENT_URI);

        private static void executeBadgeByBroadcast(int i) {
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_PACKAGE_NAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, NotificationBadge.componentName.getClassName());
            intent.putExtra(INTENT_EXTRA_MESSAGE, String.valueOf(i));
            intent.putExtra(INTENT_EXTRA_SHOW_MESSAGE, i > 0);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ApplicationLoader.applicationContext.sendBroadcast(intent);
                }
            });
        }

        private void executeBadgeByContentProvider(int i) {
            if (i >= 0) {
                if (mQueryHandler == null) {
                    mQueryHandler = new AsyncQueryHandler(ApplicationLoader.applicationContext.getApplicationContext().getContentResolver()) {
                        public void handleMessage(Message message) {
                            try {
                                super.handleMessage(message);
                            } catch (Throwable th) {
                            }
                        }
                    };
                }
                insertBadgeAsync(i, NotificationBadge.componentName.getPackageName(), NotificationBadge.componentName.getClassName());
            }
        }

        private void insertBadgeAsync(int i, String str, String str2) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROVIDER_COLUMNS_BADGE_COUNT, Integer.valueOf(i));
            contentValues.put(PROVIDER_COLUMNS_PACKAGE_NAME, str);
            contentValues.put(PROVIDER_COLUMNS_ACTIVITY_NAME, str2);
            mQueryHandler.startInsert(0, null, this.BADGE_CONTENT_URI, contentValues);
        }

        private static boolean sonyBadgeContentProviderExists() {
            return ApplicationLoader.applicationContext.getPackageManager().resolveContentProvider(SONY_HOME_PROVIDER_NAME, 0) != null;
        }

        public void executeBadge(int i) {
            if (sonyBadgeContentProviderExists()) {
                executeBadgeByContentProvider(i);
            } else {
                executeBadgeByBroadcast(i);
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.sonyericsson.home", "com.sonymobile.home"});
        }
    }

    public static class VivoHomeBadger implements Badger {
        public void executeBadge(int i) {
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", ApplicationLoader.applicationContext.getPackageName());
            intent.putExtra("className", NotificationBadge.componentName.getClassName());
            intent.putExtra("notificationNum", i);
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.vivo.launcher"});
        }
    }

    public static class XiaomiHomeBadger implements Badger {
        public static final String EXTRA_UPDATE_APP_COMPONENT_NAME = "android.intent.extra.update_application_component_name";
        public static final String EXTRA_UPDATE_APP_MSG_TEXT = "android.intent.extra.update_application_message_text";
        public static final String INTENT_ACTION = "android.intent.action.APPLICATION_MESSAGE_UPDATE";

        public void executeBadge(int i) {
            try {
                Object newInstance = Class.forName("android.app.MiuiNotification").newInstance();
                Field declaredField = newInstance.getClass().getDeclaredField("messageCount");
                declaredField.setAccessible(true);
                declaredField.set(newInstance, String.valueOf(i == 0 ? TtmlNode.ANONYMOUS_REGION_ID : Integer.valueOf(i)));
            } catch (Throwable th) {
                final Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra(EXTRA_UPDATE_APP_COMPONENT_NAME, NotificationBadge.componentName.getPackageName() + "/" + NotificationBadge.componentName.getClassName());
                intent.putExtra(EXTRA_UPDATE_APP_MSG_TEXT, String.valueOf(i == 0 ? TtmlNode.ANONYMOUS_REGION_ID : Integer.valueOf(i)));
                if (NotificationBadge.canResolveBroadcast(intent)) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ApplicationLoader.applicationContext.sendBroadcast(intent);
                        }
                    });
                }
            }
        }

        public List<String> getSupportLaunchers() {
            return Arrays.asList(new String[]{"com.miui.miuilite", "com.miui.home", "com.miui.miuihome", "com.miui.miuihome2", "com.miui.mihome", "com.miui.mihome2"});
        }
    }

    public static class ZukHomeBadger implements Badger {
        private final Uri CONTENT_URI = Uri.parse("content://com.android.badge/badge");

        @TargetApi(11)
        public void executeBadge(int i) {
            final Bundle bundle = new Bundle();
            bundle.putInt("app_badge_count", i);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    try {
                        ApplicationLoader.applicationContext.getContentResolver().call(ZukHomeBadger.this.CONTENT_URI, "setAppBadgeCount", null, bundle);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }

        public List<String> getSupportLaunchers() {
            return Collections.singletonList("com.zui.launcher");
        }
    }

    static {
        BADGERS.add(AdwHomeBadger.class);
        BADGERS.add(ApexHomeBadger.class);
        BADGERS.add(NewHtcHomeBadger.class);
        BADGERS.add(NovaHomeBadger.class);
        BADGERS.add(SonyHomeBadger.class);
        BADGERS.add(XiaomiHomeBadger.class);
        BADGERS.add(AsusHomeBadger.class);
        BADGERS.add(HuaweiHomeBadger.class);
        BADGERS.add(OPPOHomeBader.class);
        BADGERS.add(SamsungHomeBadger.class);
        BADGERS.add(ZukHomeBadger.class);
        BADGERS.add(VivoHomeBadger.class);
    }

    public static boolean applyCount(int i) {
        try {
            if (badger == null && !initied) {
                initBadger();
                initied = true;
            }
            if (badger == null) {
                return false;
            }
            badger.executeBadge(i);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static boolean canResolveBroadcast(Intent intent) {
        List queryBroadcastReceivers = ApplicationLoader.applicationContext.getPackageManager().queryBroadcastReceivers(intent, 0);
        return queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0;
    }

    public static void close(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }

    private static boolean initBadger() {
        Context context = ApplicationLoader.applicationContext;
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage == null) {
            return false;
        }
        componentName = launchIntentForPackage.getComponent();
        launchIntentForPackage = new Intent("android.intent.action.MAIN");
        launchIntentForPackage.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(launchIntentForPackage, C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
        if (resolveActivity == null || resolveActivity.activityInfo.name.toLowerCase().contains("resolver")) {
            return false;
        }
        String str = resolveActivity.activityInfo.packageName;
        for (Class newInstance : BADGERS) {
            Badger badger;
            try {
                badger = (Badger) newInstance.newInstance();
            } catch (Exception e) {
                badger = null;
            }
            if (badger != null && badger.getSupportLaunchers().contains(str)) {
                badger = badger;
                break;
            }
        }
        if (badger == null) {
            if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                badger = new XiaomiHomeBadger();
            } else if (Build.MANUFACTURER.equalsIgnoreCase("ZUK")) {
                badger = new ZukHomeBadger();
            } else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
                badger = new OPPOHomeBader();
            } else if (Build.MANUFACTURER.equalsIgnoreCase("VIVO")) {
                badger = new VivoHomeBadger();
            } else {
                badger = new DefaultBadger();
            }
        }
        return true;
    }
}
