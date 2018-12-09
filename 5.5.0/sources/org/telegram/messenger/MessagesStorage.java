package org.telegram.messenger;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.google.p098a.C1768f;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import org.telegram.SQLite.C2486a;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.customization.Model.TMData;
import org.telegram.customization.Model.UserState;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.ContactsController.Contact;
import org.telegram.messenger.MediaController.SearchImage;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionScreenshotMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_inputMediaGame;
import org.telegram.tgnet.TLRPC$TL_inputMessageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
import org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_messageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported;
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported_old;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_message_secret;
import org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer;
import org.telegram.tgnet.TLRPC$TL_messages_botResults;
import org.telegram.tgnet.TLRPC$TL_messages_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_messages_dialogs;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettingsEmpty;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_photos_photos;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong;
import org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
import org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
import org.telegram.tgnet.TLRPC$TL_userStatusRecently;
import org.telegram.tgnet.TLRPC$WallPaper;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC$photos_Photos;
import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.ChatParticipants;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.InputChannel;
import org.telegram.tgnet.TLRPC.InputMedia;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_channelFull;
import org.telegram.tgnet.TLRPC.TL_channels_deleteMessages;
import org.telegram.tgnet.TLRPC.TL_chatChannelParticipant;
import org.telegram.tgnet.TLRPC.TL_chatFull;
import org.telegram.tgnet.TLRPC.TL_chatInviteEmpty;
import org.telegram.tgnet.TLRPC.TL_chatParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_chatParticipants;
import org.telegram.tgnet.TLRPC.User;

public class MessagesStorage {
    private static volatile MessagesStorage Instance = null;
    public static int lastDateValue = 0;
    public static int lastPtsValue = 0;
    public static int lastQtsValue = 0;
    public static int lastSecretVersion = 0;
    public static int lastSeqValue = 0;
    public static int secretG = 0;
    public static byte[] secretPBytes = null;
    private File cacheFile;
    private SQLiteDatabase database;
    private int lastSavedDate = 0;
    private int lastSavedPts = 0;
    private int lastSavedQts = 0;
    private int lastSavedSeq = 0;
    private AtomicLong lastTaskId = new AtomicLong(System.currentTimeMillis());
    private DispatchQueue storageQueue = new DispatchQueue("storageQueue");

    /* renamed from: org.telegram.messenger.MessagesStorage$4 */
    class C32804 implements Runnable {
        C32804() {
        }

        public void run() {
            HashMap hashMap = new HashMap();
            Map all = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getAll();
            for (Entry entry : all.entrySet()) {
                String str = (String) entry.getKey();
                if (str.startsWith("notify2_")) {
                    Integer num = (Integer) entry.getValue();
                    if (num.intValue() == 2 || num.intValue() == 3) {
                        long j;
                        String replace = str.replace("notify2_", TtmlNode.ANONYMOUS_REGION_ID);
                        if (num.intValue() == 2) {
                            j = 1;
                        } else {
                            num = (Integer) all.get("notifyuntil_" + replace);
                            j = num != null ? (((long) num.intValue()) << 32) | 1 : 1;
                        }
                        try {
                            hashMap.put(Long.valueOf(Long.parseLong(replace)), Long.valueOf(j));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } catch (Throwable th) {
                            FileLog.m13728e(th);
                            return;
                        }
                    } else if (num.intValue() == 3) {
                    }
                }
            }
            try {
                MessagesStorage.this.database.m12168d();
                SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO dialog_settings VALUES(?, ?)");
                for (Entry entry2 : hashMap.entrySet()) {
                    a.m12180d();
                    a.m12175a(1, ((Long) entry2.getKey()).longValue());
                    a.m12175a(2, ((Long) entry2.getValue()).longValue());
                    a.m12178b();
                }
                a.m12181e();
                MessagesStorage.this.database.m12169e();
            } catch (Throwable th2) {
                FileLog.m13728e(th2);
            }
        }
    }

    /* renamed from: org.telegram.messenger.MessagesStorage$7 */
    class C32947 implements Runnable {
        C32947() {
        }

