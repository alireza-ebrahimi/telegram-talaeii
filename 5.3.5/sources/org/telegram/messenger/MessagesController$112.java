package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_updateMessageID;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifference;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceEmpty;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$updates_ChannelDifference;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$112 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$channelId;
    final /* synthetic */ int val$newDialogType;
    final /* synthetic */ long val$newTaskId;

    MessagesController$112(MessagesController this$0, int i, int i2, long j) {
        this.this$0 = this$0;
        this.val$channelId = i;
        this.val$newDialogType = i2;
        this.val$newTaskId = j;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            int a;
            final TLRPC$updates_ChannelDifference res = (TLRPC$updates_ChannelDifference) response;
            final HashMap<Integer, User> usersDict = new HashMap();
            for (a = 0; a < res.users.size(); a++) {
                User user = (User) res.users.get(a);
                usersDict.put(Integer.valueOf(user.id), user);
            }
            TLRPC$Chat channel = null;
            for (a = 0; a < res.chats.size(); a++) {
                TLRPC$Chat chat = (TLRPC$Chat) res.chats.get(a);
                if (chat.id == this.val$channelId) {
                    channel = chat;
                    break;
                }
            }
            final TLRPC$Chat channelFinal = channel;
            final ArrayList<TLRPC$TL_updateMessageID> msgUpdates = new ArrayList();
            if (!res.other_updates.isEmpty()) {
                a = 0;
                while (a < res.other_updates.size()) {
                    TLRPC$Update upd = (TLRPC$Update) res.other_updates.get(a);
                    if (upd instanceof TLRPC$TL_updateMessageID) {
                        msgUpdates.add((TLRPC$TL_updateMessageID) upd);
                        res.other_updates.remove(a);
                        a--;
                    }
                    a++;
                }
            }
            MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController$112.this.this$0.putUsers(res.users, false);
                    MessagesController$112.this.this$0.putChats(res.chats, false);
                }
            });
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$112$2$2 */
                class C14422 implements Runnable {
                    C14422() {
                    }

                    public void run() {
                        long dialog_id;
                        Integer inboxValue;
                        Integer outboxValue;
                        int a;
                        TLRPC$Message message;
                        Integer num;
                        boolean z;
                        if ((res instanceof TLRPC$TL_updates_channelDifference) || (res instanceof TLRPC$TL_updates_channelDifferenceEmpty)) {
                            if (!res.new_messages.isEmpty()) {
                                final HashMap<Long, ArrayList<MessageObject>> messages = new HashMap();
                                ImageLoader.saveMessagesThumbs(res.new_messages);
                                final ArrayList<MessageObject> pushMessages = new ArrayList();
                                dialog_id = (long) (-MessagesController$112.this.val$channelId);
                                inboxValue = (Integer) MessagesController$112.this.this$0.dialogs_read_inbox_max.get(Long.valueOf(dialog_id));
                                if (inboxValue == null) {
                                    inboxValue = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, dialog_id));
                                    MessagesController$112.this.this$0.dialogs_read_inbox_max.put(Long.valueOf(dialog_id), inboxValue);
                                }
                                outboxValue = (Integer) MessagesController$112.this.this$0.dialogs_read_outbox_max.get(Long.valueOf(dialog_id));
                                if (outboxValue == null) {
                                    outboxValue = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, dialog_id));
                                    MessagesController$112.this.this$0.dialogs_read_outbox_max.put(Long.valueOf(dialog_id), outboxValue);
                                }
                                for (a = 0; a < res.new_messages.size(); a++) {
                                    MessageObject obj;
                                    long uid;
                                    ArrayList<MessageObject> arr;
                                    message = (TLRPC$Message) res.new_messages.get(a);
                                    if (channelFinal == null || !channelFinal.left) {
                                        if (message.out) {
                                            num = outboxValue;
                                        } else {
                                            num = inboxValue;
                                        }
                                        if (num.intValue() < message.id && !(message.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                                            z = true;
                                            message.unread = z;
                                            if (channelFinal != null && channelFinal.megagroup) {
                                                message.flags |= Integer.MIN_VALUE;
                                            }
                                            obj = new MessageObject(message, usersDict, MessagesController.access$1000(MessagesController$112.this.this$0).contains(Long.valueOf(dialog_id)));
                                            if (!obj.isOut() && obj.isUnread()) {
                                                pushMessages.add(obj);
                                            }
                                            uid = (long) (-MessagesController$112.this.val$channelId);
                                            arr = (ArrayList) messages.get(Long.valueOf(uid));
                                            if (arr == null) {
                                                arr = new ArrayList();
                                                messages.put(Long.valueOf(uid), arr);
                                            }
                                            arr.add(obj);
                                        }
                                    }
                                    z = false;
                                    message.unread = z;
                                    message.flags |= Integer.MIN_VALUE;
                                    obj = new MessageObject(message, usersDict, MessagesController.access$1000(MessagesController$112.this.this$0).contains(Long.valueOf(dialog_id)));
                                    pushMessages.add(obj);
                                    uid = (long) (-MessagesController$112.this.val$channelId);
                                    arr = (ArrayList) messages.get(Long.valueOf(uid));
                                    if (arr == null) {
                                        arr = new ArrayList();
                                        messages.put(Long.valueOf(uid), arr);
                                    }
                                    arr.add(obj);
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        for (Entry<Long, ArrayList<MessageObject>> pair : messages.entrySet()) {
                                            ArrayList<MessageObject> value = (ArrayList) pair.getValue();
                                            MessagesController$112.this.this$0.updateInterfaceWithMessages(((Long) pair.getKey()).longValue(), value);
                                        }
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                    }
                                });
                                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                    /* renamed from: org.telegram.messenger.MessagesController$112$2$2$2$1 */
                                    class C14401 implements Runnable {
                                        C14401() {
                                        }

                                        public void run() {
                                            NotificationsController.getInstance().processNewMessages(pushMessages, true);
                                        }
                                    }

                                    public void run() {
                                        if (!pushMessages.isEmpty()) {
                                            AndroidUtilities.runOnUIThread(new C14401());
                                        }
                                        MessagesStorage.getInstance().putMessages(res.new_messages, true, false, false, MediaController.getInstance().getAutodownloadMask());
                                    }
                                });
                            }
                            if (!res.other_updates.isEmpty()) {
                                MessagesController$112.this.this$0.processUpdateArray(res.other_updates, res.users, res.chats, true);
                            }
                            MessagesController.access$6200(MessagesController$112.this.this$0, MessagesController$112.this.val$channelId, 1);
                            MessagesStorage.getInstance().saveChannelPts(MessagesController$112.this.val$channelId, res.pts);
                        } else if (res instanceof TLRPC$TL_updates_channelDifferenceTooLong) {
                            dialog_id = (long) (-MessagesController$112.this.val$channelId);
                            inboxValue = (Integer) MessagesController$112.this.this$0.dialogs_read_inbox_max.get(Long.valueOf(dialog_id));
                            if (inboxValue == null) {
                                inboxValue = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, dialog_id));
                                MessagesController$112.this.this$0.dialogs_read_inbox_max.put(Long.valueOf(dialog_id), inboxValue);
                            }
                            outboxValue = (Integer) MessagesController$112.this.this$0.dialogs_read_outbox_max.get(Long.valueOf(dialog_id));
                            if (outboxValue == null) {
                                outboxValue = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, dialog_id));
                                MessagesController$112.this.this$0.dialogs_read_outbox_max.put(Long.valueOf(dialog_id), outboxValue);
                            }
                            for (a = 0; a < res.messages.size(); a++) {
                                message = (TLRPC$Message) res.messages.get(a);
                                message.dialog_id = (long) (-MessagesController$112.this.val$channelId);
                                if (!(message.action instanceof TLRPC$TL_messageActionChannelCreate) && (channelFinal == null || !channelFinal.left)) {
                                    if (message.out) {
                                        num = outboxValue;
                                    } else {
                                        num = inboxValue;
                                    }
                                    if (num.intValue() < message.id) {
                                        z = true;
                                        message.unread = z;
                                        if (channelFinal != null && channelFinal.megagroup) {
                                            message.flags |= Integer.MIN_VALUE;
                                        }
                                    }
                                }
                                z = false;
                                message.unread = z;
                                message.flags |= Integer.MIN_VALUE;
                            }
                            MessagesStorage.getInstance().overwriteChannel(MessagesController$112.this.val$channelId, (TLRPC$TL_updates_channelDifferenceTooLong) res, MessagesController$112.this.val$newDialogType);
                        }
                        MessagesController.access$1500(MessagesController$112.this.this$0).remove(Integer.valueOf(MessagesController$112.this.val$channelId));
                        MessagesController.access$1600(MessagesController$112.this.this$0).put(Integer.valueOf(MessagesController$112.this.val$channelId), Integer.valueOf(res.pts));
                        if ((res.flags & 2) != 0) {
                            MessagesController.access$1700(MessagesController$112.this.this$0).put(MessagesController$112.this.val$channelId, ((int) (System.currentTimeMillis() / 1000)) + res.timeout);
                        }
                        if (!res.isFinal) {
                            MessagesController.access$4800(MessagesController$112.this.this$0, MessagesController$112.this.val$channelId);
                        }
                        FileLog.e("received channel difference with pts = " + res.pts + " channelId = " + MessagesController$112.this.val$channelId);
                        FileLog.e("new_messages = " + res.new_messages.size() + " messages = " + res.messages.size() + " users = " + res.users.size() + " chats = " + res.chats.size() + " other updates = " + res.other_updates.size());
                        if (MessagesController$112.this.val$newTaskId != 0) {
                            MessagesStorage.getInstance().removePendingTask(MessagesController$112.this.val$newTaskId);
                        }
                    }
                }

                public void run() {
                    if (!msgUpdates.isEmpty()) {
                        final HashMap<Integer, long[]> corrected = new HashMap();
                        Iterator it = msgUpdates.iterator();
                        while (it.hasNext()) {
                            TLRPC$TL_updateMessageID update = (TLRPC$TL_updateMessageID) it.next();
                            long[] ids = MessagesStorage.getInstance().updateMessageStateAndId(update.random_id, null, update.id, 0, false, MessagesController$112.this.val$channelId);
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
                    Utilities.stageQueue.postRunnable(new C14422());
                }
            });
            return;
        }
        final TLRPC$TL_error tLRPC$TL_error = error;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.access$2500(MessagesController$112.this.this$0, tLRPC$TL_error.text, MessagesController$112.this.val$channelId);
            }
        });
        MessagesController.access$1500(this.this$0).remove(Integer.valueOf(this.val$channelId));
        if (this.val$newTaskId != 0) {
            MessagesStorage.getInstance().removePendingTask(this.val$newTaskId);
        }
    }
}
