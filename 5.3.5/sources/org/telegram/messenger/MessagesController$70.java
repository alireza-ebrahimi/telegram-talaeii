package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_dialogs;
import org.telegram.tgnet.TLRPC$messages_Messages;

class MessagesController$70 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$TL_dialog val$dialog;
    final /* synthetic */ int val$lower_id;
    final /* synthetic */ long val$newTaskId;

    /* renamed from: org.telegram.messenger.MessagesController$70$1 */
    class C15041 implements Runnable {
        C15041() {
        }

        public void run() {
            TLRPC$TL_dialog currentDialog = (TLRPC$TL_dialog) MessagesController$70.this.this$0.dialogs_dict.get(Long.valueOf(MessagesController$70.this.val$dialog.id));
            if (currentDialog != null && currentDialog.top_message == 0) {
                MessagesController$70.this.this$0.deleteDialog(MessagesController$70.this.val$dialog.id, 3);
            }
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$70$2 */
    class C15052 implements Runnable {
        C15052() {
        }

        public void run() {
            MessagesController.access$5700(MessagesController$70.this.this$0).remove(Integer.valueOf(MessagesController$70.this.val$lower_id));
        }
    }

    MessagesController$70(MessagesController this$0, TLRPC$TL_dialog tLRPC$TL_dialog, long j, int i) {
        this.this$0 = this$0;
        this.val$dialog = tLRPC$TL_dialog;
        this.val$newTaskId = j;
        this.val$lower_id = i;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            TLRPC$messages_Messages res = (TLRPC$messages_Messages) response;
            if (res.messages.isEmpty()) {
                AndroidUtilities.runOnUIThread(new C15041());
            } else {
                TLRPC$TL_messages_dialogs dialogs = new TLRPC$TL_messages_dialogs();
                TLRPC$Message newMessage = (TLRPC$Message) res.messages.get(0);
                TLRPC$TL_dialog newDialog = new TLRPC$TL_dialog();
                newDialog.flags = this.val$dialog.flags;
                newDialog.top_message = newMessage.id;
                newDialog.last_message_date = newMessage.date;
                newDialog.notify_settings = this.val$dialog.notify_settings;
                newDialog.pts = this.val$dialog.pts;
                newDialog.unread_count = this.val$dialog.unread_count;
                newDialog.unread_mentions_count = this.val$dialog.unread_mentions_count;
                newDialog.read_inbox_max_id = this.val$dialog.read_inbox_max_id;
                newDialog.read_outbox_max_id = this.val$dialog.read_outbox_max_id;
                newDialog.pinned = this.val$dialog.pinned;
                newDialog.pinnedNum = this.val$dialog.pinnedNum;
                long j = this.val$dialog.id;
                newDialog.id = j;
                newMessage.dialog_id = j;
                dialogs.users.addAll(res.users);
                dialogs.chats.addAll(res.chats);
                dialogs.dialogs.add(newDialog);
                dialogs.messages.addAll(res.messages);
                dialogs.count = 1;
                this.this$0.processDialogsUpdate(dialogs, null);
                MessagesStorage.getInstance().putMessages(res.messages, true, true, false, MediaController.getInstance().getAutodownloadMask(), true);
            }
        }
        if (this.val$newTaskId != 0) {
            MessagesStorage.getInstance().removePendingTask(this.val$newTaskId);
        }
        AndroidUtilities.runOnUIThread(new C15052());
    }
}
