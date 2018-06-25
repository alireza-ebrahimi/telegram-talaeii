package org.telegram.messenger.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.TLRPC$BotInfo;
import org.telegram.tgnet.TLRPC$Message;

public class BotQuery {
    private static HashMap<Integer, TLRPC$BotInfo> botInfos = new HashMap();
    private static HashMap<Long, TLRPC$Message> botKeyboards = new HashMap();
    private static HashMap<Integer, Long> botKeyboardsByMids = new HashMap();

    public static void cleanup() {
        botInfos.clear();
        botKeyboards.clear();
        botKeyboardsByMids.clear();
    }

    public static void clearBotKeyboard(final long did, final ArrayList<Integer> messages) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (messages != null) {
                    for (int a = 0; a < messages.size(); a++) {
                        Long did = (Long) BotQuery.botKeyboardsByMids.get(messages.get(a));
                        if (did != null) {
                            BotQuery.botKeyboards.remove(did);
                            BotQuery.botKeyboardsByMids.remove(messages.get(a));
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, new Object[]{null, did});
                        }
                    }
                    return;
                }
                BotQuery.botKeyboards.remove(Long.valueOf(did));
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, new Object[]{null, Long.valueOf(did)});
            }
        });
    }

    public static void loadBotKeyboard(final long did) {
        if (((TLRPC$Message) botKeyboards.get(Long.valueOf(did))) != null) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, new Object[]{keyboard, Long.valueOf(did)});
            return;
        }
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                TLRPC$Message botKeyboard = null;
                try {
                    SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT info FROM bot_keyboard WHERE uid = %d", new Object[]{Long.valueOf(did)}), new Object[0]);
                    if (cursor.next() && !cursor.isNull(0)) {
                        NativeByteBuffer data = cursor.byteBufferValue(0);
                        if (data != null) {
                            botKeyboard = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                        }
                    }
                    cursor.dispose();
                    if (botKeyboard != null) {
                        final TLRPC$Message botKeyboardFinal = botKeyboard;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, new Object[]{botKeyboardFinal, Long.valueOf(did)});
                            }
                        });
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public static void loadBotInfo(final int uid, boolean cache, final int classGuid) {
        if (!cache || ((TLRPC$BotInfo) botInfos.get(Integer.valueOf(uid))) == null) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    TLRPC$BotInfo botInfo = null;
                    try {
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT info FROM bot_info WHERE uid = %d", new Object[]{Integer.valueOf(uid)}), new Object[0]);
                        if (cursor.next() && !cursor.isNull(0)) {
                            NativeByteBuffer data = cursor.byteBufferValue(0);
                            if (data != null) {
                                botInfo = TLRPC$BotInfo.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                            }
                        }
                        cursor.dispose();
                        if (botInfo != null) {
                            final TLRPC$BotInfo botInfoFinal = botInfo;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.botInfoDidLoaded, new Object[]{botInfoFinal, Integer.valueOf(classGuid)});
                                }
                            });
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
            return;
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.botInfoDidLoaded, new Object[]{botInfo, Integer.valueOf(classGuid)});
    }

    public static void putBotKeyboard(final long did, final TLRPC$Message message) {
        if (message != null) {
            int mid = 0;
            try {
                SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT mid FROM bot_keyboard WHERE uid = %d", new Object[]{Long.valueOf(did)}), new Object[0]);
                if (cursor.next()) {
                    mid = cursor.intValue(0);
                }
                cursor.dispose();
                if (mid < message.id) {
                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO bot_keyboard VALUES(?, ?, ?)");
                    state.requery();
                    NativeByteBuffer data = new NativeByteBuffer(message.getObjectSize());
                    message.serializeToStream(data);
                    state.bindLong(1, did);
                    state.bindInteger(2, message.id);
                    state.bindByteBuffer(3, data);
                    state.step();
                    data.reuse();
                    state.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            TLRPC$Message old = (TLRPC$Message) BotQuery.botKeyboards.put(Long.valueOf(did), message);
                            if (old != null) {
                                BotQuery.botKeyboardsByMids.remove(Integer.valueOf(old.id));
                            }
                            BotQuery.botKeyboardsByMids.put(Integer.valueOf(message.id), Long.valueOf(did));
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, new Object[]{message, Long.valueOf(did)});
                        }
                    });
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public static void putBotInfo(final TLRPC$BotInfo botInfo) {
        if (botInfo != null) {
            botInfos.put(Integer.valueOf(botInfo.user_id), botInfo);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    try {
                        SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO bot_info(uid, info) VALUES(?, ?)");
                        state.requery();
                        NativeByteBuffer data = new NativeByteBuffer(botInfo.getObjectSize());
                        botInfo.serializeToStream(data);
                        state.bindInteger(1, botInfo.user_id);
                        state.bindByteBuffer(2, data);
                        state.step();
                        data.reuse();
                        state.dispose();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }
}
