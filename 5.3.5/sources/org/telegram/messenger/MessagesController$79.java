package org.telegram.messenger;

import java.util.HashMap;
import org.telegram.tgnet.TLRPC$TL_dialog;

class MessagesController$79 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ int val$max_positive_id;
    final /* synthetic */ boolean val$popup;

    /* renamed from: org.telegram.messenger.MessagesController$79$1 */
    class C15071 implements Runnable {
        C15071() {
        }

        public void run() {
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController$79.this.this$0.dialogs_dict.get(Long.valueOf(MessagesController$79.this.val$dialog_id));
            if (dialog != null) {
                dialog.unread_count = 0;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(256)});
            }
            if (MessagesController$79.this.val$popup) {
                NotificationsController.getInstance().processReadMessages(null, MessagesController$79.this.val$dialog_id, 0, MessagesController$79.this.val$max_positive_id, true);
                HashMap<Long, Integer> dialogsToUpdate = new HashMap();
                dialogsToUpdate.put(Long.valueOf(MessagesController$79.this.val$dialog_id), Integer.valueOf(-1));
                NotificationsController.getInstance().processDialogsUpdateRead(dialogsToUpdate);
                return;
            }
            NotificationsController.getInstance().processReadMessages(null, MessagesController$79.this.val$dialog_id, 0, MessagesController$79.this.val$max_positive_id, false);
            dialogsToUpdate = new HashMap();
            dialogsToUpdate.put(Long.valueOf(MessagesController$79.this.val$dialog_id), Integer.valueOf(0));
            NotificationsController.getInstance().processDialogsUpdateRead(dialogsToUpdate);
        }
    }

    MessagesController$79(MessagesController this$0, long j, boolean z, int i) {
        this.this$0 = this$0;
        this.val$dialog_id = j;
        this.val$popup = z;
        this.val$max_positive_id = i;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C15071());
    }
}
