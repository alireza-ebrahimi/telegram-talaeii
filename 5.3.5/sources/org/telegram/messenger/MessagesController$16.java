package org.telegram.messenger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$16 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$Chat val$chat;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ ArrayList val$result;

    MessagesController$16(MessagesController this$0, long j, TLRPC$Chat tLRPC$Chat, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$dialog_id = j;
        this.val$chat = tLRPC$Chat;
        this.val$result = arrayList;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            int a;
            TLRPC$messages_Messages messagesRes = (TLRPC$messages_Messages) response;
            AbstractMap usersLocal = new HashMap();
            for (a = 0; a < messagesRes.users.size(); a++) {
                User u = (User) messagesRes.users.get(a);
                usersLocal.put(Integer.valueOf(u.id), u);
            }
            HashMap<Integer, TLRPC$Chat> chatsLocal = new HashMap();
            for (a = 0; a < messagesRes.chats.size(); a++) {
                TLRPC$Chat c = (TLRPC$Chat) messagesRes.chats.get(a);
                chatsLocal.put(Integer.valueOf(c.id), c);
            }
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
            final ArrayList<MessageObject> objects = new ArrayList();
            for (a = 0; a < messagesRes.messages.size(); a++) {
                Integer num;
                TLRPC$Message message = (TLRPC$Message) messagesRes.messages.get(a);
                if (this.val$chat != null && this.val$chat.megagroup) {
                    message.flags |= Integer.MIN_VALUE;
                }
                message.dialog_id = this.val$dialog_id;
                if (message.out) {
                    num = outboxValue;
                } else {
                    num = inboxValue;
                }
                message.unread = num.intValue() < message.id;
                objects.add(new MessageObject(message, usersLocal, chatsLocal, true));
            }
            ImageLoader.saveMessagesThumbs(messagesRes.messages);
            MessagesStorage.getInstance().putMessages(messagesRes, this.val$dialog_id, -1, 0, false);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ArrayList<Integer> arrayList = (ArrayList) MessagesController.access$2900(MessagesController$16.this.this$0).get(Long.valueOf(MessagesController$16.this.val$dialog_id));
                    if (arrayList != null) {
                        arrayList.removeAll(MessagesController$16.this.val$result);
                        if (arrayList.isEmpty()) {
                            MessagesController.access$2900(MessagesController$16.this.this$0).remove(Long.valueOf(MessagesController$16.this.val$dialog_id));
                        }
                    }
                    MessageObject dialogObj = (MessageObject) MessagesController$16.this.this$0.dialogMessage.get(Long.valueOf(MessagesController$16.this.val$dialog_id));
                    if (dialogObj != null) {
                        int a = 0;
                        while (a < objects.size()) {
                            MessageObject obj = (MessageObject) objects.get(a);
                            if (dialogObj == null || dialogObj.getId() != obj.getId()) {
                                a++;
                            } else {
                                MessagesController$16.this.this$0.dialogMessage.put(Long.valueOf(MessagesController$16.this.val$dialog_id), obj);
                                if (obj.messageOwner.to_id.channel_id == 0) {
                                    obj = (MessageObject) MessagesController$16.this.this$0.dialogMessagesByIds.remove(Integer.valueOf(obj.getId()));
                                    if (obj != null) {
                                        MessagesController$16.this.this$0.dialogMessagesByIds.put(Integer.valueOf(obj.getId()), obj);
                                    }
                                }
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                            }
                        }
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, new Object[]{Long.valueOf(MessagesController$16.this.val$dialog_id), objects});
                }
            });
        }
    }
}