        public void run() {
            try {
                SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT id, data FROM pending_tasks WHERE 1", new Object[0]);
                while (b.m12152a()) {
                    final long d = b.m12158d(0);
                    AbstractSerializedData g = b.m12161g(1);
                    if (g != null) {
                        int readInt32 = g.readInt32(false);
                        final int readInt322;
                        final int readInt323;
                        switch (readInt32) {
                            case 0:
                                final Chat TLdeserialize = Chat.TLdeserialize(g, g.readInt32(false), false);
                                if (TLdeserialize != null) {
                                    Utilities.stageQueue.postRunnable(new Runnable() {
                                        public void run() {
                                            MessagesController.getInstance().loadUnknownChannel(TLdeserialize, d);
                                        }
                                    });
                                    break;
                                }
                                break;
                            case 1:
                                readInt322 = g.readInt32(false);
                                readInt323 = g.readInt32(false);
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().getChannelDifference(readInt322, readInt323, d, null);
                                    }
                                });
                                break;
                            case 2:
                            case 5:
                            case 8:
                                final TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                                tLRPC$TL_dialog.id = g.readInt64(false);
                                tLRPC$TL_dialog.top_message = g.readInt32(false);
                                tLRPC$TL_dialog.read_inbox_max_id = g.readInt32(false);
                                tLRPC$TL_dialog.read_outbox_max_id = g.readInt32(false);
                                tLRPC$TL_dialog.unread_count = g.readInt32(false);
                                tLRPC$TL_dialog.last_message_date = g.readInt32(false);
                                tLRPC$TL_dialog.pts = g.readInt32(false);
                                tLRPC$TL_dialog.flags = g.readInt32(false);
                                if (readInt32 >= 5) {
                                    tLRPC$TL_dialog.pinned = g.readBool(false);
                                    tLRPC$TL_dialog.pinnedNum = g.readInt32(false);
                                }
                                if (readInt32 >= 8) {
                                    tLRPC$TL_dialog.unread_mentions_count = g.readInt32(false);
                                }
                                final InputPeer TLdeserialize2 = InputPeer.TLdeserialize(g, g.readInt32(false), false);
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().checkLastDialogMessage(tLRPC$TL_dialog, TLdeserialize2, d);
                                    }
                                });
                                break;
                            case 3:
                                long readInt64 = g.readInt64(false);
                                SendMessagesHelper.getInstance().sendGame(InputPeer.TLdeserialize(g, g.readInt32(false), false), (TLRPC$TL_inputMediaGame) InputMedia.TLdeserialize(g, g.readInt32(false), false), readInt64, d);
                                break;
                            case 4:
                                final long readInt642 = g.readInt64(false);
                                final boolean readBool = g.readBool(false);
                                final InputPeer TLdeserialize3 = InputPeer.TLdeserialize(g, g.readInt32(false), false);
                                final long j = d;
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().pinDialog(readInt642, readBool, TLdeserialize3, j);
                                    }
                                });
                                break;
                            case 6:
                                readInt322 = g.readInt32(false);
                                readInt323 = g.readInt32(false);
                                final InputChannel TLdeserialize4 = InputChannel.TLdeserialize(g, g.readInt32(false), false);
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().getChannelDifference(readInt322, readInt323, d, TLdeserialize4);
                                    }
                                });
                                break;
                            case 7:
                                readInt323 = g.readInt32(false);
                                readInt32 = g.readInt32(false);
                                TLObject TLdeserialize5 = TLRPC$TL_messages_deleteMessages.TLdeserialize(g, readInt32, false);
                                if (TLdeserialize5 == null) {
                                    TLdeserialize5 = TL_channels_deleteMessages.TLdeserialize(g, readInt32, false);
                                }
                                if (TLdeserialize5 != null) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            MessagesController.getInstance().deleteMessages(null, null, null, readInt323, true, d, TLdeserialize5);
                                        }
                                    });
                                    break;
                                } else {
                                    MessagesStorage.this.removePendingTask(d);
                                    break;
                                }
                        }
                        g.reuse();
                    }
                }
                b.m12155b();
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    private class Hole {
        public int end;
        public int start;
        public int type;

        public Hole(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        public Hole(int i, int i2, int i3) {
            this.type = i;
            this.start = i2;
            this.end = i3;
        }
    }

    public interface IntCallback {
        void run(int i);
    }

    public MessagesStorage() {
        this.storageQueue.setPriority(10);
        openDatabase(true);
    }

    public static void addUsersAndChatsFromMessage(Message message, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2) {
        int i = 0;
        if (message.from_id != 0) {
            if (message.from_id > 0) {
                if (!arrayList.contains(Integer.valueOf(message.from_id))) {
                    arrayList.add(Integer.valueOf(message.from_id));
                }
            } else if (!arrayList2.contains(Integer.valueOf(-message.from_id))) {
                arrayList2.add(Integer.valueOf(-message.from_id));
            }
        }
        if (!(message.via_bot_id == 0 || arrayList.contains(Integer.valueOf(message.via_bot_id)))) {
            arrayList.add(Integer.valueOf(message.via_bot_id));
        }
        if (message.action != null) {
            if (!(message.action.user_id == 0 || arrayList.contains(Integer.valueOf(message.action.user_id)))) {
                arrayList.add(Integer.valueOf(message.action.user_id));
            }
            if (!(message.action.channel_id == 0 || arrayList2.contains(Integer.valueOf(message.action.channel_id)))) {
                arrayList2.add(Integer.valueOf(message.action.channel_id));
            }
            if (!(message.action.chat_id == 0 || arrayList2.contains(Integer.valueOf(message.action.chat_id)))) {
                arrayList2.add(Integer.valueOf(message.action.chat_id));
            }
            if (!message.action.users.isEmpty()) {
                for (int i2 = 0; i2 < message.action.users.size(); i2++) {
                    Integer num = (Integer) message.action.users.get(i2);
                    if (!arrayList.contains(num)) {
                        arrayList.add(num);
                    }
                }
            }
        }
        if (!message.entities.isEmpty()) {
            while (i < message.entities.size()) {
                MessageEntity messageEntity = (MessageEntity) message.entities.get(i);
                if (messageEntity instanceof TLRPC$TL_messageEntityMentionName) {
                    arrayList.add(Integer.valueOf(((TLRPC$TL_messageEntityMentionName) messageEntity).user_id));
                } else if (messageEntity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                    arrayList.add(Integer.valueOf(((TLRPC$TL_inputMessageEntityMentionName) messageEntity).user_id.user_id));
                }
                i++;
            }
        }
        if (!(message.media == null || message.media.user_id == 0 || arrayList.contains(Integer.valueOf(message.media.user_id)))) {
            arrayList.add(Integer.valueOf(message.media.user_id));
        }
        if (message.fwd_from != null) {
            if (!(message.fwd_from.from_id == 0 || arrayList.contains(Integer.valueOf(message.fwd_from.from_id)))) {
                arrayList.add(Integer.valueOf(message.fwd_from.from_id));
            }
            if (!(message.fwd_from.channel_id == 0 || arrayList2.contains(Integer.valueOf(message.fwd_from.channel_id)))) {
                arrayList2.add(Integer.valueOf(message.fwd_from.channel_id));
            }
            if (message.fwd_from.saved_from_peer != null) {
                if (message.fwd_from.saved_from_peer.user_id != 0) {
                    if (!arrayList2.contains(Integer.valueOf(message.fwd_from.saved_from_peer.user_id))) {
                        arrayList.add(Integer.valueOf(message.fwd_from.saved_from_peer.user_id));
                    }
                } else if (message.fwd_from.saved_from_peer.channel_id != 0) {
                    if (!arrayList2.contains(Integer.valueOf(message.fwd_from.saved_from_peer.channel_id))) {
                        arrayList2.add(Integer.valueOf(message.fwd_from.saved_from_peer.channel_id));
                    }
                } else if (!(message.fwd_from.saved_from_peer.chat_id == 0 || arrayList2.contains(Integer.valueOf(message.fwd_from.saved_from_peer.chat_id)))) {
                    arrayList2.add(Integer.valueOf(message.fwd_from.saved_from_peer.chat_id));
                }
            }
        }
        if (message.ttl < 0 && !arrayList2.contains(Integer.valueOf(-message.ttl))) {
            arrayList2.add(Integer.valueOf(-message.ttl));
        }
    }

    private void cleanupInternal() {
        lastDateValue = 0;
        lastSeqValue = 0;
        lastPtsValue = 0;
        lastQtsValue = 0;
        lastSecretVersion = 0;
        this.lastSavedSeq = 0;
        this.lastSavedPts = 0;
        this.lastSavedDate = 0;
        this.lastSavedQts = 0;
        secretPBytes = null;
        secretG = 0;
        if (this.database != null) {
            this.database.m12166b();
            this.database = null;
        }
        if (this.cacheFile != null) {
            this.cacheFile.delete();
            this.cacheFile = null;
        }
    }

    private void closeHolesInTable(String str, long j, int i, int i2) {
        SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT start, end FROM " + str + " WHERE uid = %d AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", new Object[]{Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2)}), new Object[0]);
        ArrayList arrayList = null;
        while (b.m12152a()) {
            ArrayList arrayList2 = arrayList == null ? new ArrayList() : arrayList;
            int b2 = b.m12154b(0);
            int b3 = b.m12154b(1);
            if (b2 == b3 && b2 == 1) {
                arrayList = arrayList2;
            } else {
                arrayList2.add(new Hole(b2, b3));
                arrayList = arrayList2;
            }
        }
        b.m12155b();
        if (arrayList != null) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                Hole hole = (Hole) arrayList.get(i3);
                if (i2 >= hole.end - 1 && i <= hole.start + 1) {
                    this.database.m12164a(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(j), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                } else if (i2 >= hole.end - 1) {
                    if (hole.end != i) {
                        try {
                            this.database.m12164a(String.format(Locale.US, "UPDATE " + str + " SET end = %d WHERE uid = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                        } catch (Throwable e) {
                            try {
                                FileLog.m13728e(e);
                            } catch (Throwable e2) {
                                FileLog.m13728e(e2);
                                return;
                            }
                        }
                    }
                    continue;
                } else if (i > hole.start + 1) {
                    this.database.m12164a(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(j), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                    SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO " + str + " VALUES(?, ?, ?)");
                    a.m12180d();
                    a.m12175a(1, j);
                    a.m12174a(2, hole.start);
                    a.m12174a(3, i);
                    a.m12178b();
                    a.m12180d();
                    a.m12175a(1, j);
                    a.m12174a(2, i2);
                    a.m12174a(3, hole.end);
                    a.m12178b();
                    a.m12181e();
                } else if (hole.start != i2) {
                    try {
                        this.database.m12164a(String.format(Locale.US, "UPDATE " + str + " SET start = %d WHERE uid = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(i2), Long.valueOf(j), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                    } catch (Throwable e22) {
                        FileLog.m13728e(e22);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public static void createFirstHoles(long j, SQLitePreparedStatement sQLitePreparedStatement, SQLitePreparedStatement sQLitePreparedStatement2, int i) {
        sQLitePreparedStatement.m12180d();
        sQLitePreparedStatement.m12175a(1, j);
        sQLitePreparedStatement.m12174a(2, i == 1 ? 1 : 0);
        sQLitePreparedStatement.m12174a(3, i);
        sQLitePreparedStatement.m12178b();
        for (int i2 = 0; i2 < 5; i2++) {
            sQLitePreparedStatement2.m12180d();
            sQLitePreparedStatement2.m12175a(1, j);
            sQLitePreparedStatement2.m12174a(2, i2);
            sQLitePreparedStatement2.m12174a(3, i == 1 ? 1 : 0);
            sQLitePreparedStatement2.m12174a(4, i);
            sQLitePreparedStatement2.m12178b();
        }
    }

    private void doneHolesInTable(String str, long j, int i) {
        if (i == 0) {
            this.database.m12164a(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
        } else {
            this.database.m12164a(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = 0", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
        }
        SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO " + str + " VALUES(?, ?, ?)");
        a.m12180d();
        a.m12175a(1, j);
        a.m12174a(2, 1);
        a.m12174a(3, 1);
        a.m12178b();
        a.m12181e();
    }

    private void fixNotificationSettings() {
        this.storageQueue.postRunnable(new C32804());
    }

    private void fixUnsupportedMedia(Message message) {
        if (message != null) {
            if (message.media instanceof TLRPC$TL_messageMediaUnsupported_old) {
                if (message.media.bytes.length == 0) {
                    message.media.bytes = new byte[1];
                    message.media.bytes[0] = (byte) 73;
                }
            } else if (message.media instanceof TLRPC$TL_messageMediaUnsupported) {
                message.media = new TLRPC$TL_messageMediaUnsupported_old();
                message.media.bytes = new byte[1];
                message.media.bytes[0] = (byte) 73;
                message.flags |= 512;
            }
        }
    }

    private String formatUserSearchName(User user) {
        StringBuilder stringBuilder = new StringBuilder(TtmlNode.ANONYMOUS_REGION_ID);
        if (user.first_name != null && user.first_name.length() > 0) {
            stringBuilder.append(user.first_name);
        }
        if (user.last_name != null && user.last_name.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(user.last_name);
        }
        stringBuilder.append(";;;");
        if (user.username != null && user.username.length() > 0) {
            stringBuilder.append(user.username);
        }
        return stringBuilder.toString().toLowerCase();
    }

    public static MessagesStorage getInstance() {
        MessagesStorage messagesStorage = Instance;
        if (messagesStorage == null) {
            synchronized (MessagesStorage.class) {
                messagesStorage = Instance;
                if (messagesStorage == null) {
                    messagesStorage = new MessagesStorage();
                    Instance = messagesStorage;
                }
            }
        }
        return messagesStorage;
    }

    private int getMessageMediaType(Message message) {
        return (!(message instanceof TLRPC$TL_message) || (!((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)) || message.media.ttl_seconds == 0)) ? ((message instanceof TLRPC$TL_message_secret) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) && message.ttl > 0 && message.ttl <= 60) || MessageObject.isVoiceMessage(message) || MessageObject.isVideoMessage(message) || MessageObject.isRoundVideoMessage(message))) ? 1 : ((message.media instanceof TLRPC$TL_messageMediaPhoto) || MessageObject.isVideoMessage(message)) ? 0 : -1 : 1;
    }

    private boolean isValidKeyboardToSave(Message message) {
        return (message.reply_markup == null || (message.reply_markup instanceof TLRPC$TL_replyInlineMarkup) || (message.reply_markup.selective && !message.mentioned)) ? false : true;
    }

    private void loadPendingTasks() {
        this.storageQueue.postRunnable(new C32947());
    }

    private ArrayList<Long> markMessagesAsDeletedInternal(int i, int i2) {
        try {
            Integer[] numArr;
            ArrayList<Long> arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            long j = (((long) i) << 32) | ((long) i2);
            ArrayList arrayList2 = new ArrayList();
            int clientUserId = UserConfig.getClientUserId();
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT uid, data, read_state, out, mention FROM messages WHERE uid = %d AND mid <= %d", new Object[]{Integer.valueOf(-i), Long.valueOf(j)}), new Object[0]);
            while (b.m12152a()) {
                try {
                    long d = b.m12158d(0);
                    if (d != ((long) clientUserId)) {
                        int b2 = b.m12154b(2);
                        if (b.m12154b(3) == 0) {
                            numArr = (Integer[]) hashMap.get(Long.valueOf(d));
                            if (numArr == null) {
                                numArr = new Integer[]{Integer.valueOf(0), Integer.valueOf(0)};
                                hashMap.put(Long.valueOf(d), numArr);
                            }
                            if (b2 < 2) {
                                Integer num = numArr[1];
                                numArr[1] = Integer.valueOf(numArr[1].intValue() + 1);
                            }
                            if (b2 == 0 || b2 == 2) {
                                Integer num2 = numArr[0];
                                numArr[0] = Integer.valueOf(numArr[0].intValue() + 1);
                            }
                        }
                        if (((int) d) == 0) {
                            AbstractSerializedData g = b.m12161g(1);
                            if (g != null) {
                                Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                if (TLdeserialize == null) {
                                    continue;
                                } else if (TLdeserialize.media instanceof TLRPC$TL_messageMediaPhoto) {
                                    Iterator it = TLdeserialize.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        r0 = FileLoader.getPathToAttach((PhotoSize) it.next());
                                        if (r0 != null && r0.toString().length() > 0) {
                                            arrayList2.add(r0);
                                        }
                                    }
                                } else if (TLdeserialize.media instanceof TLRPC$TL_messageMediaDocument) {
                                    r0 = FileLoader.getPathToAttach(TLdeserialize.media.document);
                                    if (r0 != null && r0.toString().length() > 0) {
                                        arrayList2.add(r0);
                                    }
                                    r0 = FileLoader.getPathToAttach(TLdeserialize.media.document.thumb);
                                    if (r0 != null && r0.toString().length() > 0) {
                                        arrayList2.add(r0);
                                    }
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
            b.m12155b();
            FileLoader.getInstance().deleteFiles(arrayList2, 0);
            for (Entry entry : hashMap.entrySet()) {
                Long l = (Long) entry.getKey();
                numArr = (Integer[]) entry.getValue();
                SQLiteCursor b3 = this.database.m12165b("SELECT unread_count, unread_count_i FROM dialogs WHERE did = " + l, new Object[0]);
                clientUserId = 0;
                int i3 = 0;
                if (b3.m12152a()) {
                    clientUserId = b3.m12154b(0);
                    i3 = b3.m12154b(1);
                }
                b3.m12155b();
                arrayList.add(l);
                SQLitePreparedStatement a = this.database.m12164a("UPDATE dialogs SET unread_count = ?, unread_count_i = ? WHERE did = ?");
                a.m12180d();
                a.m12174a(1, Math.max(0, clientUserId - numArr[0].intValue()));
                a.m12174a(2, Math.max(0, i3 - numArr[1].intValue()));
                a.m12175a(3, l.longValue());
                a.m12178b();
                a.m12181e();
            }
            this.database.m12164a(String.format(Locale.US, "DELETE FROM messages WHERE uid = %d AND mid <= %d", new Object[]{Integer.valueOf(-i), Long.valueOf(j)})).m12179c().m12181e();
            this.database.m12164a(String.format(Locale.US, "DELETE FROM media_v2 WHERE uid = %d AND mid <= %d", new Object[]{Integer.valueOf(-i), Long.valueOf(j)})).m12179c().m12181e();
            this.database.m12164a("DELETE FROM media_counts_v2 WHERE 1").m12179c().m12181e();
            return arrayList;
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
            return null;
        }
    }

    private ArrayList<Long> markMessagesAsDeletedInternal(ArrayList<Integer> arrayList, int i) {
        try {
            Object stringBuilder;
            Integer[] numArr;
            ArrayList<Long> arrayList2 = new ArrayList();
            HashMap hashMap = new HashMap();
            if (i != 0) {
                StringBuilder stringBuilder2 = new StringBuilder(arrayList.size());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    long intValue = ((long) ((Integer) arrayList.get(i2)).intValue()) | (((long) i) << 32);
                    if (stringBuilder2.length() > 0) {
                        stringBuilder2.append(',');
                    }
                    stringBuilder2.append(intValue);
                }
                stringBuilder = stringBuilder2.toString();
            } else {
                String join = TextUtils.join(",", arrayList);
            }
            ArrayList arrayList3 = new ArrayList();
            int clientUserId = UserConfig.getClientUserId();
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT uid, data, read_state, out, mention FROM messages WHERE mid IN(%s)", new Object[]{stringBuilder}), new Object[0]);
            while (b.m12152a()) {
                long d = b.m12158d(0);
                if (d != ((long) clientUserId)) {
                    int b2 = b.m12154b(2);
                    if (b.m12154b(3) == 0) {
                        numArr = (Integer[]) hashMap.get(Long.valueOf(d));
                        if (numArr == null) {
                            numArr = new Integer[]{Integer.valueOf(0), Integer.valueOf(0)};
                            hashMap.put(Long.valueOf(d), numArr);
                        }
                        if (b2 < 2) {
                            Integer num = numArr[1];
                            numArr[1] = Integer.valueOf(numArr[1].intValue() + 1);
                        }
                        if (b2 == 0 || b2 == 2) {
                            Integer num2 = numArr[0];
                            numArr[0] = Integer.valueOf(numArr[0].intValue() + 1);
                        }
                    }
                    if (((int) d) == 0) {
                        AbstractSerializedData g = b.m12161g(1);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (TLdeserialize == null) {
                                continue;
                            } else if (TLdeserialize.media instanceof TLRPC$TL_messageMediaPhoto) {
                                Iterator it = TLdeserialize.media.photo.sizes.iterator();
                                while (it.hasNext()) {
                                    r0 = FileLoader.getPathToAttach((PhotoSize) it.next());
                                    if (r0 != null && r0.toString().length() > 0) {
                                        arrayList3.add(r0);
                                    }
                                }
                            } else {
                                try {
                                    if (TLdeserialize.media instanceof TLRPC$TL_messageMediaDocument) {
                                        r0 = FileLoader.getPathToAttach(TLdeserialize.media.document);
                                        if (r0 != null && r0.toString().length() > 0) {
                                            arrayList3.add(r0);
                                        }
                                        r0 = FileLoader.getPathToAttach(TLdeserialize.media.document.thumb);
                                        if (r0 != null && r0.toString().length() > 0) {
                                            arrayList3.add(r0);
                                        }
                                    }
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
            b.m12155b();
            FileLoader.getInstance().deleteFiles(arrayList3, 0);
            for (Entry entry : hashMap.entrySet()) {
                Long l = (Long) entry.getKey();
                numArr = (Integer[]) entry.getValue();
                SQLiteCursor b3 = this.database.m12165b("SELECT unread_count, unread_count_i FROM dialogs WHERE did = " + l, new Object[0]);
                clientUserId = 0;
                int i3 = 0;
                if (b3.m12152a()) {
                    clientUserId = b3.m12154b(0);
                    i3 = b3.m12154b(1);
                }
                b3.m12155b();
                arrayList2.add(l);
                SQLitePreparedStatement a = this.database.m12164a("UPDATE dialogs SET unread_count = ?, unread_count_i = ? WHERE did = ?");
                a.m12180d();
                a.m12174a(1, Math.max(0, clientUserId - numArr[0].intValue()));
                a.m12174a(2, Math.max(0, i3 - numArr[1].intValue()));
                a.m12175a(3, l.longValue());
                a.m12178b();
                a.m12181e();
            }
            this.database.m12164a(String.format(Locale.US, "DELETE FROM messages WHERE mid IN(%s)", new Object[]{stringBuilder})).m12179c().m12181e();
            this.database.m12164a(String.format(Locale.US, "DELETE FROM bot_keyboard WHERE mid IN(%s)", new Object[]{stringBuilder})).m12179c().m12181e();
            this.database.m12164a(String.format(Locale.US, "DELETE FROM messages_seq WHERE mid IN(%s)", new Object[]{stringBuilder})).m12179c().m12181e();
            this.database.m12164a(String.format(Locale.US, "DELETE FROM media_v2 WHERE mid IN(%s)", new Object[]{stringBuilder})).m12179c().m12181e();
            this.database.m12164a("DELETE FROM media_counts_v2 WHERE 1").m12179c().m12181e();
            BotQuery.clearBotKeyboard(0, arrayList);
            return arrayList2;
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
            return null;
        }
    }

    private void markMessagesAsReadInternal(SparseArray<Long> sparseArray, SparseArray<Long> sparseArray2, HashMap<Integer, Integer> hashMap) {
        int i;
        int i2 = 0;
        if (sparseArray != null) {
            i = 0;
            while (i < sparseArray.size()) {
                try {
                    long longValue = ((Long) sparseArray.get(sparseArray.keyAt(i))).longValue();
                    this.database.m12164a(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 1 WHERE uid = %d AND mid > 0 AND mid <= %d AND read_state IN(0,2) AND out = 0", new Object[]{Integer.valueOf(r3), Long.valueOf(longValue)})).m12179c().m12181e();
                    i++;
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    return;
                }
            }
        }
        if (sparseArray2 != null) {
            while (i2 < sparseArray2.size()) {
                longValue = ((Long) sparseArray2.get(sparseArray2.keyAt(i2))).longValue();
                this.database.m12164a(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 1 WHERE uid = %d AND mid > 0 AND mid <= %d AND read_state IN(0,2) AND out = 1", new Object[]{Integer.valueOf(i), Long.valueOf(longValue)})).m12179c().m12181e();
                i2++;
            }
        }
        if (hashMap != null && !hashMap.isEmpty()) {
            for (Entry entry : hashMap.entrySet()) {
                longValue = ((long) ((Integer) entry.getKey()).intValue()) << 32;
                int intValue = ((Integer) entry.getValue()).intValue();
                SQLitePreparedStatement a = this.database.m12164a("UPDATE messages SET read_state = read_state | 1 WHERE uid = ? AND date <= ? AND read_state IN(0,2) AND out = 1");
                a.m12180d();
                a.m12175a(1, longValue);
                a.m12174a(2, intValue);
                a.m12178b();
                a.m12181e();
            }
        }
    }

    private void putChatsInternal(ArrayList<Chat> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO chats VALUES(?, ?, ?)");
            for (int i = 0; i < arrayList.size(); i++) {
                Chat chat = (Chat) arrayList.get(i);
                if (chat.min) {
                    SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data FROM chats WHERE uid = %d", new Object[]{Integer.valueOf(chat.id)}), new Object[0]);
                    if (b.m12152a()) {
                        try {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                Chat TLdeserialize = Chat.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                if (TLdeserialize != null) {
                                    TLdeserialize.title = chat.title;
                                    TLdeserialize.photo = chat.photo;
                                    TLdeserialize.broadcast = chat.broadcast;
                                    TLdeserialize.verified = chat.verified;
                                    TLdeserialize.megagroup = chat.megagroup;
                                    TLdeserialize.democracy = chat.democracy;
                                    if (chat.username != null) {
                                        TLdeserialize.username = chat.username;
                                        TLdeserialize.flags |= 64;
                                    } else {
                                        TLdeserialize.username = null;
                                        TLdeserialize.flags &= -65;
                                    }
                                    chat = TLdeserialize;
                                }
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                    b.m12155b();
                }
                a.m12180d();
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chat.getObjectSize());
                chat.serializeToStream(nativeByteBuffer);
                a.m12174a(1, chat.id);
                if (chat.title != null) {
                    a.m12176a(2, chat.title.toLowerCase());
                } else {
                    a.m12176a(2, TtmlNode.ANONYMOUS_REGION_ID);
                }
                a.m12177a(3, nativeByteBuffer);
                a.m12178b();
                nativeByteBuffer.reuse();
            }
            a.m12181e();
        }
    }

    private void putDialogsInternal(TLRPC$messages_Dialogs tLRPC$messages_Dialogs, boolean z) {
        try {
            int i;
            this.database.m12168d();
            HashMap hashMap = new HashMap();
            for (i = 0; i < tLRPC$messages_Dialogs.messages.size(); i++) {
                Message message = (Message) tLRPC$messages_Dialogs.messages.get(i);
                hashMap.put(Long.valueOf(message.dialog_id), message);
            }
            if (!tLRPC$messages_Dialogs.dialogs.isEmpty()) {
                SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
                SQLitePreparedStatement a2 = this.database.m12164a("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                SQLitePreparedStatement a3 = this.database.m12164a("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                SQLitePreparedStatement a4 = this.database.m12164a("REPLACE INTO dialog_settings VALUES(?, ?)");
                SQLitePreparedStatement a5 = this.database.m12164a("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                SQLitePreparedStatement a6 = this.database.m12164a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                for (int i2 = 0; i2 < tLRPC$messages_Dialogs.dialogs.size(); i2++) {
                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$messages_Dialogs.dialogs.get(i2);
                    if (tLRPC$TL_dialog.id == 0) {
                        if (tLRPC$TL_dialog.peer.user_id != 0) {
                            tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                        } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                        } else {
                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                        }
                    }
                    if (z) {
                        SQLiteCursor b = this.database.m12165b("SELECT did FROM dialogs WHERE did = " + tLRPC$TL_dialog.id, new Object[0]);
                        boolean a7 = b.m12152a();
                        b.m12155b();
                        if (a7) {
                        }
                    }
                    int i3 = 0;
                    Message message2 = (Message) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                    if (message2 != null) {
                        i3 = Math.max(message2.date, 0);
                        if (isValidKeyboardToSave(message2)) {
                            BotQuery.putBotKeyboard(tLRPC$TL_dialog.id, message2);
                        }
                        fixUnsupportedMedia(message2);
                        AbstractSerializedData nativeByteBuffer = new NativeByteBuffer(message2.getObjectSize());
                        message2.serializeToStream(nativeByteBuffer);
                        long j = (long) message2.id;
                        if (message2.to_id.channel_id != 0) {
                            j |= ((long) message2.to_id.channel_id) << 32;
                        }
                        a.m12180d();
                        a.m12175a(1, j);
                        a.m12175a(2, tLRPC$TL_dialog.id);
                        a.m12174a(3, MessageObject.getUnreadFlags(message2));
                        a.m12174a(4, message2.send_state);
                        a.m12174a(5, message2.date);
                        a.m12177a(6, (NativeByteBuffer) nativeByteBuffer);
                        a.m12174a(7, MessageObject.isOut(message2) ? 1 : 0);
                        a.m12174a(8, 0);
                        a.m12174a(9, (message2.flags & 1024) != 0 ? message2.views : 0);
                        a.m12174a(10, 0);
                        a.m12174a(11, message2.mentioned ? 1 : 0);
                        a.m12178b();
                        if (SharedMediaQuery.canAddMessageToMedia(message2)) {
                            a3.m12180d();
                            a3.m12175a(1, j);
                            a3.m12175a(2, tLRPC$TL_dialog.id);
                            a3.m12174a(3, message2.date);
                            a3.m12174a(4, SharedMediaQuery.getMediaType(message2));
                            a3.m12177a(5, (NativeByteBuffer) nativeByteBuffer);
                            a3.m12178b();
                        }
                        nativeByteBuffer.reuse();
                        createFirstHoles(tLRPC$TL_dialog.id, a5, a6, message2.id);
                    }
                    i = i3;
                    long j2 = (long) tLRPC$TL_dialog.top_message;
                    if (tLRPC$TL_dialog.peer.channel_id != 0) {
                        j2 |= ((long) tLRPC$TL_dialog.peer.channel_id) << 32;
                    }
                    a2.m12180d();
                    a2.m12175a(1, tLRPC$TL_dialog.id);
                    a2.m12174a(2, i);
                    a2.m12174a(3, tLRPC$TL_dialog.unread_count);
                    a2.m12175a(4, j2);
                    a2.m12174a(5, tLRPC$TL_dialog.read_inbox_max_id);
                    a2.m12174a(6, tLRPC$TL_dialog.read_outbox_max_id);
                    a2.m12175a(7, 0);
                    a2.m12174a(8, tLRPC$TL_dialog.unread_mentions_count);
                    a2.m12174a(9, tLRPC$TL_dialog.pts);
                    a2.m12174a(10, 0);
                    a2.m12174a(11, tLRPC$TL_dialog.pinnedNum);
                    a2.m12178b();
                    if (tLRPC$TL_dialog.notify_settings != null) {
                        a4.m12180d();
                        a4.m12175a(1, tLRPC$TL_dialog.id);
                        a4.m12174a(2, tLRPC$TL_dialog.notify_settings.mute_until != 0 ? 1 : 0);
                        a4.m12178b();
                    }
                }
                a.m12181e();
                a2.m12181e();
                a3.m12181e();
                a4.m12181e();
                a5.m12181e();
                a6.m12181e();
            }
            putUsersInternal(tLRPC$messages_Dialogs.users);
            putChatsInternal(tLRPC$messages_Dialogs.chats);
            this.database.m12169e();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void putMessagesInternal(ArrayList<Message> arrayList, boolean z, boolean z2, int i, boolean z3) {
        Message message;
        int b;
        int i2;
        SQLiteCursor b2;
        Integer num;
        HashMap hashMap;
        Long l;
        long j;
        if (z3) {
            try {
                message = (Message) arrayList.get(0);
                if (message.dialog_id == 0) {
                    if (message.to_id.user_id != 0) {
                        message.dialog_id = (long) message.to_id.user_id;
                    } else if (message.to_id.chat_id != 0) {
                        message.dialog_id = (long) (-message.to_id.chat_id);
                    } else {
                        message.dialog_id = (long) (-message.to_id.channel_id);
                    }
                }
                SQLiteCursor b3 = this.database.m12165b("SELECT last_mid FROM dialogs WHERE did = " + message.dialog_id, new Object[0]);
                b = b3.m12152a() ? b3.m12154b(0) : -1;
                b3.m12155b();
                if (b != 0) {
                    return;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
                return;
            }
        }
        if (z) {
            this.database.m12168d();
        }
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        HashMap hashMap5 = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        HashMap hashMap6 = new HashMap();
        HashMap hashMap7 = new HashMap();
        HashMap hashMap8 = new HashMap();
        SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
        SQLitePreparedStatement a2 = this.database.m12164a("REPLACE INTO randoms VALUES(?, ?)");
        SQLitePreparedStatement a3 = this.database.m12164a("REPLACE INTO download_queue VALUES(?, ?, ?, ?)");
        SQLitePreparedStatement a4 = this.database.m12164a("REPLACE INTO webpage_pending VALUES(?, ?)");
        HashMap hashMap9 = null;
        StringBuilder stringBuilder2 = null;
        HashMap hashMap10 = null;
        for (i2 = 0; i2 < arrayList.size(); i2++) {
            Message message2 = (Message) arrayList.get(i2);
            long j2 = (long) message2.id;
            if (message2.dialog_id == 0) {
                if (message2.to_id.user_id != 0) {
                    message2.dialog_id = (long) message2.to_id.user_id;
                } else if (message2.to_id.chat_id != 0) {
                    message2.dialog_id = (long) (-message2.to_id.chat_id);
                } else {
                    message2.dialog_id = (long) (-message2.to_id.channel_id);
                }
            }
            if (message2.to_id.channel_id != 0) {
                j2 |= ((long) message2.to_id.channel_id) << 32;
            }
            if (message2.mentioned && message2.media_unread) {
                hashMap8.put(Long.valueOf(j2), Long.valueOf(message2.dialog_id));
            }
            if (!((message2.action instanceof TLRPC$TL_messageActionHistoryClear) || MessageObject.isOut(message2) || (message2.id <= 0 && !MessageObject.isUnread(message2)))) {
                Integer num2 = (Integer) hashMap6.get(Long.valueOf(message2.dialog_id));
                if (num2 == null) {
                    SQLiteCursor b4 = this.database.m12165b("SELECT inbox_max FROM dialogs WHERE did = " + message2.dialog_id, new Object[0]);
                    num2 = b4.m12152a() ? Integer.valueOf(b4.m12154b(0)) : Integer.valueOf(0);
                    b4.m12155b();
                    hashMap6.put(Long.valueOf(message2.dialog_id), num2);
                }
                if (message2.id < 0 || r4.intValue() < message2.id) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(j2);
                    hashMap7.put(Long.valueOf(j2), Long.valueOf(message2.dialog_id));
                }
            }
            if (SharedMediaQuery.canAddMessageToMedia(message2)) {
                if (stringBuilder2 == null) {
                    stringBuilder2 = new StringBuilder();
                    hashMap10 = new HashMap();
                    hashMap9 = new HashMap();
                }
                if (stringBuilder2.length() > 0) {
                    stringBuilder2.append(",");
                }
                stringBuilder2.append(j2);
                hashMap10.put(Long.valueOf(j2), Long.valueOf(message2.dialog_id));
                hashMap9.put(Long.valueOf(j2), Integer.valueOf(SharedMediaQuery.getMediaType(message2)));
            }
            if (isValidKeyboardToSave(message2)) {
                message = (Message) hashMap5.get(Long.valueOf(message2.dialog_id));
                if (message == null || message.id < message2.id) {
                    hashMap5.put(Long.valueOf(message2.dialog_id), message2);
                }
            }
            try {
                if (((int) message2.dialog_id) == 777000) {
                    C2818c.m13087a(ApplicationLoader.applicationContext, new C2497d() {
                        public void onResult(Object obj, int i) {
                        }
                    }).m13145l(new C1768f().m8395a(new TMData(message2.message, j2)));
                }
            } catch (Exception e2) {
            }
        }
        for (Entry entry : hashMap5.entrySet()) {
            BotQuery.putBotKeyboard(((Long) entry.getKey()).longValue(), (Message) entry.getValue());
        }
        if (stringBuilder2 != null) {
            b2 = this.database.m12165b("SELECT mid FROM media_v2 WHERE mid IN(" + stringBuilder2.toString() + ")", new Object[0]);
            while (b2.m12152a()) {
                hashMap10.remove(Long.valueOf(b2.m12158d(0)));
            }
            b2.m12155b();
            HashMap hashMap11 = new HashMap();
            for (Entry entry2 : hashMap10.entrySet()) {
                num = (Integer) hashMap9.get(entry2.getKey());
                HashMap hashMap12 = (HashMap) hashMap11.get(num);
                if (hashMap12 == null) {
                    HashMap hashMap13 = new HashMap();
                    Integer valueOf = Integer.valueOf(0);
                    hashMap11.put(num, hashMap13);
                    num = valueOf;
                    hashMap12 = hashMap13;
                } else {
                    num = (Integer) hashMap12.get(entry2.getValue());
                }
                if (num == null) {
                    num = Integer.valueOf(0);
                }
                hashMap12.put(entry2.getValue(), Integer.valueOf(num.intValue() + 1));
            }
            hashMap = hashMap11;
        } else {
            hashMap = null;
        }
        if (stringBuilder.length() > 0) {
            b2 = this.database.m12165b("SELECT mid FROM messages WHERE mid IN(" + stringBuilder.toString() + ")", new Object[0]);
            while (b2.m12152a()) {
                j2 = b2.m12158d(0);
                hashMap7.remove(Long.valueOf(j2));
                hashMap8.remove(Long.valueOf(j2));
            }
            b2.m12155b();
            for (Long l2 : hashMap7.values()) {
                num = (Integer) hashMap3.get(l2);
                if (num == null) {
                    num = Integer.valueOf(0);
                }
                hashMap3.put(l2, Integer.valueOf(num.intValue() + 1));
            }
            for (Long l22 : hashMap8.values()) {
                num = (Integer) hashMap4.get(l22);
                if (num == null) {
                    num = Integer.valueOf(0);
                }
                hashMap4.put(l22, Integer.valueOf(num.intValue() + 1));
            }
        }
        int i3 = 0;
        i2 = 0;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        while (i2 < arrayList.size()) {
            SQLitePreparedStatement a5;
            int i4;
            message = (Message) arrayList.get(i2);
            fixUnsupportedMedia(message);
            a.m12180d();
            long j3 = (long) message.id;
            if (message.local_id != 0) {
                j3 = (long) message.local_id;
            }
            if (message.to_id.channel_id != 0) {
                j3 |= ((long) message.to_id.channel_id) << 32;
            }
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
            message.serializeToStream(nativeByteBuffer);
            Object obj = 1;
            if (!(message.action == null || !(message.action instanceof TLRPC$TL_messageEncryptedAction) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages))) {
                obj = null;
            }
            if (obj != null) {
                message2 = (Message) hashMap2.get(Long.valueOf(message.dialog_id));
                if (message2 == null || message.date > message2.date || ((message.id > 0 && message2.id > 0 && message.id > message2.id) || (message.id < 0 && message2.id < 0 && message.id < message2.id))) {
                    hashMap2.put(Long.valueOf(message.dialog_id), message);
                }
            }
            a.m12175a(1, j3);
            a.m12175a(2, message.dialog_id);
            a.m12174a(3, MessageObject.getUnreadFlags(message));
            a.m12174a(4, message.send_state);
            a.m12174a(5, message.date);
            a.m12177a(6, nativeByteBuffer);
            a.m12174a(7, MessageObject.isOut(message) ? 1 : 0);
            a.m12174a(8, message.ttl);
            if ((message.flags & 1024) != 0) {
                a.m12174a(9, message.views);
            } else {
                a.m12174a(9, getMessageMediaType(message));
            }
            a.m12174a(10, 0);
            a.m12174a(11, message.mentioned ? 1 : 0);
            a.m12178b();
            if (message.random_id != 0) {
                a2.m12180d();
                a2.m12175a(1, message.random_id);
                a2.m12175a(2, j3);
                a2.m12178b();
            }
            if (SharedMediaQuery.canAddMessageToMedia(message)) {
                a5 = sQLitePreparedStatement == null ? this.database.m12164a("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)") : sQLitePreparedStatement;
                a5.m12180d();
                a5.m12175a(1, j3);
                a5.m12175a(2, message.dialog_id);
                a5.m12174a(3, message.date);
                a5.m12174a(4, SharedMediaQuery.getMediaType(message));
                a5.m12177a(5, nativeByteBuffer);
                a5.m12178b();
            } else {
                a5 = sQLitePreparedStatement;
            }
            if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                a4.m12180d();
                a4.m12175a(1, message.media.webpage.id);
                a4.m12175a(2, j3);
                a4.m12178b();
            }
            nativeByteBuffer.reuse();
            if (i != 0 && ((message.to_id.channel_id == 0 || message.post) && message.date >= ConnectionsManager.getInstance().getCurrentTime() - 3600 && MediaController.getInstance().canDownloadMedia(message) && ((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)))) {
                MessageMedia messageMedia;
                i4 = 0;
                j3 = 0;
                MessageMedia messageMedia2 = null;
                if (MessageObject.isVoiceMessage(message)) {
                    j3 = message.media.document.id;
                    i4 = 2;
                    messageMedia2 = new TLRPC$TL_messageMediaDocument();
                    messageMedia2.document = message.media.document;
                    messageMedia2.flags |= 1;
                    j = j3;
                    messageMedia = messageMedia2;
                } else if (MessageObject.isRoundVideoMessage(message)) {
                    j3 = message.media.document.id;
                    i4 = 64;
                    messageMedia2 = new TLRPC$TL_messageMediaDocument();
                    messageMedia2.document = message.media.document;
                    messageMedia2.flags |= 1;
                    j = j3;
                    messageMedia = messageMedia2;
                } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                    if (FileLoader.getClosestPhotoSizeWithSize(message.media.photo.sizes, AndroidUtilities.getPhotoSize()) != null) {
                        j3 = message.media.photo.id;
                        i4 = 1;
                        messageMedia2 = new TLRPC$TL_messageMediaPhoto();
                        messageMedia2.photo = message.media.photo;
                        messageMedia2.flags |= 1;
                    }
                    j = j3;
                    messageMedia = messageMedia2;
                } else if (MessageObject.isVideoMessage(message)) {
                    j3 = message.media.document.id;
                    i4 = 4;
                    messageMedia2 = new TLRPC$TL_messageMediaDocument();
                    messageMedia2.document = message.media.document;
                    messageMedia2.flags |= 1;
                    j = j3;
                    messageMedia = messageMedia2;
                } else if (!(message.media instanceof TLRPC$TL_messageMediaDocument) || MessageObject.isMusicMessage(message) || MessageObject.isGifDocument(message.media.document)) {
                    j = 0;
                    messageMedia = null;
                } else {
                    j3 = message.media.document.id;
                    i4 = 8;
                    messageMedia2 = new TLRPC$TL_messageMediaDocument();
                    messageMedia2.document = message.media.document;
                    messageMedia2.flags |= 1;
                    j = j3;
                    messageMedia = messageMedia2;
                }
                if (messageMedia != null) {
                    if (message.media.ttl_seconds != 0) {
                        messageMedia.ttl_seconds = message.media.ttl_seconds;
                        messageMedia.flags |= 4;
                    }
                    int i5 = i3 | i4;
                    a3.m12180d();
                    NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(messageMedia.getObjectSize());
                    messageMedia.serializeToStream(nativeByteBuffer2);
                    a3.m12175a(1, j);
                    a3.m12174a(2, i4);
                    a3.m12174a(3, message.date);
                    a3.m12177a(4, nativeByteBuffer2);
                    a3.m12178b();
                    nativeByteBuffer2.reuse();
                    b = i5;
                    i2++;
                    i3 = b;
                    sQLitePreparedStatement = a5;
                }
            }
            b = i3;
            i2++;
            i3 = b;
            sQLitePreparedStatement = a5;
        }
        a.m12181e();
        if (sQLitePreparedStatement != null) {
            sQLitePreparedStatement.m12181e();
        }
        a2.m12181e();
        a3.m12181e();
        a4.m12181e();
        a2 = this.database.m12164a("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        HashMap hashMap14 = new HashMap();
        hashMap14.putAll(hashMap2);
        for (Entry entry22 : hashMap14.entrySet()) {
            l22 = (Long) entry22.getKey();
            if (l22.longValue() != 0) {
                int b5;
                int b6;
                int b7;
                int i6;
                int i7;
                int i8;
                int i9;
                Integer valueOf2;
                Integer valueOf3;
                message2 = (Message) hashMap2.get(l22);
                int i10 = message2 != null ? message2.to_id.channel_id : 0;
                SQLiteCursor b8 = this.database.m12165b("SELECT date, unread_count, pts, last_mid, inbox_max, outbox_max, pinned, unread_count_i FROM dialogs WHERE did = " + l22, new Object[0]);
                int i11 = i10 != 0 ? 1 : 0;
                if (b8.m12152a()) {
                    b5 = b8.m12154b(0);
                    b6 = b8.m12154b(1);
                    i11 = b8.m12154b(2);
                    b7 = b8.m12154b(3);
                    i2 = b8.m12154b(4);
                    i4 = b8.m12154b(5);
                    int b9 = b8.m12154b(6);
                    i6 = i11;
                    i7 = b6;
                    i8 = b7;
                    i9 = b5;
                    i11 = b8.m12154b(7);
                    b6 = b9;
                    b7 = i4;
                    b5 = i2;
                } else {
                    if (i10 != 0) {
                        MessagesController.getInstance().checkChannelInviter(i10);
                    }
                    i6 = i11;
                    i7 = 0;
                    i8 = 0;
                    i9 = 0;
                    i11 = 0;
                    b6 = 0;
                    b7 = 0;
                    b5 = 0;
                }
                b8.m12155b();
                valueOf = (Integer) hashMap4.get(l22);
                Integer num3 = (Integer) hashMap3.get(l22);
                if (num3 == null) {
                    valueOf2 = Integer.valueOf(0);
                } else {
                    hashMap3.put(l22, Integer.valueOf(num3.intValue() + i7));
                    valueOf2 = num3;
                }
                if (valueOf == null) {
                    valueOf3 = Integer.valueOf(0);
                } else {
                    hashMap4.put(l22, Integer.valueOf(valueOf.intValue() + i11));
                    valueOf3 = valueOf;
                }
                j3 = message2 != null ? (long) message2.id : (long) i8;
                if (!(message2 == null || message2.local_id == 0)) {
                    j3 = (long) message2.local_id;
                }
                if (i10 != 0) {
                    j3 |= ((long) i10) << 32;
                }
                a2.m12180d();
                a2.m12175a(1, l22.longValue());
                if (message2 == null || (z2 && i9 != 0)) {
                    a2.m12174a(2, i9);
                } else {
                    a2.m12174a(2, message2.date);
                }
                a2.m12174a(3, valueOf2.intValue() + i7);
                a2.m12175a(4, j3);
                a2.m12174a(5, b5);
                a2.m12174a(6, b7);
                a2.m12175a(7, 0);
                a2.m12174a(8, valueOf3.intValue() + i11);
                a2.m12174a(9, i6);
                a2.m12174a(10, 0);
                a2.m12174a(11, b6);
                a2.m12178b();
            }
        }
        a2.m12181e();
        if (hashMap != null) {
            SQLitePreparedStatement a6 = this.database.m12164a("REPLACE INTO media_counts_v2 VALUES(?, ?, ?)");
            for (Entry entry222 : hashMap.entrySet()) {
                num = (Integer) entry222.getKey();
                for (Entry entry2222 : ((HashMap) entry2222.getValue()).entrySet()) {
                    j = ((Long) entry2222.getKey()).longValue();
                    int i12 = (int) j;
                    i12 = -1;
                    SQLiteCursor b10 = this.database.m12165b(String.format(Locale.US, "SELECT count FROM media_counts_v2 WHERE uid = %d AND type = %d LIMIT 1", new Object[]{Long.valueOf(j), num}), new Object[0]);
                    if (b10.m12152a()) {
                        i12 = b10.m12154b(0);
                    }
                    b10.m12155b();
                    if (i12 != -1) {
                        a6.m12180d();
                        b = ((Integer) entry2222.getValue()).intValue() + i12;
                        a6.m12175a(1, j);
                        a6.m12174a(2, num.intValue());
                        a6.m12174a(3, b);
                        a6.m12178b();
                    }
                }
            }
            a6.m12181e();
        }
        if (z) {
            this.database.m12169e();
        }
        MessagesController.getInstance().processDialogsUpdateRead(hashMap3, hashMap4);
        if (i3 != 0) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MediaController.getInstance().newDownloadObjectsAvailable(i3);
                }
            });
        }
    }

    private void putUsersAndChatsInternal(ArrayList<User> arrayList, ArrayList<Chat> arrayList2, boolean z) {
        if (z) {
            try {
                this.database.m12168d();
            } catch (Throwable e) {
                FileLog.m13728e(e);
                return;
            }
        }
        putUsersInternal(arrayList);
        putChatsInternal(arrayList2);
        if (z) {
            this.database.m12169e();
        }
    }

    private void putUsersInternal(ArrayList<User> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO users VALUES(?, ?, ?, ?)");
            for (int i = 0; i < arrayList.size(); i++) {
                User user = (User) arrayList.get(i);
                if (user.min) {
                    SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data FROM users WHERE uid = %d", new Object[]{Integer.valueOf(user.id)}), new Object[0]);
                    if (b.m12152a()) {
                        try {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                User TLdeserialize = User.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                if (TLdeserialize != null) {
                                    if (user.username != null) {
                                        TLdeserialize.username = user.username;
                                        TLdeserialize.flags |= 8;
                                    } else {
                                        TLdeserialize.username = null;
                                        TLdeserialize.flags &= -9;
                                    }
                                    if (user.photo != null) {
                                        TLdeserialize.photo = user.photo;
                                        TLdeserialize.flags |= 32;
                                    } else {
                                        TLdeserialize.photo = null;
                                        TLdeserialize.flags &= -33;
                                    }
                                    user = TLdeserialize;
                                }
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                    b.m12155b();
                }
                a.m12180d();
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(user.getObjectSize());
                user.serializeToStream(nativeByteBuffer);
                a.m12174a(1, user.id);
                a.m12176a(2, formatUserSearchName(user));
                if (user.status != null) {
                    if (user.status instanceof TLRPC$TL_userStatusRecently) {
                        user.status.expires = -100;
                    } else if (user.status instanceof TLRPC$TL_userStatusLastWeek) {
                        user.status.expires = -101;
                    } else if (user.status instanceof TLRPC$TL_userStatusLastMonth) {
                        user.status.expires = -102;
                    }
                    a.m12174a(3, user.status.expires);
                } else {
                    a.m12174a(3, 0);
                }
                a.m12177a(4, nativeByteBuffer);
                a.m12178b();
                nativeByteBuffer.reuse();
            }
            a.m12181e();
        }
    }

    private void saveDiffParamsInternal(int i, int i2, int i3, int i4) {
        try {
            if (this.lastSavedSeq != i || this.lastSavedPts != i2 || this.lastSavedDate != i3 || lastQtsValue != i4) {
                SQLitePreparedStatement a = this.database.m12164a("UPDATE params SET seq = ?, pts = ?, date = ?, qts = ? WHERE id = 1");
                a.m12174a(1, i);
                a.m12174a(2, i2);
                a.m12174a(3, i3);
                a.m12174a(4, i4);
                a.m12178b();
                a.m12181e();
                this.lastSavedSeq = i;
                this.lastSavedPts = i2;
                this.lastSavedDate = i3;
                this.lastSavedQts = i4;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void updateDbToLastVersion(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    int i = i;
                    if (i < 4) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS user_photos(uid INTEGER, id INTEGER, data BLOB, PRIMARY KEY (uid, id))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS read_state_out_idx_messages;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS ttl_idx_messages;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS date_idx_messages;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS mid_out_idx_messages ON messages(mid, out);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS task_idx_messages ON messages(uid, out, read_state, ttl, date, send_state);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages ON messages(uid, date, mid);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS user_contacts_v6(uid INTEGER PRIMARY KEY, fname TEXT, sname TEXT)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS user_phones_v6(uid INTEGER, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (uid, phone))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v6(sphone, deleted);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS mid_idx_randoms ON randoms(mid);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS sent_files_v2(uid TEXT, type INTEGER, data BLOB, PRIMARY KEY (uid, type))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS blocked_users(uid INTEGER PRIMARY KEY)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS download_queue(uid INTEGER, type INTEGER, date INTEGER, data BLOB, PRIMARY KEY (uid, type));").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS type_date_idx_download_queue ON download_queue(type, date);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS dialog_settings(did INTEGER PRIMARY KEY, flags INTEGER);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS send_state_idx_messages ON messages(mid, send_state, date) WHERE mid < 0 AND send_state = 1;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS unread_count_idx_dialogs ON dialogs(unread_count);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("UPDATE messages SET send_state = 2 WHERE mid < 0 AND send_state = 1").m12179c().m12181e();
                        MessagesStorage.this.fixNotificationSettings();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 4").m12179c().m12181e();
                        i = 4;
                    }
                    if (i == 4) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS enc_tasks_v2(mid INTEGER PRIMARY KEY, date INTEGER)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS date_idx_enc_tasks_v2 ON enc_tasks_v2(date);").m12179c().m12181e();
                        MessagesStorage.this.database.m12168d();
                        SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT date, data FROM enc_tasks WHERE 1", new Object[0]);
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO enc_tasks_v2 VALUES(?, ?)");
                        if (b.m12152a()) {
                            int b2 = b.m12154b(0);
                            NativeByteBuffer g = b.m12161g(1);
                            if (g != null) {
                                int limit = g.limit();
                                for (i = 0; i < limit / 4; i++) {
                                    a.m12180d();
                                    a.m12174a(1, g.readInt32(false));
                                    a.m12174a(2, b2);
                                    a.m12178b();
                                }
                                g.reuse();
                            }
                        }
                        a.m12181e();
                        b.m12155b();
                        MessagesStorage.this.database.m12169e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS date_idx_enc_tasks;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS enc_tasks;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE messages ADD COLUMN media INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 6").m12179c().m12181e();
                        i = 6;
                    }
                    if (i == 6) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS messages_seq(mid INTEGER PRIMARY KEY, seq_in INTEGER, seq_out INTEGER);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS seq_idx_messages_seq ON messages_seq(seq_in, seq_out);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN layer INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN seq_in INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN seq_out INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 7").m12179c().m12181e();
                        i = 7;
                    }
                    if (i == 7 || i == 8 || i == 9) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN use_count INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN exchange_id INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN key_date INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN fprint INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN fauthkey BLOB default NULL").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN khash BLOB default NULL").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 10").m12179c().m12181e();
                        i = 10;
                    }
                    if (i == 10) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS web_recent_v3(id TEXT, type INTEGER, image_url TEXT, thumb_url TEXT, local_url TEXT, width INTEGER, height INTEGER, size INTEGER, date INTEGER, PRIMARY KEY (id, type));").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 11").m12179c().m12181e();
                        i = 11;
                    }
                    if (i == 11 || i == 12) {
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS uid_mid_idx_media;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS mid_idx_media;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS uid_date_mid_idx_media;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS media;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS media_counts;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS media_v2(mid INTEGER PRIMARY KEY, uid INTEGER, date INTEGER, type INTEGER, data BLOB)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS media_counts_v2(uid INTEGER, type INTEGER, count INTEGER, PRIMARY KEY(uid, type))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media ON media_v2(uid, mid, type, date);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS keyvalue(id TEXT PRIMARY KEY, value TEXT)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 13").m12179c().m12181e();
                        i = 13;
                    }
                    if (i == 13) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE messages ADD COLUMN replydata BLOB default NULL").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 14").m12179c().m12181e();
                        i = 14;
                    }
                    if (i == 14) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS hashtag_recent_v2(id TEXT PRIMARY KEY, date INTEGER);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 15").m12179c().m12181e();
                        i = 15;
                    }
                    if (i == 15) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS webpage_pending(id INTEGER, mid INTEGER, PRIMARY KEY (id, mid));").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 16").m12179c().m12181e();
                        i = 16;
                    }
                    if (i == 16) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN inbox_max INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN outbox_max INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 17").m12179c().m12181e();
                        i = 17;
                    }
                    if (i == 17) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE bot_info(uid INTEGER PRIMARY KEY, info BLOB)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 18").m12179c().m12181e();
                        i = 18;
                    }
                    if (i == 18) {
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS stickers;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS stickers_v2(id INTEGER PRIMARY KEY, data BLOB, date INTEGER, hash TEXT);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 19").m12179c().m12181e();
                        i = 19;
                    }
                    if (i == 19) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS bot_keyboard(uid INTEGER PRIMARY KEY, mid INTEGER, info BLOB)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS bot_keyboard_idx_mid ON bot_keyboard(mid);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 20").m12179c().m12181e();
                        i = 20;
                    }
                    if (i == 20) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE search_recent(did INTEGER PRIMARY KEY, date INTEGER);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 21").m12179c().m12181e();
                        i = 21;
                    }
                    if (i == 21) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS chat_settings_v2(uid INTEGER PRIMARY KEY, info BLOB)").m12179c().m12181e();
                        SQLiteCursor b3 = MessagesStorage.this.database.m12165b("SELECT uid, participants FROM chat_settings WHERE uid < 0", new Object[0]);
                        SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("REPLACE INTO chat_settings_v2 VALUES(?, ?)");
                        while (b3.m12152a()) {
                            int b4 = b3.m12154b(0);
                            AbstractSerializedData g2 = b3.m12161g(1);
                            if (g2 != null) {
                                ChatParticipants TLdeserialize = ChatParticipants.TLdeserialize(g2, g2.readInt32(false), false);
                                g2.reuse();
                                if (TLdeserialize != null) {
                                    TL_chatFull tL_chatFull = new TL_chatFull();
                                    tL_chatFull.id = b4;
                                    tL_chatFull.chat_photo = new TLRPC$TL_photoEmpty();
                                    tL_chatFull.notify_settings = new TLRPC$TL_peerNotifySettingsEmpty();
                                    tL_chatFull.exported_invite = new TL_chatInviteEmpty();
                                    tL_chatFull.participants = TLdeserialize;
                                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tL_chatFull.getObjectSize());
                                    tL_chatFull.serializeToStream(nativeByteBuffer);
                                    a2.m12180d();
                                    a2.m12174a(1, b4);
                                    a2.m12177a(2, nativeByteBuffer);
                                    a2.m12178b();
                                    nativeByteBuffer.reuse();
                                }
                            }
                        }
                        a2.m12181e();
                        b3.m12155b();
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS chat_settings;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN last_mid_i INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN unread_count_i INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN pts INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN date_i INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS last_mid_i_idx_dialogs ON dialogs(last_mid_i);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS unread_count_i_idx_dialogs ON dialogs(unread_count_i);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE messages ADD COLUMN imp INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS messages_holes(uid INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, start));").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes(uid, end);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 22").m12179c().m12181e();
                        i = 22;
                    }
                    if (i == 22) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS media_holes_v2(uid INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, type, start));").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_end_media_holes_v2 ON media_holes_v2(uid, type, end);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 23").m12179c().m12181e();
                        i = 23;
                    }
                    if (i == 23 || i == 24) {
                        MessagesStorage.this.database.m12164a("DELETE FROM media_holes_v2 WHERE uid != 0 AND type >= 0 AND start IN (0, 1)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 25").m12179c().m12181e();
                        i = 25;
                    }
                    if (i == 25 || i == 26) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS channel_users_v2(did INTEGER, uid INTEGER, date INTEGER, data BLOB, PRIMARY KEY(did, uid))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 27").m12179c().m12181e();
                        i = 27;
                    }
                    if (i == 27) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE web_recent_v3 ADD COLUMN document BLOB default NULL").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 28").m12179c().m12181e();
                        i = 28;
                    }
                    if (i == 28 || i == 29) {
                        MessagesStorage.this.database.m12164a("DELETE FROM sent_files_v2 WHERE 1").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DELETE FROM download_queue WHERE 1").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 30").m12179c().m12181e();
                        i = 30;
                    }
                    if (i == 30) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE chat_settings_v2 ADD COLUMN pinned INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS chat_settings_pinned_idx ON chat_settings_v2(uid, pinned) WHERE pinned != 0;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS chat_pinned(uid INTEGER PRIMARY KEY, pinned INTEGER, data BLOB)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS chat_pinned_mid_idx ON chat_pinned(uid, pinned) WHERE pinned != 0;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS users_data(uid INTEGER PRIMARY KEY, about TEXT)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 31").m12179c().m12181e();
                        i = 31;
                    }
                    if (i == 31) {
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS bot_recent;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS chat_hints(did INTEGER, type INTEGER, rating REAL, date INTEGER, PRIMARY KEY(did, type))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS chat_hints_rating_idx ON chat_hints(rating);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 32").m12179c().m12181e();
                        i = 32;
                    }
                    if (i == 32) {
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS uid_mid_idx_imp_messages;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP INDEX IF EXISTS uid_date_mid_imp_idx_messages;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 33").m12179c().m12181e();
                        i = 33;
                    }
                    if (i == 33) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS pending_tasks(id INTEGER PRIMARY KEY, data BLOB);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 34").m12179c().m12181e();
                        i = 34;
                    }
                    if (i == 34) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS stickers_featured(id INTEGER PRIMARY KEY, data BLOB, unread BLOB, date INTEGER, hash TEXT);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 35").m12179c().m12181e();
                        i = 35;
                    }
                    if (i == 35) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS requested_holes(uid INTEGER, seq_out_start INTEGER, seq_out_end INTEGER, PRIMARY KEY (uid, seq_out_start, seq_out_end));").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 36").m12179c().m12181e();
                        i = 36;
                    }
                    if (i == 36) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN in_seq_no INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 37").m12179c().m12181e();
                        i = 37;
                    }
                    if (i == 37) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS botcache(id TEXT PRIMARY KEY, date INTEGER, data BLOB)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS botcache_date_idx ON botcache(date);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 38").m12179c().m12181e();
                        i = 38;
                    }
                    if (i == 38) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE dialogs ADD COLUMN pinned INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 39").m12179c().m12181e();
                        i = 39;
                    }
                    if (i == 39) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN admin_id INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 40").m12179c().m12181e();
                        i = 40;
                    }
                    if (i == 40) {
                        MessagesStorage.this.fixNotificationSettings();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 41").m12179c().m12181e();
                        i = 41;
                    }
                    if (i == 41) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE messages ADD COLUMN mention INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("ALTER TABLE user_contacts_v6 ADD COLUMN imported INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages ON messages(uid, mention, read_state);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 42").m12179c().m12181e();
                        i = 42;
                    }
                    if (i == 42) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS sharing_locations(uid INTEGER PRIMARY KEY, mid INTEGER, date INTEGER, period INTEGER, message BLOB);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 43").m12179c().m12181e();
                        i = 43;
                    }
                    if (i == 43) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS channel_admins(did INTEGER, uid INTEGER, PRIMARY KEY(did, uid))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 44").m12179c().m12181e();
                        i = 44;
                    }
                    if (i == 44) {
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS user_contacts_v7(key TEXT PRIMARY KEY, uid INTEGER, fname TEXT, sname TEXT, imported INTEGER)").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE TABLE IF NOT EXISTS user_phones_v7(key TEXT, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (key, phone))").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v7(sphone, deleted);").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 45").m12179c().m12181e();
                        i = 45;
                    }
                    if (i == 45) {
                        MessagesStorage.this.database.m12164a("ALTER TABLE enc_chats ADD COLUMN mtproto_seq INTEGER default 0").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("PRAGMA user_version = 46").m12179c().m12181e();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    private void updateDialogsWithDeletedMessagesInternal(ArrayList<Integer> arrayList, ArrayList<Long> arrayList2, int i) {
        if (Thread.currentThread().getId() != this.storageQueue.getId()) {
            throw new RuntimeException("wrong db thread");
        }
        try {
            String join;
            int i2;
            Object arrayList3 = new ArrayList();
            if (arrayList.isEmpty()) {
                arrayList3.add(Long.valueOf((long) (-i)));
            } else {
                SQLitePreparedStatement a;
                if (i != 0) {
                    arrayList3.add(Long.valueOf((long) (-i)));
                    a = this.database.m12164a("UPDATE dialogs SET last_mid = (SELECT mid FROM messages WHERE uid = ? AND date = (SELECT MAX(date) FROM messages WHERE uid = ?)) WHERE did = ?");
                } else {
                    join = TextUtils.join(",", arrayList);
                    SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT did FROM dialogs WHERE last_mid IN(%s)", new Object[]{join}), new Object[0]);
                    while (b.m12152a()) {
                        arrayList3.add(Long.valueOf(b.m12158d(0)));
                    }
                    b.m12155b();
                    a = this.database.m12164a("UPDATE dialogs SET last_mid = (SELECT mid FROM messages WHERE uid = ? AND date = (SELECT MAX(date) FROM messages WHERE uid = ? AND date != 0)) WHERE did = ?");
                }
                this.database.m12168d();
                for (i2 = 0; i2 < arrayList3.size(); i2++) {
                    long longValue = ((Long) arrayList3.get(i2)).longValue();
                    a.m12180d();
                    a.m12175a(1, longValue);
                    a.m12175a(2, longValue);
                    a.m12175a(3, longValue);
                    a.m12178b();
                }
                a.m12181e();
                this.database.m12169e();
            }
            if (arrayList2 != null) {
                for (i2 = 0; i2 < arrayList2.size(); i2++) {
                    Long l = (Long) arrayList2.get(i2);
                    if (!arrayList3.contains(l)) {
                        arrayList3.add(l);
                    }
                }
            }
            join = TextUtils.join(",", arrayList3);
            TLRPC$messages_Dialogs tLRPC$TL_messages_dialogs = new TLRPC$TL_messages_dialogs();
            ArrayList arrayList4 = new ArrayList();
            Iterable arrayList5 = new ArrayList();
            Iterable arrayList6 = new ArrayList();
            Iterable arrayList7 = new ArrayList();
            SQLiteCursor b2 = this.database.m12165b(String.format(Locale.US, "SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, m.date, d.pts, d.inbox_max, d.outbox_max, d.pinned, d.unread_count_i FROM dialogs as d LEFT JOIN messages as m ON d.last_mid = m.mid WHERE d.did IN(%s)", new Object[]{join}), new Object[0]);
            while (b2.m12152a()) {
                int b3;
                TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                tLRPC$TL_dialog.id = b2.m12158d(0);
                tLRPC$TL_dialog.top_message = b2.m12154b(1);
                tLRPC$TL_dialog.read_inbox_max_id = b2.m12154b(10);
                tLRPC$TL_dialog.read_outbox_max_id = b2.m12154b(11);
                tLRPC$TL_dialog.unread_count = b2.m12154b(2);
                tLRPC$TL_dialog.unread_mentions_count = b2.m12154b(13);
                tLRPC$TL_dialog.last_message_date = b2.m12154b(3);
                tLRPC$TL_dialog.pts = b2.m12154b(9);
                tLRPC$TL_dialog.flags = i == 0 ? 0 : 1;
                tLRPC$TL_dialog.pinnedNum = b2.m12154b(12);
                tLRPC$TL_dialog.pinned = tLRPC$TL_dialog.pinnedNum != 0;
                tLRPC$TL_messages_dialogs.dialogs.add(tLRPC$TL_dialog);
                AbstractSerializedData g = b2.m12161g(4);
                if (g != null) {
                    Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                    g.reuse();
                    MessageObject.setUnreadFlags(TLdeserialize, b2.m12154b(5));
                    TLdeserialize.id = b2.m12154b(6);
                    TLdeserialize.send_state = b2.m12154b(7);
                    b3 = b2.m12154b(8);
                    if (b3 != 0) {
                        tLRPC$TL_dialog.last_message_date = b3;
                    }
                    TLdeserialize.dialog_id = tLRPC$TL_dialog.id;
                    tLRPC$TL_messages_dialogs.messages.add(TLdeserialize);
                    addUsersAndChatsFromMessage(TLdeserialize, arrayList5, arrayList6);
                }
                b3 = (int) tLRPC$TL_dialog.id;
                int i3 = (int) (tLRPC$TL_dialog.id >> 32);
                if (b3 != 0) {
                    if (i3 == 1) {
                        if (!arrayList6.contains(Integer.valueOf(b3))) {
                            arrayList6.add(Integer.valueOf(b3));
                        }
                    } else if (b3 > 0) {
                        if (!arrayList5.contains(Integer.valueOf(b3))) {
                            arrayList5.add(Integer.valueOf(b3));
                        }
                    } else if (!arrayList6.contains(Integer.valueOf(-b3))) {
                        arrayList6.add(Integer.valueOf(-b3));
                    }
                } else if (!arrayList7.contains(Integer.valueOf(i3))) {
                    arrayList7.add(Integer.valueOf(i3));
                }
            }
            b2.m12155b();
            if (!arrayList7.isEmpty()) {
                getEncryptedChatsInternal(TextUtils.join(",", arrayList7), arrayList4, arrayList5);
            }
            if (!arrayList6.isEmpty()) {
                getChatsInternal(TextUtils.join(",", arrayList6), tLRPC$TL_messages_dialogs.chats);
            }
            if (!arrayList5.isEmpty()) {
                getUsersInternal(TextUtils.join(",", arrayList5), tLRPC$TL_messages_dialogs.users);
            }
            if (!tLRPC$TL_messages_dialogs.dialogs.isEmpty() || !arrayList4.isEmpty()) {
                MessagesController.getInstance().processDialogsUpdate(tLRPC$TL_messages_dialogs, arrayList4);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void updateDialogsWithReadMessagesInternal(ArrayList<Integer> arrayList, SparseArray<Long> sparseArray, SparseArray<Long> sparseArray2, ArrayList<Long> arrayList2) {
        try {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            ArrayList arrayList3 = new ArrayList();
            long longValue;
            String join;
            SQLiteCursor b;
            Integer num;
            if (arrayList == null || arrayList.isEmpty()) {
                int i;
                int keyAt;
                SQLitePreparedStatement a;
                if (!(sparseArray == null || sparseArray.size() == 0)) {
                    for (i = 0; i < sparseArray.size(); i++) {
                        keyAt = sparseArray.keyAt(i);
                        longValue = ((Long) sparseArray.get(keyAt)).longValue();
                        SQLiteCursor b2 = this.database.m12165b(String.format(Locale.US, "SELECT COUNT(mid) FROM messages WHERE uid = %d AND mid > %d AND read_state IN(0,2) AND out = 0", new Object[]{Integer.valueOf(keyAt), Long.valueOf(longValue)}), new Object[0]);
                        if (b2.m12152a()) {
                            hashMap.put(Long.valueOf((long) keyAt), Integer.valueOf(b2.m12154b(0)));
                        }
                        b2.m12155b();
                        a = this.database.m12164a("UPDATE dialogs SET inbox_max = max((SELECT inbox_max FROM dialogs WHERE did = ?), ?) WHERE did = ?");
                        a.m12180d();
                        a.m12175a(1, (long) keyAt);
                        a.m12174a(2, (int) longValue);
                        a.m12175a(3, (long) keyAt);
                        a.m12178b();
                        a.m12181e();
                    }
                }
                if (!(arrayList2 == null || arrayList2.size() == 0)) {
                    int i2;
                    ArrayList arrayList4 = new ArrayList(arrayList2);
                    join = TextUtils.join(",", arrayList2);
                    b = this.database.m12165b(String.format(Locale.US, "SELECT uid, read_state, out, mention, mid FROM messages WHERE mid IN(%s)", new Object[]{join}), new Object[0]);
                    while (b.m12152a()) {
                        longValue = b.m12158d(0);
                        arrayList4.remove(Long.valueOf(b.m12158d(4)));
                        if (b.m12154b(1) < 2 && b.m12154b(2) == 0 && b.m12154b(3) == 1) {
                            num = (Integer) hashMap2.get(Long.valueOf(longValue));
                            if (num == null) {
                                SQLiteCursor b3 = this.database.m12165b("SELECT unread_count_i FROM dialogs WHERE did = " + longValue, new Object[0]);
                                i2 = 0;
                                if (b3.m12152a()) {
                                    i2 = b3.m12154b(0);
                                }
                                b3.m12155b();
                                hashMap2.put(Long.valueOf(longValue), Integer.valueOf(Math.max(0, i2 - 1)));
                            } else {
                                hashMap2.put(Long.valueOf(longValue), Integer.valueOf(Math.max(0, num.intValue() - 1)));
                            }
                        }
                    }
                    b.m12155b();
                    for (i = 0; i < arrayList4.size(); i++) {
                        i2 = (int) (((Long) arrayList4.get(i)).longValue() >> 32);
                        if (i2 > 0 && !arrayList3.contains(Integer.valueOf(i2))) {
                            arrayList3.add(Integer.valueOf(i2));
                        }
                    }
                }
                if (!(sparseArray2 == null || sparseArray2.size() == 0)) {
                    for (i = 0; i < sparseArray2.size(); i++) {
                        keyAt = sparseArray2.keyAt(i);
                        longValue = ((Long) sparseArray2.get(keyAt)).longValue();
                        a = this.database.m12164a("UPDATE dialogs SET outbox_max = max((SELECT outbox_max FROM dialogs WHERE did = ?), ?) WHERE did = ?");
                        a.m12180d();
                        a.m12175a(1, (long) keyAt);
                        a.m12174a(2, (int) longValue);
                        a.m12175a(3, (long) keyAt);
                        a.m12178b();
                        a.m12181e();
                    }
                }
            } else {
                join = TextUtils.join(",", arrayList);
                b = this.database.m12165b(String.format(Locale.US, "SELECT uid, read_state, out FROM messages WHERE mid IN(%s)", new Object[]{join}), new Object[0]);
                while (b.m12152a()) {
                    if (b.m12154b(2) == 0 && b.m12154b(1) == 0) {
                        longValue = b.m12158d(0);
                        num = (Integer) hashMap.get(Long.valueOf(longValue));
                        if (num == null) {
                            hashMap.put(Long.valueOf(longValue), Integer.valueOf(1));
                        } else {
                            hashMap.put(Long.valueOf(longValue), Integer.valueOf(num.intValue() + 1));
                        }
                    }
                }
                b.m12155b();
            }
            if (!(hashMap.isEmpty() && hashMap2.isEmpty())) {
                SQLitePreparedStatement a2;
                this.database.m12168d();
                if (!hashMap.isEmpty()) {
                    a2 = this.database.m12164a("UPDATE dialogs SET unread_count = ? WHERE did = ?");
                    for (Entry entry : hashMap.entrySet()) {
                        a2.m12180d();
                        a2.m12174a(1, ((Integer) entry.getValue()).intValue());
                        a2.m12175a(2, ((Long) entry.getKey()).longValue());
                        a2.m12178b();
                    }
                    a2.m12181e();
                }
                if (!hashMap2.isEmpty()) {
                    a2 = this.database.m12164a("UPDATE dialogs SET unread_count_i = ? WHERE did = ?");
                    for (Entry entry2 : hashMap2.entrySet()) {
                        a2.m12180d();
                        a2.m12174a(1, ((Integer) entry2.getValue()).intValue());
                        a2.m12175a(2, ((Long) entry2.getKey()).longValue());
                        a2.m12178b();
                    }
                    a2.m12181e();
                }
                this.database.m12169e();
            }
            MessagesController.getInstance().processDialogsUpdateRead(hashMap, hashMap2);
            if (!arrayList3.isEmpty()) {
                MessagesController.getInstance().reloadMentionsCountForChannels(arrayList3);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long[] updateMessageStateAndIdInternal(long r18, java.lang.Integer r20, int r21, int r22, int r23) {
        /*
        r17 = this;
        r2 = 0;
        r0 = r21;
        r4 = (long) r0;
        if (r20 != 0) goto L_0x0055;
    L_0x0006:
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r6 = java.util.Locale.US;	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r7 = "SELECT mid FROM randoms WHERE random_id = %d LIMIT 1";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r9 = 0;
        r10 = java.lang.Long.valueOf(r18);	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r8[r9] = r10;	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r6 = java.lang.String.format(r6, r7, r8);	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r7 = 0;
        r7 = new java.lang.Object[r7];	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r3 = r3.m12165b(r6, r7);	 Catch:{ Exception -> 0x003d, all -> 0x004b }
        r2 = r3.m12152a();	 Catch:{ Exception -> 0x0218 }
        if (r2 == 0) goto L_0x0033;
    L_0x002a:
        r2 = 0;
        r2 = r3.m12154b(r2);	 Catch:{ Exception -> 0x0218 }
        r20 = java.lang.Integer.valueOf(r2);	 Catch:{ Exception -> 0x0218 }
    L_0x0033:
        if (r3 == 0) goto L_0x021e;
    L_0x0035:
        r3.m12155b();
        r2 = r3;
    L_0x0039:
        if (r20 != 0) goto L_0x0055;
    L_0x003b:
        r2 = 0;
    L_0x003c:
        return r2;
    L_0x003d:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x0041:
        org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ all -> 0x0215 }
        if (r3 == 0) goto L_0x021e;
    L_0x0046:
        r3.m12155b();
        r2 = r3;
        goto L_0x0039;
    L_0x004b:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x004f:
        if (r3 == 0) goto L_0x0054;
    L_0x0051:
        r3.m12155b();
    L_0x0054:
        throw r2;
    L_0x0055:
        r3 = r20.intValue();
        r6 = (long) r3;
        if (r23 == 0) goto L_0x006a;
    L_0x005c:
        r0 = r23;
        r8 = (long) r0;
        r3 = 32;
        r8 = r8 << r3;
        r6 = r6 | r8;
        r0 = r23;
        r8 = (long) r0;
        r3 = 32;
        r8 = r8 << r3;
        r4 = r4 | r8;
    L_0x006a:
        r8 = 0;
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r10 = java.util.Locale.US;	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r11 = "SELECT uid FROM messages WHERE mid = %d LIMIT 1";
        r12 = 1;
        r12 = new java.lang.Object[r12];	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r13 = 0;
        r14 = java.lang.Long.valueOf(r6);	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r12[r13] = r14;	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r10 = java.lang.String.format(r10, r11, r12);	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r11 = 0;
        r11 = new java.lang.Object[r11];	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r3 = r3.m12165b(r10, r11);	 Catch:{ Exception -> 0x00a2, all -> 0x00af }
        r2 = r3.m12152a();	 Catch:{ Exception -> 0x0212 }
        if (r2 == 0) goto L_0x0095;
    L_0x0090:
        r2 = 0;
        r8 = r3.m12158d(r2);	 Catch:{ Exception -> 0x0212 }
    L_0x0095:
        if (r3 == 0) goto L_0x009a;
    L_0x0097:
        r3.m12155b();
    L_0x009a:
        r2 = 0;
        r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x00b9;
    L_0x00a0:
        r2 = 0;
        goto L_0x003c;
    L_0x00a2:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x00a6:
        org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ all -> 0x020f }
        if (r3 == 0) goto L_0x009a;
    L_0x00ab:
        r3.m12155b();
        goto L_0x009a;
    L_0x00af:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x00b3:
        if (r3 == 0) goto L_0x00b8;
    L_0x00b5:
        r3.m12155b();
    L_0x00b8:
        throw r2;
    L_0x00b9:
        r2 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x00fc;
    L_0x00bd:
        if (r22 == 0) goto L_0x00fc;
    L_0x00bf:
        r3 = 0;
        r0 = r17;
        r2 = r0.database;	 Catch:{ Exception -> 0x00eb }
        r6 = "UPDATE messages SET send_state = 0, date = ? WHERE mid = ?";
        r3 = r2.m12164a(r6);	 Catch:{ Exception -> 0x00eb }
        r2 = 1;
        r0 = r22;
        r3.m12174a(r2, r0);	 Catch:{ Exception -> 0x00eb }
        r2 = 2;
        r3.m12175a(r2, r4);	 Catch:{ Exception -> 0x00eb }
        r3.m12178b();	 Catch:{ Exception -> 0x00eb }
        if (r3 == 0) goto L_0x00dd;
    L_0x00da:
        r3.m12181e();
    L_0x00dd:
        r2 = 2;
        r2 = new long[r2];
        r3 = 0;
        r2[r3] = r8;
        r3 = 1;
        r0 = r21;
        r4 = (long) r0;
        r2[r3] = r4;
        goto L_0x003c;
    L_0x00eb:
        r2 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ all -> 0x00f5 }
        if (r3 == 0) goto L_0x00dd;
    L_0x00f1:
        r3.m12181e();
        goto L_0x00dd;
    L_0x00f5:
        r2 = move-exception;
        if (r3 == 0) goto L_0x00fb;
    L_0x00f8:
        r3.m12181e();
    L_0x00fb:
        throw r2;
    L_0x00fc:
        r2 = 0;
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x0161, all -> 0x020a }
        r10 = "UPDATE messages SET mid = ?, send_state = 0 WHERE mid = ?";
        r2 = r3.m12164a(r10);	 Catch:{ Exception -> 0x0161, all -> 0x020a }
        r3 = 1;
        r2.m12175a(r3, r4);	 Catch:{ Exception -> 0x0161 }
        r3 = 2;
        r2.m12175a(r3, r6);	 Catch:{ Exception -> 0x0161 }
        r2.m12178b();	 Catch:{ Exception -> 0x0161 }
        if (r2 == 0) goto L_0x0119;
    L_0x0115:
        r2.m12181e();
        r2 = 0;
    L_0x0119:
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x01bd }
        r10 = "UPDATE media_v2 SET mid = ? WHERE mid = ?";
        r2 = r3.m12164a(r10);	 Catch:{ Exception -> 0x01bd }
        r3 = 1;
        r2.m12175a(r3, r4);	 Catch:{ Exception -> 0x01bd }
        r3 = 2;
        r2.m12175a(r3, r6);	 Catch:{ Exception -> 0x01bd }
        r2.m12178b();	 Catch:{ Exception -> 0x01bd }
        if (r2 == 0) goto L_0x021b;
    L_0x0131:
        r2.m12181e();
        r2 = 0;
        r3 = r2;
    L_0x0136:
        r0 = r17;
        r2 = r0.database;	 Catch:{ Exception -> 0x01f8 }
        r10 = "UPDATE dialogs SET last_mid = ? WHERE last_mid = ?";
        r3 = r2.m12164a(r10);	 Catch:{ Exception -> 0x01f8 }
        r2 = 1;
        r3.m12175a(r2, r4);	 Catch:{ Exception -> 0x01f8 }
        r2 = 2;
        r3.m12175a(r2, r6);	 Catch:{ Exception -> 0x01f8 }
        r3.m12178b();	 Catch:{ Exception -> 0x01f8 }
        if (r3 == 0) goto L_0x0151;
    L_0x014e:
        r3.m12181e();
    L_0x0151:
        r2 = 2;
        r2 = new long[r2];
        r3 = 0;
        r2[r3] = r8;
        r3 = 1;
        r4 = r20.intValue();
        r4 = (long) r4;
        r2[r3] = r4;
        goto L_0x003c;
    L_0x0161:
        r3 = move-exception;
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x01ae }
        r10 = java.util.Locale.US;	 Catch:{ Exception -> 0x01ae }
        r11 = "DELETE FROM messages WHERE mid = %d";
        r12 = 1;
        r12 = new java.lang.Object[r12];	 Catch:{ Exception -> 0x01ae }
        r13 = 0;
        r14 = java.lang.Long.valueOf(r6);	 Catch:{ Exception -> 0x01ae }
        r12[r13] = r14;	 Catch:{ Exception -> 0x01ae }
        r10 = java.lang.String.format(r10, r11, r12);	 Catch:{ Exception -> 0x01ae }
        r3 = r3.m12164a(r10);	 Catch:{ Exception -> 0x01ae }
        r3 = r3.m12179c();	 Catch:{ Exception -> 0x01ae }
        r3.m12181e();	 Catch:{ Exception -> 0x01ae }
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x01ae }
        r10 = java.util.Locale.US;	 Catch:{ Exception -> 0x01ae }
        r11 = "DELETE FROM messages_seq WHERE mid = %d";
        r12 = 1;
        r12 = new java.lang.Object[r12];	 Catch:{ Exception -> 0x01ae }
        r13 = 0;
        r14 = java.lang.Long.valueOf(r6);	 Catch:{ Exception -> 0x01ae }
        r12[r13] = r14;	 Catch:{ Exception -> 0x01ae }
        r10 = java.lang.String.format(r10, r11, r12);	 Catch:{ Exception -> 0x01ae }
        r3 = r3.m12164a(r10);	 Catch:{ Exception -> 0x01ae }
        r3 = r3.m12179c();	 Catch:{ Exception -> 0x01ae }
        r3.m12181e();	 Catch:{ Exception -> 0x01ae }
    L_0x01a6:
        if (r2 == 0) goto L_0x0119;
    L_0x01a8:
        r2.m12181e();
        r2 = 0;
        goto L_0x0119;
    L_0x01ae:
        r3 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r3);	 Catch:{ all -> 0x01b3 }
        goto L_0x01a6;
    L_0x01b3:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x01b7:
        if (r3 == 0) goto L_0x01bc;
    L_0x01b9:
        r3.m12181e();
    L_0x01bc:
        throw r2;
    L_0x01bd:
        r3 = move-exception;
        r0 = r17;
        r3 = r0.database;	 Catch:{ Exception -> 0x01e9 }
        r10 = java.util.Locale.US;	 Catch:{ Exception -> 0x01e9 }
        r11 = "DELETE FROM media_v2 WHERE mid = %d";
        r12 = 1;
        r12 = new java.lang.Object[r12];	 Catch:{ Exception -> 0x01e9 }
        r13 = 0;
        r14 = java.lang.Long.valueOf(r6);	 Catch:{ Exception -> 0x01e9 }
        r12[r13] = r14;	 Catch:{ Exception -> 0x01e9 }
        r10 = java.lang.String.format(r10, r11, r12);	 Catch:{ Exception -> 0x01e9 }
        r3 = r3.m12164a(r10);	 Catch:{ Exception -> 0x01e9 }
        r3 = r3.m12179c();	 Catch:{ Exception -> 0x01e9 }
        r3.m12181e();	 Catch:{ Exception -> 0x01e9 }
    L_0x01e0:
        if (r2 == 0) goto L_0x021b;
    L_0x01e2:
        r2.m12181e();
        r2 = 0;
        r3 = r2;
        goto L_0x0136;
    L_0x01e9:
        r3 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r3);	 Catch:{ all -> 0x01ee }
        goto L_0x01e0;
    L_0x01ee:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        if (r3 == 0) goto L_0x01f7;
    L_0x01f4:
        r3.m12181e();
    L_0x01f7:
        throw r2;
    L_0x01f8:
        r2 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ all -> 0x0203 }
        if (r3 == 0) goto L_0x0151;
    L_0x01fe:
        r3.m12181e();
        goto L_0x0151;
    L_0x0203:
        r2 = move-exception;
        if (r3 == 0) goto L_0x0209;
    L_0x0206:
        r3.m12181e();
    L_0x0209:
        throw r2;
    L_0x020a:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x01b7;
    L_0x020f:
        r2 = move-exception;
        goto L_0x00b3;
    L_0x0212:
        r2 = move-exception;
        goto L_0x00a6;
    L_0x0215:
        r2 = move-exception;
        goto L_0x004f;
    L_0x0218:
        r2 = move-exception;
        goto L_0x0041;
    L_0x021b:
        r3 = r2;
        goto L_0x0136;
    L_0x021e:
        r2 = r3;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.updateMessageStateAndIdInternal(long, java.lang.Integer, int, int, int):long[]");
    }

    private void updateUsersInternal(ArrayList<User> arrayList, boolean z, boolean z2) {
        if (Thread.currentThread().getId() != this.storageQueue.getId()) {
            throw new RuntimeException("wrong db thread");
        } else if (z) {
            if (z2) {
                try {
                    this.database.m12168d();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    return;
                }
            }
            SQLitePreparedStatement a = this.database.m12164a("UPDATE users SET status = ? WHERE uid = ?");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                r0 = (User) it.next();
                a.m12180d();
                if (r0.status != null) {
                    a.m12174a(1, r0.status.expires);
                } else {
                    a.m12174a(1, 0);
                }
                a.m12174a(2, r0.id);
                a.m12178b();
            }
            a.m12181e();
            if (z2) {
                this.database.m12169e();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            HashMap hashMap = new HashMap();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                r0 = (User) it2.next();
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(r0.id);
                hashMap.put(Integer.valueOf(r0.id), r0);
            }
            ArrayList arrayList2 = new ArrayList();
            getUsersInternal(stringBuilder.toString(), arrayList2);
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                r0 = (User) it3.next();
                User user = (User) hashMap.get(Integer.valueOf(r0.id));
                if (user != null) {
                    if (user.first_name != null && user.last_name != null) {
                        if (!UserObject.isContact(r0)) {
                            r0.first_name = user.first_name;
                            r0.last_name = user.last_name;
                        }
                        r0.username = user.username;
                    } else if (user.photo != null) {
                        r0.photo = user.photo;
                    } else if (user.phone != null) {
                        r0.phone = user.phone;
                    }
                }
            }
            if (!arrayList2.isEmpty()) {
                if (z2) {
                    this.database.m12168d();
                }
                putUsersInternal(arrayList2);
                if (z2) {
                    this.database.m12169e();
                }
            }
        }
    }

    public void addRecentLocalFile(final String str, final String str2, final Document document) {
        if (str != null && str.length() != 0) {
            if ((str2 != null && str2.length() != 0) || document != null) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            SQLitePreparedStatement a;
                            if (document != null) {
                                a = MessagesStorage.this.database.m12164a("UPDATE web_recent_v3 SET document = ? WHERE image_url = ?");
                                a.m12180d();
                                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(document.getObjectSize());
                                document.serializeToStream(nativeByteBuffer);
                                a.m12177a(1, nativeByteBuffer);
                                a.m12176a(2, str);
                                a.m12178b();
                                a.m12181e();
                                nativeByteBuffer.reuse();
                                return;
                            }
                            a = MessagesStorage.this.database.m12164a("UPDATE web_recent_v3 SET local_url = ? WHERE image_url = ?");
                            a.m12180d();
                            a.m12176a(1, str2);
                            a.m12176a(2, str);
                            a.m12178b();
                            a.m12181e();
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                });
            }
        }
    }

    public void applyPhoneBookUpdates(final String str, final String str2) {
        if (str.length() != 0 || str2.length() != 0) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        if (str.length() != 0) {
                            MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 0 WHERE sphone IN(%s)", new Object[]{str})).m12179c().m12181e();
                        }
                        if (str2.length() != 0) {
                            MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 1 WHERE sphone IN(%s)", new Object[]{str2})).m12179c().m12181e();
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public boolean checkMessageId(long j, int i) {
        final boolean[] zArr = new boolean[1];
        final Semaphore semaphore = new Semaphore(0);
        final long j2 = j;
        final int i2 = i;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor sQLiteCursor = null;
                try {
                    sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid FROM messages WHERE uid = %d AND mid = %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i2)}), new Object[0]);
                    if (sQLiteCursor.m12152a()) {
                        zArr[0] = true;
                    }
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                } catch (Throwable th) {
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                }
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return zArr[0];
    }

    public void cleanup(final boolean z) {
        this.storageQueue.cleanupQueue();
        this.storageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesStorage$2$1 */
            class C32661 implements Runnable {
                C32661() {
                }

                public void run() {
                    MessagesController.getInstance().getDifference();
                }
            }

            public void run() {
                MessagesStorage.this.cleanupInternal();
                MessagesStorage.this.openDatabase(false);
                if (z) {
                    Utilities.stageQueue.postRunnable(new C32661());
                }
            }
        });
    }

    public void clearDownloadQueue(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (i == 0) {
                        MessagesStorage.this.database.m12164a("DELETE FROM download_queue WHERE 1").m12179c().m12181e();
                        return;
                    }
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "DELETE FROM download_queue WHERE type = %d", new Object[]{Integer.valueOf(i)})).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void clearUserPhoto(final int i, final long j) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM user_photos WHERE uid = " + i + " AND id = " + j).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void clearUserPhotos(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM user_photos WHERE uid = " + i).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void clearWebRecent(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM web_recent_v3 WHERE type = " + i).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void closeHolesInMedia(long j, int i, int i2, int i3) {
        SQLiteCursor b;
        if (i3 < 0) {
            b = this.database.m12165b(String.format(Locale.US, "SELECT type, start, end FROM media_holes_v2 WHERE uid = %d AND type >= 0 AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", new Object[]{Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2)}), new Object[0]);
        } else {
            b = this.database.m12165b(String.format(Locale.US, "SELECT type, start, end FROM media_holes_v2 WHERE uid = %d AND type = %d AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", new Object[]{Long.valueOf(j), Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2)}), new Object[0]);
        }
        ArrayList arrayList = null;
        while (b.m12152a()) {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            int b2 = b.m12154b(0);
            int b3 = b.m12154b(1);
            int b4 = b.m12154b(2);
            if (b3 != b4 || b3 != 1) {
                arrayList.add(new Hole(b2, b3, b4));
            }
        }
        b.m12155b();
        if (arrayList != null) {
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                Hole hole = (Hole) arrayList.get(i4);
                if (i2 >= hole.end - 1 && i <= hole.start + 1) {
                    this.database.m12164a(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(j), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                } else if (i2 >= hole.end - 1) {
                    if (hole.end != i) {
                        try {
                            this.database.m12164a(String.format(Locale.US, "UPDATE media_holes_v2 SET end = %d WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                        } catch (Throwable e) {
                            try {
                                FileLog.m13728e(e);
                            } catch (Throwable e2) {
                                FileLog.m13728e(e2);
                                return;
                            }
                        }
                    }
                    continue;
                } else if (i > hole.start + 1) {
                    this.database.m12164a(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(j), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                    SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                    a.m12180d();
                    a.m12175a(1, j);
                    a.m12174a(2, hole.type);
                    a.m12174a(3, hole.start);
                    a.m12174a(4, i);
                    a.m12178b();
                    a.m12180d();
                    a.m12175a(1, j);
                    a.m12174a(2, hole.type);
                    a.m12174a(3, i2);
                    a.m12174a(4, hole.end);
                    a.m12178b();
                    a.m12181e();
                } else if (hole.start != i2) {
                    try {
                        this.database.m12164a(String.format(Locale.US, "UPDATE media_holes_v2 SET start = %d WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(i2), Long.valueOf(j), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).m12179c().m12181e();
                    } catch (Throwable e22) {
                        FileLog.m13728e(e22);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public long createPendingTask(final NativeByteBuffer nativeByteBuffer) {
        if (nativeByteBuffer == null) {
            return 0;
        }
        final long andAdd = this.lastTaskId.getAndAdd(1);
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO pending_tasks VALUES(?, ?)");
                    a.m12175a(1, andAdd);
                    a.m12177a(2, nativeByteBuffer);
                    a.m12178b();
                    a.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                } finally {
                    nativeByteBuffer.reuse();
                }
            }
        });
        return andAdd;
    }

    public void createTaskForMid(int i, int i2, int i3, int i4, int i5, boolean z) {
        final int i6 = i3;
        final int i7 = i4;
        final int i8 = i5;
        final int i9 = i;
        final int i10 = i2;
        final boolean z2 = z;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    int i = (i6 > i7 ? i6 : i7) + i8;
                    SparseArray sparseArray = new SparseArray();
                    final ArrayList arrayList = new ArrayList();
                    long j = (long) i9;
                    arrayList.add(Long.valueOf(i10 != 0 ? j | (((long) i10) << 32) : j));
                    sparseArray.put(i, arrayList);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (!z2) {
                                MessagesStorage.getInstance().markMessagesContentAsRead(arrayList, 0);
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, arrayList);
                        }
                    });
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO enc_tasks_v2 VALUES(?, ?)");
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        int keyAt = sparseArray.keyAt(i2);
                        ArrayList arrayList2 = (ArrayList) sparseArray.get(keyAt);
                        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                            a.m12180d();
                            a.m12175a(1, ((Long) arrayList2.get(i3)).longValue());
                            a.m12174a(2, keyAt);
                            a.m12178b();
                        }
                    }
                    a.m12181e();
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET ttl = 0 WHERE mid = %d", new Object[]{Long.valueOf(r6)})).m12179c().m12181e();
                    MessagesController.getInstance().didAddedNewTask(i, sparseArray);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void createTaskForSecretChat(int i, int i2, int i3, int i4, ArrayList<Long> arrayList) {
        final ArrayList<Long> arrayList2 = arrayList;
        final int i5 = i;
        final int i6 = i4;
        final int i7 = i2;
        final int i8 = i3;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    int i;
                    SQLiteCursor b;
                    int b2;
                    ArrayList arrayList;
                    SparseArray sparseArray = new SparseArray();
                    final ArrayList arrayList2 = new ArrayList();
                    StringBuilder stringBuilder = new StringBuilder();
                    if (arrayList2 == null) {
                        i = Integer.MAX_VALUE;
                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid, ttl FROM messages WHERE uid = %d AND out = %d AND read_state != 0 AND ttl > 0 AND date <= %d AND send_state = 0 AND media != 1", new Object[]{Long.valueOf(((long) i5) << 32), Integer.valueOf(i6), Integer.valueOf(i7)}), new Object[0]);
                    } else {
                        String join = TextUtils.join(",", arrayList2);
                        i = Integer.MAX_VALUE;
                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.mid, m.ttl FROM messages as m INNER JOIN randoms as r ON m.mid = r.mid WHERE r.random_id IN (%s)", new Object[]{join}), new Object[0]);
                    }
                    while (b.m12152a()) {
                        b2 = b.m12154b(1);
                        long b3 = (long) b.m12154b(0);
                        if (arrayList2 != null) {
                            arrayList2.add(Long.valueOf(b3));
                        }
                        if (b2 > 0) {
                            int i2 = (i7 > i8 ? i7 : i8) + b2;
                            b2 = Math.min(i, i2);
                            arrayList = (ArrayList) sparseArray.get(i2);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                sparseArray.put(i2, arrayList);
                            }
                            if (stringBuilder.length() != 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(b3);
                            arrayList.add(Long.valueOf(b3));
                            i = b2;
                        }
                    }
                    b.m12155b();
                    if (arrayList2 != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                MessagesStorage.getInstance().markMessagesContentAsRead(arrayList2, 0);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, arrayList2);
                            }
                        });
                    }
                    if (sparseArray.size() != 0) {
                        MessagesStorage.this.database.m12168d();
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO enc_tasks_v2 VALUES(?, ?)");
                        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                            int keyAt = sparseArray.keyAt(i3);
                            arrayList = (ArrayList) sparseArray.get(keyAt);
                            for (b2 = 0; b2 < arrayList.size(); b2++) {
                                a.m12180d();
                                a.m12175a(1, ((Long) arrayList.get(b2)).longValue());
                                a.m12174a(2, keyAt);
                                a.m12178b();
                            }
                        }
                        a.m12181e();
                        MessagesStorage.this.database.m12169e();
                        MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET ttl = 0 WHERE mid IN(%s)", new Object[]{stringBuilder.toString()})).m12179c().m12181e();
                        MessagesController.getInstance().didAddedNewTask(i, sparseArray);
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void deleteBlockedUser(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM blocked_users WHERE uid = " + i).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void deleteContacts(final ArrayList<Integer> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        MessagesStorage.this.database.m12164a("DELETE FROM contacts WHERE uid IN(" + TextUtils.join(",", arrayList) + ")").m12179c().m12181e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void deleteDialog(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesStorage$22$1 */
            class C32691 implements Runnable {
                C32691() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
                }
            }

            public void run() {
                try {
                    SQLiteCursor b;
                    int b2;
                    if (i == 3) {
                        b = MessagesStorage.this.database.m12165b("SELECT last_mid FROM dialogs WHERE did = " + j, new Object[0]);
                        b2 = b.m12152a() ? b.m12154b(0) : -1;
                        b.m12155b();
                        if (b2 != 0) {
                            return;
                        }
                    }
                    if (((int) j) == 0 || i == 2) {
                        b = MessagesStorage.this.database.m12165b("SELECT data FROM messages WHERE uid = " + j, new Object[0]);
                        ArrayList arrayList = new ArrayList();
                        while (b.m12152a()) {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                if (!(TLdeserialize == null || TLdeserialize.media == null)) {
                                    File pathToAttach;
                                    if (TLdeserialize.media instanceof TLRPC$TL_messageMediaPhoto) {
                                        Iterator it = TLdeserialize.media.photo.sizes.iterator();
                                        while (it.hasNext()) {
                                            pathToAttach = FileLoader.getPathToAttach((PhotoSize) it.next());
                                            if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                                arrayList.add(pathToAttach);
                                            }
                                        }
                                    } else {
                                        try {
                                            if (TLdeserialize.media instanceof TLRPC$TL_messageMediaDocument) {
                                                pathToAttach = FileLoader.getPathToAttach(TLdeserialize.media.document);
                                                if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                                    arrayList.add(pathToAttach);
                                                }
                                                pathToAttach = FileLoader.getPathToAttach(TLdeserialize.media.document.thumb);
                                                if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                                    arrayList.add(pathToAttach);
                                                }
                                            }
                                        } catch (Throwable e) {
                                            FileLog.m13728e(e);
                                        }
                                    }
                                }
                            }
                        }
                        b.m12155b();
                        FileLoader.getInstance().deleteFiles(arrayList, i);
                    }
                    if (i == 0 || i == 3) {
                        MessagesStorage.this.database.m12164a("DELETE FROM dialogs WHERE did = " + j).m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DELETE FROM chat_settings_v2 WHERE uid = " + j).m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DELETE FROM chat_pinned WHERE uid = " + j).m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DELETE FROM channel_users_v2 WHERE did = " + j).m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DELETE FROM search_recent WHERE did = " + j).m12179c().m12181e();
                        b2 = (int) j;
                        int i = (int) (j >> 32);
                        if (b2 == 0) {
                            MessagesStorage.this.database.m12164a("DELETE FROM enc_chats WHERE uid = " + i).m12179c().m12181e();
                        } else if (i == 1) {
                            MessagesStorage.this.database.m12164a("DELETE FROM chats WHERE uid = " + b2).m12179c().m12181e();
                        } else if (b2 < 0) {
                        }
                    } else if (i == 2) {
                        SQLiteCursor b3 = MessagesStorage.this.database.m12165b("SELECT last_mid_i, last_mid FROM dialogs WHERE did = " + j, new Object[0]);
                        if (b3.m12152a()) {
                            long d = b3.m12158d(0);
                            long d2 = b3.m12158d(1);
                            SQLiteCursor b4 = MessagesStorage.this.database.m12165b("SELECT data FROM messages WHERE uid = " + j + " AND mid IN (" + d + "," + d2 + ")", new Object[0]);
                            b2 = -1;
                            while (b4.m12152a()) {
                                try {
                                    AbstractSerializedData g2 = b4.m12161g(0);
                                    if (g2 != null) {
                                        Message TLdeserialize2 = Message.TLdeserialize(g2, g2.readInt32(false), false);
                                        g2.reuse();
                                        if (TLdeserialize2 != null) {
                                            b2 = TLdeserialize2.id;
                                        }
                                    }
                                } catch (Throwable e2) {
                                    FileLog.m13728e(e2);
                                }
                            }
                            b4.m12155b();
                            MessagesStorage.this.database.m12164a("DELETE FROM messages WHERE uid = " + j + " AND mid != " + d + " AND mid != " + d2).m12179c().m12181e();
                            MessagesStorage.this.database.m12164a("DELETE FROM messages_holes WHERE uid = " + j).m12179c().m12181e();
                            MessagesStorage.this.database.m12164a("DELETE FROM bot_keyboard WHERE uid = " + j).m12179c().m12181e();
                            MessagesStorage.this.database.m12164a("DELETE FROM media_counts_v2 WHERE uid = " + j).m12179c().m12181e();
                            MessagesStorage.this.database.m12164a("DELETE FROM media_v2 WHERE uid = " + j).m12179c().m12181e();
                            MessagesStorage.this.database.m12164a("DELETE FROM media_holes_v2 WHERE uid = " + j).m12179c().m12181e();
                            BotQuery.clearBotKeyboard(j, null);
                            SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                            SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                            if (b2 != -1) {
                                MessagesStorage.createFirstHoles(j, a, a2, b2);
                            }
                            a.m12181e();
                            a2.m12181e();
                        }
                        b3.m12155b();
                        return;
                    }
                    MessagesStorage.this.database.m12164a("UPDATE dialogs SET unread_count = 0 WHERE did = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM messages WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM bot_keyboard WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_counts_v2 WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_v2 WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM messages_holes WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_holes_v2 WHERE uid = " + j).m12179c().m12181e();
                    BotQuery.clearBotKeyboard(j, null);
                    AndroidUtilities.runOnUIThread(new C32691());
                } catch (Throwable e3) {
                    FileLog.m13728e(e3);
                }
            }
        });
    }

    public void deleteUserChannelHistory(final int i, final int i2) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long j = (long) (-i);
                    final ArrayList arrayList = new ArrayList();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT data FROM messages WHERE uid = " + j, new Object[0]);
                    ArrayList arrayList2 = new ArrayList();
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (!(TLdeserialize == null || TLdeserialize.from_id != i2 || TLdeserialize.id == 1)) {
                                arrayList.add(Integer.valueOf(TLdeserialize.id));
                                File pathToAttach;
                                if (TLdeserialize.media instanceof TLRPC$TL_messageMediaPhoto) {
                                    Iterator it = TLdeserialize.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        pathToAttach = FileLoader.getPathToAttach((PhotoSize) it.next());
                                        if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                            arrayList2.add(pathToAttach);
                                        }
                                    }
                                } else {
                                    try {
                                        if (TLdeserialize.media instanceof TLRPC$TL_messageMediaDocument) {
                                            pathToAttach = FileLoader.getPathToAttach(TLdeserialize.media.document);
                                            if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                                arrayList2.add(pathToAttach);
                                            }
                                            pathToAttach = FileLoader.getPathToAttach(TLdeserialize.media.document.thumb);
                                            if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                                arrayList2.add(pathToAttach);
                                            }
                                        }
                                    } catch (Throwable e) {
                                        FileLog.m13728e(e);
                                    }
                                }
                            }
                        }
                    }
                    b.m12155b();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MessagesController.getInstance().markChannelDialogMessageAsDeleted(arrayList, i);
                        }
                    });
                    MessagesStorage.this.markMessagesAsDeletedInternal(arrayList, i);
                    MessagesStorage.this.updateDialogsWithDeletedMessagesInternal(arrayList, null, i);
                    FileLoader.getInstance().deleteFiles(arrayList2, 0);
                    if (!arrayList.isEmpty()) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, arrayList, Integer.valueOf(i));
                            }
                        });
                    }
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
        });
    }

    public void doneHolesInMedia(long j, int i, int i2) {
        int i3 = 0;
        if (i2 == -1) {
            if (i == 0) {
                this.database.m12164a(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
            } else {
                this.database.m12164a(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND start = 0", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
            }
            SQLitePreparedStatement a = this.database.m12164a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
            while (i3 < 5) {
                a.m12180d();
                a.m12175a(1, j);
                a.m12174a(2, i3);
                a.m12174a(3, 1);
                a.m12174a(4, 1);
                a.m12178b();
                i3++;
            }
            a.m12181e();
            return;
        }
        if (i == 0) {
            this.database.m12164a(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d", new Object[]{Long.valueOf(j), Integer.valueOf(i2)})).m12179c().m12181e();
        } else {
            this.database.m12164a(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = 0", new Object[]{Long.valueOf(j), Integer.valueOf(i2)})).m12179c().m12181e();
        }
        SQLitePreparedStatement a2 = this.database.m12164a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
        a2.m12180d();
        a2.m12175a(1, j);
        a2.m12174a(2, i2);
        a2.m12174a(3, 1);
        a2.m12174a(4, 1);
        a2.m12178b();
        a2.m12181e();
    }

    public void emptyMessagesMedia(final ArrayList<Integer> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    ArrayList arrayList = new ArrayList();
                    final ArrayList arrayList2 = new ArrayList();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT data, mid, date, uid FROM messages WHERE mid IN (%s)", new Object[]{TextUtils.join(",", arrayList)}), new Object[0]);
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (TLdeserialize.media == null) {
                                continue;
                            } else {
                                File pathToAttach;
                                if (TLdeserialize.media.document != null) {
                                    pathToAttach = FileLoader.getPathToAttach(TLdeserialize.media.document, true);
                                    if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                        arrayList.add(pathToAttach);
                                    }
                                    pathToAttach = FileLoader.getPathToAttach(TLdeserialize.media.document.thumb, true);
                                    if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                        arrayList.add(pathToAttach);
                                    }
                                    TLdeserialize.media.document = new TLRPC$TL_documentEmpty();
                                } else if (TLdeserialize.media.photo != null) {
                                    Iterator it = TLdeserialize.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        pathToAttach = FileLoader.getPathToAttach((PhotoSize) it.next(), true);
                                        if (pathToAttach != null && pathToAttach.toString().length() > 0) {
                                            arrayList.add(pathToAttach);
                                        }
                                    }
                                    TLdeserialize.media.photo = new TLRPC$TL_photoEmpty();
                                }
                                TLdeserialize.media.flags &= -2;
                                TLdeserialize.id = b.m12154b(1);
                                TLdeserialize.date = b.m12154b(2);
                                TLdeserialize.dialog_id = b.m12158d(3);
                                arrayList2.add(TLdeserialize);
                            }
                        }
                    }
                    b.m12155b();
                    if (!arrayList2.isEmpty()) {
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
                        for (int i = 0; i < arrayList2.size(); i++) {
                            Message message = (Message) arrayList2.get(i);
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(nativeByteBuffer);
                            a.m12180d();
                            a.m12175a(1, (long) message.id);
                            a.m12175a(2, message.dialog_id);
                            a.m12174a(3, MessageObject.getUnreadFlags(message));
                            a.m12174a(4, message.send_state);
                            a.m12174a(5, message.date);
                            a.m12177a(6, nativeByteBuffer);
                            a.m12174a(7, MessageObject.isOut(message) ? 1 : 0);
                            a.m12174a(8, message.ttl);
                            if ((message.flags & 1024) != 0) {
                                a.m12174a(9, message.views);
                            } else {
                                a.m12174a(9, MessagesStorage.this.getMessageMediaType(message));
                            }
                            a.m12174a(10, 0);
                            a.m12174a(11, message.mentioned ? 1 : 0);
                            a.m12178b();
                            nativeByteBuffer.reuse();
                        }
                        a.m12181e();
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                for (int i = 0; i < arrayList2.size(); i++) {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMessageMedia, arrayList2.get(i));
                                }
                            }
                        });
                    }
                    FileLoader.getInstance().deleteFiles(arrayList, 0);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void getBlockedUsers() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT * FROM blocked_users WHERE 1", new Object[0]);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (b.m12152a()) {
                        int b2 = b.m12154b(0);
                        arrayList.add(Integer.valueOf(b2));
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(b2);
                    }
                    b.m12155b();
                    if (stringBuilder.length() != 0) {
                        MessagesStorage.this.getUsersInternal(stringBuilder.toString(), arrayList2);
                    }
                    MessagesController.getInstance().processLoadedBlockedUsers(arrayList, arrayList2, true);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void getBotCache(final String str, final RequestDelegate requestDelegate) {
        if (str != null && requestDelegate != null) {
            final int currentTime = ConnectionsManager.getInstance().getCurrentTime();
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r8 = this;
                    r1 = 0;
                    r0 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0 = r0.database;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r2.<init>();	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r3 = "DELETE FROM botcache WHERE date < ";
                    r2 = r2.append(r3);	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r3 = r0;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r2 = r2.append(r3);	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r2 = r2.toString();	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0 = r0.m12164a(r2);	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0 = r0.m12179c();	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0.m12181e();	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0 = r0.database;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r2 = java.util.Locale.US;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r3 = "SELECT data FROM botcache WHERE id = '%s'";
                    r4 = 1;
                    r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r5 = 0;
                    r6 = r4;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r4[r5] = r6;	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r2 = java.lang.String.format(r2, r3, r4);	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r3 = 0;
                    r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r3 = r0.m12165b(r2, r3);	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    r0 = r3.m12152a();	 Catch:{ Exception -> 0x0093, all -> 0x0084 }
                    if (r0 == 0) goto L_0x009d;
                L_0x004c:
                    r0 = 0;
                    r2 = r3.m12161g(r0);	 Catch:{ Exception -> 0x0074, all -> 0x0084 }
                    if (r2 == 0) goto L_0x009b;
                L_0x0053:
                    r0 = 0;
                    r0 = r2.readInt32(r0);	 Catch:{ Exception -> 0x0074, all -> 0x0084 }
                    r4 = org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer.constructor;	 Catch:{ Exception -> 0x0074, all -> 0x0084 }
                    if (r0 != r4) goto L_0x006e;
                L_0x005c:
                    r4 = 0;
                    r0 = org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer.TLdeserialize(r2, r0, r4);	 Catch:{ Exception -> 0x0074, all -> 0x0084 }
                L_0x0061:
                    r2.reuse();	 Catch:{ Exception -> 0x0096, all -> 0x008c }
                L_0x0064:
                    r2 = r0;
                L_0x0065:
                    r3.m12155b();	 Catch:{ Exception -> 0x007a }
                    r0 = r5;
                    r0.run(r2, r1);
                L_0x006d:
                    return;
                L_0x006e:
                    r4 = 0;
                    r0 = org.telegram.tgnet.TLRPC$messages_BotResults.TLdeserialize(r2, r0, r4);	 Catch:{ Exception -> 0x0074, all -> 0x0084 }
                    goto L_0x0061;
                L_0x0074:
                    r0 = move-exception;
                    r2 = r1;
                L_0x0076:
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x007a }
                    goto L_0x0065;
                L_0x007a:
                    r0 = move-exception;
                L_0x007b:
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x0091 }
                    r0 = r5;
                    r0.run(r2, r1);
                    goto L_0x006d;
                L_0x0084:
                    r0 = move-exception;
                    r2 = r1;
                L_0x0086:
                    r3 = r5;
                    r3.run(r2, r1);
                    throw r0;
                L_0x008c:
                    r2 = move-exception;
                    r7 = r2;
                    r2 = r0;
                    r0 = r7;
                    goto L_0x0086;
                L_0x0091:
                    r0 = move-exception;
                    goto L_0x0086;
                L_0x0093:
                    r0 = move-exception;
                    r2 = r1;
                    goto L_0x007b;
                L_0x0096:
                    r2 = move-exception;
                    r7 = r2;
                    r2 = r0;
                    r0 = r7;
                    goto L_0x0076;
                L_0x009b:
                    r0 = r1;
                    goto L_0x0064;
                L_0x009d:
                    r2 = r1;
                    goto L_0x0065;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.41.run():void");
                }
            });
        }
    }

    public void getCachedPhoneBook(final boolean z) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor b;
                Contact contact;
                String e;
                Object e2;
                try {
                    SQLiteCursor b2 = MessagesStorage.this.database.m12165b("SELECT name FROM sqlite_master WHERE type='table' AND name='user_contacts_v6'", new Object[0]);
                    boolean a = b2.m12152a();
                    b2.m12155b();
                    if (a) {
                        HashMap hashMap = new HashMap();
                        b = MessagesStorage.this.database.m12165b("SELECT us.uid, us.fname, us.sname, up.phone, up.sphone, up.deleted, us.imported FROM user_contacts_v6 as us LEFT JOIN user_phones_v6 as up ON us.uid = up.uid WHERE 1", new Object[0]);
                        while (b.m12152a()) {
                            Contact contact2;
                            int b3 = b.m12154b(0);
                            contact = (Contact) hashMap.get(Integer.valueOf(b3));
                            if (contact == null) {
                                contact = new Contact();
                                contact.first_name = b.m12159e(1);
                                contact.last_name = b.m12159e(2);
                                contact.imported = b.m12154b(6);
                                if (contact.first_name == null) {
                                    contact.first_name = TtmlNode.ANONYMOUS_REGION_ID;
                                }
                                if (contact.last_name == null) {
                                    contact.last_name = TtmlNode.ANONYMOUS_REGION_ID;
                                }
                                contact.contact_id = b3;
                                hashMap.put(Integer.valueOf(b3), contact);
                                contact2 = contact;
                            } else {
                                contact2 = contact;
                            }
                            e = b.m12159e(3);
                            if (e != null) {
                                contact2.phones.add(e);
                                e2 = b.m12159e(4);
                                if (e2 != null) {
                                    if (e2.length() == 8 && e.length() != 8) {
                                        e2 = C2488b.m12190b(e);
                                    }
                                    contact2.shortPhones.add(e2);
                                    contact2.phoneDeleted.add(Integer.valueOf(b.m12154b(5)));
                                    contact2.phoneTypes.add(TtmlNode.ANONYMOUS_REGION_ID);
                                }
                            }
                        }
                        b.m12155b();
                        ContactsController.getInstance().migratePhoneBookToV7(hashMap);
                        return;
                    }
                } catch (Throwable e3) {
                    FileLog.m13728e(e3);
                }
                HashMap hashMap2 = new HashMap();
                try {
                    b = MessagesStorage.this.database.m12165b("SELECT us.key, us.uid, us.fname, us.sname, up.phone, up.sphone, up.deleted, us.imported FROM user_contacts_v7 as us LEFT JOIN user_phones_v7 as up ON us.key = up.key WHERE 1", new Object[0]);
                    while (b.m12152a()) {
                        Contact contact3;
                        String e4 = b.m12159e(0);
                        contact = (Contact) hashMap2.get(e4);
                        if (contact == null) {
                            contact = new Contact();
                            contact.contact_id = b.m12154b(1);
                            contact.first_name = b.m12159e(2);
                            contact.last_name = b.m12159e(3);
                            contact.imported = b.m12154b(7);
                            if (contact.first_name == null) {
                                contact.first_name = TtmlNode.ANONYMOUS_REGION_ID;
                            }
                            if (contact.last_name == null) {
                                contact.last_name = TtmlNode.ANONYMOUS_REGION_ID;
                            }
                            hashMap2.put(e4, contact);
                            contact3 = contact;
                        } else {
                            contact3 = contact;
                        }
                        e = b.m12159e(4);
                        if (e != null) {
                            contact3.phones.add(e);
                            e2 = b.m12159e(5);
                            if (e2 != null) {
                                if (e2.length() == 8 && e.length() != 8) {
                                    e2 = C2488b.m12190b(e);
                                }
                                contact3.shortPhones.add(e2);
                                contact3.phoneDeleted.add(Integer.valueOf(b.m12154b(6)));
                                contact3.phoneTypes.add(TtmlNode.ANONYMOUS_REGION_ID);
                            }
                        }
                    }
                    b.m12155b();
                } catch (Throwable e32) {
                    hashMap2.clear();
                    FileLog.m13728e(e32);
                }
                ContactsController.getInstance().performSyncPhoneBook(hashMap2, true, true, false, false, !z, false);
            }
        });
    }

    public int getChannelPtsSync(final int i) {
        final Semaphore semaphore = new Semaphore(0);
        final Integer[] numArr = new Integer[]{Integer.valueOf(0)};
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor sQLiteCursor = null;
                try {
                    sQLiteCursor = MessagesStorage.this.database.m12165b("SELECT pts FROM dialogs WHERE did = " + (-i), new Object[0]);
                    if (sQLiteCursor.m12152a()) {
                        numArr[0] = Integer.valueOf(sQLiteCursor.m12154b(0));
                    }
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                } catch (Throwable th) {
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                }
                try {
                    if (semaphore != null) {
                        semaphore.release();
                    }
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return numArr[0].intValue();
    }

    public Chat getChat(int i) {
        try {
            ArrayList arrayList = new ArrayList();
            getChatsInternal(TtmlNode.ANONYMOUS_REGION_ID + i, arrayList);
            if (!arrayList.isEmpty()) {
                return (Chat) arrayList.get(0);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return null;
    }

    public Chat getChatSync(final int i) {
        final Semaphore semaphore = new Semaphore(0);
        final Chat[] chatArr = new Chat[1];
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                chatArr[0] = MessagesStorage.this.getChat(i);
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return chatArr[0];
    }

    public void getChatsInternal(String str, ArrayList<Chat> arrayList) {
        if (str != null && str.length() != 0 && arrayList != null) {
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data FROM chats WHERE uid IN(%s)", new Object[]{str}), new Object[0]);
            while (b.m12152a()) {
                try {
                    AbstractSerializedData g = b.m12161g(0);
                    if (g != null) {
                        Chat TLdeserialize = Chat.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        if (TLdeserialize != null) {
                            arrayList.add(TLdeserialize);
                        }
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
            b.m12155b();
        }
    }

    public void getContacts() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT * FROM contacts WHERE 1", new Object[0]);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (b.m12152a()) {
                        int b2 = b.m12154b(0);
                        TLRPC$TL_contact tLRPC$TL_contact = new TLRPC$TL_contact();
                        tLRPC$TL_contact.user_id = b2;
                        tLRPC$TL_contact.mutual = b.m12154b(1) == 1;
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append(",");
                        }
                        arrayList.add(tLRPC$TL_contact);
                        stringBuilder.append(tLRPC$TL_contact.user_id);
                    }
                    b.m12155b();
                    if (stringBuilder.length() != 0) {
                        MessagesStorage.this.getUsersInternal(stringBuilder.toString(), arrayList2);
                    }
                } catch (Throwable e) {
                    arrayList.clear();
                    arrayList2.clear();
                    FileLog.m13728e(e);
                }
                ContactsController.getInstance().processLoadedContacts(arrayList, arrayList2, 1);
            }
        });
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public void getDialogPhotos(int i, int i2, long j, int i3) {
        final long j2 = j;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b;
                    if (j2 != 0) {
                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d AND id < %d ORDER BY id DESC LIMIT %d", new Object[]{Integer.valueOf(i4), Long.valueOf(j2), Integer.valueOf(i5)}), new Object[0]);
                    } else {
                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d ORDER BY id DESC LIMIT %d", new Object[]{Integer.valueOf(i4), Integer.valueOf(i5)}), new Object[0]);
                    }
                    final TLRPC$photos_Photos tLRPC$TL_photos_photos = new TLRPC$TL_photos_photos();
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            Photo TLdeserialize = Photo.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            tLRPC$TL_photos_photos.photos.add(TLdeserialize);
                        }
                    }
                    b.m12155b();
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            MessagesController.getInstance().processLoadedUserPhotos(tLRPC$TL_photos_photos, i4, i5, j2, true, i6);
                        }
                    });
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public int getDialogReadMax(boolean z, long j) {
        final Semaphore semaphore = new Semaphore(0);
        final Integer[] numArr = new Integer[]{Integer.valueOf(0)};
        final boolean z2 = z;
        final long j2 = j;
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor sQLiteCursor = null;
                try {
                    sQLiteCursor = z2 ? MessagesStorage.this.database.m12165b("SELECT outbox_max FROM dialogs WHERE did = " + j2, new Object[0]) : MessagesStorage.this.database.m12165b("SELECT inbox_max FROM dialogs WHERE did = " + j2, new Object[0]);
                    if (sQLiteCursor.m12152a()) {
                        numArr[0] = Integer.valueOf(sQLiteCursor.m12154b(0));
                    }
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                } catch (Throwable th) {
                    if (sQLiteCursor != null) {
                        sQLiteCursor.m12155b();
                    }
                }
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return numArr[0].intValue();
    }

    public void getDialogs(final int i, final int i2) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                TLRPC$messages_Dialogs tLRPC$TL_messages_dialogs = new TLRPC$TL_messages_dialogs();
                ArrayList arrayList = new ArrayList();
                Iterable arrayList2 = new ArrayList();
                arrayList2.add(Integer.valueOf(UserConfig.getClientUserId()));
                Iterable arrayList3 = new ArrayList();
                Iterable arrayList4 = new ArrayList();
                Iterable arrayList5 = new ArrayList();
                HashMap hashMap = new HashMap();
                SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, s.flags, m.date, d.pts, d.inbox_max, d.outbox_max, m.replydata, d.pinned, d.unread_count_i FROM dialogs as d LEFT JOIN messages as m ON d.last_mid = m.mid LEFT JOIN dialog_settings as s ON d.did = s.did ORDER BY d.pinned DESC, d.date DESC LIMIT %d,%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}), new Object[0]);
                while (b.m12152a()) {
                    TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                    tLRPC$TL_dialog.id = b.m12158d(0);
                    tLRPC$TL_dialog.top_message = b.m12154b(1);
                    tLRPC$TL_dialog.unread_count = b.m12154b(2);
                    tLRPC$TL_dialog.last_message_date = b.m12154b(3);
                    tLRPC$TL_dialog.pts = b.m12154b(10);
                    int i = (tLRPC$TL_dialog.pts == 0 || ((int) tLRPC$TL_dialog.id) > 0) ? 0 : 1;
                    tLRPC$TL_dialog.flags = i;
                    tLRPC$TL_dialog.read_inbox_max_id = b.m12154b(11);
                    tLRPC$TL_dialog.read_outbox_max_id = b.m12154b(12);
                    tLRPC$TL_dialog.pinnedNum = b.m12154b(14);
                    tLRPC$TL_dialog.pinned = tLRPC$TL_dialog.pinnedNum != 0;
                    tLRPC$TL_dialog.unread_mentions_count = b.m12154b(15);
                    long d = b.m12158d(8);
                    i = (int) d;
                    tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                    if ((i & 1) != 0) {
                        tLRPC$TL_dialog.notify_settings.mute_until = (int) (d >> 32);
                        if (tLRPC$TL_dialog.notify_settings.mute_until == 0) {
                            tLRPC$TL_dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                        }
                    }
                    tLRPC$TL_messages_dialogs.dialogs.add(tLRPC$TL_dialog);
                    AbstractSerializedData g = b.m12161g(4);
                    if (g != null) {
                        Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        if (TLdeserialize != null) {
                            MessageObject.setUnreadFlags(TLdeserialize, b.m12154b(5));
                            TLdeserialize.id = b.m12154b(6);
                            i = b.m12154b(9);
                            if (i != 0) {
                                tLRPC$TL_dialog.last_message_date = i;
                            }
                            TLdeserialize.send_state = b.m12154b(7);
                            TLdeserialize.dialog_id = tLRPC$TL_dialog.id;
                            tLRPC$TL_messages_dialogs.messages.add(TLdeserialize);
                            MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList2, arrayList3);
                            try {
                                if (TLdeserialize.reply_to_msg_id != 0 && ((TLdeserialize.action instanceof TLRPC$TL_messageActionPinMessage) || (TLdeserialize.action instanceof TLRPC$TL_messageActionPaymentSent) || (TLdeserialize.action instanceof TLRPC$TL_messageActionGameScore))) {
                                    if (!b.m12153a(13)) {
                                        g = b.m12161g(13);
                                        if (g != null) {
                                            TLdeserialize.replyMessage = Message.TLdeserialize(g, g.readInt32(false), false);
                                            g.reuse();
                                            if (TLdeserialize.replyMessage != null) {
                                                if (MessageObject.isMegagroup(TLdeserialize)) {
                                                    Message message = TLdeserialize.replyMessage;
                                                    message.flags |= Integer.MIN_VALUE;
                                                }
                                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize.replyMessage, arrayList2, arrayList3);
                                            }
                                        }
                                    }
                                    if (TLdeserialize.replyMessage == null) {
                                        d = (long) TLdeserialize.reply_to_msg_id;
                                        if (TLdeserialize.to_id.channel_id != 0) {
                                            d |= ((long) TLdeserialize.to_id.channel_id) << 32;
                                        }
                                        if (!arrayList5.contains(Long.valueOf(d))) {
                                            arrayList5.add(Long.valueOf(d));
                                        }
                                        hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), TLdeserialize);
                                    }
                                }
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }
                    try {
                        i = (int) tLRPC$TL_dialog.id;
                        int i2 = (int) (tLRPC$TL_dialog.id >> 32);
                        if (i != 0) {
                            if (i2 == 1) {
                                if (!arrayList3.contains(Integer.valueOf(i))) {
                                    arrayList3.add(Integer.valueOf(i));
                                }
                            } else if (i > 0) {
                                if (!arrayList2.contains(Integer.valueOf(i))) {
                                    arrayList2.add(Integer.valueOf(i));
                                }
                            } else if (!arrayList3.contains(Integer.valueOf(-i))) {
                                arrayList3.add(Integer.valueOf(-i));
                            }
                        } else if (!arrayList4.contains(Integer.valueOf(i2))) {
                            arrayList4.add(Integer.valueOf(i2));
                        }
                    } catch (Throwable e2) {
                        tLRPC$TL_messages_dialogs.dialogs.clear();
                        tLRPC$TL_messages_dialogs.users.clear();
                        tLRPC$TL_messages_dialogs.chats.clear();
                        arrayList.clear();
                        FileLog.m13728e(e2);
                        MessagesController.getInstance().processLoadedDialogs(tLRPC$TL_messages_dialogs, arrayList, 0, 100, 1, true, false, true);
                        return;
                    }
                }
                b.m12155b();
                if (!arrayList5.isEmpty()) {
                    SQLiteCursor b2 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT data, mid, date, uid FROM messages WHERE mid IN(%s)", new Object[]{TextUtils.join(",", arrayList5)}), new Object[0]);
                    while (b2.m12152a()) {
                        g = b2.m12161g(0);
                        if (g != null) {
                            Message TLdeserialize2 = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            TLdeserialize2.id = b2.m12154b(1);
                            TLdeserialize2.date = b2.m12154b(2);
                            TLdeserialize2.dialog_id = b2.m12158d(3);
                            MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize2, arrayList2, arrayList3);
                            message = (Message) hashMap.get(Long.valueOf(TLdeserialize2.dialog_id));
                            if (message != null) {
                                message.replyMessage = TLdeserialize2;
                                TLdeserialize2.dialog_id = message.dialog_id;
                                if (MessageObject.isMegagroup(message)) {
                                    message = message.replyMessage;
                                    message.flags |= Integer.MIN_VALUE;
                                }
                            }
                        }
                    }
                    b2.m12155b();
                }
                if (!arrayList4.isEmpty()) {
                    MessagesStorage.this.getEncryptedChatsInternal(TextUtils.join(",", arrayList4), arrayList, arrayList2);
                }
                if (!arrayList3.isEmpty()) {
                    MessagesStorage.this.getChatsInternal(TextUtils.join(",", arrayList3), tLRPC$TL_messages_dialogs.chats);
                }
                if (!arrayList2.isEmpty()) {
                    MessagesStorage.this.getUsersInternal(TextUtils.join(",", arrayList2), tLRPC$TL_messages_dialogs.users);
                }
                MessagesController.getInstance().processLoadedDialogs(tLRPC$TL_messages_dialogs, arrayList, i, i2, 1, false, false, true);
            }
        });
    }

    public void getDownloadQueue(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    final ArrayList arrayList = new ArrayList();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT uid, type, data FROM download_queue WHERE type = %d ORDER BY date DESC LIMIT 3", new Object[]{Integer.valueOf(i)}), new Object[0]);
                    while (b.m12152a()) {
                        DownloadObject downloadObject = new DownloadObject();
                        downloadObject.type = b.m12154b(1);
                        downloadObject.id = b.m12158d(0);
                        AbstractSerializedData g = b.m12161g(2);
                        if (g != null) {
                            MessageMedia TLdeserialize = MessageMedia.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (TLdeserialize.document != null) {
                                downloadObject.object = TLdeserialize.document;
                            } else if (TLdeserialize.photo != null) {
                                downloadObject.object = FileLoader.getClosestPhotoSizeWithSize(TLdeserialize.photo.sizes, AndroidUtilities.getPhotoSize());
                            }
                            downloadObject.secret = TLdeserialize.ttl_seconds != 0;
                        }
                        arrayList.add(downloadObject);
                    }
                    b.m12155b();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MediaController.getInstance().processDownloadObjects(i, arrayList);
                        }
                    });
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public EncryptedChat getEncryptedChat(int i) {
        try {
            ArrayList arrayList = new ArrayList();
            getEncryptedChatsInternal(TtmlNode.ANONYMOUS_REGION_ID + i, arrayList, null);
            if (!arrayList.isEmpty()) {
                return (EncryptedChat) arrayList.get(0);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return null;
    }

    public void getEncryptedChat(final int i, final Semaphore semaphore, final ArrayList<TLObject> arrayList) {
        if (semaphore != null && arrayList != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r5 = this;
                    r0 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0062 }
                    r0.<init>();	 Catch:{ Exception -> 0x0062 }
                    r1 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0062 }
                    r1.<init>();	 Catch:{ Exception -> 0x0062 }
                    r2 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0062 }
                    r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0062 }
                    r3.<init>();	 Catch:{ Exception -> 0x0062 }
                    r4 = "";
                    r3 = r3.append(r4);	 Catch:{ Exception -> 0x0062 }
                    r4 = r3;	 Catch:{ Exception -> 0x0062 }
                    r3 = r3.append(r4);	 Catch:{ Exception -> 0x0062 }
                    r3 = r3.toString();	 Catch:{ Exception -> 0x0062 }
                    r2.getEncryptedChatsInternal(r3, r1, r0);	 Catch:{ Exception -> 0x0062 }
                    r2 = r1.isEmpty();	 Catch:{ Exception -> 0x0062 }
                    if (r2 != 0) goto L_0x005c;
                L_0x002b:
                    r2 = r0.isEmpty();	 Catch:{ Exception -> 0x0062 }
                    if (r2 != 0) goto L_0x005c;
                L_0x0031:
                    r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0062 }
                    r2.<init>();	 Catch:{ Exception -> 0x0062 }
                    r3 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0062 }
                    r4 = ",";
                    r0 = android.text.TextUtils.join(r4, r0);	 Catch:{ Exception -> 0x0062 }
                    r3.getUsersInternal(r0, r2);	 Catch:{ Exception -> 0x0062 }
                    r0 = r2.isEmpty();	 Catch:{ Exception -> 0x0062 }
                    if (r0 != 0) goto L_0x005c;
                L_0x0048:
                    r0 = r5;	 Catch:{ Exception -> 0x0062 }
                    r3 = 0;
                    r1 = r1.get(r3);	 Catch:{ Exception -> 0x0062 }
                    r0.add(r1);	 Catch:{ Exception -> 0x0062 }
                    r0 = r5;	 Catch:{ Exception -> 0x0062 }
                    r1 = 0;
                    r1 = r2.get(r1);	 Catch:{ Exception -> 0x0062 }
                    r0.add(r1);	 Catch:{ Exception -> 0x0062 }
                L_0x005c:
                    r0 = r4;
                    r0.release();
                L_0x0061:
                    return;
                L_0x0062:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x006c }
                    r0 = r4;
                    r0.release();
                    goto L_0x0061;
                L_0x006c:
                    r0 = move-exception;
                    r1 = r4;
                    r1.release();
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.66.run():void");
                }
            });
        }
    }

    public void getEncryptedChatsInternal(String str, ArrayList<EncryptedChat> arrayList, ArrayList<Integer> arrayList2) {
        if (str != null && str.length() != 0 && arrayList != null) {
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data, user, g, authkey, ttl, layer, seq_in, seq_out, use_count, exchange_id, key_date, fprint, fauthkey, khash, in_seq_no, admin_id, mtproto_seq FROM enc_chats WHERE uid IN(%s)", new Object[]{str}), new Object[0]);
            while (b.m12152a()) {
                try {
                    AbstractSerializedData g = b.m12161g(0);
                    if (g != null) {
                        EncryptedChat TLdeserialize = EncryptedChat.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        if (TLdeserialize != null) {
                            TLdeserialize.user_id = b.m12154b(1);
                            if (!(arrayList2 == null || arrayList2.contains(Integer.valueOf(TLdeserialize.user_id)))) {
                                arrayList2.add(Integer.valueOf(TLdeserialize.user_id));
                            }
                            TLdeserialize.a_or_b = b.m12160f(2);
                            TLdeserialize.auth_key = b.m12160f(3);
                            TLdeserialize.ttl = b.m12154b(4);
                            TLdeserialize.layer = b.m12154b(5);
                            TLdeserialize.seq_in = b.m12154b(6);
                            TLdeserialize.seq_out = b.m12154b(7);
                            int b2 = b.m12154b(8);
                            TLdeserialize.key_use_count_in = (short) (b2 >> 16);
                            TLdeserialize.key_use_count_out = (short) b2;
                            TLdeserialize.exchange_id = b.m12158d(9);
                            TLdeserialize.key_create_date = b.m12154b(10);
                            TLdeserialize.future_key_fingerprint = b.m12158d(11);
                            TLdeserialize.future_auth_key = b.m12160f(12);
                            TLdeserialize.key_hash = b.m12160f(13);
                            TLdeserialize.in_seq_no = b.m12154b(14);
                            b2 = b.m12154b(15);
                            if (b2 != 0) {
                                TLdeserialize.admin_id = b2;
                            }
                            TLdeserialize.mtproto_seq = b.m12154b(16);
                            arrayList.add(TLdeserialize);
                        }
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
            b.m12155b();
        }
    }

    public int getMessageCountDate(long j, long j2) {
        int[] iArr = new int[]{0};
        try {
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*)  FROM messages WHERE date BETWEEN %d AND %d ", new Object[]{Long.valueOf(j), Long.valueOf(j2)}), new Object[0]);
            if (b.m12152a()) {
                iArr[0] = b.m12154b(0);
            }
            b.m12155b();
        } catch (C2486a e) {
            e.printStackTrace();
        }
        Log.d("LEE", "MessageCount:" + iArr[0]);
        return iArr[0];
    }

    public void getMessages(long j, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7) {
        final int i8 = i;
        final int i9 = i2;
        final boolean z2 = z;
        final long j2 = j;
        final int i10 = i6;
        final int i11 = i4;
        final int i12 = i3;
        final int i13 = i5;
        final int i14 = i7;
        this.storageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesStorage$57$1 */
            class C32821 implements Comparator<Message> {
                C32821() {
                }

                public int compare(Message message, Message message2) {
                    if (message.id <= 0 || message2.id <= 0) {
                        if (message.id >= 0 || message2.id >= 0) {
                            if (message.date > message2.date) {
                                return -1;
                            }
                            if (message.date < message2.date) {
                                return 1;
                            }
                        } else if (message.id < message2.id) {
                            return -1;
                        } else {
                            if (message.id > message2.id) {
                                return 1;
                            }
                        }
                    } else if (message.id > message2.id) {
                        return -1;
                    } else {
                        if (message.id < message2.id) {
                            return 1;
                        }
                    }
                    return 0;
                }
            }

            public void run() {
                int i;
                Throwable e;
                Throwable th;
                TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                int i2 = 0;
                int i3 = 0;
                int i4 = i8;
                int i5 = 0;
                int i6 = 0;
                int i7 = 0;
                boolean z = false;
                int i8 = 0;
                long j = (long) i9;
                int i9 = i9;
                Object obj = null;
                int i10 = i9;
                int i11 = z2 ? -((int) j2) : 0;
                if (!(j == 0 || i11 == 0)) {
                    j |= ((long) i11) << 32;
                }
                boolean z2 = false;
                int i12 = j2 == 777000 ? 10 : 1;
                try {
                    SQLiteCursor b;
                    int i13;
                    int i14;
                    SQLiteCursor sQLiteCursor;
                    AbstractSerializedData g;
                    ArrayList arrayList;
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    HashMap hashMap = new HashMap();
                    HashMap hashMap2 = new HashMap();
                    int i15 = (int) j2;
                    SQLiteCursor b2;
                    SQLiteCursor b3;
                    Object obj2;
                    if (i15 != 0) {
                        long j2;
                        SQLiteCursor b4;
                        if (i10 == 3 && i11 == 0) {
                            b = MessagesStorage.this.database.m12165b("SELECT inbox_max, unread_count, date, unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                            if (b.m12152a()) {
                                i6 = b.m12154b(0) + 1;
                                i2 = b.m12154b(1);
                                i8 = b.m12154b(2);
                                i3 = b.m12154b(3);
                            }
                            b.m12155b();
                            j2 = j;
                            i = i4;
                            i13 = i9;
                        } else {
                            if (!(i10 == 1 || i10 == 3 || i10 == 4 || i11 != 0)) {
                                if (i10 == 2) {
                                    long j3;
                                    SQLiteCursor b5 = MessagesStorage.this.database.m12165b("SELECT inbox_max, unread_count, date, unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                                    if (b5.m12152a()) {
                                        i6 = b5.m12154b(0);
                                        j3 = (long) i6;
                                        i2 = b5.m12154b(1);
                                        i8 = b5.m12154b(2);
                                        i3 = b5.m12154b(3);
                                        z = true;
                                        if (j3 == 0 || i11 == 0) {
                                            i13 = i6;
                                            i14 = i6;
                                        } else {
                                            j3 |= ((long) i11) << 32;
                                            i13 = i6;
                                            i14 = i6;
                                        }
                                    } else {
                                        j3 = j;
                                        i13 = i9;
                                        i14 = 0;
                                    }
                                    try {
                                        b5.m12155b();
                                        if (!z) {
                                            b5 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid), max(date) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid > 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                            if (b5.m12152a()) {
                                                i6 = b5.m12154b(0);
                                                i8 = b5.m12154b(1);
                                            } else {
                                                i6 = i14;
                                            }
                                            b5.m12155b();
                                            int i16;
                                            if (i6 != 0) {
                                                b4 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid >= %d AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j2), Integer.valueOf(i6)}), new Object[0]);
                                                if (b4.m12152a()) {
                                                    i2 = b4.m12154b(0);
                                                }
                                                b4.m12155b();
                                                i16 = i13;
                                                j = j3;
                                                i9 = i16;
                                            } else {
                                                i16 = i13;
                                                j = j3;
                                                i9 = i16;
                                            }
                                        } else if (i13 == 0) {
                                            i6 = 0;
                                            b5 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid > 0 AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                            if (b5.m12152a()) {
                                                i6 = b5.m12154b(0);
                                            }
                                            b5.m12155b();
                                            if (i6 == i2) {
                                                b2 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid > 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                                if (b2.m12152a()) {
                                                    i13 = b2.m12154b(0);
                                                    j3 = (long) i13;
                                                    if (j3 == 0 || i11 == 0) {
                                                        i14 = i13;
                                                    } else {
                                                        j3 |= ((long) i11) << 32;
                                                        i14 = i13;
                                                    }
                                                }
                                                b2.m12155b();
                                            }
                                            i6 = i14;
                                            i9 = i13;
                                            j = j3;
                                        } else {
                                            b5 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start, end FROM messages_holes WHERE uid = %d AND start < %d AND end > %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i13), Integer.valueOf(i13)}), new Object[0]);
                                            Object obj3 = !b5.m12152a() ? 1 : null;
                                            b5.m12155b();
                                            if (obj3 != null) {
                                                b2 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid > %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i13)}), new Object[0]);
                                                if (b2.m12152a()) {
                                                    i13 = b2.m12154b(0);
                                                    j3 = (long) i13;
                                                    if (!(j3 == 0 || i11 == 0)) {
                                                        j3 |= ((long) i11) << 32;
                                                    }
                                                }
                                                b2.m12155b();
                                            }
                                            i6 = i14;
                                            i9 = i13;
                                            j = j3;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        i9 = i10;
                                        i6 = i14;
                                        i = i4;
                                        try {
                                            tLRPC$TL_messages_messages.messages.clear();
                                            tLRPC$TL_messages_messages.chats.clear();
                                            tLRPC$TL_messages_messages.users.clear();
                                            FileLog.m13728e(e);
                                            MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                                        } catch (Throwable e3) {
                                            th = e3;
                                            MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                                            throw th;
                                        }
                                    } catch (Throwable e32) {
                                        th = e32;
                                        i9 = i10;
                                        i6 = i14;
                                        i = i4;
                                        MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                                        throw th;
                                    }
                                }
                                if (i4 > i2 || i2 < i12) {
                                    i4 = Math.max(i4, i2 + 10);
                                    if (i2 < i12) {
                                        i2 = 0;
                                        i6 = 0;
                                        i7 = 0;
                                        z = false;
                                        j2 = 0;
                                        i = i4;
                                        i13 = i9;
                                    }
                                } else {
                                    i5 = i2 - i4;
                                    j2 = j;
                                    i = i4 + 10;
                                    i13 = i9;
                                }
                            }
                            j2 = j;
                            i = i4;
                            i13 = i9;
                        }
                        try {
                            b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start IN (0, 1)", new Object[]{Long.valueOf(j2)}), new Object[0]);
                            if (b.m12152a()) {
                                z2 = b.m12154b(0) == 1;
                                b.m12155b();
                            } else {
                                b.m12155b();
                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid > 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                if (b.m12152a()) {
                                    i14 = b.m12154b(0);
                                    if (i14 != 0) {
                                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                                        a.m12180d();
                                        a.m12175a(1, j2);
                                        a.m12174a(2, 0);
                                        a.m12174a(3, i14);
                                        a.m12178b();
                                        a.m12181e();
                                    }
                                }
                                b.m12155b();
                            }
                            long j4;
                            if (i10 == 3 || i10 == 4 || (r21 && i10 == 2)) {
                                long j5;
                                Object obj4;
                                Object obj5;
                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT max(mid) FROM messages WHERE uid = %d AND mid > 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                if (b.m12152a()) {
                                    i7 = b.m12154b(0);
                                }
                                b.m12155b();
                                if (i10 == 4 && i12 != 0) {
                                    b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT max(mid) FROM messages WHERE uid = %d AND date <= %d AND mid > 0", new Object[]{Long.valueOf(j2), Integer.valueOf(i12)}), new Object[0]);
                                    i9 = b.m12152a() ? b.m12154b(0) : -1;
                                    b.m12155b();
                                    b4 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND date >= %d AND mid > 0", new Object[]{Long.valueOf(j2), Integer.valueOf(i12)}), new Object[0]);
                                    i12 = b4.m12152a() ? b4.m12154b(0) : -1;
                                    b4.m12155b();
                                    if (!(i9 == -1 || i12 == -1)) {
                                        if (i9 == i12) {
                                            i14 = i9;
                                            j5 = j2;
                                            i9 = i10;
                                        } else {
                                            b4 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start <= %d AND end > %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i9), Integer.valueOf(i9)}), new Object[0]);
                                            if (b4.m12152a()) {
                                                i9 = -1;
                                            }
                                            b4.m12155b();
                                            if (i9 != -1) {
                                                b4 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start <= %d AND end > %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i12), Integer.valueOf(i12)}), new Object[0]);
                                                i9 = b4.m12152a() ? -1 : i12;
                                                b4.m12155b();
                                                if (i9 != -1) {
                                                    j = (long) i9;
                                                    if (j == 0 || i11 == 0) {
                                                        j5 = j;
                                                        i14 = i9;
                                                    } else {
                                                        j5 = j | (((long) i11) << 32);
                                                        i14 = i9;
                                                    }
                                                }
                                            }
                                        }
                                        obj4 = i14 == 0 ? 1 : null;
                                        if (obj4 != null) {
                                            b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start < %d AND end > %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i14), Integer.valueOf(i14)}), new Object[0]);
                                            if (b3.m12152a()) {
                                                obj4 = null;
                                            }
                                            b3.m12155b();
                                        }
                                        if (obj4 != null) {
                                            j4 = 0;
                                            j2 = 1;
                                            b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start >= %d ORDER BY start ASC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i14)}), new Object[0]);
                                            if (b.m12152a()) {
                                                j4 = (long) b.m12154b(0);
                                                if (i11 != 0) {
                                                    j4 |= ((long) i11) << 32;
                                                }
                                            }
                                            b.m12155b();
                                            b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT end FROM messages_holes WHERE uid = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i14)}), new Object[0]);
                                            if (b.m12152a()) {
                                                j2 = (long) b.m12154b(0);
                                                if (i11 != 0) {
                                                    j2 |= ((long) i11) << 32;
                                                }
                                            }
                                            b.m12155b();
                                            if (j4 == 0 || j2 != 1) {
                                                if (j4 == 0) {
                                                    j4 = C3446C.NANOS_PER_SECOND;
                                                    if (i11 != 0) {
                                                        j4 = C3446C.NANOS_PER_SECOND | (((long) i11) << 32);
                                                    }
                                                }
                                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d AND m.mid >= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d AND m.mid <= %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j2), Long.valueOf(j5), Long.valueOf(j2), Integer.valueOf(i / 2), Long.valueOf(j2), Long.valueOf(j5), Long.valueOf(j4), Integer.valueOf(i / 2)}), new Object[0]);
                                            } else {
                                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j2), Long.valueOf(j5), Integer.valueOf(i / 2), Long.valueOf(j2), Long.valueOf(j5), Integer.valueOf(i / 2)}), new Object[0]);
                                            }
                                        } else if (i10 != 2) {
                                            i12 = 0;
                                            b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid != 0 AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                            if (b3.m12152a()) {
                                                i12 = b3.m12154b(0);
                                            }
                                            b3.m12155b();
                                            if (i12 != i2) {
                                                obj5 = 1;
                                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j2), Long.valueOf(j5), Integer.valueOf(i / 2), Long.valueOf(j2), Long.valueOf(j5), Integer.valueOf(i / 2)}), new Object[0]);
                                            } else {
                                                b = null;
                                                obj5 = null;
                                            }
                                            obj = obj5;
                                        } else {
                                            b = null;
                                        }
                                        obj2 = obj;
                                        i4 = i14;
                                        sQLiteCursor = b;
                                    }
                                }
                                i9 = i10;
                                i14 = i13;
                                j5 = j2;
                                if (i14 == 0) {
                                }
                                if (obj4 != null) {
                                    b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start < %d AND end > %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i14), Integer.valueOf(i14)}), new Object[0]);
                                    if (b3.m12152a()) {
                                        obj4 = null;
                                    }
                                    b3.m12155b();
                                }
                                if (obj4 != null) {
                                    j4 = 0;
                                    j2 = 1;
                                    b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start >= %d ORDER BY start ASC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i14)}), new Object[0]);
                                    if (b.m12152a()) {
                                        j4 = (long) b.m12154b(0);
                                        if (i11 != 0) {
                                            j4 |= ((long) i11) << 32;
                                        }
                                    }
                                    b.m12155b();
                                    b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT end FROM messages_holes WHERE uid = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i14)}), new Object[0]);
                                    if (b.m12152a()) {
                                        j2 = (long) b.m12154b(0);
                                        if (i11 != 0) {
                                            j2 |= ((long) i11) << 32;
                                        }
                                    }
                                    b.m12155b();
                                    if (j4 == 0) {
                                    }
                                    if (j4 == 0) {
                                        j4 = C3446C.NANOS_PER_SECOND;
                                        if (i11 != 0) {
                                            j4 = C3446C.NANOS_PER_SECOND | (((long) i11) << 32);
                                        }
                                    }
                                    b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d AND m.mid >= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d AND m.mid <= %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j2), Long.valueOf(j5), Long.valueOf(j2), Integer.valueOf(i / 2), Long.valueOf(j2), Long.valueOf(j5), Long.valueOf(j4), Integer.valueOf(i / 2)}), new Object[0]);
                                } else if (i10 != 2) {
                                    b = null;
                                } else {
                                    i12 = 0;
                                    b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid != 0 AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                    if (b3.m12152a()) {
                                        i12 = b3.m12154b(0);
                                    }
                                    b3.m12155b();
                                    if (i12 != i2) {
                                        b = null;
                                        obj5 = null;
                                    } else {
                                        obj5 = 1;
                                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j2), Long.valueOf(j5), Integer.valueOf(i / 2), Long.valueOf(j2), Long.valueOf(j5), Integer.valueOf(i / 2)}), new Object[0]);
                                    }
                                    obj = obj5;
                                }
                                obj2 = obj;
                                i4 = i14;
                                sQLiteCursor = b;
                            } else if (i10 == 1) {
                                j4 = 0;
                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT start, end FROM messages_holes WHERE uid = %d AND start >= %d AND start != 1 AND end != 1 ORDER BY start ASC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i9)}), new Object[0]);
                                if (b.m12152a()) {
                                    j4 = (long) b.m12154b(0);
                                    if (i11 != 0) {
                                        j4 |= ((long) i11) << 32;
                                    }
                                }
                                b.m12155b();
                                i9 = i10;
                                i4 = i13;
                                obj2 = null;
                                sQLiteCursor = j4 != 0 ? MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date >= %d AND m.mid > %d AND m.mid <= %d ORDER BY m.date ASC, m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i11), Long.valueOf(j2), Long.valueOf(j4), Integer.valueOf(i)}), new Object[0]) : MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date >= %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i11), Long.valueOf(j2), Integer.valueOf(i)}), new Object[0]);
                            } else if (i11 == 0) {
                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT max(mid) FROM messages WHERE uid = %d AND mid > 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                if (b.m12152a()) {
                                    i7 = b.m12154b(0);
                                }
                                b.m12155b();
                                j4 = 0;
                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT max(end) FROM messages_holes WHERE uid = %d", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                if (b.m12152a()) {
                                    j4 = (long) b.m12154b(0);
                                    if (i11 != 0) {
                                        j4 |= ((long) i11) << 32;
                                    }
                                }
                                b.m12155b();
                                i9 = i10;
                                i4 = i13;
                                obj2 = null;
                                sQLiteCursor = j4 != 0 ? MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND (m.mid >= %d OR m.mid < 0) ORDER BY m.date DESC, m.mid DESC LIMIT %d,%d", new Object[]{Long.valueOf(j2), Long.valueOf(j4), Integer.valueOf(i5), Integer.valueOf(i)}), new Object[0]) : MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d ORDER BY m.date DESC, m.mid DESC LIMIT %d,%d", new Object[]{Long.valueOf(j2), Integer.valueOf(i5), Integer.valueOf(i)}), new Object[0]);
                            } else if (j2 != 0) {
                                j4 = 0;
                                b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT end FROM messages_holes WHERE uid = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i9)}), new Object[0]);
                                if (b.m12152a()) {
                                    j4 = (long) b.m12154b(0);
                                    if (i11 != 0) {
                                        j4 |= ((long) i11) << 32;
                                    }
                                }
                                b.m12155b();
                                i9 = i10;
                                i4 = i13;
                                obj2 = null;
                                sQLiteCursor = j4 != 0 ? MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d AND m.mid < %d AND (m.mid >= %d OR m.mid < 0) ORDER BY m.date DESC, m.mid DESC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i11), Long.valueOf(j2), Long.valueOf(j4), Integer.valueOf(i)}), new Object[0]) : MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d AND m.mid < %d ORDER BY m.date DESC, m.mid DESC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i11), Long.valueOf(j2), Integer.valueOf(i)}), new Object[0]);
                            } else {
                                i9 = i10;
                                i4 = i13;
                                obj2 = null;
                                sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d,%d", new Object[]{Long.valueOf(j2), Integer.valueOf(i11), Integer.valueOf(i5), Integer.valueOf(i)}), new Object[0]);
                            }
                        } catch (Exception e4) {
                            e32 = e4;
                            i9 = i10;
                            tLRPC$TL_messages_messages.messages.clear();
                            tLRPC$TL_messages_messages.chats.clear();
                            tLRPC$TL_messages_messages.users.clear();
                            FileLog.m13728e(e32);
                            MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                        } catch (Throwable e322) {
                            th = e322;
                            i9 = i10;
                            MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                            throw th;
                        }
                    }
                    z2 = true;
                    if (i10 == 3 && i11 == 0) {
                        SQLiteCursor b6 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid < 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                        if (b6.m12152a()) {
                            i6 = b6.m12154b(0);
                        }
                        b6.m12155b();
                        i = 0;
                        SQLiteCursor b7 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT max(mid), max(date) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid < 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                        if (b7.m12152a()) {
                            i = b7.m12154b(0);
                            i8 = b7.m12154b(1);
                        }
                        b7.m12155b();
                        if (i != 0) {
                            try {
                                b2 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid <= %d AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j2), Integer.valueOf(i)}), new Object[0]);
                                if (b2.m12152a()) {
                                    i2 = b2.m12154b(0);
                                }
                                b2.m12155b();
                                i6 = i;
                            } catch (Exception e5) {
                                e322 = e5;
                                i9 = i10;
                                i6 = i;
                                i = i4;
                                tLRPC$TL_messages_messages.messages.clear();
                                tLRPC$TL_messages_messages.chats.clear();
                                tLRPC$TL_messages_messages.users.clear();
                                FileLog.m13728e(e322);
                                MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                            } catch (Throwable e3222) {
                                th = e3222;
                                i9 = i10;
                                i6 = i;
                                i = i4;
                                MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                                throw th;
                            }
                        }
                    }
                    if (i10 == 3 || i10 == 4) {
                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid < 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                        if (b.m12152a()) {
                            i7 = b.m12154b(0);
                        }
                        b.m12155b();
                        Long[] lArr = new Object[]{Long.valueOf(j2), Long.valueOf(j), Integer.valueOf(i4 / 2), Long.valueOf(j2), Long.valueOf(j), Integer.valueOf(i4 / 2)};
                        i = i4;
                        i4 = i9;
                        i9 = i10;
                        obj2 = null;
                        sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.mid ASC LIMIT %d)", lArr), new Object[0]);
                    } else if (i10 == 1) {
                        r8 = new Object[]{Long.valueOf(j2), Integer.valueOf(i9), Integer.valueOf(i4)};
                        i = i4;
                        i4 = i9;
                        i9 = i10;
                        obj2 = null;
                        sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid < %d ORDER BY m.mid DESC LIMIT %d", r8), new Object[0]);
                    } else if (i11 == 0) {
                        if (i10 == 2) {
                            b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid < 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                            if (b3.m12152a()) {
                                i7 = b3.m12154b(0);
                            }
                            b3.m12155b();
                            b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT max(mid), max(date) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid < 0", new Object[]{Long.valueOf(j2)}), new Object[0]);
                            if (b3.m12152a()) {
                                i6 = b3.m12154b(0);
                                i8 = b3.m12154b(1);
                            }
                            b3.m12155b();
                            if (i6 != 0) {
                                b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid <= %d AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j2), Integer.valueOf(i6)}), new Object[0]);
                                if (b3.m12152a()) {
                                    i2 = b3.m12154b(0);
                                }
                                b3.m12155b();
                            }
                        }
                        if (i4 > i2 || i2 < i12) {
                            i = Math.max(i4, i2 + 10);
                            if (i2 < i12) {
                                i2 = 0;
                                i6 = 0;
                                i7 = 0;
                                i12 = 0;
                            } else {
                                i12 = 0;
                            }
                        } else {
                            i12 = i2 - i4;
                            i = i4 + 10;
                        }
                        i4 = i9;
                        i9 = i10;
                        obj2 = null;
                        sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d ORDER BY m.mid ASC LIMIT %d,%d", new Object[]{Long.valueOf(j2), Integer.valueOf(i12), Integer.valueOf(i)}), new Object[0]);
                    } else if (i9 != 0) {
                        r8 = new Object[]{Long.valueOf(j2), Integer.valueOf(i9), Integer.valueOf(i4)};
                        i = i4;
                        i4 = i9;
                        i9 = i10;
                        obj2 = null;
                        sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.mid ASC LIMIT %d", r8), new Object[0]);
                    } else {
                        r8 = new Object[]{Long.valueOf(j2), Integer.valueOf(i11), Integer.valueOf(0), Integer.valueOf(i4)};
                        i = i4;
                        i4 = i9;
                        i9 = i10;
                        obj2 = null;
                        sQLiteCursor = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d ORDER BY m.mid ASC LIMIT %d,%d", r8), new Object[0]);
                    }
                    if (sQLiteCursor != null) {
                        while (sQLiteCursor.m12152a()) {
                            g = sQLiteCursor.m12161g(1);
                            if (g != null) {
                                Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                MessageObject.setUnreadFlags(TLdeserialize, sQLiteCursor.m12154b(0));
                                TLdeserialize.id = sQLiteCursor.m12154b(3);
                                TLdeserialize.date = sQLiteCursor.m12154b(4);
                                TLdeserialize.dialog_id = j2;
                                if ((TLdeserialize.flags & 1024) != 0) {
                                    TLdeserialize.views = sQLiteCursor.m12154b(7);
                                }
                                if (i15 != 0 && TLdeserialize.ttl == 0) {
                                    TLdeserialize.ttl = sQLiteCursor.m12154b(8);
                                }
                                if (sQLiteCursor.m12154b(9) != 0) {
                                    TLdeserialize.mentioned = true;
                                }
                                tLRPC$TL_messages_messages.messages.add(TLdeserialize);
                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList2, arrayList3);
                                if (!(TLdeserialize.reply_to_msg_id == 0 && TLdeserialize.reply_to_random_id == 0)) {
                                    if (!sQLiteCursor.m12153a(6)) {
                                        g = sQLiteCursor.m12161g(6);
                                        if (g != null) {
                                            TLdeserialize.replyMessage = Message.TLdeserialize(g, g.readInt32(false), false);
                                            g.reuse();
                                            if (TLdeserialize.replyMessage != null) {
                                                if (MessageObject.isMegagroup(TLdeserialize)) {
                                                    Message message = TLdeserialize.replyMessage;
                                                    message.flags |= Integer.MIN_VALUE;
                                                }
                                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize.replyMessage, arrayList2, arrayList3);
                                            }
                                        }
                                    }
                                    if (TLdeserialize.replyMessage == null) {
                                        if (TLdeserialize.reply_to_msg_id != 0) {
                                            j = (long) TLdeserialize.reply_to_msg_id;
                                            if (TLdeserialize.to_id.channel_id != 0) {
                                                j |= ((long) TLdeserialize.to_id.channel_id) << 32;
                                            }
                                            if (!arrayList4.contains(Long.valueOf(j))) {
                                                arrayList4.add(Long.valueOf(j));
                                            }
                                            arrayList = (ArrayList) hashMap.get(Integer.valueOf(TLdeserialize.reply_to_msg_id));
                                            if (arrayList == null) {
                                                arrayList = new ArrayList();
                                                hashMap.put(Integer.valueOf(TLdeserialize.reply_to_msg_id), arrayList);
                                            }
                                            arrayList.add(TLdeserialize);
                                        } else {
                                            if (!arrayList4.contains(Long.valueOf(TLdeserialize.reply_to_random_id))) {
                                                arrayList4.add(Long.valueOf(TLdeserialize.reply_to_random_id));
                                            }
                                            arrayList = (ArrayList) hashMap2.get(Long.valueOf(TLdeserialize.reply_to_random_id));
                                            if (arrayList == null) {
                                                arrayList = new ArrayList();
                                                hashMap2.put(Long.valueOf(TLdeserialize.reply_to_random_id), arrayList);
                                            }
                                            arrayList.add(TLdeserialize);
                                        }
                                    }
                                }
                                TLdeserialize.send_state = sQLiteCursor.m12154b(2);
                                if (TLdeserialize.id > 0 && TLdeserialize.send_state != 0) {
                                    TLdeserialize.send_state = 0;
                                }
                                if (i15 == 0 && !sQLiteCursor.m12153a(5)) {
                                    TLdeserialize.random_id = sQLiteCursor.m12158d(5);
                                }
                                if (MessageObject.isSecretPhotoOrVideo(TLdeserialize)) {
                                    try {
                                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT date FROM enc_tasks_v2 WHERE mid = %d", new Object[]{Integer.valueOf(TLdeserialize.id)}), new Object[0]);
                                        if (b.m12152a()) {
                                            TLdeserialize.destroyTime = b.m12154b(0);
                                        }
                                        b.m12155b();
                                    } catch (Throwable e32222) {
                                        try {
                                            FileLog.m13728e(e32222);
                                        } catch (Exception e6) {
                                            e32222 = e6;
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                        sQLiteCursor.m12155b();
                    }
                    Collections.sort(tLRPC$TL_messages_messages.messages, new C32821());
                    if (i15 != 0) {
                        if ((i10 == 3 || i10 == 4 || (i10 == 2 && z && r11 == null)) && !tLRPC$TL_messages_messages.messages.isEmpty()) {
                            i13 = ((Message) tLRPC$TL_messages_messages.messages.get(tLRPC$TL_messages_messages.messages.size() - 1)).id;
                            i12 = ((Message) tLRPC$TL_messages_messages.messages.get(0)).id;
                            if (i13 > i4 || i12 < i4) {
                                arrayList4.clear();
                                arrayList2.clear();
                                arrayList3.clear();
                                tLRPC$TL_messages_messages.messages.clear();
                            }
                        }
                        if ((i10 == 4 || i10 == 3) && tLRPC$TL_messages_messages.messages.size() == 1) {
                            tLRPC$TL_messages_messages.messages.clear();
                        }
                    }
                    if (!arrayList4.isEmpty()) {
                        sQLiteCursor = !hashMap.isEmpty() ? MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT data, mid, date FROM messages WHERE mid IN(%s)", new Object[]{TextUtils.join(",", arrayList4)}), new Object[0]) : MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT m.data, m.mid, m.date, r.random_id FROM randoms as r INNER JOIN messages as m ON r.mid = m.mid WHERE r.random_id IN(%s)", new Object[]{TextUtils.join(",", arrayList4)}), new Object[0]);
                        while (sQLiteCursor.m12152a()) {
                            g = sQLiteCursor.m12161g(0);
                            if (g != null) {
                                Message TLdeserialize2 = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                TLdeserialize2.id = sQLiteCursor.m12154b(1);
                                TLdeserialize2.date = sQLiteCursor.m12154b(2);
                                TLdeserialize2.dialog_id = j2;
                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize2, arrayList2, arrayList3);
                                Message message2;
                                if (hashMap.isEmpty()) {
                                    arrayList = (ArrayList) hashMap2.remove(Long.valueOf(sQLiteCursor.m12158d(3)));
                                    if (arrayList != null) {
                                        for (i14 = 0; i14 < arrayList.size(); i14++) {
                                            message2 = (Message) arrayList.get(i14);
                                            message2.replyMessage = TLdeserialize2;
                                            message2.reply_to_msg_id = TLdeserialize2.id;
                                            if (MessageObject.isMegagroup(message2)) {
                                                message2 = message2.replyMessage;
                                                message2.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                } else {
                                    arrayList = (ArrayList) hashMap.get(Integer.valueOf(TLdeserialize2.id));
                                    if (arrayList != null) {
                                        for (i14 = 0; i14 < arrayList.size(); i14++) {
                                            message2 = (Message) arrayList.get(i14);
                                            message2.replyMessage = TLdeserialize2;
                                            if (MessageObject.isMegagroup(message2)) {
                                                message2 = message2.replyMessage;
                                                message2.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        sQLiteCursor.m12155b();
                        if (!hashMap2.isEmpty()) {
                            for (Entry value : hashMap2.entrySet()) {
                                arrayList = (ArrayList) value.getValue();
                                for (i14 = 0; i14 < arrayList.size(); i14++) {
                                    ((Message) arrayList.get(i14)).reply_to_random_id = 0;
                                }
                            }
                        }
                    }
                    if (i3 != 0) {
                        b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT COUNT(mid) FROM messages WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", new Object[]{Long.valueOf(j2)}), new Object[0]);
                        if (!b.m12152a()) {
                            i3 *= -1;
                        } else if (i3 != b.m12154b(0)) {
                            i3 *= -1;
                        }
                        b.m12155b();
                    }
                    if (!arrayList2.isEmpty()) {
                        MessagesStorage.this.getUsersInternal(TextUtils.join(",", arrayList2), tLRPC$TL_messages_messages.users);
                    }
                    if (!arrayList3.isEmpty()) {
                        MessagesStorage.this.getChatsInternal(TextUtils.join(",", arrayList3), tLRPC$TL_messages_messages.chats);
                    }
                    MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                } catch (Exception e7) {
                    e32222 = e7;
                    i9 = i10;
                    i = i4;
                    tLRPC$TL_messages_messages.messages.clear();
                    tLRPC$TL_messages_messages.chats.clear();
                    tLRPC$TL_messages_messages.users.clear();
                    FileLog.m13728e(e32222);
                    MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                } catch (Throwable e322222) {
                    th = e322222;
                    i9 = i10;
                    i = i4;
                    MessagesController.getInstance().processLoadedMessages(tLRPC$TL_messages_messages, j2, i, i9, i12, true, i13, i6, i7, i2, i8, i10, z2, z2, i14, z, i3);
                    throw th;
                }
            }
        });
    }

    public int getMyMessageCountByDate(long j, long j2) {
        if (UserConfig.getClientUserId() != 0) {
            new int[1][0] = 0;
            try {
                SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data  FROM messages WHERE date BETWEEN %d AND %d ", new Object[]{Long.valueOf(j), Long.valueOf(j2)}), new Object[0]);
                while (b.m12152a()) {
                    AbstractSerializedData g = b.m12161g(0);
                    if (g != null) {
                        Message.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                    }
                }
                b.m12155b();
            } catch (C2486a e) {
                e.printStackTrace();
            }
            Log.d("LEE", "SendMessageCount:" + 0);
        }
        return 0;
    }

    public int getMyMessageCountInGroupByDate(long j, long j2) {
        int i = 0;
        if (UserConfig.getClientUserId() != 0) {
            new int[1][0] = 0;
            try {
                SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data , mid, date, uid  FROM messages WHERE date BETWEEN %d AND %d ", new Object[]{Long.valueOf(j), Long.valueOf(j2)}), new Object[0]);
                while (b.m12152a()) {
                    AbstractSerializedData g = b.m12161g(0);
                    if (g != null) {
                        Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        TLdeserialize.id = b.m12154b(1);
                        TLdeserialize.date = b.m12154b(2);
                        TLdeserialize.dialog_id = b.m12158d(3);
                        if (TLdeserialize.out) {
                            int i2 = (int) (TLdeserialize.dialog_id >> 32);
                            int i3 = (int) TLdeserialize.dialog_id;
                            TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                            tLRPC$TL_dialog.id = TLdeserialize.dialog_id;
                            if (!(((int) tLRPC$TL_dialog.id) == 0 || i2 == 1 || !(tLRPC$TL_dialog instanceof TLRPC$TL_dialog))) {
                                if (tLRPC$TL_dialog.id < 0) {
                                    Chat chat = getChat(-((int) tLRPC$TL_dialog.id));
                                    if (chat == null || !chat.megagroup || chat.creator) {
                                        if (chat == null) {
                                            if (chat.megagroup) {
                                                i++;
                                            } else if (!ChatObject.isChannel(chat)) {
                                                i++;
                                            }
                                        }
                                    } else if (chat == null) {
                                        if (chat.megagroup) {
                                            i++;
                                        } else if (!ChatObject.isChannel(chat)) {
                                            i++;
                                        }
                                    }
                                } else {
                                    User user = getUser((int) tLRPC$TL_dialog.id);
                                    if (user == null) {
                                        i++;
                                    } else if (user.bot) {
                                    }
                                }
                            }
                        }
                    }
                }
                b.m12155b();
            } catch (C2486a e) {
                e.printStackTrace();
            }
            Log.d("LEE", "SendMessageCountInGroup:" + i);
        }
        return i;
    }

    public int getMyMessageReceivedInGroupCountByDate(long j, long j2) {
        int i = 0;
        if (UserConfig.getClientUserId() != 0) {
            new int[1][0] = 0;
            try {
                SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data , mid, date, uid FROM messages WHERE date BETWEEN %d AND %d ", new Object[]{Long.valueOf(j), Long.valueOf(j2)}), new Object[0]);
                while (b.m12152a()) {
                    AbstractSerializedData g = b.m12161g(0);
                    if (g != null) {
                        Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        TLdeserialize.id = b.m12154b(1);
                        TLdeserialize.date = b.m12154b(2);
                        TLdeserialize.dialog_id = b.m12158d(3);
                        TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                        tLRPC$TL_dialog.id = TLdeserialize.dialog_id;
                        if (!TLdeserialize.out) {
                            int i2 = (int) (TLdeserialize.dialog_id >> 32);
                            int i3 = (int) TLdeserialize.dialog_id;
                            if (!(((int) tLRPC$TL_dialog.id) == 0 || i2 == 1 || !(tLRPC$TL_dialog instanceof TLRPC$TL_dialog))) {
                                if (tLRPC$TL_dialog.id < 0) {
                                    Chat chat = getChat(-((int) tLRPC$TL_dialog.id));
                                    if (chat == null || !chat.megagroup || chat.creator) {
                                        if (chat == null) {
                                            if (chat.megagroup) {
                                                i++;
                                            } else if (!ChatObject.isChannel(chat)) {
                                                i++;
                                            }
                                        }
                                    } else if (chat == null) {
                                        if (chat.megagroup) {
                                            i++;
                                        } else if (!ChatObject.isChannel(chat)) {
                                            i++;
                                        }
                                    }
                                } else {
                                    User user = getUser((int) tLRPC$TL_dialog.id);
                                    if (user == null) {
                                        i++;
                                    } else if (user.bot) {
                                    }
                                }
                            }
                        }
                    }
                }
                b.m12155b();
            } catch (C2486a e) {
                e.printStackTrace();
            }
            Log.d("LEE", "ReceivedMessageCountInGroup:" + i);
        }
        return i;
    }

    public void getNewTask(final ArrayList<Integer> arrayList, int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (arrayList != null) {
                        String join = TextUtils.join(",", arrayList);
                        MessagesStorage.this.database.m12164a(String.format(Locale.US, "DELETE FROM enc_tasks_v2 WHERE mid IN(%s)", new Object[]{join})).m12179c().m12181e();
                    }
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT mid, date FROM enc_tasks_v2 WHERE date = (SELECT min(date) FROM enc_tasks_v2)", new Object[0]);
                    ArrayList arrayList = null;
                    int i = 0;
                    int i2 = -1;
                    while (b.m12152a()) {
                        long d = b.m12158d(0);
                        if (i2 == -1) {
                            i2 = (int) (d >> 32);
                            if (i2 < 0) {
                                i2 = 0;
                            }
                        }
                        i = b.m12154b(1);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(Integer.valueOf((int) d));
                    }
                    b.m12155b();
                    MessagesController.getInstance().processLoadedDeleteTask(i, arrayList, i2);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public TLObject getSentFile(String str, int i) {
        if (str == null || str.endsWith("attheme")) {
            return null;
        }
        final Semaphore semaphore = new Semaphore(0);
        final ArrayList arrayList = new ArrayList();
        final String str2 = str;
        final int i2 = i;
        this.storageQueue.postRunnable(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r6 = this;
                r0 = r2;	 Catch:{ Exception -> 0x006b }
                r0 = org.telegram.messenger.Utilities.MD5(r0);	 Catch:{ Exception -> 0x006b }
                if (r0 == 0) goto L_0x0057;
            L_0x0008:
                r1 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x006b }
                r1 = r1.database;	 Catch:{ Exception -> 0x006b }
                r2 = java.util.Locale.US;	 Catch:{ Exception -> 0x006b }
                r3 = "SELECT data FROM sent_files_v2 WHERE uid = '%s' AND type = %d";
                r4 = 2;
                r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x006b }
                r5 = 0;
                r4[r5] = r0;	 Catch:{ Exception -> 0x006b }
                r0 = 1;
                r5 = r3;	 Catch:{ Exception -> 0x006b }
                r5 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x006b }
                r4[r0] = r5;	 Catch:{ Exception -> 0x006b }
                r0 = java.lang.String.format(r2, r3, r4);	 Catch:{ Exception -> 0x006b }
                r2 = 0;
                r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x006b }
                r1 = r1.m12165b(r0, r2);	 Catch:{ Exception -> 0x006b }
                r0 = r1.m12152a();	 Catch:{ Exception -> 0x006b }
                if (r0 == 0) goto L_0x0054;
            L_0x0033:
                r0 = 0;
                r2 = r1.m12161g(r0);	 Catch:{ Exception -> 0x006b }
                if (r2 == 0) goto L_0x0054;
            L_0x003a:
                r0 = 0;
                r0 = r2.readInt32(r0);	 Catch:{ Exception -> 0x006b }
                r3 = 0;
                r0 = org.telegram.tgnet.TLRPC.MessageMedia.TLdeserialize(r2, r0, r3);	 Catch:{ Exception -> 0x006b }
                r2.reuse();	 Catch:{ Exception -> 0x006b }
                r2 = r0 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument;	 Catch:{ Exception -> 0x006b }
                if (r2 == 0) goto L_0x005d;
            L_0x004b:
                r2 = r4;	 Catch:{ Exception -> 0x006b }
                r0 = (org.telegram.tgnet.TLRPC$TL_messageMediaDocument) r0;	 Catch:{ Exception -> 0x006b }
                r0 = r0.document;	 Catch:{ Exception -> 0x006b }
                r2.add(r0);	 Catch:{ Exception -> 0x006b }
            L_0x0054:
                r1.m12155b();	 Catch:{ Exception -> 0x006b }
            L_0x0057:
                r0 = r5;
                r0.release();
            L_0x005c:
                return;
            L_0x005d:
                r2 = r0 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;	 Catch:{ Exception -> 0x006b }
                if (r2 == 0) goto L_0x0054;
            L_0x0061:
                r2 = r4;	 Catch:{ Exception -> 0x006b }
                r0 = (org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) r0;	 Catch:{ Exception -> 0x006b }
                r0 = r0.photo;	 Catch:{ Exception -> 0x006b }
                r2.add(r0);	 Catch:{ Exception -> 0x006b }
                goto L_0x0054;
            L_0x006b:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x0075 }
                r0 = r5;
                r0.release();
                goto L_0x005c;
            L_0x0075:
                r0 = move-exception;
                r1 = r5;
                r1.release();
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.58.run():void");
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return !arrayList.isEmpty() ? (TLObject) arrayList.get(0) : null;
    }

    public DispatchQueue getStorageQueue() {
        return this.storageQueue;
    }

    public int getTotalMessageCount(long j) {
        int[] iArr = new int[]{0};
        try {
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT COUNT(*)  FROM messages WHERE uid = %d ", new Object[]{Long.valueOf(j)}), new Object[0]);
            if (b.m12152a()) {
                iArr[0] = b.m12154b(0);
            }
            b.m12155b();
        } catch (C2486a e) {
            e.printStackTrace();
        }
        Log.d("LEE", "AliMessageCount:" + iArr[0]);
        return iArr[0];
    }

    public void getUnreadMention(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT MIN(mid) FROM messages WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", new Object[]{Long.valueOf(j)}), new Object[0]);
                    if (b.m12152a()) {
                        i = b.m12154b(0);
                    }
                    b.m12155b();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            intCallback.run(i);
                        }
                    });
                } catch (Throwable e) {
                    FileLog.m13727e("tmessages", e);
                }
            }
        });
    }

    public void getUnsentMessages(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    HashMap hashMap = new HashMap();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    Iterable arrayList5 = new ArrayList();
                    ArrayList arrayList6 = new ArrayList();
                    ArrayList arrayList7 = new ArrayList();
                    Iterable arrayList8 = new ArrayList();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.uid, s.seq_in, s.seq_out, m.ttl FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid LEFT JOIN messages_seq as s ON m.mid = s.mid WHERE m.mid < 0 AND m.send_state = 1 ORDER BY m.mid DESC LIMIT " + i, new Object[0]);
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(1);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (hashMap.containsKey(Integer.valueOf(TLdeserialize.id))) {
                                continue;
                            } else {
                                MessageObject.setUnreadFlags(TLdeserialize, b.m12154b(0));
                                TLdeserialize.id = b.m12154b(3);
                                TLdeserialize.date = b.m12154b(4);
                                if (!b.m12153a(5)) {
                                    TLdeserialize.random_id = b.m12158d(5);
                                }
                                TLdeserialize.dialog_id = b.m12158d(6);
                                TLdeserialize.seq_in = b.m12154b(7);
                                TLdeserialize.seq_out = b.m12154b(8);
                                TLdeserialize.ttl = b.m12154b(9);
                                arrayList.add(TLdeserialize);
                                hashMap.put(Integer.valueOf(TLdeserialize.id), TLdeserialize);
                                int i = (int) TLdeserialize.dialog_id;
                                int i2 = (int) (TLdeserialize.dialog_id >> 32);
                                if (i != 0) {
                                    if (i2 == 1) {
                                        if (!arrayList7.contains(Integer.valueOf(i))) {
                                            arrayList7.add(Integer.valueOf(i));
                                        }
                                    } else if (i < 0) {
                                        if (!arrayList6.contains(Integer.valueOf(-i))) {
                                            arrayList6.add(Integer.valueOf(-i));
                                        }
                                    } else if (!arrayList5.contains(Integer.valueOf(i))) {
                                        arrayList5.add(Integer.valueOf(i));
                                    }
                                } else if (!arrayList8.contains(Integer.valueOf(i2))) {
                                    arrayList8.add(Integer.valueOf(i2));
                                }
                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList5, arrayList6);
                                TLdeserialize.send_state = b.m12154b(2);
                                if (!(TLdeserialize.to_id.channel_id != 0 || MessageObject.isUnread(TLdeserialize) || i == 0) || TLdeserialize.id > 0) {
                                    TLdeserialize.send_state = 0;
                                }
                                if (i == 0 && !b.m12153a(5)) {
                                    TLdeserialize.random_id = b.m12158d(5);
                                }
                            }
                        }
                    }
                    b.m12155b();
                    if (!arrayList8.isEmpty()) {
                        MessagesStorage.this.getEncryptedChatsInternal(TextUtils.join(",", arrayList8), arrayList4, arrayList5);
                    }
                    if (!arrayList5.isEmpty()) {
                        MessagesStorage.this.getUsersInternal(TextUtils.join(",", arrayList5), arrayList2);
                    }
                    if (!(arrayList6.isEmpty() && arrayList7.isEmpty())) {
                        int i3;
                        Integer num;
                        StringBuilder stringBuilder = new StringBuilder();
                        for (i3 = 0; i3 < arrayList6.size(); i3++) {
                            num = (Integer) arrayList6.get(i3);
                            if (stringBuilder.length() != 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(num);
                        }
                        for (i3 = 0; i3 < arrayList7.size(); i3++) {
                            num = (Integer) arrayList7.get(i3);
                            if (stringBuilder.length() != 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(-num.intValue());
                        }
                        MessagesStorage.this.getChatsInternal(stringBuilder.toString(), arrayList3);
                    }
                    SendMessagesHelper.getInstance().processUnsentMessages(arrayList, arrayList2, arrayList3, arrayList4);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public User getUser(int i) {
        try {
            ArrayList arrayList = new ArrayList();
            getUsersInternal(TtmlNode.ANONYMOUS_REGION_ID + i, arrayList);
            if (!arrayList.isEmpty()) {
                return (User) arrayList.get(0);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return null;
    }

    public ArrayList<UserState> getUserStateWithStartAndEndTime(long j, long j2) {
        int i;
        int i2;
        C2486a e;
        UserState userState;
        ArrayList<UserState> arrayList = new ArrayList();
        long j3 = (j2 - j) / 3600;
        if (j2 - (3600 * j3) > 0) {
            j3++;
        }
        for (int i3 = 0; ((long) i3) < j3; i3++) {
            if (UserConfig.getClientUserId() == 0) {
                return new ArrayList();
            }
            int i4;
            int i5;
            long j4 = ((long) (i3 * 3600)) + j;
            long j5 = j4 + 3600;
            try {
                SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data , mid, date, uid  FROM messages WHERE date BETWEEN %d AND %d ", new Object[]{Long.valueOf(j4), Long.valueOf(j5)}), new Object[0]);
                i4 = 0;
                i5 = 0;
                i = 0;
                i2 = 0;
                while (b.m12152a()) {
                    try {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (!(TLdeserialize.out || TLdeserialize.post)) {
                                i2++;
                            }
                            if (TLdeserialize.out && !TLdeserialize.post) {
                                i++;
                            }
                            TLdeserialize.id = b.m12154b(1);
                            TLdeserialize.dialog_id = b.m12158d(3);
                            TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                            tLRPC$TL_dialog.id = TLdeserialize.dialog_id;
                            int i6 = (int) (TLdeserialize.dialog_id >> 32);
                            if (!(((int) tLRPC$TL_dialog.id) == 0 || i6 == 1 || !(tLRPC$TL_dialog instanceof TLRPC$TL_dialog))) {
                                if (tLRPC$TL_dialog.id < 0) {
                                    Chat chat = getChat(-((int) tLRPC$TL_dialog.id));
                                    if (chat != null) {
                                        if (chat.megagroup) {
                                            if (TLdeserialize.out) {
                                                i4++;
                                            } else {
                                                i5++;
                                            }
                                        } else if (!ChatObject.isChannel(chat)) {
                                            if (TLdeserialize.out) {
                                                i4++;
                                            } else {
                                                i5++;
                                            }
                                        }
                                    }
                                } else {
                                    if (getUser((int) tLRPC$TL_dialog.id) == null) {
                                        if (TLdeserialize.out) {
                                            i4++;
                                        } else {
                                            i5++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (C2486a e2) {
                        e = e2;
                    }
                }
                b.m12155b();
            } catch (C2486a e3) {
                C2486a c2486a = e3;
                i4 = 0;
                i5 = 0;
                i = 0;
                i2 = 0;
                e = c2486a;
                e.printStackTrace();
                userState = new UserState();
                userState.setMs(i);
                userState.setMr(i2);
                userState.setGp(i4);
                userState.setGr(i5);
                userState.setS(j4);
                userState.setE(j5);
                arrayList.add(userState);
            }
            userState = new UserState();
            userState.setMs(i);
            userState.setMr(i2);
            userState.setGp(i4);
            userState.setGr(i5);
            userState.setS(j4);
            userState.setE(j5);
            arrayList.add(userState);
        }
        return arrayList;
    }

    public User getUserSync(final int i) {
        final Semaphore semaphore = new Semaphore(0);
        final User[] userArr = new User[1];
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                userArr[0] = MessagesStorage.this.getUser(i);
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return userArr[0];
    }

    public ArrayList<User> getUsers(ArrayList<Integer> arrayList) {
        ArrayList<User> arrayList2 = new ArrayList();
        try {
            getUsersInternal(TextUtils.join(",", arrayList), arrayList2);
        } catch (Throwable e) {
            arrayList2.clear();
            FileLog.m13728e(e);
        }
        return arrayList2;
    }

    public void getUsersInternal(String str, ArrayList<User> arrayList) {
        if (str != null && str.length() != 0 && arrayList != null) {
            SQLiteCursor b = this.database.m12165b(String.format(Locale.US, "SELECT data, status FROM users WHERE uid IN(%s)", new Object[]{str}), new Object[0]);
            while (b.m12152a()) {
                try {
                    AbstractSerializedData g = b.m12161g(0);
                    if (g != null) {
                        User TLdeserialize = User.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        if (TLdeserialize != null) {
                            if (TLdeserialize.status != null) {
                                TLdeserialize.status.expires = b.m12154b(1);
                            }
                            arrayList.add(TLdeserialize);
                        }
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
            b.m12155b();
        }
    }

    public void getWallpapers() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT data FROM wallpapers WHERE 1", new Object[0]);
                    final ArrayList arrayList = new ArrayList();
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            TLRPC$WallPaper TLdeserialize = TLRPC$WallPaper.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            arrayList.add(TLdeserialize);
                        }
                    }
                    b.m12155b();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.wallpapersDidLoaded, arrayList);
                        }
                    });
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public boolean hasAuthMessage(final int i) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid FROM messages WHERE uid = 777000 AND date = %d AND mid < 0 LIMIT 1", new Object[]{Integer.valueOf(i)}), new Object[0]);
                    zArr[0] = b.m12152a();
                    b.m12155b();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                } finally {
                    semaphore.release();
                }
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return zArr[0];
    }

    public boolean isDialogHasMessages(long j) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] zArr = new boolean[1];
        final long j2 = j;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid FROM messages WHERE uid = %d LIMIT 1", new Object[]{Long.valueOf(j2)}), new Object[0]);
                    zArr[0] = b.m12152a();
                    b.m12155b();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                } finally {
                    semaphore.release();
                }
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return zArr[0];
    }

    public boolean isMigratedChat(final int i) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0067 in list [B:23:0x0070]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                /*
                r5 = this;
                r0 = 0;
                r1 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r1 = r1.database;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2.<init>();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = "SELECT info FROM chat_settings_v2 WHERE uid = ";	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2 = r2.append(r3);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = r6;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2 = r2.append(r3);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2 = r2.toString();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2 = r1.m12165b(r2, r3);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r1 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3.<init>();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = r2.m12152a();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r3 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0030:
                r3 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = r2.m12161g(r3);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r3 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0037:
                r1 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r1 = r3.readInt32(r1);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r4 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r1 = org.telegram.tgnet.TLRPC.ChatFull.TLdeserialize(r3, r1, r4);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3.reuse();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0044:
                r2.m12155b();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r2 = r1;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r4 = r1 instanceof org.telegram.tgnet.TLRPC.TL_channelFull;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r4 == 0) goto L_0x0053;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x004e:
                r1 = r1.migrated_from_chat_id;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r1 == 0) goto L_0x0053;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0052:
                r0 = 1;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0053:
                r2[r3] = r0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r0 = r0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r0 == 0) goto L_0x005e;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0059:
                r0 = r0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r0.release();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x005e:
                r0 = r0;
                if (r0 == 0) goto L_0x0067;
            L_0x0062:
                r0 = r0;
                r0.release();
            L_0x0067:
                return;
            L_0x0068:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r0 = r0;
                if (r0 == 0) goto L_0x0067;
            L_0x0070:
                r0 = r0;
                r0.release();
                goto L_0x0067;
            L_0x0076:
                r0 = move-exception;
                r1 = r0;
                if (r1 == 0) goto L_0x0080;
            L_0x007b:
                r1 = r0;
                r1.release();
            L_0x0080:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.45.run():void");
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return zArr[0];
    }

    public void loadChannelAdmins(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT uid FROM channel_admins WHERE did = " + i, new Object[0]);
                    ArrayList arrayList = new ArrayList();
                    while (b.m12152a()) {
                        arrayList.add(Integer.valueOf(b.m12154b(0)));
                    }
                    b.m12155b();
                    MessagesController.getInstance().processLoadedChannelAdmins(arrayList, i, true);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void loadChatInfo(int i, Semaphore semaphore, boolean z, boolean z2) {
        final int i2 = i;
        final Semaphore semaphore2 = semaphore;
        final boolean z3 = z;
        final boolean z4 = z2;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                Throwable e;
                Throwable th;
                MessageObject messageObject = null;
                int i = 0;
                ArrayList arrayList = new ArrayList();
                ChatFull TLdeserialize;
                try {
                    AbstractSerializedData g;
                    StringBuilder stringBuilder;
                    ChatParticipant chatParticipant;
                    SQLiteCursor b;
                    User TLdeserialize2;
                    User user;
                    AbstractSerializedData g2;
                    ChannelParticipant TLdeserialize3;
                    TL_chatChannelParticipant tL_chatChannelParticipant;
                    BotInfo botInfo;
                    SQLiteCursor b2 = MessagesStorage.this.database.m12165b("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + i2, new Object[0]);
                    if (b2.m12152a()) {
                        g = b2.m12161g(0);
                        if (g != null) {
                            TLdeserialize = ChatFull.TLdeserialize(g, g.readInt32(false), false);
                            try {
                                g.reuse();
                                TLdeserialize.pinned_msg_id = b2.m12154b(1);
                                b2.m12155b();
                                if (TLdeserialize instanceof TL_chatFull) {
                                    stringBuilder = new StringBuilder();
                                    while (i < TLdeserialize.participants.participants.size()) {
                                        chatParticipant = (ChatParticipant) TLdeserialize.participants.participants.get(i);
                                        if (stringBuilder.length() != 0) {
                                            stringBuilder.append(",");
                                        }
                                        stringBuilder.append(chatParticipant.user_id);
                                        i++;
                                    }
                                    if (stringBuilder.length() != 0) {
                                        MessagesStorage.this.getUsersInternal(stringBuilder.toString(), arrayList);
                                    }
                                } else if (TLdeserialize instanceof TL_channelFull) {
                                    b = MessagesStorage.this.database.m12165b("SELECT us.data, us.status, cu.data, cu.date FROM channel_users_v2 as cu LEFT JOIN users as us ON us.uid = cu.uid WHERE cu.did = " + (-i2) + " ORDER BY cu.date DESC", new Object[0]);
                                    TLdeserialize.participants = new TL_chatParticipants();
                                    while (b.m12152a()) {
                                        try {
                                            g = b.m12161g(0);
                                            if (g == null) {
                                                TLdeserialize2 = User.TLdeserialize(g, g.readInt32(false), false);
                                                g.reuse();
                                                user = TLdeserialize2;
                                            } else {
                                                user = null;
                                            }
                                            g2 = b.m12161g(2);
                                            if (g2 == null) {
                                                TLdeserialize3 = ChannelParticipant.TLdeserialize(g2, g2.readInt32(false), false);
                                                g2.reuse();
                                            } else {
                                                TLdeserialize3 = null;
                                            }
                                            if (!(user == null || TLdeserialize3 == null)) {
                                                if (user.status != null) {
                                                    user.status.expires = b.m12154b(1);
                                                }
                                                arrayList.add(user);
                                                TLdeserialize3.date = b.m12154b(3);
                                                tL_chatChannelParticipant = new TL_chatChannelParticipant();
                                                tL_chatChannelParticipant.user_id = TLdeserialize3.user_id;
                                                tL_chatChannelParticipant.date = TLdeserialize3.date;
                                                tL_chatChannelParticipant.inviter_id = TLdeserialize3.inviter_id;
                                                tL_chatChannelParticipant.channelParticipant = TLdeserialize3;
                                                TLdeserialize.participants.participants.add(tL_chatChannelParticipant);
                                            }
                                        } catch (Throwable e2) {
                                            FileLog.m13728e(e2);
                                        }
                                    }
                                    b.m12155b();
                                    stringBuilder = new StringBuilder();
                                    while (i < TLdeserialize.bot_info.size()) {
                                        botInfo = (BotInfo) TLdeserialize.bot_info.get(i);
                                        if (stringBuilder.length() != 0) {
                                            stringBuilder.append(",");
                                        }
                                        stringBuilder.append(botInfo.user_id);
                                        i++;
                                    }
                                    if (stringBuilder.length() != 0) {
                                        MessagesStorage.this.getUsersInternal(stringBuilder.toString(), arrayList);
                                    }
                                }
                                if (semaphore2 != null) {
                                    semaphore2.release();
                                }
                                if ((TLdeserialize instanceof TL_channelFull) && TLdeserialize.pinned_msg_id != 0) {
                                    messageObject = MessagesQuery.loadPinnedMessage(i2, TLdeserialize.pinned_msg_id, false);
                                }
                                MessagesController.getInstance().processChatInfo(i2, TLdeserialize, arrayList, true, z3, z4, messageObject);
                                if (semaphore2 != null) {
                                    semaphore2.release();
                                }
                            } catch (Exception e3) {
                                e2 = e3;
                            }
                        }
                    }
                    TLdeserialize = null;
                    b2.m12155b();
                    if (TLdeserialize instanceof TL_chatFull) {
                        stringBuilder = new StringBuilder();
                        while (i < TLdeserialize.participants.participants.size()) {
                            chatParticipant = (ChatParticipant) TLdeserialize.participants.participants.get(i);
                            if (stringBuilder.length() != 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(chatParticipant.user_id);
                            i++;
                        }
                        if (stringBuilder.length() != 0) {
                            MessagesStorage.this.getUsersInternal(stringBuilder.toString(), arrayList);
                        }
                    } else if (TLdeserialize instanceof TL_channelFull) {
                        b = MessagesStorage.this.database.m12165b("SELECT us.data, us.status, cu.data, cu.date FROM channel_users_v2 as cu LEFT JOIN users as us ON us.uid = cu.uid WHERE cu.did = " + (-i2) + " ORDER BY cu.date DESC", new Object[0]);
                        TLdeserialize.participants = new TL_chatParticipants();
                        while (b.m12152a()) {
                            g = b.m12161g(0);
                            if (g == null) {
                                user = null;
                            } else {
                                TLdeserialize2 = User.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                user = TLdeserialize2;
                            }
                            g2 = b.m12161g(2);
                            if (g2 == null) {
                                TLdeserialize3 = null;
                            } else {
                                TLdeserialize3 = ChannelParticipant.TLdeserialize(g2, g2.readInt32(false), false);
                                g2.reuse();
                            }
                            if (user.status != null) {
                                user.status.expires = b.m12154b(1);
                            }
                            arrayList.add(user);
                            TLdeserialize3.date = b.m12154b(3);
                            tL_chatChannelParticipant = new TL_chatChannelParticipant();
                            tL_chatChannelParticipant.user_id = TLdeserialize3.user_id;
                            tL_chatChannelParticipant.date = TLdeserialize3.date;
                            tL_chatChannelParticipant.inviter_id = TLdeserialize3.inviter_id;
                            tL_chatChannelParticipant.channelParticipant = TLdeserialize3;
                            TLdeserialize.participants.participants.add(tL_chatChannelParticipant);
                        }
                        b.m12155b();
                        stringBuilder = new StringBuilder();
                        while (i < TLdeserialize.bot_info.size()) {
                            botInfo = (BotInfo) TLdeserialize.bot_info.get(i);
                            if (stringBuilder.length() != 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(botInfo.user_id);
                            i++;
                        }
                        if (stringBuilder.length() != 0) {
                            MessagesStorage.this.getUsersInternal(stringBuilder.toString(), arrayList);
                        }
                    }
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                    messageObject = MessagesQuery.loadPinnedMessage(i2, TLdeserialize.pinned_msg_id, false);
                    MessagesController.getInstance().processChatInfo(i2, TLdeserialize, arrayList, true, z3, z4, messageObject);
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    TLdeserialize = null;
                    try {
                        FileLog.m13728e(e2);
                        MessagesController.getInstance().processChatInfo(i2, TLdeserialize, arrayList, true, z3, z4, null);
                        if (semaphore2 != null) {
                            semaphore2.release();
                        }
                    } catch (Throwable e22) {
                        th = e22;
                        MessagesController.getInstance().processChatInfo(i2, TLdeserialize, arrayList, true, z3, z4, null);
                        if (semaphore2 != null) {
                            semaphore2.release();
                        }
                        throw th;
                    }
                } catch (Throwable e222) {
                    th = e222;
                    TLdeserialize = null;
                    MessagesController.getInstance().processChatInfo(i2, TLdeserialize, arrayList, true, z3, z4, null);
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                    throw th;
                }
            }
        });
    }

    public void loadUnreadMessages() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    int i;
                    Iterable arrayList = new ArrayList();
                    Iterable arrayList2 = new ArrayList();
                    Iterable arrayList3 = new ArrayList();
                    final HashMap hashMap = new HashMap();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT d.did, d.unread_count, s.flags FROM dialogs as d LEFT JOIN dialog_settings as s ON d.did = s.did WHERE d.unread_count != 0", new Object[0]);
                    StringBuilder stringBuilder = new StringBuilder();
                    int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                    while (b.m12152a()) {
                        long d = b.m12158d(2);
                        Object obj = (1 & d) != 0 ? 1 : null;
                        int i2 = (int) (d >> 32);
                        if (b.m12153a(2) || obj == null || (i2 != 0 && i2 < currentTime)) {
                            d = b.m12158d(0);
                            hashMap.put(Long.valueOf(d), Integer.valueOf(b.m12154b(1)));
                            if (stringBuilder.length() != 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(d);
                            i = (int) d;
                            i2 = (int) (d >> 32);
                            if (i != 0) {
                                if (i < 0) {
                                    if (!arrayList2.contains(Integer.valueOf(-i))) {
                                        arrayList2.add(Integer.valueOf(-i));
                                    }
                                } else if (!arrayList.contains(Integer.valueOf(i))) {
                                    arrayList.add(Integer.valueOf(i));
                                }
                            } else if (!arrayList3.contains(Integer.valueOf(i2))) {
                                arrayList3.add(Integer.valueOf(i2));
                            }
                        }
                    }
                    b.m12155b();
                    Iterable arrayList4 = new ArrayList();
                    HashMap hashMap2 = new HashMap();
                    final Object arrayList5 = new ArrayList();
                    final ArrayList arrayList6 = new ArrayList();
                    final ArrayList arrayList7 = new ArrayList();
                    final ArrayList arrayList8 = new ArrayList();
                    if (stringBuilder.length() > 0) {
                        AbstractSerializedData g;
                        Message TLdeserialize;
                        ArrayList arrayList9;
                        int i3;
                        SQLiteCursor b2 = MessagesStorage.this.database.m12165b("SELECT read_state, data, send_state, mid, date, uid, replydata FROM messages WHERE uid IN (" + stringBuilder.toString() + ") AND out = 0 AND read_state IN(0,2) ORDER BY date DESC LIMIT 50", new Object[0]);
                        while (b2.m12152a()) {
                            g = b2.m12161g(1);
                            if (g != null) {
                                TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                MessageObject.setUnreadFlags(TLdeserialize, b2.m12154b(0));
                                TLdeserialize.id = b2.m12154b(3);
                                TLdeserialize.date = b2.m12154b(4);
                                TLdeserialize.dialog_id = b2.m12158d(5);
                                arrayList5.add(TLdeserialize);
                                i = (int) TLdeserialize.dialog_id;
                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList, arrayList2);
                                TLdeserialize.send_state = b2.m12154b(2);
                                if (!(TLdeserialize.to_id.channel_id != 0 || MessageObject.isUnread(TLdeserialize) || i == 0) || TLdeserialize.id > 0) {
                                    TLdeserialize.send_state = 0;
                                }
                                if (i == 0 && !b2.m12153a(5)) {
                                    TLdeserialize.random_id = b2.m12158d(5);
                                }
                                try {
                                    if (TLdeserialize.reply_to_msg_id != 0 && ((TLdeserialize.action instanceof TLRPC$TL_messageActionPinMessage) || (TLdeserialize.action instanceof TLRPC$TL_messageActionPaymentSent) || (TLdeserialize.action instanceof TLRPC$TL_messageActionGameScore))) {
                                        if (!b2.m12153a(6)) {
                                            g = b2.m12161g(6);
                                            if (g != null) {
                                                TLdeserialize.replyMessage = Message.TLdeserialize(g, g.readInt32(false), false);
                                                g.reuse();
                                                if (TLdeserialize.replyMessage != null) {
                                                    if (MessageObject.isMegagroup(TLdeserialize)) {
                                                        Message message = TLdeserialize.replyMessage;
                                                        message.flags |= Integer.MIN_VALUE;
                                                    }
                                                    MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize.replyMessage, arrayList, arrayList2);
                                                }
                                            }
                                        }
                                        if (TLdeserialize.replyMessage == null) {
                                            long j = (long) TLdeserialize.reply_to_msg_id;
                                            if (TLdeserialize.to_id.channel_id != 0) {
                                                j |= ((long) TLdeserialize.to_id.channel_id) << 32;
                                            }
                                            if (!arrayList4.contains(Long.valueOf(j))) {
                                                arrayList4.add(Long.valueOf(j));
                                            }
                                            arrayList9 = (ArrayList) hashMap2.get(Integer.valueOf(TLdeserialize.reply_to_msg_id));
                                            if (arrayList9 == null) {
                                                arrayList9 = new ArrayList();
                                                hashMap2.put(Integer.valueOf(TLdeserialize.reply_to_msg_id), arrayList9);
                                            }
                                            arrayList9.add(TLdeserialize);
                                        }
                                    }
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                            }
                        }
                        b2.m12155b();
                        if (!arrayList4.isEmpty()) {
                            SQLiteCursor b3 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT data, mid, date, uid FROM messages WHERE mid IN(%s)", new Object[]{TextUtils.join(",", arrayList4)}), new Object[0]);
                            while (b3.m12152a()) {
                                g = b3.m12161g(0);
                                if (g != null) {
                                    TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                    g.reuse();
                                    TLdeserialize.id = b3.m12154b(1);
                                    TLdeserialize.date = b3.m12154b(2);
                                    TLdeserialize.dialog_id = b3.m12158d(3);
                                    MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList, arrayList2);
                                    arrayList9 = (ArrayList) hashMap2.get(Integer.valueOf(TLdeserialize.id));
                                    if (arrayList9 != null) {
                                        for (i3 = 0; i3 < arrayList9.size(); i3++) {
                                            Message message2 = (Message) arrayList9.get(i3);
                                            message2.replyMessage = TLdeserialize;
                                            if (MessageObject.isMegagroup(message2)) {
                                                message2 = message2.replyMessage;
                                                message2.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                }
                            }
                            b3.m12155b();
                        }
                        if (!arrayList3.isEmpty()) {
                            MessagesStorage.this.getEncryptedChatsInternal(TextUtils.join(",", arrayList3), arrayList8, arrayList);
                        }
                        if (!arrayList.isEmpty()) {
                            MessagesStorage.this.getUsersInternal(TextUtils.join(",", arrayList), arrayList6);
                        }
                        if (!arrayList2.isEmpty()) {
                            MessagesStorage.this.getChatsInternal(TextUtils.join(",", arrayList2), arrayList7);
                            int i4 = 0;
                            while (i4 < arrayList7.size()) {
                                Chat chat = (Chat) arrayList7.get(i4);
                                if (chat == null || (!chat.left && chat.migrated_to == null)) {
                                    i = i4;
                                } else {
                                    MessagesStorage.this.database.m12164a("UPDATE dialogs SET unread_count = 0 WHERE did = " + ((long) (-chat.id))).m12179c().m12181e();
                                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET read_state = 3 WHERE uid = %d AND mid > 0 AND read_state IN(0,2) AND out = 0", new Object[]{Long.valueOf(r10)})).m12179c().m12181e();
                                    arrayList7.remove(i4);
                                    int i5 = i4 - 1;
                                    hashMap.remove(Long.valueOf((long) (-chat.id)));
                                    i3 = 0;
                                    while (i3 < arrayList5.size()) {
                                        if (((Message) arrayList5.get(i3)).dialog_id == ((long) (-chat.id))) {
                                            arrayList5.remove(i3);
                                            i4 = i3 - 1;
                                        } else {
                                            i4 = i3;
                                        }
                                        i3 = i4 + 1;
                                    }
                                    i = i5;
                                }
                                i4 = i + 1;
                            }
                        }
                    }
                    Collections.reverse(arrayList5);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationsController.getInstance().processLoadedUnreadMessages(hashMap, arrayList5, arrayList6, arrayList7, arrayList8);
                        }
                    });
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
        });
    }

    public void loadWebRecent(final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT id, image_url, thumb_url, local_url, width, height, size, date, document FROM web_recent_v3 WHERE type = " + i + " ORDER BY date DESC", new Object[0]);
                    final ArrayList arrayList = new ArrayList();
                    while (b.m12152a()) {
                        SearchImage searchImage = new SearchImage();
                        searchImage.id = b.m12159e(0);
                        searchImage.imageUrl = b.m12159e(1);
                        searchImage.thumbUrl = b.m12159e(2);
                        searchImage.localUrl = b.m12159e(3);
                        searchImage.width = b.m12154b(4);
                        searchImage.height = b.m12154b(5);
                        searchImage.size = b.m12154b(6);
                        searchImage.date = b.m12154b(7);
                        if (!b.m12153a(8)) {
                            AbstractSerializedData g = b.m12161g(8);
                            if (g != null) {
                                searchImage.document = Document.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                            }
                        }
                        searchImage.type = i;
                        arrayList.add(searchImage);
                    }
                    b.m12155b();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recentImagesDidLoaded, Integer.valueOf(i), arrayList);
                        }
                    });
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
        });
    }

    public void markMentionMessageAsRead(int i, int i2, long j) {
        final int i3 = i;
        final int i4 = i2;
        final long j2 = j;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    long j = (long) i3;
                    if (i4 != 0) {
                        j |= ((long) i4) << 32;
                    }
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 2 WHERE mid = %d", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                    if (b.m12152a()) {
                        i = Math.max(0, b.m12154b(0) - 1);
                    }
                    b.m12155b();
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", new Object[]{Integer.valueOf(i), Long.valueOf(j2)})).m12179c().m12181e();
                    HashMap hashMap = new HashMap();
                    hashMap.put(Long.valueOf(j2), Integer.valueOf(i));
                    MessagesController.getInstance().processDialogsUpdateRead(null, hashMap);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void markMessageAsMention(final long j) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET mention = 1, read_state = read_state & ~2 WHERE mid = %d", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void markMessageAsSendError(final Message message) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long j = (long) message.id;
                    if (message.to_id.channel_id != 0) {
                        j |= ((long) message.to_id.channel_id) << 32;
                    }
                    MessagesStorage.this.database.m12164a("UPDATE messages SET send_state = 2 WHERE mid = " + j).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public ArrayList<Long> markMessagesAsDeleted(final int i, final int i2, boolean z) {
        if (!z) {
            return markMessagesAsDeletedInternal(i, i2);
        }
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.markMessagesAsDeletedInternal(i, i2);
            }
        });
        return null;
    }

    public ArrayList<Long> markMessagesAsDeleted(final ArrayList<Integer> arrayList, boolean z, final int i) {
        if (arrayList.isEmpty()) {
            return null;
        }
        if (!z) {
            return markMessagesAsDeletedInternal((ArrayList) arrayList, i);
        }
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.markMessagesAsDeletedInternal(arrayList, i);
            }
        });
        return null;
    }

    public void markMessagesAsDeletedByRandoms(final ArrayList<Long> arrayList) {
        if (!arrayList.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        String join = TextUtils.join(",", arrayList);
                        SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid FROM randoms WHERE random_id IN(%s)", new Object[]{join}), new Object[0]);
                        final ArrayList arrayList = new ArrayList();
                        while (b.m12152a()) {
                            arrayList.add(Integer.valueOf(b.m12154b(0)));
                        }
                        b.m12155b();
                        if (!arrayList.isEmpty()) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, arrayList, Integer.valueOf(0));
                                }
                            });
                            MessagesStorage.getInstance().updateDialogsWithReadMessagesInternal(arrayList, null, null, null);
                            MessagesStorage.getInstance().markMessagesAsDeletedInternal(arrayList, 0);
                            MessagesStorage.getInstance().updateDialogsWithDeletedMessagesInternal(arrayList, null, 0);
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void markMessagesAsRead(final SparseArray<Long> sparseArray, final SparseArray<Long> sparseArray2, final HashMap<Integer, Integer> hashMap, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    MessagesStorage.this.markMessagesAsReadInternal(sparseArray, sparseArray2, hashMap);
                }
            });
        } else {
            markMessagesAsReadInternal(sparseArray, sparseArray2, hashMap);
        }
    }

    public void markMessagesContentAsRead(final ArrayList<Long> arrayList, final int i) {
        if (arrayList != null && !arrayList.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        String join = TextUtils.join(",", arrayList);
                        MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 2 WHERE mid IN (%s)", new Object[]{join})).m12179c().m12181e();
                        if (i != 0) {
                            SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid, ttl FROM messages WHERE mid IN (%s) AND ttl > 0", new Object[]{join}), new Object[0]);
                            ArrayList arrayList = null;
                            while (b.m12152a()) {
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                arrayList.add(Integer.valueOf(b.m12154b(0)));
                            }
                            if (arrayList != null) {
                                MessagesStorage.this.emptyMessagesMedia(arrayList);
                            }
                            b.m12155b();
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void openDatabase(boolean z) {
        this.cacheFile = new File(ApplicationLoader.getFilesDirFixed(), "cache4.db");
        boolean z2 = !this.cacheFile.exists();
        try {
            this.database = new SQLiteDatabase(this.cacheFile.getPath());
            this.database.m12164a("PRAGMA secure_delete = ON").m12179c().m12181e();
            this.database.m12164a("PRAGMA temp_store = 1").m12179c().m12181e();
            if (z2) {
                FileLog.m13726e("create new database");
                this.database.m12164a("CREATE TABLE messages_holes(uid INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, start));").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes(uid, end);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE media_holes_v2(uid INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, type, start));").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_end_media_holes_v2 ON media_holes_v2(uid, type, end);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE messages(mid INTEGER PRIMARY KEY, uid INTEGER, read_state INTEGER, send_state INTEGER, date INTEGER, data BLOB, out INTEGER, ttl INTEGER, media INTEGER, replydata BLOB, imp INTEGER, mention INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_mid_idx_messages ON messages(uid, mid);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages ON messages(uid, date, mid);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS mid_out_idx_messages ON messages(mid, out);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS task_idx_messages ON messages(uid, out, read_state, ttl, date, send_state);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS send_state_idx_messages ON messages(mid, send_state, date) WHERE mid < 0 AND send_state = 1;").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages ON messages(uid, mention, read_state);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE download_queue(uid INTEGER, type INTEGER, date INTEGER, data BLOB, PRIMARY KEY (uid, type));").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS type_date_idx_download_queue ON download_queue(type, date);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE user_contacts_v7(key TEXT PRIMARY KEY, uid INTEGER, fname TEXT, sname TEXT, imported INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE user_phones_v7(key TEXT, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (key, phone))").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v7(sphone, deleted);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE dialogs(did INTEGER PRIMARY KEY, date INTEGER, unread_count INTEGER, last_mid INTEGER, inbox_max INTEGER, outbox_max INTEGER, last_mid_i INTEGER, unread_count_i INTEGER, pts INTEGER, date_i INTEGER, pinned INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS date_idx_dialogs ON dialogs(date);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS last_mid_idx_dialogs ON dialogs(last_mid);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS unread_count_idx_dialogs ON dialogs(unread_count);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS last_mid_i_idx_dialogs ON dialogs(last_mid_i);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS unread_count_i_idx_dialogs ON dialogs(unread_count_i);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE randoms(random_id INTEGER, mid INTEGER, PRIMARY KEY (random_id, mid))").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS mid_idx_randoms ON randoms(mid);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE enc_tasks_v2(mid INTEGER PRIMARY KEY, date INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS date_idx_enc_tasks_v2 ON enc_tasks_v2(date);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE messages_seq(mid INTEGER PRIMARY KEY, seq_in INTEGER, seq_out INTEGER);").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS seq_idx_messages_seq ON messages_seq(seq_in, seq_out);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").m12179c().m12181e();
                this.database.m12164a("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE media_v2(mid INTEGER PRIMARY KEY, uid INTEGER, date INTEGER, type INTEGER, data BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media ON media_v2(uid, mid, type, date);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE bot_keyboard(uid INTEGER PRIMARY KEY, mid INTEGER, info BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS bot_keyboard_idx_mid ON bot_keyboard(mid);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE chat_settings_v2(uid INTEGER PRIMARY KEY, info BLOB, pinned INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS chat_settings_pinned_idx ON chat_settings_v2(uid, pinned) WHERE pinned != 0;").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE chat_pinned(uid INTEGER PRIMARY KEY, pinned INTEGER, data BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS chat_pinned_mid_idx ON chat_pinned(uid, pinned) WHERE pinned != 0;").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE chat_hints(did INTEGER, type INTEGER, rating REAL, date INTEGER, PRIMARY KEY(did, type))").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS chat_hints_rating_idx ON chat_hints(rating);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE botcache(id TEXT PRIMARY KEY, date INTEGER, data BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE INDEX IF NOT EXISTS botcache_date_idx ON botcache(date);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE users_data(uid INTEGER PRIMARY KEY, about TEXT)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE users(uid INTEGER PRIMARY KEY, name TEXT, status INTEGER, data BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE chats(uid INTEGER PRIMARY KEY, name TEXT, data BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE enc_chats(uid INTEGER PRIMARY KEY, user INTEGER, name TEXT, data BLOB, g BLOB, authkey BLOB, ttl INTEGER, layer INTEGER, seq_in INTEGER, seq_out INTEGER, use_count INTEGER, exchange_id INTEGER, key_date INTEGER, fprint INTEGER, fauthkey BLOB, khash BLOB, in_seq_no INTEGER, admin_id INTEGER, mtproto_seq INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE channel_users_v2(did INTEGER, uid INTEGER, date INTEGER, data BLOB, PRIMARY KEY(did, uid))").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE channel_admins(did INTEGER, uid INTEGER, PRIMARY KEY(did, uid))").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE contacts(uid INTEGER PRIMARY KEY, mutual INTEGER)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE wallpapers(uid INTEGER PRIMARY KEY, data BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE user_photos(uid INTEGER, id INTEGER, data BLOB, PRIMARY KEY (uid, id))").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE blocked_users(uid INTEGER PRIMARY KEY)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE dialog_settings(did INTEGER PRIMARY KEY, flags INTEGER);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE web_recent_v3(id TEXT, type INTEGER, image_url TEXT, thumb_url TEXT, local_url TEXT, width INTEGER, height INTEGER, size INTEGER, date INTEGER, document BLOB, PRIMARY KEY (id, type));").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE stickers_v2(id INTEGER PRIMARY KEY, data BLOB, date INTEGER, hash TEXT);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE stickers_featured(id INTEGER PRIMARY KEY, data BLOB, unread BLOB, date INTEGER, hash TEXT);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE hashtag_recent_v2(id TEXT PRIMARY KEY, date INTEGER);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE webpage_pending(id INTEGER, mid INTEGER, PRIMARY KEY (id, mid));").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE sent_files_v2(uid TEXT, type INTEGER, data BLOB, PRIMARY KEY (uid, type))").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE search_recent(did INTEGER PRIMARY KEY, date INTEGER);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE media_counts_v2(uid INTEGER, type INTEGER, count INTEGER, PRIMARY KEY(uid, type))").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE keyvalue(id TEXT PRIMARY KEY, value TEXT)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE bot_info(uid INTEGER PRIMARY KEY, info BLOB)").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE pending_tasks(id INTEGER PRIMARY KEY, data BLOB);").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE requested_holes(uid INTEGER, seq_out_start INTEGER, seq_out_end INTEGER, PRIMARY KEY (uid, seq_out_start, seq_out_end));").m12179c().m12181e();
                this.database.m12164a("CREATE TABLE sharing_locations(uid INTEGER PRIMARY KEY, mid INTEGER, date INTEGER, period INTEGER, message BLOB);").m12179c().m12181e();
                this.database.m12164a("PRAGMA user_version = 46").m12179c().m12181e();
            } else {
                int intValue = this.database.m12163a("PRAGMA user_version", new Object[0]).intValue();
                FileLog.m13726e("current db version = " + intValue);
                if (intValue == 0) {
                    throw new Exception("malformed");
                }
                try {
                    SQLiteCursor b = this.database.m12165b("SELECT seq, pts, date, qts, lsv, sg, pbytes FROM params WHERE id = 1", new Object[0]);
                    if (b.m12152a()) {
                        lastSeqValue = b.m12154b(0);
                        lastPtsValue = b.m12154b(1);
                        lastDateValue = b.m12154b(2);
                        lastQtsValue = b.m12154b(3);
                        lastSecretVersion = b.m12154b(4);
                        secretG = b.m12154b(5);
                        if (b.m12153a(6)) {
                            secretPBytes = null;
                        } else {
                            secretPBytes = b.m12160f(6);
                            if (secretPBytes != null && secretPBytes.length == 1) {
                                secretPBytes = null;
                            }
                        }
                    }
                    b.m12155b();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    try {
                        this.database.m12164a("CREATE TABLE IF NOT EXISTS params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").m12179c().m12181e();
                        this.database.m12164a("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").m12179c().m12181e();
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                }
                if (intValue < 46) {
                    updateDbToLastVersion(intValue);
                }
            }
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
            if (z && e22.getMessage().contains("malformed")) {
                cleanupInternal();
                UserConfig.dialogsLoadOffsetId = 0;
                UserConfig.totalDialogsLoadCount = 0;
                UserConfig.dialogsLoadOffsetDate = 0;
                UserConfig.dialogsLoadOffsetUserId = 0;
                UserConfig.dialogsLoadOffsetChatId = 0;
                UserConfig.dialogsLoadOffsetChannelId = 0;
                UserConfig.dialogsLoadOffsetAccess = 0;
                UserConfig.saveConfig(false);
                openDatabase(false);
            }
        }
        loadUnreadMessages();
        loadPendingTasks();
    }

    public void overwriteChannel(final int i, final TLRPC$TL_updates_channelDifferenceTooLong tLRPC$TL_updates_channelDifferenceTooLong, final int i2) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                boolean z = false;
                try {
                    int b;
                    boolean z2;
                    final long j = (long) (-i);
                    SQLiteCursor b2 = MessagesStorage.this.database.m12165b("SELECT pts, pinned FROM dialogs WHERE did = " + j, new Object[0]);
                    if (b2.m12152a()) {
                        b = b2.m12154b(1);
                        z2 = false;
                    } else if (i2 != 0) {
                        b = 0;
                        z2 = true;
                    } else {
                        b = 0;
                        z2 = false;
                    }
                    b2.m12155b();
                    MessagesStorage.this.database.m12164a("DELETE FROM messages WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM bot_keyboard WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_counts_v2 WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_v2 WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM messages_holes WHERE uid = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_holes_v2 WHERE uid = " + j).m12179c().m12181e();
                    BotQuery.clearBotKeyboard(j, null);
                    TLRPC$messages_Dialogs tLRPC$TL_messages_dialogs = new TLRPC$TL_messages_dialogs();
                    tLRPC$TL_messages_dialogs.chats.addAll(tLRPC$TL_updates_channelDifferenceTooLong.chats);
                    tLRPC$TL_messages_dialogs.users.addAll(tLRPC$TL_updates_channelDifferenceTooLong.users);
                    tLRPC$TL_messages_dialogs.messages.addAll(tLRPC$TL_updates_channelDifferenceTooLong.messages);
                    TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                    tLRPC$TL_dialog.id = j;
                    tLRPC$TL_dialog.flags = 1;
                    tLRPC$TL_dialog.peer = new TLRPC$TL_peerChannel();
                    tLRPC$TL_dialog.peer.channel_id = i;
                    tLRPC$TL_dialog.top_message = tLRPC$TL_updates_channelDifferenceTooLong.top_message;
                    tLRPC$TL_dialog.read_inbox_max_id = tLRPC$TL_updates_channelDifferenceTooLong.read_inbox_max_id;
                    tLRPC$TL_dialog.read_outbox_max_id = tLRPC$TL_updates_channelDifferenceTooLong.read_outbox_max_id;
                    tLRPC$TL_dialog.unread_count = tLRPC$TL_updates_channelDifferenceTooLong.unread_count;
                    tLRPC$TL_dialog.unread_mentions_count = tLRPC$TL_updates_channelDifferenceTooLong.unread_mentions_count;
                    tLRPC$TL_dialog.notify_settings = null;
                    if (b != 0) {
                        z = true;
                    }
                    tLRPC$TL_dialog.pinned = z;
                    tLRPC$TL_dialog.pinnedNum = b;
                    tLRPC$TL_dialog.pts = tLRPC$TL_updates_channelDifferenceTooLong.pts;
                    tLRPC$TL_messages_dialogs.dialogs.add(tLRPC$TL_dialog);
                    MessagesStorage.this.putDialogsInternal(tLRPC$TL_messages_dialogs, false);
                    MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, false, i);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, Long.valueOf(j), Boolean.valueOf(true));
                        }
                    });
                    if (!z2) {
                        return;
                    }
                    if (i2 == 1) {
                        MessagesController.getInstance().checkChannelInviter(i);
                    } else {
                        MessagesController.getInstance().generateJoinMessage(i, false);
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void processPendingRead(long j, long j2, int i) {
        final long j3 = j;
        final long j4 = j2;
        final int i2 = i;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    SQLitePreparedStatement a;
                    MessagesStorage.this.database.m12168d();
                    if (((int) j3) != 0) {
                        a = MessagesStorage.this.database.m12164a("UPDATE messages SET read_state = read_state | 1 WHERE uid = ? AND mid <= ? AND read_state IN(0,2) AND out = 0");
                        a.m12180d();
                        a.m12175a(1, j3);
                        a.m12175a(2, j4);
                        a.m12178b();
                        a.m12181e();
                    } else {
                        a = MessagesStorage.this.database.m12164a("UPDATE messages SET read_state = read_state | 1 WHERE uid = ? AND date <= ? AND read_state IN(0,2) AND out = 0");
                        a.m12180d();
                        a.m12175a(1, j3);
                        a.m12174a(2, i2);
                        a.m12178b();
                        a.m12181e();
                    }
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT inbox_max FROM dialogs WHERE did = " + j3, new Object[0]);
                    if (b.m12152a()) {
                        i = b.m12154b(0);
                    }
                    b.m12155b();
                    i = Math.max(i, (int) j4);
                    a = MessagesStorage.this.database.m12164a("UPDATE dialogs SET unread_count = 0, inbox_max = ? WHERE did = ?");
                    a.m12180d();
                    a.m12174a(1, i);
                    a.m12175a(2, j3);
                    a.m12178b();
                    a.m12181e();
                    MessagesStorage.this.database.m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void putBlockedUsers(final ArrayList<Integer> arrayList, final boolean z) {
        if (arrayList != null && !arrayList.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        if (z) {
                            MessagesStorage.this.database.m12164a("DELETE FROM blocked_users WHERE 1").m12179c().m12181e();
                        }
                        MessagesStorage.this.database.m12168d();
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO blocked_users VALUES(?)");
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            Integer num = (Integer) it.next();
                            a.m12180d();
                            a.m12174a(1, num.intValue());
                            a.m12178b();
                        }
                        a.m12181e();
                        MessagesStorage.this.database.m12169e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void putCachedPhoneBook(final HashMap<String, Contact> hashMap, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12168d();
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO user_contacts_v7 VALUES(?, ?, ?, ?, ?)");
                    SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("REPLACE INTO user_phones_v7 VALUES(?, ?, ?, ?)");
                    for (Entry value : hashMap.entrySet()) {
                        Contact contact = (Contact) value.getValue();
                        if (!(contact.phones.isEmpty() || contact.shortPhones.isEmpty())) {
                            a.m12180d();
                            a.m12176a(1, contact.key);
                            a.m12174a(2, contact.contact_id);
                            a.m12176a(3, contact.first_name);
                            a.m12176a(4, contact.last_name);
                            a.m12174a(5, contact.imported);
                            a.m12178b();
                            for (int i = 0; i < contact.phones.size(); i++) {
                                a2.m12180d();
                                a2.m12176a(1, contact.key);
                                a2.m12176a(2, (String) contact.phones.get(i));
                                a2.m12176a(3, (String) contact.shortPhones.get(i));
                                a2.m12174a(4, ((Integer) contact.phoneDeleted.get(i)).intValue());
                                a2.m12178b();
                            }
                        }
                    }
                    a.m12181e();
                    a2.m12181e();
                    MessagesStorage.this.database.m12169e();
                    if (z) {
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS user_contacts_v6;").m12179c().m12181e();
                        MessagesStorage.this.database.m12164a("DROP TABLE IF EXISTS user_phones_v6;").m12179c().m12181e();
                        MessagesStorage.this.getCachedPhoneBook(false);
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void putChannelAdmins(final int i, final ArrayList<Integer> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM channel_admins WHERE did = " + i).m12179c().m12181e();
                    MessagesStorage.this.database.m12168d();
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO channel_admins VALUES(?, ?)");
                    int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
                    for (int i = 0; i < arrayList.size(); i++) {
                        a.m12180d();
                        a.m12174a(1, i);
                        a.m12174a(2, ((Integer) arrayList.get(i)).intValue());
                        a.m12178b();
                    }
                    a.m12181e();
                    MessagesStorage.this.database.m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void putChannelViews(final SparseArray<SparseIntArray> sparseArray, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12168d();
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("UPDATE messages SET media = max((SELECT media FROM messages WHERE mid = ?), ?) WHERE mid = ?");
                    for (int i = 0; i < sparseArray.size(); i++) {
                        int keyAt = sparseArray.keyAt(i);
                        SparseIntArray sparseIntArray = (SparseIntArray) sparseArray.get(keyAt);
                        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                            int i3 = sparseIntArray.get(sparseIntArray.keyAt(i2));
                            long keyAt2 = (long) sparseIntArray.keyAt(i2);
                            if (z) {
                                keyAt2 |= ((long) (-keyAt)) << 32;
                            }
                            a.m12180d();
                            a.m12175a(1, keyAt2);
                            a.m12174a(2, i3);
                            a.m12175a(3, keyAt2);
                            a.m12178b();
                        }
                    }
                    a.m12181e();
                    MessagesStorage.this.database.m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void putContacts(ArrayList<TLRPC$TL_contact> arrayList, final boolean z) {
        if (!arrayList.isEmpty()) {
            final ArrayList arrayList2 = new ArrayList(arrayList);
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        if (z) {
                            MessagesStorage.this.database.m12164a("DELETE FROM contacts WHERE 1").m12179c().m12181e();
                        }
                        MessagesStorage.this.database.m12168d();
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO contacts VALUES(?, ?)");
                        for (int i = 0; i < arrayList2.size(); i++) {
                            TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) arrayList2.get(i);
                            a.m12180d();
                            a.m12174a(1, tLRPC$TL_contact.user_id);
                            a.m12174a(2, tLRPC$TL_contact.mutual ? 1 : 0);
                            a.m12178b();
                        }
                        a.m12181e();
                        MessagesStorage.this.database.m12169e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void putDialogPhotos(final int i, final TLRPC$photos_Photos tLRPC$photos_Photos) {
        if (tLRPC$photos_Photos != null && !tLRPC$photos_Photos.photos.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO user_photos VALUES(?, ?, ?)");
                        Iterator it = tLRPC$photos_Photos.photos.iterator();
                        while (it.hasNext()) {
                            Photo photo = (Photo) it.next();
                            if (!(photo instanceof TLRPC$TL_photoEmpty)) {
                                a.m12180d();
                                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(photo.getObjectSize());
                                photo.serializeToStream(nativeByteBuffer);
                                a.m12174a(1, i);
                                a.m12175a(2, photo.id);
                                a.m12177a(3, nativeByteBuffer);
                                a.m12178b();
                                nativeByteBuffer.reuse();
                            }
                        }
                        a.m12181e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void putDialogs(final TLRPC$messages_Dialogs tLRPC$messages_Dialogs, final boolean z) {
        if (!tLRPC$messages_Dialogs.dialogs.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    MessagesStorage.this.putDialogsInternal(tLRPC$messages_Dialogs, z);
                    try {
                        MessagesStorage.this.loadUnreadMessages();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void putEncryptedChat(final EncryptedChat encryptedChat, final User user, final TLRPC$TL_dialog tLRPC$TL_dialog) {
        if (encryptedChat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    int i = 1;
                    try {
                        if ((encryptedChat.key_hash == null || encryptedChat.key_hash.length < 16) && encryptedChat.auth_key != null) {
                            encryptedChat.key_hash = AndroidUtilities.calcAuthKeyHash(encryptedChat.auth_key);
                        }
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO enc_chats VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(encryptedChat.getObjectSize());
                        NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(encryptedChat.a_or_b != null ? encryptedChat.a_or_b.length : 1);
                        NativeByteBuffer nativeByteBuffer3 = new NativeByteBuffer(encryptedChat.auth_key != null ? encryptedChat.auth_key.length : 1);
                        NativeByteBuffer nativeByteBuffer4 = new NativeByteBuffer(encryptedChat.future_auth_key != null ? encryptedChat.future_auth_key.length : 1);
                        if (encryptedChat.key_hash != null) {
                            i = encryptedChat.key_hash.length;
                        }
                        NativeByteBuffer nativeByteBuffer5 = new NativeByteBuffer(i);
                        encryptedChat.serializeToStream(nativeByteBuffer);
                        a.m12174a(1, encryptedChat.id);
                        a.m12174a(2, user.id);
                        a.m12176a(3, MessagesStorage.this.formatUserSearchName(user));
                        a.m12177a(4, nativeByteBuffer);
                        if (encryptedChat.a_or_b != null) {
                            nativeByteBuffer2.writeBytes(encryptedChat.a_or_b);
                        }
                        if (encryptedChat.auth_key != null) {
                            nativeByteBuffer3.writeBytes(encryptedChat.auth_key);
                        }
                        if (encryptedChat.future_auth_key != null) {
                            nativeByteBuffer4.writeBytes(encryptedChat.future_auth_key);
                        }
                        if (encryptedChat.key_hash != null) {
                            nativeByteBuffer5.writeBytes(encryptedChat.key_hash);
                        }
                        a.m12177a(5, nativeByteBuffer2);
                        a.m12177a(6, nativeByteBuffer3);
                        a.m12174a(7, encryptedChat.ttl);
                        a.m12174a(8, encryptedChat.layer);
                        a.m12174a(9, encryptedChat.seq_in);
                        a.m12174a(10, encryptedChat.seq_out);
                        a.m12174a(11, (encryptedChat.key_use_count_in << 16) | encryptedChat.key_use_count_out);
                        a.m12175a(12, encryptedChat.exchange_id);
                        a.m12174a(13, encryptedChat.key_create_date);
                        a.m12175a(14, encryptedChat.future_key_fingerprint);
                        a.m12177a(15, nativeByteBuffer4);
                        a.m12177a(16, nativeByteBuffer5);
                        a.m12174a(17, encryptedChat.in_seq_no);
                        a.m12174a(18, encryptedChat.admin_id);
                        a.m12174a(19, encryptedChat.mtproto_seq);
                        a.m12178b();
                        a.m12181e();
                        nativeByteBuffer.reuse();
                        nativeByteBuffer2.reuse();
                        nativeByteBuffer3.reuse();
                        nativeByteBuffer4.reuse();
                        nativeByteBuffer5.reuse();
                        if (tLRPC$TL_dialog != null) {
                            SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            a2.m12175a(1, tLRPC$TL_dialog.id);
                            a2.m12174a(2, tLRPC$TL_dialog.last_message_date);
                            a2.m12174a(3, tLRPC$TL_dialog.unread_count);
                            a2.m12174a(4, tLRPC$TL_dialog.top_message);
                            a2.m12174a(5, tLRPC$TL_dialog.read_inbox_max_id);
                            a2.m12174a(6, tLRPC$TL_dialog.read_outbox_max_id);
                            a2.m12174a(7, 0);
                            a2.m12174a(8, tLRPC$TL_dialog.unread_mentions_count);
                            a2.m12174a(9, tLRPC$TL_dialog.pts);
                            a2.m12174a(10, 0);
                            a2.m12174a(11, tLRPC$TL_dialog.pinnedNum);
                            a2.m12178b();
                            a2.m12181e();
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void putMessages(ArrayList<Message> arrayList, boolean z, boolean z2, boolean z3, int i) {
        putMessages(arrayList, z, z2, z3, i, false);
    }

    public void putMessages(ArrayList<Message> arrayList, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        if (arrayList.size() != 0) {
            if (z2) {
                final ArrayList<Message> arrayList2 = arrayList;
                final boolean z5 = z;
                final boolean z6 = z3;
                final int i2 = i;
                final boolean z7 = z4;
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.putMessagesInternal(arrayList2, z5, z6, i2, z7);
                    }
                });
                return;
            }
            putMessagesInternal(arrayList, z, z3, i, z4);
        }
    }

    public void putMessages(TLRPC$messages_Messages tLRPC$messages_Messages, long j, int i, int i2, boolean z) {
        final TLRPC$messages_Messages tLRPC$messages_Messages2 = tLRPC$messages_Messages;
        final int i3 = i;
        final long j2 = j;
        final int i4 = i2;
        final boolean z2 = z;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (!tLRPC$messages_Messages2.messages.isEmpty()) {
                        int i;
                        int i2;
                        MessagesStorage.this.database.m12168d();
                        if (i3 == 0) {
                            i = ((Message) tLRPC$messages_Messages2.messages.get(tLRPC$messages_Messages2.messages.size() - 1)).id;
                            MessagesStorage.this.closeHolesInTable("messages_holes", j2, i, i4);
                            MessagesStorage.this.closeHolesInMedia(j2, i, i4, -1);
                        } else if (i3 == 1) {
                            i2 = ((Message) tLRPC$messages_Messages2.messages.get(0)).id;
                            MessagesStorage.this.closeHolesInTable("messages_holes", j2, i4, i2);
                            MessagesStorage.this.closeHolesInMedia(j2, i4, i2, -1);
                        } else if (i3 == 3 || i3 == 2 || i3 == 4) {
                            i2 = (i4 != 0 || i3 == 4) ? ((Message) tLRPC$messages_Messages2.messages.get(0)).id : Integer.MAX_VALUE;
                            i = ((Message) tLRPC$messages_Messages2.messages.get(tLRPC$messages_Messages2.messages.size() - 1)).id;
                            MessagesStorage.this.closeHolesInTable("messages_holes", j2, i, i2);
                            MessagesStorage.this.closeHolesInMedia(j2, i, i2, -1);
                        }
                        int size = tLRPC$messages_Messages2.messages.size();
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
                        SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                        SQLitePreparedStatement sQLitePreparedStatement = null;
                        Message message = null;
                        int i3 = 0;
                        int i4 = 0;
                        int i5 = Integer.MAX_VALUE;
                        while (i4 < size) {
                            SQLitePreparedStatement sQLitePreparedStatement2;
                            int i6;
                            Message message2 = (Message) tLRPC$messages_Messages2.messages.get(i4);
                            long j = (long) message2.id;
                            int i7 = i3 == 0 ? message2.to_id.channel_id : i3;
                            long j2 = message2.to_id.channel_id != 0 ? j | (((long) i7) << 32) : j;
                            if (i3 == -2) {
                                SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid, data, ttl, mention, read_state FROM messages WHERE mid = %d", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                boolean a3 = b.m12152a();
                                if (a3) {
                                    AbstractSerializedData g = b.m12161g(1);
                                    if (g != null) {
                                        Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                        g.reuse();
                                        if (TLdeserialize != null) {
                                            message2.attachPath = TLdeserialize.attachPath;
                                            message2.ttl = b.m12154b(2);
                                        }
                                    }
                                    boolean z = b.m12154b(3) != 0;
                                    int b2 = b.m12154b(4);
                                    if (z != message2.mentioned) {
                                        if (i5 == Integer.MAX_VALUE) {
                                            SQLiteCursor b3 = MessagesStorage.this.database.m12165b("SELECT unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                                            if (b3.m12152a()) {
                                                i5 = b3.m12154b(0);
                                            }
                                            b3.m12155b();
                                        }
                                        if (z) {
                                            if (b2 <= 1) {
                                                i5--;
                                            }
                                        } else if (message2.media_unread) {
                                            i5++;
                                        }
                                    }
                                }
                                b.m12155b();
                                if (!a3) {
                                    message2 = message;
                                    sQLitePreparedStatement2 = sQLitePreparedStatement;
                                    i6 = i5;
                                    i4++;
                                    i3 = i7;
                                    i5 = i6;
                                    sQLitePreparedStatement = sQLitePreparedStatement2;
                                    message = message2;
                                }
                            }
                            if (i4 == 0 && z2) {
                                i2 = 0;
                                i = 0;
                                SQLiteCursor b4 = MessagesStorage.this.database.m12165b("SELECT pinned, unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                                if (b4.m12152a()) {
                                    i2 = b4.m12154b(0);
                                    i = b4.m12154b(1);
                                }
                                b4.m12155b();
                                SQLitePreparedStatement a4 = MessagesStorage.this.database.m12164a("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                a4.m12175a(1, j2);
                                a4.m12174a(2, message2.date);
                                a4.m12174a(3, 0);
                                a4.m12175a(4, j2);
                                a4.m12174a(5, message2.id);
                                a4.m12174a(6, 0);
                                a4.m12175a(7, j2);
                                a4.m12174a(8, i);
                                a4.m12174a(9, tLRPC$messages_Messages2.pts);
                                a4.m12174a(10, message2.date);
                                a4.m12174a(11, i2);
                                a4.m12178b();
                                a4.m12181e();
                            }
                            MessagesStorage.this.fixUnsupportedMedia(message2);
                            a.m12180d();
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message2.getObjectSize());
                            message2.serializeToStream(nativeByteBuffer);
                            a.m12175a(1, j2);
                            a.m12175a(2, j2);
                            a.m12174a(3, MessageObject.getUnreadFlags(message2));
                            a.m12174a(4, message2.send_state);
                            a.m12174a(5, message2.date);
                            a.m12177a(6, nativeByteBuffer);
                            a.m12174a(7, MessageObject.isOut(message2) ? 1 : 0);
                            a.m12174a(8, message2.ttl);
                            if ((message2.flags & 1024) != 0) {
                                a.m12174a(9, message2.views);
                            } else {
                                a.m12174a(9, MessagesStorage.this.getMessageMediaType(message2));
                            }
                            a.m12174a(10, 0);
                            a.m12174a(11, message2.mentioned ? 1 : 0);
                            a.m12178b();
                            if (SharedMediaQuery.canAddMessageToMedia(message2)) {
                                a2.m12180d();
                                a2.m12175a(1, j2);
                                a2.m12175a(2, j2);
                                a2.m12174a(3, message2.date);
                                a2.m12174a(4, SharedMediaQuery.getMediaType(message2));
                                a2.m12177a(5, nativeByteBuffer);
                                a2.m12178b();
                            }
                            nativeByteBuffer.reuse();
                            if (message2.media instanceof TLRPC$TL_messageMediaWebPage) {
                                if (sQLitePreparedStatement == null) {
                                    sQLitePreparedStatement = MessagesStorage.this.database.m12164a("REPLACE INTO webpage_pending VALUES(?, ?)");
                                }
                                sQLitePreparedStatement.m12180d();
                                sQLitePreparedStatement.m12175a(1, message2.media.webpage.id);
                                sQLitePreparedStatement.m12175a(2, j2);
                                sQLitePreparedStatement.m12178b();
                            }
                            if (i3 == 0 && MessagesStorage.this.isValidKeyboardToSave(message2) && (message == null || message.id < message2.id)) {
                                sQLitePreparedStatement2 = sQLitePreparedStatement;
                                i6 = i5;
                                i4++;
                                i3 = i7;
                                i5 = i6;
                                sQLitePreparedStatement = sQLitePreparedStatement2;
                                message = message2;
                            } else {
                                message2 = message;
                                sQLitePreparedStatement2 = sQLitePreparedStatement;
                                i6 = i5;
                                i4++;
                                i3 = i7;
                                i5 = i6;
                                sQLitePreparedStatement = sQLitePreparedStatement2;
                                message = message2;
                            }
                        }
                        a.m12181e();
                        a2.m12181e();
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.m12181e();
                        }
                        if (message != null) {
                            BotQuery.putBotKeyboard(j2, message);
                        }
                        MessagesStorage.this.putUsersInternal(tLRPC$messages_Messages2.users);
                        MessagesStorage.this.putChatsInternal(tLRPC$messages_Messages2.chats);
                        if (i5 != Integer.MAX_VALUE) {
                            MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", new Object[]{Integer.valueOf(i5), Long.valueOf(j2)})).m12179c().m12181e();
                            HashMap hashMap = new HashMap();
                            hashMap.put(Long.valueOf(j2), Integer.valueOf(i5));
                            MessagesController.getInstance().processDialogsUpdateRead(null, hashMap);
                        }
                        MessagesStorage.this.database.m12169e();
                        if (z2) {
                            MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, false, i3);
                        }
                    } else if (i3 == 0) {
                        MessagesStorage.this.doneHolesInTable("messages_holes", j2, i4);
                        MessagesStorage.this.doneHolesInMedia(j2, i4, -1);
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void putSentFile(final String str, final TLObject tLObject, final int i) {
        if (str != null && tLObject != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0028 in list [B:10:0x0025]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                    /*
                    r5 = this;
                    r1 = 0;
                    r0 = r3;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r3 = org.telegram.messenger.Utilities.MD5(r0);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    if (r3 == 0) goto L_0x0072;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                L_0x0009:
                    r0 = r4;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r0 instanceof org.telegram.tgnet.TLRPC.Photo;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    if (r0 == 0) goto L_0x0029;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                L_0x000f:
                    r2 = new org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.<init>();	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r4;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = (org.telegram.tgnet.TLRPC.Photo) r0;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.photo = r0;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r2.flags;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r0 | 1;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.flags = r0;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r2;
                L_0x0021:
                    if (r0 != 0) goto L_0x0042;
                L_0x0023:
                    if (r1 == 0) goto L_0x0028;
                L_0x0025:
                    r1.m12181e();
                L_0x0028:
                    return;
                L_0x0029:
                    r0 = r4;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r0 instanceof org.telegram.tgnet.TLRPC.Document;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    if (r0 == 0) goto L_0x0089;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                L_0x002f:
                    r2 = new org.telegram.tgnet.TLRPC$TL_messageMediaDocument;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.<init>();	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r4;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = (org.telegram.tgnet.TLRPC.Document) r0;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.document = r0;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r2.flags;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r0 | 1;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.flags = r0;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = r2;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    goto L_0x0021;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                L_0x0042:
                    r2 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2 = r2.database;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r4 = "REPLACE INTO sent_files_v2 VALUES(?, ?, ?)";	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r1 = r2.m12164a(r4);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r1.m12180d();	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r4 = r0.getObjectSize();	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.<init>(r4);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0.serializeToStream(r2);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = 1;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r1.m12176a(r0, r3);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = 2;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r3 = r5;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r1.m12174a(r0, r3);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r0 = 3;	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r1.m12177a(r0, r2);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r1.m12178b();	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    r2.reuse();	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                L_0x0072:
                    if (r1 == 0) goto L_0x0028;
                L_0x0074:
                    r1.m12181e();
                    goto L_0x0028;
                L_0x0078:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0078, all -> 0x0082 }
                    if (r1 == 0) goto L_0x0028;
                L_0x007e:
                    r1.m12181e();
                    goto L_0x0028;
                L_0x0082:
                    r0 = move-exception;
                    if (r1 == 0) goto L_0x0088;
                L_0x0085:
                    r1.m12181e();
                L_0x0088:
                    throw r0;
                L_0x0089:
                    r0 = r1;
                    goto L_0x0021;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.59.run():void");
                }
            });
        }
    }

    public void putUsersAndChats(final ArrayList<User> arrayList, final ArrayList<Chat> arrayList2, final boolean z, boolean z2) {
        if (arrayList != null && arrayList.isEmpty() && arrayList2 != null && arrayList2.isEmpty()) {
            return;
        }
        if (z2) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    MessagesStorage.this.putUsersAndChatsInternal(arrayList, arrayList2, z);
                }
            });
        } else {
            putUsersAndChatsInternal(arrayList, arrayList2, z);
        }
    }

    public void putWallpapers(final ArrayList<TLRPC$WallPaper> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM wallpapers WHERE 1").m12179c().m12181e();
                    MessagesStorage.this.database.m12168d();
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO wallpapers VALUES(?, ?)");
                    Iterator it = arrayList.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        TLRPC$WallPaper tLRPC$WallPaper = (TLRPC$WallPaper) it.next();
                        a.m12180d();
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$WallPaper.getObjectSize());
                        tLRPC$WallPaper.serializeToStream(nativeByteBuffer);
                        a.m12174a(1, i);
                        a.m12177a(2, nativeByteBuffer);
                        a.m12178b();
                        int i2 = i + 1;
                        nativeByteBuffer.reuse();
                        i = i2;
                    }
                    a.m12181e();
                    MessagesStorage.this.database.m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void putWebPages(final HashMap<Long, TLRPC$WebPage> hashMap) {
        if (hashMap != null && !hashMap.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        final ArrayList arrayList = new ArrayList();
                        for (Entry entry : hashMap.entrySet()) {
                            SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT mid FROM webpage_pending WHERE id = " + entry.getKey(), new Object[0]);
                            Iterable arrayList2 = new ArrayList();
                            while (b.m12152a()) {
                                arrayList2.add(Long.valueOf(b.m12158d(0)));
                            }
                            b.m12155b();
                            if (!arrayList2.isEmpty()) {
                                SQLiteCursor b2 = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT mid, data FROM messages WHERE mid IN (%s)", new Object[]{TextUtils.join(",", arrayList2)}), new Object[0]);
                                while (b2.m12152a()) {
                                    int b3 = b2.m12154b(0);
                                    AbstractSerializedData g = b2.m12161g(1);
                                    if (g != null) {
                                        Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                        g.reuse();
                                        if (TLdeserialize.media instanceof TLRPC$TL_messageMediaWebPage) {
                                            TLdeserialize.id = b3;
                                            TLdeserialize.media.webpage = (TLRPC$WebPage) entry.getValue();
                                            arrayList.add(TLdeserialize);
                                        }
                                    }
                                }
                                b2.m12155b();
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            MessagesStorage.this.database.m12168d();
                            SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("UPDATE messages SET data = ? WHERE mid = ?");
                            SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("UPDATE media_v2 SET data = ? WHERE mid = ?");
                            for (int i = 0; i < arrayList.size(); i++) {
                                Message message = (Message) arrayList.get(i);
                                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                                message.serializeToStream(nativeByteBuffer);
                                long j = (long) message.id;
                                long j2 = message.to_id.channel_id != 0 ? (((long) message.to_id.channel_id) << 32) | j : j;
                                a.m12180d();
                                a.m12177a(1, nativeByteBuffer);
                                a.m12175a(2, j2);
                                a.m12178b();
                                a2.m12180d();
                                a2.m12177a(1, nativeByteBuffer);
                                a2.m12175a(2, j2);
                                a2.m12178b();
                                nativeByteBuffer.reuse();
                            }
                            a.m12181e();
                            a2.m12181e();
                            MessagesStorage.this.database.m12169e();
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReceivedWebpages, arrayList);
                                }
                            });
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void putWebRecent(final ArrayList<SearchImage> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                try {
                    MessagesStorage.this.database.m12168d();
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO web_recent_v3 VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    int i2 = 0;
                    while (i2 < arrayList.size() && i2 != Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        NativeByteBuffer nativeByteBuffer;
                        SearchImage searchImage = (SearchImage) arrayList.get(i2);
                        a.m12180d();
                        a.m12176a(1, searchImage.id);
                        a.m12174a(2, searchImage.type);
                        a.m12176a(3, searchImage.imageUrl != null ? searchImage.imageUrl : TtmlNode.ANONYMOUS_REGION_ID);
                        a.m12176a(4, searchImage.thumbUrl != null ? searchImage.thumbUrl : TtmlNode.ANONYMOUS_REGION_ID);
                        a.m12176a(5, searchImage.localUrl != null ? searchImage.localUrl : TtmlNode.ANONYMOUS_REGION_ID);
                        a.m12174a(6, searchImage.width);
                        a.m12174a(7, searchImage.height);
                        a.m12174a(8, searchImage.size);
                        a.m12174a(9, searchImage.date);
                        if (searchImage.document != null) {
                            NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(searchImage.document.getObjectSize());
                            searchImage.document.serializeToStream(nativeByteBuffer2);
                            a.m12177a(10, nativeByteBuffer2);
                            nativeByteBuffer = nativeByteBuffer2;
                        } else {
                            a.m12172a(10);
                            nativeByteBuffer = null;
                        }
                        a.m12178b();
                        if (nativeByteBuffer != null) {
                            nativeByteBuffer.reuse();
                        }
                        i2++;
                    }
                    a.m12181e();
                    MessagesStorage.this.database.m12169e();
                    if (arrayList.size() >= Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        MessagesStorage.this.database.m12168d();
                        while (i < arrayList.size()) {
                            MessagesStorage.this.database.m12164a("DELETE FROM web_recent_v3 WHERE id = '" + ((SearchImage) arrayList.get(i)).id + "'").m12179c().m12181e();
                            i++;
                        }
                        MessagesStorage.this.database.m12169e();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void removeFromDownloadQueue(long j, int i, boolean z) {
        final boolean z2 = z;
        final int i2 = i;
        final long j2 = j;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (z2) {
                        SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT min(date) FROM download_queue WHERE type = %d", new Object[]{Integer.valueOf(i2)}), new Object[0]);
                        int b2 = b.m12152a() ? b.m12154b(0) : -1;
                        b.m12155b();
                        if (b2 != -1) {
                            MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE download_queue SET date = %d WHERE uid = %d AND type = %d", new Object[]{Integer.valueOf(b2 - 1), Long.valueOf(j2), Integer.valueOf(i2)})).m12179c().m12181e();
                            return;
                        }
                        return;
                    }
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "DELETE FROM download_queue WHERE uid = %d AND type = %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i2)})).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void removePendingTask(final long j) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a("DELETE FROM pending_tasks WHERE id = " + j).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void resetDialogs(TLRPC$messages_Dialogs tLRPC$messages_Dialogs, int i, int i2, int i3, int i4, int i5, HashMap<Long, TLRPC$TL_dialog> hashMap, HashMap<Long, MessageObject> hashMap2, Message message, int i6) {
        final TLRPC$messages_Dialogs tLRPC$messages_Dialogs2 = tLRPC$messages_Dialogs;
        final int i7 = i6;
        final int i8 = i2;
        final int i9 = i3;
        final int i10 = i4;
        final int i11 = i5;
        final Message message2 = message;
        final int i12 = i;
        final HashMap<Long, TLRPC$TL_dialog> hashMap3 = hashMap;
        final HashMap<Long, MessageObject> hashMap4 = hashMap2;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    int i2;
                    int b;
                    Iterable arrayList = new ArrayList();
                    int size = tLRPC$messages_Dialogs2.dialogs.size() - i7;
                    final HashMap hashMap = new HashMap();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    for (i2 = i7; i2 < tLRPC$messages_Dialogs2.dialogs.size(); i2++) {
                        arrayList3.add(Long.valueOf(((TLRPC$TL_dialog) tLRPC$messages_Dialogs2.dialogs.get(i2)).id));
                    }
                    SQLiteCursor b2 = MessagesStorage.this.database.m12165b("SELECT did, pinned FROM dialogs WHERE 1", new Object[0]);
                    int i3 = 0;
                    while (b2.m12152a()) {
                        long d = b2.m12158d(0);
                        b = b2.m12154b(1);
                        int i4 = (int) d;
                        if (i4 != 0) {
                            arrayList.add(Integer.valueOf(i4));
                            if (b > 0) {
                                i4 = Math.max(b, i3);
                                hashMap.put(Long.valueOf(d), Integer.valueOf(b));
                                arrayList2.add(Long.valueOf(d));
                                i3 = i4;
                            }
                        }
                        i4 = i3;
                        i3 = i4;
                    }
                    Collections.sort(arrayList2, new Comparator<Long>() {
                        public int compare(Long l, Long l2) {
                            Integer num = (Integer) hashMap.get(l);
                            Integer num2 = (Integer) hashMap.get(l2);
                            return num.intValue() < num2.intValue() ? 1 : num.intValue() > num2.intValue() ? -1 : 0;
                        }
                    });
                    while (arrayList2.size() < size) {
                        arrayList2.add(0, Long.valueOf(0));
                    }
                    b2.m12155b();
                    String str = "(" + TextUtils.join(",", arrayList) + ")";
                    MessagesStorage.this.database.m12168d();
                    MessagesStorage.this.database.m12164a("DELETE FROM dialogs WHERE did IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM messages WHERE uid IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM bot_keyboard WHERE uid IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_counts_v2 WHERE uid IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_v2 WHERE uid IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM messages_holes WHERE uid IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12164a("DELETE FROM media_holes_v2 WHERE uid IN " + str).m12179c().m12181e();
                    MessagesStorage.this.database.m12169e();
                    for (int i5 = 0; i5 < size; i5++) {
                        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$messages_Dialogs2.dialogs.get(i7 + i5);
                        i2 = arrayList2.indexOf(Long.valueOf(tLRPC$TL_dialog.id));
                        b = arrayList3.indexOf(Long.valueOf(tLRPC$TL_dialog.id));
                        if (!(i2 == -1 || b == -1)) {
                            Integer num;
                            if (i2 == b) {
                                num = (Integer) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num != null) {
                                    tLRPC$TL_dialog.pinnedNum = num.intValue();
                                }
                            } else {
                                num = (Integer) hashMap.get(Long.valueOf(((Long) arrayList2.get(b)).longValue()));
                                if (num != null) {
                                    tLRPC$TL_dialog.pinnedNum = num.intValue();
                                }
                            }
                        }
                        if (tLRPC$TL_dialog.pinnedNum == 0) {
                            tLRPC$TL_dialog.pinnedNum = (size - i5) + i3;
                        }
                    }
                    MessagesStorage.this.putDialogsInternal(tLRPC$messages_Dialogs2, false);
                    MessagesStorage.this.saveDiffParamsInternal(i8, i9, i10, i11);
                    if (message2 == null || message2.id == UserConfig.dialogsLoadOffsetId) {
                        UserConfig.dialogsLoadOffsetId = Integer.MAX_VALUE;
                    } else {
                        UserConfig.totalDialogsLoadCount = tLRPC$messages_Dialogs2.dialogs.size();
                        UserConfig.dialogsLoadOffsetId = message2.id;
                        UserConfig.dialogsLoadOffsetDate = message2.date;
                        Chat chat;
                        if (message2.to_id.channel_id != 0) {
                            UserConfig.dialogsLoadOffsetChannelId = message2.to_id.channel_id;
                            UserConfig.dialogsLoadOffsetChatId = 0;
                            UserConfig.dialogsLoadOffsetUserId = 0;
                            while (i < tLRPC$messages_Dialogs2.chats.size()) {
                                chat = (Chat) tLRPC$messages_Dialogs2.chats.get(i);
                                if (chat.id == UserConfig.dialogsLoadOffsetChannelId) {
                                    UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                                    break;
                                }
                                i++;
                            }
                        } else if (message2.to_id.chat_id != 0) {
                            UserConfig.dialogsLoadOffsetChatId = message2.to_id.chat_id;
                            UserConfig.dialogsLoadOffsetChannelId = 0;
                            UserConfig.dialogsLoadOffsetUserId = 0;
                            while (i < tLRPC$messages_Dialogs2.chats.size()) {
                                chat = (Chat) tLRPC$messages_Dialogs2.chats.get(i);
                                if (chat.id == UserConfig.dialogsLoadOffsetChatId) {
                                    UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                                    break;
                                }
                                i++;
                            }
                        } else if (message2.to_id.user_id != 0) {
                            UserConfig.dialogsLoadOffsetUserId = message2.to_id.user_id;
                            UserConfig.dialogsLoadOffsetChatId = 0;
                            UserConfig.dialogsLoadOffsetChannelId = 0;
                            for (i2 = 0; i2 < tLRPC$messages_Dialogs2.users.size(); i2++) {
                                User user = (User) tLRPC$messages_Dialogs2.users.get(i2);
                                if (user.id == UserConfig.dialogsLoadOffsetUserId) {
                                    UserConfig.dialogsLoadOffsetAccess = user.access_hash;
                                    break;
                                }
                            }
                        }
                    }
                    UserConfig.saveConfig(false);
                    MessagesController.getInstance().completeDialogsReset(tLRPC$messages_Dialogs2, i12, i8, i9, i10, i11, hashMap3, hashMap4, message2);
                } catch (Throwable e) {
                    FileLog.m13727e("tmessages", e);
                }
            }
        });
    }

    public void resetMentionsCount(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (i == 0) {
                        MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 2 WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", new Object[]{Long.valueOf(j)})).m12179c().m12181e();
                    }
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", new Object[]{Integer.valueOf(i), Long.valueOf(j)})).m12179c().m12181e();
                    HashMap hashMap = new HashMap();
                    hashMap.put(Long.valueOf(j), Integer.valueOf(i));
                    MessagesController.getInstance().processDialogsUpdateRead(null, hashMap);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void saveBotCache(final String str, final TLObject tLObject) {
        if (tLObject != null && !TextUtils.isEmpty(str)) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                        int i = tLObject instanceof TLRPC$TL_messages_botCallbackAnswer ? ((TLRPC$TL_messages_botCallbackAnswer) tLObject).cache_time + currentTime : tLObject instanceof TLRPC$TL_messages_botResults ? ((TLRPC$TL_messages_botResults) tLObject).cache_time + currentTime : currentTime;
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO botcache VALUES(?, ?, ?)");
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLObject.getObjectSize());
                        tLObject.serializeToStream(nativeByteBuffer);
                        a.m12176a(1, str);
                        a.m12174a(2, i);
                        a.m12177a(3, nativeByteBuffer);
                        a.m12178b();
                        a.m12181e();
                        nativeByteBuffer.reuse();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void saveChannelPts(final int i, final int i2) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("UPDATE dialogs SET pts = ? WHERE did = ?");
                    a.m12174a(1, i2);
                    a.m12174a(2, -i);
                    a.m12178b();
                    a.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void saveDiffParams(int i, int i2, int i3, int i4) {
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.saveDiffParamsInternal(i5, i6, i7, i8);
            }
        });
    }

    public void saveSecretParams(final int i, final int i2, final byte[] bArr) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 1;
                try {
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("UPDATE params SET lsv = ?, sg = ?, pbytes = ? WHERE id = 1");
                    a.m12174a(1, i);
                    a.m12174a(2, i2);
                    if (bArr != null) {
                        i = bArr.length;
                    }
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(i);
                    if (bArr != null) {
                        nativeByteBuffer.writeBytes(bArr);
                    }
                    a.m12177a(3, nativeByteBuffer);
                    a.m12178b();
                    a.m12181e();
                    nativeByteBuffer.reuse();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void setDialogFlags(long j, long j2) {
        final long j3 = j;
        final long j4 = j2;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.m12164a(String.format(Locale.US, "REPLACE INTO dialog_settings VALUES(%d, %d)", new Object[]{Long.valueOf(j3), Long.valueOf(j4)})).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void setDialogPinned(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("UPDATE dialogs SET pinned = ? WHERE did = ?");
                    a.m12174a(1, i);
                    a.m12175a(2, j);
                    a.m12178b();
                    a.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void setMessageSeq(final int i, final int i2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO messages_seq VALUES(?, ?, ?)");
                    a.m12180d();
                    a.m12174a(1, i);
                    a.m12174a(2, i2);
                    a.m12174a(3, i3);
                    a.m12178b();
                    a.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void unpinAllDialogsExceptNew(final ArrayList<Long> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    ArrayList arrayList = new ArrayList();
                    SQLiteCursor b = MessagesStorage.this.database.m12165b(String.format(Locale.US, "SELECT did FROM dialogs WHERE pinned != 0 AND did NOT IN (%s)", new Object[]{TextUtils.join(",", arrayList)}), new Object[0]);
                    while (b.m12152a()) {
                        if (((int) b.m12158d(0)) != 0) {
                            arrayList.add(Long.valueOf(b.m12158d(0)));
                        }
                    }
                    b.m12155b();
                    if (!arrayList.isEmpty()) {
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("UPDATE dialogs SET pinned = ? WHERE did = ?");
                        for (int i = 0; i < arrayList.size(); i++) {
                            long longValue = ((Long) arrayList.get(i)).longValue();
                            a.m12180d();
                            a.m12174a(1, 0);
                            a.m12175a(2, longValue);
                            a.m12178b();
                        }
                        a.m12181e();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void updateChannelPinnedMessage(final int i, final int i2) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + i, new Object[0]);
                    ChatFull chatFull = null;
                    ArrayList arrayList = new ArrayList();
                    if (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            chatFull = ChatFull.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            chatFull.pinned_msg_id = b.m12154b(1);
                        }
                    }
                    b.m12155b();
                    if (chatFull instanceof TL_channelFull) {
                        chatFull.pinned_msg_id = i2;
                        chatFull.flags |= 32;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull, Integer.valueOf(0), Boolean.valueOf(false), null);
                            }
                        });
                        SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chatFull.getObjectSize());
                        chatFull.serializeToStream(nativeByteBuffer);
                        a.m12174a(1, i);
                        a.m12177a(2, nativeByteBuffer);
                        a.m12174a(3, chatFull.pinned_msg_id);
                        a.m12178b();
                        a.m12181e();
                        nativeByteBuffer.reuse();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void updateChannelUsers(final int i, final ArrayList<ChannelParticipant> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long j = (long) (-i);
                    MessagesStorage.this.database.m12164a("DELETE FROM channel_users_v2 WHERE did = " + j).m12179c().m12181e();
                    MessagesStorage.this.database.m12168d();
                    SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO channel_users_v2 VALUES(?, ?, ?, ?)");
                    int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
                    for (int i = 0; i < arrayList.size(); i++) {
                        ChannelParticipant channelParticipant = (ChannelParticipant) arrayList.get(i);
                        a.m12180d();
                        a.m12175a(1, j);
                        a.m12174a(2, channelParticipant.user_id);
                        a.m12174a(3, currentTimeMillis);
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(channelParticipant.getObjectSize());
                        channelParticipant.serializeToStream(nativeByteBuffer);
                        a.m12177a(4, nativeByteBuffer);
                        nativeByteBuffer.reuse();
                        a.m12178b();
                        currentTimeMillis--;
                    }
                    a.m12181e();
                    MessagesStorage.this.database.m12169e();
                    MessagesStorage.this.loadChatInfo(i, null, false, true);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void updateChatInfo(int i, int i2, int i3, int i4, int i5) {
        final int i6 = i;
        final int i7 = i3;
        final int i8 = i2;
        final int i9 = i4;
        final int i10 = i5;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    ChatFull chatFull;
                    int i;
                    Iterator it;
                    TL_chatParticipant tL_chatParticipant;
                    int i2;
                    ChatParticipant chatParticipant;
                    ChatParticipant tL_chatParticipantAdmin;
                    Object obj;
                    SQLitePreparedStatement a;
                    NativeByteBuffer nativeByteBuffer;
                    SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + i6, new Object[0]);
                    ArrayList arrayList = new ArrayList();
                    if (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            ChatFull TLdeserialize = ChatFull.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            TLdeserialize.pinned_msg_id = b.m12154b(1);
                            chatFull = TLdeserialize;
                            b.m12155b();
                            if (chatFull instanceof TL_chatFull) {
                                if (i7 == 1) {
                                    for (i = 0; i < chatFull.participants.participants.size(); i++) {
                                        if (((ChatParticipant) chatFull.participants.participants.get(i)).user_id == i8) {
                                            chatFull.participants.participants.remove(i);
                                            break;
                                        }
                                    }
                                } else if (i7 == 0) {
                                    it = chatFull.participants.participants.iterator();
                                    while (it.hasNext()) {
                                        if (((ChatParticipant) it.next()).user_id == i8) {
                                            return;
                                        }
                                    }
                                    tL_chatParticipant = new TL_chatParticipant();
                                    tL_chatParticipant.user_id = i8;
                                    tL_chatParticipant.inviter_id = i9;
                                    tL_chatParticipant.date = ConnectionsManager.getInstance().getCurrentTime();
                                    chatFull.participants.participants.add(tL_chatParticipant);
                                } else if (i7 == 2) {
                                    i2 = 0;
                                    while (i2 < chatFull.participants.participants.size()) {
                                        chatParticipant = (ChatParticipant) chatFull.participants.participants.get(i2);
                                        if (chatParticipant.user_id != i8) {
                                            if (i9 != 1) {
                                                tL_chatParticipantAdmin = new TL_chatParticipantAdmin();
                                                tL_chatParticipantAdmin.user_id = chatParticipant.user_id;
                                                tL_chatParticipantAdmin.date = chatParticipant.date;
                                                tL_chatParticipantAdmin.inviter_id = chatParticipant.inviter_id;
                                                obj = tL_chatParticipantAdmin;
                                            } else {
                                                tL_chatParticipantAdmin = new TL_chatParticipant();
                                                tL_chatParticipantAdmin.user_id = chatParticipant.user_id;
                                                tL_chatParticipantAdmin.date = chatParticipant.date;
                                                tL_chatParticipantAdmin.inviter_id = chatParticipant.inviter_id;
                                                chatParticipant = tL_chatParticipantAdmin;
                                            }
                                            chatFull.participants.participants.set(i2, obj);
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                                chatFull.participants.version = i10;
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull, Integer.valueOf(0), Boolean.valueOf(false), null);
                                    }
                                });
                                a = MessagesStorage.this.database.m12164a("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                                nativeByteBuffer = new NativeByteBuffer(chatFull.getObjectSize());
                                chatFull.serializeToStream(nativeByteBuffer);
                                a.m12174a(1, i6);
                                a.m12177a(2, nativeByteBuffer);
                                a.m12174a(3, chatFull.pinned_msg_id);
                                a.m12178b();
                                a.m12181e();
                                nativeByteBuffer.reuse();
                            }
                        }
                    }
                    chatFull = null;
                    b.m12155b();
                    if (chatFull instanceof TL_chatFull) {
                        if (i7 == 1) {
                            for (i = 0; i < chatFull.participants.participants.size(); i++) {
                                if (((ChatParticipant) chatFull.participants.participants.get(i)).user_id == i8) {
                                    chatFull.participants.participants.remove(i);
                                    break;
                                }
                            }
                        } else if (i7 == 0) {
                            it = chatFull.participants.participants.iterator();
                            while (it.hasNext()) {
                                if (((ChatParticipant) it.next()).user_id == i8) {
                                    return;
                                }
                            }
                            tL_chatParticipant = new TL_chatParticipant();
                            tL_chatParticipant.user_id = i8;
                            tL_chatParticipant.inviter_id = i9;
                            tL_chatParticipant.date = ConnectionsManager.getInstance().getCurrentTime();
                            chatFull.participants.participants.add(tL_chatParticipant);
                        } else if (i7 == 2) {
                            i2 = 0;
                            while (i2 < chatFull.participants.participants.size()) {
                                chatParticipant = (ChatParticipant) chatFull.participants.participants.get(i2);
                                if (chatParticipant.user_id != i8) {
                                    i2++;
                                } else {
                                    if (i9 != 1) {
                                        tL_chatParticipantAdmin = new TL_chatParticipant();
                                        tL_chatParticipantAdmin.user_id = chatParticipant.user_id;
                                        tL_chatParticipantAdmin.date = chatParticipant.date;
                                        tL_chatParticipantAdmin.inviter_id = chatParticipant.inviter_id;
                                        chatParticipant = tL_chatParticipantAdmin;
                                    } else {
                                        tL_chatParticipantAdmin = new TL_chatParticipantAdmin();
                                        tL_chatParticipantAdmin.user_id = chatParticipant.user_id;
                                        tL_chatParticipantAdmin.date = chatParticipant.date;
                                        tL_chatParticipantAdmin.inviter_id = chatParticipant.inviter_id;
                                        obj = tL_chatParticipantAdmin;
                                    }
                                    chatFull.participants.participants.set(i2, obj);
                                }
                            }
                        }
                        chatFull.participants.version = i10;
                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                        a = MessagesStorage.this.database.m12164a("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                        nativeByteBuffer = new NativeByteBuffer(chatFull.getObjectSize());
                        chatFull.serializeToStream(nativeByteBuffer);
                        a.m12174a(1, i6);
                        a.m12177a(2, nativeByteBuffer);
                        a.m12174a(3, chatFull.pinned_msg_id);
                        a.m12178b();
                        a.m12181e();
                        nativeByteBuffer.reuse();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void updateChatInfo(final ChatFull chatFull, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b;
                    if (z) {
                        b = MessagesStorage.this.database.m12165b("SELECT uid FROM chat_settings_v2 WHERE uid = " + chatFull.id, new Object[0]);
                        boolean a = b.m12152a();
                        b.m12155b();
                        if (!a) {
                            return;
                        }
                    }
                    SQLitePreparedStatement a2 = MessagesStorage.this.database.m12164a("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chatFull.getObjectSize());
                    chatFull.serializeToStream(nativeByteBuffer);
                    a2.m12174a(1, chatFull.id);
                    a2.m12177a(2, nativeByteBuffer);
                    a2.m12174a(3, chatFull.pinned_msg_id);
                    a2.m12178b();
                    a2.m12181e();
                    nativeByteBuffer.reuse();
                    if (chatFull instanceof TL_channelFull) {
                        b = MessagesStorage.this.database.m12165b("SELECT date, pts, last_mid, inbox_max, outbox_max, pinned, unread_count_i FROM dialogs WHERE did = " + (-chatFull.id), new Object[0]);
                        if (b.m12152a()) {
                            int b2 = b.m12154b(3);
                            if (b2 <= chatFull.read_inbox_max_id) {
                                b2 = chatFull.read_inbox_max_id - b2;
                                if (b2 < chatFull.unread_count) {
                                    chatFull.unread_count = b2;
                                }
                                b2 = b.m12154b(0);
                                int b3 = b.m12154b(1);
                                long d = b.m12158d(2);
                                int b4 = b.m12154b(4);
                                int b5 = b.m12154b(5);
                                int b6 = b.m12154b(6);
                                SQLitePreparedStatement a3 = MessagesStorage.this.database.m12164a("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                a3.m12175a(1, (long) (-chatFull.id));
                                a3.m12174a(2, b2);
                                a3.m12174a(3, chatFull.unread_count);
                                a3.m12175a(4, d);
                                a3.m12174a(5, chatFull.read_inbox_max_id);
                                a3.m12174a(6, Math.max(b4, chatFull.read_outbox_max_id));
                                a3.m12175a(7, 0);
                                a3.m12174a(8, b6);
                                a3.m12174a(9, b3);
                                a3.m12174a(10, 0);
                                a3.m12174a(11, b5);
                                a3.m12178b();
                                a3.m12181e();
                            }
                        }
                        b.m12155b();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void updateChatParticipants(final ChatParticipants chatParticipants) {
        if (chatParticipants != null) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        SQLiteCursor b = MessagesStorage.this.database.m12165b("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + chatParticipants.chat_id, new Object[0]);
                        ChatFull chatFull = null;
                        ArrayList arrayList = new ArrayList();
                        if (b.m12152a()) {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                chatFull = ChatFull.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                chatFull.pinned_msg_id = b.m12154b(1);
                            }
                        }
                        b.m12155b();
                        if (chatFull instanceof TL_chatFull) {
                            chatFull.participants = chatParticipants;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull, Integer.valueOf(0), Boolean.valueOf(false), null);
                                }
                            });
                            SQLitePreparedStatement a = MessagesStorage.this.database.m12164a("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chatFull.getObjectSize());
                            chatFull.serializeToStream(nativeByteBuffer);
                            a.m12174a(1, chatFull.id);
                            a.m12177a(2, nativeByteBuffer);
                            a.m12174a(3, chatFull.pinned_msg_id);
                            a.m12178b();
                            a.m12181e();
                            nativeByteBuffer.reuse();
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    public void updateDialogsWithDeletedMessages(final ArrayList<Integer> arrayList, final ArrayList<Long> arrayList2, boolean z, final int i) {
        if (!arrayList.isEmpty() || i != 0) {
            if (z) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.updateDialogsWithDeletedMessagesInternal(arrayList, arrayList2, i);
                    }
                });
            } else {
                updateDialogsWithDeletedMessagesInternal(arrayList, arrayList2, i);
            }
        }
    }

    public void updateDialogsWithReadMessages(final SparseArray<Long> sparseArray, final SparseArray<Long> sparseArray2, final ArrayList<Long> arrayList, boolean z) {
        if (sparseArray.size() != 0 || !arrayList.isEmpty()) {
            if (z) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.updateDialogsWithReadMessagesInternal(null, sparseArray, sparseArray2, arrayList);
                    }
                });
            } else {
                updateDialogsWithReadMessagesInternal(null, sparseArray, sparseArray2, arrayList);
            }
        }
    }

    public void updateEncryptedChat(final EncryptedChat encryptedChat) {
        if (encryptedChat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0150 in list [B:35:0x014d]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                    /*
                    r10 = this;
                    r3 = 16;
                    r0 = 1;
                    r1 = 0;
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r2 == 0) goto L_0x0011;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x000a:
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r2 >= r3) goto L_0x0023;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0011:
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r2 == 0) goto L_0x0023;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0017:
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3 = r3.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3 = org.telegram.messenger.AndroidUtilities.calcAuthKeyHash(r3);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2.key_hash = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0023:
                    r2 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.database;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3 = "UPDATE enc_chats SET data = ?, g = ?, authkey = ?, ttl = ?, layer = ?, seq_in = ?, seq_out = ?, use_count = ?, exchange_id = ?, key_date = ?, fprint = ?, fauthkey = ?, khash = ?, in_seq_no = ?, admin_id = ?, mtproto_seq = ? WHERE uid = ?";	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1 = r2.m12164a(r3);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.getObjectSize();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3.<init>(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r4 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r2 == 0) goto L_0x0151;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0043:
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0048:
                    r4.<init>(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r5 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r2 == 0) goto L_0x0154;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0053:
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0058:
                    r5.<init>(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r2 == 0) goto L_0x0157;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0063:
                    r2 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = r2.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0068:
                    r6.<init>(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r7 == 0) goto L_0x0078;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0073:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0078:
                    r2.<init>(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0.serializeToStream(r3);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 1;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12177a(r0, r3);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r0 == 0) goto L_0x0091;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x008a:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r4.writeBytes(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0091:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r0 == 0) goto L_0x009e;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0097:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r5.writeBytes(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x009e:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r0 == 0) goto L_0x00ab;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00a4:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.writeBytes(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00ab:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r0 == 0) goto L_0x00b8;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00b1:
                    r0 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = r0.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2.writeBytes(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00b8:
                    r0 = 2;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12177a(r0, r4);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12177a(r0, r5);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 4;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.ttl;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 5;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.layer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 6;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.seq_in;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 7;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.seq_out;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 8;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.key_use_count_in;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7 << 16;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.key_use_count_out;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7 | r8;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 9;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r7.exchange_id;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12175a(r0, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 10;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.key_create_date;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 11;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r7.future_key_fingerprint;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12175a(r0, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 12;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12177a(r0, r6);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 13;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12177a(r0, r2);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 14;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.in_seq_no;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 15;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.admin_id;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 16;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.mtproto_seq;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = 17;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.id;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12174a(r0, r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.m12178b();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r4.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r5.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r1 == 0) goto L_0x0150;
                L_0x014d:
                    r1.m12181e();
                L_0x0150:
                    return;
                L_0x0151:
                    r2 = r0;
                    goto L_0x0048;
                L_0x0154:
                    r2 = r0;
                    goto L_0x0058;
                L_0x0157:
                    r2 = r0;
                    goto L_0x0068;
                L_0x015a:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r1 == 0) goto L_0x0150;
                L_0x0160:
                    r1.m12181e();
                    goto L_0x0150;
                L_0x0164:
                    r0 = move-exception;
                    if (r1 == 0) goto L_0x016a;
                L_0x0167:
                    r1.m12181e();
                L_0x016a:
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.63.run():void");
                }
            });
        }
    }

    public void updateEncryptedChatLayer(final EncryptedChat encryptedChat) {
        if (encryptedChat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0026 in list [B:4:0x0023]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                    /*
                    r3 = this;
                    r1 = 0;
                    r0 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r0 = r0.database;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = "UPDATE enc_chats SET layer = ? WHERE uid = ?";	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1 = r0.m12164a(r2);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r0 = 1;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r2.layer;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r0 = 2;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r2.id;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.m12178b();	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x0023:
                    r1.m12181e();
                L_0x0026:
                    return;
                L_0x0027:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x002d:
                    r1.m12181e();
                    goto L_0x0026;
                L_0x0031:
                    r0 = move-exception;
                    if (r1 == 0) goto L_0x0037;
                L_0x0034:
                    r1.m12181e();
                L_0x0037:
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.62.run():void");
                }
            });
        }
    }

    public void updateEncryptedChatSeq(final EncryptedChat encryptedChat, final boolean z) {
        if (encryptedChat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0088 in list [B:6:0x0085]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                    /*
                    r8 = this;
                    r1 = 0;
                    r0 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = r0.database;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = "UPDATE enc_chats SET seq_in = ?, seq_out = ?, use_count = ?, in_seq_no = ?, mtproto_seq = ? WHERE uid = ?";	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1 = r0.m12164a(r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 1;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2.seq_in;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 2;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2.seq_out;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2.key_use_count_in;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2 << 16;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r3 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r3 = r3.key_use_count_out;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2 | r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 4;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2.in_seq_no;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 5;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2.mtproto_seq;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 6;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2.id;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r1.m12178b();	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = r4;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    if (r0 == 0) goto L_0x0083;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                L_0x004c:
                    r0 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = r0.id;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = (long) r0;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = 32;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = r2 << r0;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = r0.database;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r4 = java.util.Locale.US;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r5 = "DELETE FROM messages WHERE mid IN (SELECT m.mid FROM messages as m LEFT JOIN messages_seq as s ON m.mid = s.mid WHERE m.uid = %d AND m.date = 0 AND m.mid < 0 AND s.seq_out <= %d)";	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r6 = 2;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r6 = new java.lang.Object[r6];	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r7 = 0;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r6[r7] = r2;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = 1;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r3 = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r3 = r3.in_seq_no;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r6[r2] = r3;	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r2 = java.lang.String.format(r4, r5, r6);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = r0.m12164a(r2);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0 = r0.m12179c();	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    r0.m12181e();	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                L_0x0083:
                    if (r1 == 0) goto L_0x0088;
                L_0x0085:
                    r1.m12181e();
                L_0x0088:
                    return;
                L_0x0089:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0089, all -> 0x0093 }
                    if (r1 == 0) goto L_0x0088;
                L_0x008f:
                    r1.m12181e();
                    goto L_0x0088;
                L_0x0093:
                    r0 = move-exception;
                    if (r1 == 0) goto L_0x0099;
                L_0x0096:
                    r1.m12181e();
                L_0x0099:
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.60.run():void");
                }
            });
        }
    }

    public void updateEncryptedChatTTL(final EncryptedChat encryptedChat) {
        if (encryptedChat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0026 in list [B:4:0x0023]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                    /*
                    r3 = this;
                    r1 = 0;
                    r0 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r0 = r0.database;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = "UPDATE enc_chats SET ttl = ? WHERE uid = ?";	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1 = r0.m12164a(r2);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r0 = 1;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r2.ttl;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r0 = 2;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r2.id;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.m12174a(r0, r2);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.m12178b();	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x0023:
                    r1.m12181e();
                L_0x0026:
                    return;
                L_0x0027:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x002d:
                    r1.m12181e();
                    goto L_0x0026;
                L_0x0031:
                    r0 = move-exception;
                    if (r1 == 0) goto L_0x0037;
                L_0x0034:
                    r1.m12181e();
                L_0x0037:
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.61.run():void");
                }
            });
        }
    }

    public long[] updateMessageStateAndId(long j, Integer num, int i, int i2, boolean z, int i3) {
        if (!z) {
            return updateMessageStateAndIdInternal(j, num, i, i2, i3);
        }
        final long j2 = j;
        final Integer num2 = num;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.updateMessageStateAndIdInternal(j2, num2, i4, i5, i6);
            }
        });
        return null;
    }

    public void updateUsers(final ArrayList<User> arrayList, final boolean z, final boolean z2, boolean z3) {
        if (!arrayList.isEmpty()) {
            if (z3) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.updateUsersInternal(arrayList, z, z2);
                    }
                });
            } else {
                updateUsersInternal(arrayList, z, z2);
            }
        }
    }
}
