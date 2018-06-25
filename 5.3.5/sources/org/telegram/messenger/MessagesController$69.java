package org.telegram.messenger;

import java.util.HashMap;
import java.util.Map.Entry;
import org.telegram.tgnet.TLRPC$TL_dialog;

class MessagesController$69 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ HashMap val$dialogsMentionsToUpdate;
    final /* synthetic */ HashMap val$dialogsToUpdate;

    MessagesController$69(MessagesController this$0, HashMap hashMap, HashMap hashMap2) {
        this.this$0 = this$0;
        this.val$dialogsToUpdate = hashMap;
        this.val$dialogsMentionsToUpdate = hashMap2;
    }

    public void run() {
        TLRPC$TL_dialog currentDialog;
        if (this.val$dialogsToUpdate != null) {
            for (Entry<Long, Integer> entry : this.val$dialogsToUpdate.entrySet()) {
                currentDialog = (TLRPC$TL_dialog) this.this$0.dialogs_dict.get((Long) entry.getKey());
                if (currentDialog != null) {
                    currentDialog.unread_count = ((Integer) entry.getValue()).intValue();
                }
            }
        }
        if (this.val$dialogsMentionsToUpdate != null) {
            for (Entry<Long, Integer> entry2 : this.val$dialogsMentionsToUpdate.entrySet()) {
                currentDialog = (TLRPC$TL_dialog) this.this$0.dialogs_dict.get((Long) entry2.getKey());
                if (currentDialog != null) {
                    currentDialog.unread_mentions_count = ((Integer) entry2.getValue()).intValue();
                    if (MessagesController.access$5600(this.this$0).contains(Long.valueOf(currentDialog.id))) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMentionsCount, new Object[]{Long.valueOf(currentDialog.id), Integer.valueOf(currentDialog.unread_mentions_count)});
                    }
                }
            }
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(256)});
        if (this.val$dialogsToUpdate != null) {
            NotificationsController.getInstance().processDialogsUpdateRead(this.val$dialogsToUpdate);
        }
    }
}
