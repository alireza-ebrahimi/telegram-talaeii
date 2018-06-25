package com.onesignal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.HashMap;

class OneSignalPrefs {
    static final String PREFS_EXISTING_PURCHASES = "ExistingPurchases";
    static final String PREFS_GT_APP_ID = "GT_APP_ID";
    static final String PREFS_GT_DO_NOT_SHOW_MISSING_GPS = "GT_DO_NOT_SHOW_MISSING_GPS";
    static final String PREFS_GT_FIREBASE_TRACKING_ENABLED = "GT_FIREBASE_TRACKING_ENABLED";
    static final String PREFS_GT_PLAYER_ID = "GT_PLAYER_ID";
    static final String PREFS_GT_REGISTRATION_ID = "GT_REGISTRATION_ID";
    static final String PREFS_GT_SOUND_ENABLED = "GT_SOUND_ENABLED";
    static final String PREFS_GT_UNSENT_ACTIVE_TIME = "GT_UNSENT_ACTIVE_TIME";
    static final String PREFS_GT_VIBRATE_ENABLED = "GT_VIBRATE_ENABLED";
    static final String PREFS_ONESIGNAL = OneSignal.class.getSimpleName();
    static final String PREFS_ONESIGNAL_ACCEPTED_NOTIFICATION_LAST = "ONESIGNAL_ACCEPTED_NOTIFICATION_LAST";
    static final String PREFS_ONESIGNAL_EMAIL_ADDRESS_LAST = "PREFS_ONESIGNAL_EMAIL_ADDRESS_LAST";
    static final String PREFS_ONESIGNAL_EMAIL_ID_LAST = "PREFS_ONESIGNAL_EMAIL_ID_LAST";
    static final String PREFS_ONESIGNAL_PERMISSION_ACCEPTED_LAST = "ONESIGNAL_PERMISSION_ACCEPTED_LAST";
    static final String PREFS_ONESIGNAL_PLAYER_ID_LAST = "ONESIGNAL_PLAYER_ID_LAST";
    static final String PREFS_ONESIGNAL_PUSH_TOKEN_LAST = "ONESIGNAL_PUSH_TOKEN_LAST";
    static final String PREFS_ONESIGNAL_SUBSCRIPTION = "ONESIGNAL_SUBSCRIPTION";
    static final String PREFS_ONESIGNAL_SUBSCRIPTION_LAST = "ONESIGNAL_SUBSCRIPTION_LAST";
    static final String PREFS_ONESIGNAL_SYNCED_SUBSCRIPTION = "ONESIGNAL_SYNCED_SUBSCRIPTION";
    static final String PREFS_ONESIGNAL_USERSTATE_DEPENDVALYES_ = "ONESIGNAL_USERSTATE_DEPENDVALYES_";
    static final String PREFS_ONESIGNAL_USERSTATE_SYNCVALYES_ = "ONESIGNAL_USERSTATE_SYNCVALYES_";
    static final String PREFS_OS_EMAIL_ID = "OS_EMAIL_ID";
    static final String PREFS_OS_FILTER_OTHER_GCM_RECEIVERS = "OS_FILTER_OTHER_GCM_RECEIVERS";
    static final String PREFS_OS_LAST_LOCATION_TIME = "OS_LAST_LOCATION_TIME";
    static final String PREFS_OS_LAST_SESSION_TIME = "OS_LAST_SESSION_TIME";
    static final String PREFS_PLAYER_PURCHASES = "GTPlayerPurchases";
    static final String PREFS_PURCHASE_TOKENS = "purchaseTokens";
    public static WritePrefHandlerThread prefsHandler;
    static HashMap<String, HashMap<String, Object>> prefsToApply;

    public static class WritePrefHandlerThread extends HandlerThread {
        private static final int WRITE_CALL_DELAY_TO_BUFFER_MS = 200;
        private long lastSyncTime = 0;
        public Handler mHandler;

        /* renamed from: com.onesignal.OneSignalPrefs$WritePrefHandlerThread$1 */
        class C06821 implements Runnable {
            C06821() {
            }

            public void run() {
                WritePrefHandlerThread.this.flushBufferToDisk();
            }
        }

        WritePrefHandlerThread() {
            super("OSH_WritePrefs");
            start();
            this.mHandler = new Handler(getLooper());
        }

        void startDelayedWrite() {
            synchronized (this.mHandler) {
                this.mHandler.removeCallbacksAndMessages(null);
                if (this.lastSyncTime == 0) {
                    this.lastSyncTime = System.currentTimeMillis();
                }
                this.mHandler.postDelayed(getNewRunnable(), (this.lastSyncTime - System.currentTimeMillis()) + 200);
            }
        }

        private Runnable getNewRunnable() {
            return new C06821();
        }

        private void flushBufferToDisk() {
            for (String pref : OneSignalPrefs.prefsToApply.keySet()) {
                Editor editor = OneSignalPrefs.getSharedPrefsByName(pref).edit();
                HashMap<String, Object> prefHash = (HashMap) OneSignalPrefs.prefsToApply.get(pref);
                synchronized (prefHash) {
                    for (String key : prefHash.keySet()) {
                        Object value = prefHash.get(key);
                        if (value instanceof String) {
                            editor.putString(key, (String) value);
                        } else if (value instanceof Boolean) {
                            editor.putBoolean(key, ((Boolean) value).booleanValue());
                        } else if (value instanceof Integer) {
                            editor.putInt(key, ((Integer) value).intValue());
                        } else if (value instanceof Long) {
                            editor.putLong(key, ((Long) value).longValue());
                        }
                    }
                    prefHash.clear();
                }
                editor.apply();
            }
            this.lastSyncTime = System.currentTimeMillis();
        }
    }

