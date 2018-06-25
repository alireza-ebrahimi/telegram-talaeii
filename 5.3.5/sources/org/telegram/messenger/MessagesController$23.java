package org.telegram.messenger;

import android.util.SparseArray;

class MessagesController$23 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ SparseArray val$mids;

    MessagesController$23(MessagesController this$0, SparseArray sparseArray) {
        this.this$0 = this$0;
        this.val$mids = sparseArray;
    }

    public void run() {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.didCreatedNewDeleteTask, new Object[]{this.val$mids});
    }
}
