package org.telegram.messenger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_draftMessage;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardHide;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ChatActivity;

class MessagesController$67 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$count;
    final /* synthetic */ TLRPC$messages_Dialogs val$dialogsRes;
    final /* synthetic */ ArrayList val$encChats;
    final /* synthetic */ boolean val$fromCache;
    final /* synthetic */ int val$loadType;
    final /* synthetic */ boolean val$migrate;
    final /* synthetic */ int val$offset;
    final /* synthetic */ boolean val$resetEnd;

    /* renamed from: org.telegram.messenger.MessagesController$67$1 */
    class C15001 implements Runnable {
        C15001() {
        }

        public void run() {
            MessagesController$67.this.this$0.putUsers(MessagesController$67.this.val$dialogsRes.users, true);
            MessagesController$67.this.this$0.loadingDialogs = false;
            if (MessagesController$67.this.val$resetEnd) {
                MessagesController$67.this.this$0.dialogsEndReached = false;
                MessagesController$67.this.this$0.serverDialogsEndReached = false;
            } else if (UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE) {
                MessagesController$67.this.this$0.dialogsEndReached = true;
                MessagesController$67.this.this$0.serverDialogsEndReached = true;
            } else {
                MessagesController$67.this.this$0.loadDialogs(0, MessagesController$67.this.val$count, false);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
        }
    }

    MessagesController$67(MessagesController this$0, int i, TLRPC$messages_Dialogs tLRPC$messages_Dialogs, boolean z, int i2, int i3, boolean z2, boolean z3, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$loadType = i;
        this.val$dialogsRes = tLRPC$messages_Dialogs;
        this.val$resetEnd = z;
        this.val$count = i2;
        this.val$offset = i3;
        this.val$fromCache = z2;
        this.val$migrate = z3;
        this.val$encChats = arrayList;
    }

    public void run() {
        if (!this.this$0.firstGettingTask) {
            this.this$0.getNewDeleteTask(null, 0);
            this.this$0.firstGettingTask = true;
        }
        FileLog.e("loaded loadType " + this.val$loadType + " count " + this.val$dialogsRes.dialogs.size());
        if (this.val$loadType == 1 && this.val$dialogsRes.dialogs.size() == 0) {
            AndroidUtilities.runOnUIThread(new C15001());
            return;
        }
        int a;
        User user;
        Integer value;
        final HashMap<Long, TLRPC$TL_dialog> new_dialogs_dict = new HashMap();
        final HashMap<Long, MessageObject> new_dialogMessage = new HashMap();
        AbstractMap usersDict = new HashMap();
        final HashMap<Integer, TLRPC$Chat> chatsDict = new HashMap();
        for (a = 0; a < this.val$dialogsRes.users.size(); a++) {
            User u = (User) this.val$dialogsRes.users.get(a);
            usersDict.put(Integer.valueOf(u.id), u);
        }
        for (a = 0; a < this.val$dialogsRes.chats.size(); a++) {
            TLRPC$Chat c = (TLRPC$Chat) this.val$dialogsRes.chats.get(a);
            chatsDict.put(Integer.valueOf(c.id), c);
        }
        if (this.val$loadType == 1) {
            this.this$0.nextDialogsCacheOffset = this.val$offset + this.val$count;
        }
        TLRPC$Message lastMessage = null;
        for (a = 0; a < this.val$dialogsRes.messages.size(); a++) {
            TLRPC$Chat chat;
            TLRPC$Message message = (TLRPC$Message) this.val$dialogsRes.messages.get(a);
            if (lastMessage == null || message.date < lastMessage.date) {
                lastMessage = message;
            }
            MessageObject messageObject;
            if (message.to_id.channel_id != 0) {
                chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(message.to_id.channel_id));
                if (chat == null || !chat.left) {
                    if (chat != null && chat.megagroup) {
                        message.flags |= Integer.MIN_VALUE;
                    }
                    messageObject = new MessageObject(message, usersDict, chatsDict, false);
                    new_dialogMessage.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                }
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
        if (!(this.val$fromCache || this.val$migrate || UserConfig.dialogsLoadOffsetId == -1 || this.val$loadType != 0)) {
            if (lastMessage == null || lastMessage.id == UserConfig.dialogsLoadOffsetId) {
                UserConfig.dialogsLoadOffsetId = Integer.MAX_VALUE;
            } else {
                UserConfig.totalDialogsLoadCount += this.val$dialogsRes.dialogs.size();
                UserConfig.dialogsLoadOffsetId = lastMessage.id;
                UserConfig.dialogsLoadOffsetDate = lastMessage.date;
                if (lastMessage.to_id.channel_id != 0) {
                    UserConfig.dialogsLoadOffsetChannelId = lastMessage.to_id.channel_id;
                    UserConfig.dialogsLoadOffsetChatId = 0;
                    UserConfig.dialogsLoadOffsetUserId = 0;
                    for (a = 0; a < this.val$dialogsRes.chats.size(); a++) {
                        chat = (TLRPC$Chat) this.val$dialogsRes.chats.get(a);
                        if (chat.id == UserConfig.dialogsLoadOffsetChannelId) {
                            UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                            break;
                        }
                    }
                } else if (lastMessage.to_id.chat_id != 0) {
                    UserConfig.dialogsLoadOffsetChatId = lastMessage.to_id.chat_id;
                    UserConfig.dialogsLoadOffsetChannelId = 0;
                    UserConfig.dialogsLoadOffsetUserId = 0;
                    for (a = 0; a < this.val$dialogsRes.chats.size(); a++) {
                        chat = (TLRPC$Chat) this.val$dialogsRes.chats.get(a);
                        if (chat.id == UserConfig.dialogsLoadOffsetChatId) {
                            UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                            break;
                        }
                    }
                } else if (lastMessage.to_id.user_id != 0) {
                    UserConfig.dialogsLoadOffsetUserId = lastMessage.to_id.user_id;
                    UserConfig.dialogsLoadOffsetChatId = 0;
                    UserConfig.dialogsLoadOffsetChannelId = 0;
                    for (a = 0; a < this.val$dialogsRes.users.size(); a++) {
                        user = (User) this.val$dialogsRes.users.get(a);
                        if (user.id == UserConfig.dialogsLoadOffsetUserId) {
                            UserConfig.dialogsLoadOffsetAccess = user.access_hash;
                            break;
                        }
                    }
                }
            }
            UserConfig.saveConfig(false);
        }
        final ArrayList<TLRPC$TL_dialog> dialogsToReload = new ArrayList();
        for (a = 0; a < this.val$dialogsRes.dialogs.size(); a++) {
            TLRPC$TL_dialog d = (TLRPC$TL_dialog) this.val$dialogsRes.dialogs.get(a);
            if (d.id == 0 && d.peer != null) {
                if (d.peer.user_id != 0) {
                    d.id = (long) d.peer.user_id;
                } else if (d.peer.chat_id != 0) {
                    d.id = (long) (-d.peer.chat_id);
                } else if (d.peer.channel_id != 0) {
                    d.id = (long) (-d.peer.channel_id);
                }
            }
            if (d.id != 0) {
                if (d.last_message_date == 0) {
                    MessageObject mess = (MessageObject) new_dialogMessage.get(Long.valueOf(d.id));
                    if (mess != null) {
                        d.last_message_date = mess.messageOwner.date;
                    }
                }
                boolean allowCheck = true;
                if (DialogObject.isChannel(d)) {
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-((int) d.id)));
                    if (chat != null) {
                        if (!chat.megagroup) {
                            allowCheck = false;
                        }
                        if (chat.left) {
                        }
                    }
                    MessagesController.access$1600(this.this$0).put(Integer.valueOf(-((int) d.id)), Integer.valueOf(d.pts));
                } else if (((int) d.id) < 0) {
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-((int) d.id)));
                    if (!(chat == null || chat.migrated_to == null)) {
                    }
                }
                new_dialogs_dict.put(Long.valueOf(d.id), d);
                if (allowCheck && this.val$loadType == 1 && ((d.read_outbox_max_id == 0 || d.read_inbox_max_id == 0) && d.top_message != 0)) {
                    dialogsToReload.add(d);
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
        if (this.val$loadType != 1) {
            ImageLoader.saveMessagesThumbs(this.val$dialogsRes.messages);
            a = 0;
            while (a < this.val$dialogsRes.messages.size()) {
                message = (TLRPC$Message) this.val$dialogsRes.messages.get(a);
                if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                    if (this.this$0.hideLeftGroup && message.action.user_id == message.from_id) {
                        a++;
                    } else {
                        user = (User) usersDict.get(Integer.valueOf(message.action.user_id));
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
                    boolean z;
                    ConcurrentHashMap<Long, Integer> read_max = message.out ? this.this$0.dialogs_read_outbox_max : this.this$0.dialogs_read_inbox_max;
                    value = (Integer) read_max.get(Long.valueOf(message.dialog_id));
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
                    a++;
                }
            }
            MessagesStorage.getInstance().putDialogs(this.val$dialogsRes, false);
        }
        if (this.val$loadType == 2) {
            chat = (TLRPC$Chat) this.val$dialogsRes.chats.get(0);
            MessagesController.access$4800(this.this$0, chat.id);
            this.this$0.checkChannelInviter(chat.id);
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (MessagesController$67.this.val$loadType != 1) {
                    MessagesController.access$5200(MessagesController$67.this.this$0, MessagesController$67.this.val$dialogsRes.dialogs);
                    if (!UserConfig.draftsLoaded) {
                        DraftQuery.loadDrafts();
                    }
                }
                MessagesController$67.this.this$0.putUsers(MessagesController$67.this.val$dialogsRes.users, MessagesController$67.this.val$loadType == 1);
                MessagesController$67.this.this$0.putChats(MessagesController$67.this.val$dialogsRes.chats, MessagesController$67.this.val$loadType == 1);
                if (MessagesController$67.this.val$encChats != null) {
                    for (int a = 0; a < MessagesController$67.this.val$encChats.size(); a++) {
                        TLRPC$EncryptedChat encryptedChat = (TLRPC$EncryptedChat) MessagesController$67.this.val$encChats.get(a);
                        if ((encryptedChat instanceof TLRPC$TL_encryptedChat) && AndroidUtilities.getMyLayerVersion(encryptedChat.layer) < 73) {
                            SecretChatHelper.getInstance().sendNotifyLayerMessage(encryptedChat, null);
                        }
                        MessagesController$67.this.this$0.putEncryptedChat(encryptedChat, true);
                    }
                }
                if (!MessagesController$67.this.val$migrate) {
                    MessagesController$67.this.this$0.loadingDialogs = false;
                }
                boolean added = false;
                int lastDialogDate = (!MessagesController$67.this.val$migrate || MessagesController$67.this.this$0.dialogs.isEmpty()) ? 0 : ((TLRPC$TL_dialog) MessagesController$67.this.this$0.dialogs.get(MessagesController$67.this.this$0.dialogs.size() - 1)).last_message_date;
                for (Entry<Long, TLRPC$TL_dialog> pair : new_dialogs_dict.entrySet()) {
                    Long key = (Long) pair.getKey();
                    TLRPC$TL_dialog value = (TLRPC$TL_dialog) pair.getValue();
                    if (!MessagesController$67.this.val$migrate || lastDialogDate == 0 || value.last_message_date >= lastDialogDate) {
                        TLRPC$TL_dialog currentDialog = (TLRPC$TL_dialog) MessagesController$67.this.this$0.dialogs_dict.get(key);
                        if (MessagesController$67.this.val$loadType != 1 && (value.draft instanceof TLRPC$TL_draftMessage)) {
                            DraftQuery.saveDraft(value.id, value.draft, null, false);
                        }
                        MessageObject messageObject;
                        if (currentDialog == null) {
                            added = true;
                            MessagesController$67.this.this$0.dialogs_dict.put(key, value);
                            messageObject = (MessageObject) new_dialogMessage.get(Long.valueOf(value.id));
                            MessagesController$67.this.this$0.dialogMessage.put(key, messageObject);
                            if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                MessagesController$67.this.this$0.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                if (messageObject.messageOwner.random_id != 0) {
                                    MessagesController$67.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                }
                            }
                        } else {
                            if (MessagesController$67.this.val$loadType != 1) {
                                currentDialog.notify_settings = value.notify_settings;
                            }
                            currentDialog.pinned = value.pinned;
                            currentDialog.pinnedNum = value.pinnedNum;
                            MessageObject oldMsg = (MessageObject) MessagesController$67.this.this$0.dialogMessage.get(key);
                            if ((oldMsg == null || !oldMsg.deleted) && oldMsg != null && currentDialog.top_message <= 0) {
                                MessageObject newMsg = (MessageObject) new_dialogMessage.get(Long.valueOf(value.id));
                                if (oldMsg.deleted || newMsg == null || newMsg.messageOwner.date > oldMsg.messageOwner.date) {
                                    MessagesController$67.this.this$0.dialogs_dict.put(key, value);
                                    MessagesController$67.this.this$0.dialogMessage.put(key, newMsg);
                                    if (newMsg != null && newMsg.messageOwner.to_id.channel_id == 0) {
                                        MessagesController$67.this.this$0.dialogMessagesByIds.put(Integer.valueOf(newMsg.getId()), newMsg);
                                        if (!(newMsg == null || newMsg.messageOwner.random_id == 0)) {
                                            MessagesController$67.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(newMsg.messageOwner.random_id), newMsg);
                                        }
                                    }
                                    MessagesController$67.this.this$0.dialogMessagesByIds.remove(Integer.valueOf(oldMsg.getId()));
                                    if (oldMsg.messageOwner.random_id != 0) {
                                        MessagesController$67.this.this$0.dialogMessagesByRandomIds.remove(Long.valueOf(oldMsg.messageOwner.random_id));
                                    }
                                }
                            } else if (value.top_message >= currentDialog.top_message) {
                                MessagesController$67.this.this$0.dialogs_dict.put(key, value);
                                messageObject = (MessageObject) new_dialogMessage.get(Long.valueOf(value.id));
                                MessagesController$67.this.this$0.dialogMessage.put(key, messageObject);
                                if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                    MessagesController$67.this.this$0.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                    if (!(messageObject == null || messageObject.messageOwner.random_id == 0)) {
                                        MessagesController$67.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                    }
                                }
                                if (oldMsg != null) {
                                    MessagesController$67.this.this$0.dialogMessagesByIds.remove(Integer.valueOf(oldMsg.getId()));
                                    if (oldMsg.messageOwner.random_id != 0) {
                                        MessagesController$67.this.this$0.dialogMessagesByRandomIds.remove(Long.valueOf(oldMsg.messageOwner.random_id));
                                    }
                                }
                            }
                        }
                    }
                }
                MessagesController$67.this.this$0.dialogs.clear();
                MessagesController$67.this.this$0.dialogs.addAll(MessagesController$67.this.this$0.dialogs_dict.values());
                MessagesController$67.this.this$0.sortDialogs(MessagesController$67.this.val$migrate ? chatsDict : null);
                if (!(MessagesController$67.this.val$loadType == 2 || MessagesController$67.this.val$migrate)) {
                    MessagesController messagesController = MessagesController$67.this.this$0;
                    boolean z = (MessagesController$67.this.val$dialogsRes.dialogs.size() == 0 || MessagesController$67.this.val$dialogsRes.dialogs.size() != MessagesController$67.this.val$count) && MessagesController$67.this.val$loadType == 0;
                    messagesController.dialogsEndReached = z;
                    if (!MessagesController$67.this.val$fromCache) {
                        messagesController = MessagesController$67.this.this$0;
                        z = (MessagesController$67.this.val$dialogsRes.dialogs.size() == 0 || MessagesController$67.this.val$dialogsRes.dialogs.size() != MessagesController$67.this.val$count) && MessagesController$67.this.val$loadType == 0;
                        messagesController.serverDialogsEndReached = z;
                    }
                }
                if (!(MessagesController$67.this.val$fromCache || MessagesController$67.this.val$migrate || UserConfig.totalDialogsLoadCount >= ChatActivity.scheduleDownloads || UserConfig.dialogsLoadOffsetId == -1 || UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE)) {
                    MessagesController$67.this.this$0.loadDialogs(0, 100, false);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                if (MessagesController$67.this.val$migrate) {
                    UserConfig.migrateOffsetId = MessagesController$67.this.val$offset;
                    UserConfig.saveConfig(false);
                    MessagesController.access$5302(MessagesController$67.this.this$0, false);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
                } else {
                    MessagesController$67.this.this$0.generateUpdateMessage();
                    if (!added && MessagesController$67.this.val$loadType == 1) {
                        MessagesController$67.this.this$0.loadDialogs(0, MessagesController$67.this.val$count, false);
                    }
                }
                MessagesController.access$5400(MessagesController$67.this.this$0, UserConfig.migrateOffsetId, UserConfig.migrateOffsetDate, UserConfig.migrateOffsetUserId, UserConfig.migrateOffsetChatId, UserConfig.migrateOffsetChannelId, UserConfig.migrateOffsetAccess);
                if (!dialogsToReload.isEmpty()) {
                    MessagesController.access$5500(MessagesController$67.this.this$0, dialogsToReload, 0);
                }
            }
        });
    }
}
