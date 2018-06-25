package org.telegram.messenger;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLRPC.TL_account_tmpPassword;
import org.telegram.tgnet.TLRPC.User;

public class UserConfig {
    public static boolean allowScreenCapture;
    public static boolean appLocked;
    public static int autoLockIn = 3600;
    public static boolean blockedUsersLoaded;
    public static int botRatingLoadTime;
    private static boolean configLoaded;
    public static boolean contactsReimported;
    public static int contactsSavedCount;
    private static User currentUser;
    public static long dialogsLoadOffsetAccess = 0;
    public static int dialogsLoadOffsetChannelId = 0;
    public static int dialogsLoadOffsetChatId = 0;
    public static int dialogsLoadOffsetDate = 0;
    public static int dialogsLoadOffsetId = 0;
    public static int dialogsLoadOffsetUserId = 0;
    public static boolean draftsLoaded;
    public static boolean isWaitingForPasscodeEnter;
    public static long lastAppPauseTime;
    public static int lastBroadcastId = -1;
    public static int lastContactsSyncTime;
    public static int lastHintsSyncTime;
    public static int lastLocalId = -210000;
    public static int lastPauseTime;
    public static int lastSendMessageId = -210000;
    public static String lastUpdateVersion;
    public static long migrateOffsetAccess = -1;
    public static int migrateOffsetChannelId = -1;
    public static int migrateOffsetChatId = -1;
    public static int migrateOffsetDate = -1;
    public static int migrateOffsetId = -1;
    public static int migrateOffsetUserId = -1;
    public static boolean notificationsConverted = true;
    public static String passcodeHash = TtmlNode.ANONYMOUS_REGION_ID;
    public static byte[] passcodeSalt = new byte[0];
    public static int passcodeType;
    public static boolean pinnedDialogsLoaded = true;
    public static String pushString = TtmlNode.ANONYMOUS_REGION_ID;
    public static int ratingLoadTime;
    public static boolean registeredForPush;
    public static boolean saveIncomingPhotos;
    private static final Object sync = new Object();
    public static TL_account_tmpPassword tmpPassword;
    public static int totalDialogsLoadCount = 0;
    public static boolean useFingerprint = true;

