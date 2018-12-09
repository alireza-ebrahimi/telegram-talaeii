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
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.Message;

public class BotQuery {
    private static HashMap<Integer, BotInfo> botInfos = new HashMap();
    private static HashMap<Long, Message> botKeyboards = new HashMap();
    private static HashMap<Integer, Long> botKeyboardsByMids = new HashMap();

    public static void cleanup() {
        botInfos.clear();
        botKeyboards.clear();
        botKeyboardsByMids.clear();
    }

    public static void clearBotKeyboard(final long j, final ArrayList<Integer> arrayList) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (arrayList != null) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        Long l = (Long) BotQuery.botKeyboardsByMids.get(arrayList.get(i));
                        if (l != null) {
                            BotQuery.botKeyboards.remove(l);
                            BotQuery.botKeyboardsByMids.remove(arrayList.get(i));
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, null, l);
                        }
                    }
                    return;
                }
                BotQuery.botKeyboards.remove(Long.valueOf(j));
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, null, Long.valueOf(j));
            }
        });
    }

    public static void loadBotInfo(final int i, boolean z, final int i2) {
        if (!z || ((BotInfo) botInfos.get(Integer.valueOf(i))) == null) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    BotInfo botInfo = null;
                    try {
                        SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT info FROM bot_info WHERE uid = %d", new Object[]{Integer.valueOf(i)}), new Object[0]);
                        if (b.m12152a() && !b.m12153a(0)) {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                botInfo = BotInfo.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                            }
                        }
                        b.m12155b();
                        if (botInfo != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.botInfoDidLoaded, botInfo, Integer.valueOf(i2));
                                }
                            });
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
            return;
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.botInfoDidLoaded, r0, Integer.valueOf(i2));
    }

    public static void loadBotKeyboard(final long j) {
        if (((Message) botKeyboards.get(Long.valueOf(j))) != null) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, r0, Long.valueOf(j));
            return;
        }
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                Message message = null;
                try {
                    SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT info FROM bot_keyboard WHERE uid = %d", new Object[]{Long.valueOf(j)}), new Object[0]);
                    if (b.m12152a() && !b.m12153a(0)) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            message = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                        }
                    }
                    b.m12155b();
                    if (message != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, message, Long.valueOf(j));
                            }
                        });
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void putBotInfo(final BotInfo botInfo) {
        if (botInfo != null) {
            botInfos.put(Integer.valueOf(botInfo.user_id), botInfo);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    try {
                        SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO bot_info(uid, info) VALUES(?, ?)");
                        a.m12180d();
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(botInfo.getObjectSize());
                        botInfo.serializeToStream(nativeByteBuffer);
                        a.m12174a(1, botInfo.user_id);
                        a.m12177a(2, nativeByteBuffer);
                        a.m12178b();
                        nativeByteBuffer.reuse();
                        a.m12181e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public static void putBotKeyboard(final long j, final Message message) {
        int i = 0;
        if (message != null) {
            try {
                SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT mid FROM bot_keyboard WHERE uid = %d", new Object[]{Long.valueOf(j)}), new Object[0]);
                if (b.m12152a()) {
                    i = b.m12154b(0);
                }
                b.m12155b();
                if (i < message.id) {
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO bot_keyboard VALUES(?, ?, ?)");
                    a.m12180d();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                    message.serializeToStream(nativeByteBuffer);
                    a.m12175a(1, j);
                    a.m12174a(2, message.id);
                    a.m12177a(3, nativeByteBuffer);
                    a.m12178b();
                    nativeByteBuffer.reuse();
                    a.m12181e();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            Message message = (Message) BotQuery.botKeyboards.put(Long.valueOf(j), message);
                            if (message != null) {
                                BotQuery.botKeyboardsByMids.remove(Integer.valueOf(message.id));
                            }
                            BotQuery.botKeyboardsByMids.put(Integer.valueOf(message.id), Long.valueOf(j));
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.botKeyboardDidLoaded, message, Long.valueOf(j));
                        }
                    });
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }
}
