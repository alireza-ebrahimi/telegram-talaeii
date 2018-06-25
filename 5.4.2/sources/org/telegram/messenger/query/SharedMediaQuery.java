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
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.User;

public class SharedMediaQuery {
    public static final int MEDIA_AUDIO = 2;
    public static final int MEDIA_FILE = 1;
    public static final int MEDIA_MUSIC = 4;
    public static final int MEDIA_PHOTOVIDEO = 0;
    public static final int MEDIA_TYPES_COUNT = 5;
    public static final int MEDIA_URL = 3;

    public static boolean canAddMessageToMedia(Message message) {
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
        for (int i = 0; i < message.entities.size(); i++) {
            MessageEntity messageEntity = (MessageEntity) message.entities.get(i);
            if ((messageEntity instanceof TLRPC$TL_messageEntityUrl) || (messageEntity instanceof TLRPC$TL_messageEntityTextUrl) || (messageEntity instanceof TLRPC$TL_messageEntityEmail)) {
                return true;
            }
        }
        return false;
    }

    public static void getMediaCount(final long j, final int i, final int i2, boolean z) {
        int i3 = (int) j;
        if (z || i3 == 0) {
            getMediaCountDatabase(j, i, i2);
            return;
        }
        TLObject tLRPC$TL_messages_search = new TLRPC$TL_messages_search();
        tLRPC$TL_messages_search.limit = 1;
        tLRPC$TL_messages_search.offset_id = 0;
        if (i == 0) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterPhotoVideo();
        } else if (i == 1) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterDocument();
        } else if (i == 2) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterVoice();
        } else if (i == 3) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterUrl();
        } else if (i == 4) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterMusic();
        }
        tLRPC$TL_messages_search.f10166q = TtmlNode.ANONYMOUS_REGION_ID;
        tLRPC$TL_messages_search.peer = MessagesController.getInputPeer(i3);
        if (tLRPC$TL_messages_search.peer != null) {
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_search, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        final TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                        MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                        int size = tLRPC$messages_Messages instanceof TLRPC$TL_messages_messages ? tLRPC$messages_Messages.messages.size() : tLRPC$messages_Messages.count;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                MessagesController.getInstance().putUsers(tLRPC$messages_Messages.users, false);
                                MessagesController.getInstance().putChats(tLRPC$messages_Messages.chats, false);
                            }
                        });
                        SharedMediaQuery.processLoadedMediaCount(size, j, i, i2, false);
                    }
                }
            }), i2);
        }
    }

    private static void getMediaCountDatabase(final long j, final int i, final int i2) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT count FROM media_counts_v2 WHERE uid = %d AND type = %d LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(i)}), new Object[0]);
                    int b2 = b.m12152a() ? b.m12154b(0) : -1;
                    b.m12155b();
                    int i = (int) j;
                    if (b2 == -1 && i == 0) {
                        b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT COUNT(mid) FROM media_v2 WHERE uid = %d AND type = %d LIMIT 1", new Object[]{Long.valueOf(j), Integer.valueOf(i)}), new Object[0]);
                        if (b.m12152a()) {
                            b2 = b.m12154b(0);
                        }
                        b.m12155b();
                        if (b2 != -1) {
                            SharedMediaQuery.putMediaCountDatabase(j, i, b2);
                        }
                    }
                    SharedMediaQuery.processLoadedMediaCount(b2, j, i, i2, true);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static int getMediaType(Message message) {
        if (message == null) {
            return -1;
        }
        if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
            return 0;
        }
        if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            return (MessageObject.isVoiceMessage(message) || MessageObject.isRoundVideoMessage(message)) ? 2 : !MessageObject.isVideoMessage(message) ? MessageObject.isStickerMessage(message) ? -1 : MessageObject.isMusicMessage(message) ? 4 : 1 : 0;
        } else {
            if (!message.entities.isEmpty()) {
                for (int i = 0; i < message.entities.size(); i++) {
                    MessageEntity messageEntity = (MessageEntity) message.entities.get(i);
                    if ((messageEntity instanceof TLRPC$TL_messageEntityUrl) || (messageEntity instanceof TLRPC$TL_messageEntityTextUrl) || (messageEntity instanceof TLRPC$TL_messageEntityEmail)) {
                        return 3;
                    }
                }
            }
            return -1;
        }
    }

    public static void loadMedia(long j, int i, int i2, int i3, boolean z, int i4) {
        boolean z2 = ((int) j) < 0 && ChatObject.isChannel(-((int) j));
        int i5 = (int) j;
        if (z || i5 == 0) {
            loadMediaDatabase(j, i, i2, i3, i4, z2);
            return;
        }
        TLObject tLRPC$TL_messages_search = new TLRPC$TL_messages_search();
        tLRPC$TL_messages_search.limit = i + 1;
        tLRPC$TL_messages_search.offset_id = i2;
        if (i3 == 0) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterPhotoVideo();
        } else if (i3 == 1) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterDocument();
        } else if (i3 == 2) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterVoice();
        } else if (i3 == 3) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterUrl();
        } else if (i3 == 4) {
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterMusic();
        }
        tLRPC$TL_messages_search.f10166q = TtmlNode.ANONYMOUS_REGION_ID;
        tLRPC$TL_messages_search.peer = MessagesController.getInputPeer(i5);
        if (tLRPC$TL_messages_search.peer != null) {
            final int i6 = i;
            final long j2 = j;
            final int i7 = i2;
            final int i8 = i3;
            final int i9 = i4;
            final boolean z3 = z2;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_search, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        boolean z;
                        TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                        if (tLRPC$messages_Messages.messages.size() > i6) {
                            tLRPC$messages_Messages.messages.remove(tLRPC$messages_Messages.messages.size() - 1);
                            z = false;
                        } else {
                            z = true;
                        }
                        SharedMediaQuery.processLoadedMedia(tLRPC$messages_Messages, j2, i6, i7, i8, false, i9, z3, z);
                    }
                }
            }), i4);
        }
    }

    private static void loadMediaDatabase(long j, int i, int i2, int i3, int i4, boolean z) {
        final int i5 = i;
        final long j2 = j;
        final int i6 = i2;
        final boolean z2 = z;
        final int i7 = i3;
        final int i8 = i4;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                boolean z;
                Throwable e;
                TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                try {
                    SQLiteCursor b;
                    Iterable arrayList = new ArrayList();
                    Iterable arrayList2 = new ArrayList();
                    int i = i5 + 1;
                    SQLiteDatabase database = MessagesStorage.getInstance().getDatabase();
                    z = false;
                    if (((int) j2) != 0) {
                        int i2 = 0;
                        long j = (long) i6;
                        if (z2) {
                            i2 = -((int) j2);
                        }
                        long j2 = (j == 0 || i2 == 0) ? j : j | (((long) i2) << 32);
                        SQLiteCursor b2 = database.m12165b(String.format(Locale.US, "SELECT start FROM media_holes_v2 WHERE uid = %d AND type = %d AND start IN (0, 1)", new Object[]{Long.valueOf(j2), Integer.valueOf(i7)}), new Object[0]);
                        if (b2.m12152a()) {
                            z = b2.m12154b(0) == 1;
                            b2.m12155b();
                        } else {
                            b2.m12155b();
                            b2 = database.m12165b(String.format(Locale.US, "SELECT min(mid) FROM media_v2 WHERE uid = %d AND type = %d AND mid > 0", new Object[]{Long.valueOf(j2), Integer.valueOf(i7)}), new Object[0]);
                            if (b2.m12152a()) {
                                int b3 = b2.m12154b(0);
                                if (b3 != 0) {
                                    SQLitePreparedStatement a = database.m12164a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                                    a.m12180d();
                                    a.m12175a(1, j2);
                                    a.m12174a(2, i7);
                                    a.m12174a(3, 0);
                                    a.m12174a(4, b3);
                                    a.m12178b();
                                    a.m12181e();
                                }
                            }
                            b2.m12155b();
                        }
                        if (j2 != 0) {
                            j = 0;
                            SQLiteCursor b4 = database.m12165b(String.format(Locale.US, "SELECT end FROM media_holes_v2 WHERE uid = %d AND type = %d AND end <= %d ORDER BY end DESC LIMIT 1", new Object[]{Long.valueOf(j2), Integer.valueOf(i7), Integer.valueOf(i6)}), new Object[0]);
                            if (b4.m12152a()) {
                                j = (long) b4.m12154b(0);
                                if (i2 != 0) {
                                    j |= ((long) i2) << 32;
                                }
                            }
                            b4.m12155b();
                            b = j > 1 ? database.m12165b(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > 0 AND mid < %d AND mid >= %d AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j2), Long.valueOf(j2), Long.valueOf(j), Integer.valueOf(i7), Integer.valueOf(i)}), new Object[0]) : database.m12165b(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > 0 AND mid < %d AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j2), Long.valueOf(j2), Integer.valueOf(i7), Integer.valueOf(i)}), new Object[0]);
                        } else {
                            j = 0;
                            SQLiteCursor b5 = database.m12165b(String.format(Locale.US, "SELECT max(end) FROM media_holes_v2 WHERE uid = %d AND type = %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i7)}), new Object[0]);
                            if (b5.m12152a()) {
                                j = (long) b5.m12154b(0);
                                if (i2 != 0) {
                                    j |= ((long) i2) << 32;
                                }
                            }
                            b5.m12155b();
                            b = j > 1 ? database.m12165b(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid >= %d AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j2), Long.valueOf(j), Integer.valueOf(i7), Integer.valueOf(i)}), new Object[0]) : database.m12165b(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > 0 AND type = %d ORDER BY date DESC, mid DESC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i7), Integer.valueOf(i)}), new Object[0]);
                        }
                    } else {
                        z = true;
                        b = i6 != 0 ? database.m12165b(String.format(Locale.US, "SELECT m.data, m.mid, r.random_id FROM media_v2 as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND m.mid > %d AND type = %d ORDER BY m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i)}), new Object[0]) : database.m12165b(String.format(Locale.US, "SELECT m.data, m.mid, r.random_id FROM media_v2 as m LEFT JOIN randoms as r ON r.mid = m.mid WHERE m.uid = %d AND type = %d ORDER BY m.mid ASC LIMIT %d", new Object[]{Long.valueOf(j2), Integer.valueOf(i7), Integer.valueOf(i)}), new Object[0]);
                    }
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            TLdeserialize.id = b.m12154b(1);
                            TLdeserialize.dialog_id = j2;
                            if (((int) j2) == 0) {
                                TLdeserialize.random_id = b.m12158d(2);
                            }
                            tLRPC$TL_messages_messages.messages.add(TLdeserialize);
                            if (TLdeserialize.from_id > 0) {
                                if (!arrayList.contains(Integer.valueOf(TLdeserialize.from_id))) {
                                    arrayList.add(Integer.valueOf(TLdeserialize.from_id));
                                }
                            } else if (!arrayList2.contains(Integer.valueOf(-TLdeserialize.from_id))) {
                                arrayList2.add(Integer.valueOf(-TLdeserialize.from_id));
                            }
                        }
                    }
                    b.m12155b();
                    if (!arrayList.isEmpty()) {
                        MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList), tLRPC$TL_messages_messages.users);
                    }
                    if (!arrayList2.isEmpty()) {
                        MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList2), tLRPC$TL_messages_messages.chats);
                    }
                    if (tLRPC$TL_messages_messages.messages.size() > i5) {
                        z = false;
                        try {
                            tLRPC$TL_messages_messages.messages.remove(tLRPC$TL_messages_messages.messages.size() - 1);
                        } catch (Exception e2) {
                            e = e2;
                            try {
                                tLRPC$TL_messages_messages.messages.clear();
                                tLRPC$TL_messages_messages.chats.clear();
                                tLRPC$TL_messages_messages.users.clear();
                                FileLog.m13728e(e);
                                SharedMediaQuery.processLoadedMedia(tLRPC$TL_messages_messages, j2, i5, i6, i7, true, i8, z2, z);
                            } catch (Throwable th) {
                                e = th;
                                SharedMediaQuery.processLoadedMedia(tLRPC$TL_messages_messages, j2, i5, i6, i7, true, i8, z2, z);
                                throw e;
                            }
                        }
                    }
                    SharedMediaQuery.processLoadedMedia(tLRPC$TL_messages_messages, j2, i5, i6, i7, true, i8, z2, z);
                } catch (Exception e3) {
                    e = e3;
                    z = false;
                    tLRPC$TL_messages_messages.messages.clear();
                    tLRPC$TL_messages_messages.chats.clear();
                    tLRPC$TL_messages_messages.users.clear();
                    FileLog.m13728e(e);
                    SharedMediaQuery.processLoadedMedia(tLRPC$TL_messages_messages, j2, i5, i6, i7, true, i8, z2, z);
                } catch (Throwable th2) {
                    e = th2;
                    z = false;
                    SharedMediaQuery.processLoadedMedia(tLRPC$TL_messages_messages, j2, i5, i6, i7, true, i8, z2, z);
                    throw e;
                }
            }
        });
    }

    public static void loadMusic(final long j, final long j2) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                final ArrayList arrayList = new ArrayList();
                try {
                    SQLiteCursor b;
                    if (((int) j) != 0) {
                        b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid < %d AND type = %d ORDER BY date DESC, mid DESC LIMIT 1000", new Object[]{Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(4)}), new Object[0]);
                    } else {
                        b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data, mid FROM media_v2 WHERE uid = %d AND mid > %d AND type = %d ORDER BY date DESC, mid DESC LIMIT 1000", new Object[]{Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(4)}), new Object[0]);
                    }
                    while (b.m12152a()) {
                        AbstractSerializedData g = b.m12161g(0);
                        if (g != null) {
                            Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                            g.reuse();
                            if (MessageObject.isMusicMessage(TLdeserialize)) {
                                TLdeserialize.id = b.m12154b(1);
                                TLdeserialize.dialog_id = j;
                                arrayList.add(0, new MessageObject(TLdeserialize, null, false));
                            }
                        }
                    }
                    b.m12155b();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.musicDidLoaded, Long.valueOf(j), arrayList);
                    }
                });
            }
        });
    }

    private static void processLoadedMedia(TLRPC$messages_Messages tLRPC$messages_Messages, long j, int i, int i2, int i3, boolean z, int i4, boolean z2, boolean z3) {
        int i5 = (int) j;
        if (z && tLRPC$messages_Messages.messages.isEmpty() && i5 != 0) {
            loadMedia(j, i, i2, i3, false, i4);
            return;
        }
        int i6;
        if (!z) {
            ImageLoader.saveMessagesThumbs(tLRPC$messages_Messages.messages);
            MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
            putMediaDatabase(j, i3, tLRPC$messages_Messages.messages, i2, z3);
        }
        AbstractMap hashMap = new HashMap();
        for (i6 = 0; i6 < tLRPC$messages_Messages.users.size(); i6++) {
            User user = (User) tLRPC$messages_Messages.users.get(i6);
            hashMap.put(Integer.valueOf(user.id), user);
        }
        final ArrayList arrayList = new ArrayList();
        for (i6 = 0; i6 < tLRPC$messages_Messages.messages.size(); i6++) {
            arrayList.add(new MessageObject((Message) tLRPC$messages_Messages.messages.get(i6), hashMap, true));
        }
        final TLRPC$messages_Messages tLRPC$messages_Messages2 = tLRPC$messages_Messages;
        final boolean z4 = z;
        final long j2 = j;
        final int i7 = i4;
        final int i8 = i3;
        final boolean z5 = z3;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int i = tLRPC$messages_Messages2.count;
                MessagesController.getInstance().putUsers(tLRPC$messages_Messages2.users, z4);
                MessagesController.getInstance().putChats(tLRPC$messages_Messages2.chats, z4);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.mediaDidLoaded, Long.valueOf(j2), Integer.valueOf(i), arrayList, Integer.valueOf(i7), Integer.valueOf(i8), Boolean.valueOf(z5));
            }
        });
    }

    private static void processLoadedMediaCount(int i, long j, int i2, int i3, boolean z) {
        final long j2 = j;
        final boolean z2 = z;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int i = 0;
                int i2 = (int) j2;
                if (z2 && i4 == -1 && i2 != 0) {
                    SharedMediaQuery.getMediaCount(j2, i5, i6, false);
                    return;
                }
                if (!z2) {
                    SharedMediaQuery.putMediaCountDatabase(j2, i5, i4);
                }
                NotificationCenter instance = NotificationCenter.getInstance();
                int i3 = NotificationCenter.mediaCountDidLoaded;
                Object[] objArr = new Object[4];
                objArr[0] = Long.valueOf(j2);
                if (!(z2 && i4 == -1)) {
                    i = i4;
                }
                objArr[1] = Integer.valueOf(i);
                objArr[2] = Boolean.valueOf(z2);
                objArr[3] = Integer.valueOf(i5);
                instance.postNotificationName(i3, objArr);
            }
        });
    }

    private static void putMediaCountDatabase(final long j, final int i, final int i2) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO media_counts_v2 VALUES(?, ?, ?)");
                    a.m12180d();
                    a.m12175a(1, j);
                    a.m12174a(2, i);
                    a.m12174a(3, i2);
                    a.m12178b();
                    a.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    private static void putMediaDatabase(long j, int i, ArrayList<Message> arrayList, int i2, boolean z) {
        final ArrayList<Message> arrayList2 = arrayList;
        final boolean z2 = z;
        final long j2 = j;
        final int i3 = i2;
        final int i4 = i;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                int i = 1;
                try {
                    if (arrayList2.isEmpty() || z2) {
                        MessagesStorage.getInstance().doneHolesInMedia(j2, i3, i4);
                        if (arrayList2.isEmpty()) {
                            return;
                        }
                    }
                    MessagesStorage.getInstance().getDatabase().m12168d();
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO media_v2 VALUES(?, ?, ?, ?, ?)");
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        Message message = (Message) it.next();
                        if (SharedMediaQuery.canAddMessageToMedia(message)) {
                            long j = (long) message.id;
                            if (message.to_id.channel_id != 0) {
                                j |= ((long) message.to_id.channel_id) << 32;
                            }
                            a.m12180d();
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(nativeByteBuffer);
                            a.m12175a(1, j);
                            a.m12175a(2, j2);
                            a.m12174a(3, message.date);
                            a.m12174a(4, i4);
                            a.m12177a(5, nativeByteBuffer);
                            a.m12178b();
                            nativeByteBuffer.reuse();
                        }
                    }
                    a.m12181e();
                    if (!(z2 && i3 == 0)) {
                        if (!z2) {
                            i = ((Message) arrayList2.get(arrayList2.size() - 1)).id;
                        }
                        if (i3 != 0) {
                            MessagesStorage.getInstance().closeHolesInMedia(j2, i, i3, i4);
                        } else {
                            MessagesStorage.getInstance().closeHolesInMedia(j2, i, Integer.MAX_VALUE, i4);
                        }
                    }
                    MessagesStorage.getInstance().getDatabase().m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }
}
