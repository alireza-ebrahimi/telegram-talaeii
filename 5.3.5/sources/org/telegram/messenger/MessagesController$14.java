package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInfo;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_chatFull;
import org.telegram.tgnet.TLRPC$TL_updateReadChannelInbox;
import org.telegram.tgnet.TLRPC$TL_updateReadChannelOutbox;
import org.telegram.tgnet.TLRPC$Update;

class MessagesController$14 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$Chat val$chat;
    final /* synthetic */ int val$chat_id;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ long val$dialog_id;

    MessagesController$14(MessagesController this$0, TLRPC$Chat tLRPC$Chat, long j, int i, int i2) {
        this.this$0 = this$0;
        this.val$chat = tLRPC$Chat;
        this.val$dialog_id = j;
        this.val$chat_id = i;
        this.val$classGuid = i2;
    }

    public void run(TLObject response, final TLRPC$TL_error error) {
        if (error == null) {
            final TLRPC$TL_messages_chatFull res = (TLRPC$TL_messages_chatFull) response;
            MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
            MessagesStorage.getInstance().updateChatInfo(res.full_chat, false);
            if (ChatObject.isChannel(this.val$chat)) {
                ArrayList<TLRPC$Update> arrayList;
                Integer value = (Integer) this.this$0.dialogs_read_inbox_max.get(Long.valueOf(this.val$dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, this.val$dialog_id));
                }
                this.this$0.dialogs_read_inbox_max.put(Long.valueOf(this.val$dialog_id), Integer.valueOf(Math.max(res.full_chat.read_inbox_max_id, value.intValue())));
                if (value.intValue() == 0) {
                    arrayList = new ArrayList();
                    TLRPC$TL_updateReadChannelInbox update = new TLRPC$TL_updateReadChannelInbox();
                    update.channel_id = this.val$chat_id;
                    update.max_id = res.full_chat.read_inbox_max_id;
                    arrayList.add(update);
                    this.this$0.processUpdateArray(arrayList, null, null, false);
                }
                value = (Integer) this.this$0.dialogs_read_outbox_max.get(Long.valueOf(this.val$dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, this.val$dialog_id));
                }
                this.this$0.dialogs_read_outbox_max.put(Long.valueOf(this.val$dialog_id), Integer.valueOf(Math.max(res.full_chat.read_outbox_max_id, value.intValue())));
                if (value.intValue() == 0) {
                    arrayList = new ArrayList();
                    TLRPC$TL_updateReadChannelOutbox update2 = new TLRPC$TL_updateReadChannelOutbox();
                    update2.channel_id = this.val$chat_id;
                    update2.max_id = res.full_chat.read_outbox_max_id;
                    arrayList.add(update2);
                    this.this$0.processUpdateArray(arrayList, null, null, false);
                }
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.access$2100(MessagesController$14.this.this$0, (long) (-MessagesController$14.this.val$chat_id), res.full_chat.notify_settings);
                    for (int a = 0; a < res.full_chat.bot_info.size(); a++) {
                        BotQuery.putBotInfo((TLRPC$BotInfo) res.full_chat.bot_info.get(a));
                    }
                    MessagesController.access$2200(MessagesController$14.this.this$0).put(Integer.valueOf(MessagesController$14.this.val$chat_id), res.full_chat.exported_invite);
                    MessagesController.access$2300(MessagesController$14.this.this$0).remove(Integer.valueOf(MessagesController$14.this.val$chat_id));
                    MessagesController.access$2400(MessagesController$14.this.this$0).add(Integer.valueOf(MessagesController$14.this.val$chat_id));
                    if (!res.chats.isEmpty()) {
                        ((TLRPC$Chat) res.chats.get(0)).address = res.full_chat.about;
                    }
                    MessagesController$14.this.this$0.putUsers(res.users, false);
                    MessagesController$14.this.this$0.putChats(res.chats, false);
                    if (res.full_chat.stickerset != null) {
                        StickersQuery.getGroupStickerSetById(res.full_chat.stickerset);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{res.full_chat, Integer.valueOf(MessagesController$14.this.val$classGuid), Boolean.valueOf(false), null});
                }
            });
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.access$2500(MessagesController$14.this.this$0, error.text, MessagesController$14.this.val$chat_id);
                MessagesController.access$2300(MessagesController$14.this.this$0).remove(Integer.valueOf(MessagesController$14.this.val$chat_id));
            }
        });
    }
}