    OneSignalPrefs() {
    }

    static {
        initializePool();
    }

    public static void initializePool() {
        prefsToApply = new HashMap();
        prefsToApply.put(PREFS_ONESIGNAL, new HashMap());
        prefsToApply.put(PREFS_PLAYER_PURCHASES, new HashMap());
        prefsHandler = new WritePrefHandlerThread();
    }

    static void saveString(String prefsName, String key, String value) {
        save(prefsName, key, value);
    }

    static void saveBool(String prefsName, String key, boolean value) {
        save(prefsName, key, Boolean.valueOf(value));
    }

    static void saveInt(String prefsName, String key, int value) {
        save(prefsName, key, Integer.valueOf(value));
    }

    static void saveLong(String prefsName, String key, long value) {
        save(prefsName, key, Long.valueOf(value));
    }

    private static void save(String prefsName, String key, Object value) {
        HashMap<String, Object> pref = (HashMap) prefsToApply.get(prefsName);
        synchronized (pref) {
            pref.put(key, value);
        }
        prefsHandler.startDelayedWrite();
    }

    static String getString(String prefsName, String key, String defValue) {
        return (String) get(prefsName, key, String.class, defValue);
    }

    static boolean getBool(String prefsName, String key, boolean defValue) {
        return ((Boolean) get(prefsName, key, Boolean.class, Boolean.valueOf(defValue))).booleanValue();
    }

    static int getInt(String prefsName, String key, int defValue) {
        return ((Integer) get(prefsName, key, Integer.class, Integer.valueOf(defValue))).intValue();
    }

    static long getLong(String prefsName, String key, long defValue) {
        return ((Long) get(prefsName, key, Long.class, Long.valueOf(defValue))).longValue();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object get(java.lang.String r6, java.lang.String r7, java.lang.Class r8, java.lang.Object r9) {
        /*
        r3 = prefsToApply;
        r1 = r3.get(r6);
        r1 = (java.util.HashMap) r1;
        monitor-enter(r1);
        r3 = java.lang.Object.class;
        r3 = r8.equals(r3);	 Catch:{ all -> 0x0043 }
        if (r3 == 0) goto L_0x001e;
    L_0x0011:
        r3 = r1.containsKey(r7);	 Catch:{ all -> 0x0043 }
        if (r3 == 0) goto L_0x001e;
    L_0x0017:
        r3 = 1;
        r9 = java.lang.Boolean.valueOf(r3);	 Catch:{ all -> 0x0043 }
        monitor-exit(r1);	 Catch:{ all -> 0x0043 }
    L_0x001d:
        return r9;
    L_0x001e:
        r0 = r1.get(r7);	 Catch:{ all -> 0x0043 }
        if (r0 != 0) goto L_0x002a;
    L_0x0024:
        r3 = r1.containsKey(r7);	 Catch:{ all -> 0x0043 }
        if (r3 == 0) goto L_0x002d;
    L_0x002a:
        monitor-exit(r1);	 Catch:{ all -> 0x0043 }
        r9 = r0;
        goto L_0x001d;
    L_0x002d:
        monitor-exit(r1);	 Catch:{ all -> 0x0043 }
        r2 = getSharedPrefsByName(r6);
        if (r2 == 0) goto L_0x001d;
    L_0x0034:
        r3 = java.lang.String.class;
        r3 = r8.equals(r3);
        if (r3 == 0) goto L_0x0046;
    L_0x003c:
        r9 = (java.lang.String) r9;
        r9 = r2.getString(r7, r9);
        goto L_0x001d;
    L_0x0043:
        r3 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0043 }
        throw r3;
    L_0x0046:
        r3 = java.lang.Boolean.class;
        r3 = r8.equals(r3);
        if (r3 == 0) goto L_0x005d;
    L_0x004e:
        r9 = (java.lang.Boolean) r9;
        r3 = r9.booleanValue();
        r3 = r2.getBoolean(r7, r3);
        r9 = java.lang.Boolean.valueOf(r3);
        goto L_0x001d;
    L_0x005d:
        r3 = java.lang.Integer.class;
        r3 = r8.equals(r3);
        if (r3 == 0) goto L_0x0074;
    L_0x0065:
        r9 = (java.lang.Integer) r9;
        r3 = r9.intValue();
        r3 = r2.getInt(r7, r3);
        r9 = java.lang.Integer.valueOf(r3);
        goto L_0x001d;
    L_0x0074:
        r3 = java.lang.Long.class;
        r3 = r8.equals(r3);
        if (r3 == 0) goto L_0x008b;
    L_0x007c:
        r9 = (java.lang.Long) r9;
        r4 = r9.longValue();
        r4 = r2.getLong(r7, r4);
        r9 = java.lang.Long.valueOf(r4);
        goto L_0x001d;
    L_0x008b:
        r3 = java.lang.Object.class;
        r3 = r8.equals(r3);
        if (r3 == 0) goto L_0x009c;
    L_0x0093:
        r3 = r2.contains(r7);
        r9 = java.lang.Boolean.valueOf(r3);
        goto L_0x001d;
    L_0x009c:
        r9 = 0;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalPrefs.get(java.lang.String, java.lang.String, java.lang.Class, java.lang.Object):java.lang.Object");
    }

    private static synchronized SharedPreferences getSharedPrefsByName(String prefsName) {
        SharedPreferences sharedPreferences;
        synchronized (OneSignalPrefs.class) {
            if (OneSignal.appContext == null) {
                sharedPreferences = null;
            } else {
                sharedPreferences = OneSignal.appContext.getSharedPreferences(prefsName, 0);
            }
        }
        return sharedPreferences;
    }
}
