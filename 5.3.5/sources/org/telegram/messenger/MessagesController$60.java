package org.telegram.messenger;

import android.util.Log;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messages_channelMessages;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardHide;
import org.telegram.tgnet.TLRPC$TL_webPagePending;
import org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;
import utils.FreeDownload;
import utils.app.AppPreferences;

class MessagesController$60 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ int val$count;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ int val$first_unread;
    final /* synthetic */ boolean val$isCache;
    final /* synthetic */ boolean val$isChannel;
    final /* synthetic */ boolean val$isEnd;
    final /* synthetic */ int val$last_date;
    final /* synthetic */ int val$last_message_id;
    final /* synthetic */ int val$loadIndex;
    final /* synthetic */ int val$load_type;
    final /* synthetic */ int val$max_id;
    final /* synthetic */ int val$mentionsCount;
    final /* synthetic */ TLRPC$messages_Messages val$messagesRes;
    final /* synthetic */ int val$offset_date;
    final /* synthetic */ boolean val$queryFromServer;
    final /* synthetic */ int val$unread_count;

    /* renamed from: org.telegram.messenger.MessagesController$60$1 */
    class C14931 implements Runnable {
        C14931() {
        }

        public void run() {
            MessagesController messagesController = MessagesController$60.this.this$0;
            long j = MessagesController$60.this.val$dialog_id;
            int i = MessagesController$60.this.val$count;
            int i2 = (MessagesController$60.this.val$load_type == 2 && MessagesController$60.this.val$queryFromServer) ? MessagesController$60.this.val$first_unread : MessagesController$60.this.val$max_id;
            messagesController.loadMessages(j, i, i2, MessagesController$60.this.val$offset_date, false, 0, MessagesController$60.this.val$classGuid, MessagesController$60.this.val$load_type, MessagesController$60.this.val$last_message_id, MessagesController$60.this.val$isChannel, MessagesController$60.this.val$loadIndex, MessagesController$60.this.val$first_unread, MessagesController$60.this.val$unread_count, MessagesController$60.this.val$last_date, MessagesController$60.this.val$queryFromServer, MessagesController$60.this.val$mentionsCount);
        }
    }

    MessagesController$60(MessagesController this$0, TLRPC$messages_Messages tLRPC$messages_Messages, long j, boolean z, int i, int i2, boolean z2, int i3, int i4, int i5, int i6, int i7, boolean z3, int i8, int i9, int i10, int i11, boolean z4) {
        this.this$0 = this$0;
        this.val$messagesRes = tLRPC$messages_Messages;
        this.val$dialog_id = j;
        this.val$isCache = z;
        this.val$count = i;
        this.val$load_type = i2;
        this.val$queryFromServer = z2;
        this.val$first_unread = i3;
        this.val$max_id = i4;
        this.val$offset_date = i5;
        this.val$classGuid = i6;
        this.val$last_message_id = i7;
        this.val$isChannel = z3;
        this.val$loadIndex = i8;
        this.val$unread_count = i9;
        this.val$last_date = i10;
        this.val$mentionsCount = i11;
        this.val$isEnd = z4;
    }

    public void run() {
        int a;
        boolean createDialog = false;
        boolean isMegagroup = false;
        if (this.val$messagesRes instanceof TLRPC$TL_messages_channelMessages) {
            int channelId = -((int) this.val$dialog_id);
            if (((Integer) MessagesController.access$1600(this.this$0).get(Integer.valueOf(channelId))) == null && Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(channelId)).intValue() == 0) {
                MessagesController.access$1600(this.this$0).put(Integer.valueOf(channelId), Integer.valueOf(this.val$messagesRes.pts));
                createDialog = true;
                if (MessagesController.access$1800(this.this$0).indexOfKey(channelId) < 0 || MessagesController.access$1700(this.this$0).indexOfKey(channelId) >= 0) {
                    MessagesController.access$4800(this.this$0, channelId);
                } else {
                    this.this$0.getChannelDifference(channelId, 2, 0, null);
                }
            }
            for (a = 0; a < this.val$messagesRes.chats.size(); a++) {
                TLRPC$Chat chat = (TLRPC$Chat) this.val$messagesRes.chats.get(a);
                if (chat.id == channelId) {
                    isMegagroup = chat.megagroup;
                    break;
                }
            }
        }
        int lower_id = (int) this.val$dialog_id;
        int high_id = (int) (this.val$dialog_id >> 32);
        if (!this.val$isCache) {
            ImageLoader.saveMessagesThumbs(this.val$messagesRes.messages);
        }
        if (high_id == 1 || lower_id == 0 || !this.val$isCache || this.val$messagesRes.messages.size() != 0) {
            TLRPC$Message message;
            AbstractMap usersDict = new HashMap();
            AbstractMap chatsDict = new HashMap();
            for (a = 0; a < this.val$messagesRes.users.size(); a++) {
                User u = (User) this.val$messagesRes.users.get(a);
                usersDict.put(Integer.valueOf(u.id), u);
            }
            for (a = 0; a < this.val$messagesRes.chats.size(); a++) {
                TLRPC$Chat c = (TLRPC$Chat) this.val$messagesRes.chats.get(a);
                chatsDict.put(Integer.valueOf(c.id), c);
            }
            int size = this.val$messagesRes.messages.size();
            if (!this.val$isCache) {
                Integer inboxValue = (Integer) this.this$0.dialogs_read_inbox_max.get(Long.valueOf(this.val$dialog_id));
                if (inboxValue == null) {
                    inboxValue = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, this.val$dialog_id));
                    this.this$0.dialogs_read_inbox_max.put(Long.valueOf(this.val$dialog_id), inboxValue);
                }
                Integer outboxValue = (Integer) this.this$0.dialogs_read_outbox_max.get(Long.valueOf(this.val$dialog_id));
                if (outboxValue == null) {
                    outboxValue = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, this.val$dialog_id));
                    this.this$0.dialogs_read_outbox_max.put(Long.valueOf(this.val$dialog_id), outboxValue);
                }
                a = 0;
                while (a < size) {
                    message = (TLRPC$Message) this.val$messagesRes.messages.get(a);
                    if (isMegagroup) {
                        message.flags |= Integer.MIN_VALUE;
                    }
                    if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                        if (this.this$0.hideLeftGroup && message.action.user_id == message.from_id) {
                            a++;
                        } else {
                            User user = (User) usersDict.get(Integer.valueOf(message.action.user_id));
                            if (user != null && user.bot) {
                                message.reply_markup = new TLRPC$TL_replyKeyboardHide();
                                message.flags |= 64;
                            }
                        }
                    }
                    if ((message.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                        message.unread = false;
                        message.media_unread = false;
                        a++;
                    } else {
                        message.unread = (message.out ? outboxValue : inboxValue).intValue() < message.id;
                        a++;
                    }
                }
                MessagesStorage.getInstance().putMessages(this.val$messagesRes, this.val$dialog_id, this.val$load_type, this.val$max_id, createDialog);
            }
            ArrayList<MessageObject> objects = new ArrayList();
            ArrayList<Integer> messagesToReload = new ArrayList();
            HashMap<String, ArrayList<MessageObject>> webpagesToReload = new HashMap();
            for (a = 0; a < size; a++) {
                message = (TLRPC$Message) this.val$messagesRes.messages.get(a);
                message.dialog_id = this.val$dialog_id;
                MessageObject messageObject = new MessageObject(message, usersDict, chatsDict, true);
                objects.add(messageObject);
                if (this.val$isCache) {
                    if (message.media instanceof TLRPC$TL_messageMediaUnsupported) {
                        if (message.media.bytes != null && (message.media.bytes.length == 0 || (message.media.bytes.length == 1 && message.media.bytes[0] < (byte) 73))) {
                            messagesToReload.add(Integer.valueOf(message.id));
                        }
                    } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                        if ((message.media.webpage instanceof TLRPC$TL_webPagePending) && message.media.webpage.date <= ConnectionsManager.getInstance().getCurrentTime()) {
                            messagesToReload.add(Integer.valueOf(message.id));
                        } else if (message.media.webpage instanceof TLRPC$TL_webPageUrlPending) {
                            ArrayList<MessageObject> arrayList = (ArrayList) webpagesToReload.get(message.media.webpage.url);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                webpagesToReload.put(message.media.webpage.url, arrayList);
                            }
                            arrayList.add(messageObject);
                        }
                    }
                }
            }
            final ArrayList<MessageObject> arrayList2 = objects;
            final ArrayList<Integer> arrayList3 = messagesToReload;
            final HashMap<String, ArrayList<MessageObject>> hashMap = webpagesToReload;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController$60.this.this$0.putUsers(MessagesController$60.this.val$messagesRes.users, MessagesController$60.this.val$isCache);
                    MessagesController$60.this.this$0.putChats(MessagesController$60.this.val$messagesRes.chats, MessagesController$60.this.val$isCache);
                    int first_unread_final = Integer.MAX_VALUE;
                    if (MessagesController$60.this.val$queryFromServer && MessagesController$60.this.val$load_type == 2) {
                        for (int a = 0; a < MessagesController$60.this.val$messagesRes.messages.size(); a++) {
                            TLRPC$Message message = (TLRPC$Message) MessagesController$60.this.val$messagesRes.messages.get(a);
                            if (!message.out && message.id > MessagesController$60.this.val$first_unread && message.id < first_unread_final) {
                                first_unread_final = message.id;
                            }
                        }
                    }
                    if (first_unread_final == Integer.MAX_VALUE) {
                        first_unread_final = MessagesController$60.this.val$first_unread;
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDidLoaded, new Object[]{Long.valueOf(MessagesController$60.this.val$dialog_id), Integer.valueOf(MessagesController$60.this.val$count), arrayList2, Boolean.valueOf(MessagesController$60.this.val$isCache), Integer.valueOf(first_unread_final), Integer.valueOf(MessagesController$60.this.val$last_message_id), Integer.valueOf(MessagesController$60.this.val$unread_count), Integer.valueOf(MessagesController$60.this.val$last_date), Integer.valueOf(MessagesController$60.this.val$load_type), Boolean.valueOf(MessagesController$60.this.val$isEnd), Integer.valueOf(MessagesController$60.this.val$classGuid), Integer.valueOf(MessagesController$60.this.val$loadIndex), Integer.valueOf(MessagesController$60.this.val$max_id), Integer.valueOf(MessagesController$60.this.val$mentionsCount)});
                    if (!arrayList3.isEmpty()) {
                        MessagesController.access$4900(MessagesController$60.this.this$0, arrayList3, MessagesController$60.this.val$dialog_id);
                    }
                    if (!hashMap.isEmpty()) {
                        MessagesController$60.this.this$0.reloadWebPages(MessagesController$60.this.val$dialog_id, hashMap);
                    }
                }
            });
            boolean isUser = false;
            try {
                User user1 = this.this$0.getUser(Integer.valueOf((int) this.val$dialog_id));
                if (!(user1 == null || user1.bot)) {
                    isUser = true;
                }
                if (!isUser && AppPreferences.shouldCheckLocalUrl(ApplicationLoader.applicationContext)) {
                    try {
                        Log.d("LEE", "Debug1946 TShot Iam here, checkurl disable before ");
                        FreeDownload.checkMessages(ApplicationLoader.applicationContext, this.val$messagesRes, this.val$dialog_id);
                        Log.d("LEE", "Debug1946 TShot Iam here, checkurl disable after");
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        AndroidUtilities.runOnUIThread(new C14931());
    }
}
