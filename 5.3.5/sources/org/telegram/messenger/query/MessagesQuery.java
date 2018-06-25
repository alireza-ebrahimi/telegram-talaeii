package org.telegram.messenger.query;

import android.text.Spannable;
import android.text.TextUtils;
import com.persianswitch.sdk.base.log.LogCollector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$TL_channels_getMessages;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
import org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
import org.telegram.tgnet.TLRPC$TL_messageEmpty;
import org.telegram.tgnet.TLRPC$TL_messageEntityBold;
import org.telegram.tgnet.TLRPC$TL_messageEntityCode;
import org.telegram.tgnet.TLRPC$TL_messageEntityItalic;
import org.telegram.tgnet.TLRPC$TL_messageEntityPre;
import org.telegram.tgnet.TLRPC$TL_messages_getMessages;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanUserMention;

public class MessagesQuery {
    private static Comparator<TLRPC$MessageEntity> entityComparator = new C17901();

    /* renamed from: org.telegram.messenger.query.MessagesQuery$1 */
    static class C17901 implements Comparator<TLRPC$MessageEntity> {
        C17901() {
        }

        public int compare(TLRPC$MessageEntity entity1, TLRPC$MessageEntity entity2) {
            if (entity1.offset > entity2.offset) {
                return 1;
            }
            if (entity1.offset < entity2.offset) {
                return -1;
            }
            return 0;
        }
    }

