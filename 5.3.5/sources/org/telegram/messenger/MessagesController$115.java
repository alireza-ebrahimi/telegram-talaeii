package org.telegram.messenger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_dialogs;
import org.telegram.tgnet.TLRPC$TL_messages_peerDialogs;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$115 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$newDialogId;
    final /* synthetic */ ArrayList val$order;

    MessagesController$115(MessagesController this$0, ArrayList arrayList, long j) {
        this.this$0 = this$0;
        this.val$order = arrayList;
        this.val$newDialogId = j;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            int a;
            TLRPC$Chat chat;
            final TLRPC$TL_messages_peerDialogs res = (TLRPC$TL_messages_peerDialogs) response;
            final TLRPC$TL_messages_dialogs toCache = new TLRPC$TL_messages_dialogs();
            toCache.users.addAll(res.users);
            toCache.chats.addAll(res.chats);
            toCache.dialogs.addAll(res.dialogs);
            toCache.messages.addAll(res.messages);
            final HashMap<Long, MessageObject> new_dialogMessage = new HashMap();
            AbstractMap usersDict = new HashMap();
            HashMap<Integer, TLRPC$Chat> chatsDict = new HashMap();
            final ArrayList<Long> newPinnedOrder = new ArrayList();
            for (a = 0; a < res.users.size(); a++) {
                User u = (User) res.users.get(a);
                usersDict.put(Integer.valueOf(u.id), u);
            }
            for (a = 0; a < res.chats.size(); a++) {
                TLRPC$Chat c = (TLRPC$Chat) res.chats.get(a);
                chatsDict.put(Integer.valueOf(c.id), c);
            }
            for (a = 0; a < res.messages.size(); a++) {
                TLRPC$Message message = (TLRPC$Message) res.messages.get(a);
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
            for (a = 0; a < res.dialogs.size(); a++) {
                TLRPC$TL_dialog d = (TLRPC$TL_dialog) res.dialogs.get(a);
                if (d.id == 0) {
                    if (d.peer.user_id != 0) {
                        d.id = (long) d.peer.user_id;
                    } else if (d.peer.chat_id != 0) {
                        d.id = (long) (-d.peer.chat_id);
                    } else if (d.peer.channel_id != 0) {
                        d.id = (long) (-d.peer.channel_id);
                    }
                }
                newPinnedOrder.add(Long.valueOf(d.id));
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
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$115$1$1 */
                class C14531 implements Runnable {
                    C14531() {
                    }

                    public void run() {
                        int a;
                        MessagesController.access$5200(MessagesController$115.this.this$0, res.dialogs);
                        boolean changed = false;
                        boolean added = false;
                        int maxPinnedNum = 0;
                        HashMap<Long, Integer> oldPinnedDialogNums = new HashMap();
                        ArrayList<Long> oldPinnedOrder = new ArrayList();
                        for (a = 0; a < MessagesController$115.this.this$0.dialogs.size(); a++) {
                            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController$115.this.this$0.dialogs.get(a);
                            if (((int) dialog.id) != 0) {
                                if (!dialog.pinned) {
                                    break;
                                }
                                maxPinnedNum = Math.max(dialog.pinnedNum, maxPinnedNum);
                                oldPinnedDialogNums.put(Long.valueOf(dialog.id), Integer.valueOf(dialog.pinnedNum));
                                oldPinnedOrder.add(Long.valueOf(dialog.id));
                                dialog.pinned = false;
                                dialog.pinnedNum = 0;
                                changed = true;
                            }
                        }
                        ArrayList<Long> pinnedDialogs = new ArrayList();
                        ArrayList<Long> orderArrayList = MessagesController$115.this.val$order != null ? MessagesController$115.this.val$order : newPinnedOrder;
                        if (orderArrayList.size() < oldPinnedOrder.size()) {
                            orderArrayList.add(Long.valueOf(0));
                        }
                        while (oldPinnedOrder.size() < orderArrayList.size()) {
                            oldPinnedOrder.add(0, Long.valueOf(0));
                        }
                        if (!res.dialogs.isEmpty()) {
                            MessagesController$115.this.this$0.putUsers(res.users, false);
                            MessagesController$115.this.this$0.putChats(res.chats, false);
                            for (a = 0; a < res.dialogs.size(); a++) {
                                dialog = (TLRPC$TL_dialog) res.dialogs.get(a);
                                Integer oldNum;
                                if (MessagesController$115.this.val$newDialogId != 0) {
                                    oldNum = (Integer) oldPinnedDialogNums.get(Long.valueOf(dialog.id));
                                    if (oldNum != null) {
                                        dialog.pinnedNum = oldNum.intValue();
                                    }
                                } else {
                                    int oldIdx = oldPinnedOrder.indexOf(Long.valueOf(dialog.id));
                                    int newIdx = orderArrayList.indexOf(Long.valueOf(dialog.id));
                                    if (!(oldIdx == -1 || newIdx == -1)) {
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
                                }
                                if (dialog.pinnedNum == 0) {
                                    dialog.pinnedNum = (res.dialogs.size() - a) + maxPinnedNum;
                                }
                                pinnedDialogs.add(Long.valueOf(dialog.id));
                                TLRPC$TL_dialog d = (TLRPC$TL_dialog) MessagesController$115.this.this$0.dialogs_dict.get(Long.valueOf(dialog.id));
                                if (d != null) {
                                    d.pinned = true;
                                    d.pinnedNum = dialog.pinnedNum;
                                    MessagesStorage.getInstance().setDialogPinned(dialog.id, dialog.pinnedNum);
                                } else {
                                    added = true;
                                    MessagesController$115.this.this$0.dialogs_dict.put(Long.valueOf(dialog.id), dialog);
                                    MessageObject messageObject = (MessageObject) new_dialogMessage.get(Long.valueOf(dialog.id));
                                    MessagesController$115.this.this$0.dialogMessage.put(Long.valueOf(dialog.id), messageObject);
                                    if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                        MessagesController$115.this.this$0.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                        if (messageObject.messageOwner.random_id != 0) {
                                            MessagesController$115.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                        }
                                    }
                                }
                                changed = true;
                            }
                        }
                        if (changed) {
                            if (added) {
                                MessagesController$115.this.this$0.dialogs.clear();
                                MessagesController$115.this.this$0.dialogs.addAll(MessagesController$115.this.this$0.dialogs_dict.values());
                            }
                            MessagesController$115.this.this$0.sortDialogs(null);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                        }
                        MessagesStorage.getInstance().unpinAllDialogsExceptNew(pinnedDialogs);
                        MessagesStorage.getInstance().putDialogs(toCache, true);
                        UserConfig.pinnedDialogsLoaded = true;
                        UserConfig.saveConfig(false);
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C14531());
                }
            });
        }
    }
}
