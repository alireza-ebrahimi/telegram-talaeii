package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedMessage;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardHide;
import org.telegram.tgnet.TLRPC$TL_updateMessageID;
import org.telegram.tgnet.TLRPC$TL_updates_difference;
import org.telegram.tgnet.TLRPC$TL_updates_differenceEmpty;
import org.telegram.tgnet.TLRPC$TL_updates_differenceSlice;
import org.telegram.tgnet.TLRPC$TL_updates_differenceTooLong;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$updates_Difference;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$113 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$date;
    final /* synthetic */ int val$qts;

    MessagesController$113(MessagesController this$0, int i, int i2) {
        this.this$0 = this$0;
        this.val$date = i;
        this.val$qts = i2;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            final TLRPC$updates_Difference res = (TLRPC$updates_Difference) response;
            if (res instanceof TLRPC$TL_updates_differenceTooLong) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MessagesController.access$2800(MessagesController$113.this.this$0).clear();
                        MessagesController.access$2400(MessagesController$113.this.this$0).clear();
                        MessagesController.access$5000(MessagesController$113.this.this$0, true, MessagesStorage.lastSeqValue, res.pts, MessagesController$113.this.val$date, MessagesController$113.this.val$qts);
                    }
                });
                return;
            }
            int a;
            if (res instanceof TLRPC$TL_updates_differenceSlice) {
                this.this$0.getDifference(res.intermediate_state.pts, res.intermediate_state.date, res.intermediate_state.qts, true);
            }
            final HashMap<Integer, User> usersDict = new HashMap();
            final HashMap<Integer, TLRPC$Chat> chatsDict = new HashMap();
            for (a = 0; a < res.users.size(); a++) {
                User user = (User) res.users.get(a);
                usersDict.put(Integer.valueOf(user.id), user);
            }
            for (a = 0; a < res.chats.size(); a++) {
                TLRPC$Chat chat = (TLRPC$Chat) res.chats.get(a);
                chatsDict.put(Integer.valueOf(chat.id), chat);
            }
            final ArrayList<TLRPC$TL_updateMessageID> msgUpdates = new ArrayList();
            if (!res.other_updates.isEmpty()) {
                a = 0;
                while (a < res.other_updates.size()) {
                    TLRPC$Update upd = (TLRPC$Update) res.other_updates.get(a);
                    if (upd instanceof TLRPC$TL_updateMessageID) {
                        msgUpdates.add((TLRPC$TL_updateMessageID) upd);
                        res.other_updates.remove(a);
                        a--;
                    } else if (MessagesController.access$000(this.this$0, upd) == 2) {
                        int channelId = MessagesController.access$100(this.this$0, upd);
                        Integer channelPts = (Integer) MessagesController.access$1600(this.this$0).get(Integer.valueOf(channelId));
                        if (channelPts == null) {
                            channelPts = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(channelId));
                            if (channelPts.intValue() != 0) {
                                MessagesController.access$1600(this.this$0).put(Integer.valueOf(channelId), channelPts);
                            }
                        }
                        if (channelPts.intValue() != 0 && upd.pts <= channelPts.intValue()) {
                            res.other_updates.remove(a);
                            a--;
                        }
                    }
                    a++;
                }
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.access$2800(MessagesController$113.this.this$0).clear();
                    MessagesController.access$2400(MessagesController$113.this.this$0).clear();
                    MessagesController$113.this.this$0.putUsers(res.users, false);
                    MessagesController$113.this.this$0.putChats(res.chats, false);
                }
            });
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$113$3$2 */
                class C14512 implements Runnable {
                    C14512() {
                    }

                    public void run() {
                        int a;
                        if (!(res.new_messages.isEmpty() && res.new_encrypted_messages.isEmpty())) {
                            final HashMap<Long, ArrayList<MessageObject>> messages = new HashMap();
                            for (int b = 0; b < res.new_encrypted_messages.size(); b++) {
                                ArrayList<TLRPC$Message> decryptedMessages = SecretChatHelper.getInstance().decryptMessage((TLRPC$EncryptedMessage) res.new_encrypted_messages.get(b));
                                if (!(decryptedMessages == null || decryptedMessages.isEmpty())) {
                                    res.new_messages.addAll(decryptedMessages);
                                }
                            }
                            ImageLoader.saveMessagesThumbs(res.new_messages);
                            final ArrayList<MessageObject> pushMessages = new ArrayList();
                            int clientUserId = UserConfig.getClientUserId();
                            for (a = 0; a < res.new_messages.size(); a++) {
                                TLRPC$Message message = (TLRPC$Message) res.new_messages.get(a);
                                if (message.dialog_id == 0) {
                                    if (message.to_id.chat_id != 0) {
                                        message.dialog_id = (long) (-message.to_id.chat_id);
                                    } else {
                                        if (message.to_id.user_id == UserConfig.getClientUserId()) {
                                            message.to_id.user_id = message.from_id;
                                        }
                                        message.dialog_id = (long) message.to_id.user_id;
                                    }
                                }
                                if (((int) message.dialog_id) != 0) {
                                    if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                                        if (MessagesController$113.this.this$0.hideLeftGroup && message.action.user_id == message.from_id) {
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
                                    } else {
                                        boolean z;
                                        ConcurrentHashMap<Long, Integer> read_max = message.out ? MessagesController$113.this.this$0.dialogs_read_outbox_max : MessagesController$113.this.this$0.dialogs_read_inbox_max;
                                        Integer value = (Integer) read_max.get(Long.valueOf(message.dialog_id));
                                        if (value == null) {
                                            value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                                            read_max.put(Long.valueOf(message.dialog_id), value);
                                        }
                                        if (value.intValue() < message.id) {
                                            z = true;
                                        } else {
                                            z = false;
                                        }
                                        message.unread = z;
                                    }
                                }
                                if (message.dialog_id == ((long) clientUserId)) {
                                    message.unread = false;
                                    message.media_unread = false;
                                    message.out = true;
                                }
                                MessageObject obj = new MessageObject(message, usersDict, chatsDict, MessagesController.access$1000(MessagesController$113.this.this$0).contains(Long.valueOf(message.dialog_id)));
                                if (!obj.isOut() && obj.isUnread()) {
                                    pushMessages.add(obj);
                                }
                                ArrayList<MessageObject> arr = (ArrayList) messages.get(Long.valueOf(message.dialog_id));
                                if (arr == null) {
                                    arr = new ArrayList();
                                    messages.put(Long.valueOf(message.dialog_id), arr);
                                }
                                arr.add(obj);
                            }
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    for (Entry<Long, ArrayList<MessageObject>> pair : messages.entrySet()) {
                                        ArrayList<MessageObject> value = (ArrayList) pair.getValue();
                                        MessagesController$113.this.this$0.updateInterfaceWithMessages(((Long) pair.getKey()).longValue(), value);
                                    }
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                }
                            });
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                /* renamed from: org.telegram.messenger.MessagesController$113$3$2$2$1 */
                                class C14491 implements Runnable {
                                    C14491() {
                                    }

                                    public void run() {
                                        NotificationsController.getInstance().processNewMessages(pushMessages, !(res instanceof TLRPC$TL_updates_differenceSlice));
                                    }
                                }

                                public void run() {
                                    if (!pushMessages.isEmpty()) {
                                        AndroidUtilities.runOnUIThread(new C14491());
                                    }
                                    MessagesStorage.getInstance().putMessages(res.new_messages, true, false, false, MediaController.getInstance().getAutodownloadMask());
                                }
                            });
                            SecretChatHelper.getInstance().processPendingEncMessages();
                        }
                        if (!res.other_updates.isEmpty()) {
                            MessagesController$113.this.this$0.processUpdateArray(res.other_updates, res.users, res.chats, true);
                        }
                        if (res instanceof TLRPC$TL_updates_difference) {
                            MessagesController$113.this.this$0.gettingDifference = false;
                            MessagesStorage.lastSeqValue = res.state.seq;
                            MessagesStorage.lastDateValue = res.state.date;
                            MessagesStorage.lastPtsValue = res.state.pts;
                            MessagesStorage.lastQtsValue = res.state.qts;
                            ConnectionsManager.getInstance().setIsUpdating(false);
                            for (a = 0; a < 3; a++) {
                                MessagesController.access$6000(MessagesController$113.this.this$0, a, 1);
                            }
                        } else if (res instanceof TLRPC$TL_updates_differenceSlice) {
                            MessagesStorage.lastDateValue = res.intermediate_state.date;
                            MessagesStorage.lastPtsValue = res.intermediate_state.pts;
                            MessagesStorage.lastQtsValue = res.intermediate_state.qts;
                        } else if (res instanceof TLRPC$TL_updates_differenceEmpty) {
                            MessagesController$113.this.this$0.gettingDifference = false;
                            MessagesStorage.lastSeqValue = res.seq;
                            MessagesStorage.lastDateValue = res.date;
                            ConnectionsManager.getInstance().setIsUpdating(false);
                            for (a = 0; a < 3; a++) {
                                MessagesController.access$6000(MessagesController$113.this.this$0, a, 1);
                            }
                        }
                        MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
                        FileLog.e("received difference with date = " + MessagesStorage.lastDateValue + " pts = " + MessagesStorage.lastPtsValue + " seq = " + MessagesStorage.lastSeqValue + " messages = " + res.new_messages.size() + " users = " + res.users.size() + " chats = " + res.chats.size() + " other updates = " + res.other_updates.size());
                    }
                }

                public void run() {
                    MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, false);
                    if (!msgUpdates.isEmpty()) {
                        final HashMap<Integer, long[]> corrected = new HashMap();
                        for (int a = 0; a < msgUpdates.size(); a++) {
                            TLRPC$TL_updateMessageID update = (TLRPC$TL_updateMessageID) msgUpdates.get(a);
                            long[] ids = MessagesStorage.getInstance().updateMessageStateAndId(update.random_id, null, update.id, 0, false, 0);
                            if (ids != null) {
                                corrected.put(Integer.valueOf(update.id), ids);
                            }
                        }
                        if (!corrected.isEmpty()) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    for (Entry<Integer, long[]> entry : corrected.entrySet()) {
                                        Integer newId = (Integer) entry.getKey();
                                        SendMessagesHelper.getInstance().processSentMessage(Integer.valueOf((int) ((long[]) entry.getValue())[1]).intValue());
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, new Object[]{oldId, newId, null, Long.valueOf(ids[0])});
                                    }
                                }
                            });
                        }
                    }
                    Utilities.stageQueue.postRunnable(new C14512());
                }
            });
            return;
        }
        this.this$0.gettingDifference = false;
        ConnectionsManager.getInstance().setIsUpdating(false);
    }
}
