package org.telegram.messenger.query;

import android.text.TextUtils;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterDocument;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterMusic;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterPhotoVideo;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterUrl;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterVoice;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageEntityEmail;
import org.telegram.tgnet.TLRPC$TL_messageEntityTextUrl;
import org.telegram.tgnet.TLRPC$TL_messageEntityUrl;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_message_secret;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;

public class SharedMediaQuery {
    public static final int MEDIA_AUDIO = 2;
    public static final int MEDIA_FILE = 1;
    public static final int MEDIA_MUSIC = 4;
    public static final int MEDIA_PHOTOVIDEO = 0;
    public static final int MEDIA_TYPES_COUNT = 5;
    public static final int MEDIA_URL = 3;

    public static void loadMedia(long uid, int count, int max_id, int type, boolean fromCache, int classGuid) {
        boolean isChannel = ((int) uid) < 0 && ChatObject.isChannel(-((int) uid));
        int lower_part = (int) uid;
        if (fromCache || lower_part == 0) {
            loadMediaDatabase(uid, count, max_id, type, classGuid, isChannel);
            return;
        }
        TLObject req = new TLRPC$TL_messages_search();
        req.limit = count + 1;
        req.offset_id = max_id;
        if (type == 0) {
            req.filter = new TLRPC$TL_inputMessagesFilterPhotoVideo();
        } else if (type == 1) {
            req.filter = new TLRPC$TL_inputMessagesFilterDocument();
        } else if (type == 2) {
            req.filter = new TLRPC$TL_inputMessagesFilterVoice();
        } else if (type == 3) {
            req.filter = new TLRPC$TL_inputMessagesFilterUrl();
        } else if (type == 4) {
            req.filter = new TLRPC$TL_inputMessagesFilterMusic();
        }
        req.f87q = "";
        req.peer = MessagesController.getInputPeer(lower_part);
        if (req.peer != null) {
            final int i = count;
            final long j = uid;
            final int i2 = max_id;
            final int i3 = type;
            final int i4 = classGuid;
            final boolean z = isChannel;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(TLObject response, TLRPC$TL_error error) {
                    if (error == null) {
                        boolean topReached;
                        TLRPC$messages_Messages res = (TLRPC$messages_Messages) response;
                        if (res.messages.size() > i) {
                            topReached = false;
                            res.messages.remove(res.messages.size() - 1);
                        } else {
                            topReached = true;
                        }
                        SharedMediaQuery.processLoadedMedia(res, j, i, i2, i3, false, i4, z, topReached);
                    }
                }
            }), classGuid);
        }
    }

    public static void getMediaCount(final long uid, final int type, final int classGuid, boolean fromCache) {
        int lower_part = (int) uid;
        if (fromCache || lower_part == 0) {
            getMediaCountDatabase(uid, type, classGuid);
            return;
        }
        TLRPC$TL_messages_search req = new TLRPC$TL_messages_search();
        req.limit = 1;
        req.offset_id = 0;
        if (type == 0) {
            req.filter = new TLRPC$TL_inputMessagesFilterPhotoVideo();
        } else if (type == 1) {
            req.filter = new TLRPC$TL_inputMessagesFilterDocument();
        } else if (type == 2) {
            req.filter = new TLRPC$TL_inputMessagesFilterVoice();
        } else if (type == 3) {
            req.filter = new TLRPC$TL_inputMessagesFilterUrl();
        } else if (type == 4) {
            req.filter = new TLRPC$TL_inputMessagesFilterMusic();
        }
        req.f87q = "";
        req.peer = MessagesController.getInputPeer(lower_part);
        if (req.peer != null) {
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(TLObject response, TLRPC$TL_error error) {
                    if (error == null) {
                        int count;
                        final TLRPC$messages_Messages res = (TLRPC$messages_Messages) response;
                        MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
                        if (res instanceof TLRPC$TL_messages_messages) {
                            count = res.messages.size();
                        } else {
                            count = res.count;
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                MessagesController.getInstance().putUsers(res.users, false);
                                MessagesController.getInstance().putChats(res.chats, false);
                            }
                        });
                        SharedMediaQuery.processLoadedMediaCount(count, uid, type, classGuid, false);
                    }
                }
            }), classGuid);
        }
    }

    public static int getMediaType(TLRPC$Message message) {
        if (message == null) {
            return -1;
        }
        if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
            return 0;
        }
        if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            if (MessageObject.isVoiceMessage(message) || MessageObject.isRoundVideoMessage(message)) {
                return 2;
            }
            if (MessageObject.isVideoMessage(message)) {
                return 0;
            }
            if (MessageObject.isStickerMessage(message)) {
                return -1;
            }
            if (MessageObject.isMusicMessage(message)) {
                return 4;
            }
            return 1;
        } else if (message.entities.isEmpty()) {
            return -1;
        } else {
            for (int a = 0; a < message.entities.size(); a++) {
                TLRPC$MessageEntity entity = (TLRPC$MessageEntity) message.entities.get(a);
                if ((entity instanceof TLRPC$TL_messageEntityUrl) || (entity instanceof TLRPC$TL_messageEntityTextUrl) || (entity instanceof TLRPC$TL_messageEntityEmail)) {
                    return 3;
                }
            }
            return -1;
        }
    }

    public static boolean canAddMessageToMedia(TLRPC$Message message) {
        if ((message instanceof TLRPC$TL_message) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)) && message.media.ttl_seconds != 0)) {
            return false;
        }
        if ((message instanceof TLRPC$TL_message_secret) && (message.media instanceof TLRPC$TL_messageMediaPhoto) && message.ttl != 0 && message.ttl <= 60) {
            return false;
        }
        if ((message.media instanceof TLRPC$TL_messageMediaPhoto) || ((message.media instanceof TLRPC$TL_messageMediaDocument) && !MessageObject.isGifDocument(message.media.document))) {
            return true;
        }
        if (message.entities.isEmpty()) {
            return false;
        }
        for (int a = 0; a < message.entities.size(); a++) {
            TLRPC$MessageEntity entity = (TLRPC$MessageEntity) message.entities.get(a);
            if ((entity instanceof TLRPC$TL_messageEntityUrl) || (entity instanceof TLRPC$TL_messageEntityTextUrl) || (entity instanceof TLRPC$TL_messageEntityEmail)) {
                return true;
            }
        }
        return false;
    }

    private static void processLoadedMedia(TLRPC$messages_Messages res, long uid, int count, int max_id, int type, boolean fromCache, int classGuid, boolean isChannel, boolean topReached) {
        int lower_part = (int) uid;
        if (fromCache && res.messages.isEmpty() && lower_part != 0) {
            loadMedia(uid, count, max_id, type, false, classGuid);
            return;
        }
        int a;
        if (!fromCache) {
            ImageLoader.saveMessagesThumbs(res.messages);
            MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
            putMediaDatabase(uid, type, res.messages, max_id, topReached);
        }
        AbstractMap usersDict = new HashMap();
        for (a = 0; a < res.users.size(); a++) {
            User u = (User) res.users.get(a);
            usersDict.put(Integer.valueOf(u.id), u);
        }
        final ArrayList<MessageObject> objects = new ArrayList();
        for (a = 0; a < res.messages.size(); a++) {
            objects.add(new MessageObject((TLRPC$Message) res.messages.get(a), usersDict, true));
        }
        final TLRPC$messages_Messages tLRPC$messages_Messages = res;
        final boolean z = fromCache;
        final long j = uid;
        final int i = classGuid;
        final int i2 = type;
        final boolean z2 = topReached;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int totalCount = tLRPC$messages_Messages.count;
                MessagesController.getInstance().putUsers(tLRPC$messages_Messages.users, z);
                MessagesController.getInstance().putChats(tLRPC$messages_Messages.chats, z);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.mediaDidLoaded, new Object[]{Long.valueOf(j), Integer.valueOf(totalCount), objects, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z2)});
            }
        });
    }

    private static void processLoadedMediaCount(int count, long uid, int type, int classGuid, boolean fromCache) {
        final long j = uid;
        final boolean z = fromCache;
        final int i = count;
        final int i2 = type;
        final int i3 = classGuid;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int i = 0;
                int lower_part = (int) j;
                if (z && i == -1 && lower_part != 0) {
                    SharedMediaQuery.getMediaCount(j, i2, i3, false);
                    return;
                }
                if (!z) {
                    SharedMediaQuery.putMediaCountDatabase(j, i2, i);
                }
                NotificationCenter instance = NotificationCenter.getInstance();
                int i2 = NotificationCenter.mediaCountDidLoaded;
                Object[] objArr = new Object[4];
                objArr[0] = Long.valueOf(j);
                if (!(z && i == -1)) {
                    i = i;
                }
                objArr[1] = Integer.valueOf(i);
                objArr[2] = Boolean.valueOf(z);
                objArr[3] = Integer.valueOf(i2);
                instance.postNotificationName(i2, objArr);
            }
        });
    }

    private static void putMediaCountDatabase(final long uid, final int type, final int count) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state2 = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO media_counts_v2 VALUES(?, ?, ?)");
                    state2.requery();
                    state2.bindLong(1, uid);
                    state2.bindInteger(2, type);
                    state2.bindInteger(3, count);
                    state2.step();
                    state2.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private static void getMediaCountDatabase(final long uid, final int type, final int classGuid) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                int count = -1;
                try {
                    SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT count FROM media_counts_v2 WHERE uid = %d AND type = %d LIMIT 1", new Object[]{Long.valueOf(uid), Integer.valueOf(type)}), new Object[0]);
                    if (cursor.next()) {
                        count = cursor.intValue(0);
                    }
                    cursor.dispose();
                    int lower_part = (int) uid;
                    if (count == -1 && lower_part == 0) {
                        cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM media_v2 WHERE uid = %d AND type = %d LIMIT 1", new Object[]{Long.valueOf(uid), Integer.valueOf(type)}), new Object[0]);
                        if (cursor.next()) {
                            count = cursor.intValue(0);
                        }
                        cursor.dispose();
                        if (count != -1) {
                            SharedMediaQuery.putMediaCountDatabase(uid, type, count);
                        }
                    }
                    SharedMediaQuery.processLoadedMediaCount(count, uid, type, classGuid, true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private static void loadMediaDatabase(long uid, int count, int max_id, int type, int classGuid, boolean isChannel) {
        final int i = count;
        final long j = uid;
        final int i2 = max_id;
        final boolean z = isChannel;
        final int i3 = type;
        final int i4 = classGuid;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                TLRPC$TL_messages_messages res = new TLRPC$TL_messages_messages();
                try {
                    SQLiteCursor cursor;
                    boolean topReached;
                    ArrayList<Integer> usersToLoad = new ArrayList();
                    ArrayList<Integer> chatsToLoad = new ArrayList();
                    int countToLoad = i + 1;
                    SQLiteDatabase database = MessagesStorage.getInstance().getDatabase();
                    boolean isEnd = false;
                    if (((int) j) != 0) {
                        int channelId = 0;
                        long messageMaxId = (long) i2;
                        if (z) {
                            channelId = -((int) j);
                        }
                        if (!(messageMaxId == 0 || channelId == 0)) {
                            messageMaxId |= ((long) channelId) << 32;
                        }
                        cursor = database.queryFinalized(String.format(Locale.US, "SELECT start FROM media_holes_v2 WHERE uid = %d AND type = %d AND start IN (0, 1)", new Object[]{Long.valueOf(j), Integer.valueOf(i3)}), new Object[0]);
                        if (cursor.next()) {
                            isEnd = cursor.intValue(0) == 1;
                            cursor.dispose();
                        } else {
                            cursor.dispose();
                            cursor = database.queryFinalized(String.format(Locale.US, "SELECT min(mid) FROM media_v2 WHERE uid = %d AND type = %d AND mid > 0", new Object[]{Long.valueOf(j), Integer.valueOf(i3)}), new Object[0]);
                            if (cursor.next()) {
                                int mid = cursor.intValue(0);
                                if (mid != 0) {
                                    SQLitePreparedStatement state = database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                                    state.requery();
                                    state.bindLong(1, j);
                                    state.bindInteger(2, i3);
                                    state.bindInteger(3, 0);
                                    state.bindInteger(4, mid);
                                    state.step();
                                    state.dispose();
                                }
                            }
                            cursor.dispose();
                        }
                        long holeMessageId;
                        if (messageMaxId != 0) {
                            holeMessageId = 0;
                            cursor = database.queryFinalized(String.format(Locale.US, "SELECT end FROM media_holes_v2 WHERE uid = %d AND type = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(i3), Integer.valueOf(i2)}), new Object[0]);
                            if (cursor.next()) {
                                holeMessageId = (long) cursor.intValue(0);
                                if (channelId != 0) {
                                    holeMessageId |= ((long) channelId) << 32;
                                }
                            }
                            cursor.dispose();
                            if (holeMessageId > 1) {
                                cursor = database.queryFinalized(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > 0 AND mid < %d AND mid >= %d AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Long.valueOf(messageMaxId), Long.valueOf(holeMessageId), Integer.valueOf(i3), Integer.valueOf(countToLoad)}), new Object[0]);
                            } else {
                                cursor = database.queryFinalized(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > 0 AND mid < %d AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Long.valueOf(messageMaxId), Integer.valueOf(i3), Integer.valueOf(countToLoad)}), new Object[0]);
                            }
                        } else {
                            holeMessageId = 0;
                            cursor = database.queryFinalized(String.format(Locale.US, "SELECT max(end) FROM media_holes_v2 WHERE uid = %d AND type = %d", new Object[]{Long.valueOf(j), Integer.valueOf(i3)}), new Object[0]);
                            if (cursor.next()) {
                                holeMessageId = (long) cursor.intValue(0);
                                if (channelId != 0) {
                                    holeMessageId |= ((long) channelId) << 32;
                                }
                            }
                            cursor.dispose();
                            if (holeMessageId > 1) {
                                cursor = database.queryFinalized(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid >= %d AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Long.valueOf(holeMessageId), Integer.valueOf(i3), Integer.valueOf(countToLoad)}), new Object[0]);
                            } else {
                                cursor = database.queryFinalized(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > 0 AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i3), Integer.valueOf(countToLoad)}), new Object[0]);
                            }
                        }
                    } else {
                        isEnd = true;
                        if (i2 != 0) {
                            cursor = database.queryFinalized(String.format(Locale.US, "SELECT m.data, m.mid, r.random_id FROM media_v2 as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d AND type = %d ORDER BY m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(countToLoad)}), new Object[0]);
                        } else {
                            cursor = database.queryFinalized(String.format(Locale.US, "SELECT m.data, m.mid, r.random_id FROM media_v2 as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND type = %d ORDER BY m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j), Integer.valueOf(i3), Integer.valueOf(countToLoad)}), new Object[0]);
                        }
                    }
                    while (cursor.next()) {
                        AbstractSerializedData data = cursor.byteBufferValue(0);
                        if (data != null) {
                            TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            message.id = cursor.intValue(1);
                            message.dialog_id = j;
                            if (((int) j) == 0) {
                                message.random_id = cursor.longValue(2);
                            }
                            res.messages.add(message);
                            if (message.from_id > 0) {
                                if (!usersToLoad.contains(Integer.valueOf(message.from_id))) {
                                    usersToLoad.add(Integer.valueOf(message.from_id));
                                }
                            } else if (!chatsToLoad.contains(Integer.valueOf(-message.from_id))) {
                                chatsToLoad.add(Integer.valueOf(-message.from_id));
                            }
                        }
                    }
                    cursor.dispose();
                    if (!usersToLoad.isEmpty()) {
                        MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), res.users);
                    }
                    if (!chatsToLoad.isEmpty()) {
                        MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), res.chats);
                    }
                    if (res.messages.size() > i) {
                        topReached = false;
                        res.messages.remove(res.messages.size() - 1);
                    } else {
                        topReached = isEnd;
                    }
                    SharedMediaQuery.processLoadedMedia(res, j, i, i2, i3, true, i4, z, topReached);
                } catch (Exception e) {
                    res.messages.clear();
                    res.chats.clear();
                    res.users.clear();
                    FileLog.e(e);
                    SharedMediaQuery.processLoadedMedia(res, j, i, i2, i3, true, i4, z, false);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    SharedMediaQuery.processLoadedMedia(res, j, i, i2, i3, true, i4, z, false);
                }
            }
        });
    }

    private static void putMediaDatabase(long uid, int type, ArrayList<TLRPC$Message> messages, int max_id, boolean topReached) {
        final ArrayList<TLRPC$Message> arrayList = messages;
        final boolean z = topReached;
        final long j = uid;
        final int i = max_id;
        final int i2 = type;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                int minId = 1;
                try {
                    if (arrayList.isEmpty() || z) {
                        MessagesStorage.getInstance().doneHolesInMedia(j, i, i2);
                        if (arrayList.isEmpty()) {
                            return;
                        }
                    }
                    MessagesStorage.getInstance().getDatabase().beginTransaction();
                    SQLitePreparedStatement state2 = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        TLRPC$Message message = (TLRPC$Message) it.next();
                        if (SharedMediaQuery.canAddMessageToMedia(message)) {
                            long messageId = (long) message.id;
                            if (message.to_id.channel_id != 0) {
                                messageId |= ((long) message.to_id.channel_id) << 32;
                            }
                            state2.requery();
                            NativeByteBuffer data = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(data);
                            state2.bindLong(1, messageId);
                            state2.bindLong(2, j);
                            state2.bindInteger(3, message.date);
                            state2.bindInteger(4, i2);
                            state2.bindByteBuffer(5, data);
                            state2.step();
                            data.reuse();
                        }
                    }
                    state2.dispose();
                    if (!(z && i == 0)) {
                        if (!z) {
                            minId = ((TLRPC$Message) arrayList.get(arrayList.size() - 1)).id;
                        }
                        if (i != 0) {
                            MessagesStorage.getInstance().closeHolesInMedia(j, minId, i, i2);
                        } else {
                            MessagesStorage.getInstance().closeHolesInMedia(j, minId, Integer.MAX_VALUE, i2);
                        }
                    }
                    MessagesStorage.getInstance().getDatabase().commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public static void loadMusic(final long uid, final long max_id) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                final ArrayList<MessageObject> arrayList = new ArrayList();
                try {
                    SQLiteCursor cursor;
                    if (((int) uid) != 0) {
                        cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid < %d AND type = %d ORDER BY date DESC, mid DESC LIMIT 1000", new Object[]{Long.valueOf(uid), Long.valueOf(max_id), Integer.valueOf(4)}), new Object[0]);
                    } else {
                        cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > %d AND type = %d ORDER BY date DESC, mid DESC LIMIT 1000", new Object[]{Long.valueOf(uid), Long.valueOf(max_id), Integer.valueOf(4)}), new Object[0]);
                    }
                    while (cursor.next()) {
                        NativeByteBuffer data = cursor.byteBufferValue(0);
                        if (data != null) {
                            TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                            data.reuse();
                            if (MessageObject.isMusicMessage(message)) {
                                message.id = cursor.intValue(1);
                                message.dialog_id = uid;
                                arrayList.add(0, new MessageObject(message, null, false));
                            }
                        }
                    }
                    cursor.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.musicDidLoaded, new Object[]{Long.valueOf(uid), arrayList});
                    }
                });
            }
        });
    }
}
