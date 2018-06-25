package org.telegram.messenger;

import java.util.HashMap;
import org.telegram.tgnet.TLRPC$TL_dialog;

class MessagesController$82 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ int val$max_date;
    final /* synthetic */ boolean val$popup;

    /* renamed from: org.telegram.messenger.MessagesController$82$1 */
    class C15081 implements Runnable {
        C15081() {
        }

        public void run() {
            NotificationsController.getInstance().processReadMessages(null, MessagesController$82.this.val$dialog_id, MessagesController$82.this.val$max_date, 0, MessagesController$82.this.val$popup);
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController$82.this.this$0.dialogs_dict.get(Long.valueOf(MessagesController$82.this.val$dialog_id));
            if (dialog != null) {
                dialog.unread_count = 0;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(256)});
            }
            HashMap<Long, Integer> dialogsToUpdate = new HashMap();
            dialogsToUpdate.put(Long.valueOf(MessagesController$82.this.val$dialog_id), Integer.valueOf(0));
            NotificationsController.getInstance().processDialogsUpdateRead(dialogsToUpdate);
        }
    }

    MessagesController$82(MessagesController this$0, long j, int i, boolean z) {
        this.this$0 = this$0;
        this.val$dialog_id = j;
        this.val$max_date = i;
        this.val$popup = z;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C15081());
    }
}