    public static MessageObject loadPinnedMessage(final int channelId, final int mid, boolean useQueue) {
        if (!useQueue) {
            return loadPinnedMessageInternal(channelId, mid, true);
        }
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                MessagesQuery.loadPinnedMessageInternal(channelId, mid, false);
            }
        });
        return null;
    }

    private static MessageObject loadPinnedMessageInternal(int channelId, int mid, boolean returnValue) {
        long messageId = ((long) mid) | (((long) channelId) << 32);
        TLRPC$Message result = null;
        try {
            NativeByteBuffer data;
            ArrayList<User> users = new ArrayList();
            ArrayList<TLRPC$Chat> chats = new ArrayList();
            ArrayList<Integer> usersToLoad = new ArrayList();
            ArrayList<Integer> chatsToLoad = new ArrayList();
            SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, mid, date FROM messages WHERE mid = %d", new Object[]{Long.valueOf(messageId)}), new Object[0]);
            if (cursor.next()) {
                data = cursor.byteBufferValue(0);
                if (data != null) {
                    result = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                    data.reuse();
                    if (result.action instanceof TLRPC$TL_messageActionHistoryClear) {
                        result = null;
                    } else {
                        result.id = cursor.intValue(1);
                        result.date = cursor.intValue(2);
                        result.dialog_id = (long) (-channelId);
                        MessagesStorage.addUsersAndChatsFromMessage(result, usersToLoad, chatsToLoad);
                    }
                }
            }
            cursor.dispose();
            if (result == null) {
                cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data FROM chat_pinned WHERE uid = %d", new Object[]{Integer.valueOf(channelId)}), new Object[0]);
                if (cursor.next()) {
                    data = cursor.byteBufferValue(0);
                    if (data != null) {
                        result = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                        data.reuse();
                        if (result.id != mid || (result.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                            result = null;
                        } else {
                            result.dialog_id = (long) (-channelId);
                            MessagesStorage.addUsersAndChatsFromMessage(result, usersToLoad, chatsToLoad);
                        }
                    }
                }
                cursor.dispose();
            }
            if (result == null) {
                TLRPC$TL_channels_getMessages req = new TLRPC$TL_channels_getMessages();
                req.channel = MessagesController.getInputChannel(channelId);
                req.id.add(Integer.valueOf(mid));
                final int i = channelId;
                ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                    public void run(TLObject response, TLRPC$TL_error error) {
                        boolean ok = false;
                        if (error == null) {
                            TLRPC$messages_Messages messagesRes = (TLRPC$messages_Messages) response;
                            MessagesQuery.removeEmptyMessages(messagesRes.messages);
                            if (!messagesRes.messages.isEmpty()) {
                                ImageLoader.saveMessagesThumbs(messagesRes.messages);
                                MessagesQuery.broadcastPinnedMessage((TLRPC$Message) messagesRes.messages.get(0), messagesRes.users, messagesRes.chats, false, false);
                                MessagesStorage.getInstance().putUsersAndChats(messagesRes.users, messagesRes.chats, true, true);
                                MessagesQuery.savePinnedMessage((TLRPC$Message) messagesRes.messages.get(0));
                                ok = true;
                            }
                        }
                        if (!ok) {
                            MessagesStorage.getInstance().updateChannelPinnedMessage(i, 0);
                        }
                    }
                });
            } else if (returnValue) {
                return broadcastPinnedMessage(result, users, chats, true, returnValue);
            } else {
                if (!usersToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), users);
                }
                if (!chatsToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                }
                broadcastPinnedMessage(result, users, chats, true, false);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        return null;
    }

    private static void savePinnedMessage(final TLRPC$Message result) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO chat_pinned VALUES(?, ?, ?)");
                    NativeByteBuffer data = new NativeByteBuffer(result.getObjectSize());
                    result.serializeToStream(data);
                    state.requery();
                    state.bindInteger(1, result.to_id.channel_id);
                    state.bindInteger(2, result.id);
                    state.bindByteBuffer(3, data);
                    state.step();
                    data.reuse();
                    state.dispose();
                    MessagesStorage.getInstance().getDatabase().commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private static MessageObject broadcastPinnedMessage(TLRPC$Message result, ArrayList<User> users, ArrayList<TLRPC$Chat> chats, boolean isCache, boolean returnValue) {
        int a;
        final HashMap<Integer, User> usersDict = new HashMap();
        for (a = 0; a < users.size(); a++) {
            User user = (User) users.get(a);
            usersDict.put(Integer.valueOf(user.id), user);
        }
        final HashMap<Integer, TLRPC$Chat> chatsDict = new HashMap();
        for (a = 0; a < chats.size(); a++) {
            TLRPC$Chat chat = (TLRPC$Chat) chats.get(a);
            chatsDict.put(Integer.valueOf(chat.id), chat);
        }
        if (returnValue) {
            return new MessageObject(result, usersDict, chatsDict, false);
        }
        final ArrayList<User> arrayList = users;
        final boolean z = isCache;
        final ArrayList<TLRPC$Chat> arrayList2 = chats;
        final TLRPC$Message tLRPC$Message = result;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.getInstance().putUsers(arrayList, z);
                MessagesController.getInstance().putChats(arrayList2, z);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didLoadedPinnedMessage, new Object[]{new MessageObject(tLRPC$Message, usersDict, chatsDict, false)});
            }
        });
        return null;
    }

    private static void removeEmptyMessages(ArrayList<TLRPC$Message> messages) {
        int a = 0;
        while (a < messages.size()) {
            TLRPC$Message message = (TLRPC$Message) messages.get(a);
            if (message == null || (message instanceof TLRPC$TL_messageEmpty) || (message.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                messages.remove(a);
                a--;
            }
            a++;
        }
    }

    public static void loadReplyMessagesForMessages(ArrayList<MessageObject> messages, long dialogId) {
        StringBuilder stringBuilder;
        int a;
        MessageObject messageObject;
        ArrayList<MessageObject> messageObjects;
        if (((int) dialogId) == 0) {
            ArrayList<Long> replyMessages = new ArrayList();
            HashMap<Long, ArrayList<MessageObject>> replyMessageRandomOwners = new HashMap();
            stringBuilder = new StringBuilder();
            for (a = 0; a < messages.size(); a++) {
                messageObject = (MessageObject) messages.get(a);
                if (messageObject.isReply() && messageObject.replyMessageObject == null) {
                    Long id = Long.valueOf(messageObject.messageOwner.reply_to_random_id);
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(id);
                    messageObjects = (ArrayList) replyMessageRandomOwners.get(id);
                    if (messageObjects == null) {
                        messageObjects = new ArrayList();
                        replyMessageRandomOwners.put(id, messageObjects);
                    }
                    messageObjects.add(messageObject);
                    if (!replyMessages.contains(id)) {
                        replyMessages.add(id);
                    }
                }
            }
            if (!replyMessages.isEmpty()) {
                final ArrayList<Long> arrayList = replyMessages;
                final long j = dialogId;
                final HashMap<Long, ArrayList<MessageObject>> hashMap = replyMessageRandomOwners;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.query.MessagesQuery$6$1 */
                    class C17951 implements Runnable {
                        C17951() {
                        }

                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didLoadedReplyMessages, new Object[]{Long.valueOf(j)});
                        }
                    }

                    public void run() {
                        try {
                            ArrayList<MessageObject> arrayList;
                            SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT m.data, m.mid, m.date, r.random_id FROM randoms as r INNER JOIN messages as m ON r.mid = m.mid WHERE r.random_id IN(%s)", new Object[]{TextUtils.join(",", arrayList)}), new Object[0]);
                            while (cursor.next()) {
                                NativeByteBuffer data = cursor.byteBufferValue(0);
                                if (data != null) {
                                    TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                    message.id = cursor.intValue(1);
                                    message.date = cursor.intValue(2);
                                    message.dialog_id = j;
                                    arrayList = (ArrayList) hashMap.remove(Long.valueOf(cursor.longValue(3)));
                                    if (arrayList != null) {
                                        MessageObject messageObject = new MessageObject(message, null, null, false);
                                        for (int b = 0; b < arrayList.size(); b++) {
                                            MessageObject object = (MessageObject) arrayList.get(b);
                                            object.replyMessageObject = messageObject;
                                            object.messageOwner.reply_to_msg_id = messageObject.getId();
                                            if (object.isMegagroup()) {
                                                TLRPC$Message tLRPC$Message = object.replyMessageObject.messageOwner;
                                                tLRPC$Message.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                }
                            }
                            cursor.dispose();
                            if (!hashMap.isEmpty()) {
                                for (Entry<Long, ArrayList<MessageObject>> entry : hashMap.entrySet()) {
                                    arrayList = (ArrayList) entry.getValue();
                                    for (int a = 0; a < arrayList.size(); a++) {
                                        ((MessageObject) arrayList.get(a)).messageOwner.reply_to_random_id = 0;
                                    }
                                }
                            }
                            AndroidUtilities.runOnUIThread(new C17951());
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
                return;
            }
            return;
        }
        final ArrayList<Integer> replyMessages2 = new ArrayList();
        final HashMap<Integer, ArrayList<MessageObject>> replyMessageOwners = new HashMap();
        stringBuilder = new StringBuilder();
        int channelId = 0;
        for (a = 0; a < messages.size(); a++) {
            messageObject = (MessageObject) messages.get(a);
            if (messageObject.getId() > 0 && messageObject.isReply() && messageObject.replyMessageObject == null) {
                Integer id2 = Integer.valueOf(messageObject.messageOwner.reply_to_msg_id);
                long messageId = (long) id2.intValue();
                if (messageObject.messageOwner.to_id.channel_id != 0) {
                    messageId |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                    channelId = messageObject.messageOwner.to_id.channel_id;
                }
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(messageId);
                messageObjects = (ArrayList) replyMessageOwners.get(id2);
                if (messageObjects == null) {
                    messageObjects = new ArrayList();
                    replyMessageOwners.put(id2, messageObjects);
                }
                messageObjects.add(messageObject);
                if (!replyMessages2.contains(id2)) {
                    replyMessages2.add(id2);
                }
            }
        }
        if (!replyMessages2.isEmpty()) {
            final int channelIdFinal = channelId;
            final long j2 = dialogId;
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.query.MessagesQuery$7$1 */
                class C17971 implements RequestDelegate {
                    C17971() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                        if (error == null) {
                            TLRPC$messages_Messages messagesRes = (TLRPC$messages_Messages) response;
                            MessagesQuery.removeEmptyMessages(messagesRes.messages);
                            ImageLoader.saveMessagesThumbs(messagesRes.messages);
                            MessagesQuery.broadcastReplyMessages(messagesRes.messages, replyMessageOwners, messagesRes.users, messagesRes.chats, j2, false);
                            MessagesStorage.getInstance().putUsersAndChats(messagesRes.users, messagesRes.chats, true, true);
                            MessagesQuery.saveReplyMessages(replyMessageOwners, messagesRes.messages);
                        }
                    }
                }

                /* renamed from: org.telegram.messenger.query.MessagesQuery$7$2 */
                class C17982 implements RequestDelegate {
                    C17982() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                        if (error == null) {
                            TLRPC$messages_Messages messagesRes = (TLRPC$messages_Messages) response;
                            MessagesQuery.removeEmptyMessages(messagesRes.messages);
                            ImageLoader.saveMessagesThumbs(messagesRes.messages);
                            MessagesQuery.broadcastReplyMessages(messagesRes.messages, replyMessageOwners, messagesRes.users, messagesRes.chats, j2, false);
                            MessagesStorage.getInstance().putUsersAndChats(messagesRes.users, messagesRes.chats, true, true);
                            MessagesQuery.saveReplyMessages(replyMessageOwners, messagesRes.messages);
                        }
                    }
                }

                public void run() {
                    try {
                        ArrayList<TLRPC$Message> result = new ArrayList();
                        ArrayList<User> users = new ArrayList();
                        ArrayList<TLRPC$Chat> chats = new ArrayList();
                        ArrayList<Integer> usersToLoad = new ArrayList();
                        ArrayList<Integer> chatsToLoad = new ArrayList();
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, mid, date FROM messages WHERE mid IN(%s)", new Object[]{stringBuilder.toString()}), new Object[0]);
                        while (cursor.next()) {
                            NativeByteBuffer data = cursor.byteBufferValue(0);
                            if (data != null) {
                                TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                data.reuse();
                                message.id = cursor.intValue(1);
                                message.date = cursor.intValue(2);
                                message.dialog_id = j2;
                                MessagesStorage.addUsersAndChatsFromMessage(message, usersToLoad, chatsToLoad);
                                result.add(message);
                                replyMessages2.remove(Integer.valueOf(message.id));
                            }
                        }
                        cursor.dispose();
                        if (!usersToLoad.isEmpty()) {
                            MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), users);
                        }
                        if (!chatsToLoad.isEmpty()) {
                            MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                        }
                        MessagesQuery.broadcastReplyMessages(result, replyMessageOwners, users, chats, j2, true);
                        if (!replyMessages2.isEmpty()) {
                            if (channelIdFinal != 0) {
                                TLRPC$TL_channels_getMessages req = new TLRPC$TL_channels_getMessages();
                                req.channel = MessagesController.getInputChannel(channelIdFinal);
                                req.id = replyMessages2;
                                ConnectionsManager.getInstance().sendRequest(req, new C17971());
                                return;
                            }
                            TLRPC$TL_messages_getMessages req2 = new TLRPC$TL_messages_getMessages();
                            req2.id = replyMessages2;
                            ConnectionsManager.getInstance().sendRequest(req2, new C17982());
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    private static void saveReplyMessages(final HashMap<Integer, ArrayList<MessageObject>> replyMessageOwners, final ArrayList<TLRPC$Message> result) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("UPDATE messages SET replydata = ? WHERE mid = ?");
                    for (int a = 0; a < result.size(); a++) {
                        TLRPC$Message message = (TLRPC$Message) result.get(a);
                        ArrayList<MessageObject> messageObjects = (ArrayList) replyMessageOwners.get(Integer.valueOf(message.id));
                        if (messageObjects != null) {
                            NativeByteBuffer data = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(data);
                            for (int b = 0; b < messageObjects.size(); b++) {
                                MessageObject messageObject = (MessageObject) messageObjects.get(b);
                                state.requery();
                                long messageId = (long) messageObject.getId();
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    messageId |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                state.bindByteBuffer(1, data);
                                state.bindLong(2, messageId);
                                state.step();
                            }
                            data.reuse();
                        }
                    }
                    state.dispose();
                    MessagesStorage.getInstance().getDatabase().commitTransaction();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private static void broadcastReplyMessages(ArrayList<TLRPC$Message> result, HashMap<Integer, ArrayList<MessageObject>> replyMessageOwners, ArrayList<User> users, ArrayList<TLRPC$Chat> chats, long dialog_id, boolean isCache) {
        int a;
        final HashMap<Integer, User> usersDict = new HashMap();
        for (a = 0; a < users.size(); a++) {
            User user = (User) users.get(a);
            usersDict.put(Integer.valueOf(user.id), user);
        }
        final HashMap<Integer, TLRPC$Chat> chatsDict = new HashMap();
        for (a = 0; a < chats.size(); a++) {
            TLRPC$Chat chat = (TLRPC$Chat) chats.get(a);
            chatsDict.put(Integer.valueOf(chat.id), chat);
        }
        final ArrayList<User> arrayList = users;
        final boolean z = isCache;
        final ArrayList<TLRPC$Chat> arrayList2 = chats;
        final ArrayList<TLRPC$Message> arrayList3 = result;
        final HashMap<Integer, ArrayList<MessageObject>> hashMap = replyMessageOwners;
        final long j = dialog_id;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.getInstance().putUsers(arrayList, z);
                MessagesController.getInstance().putChats(arrayList2, z);
                boolean changed = false;
                for (int a = 0; a < arrayList3.size(); a++) {
                    TLRPC$Message message = (TLRPC$Message) arrayList3.get(a);
                    ArrayList<MessageObject> arrayList = (ArrayList) hashMap.get(Integer.valueOf(message.id));
                    if (arrayList != null) {
                        MessageObject messageObject = new MessageObject(message, usersDict, chatsDict, false);
                        for (int b = 0; b < arrayList.size(); b++) {
                            MessageObject m = (MessageObject) arrayList.get(b);
                            m.replyMessageObject = messageObject;
                            if (m.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage) {
                                m.generatePinMessageText(null, null);
                            } else if (m.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) {
                                m.generateGameMessageText(null);
                            } else if (m.messageOwner.action instanceof TLRPC$TL_messageActionPaymentSent) {
                                m.generatePaymentSentMessageText(null);
                            }
                            if (m.isMegagroup()) {
                                TLRPC$Message tLRPC$Message = m.replyMessageObject.messageOwner;
                                tLRPC$Message.flags |= Integer.MIN_VALUE;
                            }
                        }
                        changed = true;
                    }
                }
                if (changed) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didLoadedReplyMessages, new Object[]{Long.valueOf(j)});
                }
            }
        });
    }

    public static void sortEntities(ArrayList<TLRPC$MessageEntity> entities) {
        Collections.sort(entities, entityComparator);
    }

    private static boolean checkInclusion(int index, ArrayList<TLRPC$MessageEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return false;
        }
        int count = entities.size();
        for (int a = 0; a < count; a++) {
            TLRPC$MessageEntity entity = (TLRPC$MessageEntity) entities.get(a);
            if (entity.offset <= index && entity.offset + entity.length > index) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkIntersection(int start, int end, ArrayList<TLRPC$MessageEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return false;
        }
        int count = entities.size();
        for (int a = 0; a < count; a++) {
            TLRPC$MessageEntity entity = (TLRPC$MessageEntity) entities.get(a);
            if (entity.offset > start && entity.offset + entity.length <= end) {
                return true;
            }
        }
        return false;
    }

    private static void removeOffsetAfter(int start, int countToRemove, ArrayList<TLRPC$MessageEntity> entities) {
        int count = entities.size();
        for (int a = 0; a < count; a++) {
            TLRPC$MessageEntity entity = (TLRPC$MessageEntity) entities.get(a);
            if (entity.offset > start) {
                entity.offset -= countToRemove;
            }
        }
    }

    public static ArrayList<TLRPC$MessageEntity> getEntities(CharSequence[] message) {
        if (message == null || message[0] == null) {
            return null;
        }
        TLRPC$MessageEntity entity;
        ArrayList<TLRPC$MessageEntity> entities = null;
        int start = -1;
        int lastIndex = 0;
        boolean isPre = false;
        String mono = "`";
        String pre = "```";
        String bold = "**";
        String italic = "__";
        while (true) {
            int a;
            int index = TextUtils.indexOf(message[0], !isPre ? "`" : "```", lastIndex);
            if (index == -1) {
                break;
            } else if (start == -1) {
                isPre = message[0].length() - index > 2 && message[0].charAt(index + 1) == '`' && message[0].charAt(index + 2) == '`';
                start = index;
                lastIndex = index + (isPre ? 3 : 1);
            } else {
                if (entities == null) {
                    entities = new ArrayList();
                }
                a = index + (isPre ? 3 : 1);
                while (a < message[0].length() && message[0].charAt(a) == '`') {
                    index++;
                    a++;
                }
                lastIndex = index + (isPre ? 3 : 1);
                if (isPre) {
                    int firstChar = start > 0 ? message[0].charAt(start - 1) : 0;
                    boolean replacedFirst = firstChar == 32 || firstChar == 10;
                    CharSequence startMessage = TextUtils.substring(message[0], 0, start - (replacedFirst ? 1 : 0));
                    CharSequence content = TextUtils.substring(message[0], start + 3, index);
                    firstChar = index + 3 < message[0].length() ? message[0].charAt(index + 3) : 0;
                    CharSequence charSequence = message[0];
                    int i = index + 3;
                    int i2 = (firstChar == 32 || firstChar == 10) ? 1 : 0;
                    CharSequence endMessage = TextUtils.substring(charSequence, i2 + i, message[0].length());
                    if (startMessage.length() != 0) {
                        startMessage = TextUtils.concat(new CharSequence[]{startMessage, LogCollector.LINE_SEPARATOR});
                    } else {
                        replacedFirst = true;
                    }
                    if (endMessage.length() != 0) {
                        endMessage = TextUtils.concat(new CharSequence[]{LogCollector.LINE_SEPARATOR, endMessage});
                    }
                    if (!TextUtils.isEmpty(content)) {
                        message[0] = TextUtils.concat(new CharSequence[]{startMessage, content, endMessage});
                        TLRPC$TL_messageEntityPre entity2 = new TLRPC$TL_messageEntityPre();
                        entity2.offset = (replacedFirst ? 0 : 1) + start;
                        entity2.length = (replacedFirst ? 0 : 1) + ((index - start) - 3);
                        entity2.language = "";
                        entities.add(entity2);
                        lastIndex -= 6;
                    }
                } else if (start + 1 != index) {
                    message[0] = TextUtils.concat(new CharSequence[]{TextUtils.substring(message[0], 0, start), TextUtils.substring(message[0], start + 1, index), TextUtils.substring(message[0], index + 1, message[0].length())});
                    TLRPC$TL_messageEntityCode entity3 = new TLRPC$TL_messageEntityCode();
                    entity3.offset = start;
                    entity3.length = (index - start) - 1;
                    entities.add(entity3);
                    lastIndex -= 2;
                }
                start = -1;
                isPre = false;
            }
        }
        if (start != -1 && isPre) {
            message[0] = TextUtils.concat(new CharSequence[]{TextUtils.substring(message[0], 0, start), TextUtils.substring(message[0], start + 2, message[0].length())});
            if (entities == null) {
                entities = new ArrayList();
            }
            entity3 = new TLRPC$TL_messageEntityCode();
            entity3.offset = start;
            entity3.length = 1;
            entities.add(entity3);
        }
        if (message[0] instanceof Spannable) {
            Spannable spannable = message[0];
            TypefaceSpan[] spans = (TypefaceSpan[]) spannable.getSpans(0, message[0].length(), TypefaceSpan.class);
            if (spans != null && spans.length > 0) {
                for (TypefaceSpan span : spans) {
                    int spanStart = spannable.getSpanStart(span);
                    int spanEnd = spannable.getSpanEnd(span);
                    if (!(checkInclusion(spanStart, entities) || checkInclusion(spanEnd, entities) || checkIntersection(spanStart, spanEnd, entities))) {
                        if (entities == null) {
                            entities = new ArrayList();
                        }
                        if (span.isBold()) {
                            entity = new TLRPC$TL_messageEntityBold();
                        } else {
                            entity = new TLRPC$TL_messageEntityItalic();
                        }
                        entity.offset = spanStart;
                        entity.length = spanEnd - spanStart;
                        entities.add(entity);
                    }
                }
            }
            URLSpanUserMention[] spansMentions = (URLSpanUserMention[]) spannable.getSpans(0, message[0].length(), URLSpanUserMention.class);
            if (spansMentions != null && spansMentions.length > 0) {
                if (entities == null) {
                    entities = new ArrayList();
                }
                for (int b = 0; b < spansMentions.length; b++) {
                    TLRPC$TL_inputMessageEntityMentionName entity4 = new TLRPC$TL_inputMessageEntityMentionName();
                    entity4.user_id = MessagesController.getInputUser(Utilities.parseInt(spansMentions[b].getURL()).intValue());
                    if (entity4.user_id != null) {
                        entity4.offset = spannable.getSpanStart(spansMentions[b]);
                        entity4.length = Math.min(spannable.getSpanEnd(spansMentions[b]), message[0].length()) - entity4.offset;
                        if (message[0].charAt((entity4.offset + entity4.length) - 1) == ' ') {
                            entity4.length--;
                        }
                        entities.add(entity4);
                    }
                }
            }
        }
        int c = 0;
        while (c < 2) {
            lastIndex = 0;
            start = -1;
            String checkString = c == 0 ? "**" : "__";
            char checkChar = c == 0 ? '*' : '_';
            while (true) {
                index = TextUtils.indexOf(message[0], checkString, lastIndex);
                if (index == -1) {
                    break;
                } else if (start == -1) {
                    char prevChar = index == 0 ? ' ' : message[0].charAt(index - 1);
                    if (!checkInclusion(index, entities) && (prevChar == ' ' || prevChar == '\n')) {
                        start = index;
                    }
                    lastIndex = index + 2;
                } else {
                    a = index + 2;
                    while (a < message[0].length() && message[0].charAt(a) == checkChar) {
                        index++;
                        a++;
                    }
                    lastIndex = index + 2;
                    if (checkInclusion(index, entities) || checkIntersection(start, index, entities)) {
                        start = -1;
                    } else {
                        if (start + 2 != index) {
                            if (entities == null) {
                                entities = new ArrayList();
                            }
                            message[0] = TextUtils.concat(new CharSequence[]{TextUtils.substring(message[0], 0, start), TextUtils.substring(message[0], start + 2, index), TextUtils.substring(message[0], index + 2, message[0].length())});
                            if (c == 0) {
                                entity = new TLRPC$TL_messageEntityBold();
                            } else {
                                entity = new TLRPC$TL_messageEntityItalic();
                            }
                            entity.offset = start;
                            entity.length = (index - start) - 2;
                            removeOffsetAfter(entity.offset + entity.length, 4, entities);
                            entities.add(entity);
                            lastIndex -= 4;
                        }
                        start = -1;
                    }
                }
            }
            c++;
        }
        return entities;
    }
}
