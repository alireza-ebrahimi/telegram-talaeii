package org.telegram.messenger;

import java.util.HashMap;
import java.util.Locale;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$66 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$offset;

    /* renamed from: org.telegram.messenger.MessagesController$66$2 */
    class C14992 implements Runnable {
        C14992() {
        }

        public void run() {
            MessagesController.access$5302(MessagesController$66.this.this$0, false);
        }
    }

    MessagesController$66(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$offset = i;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            final TLRPC$messages_Dialogs dialogsRes = (TLRPC$messages_Dialogs) response;
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$66$1$1 */
                class C14971 implements Runnable {
                    C14971() {
                    }

                    public void run() {
                        MessagesController.access$5302(MessagesController$66.this.this$0, false);
                    }
                }

                public void run() {
                    try {
                        int a;
                        TLRPC$Message message;
                        int offsetId;
                        TLRPC$TL_dialog dialog;
                        UserConfig.totalDialogsLoadCount += dialogsRes.dialogs.size();
                        TLRPC$Message lastMessage = null;
                        for (a = 0; a < dialogsRes.messages.size(); a++) {
                            message = (TLRPC$Message) dialogsRes.messages.get(a);
                            FileLog.e("search migrate id " + message.id + " date " + LocaleController.getInstance().formatterStats.format(((long) message.date) * 1000));
                            if (lastMessage == null || message.date < lastMessage.date) {
                                lastMessage = message;
                            }
                        }
                        FileLog.e("migrate step with id " + lastMessage.id + " date " + LocaleController.getInstance().formatterStats.format(((long) lastMessage.date) * 1000));
                        if (dialogsRes.dialogs.size() >= 100) {
                            offsetId = lastMessage.id;
                        } else {
                            FileLog.e("migrate stop due to not 100 dialogs");
                            UserConfig.dialogsLoadOffsetId = Integer.MAX_VALUE;
                            UserConfig.dialogsLoadOffsetDate = UserConfig.migrateOffsetDate;
                            UserConfig.dialogsLoadOffsetUserId = UserConfig.migrateOffsetUserId;
                            UserConfig.dialogsLoadOffsetChatId = UserConfig.migrateOffsetChatId;
                            UserConfig.dialogsLoadOffsetChannelId = UserConfig.migrateOffsetChannelId;
                            UserConfig.dialogsLoadOffsetAccess = UserConfig.migrateOffsetAccess;
                            offsetId = -1;
                        }
                        StringBuilder stringBuilder = new StringBuilder(dialogsRes.dialogs.size() * 12);
                        HashMap<Long, TLRPC$TL_dialog> dialogHashMap = new HashMap();
                        for (a = 0; a < dialogsRes.dialogs.size(); a++) {
                            dialog = (TLRPC$TL_dialog) dialogsRes.dialogs.get(a);
                            if (dialog.peer.channel_id != 0) {
                                dialog.id = (long) (-dialog.peer.channel_id);
                            } else if (dialog.peer.chat_id != 0) {
                                dialog.id = (long) (-dialog.peer.chat_id);
                            } else {
                                dialog.id = (long) dialog.peer.user_id;
                            }
                            if (stringBuilder.length() > 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(dialog.id);
                            dialogHashMap.put(Long.valueOf(dialog.id), dialog);
                        }
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT did FROM dialogs WHERE did IN (%s)", new Object[]{stringBuilder.toString()}), new Object[0]);
                        while (cursor.next()) {
                            long did = cursor.longValue(0);
                            dialog = (TLRPC$TL_dialog) dialogHashMap.remove(Long.valueOf(did));
                            if (dialog != null) {
                                dialogsRes.dialogs.remove(dialog);
                                a = 0;
                                while (a < dialogsRes.messages.size()) {
                                    message = (TLRPC$Message) dialogsRes.messages.get(a);
                                    if (MessageObject.getDialogId(message) == did) {
                                        dialogsRes.messages.remove(a);
                                        a--;
                                        if (message.id == dialog.top_message) {
                                            dialog.top_message = 0;
                                            break;
                                        }
                                    }
                                    a++;
                                }
                            }
                        }
                        cursor.dispose();
                        FileLog.e("migrate found missing dialogs " + dialogsRes.dialogs.size());
                        cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT min(date) FROM dialogs WHERE date != 0 AND did >> 32 IN (0, -1)", new Object[0]);
                        if (cursor.next()) {
                            int date = Math.max(1441062000, cursor.intValue(0));
                            a = 0;
                            while (a < dialogsRes.messages.size()) {
                                message = (TLRPC$Message) dialogsRes.messages.get(a);
                                if (message.date < date) {
                                    if (MessagesController$66.this.val$offset != -1) {
                                        UserConfig.dialogsLoadOffsetId = UserConfig.migrateOffsetId;
                                        UserConfig.dialogsLoadOffsetDate = UserConfig.migrateOffsetDate;
                                        UserConfig.dialogsLoadOffsetUserId = UserConfig.migrateOffsetUserId;
                                        UserConfig.dialogsLoadOffsetChatId = UserConfig.migrateOffsetChatId;
                                        UserConfig.dialogsLoadOffsetChannelId = UserConfig.migrateOffsetChannelId;
                                        UserConfig.dialogsLoadOffsetAccess = UserConfig.migrateOffsetAccess;
                                        offsetId = -1;
                                        FileLog.e("migrate stop due to reached loaded dialogs " + LocaleController.getInstance().formatterStats.format(((long) date) * 1000));
                                    }
                                    dialogsRes.messages.remove(a);
                                    a--;
                                    dialog = (TLRPC$TL_dialog) dialogHashMap.remove(Long.valueOf(MessageObject.getDialogId(message)));
                                    if (dialog != null) {
                                        dialogsRes.dialogs.remove(dialog);
                                    }
                                }
                                a++;
                            }
                            if (!(lastMessage == null || lastMessage.date >= date || MessagesController$66.this.val$offset == -1)) {
                                UserConfig.dialogsLoadOffsetId = UserConfig.migrateOffsetId;
                                UserConfig.dialogsLoadOffsetDate = UserConfig.migrateOffsetDate;
                                UserConfig.dialogsLoadOffsetUserId = UserConfig.migrateOffsetUserId;
                                UserConfig.dialogsLoadOffsetChatId = UserConfig.migrateOffsetChatId;
                                UserConfig.dialogsLoadOffsetChannelId = UserConfig.migrateOffsetChannelId;
                                UserConfig.dialogsLoadOffsetAccess = UserConfig.migrateOffsetAccess;
                                offsetId = -1;
                                FileLog.e("migrate stop due to reached loaded dialogs " + LocaleController.getInstance().formatterStats.format(((long) date) * 1000));
                            }
                        }
                        cursor.dispose();
                        UserConfig.migrateOffsetDate = lastMessage.date;
                        TLRPC$Chat chat;
                        if (lastMessage.to_id.channel_id != 0) {
                            UserConfig.migrateOffsetChannelId = lastMessage.to_id.channel_id;
                            UserConfig.migrateOffsetChatId = 0;
                            UserConfig.migrateOffsetUserId = 0;
                            for (a = 0; a < dialogsRes.chats.size(); a++) {
                                chat = (TLRPC$Chat) dialogsRes.chats.get(a);
                                if (chat.id == UserConfig.migrateOffsetChannelId) {
                                    UserConfig.migrateOffsetAccess = chat.access_hash;
                                    break;
                                }
                            }
                        } else if (lastMessage.to_id.chat_id != 0) {
                            UserConfig.migrateOffsetChatId = lastMessage.to_id.chat_id;
                            UserConfig.migrateOffsetChannelId = 0;
                            UserConfig.migrateOffsetUserId = 0;
                            for (a = 0; a < dialogsRes.chats.size(); a++) {
                                chat = (TLRPC$Chat) dialogsRes.chats.get(a);
                                if (chat.id == UserConfig.migrateOffsetChatId) {
                                    UserConfig.migrateOffsetAccess = chat.access_hash;
                                    break;
                                }
                            }
                        } else if (lastMessage.to_id.user_id != 0) {
                            UserConfig.migrateOffsetUserId = lastMessage.to_id.user_id;
                            UserConfig.migrateOffsetChatId = 0;
                            UserConfig.migrateOffsetChannelId = 0;
                            for (a = 0; a < dialogsRes.users.size(); a++) {
                                User user = (User) dialogsRes.users.get(a);
                                if (user.id == UserConfig.migrateOffsetUserId) {
                                    UserConfig.migrateOffsetAccess = user.access_hash;
                                    break;
                                }
                            }
                        }
                        MessagesController$66.this.this$0.processLoadedDialogs(dialogsRes, null, offsetId, 0, 0, false, true, false);
                    } catch (Exception e) {
                        FileLog.e(e);
                        AndroidUtilities.runOnUIThread(new C14971());
                    }
                }
            });
            return;
        }
        AndroidUtilities.runOnUIThread(new C14992());
    }
}
