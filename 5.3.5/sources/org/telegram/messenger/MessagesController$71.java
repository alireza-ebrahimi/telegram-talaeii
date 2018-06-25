package org.telegram.messenger;

import java.util.HashMap;
import java.util.Map.Entry;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$71 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$messages_Dialogs val$dialogsRes;

    MessagesController$71(MessagesController this$0, TLRPC$messages_Dialogs tLRPC$messages_Dialogs) {
        this.this$0 = this$0;
        this.val$dialogsRes = tLRPC$messages_Dialogs;
    }

    public void run() {
        int a;
        TLRPC$Chat chat;
        final HashMap<Long, TLRPC$TL_dialog> new_dialogs_dict = new HashMap();
        final HashMap<Long, MessageObject> new_dialogMessage = new HashMap();
        HashMap<Integer, User> usersDict = new HashMap();
        HashMap<Integer, TLRPC$Chat> chatsDict = new HashMap();
        final HashMap<Long, Integer> dialogsToUpdate = new HashMap();
        for (a = 0; a < this.val$dialogsRes.users.size(); a++) {
            User u = (User) this.val$dialogsRes.users.get(a);
            usersDict.put(Integer.valueOf(u.id), u);
        }
        for (a = 0; a < this.val$dialogsRes.chats.size(); a++) {
            TLRPC$Chat c = (TLRPC$Chat) this.val$dialogsRes.chats.get(a);
            chatsDict.put(Integer.valueOf(c.id), c);
        }
        for (a = 0; a < this.val$dialogsRes.messages.size(); a++) {
            TLRPC$Message message = (TLRPC$Message) this.val$dialogsRes.messages.get(a);
            MessageObject messageObject;
            if (message.to_id.channel_id != 0) {
                chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(message.to_id.channel_id));
                if (chat != null && chat.left) {
                }
                messageObject = new MessageObject(message, usersDict, chatsDict, false);
                new_dialogMessage.put(Long.valueOf(messageObject.getDialogId()), messageObject);
            } else {
                if (message.to_id.chat_id != 0) {
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(message.to_id.chat_id));
                    if (!(chat == null || chat.migrated_to == null)) {
                    }
                }
                messageObject = new MessageObject(message, usersDict, chatsDict, false);
                new_dialogMessage.put(Long.valueOf(messageObject.getDialogId()), messageObject);
            }
        }
        for (a = 0; a < this.val$dialogsRes.dialogs.size(); a++) {
            TLRPC$TL_dialog d = (TLRPC$TL_dialog) this.val$dialogsRes.dialogs.get(a);
            if (d.id == 0) {
                if (d.peer.user_id != 0) {
                    d.id = (long) d.peer.user_id;
                } else if (d.peer.chat_id != 0) {
                    d.id = (long) (-d.peer.chat_id);
                } else if (d.peer.channel_id != 0) {
                    d.id = (long) (-d.peer.channel_id);
                }
            }
            MessageObject mess;
            Integer value;
            if (DialogObject.isChannel(d)) {
                chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-((int) d.id)));
                if (chat != null && chat.left) {
                }
                if (d.last_message_date == 0) {
                    mess = (MessageObject) new_dialogMessage.get(Long.valueOf(d.id));
                    if (mess != null) {
                        d.last_message_date = mess.messageOwner.date;
                    }
                }
                new_dialogs_dict.put(Long.valueOf(d.id), d);
                dialogsToUpdate.put(Long.valueOf(d.id), Integer.valueOf(d.unread_count));
                value = (Integer) this.this$0.dialogs_read_inbox_max.get(Long.valueOf(d.id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.this$0.dialogs_read_inbox_max.put(Long.valueOf(d.id), Integer.valueOf(Math.max(value.intValue(), d.read_inbox_max_id)));
                value = (Integer) this.this$0.dialogs_read_outbox_max.get(Long.valueOf(d.id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.this$0.dialogs_read_outbox_max.put(Long.valueOf(d.id), Integer.valueOf(Math.max(value.intValue(), d.read_outbox_max_id)));
            } else {
                if (((int) d.id) < 0) {
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-((int) d.id)));
                    if (!(chat == null || chat.migrated_to == null)) {
                    }
                }
                if (d.last_message_date == 0) {
                    mess = (MessageObject) new_dialogMessage.get(Long.valueOf(d.id));
                    if (mess != null) {
                        d.last_message_date = mess.messageOwner.date;
                    }
                }
                new_dialogs_dict.put(Long.valueOf(d.id), d);
                dialogsToUpdate.put(Long.valueOf(d.id), Integer.valueOf(d.unread_count));
                value = (Integer) this.this$0.dialogs_read_inbox_max.get(Long.valueOf(d.id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.this$0.dialogs_read_inbox_max.put(Long.valueOf(d.id), Integer.valueOf(Math.max(value.intValue(), d.read_inbox_max_id)));
                value = (Integer) this.this$0.dialogs_read_outbox_max.get(Long.valueOf(d.id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.this$0.dialogs_read_outbox_max.put(Long.valueOf(d.id), Integer.valueOf(Math.max(value.intValue(), d.read_outbox_max_id)));
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController$71.this.this$0.putUsers(MessagesController$71.this.val$dialogsRes.users, true);
                MessagesController$71.this.this$0.putChats(MessagesController$71.this.val$dialogsRes.chats, true);
                for (Entry<Long, TLRPC$TL_dialog> pair : new_dialogs_dict.entrySet()) {
                    Long key = (Long) pair.getKey();
                    TLRPC$TL_dialog value = (TLRPC$TL_dialog) pair.getValue();
                    TLRPC$TL_dialog currentDialog = (TLRPC$TL_dialog) MessagesController$71.this.this$0.dialogs_dict.get(key);
                    MessageObject messageObject;
                    if (currentDialog == null) {
                        MessagesController messagesController = MessagesController$71.this.this$0;
                        messagesController.nextDialogsCacheOffset++;
                        MessagesController$71.this.this$0.dialogs_dict.put(key, value);
                        messageObject = (MessageObject) new_dialogMessage.get(Long.valueOf(value.id));
                        MessagesController$71.this.this$0.dialogMessage.put(key, messageObject);
                        if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                            MessagesController$71.this.this$0.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                            if (messageObject.messageOwner.random_id != 0) {
                                MessagesController$71.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                            }
                        }
                    } else {
                        currentDialog.unread_count = value.unread_count;
                        if (currentDialog.unread_mentions_count != value.unread_mentions_count) {
                            currentDialog.unread_mentions_count = value.unread_mentions_count;
                            if (MessagesController.access$5600(MessagesController$71.this.this$0).contains(Long.valueOf(currentDialog.id))) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMentionsCount, new Object[]{Long.valueOf(currentDialog.id), Integer.valueOf(currentDialog.unread_mentions_count)});
                            }
                        }
                        MessageObject oldMsg = (MessageObject) MessagesController$71.this.this$0.dialogMessage.get(key);
                        if (oldMsg != null && currentDialog.top_message <= 0) {
                            MessageObject newMsg = (MessageObject) new_dialogMessage.get(Long.valueOf(value.id));
                            if (oldMsg.deleted || newMsg == null || newMsg.messageOwner.date > oldMsg.messageOwner.date) {
                                MessagesController$71.this.this$0.dialogs_dict.put(key, value);
                                MessagesController$71.this.this$0.dialogMessage.put(key, newMsg);
                                if (newMsg != null && newMsg.messageOwner.to_id.channel_id == 0) {
                                    MessagesController$71.this.this$0.dialogMessagesByIds.put(Integer.valueOf(newMsg.getId()), newMsg);
                                    if (newMsg.messageOwner.random_id != 0) {
                                        MessagesController$71.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(newMsg.messageOwner.random_id), newMsg);
                                    }
                                }
                                MessagesController$71.this.this$0.dialogMessagesByIds.remove(Integer.valueOf(oldMsg.getId()));
                                if (oldMsg.messageOwner.random_id != 0) {
                                    MessagesController$71.this.this$0.dialogMessagesByRandomIds.remove(Long.valueOf(oldMsg.messageOwner.random_id));
                                }
                            }
                        } else if ((oldMsg != null && oldMsg.deleted) || value.top_message > currentDialog.top_message) {
                            MessagesController$71.this.this$0.dialogs_dict.put(key, value);
                            messageObject = (MessageObject) new_dialogMessage.get(Long.valueOf(value.id));
                            MessagesController$71.this.this$0.dialogMessage.put(key, messageObject);
                            if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                MessagesController$71.this.this$0.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                if (messageObject.messageOwner.random_id != 0) {
                                    MessagesController$71.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                }
                            }
                            if (oldMsg != null) {
                                MessagesController$71.this.this$0.dialogMessagesByIds.remove(Integer.valueOf(oldMsg.getId()));
                                if (oldMsg.messageOwner.random_id != 0) {
                                    MessagesController$71.this.this$0.dialogMessagesByRandomIds.remove(Long.valueOf(oldMsg.messageOwner.random_id));
                                }
                            }
                            if (messageObject == null) {
                                MessagesController$71.this.this$0.checkLastDialogMessage(value, null, 0);
                            }
                        }
                    }
                }
                MessagesController$71.this.this$0.dialogs.clear();
                MessagesController$71.this.this$0.dialogs.addAll(MessagesController$71.this.this$0.dialogs_dict.values());
                MessagesController$71.this.this$0.sortDialogs(null);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                NotificationsController.getInstance().processDialogsUpdateRead(dialogsToUpdate);
            }
        });
    }
}