    public static boolean checkPasscode(String str) {
        boolean z = false;
        Object bytes;
        Object obj;
        if (passcodeSalt.length == 0) {
            z = Utilities.MD5(str).equals(passcodeHash);
            if (z) {
                try {
                    passcodeSalt = new byte[16];
                    Utilities.random.nextBytes(passcodeSalt);
                    bytes = str.getBytes(C3446C.UTF8_NAME);
                    obj = new byte[(bytes.length + 32)];
                    System.arraycopy(passcodeSalt, 0, obj, 0, 16);
                    System.arraycopy(bytes, 0, obj, 16, bytes.length);
                    System.arraycopy(passcodeSalt, 0, obj, bytes.length + 16, 16);
                    passcodeHash = Utilities.bytesToHex(Utilities.computeSHA256(obj, 0, obj.length));
                    saveConfig(false);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        } else {
            try {
                bytes = str.getBytes(C3446C.UTF8_NAME);
                obj = new byte[(bytes.length + 32)];
                System.arraycopy(passcodeSalt, 0, obj, 0, 16);
                System.arraycopy(bytes, 0, obj, 16, bytes.length);
                System.arraycopy(passcodeSalt, 0, obj, bytes.length + 16, 16);
                z = passcodeHash.equals(Utilities.bytesToHex(Utilities.computeSHA256(obj, 0, obj.length)));
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
        }
        return z;
    }

    public static void clearConfig() {
        currentUser = null;
        registeredForPush = false;
        contactsSavedCount = 0;
        lastSendMessageId = -210000;
        lastBroadcastId = -1;
        saveIncomingPhotos = false;
        blockedUsersLoaded = false;
        migrateOffsetId = -1;
        migrateOffsetDate = -1;
        migrateOffsetUserId = -1;
        migrateOffsetChatId = -1;
        migrateOffsetChannelId = -1;
        migrateOffsetAccess = -1;
        dialogsLoadOffsetId = 0;
        totalDialogsLoadCount = 0;
        dialogsLoadOffsetDate = 0;
        dialogsLoadOffsetUserId = 0;
        dialogsLoadOffsetChatId = 0;
        dialogsLoadOffsetChannelId = 0;
        dialogsLoadOffsetAccess = 0;
        ratingLoadTime = 0;
        botRatingLoadTime = 0;
        appLocked = false;
        passcodeType = 0;
        passcodeHash = TtmlNode.ANONYMOUS_REGION_ID;
        passcodeSalt = new byte[0];
        autoLockIn = 3600;
        lastPauseTime = 0;
        useFingerprint = true;
        draftsLoaded = true;
        notificationsConverted = true;
        contactsReimported = true;
        isWaitingForPasscodeEnter = false;
        allowScreenCapture = false;
        pinnedDialogsLoaded = false;
        lastUpdateVersion = BuildVars.BUILD_VERSION_STRING;
        lastContactsSyncTime = ((int) (System.currentTimeMillis() / 1000)) - 82800;
        lastHintsSyncTime = ((int) (System.currentTimeMillis() / 1000)) - 90000;
        saveConfig(true);
    }

    public static int getClientUserId() {
        int i;
        synchronized (sync) {
            i = currentUser != null ? currentUser.id : 0;
        }
        return i;
    }

    public static User getCurrentUser() {
        User user;
        synchronized (sync) {
            user = currentUser;
        }
        return user;
    }

    public static int getNewMessageId() {
        int i;
        synchronized (sync) {
            i = lastSendMessageId;
            lastSendMessageId--;
        }
        return i;
    }

    public static boolean isClientActivated() {
        boolean z;
        synchronized (sync) {
            z = currentUser != null;
        }
        return z;
    }

    public static void loadConfig() {
        synchronized (sync) {
            if (configLoaded) {
                return;
            }
            final File file = new File(ApplicationLoader.getFilesDirFixed(), "user.dat");
            if (file.exists()) {
                try {
                    AbstractSerializedData serializedData = new SerializedData(file);
                    int readInt32 = serializedData.readInt32(false);
                    if (readInt32 == 1) {
                        currentUser = User.TLdeserialize(serializedData, serializedData.readInt32(false), false);
                        MessagesStorage.lastDateValue = serializedData.readInt32(false);
                        MessagesStorage.lastPtsValue = serializedData.readInt32(false);
                        MessagesStorage.lastSeqValue = serializedData.readInt32(false);
                        registeredForPush = serializedData.readBool(false);
                        pushString = serializedData.readString(false);
                        lastSendMessageId = serializedData.readInt32(false);
                        lastLocalId = serializedData.readInt32(false);
                        serializedData.readString(false);
                        serializedData.readString(false);
                        saveIncomingPhotos = serializedData.readBool(false);
                        MessagesStorage.lastQtsValue = serializedData.readInt32(false);
                        MessagesStorage.lastSecretVersion = serializedData.readInt32(false);
                        if (serializedData.readInt32(false) == 1) {
                            MessagesStorage.secretPBytes = serializedData.readByteArray(false);
                        }
                        MessagesStorage.secretG = serializedData.readInt32(false);
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                UserConfig.saveConfig(true, file);
                            }
                        });
                    } else if (readInt32 == 2) {
                        currentUser = User.TLdeserialize(serializedData, serializedData.readInt32(false), false);
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", 0);
                        registeredForPush = sharedPreferences.getBoolean("registeredForPush", false);
                        pushString = sharedPreferences.getString("pushString2", TtmlNode.ANONYMOUS_REGION_ID);
                        lastSendMessageId = sharedPreferences.getInt("lastSendMessageId", -210000);
                        lastLocalId = sharedPreferences.getInt("lastLocalId", -210000);
                        contactsSavedCount = sharedPreferences.getInt("contactsHash", 0);
                        saveIncomingPhotos = sharedPreferences.getBoolean("saveIncomingPhotos", false);
                    }
                    if (lastLocalId > -210000) {
                        lastLocalId = -210000;
                    }
                    if (lastSendMessageId > -210000) {
                        lastSendMessageId = -210000;
                    }
                    serializedData.cleanup();
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            UserConfig.saveConfig(true, file);
                        }
                    });
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            } else {
                byte[] decode;
                AbstractSerializedData serializedData2;
                SharedPreferences sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", 0);
                registeredForPush = sharedPreferences2.getBoolean("registeredForPush", false);
                pushString = sharedPreferences2.getString("pushString2", TtmlNode.ANONYMOUS_REGION_ID);
                lastSendMessageId = sharedPreferences2.getInt("lastSendMessageId", -210000);
                lastLocalId = sharedPreferences2.getInt("lastLocalId", -210000);
                contactsSavedCount = sharedPreferences2.getInt("contactsSavedCount", 0);
                saveIncomingPhotos = sharedPreferences2.getBoolean("saveIncomingPhotos", false);
                lastBroadcastId = sharedPreferences2.getInt("lastBroadcastId", -1);
                blockedUsersLoaded = sharedPreferences2.getBoolean("blockedUsersLoaded", false);
                passcodeHash = sharedPreferences2.getString("passcodeHash1", TtmlNode.ANONYMOUS_REGION_ID);
                appLocked = sharedPreferences2.getBoolean("appLocked", false);
                passcodeType = sharedPreferences2.getInt("passcodeType", 0);
                autoLockIn = sharedPreferences2.getInt("autoLockIn", 3600);
                lastPauseTime = sharedPreferences2.getInt("lastPauseTime", 0);
                lastAppPauseTime = sharedPreferences2.getLong("lastAppPauseTime", 0);
                useFingerprint = sharedPreferences2.getBoolean("useFingerprint", true);
                lastUpdateVersion = sharedPreferences2.getString("lastUpdateVersion2", "3.5");
                lastContactsSyncTime = sharedPreferences2.getInt("lastContactsSyncTime", ((int) (System.currentTimeMillis() / 1000)) - 82800);
                lastHintsSyncTime = sharedPreferences2.getInt("lastHintsSyncTime", ((int) (System.currentTimeMillis() / 1000)) - 90000);
                draftsLoaded = sharedPreferences2.getBoolean("draftsLoaded", false);
                notificationsConverted = sharedPreferences2.getBoolean("notificationsConverted", false);
                allowScreenCapture = sharedPreferences2.getBoolean("allowScreenCapture", false);
                pinnedDialogsLoaded = sharedPreferences2.getBoolean("pinnedDialogsLoaded", false);
                contactsReimported = sharedPreferences2.getBoolean("contactsReimported", false);
                ratingLoadTime = sharedPreferences2.getInt("ratingLoadTime", 0);
                botRatingLoadTime = sharedPreferences2.getInt("botRatingLoadTime", 0);
                if (passcodeHash.length() > 0 && lastPauseTime == 0) {
                    lastPauseTime = (int) ((System.currentTimeMillis() / 1000) - 600);
                }
                migrateOffsetId = sharedPreferences2.getInt("3migrateOffsetId", 0);
                if (migrateOffsetId != -1) {
                    migrateOffsetDate = sharedPreferences2.getInt("3migrateOffsetDate", 0);
                    migrateOffsetUserId = sharedPreferences2.getInt("3migrateOffsetUserId", 0);
                    migrateOffsetChatId = sharedPreferences2.getInt("3migrateOffsetChatId", 0);
                    migrateOffsetChannelId = sharedPreferences2.getInt("3migrateOffsetChannelId", 0);
                    migrateOffsetAccess = sharedPreferences2.getLong("3migrateOffsetAccess", 0);
                }
                dialogsLoadOffsetId = sharedPreferences2.getInt("2dialogsLoadOffsetId", -1);
                totalDialogsLoadCount = sharedPreferences2.getInt("2totalDialogsLoadCount", 0);
                dialogsLoadOffsetDate = sharedPreferences2.getInt("2dialogsLoadOffsetDate", -1);
                dialogsLoadOffsetUserId = sharedPreferences2.getInt("2dialogsLoadOffsetUserId", -1);
                dialogsLoadOffsetChatId = sharedPreferences2.getInt("2dialogsLoadOffsetChatId", -1);
                dialogsLoadOffsetChannelId = sharedPreferences2.getInt("2dialogsLoadOffsetChannelId", -1);
                dialogsLoadOffsetAccess = sharedPreferences2.getLong("2dialogsLoadOffsetAccess", -1);
                String string = sharedPreferences2.getString("tmpPassword", null);
                if (string != null) {
                    decode = Base64.decode(string, 0);
                    if (decode != null) {
                        serializedData2 = new SerializedData(decode);
                        tmpPassword = TL_account_tmpPassword.TLdeserialize(serializedData2, serializedData2.readInt32(false), false);
                        serializedData2.cleanup();
                    }
                }
                string = sharedPreferences2.getString("user", null);
                if (string != null) {
                    decode = Base64.decode(string, 0);
                    if (decode != null) {
                        serializedData2 = new SerializedData(decode);
                        currentUser = User.TLdeserialize(serializedData2, serializedData2.readInt32(false), false);
                        serializedData2.cleanup();
                    }
                }
                String string2 = sharedPreferences2.getString("passcodeSalt", TtmlNode.ANONYMOUS_REGION_ID);
                if (string2.length() > 0) {
                    passcodeSalt = Base64.decode(string2, 0);
                } else {
                    passcodeSalt = new byte[0];
                }
                if (!notificationsConverted) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        SharedPreferences sharedPreferences3 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                        Map all = sharedPreferences3.getAll();
                        String string3 = LocaleController.getString("SoundDefault", R.string.SoundDefault);
                        for (Entry entry : all.entrySet()) {
                            string = (String) entry.getKey();
                            long longValue;
                            if (string.startsWith("sound_")) {
                                if (!((String) entry.getValue()).equals(string3)) {
                                    longValue = Utilities.parseLong(string).longValue();
                                    if (!arrayList.contains(Long.valueOf(longValue))) {
                                        arrayList.add(Long.valueOf(longValue));
                                    }
                                }
                            } else if (string.startsWith("vibrate_")) {
                                if (((Integer) entry.getValue()).intValue() != 0) {
                                    longValue = Utilities.parseLong(string).longValue();
                                    if (!arrayList.contains(Long.valueOf(longValue))) {
                                        arrayList.add(Long.valueOf(longValue));
                                    }
                                }
                            } else if (string.startsWith("priority_")) {
                                if (((Integer) entry.getValue()).intValue() != 0) {
                                    longValue = Utilities.parseLong(string).longValue();
                                    if (!arrayList.contains(Long.valueOf(longValue))) {
                                        arrayList.add(Long.valueOf(longValue));
                                    }
                                }
                            } else if (string.startsWith("color_")) {
                                if (((Integer) entry.getValue()).intValue() != 0) {
                                    longValue = Utilities.parseLong(string).longValue();
                                    if (!arrayList.contains(Long.valueOf(longValue))) {
                                        arrayList.add(Long.valueOf(longValue));
                                    }
                                }
                            } else if (string.startsWith("smart_max_count_")) {
                                if (((Integer) entry.getValue()).intValue() != 2) {
                                    longValue = Utilities.parseLong(string).longValue();
                                    if (!arrayList.contains(Long.valueOf(longValue))) {
                                        arrayList.add(Long.valueOf(longValue));
                                    }
                                }
                            } else if (string.startsWith("smart_delay_") && ((Integer) entry.getValue()).intValue() != 180) {
                                longValue = Utilities.parseLong(string).longValue();
                                if (!arrayList.contains(Long.valueOf(longValue))) {
                                    arrayList.add(Long.valueOf(longValue));
                                }
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            Editor edit = sharedPreferences3.edit();
                            for (int i = 0; i < arrayList.size(); i++) {
                                edit.putBoolean("custom_" + arrayList.get(i), true);
                            }
                            edit.commit();
                        }
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                    notificationsConverted = true;
                    saveConfig(false);
                }
            }
            configLoaded = true;
        }
    }

    public static void saveConfig(boolean z) {
        saveConfig(z, null);
    }

    public static void saveConfig(boolean z, File file) {
        synchronized (sync) {
            try {
                AbstractSerializedData serializedData;
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", 0).edit();
                edit.putBoolean("registeredForPush", registeredForPush);
                edit.putString("pushString2", pushString);
                edit.putInt("lastSendMessageId", lastSendMessageId);
                edit.putInt("lastLocalId", lastLocalId);
                edit.putInt("contactsSavedCount", contactsSavedCount);
                edit.putBoolean("saveIncomingPhotos", saveIncomingPhotos);
                edit.putInt("lastBroadcastId", lastBroadcastId);
                edit.putBoolean("blockedUsersLoaded", blockedUsersLoaded);
                edit.putString("passcodeHash1", passcodeHash);
                edit.putString("passcodeSalt", passcodeSalt.length > 0 ? Base64.encodeToString(passcodeSalt, 0) : TtmlNode.ANONYMOUS_REGION_ID);
                edit.putBoolean("appLocked", appLocked);
                edit.putInt("passcodeType", passcodeType);
                edit.putInt("autoLockIn", autoLockIn);
                edit.putInt("lastPauseTime", lastPauseTime);
                edit.putLong("lastAppPauseTime", lastAppPauseTime);
                edit.putString("lastUpdateVersion2", lastUpdateVersion);
                edit.putInt("lastContactsSyncTime", lastContactsSyncTime);
                edit.putBoolean("useFingerprint", useFingerprint);
                edit.putInt("lastHintsSyncTime", lastHintsSyncTime);
                edit.putBoolean("draftsLoaded", draftsLoaded);
                edit.putBoolean("notificationsConverted", notificationsConverted);
                edit.putBoolean("allowScreenCapture", allowScreenCapture);
                edit.putBoolean("pinnedDialogsLoaded", pinnedDialogsLoaded);
                edit.putInt("ratingLoadTime", ratingLoadTime);
                edit.putInt("botRatingLoadTime", botRatingLoadTime);
                edit.putBoolean("contactsReimported", contactsReimported);
                edit.putInt("3migrateOffsetId", migrateOffsetId);
                if (migrateOffsetId != -1) {
                    edit.putInt("3migrateOffsetDate", migrateOffsetDate);
                    edit.putInt("3migrateOffsetUserId", migrateOffsetUserId);
                    edit.putInt("3migrateOffsetChatId", migrateOffsetChatId);
                    edit.putInt("3migrateOffsetChannelId", migrateOffsetChannelId);
                    edit.putLong("3migrateOffsetAccess", migrateOffsetAccess);
                }
                edit.putInt("2totalDialogsLoadCount", totalDialogsLoadCount);
                edit.putInt("2dialogsLoadOffsetId", dialogsLoadOffsetId);
                edit.putInt("2dialogsLoadOffsetDate", dialogsLoadOffsetDate);
                edit.putInt("2dialogsLoadOffsetUserId", dialogsLoadOffsetUserId);
                edit.putInt("2dialogsLoadOffsetChatId", dialogsLoadOffsetChatId);
                edit.putInt("2dialogsLoadOffsetChannelId", dialogsLoadOffsetChannelId);
                edit.putLong("2dialogsLoadOffsetAccess", dialogsLoadOffsetAccess);
                if (tmpPassword != null) {
                    serializedData = new SerializedData();
                    tmpPassword.serializeToStream(serializedData);
                    edit.putString("tmpPassword", Base64.encodeToString(serializedData.toByteArray(), 0));
                    serializedData.cleanup();
                } else {
                    edit.remove("tmpPassword");
                }
                if (currentUser == null) {
                    edit.remove("user");
                } else if (z) {
                    serializedData = new SerializedData();
                    currentUser.serializeToStream(serializedData);
                    edit.putString("user", Base64.encodeToString(serializedData.toByteArray(), 0));
                    serializedData.cleanup();
                }
                edit.commit();
                if (file != null) {
                    file.delete();
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static void setCurrentUser(User user) {
        synchronized (sync) {
            currentUser = user;
        }
    }
}
