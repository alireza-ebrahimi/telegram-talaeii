package org.telegram.messenger.query;

import android.text.Spannable;
import android.text.TextUtils;
import java.util.AbstractMap;
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
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
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
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.TL_channels_getMessages;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanUserMention;

public class MessagesQuery {
    private static Comparator<MessageEntity> entityComparator = new C35681();

    /* renamed from: org.telegram.messenger.query.MessagesQuery$1 */
    static class C35681 implements Comparator<MessageEntity> {
        C35681() {
        }

        public int compare(MessageEntity messageEntity, MessageEntity messageEntity2) {
            return messageEntity.offset > messageEntity2.offset ? 1 : messageEntity.offset < messageEntity2.offset ? -1 : 0;
        }
    }

    private static MessageObject broadcastPinnedMessage(Message message, ArrayList<User> arrayList, ArrayList<Chat> arrayList2, boolean z, boolean z2) {
        int i;
        final AbstractMap hashMap = new HashMap();
        for (i = 0; i < arrayList.size(); i++) {
            User user = (User) arrayList.get(i);
            hashMap.put(Integer.valueOf(user.id), user);
        }
        final AbstractMap hashMap2 = new HashMap();
        for (i = 0; i < arrayList2.size(); i++) {
            Chat chat = (Chat) arrayList2.get(i);
            hashMap2.put(Integer.valueOf(chat.id), chat);
        }
        if (z2) {
            return new MessageObject(message, hashMap, hashMap2, false);
        }
        final ArrayList<User> arrayList3 = arrayList;
        final boolean z3 = z;
        final ArrayList<Chat> arrayList4 = arrayList2;
        final Message message2 = message;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.getInstance().putUsers(arrayList3, z3);
                MessagesController.getInstance().putChats(arrayList4, z3);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didLoadedPinnedMessage, new MessageObject(message2, hashMap, hashMap2, false));
            }
        });
        return null;
    }

    private static void broadcastReplyMessages(ArrayList<Message> arrayList, HashMap<Integer, ArrayList<MessageObject>> hashMap, ArrayList<User> arrayList2, ArrayList<Chat> arrayList3, long j, boolean z) {
        int i;
        final HashMap hashMap2 = new HashMap();
        for (i = 0; i < arrayList2.size(); i++) {
            User user = (User) arrayList2.get(i);
            hashMap2.put(Integer.valueOf(user.id), user);
        }
        final HashMap hashMap3 = new HashMap();
        for (i = 0; i < arrayList3.size(); i++) {
            Chat chat = (Chat) arrayList3.get(i);
            hashMap3.put(Integer.valueOf(chat.id), chat);
        }
        final ArrayList<User> arrayList4 = arrayList2;
        final boolean z2 = z;
        final ArrayList<Chat> arrayList5 = arrayList3;
        final ArrayList<Message> arrayList6 = arrayList;
        final HashMap<Integer, ArrayList<MessageObject>> hashMap4 = hashMap;
        final long j2 = j;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.getInstance().putUsers(arrayList4, z2);
                MessagesController.getInstance().putChats(arrayList5, z2);
                int i = 0;
                boolean z = false;
                while (i < arrayList6.size()) {
                    boolean z2;
                    Message message = (Message) arrayList6.get(i);
                    ArrayList arrayList = (ArrayList) hashMap4.get(Integer.valueOf(message.id));
                    if (arrayList != null) {
                        MessageObject messageObject = new MessageObject(message, hashMap2, hashMap3, false);
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            MessageObject messageObject2 = (MessageObject) arrayList.get(i2);
                            messageObject2.replyMessageObject = messageObject;
                            if (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage) {
                                messageObject2.generatePinMessageText(null, null);
                            } else if (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) {
                                messageObject2.generateGameMessageText(null);
                            } else if (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionPaymentSent) {
                                messageObject2.generatePaymentSentMessageText(null);
                            }
                            if (messageObject2.isMegagroup()) {
                                message = messageObject2.replyMessageObject.messageOwner;
                                message.flags |= Integer.MIN_VALUE;
                            }
                        }
                        z2 = true;
                    } else {
                        z2 = z;
                    }
                    i++;
                    z = z2;
                }
                if (z) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didLoadedReplyMessages, Long.valueOf(j2));
                }
            }
        });
    }

    private static boolean checkInclusion(int i, ArrayList<MessageEntity> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return false;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            MessageEntity messageEntity = (MessageEntity) arrayList.get(i2);
            if (messageEntity.offset <= i) {
                if (messageEntity.length + messageEntity.offset > i) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkIntersection(int i, int i2, ArrayList<MessageEntity> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return false;
        }
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            MessageEntity messageEntity = (MessageEntity) arrayList.get(i3);
            if (messageEntity.offset > i) {
                if (messageEntity.length + messageEntity.offset <= i2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<MessageEntity> getEntities(CharSequence[] charSequenceArr) {
        if (charSequenceArr == null || charSequenceArr[0] == null) {
            return null;
        }
        int i;
        ArrayList<MessageEntity> arrayList;
        ArrayList arrayList2 = null;
        String str = "`";
        str = "```";
        str = "**";
        str = "__";
        int i2 = -1;
        Object obj = null;
        int i3 = 0;
        while (true) {
            int i4;
            int indexOf = TextUtils.indexOf(charSequenceArr[0], obj == null ? "`" : "```", i3);
            if (indexOf == -1) {
                break;
            } else if (i2 == -1) {
                r0 = (charSequenceArr[0].length() - indexOf > 2 && charSequenceArr[0].charAt(indexOf + 1) == '`' && charSequenceArr[0].charAt(indexOf + 2) == '`') ? 1 : null;
                i2 = indexOf;
                obj = r0;
                i3 = (r0 != null ? 3 : 1) + indexOf;
            } else {
                ArrayList arrayList3;
                if (arrayList2 == null) {
                    arrayList3 = new ArrayList();
                } else {
                    ArrayList<MessageEntity> arrayList4 = arrayList2;
                }
                i3 = (obj != null ? 3 : 1) + indexOf;
                while (i3 < charSequenceArr[0].length() && charSequenceArr[0].charAt(i3) == '`') {
                    indexOf++;
                    i3++;
                }
                i4 = indexOf + (obj != null ? 3 : 1);
                if (obj != null) {
                    CharSequence concat;
                    char charAt = i2 > 0 ? charSequenceArr[0].charAt(i2 - 1) : '\u0000';
                    r0 = (charAt == ' ' || charAt == '\n') ? 1 : null;
                    CharSequence substring = TextUtils.substring(charSequenceArr[0], 0, i2 - (r0 != null ? 1 : 0));
                    CharSequence substring2 = TextUtils.substring(charSequenceArr[0], i2 + 3, indexOf);
                    char charAt2 = indexOf + 3 < charSequenceArr[0].length() ? charSequenceArr[0].charAt(indexOf + 3) : '\u0000';
                    CharSequence charSequence = charSequenceArr[0];
                    int i5 = indexOf + 3;
                    i = (charAt2 == ' ' || charAt2 == '\n') ? 1 : 0;
                    charSequence = TextUtils.substring(charSequence, i + i5, charSequenceArr[0].length());
                    if (substring.length() != 0) {
                        obj = r0;
                        concat = TextUtils.concat(new CharSequence[]{substring, "\n"});
                    } else {
                        i = 1;
                        concat = substring;
                    }
                    substring = charSequence.length() != 0 ? TextUtils.concat(new CharSequence[]{"\n", charSequence}) : charSequence;
                    if (!TextUtils.isEmpty(substring2)) {
                        charSequenceArr[0] = TextUtils.concat(new CharSequence[]{concat, substring2, substring});
                        TLRPC$TL_messageEntityPre tLRPC$TL_messageEntityPre = new TLRPC$TL_messageEntityPre();
                        tLRPC$TL_messageEntityPre.offset = (obj != null ? 0 : 1) + i2;
                        tLRPC$TL_messageEntityPre.length = (obj != null ? 0 : 1) + ((indexOf - i2) - 3);
                        tLRPC$TL_messageEntityPre.language = TtmlNode.ANONYMOUS_REGION_ID;
                        arrayList3.add(tLRPC$TL_messageEntityPre);
                        i3 = i4 - 6;
                    }
                    i3 = i4;
                } else {
                    if (i2 + 1 != indexOf) {
                        charSequenceArr[0] = TextUtils.concat(new CharSequence[]{TextUtils.substring(charSequenceArr[0], 0, i2), TextUtils.substring(charSequenceArr[0], i2 + 1, indexOf), TextUtils.substring(charSequenceArr[0], indexOf + 1, charSequenceArr[0].length())});
                        TLRPC$TL_messageEntityCode tLRPC$TL_messageEntityCode = new TLRPC$TL_messageEntityCode();
                        tLRPC$TL_messageEntityCode.offset = i2;
                        tLRPC$TL_messageEntityCode.length = (indexOf - i2) - 1;
                        arrayList3.add(tLRPC$TL_messageEntityCode);
                        i3 = i4 - 2;
                    }
                    i3 = i4;
                }
                i2 = -1;
                arrayList2 = arrayList3;
                obj = null;
            }
        }
        if (!(i2 == -1 || obj == null)) {
            charSequenceArr[0] = TextUtils.concat(new CharSequence[]{TextUtils.substring(charSequenceArr[0], 0, i2), TextUtils.substring(charSequenceArr[0], i2 + 2, charSequenceArr[0].length())});
            if (arrayList2 == null) {
                arrayList = new ArrayList();
            }
            tLRPC$TL_messageEntityCode = new TLRPC$TL_messageEntityCode();
            tLRPC$TL_messageEntityCode.offset = i2;
            tLRPC$TL_messageEntityCode.length = 1;
            arrayList.add(tLRPC$TL_messageEntityCode);
        }
        if (charSequenceArr[0] instanceof Spannable) {
            Spannable spannable = (Spannable) charSequenceArr[0];
            TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) spannable.getSpans(0, charSequenceArr[0].length(), TypefaceSpan.class);
            if (typefaceSpanArr != null && typefaceSpanArr.length > 0) {
                for (TypefaceSpan typefaceSpan : typefaceSpanArr) {
                    i4 = spannable.getSpanStart(typefaceSpan);
                    i2 = spannable.getSpanEnd(typefaceSpan);
                    if (!(checkInclusion(i4, arrayList) || checkInclusion(i2, arrayList) || checkIntersection(i4, i2, arrayList))) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        MessageEntity tLRPC$TL_messageEntityBold = typefaceSpan.isBold() ? new TLRPC$TL_messageEntityBold() : new TLRPC$TL_messageEntityItalic();
                        tLRPC$TL_messageEntityBold.offset = i4;
                        tLRPC$TL_messageEntityBold.length = i2 - i4;
                        arrayList.add(tLRPC$TL_messageEntityBold);
                    }
                }
            }
            URLSpanUserMention[] uRLSpanUserMentionArr = (URLSpanUserMention[]) spannable.getSpans(0, charSequenceArr[0].length(), URLSpanUserMention.class);
            if (uRLSpanUserMentionArr != null && uRLSpanUserMentionArr.length > 0) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                for (i = 0; i < uRLSpanUserMentionArr.length; i++) {
                    TLRPC$TL_inputMessageEntityMentionName tLRPC$TL_inputMessageEntityMentionName = new TLRPC$TL_inputMessageEntityMentionName();
                    tLRPC$TL_inputMessageEntityMentionName.user_id = MessagesController.getInputUser(Utilities.parseInt(uRLSpanUserMentionArr[i].getURL()).intValue());
                    if (tLRPC$TL_inputMessageEntityMentionName.user_id != null) {
                        tLRPC$TL_inputMessageEntityMentionName.offset = spannable.getSpanStart(uRLSpanUserMentionArr[i]);
                        tLRPC$TL_inputMessageEntityMentionName.length = Math.min(spannable.getSpanEnd(uRLSpanUserMentionArr[i]), charSequenceArr[0].length()) - tLRPC$TL_inputMessageEntityMentionName.offset;
                        if (charSequenceArr[0].charAt((tLRPC$TL_inputMessageEntityMentionName.offset + tLRPC$TL_inputMessageEntityMentionName.length) - 1) == ' ') {
                            tLRPC$TL_inputMessageEntityMentionName.length--;
                        }
                        arrayList.add(tLRPC$TL_inputMessageEntityMentionName);
                    }
                }
            }
        }
        int i6 = 0;
        while (i6 < 2) {
            indexOf = 0;
            i = -1;
            if (i6 == 0) {
                CharSequence charSequence2 = "**";
            } else {
                Object obj2 = "__";
            }
            charAt = i6 == 0 ? '*' : '_';
            while (true) {
                int indexOf2 = TextUtils.indexOf(charSequenceArr[0], charSequence2, indexOf);
                if (indexOf2 == -1) {
                    break;
                } else if (i == -1) {
                    char charAt3 = indexOf2 == 0 ? ' ' : charSequenceArr[0].charAt(indexOf2 - 1);
                    if (!checkInclusion(indexOf2, arrayList) && (charAt3 == ' ' || charAt3 == '\n')) {
                        i = indexOf2;
                    }
                    indexOf = indexOf2 + 2;
                } else {
                    indexOf = indexOf2 + 2;
                    while (indexOf < charSequenceArr[0].length() && charSequenceArr[0].charAt(indexOf) == r0) {
                        indexOf2++;
                        indexOf++;
                    }
                    indexOf = indexOf2 + 2;
                    if (checkInclusion(indexOf2, arrayList) || checkIntersection(i, indexOf2, arrayList)) {
                        i = -1;
                    } else {
                        if (i + 2 != indexOf2) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            charSequenceArr[0] = TextUtils.concat(new CharSequence[]{TextUtils.substring(charSequenceArr[0], 0, i), TextUtils.substring(charSequenceArr[0], i + 2, indexOf2), TextUtils.substring(charSequenceArr[0], indexOf2 + 2, charSequenceArr[0].length())});
                            MessageEntity tLRPC$TL_messageEntityBold2 = i6 == 0 ? new TLRPC$TL_messageEntityBold() : new TLRPC$TL_messageEntityItalic();
                            tLRPC$TL_messageEntityBold2.offset = i;
                            tLRPC$TL_messageEntityBold2.length = (indexOf2 - i) - 2;
                            removeOffsetAfter(tLRPC$TL_messageEntityBold2.offset + tLRPC$TL_messageEntityBold2.length, 4, arrayList);
                            arrayList.add(tLRPC$TL_messageEntityBold2);
                            indexOf -= 4;
                        }
                        i = -1;
                    }
                }
            }
            i6++;
        }
        return arrayList;
    }

    public static MessageObject loadPinnedMessage(final int i, final int i2, boolean z) {
        if (!z) {
            return loadPinnedMessageInternal(i, i2, true);
        }
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                MessagesQuery.loadPinnedMessageInternal(i, i2, false);
            }
        });
        return null;
    }

    private static MessageObject loadPinnedMessageInternal(final int i, int i2, boolean z) {
        long j = ((long) i2) | (((long) i) << 32);
        try {
            AbstractSerializedData g;
            Message TLdeserialize;
            TLObject tL_channels_getMessages;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterable arrayList3 = new ArrayList();
            Iterable arrayList4 = new ArrayList();
            SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data, mid, date FROM messages WHERE mid = %d", new Object[]{Long.valueOf(j)}), new Object[0]);
            if (b.m12152a()) {
                g = b.m12161g(0);
                if (g != null) {
                    TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                    g.reuse();
                    if (TLdeserialize.action instanceof TLRPC$TL_messageActionHistoryClear) {
                        TLdeserialize = null;
                    } else {
                        TLdeserialize.id = b.m12154b(1);
                        TLdeserialize.date = b.m12154b(2);
                        TLdeserialize.dialog_id = (long) (-i);
                        MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList3, arrayList4);
                    }
                    b.m12155b();
                    if (TLdeserialize == null) {
                        b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data FROM chat_pinned WHERE uid = %d", new Object[]{Integer.valueOf(i)}), new Object[0]);
                        if (b.m12152a()) {
                            g = b.m12161g(0);
                            if (g != null) {
                                TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                if (TLdeserialize.id == i2 || (TLdeserialize.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                                    TLdeserialize = null;
                                } else {
                                    TLdeserialize.dialog_id = (long) (-i);
                                    MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList3, arrayList4);
                                }
                            }
                        }
                        b.m12155b();
                    }
                    if (TLdeserialize == null) {
                        tL_channels_getMessages = new TL_channels_getMessages();
                        tL_channels_getMessages.channel = MessagesController.getInputChannel(i);
                        tL_channels_getMessages.id.add(Integer.valueOf(i2));
                        ConnectionsManager.getInstance().sendRequest(tL_channels_getMessages, new RequestDelegate() {
                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                boolean z;
                                if (tLRPC$TL_error == null) {
                                    TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                    MessagesQuery.removeEmptyMessages(tLRPC$messages_Messages.messages);
                                    if (!tLRPC$messages_Messages.messages.isEmpty()) {
                                        ImageLoader.saveMessagesThumbs(tLRPC$messages_Messages.messages);
                                        MessagesQuery.broadcastPinnedMessage((Message) tLRPC$messages_Messages.messages.get(0), tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, false, false);
                                        MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                                        MessagesQuery.savePinnedMessage((Message) tLRPC$messages_Messages.messages.get(0));
                                        z = true;
                                        if (!z) {
                                            MessagesStorage.getInstance().updateChannelPinnedMessage(i, 0);
                                        }
                                    }
                                }
                                z = false;
                                if (!z) {
                                    MessagesStorage.getInstance().updateChannelPinnedMessage(i, 0);
                                }
                            }
                        });
                        return null;
                    } else if (z) {
                        return broadcastPinnedMessage(TLdeserialize, arrayList, arrayList2, true, z);
                    } else {
                        if (!arrayList3.isEmpty()) {
                            MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList3), arrayList);
                        }
                        if (!arrayList4.isEmpty()) {
                            MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList4), arrayList2);
                        }
                        broadcastPinnedMessage(TLdeserialize, arrayList, arrayList2, true, false);
                        return null;
                    }
                }
            }
            TLdeserialize = null;
            b.m12155b();
            if (TLdeserialize == null) {
                b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data FROM chat_pinned WHERE uid = %d", new Object[]{Integer.valueOf(i)}), new Object[0]);
                if (b.m12152a()) {
                    g = b.m12161g(0);
                    if (g != null) {
                        TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                        g.reuse();
                        if (TLdeserialize.id == i2) {
                        }
                        TLdeserialize = null;
                    }
                }
                b.m12155b();
            }
            if (TLdeserialize == null) {
                tL_channels_getMessages = new TL_channels_getMessages();
                tL_channels_getMessages.channel = MessagesController.getInputChannel(i);
                tL_channels_getMessages.id.add(Integer.valueOf(i2));
                ConnectionsManager.getInstance().sendRequest(tL_channels_getMessages, /* anonymous class already generated */);
                return null;
            } else if (z) {
                return broadcastPinnedMessage(TLdeserialize, arrayList, arrayList2, true, z);
            } else {
                if (arrayList3.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList3), arrayList);
                }
                if (arrayList4.isEmpty()) {
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList4), arrayList2);
                }
                broadcastPinnedMessage(TLdeserialize, arrayList, arrayList2, true, false);
                return null;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return null;
        }
    }

    public static void loadReplyMessagesForMessages(ArrayList<MessageObject> arrayList, final long j) {
        MessageObject messageObject;
        if (((int) j) == 0) {
            final ArrayList arrayList2 = new ArrayList();
            final HashMap hashMap = new HashMap();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arrayList.size(); i++) {
                messageObject = (MessageObject) arrayList.get(i);
                if (messageObject.isReply() && messageObject.replyMessageObject == null) {
                    Long valueOf = Long.valueOf(messageObject.messageOwner.reply_to_random_id);
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(valueOf);
                    ArrayList arrayList3 = (ArrayList) hashMap.get(valueOf);
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                        hashMap.put(valueOf, arrayList3);
                    }
                    arrayList3.add(messageObject);
                    if (!arrayList2.contains(valueOf)) {
                        arrayList2.add(valueOf);
                    }
                }
            }
            if (!arrayList2.isEmpty()) {
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.query.MessagesQuery$6$1 */
                    class C35731 implements Runnable {
                        C35731() {
                        }

                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didLoadedReplyMessages, Long.valueOf(j));
                        }
                    }

                    public void run() {
                        try {
                            ArrayList arrayList;
                            int i;
                            SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT m.data, m.mid, m.date, r.random_id FROM randoms as r INNER JOIN messages as m ON r.mid = m.mid WHERE r.random_id IN(%s)", new Object[]{TextUtils.join(",", arrayList2)}), new Object[0]);
                            while (b.m12152a()) {
                                AbstractSerializedData g = b.m12161g(0);
                                if (g != null) {
                                    Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                    g.reuse();
                                    TLdeserialize.id = b.m12154b(1);
                                    TLdeserialize.date = b.m12154b(2);
                                    TLdeserialize.dialog_id = j;
                                    arrayList = (ArrayList) hashMap.remove(Long.valueOf(b.m12158d(3)));
                                    if (arrayList != null) {
                                        MessageObject messageObject = new MessageObject(TLdeserialize, null, null, false);
                                        for (i = 0; i < arrayList.size(); i++) {
                                            MessageObject messageObject2 = (MessageObject) arrayList.get(i);
                                            messageObject2.replyMessageObject = messageObject;
                                            messageObject2.messageOwner.reply_to_msg_id = messageObject.getId();
                                            if (messageObject2.isMegagroup()) {
                                                TLdeserialize = messageObject2.replyMessageObject.messageOwner;
                                                TLdeserialize.flags |= Integer.MIN_VALUE;
                                            }
                                        }
                                    }
                                }
                            }
                            b.m12155b();
                            if (!hashMap.isEmpty()) {
                                for (Entry value : hashMap.entrySet()) {
                                    arrayList = (ArrayList) value.getValue();
                                    for (i = 0; i < arrayList.size(); i++) {
                                        ((MessageObject) arrayList.get(i)).messageOwner.reply_to_random_id = 0;
                                    }
                                }
                            }
                            AndroidUtilities.runOnUIThread(new C35731());
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                });
                return;
            }
            return;
        }
        final ArrayList arrayList4 = new ArrayList();
        final HashMap hashMap2 = new HashMap();
        final StringBuilder stringBuilder2 = new StringBuilder();
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            messageObject = (MessageObject) arrayList.get(i3);
            if (messageObject.getId() > 0 && messageObject.isReply() && messageObject.replyMessageObject == null) {
                long j2;
                int i4;
                Integer valueOf2 = Integer.valueOf(messageObject.messageOwner.reply_to_msg_id);
                long intValue = (long) valueOf2.intValue();
                if (messageObject.messageOwner.to_id.channel_id != 0) {
                    j2 = (((long) messageObject.messageOwner.to_id.channel_id) << 32) | intValue;
                    i4 = messageObject.messageOwner.to_id.channel_id;
                } else {
                    long j3 = intValue;
                    i4 = i2;
                    j2 = j3;
                }
                if (stringBuilder2.length() > 0) {
                    stringBuilder2.append(',');
                }
                stringBuilder2.append(j2);
                ArrayList arrayList5 = (ArrayList) hashMap2.get(valueOf2);
                if (arrayList5 == null) {
                    arrayList5 = new ArrayList();
                    hashMap2.put(valueOf2, arrayList5);
                }
                arrayList5.add(messageObject);
                if (!arrayList4.contains(valueOf2)) {
                    arrayList4.add(valueOf2);
                }
                i2 = i4;
            }
        }
        if (!arrayList4.isEmpty()) {
            final long j4 = j;
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.query.MessagesQuery$7$1 */
                class C35751 implements RequestDelegate {
                    C35751() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                            MessagesQuery.removeEmptyMessages(tLRPC$messages_Messages.messages);
                            ImageLoader.saveMessagesThumbs(tLRPC$messages_Messages.messages);
                            MessagesQuery.broadcastReplyMessages(tLRPC$messages_Messages.messages, hashMap2, tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, j4, false);
                            MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                            MessagesQuery.saveReplyMessages(hashMap2, tLRPC$messages_Messages.messages);
                        }
                    }
                }

                /* renamed from: org.telegram.messenger.query.MessagesQuery$7$2 */
                class C35762 implements RequestDelegate {
                    C35762() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                            MessagesQuery.removeEmptyMessages(tLRPC$messages_Messages.messages);
                            ImageLoader.saveMessagesThumbs(tLRPC$messages_Messages.messages);
                            MessagesQuery.broadcastReplyMessages(tLRPC$messages_Messages.messages, hashMap2, tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, j4, false);
                            MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                            MessagesQuery.saveReplyMessages(hashMap2, tLRPC$messages_Messages.messages);
                        }
                    }
                }

                public void run() {
                    try {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        Iterable arrayList4 = new ArrayList();
                        Iterable arrayList5 = new ArrayList();
                        SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data, mid, date FROM messages WHERE mid IN(%s)", new Object[]{stringBuilder2.toString()}), new Object[0]);
                        while (b.m12152a()) {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                                TLdeserialize.id = b.m12154b(1);
                                TLdeserialize.date = b.m12154b(2);
                                TLdeserialize.dialog_id = j4;
                                MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList4, arrayList5);
                                arrayList.add(TLdeserialize);
                                arrayList4.remove(Integer.valueOf(TLdeserialize.id));
                            }
                        }
                        b.m12155b();
                        if (!arrayList4.isEmpty()) {
                            MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList4), arrayList2);
                        }
                        if (!arrayList5.isEmpty()) {
                            MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList5), arrayList3);
                        }
                        MessagesQuery.broadcastReplyMessages(arrayList, hashMap2, arrayList2, arrayList3, j4, true);
                        if (!arrayList4.isEmpty()) {
                            TLObject tL_channels_getMessages;
                            if (i2 != 0) {
                                tL_channels_getMessages = new TL_channels_getMessages();
                                tL_channels_getMessages.channel = MessagesController.getInputChannel(i2);
                                tL_channels_getMessages.id = arrayList4;
                                ConnectionsManager.getInstance().sendRequest(tL_channels_getMessages, new C35751());
                                return;
                            }
                            tL_channels_getMessages = new TLRPC$TL_messages_getMessages();
                            tL_channels_getMessages.id = arrayList4;
                            ConnectionsManager.getInstance().sendRequest(tL_channels_getMessages, new C35762());
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    private static void removeEmptyMessages(ArrayList<Message> arrayList) {
        int i = 0;
        while (i < arrayList.size()) {
            Message message = (Message) arrayList.get(i);
            if (message == null || (message instanceof TLRPC$TL_messageEmpty) || (message.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                arrayList.remove(i);
                i--;
            }
            i++;
        }
    }

    private static void removeOffsetAfter(int i, int i2, ArrayList<MessageEntity> arrayList) {
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            MessageEntity messageEntity = (MessageEntity) arrayList.get(i3);
            if (messageEntity.offset > i) {
                messageEntity.offset -= i2;
            }
        }
    }

    private static void savePinnedMessage(final Message message) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().m12168d();
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO chat_pinned VALUES(?, ?, ?)");
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                    message.serializeToStream(nativeByteBuffer);
                    a.m12180d();
                    a.m12174a(1, message.to_id.channel_id);
                    a.m12174a(2, message.id);
                    a.m12177a(3, nativeByteBuffer);
                    a.m12178b();
                    nativeByteBuffer.reuse();
                    a.m12181e();
                    MessagesStorage.getInstance().getDatabase().m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    private static void saveReplyMessages(final HashMap<Integer, ArrayList<MessageObject>> hashMap, final ArrayList<Message> arrayList) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().m12168d();
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("UPDATE messages SET replydata = ? WHERE mid = ?");
                    for (int i = 0; i < arrayList.size(); i++) {
                        Message message = (Message) arrayList.get(i);
                        ArrayList arrayList = (ArrayList) hashMap.get(Integer.valueOf(message.id));
                        if (arrayList != null) {
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(message.getObjectSize());
                            message.serializeToStream(nativeByteBuffer);
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                                a.m12180d();
                                long id = (long) messageObject.getId();
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    id |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                a.m12177a(1, nativeByteBuffer);
                                a.m12175a(2, id);
                                a.m12178b();
                            }
                            nativeByteBuffer.reuse();
                        }
                    }
                    a.m12181e();
                    MessagesStorage.getInstance().getDatabase().m12169e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void sortEntities(ArrayList<MessageEntity> arrayList) {
        Collections.sort(arrayList, entityComparator);
    }
}
